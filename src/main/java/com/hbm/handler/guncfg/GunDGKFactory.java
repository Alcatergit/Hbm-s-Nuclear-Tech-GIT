package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfiguration;
import com.hbm.items.ModItems.Armory;

public class GunDGKFactory {

	public static BulletConfiguration getDGKConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		bullet.ammo = Armory.ammo_dgk;
		return bullet;
	}
}
