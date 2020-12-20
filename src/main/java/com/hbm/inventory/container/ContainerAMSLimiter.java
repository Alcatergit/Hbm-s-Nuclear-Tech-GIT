package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityAMSLimiter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerAMSLimiter extends Container {

	private TileEntityAMSLimiter amsLmiter;

	private int heat;
	private int warning;
	private int mode;
	
	public ContainerAMSLimiter(InventoryPlayer invPlayer, TileEntityAMSLimiter tedf) {
		amsLmiter = tedf;

		//Fluid In
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 44, 17));
		//Fluid Out
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 44, 53));
		//Focus
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 80, 53));
		//Battery
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3, 116, 53));
		
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
		return amsLmiter.isUseableByPlayer(player);
	}
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendWindowProperty(this, 0, this.amsLmiter.heat);
		listener.sendWindowProperty(this, 2, this.amsLmiter.warning);
		listener.sendWindowProperty(this, 3, this.amsLmiter.mode);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); i++)
		{
			IContainerListener par1 = (IContainerListener)this.listeners.get(i);
			
			if(this.heat != this.amsLmiter.heat)
			{
				par1.sendWindowProperty(this, 0, this.amsLmiter.heat);
			}
			
			if(this.warning != this.amsLmiter.warning)
			{
				par1.sendWindowProperty(this, 2, this.amsLmiter.warning);
			}
			
			if(this.mode != this.amsLmiter.mode)
			{
				par1.sendWindowProperty(this, 3, this.amsLmiter.mode);
			}
		}

		this.heat = this.amsLmiter.heat;
		this.warning = this.amsLmiter.warning;
		this.mode = this.amsLmiter.mode;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			amsLmiter.heat = j;
		}
		if(i == 2)
		{
			amsLmiter.warning = j;
		}
		if(i == 3)
		{
			amsLmiter.mode = j;
		}
	}
}