package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemModBandaid extends ItemArmorMod {

	public ItemModBandaid(String s) {
		super(ArmorModHandler.extra, true, true, true, true, s);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn){
		list.add(TextFormatting.RED + "3% chance for full heal when damaged");
		list.add("Dropped by 1:500 Animals");
		list.add("");
		super.addInformation(stack, worldIn, list, flagIn);
	}
	
	@Override
	public void addDesc(List<String> list, ItemStack stack, ItemStack armor) {
		list.add(TextFormatting.RED + "  " + stack.getDisplayName() + " (3% chance for full heal)");
	}
	
	@Override
	public void modDamage(LivingHurtEvent event, ItemStack armor) {
		
		if(event.getEntity().world.rand.nextInt(100) < 3) {
			event.setAmount(0);
			event.getEntityLiving().heal(event.getEntityLiving().getMaxHealth());
		}
	}
}