package com.hbm.hazard.type;

import java.util.List;

import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.config.GeneralConfig;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.util.I18nUtil;
import com.hbm.handler.ArmorUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;

public class HazardTypeCryogenic extends HazardTypeBase {

	@Override
	public void onUpdate(EntityLivingBase livingCEntity, float level, ItemStack stack) {
		boolean reacher = false;
		
		if(livingCEntity instanceof EntityPlayer player && !GeneralConfig.enable528)
			reacher = player.capabilities.isCreativeMode || Library.checkForHeld(player, ModItems.reacher) || Library.checkForBauble(player, ModItems.reacher);
		
		if(!reacher){
			boolean isProtected = livingCEntity instanceof EntityPlayer && ArmorUtil.checkForHazmat(livingCEntity);
			if(!isProtected){
				livingCEntity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 110, (int)level-1));
				livingCEntity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 110, Math.min(4, (int)level-1)));
				livingCEntity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 110, (int)level-1));
				if(level > 4){
					livingCEntity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 110, (int)level-3));
					livingCEntity.extinguish();
				}
			}
		}
	}

	@Override
	public void updateEntity(EntityItem item, float level) {
	}

	@Override
	public void addHazardInformation(EntityPlayer player, List<String> list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		list.add("§b[" + I18nUtil.resolveKey("trait.cryogenic") + "]");
	}
}