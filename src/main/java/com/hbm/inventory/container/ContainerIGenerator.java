package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineIGenerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerIGenerator extends Container {
	
	private TileEntityMachineIGenerator igen;
	
	public ContainerIGenerator(InventoryPlayer invPlayer, TileEntityMachineIGenerator te) {
		
		igen = te;
		
		//Solid Fuel
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 0, 5, 27));
		//RTG In
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 1, 41, 63));
		//RTG Out
		this.addSlotToContainer(new SlotMachineOutput(te.inventory, 2, 41, 99));
		//Thermo Slots
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 3, 68, 36));
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 4, 86, 36));
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 5, 104, 36));
		//Battery
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 6, 86, 108));
		//Water In
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 7, 131, 27));
		//Water Out
		this.addSlotToContainer(new SlotMachineOutput(te.inventory, 8, 167, 27));
		//Fuel In
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 9, 131, 63));
		//Fuel Out
		this.addSlotToContainer(new SlotMachineOutput(te.inventory, 10, 167, 63));
		//Lube In
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 11, 131, 85));
		//Lube Out
		this.addSlotToContainer(new SlotMachineOutput(te.inventory, 12, 167, 85));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 14 + j * 18, 84 + i * 18 + 56));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 14 + i * 18, 142 + 56));
		}
	}
	
	//TODO: use smart shift click magic
	@Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
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
		return igen.isUseableByPlayer(player);
	}
}
