package de.macbrayne.architectury.wanderers_spawn.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.TimeArgument;
import net.minecraft.network.chat.Component;

import java.util.Locale;

public class ConditionsSubcommand {
    public LiteralArgumentBuilder<CommandSourceStack> get() {
        return Commands.literal("conditions")
                .requires(CommandUtils::isPermitted)
                .then(getSet())
                .then(getQuery())
                .then(getRemove());
    }

    private LiteralArgumentBuilder<CommandSourceStack> getSet() {
        return Commands.literal("set")
                .requires(CommandUtils::isPermitted)
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.literal(Conditions.TIME_SPENT.commandSyntax)
                                .then(Commands.argument("cooldown", TimeArgument.time())
                                        .executes(context -> {
                                            int cooldown = IntegerArgumentType.getInteger(context, "cooldown");
                                            CommandUtils.getConfigFromPlayer(context).timeSpentCondition.setAndEnable(cooldown);
                                            return announceSet(context, Conditions.TIME_SPENT, cooldown);
                                        })))
                        .then(Commands.literal(Conditions.AFTER.commandSyntax)
                                .then(Commands.literal("day")
                                        .executes(context -> setAfter(context, 0)))
                                .then(Commands.literal("noon")
                                        .executes(context -> setAfter(context, 6000)))
                                .then(Commands.literal("night")
                                        .executes(context -> setAfter(context, 12000)))
                                .then(Commands.literal("midnight")
                                        .executes(context -> setAfter(context, 18000)))
                                .then(Commands.argument("time", TimeArgument.time())
                                        .executes(context -> setAfter(context, IntegerArgumentType.getInteger(context, "time")))))
                        .then(Commands.literal(Conditions.BEFORE.commandSyntax)
                                .then(Commands.literal("day")
                                        .executes(context -> setBefore(context, 0)))
                                .then(Commands.literal("noon")
                                        .executes(context -> setBefore(context, 6000)))
                                .then(Commands.literal("night")
                                        .executes(context -> setBefore(context, 12000)))
                                .then(Commands.literal("midnight")
                                        .executes(context -> setBefore(context, 18000)))
                                .then(Commands.argument("time", TimeArgument.time())
                                        .executes(context -> setBefore(context, IntegerArgumentType.getInteger(context, "time")))))
                        .then(Commands.literal(Conditions.DIRECT_SKYLIGHT.commandSyntax)
                                .then(Commands.argument("required", BoolArgumentType.bool())
                                        .executes(context -> {
                                            boolean directSunlightCondition = BoolArgumentType.getBool(context, "required");
                                            CommandUtils.getConfigFromPlayer(context).directSunlightCondition.setAndEnable(directSunlightCondition);
                                            return announceSet(context, Conditions.DIRECT_SKYLIGHT, directSunlightCondition);
                                        })))
                        .then(Commands.literal(Conditions.DISTANCE_WALKED.commandSyntax)
                                .then(Commands.argument("walked", IntegerArgumentType.integer(0))
                                        .executes(context -> {
                                            int distanceWalked = IntegerArgumentType.getInteger(context, "walked");
                                            CommandUtils.getConfigFromPlayer(context).distanceWalkedCondition.setAndEnable(distanceWalked);
                                            return announceSet(context, Conditions.DISTANCE_WALKED, distanceWalked);
                                        })))
                        .then(Commands.literal(Conditions.NO_MONSTERS_NEARBY.commandSyntax)
                                .then(Commands.argument("required", BoolArgumentType.bool())
                                        .executes(context -> {
                                            boolean noMonstersNearbyCondition = BoolArgumentType.getBool(context, "required");
                                            CommandUtils.getConfigFromPlayer(context).noMonstersNearbyCondition.setAndEnable(noMonstersNearbyCondition);
                                            return announceSet(context, Conditions.NO_MONSTERS_NEARBY, noMonstersNearbyCondition);
                                        })))
                        .then(Commands.literal(Conditions.MIN_HEALTH.commandSyntax)
                                .then(Commands.argument("health", IntegerArgumentType.integer(0, 20))
                                        .executes(context -> {
                                            int minHealth = IntegerArgumentType.getInteger(context, "health");
                                            CommandUtils.getConfigFromPlayer(context).minHealthCondition.setAndEnable(minHealth);
                                            return announceSet(context, Conditions.MIN_HEALTH, minHealth);
                                        }))));
    }

    private int setBefore(CommandContext<CommandSourceStack> context, int ticks) throws CommandSyntaxException {
        CommandUtils.getConfigFromPlayer(context).beforeCondition.setAndEnable(ticks);
        return announceSet(context, Conditions.BEFORE, ticks);
    }

    private int setAfter(CommandContext<CommandSourceStack> context, int ticks) throws CommandSyntaxException {
        CommandUtils.getConfigFromPlayer(context).afterCondition.setAndEnable(ticks);
        return announceSet(context, Conditions.AFTER, ticks);
    }

    private LiteralArgumentBuilder<CommandSourceStack> getQuery() {
        return Commands.literal("query")
                .requires(CommandUtils::isPermitted)
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.literal(Conditions.TIME_SPENT.commandSyntax)
                                .executes(context -> announceQuery(context, Conditions.TIME_SPENT, CommandUtils.getConfigFromPlayer(context).timeSpentCondition.getValue())))
                        .then(Commands.literal(Conditions.AFTER.commandSyntax)
                                .executes(context -> announceQuery(context, Conditions.AFTER, CommandUtils.getConfigFromPlayer(context).afterCondition.getValue())))
                        .then(Commands.literal(Conditions.BEFORE.commandSyntax)
                                .executes(context -> announceQuery(context, Conditions.BEFORE, CommandUtils.getConfigFromPlayer(context).beforeCondition.getValue())))
                        .then(Commands.literal(Conditions.DIRECT_SKYLIGHT.commandSyntax)
                                .executes(context -> announceQuery(context, Conditions.DIRECT_SKYLIGHT, CommandUtils.getConfigFromPlayer(context).directSunlightCondition.getValue())))
                        .then(Commands.literal(Conditions.DISTANCE_WALKED.commandSyntax)
                                .executes(context -> announceQuery(context, Conditions.DISTANCE_WALKED, CommandUtils.getConfigFromPlayer(context).distanceWalkedCondition.getValue())))
                        .then(Commands.literal(Conditions.NO_MONSTERS_NEARBY.commandSyntax)
                                .executes(context -> announceQuery(context, Conditions.NO_MONSTERS_NEARBY, CommandUtils.getConfigFromPlayer(context).noMonstersNearbyCondition.getValue())))
                        .then(Commands.literal(Conditions.MIN_HEALTH.commandSyntax)
                                .executes(context -> announceQuery(context, Conditions.MIN_HEALTH, CommandUtils.getConfigFromPlayer(context).minHealthCondition.getValue()))));
    }

    private LiteralArgumentBuilder<CommandSourceStack> getRemove() {
        return Commands.literal("remove")
                .requires(CommandUtils::isPermitted)
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.literal(Conditions.TIME_SPENT.commandSyntax)
                                .executes(context -> announceReset(context, Conditions.TIME_SPENT)))
                        .then(Commands.literal(Conditions.AFTER.commandSyntax)
                                .executes(context -> announceReset(context, Conditions.AFTER)))
                        .then(Commands.literal(Conditions.BEFORE.commandSyntax)
                                .executes(context -> announceReset(context, Conditions.BEFORE)))
                        .then(Commands.literal(Conditions.DIRECT_SKYLIGHT.commandSyntax)
                                .executes(context -> announceReset(context, Conditions.DIRECT_SKYLIGHT)))
                        .then(Commands.literal(Conditions.DISTANCE_WALKED.commandSyntax)
                                .executes(context -> announceReset(context, Conditions.DISTANCE_WALKED)))
                        .then(Commands.literal(Conditions.NO_MONSTERS_NEARBY.commandSyntax)
                                .executes(context -> announceReset(context, Conditions.NO_MONSTERS_NEARBY)))
                        .then(Commands.literal(Conditions.MIN_HEALTH.commandSyntax)
                                .executes(context -> announceReset(context, Conditions.MIN_HEALTH))));
    }

    private <T> int announceSet(CommandContext<CommandSourceStack> context, Conditions conditions, T value) {
        context.getSource().sendSuccess(Component.nullToEmpty("Set " + conditions.commandSyntax + " to " + value), false);
        return 1;
    }

    private <T> int announceQuery(CommandContext<CommandSourceStack> context, Conditions conditions, T value) {
        context.getSource().sendSuccess(Component.nullToEmpty(conditions.commandSyntax + " is " + value), false);
        return 1;
    }

    private int announceReset(CommandContext<CommandSourceStack> context, Conditions conditions) {
        context.getSource().sendSuccess(Component.nullToEmpty("Reset " + conditions.commandSyntax), false);
        return 1;
    }

    public enum Conditions {
        TIME_SPENT("timeSpent"), AFTER, BEFORE,
        DIRECT_SKYLIGHT("directSkylight"), DISTANCE_WALKED("distanceWalked"),
        NO_MONSTERS_NEARBY("noMonstersNearby"), MIN_HEALTH("minHealth");

        String commandSyntax;

        Conditions() {
            commandSyntax = name().toLowerCase(Locale.ROOT);
        }

        Conditions(String commandSyntax) {
            this.commandSyntax = commandSyntax;
        }
    }
}
