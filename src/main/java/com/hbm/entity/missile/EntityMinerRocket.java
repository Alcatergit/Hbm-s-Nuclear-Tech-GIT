package com.hbm.entity.missile;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.explosion.ExplosionLarge;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityMinerRocket extends Entity {

	public static final DataParameter<Integer> TIMER = EntityDataManager.createKey(EntityMinerRocket.class, DataSerializers.VARINT);
	public static final DataParameter<Byte> TYPE = EntityDataManager.createKey(EntityMinerRocket.class, DataSerializers.BYTE);
	
	// 0 landing, 1 unloading, 2 lifting
	public int timer = 0;
	public byte type = 0;

	public EntityMinerRocket(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		this.setSize(1F, 3F);
	}

	public EntityMinerRocket(World p_i1582_1_, byte type) {
		this(p_i1582_1_);
		this.type = type;
	}

	@Override
	protected void entityInit() {
		this.getDataManager().register(TIMER, 0);
		this.getDataManager().register(TYPE, type);
	}

	public byte getRocketType(){
		return this.getDataManager().get(TYPE);
	}

	public void setRocketType(byte value){
		this.type = value;
		this.getDataManager().set(TYPE, value);
	}
	
	@Override
	public void onUpdate() {
		int i = this.getDataManager().get(TIMER);
		if(i == 0)
			motionY = -0.75;
		if(i == 1)
			motionY = 0;
		if(i == 2)
			motionY = 1;
		motionX = 0;
		motionZ = 0;
		
		this.setPositionAndRotation(posX + motionX, posY + motionY, posZ + motionZ, 0.0F, 0.0F);

		
		if(this.getDataManager().get(TIMER) == 0 && world.getBlockState(new BlockPos((int)(posX - 0.5), (int)(posY - 0.5), (int)(posZ - 0.5))).getBlock() == ModBlocks.sat_dock) {
			this.getDataManager().set(TIMER, 1);
			motionY = 0;
			posY = (int)posY;
		} else if(!world.isRemote && !world.isAirBlock(new BlockPos((int)(posX - 0.5), (int)(posY + 1), (int)(posZ - 0.5))) && this.getDataManager().get(TIMER) != 1) {
			this.setDead();
			ExplosionLarge.explodeFire(world, posX - 0.5, posY, posZ - 0.5, 10F, true, false, true);
		}
		
		if(this.getDataManager().get(TIMER) == 1) {
			
			if(ticksExisted % 2 == 0)
				ExplosionLarge.spawnShock(world, posX, posY, posZ, 1 + rand.nextInt(3), 1 + rand.nextGaussian());
			
			timer++;
			
			if(timer > 100) {
				this.getDataManager().set(TIMER, 2);
			}
		}
		int t = this.getDataManager().get(TIMER);
		
		if(t != 1) {
			
			if(ticksExisted % 2 == 0) {
				EntityGasFlameFX fx = new EntityGasFlameFX(world);
				fx.posY = posY - 0.5D;
				fx.posX = posX;
				fx.posZ = posZ;
				fx.motionY = -1D;
				
				world.spawnEntity(fx);
			}
		}
		
		if(t == 2 && posY > 300)
			this.setDead();
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
	}
}
