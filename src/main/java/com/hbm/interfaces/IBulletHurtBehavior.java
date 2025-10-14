package com.hbm.interfaces;

import com.hbm.entity.projectile.EntityBulletBase;

import net.minecraft.entity.Entity;

public interface IBulletHurtBehavior {
	//entity is hit
    void behaveEntityHurt(EntityBulletBase bullet, Entity hit);
}
