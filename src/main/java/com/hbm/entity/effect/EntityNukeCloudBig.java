package com.hbm.entity.effect;

import com.hbm.interfaces.IConstantRenderer;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityNukeCloudBig extends Entity implements IConstantRenderer {

	public int maxAge = 1000;
	public int age;
    public float scale = 0;
    public float ring = 0;
    public float height = 0;
	
	public EntityNukeCloudBig(World worldIn) {
		super(worldIn);
		this.setSize(1, 80);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.age = 0;
    	scale = 0;
    	ring = 0;
    	height = 0;
	}
	
	public EntityNukeCloudBig(World p_i1582_1_, int maxAge) {
		super(p_i1582_1_);
		this.setSize(20, 40);
		this.isImmuneToFire = true;
		this.maxAge = maxAge;
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

    @Override
    public void onUpdate() {
    	this.age++;
        this.world.spawnEntity(new EntityLightningBolt(this.world, this.posX, this.posY + 200, this.posZ, true));
        
        if(this.age >= this.maxAge)
        {
    		this.age = 0;
        	this.setDead();
        }
    	ring += 0.1F;
    	
        if(age < 150)
        {
        	height = -60F + ((age - 100) * 60 / 50);
        	if(scale < 1.5)
        	{
        		scale += 0.02;
        	}
        }
        
        if(age > 100)
        {
        	if(scale < 1.5)
        	{
        		scale += 0.02;
        	}
        } else {
        	scale = 0;
        }
    }
    
	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("age"))
			age = compound.getInteger("age");
		if(compound.hasKey("maxage"))
			maxAge = compound.getInteger("maxage");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("age", age);
		compound.setInteger("maxage", maxAge);
	}

}
