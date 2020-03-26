package com.hbm.explosion;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.EntityGrenadeTau;
import com.hbm.entity.grenade.EntityGrenadeZOMG;
import com.hbm.entity.particle.EntityChlorineFX;
import com.hbm.entity.particle.EntityCloudFX;
import com.hbm.entity.particle.EntityModFX;
import com.hbm.entity.particle.EntityOrangeFX;
import com.hbm.entity.particle.EntityPinkCloudFX;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.entity.projectile.EntityMiniNuke;
import com.hbm.entity.projectile.EntityRainbow;
import com.hbm.entity.projectile.EntityRocket;
import com.hbm.entity.projectile.EntitySchrab;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ExplosionChaos {

	private final static Random random = new Random();
	private static Random rand = new Random();
	
	public static void explode(World world, int x, int y, int z, int bombStartStrength) {
		MutableBlockPos pos = new BlockPos.MutableBlockPos();
		int r = bombStartStrength;
		int r2 = r * r;
		int r22 = r2 / 2;
		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if (ZZ < r22) {
						destruction(world, pos.setPos(X, Y, Z));
					}
				}
			}
		}
	}
	
	public static void destruction(World world, BlockPos pos) {

		Block b = world.getBlockState(pos).getBlock();
		if (b == Blocks.BEDROCK || b == ModBlocks.reinforced_brick
				|| b == ModBlocks.reinforced_sand
				|| b == ModBlocks.reinforced_glass
				|| b == ModBlocks.reinforced_lamp_on
				|| b == ModBlocks.reinforced_lamp_off) {

		} else {
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}
	
	public static void spawnExplosion(World world, int x, int y, int z, int bound) {

		int randX;
		int randY;
		int randZ;

		for (int i = 0; i < 25; i++) {

			randX = random.nextInt(bound);
			randY = random.nextInt(bound);
			randZ = random.nextInt(bound);

			world.createExplosion(null, x + randX, y + randY, z + randZ, 10.0F, true);
			// ExplosionChaos.explode(world, x + randX, y + randY, z + randZ,
			// 5);

			randX = random.nextInt(bound);
			randY = random.nextInt(bound);
			randZ = random.nextInt(bound);

			world.createExplosion(null, x + randX, y - randY, z + randZ, 10.0F, true);
			// ExplosionChaos.explode(world, x - randX, y + randY, z + randZ,
			// 5);

			randX = random.nextInt(bound);
			randY = random.nextInt(bound);
			randZ = random.nextInt(bound);

			world.createExplosion(null, x + randX, y + randY, z - randZ, 10.0F, true);
			// ExplosionChaos.explode(world, x + randX, y - randY, z + randZ,
			// 5);

			randX = random.nextInt(bound);
			randY = random.nextInt(bound);
			randZ = random.nextInt(bound);

			world.createExplosion(null, x - randX, y + randY, z + randZ, 10.0F, true);
			// ExplosionChaos.explode(world, x + randX, y + randY, z - randZ,
			// 5);
			randX = random.nextInt(bound);
			randY = random.nextInt(bound);
			randZ = random.nextInt(bound);

			world.createExplosion(null, x - randX, y - randY, z + randZ, 10.0F, true);
			// ExplosionChaos.explode(world, x - randX, y - randY, z + randZ,
			// 5);

			randX = random.nextInt(bound);
			randY = random.nextInt(bound);
			randZ = random.nextInt(bound);

			world.createExplosion(null, x - randX, y + randY, z - randZ, 10.0F, true);
			// ExplosionChaos.explode(world, x - randX, y + randY, z - randZ,
			// 5);

			randX = random.nextInt(bound);
			randY = random.nextInt(bound);
			randZ = random.nextInt(bound);

			world.createExplosion(null, x + randX, y - randY, z - randZ, 10.0F, true);
			// ExplosionChaos.explode(world, x + randX, y - randY, z - randZ,
			// 5);

			randX = random.nextInt(bound);
			randY = random.nextInt(bound);
			randZ = random.nextInt(bound);

			world.createExplosion(null, x - randX, y - randY, z - randZ, 10.0F, true);
			// ExplosionChaos.explode(world, x - randX, y - randY, z - randZ,
			// 5);
		}
	}
	
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
	public static void miniMirv(World world, double x, double y, double z) {
		double modifier = 1.25;
		double zeta = Math.sqrt(2) / 2;
		EntityMiniNuke mirv1 = new EntityMiniNuke(world);
		EntityMiniNuke mirv2 = new EntityMiniNuke(world);
		EntityMiniNuke mirv3 = new EntityMiniNuke(world);
		EntityMiniNuke mirv4 = new EntityMiniNuke(world);
		double vx1 = 1;
		double vy1 = rand.nextDouble() * -1;
		double vz1 = 0;

		mirv1.posX = x;
		mirv1.posY = y;
		mirv1.posZ = z;
		mirv1.motionY = vy1;
		mirv2.posX = x;
		mirv2.posY = y;
		mirv2.posZ = z;
		mirv2.motionY = vy1;
		mirv3.posX = x;
		mirv3.posY = y;
		mirv3.posZ = z;
		mirv3.motionY = vy1;
		mirv4.posX = x;
		mirv4.posY = y;
		mirv4.posZ = z;
		mirv4.motionY = vy1;

		mirv1.motionX = vx1 * modifier;
		mirv1.motionZ = vz1 * modifier;
		world.spawnEntity(mirv1);

		mirv2.motionX = -vz1 * modifier;
		mirv2.motionZ = vx1 * modifier;
		world.spawnEntity(mirv2);

		mirv3.motionX = -vx1 * modifier;
		mirv3.motionZ = -vz1 * modifier;
		world.spawnEntity(mirv3);

		mirv4.motionX = vz1 * modifier;
		mirv4.motionZ = -vx1 * modifier;
		world.spawnEntity(mirv4);

		EntityMiniNuke mirv5 = new EntityMiniNuke(world);
		EntityMiniNuke mirv6 = new EntityMiniNuke(world);
		EntityMiniNuke mirv7 = new EntityMiniNuke(world);
		EntityMiniNuke mirv8 = new EntityMiniNuke(world);
		// double vx2 = vx1 < theta ? vx1 + theta : vx1 - theta;
		// double vy2 = vy1;
		// double vz2 = Math.sqrt(Math.pow(1, 2) - Math.pow(vx2, 2));
		double vx2 = zeta;
		double vy2 = vy1;
		double vz2 = zeta;

		mirv5.posX = x;
		mirv5.posY = y;
		mirv5.posZ = z;
		mirv5.motionY = vy2;
		mirv6.posX = x;
		mirv6.posY = y;
		mirv6.posZ = z;
		mirv6.motionY = vy2;
		mirv7.posX = x;
		mirv7.posY = y;
		mirv7.posZ = z;
		mirv7.motionY = vy2;
		mirv8.posX = x;
		mirv8.posY = y;
		mirv8.posZ = z;
		mirv8.motionY = vy2;

		mirv5.motionX = vx2 * modifier;
		mirv5.motionZ = vz2 * modifier;
		world.spawnEntity(mirv5);

		mirv6.motionX = -vz2 * modifier;
		mirv6.motionZ = vx2 * modifier;
		world.spawnEntity(mirv6);

		mirv7.motionX = -vx2 * modifier;
		mirv7.motionZ = -vz2 * modifier;
		world.spawnEntity(mirv7);

		mirv8.motionX = vz2 * modifier;
		mirv8.motionZ = -vx2 * modifier;
		world.spawnEntity(mirv8);
	}
	
	public static void explodeZOMG(World world, int x, int y, int z, int bombStartStrength) {
		MutableBlockPos pos = new BlockPos.MutableBlockPos();
		int r = bombStartStrength;
		int r2 = r * r;
		int r22 = r2 / 2;
		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if (ZZ < r22) {
						pos.setPos(X, Y, Z);
						if (!(world.getBlockState(pos).getBlock() == Blocks.BEDROCK && Y <= 0))
							world.setBlockToAir(pos);
					}
				}
			}
		}
	}
	
	public static void frag(World world, int x, int y, int z, int count, boolean flame, Entity shooter) {

		double d1 = 0;
		double d2 = 0;
		double d3 = 0;
		EntityArrow fragment;

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

			fragment = new EntityTippedArrow(world, x, y, z);

			fragment.motionX = d1;
			fragment.motionY = d2;
			fragment.motionZ = d3;
			fragment.shootingEntity = shooter;

			fragment.setIsCritical(true);
			if (flame) {
				fragment.setFire(1000);
			}

			fragment.setDamage(2.5);

			world.spawnEntity(fragment);
		}
	}
	
	public static void schrab(World world, int x, int y, int z, int count, int gravity) {

		double d1 = 0;
		double d2 = 0;
		double d3 = 0;
		EntitySchrab fragment;

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

			fragment = new EntitySchrab(world, x, y, z, d1, d2, d3, 0.0125D);

			world.spawnEntity(fragment);
		}
	}
	
	public static void pulse(World world, int x, int y, int z, int bombStartStrength) {

		int r = bombStartStrength;
		int r2 = r * r;
		int r22 = r2 / 2;
		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if (ZZ < r22) {
						pDestruction(world, X, Y, Z);
					}
				}
			}
		}
	}
	
	public static void pDestruction(World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		IBlockState state = world.getBlockState(pos);
        EntityFallingBlock entityfallingblock = new EntityFallingBlock(world, (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), state);
        world.spawnEntity(entityfallingblock);
        
        /*
		if (Blocks.air.getBlockHardness(world, x, y, z) != Float.POSITIVE_INFINITY) {
			Block b = world.getBlock(x, y, z);
			TileEntity t = world.getTileEntity(x, y, z);

			if (b == Blocks.sandstone || b == Blocks.sandstone_stairs)
				world.setBlock(x, y, z, Blocks.sand);
			else if (t != null && t instanceof ISource)
				world.setBlock(x, y, z, ModBlocks.block_electrical_scrap);
			else if (t != null && t instanceof IConductor)
				world.setBlock(x, y, z, ModBlocks.block_electrical_scrap);
			else if (t != null && t instanceof IConsumer)
				world.setBlock(x, y, z, ModBlocks.block_electrical_scrap);
			else if (b == Blocks.sand)
				world.setBlock(x, y, z, Blocks.sand);
			else if (b == Blocks.gravel)
				world.setBlock(x, y, z, Blocks.gravel);
			else if (b == ModBlocks.gravel_obsidian)
				world.setBlock(x, y, z, ModBlocks.gravel_obsidian);
			else if (b == ModBlocks.block_electrical_scrap)
				world.setBlock(x, y, z, ModBlocks.block_electrical_scrap);
			else if (b == ModBlocks.block_scrap)
				world.setBlock(x, y, z, ModBlocks.block_scrap);
			else if (b == ModBlocks.brick_obsidian)
				world.setBlock(x, y, z, ModBlocks.gravel_obsidian);
			else if (b.getMaterial() == Material.anvil)
				world.setBlock(x, y, z, Blocks.gravel);
			else if (b.getMaterial() == Material.clay)
				world.setBlock(x, y, z, Blocks.sand);
			else if (b.getMaterial() == Material.grass)
				world.setBlock(x, y, z, Blocks.sand);
			else if (b.getMaterial() == Material.ground)
				world.setBlock(x, y, z, Blocks.sand);
			else if (b.getMaterial() == Material.iron)
				world.setBlock(x, y, z, Blocks.gravel);
			else if (b.getMaterial() == Material.piston)
				world.setBlock(x, y, z, Blocks.gravel);
			else if (b.getMaterial() == Material.rock)
				world.setBlock(x, y, z, Blocks.gravel);
			else if (b.getMaterial() == Material.sand)
				world.setBlock(x, y, z, Blocks.sand);
			else if (b.getMaterial() == Material.tnt)
				world.setBlock(x, y, z, ModBlocks.block_scrap);
			else
				world.setBlock(x, y, z, Blocks.air);
		}*/
	}
	
	public static void plasma(World world, int x, int y, int z, int radius) {
		MutableBlockPos pos = new BlockPos.MutableBlockPos();
		int r = radius;
		int r2 = r * r;
		int r22 = r2 / 2;
		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if (ZZ < r22 + world.rand.nextInt(r22 / 2)) {
						pos.setPos(X, Y, Z);
						Block block = world.getBlockState(pos).getBlock();
						//TODO statue
						if (block != Blocks.BEDROCK/* && world.getBlock(X, Y, Z) != ModBlocks.statue_elb
								&& world.getBlock(X, Y, Z) != ModBlocks.statue_elb_g
								&& world.getBlock(X, Y, Z) != ModBlocks.statue_elb_w
								&& world.getBlock(X, Y, Z) != ModBlocks.statue_elb_f*/)
							world.setBlockState(pos, ModBlocks.plasma.getDefaultState());
					}
				}
			}
		}
	}
	
	//Drillgon200: This method name irks me.
	public static void tauMeSinPi(World world, double x, double y, double z, int count, Entity shooter, EntityGrenadeTau tau) {

		double d1 = 0;
		double d2 = 0;
		double d3 = 0;
		EntityBullet fragment;

		if (shooter != null && shooter instanceof EntityPlayer)
			for (int i = 0; i < count; i++) {
				d1 = rand.nextDouble();
				d2 = rand.nextDouble();
				d3 = rand.nextDouble();

				if (rand.nextInt(2) == 0) {
					d1 *= -1;
				}

				if (rand.nextInt(2) == 0) {
					d2 *= -1;
				}

				if (rand.nextInt(2) == 0) {
					d3 *= -1;
				}

				if (rand.nextInt(5) == 0) {
					fragment = new EntityBullet(world, (EntityPlayer) shooter, 3.0F, 35, 45, false, "tauDay", tau);
					fragment.setDamage(rand.nextInt(301) + 100);
				} else {
					fragment = new EntityBullet(world, (EntityPlayer) shooter, 3.0F, 35, 45, false, "eyyOk", tau);
					fragment.setDamage(rand.nextInt(11) + 35);
				}

				fragment.motionX = d1 * 5;
				fragment.motionY = d2 * 5;
				fragment.motionZ = d3 * 5;
				fragment.shootingEntity = shooter;

				fragment.setIsCritical(true);

				world.spawnEntity(fragment);
			}
	}
	
	//Drillgon200: You know what? I'm changing this one.
	public static void zomg(World world, double x, double y, double z, int count, Entity shooter, EntityGrenadeZOMG zomg) {

		double d1 = 0;
		double d2 = 0;
		double d3 = 0;

		// if (shooter != null && shooter instanceof EntityPlayer)
		for (int i = 0; i < count; i++) {
			d1 = rand.nextDouble();
			d2 = rand.nextDouble();
			d3 = rand.nextDouble();

			if (rand.nextInt(2) == 0) {
				d1 *= -1;
			}

			if (rand.nextInt(2) == 0) {
				d2 *= -1;
			}

			if (rand.nextInt(2) == 0) {
				d3 *= -1;
			}

			EntityRainbow entityZomg = new EntityRainbow(world, (EntityPlayer) shooter, 1F, 10000, 100000, zomg);

			entityZomg.motionX = d1;// * 5;
			entityZomg.motionY = d2;// * 5;
			entityZomg.motionZ = d3;// * 5;
			entityZomg.shootingEntity = shooter;

			world.spawnEntity(entityZomg);
			world.playSound(null, zomg.posX, zomg.posY, zomg.posZ, HBMSoundHandler.zomgShoot, SoundCategory.AMBIENT, 10.0F, 0.8F + (rand.nextFloat() * 0.4F));
		}
	}
	
	public static void spawnVolley(World world, double x, double y, double z, int count, double speed) {
		
		for(int i = 0; i < count; i++) {
			
			EntityModFX fx = new EntityOrangeFX(world, x, y, z, 0.0, 0.0, 0.0);
			
			fx.motionX = rand.nextGaussian() * speed;
			fx.motionZ = rand.nextGaussian() * speed;
			
			fx.motionY = rand.nextDouble() * speed * 7.5D;
			
			world.spawnEntity(fx);
		}
	}
	
	public static void floater(World world, BlockPos pos, int radi, int height){
		floater(world, pos.getX(), pos.getY(), pos.getZ(), radi, height);
	}
	
	public static void floater(World world, int x, int y, int z, int radi, int height) {
		MutableBlockPos pos = new BlockPos.MutableBlockPos();
		IBlockState save;

		int r = radi;
		int r2 = r * r;
		int r22 = r2 / 2;
		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if (ZZ < r22) {
						pos.setPos(X, Y, Z);
						save = world.getBlockState(pos);
						world.setBlockState(pos, Blocks.AIR.getDefaultState());
						if (save.getBlock() != Blocks.AIR) {
							world.setBlockState(pos.setPos(X, Y + height, Z), save);
						}
					}
				}
			}
		}

	}
	
	public static void move(World world, BlockPos pos, int radius, int a, int b, int c){
		move(world, pos.getX(), pos.getY(), pos.getZ(), radius, a, b, c);
	}

	public static void move(World world, int x, int y, int z, int radius, int a, int b, int c) {
		float f = radius;
		int i;
		int j;
		int k;
		double d5;
		double d6;
		double d7;
		double wat = radius;
		int rand = 0;

		radius *= 2.0F;
		i = MathHelper.floor(x - wat - 1.0D);
		j = MathHelper.floor(x + wat + 1.0D);
		k = MathHelper.floor(y - wat - 1.0D);
		int i2 = MathHelper.floor(y + wat + 1.0D);
		int l = MathHelper.floor(z - wat - 1.0D);
		int j2 = MathHelper.floor(z + wat + 1.0D);
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(i, k, l, j, i2, j2));

		for (int i1 = 0; i1 < list.size(); ++i1) {
			Entity entity = (Entity) list.get(i1);
			double d4 = entity.getDistance(x, y, z) / radius;

			if (d4 <= 1.0D) {
				d5 = entity.posX - x;
				d6 = entity.posY + entity.getEyeHeight() - y;
				d7 = entity.posZ - z;
				if (entity instanceof EntityLiving && !(entity instanceof EntitySheep)) {
					rand = random.nextInt(2);
					if (rand == 0) {
						((EntityLiving) entity).setCustomNameTag("Dinnerbone");
					} else {
						((EntityLiving) entity).setCustomNameTag("Grumm");
					}
				}

				if (entity instanceof EntitySheep) {
					((EntityLiving) entity).setCustomNameTag("jeb_");
				}

				double d9 = MathHelper.sqrt(d5 * d5 + d6 * d6 + d7 * d7);
				if (d9 < wat) {
					entity.setPosition(entity.posX += a, entity.posY += b, entity.posZ += c);
				}
			}
		}

		radius = (int) f;
	}
}
