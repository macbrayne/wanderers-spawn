package de.macbrayne.architectury.wanderers_spawn.config;

import java.util.Objects;
import java.util.function.Predicate;

public class IntegerToggledValue {
    private boolean enabled = false;
    private int value;

    private IntegerToggledValue() {

    }

    public IntegerToggledValue(int value) {
        this.value = value;
    }


    public IntegerToggledValue(boolean enabled, int value) {
        this.enabled = enabled;
        this.value = value;
    }

    public IntegerToggledValue(IntegerToggledValue clone) {
        this.enabled = clone.enabled;
        this.value = clone.value;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Integer getValue() {
        return value;
    }

    public void setAndEnable(int value) {
        enabled = true;
        this.value = value;
    }

    public boolean isDisabledOr(Predicate<Integer> action) {
        return !enabled || action.test(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegerToggledValue that = (IntegerToggledValue) o;
        return enabled == that.enabled && value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(enabled, value);
    }

    @Override
    public String toString() {
        return "IntegerToggledValue{" +
                "enabled=" + enabled +
                ", value=" + value +
                '}';
    }
}