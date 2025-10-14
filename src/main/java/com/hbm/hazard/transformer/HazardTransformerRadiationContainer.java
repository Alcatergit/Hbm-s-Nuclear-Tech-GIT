package com.hbm.hazard.transformer;

import java.util.List;

import com.hbm.blocks.generic.BlockStorageCrate;
import com.hbm.blocks.generic.BlockStorageCrateRadResistant;
import com.hbm.hazard.HazardEntry;
import com.hbm.hazard.HazardRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class HazardTransformerRadiationContainer extends HazardTransformerBase {

	@Override
	public void transformPre(ItemStack stack, List<HazardEntry> entries) { }

	@Override
	public void transformPost(ItemStack stack, List<HazardEntry> entries) {
		
		if(!stack.hasTagCompound()) return;
		Block b = Block.getBlockFromItem(stack.getItem());
		boolean isCrate = b instanceof BlockStorageCrate && !(b instanceof BlockStorageCrateRadResistant);
		
		if(!isCrate) return;
		if(!stack.getTagCompound().hasKey("cRads")) return;
		float rads = stack.getTagCompound().getFloat("cRads");
		if(rads > 0) {
			entries.add(new HazardEntry(HazardRegistry.RADIATION, rads));
		}
	}
}
