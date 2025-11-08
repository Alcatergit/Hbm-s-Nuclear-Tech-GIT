package com.hbm.hazard.type;

import java.util.List;

import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.handler.ArmorUtil;
import com.hbm.lib.Library;
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
        level *= stack.getCount();
		if(livingTEntity instanceof EntityPlayer player){
            if(player.capabilities.isCreativeMode) return;
			if(ArmorRegistry.hasProtection(livingTEntity, EntityEquipmentSlot.HEAD, HazardClass.NERVE_AGENT)){
				ArmorUtil.damageGasMaskFilter(livingTEntity, 1);
				hasToxFilter = true;
			}
			hasHazmat = ArmorUtil.checkForHazmat(livingTEntity);
		}

        if(!hasToxFilter && !hasHazmat){
            if(level > 0) livingTEntity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 110, Math.min(255, (int)level)));
            if(level > 10) livingTEntity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 110, Math.min(255, (int)level/10)));
            if(level > 100) livingTEntity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 110, Math.min(4, (int)level/100)));
            if(level > 1000){
				if(level > 2000 || livingTEntity.world.rand.nextInt((int)(2000/level)) == 0){
					livingTEntity.addPotionEffect(new PotionEffect(MobEffects.POISON, 110, Math.min(255, (int)level/1000)));
				}
			}
		}
		if(!(hasHazmat && hasToxFilter)){
			if(level >  2000) livingTEntity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 110, Math.min(255, (int)level/2000)));
			if(level > 10000) livingTEntity.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 110, Math.min(255, (int)level/5000)));
		}
	}

	@Override
	public void updateEntity(EntityItem item, float level) {
	}

	@Override
	public void addHazardInformation(EntityPlayer player, List<String> list, float level, ItemStack stack, List<HazardModifier> modifiers) {
        level *= stack.getCount();
        float rl = Library.roundFloat(level, 3);
		if(level > 10000)
			list.add("§a[" + I18nUtil.resolveKey("adjective.extreme") + " " + I18nUtil.resolveKey("trait.toxic") + "] "+rl);
		else if(level > 1000)
			list.add("§a[" + I18nUtil.resolveKey("adjective.veryhigh") + " " + I18nUtil.resolveKey("trait.toxic") + "] "+rl);
		else if(level > 100)
			list.add("§a[" + I18nUtil.resolveKey("adjective.high") + " " + I18nUtil.resolveKey("trait.toxic") + "] "+rl);
		else if(level > 10)
			list.add("§a[" + I18nUtil.resolveKey("adjective.medium") + " " + I18nUtil.resolveKey("trait.toxic") + "] "+rl);
		else if(level > 0)
			list.add("§a[" + I18nUtil.resolveKey("adjective.little") + " " + I18nUtil.resolveKey("trait.toxic") + "] "+rl);
	}
}