package com.leafia.contents.machines.manfacturing.soldering.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.inventory.SlotUpgrade;
import com.leafia.contents.machines.manfacturing.soldering.SolderingTE;
import com.leafia.dev.LeafiaDebug;
import com.leafia.dev.container_utility.LeafiaItemTransferable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class SolderingContainer extends LeafiaItemTransferable {
	SolderingTE te;
	public SolderingContainer(InventoryPlayer invPlayer,SolderingTE te) {
		te.listeners.add(invPlayer.player);
		this.te = te;
		//Inputs
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 3; j++)
				this.addSlotToContainer(new SlotItemHandler(te.inventory,i*3+j,17+j*18,18+i*18));
		//Output
		this.addSlotToContainer(new SlotMachineOutput(te.inventory,6,107,27));
		//Battery
		this.addSlotToContainer(new SlotItemHandler(te.inventory,7,152,72));
		//Upgrades
		this.addSlotToContainer(new SlotUpgrade(te.inventory,8,89,63));
		this.addSlotToContainer(new SlotUpgrade(te.inventory,9,107,63));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer,j+i*9+9,8+j*18,122+i*18));
			}
		}

		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer,i,8+i*18,180));
		}
	}
	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		te.listeners.remove(playerIn);
		super.onContainerClosed(playerIn);
	}
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player,int clickIndex) {
		LeafiaItemTransfer transfer = new LeafiaItemTransfer(10)._selected(clickIndex);
		return transfer.__forSlots(0,9999)
				.__tryMoveToInventory(true)

				.__forInventory()
				.__tryMoveToSlot(0,transfer.__maxIndex,false)

				.__getReturn();
	}
}
