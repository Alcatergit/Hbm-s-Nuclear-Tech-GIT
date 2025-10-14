package com.hbm.hazard.type;

import java.util.List;

import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.handler.ArmorUtil;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;

public class HazardTypeToxic extends HazardTypeBase {

	@Override
	public void onUpdate(EntityLivingBase livingTEntity, float level, ItemStack stack) {
		boolean hasToxFilter = false;
		boolean hasHazmat = false;
		if(livingTEntity instanceof EntityPlayer){
			if(ArmorRegistry.hasProtection(livingTEntity, EntityEquipmentSlot.HEAD, HazardClass.NERVE_AGENT)){
				ArmorUtil.damageGasMaskFilter(livingTEntity, 1);
				hasToxFilter = true;
			}
			hasHazmat = ArmorUtil.checkForHazmat((EntityPlayer)livingTEntity);
		}

		if(!hasToxFilter && !hasHazmat){
			livingTEntity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 110, (int)level-1));
			
			if(level > 2)
				livingTEntity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 110, Math.min(4, (int)level-1)));
			if(level > 4)
				livingTEntity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 110, (int)level));
			if(level > 6){
				if(livingTEntity.world.rand.nextInt((int)(2000/level)) == 0){
					livingTEntity.addPotionEffect(new PotionEffect(MobEffects.POISON, 110, (int)level-4));
				}
			}
		}
		if(!(hasHazmat && hasToxFilter)){
			if(level > 8)
				livingTEntity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 110, (int)level-6));
			if(level > 16)
				livingTEntity.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 110, (int)level-16));
		}
	}

	@Override
	public void updateEntity(EntityItem item, float level) {
	}

	@Override
	public void addHazardInformation(EntityPlayer player, List<String> list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		if(level > 16)
			list.add("§a[" + I18nUtil.resolveKey("adjective.extreme") + " " + I18nUtil.resolveKey("trait.toxic") + "]");
		else if(level > 8)
			list.add("§a[" + I18nUtil.resolveKey("adjective.veryhigh") + " " + I18nUtil.resolveKey("trait.toxic") + "]");
		else if(level > 4)
			list.add("§a[" + I18nUtil.resolveKey("adjective.high") + " " + I18nUtil.resolveKey("trait.toxic") + "]");
		else if(level > 2)
			list.add("§a[" + I18nUtil.resolveKey("adjective.medium") + " " + I18nUtil.resolveKey("trait.toxic") + "]");
		else
			list.add("§a[" + I18nUtil.resolveKey("adjective.little") + " " + I18nUtil.resolveKey("trait.toxic") + "]");
	}
}