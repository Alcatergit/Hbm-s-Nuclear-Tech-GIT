package com.hbm.hazard.type;

import java.util.List;

import com.hbm.hazard.HazardModifier;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;

public class HazardTypeBlinding extends HazardTypeBase {

	@Override
	public void onUpdate(EntityLivingBase target, float level, ItemStack stack) {

		if(!ArmorRegistry.hasProtection(target, EntityEquipmentSlot.HEAD, HazardClass.LIGHT)) {
			target.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, (int)level, 0));
		}
	}

	@Override
	public void updateEntity(EntityItem item, float level) { }

	@Override
	public void addHazardInformation(EntityPlayer player, List<String> list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		list.add(TextFormatting.DARK_AQUA + "[" + I18nUtil.resolveKey("trait.blinding") + "]");
	}

}
