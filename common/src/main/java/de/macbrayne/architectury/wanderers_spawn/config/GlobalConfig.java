package de.macbrayne.architectury.wanderers_spawn.config;

public class GlobalConfig extends BaseConfig {
    public GlobalConfig() {
        timeSpentCondition = new ToggledValue<>(300);
        afterCondition = new ToggledValue<>(0);
        beforeCondition = new ToggledValue<>(0);
        directSunlightCondition = new ToggledValue<>(false);
        distanceWalkedCondition = new ToggledValue<>(0);
        noMonstersNearbyCondition = new ToggledValue<>(false);
        minHealthCondition = new ToggledValue<>(0);

        xpCostAction = new ToggledValue<>(0);
    }
}
