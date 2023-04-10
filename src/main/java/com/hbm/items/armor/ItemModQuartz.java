package com.hbm.items.armor;

import java.util.List;

import com.hbm.capability.HbmLivingProps;
import com.hbm.handler.ArmorModHandler;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemModQuartz extends ItemArmorMod {

	public ItemModQuartz(String s) {
		super(ArmorModHandler.extra, true, true, true, true, s);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn){
		list.add(TextFormatting.DARK_GRAY + "Taking damage removes 10 RAD");
		list.add("Dropped by 1:64 Smelted Uranium Ingots");
		list.add("");
		super.addInformation(stack, worldIn, list, flagIn);
	}
	
	@Override
	public void addDesc(List<String> list, ItemStack stack, ItemStack armor) {
		list.add(TextFormatting.DARK_GRAY + "  " + stack.getDisplayName() + " (-10 RAD when hit)");
	}
	
	@Override
	public void modDamage(LivingHurtEvent event, ItemStack armor) {
		
		if(!event.getEntityLiving().world.isRemote) {
			HbmLivingProps.incrementRadiation(event.getEntityLiving(), -10);
		}
	}
}
