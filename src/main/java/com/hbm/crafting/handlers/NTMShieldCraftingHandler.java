package com.hbm.crafting.handlers;

import com.hbm.items.gear.ModShield;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class NTMShieldCraftingHandler extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        ItemStack shield = ItemStack.EMPTY;
        ItemStack banner = ItemStack.EMPTY;

        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stackInSlot = inv.getStackInSlot(i);

            if (!stackInSlot.isEmpty()) {
                if (stackInSlot.getItem() instanceof ItemBanner) {
                    if (!banner.isEmpty()) {
                        return false;
                    }

                    banner = stackInSlot;
                } else {
                    if (!(stackInSlot.getItem() instanceof ModShield)) {
                        return false;
                    }

                    if(!shield.isEmpty()) {
                        return false;
                    }

                    if(stackInSlot.getSubCompound("BlockEntityTag") != null) {
                        return false;
                    }

                    shield = stackInSlot;
                }
            }
        }

        return !shield.isEmpty() && !banner.isEmpty();
    }

    @Override
    public @NotNull ItemStack getCraftingResult(InventoryCrafting inv)
    {
        ItemStack banner = ItemStack.EMPTY;
        ItemStack shield = ItemStack.EMPTY;

        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stackInSlot = inv.getStackInSlot(i);

            if (!stackInSlot.isEmpty()) {
                if (stackInSlot.getItem() instanceof ItemBanner) {
                    banner = stackInSlot;
                } else if (stackInSlot.getItem() instanceof ModShield) {
                    shield = stackInSlot.copy();
                }
            }
        }

        if(!shield.isEmpty()) {
            NBTTagCompound nbttagcompound = banner.getSubCompound("BlockEntityTag");
            NBTTagCompound nbttagcompound1 = nbttagcompound == null ? new NBTTagCompound() : nbttagcompound.copy();
            nbttagcompound1.setInteger("Base", banner.getMetadata() & 15);
            shield.setTagInfo("BlockEntityTag", nbttagcompound1);
        }
        return shield;
    }

    @Override
    public @NotNull ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for(int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack stackInSlot = inv.getStackInSlot(i);

            if(stackInSlot.getItem().hasContainerItem()) {
                nonnulllist.set(i, new ItemStack(Objects.requireNonNull(stackInSlot.getItem().getContainerItem())));
            }
        }

        return nonnulllist;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }
}