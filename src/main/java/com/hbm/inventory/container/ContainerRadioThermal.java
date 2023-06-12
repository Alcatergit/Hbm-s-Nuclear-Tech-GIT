package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityHeaterRadioThermal;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerRadioThermal extends Container {

	private TileEntityHeaterRadioThermal heater;
	private int heat;
	
	public ContainerRadioThermal(InventoryPlayer invPlayer, TileEntityHeaterRadioThermal tedf) {
		heat = 0;
		
		heater = tedf;
		
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 26, 22));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 44, 22));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 62, 22));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3, 80, 22));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4, 98, 22));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 5, 26, 40));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 6, 44, 40));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 7, 62, 40));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 8, 80, 40));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 9, 98, 40));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 10, 26, 58));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 11, 44, 58));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 12, 62, 58));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 13, 80, 58));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 14, 98, 58));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 94 + i * 18));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 152));
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
			
            if (par2 <= 14) {
				if (!this.mergeItemStack(var5, 15, this.inventorySlots.size(), true))
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
		return heater.isUseableByPlayer(player);
	}
}
