package com.hbm.hazard.transformer;

import java.util.List;

import com.hbm.blocks.generic.BlockStorageCrate;
import com.hbm.hazard.HazardEntry;
import com.hbm.hazard.HazardRegistry;
import com.hbm.hazard.HazardSystem;
import com.hbm.items.ModItems;
import com.hbm.util.BobMathUtil;
import com.hbm.util.ItemStackUtil;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class HazardTransformerRadiationContainer extends HazardTransformerBase {

	@Override
	public void transformPre(final ItemStack stack, final List<HazardEntry> entries) { }

	@Override
	public void transformPost(final ItemStack stack, final List<HazardEntry> entries) {
		
		final boolean isCrate = Block.getBlockFromItem(stack.getItem()) instanceof BlockStorageCrate;
		final boolean isBox = stack.getItem() == ModItems.containment_box;
		//boolean isBag = stack.getItem() == ModItems.plastic_bag;

		//TODO:Add that bag
		//if(!isCrate && !isBox && !isBag) return;
		if(!isCrate && !isBox) return;
		if(!stack.hasTagCompound()) return;

		float radiation = 0;
		
		if(isCrate) {

			for(int i = 0; i < 104; i++) {
				final NBTTagCompound slotTag = stack.getTagCompound().getCompoundTag("slot"+i);
				final ItemStack held = ItemStackUtil.itemStackFrom(slotTag);


				if(held != null) {
					radiation += HazardSystem.getHazardLevelFromStack(held, HazardRegistry.RADIATION) * held.getCount();
				}
			}
		}
		
		if(isBox) {

			//ItemStack[] fromNBT = ItemStackUtil.readStacksFromNBT(stack, 20);
			final ItemStack[] fromNBT = ItemStackUtil.readStacksFromNBT(stack);
			if(fromNBT == null) return;
			
			for(final ItemStack held : fromNBT) {
				if(held != null) {
					radiation += HazardSystem.getHazardLevelFromStack(held, HazardRegistry.RADIATION) * held.getCount();
				}
			}
			
			radiation = (float) BobMathUtil.sqrt(radiation);
		}
		
//		if(isBag) {
//
//			ItemStack[] fromNBT = ItemStackUtil.readStacksFromNBT(stack, 1);
//			if(fromNBT == null) return;
//
//			for(ItemStack held : fromNBT) {
//				if(held != null) {
//					radiation += HazardSystem.getHazardLevelFromStack(held, HazardRegistry.RADIATION) * held.stackSize;
//				}
//			}
//
//			radiation *= 2F;
//		}
		
		if(radiation > 0) {
			entries.add(new HazardEntry(HazardRegistry.RADIATION, radiation));
		}
	}
}
