package com.hbm.creativetabs;

import com.hbm.items.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class MissileTab extends CreativeTabs {

	public MissileTab(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack getTabIconItem() {
		if(ModItems.missile_nuclear != null){
			return new ItemStack(ModItems.missile_nuclear);
		}
		return new ItemStack(Items.IRON_PICKAXE);
	}

	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> p_78018_1_) {
		super.displayAllRelevantItems(p_78018_1_);
		//TODO custom missiles
		/*
		list.add(ItemCustomMissile.buildMissile(
				ModItems.mp_chip_3,
				ModItems.mp_warhead_10_he,
				ModItems.mp_fuselage_10_kerosene,
				ModItems.mp_stability_10_flat,
				ModItems.mp_thruster_10_kerosene).setStackDisplayName(EnumChatFormatting.DARK_PURPLE + "Lil Bub"));
		
		list.add(ItemCustomMissile.buildMissile(
				ModItems.mp_chip_3,
				ModItems.mp_warhead_10_incendiary,
				ModItems.mp_fuselage_10_long_solid,
				ModItems.mp_stability_10_space,
				ModItems.mp_thruster_10_solid).setStackDisplayName(EnumChatFormatting.DARK_PURPLE + "Long Boy"));
		
		list.add(ItemCustomMissile.buildMissile(
				ModItems.mp_chip_3,
				ModItems.mp_warhead_10_nuclear,
				ModItems.mp_fuselage_10_15_kerosene,
				ModItems.mp_stability_15_flat,
				ModItems.mp_thruster_15_kerosene).setStackDisplayName(EnumChatFormatting.DARK_PURPLE + "Uncle Kim"));
		
		list.add(ItemCustomMissile.buildMissile(
				ModItems.mp_chip_3,
				ModItems.mp_warhead_10_nuclear_large,
				ModItems.mp_fuselage_10_15_balefire,
				ModItems.mp_stability_15_flat,
				ModItems.mp_thruster_15_balefire_large).setStackDisplayName(EnumChatFormatting.GREEN + "Trotty's Toy Rocket"));
		
		list.add(ItemCustomMissile.buildMissile(
				ModItems.mp_chip_3,
				ModItems.mp_warhead_15_nuclear_shark,
				ModItems.mp_fuselage_15_kerosene_camo,
				ModItems.mp_stability_15_thin,
				ModItems.mp_thruster_15_kerosene_triple).setStackDisplayName(EnumChatFormatting.DARK_PURPLE + "Stealthy Shark"));
		
		list.add(ItemCustomMissile.buildMissile(
				ModItems.mp_chip_3,
				ModItems.mp_warhead_15_he,
				ModItems.mp_fuselage_15_kerosene_polite,
				ModItems.mp_stability_15_thin,
				ModItems.mp_thruster_15_kerosene_dual).setStackDisplayName(EnumChatFormatting.DARK_PURPLE + "Polite Lad"));
		
		list.add(ItemCustomMissile.buildMissile(
				ModItems.mp_chip_3,
				ModItems.mp_warhead_15_n2,
				ModItems.mp_fuselage_15_solid_desh,
				ModItems.mp_stability_15_thin,
				ModItems.mp_thruster_15_solid_hexdecuple).setStackDisplayName(EnumChatFormatting.DARK_PURPLE + "NERV's Leftover Missile"));
		
		list.add(ItemCustomMissile.buildMissile(
				ModItems.mp_chip_5,
				ModItems.mp_warhead_15_boxcar,
				ModItems.mp_fuselage_15_kerosene_blackjack,
				ModItems.mp_stability_15_thin,
				ModItems.mp_thruster_15_kerosene).setStackDisplayName(EnumChatFormatting.RED + "Auntie Blackjack"));
		
		list.add(ItemCustomMissile.buildMissile(
				ModItems.mp_chip_4,
				ModItems.mp_warhead_15_balefire,
				ModItems.mp_fuselage_15_20_kerosene_magnusson,
				null,
				ModItems.mp_thruster_20_kerosene).setStackDisplayName(EnumChatFormatting.GREEN + "Hightower Missile"));
		*/
	}
}
