package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineExcavator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMachineExcavator extends Container {
	
	TileEntityMachineExcavator excavator;

	public ContainerMachineExcavator(InventoryPlayer invPlayer, TileEntityMachineExcavator tile) {
		this.excavator = tile;

		//Battery: 0
		this.addSlotToContainer(new SlotItemHandler(tile.inventory, 0, 220, 72));
		//Fluid ID: 1
		this.addSlotToContainer(new SlotItemHandler(tile.inventory, 1, 202, 72));
		//Upgrades: 2-4
		for(int i = 0; i < 3; i++) {
			this.addSlotToContainer(new SlotItemHandler(tile.inventory, 2 + i, 136 + i * 18, 75));
		}
		//Buffer: 5-13
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				this.addSlotToContainer(new SlotMachineOutput(tile.inventory, 5 + j + i * 3, 136 + j * 18, 5 + i * 18));
			}
		}
		
		//Inventory
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 41 + j * 18, 122 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 41 + i * 18, 180));
		}
	}

	@Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2)
    {
		ItemStack var3 = ItemStack.EMPTY;
		Slot var4 = (Slot) this.inventorySlots.get(par2);
		
		if (var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			
            if (par2 <= 3) {
				if (!this.mergeItemStack(var5, 4, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 3, false))
			{
				return ItemStack.EMPTY;
			}
			
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
		return excavator.isUseableByPlayer(player);
	}
}
