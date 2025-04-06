package com.hbm.hazard.type;

import com.hbm.config.RadiationConfig;
import com.hbm.hazard.helper.HazardHelper;
import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.util.I18nUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class HazardTypeHot extends HazardTypeBase {

	@Override
	public void onUpdate(final EntityLivingBase target, final float level, final ItemStack stack) {

		final boolean wetOrReacher = HazardHelper.isHoldingReacher(target) || target.isWet() ;
		if(RadiationConfig.disableHot || wetOrReacher) return;

		target.setFire((int) Math.ceil(level)*hazardRate);
	}

	@Override
	public void updateEntity(final EntityItem item, final float level) { }

	@Override
	@SideOnly(Side.CLIENT)
	public void addHazardInformation(final EntityPlayer player, final List list, float level, final ItemStack stack, final List<HazardModifier> modifiers) {
		
		level = HazardModifier.evalAllModifiers(stack, player, level, modifiers);
		
		if(level > 0)
			list.add(TextFormatting.GOLD + "[" + I18nUtil.resolveKey("trait.hot") + "]");
	}

}
