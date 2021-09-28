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

    public void fromNbt(CompoundTag configCompound) {
        if (configCompound.contains("enabled")) {
            enabled = configCompound.getBoolean("enabled");
        }
        if (configCompound.contains("timeSpentCondition")) {
            timeSpentCondition.fromNbt(configCompound.getCompound("timeSpentCondition"));
        }
        if (configCompound.contains("afterCondition")) {
            afterCondition.fromNbt(configCompound.getCompound("afterCondition"));
        }
        if (configCompound.contains("beforeCondition")) {
            beforeCondition.fromNbt(configCompound.getCompound("beforeCondition"));
        }
        if (configCompound.contains("directSunlightCondition")) {
            directSunlightCondition.fromNbt(configCompound.getCompound("directSunlightCondition"));
        }
        if (configCompound.contains("distanceWalkedCondition")) {
            distanceWalkedCondition.fromNbt(configCompound.getCompound("distanceWalkedCondition"));
        }
        if (configCompound.contains("noMonstersNearbyCondition")) {
            noMonstersNearbyCondition.fromNbt(configCompound.getCompound("noMonstersNearbyCondition"));
        }
        if (configCompound.contains("minHealthCondition")) {
            minHealthCondition.fromNbt(configCompound.getCompound("minHealthCondition"));
        }

        if (configCompound.contains("xpCostAction")) {
            xpCostAction.fromNbt(configCompound.getCompound("xpCostAction"));
        }
    }

    public CompoundTag toNbt() {
        CompoundTag root = new CompoundTag();
        GlobalConfig config = Reference.globalConfig;
        if (enabled != config.enabled) {
            root.putBoolean("enabled", enabled);
        }
        if (!timeSpentCondition.equals(config.timeSpentCondition)) {
            root.put("timeSpentCondition", timeSpentCondition.toNbt());
        }
        if (!afterCondition.equals(config.afterCondition)) {
            root.put("afterCondition", afterCondition.toNbt());
        }
        if (!beforeCondition.equals(config.beforeCondition)) {
            root.put("beforeCondition", beforeCondition.toNbt());
        }
        if (!directSunlightCondition.equals(config.directSunlightCondition)) {
            root.put("directSunlightCondition", directSunlightCondition.toNbt());
        }
        if (!distanceWalkedCondition.equals(config.distanceWalkedCondition)) {
            root.put("distanceWalkedCondition", distanceWalkedCondition.toNbt());
        }
        if (!noMonstersNearbyCondition.equals(config.noMonstersNearbyCondition)) {
            root.put("noMonstersNearbyCondition", noMonstersNearbyCondition.toNbt());
        }
        if (!minHealthCondition.equals(config.minHealthCondition)) {
            root.put("minHealthCondition", minHealthCondition.toNbt());
        }

        if (!xpCostAction.equals(config.xpCostAction)) {
            root.put("xpCostAction", xpCostAction.toNbt());
        }
        return root;
    }
}
