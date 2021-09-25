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
    private static int distanceWalkedRawValue = 0;
    private static int timeLeft = MOD_CONFIG.timeSpentCondition.getValue();
    private static int distanceLeft = MOD_CONFIG.distanceWalkedCondition.getValue();

    public static void tick(ServerPlayer player) {
        if (player.isAlive()) {
            if(MOD_CONFIG.timeSpentCondition.isEnabled() && timeLeft > 0) {
                timeLeft -= 1;
            }
            if(MOD_CONFIG.distanceWalkedCondition.isEnabled() && distanceLeft > 0) {
                int oldValue = distanceWalkedRawValue;
                distanceWalkedRawValue = player.getStats().getValue(Stats.CUSTOM.get(Stats.WALK_ONE_CM)) +
                        player.getStats().getValue(Stats.CUSTOM.get(Stats.SPRINT_ONE_CM));

                if(distanceWalkedRawValue != 0) {
                    distanceWalkedRawValue /= 100; // Convert from CM into Metres
                }

                distanceLeft -= Math.min(0, distanceWalkedRawValue - oldValue);
            }
            if (MOD_CONFIG.directSunlightCondition.ifEnabled(ignored -> !hasDirectSunlight(player)) ||
                    MOD_CONFIG.afterCondition.ifEnabled(after -> player.level.dayTime() < after) ||
                    MOD_CONFIG.beforeCondition.ifEnabled(before -> player.level.dayTime() > before) ||
                    MOD_CONFIG.minHealthCondition.ifEnabled(minHealth -> player.getHealth() < minHealth) ||
                    MOD_CONFIG.noMonstersNearbyCondition.ifEnabled(ignored -> areMonstersNearby(player))) {
                return;
            }
            if(timeLeft <= 0 && distanceLeft <= 0) {
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
        timeLeft = MOD_CONFIG.timeSpentCondition.getValue();
    }
}
