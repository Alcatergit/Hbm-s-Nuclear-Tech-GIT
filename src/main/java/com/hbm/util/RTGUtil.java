package com.hbm.util;

import com.hbm.items.machine.ItemRTGPellet;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraft.item.ItemStack;

public class RTGUtil {
	
	public static short getPower(ItemRTGPellet fuel, ItemStack stack) {
		return ItemRTGPellet.getScaledPower(fuel, stack);
	}
	
	public static boolean hasHeat(ItemStackHandler inventory) {
		for(int i = 0; i < inventory.getSlots(); i++){

			if(inventory.getStackInSlot(i).isEmpty())
				continue;

			if(inventory.getStackInSlot(i).getItem() instanceof ItemRTGPellet)
				return true;
		}
		
		return false;
	}
	
	public static int updateRTGs(ItemStackHandler inventory, int[] slots) {
		int newHeat = 0;
		for(int i: slots){
			
			if(inventory.getStackInSlot(i).isEmpty())
				continue;
			
			if(!(inventory.getStackInSlot(i).getItem() instanceof ItemRTGPellet pellet))
				continue;

            newHeat += getPower(pellet, inventory.getStackInSlot(i));
			inventory.setStackInSlot(i, ItemRTGPellet.handleDecay(inventory.getStackInSlot(i), pellet));
		}
		
		return newHeat;
		
	}
}
