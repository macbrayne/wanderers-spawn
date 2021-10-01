package de.macbrayne.architectury.wanderers_spawn.command;

public interface Announceable {
    String commandSyntax();

    default String translationKey() {
        return commandSyntax();
    }
}
