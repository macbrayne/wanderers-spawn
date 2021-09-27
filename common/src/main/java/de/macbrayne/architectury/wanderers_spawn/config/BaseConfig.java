package de.macbrayne.architectury.wanderers_spawn.config;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseConfig that)) return false;
        return enabled == that.enabled && Objects.equals(timeSpentCondition, that.timeSpentCondition) && Objects.equals(afterCondition, that.afterCondition) && Objects.equals(beforeCondition, that.beforeCondition) && Objects.equals(directSunlightCondition, that.directSunlightCondition) && Objects.equals(distanceWalkedCondition, that.distanceWalkedCondition) && Objects.equals(noMonstersNearbyCondition, that.noMonstersNearbyCondition) && Objects.equals(minHealthCondition, that.minHealthCondition) && Objects.equals(xpCostAction, that.xpCostAction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enabled, timeSpentCondition, afterCondition, beforeCondition, directSunlightCondition, distanceWalkedCondition, noMonstersNearbyCondition, minHealthCondition, xpCostAction);
    }
}
