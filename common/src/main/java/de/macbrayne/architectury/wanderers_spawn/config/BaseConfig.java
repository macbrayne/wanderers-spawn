package de.macbrayne.architectury.wanderers_spawn.config;

public abstract class BaseConfig {
    public boolean enabled;
    public ToggledValue<Integer> timeSpentCondition;;
    public ToggledValue<Integer> afterCondition;
    public ToggledValue<Integer> beforeCondition;
    public ToggledValue<Boolean> directSunlightCondition;
    public ToggledValue<Integer> distanceWalkedCondition;
    public ToggledValue<Boolean> noMonstersNearbyCondition;
    public ToggledValue<Integer> minHealthCondition;

    public ToggledValue<Integer> xpCostAction;
}
