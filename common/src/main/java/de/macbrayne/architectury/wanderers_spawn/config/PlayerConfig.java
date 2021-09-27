package de.macbrayne.architectury.wanderers_spawn.config;

public class PlayerConfig extends BaseConfig {
    public PlayerConfig(BaseConfig copyConfig) {
        this.enabled = copyConfig.enabled;
        this.timeSpentCondition = new ToggledValue<>(copyConfig.timeSpentCondition);
        afterCondition = new ToggledValue<>(copyConfig.afterCondition);
        beforeCondition = new ToggledValue<>(copyConfig.beforeCondition);
        directSunlightCondition = new ToggledValue<>(copyConfig.directSunlightCondition);
        distanceWalkedCondition = new ToggledValue<>(copyConfig.distanceWalkedCondition);
        noMonstersNearbyCondition = new ToggledValue<>(copyConfig.noMonstersNearbyCondition);
        minHealthCondition = new ToggledValue<>(copyConfig.minHealthCondition);

        xpCostAction = new ToggledValue<>(copyConfig.xpCostAction);
    }
}
