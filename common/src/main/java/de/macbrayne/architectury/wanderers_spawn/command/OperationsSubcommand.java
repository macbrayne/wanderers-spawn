package de.macbrayne.architectury.wanderers_spawn.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import de.macbrayne.architectury.wanderers_spawn.Reference;
import de.macbrayne.architectury.wanderers_spawn.openmods.utils.EnchantmentUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;

import java.util.Locale;

public class OperationsSubcommand {
    public LiteralArgumentBuilder<CommandSourceStack> get() {
        return Commands.literal("operations")
                .requires(CommandUtils::isPermitted)
                .then(getAdd())
                .then(getQuery())
                .then(getRemove());
    }

    private LiteralArgumentBuilder<CommandSourceStack> getAdd() {
        return Commands.literal("add")
                .requires(CommandUtils::isPermitted)
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.literal(Operations.XP_COST.commandSyntax)
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                        .then(Commands.literal("points")
                                                .executes(context -> setXpCost(context,  false)))
                                        .then(Commands.literal("levels")
                                                .executes(context -> setXpCost(context,  true))))));
    }

    private int setXpCost(CommandContext<CommandSourceStack> context, boolean isAmountInLevels) {
        int argument = IntegerArgumentType.getInteger(context, "amount");
        int amount = isAmountInLevels ? EnchantmentUtils.getExperienceForLevel(argument) : argument;
        Reference.globalConfig.xpCostOperation.setAndEnable(amount);
        return announceQuery(context, "");
    }

    private LiteralArgumentBuilder<CommandSourceStack> getQuery() {
        return Commands.literal("query")
                .requires(CommandUtils::isPermitted)
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.literal(Operations.XP_COST.commandSyntax)
                                .executes(context -> announceQuery(context, "XP Cost"))));
    }

    private LiteralArgumentBuilder<CommandSourceStack> getRemove() {
        return Commands.literal("remove")
                .requires(CommandUtils::isPermitted)
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.literal(Operations.XP_COST.commandSyntax)
                                .executes(context -> announceReset(context, "XP Cost"))));
    }

    private int announceQuery(CommandContext<CommandSourceStack> context, String message) {
        context.getSource().sendSuccess(Component.nullToEmpty(message), false);
        return 1;
    }

    private int announceReset(CommandContext<CommandSourceStack> context, String message) {
        context.getSource().sendSuccess(Component.nullToEmpty(message), false);
        return 1;
    }

    public enum Operations {
        XP_COST("xpCost");

        String commandSyntax;

        Operations() {
            commandSyntax = name().toLowerCase(Locale.ROOT);
        }

        Operations(String commandSyntax) {
            this.commandSyntax = commandSyntax;
        }
    }
}
