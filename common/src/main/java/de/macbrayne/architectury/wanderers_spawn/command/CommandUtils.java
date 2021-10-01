package de.macbrayne.architectury.wanderers_spawn.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.macbrayne.architectury.wanderers_spawn.accessor.ServerPlayerPropertiesMixinAccessor;
import de.macbrayne.architectury.wanderers_spawn.config.BaseConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;

public class CommandUtils {
    public static boolean isPermitted(CommandSourceStack sourceStack) {
        return sourceStack.hasPermission(0);
    }

    public static BaseConfig getConfigFromPlayer(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return ((ServerPlayerPropertiesMixinAccessor) EntityArgument.getPlayer(context, "player")).wanderersSpawn$playerConfig();
    }


    public static  <T> int announceSet(CommandContext<CommandSourceStack> context, Announceable conditions, T value) {
        context.getSource().sendSuccess(Component.nullToEmpty("Set " + conditions.translationKey() + " to " + value), false);
        return 1;
    }

    public static  <T> int announceQuery(CommandContext<CommandSourceStack> context, Announceable conditions, T value) {
        context.getSource().sendSuccess(Component.nullToEmpty(conditions.translationKey() + " is " + value), false);
        return 1;
    }

    public static int announceReset(CommandContext<CommandSourceStack> context, Announceable conditions) {
        context.getSource().sendSuccess(Component.nullToEmpty("Reset " + conditions.translationKey()), false);
        return 1;
    }
}
