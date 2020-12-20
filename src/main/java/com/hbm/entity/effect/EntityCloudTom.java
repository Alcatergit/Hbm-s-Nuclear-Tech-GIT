package com.hbm.entity.effect;

import com.hbm.interfaces.IConstantRenderer;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class EntityCloudTom extends Entity implements IConstantRenderer {

	public static final DataParameter<Integer> MAXAGE = EntityDataManager.createKey(EntityCloudTom.class, DataSerializers.VARINT);
	
	public int maxAge = 100;
	public int age;
	
	public EntityCloudTom(World worldIn) {
		super(worldIn);
		this.setSize(1, 4);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.age = 0;
	}

	public EntityCloudTom(World p_i1582_1_, int maxAge) {
		super(p_i1582_1_);
		this.setSize(20, 40);
		this.isImmuneToFire = true;
		this.setMaxAge(maxAge);
	}
	
	@Override
	protected void entityInit() {
		this.getDataManager().register(MAXAGE, 0);
	}

	@Override
	public void onUpdate() {
		this.age++;
		this.world.setLastLightningBolt(2);

		if (this.age >= this.getMaxAge()) {
			this.setDead();
		}
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.age = compound.getShort("age");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setShort("age", (short) age);
	}
	
	public void setMaxAge(int i) {
		this.getDataManager().set(MAXAGE, Integer.valueOf(i));
	}

	public int getMaxAge() {
		return this.getDataManager().get(MAXAGE);
	}
	
	@Override
	public boolean isInRangeToRenderDist(double distance) {
		return true;
	}

}
