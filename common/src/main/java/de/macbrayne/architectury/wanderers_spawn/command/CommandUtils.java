package de.macbrayne.architectury.wanderers_spawn.command;

import net.minecraft.commands.CommandSourceStack;

public class CommandUtils {
    public static boolean isPermitted(CommandSourceStack sourceStack) {
        return sourceStack.hasPermission(0);
    }
}
