package com.hbm.items.armor;

import java.util.List;

import com.hbm.capability.HbmLivingProps;
import com.hbm.handler.ArmorModHandler;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemModMedal extends ItemArmorMod {
	private float minusRads;

	public ItemModMedal(String s, float minusRads) {
		super(ArmorModHandler.extra, false, true, false, false, s);
		this.minusRads = minusRads;

	}
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn){
		list.add(TextFormatting.GOLD + "-"+minusRads*20+" RAD/s");
		super.addInformation(stack, worldIn, list, flagIn);
	}

	@Override
	public void addDesc(List<String> list, ItemStack stack, ItemStack armor) {
		list.add(TextFormatting.GOLD + "  " + stack.getDisplayName() + " (-"+minusRads*20+" RAD/s)");
	}
	
	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		if(!entity.world.isRemote) {
			float rad = HbmLivingProps.getRadiation(entity);
			rad -= minusRads;
			HbmLivingProps.setRadiation(entity, Math.max(rad, 0));
		}
	}
}
