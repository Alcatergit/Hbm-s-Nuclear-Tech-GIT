package com.leafia.contents.machines.powercores.dfc.exchanger;

import com.leafia.dev.container_utility.LeafiaItemTransferable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class DFCExchangerContainer extends LeafiaItemTransferable {
	public final DFCExchangerTE te;
	public DFCExchangerContainer(EntityPlayer player,DFCExchangerTE te) {
		this.te = te;
		InventoryPlayer invPlayer = player.inventory;
		te.listeners.add(player);
		this.addSlotToContainer(new SlotItemHandler(te.inventory, 0, 62, 67));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++)
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 102 + i * 18));
		}
		for(int i = 0; i < 9; i++)
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 160));
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		te.listeners.remove(playerIn);
		super.onContainerClosed(playerIn);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return te.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn,int index) {
		LeafiaItemTransfer transfer = new LeafiaItemTransfer(1)._selected(index);
		return transfer.__forSlots(0,9999)
				.__tryMoveToInventory(true)

				.__forInventory()
				.__tryMoveToSlot(0,transfer.__maxIndex,false)

				.__getReturn();
	}
}
