package de.macbrayne.architectury.wanderers_spawn.config;

public class ModConfig {
    public Trigger trigger = Trigger.DISTANCE;
    public int triggerCooldown = 600;

    public enum Trigger {
        TIME, DISTANCE;
    }
}
