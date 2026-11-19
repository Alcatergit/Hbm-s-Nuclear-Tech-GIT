package com.hbm.handler.guncfg;

import static com.hbm.items.ItemEnums.EnumAmmo;

import java.util.ArrayList;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityBalefire;
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
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationKeyframe.InterpolationType;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.misc.RenderScreenOverlay.Crosshair;

import net.minecraft.util.SoundCategory;

public class GunFatmanFactory {
	
public static GunConfiguration getFatmanConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 20;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 55;
		config.firingDuration = 0;
		config.ammoCap = 1;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCUMFLEX;
		config.firingSound = HBMSoundHandler.fireFatman;
		config.reloadSound = HBMSoundHandler.fatmanFull;
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
		config.config.add(BulletConfigSyncingUtil.NUKE_AMAT);
		config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_NORMAL);
		config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_LOW);
		config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_HIGH);
		config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_SAFE);
		config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_SPECIAL);
		config.durability = 1000;
		addFatmanAnims(config);
		
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
		config.reloadDuration = 55;
		config.firingDuration = 0;
		config.ammoCap = 8;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCUMFLEX;
		config.firingSound = HBMSoundHandler.fireFatman;
		config.reloadSound = HBMSoundHandler.fatmanFull;
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
		config.config.add(BulletConfigSyncingUtil.NUKE_PROTO_AMAT);
		config.durability = 1000;
		addFatmanAnims(config);
		
		return config;
	}
	
	private static void addFatmanAnims(GunConfiguration config) {
		config.animations.put(AnimType.EQUIP, new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(60, 0, 0, 0, InterpolationType.SMOOTH))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 1000, InterpolationType.SIN_DOWN))));

		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("GAUGE", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 135, 100, InterpolationType.SIN_DOWN))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 500, InterpolationType.SIN_DOWN)))
				.addBus("PISTON", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 3, 100, InterpolationType.SIN_UP)))
				.addBus("NUKE", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 3, 100, InterpolationType.SIN_UP))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 0, InterpolationType.SMOOTH))));

		config.animations.put(AnimType.RELOAD, new BusAnimation()
				.addBus("LID", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 250, InterpolationType.SMOOTH))
						.addKeyframe(new BusAnimationKeyframe(0, 0, -45, 250, InterpolationType.SIN_UP))
						.addKeyframe(new BusAnimationKeyframe(0, 0, -45, 1200, InterpolationType.SMOOTH))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 250, InterpolationType.SIN_UP)))
				.addBus("HANDLE", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, -2, 500, InterpolationType.SIN_FULL))
						.addKeyframe(new BusAnimationKeyframe(0, 0, -2, 1700, InterpolationType.SMOOTH))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 750, InterpolationType.SIN_FULL)))
				.addBus("NUKE", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(5, -4, 3, 0, InterpolationType.SMOOTH))
						.addKeyframe(new BusAnimationKeyframe(5, -4, 3, 750, InterpolationType.SMOOTH))
						.addKeyframe(new BusAnimationKeyframe(2, 0.5, 3, 500, InterpolationType.SIN_UP))
						.addKeyframe(new BusAnimationKeyframe(1, 0.5, 3, 100, InterpolationType.SMOOTH))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 3, 100, InterpolationType.SMOOTH))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 3, 750, InterpolationType.SMOOTH))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 750, InterpolationType.SIN_FULL)))
				.addBus("PISTON", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 3, 0, InterpolationType.SMOOTH))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 3, 2200, InterpolationType.SMOOTH))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 750, InterpolationType.SIN_FULL)))
				.addBus("EQUIP", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(5, 0, 0, 500, InterpolationType.SIN_FULL))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 500, InterpolationType.SIN_FULL))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 450, InterpolationType.SMOOTH))
						.addKeyframe(new BusAnimationKeyframe(3, 0, 0, 100, InterpolationType.SIN_DOWN))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 100, InterpolationType.SIN_FULL))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 500, InterpolationType.SMOOTH))
						.addKeyframe(new BusAnimationKeyframe(-10, 0, 0, 375, InterpolationType.SIN_DOWN))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 375, InterpolationType.SIN_UP))));

		config.animations.put(AnimType.INSPECT, new BusAnimation()
				.addBus("HANDLE", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 250, InterpolationType.SMOOTH))
						.addKeyframe(new BusAnimationKeyframe(0, 0, -2, 250, InterpolationType.SIN_FULL))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 250, InterpolationType.SIN_FULL))
						.addKeyframe(new BusAnimationKeyframe(0, 0, -2, 250, InterpolationType.SIN_FULL))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 250, InterpolationType.SIN_FULL)))
				.addBus("EQUIP", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(-15, 0, 0, 250, InterpolationType.SIN_FULL))
						.addKeyframe(new BusAnimationKeyframe(-15, 0, 0, 1000, InterpolationType.SMOOTH))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 250, InterpolationType.SIN_FULL))));
	}

	public static BulletConfiguration getNukeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		bullet.ammo = ModItems.ammo_standard;
		bullet.ammoMeta = EnumAmmo.NUKE_STANDARD.ordinal();
		
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
		bullet.ammo = ModItems.ammo_standard;
		bullet.ammoMeta = EnumAmmo.NUKE_LOW.ordinal();
		
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
		bullet.ammo = ModItems.ammo_standard;
		bullet.ammoMeta = EnumAmmo.NUKE_HIGH.ordinal();
		
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
		bullet.ammo = ModItems.ammo_standard;
		bullet.ammoMeta = EnumAmmo.NUKE_TOTS.ordinal();
		bullet.bulletsMin = 8;
		bullet.bulletsMax = 8;
		bullet.spread = 0.1F;
		bullet.style = BulletConfiguration.STYLE_GRENADE;
		
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
		bullet.ammo = ModItems.ammo_standard;
		bullet.ammoMeta = EnumAmmo.NUKE_SAFE.ordinal();
		
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
		bullet.ammo = ModItems.ammo_standard;
		bullet.ammoMeta = EnumAmmo.NUKE_PUMPKIN.ordinal();
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
		
		bullet.ammo = ModItems.ammo_standard;
		bullet.ammoMeta = EnumAmmo.MIRV_STANDARD.ordinal();
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
		
		bullet.ammo = ModItems.ammo_standard;
		bullet.ammoMeta = EnumAmmo.MIRV_LOW.ordinal();
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
		
		bullet.ammo = ModItems.ammo_standard;
		bullet.ammoMeta = EnumAmmo.MIRV_HIGH.ordinal();
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
		
		bullet.ammo = ModItems.ammo_standard;
		bullet.ammoMeta = EnumAmmo.MIRV_SAFE.ordinal();
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
		
		bullet.ammo = ModItems.ammo_standard;
		bullet.ammoMeta = EnumAmmo.MIRV_SPECIAL.ordinal();
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
		
		bullet.ammo = ModItems.ammo_standard;
		bullet.ammoMeta = EnumAmmo.NUKE_BALEFIRE.ordinal();
		bullet.style = BulletConfiguration.STYLE_BF;
		
		bullet.bImpact = new IBulletImpactBehavior() {
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				
				if(!bullet.world.isRemote) {
					EntityBalefire bf = new EntityBalefire(bullet.world);
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

					bf.posX = posX;
					bf.posY = posY;
					bf.posZ = posZ;
					bf.destructionRange = 15;
					bullet.world.spawnEntity(bf);
					
					if(BombConfig.enableNukeClouds) {
						EntityNukeTorex.statFacBale(bullet.world, posX, posY, posZ, 15);
					}
				}
			}
		};
		return bullet;
	}
}
