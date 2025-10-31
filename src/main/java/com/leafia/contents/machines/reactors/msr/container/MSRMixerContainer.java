package com.leafia.contents.machines.reactors.msr.container;

import com.hbm.inventory.SlotMachineOutput;
import com.leafia.contents.machines.reactors.msr.MSRMixerTE;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class MSRMixerContainer extends Container {

	private MSRMixerTE diFurnace;
	
	public MSRMixerContainer(InventoryPlayer invPlayer,MSRMixerTE tedf) {
		
		diFurnace = tedf;

		//Battery
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 8, 53));

		//Fluid IO
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 44, 17));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 2, 44, 53));
		for (int x = 0; x <= 2; x++) {
			for (int y = 0; y <= 1; y++)
				this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3+x+y*3, 71+x*18, 17+y*18));
		}
		
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
			
            if (par2 <= 8) {
				if (!this.mergeItemStack(var5, 9, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 2, false))
			{
				if (!this.mergeItemStack(var5, 3, 4, false))
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
