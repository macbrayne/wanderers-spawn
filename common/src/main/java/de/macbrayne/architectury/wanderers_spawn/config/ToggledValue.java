package de.macbrayne.architectury.wanderers_spawn.config;

import java.util.function.Predicate;

public class ToggledValue<T> {
    private boolean enabled = true;
    private T value;

    private ToggledValue() {

    }

    public ToggledValue(T value) {
        this.value = value;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean ifEnabled(Predicate<T> action) {
        return enabled && action.test(value);
    }
}