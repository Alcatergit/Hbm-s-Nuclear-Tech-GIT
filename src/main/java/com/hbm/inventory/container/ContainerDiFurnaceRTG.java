package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityDiFurnaceRTG;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerDiFurnaceRTG extends Container {
	private TileEntityDiFurnaceRTG bFurnace;
	// private int progress;

	public ContainerDiFurnaceRTG(InventoryPlayer playerInv, TileEntityDiFurnaceRTG teIn) {
		bFurnace = teIn;
		// Input
		this.addSlotToContainer(new SlotItemHandler(teIn.inventory, 0, 80, 18));
		this.addSlotToContainer(new SlotItemHandler(teIn.inventory, 1, 80, 54));
		// Output
		this.addSlotToContainer(new SlotMachineOutput(teIn.inventory, 2, 134, 36));
		// RTG pellets
		this.addSlotToContainer(new SlotItemHandler(teIn.inventory, 3, 22, 18));
		this.addSlotToContainer(new SlotItemHandler(teIn.inventory, 4, 40, 18));
		this.addSlotToContainer(new SlotItemHandler(teIn.inventory, 5, 22, 36));
		this.addSlotToContainer(new SlotItemHandler(teIn.inventory, 6, 40, 36));
		this.addSlotToContainer(new SlotItemHandler(teIn.inventory, 7, 22, 54));
		this.addSlotToContainer(new SlotItemHandler(teIn.inventory, 8, 40, 54));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return bFurnace.isUseableByPlayer(player);
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
}
