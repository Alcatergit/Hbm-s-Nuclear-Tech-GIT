package com.hbm.lib;

import com.hbm.handler.HazmatRegistry;
import com.hbm.potion.HbmPotion;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.saveddata.RadEntitySavedData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class Library {

	public static boolean checkForHazmat(EntityPlayer player) {
		// TODO Make hazmat armors
		return false;
	}

	public static void applyRadData(Entity e, float f) {
		if(!(e instanceof EntityLivingBase))
			return;
		
		EntityLivingBase entity = (EntityLivingBase)e;
		
	if(entity.isPotionActive(HbmPotion.mutation))
			return;
		
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entity;
			
			float koeff = 5.0F;
			f *= (float) Math.pow(koeff, -HazmatRegistry.instance.getResistance(player));
		}
		
		RadEntitySavedData data = RadEntitySavedData.getData(entity.world);
		data.increaseRad(entity, f);
	}
	public static void applyRadDirect(Entity e, float f) {

		if(!(e instanceof EntityLivingBase))
			return;
		
		if(((EntityLivingBase)e).isPotionActive(HbmPotion.mutation))
			return;
		
		RadEntitySavedData data = RadEntitySavedData.getData(e.world);
		data.increaseRad(e, f);
	}
	public static boolean isObstructed(World world, double x, double y, double z, double a, double b, double c) {
		
		Vec3 vector = Vec3.createVectorHelper(a - x, b - y, c - z);
		double length = vector.lengthVector();
		Vec3 nVec = vector.normalize();
		MutableBlockPos pos = new BlockPos.MutableBlockPos();
		for(float i = 0; i < length; i += 0.25F)
			pos.setPos((int) Math.round(x + (nVec.xCoord * i)), (int) Math.round(y + (nVec.yCoord * i)), (int) Math.round(z + (nVec.zCoord * i)));
			if(world.getBlockState(pos).getBlock() != Blocks.AIR && 
					world.getBlockState(pos).isNormalCube())
				return true;
		
		return false;
	}

}
