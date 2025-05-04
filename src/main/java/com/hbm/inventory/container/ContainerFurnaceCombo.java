package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityFurnaceCombination;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFurnaceCombo extends Container {
	
	protected TileEntityFurnaceCombination furnace;
	
	public ContainerFurnaceCombo(InventoryPlayer invPlayer, TileEntityFurnaceCombination furnace) {
		this.furnace = furnace;

		//input
		this.addSlotToContainer(new SlotItemHandler(furnace.inventory, 0, 26, 36));
		//output
		this.addSlotToContainer(new SlotMachineOutput(furnace.inventory, 1, 89, 36));
		this.addSlotToContainer(new SlotItemHandler(furnace.inventory, 2, 136, 18)); //Tank in
		this.addSlotToContainer(new SlotMachineOutput(furnace.inventory, 3, 136, 54)); //Tank out
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 104 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 162));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack originalStack = slot.getStack();
			stack = originalStack.copy();

			if(index <= 5) {
				if(!this.mergeItemStack(originalStack, 6, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
				
				slot.onSlotChange(originalStack, stack);
				
			} else if(!this.mergeItemStack(originalStack, 0, 3, false)) {
				return ItemStack.EMPTY;
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
		return furnace.isUseableByPlayer(player);
	}
}
