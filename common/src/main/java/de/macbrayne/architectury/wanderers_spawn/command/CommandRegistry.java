package de.macbrayne.architectury.wanderers_spawn.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;

public class CommandRegistry {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, boolean isDedicated) {
        LiteralCommandNode<CommandSourceStack> root = RootCommand.get().build();
        dispatcher.getRoot().addChild(root);
        dispatcher.register(RootCommand.alias(root));
    }
}
