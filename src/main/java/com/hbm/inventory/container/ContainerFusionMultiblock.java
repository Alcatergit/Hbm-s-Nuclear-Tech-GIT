package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityFusionMultiblock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFusionMultiblock extends Container {
	
	private TileEntityFusionMultiblock diFurnace;
	
	private boolean isRunning;
	
	public ContainerFusionMultiblock(InventoryPlayer invPlayer, TileEntityFusionMultiblock tedf) {
		
		diFurnace = tedf;
		
		//Water Input
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 8, 108 - 18));
		//Battery
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 26, 108 - 18));
		//Deut Input
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 134, 108 - 18));
		//Trit Input
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3, 152, 108 - 18));
		//Startup
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4, 53, 45));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 5, 107, 45));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 6, 53, 81));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 7, 107, 81));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 8, 80, 63));
		//Water Output
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 9, 8, 108));
		//Deut Output
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 10, 134, 108));
		//Trit Output
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 11, 152, 108));
		
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
			
            if (par2 <= 11) {
				if (!this.mergeItemStack(var5, 12, this.inventorySlots.size(), true))
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
				//diFurnace.emptyPlasma();
			} else {
				//diFurnace.fillPlasma();
			}
		}
	}
}