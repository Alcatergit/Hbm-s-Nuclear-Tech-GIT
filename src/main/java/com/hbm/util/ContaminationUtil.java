package com.hbm.util;

import com.hbm.capability.RadiationCapability;
import com.hbm.handler.HazmatRegistry;
import com.hbm.handler.RadiationSystemNT;
import com.hbm.interfaces.IRadiationImmune;
import com.hbm.lib.Library;
import com.hbm.potion.HbmPotion;
import com.hbm.saveddata.RadiationSavedData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ContaminationUtil {

	/**
	 * Calculates how much radiation can be applied to this entity by calculating resistance
	 * @param entity
	 * @return
	 */
	public static float calculateRadiationMod(EntityLivingBase entity) {

		if(entity.isPotionActive(HbmPotion.mutation))
			return 0;
		float mult = 1;
		if(entity.getEntityData().hasKey("hbmradmultiplier", 99))
			mult = entity.getEntityData().getFloat("hbmradmultiplier");

		float koeff = 5.0F;
		return (float) Math.pow(koeff, -HazmatRegistry.getResistance(entity)) * mult;
	}

	public static void applyRadData(Entity e, float f) {

		if(e instanceof IRadiationImmune)
			return;
		
		if(!(e instanceof EntityLivingBase))
			return;

		if(e instanceof EntityPlayer && (((EntityPlayer) e).capabilities.isCreativeMode || ((EntityPlayer) e).isSpectator()))
			return;
		
		if(e instanceof EntityPlayer && e.ticksExisted < 200)
			return;
		
		EntityLivingBase entity = (EntityLivingBase)e;

		f *= calculateRadiationMod(entity);

		if(entity.hasCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null)) {
			RadiationCapability.IEntityRadioactive ent = entity.getCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null);
			ent.increaseRads(f);
		}
	}

	public static void applyRadDirect(Entity entity, float f) {

		if(entity instanceof IRadiationImmune)
			return;
		
		if(entity.getEntityData().hasKey("hbmradmultiplier", 99))
			f *= entity.getEntityData().getFloat("hbmradmultiplier");
		
		if(entity instanceof EntityPlayer && (((EntityPlayer) entity).capabilities.isCreativeMode || ((EntityPlayer) entity).isSpectator()))
			return;
		
		if(!(entity instanceof EntityLivingBase))
			return;

		if(((EntityLivingBase) entity).isPotionActive(HbmPotion.mutation))
			return;

		if(entity.hasCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null)) {
			RadiationCapability.IEntityRadioactive ent = entity.getCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null);
			ent.increaseRads(f);
		}
	}

	public static void printGeigerData(EntityPlayer player) {

		double eRad = ((int)(Library.getEntRadCap(player).getRads() * 10)) / 10D;

		RadiationSavedData data = RadiationSavedData.getData(player.world);
		double rads = ((int)(data.getRadNumFromCoord(player.getPosition()) * 10)) / 10D;

		double res = 100.0D - ((int)(ContaminationUtil.calculateRadiationMod(player) * 10000)) / 100D;
		double resKoeff = ((int)(HazmatRegistry.getResistance(player) * 100)) / 100D;

		String chunkPrefix = "";
		String radPrefix = "";
		String resPrefix = "" + TextFormatting.WHITE;

		if(rads == 0)
			chunkPrefix += TextFormatting.GREEN;
		else if(rads < 1)
			chunkPrefix += TextFormatting.YELLOW;
		else if(rads < 10)
			chunkPrefix += TextFormatting.GOLD;
		else if(rads < 100)
			chunkPrefix += TextFormatting.RED;
		else if(rads < 1000)
			chunkPrefix += TextFormatting.DARK_RED;
		else
			chunkPrefix += TextFormatting.DARK_GRAY;

		if(eRad < 200)
			radPrefix += TextFormatting.GREEN;
		else if(eRad < 400)
			radPrefix += TextFormatting.YELLOW;
		else if(eRad < 600)
			radPrefix += TextFormatting.GOLD;
		else if(eRad < 800)
			radPrefix += TextFormatting.RED;
		else if(eRad < 1000)
			radPrefix += TextFormatting.DARK_RED;
		else
			radPrefix += TextFormatting.DARK_GRAY;

		if(resKoeff > 0)
			resPrefix += TextFormatting.GREEN;

		//localization and server-side restrictions have turned this into a painful mess
		//a *functioning* painful mess, nonetheless
		player.sendMessage(new TextComponentString("===== ☢ ").appendSibling(new TextComponentTranslation("geiger.title")).appendSibling(new TextComponentString(" ☢ =====")).setStyle(new Style().setColor(TextFormatting.GOLD)));
		player.sendMessage(new TextComponentTranslation("geiger.chunkRad").appendSibling(new TextComponentString(" " + chunkPrefix + rads + " RAD/s")).setStyle(new Style().setColor(TextFormatting.YELLOW)));
		player.sendMessage(new TextComponentTranslation("geiger.playerRad").appendSibling(new TextComponentString(" " + radPrefix + eRad + " RAD")).setStyle(new Style().setColor(TextFormatting.YELLOW)));
		player.sendMessage(new TextComponentTranslation("geiger.playerRes").appendSibling(new TextComponentString(" " + resPrefix + res + "% (" + resKoeff + ")")).setStyle(new Style().setColor(TextFormatting.YELLOW)));
	}
	
	public static float getRads(Entity e) {
		if(e instanceof IRadiationImmune)
			return 0.0F;
		return Library.getEntRadCap(e).getRads();
	}
}