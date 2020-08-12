	package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachinePress;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMachinePress extends Container {

	private TileEntityMachinePress nukeBoy;

	private int power;
	private int progress;
	private int burnTime;
	private int maxBurn;
	
	public ContainerMachinePress(InventoryPlayer invPlayer, TileEntityMachinePress tedf) {
		
		power = 0;
		progress = 0;
		burnTime = 0;
		maxBurn = 0;
		
		nukeBoy = tedf;

		//Coal
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 26, 53));
		//Stamp
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 80, 17));
		//Input
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 80, 53));
		//Output
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 3, 140, 35));
		
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
	public void addListener(IContainerListener crafting) {
		super.addListener(crafting);
		crafting.sendWindowProperty(this, 0, this.nukeBoy.power);
		crafting.sendWindowProperty(this, 1, this.nukeBoy.progress);
		crafting.sendWindowProperty(this, 2, this.nukeBoy.burnTime);
		crafting.sendWindowProperty(this, 3, this.nukeBoy.maxBurn);
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
			else if (!this.mergeItemStack(var5, 2, 3, false))
				if (!this.mergeItemStack(var5, 0, 1, false))
					if (!this.mergeItemStack(var5, 1, 2, false))
						return ItemStack.EMPTY;
			
			if (var5.getCount() == 0)
			{
				var4.putStack((ItemStack) ItemStack.EMPTY);
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
		return nukeBoy.isUsableByPlayer(player);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); i++)
		{
			IContainerListener par1 = (IContainerListener)this.listeners.get(i);
			
			if(this.power != this.nukeBoy.power)
			{
				par1.sendWindowProperty(this, 0, nukeBoy.power);
			}
			
			if(this.progress != this.nukeBoy.progress)
			{
				par1.sendWindowProperty(this, 1, this.nukeBoy.progress);
			}
			
			if(this.burnTime != this.nukeBoy.burnTime)
			{
				par1.sendWindowProperty(this, 2, this.nukeBoy.burnTime);
			}
			
			if(this.maxBurn != this.nukeBoy.maxBurn)
			{
				par1.sendWindowProperty(this, 3, this.nukeBoy.maxBurn);
			}
		}

		this.power = this.nukeBoy.power;
		this.progress = this.nukeBoy.progress;
		this.burnTime = this.nukeBoy.burnTime;
		this.maxBurn = this.nukeBoy.maxBurn;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			nukeBoy.power = j;
		}
		if(i == 1)
		{
			nukeBoy.progress = j;
		}
		if(i == 2)
		{
			nukeBoy.burnTime = j;
		}
		if(i == 3)
		{
			nukeBoy.maxBurn = j;
		}
	}

}
