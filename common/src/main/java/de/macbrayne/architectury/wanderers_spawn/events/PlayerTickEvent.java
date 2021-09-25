package de.macbrayne.architectury.wanderers_spawn.events;

import de.macbrayne.architectury.wanderers_spawn.Reference;
import de.macbrayne.architectury.wanderers_spawn.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.levelgen.Heightmap;

public class PlayerTickEvent {
    private static final ModConfig MOD_CONFIG = Reference.modConfig;
    private static int rawStat = 0;
    private static int cooldown = MOD_CONFIG.triggerCooldown;

    public static void tick(ServerPlayer player) {
        if (player.isAlive()) {
            cooldown = switch (MOD_CONFIG.trigger) {
                case TIME -> cooldown - 1;
                case DISTANCE -> {
                    int oldStat = rawStat;
                    rawStat = player.getStats().getValue(Stats.CUSTOM.get(Stats.WALK_ONE_CM)) +
                                player.getStats().getValue(Stats.CUSTOM.get(Stats.SPRINT_ONE_CM));
                    if(oldStat == 0) {
                        oldStat = rawStat;
                    }

                    yield cooldown - (rawStat - oldStat);
                }
            };

            if(cooldown < 0 &&
                    player.level.getHeight(Heightmap.Types.WORLD_SURFACE, (int) player.position().x, (int) player.position().z) >= player.position().y) {
                reset(player);
            }
        }
    }

    private static void reset(ServerPlayer player) {
        player.setRespawnPosition(player.level.dimension(), new BlockPos(player.position()), 0, false, true);
        cooldown = MOD_CONFIG.triggerCooldown;
    }
}
