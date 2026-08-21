package com.hbm.entity.effect;

import java.util.ArrayList;

import com.hbm.explosion.ExplosionLarge;
import com.hbm.interfaces.IConstantRenderer;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.main.MainRegistry;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.hbm.entity.logic.EntityNukeExplosionMK5.shockSpeed;

/*
 * Toroidial Convection Simulation Explosion Effect
 * Tor                             Ex
 */
public class EntityNukeTorex extends Entity implements IConstantRenderer {

	public static final DataParameter<Float> SCALE = EntityDataManager.createKey(EntityNukeTorex.class, DataSerializers.FLOAT);
	public static final DataParameter<Byte> TYPE = EntityDataManager.createKey(EntityNukeTorex.class, DataSerializers.BYTE);
	public static final DataParameter<Integer> MAX_AGE = EntityDataManager.createKey(EntityNukeTorex.class, DataSerializers.VARINT);
	public static final DataParameter<Integer> TICKS_EXISTED = EntityDataManager.createKey(EntityNukeTorex.class, DataSerializers.VARINT);
	public static final DataParameter<Integer> TICKS_EXISTED_SAVED = EntityDataManager.createKey(EntityNukeTorex.class, DataSerializers.VARINT);
    public static final DataParameter<Boolean> IS_RELOADED = EntityDataManager.createKey(EntityNukeTorex.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> IS_INITIALIZED = EntityDataManager.createKey(EntityNukeTorex.class, DataSerializers.BOOLEAN);

	public static final int firstCondenseHeight = 130;
	public static final int secondCondenseHeight = 170;
	public static final int maxCloudlets = 20_000;

	//Nuke colors
	public static final double nr1 = 2.5;
	public static final double ng1 = 1.3;
	public static final double nb1 = 0.4;
	public static final double nr2 = 0.1;
	public static final double ng2 = 0.075;
	public static final double nb2 = 0.05;

	//Balefire colors
	public static final double br1 = 1;
	public static final double bg1 = 2;
	public static final double bb1 = 0.5;
	public static final double br2 = 0.1;
	public static final double bg2 = 0.1;
	public static final double bb2 = 0.1;

	public double coreHeight = 3;
	public double convectionHeight = 3;
	public double torusWidth = 3;
	public double rollerSize = 1;
	public double heat = 1;
	public double lastSpawnY = -1;
	public ArrayList<Cloudlet> cloudlets = new ArrayList<>();
	public int maxAge = 1000;
	public float humidity = -1;
	public float scale = 1.0F;
	public boolean didPlaySound = false;
	public boolean didShake = false;
	public int ticksExistedSaved = 0;
    public int ticksExistedSaved2 = 0;
	public boolean dataReady = false;
    public boolean needScale = false;
    public boolean isFirstEqual = true;
	public boolean isReloaded = false;
    public boolean isReloaded2 = false;
    public boolean isScaled = false;
	public boolean isInitialized = false;

	public EntityNukeTorex(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(20F, 40F);
		this.isImmuneToFire = true;
		this.ignoreFrustumCheck = true;
	}

	@Override
	protected void entityInit() {
		this.dataManager.register(SCALE, 1.0F);
		this.dataManager.register(TYPE, (byte) 0);
		this.dataManager.register(MAX_AGE, 1000);
		this.dataManager.register(TICKS_EXISTED, 0);
		this.dataManager.register(TICKS_EXISTED_SAVED, 0);
		this.dataManager.register(IS_RELOADED, false);
		this.dataManager.register(IS_INITIALIZED, false);
	}

	@Override
	public void notifyDataManagerChange(DataParameter<?> key) {
		super.notifyDataManagerChange(key);
		// When the client receives the DataParameter data synchronization, it updates the instance variables.
        if (key == TICKS_EXISTED && !this.world.isRemote) {
            if (this.isReloaded2) {
                this.ticksExistedSaved2 ++;
                if (this.ticksExisted != this.ticksExistedSaved2) {
                    this.ticksExisted = this.ticksExistedSaved2;
                    this.dataManager.set(TICKS_EXISTED, this.ticksExisted);
                }
            }
        }
		if (key == TICKS_EXISTED && this.world.isRemote) {
			// Some client instance variables can only be updated once after the entity is initialized.
			if (!this.dataReady) {
				this.scale = this.dataManager.get(SCALE);
				this.maxAge = this.dataManager.get(MAX_AGE);
				this.ticksExisted = this.dataManager.get(TICKS_EXISTED);
				this.ticksExistedSaved = this.dataManager.get(TICKS_EXISTED_SAVED);
				this.isReloaded = this.dataManager.get(IS_RELOADED);
                this.needScale = this.isReloaded;
                this.isReloaded2 = this.isReloaded;
				this.isInitialized = this.dataManager.get(IS_INITIALIZED);
                if (this.needScale) {
                    this.convectionHeight = this.convectionHeight * this.scale;
                    this.rollerSize = this.rollerSize * this.scale;
                    this.needScale = false;
                }
				this.dataReady = true;
			} else if (this.isReloaded2) {
                if (this.ticksExisted != this.dataManager.get(TICKS_EXISTED)) {
                    this.ticksExisted = this.dataManager.get(TICKS_EXISTED);
                } else if (this.isFirstEqual) {
                    this.ticksExistedSaved = this.dataManager.get(TICKS_EXISTED);
                    this.isFirstEqual = false;
                }
            }
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("scale")){
			float scaleTemp = nbt.getFloat("scale");
			this.scale = scaleTemp;
			this.dataManager.set(SCALE, scaleTemp);
		}
		if(nbt.hasKey("type"))
			this.dataManager.set(TYPE, nbt.getByte("type"));
		if(nbt.hasKey("maxAge")){
			int maxAgeTemp = nbt.getInteger("maxAge");
			this.maxAge = maxAgeTemp;
			this.dataManager.set(MAX_AGE, maxAgeTemp);
		}
		if(nbt.hasKey("ticksExisted")){
			int ticksExistedTemp = nbt.getInteger("ticksExisted");
			this.ticksExisted = ticksExistedTemp;
			this.dataManager.set(TICKS_EXISTED, ticksExistedTemp);
			this.ticksExistedSaved = ticksExistedTemp;
			this.dataManager.set(TICKS_EXISTED_SAVED, ticksExistedTemp);
            this.ticksExistedSaved2 = this.ticksExistedSaved;
		}
        this.isReloaded = true;
        this.dataManager.set(IS_RELOADED, true);
        this.isReloaded2 = this.isReloaded;
        this.isInitialized = true;
        this.dataManager.set(IS_INITIALIZED, true);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setFloat("scale", this.dataManager.get(SCALE));
		nbt.setByte("type", this.dataManager.get(TYPE));
		nbt.setInteger("maxAge", this.dataManager.get(MAX_AGE));
		nbt.setInteger("ticksExisted", this.dataManager.get(TICKS_EXISTED));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return true;
	}

	@Override
	public void onUpdate() {
		this.dataManager.set(TICKS_EXISTED, this.ticksExisted);

		if(world.isRemote){
			//MainRegistry.logger.info("[NTM] Nuke Block: "+"(" + this.posX + ", " + this.posY + ", " + this.posZ + ")"+" Client onUpdate ticksExisted: " + this.ticksExisted);
            //MainRegistry.logger.info("[NTM] Nuke Block: "+"(" + this.posX + ", " + this.posY + ", " + this.posZ + ")"+" Client onUpdate ticksExistedSaved: " + this.ticksExistedSaved);
			double s = this.getScale();
			double cs = 1.5;
			if(this.ticksExisted == 1 || (!this.isInitialized && !this.isScaled)){
				this.setScale((float) s);
			}else if(!this.isScaled){
				this.coreHeight = this.coreHeight * this.scale;
				this.torusWidth = this.torusWidth * this.scale;
			}

			boolean needReload = cloudlets.isEmpty() && (this.coreHeight == 3.0 * this.scale && this.convectionHeight == 3.0 && this.torusWidth == 3.0 * this.scale && this.rollerSize == 1.0) && this.isInitialized;
            //MainRegistry.logger.info("[NTM] Nuke Block: ({}, {}, {}) Client onUpdate:\n[Client] cloudlets.isEmpty: {};\n[Client] this.coreHeight == 3.0 * this.scale: {};\n[Client] this.convectionHeight == 3.0: {};\n[Client] this.torusWidth == 3.0 * this.scale: {};\n[Client] this.rollerSize == 1.0: {};\n[Client] isInitialized: {}\n[Client] needReload: {}", this.posX, this.posY, this.posZ, cloudlets.isEmpty(), this.coreHeight == 3.0 * this.scale, this.convectionHeight == 3.0, this.torusWidth == 3.0 * this.scale, this.rollerSize == 1.0, this.isInitialized, needReload);
			if (needReload) {
                this.ticksExistedSaved = this.ticksExisted;
                this.convectionHeight = this.convectionHeight * this.scale;
                this.rollerSize = this.rollerSize * this.scale;
                this.isReloaded = true;
				//MainRegistry.logger.info("[NTM] Nuke Block: "+"(" + this.posX + ", " + this.posY + ", " + this.posZ + ")"+" Client re-enters rendering distance");
			}

			if(humidity == -1) humidity = world.getBiome(this.getPosition()).getRainfall();

			if(lastSpawnY == -1) {
				lastSpawnY = posY - 3;
			}

			int spawnTarget = Math.max(world.getHeight((int) Math.floor(posX), (int) Math.floor(posZ)) - 3, 1);
			double moveSpeed = 0.5D;

			if(Math.abs(spawnTarget - lastSpawnY) < moveSpeed) {
				lastSpawnY = spawnTarget;
			} else {
				lastSpawnY += moveSpeed * Math.signum(spawnTarget - lastSpawnY);
			}

			// Modified: Determine if the system is in the reloading acceleration phase.
			int ticksExisted2 = this.ticksExisted - this.ticksExistedSaved;
			boolean isLateStage = this.ticksExistedSaved > this.maxAge * 2 / 3;
			boolean isAccelReload = this.isReloaded && ticksExisted2 < (isLateStage ?  90 : 95);

			if (this.isReloaded && !isAccelReload) {
				this.isReloaded = false;
			}

			// spawn mush clouds
			double range = (torusWidth - rollerSize) * 0.5;
			// Calculate speed ratio
			double speedMultiplier = 1.0D;
			double simSpeed = getSimulationSpeed();
			int lifetime = Math.min((this.ticksExisted * this.ticksExisted) + 200, maxAge - this.ticksExisted + 200);

			if(this.isReloaded && ticksExisted2 <= (isLateStage ? 50 : 75)){
				speedMultiplier = isLateStage ? 4.0D : 2.0D;
				simSpeed = 1.0D;
				lifetime = Math.min((ticksExisted2 * ticksExisted2) + 200, maxAge - ticksExisted2 + 200);
			} else if(this.isReloaded){
				speedMultiplier = Math.max(1.0D, isLateStage ? (160 - (3 * (ticksExisted2 - 50))) / 40.0D : (40 - (ticksExisted2 - 75)) / 20.0D);
				// Linear interpolation was used to smoothly transition simSpeed and lifetime to normal values.
				double progress = Math.min(1.0D, isLateStage ? (ticksExisted2 - 50) / 40.0D : (ticksExisted2 - 75) / 20.0D);
				if (this.ticksExisted > maxAge / 4) {
					double accelSimSpeed = 1.0D - ((double)(ticksExisted2 - (maxAge / 4)) / (double)(maxAge - (maxAge / 4)));
					double normalSimSpeed = 1.0D - ((double)(this.ticksExisted - (maxAge / 4)) / (double)(maxAge - (maxAge / 4)));
					simSpeed = accelSimSpeed * (1 - progress) + normalSimSpeed * progress;
				}
				double accelLifetime = Math.min((ticksExisted2 * ticksExisted2) + 200, maxAge - ticksExisted2 + 200);
				double normalLifetime = Math.min((this.ticksExisted * this.ticksExisted) + 200, maxAge - this.ticksExisted + 200);
				lifetime = (int)(accelLifetime * (1 - progress) + normalLifetime * progress);
                //MainRegistry.logger.info("[NTM] Nuke Block: ({}, {}, {}) Client onUpdate:\n[Client] decay speedMultiplier: {}->{};\n[Client] decay simSpeed: {}->{};\n[Client] decay lifetime: {}->{}", this.posX, this.posY, this.posZ, speedMultiplier, 1.0D, simSpeed, getSimulationSpeed(), lifetime, Math.min((this.ticksExisted * this.ticksExisted) + 200, maxAge - this.ticksExisted + 200));
			}

			int toSpawn;
			if (isAccelReload) {
				toSpawn = (int) (0.6 * Math.min(Math.max(0, maxCloudlets-cloudlets.size()), Math.ceil(10 * simSpeed * simSpeed * Math.min(1, 1200/(double)lifetime) * speedMultiplier)));
			} else {
				toSpawn = (int) (0.6 * Math.min(Math.max(0, maxCloudlets-cloudlets.size()), Math.ceil(10 * simSpeed * simSpeed * Math.min(1, 1200/(double)lifetime))));
			}


			for(int i = 0; i < toSpawn; i++) {
				double x = posX + rand.nextGaussian() * range;
				double z = posZ + rand.nextGaussian() * range;
				Cloudlet cloud = new Cloudlet(x, lastSpawnY, z, (float)(rand.nextDouble() * 2D * Math.PI), 0, lifetime);
				cloud.setScale((float) (Math.sqrt(s) * 3 + this.ticksExisted * 0.0025 * s), (float) (Math.sqrt(s) * 3 + this.ticksExisted * 0.0025 * 6 * cs * s));
				if (isAccelReload) {
					cloud.setMotion(cloud.motionMult * speedMultiplier);
				}
				cloudlets.add(cloud);
			}

			if(this.ticksExisted < 120 * s){
				world.setLastLightningBolt(2);
			}

			// spawn shock clouds
			if(this.ticksExisted * shockSpeed < 160) {

				int cloudCount = (int) Math.min(this.ticksExisted * shockSpeed, 100);
				int shockLife = (int) Math.max(s * 300 - this.ticksExisted * shockSpeed * 10, 60);

				for(int i = 0; i < cloudCount; i++) {
					Vec3 vec = Vec3.createVectorHelper((this.ticksExisted + rand.nextDouble() * 2 - 2) * shockSpeed, 0, 0);
					float rot = (float) (Math.PI * 2 * rand.nextDouble());
					vec.rotateAroundY(rot);
					Cloudlet shockClouds = new Cloudlet(vec.xCoord + posX, world.getHeight((int) (vec.xCoord + posX) + 1, (int) (vec.zCoord + posZ)), vec.zCoord + posZ, rot, 0, shockLife, TorexType.SHOCK)
							.setScale((float)s * 5F, (float)s * 2F).setMotion(MathHelper.clamp(0.25 * this.ticksExisted - 5, 0, 1));
					if (isAccelReload) {
						shockClouds.setMotion(shockClouds.motionMult * speedMultiplier);
					}
					this.cloudlets.add(shockClouds);
				}

				if(!didPlaySound) {
					if(MainRegistry.proxy.me() != null && MainRegistry.proxy.me().getDistance(this) < (this.ticksExisted * 1.5 + 1) * 1.5) {
						MainRegistry.proxy.playSoundClient(posX, posY, posZ, HBMSoundHandler.nuclearExplosion, SoundCategory.HOSTILE, 10_000F, 1F);
						didPlaySound = true;
					}
				}
			}

			// spawn ring clouds
			if (ticksExisted2 < 200 && (int)(this.getScale()) > 0) {
				lifetime *= (int) s;
				for(int i = 0; i < 2; i++) {
					Cloudlet cloud = new Cloudlet(posX, posY + coreHeight, posZ, (float)(rand.nextDouble() * 2D * Math.PI), 0, lifetime, TorexType.RING);
					cloud.setScale((float) (Math.sqrt(s) * cs + this.ticksExisted * 0.0015 * s), (float) (Math.sqrt(s) * cs + this.ticksExisted * 0.0015 * 6 * cs * s));
					if (isAccelReload) {
						cloud.setMotion(cloud.motionMult * speedMultiplier);
					}
					cloudlets.add(cloud);
				}
			}

			if(this.humidity > 0 && ticksExisted2 * shockSpeed < 180){
				// spawn lower condensation clouds
				spawnCondensationClouds(this.ticksExisted * shockSpeed-8, this.humidity, firstCondenseHeight, 80, 4, s, cs, isAccelReload, speedMultiplier);

				// spawn upper condensation clouds
				spawnCondensationClouds(this.ticksExisted * shockSpeed-8, this.humidity, secondCondenseHeight, 80, 2, s, cs, isAccelReload, speedMultiplier);
			}

			cloudlets.removeIf(x -> x.isDead);
			for(Cloudlet cloud : cloudlets) {
				// Modification: Adjust update logic based on whether acceleration is required.
				if (isAccelReload) {
					// Accelerated Updates: Application Speed Multiplier
					cloud.updateWithMultiplier(speedMultiplier);
				} else {
					cloud.update();
				}
			}

			// Edit: Adjust the growth rate based on whether it is accelerated.
			double coreHeightIncrement = 0.15;
			double torusWidthIncrement = 0.05;
			if (!this.isScaled) {
				for(int i = 0; i < (this.ticksExistedSaved > 0 ? this.ticksExistedSaved : this.ticksExisted); i++) {
					coreHeight += coreHeightIncrement;
					torusWidth += torusWidthIncrement;
				}
				this.isScaled = true;
			} else if (isAccelReload) {
				coreHeightIncrement *= speedMultiplier;
				torusWidthIncrement *= speedMultiplier;
				coreHeight += coreHeightIncrement;
				torusWidth += torusWidthIncrement;
			} else {
				coreHeight += coreHeightIncrement;
				torusWidth += torusWidthIncrement;
			}

			rollerSize = torusWidth * 0.35;
			convectionHeight = coreHeight + rollerSize;

			int maxHeat = (int) (50 * s * s);
			heat = maxHeat - Math.pow((double) (maxHeat * this.ticksExisted) / maxAge, 0.6);
		}else if(this.ticksExisted == 1){
            this.isInitialized = true;
            this.dataManager.set(IS_INITIALIZED, true);
        }

		if(!world.isRemote && this.ticksExisted > maxAge) {
			this.setDead();
		}
	}

	public void spawnCondensationClouds(double range, float humidity, int height, int count, int spreadAngle, double s, double cs, boolean isAccelerated, double speedMultiplier){
		if(range > 0 && (posY + range) > height) {

			for(int i = 0; i < (int)(5 * humidity * count/(double)spreadAngle); i++) {
				for(int j = 1; j < spreadAngle; j++) {
					float angle = (float) (Math.PI * 2 * rand.nextDouble());
					Vec3 vec = Vec3.createVectorHelper(0, range, 0);
					vec.rotateAroundZ((float)Math.acos((height-posY)/(range))+(float)Math.toRadians(humidity*humidity*90*j*(0.1*rand.nextDouble()-0.05)));
					vec.rotateAroundY(angle);
					Cloudlet cloud = new Cloudlet(posX + vec.xCoord, posY + vec.yCoord, posZ + vec.zCoord, angle, 0, (int) ((20 + range / 10) * (1 + rand.nextDouble() * 0.1)), TorexType.CONDENSATION);
					cloud.setScale(3F * (float) (cs * s), 4F * (float) (cs * s));
					if (isAccelerated) {
						cloud.setMotion(cloud.motionMult * speedMultiplier);
					}

					cloudlets.add(cloud);
				}
			}
		}
	}

	public EntityNukeTorex setScale(float scale) {
		if(!world.isRemote)
			this.dataManager.set(SCALE, scale);
		this.coreHeight = this.coreHeight * scale;
		this.convectionHeight = this.convectionHeight * scale;
		this.torusWidth = this.torusWidth * scale;
		this.rollerSize = this.rollerSize * scale;
		this.maxAge = (int) (45 * 20 * scale);
		this.dataManager.set(MAX_AGE, this.maxAge);
		return this;
	}

	public EntityNukeTorex setType(int type) {
		this.dataManager.set(TYPE, (byte) type);
		return this;
	}

	public double getScale() {
		return this.dataManager.get(SCALE);
	}

	public byte getType() {
		return this.dataManager.get(TYPE);
	}

	public double getSimulationSpeed() {

		int simSlow = maxAge / 4;
		int life = this.ticksExisted;

		if(life > maxAge) {
			return 0D;
		}

		if(life > simSlow) {
			return 1D - ((double)(life - simSlow) / (double)(maxAge - simSlow));
		}

		return 1.0D;
	}

	public float getAlpha() {

		int fadeOut = maxAge * 3 / 4;
		int life = this.ticksExisted;

		if(life > fadeOut) {
			float fac = (float)(life - fadeOut) / (float)(maxAge - fadeOut);
			return 1F - fac;
		}

		return 1.0F;
	}

	public Vec3 getInterpColor(double interp, byte type) {
		if(type == 0){
			return Vec3.createVectorHelper(
					(nr2 + (nr1 - nr2) * interp),
					(ng2 + (ng1 - ng2) * interp),
					(nb2 + (nb1 - nb2) * interp));
		}
		return Vec3.createVectorHelper(
				(br2 + (br1 - br2) * interp),
				(bg2 + (bg1 - bg2) * interp),
				(bb2 + (bb1 - bb2) * interp));
	}

	public class Cloudlet {

		public double posX;
		public double posY;
		public double posZ;
		public double prevPosX;
		public double prevPosY;
		public double prevPosZ;
		public double motionX;
		public double motionY;
		public double motionZ;
		public int age;
		public int cloudletLife;
		public float angle;
		public boolean isDead = false;
		float rangeMod = 1.0F;
		public float colorMod = 1.0F;
		public Vec3 color;
		public Vec3 prevColor;
		public TorexType type;
		private float startingScale = 3F;
		private float growingScale = 5F;

		public Cloudlet(double posX, double posY, double posZ, float angle, int age, int maxAge) {
			this(posX, posY, posZ, angle, age, maxAge, TorexType.STANDARD);
		}

		public Cloudlet(double posX, double posY, double posZ, float angle, int age, int maxAge, TorexType type) {
			this.posX = posX;
			this.posY = posY;
			this.posZ = posZ;
			this.age = age;
			this.cloudletLife = maxAge;
			this.angle = angle;
			this.rangeMod = 0.3F + rand.nextFloat() * 0.7F;
			this.colorMod = 0.8F + rand.nextFloat() * 0.2F;
			this.type = type;

			this.updateColor();
		}

		private double motionMult = 1F;
		private double motionConvectionMult = 0.5F;
		private double motionLiftMult = 0.625F;
		private double motionRingMult = 0.5F;
		private double motionCondensationMult = 1F;
		private double motionShockwaveMult = 1F;


		private void update() {
			updateWithMultiplier(1.0);
		}

		// Added: Support for updating speed multipliers
		private void updateWithMultiplier(double speedMultiplier) {
			age++;

			if(age > cloudletLife) {
				this.isDead = true;
			}

			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;

			Vec3 simPos = Vec3.createVectorHelper(EntityNukeTorex.this.posX - this.posX, 0, EntityNukeTorex.this.posZ - this.posZ);
			double simPosX = EntityNukeTorex.this.posX + simPos.length();
			double simPosZ = EntityNukeTorex.this.posZ + 0D;

			if(this.type == TorexType.STANDARD) {
				Vec3 convection = getConvectionMotion(simPosX, simPosZ);
				Vec3 lift = getLiftMotion(simPosX, simPosZ);

				double factor = MathHelper.clamp((this.posY - EntityNukeTorex.this.posY) / EntityNukeTorex.this.coreHeight, 0, 1);
				this.motionX = convection.xCoord * factor + lift.xCoord * (1D - factor);
				this.motionY = convection.yCoord * factor + lift.yCoord * (1D - factor);
				this.motionZ = convection.zCoord * factor + lift.zCoord * (1D - factor);
			} else if(this.type == TorexType.RING) {
				Vec3 motion = getRingMotion(simPosX, simPosZ);
				this.motionX = motion.xCoord;
				this.motionY = motion.yCoord;
				this.motionZ = motion.zCoord;
			} else if(this.type == TorexType.CONDENSATION) {
				Vec3 motion = getCondensationMotion();
				this.motionX = motion.xCoord;
				this.motionY = motion.yCoord;
				this.motionZ = motion.zCoord;
			} else if(this.type == TorexType.SHOCK) {
				Vec3 motion = getShockwaveMotion();
				this.motionX = motion.xCoord;
				this.motionY = motion.yCoord;
				this.motionZ = motion.zCoord;
			}

			double mult = this.motionMult * EntityNukeTorex.this.getSimulationSpeed() * speedMultiplier;

			this.posX += this.motionX * mult;
			this.posY += this.motionY * mult;
			this.posZ += this.motionZ * mult;

			this.updateColor();
		}

		private Vec3 getCondensationMotion() {
			Vec3 delta = Vec3.createVectorHelper(posX - EntityNukeTorex.this.posX, 0, posZ - EntityNukeTorex.this.posZ).normalize();
			double speed = motionCondensationMult * EntityNukeTorex.this.getScale() * 0.125D;
			delta.xCoord *= speed;
			delta.yCoord = 0;
			delta.zCoord *= speed;
			return delta;
		}

		private Vec3 getShockwaveMotion() {
			Vec3 delta = Vec3.createVectorHelper(posX - EntityNukeTorex.this.posX, 0, posZ - EntityNukeTorex.this.posZ).normalize();
			double speed = motionShockwaveMult * EntityNukeTorex.this.getScale() * 0.25D;
			delta.xCoord *= speed;
			delta.yCoord = 0;
			delta.zCoord *= speed;
			return delta;
		}

		private Vec3 getRingMotion(double simPosX, double simPosZ) {

			if(simPosX > EntityNukeTorex.this.posX + torusWidth * 2)
				return Vec3.createVectorHelper(0, 0, 0);

			/* the position of the torus' outer ring center */
			Vec3 torusPos = Vec3.createVectorHelper(
					(EntityNukeTorex.this.posX + torusWidth),
					(EntityNukeTorex.this.posY + coreHeight * 0.5),
					EntityNukeTorex.this.posZ);

			/* the difference between the cloudlet and the torus' ring center */
			Vec3 delta = Vec3.createVectorHelper(torusPos.xCoord - simPosX, torusPos.yCoord - this.posY, torusPos.zCoord - simPosZ);

			/* the distance this cloudlet wants to achieve to the torus' ring center */
			double roller = EntityNukeTorex.this.rollerSize * this.rangeMod * 0.25;
			/* the distance between this cloudlet and the torus' outer ring perimeter */
			double dist = delta.length() / roller - 1D;

			/* euler function based on how far the cloudlet is away from the perimeter */
			double func = 1D - Math.pow(Math.E, -dist); // [0;1]
			/* just an approximation, but it's good enough */
			float angle = (float) (func * Math.PI * 0.5D); // [0;90°]

			/* vector going from the ring center in the direction of the cloudlet, stopping at the perimeter */
			Vec3 rot = Vec3.createVectorHelper(-delta.xCoord / dist, -delta.yCoord / dist, -delta.zCoord / dist);
			/* rotate by the approximate angle */
			rot.rotateAroundZ(angle);

			/* the direction from the cloudlet to the target position on the perimeter */
			Vec3 motion = Vec3.createVectorHelper(
					torusPos.xCoord + rot.xCoord - simPosX,
					torusPos.yCoord + rot.yCoord - this.posY,
					torusPos.zCoord + rot.zCoord - simPosZ);

			motion = motion.normalize();
			motion.rotateAroundY(this.angle);
			double speed = motionRingMult * 0.5D;
			motion.xCoord *= speed;
			motion.yCoord *= speed;
			motion.zCoord *= speed;

			return motion;
		}

		/* simulated on a 2D-plane along the X/Y axis */
		private Vec3 getConvectionMotion(double simPosX, double simPosZ) {

			if(simPosX > EntityNukeTorex.this.posX + torusWidth * 2)
				return Vec3.createVectorHelper(0, 0, 0);

			/* the position of the torus' outer ring center */
			Vec3 torusPos = Vec3.createVectorHelper(
					(EntityNukeTorex.this.posX + torusWidth),
					(EntityNukeTorex.this.posY + coreHeight),
					EntityNukeTorex.this.posZ);

			/* the difference between the cloudlet and the torus' ring center */
			Vec3 delta = Vec3.createVectorHelper(torusPos.xCoord - simPosX, torusPos.yCoord - this.posY, torusPos.zCoord - simPosZ);

			/* the distance this cloudlet wants to achieve to the torus' ring center */
			double roller = EntityNukeTorex.this.rollerSize * this.rangeMod;
			/* the distance between this cloudlet and the torus' outer ring perimeter */
			double dist = delta.length() / roller - 1D;

			/* euler function based on how far the cloudlet is away from the perimeter */
			double func = 1D - Math.pow(Math.E, -dist); // [0;1]
			/* just an approximation, but it's good enough */
			float angle = (float) (func * Math.PI * 0.5D); // [0;90°]

			/* vector going from the ring center in the direction of the cloudlet, stopping at the perimeter */
			Vec3 rot = Vec3.createVectorHelper(-delta.xCoord / dist, -delta.yCoord / dist, -delta.zCoord / dist);
			/* rotate by the approximate angle */
			rot.rotateAroundZ(angle);

			/* the direction from the cloudlet to the target position on the perimeter */
			Vec3 motion = Vec3.createVectorHelper(
					torusPos.xCoord + rot.xCoord - simPosX,
					torusPos.yCoord + rot.yCoord - this.posY,
					torusPos.zCoord + rot.zCoord - simPosZ);

			motion = motion.normalize();
			motion.rotateAroundY(this.angle);

			motion.xCoord *= motionConvectionMult;
			motion.yCoord *= motionConvectionMult;
			motion.zCoord *= motionConvectionMult;

			return motion;
		}

		private Vec3 getLiftMotion(double simPosX, double simPosZ) {
			double scale = MathHelper.clamp(1D - (simPosX - (EntityNukeTorex.this.posX + torusWidth)), 0, 1) * motionLiftMult;

			Vec3 motion = Vec3.createVectorHelper(EntityNukeTorex.this.posX - this.posX, (EntityNukeTorex.this.posY + convectionHeight) - this.posY, EntityNukeTorex.this.posZ - this.posZ);

			motion = motion.normalize();
			motion.xCoord *= scale;
			motion.yCoord *= scale;
			motion.zCoord *= scale;

			return motion;
		}

		private void updateColor() {
			this.prevColor = this.color;

			double exX = EntityNukeTorex.this.posX;
			double exY = EntityNukeTorex.this.posY + EntityNukeTorex.this.coreHeight;
			double exZ = EntityNukeTorex.this.posZ;

			double distX = exX - posX;
			double distY = exY - posY;
			double distZ = exZ - posZ;

			double distSq = distX * distX + distY * distY + distZ * distZ;
			distSq /= this.type == TorexType.SHOCK ? EntityNukeTorex.this.heat * 3 : EntityNukeTorex.this.heat;

			double col = 2D / Math.max(distSq, 1); //col goes from 2-0

			byte type = EntityNukeTorex.this.getType();

			this.color = EntityNukeTorex.this.getInterpColor(col, type);
		}

		public Vec3 getInterpPos(float interp) {
			return Vec3.createVectorHelper(
					prevPosX + (posX - prevPosX) * interp,
					prevPosY + (posY - prevPosY) * interp,
					prevPosZ + (posZ - prevPosZ) * interp);
		}

		public Vec3 getInterpColor(float interp) {

			if(this.type == TorexType.CONDENSATION) {
				return Vec3.createVectorHelper(1F, 1F, 1F);
			}

			double greying = 0;

			if(this.type == TorexType.RING) {
				greying += 0.05;
			}

			return Vec3.createVectorHelper(
					(prevColor.xCoord + (color.xCoord - prevColor.xCoord) * interp) + greying,
					(prevColor.yCoord + (color.yCoord - prevColor.yCoord) * interp) + greying,
					(prevColor.zCoord + (color.zCoord - prevColor.zCoord) * interp) + greying);
		}

		public float getAlpha() {
			float alpha = (1F - ((float)age / (float)cloudletLife)) * EntityNukeTorex.this.getAlpha();
			if(this.type == TorexType.CONDENSATION) alpha *= 0.25;
			return MathHelper.clamp(alpha, 0.0001F, 1F);
		}


		public float getScale() {
			return startingScale + ((float)age / (float)cloudletLife) * growingScale;
		}

		public Cloudlet setScale(float start, float grow) {
			this.startingScale = start;
			this.growingScale = grow;
			return this;
		}

		public Cloudlet setMotion(double mult) {
			this.motionMult = mult;
			return this;
		}
	}

	public enum TorexType {
		STANDARD,
		RING,
		CONDENSATION,
		SHOCK
	}

	public static void statFac(World world, double x, double y, double z, float scale) {
		if (scale < 25) {
			ExplosionLarge.explode(world, x, y, z, scale, true, true, true);
		} else {
			EntityNukeTorex torex = new EntityNukeTorex(world).setScale(Math.min(5F,scale * 0.01F));
			torex.setPosition(x, y, z);
			world.spawnEntity(torex);
		}
	}

	public static void statFacBale(World world, double x, double y, double z, float scale) {
		if (scale < 25) {
			ExplosionLarge.explode(world, x, y, z, scale, true, true, true);
		} else {
			EntityNukeTorex torex = new EntityNukeTorex(world).setScale(Math.min(5F,scale * 0.01F)).setType(1);
			torex.setPosition(x, y, z);
			world.spawnEntity(torex);
		}
	}
}
