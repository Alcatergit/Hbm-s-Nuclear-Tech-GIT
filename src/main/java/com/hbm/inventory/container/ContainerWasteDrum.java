package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityWasteDrum;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerWasteDrum extends Container {
	
	private TileEntityWasteDrum diFurnace;
	
	public ContainerWasteDrum(InventoryPlayer invPlayer, TileEntityWasteDrum tedf) {
		diFurnace = tedf;

		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 71, 21));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 89, 21));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 53, 39));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3, 71, 39));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4, 89, 39));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 5, 107, 39));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 6, 53, 57));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 7, 71, 57));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 8, 89, 57));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 9, 107, 57));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 10, 71, 75));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 11, 89, 75));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 92 + i * 18 + 20));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 170));
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
			
            if (par2 <= diFurnace.inventory.getSlots() - 1) {
				if (!this.mergeItemStack(var5, diFurnace.inventory.getSlots(), this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 0, false))
			{
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
		return diFurnace.isUseableByPlayer(player);
	}
}
