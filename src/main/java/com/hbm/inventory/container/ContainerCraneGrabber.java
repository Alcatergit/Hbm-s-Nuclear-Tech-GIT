package com.hbm.inventory.container;

import com.hbm.inventory.SlotPattern;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.tileentity.network.TileEntityCraneGrabber;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCraneGrabber extends Container {
    protected TileEntityCraneGrabber grabber;

    public ContainerCraneGrabber(InventoryPlayer invPlayer, TileEntityCraneGrabber grabber) {
        this.grabber = grabber;

        //filter
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlotToContainer(new SlotPattern(grabber.inventory, j + i * 3, 40 + j * 18, 17 + i * 18));
            }
        }

        this.addSlotToContainer(new SlotUpgrade(grabber.inventory, 9, 121, 23));
        this.addSlotToContainer(new SlotUpgrade(grabber.inventory, 10, 121, 47));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 103 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 161));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        ItemStack var3 = ItemStack.EMPTY;
        Slot var4 = (Slot) this.inventorySlots.get(slot);

        if(var4 != null && var4.getHasStack()) {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if(slot < 9) { //filters
                return ItemStack.EMPTY;
            }

            if(slot <= this.inventorySlots.size() - 1) {
                if(!this.mergeItemStack(var5, this.inventorySlots.size(), this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }

            if (var5.isEmpty())
            {
                var4.putStack(ItemStack.EMPTY);
            }
            else {
                var4.onSlotChanged();
            }
        }

        return var3;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return grabber.isUseableByPlayer(player);
    }

}
