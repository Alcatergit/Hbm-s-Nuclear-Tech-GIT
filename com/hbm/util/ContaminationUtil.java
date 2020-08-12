package com.hbm.util;

import com.hbm.capability.RadiationCapability;
import com.hbm.handler.HazmatRegistry;
import com.hbm.lib.Library;
import com.hbm.potion.HbmPotion;
import com.hbm.saveddata.RadiationSavedData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
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

		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entity;

			float koeff = 5.0F;
			return (float) Math.pow(koeff, -HazmatRegistry.instance.getResistance(player)) * entity.getEntityData().getFloat("hbmradmultiplier");
		}

		return entity.getEntityData().getFloat("hbmradmultiplier");
	}

	public static void applyRadData(Entity e, float f) {

		if(!(e instanceof EntityLivingBase))
			return;

		EntityLivingBase entity = (EntityLivingBase)e;

		f *= calculateRadiationMod(entity);

		if(entity.hasCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null)) {
			RadiationCapability.IEntityRadioactive ent = entity.getCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null);
			ent.increaseRads(f);
		}
	}

	public static void applyRadDirect(Entity entity, float f) {

		if(entity.getEntityData().hasKey("hbmradmultiplier", 99))
			f *= entity.getEntityData().getFloat("hbmradmultiplier");
		
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

		World world = player.world;

		double eRad = ((int)(Library.getEntRadCap(player).getRads() * 10)) / 10D;

		RadiationSavedData data = RadiationSavedData.getData(player.world);
		Chunk chunk = world.getChunkFromBlockCoords(new BlockPos((int)player.posX, 0, (int)player.posZ));
		double rads = ((int)(data.getRadNumFromCoord(chunk.x, chunk.z) * 10)) / 10D;

		double res = 100.0D - ((int)(ContaminationUtil.calculateRadiationMod(player) * 10000)) / 100D;
		double resKoeff = ((int)(HazmatRegistry.instance.getResistance(player) * 100)) / 100D;

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

		player.sendMessage(new TextComponentString(TextFormatting.GOLD + "===== ☢ GEIGER COUNTER ☢ ====="));
		player.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Current chunk radiation: " + chunkPrefix + rads + " RAD/s"));
		player.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Player contamination: " + radPrefix + eRad + " RAD"));
		player.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Player resistance: " + resPrefix + res + "% (" + resKoeff + ")"));
	}
}