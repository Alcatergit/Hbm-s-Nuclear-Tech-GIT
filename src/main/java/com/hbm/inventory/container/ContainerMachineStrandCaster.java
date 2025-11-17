package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineStrandCaster;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ContainerMachineStrandCaster extends Container {

  protected TileEntityMachineStrandCaster caster;

  public ContainerMachineStrandCaster(
      InventoryPlayer invPlayer, TileEntityMachineStrandCaster caster) {
    this.caster = caster;

    // the wretched mold
    this.addSlotToContainer(new SlotItemHandler(caster.inventory, 0, 57, 62));

    // output
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 2; j++) {
        this.addSlotToContainer(
            new SlotMachineOutput(caster.inventory, j + i * 2 + 1, 125 + j * 18, 26 + i * 18));
      }
    }

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 9; j++) {
        this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 132 + i * 18));
      }
    }

    for (int i = 0; i < 9; i++) {
      this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 190));
    }
  }

  @Override
  public @NotNull ItemStack transferStackInSlot(@NotNull EntityPlayer player, int index) {
    ItemStack stack = ItemStack.EMPTY;
    Slot slot = this.inventorySlots.get(index);

    if (slot != null && slot.getHasStack()) {
      ItemStack originalStack = slot.getStack();
      stack = originalStack.copy();

      if (index <= 6) {
        if (!this.mergeItemStack(originalStack, 7, this.inventorySlots.size(), true)) {
          return ItemStack.EMPTY;
        }

        slot.onSlotChange(originalStack, stack);

      } else if (!this.mergeItemStack(originalStack, 1, 2, false)) {
        return ItemStack.EMPTY;
      }

      if (originalStack.isEmpty()) {
        slot.putStack(ItemStack.EMPTY);
      } else {
        slot.onSlotChanged();
      }
    }

    return stack;
  }

  @Override
  public boolean canInteractWith(@NotNull EntityPlayer player) {
    return caster.isUseableByPlayer(player);
  }
}
