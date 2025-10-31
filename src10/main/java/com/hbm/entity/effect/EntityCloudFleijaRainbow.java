package com.hbm.entity.effect;

import com.hbm.config.CompatibilityConfig;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCloudFleijaRainbow extends Entity {
	
	public static final DataParameter<Integer> MAXAGE = EntityDataManager.createKey(EntityCloudFleijaRainbow.class, DataSerializers.VARINT);
	
	public int maxAge = 100;
	public int age;
    public float scale = 0;

	public EntityCloudFleijaRainbow(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(1, 4);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.age = 0;
    	scale = 0;
	}

	@Override
	protected void entityInit() {
		this.dataManager.register(MAXAGE, Integer.valueOf(0));
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
    

	public EntityCloudFleijaRainbow(World p_i1582_1_, int maxAge) {
		super(p_i1582_1_);
		this.setSize(20, 40);
		this.isImmuneToFire = true;
		this.setMaxAge(maxAge);
	}

    @Override
	public void onUpdate() {
		if(!CompatibilityConfig.isWarDim(world)){
			this.setDead();
			return;
		}
        this.age++;
        this.world.spawnEntity(new EntityLightningBolt(this.world, this.posX, this.posY + 200, this.posZ, true));
        
        if(this.age >= this.getMaxAge())
        {
    		this.age = 0;
        	this.setDead();
        }
        
        this.scale++;
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		age = p_70037_1_.getShort("age");
		scale = p_70037_1_.getShort("scale");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		p_70014_1_.setShort("age", (short)age);
		p_70014_1_.setShort("scale", (short)scale);
		
	}
	
	public void setMaxAge(int i) {
		this.dataManager.set(MAXAGE, Integer.valueOf(i));
	}
	
	public int getMaxAge() {
		return this.dataManager.get(MAXAGE);
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 25000;
    }
}