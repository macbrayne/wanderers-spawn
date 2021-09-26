package de.macbrayne.architectury.wanderers_spawn.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import de.macbrayne.architectury.wanderers_spawn.Reference;
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
                .then(getAdd())
                .then(getQuery())
                .then(getRemove());
    }

    private LiteralArgumentBuilder<CommandSourceStack> getAdd() {
        return Commands.literal("add")
                .requires(CommandUtils::isPermitted)
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.literal(Conditions.TIME_SPENT.commandSyntax)
                                .then(Commands.argument("cooldown", TimeArgument.time())
                                        .executes(context -> {
                                            Reference.globalConfig.timeSpentCondition.setAndEnable(IntegerArgumentType.getInteger(context, "cooldown"));
                                            return announceQuery(context, "Time Spent");
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
                                            Reference.globalConfig.directSunlightCondition.setAndEnable(BoolArgumentType.getBool(context, "required"));
                                            return announceQuery(context, "Required");
                                        })))
                        .then(Commands.literal(Conditions.DISTANCE_WALKED.commandSyntax)
                                .then(Commands.argument("walked", IntegerArgumentType.integer(0))
                                        .executes(context -> {
                                            Reference.globalConfig.distanceWalkedCondition.setAndEnable(IntegerArgumentType.getInteger(context, "walked"));
                                            return announceQuery(context, "Walked");
                                        })))
                        .then(Commands.literal(Conditions.NO_MONSTERS_NEARBY.commandSyntax)
                                .then(Commands.argument("required", BoolArgumentType.bool())
                                        .executes(context -> {
                                            Reference.globalConfig.noMonstersNearbyCondition.setAndEnable(BoolArgumentType.getBool(context, "required"));
                                            return announceQuery(context, "Required");
                                        })))
                        .then(Commands.literal(Conditions.MIN_HEALTH.commandSyntax)
                                .then(Commands.argument("health", IntegerArgumentType.integer(0, 20))
                                        .executes(context -> {
                                            Reference.globalConfig.minHealthCondition.setAndEnable(IntegerArgumentType.getInteger(context, "health"));
                                            return announceQuery(context, "Health");
                                        }))));
    }

    private int setBefore(CommandContext<CommandSourceStack> context, int ticks) {
        Reference.globalConfig.beforeCondition.setAndEnable(ticks);
        return announceQuery(context, "Set Before to " + ticks);
    }

    private int setAfter(CommandContext<CommandSourceStack> context, int ticks) {
        Reference.globalConfig.afterCondition.setAndEnable(ticks);
        return announceQuery(context, "Set After to " + ticks);
    }

    private LiteralArgumentBuilder<CommandSourceStack> getQuery() {
        return Commands.literal("query")
                .requires(CommandUtils::isPermitted)
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.literal(Conditions.TIME_SPENT.commandSyntax)
                                .executes(context -> announceQuery(context, "Time Spent")))
                        .then(Commands.literal(Conditions.AFTER.commandSyntax)
                                .executes(context -> announceQuery(context, "After")))
                        .then(Commands.literal(Conditions.BEFORE.commandSyntax)
                                .executes(context -> announceQuery(context, "Before")))
                        .then(Commands.literal(Conditions.DIRECT_SKYLIGHT.commandSyntax)
                                .executes(context -> announceQuery(context, "Direct Sunlight")))
                        .then(Commands.literal(Conditions.DISTANCE_WALKED.commandSyntax)
                                .executes(context -> announceQuery(context, "Distance Walked")))
                        .then(Commands.literal(Conditions.NO_MONSTERS_NEARBY.commandSyntax)
                                .executes(context -> announceQuery(context, "No Monsters Nearby")))
                        .then(Commands.literal(Conditions.MIN_HEALTH.commandSyntax)
                                .executes(context -> announceQuery(context, "Min Health")))
                        .then(Commands.literal(Conditions.XP_COST.commandSyntax)
                                .executes(context -> announceQuery(context, "XP Cost"))));
    }

    private LiteralArgumentBuilder<CommandSourceStack> getRemove() {
        return Commands.literal("remove")
                .requires(CommandUtils::isPermitted)
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.literal(Conditions.TIME_SPENT.commandSyntax)
                                .executes(context -> announceReset(context, "Time Spent")))
                        .then(Commands.literal(Conditions.AFTER.commandSyntax)
                                .executes(context -> announceReset(context, "After")))
                        .then(Commands.literal(Conditions.BEFORE.commandSyntax)
                                .executes(context -> announceReset(context, "Before")))
                        .then(Commands.literal(Conditions.DIRECT_SKYLIGHT.commandSyntax)
                                .executes(context -> announceReset(context, "Direct Sunlight")))
                        .then(Commands.literal(Conditions.DISTANCE_WALKED.commandSyntax)
                                .executes(context -> announceReset(context, "Distance Walked")))
                        .then(Commands.literal(Conditions.NO_MONSTERS_NEARBY.commandSyntax)
                                .executes(context -> announceReset(context, "No Monsters Nearby")))
                        .then(Commands.literal(Conditions.MIN_HEALTH.commandSyntax)
                                .executes(context -> announceReset(context, "Min Health")))
                        .then(Commands.literal(Conditions.XP_COST.commandSyntax)
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

    public enum Conditions {
        TIME_SPENT("timeSpent"), AFTER, BEFORE,
        DIRECT_SKYLIGHT("directSkylight"), DISTANCE_WALKED("distanceWalked"),
        NO_MONSTERS_NEARBY("noMonstersNearby"), XP_COST("xpCost"),
        MIN_HEALTH("minHealth");

        String commandSyntax;

        Conditions() {
            commandSyntax = name().toLowerCase(Locale.ROOT);
        }

        Conditions(String commandSyntax) {
            this.commandSyntax = commandSyntax;
        }
    }
}
