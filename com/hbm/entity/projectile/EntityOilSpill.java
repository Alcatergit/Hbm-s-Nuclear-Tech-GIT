package com.hbm.entity.projectile;

import com.hbm.entity.particle.EntityOilSpillFX;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityOilSpill extends EntityThrowable {

	public EntityOilSpill(World worldIn) {
		super(worldIn);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
    	if(!world.isRemote) {
    		world.spawnEntity(new EntityOilSpillFX(world, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0));
    		if(this.isBurning()) {
    			this.setDead();
    			world.createExplosion(null, posX, posY, posZ, 1.5F, true);
    		}
    	}
	}
	
	@Override
	protected void onImpact(RayTraceResult result) {
		if(this.ticksExisted > 5) {
        	this.setDead();
        }
	}

}
