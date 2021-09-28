package de.macbrayne.architectury.wanderers_spawn.config;

import net.minecraft.nbt.CompoundTag;

import java.util.function.Predicate;

public class BooleanToggledValue {
    private boolean enabled = false;
    private boolean value;

    private BooleanToggledValue() {

    }

    public BooleanToggledValue(boolean value) {
        this.value = value;
    }


    public BooleanToggledValue(BooleanToggledValue clone) {
        this.enabled = clone.enabled;
        this.value = clone.value;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean getValue() {
        return value;
    }

    public void setAndEnable(boolean value) {
        enabled = true;
        this.value = value;
    }

    public CompoundTag toNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("enabled", isEnabled());
        tag.putBoolean("value", getValue());
        return tag;
    }

    public void fromNbt(CompoundTag tag) {
        enabled = tag.getBoolean("enabled");
        value = tag.getBoolean("value");
    }

    public boolean isDisabledOr(Predicate<Boolean> action) {
        return !enabled || action.test(value);
    }
}
