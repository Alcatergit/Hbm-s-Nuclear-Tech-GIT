package com.hbm.entity.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityNukeCloudSmall extends Entity {
	//16
	private static final DataParameter<Integer> AGE = EntityDataManager.createKey(EntityNukeCloudSmall.class, DataSerializers.VARINT);
	//17
	private static final DataParameter<Integer> MAXAGE = EntityDataManager.createKey(EntityNukeCloudSmall.class, DataSerializers.VARINT);
	//18
	public static final DataParameter<Float> SCALE = EntityDataManager.createKey(EntityNukeCloudSmall.class, DataSerializers.FLOAT);
	//I really don't know. Some documentation would have been nice
	//19
	public static final DataParameter<Byte> SOMETHING = EntityDataManager.createKey(EntityNukeCloudSmall.class, DataSerializers.BYTE);
	public int maxAge = 1000;
	public int age;

	public EntityNukeCloudSmall(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(1, 80);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.age = 0;
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
    

	public EntityNukeCloudSmall(World p_i1582_1_, int maxAge, float scale) {
		super(p_i1582_1_);
		this.setSize(20, 40);
		this.isImmuneToFire = true;
		this.maxAge = maxAge;
		this.dataManager.set(SCALE, scale);
	}

    @Override
	public void onUpdate() {
        //super.onUpdate();
        this.age++;
        this.world.spawnEntity(new EntityLightningBolt(this.world, this.posX, this.posY + 400, this.posZ, true));
        
        if(this.age >= this.maxAge)
        {
    		this.age = 0;
        	this.setDead();
        }

        this.dataManager.set(MAXAGE, maxAge);
        this.dataManager.set(AGE, age);
    }

	@Override
	protected void entityInit() {
		this.dataManager.register(MAXAGE, maxAge);
		this.dataManager.register(AGE, age);
		this.dataManager.register(SCALE, 1.0F);
        this.dataManager.register(SOMETHING, Byte.valueOf((byte)0));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		maxAge = p_70037_1_.getShort("maxAge");
		age = p_70037_1_.getShort("age");
		this.dataManager.set(SCALE, p_70037_1_.getFloat("scale"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		p_70014_1_.setShort("maxAge", (short)maxAge);
		p_70014_1_.setShort("age", (short)age);
		p_70014_1_.setFloat("scale", this.dataManager.get(SCALE));
		
	}
	
	public static EntityNukeCloudSmall statFac(World world, double x, double y, double z, float radius) {
		
		EntityNukeCloudSmall cloud = new EntityNukeCloudSmall(world, (int)radius * 5, radius * 0.005F);
		cloud.posX = x;
		cloud.posY = y;
		cloud.posZ = z;
		cloud.dataManager.set(SOMETHING, (byte)0);
		
		return cloud;
	}
	
	public static EntityNukeCloudSmall statFacBale(World world, double x, double y, double z, float radius, int maxAge) {
		
		EntityNukeCloudSmall cloud = new EntityNukeCloudSmall(world, (int)radius * 5, radius * 0.005F);
		cloud.posX = x;
		cloud.posY = y;
		cloud.posZ = z;
		cloud.dataManager.set(SOMETHING, (byte)1);
		
		return cloud;
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 25000;
    }
}
