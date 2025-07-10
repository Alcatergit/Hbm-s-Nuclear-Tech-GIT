package com.hbm.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface ISatChip {

    static int getFreqS(ItemStack stack) {
        if(stack != null && stack.getItem() instanceof ISatChip) {
            return ((ISatChip) stack.getItem()).getFreq(stack);
        }

        return 0;
    }

    static void setFreqS(ItemStack stack, int freq) {
        if(stack != null && stack.getItem() instanceof ISatChip) {
            ((ISatChip) stack.getItem()).setFreq(stack, freq);
        }
    }

    default int getFreq(ItemStack stack) {
        if(stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
            return 0;
        }
        return stack.getTagCompound().getInteger("freq");
    }

    default void setFreq(ItemStack stack, int freq) {
        if(stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound().setInteger("freq", freq);
    }
}
