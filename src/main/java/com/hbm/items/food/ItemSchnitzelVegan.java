package com.hbm.items.food;

import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemSchnitzelVegan extends ItemFood {

	public ItemSchnitzelVegan(int amount, boolean isWolfFood, String s) {
		super(amount, isWolfFood);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if (!worldIn.isRemote)
        {
        	player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 10 * 20, 0));
        	player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 30 * 20, 0));
        	player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 3 * 60 * 20, 4));
        	player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 3 * 20, 0));
        	
        	player.setFire(5 * 20);
        	player.motionY = 2;
        }
	}
}
