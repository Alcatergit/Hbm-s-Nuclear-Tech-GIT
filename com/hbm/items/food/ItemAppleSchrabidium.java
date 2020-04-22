package com.hbm.items.food;

import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemAppleSchrabidium extends ItemFood {

	public ItemAppleSchrabidium(int amount, float saturation, boolean isWolfFood, String s) {
		super(amount, saturation, isWolfFood);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
        this.setAlwaysEdible();
        
        ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return stack.getItem() == ModItems.apple_schrabidium2 || stack.getItem() == ModItems.apple_lead2;
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			
			if(stack.getItem() == ModItems.apple_schrabidium){
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600, 4));
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 6000, 0));
				player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 6000, 0));
			} else if(stack.getItem() == ModItems.apple_schrabidium1){
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 1200, 4));
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 1200, 4));
				player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 1200, 0));
				player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 1200, 4));
				player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 1200, 2));
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1200, 2));
				player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 1200, 4));
				player.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 1200, 9));
				player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 1200, 4));
				player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 1200, 9));
			} else if(stack.getItem() == ModItems.apple_schrabidium2){
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 2147483647, 4));
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 2147483647, 1));
				player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 2147483647, 0));
				player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 2147483647, 9));
				player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 2147483647, 4));
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 2147483647, 3));
				player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 2147483647, 4));
				player.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 2147483647, 24));
				player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2147483647, 14));
				player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 2147483647, 99));
			} else if(stack.getItem() == ModItems.apple_lead){
				player.addPotionEffect(new PotionEffect(HbmPotion.lead, 15 * 20, 2));
			} else if(stack.getItem() == ModItems.apple_lead1){
				player.addPotionEffect(new PotionEffect(HbmPotion.lead, 60 * 20, 4));
			} else if(stack.getItem() == ModItems.apple_lead2){
				player.attackEntityFrom(ModDamageSource.lead, 500F);
			}
			
		}
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		if(stack.getItem() == ModItems.apple_schrabidium || stack.getItem() == ModItems.apple_lead)
    	{
    		return EnumRarity.UNCOMMON;
    	}
    	
    	if(stack.getItem() == ModItems.apple_schrabidium1 || stack.getItem() == ModItems.apple_lead1)
    	{
    		return EnumRarity.RARE;
    	}
    	
    	if(stack.getItem() == ModItems.apple_schrabidium2 || stack.getItem() == ModItems.apple_lead2)
    	{
    		return EnumRarity.EPIC;
    	}
    	
		return EnumRarity.COMMON;
	}
	
}
