package com.hbm.items.armor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.hbm.handler.ArmorModHandler;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemModMilk extends ItemArmorMod {
	
	public ItemModMilk(String s) {
		super(ArmorModHandler.extra, true, true, true, true, s);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn){
		list.add(TextFormatting.WHITE + "Removes bad potion effects");
		list.add("Dropped by 1:500 Spiders");
		list.add("");
		super.addInformation(stack, worldIn, list, flagIn);
	}

	@Override
	public void addDesc(List<String> list, ItemStack stack, ItemStack armor) {
		list.add(TextFormatting.WHITE + "  " + stack.getDisplayName() + " (Removes bad potion effects)");
	}
	
	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		
		List<Potion> pots = new ArrayList<>();

		Iterator<PotionEffect> iterator = ((Collection<PotionEffect>) entity.getActivePotionEffects()).iterator();

		while(iterator.hasNext()) {
			PotionEffect eff = iterator.next();

			if(eff.getPotion().isBadEffect()) {
				pots.add(eff.getPotion());
			}
		}

		for(Potion p : pots) {
			entity.removePotionEffect(p);
		}
	}
}