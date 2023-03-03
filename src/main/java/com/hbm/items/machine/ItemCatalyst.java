package com.hbm.items.machine;

import java.util.List;

import com.hbm.lib.Library;
import com.hbm.items.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCatalyst extends Item {

	int color;
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
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	public ItemCatalyst(int color, long powerAbs, float powerMod, float heatMod, float fuelMod, String s) {
		this.color = color;
		this.powerAbs = powerAbs;
		this.powerMod = powerMod;
		this.heatMod = heatMod;
		this.fuelMod = fuelMod;
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	public int getColor() {
		return this.color;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("Absolute Energy Bonus: " + (powerAbs >= 0 ? "§a+" : "§c") + Library.getShortNumber(powerAbs) + "HE");
		tooltip.add("Energy Modifier:           " + (powerMod >= 1 ? "§a+" : "§c") + (Math.round(powerMod * 1000) * .10 - 100) + "%");
		tooltip.add("Heat Modifier:               " + (heatMod > 1 ? "§c+" : "§a") + (Math.round(heatMod * 1000) * .10 - 100) + "%");
		tooltip.add("Fuel Modifier:               " + (fuelMod > 1 ? "§c+" : "§a") + (Math.round(fuelMod * 1000) * .10 - 100) + "%");
	}
	
	public static long getPowerAbs(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof ItemCatalyst))
			return 0;
		return ((ItemCatalyst)stack.getItem()).powerAbs;
	}
	
	public static float getPowerMod(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof ItemCatalyst))
			return 1F;
		return ((ItemCatalyst)stack.getItem()).powerMod;
	}
	
	public static float getHeatMod(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof ItemCatalyst))
			return 1F;
		return ((ItemCatalyst)stack.getItem()).heatMod;
	}
	
	public static float getFuelMod(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof ItemCatalyst))
			return 1F;
		return ((ItemCatalyst)stack.getItem()).fuelMod;
	}
}
