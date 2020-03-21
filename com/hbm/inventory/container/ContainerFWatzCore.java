package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityFWatzCore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFWatzCore extends Container {
	
	private TileEntityFWatzCore diFurnace;
	
	private boolean isRunning;
	
	public ContainerFWatzCore(InventoryPlayer invPlayer, TileEntityFWatzCore tedf) {
		
		diFurnace = tedf;
		
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 26, 108));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 62, 90));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 98, 90));
		//Inputs
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3, 134, 108 - 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4, 152, 108 - 18));
		//Outputs
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 5, 134, 108));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 6, 152, 108));
		
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
	public void addListener(IContainerListener crafting) {
		super.addListener(crafting);
		crafting.sendWindowProperty(this, 1, isRunning ? 1 : 0);
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
			
            if (par2 <= 6) {
				if (!this.mergeItemStack(var5, 7, this.inventorySlots.size(), true))
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
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); i++)
		{
			IContainerListener par1 = (IContainerListener)this.listeners.get(i);
			
			if(this.isRunning != this.diFurnace.isRunning())
			{
				par1.sendWindowProperty(this, 1, this.diFurnace.isRunning() ? 1 : 0);
			}
		}
		
		this.isRunning = this.diFurnace.isRunning();
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 1)
		{
			if(j == 0)
			{
				diFurnace.emptyPlasma();
			} else {
				diFurnace.fillPlasma();
			}
		}
	}
}
