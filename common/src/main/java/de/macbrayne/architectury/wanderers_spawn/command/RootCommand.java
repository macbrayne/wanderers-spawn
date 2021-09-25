package de.macbrayne.architectury.wanderers_spawn.command;


import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import de.macbrayne.architectury.wanderers_spawn.Reference;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;

public class RootCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> get() {
        return Commands.literal("wanderersspawn")
                .requires(CommandUtils::isPermitted)
                .then(ConditionsSubcommand.get())
                .then(getEnable())
                .then(getDisable())
                .then(getStatus());
    }

    public static LiteralArgumentBuilder<CommandSourceStack> alias(LiteralCommandNode<CommandSourceStack> redirect) {
        return Commands.literal("ws")
                .requires(CommandUtils::isPermitted)
                .redirect(redirect);
    }

    private static LiteralArgumentBuilder<CommandSourceStack> getEnable() {
        return Commands.literal("enable")
                .then(Commands.literal("Global")
                        .executes(context -> {
                            Reference.modConfig.enabled = true;
                            return 1;
                        }))
                .then(Commands.argument("player", EntityArgument.player()));
    }

    private static LiteralArgumentBuilder<CommandSourceStack> getDisable() {
        return Commands.literal("disable")
                .then(Commands.literal("Global")
                        .executes(context -> {
                            Reference.modConfig.enabled = false;
                            return 1;
                        }))
                .then(Commands.argument("player", EntityArgument.player()));
    }

    private static LiteralArgumentBuilder<CommandSourceStack> getStatus() {
        return Commands.literal("status")
                .then(Commands.literal("Global")
                        .executes(context -> {
                            context.getSource().sendSuccess(Component.nullToEmpty(Reference.modConfig.enabled + ""), false);
                            return 1;
                        }))
                .then(Commands.argument("player", EntityArgument.player()));
    }
}
