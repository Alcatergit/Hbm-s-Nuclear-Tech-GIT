package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemModPolish extends ItemArmorMod {

	public ItemModPolish(String s) {
		super(ArmorModHandler.extra, true, true, true, true, s);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn){
		list.add(TextFormatting.BLUE + "5% chance to nullify damage");
		list.add("");
		super.addInformation(stack, worldIn, list, flagIn);
	}
	

	@Override
	public void addDesc(List<String> list, ItemStack stack, ItemStack armor) {
		list.add(TextFormatting.BLUE + "  " + stack.getDisplayName() + " (5% chance to nullify damage)");
	}
	
	@Override
	public void modDamage(LivingHurtEvent event, ItemStack armor) {
		if(event.getEntity().world.rand.nextInt(20) == 0)
			event.setAmount(0);
	}
}