package com.hbm.creativetabs;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemCustomMissile;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;

public class MissileTab extends CreativeTabs {

	public MissileTab(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack createIcon() {
		if(ModItems.missile_nuclear != null){
			return new ItemStack(ModItems.missile_nuclear);
		}
		return new ItemStack(Items.IRON_PICKAXE);
	}

	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> list) {
		super.displayAllRelevantItems(list);
		
		list.add(ItemCustomMissile.buildMissile(
				ModItems.mp_chip_3,
				ModItems.mp_warhead_10_he,
				ModItems.mp_fuselage_10_kerosene,
				ModItems.mp_stability_10_flat,
				ModItems.mp_thruster_10_kerosene).setStackDisplayName(TextFormatting.DARK_PURPLE + "Lil Bub"));
		
		list.add(ItemCustomMissile.buildMissile(
				ModItems.mp_chip_3,
				ModItems.mp_warhead_10_incendiary,
				ModItems.mp_fuselage_10_long_solid,
				ModItems.mp_stability_10_space,
				ModItems.mp_thruster_10_solid).setStackDisplayName(TextFormatting.DARK_PURPLE + "Long Boy"));
		
		list.add(ItemCustomMissile.buildMissile(
				ModItems.mp_chip_3,
				ModItems.mp_warhead_10_nuclear,
				ModItems.mp_fuselage_10_15_kerosene,
				ModItems.mp_stability_15_flat,
				ModItems.mp_thruster_15_kerosene).setStackDisplayName(TextFormatting.DARK_PURPLE + "Uncle Kim"));
		
		list.add(ItemCustomMissile.buildMissile(
				ModItems.mp_chip_3,
				ModItems.mp_warhead_10_nuclear_large,
				ModItems.mp_fuselage_10_15_balefire,
				ModItems.mp_stability_15_flat,
				ModItems.mp_thruster_15_balefire_large).setStackDisplayName(TextFormatting.GREEN + "Trotty's Toy Rocket"));
		
		list.add(ItemCustomMissile.buildMissile(
				ModItems.mp_chip_3,
				ModItems.mp_warhead_15_nuclear_shark,
				ModItems.mp_fuselage_15_kerosene_camo,
				ModItems.mp_stability_15_thin,
				ModItems.mp_thruster_15_kerosene_triple).setStackDisplayName(TextFormatting.DARK_PURPLE + "Stealthy Shark"));
		
		list.add(ItemCustomMissile.buildMissile(
				ModItems.mp_chip_3,
				ModItems.mp_warhead_15_he,
				ModItems.mp_fuselage_15_kerosene_polite,
				ModItems.mp_stability_15_thin,
				ModItems.mp_thruster_15_kerosene_dual).setStackDisplayName(TextFormatting.DARK_PURPLE + "Polite Lad"));
		
		list.add(ItemCustomMissile.buildMissile(
				ModItems.mp_chip_3,
				ModItems.mp_warhead_15_n2,
				ModItems.mp_fuselage_15_solid_desh,
				ModItems.mp_stability_15_thin,
				ModItems.mp_thruster_15_solid_hexdecuple).setStackDisplayName(TextFormatting.DARK_PURPLE + "NERV's Leftover Missile"));
		
		list.add(ItemCustomMissile.buildMissile(
				ModItems.mp_chip_5,
				ModItems.mp_warhead_15_mirv,
				ModItems.mp_fuselage_15_kerosene_lambda,
				ModItems.mp_stability_15_soyuz,
				ModItems.mp_thruster_15_kerosene).setStackDisplayName(TextFormatting.BLUE + "7 For 1 Package Deal"));
		
		list.add(ItemCustomMissile.buildMissile(
				ModItems.mp_chip_4,
				ModItems.mp_warhead_15_balefire,
				ModItems.mp_fuselage_15_20_kerosene_magnusson,
				null,
				ModItems.mp_thruster_20_kerosene).setStackDisplayName(TextFormatting.GREEN + "Hightower Missile"));
		
	}
}
