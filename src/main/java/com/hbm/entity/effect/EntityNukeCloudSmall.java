package com.hbm.entity.effect;

import java.util.ArrayList;

import com.hbm.interfaces.IConstantRenderer;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityNukeCloudSmall extends Entity implements IConstantRenderer {
	// 16
	public static final DataParameter<Integer> AGE = EntityDataManager.createKey(EntityNukeCloudSmall.class,
			DataSerializers.VARINT);
	// 17
	public static final DataParameter<Integer> MAXAGE = EntityDataManager.createKey(EntityNukeCloudSmall.class,
			DataSerializers.VARINT);
	// 18
	public static final DataParameter<Float> SCALE = EntityDataManager.createKey(EntityNukeCloudSmall.class,
			DataSerializers.FLOAT);
	// I really don't know. Some documentation would have been nice
	// 19
	public static final DataParameter<Byte> TYPE = EntityDataManager.createKey(EntityNukeCloudSmall.class,
			DataSerializers.BYTE);
	public int maxAge;
	public int age;
	public static int cloudletLife = 50;
	public float sizeFactor = 1;
	public ArrayList<Cloudlet> cloudlets = new ArrayList<>();

	public EntityNukeCloudSmall(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(20, 40);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.age = 0;
		this.maxAge = 275000;
	}

	public EntityNukeCloudSmall(World p_i1582_1_, float radius) {
		this(p_i1582_1_);
		this.setSize(20, 40);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.age = 0;

		int maxLifetime = (int)Math.max(600, 0.25F * Math.pow(radius+16F, 2));

		this.isImmuneToFire = true;
		this.maxAge = maxLifetime;
		this.sizeFactor = (float)(Math.pow(this.dataManager.get(SCALE), 2) / 15129);
		this.dataManager.set(AGE, 0);
		this.dataManager.set(SCALE, radius * 0.005F);
		this.dataManager.set(MAXAGE, maxLifetime);
		this.noClip = true;
	}

	@Override
	public void onUpdate() {
		this.age++;

		if(this.age < 460F * this.sizeFactor){
			world.setLastLightningBolt(2);
		}

		if (this.age >= maxAge) {
			this.age = 0;
			this.setDead();
		}
		int cloudCount = age * 3;

        Vec3 vec = Vec3.createVectorHelper(age * 2, 0, 0);

        int toRem = 0;

        for(int i = 0; i < this.cloudlets.size(); i++) {

        	if(age > cloudlets.get(i).age + cloudletLife)
        		toRem = i;
        	else
        		break;
        }

        for(int i = 0; i < toRem; i++)
        	this.cloudlets.remove(0);

        if(age < 200) {
	        for(int i = 0; i < cloudCount; i++) {
	        	vec.rotateAroundY((float)(Math.PI * 2 * world.rand.nextDouble()));

	        	this.cloudlets.add(new Cloudlet(vec.xCoord, world.getHeight((int) (vec.xCoord + posX), (int) (vec.zCoord + posZ)), vec.zCoord, age));
	        }
        }

		this.dataManager.set(AGE, age);
	}

	@Override
	protected void entityInit() {
		this.dataManager.register(MAXAGE, maxAge);
		this.dataManager.register(AGE, age);
		this.dataManager.register(SCALE, 1.0F);
		this.dataManager.register(TYPE, Byte.valueOf((byte) 0));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("maxAge"))
			maxAge = nbt.getShort("maxAge");
		if (nbt.hasKey("age"))
			age = nbt.getShort("age");
		if (nbt.hasKey("scale"))
			this.dataManager.set(SCALE, nbt.getFloat("scale"));
		if(nbt.hasKey("type"))
			this.dataManager.set(TYPE, nbt.getByte("type"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		p_70014_1_.setShort("maxAge", (short) maxAge);
		p_70014_1_.setShort("age", (short) age);
		p_70014_1_.setFloat("scale", this.dataManager.get(SCALE));
		p_70014_1_.setByte("type", this.dataManager.get(TYPE));
	}

	public static EntityNukeCloudSmall statFac(World world, double x, double y, double z, float radius) {
		EntityNukeCloudSmall cloud = new EntityNukeCloudSmall(world, radius);
		cloud.posX = x;
		cloud.posY = y;
		cloud.posZ = z;
		cloud.age = 0;
		cloud.dataManager.set(AGE, 0);
		cloud.dataManager.set(TYPE, (byte) 0);
		cloud.sizeFactor = (float)(Math.pow(radius, 2) / 15129);

		return cloud;
	}

	public static EntityNukeCloudSmall statFacBale(World world, double x, double y, double z, float radius) {
		EntityNukeCloudSmall cloud = new EntityNukeCloudSmall(world, radius);
		cloud.posX = x;
		cloud.posY = y;
		cloud.posZ = z;
		cloud.age = 0;
		cloud.dataManager.set(AGE, 0);
		cloud.dataManager.set(TYPE, (byte) 1);
		cloud.sizeFactor = (float)(Math.pow(radius, 2) / 15129);

		return cloud;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 2500000;
	}
	
	public static class Cloudlet {

    	public double posX;
    	public double posY;
    	public double posZ;
    	public int age;

    	public Cloudlet(double posX, double posY, double posZ, int age) {
    		this.posX = posX;
    		this.posY = posY;
    		this.posZ = posZ;
    		this.age = age;
    	}
    }
}
