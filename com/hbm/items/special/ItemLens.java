package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemLens extends Item {

	public static final long maxDamage = 60 * 60 * 60 * 20 * 100; //1 hour at 100%, 100 hours at 1%
	
	public ItemLens(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		long damage = getLensDamage(stack);
		int percent = (int) ((maxDamage - damage) * 100 / maxDamage);
		
		tooltip.add("Durability: " + (maxDamage - damage) + "/" + maxDamage + " (" + percent + "%)");
	}
	
	@Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return (double)getLensDamage(stack) / (double)maxDamage;
    }
    
    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }
	
	public static long getLensDamage(ItemStack stack) {
		
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			return 0;
		}
		
		return stack.getTagCompound().getLong("damage");
	}
	
	public static void setLensDamage(ItemStack stack, long damage) {
		
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		stack.getTagCompound().setLong("damage", damage);
	}
	
	
}
