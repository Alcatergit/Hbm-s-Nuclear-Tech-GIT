package com.leafia.contents.machines.processing.assemtable.container;

import com.hbm.inventory.SlotMachineOutput;
import com.leafia.contents.machines.processing.assemtable.AssemTableTE;
import com.leafia.dev.container_utility.LeafiaItemTransferable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class AssemTableContainer extends LeafiaItemTransferable {

	private AssemTableTE te;

	public AssemTableContainer(InventoryPlayer invPlayer,AssemTableTE te) {
		this.te = te;
		te.listeners.add(invPlayer.player);
		for (int i = 0; i < 6; i++)
			this.addSlotToContainer(new SlotItemHandler(te.inventory,0,8+18*Math.floorMod(i,2),28+18*Math.floorDiv(i,2)));
		this.addSlotToContainer(new SlotItemHandler(te.inventory,6,65,31));
		this.addSlotToContainer(new SlotMachineOutput(te.inventory,7,102,57));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++)
				this.addSlotToContainer(new Slot(invPlayer,j+i*9+9,46+j*18,110+i*18));
		}

		for (int i = 0; i < 9; i++)
			this.addSlotToContainer(new Slot(invPlayer,i,46+i*18,168));
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		te.listeners.remove(playerIn);
		super.onContainerClosed(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int clickIndex) {
	    LeafiaItemTransfer transfer = new LeafiaItemTransfer(8)._selected(clickIndex);
	    return transfer.__forSlots(0,9999)
			    .__tryMoveToInventory(true)

			    .__forInventory()
			    .__tryMoveToSlot(0,transfer.__maxIndex,false)

			    .__getReturn();
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return te.isUseableByPlayer(player);
	}
}