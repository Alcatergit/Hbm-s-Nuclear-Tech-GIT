package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityConverterHeRf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ContainerConverterHeRf extends Container {

	public ContainerConverterHeRf(EntityPlayer invPlayer, TileEntityConverterHeRf tedf) {
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
