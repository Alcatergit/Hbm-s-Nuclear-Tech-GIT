package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityConverterRfHe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ContainerConverterRfHe extends Container {

	public ContainerConverterRfHe(EntityPlayer invPlayer, TileEntityConverterRfHe tedf) {
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

}
