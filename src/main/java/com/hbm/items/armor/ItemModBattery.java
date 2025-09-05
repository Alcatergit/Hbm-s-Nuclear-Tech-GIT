package com.hbm.items.armor;

import com.hbm.handler.ArmorModHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemModBattery extends ItemArmorMod {

    public double mod;

    public ItemModBattery(double mod, String s) {
        super(ArmorModHandler.battery, true, true, true, true, s);
        this.mod = mod;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn){

        list.add("§bArmor Battery Multiplier: "+mod+"x");

        super.addInformation(stack, worldIn, list, flagIn);
    }
}
