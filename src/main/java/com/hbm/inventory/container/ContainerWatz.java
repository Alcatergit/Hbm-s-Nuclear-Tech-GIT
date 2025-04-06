package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityWatz;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerWatz extends Container {
	
	protected TileEntityWatz watz;
	
	public ContainerWatz(InventoryPlayer invPlayer, TileEntityWatz tedf) {
		watz = tedf;

		int index = 0;
		for(int j = 0; j < 6; j++) {
			for(int i = 0; i < 6; i++) {

				if(i + j > 1 && i + j < 9 && 5 - i + j > 1 && i + 5 - j > 1) {
					this.addSlotToContainer(new SlotItemHandler(watz.inventory, index, 17 + i * 18, 8 + j * 18));
					index++;
				}
			}
		}
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 147 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 205));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = ItemStack.EMPTY;
		Slot var4 = this.inventorySlots.get(par2);
		
		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			
			if(par2 <= watz.inventory.getSlots() - 1) {
				if(!mergeItemStack(var5, watz.inventory.getSlots(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!mergeItemStack(var5, 0, watz.inventory.getSlots(), false)) {
				return null;
			}

			if(var5.getCount() == 0) {
				var4.putStack(ItemStack.EMPTY);
			} else {
				var4.onSlotChanged();
			}
			
			var4.onTake(p_82846_1_, var5);
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return watz.isUseableByPlayer(player);
	}
}
