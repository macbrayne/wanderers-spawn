package de.macbrayne.architectury.wanderers_spawn.mixin;

import de.macbrayne.architectury.wanderers_spawn.Reference;
import de.macbrayne.architectury.wanderers_spawn.accessor.ServerPlayerMixinPropertiesAccessor;
import de.macbrayne.architectury.wanderers_spawn.config.PlayerConfig;
import de.macbrayne.architectury.wanderers_spawn.events.PlayerTickEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

    @Inject(at = @At("RETURN"), method = "readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V")
    public void readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        wanderersSpawn$playerConfig = new PlayerConfig(Reference.globalConfig);
        wanderersSpawn$playerTickEvent = new PlayerTickEvent((ServerPlayer) (Object) this, wanderersSpawn$playerConfig);

        if(compoundTag.contains("wsConfig")) {
            CompoundTag configCompound = compoundTag.getCompound("wsConfig");
            wanderersSpawn$playerConfig.fromNbt(configCompound);
        }
    }

    @Inject(at = @At("RETURN"), method = "addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V")
    public void addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        compoundTag.put("wsConfig", wanderersSpawn$playerConfig.toNbt());
    }
}