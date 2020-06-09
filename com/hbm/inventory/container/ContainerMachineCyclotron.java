package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineCyclotron;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMachineCyclotron extends Container {

	private TileEntityMachineCyclotron testNuke;
	private int progress;
	
	public ContainerMachineCyclotron(InventoryPlayer invPlayer, TileEntityMachineCyclotron tedf) {
		progress = 0;
		
		testNuke = tedf;
		
		//Input
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 8, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 8, 36));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 8, 54));
		//Targets
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3, 80, 72));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4, 98, 72));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 5, 116, 72));
		//Tech
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 6, 8, 81));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 7, 26, 81));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 8, 44, 81));
		//Battery
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 9, 152, 108));
		//Cell
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 10, 8, 108));
		//Output
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 11, 44, 108));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 12, 62, 108));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 13, 80, 108));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 14, 98, 108));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 15, 116, 108));
		
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
		listener.sendWindowProperty(this, 0, this.testNuke.progress);
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
			
            if (par2 <= 15) {
				if (!this.mergeItemStack(var5, 16, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 16, false))
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
			IContainerListener listener = (IContainerListener)this.listeners.get(i);

			if(this.progress != this.testNuke.progress)
			{
				listener.sendWindowProperty(this, 0, this.testNuke.progress);
			}
		}

		this.progress = this.testNuke.progress;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			testNuke.progress = j;
		}
	}
}
