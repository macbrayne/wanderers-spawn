package de.macbrayne.architectury.wanderers_spawn.config;

import net.minecraft.util.Unit;

public class PlayerConfig {
    public boolean enabled;
    public ToggledValue<Integer> timeSpentCondition = new ToggledValue<>(300);
    public ToggledValue<Integer> afterCondition = new ToggledValue<>(0);
    public ToggledValue<Integer> beforeCondition = new ToggledValue<>(0);
    public ToggledValue<Unit> directSunlightCondition = new ToggledValue<>(Unit.INSTANCE);
    public ToggledValue<Integer> distanceWalkedCondition = new ToggledValue<>(0);
    public ToggledValue<Unit> noMonstersNearbyCondition = new ToggledValue<>(Unit.INSTANCE);
}
