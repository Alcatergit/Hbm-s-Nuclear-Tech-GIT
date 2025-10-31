package com.leafia.contents.machines.processing.mixingvat.container;

import com.hbm.inventory.SlotMachineOutput;
import com.leafia.contents.machines.processing.mixingvat.MixingVatTE;
import com.leafia.dev.container_utility.LeafiaItemTransferable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class MixingVatContainer extends LeafiaItemTransferable {

	private MixingVatTE vat;
	
	public MixingVatContainer(InventoryPlayer invPlayer,MixingVatTE vat) {

		this.vat = vat;

		//Battery
		this.addSlotToContainer(new SlotItemHandler(vat.inventory,0,186,55));

		// Upgrades
		this.addSlotToContainer(new SlotItemHandler(vat.inventory,1,186,86));
		this.addSlotToContainer(new SlotItemHandler(vat.inventory,2,186,104));

		if (!vat.nuclearMode) {
		} else {
			//Fluid IO
			this.addSlotToContainer(new SlotItemHandler(vat.inventory,13,44,17));
			this.addSlotToContainer(new SlotMachineOutput(vat.inventory,14,44,53));
			for (int x = 0; x <= 2; x++) {
				for (int y = 0; y <= 1; y++)
					this.addSlotToContainer(new SlotItemHandler(vat.inventory,15+x+y*3,71+x*18,17+y*18));
			}
		}

		// Inventory
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++)
				this.addSlotToContainer(new Slot(invPlayer,j+i*9+9,8+j*18,121+i*18));
		}
		for (int i = 0; i < 9; i++)
			this.addSlotToContainer(new Slot(invPlayer,i,8+i*18,179));
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int clickIndex)
    {
	    LeafiaItemTransfer transfer = new LeafiaItemTransfer(3+10+8)._selected(clickIndex);
	    transfer.__forSlots(0,9999)
			    .__tryMoveToInventory(true)

			    .__forInventory()

			    .__tryMoveToSlot(0,2,false);
		if (!vat.nuclearMode)
			return transfer.__tryMoveToSlot(3,12,false)
					.__getReturn();
		else
			return transfer.__tryMoveToSlot(13,transfer.__maxIndex,false)
					.__getReturn();
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return vat.isUseableByPlayer(player);
	}
}
