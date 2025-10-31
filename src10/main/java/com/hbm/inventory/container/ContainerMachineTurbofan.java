package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineTurbofan;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMachineTurbofan extends Container {
	
	private TileEntityMachineTurbofan diFurnace;
	private int afterburner;
	
	public ContainerMachineTurbofan(InventoryPlayer invPlayer, TileEntityMachineTurbofan tedf) {
		afterburner = 0;
		
		diFurnace = tedf;
		
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 17, 17));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 1, 17, 53));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 98, 71));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3, 143, 71));
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 121 + i * 18));
			}
		}
		
		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 179));
		}
	}
	
	@Override
	public void addListener(IContainerListener crafting) {
		super.addListener(crafting);
		crafting.sendWindowProperty(this, 1, this.diFurnace.afterburner);
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
			
            if (par2 <= 2) {
				if (!this.mergeItemStack(var5, 3, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 1, false))
			{
				if (!this.mergeItemStack(var5, 2, 3, false))
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
			
			if(this.afterburner != this.diFurnace.afterburner)
			{
				par1.sendWindowProperty(this, 1, this.diFurnace.afterburner);
			}
		}
		
		this.afterburner = this.diFurnace.afterburner;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 1)
		{
			diFurnace.afterburner = j;
		}
	}

}
