package com.hbm.lib;

import com.hbm.handler.HazmatRegistry;
import com.hbm.potion.HbmPotion;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.saveddata.RadEntitySavedData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
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

	public static boolean checkArmor(EntityPlayer player, Item helm, Item chest, Item leg, Item shoe) {
		if(player.inventory.armorInventory.get(0).getItem() == shoe && 
				player.inventory.armorInventory.get(1).getItem() == leg && 
				player.inventory.armorInventory.get(2).getItem() == chest && 
				player.inventory.armorInventory.get(3).getItem() == helm)
		{
			return true;
		}
		
		return false;
	}

	public static String getShortNumber(long l) {
		if(l >= Math.pow(10, 18)) {
			double res = l / Math.pow(10, 18);
			res = Math.round(res * 100.0) / 100.0;
			return res + "E";
		}
		if(l >= Math.pow(10, 15)) {
			double res = l / Math.pow(10, 15);
			res = Math.round(res * 100.0) / 100.0;
			return res + "P";
		}
		if(l >= Math.pow(10, 12)) {
			double res = l / Math.pow(10, 12);
			res = Math.round(res * 100.0) / 100.0;
			return res + "T";
		}
		if(l >= Math.pow(10, 9)) {
			double res = l / Math.pow(10, 9);
			res = Math.round(res * 100.0) / 100.0;
			return res + "G";
		}
		if(l >= Math.pow(10, 6)) {
			double res = l / Math.pow(10, 6);
			res = Math.round(res * 100.0) / 100.0;
			return res + "M";
		}
		if(l >= Math.pow(10, 3)) {
			double res = l / Math.pow(10, 3);
			res = Math.round(res * 100.0) / 100.0;
			return res + "k";
		}
		
		return Long.toString(l);
	}

}
