package com.hbm.items.machine;

import com.google.common.collect.Sets;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.jei.UpgradeDetailsDatabase;
import com.hbm.items.ModItems;
import com.hbm.items.ModItems.Upgrades;
import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.Set;

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
		this.setTranslationKey(s);
		this.setRegistryName(s);

		this.type = type;
		this.tier = tier;

		ModItems.ALL_ITEMS.add(this);
		UpgradeDetailsDatabase.tryAddUpgrade(type,tier,this);
	}

	public int getSpeed(){
		if(this == Upgrades.upgrade_speed_1) return 1;
		if(this == Upgrades.upgrade_speed_2) return 2;
		if(this == Upgrades.upgrade_speed_3) return 3;
		if(this == Upgrades.upgrade_overdrive_1) return 4;
		if(this == Upgrades.upgrade_overdrive_2) return 6;
		if(this == Upgrades.upgrade_overdrive_3) return 8;
		if(this == Upgrades.upgrade_screm) return 10;
		return 0;
	}

	public static int getSpeed(ItemStack stack){
		if(stack == null || stack.isEmpty()) return 0;
		Item upgrade = stack.getItem();
		if(upgrade == Upgrades.upgrade_speed_1) return 1;
		if(upgrade == Upgrades.upgrade_speed_2) return 2;
		if(upgrade == Upgrades.upgrade_speed_3) return 3;
		if(upgrade == Upgrades.upgrade_overdrive_1) return 4;
		if(upgrade == Upgrades.upgrade_overdrive_2) return 6;
		if(upgrade == Upgrades.upgrade_overdrive_3) return 8;
		if(upgrade == Upgrades.upgrade_screm) return 10;
		return 0;
	}
	private static void writeUpgrade(List<String> list,String category,String key) {
		list.add(TextFormatting.LIGHT_PURPLE + I18nUtil.resolveKey("desc.upgradenew.upgrade",I18nUtil.resolveKey(category)));
		String[] descs = I18nUtil.resolveKey("desc.upgradenew."+key).split("__");
		for (String desc:descs) {
			list.add(desc);
		}
		list.add("");
		list.add(TextFormatting.GREEN + I18nUtil.resolveKey("desc.upgradenew.jei")+"!"); // yay
		list.add("");
	}
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		String res = this.getRegistryName().getPath();
		switch(res.substring(0,res.length()-2)) {
			case "upgrade_speed":
				writeUpgrade(list,"desc.upgradenew.general","speed");
				return; // java why
			case "upgrade_effect":
				writeUpgrade(list,"desc.upgradenew.general","effect");
				return; // java why
			case "upgrade_power":
				writeUpgrade(list,"desc.upgradenew.general","power");
				return; // java why
			case "upgrade_overdrive":
				writeUpgrade(list,"desc.upgradenew.general","overdrive");
				return; // java why

			case "upgrade_fortune":
				list.add(TextFormatting.LIGHT_PURPLE + I18nUtil.resolveKey("desc.upgradenew.upgrade",I18nUtil.resolveKey("tile.machine_mining_laser.name")));
				list.add(I18nUtil.resolveKey("enchantment.lootBonusDigger"));
				list.add("");
				list.add(TextFormatting.GREEN + I18nUtil.resolveKey("desc.upgradenew.jei")+"!"); // yay
				list.add("");
				return; // java why
			case "upgrade_afterburn":
				writeUpgrade(list,"tile.machine_turbofan.name","afterburner");
				return; // why
		}
		if(this == Upgrades.upgrade_radius)
		{
			list.add(TextFormatting.GOLD+I18nUtil.resolveKey("desc.upgrade7"));
			list.add(" "+I18nUtil.resolveKey("desc.upgraderd"));
			list.add("");
			list.add(" "+I18nUtil.resolveKey("desc.upgradestack"));
		}

		if(this == Upgrades.upgrade_health)
		{
			list.add(TextFormatting.GOLD+I18nUtil.resolveKey("desc.upgrade8"));
			list.add(" "+I18nUtil.resolveKey("desc.upgradeht"));
			list.add("");
			list.add(" "+I18nUtil.resolveKey("desc.upgradestack"));
		}
		
		if(this == Upgrades.upgrade_smelter)
		{
			list.add(TextFormatting.GOLD+I18nUtil.resolveKey("desc.upgrade9"));
			list.add(" "+I18nUtil.resolveKey("desc.upgrade12"));
		}

		if(this == Upgrades.upgrade_shredder)
		{
			list.add(TextFormatting.GOLD+I18nUtil.resolveKey("desc.upgrade9"));
			list.add(" "+I18nUtil.resolveKey("desc.upgrade13"));
		}

		if(this == Upgrades.upgrade_centrifuge)
		{
			list.add(TextFormatting.GOLD+I18nUtil.resolveKey("desc.upgrade9"));
			list.add(" "+I18nUtil.resolveKey("desc.upgrade21"));
		}

		if(this == Upgrades.upgrade_crystallizer)
		{
			list.add(TextFormatting.GOLD+I18nUtil.resolveKey("desc.upgrade9"));
			list.add(" "+I18nUtil.resolveKey("desc.upgrade14"));
		}

		if(this == Upgrades.upgrade_screm)
		{
			list.add(I18nUtil.resolveKey("desc.upgrade15"));
			list.add(I18nUtil.resolveKey("desc.upgrade16"));
			list.add(I18nUtil.resolveKey("desc.upgrade17"));
		}

		if(this == Upgrades.upgrade_nullifier)
		{
			String[] duh = I18nUtil.resolveKey("jei.upg.nullify").split("__");
			list.add(duh[0]);
			if (duh.length >= 2)
				list.add(duh[1]);
		}

		if(this == Upgrades.upgrade_ejector_1)
		{
			list.add(TextFormatting.GOLD+I18nUtil.resolveKey("desc.upgrade22"));
			list.add(" "+I18nUtil.resolveKey("desc.upgradeej1"));
		}

		if(this == Upgrades.upgrade_ejector_2)
		{
			list.add(TextFormatting.GOLD+I18nUtil.resolveKey("desc.upgrade22"));
			list.add(" "+I18nUtil.resolveKey("desc.upgradeej2"));
		}

		if(this == Upgrades.upgrade_ejector_3)
		{
			list.add(TextFormatting.GOLD+I18nUtil.resolveKey("desc.upgrade22"));
			list.add(" "+I18nUtil.resolveKey("desc.upgradeej3"));
		}

		if(this == Upgrades.upgrade_stack_1)
		{
			list.add(TextFormatting.GOLD+I18nUtil.resolveKey("desc.upgrade22"));
			list.add(" "+I18nUtil.resolveKey("desc.upgradeej4"));
		}

		if(this == Upgrades.upgrade_stack_2)
		{
			list.add(TextFormatting.GOLD+I18nUtil.resolveKey("desc.upgrade22"));
			list.add(" "+I18nUtil.resolveKey("desc.upgradeej5"));
		}

		if(this == Upgrades.upgrade_stack_3)
		{
			list.add(TextFormatting.GOLD+I18nUtil.resolveKey("desc.upgrade22"));
			list.add(" "+I18nUtil.resolveKey("desc.upgradeej6"));
		}
	}

	public static final Set<Item> scrapItems = Sets.newHashSet(new Item[] {
			Item.getItemFromBlock(Blocks.GRASS),
			Item.getItemFromBlock(Blocks.DIRT),
			Item.getItemFromBlock(Blocks.STONE),
			Item.getItemFromBlock(Blocks.COBBLESTONE),
			Item.getItemFromBlock(Blocks.SAND),
			Item.getItemFromBlock(Blocks.SANDSTONE),
			Item.getItemFromBlock(Blocks.GRAVEL),
			Item.getItemFromBlock(Blocks.NETHERRACK),
			Item.getItemFromBlock(Blocks.END_STONE),
			Item.getItemFromBlock(ModBlocks.stone_gneiss),
			Items.FLINT,
			Items.SNOWBALL,
			Items.WHEAT_SEEDS,
			Items.STICK
			});

	public enum UpgradeType {
		SPEED,
		EFFECT,
		POWER,
		FORTUNE,
		AFTERBURN,
		OVERDRIVE,
		NULLIFIER,
		SCREAM,
		SPECIAL;

		public boolean mutex = false;

		UpgradeType() { }

		UpgradeType(boolean mutex) {
			this.mutex = mutex;
		}
	}
}
