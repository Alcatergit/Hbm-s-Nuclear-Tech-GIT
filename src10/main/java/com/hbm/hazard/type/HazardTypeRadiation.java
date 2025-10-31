package com.hbm.hazard.type;

import java.util.List;

import com.hbm.config.GeneralConfig;
import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HazardTypeRadiation extends HazardTypeBase {

	@Override
	public void onUpdate(EntityLivingBase target, float level, ItemStack stack) {
		
		boolean reacher = false;
		
		if(target instanceof EntityPlayer && !GeneralConfig.enable528)
			reacher = Library.checkForHeld((EntityPlayer) target, ModItems.reacher);
			
		if(level > 0) {
			float rad = level / 20F;
			
			if(reacher)
				rad = (float) Math.min(Math.sqrt(rad), rad); //to prevent radiation from going up when being <1
			
			ContaminationUtil.contaminate(target, HazardType.RADIATION, ContaminationType.CREATIVE, rad);
		}
	}

	@Override
	public void updateEntity(EntityItem item, float level) { }

	@Override
	@SideOnly(Side.CLIENT)
	public void addHazardInformation(EntityPlayer player, List<String> list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		
		level = HazardModifier.evalAllModifiers(stack, player, level, modifiers);
		if(level == 0) return;
		list.add("§a[" + I18nUtil.resolveKey("trait.radioactive") + "]");
		list.add(" §e" + (Library.roundFloat(getNewValue(level), 3)+ getSuffix(level) + " " + I18nUtil.resolveKey("desc.rads")));
			
		if(stack.getCount() > 1) {
			float stackRad = level * stack.getCount();
			list.add(" §e" + I18nUtil.resolveKey("desc.stack")+" " + Library.roundFloat(getNewValue(stackRad), 3) + getSuffix(stackRad) + " " + I18nUtil.resolveKey("desc.rads"));
		}
	}


	public static float getNewValue(float radiation){
		if(radiation < 1000000){
			return radiation;
		} else if(radiation < 1000000000){
			return radiation * 0.000001F;
		} else{
			return radiation * 0.000000001F;
		}
	}

	public static String getSuffix(float radiation){
		if(radiation < 1000000){
			return "";
		} else if(radiation < 1000000000){
			return I18nUtil.resolveKey("desc.mil");
		} else{
			return I18nUtil.resolveKey("desc.bil");
		}
	}
}