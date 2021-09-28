package de.macbrayne.architectury.wanderers_spawn.config;

import de.macbrayne.architectury.wanderers_spawn.config.toggled.BooleanToggledValue;
import de.macbrayne.architectury.wanderers_spawn.config.toggled.IntegerToggledValue;

import java.util.Objects;

public abstract class BaseConfig {
    public boolean enabled;
    public IntegerToggledValue timeSpentCondition;;
    public IntegerToggledValue afterCondition;
    public IntegerToggledValue beforeCondition;
    public BooleanToggledValue directSunlightCondition;
    public IntegerToggledValue distanceWalkedCondition;
    public BooleanToggledValue noMonstersNearbyCondition;
    public IntegerToggledValue minHealthCondition;

    public IntegerToggledValue xpCostAction;

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
