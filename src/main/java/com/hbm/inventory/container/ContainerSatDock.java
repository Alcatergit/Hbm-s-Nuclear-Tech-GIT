package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityMachineSatDock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerSatDock extends Container {
	
	private TileEntityMachineSatDock diFurnace;
	
	public ContainerSatDock(InventoryPlayer invPlayer, TileEntityMachineSatDock tedf) {
		
		diFurnace = tedf;

		//Storage
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 62, 17));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 80, 17));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 98, 17));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3, 116, 17));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4, 134, 17));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 5, 62, 35));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 6, 80, 35));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 7, 98, 35));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 8, 116, 35));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 9, 134, 35));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 10, 62, 53));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 11, 80, 53));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 12, 98, 53));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 13, 116, 53));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 14, 134, 53));
		//Chip
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 15, 26, 35));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
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
			
            if (par2 <= 15) {
				if (!this.mergeItemStack(var5, 16, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 15, false))
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
