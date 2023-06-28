package com.hbm.inventory;

import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import net.minecraft.item.Item;
import net.minecraftforge.items.IItemHandler;

import java.util.EnumMap;
import java.util.Map;

public class UpgradeManager {
    private final Map<UpgradeType, Integer> upgrades = new EnumMap<>(UpgradeType.class);

	private UpgradeType mutexType = null;

	public void eval(IItemHandler inv, int start, int end) {
		upgrades.clear();

		for (int i = start; i <= end; i++) {
			Item item = inv.getStackInSlot(i).getItem();
			if (item instanceof ItemMachineUpgrade) {
				ItemMachineUpgrade upgrade = (ItemMachineUpgrade) item;
				if (!upgrade.type.mutex) {
					if(upgrade.type == UpgradeType.SPEED)
						upgrades.compute(upgrade.type, (type, level) -> level == null ? upgrade.getSpeed() : level + upgrade.getSpeed());
					else
						upgrades.compute(upgrade.type, (type, level) -> level == null ? upgrade.tier : level + upgrade.tier);
				} else {
					if (mutexType == null || mutexType.ordinal() < upgrade.type.ordinal()) {
						mutexType = upgrade.type;
					}
				}
			}
		}
	}

    public int getLevel(UpgradeType type) {
        return upgrades.getOrDefault(type, 0);
    }
}
