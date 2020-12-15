package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineShredder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMachineShredder extends Container {

	private TileEntityMachineShredder diFurnace;
	private int progress;
	
	public ContainerMachineShredder(InventoryPlayer invPlayer, TileEntityMachineShredder tedf) {
		
		diFurnace = tedf;
		
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 44, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 62, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 80, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3, 44, 36));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4, 62, 36));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 5, 80, 36));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 6, 44, 54));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 7, 62, 54));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 8, 80, 54));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 9, 116, 18));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 10, 134, 18));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 11, 152, 18));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 12, 116, 36));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 13, 134, 36));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 14, 152, 36));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 15, 116, 54));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 16, 134, 54));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 17, 152, 54));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 18, 116, 72));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 19, 134, 72));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 20, 152, 72));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 21, 116, 90));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 22, 134, 90));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 23, 152, 90));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 24, 116, 108));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 25, 134, 108));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 26, 152, 108));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 27, 44, 108));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 28, 80, 108));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 29, 8, 108));
		
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
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendWindowProperty(this, 1, diFurnace.progress);
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
			
            if (par2 <= 29) {
				if (!this.mergeItemStack(var5, 30, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else
			{
				if (!this.mergeItemStack(var5, 0, 9, false))
					if (!this.mergeItemStack(var5, 27, 30, false))
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
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); i++)
		{
			IContainerListener par1 = (IContainerListener)this.listeners.get(i);
			
			if(this.progress != this.diFurnace.progress)
			{
				par1.sendWindowProperty(this, 1, this.diFurnace.progress);
			}
		}

		this.progress = this.diFurnace.progress;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 1)
		{
			diFurnace.progress = j;
		}
	}
}
