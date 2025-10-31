package com.hbm.items.machine;

import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.llib.math.LeafiaColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemCatalyst extends Item {

    int color;
    int meltingPoint;
    long powerAbs;
    float powerMod;
    float heatMod;
    float fuelMod;

    public ItemCatalyst(int color, String s) {
        this.color = color;
        this.powerAbs = 0;
        this.powerMod = 1.0F;
        this.heatMod = 1.0F;
        this.fuelMod = 1.0F;
        this.setTranslationKey(s);
        this.setRegistryName(s);

        ModItems.ALL_ITEMS.add(this);
    }

    public ItemCatalyst(int color, int meltingPoint, long powerAbs, float powerMod, float heatMod, float fuelMod, String s) {
        this.color = color;
        this.meltingPoint = meltingPoint;
        this.powerAbs = powerAbs;
        this.powerMod = powerMod;
        this.heatMod = heatMod;
        this.fuelMod = fuelMod;
        this.setTranslationKey(s);
        this.setRegistryName(s);

        ModItems.ALL_ITEMS.add(this);
    }

    public int getColor() {
        return this.color;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("§6Melting Point: §8" + meltingPoint + "°C");
        //tooltip.add("Absolute Energy Bonus: " + (powerAbs >= 0 ? "§a+" : "§c") + Library.getShortNumber(powerAbs) + "HE");
        tooltip.add("Energy Modifier:           " + (powerMod >= 1 ? "§a+" : "§c") + (Math.round(powerMod * 1000) * .10 - 100) + "%");
        tooltip.add("Heat Modifier:               " + (heatMod > 1 ? "§c+" : "§a") + (Math.round(heatMod * 1000) * .10 - 100) + "%");
        tooltip.add("Fuel Modifier:               " + (fuelMod > 1 ? "§c+" : "§a") + (Math.round(fuelMod * 1000) * .10 - 100) + "%");
        tooltip.add("");
        tooltip.add(TextFormatting.LIGHT_PURPLE + "Required to contain the deadly stellar core of DFC");
    }

    public static long getPowerAbs(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof ItemCatalyst))
            return 0;
        return ((ItemCatalyst) stack.getItem()).powerAbs;
    }

    public static float getPowerMod(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof ItemCatalyst))
            return 1F;
        return ((ItemCatalyst) stack.getItem()).powerMod;
    }

    public static float getHeatMod(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof ItemCatalyst))
            return 1F;
        return ((ItemCatalyst) stack.getItem()).heatMod;
    }

    public static float getFuelMod(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof ItemCatalyst))
            return 1F;
        return ((ItemCatalyst) stack.getItem()).fuelMod;
    }

    public static int getMelting(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof ItemCatalyst))
            return 1500000;
        return ((ItemCatalyst) stack.getItem()).meltingPoint;
    }

    NBTTagCompound getTag(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null) {
            compound = new NBTTagCompound();
            stack.setTagCompound(compound);
        }
        return compound;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true; //super.showDurabilityBar(stack);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return getTag(stack).getDouble("damage") / 100d; //super.getDurabilityForDisplay(stack);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        LeafiaColor fiac = new LeafiaColor(color);
        double damage = getTag(stack).getDouble("damage") / 100d;
        fiac = fiac.lerp(new LeafiaColor(0.1, 0.1, 0.1), damage);
        if (damage > 0.666 && Math.floorMod(System.currentTimeMillis(), 400) >= 200)
            fiac = new LeafiaColor(1, 0, 0);
        return fiac.toInARGB();
    }
}
