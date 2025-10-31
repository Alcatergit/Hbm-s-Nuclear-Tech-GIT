package com.leafia.contents.effects.folkvangr.visual;

import com.hbm.config.CompatibilityConfig;
import com.hbm.items.ModItems;
import com.leafia.contents.effects.folkvangr.EntityNukeFolkvangr;
import com.leafia.contents.effects.folkvangr.particles.ParticleFleijaAntischrabA;
import com.leafia.contents.effects.folkvangr.particles.ParticleFleijaAntischrabB;
import com.leafia.contents.effects.folkvangr.particles.ParticleFleijaCloud;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.leafia.contents.effects.folkvangr.EntityNukeFolkvangr.getPreferredSpeedMultiplier;

public class EntityCloudFleija extends Entity {

	public static final DataParameter<Integer> MAXAGE = EntityDataManager.createKey(EntityCloudFleija.class,DataSerializers.VARINT);
	public static final DataParameter<Float> SCALE = EntityDataManager.createKey(EntityCloudFleija.class,DataSerializers.FLOAT);
	public static final DataParameter<Float> TICKRATE = EntityDataManager.createKey(EntityCloudFleija.class,DataSerializers.FLOAT);
	public static final DataParameter<Boolean> ANTISCHRAB = EntityDataManager.createKey(EntityCloudFleija.class,DataSerializers.BOOLEAN);
	public static final DataParameter<Boolean> FINISHED = EntityDataManager.createKey(EntityCloudFleija.class,DataSerializers.BOOLEAN);
	//public int maxAge = 100; wow this was a fucking trap
	//public int age;
    public double scale = 0;
	public float tickrate = 1;
	public float tickrate1 = 1;
	public float tickrate2 = 1;
	public EntityNukeFolkvangr bound = null;
	public boolean isAntischrab;
	
	public EntityCloudFleija(World worldIn) {
		super(worldIn);
		this.setSize(1, 4);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.isAntischrab = false;
		//this.age = 0;
    	scale = 0;
		if (!worldIn.isRemote)
			tryBindAuto();
	}

	protected void tryBindAuto() {
		for (EntityNukeFolkvangr folkvangr : EntityNukeFolkvangr.awaitingBind) {
			for (EntityPlayer player : world.playerEntities) {
				if (player.getHeldItemMainhand().getItem() == ModItems.wand_d)
					player.sendMessage(new TextComponentString("Distance "+(folkvangr.getPositionVector().distanceTo(this.getPositionVector()))).setStyle(new Style().setColor(TextFormatting.YELLOW)));
			}
			if (folkvangr.getPositionVector().distanceTo(this.getPositionVector()) <= 1.5) {
				for (EntityPlayer player : world.playerEntities) {
					if (player.getHeldItemMainhand().getItem() == ModItems.wand_d)
						player.sendMessage(new TextComponentString("Bound").setStyle(new Style().setColor(TextFormatting.YELLOW)));
				}
				folkvangr.cloudBound = this;
				EntityNukeFolkvangr.awaitingBind.remove(folkvangr);
				bound = folkvangr;
				break;
			}
		}
	}
	public EntityCloudFleija setAntischrab() {
		this.isAntischrab = true;
		this.dataManager.set(ANTISCHRAB,true);
		return this;
	}
	public EntityCloudFleija(World p_i1582_1_, int maxAge) {
		super(p_i1582_1_);
		this.setSize(20, 40);
		this.isImmuneToFire = true;
		this.setMaxAge(maxAge);
		if (!p_i1582_1_.isRemote) {
			tryBindAuto();
		}
	}

	@Override
	protected void entityInit() {
		this.dataManager.register(MAXAGE,0);
		this.dataManager.register(SCALE,0f);
		this.dataManager.register(TICKRATE,1f);
		this.dataManager.register(ANTISCHRAB,isAntischrab);
		this.dataManager.register(FINISHED,false);
		if (world.isRemote) {
			spawnParticle(5);
			spawnParticle(4);
			spawnParticle(7);
			spawnParticle(6);
		}
	}
	
	@Override
	public int getBrightnessForRender() {
		return 15728880;
	}
	
	@Override
	public float getBrightness() {
		return 1.0F;
	}
	int lastMillis = -1;
	public int remoteTicks = 0;
	@SideOnly(Side.CLIENT)
	void spawnParticle(double radius) {
		double speed = EntityNukeFolkvangr.getPreferredSpeedMultiplier((short)Math.floor(radius/16));
		float yaw = rand.nextFloat() * 360;
		Vec3d particleDir = Vec3d.fromPitchYaw(rand.nextFloat() * 120 - 60,yaw);
		Vec3d rightVector = Vec3d.fromPitchYaw(0,yaw+90);
		double length = radius*0.8;
		double theta = 1/(2*radius*Math.PI);
		for (double offs = 0; offs < length/theta; offs+=1/theta) {
			Vec3d forward = particleDir.scale(Math.cos(theta*offs));
			Vec3d right = rightVector.scale(Math.sin(theta*offs));
			for (int i = -1; i <= ((offs == 0) ? 1 : -1); i++) {
				Minecraft.getMinecraft().effectRenderer.addEffect(
						new ParticleFleijaCloud(
								world,
								forward,right,
								this
						)
				);
			}
		}
	}
	int didInitialEffects = 0;
	int finishTimer = 0;
	@SideOnly(Side.CLIENT)
	public void remoteUpdate() { // stupid minecraft needs this as separate method
		isAntischrab = this.dataManager.get(ANTISCHRAB);
		tickrate = this.dataManager.get(TICKRATE);
		remoteTicks+=tickrate;
		float getSc = this.dataManager.get(SCALE);
		if (getSc > this.scale)
			remoteTicks = 0;
		this.scale = getSc;
		if (world.rand.nextInt(16) == 0)
			spawnParticle(this.scale*0.75);
		if (isAntischrab && (didInitialEffects < 5)) {
			didInitialEffects++;
			int angRand = rand.nextInt(360);
			for (int i = 0; i < 360; i+=5) {
				Minecraft.getMinecraft().effectRenderer.addEffect(
						new ParticleFleijaAntischrabA(
								world,
								posX,posY,posZ,
								i+angRand
						)
				);
			}
		}
		if (isAntischrab && (ticksExisted == 5)) {
			int addRand = rand.nextInt(360);
			for (int i = addRand; i < 360+addRand; i+=10) {
				for (float i2 = 1; i2 <= 6; i2+=0.5) {
					Minecraft.getMinecraft().effectRenderer.addEffect(
							new ParticleFleijaAntischrabB(
									world,
									posX,posY,posZ,
									(float) Math.cos(i / 180d * Math.PI) * i2 * 1.414f,(float) Math.sin(i / 180d * Math.PI) * i2 * 1.414f
							)
					);
				}
			}
		}
	}
	@Override
	public void onUpdate() {
		super.onUpdate();
		if (world.isRemote) {
			remoteUpdate();
		} else {
			if (!CompatibilityConfig.isWarDim(world)) {
				this.setDead();
				return;
			}
			if (this.isEntityAlive()) {
				if (this.bound == null)
					tryBindAuto();
			}
			if (this.scale >= this.getMaxAge()) {
				if (!this.dataManager.get(FINISHED))
					this.dataManager.set(FINISHED,true);
				finishTimer++;
				if (finishTimer > (int)Math.ceil(10+Math.pow(getMaxAge(),0.5))+10)
					this.setDead();
			} else {
				this.world.spawnEntity(new EntityLightningBolt(this.world,this.posX,this.posY + 200,this.posZ,true));
			}
			if (bound == null)
				this.scale++;
			else
				this.scale += getPreferredSpeedMultiplier((short) Math.ceil(scale / 16));
			this.dataManager.set(SCALE,(float)scale);
			int millis = (int)Math.floorMod(System.currentTimeMillis(),10000);
			if (lastMillis >= 0) {
				int elapsed = Math.floorMod(millis-lastMillis,10000);
				tickrate2 = tickrate1;
				tickrate1 = tickrate;
				tickrate = 50f/elapsed;
				this.dataManager.set(TICKRATE,(tickrate+tickrate1+tickrate2)/3);
			}
			lastMillis = millis;
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.scale = compound.getDouble("scale");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setDouble("scale",scale);
	}
	
	public void setMaxAge(int maxAge) {
		this.dataManager.set(MAXAGE, maxAge);
	}
	
	public int getMaxAge() {
		return this.dataManager.get(MAXAGE);
	}
	
	@Override
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 25000;
	}

}
