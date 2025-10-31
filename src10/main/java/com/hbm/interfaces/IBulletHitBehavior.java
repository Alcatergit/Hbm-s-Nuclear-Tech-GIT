package com.hbm.interfaces;

import com.hbm.entity.projectile.EntityBulletBase;

import net.minecraft.entity.Entity;

public interface IBulletHitBehavior {
	
	//entity is hit, bullet dies
    void behaveEntityHit(EntityBulletBase bullet, Entity hit);

}

