package de.macbrayne.architectury.wanderers_spawn.mixin;

import de.macbrayne.architectury.wanderers_spawn.Reference;
import de.macbrayne.architectury.wanderers_spawn.ServerPlayerMixinPropertiesAccessor;
import de.macbrayne.architectury.wanderers_spawn.config.PlayerConfig;
import de.macbrayne.architectury.wanderers_spawn.events.PlayerTickEvent;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixinProperties implements ServerPlayerMixinPropertiesAccessor {
    @Unique
    public PlayerConfig wanderersSpawn$playerConfig = new PlayerConfig(Reference.globalConfig);

    @Unique
    private PlayerTickEvent wanderersSpawn$playerTickEvent = new PlayerTickEvent((ServerPlayer) (Object) this, wanderersSpawn$playerConfig);

    @Override
    public PlayerTickEvent wanderersSpawn$playerTickEvent() {
        return wanderersSpawn$playerTickEvent;
    }
}