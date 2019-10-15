package com.hbm.lib;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class Library {

	public static boolean checkForHazmat(EntityPlayer player) {
		// TODO Make hazmat armors
		return false;
	}

	public static void applyRadData(Entity e, float f) {
		// TODO make radiation system
		if(!(e instanceof EntityLivingBase))
			return;
		
		EntityLivingBase entity = (EntityLivingBase)e;
		
	//if(entity.isPotionActive(HbmPotion.mutation))
	//		return;
		
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entity;
			
			float koeff = 5.0F;
		//	f *= (float) Math.pow(koeff, -HazmatRegistry.instance.getResistance(player));
		}
		
		//RadEntitySavedData data = RadEntitySavedData.getData(entity.worldObj);
		//data.increaseRad(entity, f);
	}
	
	

}
