package de.macbrayne.architectury.wanderers_spawn.events;

import de.macbrayne.architectury.wanderers_spawn.Reference;
import de.macbrayne.architectury.wanderers_spawn.config.PlayerConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class PlayerTickEvent {
    private static final PlayerConfig MOD_CONFIG = Reference.globalConfig;
    private static boolean distanceTriggered = false;
    private static boolean timeSpentTriggered = false;

    public static void tick(ServerPlayer player) {
        if (player.isAlive()) {
            int distanceWalkedRawValue = player.getStats().getValue(Stats.CUSTOM.get(Stats.WALK_ONE_CM)) +
                    player.getStats().getValue(Stats.CUSTOM.get(Stats.SPRINT_ONE_CM));

            if (!distanceTriggered && MOD_CONFIG.timeSpentCondition.notEnabledOr(time -> player.tickCount % time == 0)) {
                distanceTriggered = true;
            }
            if (!timeSpentTriggered && MOD_CONFIG.distanceWalkedCondition.notEnabledOr(distance -> distanceWalkedRawValue % distance == 0)) {
                timeSpentTriggered = true;
            }

            if (MOD_CONFIG.directSunlightCondition.notEnabledOr(directSunlight -> directSunlight == hasDirectSunlight(player)) &&
                    timeSpentTriggered &&
                    distanceTriggered &&
                    MOD_CONFIG.afterCondition.notEnabledOr(after -> player.level.dayTime() > after) &&
                    MOD_CONFIG.beforeCondition.notEnabledOr(before -> player.level.dayTime() < before) &&
                    MOD_CONFIG.minHealthCondition.notEnabledOr(minHealth -> player.getHealth() > minHealth) &&
                    MOD_CONFIG.noMonstersNearbyCondition.notEnabledOr(noMonsters -> noMonsters != areMonstersNearby(player))) {
                reset(player);
            }
        }
    }

    private static boolean hasDirectSunlight(ServerPlayer player) {
        return player.position().y >= player.level.getHeight(Heightmap.Types.WORLD_SURFACE, (int) player.position().x, (int) player.position().z);
    }

    private static boolean areMonstersNearby(ServerPlayer player) {
        Vec3 blockCentre = Vec3.atBottomCenterOf(player.blockPosition());
        return !player.level.getEntitiesOfClass(Monster.class,
                        new AABB(blockCentre.x() - 8.0D, blockCentre.y() - 5.0D,blockCentre.z() - 8.0D,
                                blockCentre.x() + 8.0D, blockCentre.y() + 5.0D, blockCentre.z() + 8.0D),
                        (monster) -> monster.isPreventingPlayerRest(player))
                .isEmpty();
    }

    private static void reset(ServerPlayer player) {
        player.setRespawnPosition(player.level.dimension(), new BlockPos(player.position()), 0, false, true);
        distanceTriggered = false;
        timeSpentTriggered = false;
    }
}
