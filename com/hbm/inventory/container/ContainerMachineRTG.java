package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityMachineRTG;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMachineRTG extends Container {

	private TileEntityMachineRTG testNuke;
	private int heat;
	
	public ContainerMachineRTG(InventoryPlayer invPlayer, TileEntityMachineRTG tedf) {
		heat = 0;
		
		testNuke = tedf;
		
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 26, 17));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 44, 17));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 62, 17));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3, 80, 17));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4, 98, 17));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 5, 26, 35));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 6, 44, 35));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 7, 62, 35));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 8, 80, 35));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 9, 98, 35));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 10, 26, 53));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 11, 44, 53));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 12, 62, 53));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 13, 80, 53));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 14, 98, 53));
		
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
		listener.sendWindowProperty(this, 0, testNuke.heat);
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
			
            if (par2 <= 14) {
				if (!this.mergeItemStack(var5, 15, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 15, false))
			{
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
		return testNuke.isUseableByPlayer(player);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); i++)
		{
			IContainerListener par1 = (IContainerListener)this.listeners.get(i);

			if(this.heat != this.testNuke.heat)
			{
				par1.sendWindowProperty(this, 0, this.testNuke.heat);
			}
		}

		this.heat = this.testNuke.heat;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			testNuke.heat = j;
		}
	}
}
