package de.macbrayne.architectury.wanderers_spawn.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class CommandRegistry {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, boolean isDedicated) {
        dispatcher.register(Commands.literal("test").executes((test) -> {
                test.getSource().sendSuccess(Component.nullToEmpty("Testetsetests"), false);
                return 1;
        }));
    }
}
