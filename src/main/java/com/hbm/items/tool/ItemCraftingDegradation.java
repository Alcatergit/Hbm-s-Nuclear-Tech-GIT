package com.hbm.items.tool;

import com.hbm.items.ItemBase;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCraftingDegradation extends ItemBase {

    public ItemCraftingDegradation(String s, int durability) {
        super(s);
        this.setMaxStackSize(1);
        this.setMaxDamage(durability);
        this.setNoRepair();
        this.setCreativeTab(MainRegistry.controlTab);
    }


    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        if(this.getMaxDamage() > 0) {
            stack.setItemDamage(stack.getItemDamage() + 1);
            return stack;

        } else {
            return stack;
        }
    }

}
