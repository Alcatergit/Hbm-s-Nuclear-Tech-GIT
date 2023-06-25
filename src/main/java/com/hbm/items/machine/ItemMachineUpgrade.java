package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMachineUpgrade extends Item {
	public UpgradeType type;
	public int tier = 0;

	public ItemMachineUpgrade(String s) {
		this(s, UpgradeType.SPECIAL, 0);
	}

	public ItemMachineUpgrade(String s, UpgradeType type) {
		this(s, type, 0);
	}

	public ItemMachineUpgrade(String s, UpgradeType type, int tier) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);

		this.type = type;
		this.tier = tier;

		ModItems.ALL_ITEMS.add(this);
	}

	public int getSpeed(){
		if(this == ModItems.upgrade_speed_1) return 1;
		if(this == ModItems.upgrade_speed_2) return 2;
		if(this == ModItems.upgrade_speed_3) return 3;
		if(this == ModItems.upgrade_overdrive_1) return 4;
		if(this == ModItems.upgrade_overdrive_2) return 6;
		if(this == ModItems.upgrade_overdrive_3) return 8;
		if(this == ModItems.upgrade_screm) return 10;
		return 0;
	}

	public static int getSpeed(ItemStack stack){
		if(stack == null || stack.isEmpty()) return 0;
		Item upgrade = stack.getItem();
		if(upgrade == ModItems.upgrade_speed_1) return 1;
		if(upgrade == ModItems.upgrade_speed_2) return 2;
		if(upgrade == ModItems.upgrade_speed_3) return 3;
		if(upgrade == ModItems.upgrade_overdrive_1) return 4;
		if(upgrade == ModItems.upgrade_overdrive_2) return 6;
		if(upgrade == ModItems.upgrade_overdrive_3) return 8;
		if(upgrade == ModItems.upgrade_screm) return 10;
		return 0;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		if(this == ModItems.upgrade_speed_1)
		{
			list.add("Mining Drill:");
			list.add(" Delay -15 / Consumption +300");
			list.add("");
			list.add("Assembly Machine:");
			list.add(" Delay -25% / Consumption +200%");
			list.add("");
			list.add("Chemical Plant:");
			list.add(" Delay -25% / Consumption +200%");
			list.add("");
			list.add("Crystallizer");
			list.add(" Delay -10% / Consumption +1000");
			list.add("");
			list.add("Cyclotron");
			list.add(" Speed x2");
		}

		if(this == ModItems.upgrade_speed_2)
		{
			list.add("Mining Drill:");
			list.add(" Delay -30 / Consumption +600");
			list.add("");
			list.add("Assembly Machine:");
			list.add(" Delay -50% / Consumption +500%");
			list.add("");
			list.add("Chemical Plant:");
			list.add(" Delay -50% / Consumption +500%");
			list.add("");
			list.add("Crystallizer");
			list.add(" Delay -20% / Consumption +2000");
			list.add("");
			list.add("Cyclotron");
			list.add(" Speed x3");
		}

		if(this == ModItems.upgrade_speed_3)
		{
			list.add("Mining Drill:");
			list.add(" Delay -45 / Consumption +900");
			list.add("");
			list.add("Assembly Machine:");
			list.add(" Delay -75% / Consumption +800%");
			list.add("");
			list.add("Chemical Plant:");
			list.add(" Delay -75% / Consumption +800%");
			list.add("");
			list.add("Crystallizer");
			list.add(" Speed Delay -30% / Consumption +3000");
			list.add("");
			list.add("Cyclotron");
			list.add(" Speed x4");
		}

		if(this == ModItems.upgrade_effect_1)
		{
			list.add("Mining Drill:");
			list.add(" Radius +1 / Consumption +80");
			list.add("");
			list.add("Crystallizer");
			list.add(" +5% chance of not consuming an item / Acid consumption +1000mB");
			list.add("");
			list.add("Cyclotron");
			list.add(" -50% chance of incrementing overheat counter");
		}

		if(this == ModItems.upgrade_effect_2)
		{
			list.add("Mining Drill:");
			list.add(" Radius +2 / Consumption +160");
			list.add("");
			list.add("Crystallizer");
			list.add(" +10% chance of not consuming an item / Acid consumption +2000mB");
			list.add("");
			list.add("Cyclotron");
			list.add(" -66% chance of incrementing overheat counter");
		}

		if(this == ModItems.upgrade_effect_3)
		{
			list.add("Mining Drill:");
			list.add(" Radius +3 / Consumption +240");
			list.add("");
			list.add("Crystallizer");
			list.add(" +15% chance of not consuming an item / Acid consumption +3000mB");
			list.add("");
			list.add("Cyclotron");
			list.add(" -75% chance of incrementing overheat counter");
		}

		if(this == ModItems.upgrade_power_1)
		{
			list.add("Assembly Machine:");
			list.add(" Consumption -20% / Delay +25%");
			list.add("");
			list.add("Chemical Plant:");
			list.add(" Consumption -20% / Delay +25%");
			list.add("");
			list.add("Cyclotron");
			list.add(" Consumption -100k");
		}

		if(this == ModItems.upgrade_power_2)
		{
			list.add("Assembly Machine:");
			list.add(" Consumption -60% / Delay +50%");
			list.add("");
			list.add("Chemical Plant:");
			list.add(" Consumption -60% / Delay +50%");
			list.add("");
			list.add("Cyclotron");
			list.add(" Consumption -200k");
		}

		if(this == ModItems.upgrade_power_3)
		{
			list.add("Assembly Machine:");
			list.add(" Consumption -80% / Delay +100%");
			list.add("");
			list.add("Chemical Plant:");
			list.add(" Consumption -80% / Delay +100%");
			list.add("");
			list.add("Cyclotron");
			list.add(" Consumption -300k");
		}

		if(this == ModItems.upgrade_fortune_1)
		{
			list.add("Mining Drill:");
			list.add(" Fortune +1 / Delay +15");
		}

		if(this == ModItems.upgrade_fortune_2)
		{
			list.add("Mining Drill:");
			list.add(" Fortune +2 / Delay +30");
		}

		if(this == ModItems.upgrade_fortune_3)
		{
			list.add("Mining Drill:");
			list.add(" Fortune +3 / Delay +45");
		}

		if(this == ModItems.upgrade_afterburn_1)
		{
			list.add("Turbofan:");
			list.add(" Production x2 / Consumption x2.5");
		}

		if(this == ModItems.upgrade_afterburn_2)
		{
			list.add("Turbofan:");
			list.add(" Production x3 / Consumption x5");
		}

		if(this == ModItems.upgrade_afterburn_3)
		{
			list.add("Turbofan:");
			list.add(" Production x4 / Consumption x7.5");
		}

		if(this == ModItems.upgrade_radius)
		{
			list.add("Forcefield Range Upgrade");
			list.add(" Radius +16 / Consumption +500");
			list.add("");
			list.add("Stacks to 16");
		}

		if(this == ModItems.upgrade_health)
		{
			list.add("Forcefield Health Upgrade");
			list.add(" Max. Health +50 / Consumption +250");
			list.add("");
			list.add("Stacks to 16");
		}
		
		if(this == ModItems.upgrade_smelter)
		{
			list.add("Mining Laser Upgrade");
			list.add(" Smelts blocks. Easy enough.");
		}

		if(this == ModItems.upgrade_shredder)
		{
			list.add("Mining Laser Upgrade");
			list.add(" Crunches ores");
		}

		if(this == ModItems.upgrade_centrifuge)
		{
			list.add("Mining Laser Upgrade");
			list.add(" Hopefully self-explanatory");
		}

		if(this == ModItems.upgrade_crystallizer)
		{
			list.add("Mining Laser Upgrade");
			list.add(" Your new best friend");
		}

		if(this == ModItems.upgrade_screm)
		{
			list.add("Mining Laser Upgrade");
			list.add(" It's like in Super Mario where all blocks are");
			list.add(" actually Toads, but here it's Half-Life scientists");
			list.add(" and they scream. A lot.");
			list.add("");
			list.add("Mixer Upgrade");
			list.add(" Allows for UU-Matter processing.");
		}
		
		if(this == ModItems.upgrade_nullifier)
		{
			list.add("Mining Laser Upgrade");
			list.add(" 50% chance to override worthless items with /dev/zero");
			list.add(" 50% chance to move worthless items to /dev/null");
		}
	}

	public enum UpgradeType {
		SPEED,
		EFFECT,
		POWER,
		FORTUNE,
		AFTERBURN,
		OVERDRIVE,
		SCREAM,
		SPECIAL;

		public boolean mutex = false;

		UpgradeType() { }

		UpgradeType(boolean mutex) {
			this.mutex = mutex;
		}
	}
}
