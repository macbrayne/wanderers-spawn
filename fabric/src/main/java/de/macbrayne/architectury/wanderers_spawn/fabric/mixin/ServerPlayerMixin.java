package de.macbrayne.architectury.wanderers_spawn.fabric.mixin;

import de.macbrayne.architectury.wanderers_spawn.ServerPlayerMixinPropertiesAccessor;
import de.macbrayne.architectury.wanderers_spawn.events.PlayerTickEvent;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerPlayer.class)
public class ServerPlayerMixin {
    @Inject(at = @At(value = "HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {
        ((ServerPlayerMixinPropertiesAccessor)this).wanderersSpawn$playerTickEvent().tick();
    }
}
