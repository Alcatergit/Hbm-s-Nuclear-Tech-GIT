package com.hbm.hazard.type;

import java.util.List;

import com.hbm.config.GeneralConfig;
import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class HazardTypeHot extends HazardTypeBase {

	@Override
	public void onUpdate(EntityLivingBase target, float level, ItemStack stack) {
		
		boolean reacher = false;

        if(target instanceof EntityPlayer player && !GeneralConfig.enable528)
            reacher = Library.checkForHeld(player, ModItems.reacher) || Library.checkForBauble(player, ModItems.reacher);
		
		if(!reacher && !target.isWet())
			target.setFire((int) Math.ceil(level));
	}

	@Override
	public void updateEntity(EntityItem item, float level) { }

	@Override
	public void addHazardInformation(EntityPlayer player, List<String> list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		if(level > 0) list.add("§6[" + I18nUtil.resolveKey("trait.hot") + "]");
	}

}
