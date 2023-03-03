package com.hbm.inventory.container;

import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.TileEntitySILEX;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerSILEX extends Container {

	private TileEntitySILEX silex;

	public ContainerSILEX(InventoryPlayer invPlayer, TileEntitySILEX te) {
		silex = te;

		//Input
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 0, 80, 12));
		//Fluid Container
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 1, 8, 24));
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 2, 44, 24));
		//Output
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 3, 116, 90));
		//Output Queue
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 4, 134, 72));
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 5, 152, 72));
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 6, 134, 90));
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 7, 152, 90));
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 8, 134, 108));
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 9, 152, 108));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + (18 * 3) + 2));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + (18 * 3) + 2));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = ItemStack.EMPTY;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= silex.inventory.getSlots() - 1) {
				if(!this.mergeItemStack(var5, silex.inventory.getSlots(), this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if(var5.getItem() == ModItems.turret_chip) { //did i copy this from turrets? tf is happening lol
				
				if(!this.mergeItemStack(var5, 0, 1, false))
					return ItemStack.EMPTY;
				
			} else if(!this.mergeItemStack(var5, 1, silex.inventory.getSlots(), false)) {
				return ItemStack.EMPTY;
			}

			if(var5.isEmpty()) {
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
		return silex.isUseableByPlayer(player);
	}
}