package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityAMSBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerAMSBase extends Container {

	private TileEntityAMSBase amsBase;

	private int heat;
	private int warning;
	private int mode;
	
	public ContainerAMSBase(InventoryPlayer invPlayer, TileEntityAMSBase tedf) {
		amsBase = tedf;

		//Cool 1 In
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 8, 18));
		//Cool 1 Out
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 8, 54));
		//Cool 2 In
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 152, 18));
		//Cool 2 Out
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3, 152, 54));
		//Fuel 1 In
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4, 8, 72));
		//Fuel 1 Out
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 5, 8, 108));
		//Fuel 2 In
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 6, 152, 72));
		//Fuel 2 Out
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 7, 152, 108));
		//Moderator
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 8, 80, 45));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 9, 62, 63));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 10, 98, 63));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 11, 80, 81));
		//Core
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 12, 80, 63));
		//Sat Chips
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 13, 62, 108));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 14, 62 + 18, 108));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 15, 62 + 36, 108));
		
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
			
            if (par2 <= 3) {
				if (!this.mergeItemStack(var5, 4, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else
				return ItemStack.EMPTY;
			
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
		return amsBase.isUseableByPlayer(player);
	}
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendWindowProperty(this, 0, this.amsBase.heat);
		listener.sendWindowProperty(this, 2, this.amsBase.warning);
		listener.sendWindowProperty(this, 4, this.amsBase.mode);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); i++)
		{
			IContainerListener par1 = (IContainerListener)this.listeners.get(i);
			
			if(this.heat != this.amsBase.heat)
			{
				par1.sendWindowProperty(this, 0, this.amsBase.heat);
			}
			
			if(this.warning != this.amsBase.warning)
			{
				par1.sendWindowProperty(this, 2, this.amsBase.warning);
			}
			
			if(this.mode != this.amsBase.mode)
			{
				par1.sendWindowProperty(this, 4, this.amsBase.mode);
			}
		}

		this.heat = this.amsBase.heat;
		this.warning = this.amsBase.warning;
		this.mode = this.amsBase.mode;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			amsBase.heat = j;
		}
		if(i == 1)
		{
			amsBase.efficiency = j;
		}
		if(i == 2)
		{
			amsBase.warning = j;
		}
		if(i == 3)
		{
			amsBase.field = j;
		}
		if(i == 4)
		{
			amsBase.mode = j;
		}
	}
}