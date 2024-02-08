package com.hbm.inventory.container;

import com.hbm.tileentity.bomb.TileEntityNukeTsar;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerNukeTsar extends Container {

private TileEntityNukeTsar nukeTsar;
	
	public ContainerNukeTsar(InventoryPlayer invPlayer, TileEntityNukeTsar tedf) {
		
		nukeTsar = tedf;
		
		//Core
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 41, 39)); // Plutonium Core

		//Lenses
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 66, 93)); // Top Left
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 84, 93)); // Top Right
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3, 66, 111)); // Bottom Left
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4, 84, 111)); // Bottom Right

		//Stage 1 
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 5, 111, 102)); // Uranium coating
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 6, 129, 102)); // Deuterium tank

		//Stage 2
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 7, 155, 102)); // Uranium coating
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 8, 173, 102)); // Deuterium tank

		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 48 + j * 18, 138 + i * 18));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 48 + i * 18, 196));
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
			
            if (par2 <= 5) {
				if (!this.mergeItemStack(var5, 6, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			} else {
				return ItemStack.EMPTY;
			}
            
			if (var5.isEmpty())
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
		return nukeTsar.isUseableByPlayer(player);
	}
}
