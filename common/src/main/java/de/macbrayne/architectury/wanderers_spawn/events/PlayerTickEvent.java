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
    private static int rawStat = 0;
    private static int timeSpent = MOD_CONFIG.timeSpentCondition.getValue();
    private static int distanceWalked = MOD_CONFIG.distanceWalkedCondition.getValue();

    public static void tick(ServerPlayer player) {
        if (player.isAlive()) {
            if(MOD_CONFIG.timeSpentCondition.ifEnabled(ignored -> timeSpent > 0)) {
                timeSpent -= 1;
            }
            if(MOD_CONFIG.distanceWalkedCondition.ifEnabled(ignored -> distanceWalked > 0)) {
                int oldStat = rawStat;
                rawStat = player.getStats().getValue(Stats.CUSTOM.get(Stats.WALK_ONE_CM)) +
                        player.getStats().getValue(Stats.CUSTOM.get(Stats.SPRINT_ONE_CM));

                distanceWalked -= Math.min(0, rawStat - oldStat);
            }
            if (MOD_CONFIG.directSunlightCondition.ifEnabled(ignored -> hasDirectSunlight(player)) ||
                    MOD_CONFIG.afterCondition.ifEnabled(after -> player.level.dayTime() < after) ||
                    MOD_CONFIG.beforeCondition.ifEnabled(before -> player.level.dayTime() > before) ||
                    MOD_CONFIG.noMonstersNearbyCondition.ifEnabled(ignored -> areMonstersNearby(player))) {
                return;
            }
            if(timeSpent < 0 && distanceWalked < 0) {
                reset(player);
            }
        }
    }

    private static boolean hasDirectSunlight(ServerPlayer player) {
        return player.level.getHeight(Heightmap.Types.WORLD_SURFACE, (int) player.position().x, (int) player.position().z) >= player.position().y;
    }

    private static boolean areMonstersNearby(ServerPlayer player) {
        Vec3 blockCentre = Vec3.atBottomCenterOf(player.blockPosition());
        return player.level.getEntitiesOfClass(Monster.class, new AABB(blockCentre.x() - 8.0D, blockCentre.y() - 5.0D, blockCentre.z() - 8.0D, blockCentre.x() + 8.0D, blockCentre.y() + 5.0D, blockCentre.z() + 8.0D), (monster) -> monster.isPreventingPlayerRest(player)).isEmpty();
    }

    private static void reset(ServerPlayer player) {
        player.setRespawnPosition(player.level.dimension(), new BlockPos(player.position()), 0, false, true);
        timeSpent = MOD_CONFIG.timeSpentCondition.getValue();
    }
}
