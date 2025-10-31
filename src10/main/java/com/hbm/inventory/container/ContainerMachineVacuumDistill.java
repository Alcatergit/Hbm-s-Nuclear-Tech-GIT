package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.oil.TileEntityMachineVacuumDistill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMachineVacuumDistill extends Container {

    private TileEntityMachineVacuumDistill distill;

    public ContainerMachineVacuumDistill(InventoryPlayer invPlayer, TileEntityMachineVacuumDistill tedf) {

        distill = tedf;

        //Battery
        this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 26, 90));
        //Battery Output
        this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 26, 108));
        //Canister Input
        this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 44, 90));
        //Canister Output
        this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 3, 44, 108));
        //Heavy Oil Input
        this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4, 80, 90));
        //Heavy Oil Output
        this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 5, 80, 108));
        //Nahptha Input
        this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 6, 98, 90));
        //Nahptha Output
        this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 7, 98, 108));
        //Light Oil Input
        this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 8, 116, 90));
        //Light Oil Output
        this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 9, 116, 108));
        //Petroleum Input
        this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 10, 134, 90));
        //Petroleum Output
        this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 11, 134, 108));


        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 156 + i * 18));
            }
        }

        for(int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 214));
        }
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2)
    {
        ItemStack var3 = ItemStack.EMPTY;
        Slot var4 = (Slot) this.inventorySlots.get(par2);

        if (var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if (par2 <= 11) {
                if (!this.mergeItemStack(var5, 11, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            } else {
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
        return distill.isUseableByPlayer(player);
    }
}
