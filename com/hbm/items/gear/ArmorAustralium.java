package com.hbm.items.gear;

import java.util.List;
import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ArmorAustralium extends ItemArmor {

	Random rand = new Random();
	
	public ArmorAustralium(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String s) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
		if(armor.getItemDamage() < armor.getMaxDamage()) {
			if (armor.getItem() == ModItems.australium_iii) {
				if(rand.nextInt(3) == 0) {
					armor.damageItem(1, player);
				}
				if(!player.isPotionActive(MobEffects.ABSORPTION))
					player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 80, 2, false, true));
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (stack.getItem() == ModItems.australium_iii)
			tooltip.add("Ouch, that hurts.");
	}
	
	@Override
	
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if(stack.getItem().equals(ModItems.australium_iii)) {
			return (RefStrings.MODID + ":textures/armor/australium_iii.png");
		}
		return super.getArmorTexture(stack, entity, slot, type);
	}
	
}
