package de.macbrayne.architectury.wanderers_spawn;

import de.macbrayne.architectury.wanderers_spawn.config.BaseConfig;
import de.macbrayne.architectury.wanderers_spawn.config.GlobalConfig;

public class Reference {
    public static BaseConfig globalConfig = GlobalConfig.load();
}
