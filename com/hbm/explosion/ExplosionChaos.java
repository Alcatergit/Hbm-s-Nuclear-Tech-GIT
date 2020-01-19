package com.hbm.explosion;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.hbm.entity.particle.EntityChlorineFX;
import com.hbm.entity.particle.EntityCloudFX;
import com.hbm.entity.particle.EntityModFX;
import com.hbm.entity.particle.EntityOrangeFX;
import com.hbm.entity.particle.EntityPinkCloudFX;
import com.hbm.entity.projectile.EntityRocket;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ExplosionChaos {

	private static Random rand = new Random();
	
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
	
	/**
	 * Sets all flammable blocks on fire
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param bound
	 */
	public static void flameDeath(World world, BlockPos pos, int bound) {

		MutableBlockPos mPos = new BlockPos.MutableBlockPos(pos);
		MutableBlockPos mPosUp = new BlockPos.MutableBlockPos(pos.up());
		MutableBlockPos mPosFlameCheck = new BlockPos.MutableBlockPos(mPosUp);
		
		int r = bound;
		int r2 = r * r;
		int r22 = r2 / 2;
		for (int xx = -r; xx < r; xx++) {
			int X = xx + pos.getX();
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + pos.getY();
				int YY = XX + yy * yy;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + pos.getZ();
					int ZZ = YY + zz * zz;
					if (ZZ < r22) {
						mPos.setPos(X, Y, Z);
						mPosUp.setPos(X, Y + 1, Z);
						mPosFlameCheck.setPos(XX, YY, ZZ);
						if (world.getBlockState(mPos).getBlock().isFlammable(world, mPosFlameCheck, EnumFacing.UP)
								&& world.getBlockState(mPosUp).getBlock() == Blocks.AIR) {
							world.setBlockState(mPosUp, Blocks.FIRE.getDefaultState());
						}
					}
				}
			}
		}

	}
	
	/**
	 * Sets all blocks on fire
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param bound
	 */
	public static void burn(World world, BlockPos pos, int bound) {

		MutableBlockPos mPos = new BlockPos.MutableBlockPos(pos);
		MutableBlockPos mPosUp = new BlockPos.MutableBlockPos(pos.up());
		
		int r = bound;
		int r2 = r * r;
		int r22 = r2 / 2;
		for (int xx = -r; xx < r; xx++) {
			int X = xx + pos.getX();
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + pos.getY();
				int YY = XX + yy * yy;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + pos.getZ();
					int ZZ = YY + zz * zz;
					if (ZZ < r22) {
						mPos.setPos(X, Y, Z);
						mPosUp.setPos(X, Y + 1, Z);
						if ((world.getBlockState(mPosUp).getBlock() == Blocks.AIR
								|| world.getBlockState(mPosUp).getBlock() == Blocks.SNOW_LAYER)
								&& world.getBlockState(mPos) != Blocks.AIR) {
							world.setBlockState(mPosUp, Blocks.FIRE.getDefaultState());
						}
					}
				}
			}
		}

	}
	
	public static void spawnChlorine(World world, double x, double y, double z, int count, double speed, int type) {
		
		for(int i = 0; i < count; i++) {
			
			EntityModFX fx = null;
			
			if(type == 0) {
				fx = new EntityChlorineFX(world, x, y, z, 0.0, 0.0, 0.0);
			} else if(type == 1) {
				fx = new EntityCloudFX(world, x, y, z, 0.0, 0.0, 0.0);
			} else if(type == 2) {
				fx = new EntityPinkCloudFX(world, x, y, z, 0.0, 0.0, 0.0);
			} else {
				fx = new EntityOrangeFX(world, x, y, z, 0.0, 0.0, 0.0);
			}
			
			fx.motionY = rand.nextGaussian() * speed;
			fx.motionX = rand.nextGaussian() * speed;
			fx.motionZ = rand.nextGaussian() * speed;
			world.spawnEntity(fx);
		}
	}
	
	public static void pc(World world, int x, int y, int z, int bombStartStrength) {
		float f = bombStartStrength;
		int i;
		int j;
		int k;
		double d5;
		double d6;
		double d7;
		double wat = bombStartStrength * 2;
		boolean isOccupied = false;

		bombStartStrength *= 2.0F;
		i = MathHelper.floor(x - wat - 1.0D);
		j = MathHelper.floor(x + wat + 1.0D);
		k = MathHelper.floor(y - wat - 1.0D);
		int i2 = MathHelper.floor(y + wat + 1.0D);
		int l = MathHelper.floor(z - wat - 1.0D);
		int j2 = MathHelper.floor(z + wat + 1.0D);
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(i, k, l, j, i2, j2));
		Vec3d vec3 = new Vec3d(x, y, z);

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
						
						Library.damageSuit((EntityPlayer)entity, 0, 25);
						Library.damageSuit((EntityPlayer)entity, 1, 25);
						Library.damageSuit((EntityPlayer)entity, 2, 25);
						Library.damageSuit((EntityPlayer)entity, 3, 25);
						
					}
					
					entity.attackEntityFrom(ModDamageSource.pc, 5);
				}
			}
		}

		bombStartStrength = (int) f;
	}
	
	public static void poison(World world, int x, int y, int z, int bombStartStrength) {
		float f = bombStartStrength;
		int i;
		int j;
		int k;
		double d5;
		double d6;
		double d7;
		double wat = bombStartStrength * 2;
		boolean isOccupied = false;

		bombStartStrength *= 2.0F;
		i = MathHelper.floor(x - wat - 1.0D);
		j = MathHelper.floor(x + wat + 1.0D);
		k = MathHelper.floor(y - wat - 1.0D);
		int i2 = MathHelper.floor(y + wat + 1.0D);
		int l = MathHelper.floor(z - wat - 1.0D);
		int j2 = MathHelper.floor(z + wat + 1.0D);
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(i, k, l, j, i2, j2));
		Vec3d vec3 = new Vec3d(x, y, z);

		for (int i1 = 0; i1 < list.size(); ++i1) {
			Entity entity = (Entity) list.get(i1);
			double d4 = entity.getDistance(x, y, z) / bombStartStrength;

			if (d4 <= 1.0D) {
				d5 = entity.posX - x;
				d6 = entity.posY + entity.getEyeHeight() - y;
				d7 = entity.posZ - z;
				double d9 = MathHelper.sqrt(d5 * d5 + d6 * d6 + d7 * d7);
				if (d9 < wat) {
					if (entity instanceof EntityPlayer && Library.checkForGasMask((EntityPlayer) entity)) {
						Library.damageSuit((EntityPlayer)entity, 3, rand.nextInt(2));

					} else if (entity instanceof EntityLivingBase) {
						((EntityLivingBase) entity)
								.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 5 * 20, 0));
						((EntityLivingBase) entity)
								.addPotionEffect(new PotionEffect(MobEffects.POISON, 20 * 20, 2));
						((EntityLivingBase) entity)
								.addPotionEffect(new PotionEffect(MobEffects.WITHER, 1 * 20, 1));
						((EntityLivingBase) entity)
								.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 30 * 20, 1));
						((EntityLivingBase) entity)
								.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 30 * 20, 2));
					}
				}
			}
		}

		bombStartStrength = (int) f;
	}
	
	public static void cluster(World world, int x, int y, int z, int count, int gravity) {

		double d1 = 0;
		double d2 = 0;
		double d3 = 0;
		EntityRocket fragment;

		for (int i = 0; i < count; i++) {
			d1 = rand.nextDouble();
			d2 = rand.nextDouble();
			d3 = rand.nextDouble();

			if (rand.nextInt(2) == 0) {
				d1 *= -1;
			}

			if (rand.nextInt(2) == 0) {
				d3 *= -1;
			}

			fragment = new EntityRocket(world, x, y, z, d1, d2, d3, 0.0125D);
			world.spawnEntity(fragment);
		}
	}
}
