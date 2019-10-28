package com.hbm.items.special;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemFuelRod extends ItemRadioactive {
	
	private int lifeTime;
	private int heat;

	public ItemFuelRod(int life, int heat, String s) {
		super(s);
		this.lifeTime = life;
		this.heat = heat;
		this.setMaxDamage(100);
		this.canRepair = false;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("Used in nuclear reactor");
		
		tooltip.add("Generates " + heat + " heat per tick");
		tooltip.add("Lasts " + lifeTime + " ticks");
	}
	
	public static void setLifetime(ItemStack stack, int time){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("life", time);
	}
	
	public static int getLifeTime(ItemStack stack){
		if(!stack.hasTagCompound()){
			stack.setTagCompound(new NBTTagCompound());
			return 0;
		}
		return stack.getTagCompound().getInteger("life");
	}
	
	public static void updateDamage(ItemStack stack){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.setItemDamage((int)((double)getLifeTime(stack) / (double)((ItemFuelRod)stack.getItem()).lifeTime * 100D));
	}

}
