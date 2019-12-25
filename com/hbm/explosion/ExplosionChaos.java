package com.hbm.explosion;

import java.util.List;

import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ExplosionChaos {

	//Drillgon200: Descriptive method names anyone?
	public static void c(World world, int x, int y, int z, int bombStartStrength) {
		float f = bombStartStrength;
		int i;
		int j;
		int k;
		double d5;
		double d6;
		double d7;
		double wat = bombStartStrength * 2;

		bombStartStrength *= 2.0F;
		i = MathHelper.floor(x - wat - 1.0D);
		j = MathHelper.floor(x + wat + 1.0D);
		k = MathHelper.floor(y - wat - 1.0D);
		int i2 = MathHelper.floor(y + wat + 1.0D);
		int l = MathHelper.floor(z - wat - 1.0D);
		int j2 = MathHelper.floor(z + wat + 1.0D);
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(i, k, l, j, i2, j2));

		for (int i1 = 0; i1 < list.size(); ++i1) {
			Entity entity = (Entity) list.get(i1);
			double d4 = entity.getDistance(x, y, z) / bombStartStrength;

			if (d4 <= 1.0D) {
				d5 = entity.posX - x;
				d6 = entity.posY + entity.getEyeHeight() - y;
				d7 = entity.posZ - z;
				double d9 = MathHelper.sqrt(d5 * d5 + d6 * d6 + d7 * d7);
				if (d9 < wat) {
					
					if (entity instanceof EntityPlayer) {
						
						Library.damageSuit((EntityPlayer)entity, 0, 5);
						Library.damageSuit((EntityPlayer)entity, 1, 5);
						Library.damageSuit((EntityPlayer)entity, 2, 5);
						Library.damageSuit((EntityPlayer)entity, 3, 5);
						
					}
					
					if (entity instanceof EntityPlayer && Library.checkForHazmat((EntityPlayer) entity)) { } else {
						
						if(entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(HbmPotion.taint)) {
							((EntityLivingBase)entity).removePotionEffect(HbmPotion.taint);
							((EntityLivingBase)entity).addPotionEffect(new PotionEffect(HbmPotion.mutation, 1 * 60 * 60 * 20, 0, false, true));
						} else {
							entity.attackEntityFrom(ModDamageSource.cloud, 3);
						}
					}
				}
			}
		}

		bombStartStrength = (int) f;
	}
}
