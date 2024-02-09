package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletImpactBehavior;
import com.hbm.interfaces.IBulletUpdateBehavior;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.misc.RenderScreenOverlay.Crosshair;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class GunFatmanFactory {
	
public static GunConfiguration getFatmanConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 20;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 120;
		config.firingDuration = 0;
		config.ammoCap = 1;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCUMFLEX;
		config.firingSound = HBMSoundHandler.fatmanShoot;
		config.reloadSound = GunConfiguration.RSOUND_FATMAN;
		config.reloadSoundEnd = false;
		
		config.name = "M-42 Tactical Nuclear Catapult";
		config.manufacturer = "Fort Strong";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.NUKE_NORMAL);
		config.config.add(BulletConfigSyncingUtil.NUKE_LOW);
		config.config.add(BulletConfigSyncingUtil.NUKE_HIGH);
		config.config.add(BulletConfigSyncingUtil.NUKE_TOTS);
		config.config.add(BulletConfigSyncingUtil.NUKE_SAFE);
		config.config.add(BulletConfigSyncingUtil.NUKE_PUMPKIN);
		config.durability = 1000;
		
		return config;
	}
	
	public static GunConfiguration getMIRVConfig() {
		
		GunConfiguration config = getFatmanConfig();
		
		config.name = "M-42 Experimental MIRV";
		config.manufacturer = "Fort Strong";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_NORMAL);
		config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_LOW);
		config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_HIGH);
		config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_SAFE);
		config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_SPECIAL);
		config.durability = 1000;
		
		return config;
	}
	
	public static GunConfiguration getBELConfig() {
		
		GunConfiguration config = getFatmanConfig();
		
		config.name = "Balefire Egg Launcher";
		config.manufacturer = "Fort Strong";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.NUKE_AMAT);
		
		return config;
	}
	
	public static GunConfiguration getProtoConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 20;
		config.roundsPerCycle = 8;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 120;
		config.firingDuration = 0;
		config.ammoCap = 8;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCUMFLEX;
		config.firingSound = HBMSoundHandler.fatmanShoot;
		config.reloadSound = GunConfiguration.RSOUND_FATMAN;
		config.reloadSoundEnd = false;
		
		config.name = "M-42 Tactical Nuclear Catapult";
		config.manufacturer = "Fort Strong";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.NUKE_PROTO_NORMAL);
		config.config.add(BulletConfigSyncingUtil.NUKE_PROTO_LOW);
		config.config.add(BulletConfigSyncingUtil.NUKE_PROTO_HIGH);
		config.config.add(BulletConfigSyncingUtil.NUKE_PROTO_TOTS);
		config.config.add(BulletConfigSyncingUtil.NUKE_PROTO_SAFE);
		config.config.add(BulletConfigSyncingUtil.NUKE_PROTO_PUMPKIN);
		config.durability = 1000;
		
		return config;
	}
	
	public static BulletConfiguration getNukeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		bullet.ammo = ModItems.ammo_nuke;
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				BulletConfigFactory.nuclearExplosion(bullet, x, y, z, 35);
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getNukeLowConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		bullet.ammo = ModItems.ammo_nuke_low;
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				BulletConfigFactory.nuclearExplosion(bullet, x, y, z, 20);
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getNukeHighConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		bullet.ammo = ModItems.ammo_nuke_high;
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				BulletConfigFactory.nuclearExplosion(bullet, x, y, z, 50);
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getNukeTotsConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		bullet.ammo = ModItems.ammo_nuke_tots;
		bullet.bulletsMin = 8;
		bullet.bulletsMax = 8;
		bullet.spread = 0.1F;
		bullet.style = bullet.STYLE_GRENADE;
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				BulletConfigFactory.nuclearExplosion(bullet, x, y, z, 10);
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getNukeSafeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		bullet.ammo = ModItems.ammo_nuke_safe;
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				BulletConfigFactory.nuclearExplosion(bullet, x, y, z, 0);
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getNukePumpkinConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		bullet.ammo = ModItems.ammo_nuke_pumpkin;
		bullet.explosive = 10F;
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				
				if(bullet.world.isRemote) {

					double posX = bullet.posX;
					double posY = bullet.posY + 0.5;
					double posZ = bullet.posZ;
					
					if(y >= 0) {
						posX = x + 0.5;
						posY = y + 1.5;
						posZ = z + 0.5;
					}
					
					ExplosionLarge.spawnParticles(bullet.world, posX, posY, posZ, 45);
				}
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getMirvConfig() {
		
		BulletConfiguration bullet = getNukeConfig();
		
		bullet.ammo = ModItems.ammo_mirv;
		bullet.style = BulletConfiguration.STYLE_MIRV;
		bullet.velocity *= 3;
		
		bullet.bUpdate = new IBulletUpdateBehavior() {

			@Override
			public void behaveUpdate(EntityBulletBase bullet) {
				
				if(bullet.world.isRemote)
					return;
				
				if(bullet.ticksExisted == 15) {
					bullet.setDead();
					
					for(int i = 0; i < 6; i++) {
						
						EntityBulletBase nuke = new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.NUKE_NORMAL);
						nuke.setPosition(bullet.posX, bullet.posY, bullet.posZ);
						double mod = 0.1D;
						nuke.motionX = bullet.world.rand.nextGaussian() * mod;
						nuke.motionY = -0.1D;
						nuke.motionZ = bullet.world.rand.nextGaussian() * mod;
						bullet.world.spawnEntity(nuke);
					}
				}
			}
			
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getMirvLowConfig() {
		
		BulletConfiguration bullet = getNukeLowConfig();
		
		bullet.ammo = ModItems.ammo_mirv_low;
		bullet.style = BulletConfiguration.STYLE_MIRV;
		bullet.velocity *= 3;
		
		bullet.bUpdate = new IBulletUpdateBehavior() {

			@Override
			public void behaveUpdate(EntityBulletBase bullet) {
				
				if(bullet.world.isRemote)
					return;
				
				if(bullet.ticksExisted == 15) {
					bullet.setDead();
					
					for(int i = 0; i < 6; i++) {
						
						EntityBulletBase nuke = new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.NUKE_LOW);
						nuke.setPosition(bullet.posX, bullet.posY, bullet.posZ);
						double mod = 0.1D;
						nuke.motionX = bullet.world.rand.nextGaussian() * mod;
						nuke.motionY = -0.1D;
						nuke.motionZ = bullet.world.rand.nextGaussian() * mod;
						bullet.world.spawnEntity(nuke);
					}
				}
			}
			
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getMirvHighConfig() {
		
		BulletConfiguration bullet = getNukeHighConfig();
		
		bullet.ammo = ModItems.ammo_mirv_high;
		bullet.style = BulletConfiguration.STYLE_MIRV;
		bullet.velocity *= 3;
		
		bullet.bUpdate = new IBulletUpdateBehavior() {

			@Override
			public void behaveUpdate(EntityBulletBase bullet) {
				
				if(bullet.world.isRemote)
					return;
				
				if(bullet.ticksExisted == 15) {
					bullet.setDead();
					
					for(int i = 0; i < 6; i++) {
						
						EntityBulletBase nuke = new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.NUKE_HIGH);
						nuke.setPosition(bullet.posX, bullet.posY, bullet.posZ);
						double mod = 0.1D;
						nuke.motionX = bullet.world.rand.nextGaussian() * mod;
						nuke.motionY = -0.1D;
						nuke.motionZ = bullet.world.rand.nextGaussian() * mod;
						bullet.world.spawnEntity(nuke);
					}
				}
			}
			
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getMirvSafeConfig() {
		
		BulletConfiguration bullet = getNukeSafeConfig();
		
		bullet.ammo = ModItems.ammo_mirv_safe;
		bullet.style = BulletConfiguration.STYLE_MIRV;
		bullet.velocity *= 3;
		
		bullet.bUpdate = new IBulletUpdateBehavior() {

			@Override
			public void behaveUpdate(EntityBulletBase bullet) {
				
				if(bullet.world.isRemote)
					return;
				
				if(bullet.ticksExisted == 15) {
					bullet.setDead();
					
					for(int i = 0; i < 6; i++) {
						
						EntityBulletBase nuke = new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.NUKE_SAFE);
						nuke.setPosition(bullet.posX, bullet.posY, bullet.posZ);
						double mod = 0.1D;
						nuke.motionX = bullet.world.rand.nextGaussian() * mod;
						nuke.motionY = -0.1D;
						nuke.motionZ = bullet.world.rand.nextGaussian() * mod;
						bullet.world.spawnEntity(nuke);
					}
				}
			}
			
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getMirvSpecialConfig() {
		
		BulletConfiguration bullet = getNukeConfig();
		
		bullet.ammo = ModItems.ammo_mirv_special;
		bullet.style = BulletConfiguration.STYLE_MIRV;
		bullet.velocity *= 3;
		
		bullet.bUpdate = new IBulletUpdateBehavior() {

			@Override
			public void behaveUpdate(EntityBulletBase bullet) {
				
				if(bullet.world.isRemote)
					return;
				
				if(bullet.ticksExisted == 15) {
					bullet.setDead();
					
					for(int i = 0; i < 24; i++) {
						
						EntityBulletBase nuke = null;
						
						if(i < 6)
							nuke = new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.NUKE_LOW);
						else if(i < 12)
							nuke = new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.NUKE_TOTS);
						else if(i < 18)
							nuke = new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.NUKE_NORMAL);
						else
							nuke = new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.NUKE_AMAT);
						
						nuke.setPosition(bullet.posX, bullet.posY, bullet.posZ);
						
						double mod = 0.25D;
						nuke.motionX = bullet.world.rand.nextGaussian() * mod;
						nuke.motionY = -0.1D;
						nuke.motionZ = bullet.world.rand.nextGaussian() * mod;
						bullet.world.spawnEntity(nuke);
					}
				}
			}
			
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getBalefireConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		
		bullet.ammo = ModItems.gun_bf_ammo;
		bullet.style = BulletConfiguration.STYLE_BF;
		
		bullet.bImpact = new IBulletImpactBehavior() {
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				
				if(!bullet.world.isRemote) {

					double posX = bullet.posX;
					double posY = bullet.posY + 0.5;
					double posZ = bullet.posZ;
					
					if(y >= 0) {
						posX = x + 0.5;
						posY = y + 1.5;
						posZ = z + 0.5;
					}
					
					bullet.world.playSound(null, x, y, z, HBMSoundHandler.mukeExplosion, SoundCategory.HOSTILE, 15.0F, 1.0F);
					
					ExplosionLarge.spawnShrapnels(bullet.world, posX, posY, posZ, 25);
					
					ExplosionNT exp = new ExplosionNT(bullet.world, null, posX, posY, posZ, 15F)
							.addAttrib(ExAttrib.BALEFIRE)
							.addAttrib(ExAttrib.NOPARTICLE)
							.addAttrib(ExAttrib.NOSOUND)
							.addAttrib(ExAttrib.NODROP)
							.addAttrib(ExAttrib.NOHURT)
							.overrideResolution(64);
					exp.explode();
					
					if(BombConfig.enableNukeClouds) {
						EntityNukeTorex.statFacBale(bullet.world, posX, posY, posZ, 15);
					}
				}
			}
		};
		return bullet;
	}
}
