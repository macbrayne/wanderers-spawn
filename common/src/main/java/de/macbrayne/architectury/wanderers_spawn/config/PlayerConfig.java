package de.macbrayne.architectury.wanderers_spawn.config;

public class PlayerConfig {
    public boolean enabled;
    public ToggledValue<Integer> timeSpentCondition = new ToggledValue<>(300);
    public ToggledValue<Integer> afterCondition = new ToggledValue<>(0);
    public ToggledValue<Integer> beforeCondition = new ToggledValue<>(0);
    public ToggledValue<Boolean> directSunlightCondition = new ToggledValue<>(false);
    public ToggledValue<Integer> distanceWalkedCondition = new ToggledValue<>(0);
    public ToggledValue<Boolean> noMonstersNearbyCondition = new ToggledValue<>(false);
    public ToggledValue<Integer> minHealthCondition = new ToggledValue<>(0);

    public ToggledValue<Integer> xpCostOperation = new ToggledValue<>(0);
}
