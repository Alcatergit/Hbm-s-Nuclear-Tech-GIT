package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityCoreTitanium;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCoreTitanium extends Container {
	
	private TileEntityCoreTitanium diFurnace;
	
	public ContainerCoreTitanium(InventoryPlayer invPlayer, TileEntityCoreTitanium tedf) {
		
		diFurnace = tedf;
		
		//Input Storage
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 8, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 26, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 44, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3, 62, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4, 80, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 5, 98, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 6, 116, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 7, 134, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 8, 152, 18));
		//Inputs
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 9, 8, 54));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 10, 8, 72));
		//Outputs
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 11, 152, 54));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 12, 152, 72));
		//Output Storage
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 13, 8, 108));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 14, 26, 108));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 15, 44, 108));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 16, 62, 108));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 17, 80, 108));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 18, 98, 108));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 19, 116, 108));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 20, 134, 108));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 21, 152, 108));
		//Power Cell
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 22, 44, 72));
		
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
			
            if (par2 <= 22) {
				if (!this.mergeItemStack(var5, 23, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(var5, 22, 23, false))
			{
				if (!this.mergeItemStack(var5, 9, 11, false))
					if (!this.mergeItemStack(var5, 0, 9, false))
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