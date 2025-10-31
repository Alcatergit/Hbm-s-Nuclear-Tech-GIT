package com.hbm.items.food;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTemFlakes extends ItemFood {

	public ItemTemFlakes(int amount, float saturation, boolean isWolfFood, String s) {
		super(amount, saturation, isWolfFood);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setAlwaysEdible();
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		player.heal(2.0F);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(stack.getItem() == ModItems.tem_flakes)
		{
			tooltip.add("Heals 2HP DISCOUNT FOOD OF TEM!!!");
		}
		if(stack.getItem() == ModItems.tem_flakes1)
		{
			tooltip.add("Heals 2HP food of tem");
		}
		if(stack.getItem() == ModItems.tem_flakes2)
		{
			tooltip.add("Heals food of tem (expensiv)");
		}
	}
}
