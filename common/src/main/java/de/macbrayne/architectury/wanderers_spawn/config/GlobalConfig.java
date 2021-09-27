package de.macbrayne.architectury.wanderers_spawn.config;

import de.macbrayne.architectury.wanderers_spawn.PlatformUtils;

import java.io.IOException;

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


    public static GlobalConfig load() {
        try {
            return GsonHelper.load(PlatformUtils.getConfigDirectory().resolve("wanderers-spawn.json"), GlobalConfig.class);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            save(new GlobalConfig());
            return new GlobalConfig();
        }
    }

    public static void save(GlobalConfig config) {
        GsonHelper.write(PlatformUtils.getConfigDirectory().resolve("wanderers-spawn.json"), config);
    }
}
