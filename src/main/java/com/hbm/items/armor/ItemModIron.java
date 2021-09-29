package com.hbm.items.armor;

import java.util.List;

import com.google.common.collect.Multimap;
import com.hbm.handler.ArmorModHandler;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemModIron extends ItemArmorMod {

	public ItemModIron(String s) {
		super(ArmorModHandler.cladding, true, true, true, true, s);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn){
		list.add(TextFormatting.WHITE + "+0.5 knockback resistance");
		list.add("");
		super.addInformation(stack, worldIn, list, flagIn);
	}
	

	@Override
	public void addDesc(List<String> list, ItemStack stack, ItemStack armor) {
		list.add(TextFormatting.WHITE + "  " + stack.getDisplayName() + " (+0.5 knockback resistence)");
	}
	
	@Override
	public Multimap<String, AttributeModifier> getModifiers(EntityEquipmentSlot slot, ItemStack armor){
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, armor);
		multimap.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(),
				new AttributeModifier(ArmorModHandler.UUIDs[((ItemArmor)armor.getItem()).armorType.getIndex()], "NTM Armor Mod Knockback", 0.5, 0));
		return multimap;
	}
	
}
