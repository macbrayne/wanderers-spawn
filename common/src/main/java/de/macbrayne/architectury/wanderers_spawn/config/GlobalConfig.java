package de.macbrayne.architectury.wanderers_spawn.config;

import de.macbrayne.architectury.wanderers_spawn.PlatformUtils;

import java.io.IOException;

public class GlobalConfig extends BaseConfig {
    public GlobalConfig() {
        timeSpentCondition = new IntegerToggledValue(300);
        afterCondition = new IntegerToggledValue(0);
        beforeCondition = new IntegerToggledValue(0);
        directSunlightCondition = new BooleanToggledValue(false);
        distanceWalkedCondition = new IntegerToggledValue(0);
        noMonstersNearbyCondition = new BooleanToggledValue(false);
        minHealthCondition = new IntegerToggledValue(0);

        xpCostAction = new IntegerToggledValue(0);
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
