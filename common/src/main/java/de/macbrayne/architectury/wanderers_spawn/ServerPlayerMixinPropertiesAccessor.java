package de.macbrayne.architectury.wanderers_spawn;

import de.macbrayne.architectury.wanderers_spawn.events.PlayerTickEvent;

public interface ServerPlayerMixinPropertiesAccessor {
    PlayerTickEvent wanderersSpawn$playerTickEvent();
}