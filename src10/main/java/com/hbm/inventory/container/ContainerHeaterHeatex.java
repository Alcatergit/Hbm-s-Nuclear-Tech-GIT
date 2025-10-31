package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityHeaterHeatex;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerHeaterHeatex extends Container {

	private TileEntityHeaterHeatex heater;

	public ContainerHeaterHeatex(InventoryPlayer invPlayer, TileEntityHeaterHeatex tedf) {
		heater = tedf;

		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 80, 72));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 122 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 180));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack originalStack = slot.getStack();
			stack = originalStack.copy();

			if(index == 0) {
				if(!this.mergeItemStack(originalStack, 1, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else {
				if(!this.mergeItemStack(originalStack, 0, 1, false)) {
					return ItemStack.EMPTY;
				}
			}

			if(originalStack.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return stack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return heater.isUseableByPlayer(player);
	}
}
