package de.macbrayne.architectury.wanderers_spawn.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;

public class CommandRegistry {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, boolean isDedicated) {
        RootCommand rootCommand = new RootCommand();
        LiteralCommandNode<CommandSourceStack> root = rootCommand.get().build();
        dispatcher.getRoot().addChild(root);
        dispatcher.register(rootCommand.alias(root));
    }
}
