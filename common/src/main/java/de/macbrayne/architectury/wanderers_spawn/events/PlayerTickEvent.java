package de.macbrayne.architectury.wanderers_spawn.events;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.Heightmap;

public class PlayerTickEvent {
    public static final int COOLDOWN = 60;
    public static int cooldownTime = COOLDOWN;

    public static void tick(ServerPlayer player) {
        if (player.isAlive()) {
            cooldownTime--;

            if(cooldownTime == 0 &&
                    player.level.getHeight(Heightmap.Types.WORLD_SURFACE, (int) player.position().x, (int) player.position().z) >= player.position().y) {
                player.setRespawnPosition(player.level.dimension(), new BlockPos(player.position()), 0, false, true);
                cooldownTime = COOLDOWN;
            }
        }
    }
}
