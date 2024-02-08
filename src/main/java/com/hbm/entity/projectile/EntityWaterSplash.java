package com.hbm.entity.projectile;

import com.hbm.packet.AuxParticlePacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityWaterSplash extends EntityThrowable {

	public EntityWaterSplash(World worldIn) {
		super(worldIn);
	}
	
	public EntityWaterSplash(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

	@Override
	protected void onImpact(RayTraceResult result) {
		if(this.ticksExisted > 5) {
    		world.spawnParticle(EnumParticleTypes.WATER_SPLASH, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        	this.setDead();
        }
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
    	if(!world.isRemote) {
    		if(this.ticksExisted % 4 == 0)
    			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(posX, posY, posZ, 0), new TargetPoint(this.dimension, posX, posY, posZ, 75));
    		
	        if(this.ticksExisted > 80) {
	        	this.setDead();
	        }
    	}
	}

	@Override
	public boolean writeToNBTOptional(NBTTagCompound nbt) {
		return false;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.setDead();
	}
}
