package de.macbrayne.architectury.wanderers_spawn.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import openmods.utils.EnchantmentUtils;

import java.util.Locale;

public class ActionsSubcommand {
    public LiteralArgumentBuilder<CommandSourceStack> get() {
        return Commands.literal("actions")
                .requires(CommandUtils::isPermitted)
                .then(getSet())
                .then(getQuery())
                .then(getRemove());
    }

    private LiteralArgumentBuilder<CommandSourceStack> getSet() {
        return Commands.literal("set")
                .requires(CommandUtils::isPermitted)
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.literal(Actions.XP_COST.commandSyntax())
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                        .then(Commands.literal("points")
                                                .executes(context -> setXpCost(context,  false)))
                                        .then(Commands.literal("levels")
                                                .executes(context -> setXpCost(context,  true))))));
    }

    private int setXpCost(CommandContext<CommandSourceStack> context, boolean isAmountInLevels) throws CommandSyntaxException {
        int argument = IntegerArgumentType.getInteger(context, "amount");
        int amount = isAmountInLevels ? EnchantmentUtils.getExperienceForLevel(argument) : argument;
        CommandUtils.getConfigFromPlayer(context).xpCostAction.setAndEnable(amount);
        return CommandUtils.announceQuery(context, Actions.XP_COST, argument);
    }

    private LiteralArgumentBuilder<CommandSourceStack> getQuery() {
        return Commands.literal("query")
                .requires(CommandUtils::isPermitted)
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.literal(Actions.XP_COST.commandSyntax())
                                .executes(context -> CommandUtils.announceQuery(context, Actions.XP_COST, CommandUtils.getConfigFromPlayer(context).xpCostAction.getValue()))));
    }

    private LiteralArgumentBuilder<CommandSourceStack> getRemove() {
        return Commands.literal("remove")
                .requires(CommandUtils::isPermitted)
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.literal(Actions.XP_COST.commandSyntax())
                                .executes(context -> CommandUtils.announceReset(context, Actions.XP_COST))));
    }

    public enum Actions implements Announceable {
        XP_COST("xpCost");

        String commandSyntax;

        Actions() {
            commandSyntax = name().toLowerCase(Locale.ROOT);
        }

        Actions(String commandSyntax) {
            this.commandSyntax = commandSyntax;
        }

        @Override
        public String commandSyntax() {
            return commandSyntax;
        }
    }
}
