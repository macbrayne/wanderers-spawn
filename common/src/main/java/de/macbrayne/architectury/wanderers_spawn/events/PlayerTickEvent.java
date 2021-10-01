package de.macbrayne.architectury.wanderers_spawn.events;

import de.macbrayne.architectury.wanderers_spawn.config.PlayerConfig;
import openmods.utils.EnchantmentUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class PlayerTickEvent {
    private final ServerPlayer player;
    private final PlayerConfig config;
    private boolean distanceTriggered = false;
    private boolean timeSpentTriggered = false;

    public PlayerTickEvent(ServerPlayer player, PlayerConfig config) {
        this.player = player;
        this.config = config;
    }

    public void tick() {
        if (player.isAlive()) {
            int distanceWalkedRawValue = player.getStats().getValue(Stats.CUSTOM.get(Stats.WALK_ONE_CM)) +
                    player.getStats().getValue(Stats.CUSTOM.get(Stats.SPRINT_ONE_CM));

            if (!distanceTriggered && config.timeSpentCondition.isDisabledOr(time -> player.tickCount % time == 0)) {
                distanceTriggered = true;
            }
            if (!timeSpentTriggered && config.distanceWalkedCondition.isDisabledOr(distance -> distanceWalkedRawValue % distance == 0)) {
                timeSpentTriggered = true;
            }

            if (config.directSunlightCondition.isDisabledOr(directSunlight -> directSunlight == hasDirectSunlight()) &&
                    timeSpentTriggered &&
                    distanceTriggered &&
                    config.afterCondition.isDisabledOr(after -> player.level.dayTime() > after) &&
                    config.beforeCondition.isDisabledOr(before -> player.level.dayTime() < before) &&
                    config.minHealthCondition.isDisabledOr(minHealth -> player.getHealth() > minHealth) &&
                    config.noMonstersNearbyCondition.isDisabledOr(noMonsters -> noMonsters != areMonstersNearby())) {
                setSpawnPoint();
            }
        }
    }

    private boolean hasDirectSunlight() {
        return player.position().y >= player.level.getHeight(Heightmap.Types.WORLD_SURFACE, (int) player.position().x, (int) player.position().z);
    }

    private boolean areMonstersNearby() {
        Vec3 blockCentre = Vec3.atBottomCenterOf(player.blockPosition());
        return !player.level.getEntitiesOfClass(Monster.class,
                        new AABB(blockCentre.x() - 8.0D, blockCentre.y() - 5.0D, blockCentre.z() - 8.0D,
                                blockCentre.x() + 8.0D, blockCentre.y() + 5.0D, blockCentre.z() + 8.0D),
                        (monster) -> monster.isPreventingPlayerRest(player))
                .isEmpty();
    }

    private void setSpawnPoint() {
        // Check if actions can succeed
        if (config.xpCostAction.isEnabled() && EnchantmentUtils.getPlayerXP(player) < config.xpCostAction.getValue()) {
            return;
        }

        // Trigger actions
        if(config.xpCostAction.isEnabled()) {
            player.giveExperiencePoints(-config.xpCostAction.getValue());
        }

        // Reset Trigger Flags
        distanceTriggered = false;
        timeSpentTriggered = false;

        // Set Spawn Position
        // Dimension, Position, Angle, Announce in Chat, Force even if bed obstructed
        player.setRespawnPosition(player.level.dimension(), new BlockPos(player.position()), 0, true, true);
    }
}
