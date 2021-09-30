package de.macbrayne.architectury.wanderers_spawn.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.macbrayne.architectury.wanderers_spawn.accessor.ServerPlayerPropertiesMixinAccessor;
import de.macbrayne.architectury.wanderers_spawn.config.BaseConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;

public class CommandUtils {
    public static boolean isPermitted(CommandSourceStack sourceStack) {
        return sourceStack.hasPermission(0);
    }

    public static BaseConfig getConfigFromPlayer(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return ((ServerPlayerPropertiesMixinAccessor) EntityArgument.getPlayer(context, "player")).wanderersSpawn$playerConfig();
    }
}
