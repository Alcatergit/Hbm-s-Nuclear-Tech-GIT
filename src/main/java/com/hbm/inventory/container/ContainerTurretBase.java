package com.hbm.inventory.container;

import com.hbm.items.ModItems;
import com.hbm.tileentity.turret.TileEntityTurretBaseNT;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerTurretBase extends Container {

	private TileEntityTurretBaseNT turret;

	public ContainerTurretBase(InventoryPlayer invPlayer, TileEntityTurretBaseNT te) {
		turret = te;
		
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 0, 98, 27));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				this.addSlotToContainer(new SlotItemHandler(te.inventory, 1 + i * 3 + j, 80 + j * 18, 63 + i * 18));
			}
		}
		
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 10, 152, 99));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + (18 * 3) + 2));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + (18 * 3) + 2));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = ItemStack.EMPTY;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= turret.inventory.getSlots() - 1) {
				if(!this.mergeItemStack(var5, turret.inventory.getSlots(), this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if(var5.getItem() == ModItems.turret_chip) {
				
				if(!this.mergeItemStack(var5, 0, 1, false))
					return ItemStack.EMPTY;
				
			} else if(!this.mergeItemStack(var5, 1, turret.inventory.getSlots(), false)) {
				return ItemStack.EMPTY;
			}

			if(var5.isEmpty()) {
				var4.putStack(ItemStack.EMPTY);
			} else {
				var4.onSlotChanged();
			}

			var4.onTake(p_82846_1_, var5);
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return turret.isUseableByPlayer(player);
	}
}