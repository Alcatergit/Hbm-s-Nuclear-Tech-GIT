package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.items.tool.ItemAssemblyTemplate;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMachineAssembler extends Container {

private TileEntityMachineAssembler nukeBoy;

	private int progress;
	private int maxProgress;
	
	public ContainerMachineAssembler(InventoryPlayer invPlayer, TileEntityMachineAssembler tedf) {
		progress = 0;
		nukeBoy = tedf;

		//Battery
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 80, 18));
		//Upgrades
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 152, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 152, 36));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3, 152, 54));
		//Schematic
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4, 80, 54){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack != null && stack.getItem() instanceof ItemAssemblyTemplate;
			};
		});
		//Output
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf.inventory, 5, 134, 90));
		//Input
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 6, 8, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 7, 26, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 8, 8, 36));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 9, 26, 36));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 10, 8, 54));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 11, 26, 54));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 12, 8, 72));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 13, 26, 72));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 14, 8, 90));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 15, 26, 90));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 16, 8, 108));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 17, 26, 108));
		
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
			
            if (par2 <= 17) {
				if (!this.mergeItemStack(var5, 18, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(var5, 6, 18, false))
				if (!this.mergeItemStack(var5, 0, 4, false))
					return ItemStack.EMPTY;
			
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
		return nukeBoy.isUseableByPlayer(player);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); i++)
		{
			IContainerListener par1 = (IContainerListener)this.listeners.get(i);
			
			if(this.progress != this.nukeBoy.progress)
			{
				par1.sendWindowProperty(this, 1, this.nukeBoy.progress);
			}
			
			if(this.maxProgress != this.nukeBoy.maxProgress)
			{
				par1.sendWindowProperty(this, 2, this.nukeBoy.maxProgress);
			}
		}

		this.progress= this.nukeBoy.progress;
		this.maxProgress= this.nukeBoy.maxProgress;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 1)
		{
			nukeBoy.progress = j;
		}
		if(i == 2)
		{
			nukeBoy.maxProgress = j;
		}
	}
}
