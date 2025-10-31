package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityDiFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerDiFurnace extends Container {
	private TileEntityDiFurnace diFurnace;
	private int dualCookTime;
	private int dualPower;
	public ContainerDiFurnace(InventoryPlayer invPlayer, TileEntityDiFurnace tedf) {
		dualCookTime = 0;
		dualPower = 0;
		diFurnace = tedf;
		
		//Inputs
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 80, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 80, 54));
		//Fuel
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 8, 36));
		//Output
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 3, 134, 36));
		
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
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendWindowProperty(this, 0, this.diFurnace.dualCookTime);
		listener.sendWindowProperty(this, 1, this.diFurnace.dualPower);
		/**=====We are entering the magic realm of broken shit.=====**/
	}
	
	//What is this!?
	//Drillgon200: ^ Literally wrote basically the same comment 10 seconds ago.
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

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return diFurnace.isUsableByPlayer(player);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); i++)
		{
			IContainerListener par1 = (IContainerListener)this.listeners.get(i);
			
			if(this.dualCookTime != this.diFurnace.dualCookTime)
			{
				par1.sendWindowProperty(this, 0, this.diFurnace.dualCookTime);
			}
			
			if(this.dualPower != this.diFurnace.dualPower)
			{
				par1.sendWindowProperty(this, 1, this.diFurnace.dualPower);
			}
		}
		
		this.dualCookTime = this.diFurnace.dualCookTime;
		this.dualPower = this.diFurnace.dualPower;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			diFurnace.dualCookTime = j;
		}
		if(i == 1)
		{
			diFurnace.dualPower = j;
		}
	}
}
