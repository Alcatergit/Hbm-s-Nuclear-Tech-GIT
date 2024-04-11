package com.hbm.inventory.container;

import com.hbm.inventory.SlotPattern;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.tileentity.network.TileEntityCraneGrabber;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
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
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
        if (slotId < 0 || slotId >= 9) {
            return super.slotClick(slotId, dragType, clickTypeIn, player);
        }

        Slot slot = this.inventorySlots.get(slotId);

        ItemStack ret = ItemStack.EMPTY;
        ItemStack held = player.inventory.getItemStack();

        if (slot.getHasStack()) {
            ret = slot.getStack().copy();
        }

        if (clickTypeIn == ClickType.PICKUP && dragType == 1 && slot.getHasStack()) {
            grabber.nextMode(slotId);
            return ret;
        } else {
            slot.putStack(held.isEmpty() ? ItemStack.EMPTY : held.copy());

            if (slot.getHasStack()) {
                slot.getStack().setCount(1);
            }

            slot.onSlotChanged();
            grabber.initPattern(slot.getStack(), slotId);

            return ret;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return grabber.isUseableByPlayer(player);
    }

}
