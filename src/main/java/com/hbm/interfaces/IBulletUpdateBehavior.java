package com.hbm.interfaces;

import com.hbm.entity.projectile.EntityBulletBase;

public interface IBulletUpdateBehavior {

	//once every update, for lockon, steering and other memes
    void behaveUpdate(EntityBulletBase bullet);

}