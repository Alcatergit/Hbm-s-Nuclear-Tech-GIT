package com.hbm.items.tool;

import java.util.List;

import com.hbm.items.ModItems;

import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemKeyPin extends Item {

	public ItemKeyPin(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(getPins(stack) != 0)
			tooltip.add(I18nUtil.resolveKey("desc.keypin1", getPins(stack)));
		else
			tooltip.add(I18nUtil.resolveKey("desc.keypin2"));
		
		if(this == ModItems.key_fake) {

			tooltip.add("");
			tooltip.add(I18nUtil.resolveKey("desc.keypin3"));
		}
	}
	
	public static int getPins(ItemStack stack) {
		if(stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
			return 0;
		}
		return stack.getTagCompound().getInteger("pins");
	}
	
	public static void setPins(ItemStack stack, int i) {
		if(stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setInteger("pins", i);
	}
	
	public boolean canTransfer() {
		return this != ModItems.key_fake;
	}
}
