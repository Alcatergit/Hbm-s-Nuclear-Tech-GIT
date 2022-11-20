package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineReactorLarge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerReactorMultiblock extends Container {
	
	private TileEntityMachineReactorLarge diFurnace;
	
	
	public ContainerReactorMultiblock(InventoryPlayer invPlayer, TileEntityMachineReactorLarge tedf) {
		
		diFurnace = tedf;

		//Water in
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 6, 60));
		//Water out
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 1, 6, 78));
		//Coolant in
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 24, 60));
		//Coolant out
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 3, 24, 78));
		
		//Fuel in
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4, 70, 59));
		//Fuel out
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 5, 70, 95));
		//Waste in
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 6, 152, 59));
		//Waste out
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 7, 152, 95));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 56));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 56));
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
			
            if (par2 <= 7) {
				if (!this.mergeItemStack(var5, 8, this.inventorySlots.size(), true))
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
		return diFurnace.isUseableByPlayer(player);
	}
}
