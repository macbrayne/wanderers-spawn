package de.macbrayne.architectury.wanderers_spawn.forge;

import de.macbrayne.architectury.wanderers_spawn.WanderersSpawn;
import de.macbrayne.architectury.wanderers_spawn.command.CommandRegistry;
import net.minecraft.commands.Commands;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(WanderersSpawn.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WanderersSpawnForge {
    public WanderersSpawnForge() {
        MinecraftForge.EVENT_BUS.register(this);
        WanderersSpawn.init();
    }

    @SubscribeEvent
    public void onRegisterCommandEvent(RegisterCommandsEvent event) {
        CommandRegistry.register(event.getDispatcher(), event.getEnvironment().equals(Commands.CommandSelection.DEDICATED));
    }
}
