package de.macbrayne.architectury.wanderers_spawn.config;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Predicate;

public class ToggledValue<T> {
    private boolean enabled = false;
    private T value;

    private ToggledValue() {

    }

    public ToggledValue(@NotNull T value) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToggledValue<?> that = (ToggledValue<?>) o;
        return enabled == that.enabled && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enabled, value);
    }

    @Override
    public String toString() {
        return "ToggledValue{" +
                "enabled=" + enabled +
                ", value=" + value +
                '}';
    }
}