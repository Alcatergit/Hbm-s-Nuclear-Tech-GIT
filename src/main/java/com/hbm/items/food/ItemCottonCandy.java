package com.hbm.items.food;

import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemCottonCandy extends ItemFood {

	public ItemCottonCandy(int amount, boolean isWolfFood, String s) {
		super(amount, isWolfFood);
		this.setAlwaysEdible();
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if (!worldIn.isRemote)
        {
			player.addPotionEffect(new PotionEffect(MobEffects.POISON, 15 * 20, 0));
			player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 5 * 20, 0));
			player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 25 * 20, 2));
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 25 * 20, 2));
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 30 * 20, 4));
        }
	}
	
}
