package com.hbm.items.gear;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WeaponSpecial extends ItemSword {

	Random rand = new Random();
	
	public WeaponSpecial(ToolMaterial material, String s) {
		super(material);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		if(this == ModItems.ullapool_caber) {
			return EnumRarity.UNCOMMON;
		}
		return EnumRarity.COMMON;
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		World world = target.world;
		if(this == ModItems.bottle_opener) {
			if (!target.world.isRemote)
        	{
				int i = rand.nextInt(7);
				if(i == 0)
					target.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 5 * 60 * 20, 0));
				if(i == 1)
					target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 5 * 60 * 20, 2));
				if(i == 2)
					target.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 5 * 60 * 20, 2));
				if(i == 3)
					target.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 1 * 60 * 20, 0));
        	}
        	target.playSound(SoundEvents.BLOCK_ANVIL_LAND, 3.0F, 1.0F);
		}
		if(this == ModItems.ullapool_caber) {
			if (!world.isRemote)
        	{
				world.createExplosion(null, target.posX, target.posY, target.posZ, 7.5F, true);
        	}
			
			stack.damageItem(505, attacker);
		}
		return false;
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		return super.getItemAttributeModifiers(equipmentSlot);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		if(this == ModItems.bottle_opener) {
			list.add("My very own bottle opener.");
			list.add("Use with caution!");
		}
		if(this == ModItems.ullapool_caber) {
			list.add("High-yield Scottish face removal.");
			list.add("A sober person would throw it...");
		}
	}

}
