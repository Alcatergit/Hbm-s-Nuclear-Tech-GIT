package com.hbm.items.food;

import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.explosion.ExplosionParticle;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWaffle extends ItemFood {

	public ItemWaffle(int amount, boolean isWolfFood, String s) {
		super(amount, isWolfFood);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		worldIn.spawnEntity(EntityNukeExplosionMK4.statFac(worldIn, (int)(MainRegistry.fatmanRadius * 1.5), player.posX, player.posY, player.posZ));
    	
    	ExplosionParticle.spawnMush(worldIn, (int)player.posX, (int)player.posY - 3, (int)player.posZ);
	}
	
}
