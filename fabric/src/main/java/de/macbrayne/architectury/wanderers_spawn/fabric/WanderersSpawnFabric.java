package de.macbrayne.architectury.wanderers_spawn.fabric;

import de.macbrayne.architectury.wanderers_spawn.WanderersSpawn;
import de.macbrayne.architectury.wanderers_spawn.command.CommandRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class WanderersSpawnFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        WanderersSpawn.init();
        CommandRegistrationCallback.EVENT.register(CommandRegistry::register);
    }
}
