package com.hbm.entity.effect;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class EntityVortex extends EntityBlackHole {

    public static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityVortex.class, DataSerializers.VARINT);

    public EntityVortex(World world) {
		super(world);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
        this.getDataManager().set(TYPE, 1);
	}

	public EntityVortex(World world, float size, int type) {
		this(world);
		this.getDataManager().set(SIZE, size);
        this.getDataManager().set(TYPE, type);
	}
	
	@Override
	public void onUpdate() {
		
		this.getDataManager().set(SIZE, this.getDataManager().get(SIZE) - 0.0025F);
		if(this.getDataManager().get(SIZE) <= 0) {
			this.setDead();
			return;
		}
		
		super.onUpdate();
	}

    public int getType(){
        return this.getDataManager().get(TYPE);
    }

    @Override
    protected void entityInit() {
        this.getDataManager().register(TYPE, 0);
        super.entityInit();
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.getDataManager().set(TYPE, compound.getInteger("t"));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setFloat("t", this.getDataManager().get(TYPE));
    }
}
