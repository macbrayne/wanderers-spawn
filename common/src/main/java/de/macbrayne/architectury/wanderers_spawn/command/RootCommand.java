package de.macbrayne.architectury.wanderers_spawn.command;


import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import de.macbrayne.architectury.wanderers_spawn.Reference;
import de.macbrayne.architectury.wanderers_spawn.config.GlobalConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;

public class RootCommand {
    public LiteralArgumentBuilder<CommandSourceStack> get() {
        return Commands.literal("wanderersspawn")
                .requires(CommandUtils::isPermitted)
                .then(new ConditionsSubcommand().get())
                .then(new ActionsSubcommand().get())
                .then(getEnable())
                .then(getDisable())
                .then(getStatus())
                .then(getReload());
    }

    public LiteralArgumentBuilder<CommandSourceStack> alias(LiteralCommandNode<CommandSourceStack> redirect) {
        return Commands.literal("ws")
                .requires(CommandUtils::isPermitted)
                .redirect(redirect);
    }

    private LiteralArgumentBuilder<CommandSourceStack> getEnable() {
        return Commands.literal("enable")
                .then(Commands.literal("Global")
                        .executes(context -> {
                            Reference.globalConfig.enabled = true;
                            return 1;
                        }))
                .then(Commands.argument("player", EntityArgument.player()));
    }

    private static LiteralArgumentBuilder<CommandSourceStack> getDisable() {
        return Commands.literal("disable")
                .then(Commands.literal("Global")
                        .executes(context -> {
                            Reference.globalConfig.enabled = false;
                            return 1;
                        }))
                .then(Commands.argument("player", EntityArgument.player()));
    }

    private LiteralArgumentBuilder<CommandSourceStack> getStatus() {
        return Commands.literal("status")
                .then(Commands.literal("Global")
                        .executes(context -> {
                            context.getSource().sendSuccess(Component.nullToEmpty(Reference.globalConfig.enabled + ""), false);
                            return 1;
                        }))
                .then(Commands.argument("player", EntityArgument.player()));
    }

    private LiteralArgumentBuilder<CommandSourceStack> getReload() {
        return Commands.literal("reload")
                .executes(context -> {
                    Reference.globalConfig = GlobalConfig.load();
                    return 1;
                });
    }
}
