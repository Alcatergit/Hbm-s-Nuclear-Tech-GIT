package com.hbm.inventory.container;

import api.hbm.energy.IBatteryItem;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.SlotMachineOutput;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.inventory.SolderingRecipes;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineSolderingStation;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMachineSolderingStation extends Container {
	
	private TileEntityMachineSolderingStation solderer;

	public ContainerMachineSolderingStation(InventoryPlayer playerInv, TileEntityMachineSolderingStation tile) {
		solderer = tile;
		
		//Inputs
		for(int i = 0; i < 2; i++) for(int j = 0; j < 3; j++)
			this.addSlotToContainer(new SlotItemHandler(tile.inventory, i * 3 + j, 17 + j * 18, 18 + i * 18));
		//Output
		this.addSlotToContainer(new SlotMachineOutput(tile.inventory, 6, 107, 27));
		//Battery
		this.addSlotToContainer(new SlotItemHandler(tile.inventory, 7, 152, 72));
		//Upgrades
		this.addSlotToContainer(new SlotUpgrade(tile.inventory, 8, 89, 63));
		this.addSlotToContainer(new SlotUpgrade(tile.inventory, 9, 107, 63));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 122 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 180));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack rStack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			rStack = stack.copy();

			if(index <= 9) {
				if(!this.mergeItemStack(stack, 10, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else {
				
				if(rStack.getItem() instanceof IBatteryItem) {
					if(!this.mergeItemStack(stack, 7, 8, false)) return ItemStack.EMPTY;
				} else if(rStack.getItem() instanceof ItemMachineUpgrade ) {
					if(!this.mergeItemStack(stack, 8, 10, false)) return ItemStack.EMPTY;
				} else {
					for(AStack t : SolderingRecipes.toppings) if(t.matchesRecipe(stack, false)) if(!this.mergeItemStack(stack, 0, 3, false)) return ItemStack.EMPTY;
					for(AStack t : SolderingRecipes.pcb) if(t.matchesRecipe(stack, false)) if(!this.mergeItemStack(stack, 3, 5, false)) return ItemStack.EMPTY;
					for(AStack t : SolderingRecipes.solder) if(t.matchesRecipe(stack, false)) if(!this.mergeItemStack(stack, 5, 6, false)) return ItemStack.EMPTY;
					return ItemStack.EMPTY;
				}
			}

			if(stack.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return rStack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return solderer.isUseableByPlayer(player);
	}
}
