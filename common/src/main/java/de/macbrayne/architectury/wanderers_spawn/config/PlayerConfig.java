package de.macbrayne.architectury.wanderers_spawn.config;

import net.minecraft.util.Unit;

public class PlayerConfig {
    public boolean enabled;
    public Condition<Integer> timeSpentCondition = new Condition<>(300);
    public Condition<Integer> afterCondition = new Condition<>(0);
    public Condition<Integer> beforeCondition = new Condition<>(0);
    public Condition<Unit> directSunlightCondition = new Condition<>(Unit.INSTANCE);
    public Condition<Integer> distanceWalkedCondition = new Condition<>(0);
    public Condition<Unit> noMonstersNearbyCondition = new Condition<>(Unit.INSTANCE);
}
