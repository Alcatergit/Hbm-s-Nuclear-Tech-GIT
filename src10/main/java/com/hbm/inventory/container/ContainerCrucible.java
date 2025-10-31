package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityCrucible;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ContainerCrucible extends Container {
	
	protected TileEntityCrucible crucible;
	
	public ContainerCrucible(InventoryPlayer invPlayer, TileEntityCrucible crucible) {
		this.crucible = crucible;
		
		//template
		this.addSlotToContainer(new SlotItemHandler(crucible.inventory, 0, 107, 81));
		
		//input
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				this.addSlotToContainer(new SlotItemHandler(crucible.inventory, j + i * 3 + 1, 107 + j * 18, 18 + i * 18));
			}
		}
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 132 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 190));
		}
	}

	@Override
	public @NotNull ItemStack transferStackInSlot(@NotNull EntityPlayer playerIn, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			stack = slotStack.copy();
			if(index > 10) {
				if (crucible.isItemValidForSlot(0, stack)) {
					if (!this.mergeItemStack(slotStack, 0, 1, false))
						return ItemStack.EMPTY;
				} else if (!this.mergeItemStack(slotStack, 1, 11, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(slotStack, 11, this.inventorySlots.size(), false)) {
				return ItemStack.EMPTY;
			}

			if (slotStack.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}
		}
		
		return stack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return crucible.isUseableByPlayer(player);
	}
}
