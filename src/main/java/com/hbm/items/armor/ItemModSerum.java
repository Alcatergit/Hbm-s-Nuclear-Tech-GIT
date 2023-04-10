package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemModSerum extends ItemArmorMod {

	public ItemModSerum(String s) {
		super(ArmorModHandler.extra, true, true, true, true, s);
	}
	
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn){
		list.add(TextFormatting.GREEN + "Cures poison and gives strength");
		list.add("Dropped by 1:100 Cave Spiders");
		list.add("");
		super.addInformation(stack, worldIn, list, flagIn);
	}
	
	@Override
	public void addDesc(List<String> list, ItemStack stack, ItemStack armor) {
		list.add(TextFormatting.BLUE + "  " + stack.getDisplayName() + " (replaces poison with strength)");
	}
	
	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		if(!entity.world.isRemote && entity.isPotionActive(MobEffects.POISON)) {
			entity.removePotionEffect(MobEffects.POISON);
			entity.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 100, 4));
		}
	}
}
