package com.hbm.hazard.type;

import java.util.List;

import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.I18nUtil;
import com.hbm.lib.Library;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class HazardTypeDigamma extends HazardTypeBase {

	@Override
	public void onUpdate(EntityLivingBase target, float level, ItemStack stack) {
		ContaminationUtil.applyDigammaData(target, level / 20F);
	}

	@Override
	public void updateEntity(EntityItem item, float level) { }

	@Override
	public void addHazardInformation(EntityPlayer player, List<String> list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		
		level = HazardModifier.evalAllModifiers(stack, player, level, modifiers);
		
		list.add("§c[" + I18nUtil.resolveKey("trait.digamma") + "]");
		list.add(" §4" + Library.roundFloat(level * 1000F, 2) + " " + I18nUtil.resolveKey("desc.digammaed"));
		if(stack.getCount() > 1) {
			list.add(" §4" + I18nUtil.resolveKey("desc.stack") + " " + Library.roundFloat(level * stack.getCount() * 1000F, 2) + " " + I18nUtil.resolveKey("desc.digammaed"));
		}
	}
}