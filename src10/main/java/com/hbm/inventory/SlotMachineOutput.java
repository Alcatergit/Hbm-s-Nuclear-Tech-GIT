package com.hbm.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotMachineOutput extends SlotItemHandler {
	public SlotMachineOutput(IItemHandler inventory, int i, int j, int k) {
		super(inventory, i, j, k);
	}
	
	@Override
	public boolean isItemValid(ItemStack p_75214_1_)
    {
        return false;
    }
}
