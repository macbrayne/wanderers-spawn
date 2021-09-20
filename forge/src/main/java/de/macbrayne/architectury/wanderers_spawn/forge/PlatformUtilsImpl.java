package de.macbrayne.architectury.wanderers_spawn.forge;

import de.macbrayne.architectury.wanderers_spawn.PlatformUtils;
import de.macbrayne.architectury.wanderers_spawn.command.CommandRegistry;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.DeferredRegister;

import java.nio.file.Path;

public class PlatformUtilsImpl {
    /**
     * This is our actual method to {@link PlatformUtils#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
