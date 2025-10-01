package com.hbm.inventory.container;

import com.hbm.tileentity.network.TileEntityCraneInserter;
import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

@ChestContainer(rowSize = 7) //Inventory-Tweaks
public class ContainerCraneInserter extends Container {
    protected TileEntityCraneInserter inserter;
    public static int machineSlots = 21;

    public ContainerCraneInserter(InventoryPlayer invPlayer, TileEntityCraneInserter inserter) {
        this.inserter = inserter;

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 7; j++) {
                this.addSlotToContainer(new SlotItemHandler(inserter.inventory, j + i * 7, 26 + j * 18, 17 + i * 18));
            }
        }

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 103 + i * 18));
            }
        }

        for(int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 161));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2)
    {
        ItemStack var3 = ItemStack.EMPTY;
        Slot var4 = this.inventorySlots.get(par2);

        if (var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if (par2 < machineSlots) {//From machine to player
                if (!this.mergeItemStack(var5, machineSlots, this.inventorySlots.size(), false)) {
                    return ItemStack.EMPTY;
                }
                //From player to machine
            } else if (!this.mergeItemStack(var5, 0, machineSlots, false)) {
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
        return inserter.isUseableByPlayer(player);
    }
}
