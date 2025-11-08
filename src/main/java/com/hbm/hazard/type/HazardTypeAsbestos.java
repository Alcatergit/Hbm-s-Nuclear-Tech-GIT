package com.hbm.hazard.type;

import java.util.List;

import com.hbm.capability.HbmLivingProps;
import com.hbm.config.GeneralConfig;
import com.hbm.handler.ArmorUtil;
import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.lib.Library;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class HazardTypeAsbestos extends HazardTypeBase {

	@Override
	public void onUpdate(EntityLivingBase target, float level, ItemStack stack) {
        if(!GeneralConfig.enableAsbestos) return;
        level *= stack.getCount();
        ContaminationUtil.applyAsbestos(target, level/100F, level/500F);
	}

	@Override
	public void updateEntity(EntityItem item, float level) { }

	@Override
	public void addHazardInformation(EntityPlayer player, List<String> list, float level, ItemStack stack, List<HazardModifier> modifiers) {
        if(GeneralConfig.enableAsbestos) {
            level *= stack.getCount();
            list.add("§f[" + I18nUtil.resolveKey("trait.asbestos") + "] "+Library.roundFloat(level, 3));
        }
	}
}
