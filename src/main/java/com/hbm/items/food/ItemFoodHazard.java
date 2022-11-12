package com.hbm.items.food;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.interfaces.IItemHazard;
import com.hbm.modules.ItemHazardModule;
import com.hbm.config.BombConfig;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.explosion.ExplosionParticle;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;


import net.minecraft.item.ItemFood;

public class ItemFoodHazard extends ItemFood implements IItemHazard {

	ItemHazardModule module;

	public ItemFoodHazard(int amount, float saturation, boolean isWolfFood, String s){
		super(amount, saturation, isWolfFood);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.module = new ItemHazardModule();
		
		ModItems.ALL_ITEMS.add(this);
	}

	
	@Override
	public ItemHazardModule getModule() {
		return this.module;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entity, int itemSlot, boolean isSelected){
		if(entity instanceof EntityLivingBase)
			this.module.applyEffects((EntityLivingBase) entity, stack.getCount(), itemSlot, isSelected, ((EntityLivingBase)entity).getHeldItem(EnumHand.MAIN_HAND) == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn){
		if(this == ModItems.cotton_candy) {
            list.add("Gives you a radioactive sugarshock");
            list.add("§b[SPEED V]§r");
    	}
    	if(this == ModItems.schnitzel_vegan) {
            list.add("Wasteschnitzel is all i need.");
            list.add("§c[STRENGTH X]§r");
    	}
    	if(this == ModItems.apple_lead) {
            list.add("Lead shields radiation right? So lets eat some it");
            list.add("Might have some side effects");
            list.add("§a[RAD-X (0.5)]§r");
    	}
    	if(this == ModItems.apple_lead1) {
            list.add("Lead shields radiation right? So lets eat a lot it");
            list.add("I will survive it right? - right?");
            list.add("§a[RAD-X (1)]§r");
    	}
    	if(this == ModItems.apple_lead2) {
            list.add("Lead shields radiation right? So lets eat tons of it");
            list.add("Yeah thats leathal if not extremely healthy");
            list.add("§a[RAD-X (4)]§r");
    	}

		this.module.addInformation(stack, list, flagIn);
		super.addInformation(stack, world, list, flagIn);
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem item){
		super.onEntityItemUpdate(item);
		return super.onEntityItemUpdate(item);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if(stack.getItem() == ModItems.bomb_waffle){
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 5 * 60 * 20, 10));
			worldIn.spawnEntity(EntityNukeExplosionMK4.statFac(worldIn, (int)(BombConfig.fatmanRadius * 1.5), player.posX, player.posY, player.posZ));
	    	
	    	ExplosionParticle.spawnMush(worldIn, (int)player.posX, (int)player.posY - 3, (int)player.posZ);
		}
		if(stack.getItem() == ModItems.cotton_candy){
			player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 5 * 20, 0));
			player.addPotionEffect(new PotionEffect(MobEffects.POISON, 15 * 20, 0));
			player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 25 * 20, 2));
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 25 * 20, 5));
		}
		if(stack.getItem() == ModItems.schnitzel_vegan){
			player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 10 * 20, 0));
        	player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 3 * 20, 0));
        	player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 3 * 60 * 20, 4));
        	player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 3 * 20, 0));
        	player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 30 * 20, 10));
        	
        	player.setFire(5 * 20);
        	player.motionY = 2;
		}
		if(stack.getItem() == ModItems.apple_schrabidium){
			player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 10 * 20, 0));
        	player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 3 * 20, 0));
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600, 4));
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 600, 0));
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 600, 0));
		}
		if(stack.getItem() == ModItems.apple_schrabidium1){
			player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 10 * 20, 0));
        	player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 3 * 20, 0));
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
		}
		if(stack.getItem() == ModItems.apple_schrabidium2){
			player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 10 * 20, 0));
        	player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 3 * 20, 0));
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
		}
		if(stack.getItem() == ModItems.apple_lead){
			player.addPotionEffect(new PotionEffect(HbmPotion.radx, 15 * 60 * 20, 5));
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 15 * 20, 0));
			player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 1 * 20, 0));
			player.addPotionEffect(new PotionEffect(HbmPotion.lead, 2 * 20, 1));
			player.attackEntityFrom(ModDamageSource.lead, 1F);
		}
		if(stack.getItem() == ModItems.apple_lead1){
			player.addPotionEffect(new PotionEffect(HbmPotion.radx, 15 * 60 * 20, 10));
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 60 * 20, 0));
			player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 15 * 20, 0));
			player.addPotionEffect(new PotionEffect(HbmPotion.lead, 4 * 20, 2));
			player.attackEntityFrom(ModDamageSource.lead, 10F);
		}
		if(stack.getItem() == ModItems.apple_lead2){
			player.addPotionEffect(new PotionEffect(HbmPotion.radx, 60 * 60 * 20, 40));
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 4 * 60 * 20, 0));
			player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 30 * 20, 0));
			player.addPotionEffect(new PotionEffect(HbmPotion.lead, 8 * 20, 4));
			player.attackEntityFrom(ModDamageSource.lead, 19F);
		}
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		if(stack.getItem() == ModItems.apple_schrabidium || stack.getItem() == ModItems.apple_lead){
    		return EnumRarity.UNCOMMON;
    	}
    	
    	if(stack.getItem() == ModItems.apple_schrabidium1 || stack.getItem() == ModItems.apple_lead1){
    		return EnumRarity.RARE;
    	}
    	
    	if(stack.getItem() == ModItems.apple_schrabidium2 || stack.getItem() == ModItems.apple_lead2){
    		return EnumRarity.EPIC;
    	}
    	
		return EnumRarity.COMMON;
	}
}

