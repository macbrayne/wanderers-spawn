package de.macbrayne.architectury.wanderers_spawn.config;

import java.util.function.Predicate;

public class ToggledValue<T> {
    private boolean enabled = false;
    private T value;

    private ToggledValue() {

    }

    public ToggledValue(T value) {
        this.value = value;
    }

    public ToggledValue(ToggledValue<T> clone) {
        this.enabled = clone.enabled;
        this.value = clone.value;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public T getValue() {
        return value;
    }

    public void setAndEnable(T value) {
        enabled = true;
        this.value = value;
    }

    public boolean isDisabledOr(Predicate<T> action) {
        return !enabled || action.test(value);
    }
}