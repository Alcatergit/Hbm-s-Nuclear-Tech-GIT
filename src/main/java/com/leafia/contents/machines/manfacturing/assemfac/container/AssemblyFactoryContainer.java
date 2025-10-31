package com.leafia.contents.machines.manfacturing.assemfac.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.inventory.SlotUpgrade;
import com.leafia.contents.machines.manfacturing.assemfac.AssemblyFactoryTE;
import com.leafia.dev.container_utility.LeafiaItemTransferable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class AssemblyFactoryContainer extends LeafiaItemTransferable {
	AssemblyFactoryTE te;
	public AssemblyFactoryContainer(InventoryPlayer playerInv,AssemblyFactoryTE tile) {
		tile.listeners.add(playerInv.player);
		te = tile;
		//Battery
		this.addSlotToContainer(new SlotItemHandler(tile.inventory, 0, 234, 218));

		for(int i = 0; i < 4; i++) {
			this.addSlotToContainer(new SlotUpgrade(tile.inventory, 1 + i, 5, 172 + i * 18));
		}

		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 2; j++) {
				int offX = 7 + j * 118;
				int offY = 14 + i * 38;
				int startIndex = 5 + (i * 2 + j) * 14;

				for(int k = 0; k < 2; k++) {
					for(int l = 0; l < 6; l++) {
						this.addSlotToContainer(new SlotItemHandler(tile.inventory, startIndex + k * 6 + l, offX + l * 16, offY + k * 16));
					}
				}
			}
		}

		for(int i = 0; i < 8; i++) {
			this.addSlotToContainer(new SlotItemHandler(tile.inventory, 17 + i * 14, 106, 13 + i * 19 - (i % 2 == 1 ? 1 : 0)));
			this.addSlotToContainer(new SlotItemHandler(tile.inventory, 18 + i * 14, 234, 13 + i * 16));
		}

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 34 + j * 18, 174 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(playerInv, i, 34 + i * 18, 232));
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
	public ItemStack transferStackInSlot(EntityPlayer player,int clickIndex)
	{
		LeafiaItemTransfer transfer = new LeafiaItemTransfer(14 * 8 + 4 + 1)._selected(clickIndex);
		return transfer.__forSlots(0,9999)
				.__tryMoveToInventory(true)

				.__forInventory()
				.__tryMoveToSlot(0,transfer.__maxIndex,false)

				.__getReturn();
	}
}
