package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletHurtBehavior;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGunDart;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.render.misc.RenderScreenOverlay.Crosshair;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;

public class GunDartFactory {

	public static GunConfiguration getDarterConfig() {

		GunConfiguration config = new GunConfiguration();

		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.hasSights = false;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 1;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CROSS;
		config.durability = 1000;
		config.reloadSound = GunConfiguration.RSOUND_GRENADE;
		config.firingSound = HBMSoundHandler.dartShoot;
		config.reloadSoundEnd = false;
		config.showAmmo = true;

		config.name = "Needle Gun";
		config.manufacturer = "-";

		config.config = new ArrayList<>();
		config.config.add(BulletConfigSyncingUtil.NEEDLE_GPS);

		return config;
	}

	public static BulletConfiguration getGPSConfig() {

		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();

		bullet.ammo = ModItems.ammo_dart;
		bullet.velocity = 5.0F;
		bullet.spread = 0;
		bullet.dmgMin = 1;
		bullet.dmgMax = 2;
		bullet.doesRicochet = true;
		bullet.doesPenetrate = false;
		bullet.style = BulletConfiguration.STYLE_FLECHETTE;

		bullet.effects = new ArrayList<>();
		bullet.effects.add(new PotionEffect(MobEffects.WITHER, 60 * 20, 2));

		bullet.bHurt = new IBulletHurtBehavior() {

			@Override
			public void behaveEntityHurt(EntityBulletBase bullet, Entity hit) {

				if(bullet.world.isRemote)
					return;

				if(hit instanceof EntityPlayer) {

					if(Library.hasInventoryItem(((EntityPlayer) hit).inventory, ModItems.ingot_meteorite_forged))
						return;

					if(bullet.shooter instanceof EntityPlayer) {

						EntityPlayer shooter = (EntityPlayer) bullet.shooter;

						for(EnumHand hand : EnumHand.values())
							if(shooter.getHeldItem(hand) != null && shooter.getHeldItem(hand).getItem() == ModItems.gun_darter) {
								ItemGunDart.writePlayer(shooter.getHeldItem(hand), (EntityPlayer)hit);
								shooter.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
							}
					}
				}
			}
		};

		return bullet;
	}
}
