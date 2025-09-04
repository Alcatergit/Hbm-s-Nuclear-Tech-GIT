package com.hbm.items.machine;

import com.hbm.items.ISatChip;
import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemSatChip extends Item implements ISatChip {

	public ItemSatChip(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18nUtil.resolveKey("desc.satellitefr", getFreq(stack)));

		if(this == ModItems.sat_foeq)
			tooltip.add("Gives you an achievement. That's it.");

		if(this == ModItems.sat_gerald) {
			tooltip.add("Single use.");
			tooltip.add("Requires orbital module.");
			tooltip.add("Melter of CPUs, bane of every server owner.");
		}

		if(this == ModItems.sat_laser)
			tooltip.add("Allows to summon lasers with a 15 second cooldown.");

		if(this == ModItems.sat_mapper)
			tooltip.add("Displays currently loaded chunks.");

		if(this == ModItems.sat_miner)
			tooltip.add("Will deliver ore powders to a cargo landing pad.");

		if(this == ModItems.sat_radar)
			tooltip.add("Shows a map of active entities.");

		if(this == ModItems.sat_resonator)
			tooltip.add("Allows for teleportation with no cooldown.");

		if(this == ModItems.sat_scanner)
			tooltip.add("Creates a topdown map of underground ores.");
	}
}
