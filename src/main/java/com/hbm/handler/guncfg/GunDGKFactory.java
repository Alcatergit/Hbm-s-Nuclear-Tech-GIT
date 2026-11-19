package com.hbm.handler.guncfg;

import static com.hbm.items.ItemEnums.EnumAmmo;

import com.hbm.handler.BulletConfiguration;
import com.hbm.items.ModItems;

public class GunDGKFactory {

	public static BulletConfiguration getDGKConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		bullet.ammo = ModItems.ammo_standard;
		bullet.ammoMeta = EnumAmmo.DGK.ordinal();
		return bullet;
	}
}
