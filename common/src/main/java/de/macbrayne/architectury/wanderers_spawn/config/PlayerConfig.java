package de.macbrayne.architectury.wanderers_spawn.config;

import de.macbrayne.architectury.wanderers_spawn.Reference;
import net.minecraft.nbt.CompoundTag;

public class PlayerConfig extends BaseConfig {
    public PlayerConfig(BaseConfig copyConfig) {
        this.enabled = copyConfig.enabled;
        timeSpentCondition = new IntegerToggledValue(copyConfig.timeSpentCondition);
        afterCondition = new IntegerToggledValue(copyConfig.afterCondition);
        beforeCondition = new IntegerToggledValue(copyConfig.beforeCondition);
        directSunlightCondition = new BooleanToggledValue(copyConfig.directSunlightCondition);
        distanceWalkedCondition = new IntegerToggledValue(copyConfig.distanceWalkedCondition);
        noMonstersNearbyCondition = new BooleanToggledValue(copyConfig.noMonstersNearbyCondition);
        minHealthCondition = new IntegerToggledValue(copyConfig.minHealthCondition);

        xpCostAction = new IntegerToggledValue(copyConfig.xpCostAction);
    }

    private static IntegerToggledValue parseIntValue(CompoundTag tag) {
        return new IntegerToggledValue(tag.getBoolean("enabled"), tag.getInt("value"));
    }

    private static BooleanToggledValue parseBooleanValue(CompoundTag tag) {
        return new BooleanToggledValue(tag.getBoolean("enabled"), tag.getBoolean("value"));
    }

    private static CompoundTag getIntegerValue(IntegerToggledValue value) {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("enabled", value.isEnabled());
        tag.putInt("value", value.getValue());
        return tag;
    }

    private static CompoundTag getBooleanValue(BooleanToggledValue value) {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("enabled", value.isEnabled());
        tag.putBoolean("value", value.getValue());
        return tag;
    }

    public void fromNbt(CompoundTag configCompound) {
        if (configCompound.contains("enabled")) {
            enabled = configCompound.getBoolean("enabled");
        }
        if (configCompound.contains("timeSpentCondition")) {
            timeSpentCondition = PlayerConfig.parseIntValue(configCompound.getCompound("timeSpentCondition"));
        }
        if (configCompound.contains("afterCondition")) {
            afterCondition = PlayerConfig.parseIntValue(configCompound.getCompound("afterCondition"));
        }
        if (configCompound.contains("beforeCondition")) {
            beforeCondition = PlayerConfig.parseIntValue(configCompound.getCompound("beforeCondition"));
        }
        if (configCompound.contains("directSunlightCondition")) {
            directSunlightCondition = PlayerConfig.parseBooleanValue(configCompound.getCompound("directSunlightCondition"));
        }
        if (configCompound.contains("distanceWalkedCondition")) {
            distanceWalkedCondition = PlayerConfig.parseIntValue(configCompound.getCompound("distanceWalkedCondition"));
        }
        if (configCompound.contains("noMonstersNearbyCondition")) {
            noMonstersNearbyCondition = PlayerConfig.parseBooleanValue(configCompound.getCompound("noMonstersNearbyCondition"));
        }
        if (configCompound.contains("minHealthCondition")) {
            minHealthCondition = PlayerConfig.parseIntValue(configCompound.getCompound("minHealthCondition"));
        }

        if (configCompound.contains("xpCostAction")) {
            xpCostAction = PlayerConfig.parseIntValue(configCompound.getCompound("xpCostAction"));
        }
    }

    public CompoundTag toNbt() {
        CompoundTag root = new CompoundTag();
        GlobalConfig config = Reference.globalConfig;
        if (enabled != config.enabled) {
            root.putBoolean("enabled", enabled);
        }
        if (!timeSpentCondition.equals(config.timeSpentCondition)) {
            root.put("timeSpentCondition", getIntegerValue(timeSpentCondition));
        }
        if (!afterCondition.equals(config.afterCondition)) {
            root.put("afterCondition", getIntegerValue(afterCondition));
        }
        if (!beforeCondition.equals(config.beforeCondition)) {
            root.put("beforeCondition", getIntegerValue(beforeCondition));
        }
        if (!directSunlightCondition.equals(config.directSunlightCondition)) {
            root.put("directSunlightCondition", getBooleanValue(directSunlightCondition));
        }
        if (!distanceWalkedCondition.equals(config.distanceWalkedCondition)) {
            root.put("distanceWalkedCondition", getIntegerValue(distanceWalkedCondition));
        }
        if (!noMonstersNearbyCondition.equals(config.noMonstersNearbyCondition)) {
            root.put("noMonstersNearbyCondition", getBooleanValue(noMonstersNearbyCondition));
        }
        if (!minHealthCondition.equals(config.minHealthCondition)) {
            root.put("minHealthCondition", getIntegerValue(minHealthCondition));
        }

        if (!xpCostAction.equals(config.xpCostAction)) {
            root.put("xpCostAction", getIntegerValue(xpCostAction));
        }
        return root;
    }
}
