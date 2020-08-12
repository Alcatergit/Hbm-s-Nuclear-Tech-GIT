package com.hbm.handler.guncfg;

import java.util.List;
import java.util.Random;

import com.hbm.entity.particle.EntityBSmokeFX;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.ArmorUtil;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.interfaces.IBulletImpactBehavior;
import com.hbm.interfaces.IBulletUpdateBehavior;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class BulletConfigFactory {

	/// configs should never be loaded manually due to syncing issues: use the
		/// syncing util and pass the UID in the DW of the bullet to make the client
		/// load the config correctly ////

	public static BulletConfiguration getTestConfig() {

		BulletConfiguration bullet = new BulletConfiguration();

		bullet.ammo = ModItems.gun_revolver_ammo;
		bullet.velocity = 5.0F;
		bullet.spread = 0.05F;
		bullet.wear = 10;
		bullet.dmgMin = 15;
		bullet.dmgMax = 17;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 10;
		bullet.HBRC = 2;
		bullet.LBRC = 90;
		bullet.bounceMod = 0.8;
		bullet.doesPenetrate = false;
		bullet.doesBreakGlass = true;
		bullet.style = 0;
		bullet.plink = 1;

		return bullet;

	}

	/// STANDARD CONFIGS ///
	// do not include damage or ammo
	public static BulletConfiguration standardBulletConfig() {

		BulletConfiguration bullet = new BulletConfiguration();

		bullet.velocity = 5.0F;
		bullet.spread = 0.005F;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 5;
		bullet.HBRC = 2;
		bullet.LBRC = 95;
		bullet.bounceMod = 0.8;
		bullet.doesPenetrate = true;
		bullet.doesBreakGlass = true;
		bullet.destroysBlocks = false;
		bullet.style = BulletConfiguration.STYLE_NORMAL;
		bullet.plink = BulletConfiguration.PLINK_BULLET;
		bullet.leadChance = 5;

		return bullet;
	}

	public static BulletConfiguration standardNukeConfig() {

		BulletConfiguration bullet = new BulletConfiguration();

		bullet.velocity = 3.0F;
		bullet.spread = 0.005F;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.dmgMin = 1000;
		bullet.dmgMax = 1000;
		bullet.gravity = 0.025D;
		bullet.maxAge = 300;
		bullet.doesRicochet = false;
		bullet.ricochetAngle = 0;
		bullet.HBRC = 0;
		bullet.LBRC = 0;
		bullet.bounceMod = 1.0;
		bullet.doesPenetrate = false;
		bullet.doesBreakGlass = false;
		bullet.nuke = 35;
		bullet.style = BulletConfiguration.STYLE_NUKE;
		bullet.plink = BulletConfiguration.PLINK_GRENADE;

		return bullet;
	}

	public static BulletConfiguration standardBuckshotConfig() {

		BulletConfiguration bullet = new BulletConfiguration();

		bullet.velocity = 5.0F;
		bullet.spread = 0.05F;
		bullet.wear = 10;
		bullet.bulletsMin = 5;
		bullet.bulletsMax = 8;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 5;
		bullet.HBRC = 10;
		bullet.LBRC = 95;
		bullet.bounceMod = 0.8;
		bullet.doesPenetrate = false;
		bullet.doesBreakGlass = true;
		bullet.style = BulletConfiguration.STYLE_PELLET;
		bullet.plink = BulletConfiguration.PLINK_BULLET;
		bullet.leadChance = 10;

		return bullet;
	}

	public static BulletConfiguration standardRocketConfig() {

		BulletConfiguration bullet = new BulletConfiguration();

		bullet.velocity = 2.0F;
		bullet.spread = 0.005F;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0.005D;
		bullet.maxAge = 300;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 10;
		bullet.HBRC = 2;
		bullet.LBRC = 100;
		bullet.bounceMod = 0.8;
		bullet.doesPenetrate = false;
		bullet.doesBreakGlass = false;
		bullet.explosive = 5.0F;
		bullet.style = BulletConfiguration.STYLE_ROCKET;
		bullet.plink = BulletConfiguration.PLINK_GRENADE;

		return bullet;
	}

	public static BulletConfiguration standardGrenadeConfig() {

		BulletConfiguration bullet = new BulletConfiguration();

		bullet.velocity = 2.0F;
		bullet.spread = 0.005F;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0.035D;
		bullet.maxAge = 300;
		bullet.doesRicochet = false;
		bullet.ricochetAngle = 0;
		bullet.HBRC = 0;
		bullet.LBRC = 0;
		bullet.bounceMod = 1.0;
		bullet.doesPenetrate = false;
		bullet.doesBreakGlass = false;
		bullet.explosive = 2.5F;
		bullet.style = BulletConfiguration.STYLE_GRENADE;
		bullet.plink = BulletConfiguration.PLINK_GRENADE;
		bullet.vPFX = "smoke";

		return bullet;
	}
	
	public static BulletConfiguration standardAirstrikeConfig() {

		BulletConfiguration bullet = new BulletConfiguration();

		bullet.velocity = 5.0F;
		bullet.spread = 0.0F;
		bullet.wear = 50;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = false;
		bullet.doesPenetrate = false;
		bullet.doesBreakGlass = false;
		bullet.style = BulletConfiguration.STYLE_BOLT;
		bullet.leadChance = 0;
		bullet.vPFX = "reddust";

		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {

				if(bullet.world.isRemote)
					return;

				Random rand = bullet.world.rand;
				int count = rand.nextInt(11) + 95;

				for(int i = 0; i < count; i++) {

					double dx = bullet.posX + rand.nextGaussian() * 4;
					double dy = bullet.posY + 25 + rand.nextGaussian() * 5;
					double dz = bullet.posZ + rand.nextGaussian() * 4;

					Vec3 motion = Vec3.createVectorHelper(bullet.posX - dx, bullet.posY - dy, bullet.posZ - dz);
					motion = motion.normalize();

					EntityBulletBase bolt = new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.R556_FLECHETTE_DU);
					bolt.setPosition(dx, dy, dz);
					bolt.shoot(motion.xCoord, motion.yCoord, motion.zCoord, 0.5F, 0.1F);
					bullet.world.spawnEntity(bolt);

					if(i < 30) {
						EntityBSmokeFX bsmoke = new EntityBSmokeFX(bullet.world);
						bsmoke.setPosition(dx, dy, dz);
						bullet.world.spawnEntity(bsmoke);
					}
				}
			}
		};

		return bullet;
	}
	
	public static IBulletImpactBehavior getPhosphorousEffect(final int radius, final int duration, final int count, final double motion) {
		
		IBulletImpactBehavior impact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				
				List<Entity> hit = bullet.world.getEntitiesWithinAABBExcludingEntity(bullet, new AxisAlignedBB(bullet.posX - radius, bullet.posY - radius, bullet.posZ - radius, bullet.posX + radius, bullet.posY + radius, bullet.posZ + radius));
				
				for(Entity e : hit) {
					
					if(!Library.isObstructed(bullet.world, bullet.posX, bullet.posY, bullet.posZ, e.posX, e.posY + e.getEyeHeight(), e.posZ)) {
						e.setFire(5);
						
						if(e instanceof EntityLivingBase) {
							
							PotionEffect eff = new PotionEffect(HbmPotion.phosphorus, duration, 0, true, false);
							eff.getCurativeItems().clear();
							((EntityLivingBase)e).addPotionEffect(eff);
						}
					}
				}
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaburst");
				data.setString("mode", "flame");
				data.setInteger("count", count);
				data.setDouble("motion", motion);
				
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, bullet.posX, bullet.posY, bullet.posZ), new TargetPoint(bullet.dimension, bullet.posX, bullet.posY, bullet.posZ, 50));
			}
		};
		
		return impact;
	}
	
	public static IBulletImpactBehavior getGasEffect(final int radius, final int duration) {
		
		IBulletImpactBehavior impact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				
				List<Entity> hit = bullet.world.getEntitiesWithinAABBExcludingEntity(bullet, new AxisAlignedBB(bullet.posX - radius, bullet.posY - radius, bullet.posZ - radius, bullet.posX + radius, bullet.posY + radius, bullet.posZ + radius));
				
				for(Entity e : hit) {
					
					if(!Library.isObstructed(bullet.world, bullet.posX, bullet.posY, bullet.posZ, e.posX, e.posY + e.getEyeHeight(), e.posZ)) {
						
						if(e instanceof EntityLivingBase) {
							
							if(e instanceof EntityPlayer && ArmorUtil.checkForGasMask((EntityPlayer) e))
								continue;

							PotionEffect eff0 = new PotionEffect(MobEffects.POISON, duration, 2, true, false);
							PotionEffect eff1 = new PotionEffect(MobEffects.MINING_FATIGUE, duration, 2, true, false);
							PotionEffect eff2 = new PotionEffect(MobEffects.WEAKNESS, duration, 4, true, false);
							eff0.getCurativeItems().clear();
							eff1.getCurativeItems().clear();
							eff2.getCurativeItems().clear();
							((EntityLivingBase)e).addPotionEffect(eff0);
							((EntityLivingBase)e).addPotionEffect(eff1);
							((EntityLivingBase)e).addPotionEffect(eff2);
						}
					}
				}
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaburst");
				data.setString("mode", "cloud");
				data.setInteger("count", 15);
				data.setDouble("motion", 0.1D);
				
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, bullet.posX, bullet.posY, bullet.posZ), new TargetPoint(bullet.dimension, bullet.posX, bullet.posY, bullet.posZ, 50));
			}
		};
		
		return impact;
	}
	
	public static IBulletUpdateBehavior getLaserSteering() {

		IBulletUpdateBehavior onUpdate = new IBulletUpdateBehavior() {

			@Override
			public void behaveUpdate(EntityBulletBase bullet) {

				if(bullet.shooter == null || !(bullet.shooter instanceof EntityPlayer))
					return;
				
				if(Vec3.createVectorHelper(bullet.posX - bullet.shooter.posX, bullet.posY - bullet.shooter.posY, bullet.posZ - bullet.shooter.posZ).lengthVector() > 100)
					return;

				RayTraceResult mop = Library.rayTraceIncludeEntities((EntityPlayer)bullet.shooter, 200, 1);
				
				if(mop == null || mop.hitVec == null)
					return;

				Vec3 vec = Vec3.createVectorHelper(mop.hitVec.x - bullet.posX, mop.hitVec.y - bullet.posY, mop.hitVec.z - bullet.posZ);

				if(vec.lengthVector() < 1)
					return;

				vec = vec.normalize();

				double speed = Vec3.createVectorHelper(bullet.motionX, bullet.motionY, bullet.motionZ).lengthVector();

				bullet.motionX = vec.xCoord * speed;
				bullet.motionY = vec.yCoord * speed;
				bullet.motionZ = vec.zCoord * speed;
			}

		};

		return onUpdate;
	}
}
