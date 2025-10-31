package com.leafia.contents.machines.processing.advcent.container;

import com.leafia.contents.machines.processing.advcent.AdvCentTE;
import com.leafia.dev.container_utility.LeafiaItemTransferable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class AdvCentContainer extends LeafiaItemTransferable {

	private AdvCentTE diFurnace;
	
	public AdvCentContainer(InventoryPlayer invPlayer,AdvCentTE tedf) {
		
		diFurnace = tedf;
		tedf.listeners.add(invPlayer.player);

		//Battery
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 186, 175));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 58, 15));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 58+18, 15));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3, 58+18*2, 15));
		for (int x = 0; x <= 2; x++) {
			for (int y = 0; y <= 1; y++)
				this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4+x+y*2, 58+18*x, 53+18*y));
		}
		//Fluid ID IO
		//Drillgon200 - Don't need you anymore.
		//this.addSlotToContainer(new Slot(tedf, 1, 35, 17));
		//this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 2, 35, 53));
		//Fluid IO
		//this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3, 62, 17));
		//this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 4, 62, 53));
		//Output
		//this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 5, 134, 17));
		//this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 6, 152, 17));
		//this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 7, 134, 53));
		//this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 8, 152, 53));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 122 + i * 18));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 180));
		}
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		diFurnace.listeners.remove(playerIn);
		super.onContainerClosed(playerIn);
	}

	@Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2)
    {
	    LeafiaItemTransfer transfer = new LeafiaItemTransfer(6)._selected(par2);
	    return transfer.__forSlots(0,9999)
			    .__tryMoveToInventory(true)

			    .__forInventory()
			    .__tryMoveToSlot(0,transfer.__maxIndex,false)

			    .__getReturn();
		/*
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
		
		return var3;*/
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return diFurnace.isUseableByPlayer(player);
	}
}
