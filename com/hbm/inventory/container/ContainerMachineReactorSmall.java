package com.hbm.inventory.container;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineReactorSmall;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMachineReactorSmall extends Container {

	private TileEntityMachineReactorSmall seleniumEngine;
	
	public ContainerMachineReactorSmall(InventoryPlayer invPlayer, TileEntityMachineReactorSmall tedf) {
		
		seleniumEngine = tedf;
		
		//Rods
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 98, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 134, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 80, 36));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3, 116, 36));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4, 152, 36));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 5, 98, 54));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 6, 134, 54));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 7, 80, 72));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 8, 116, 72));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 9, 152, 72));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 10, 98, 90));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 11, 134, 90));
		
		//Fluid IO
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 12, 8, 90));
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf.inventory, 13, 8, 108));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 14, 26, 90));
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf.inventory, 15, 26, 108));
		
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
			
            if (par2 <= 16) {
				if (!this.mergeItemStack(var5, 17, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			} else {
				if (!this.mergeItemStack(var5, 0, 13, true))
					if (!this.mergeItemStack(var5, 14, 15, true))
						if (!this.mergeItemStack(var5, 16, 17, true))
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
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendWindowProperty(this, 0, this.seleniumEngine.compression);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return seleniumEngine.isUseableByPlayer(player);
	}
	
	@Override
	public void updateProgressBar(int id, int data) {
		if(id == 0){
			this.seleniumEngine.compression = data;
			switch(data){
			case 0:
				this.seleniumEngine.tankTypes[2] = ModForgeFluids.steam;
				break;
			case 1:
				this.seleniumEngine.tankTypes[2] = ModForgeFluids.hotsteam;
				break;
			case 2:
				this.seleniumEngine.tankTypes[2] = ModForgeFluids.superhotsteam;
				break;
			default:
				break;
			}
		}
	}
}
