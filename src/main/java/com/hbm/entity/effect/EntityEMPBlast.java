package com.hbm.entity.effect;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityEMPBlast extends Entity {
	
	public static final DataParameter<Integer> MAXAGE = EntityDataManager.createKey(EntityEMPBlast.class, DataSerializers.VARINT);
	
	public int maxAge = 100;
	public int age;
    public float scale = 0;
	
    public EntityEMPBlast(World p_i1582_1_) {
		super(p_i1582_1_);
        this.setSize(1.5F, 1.5F);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.age = 0;
    	scale = 0;
	}
	
	public EntityEMPBlast(World p_i1582_1_, int maxAge) {
		this(p_i1582_1_);
		this.setMaxAge(maxAge);
	}
	
	@Override
	public void onUpdate() {
		this.age++;
        
        if(this.age >= this.getMaxAge())
        {
    		this.age = 0;
        	this.setDead();
        }
        
        this.scale++;
	}

	@Override
	protected void entityInit() {
		this.getDataManager().register(MAXAGE, 0);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.scale = compound.getFloat("scale");
		this.age = compound.getInteger("age");
		this.setMaxAge(compound.getInteger("maxage"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setFloat("scale", scale);
		compound.setInteger("age", age);
		compound.setInteger("maxage", maxAge);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    @Override
	public float getBrightness()
    {
        return 1.0F;
    }
	
	public void setMaxAge(int i) {
		this.getDataManager().set(MAXAGE, i);
		this.maxAge = i;
	}
	
	public int getMaxAge() {
		return this.getDataManager().get(MAXAGE);
	}

}
