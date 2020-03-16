package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityMachineMiningDrill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMachineMiningDrill extends Container {

	private TileEntityMachineMiningDrill nukeBoy;

	private int warning;

	public ContainerMachineMiningDrill(InventoryPlayer invPlayer, TileEntityMachineMiningDrill tedf) {

		nukeBoy = tedf;

		// Battery
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 44, 53));
		// Outputs
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 80, 17));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 98, 17));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3, 116, 17));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4, 80, 35));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 5, 98, 35));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 6, 116, 35));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 7, 80, 53));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 8, 98, 53));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 9, 116, 53));
		// Upgrades
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 10, 152, 17));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 11, 152, 35));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 12, 152, 53));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = ItemStack.EMPTY;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= 12) {
				if(!this.mergeItemStack(var5, 13, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if(!this.mergeItemStack(var5, 0, 13, false)) {
				return ItemStack.EMPTY;
			}

			if(var5.isEmpty()) {
				var4.putStack(ItemStack.EMPTY);
			} else {
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
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendWindowProperty(this, 1, this.nukeBoy.warning);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for(int i = 0; i < this.listeners.size(); i++) {
			IContainerListener par1 = (IContainerListener) this.listeners.get(i);

			if(this.warning != this.nukeBoy.warning) {
				par1.sendWindowProperty(this, 1, this.nukeBoy.warning);
			}
		}

		this.warning = this.nukeBoy.warning;
	}

	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 1) {
			nukeBoy.warning = j;
		}
	}
}
