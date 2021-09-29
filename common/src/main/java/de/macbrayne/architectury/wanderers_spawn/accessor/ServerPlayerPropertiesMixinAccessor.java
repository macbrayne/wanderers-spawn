package de.macbrayne.architectury.wanderers_spawn.accessor;

import de.macbrayne.architectury.wanderers_spawn.events.PlayerTickEvent;

public interface ServerPlayerPropertiesMixinAccessor {
    PlayerTickEvent wanderersSpawn$playerTickEvent();
}