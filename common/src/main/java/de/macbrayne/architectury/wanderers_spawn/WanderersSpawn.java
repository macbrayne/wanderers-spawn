package de.macbrayne.architectury.wanderers_spawn;


public class WanderersSpawn {
    public static final String MOD_ID = "wanderers_spawn";

    public static void init() {
        System.out.println(PlatformUtils.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}
