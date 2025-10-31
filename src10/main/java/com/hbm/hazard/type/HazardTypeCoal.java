package com.hbm.hazard.type;

import java.util.List;

import com.hbm.capability.HbmLivingProps;
import com.hbm.handler.ArmorUtil;
import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class HazardTypeCoal extends HazardTypeBase {

	@Override
	public void onUpdate(EntityLivingBase target, float level, ItemStack stack) {
		
		if(!ArmorRegistry.hasProtection(target, EntityEquipmentSlot.HEAD, HazardClass.PARTICLE_COARSE))
			HbmLivingProps.incrementBlackLung(target, (int) Math.min(level, 10));
		else
			ArmorUtil.damageGasMaskFilter(target, (int) level);
	}

	@Override
	public void updateEntity(EntityItem item, float level) { }

	@Override
	public void addHazardInformation(EntityPlayer player, List<String> list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		list.add("§8[" + I18nUtil.resolveKey("trait.coal") + "]");
	}

}
