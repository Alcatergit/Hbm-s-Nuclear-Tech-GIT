package com.leafia.contents.machines.elevators.gui;

import com.leafia.contents.machines.elevators.car.ElevatorEntity;
import com.leafia.dev.container_utility.LeafiaItemTransferable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class EvCabinContainer extends LeafiaItemTransferable {
	public EvCabinContainer(EntityPlayer player,ElevatorEntity entity) {
		InventoryPlayer invPlayer = player.inventory;
		this.addSlotToContainer(new SlotItemHandler(entity.inventory, 0, 30, 35));
		this.addSlotToContainer(new SlotItemHandler(entity.inventory, 1, 30-21, 35+21));
		this.addSlotToContainer(new SlotItemHandler(entity.inventory, 2, 30-21, 35-21));
		this.addSlotToContainer(new SlotItemHandler(entity.inventory, 3, 30, 35-21));
		this.addSlotToContainer(new SlotItemHandler(entity.inventory, 4, 30-21, 35));
		this.addSlotToContainer(new SlotItemHandler(entity.inventory, 5, 30, 35+21));
		this.addSlotToContainer(new SlotItemHandler(entity.inventory, 6, 30+21, 35));
		this.addSlotToContainer(new SlotItemHandler(entity.inventory, 7, 30+21, 35+21));
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 91 + i * 18));
		}

		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 149));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn,int index) {
		LeafiaItemTransfer transfer = new LeafiaItemTransfer(8)._selected(index);
		return transfer.__forSlots(0,9999)
				.__tryMoveToInventory(true)

				.__forInventory()
				.__tryMoveToSlot(0,transfer.__maxIndex,false)

				.__getReturn();
	}
}
