package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityCrucible;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

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
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int par2) {
		ItemStack var3 = ItemStack.EMPTY;
		Slot var4 = (Slot) this.inventorySlots.get(par2);
		
		if (var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			
            if (par2 <= 1) {
				if (!this.mergeItemStack(var5, 2, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 1, false))
				if (!this.mergeItemStack(var5, 1, 2, false))
					return ItemStack.EMPTY;
			
			if (var5.getCount() == 0)
			{
				var4.putStack(ItemStack.EMPTY);
			}
			else
			{
				var4.onSlotChanged();
			}
		}
		
		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return crucible.isUseableByPlayer(player);
	}
}
