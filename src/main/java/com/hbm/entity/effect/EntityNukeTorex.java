package com.hbm.entity.effect;

import com.hbm.interfaces.IConstantRenderer;
import com.hbm.items.ModItems;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.amlfrom1710.Vec3;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import com.llib.exceptions.messages.TextWarningLeafia;
import com.llib.technical.LeafiaEase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
/*
 * Toroidal Convection Simulation Explosion Effect
 * Tor                            Ex
 */
public class EntityNukeTorex extends Entity implements IConstantRenderer {
	//@SideOnly(Side.CLIENT) Using these on fields are forced crashes on servers??? Fuck off.
	static final Set<EntityNukeTorex> waitingTorexes = new HashSet<>();

	public static Entity bindMe = null;

	//@SideOnly(Side.CLIENT)
	public boolean reachedPlayer = false;
	public boolean sound = true;

	public boolean calculationFinished = true;
	Entity boundEntity;
	UUID backupUUID;
	int subt = 0;

	public double initPosX;
	public double initPosY;
	public double initPosZ;
	boolean valid = false;

	public static final DataParameter<Float> SCALE = EntityDataManager.createKey(EntityNukeTorex.class, DataSerializers.FLOAT);
	public static final DataParameter<Byte> TYPE = EntityDataManager.createKey(EntityNukeTorex.class, DataSerializers.BYTE);
	public static final float animationSpeedShk = 6;
	public static final float animationSpeedGeneral = 3f;
	
	public static final int firstCondenseHeight = 130;
	public static final int secondCondenseHeight = 170;
	public static final int blastWaveHeadstart = 5;
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
	public double heatScaled = 1;
	public double scaleCurrent = 0;
	public double scaleGrow = 0;
	public double lastSpawnY = -1;
	public ArrayList<Cloudlet> cloudlets = new ArrayList();
	public int maxAge = 1000;
	public float humidity = -1;
	public BlockPos getInitialPosition() {
		return new BlockPos(initPosX,initPosY,initPosZ);
	}
	public EntityNukeTorex(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(20F, 40F);
		this.isImmuneToFire = true;
		this.ignoreFrustumCheck = true;
	}

	@Override
	protected void entityInit() {
		this.dataManager.register(SCALE, 1.0F);
		this.dataManager.register(TYPE, Byte.valueOf((byte) 0));
	}

	public boolean isValid() {
		return valid;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("scale"))
			setScale(nbt.getFloat("scale"));
		if (nbt.hasKey("type"))
			this.dataManager.set(TYPE, nbt.getByte("type"));
		if (nbt.hasKey("sound"))
			sound = nbt.getBoolean("sound");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setFloat("scale", this.dataManager.get(SCALE));
		nbt.setByte("type", this.dataManager.get(TYPE));
		nbt.setBoolean("sound", this.sound);
	}

	@Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
		return true;
	}
	float animTick = 0;
	int animTickI = 0;
	@Override
	public void onUpdate() {
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		boolean nope = this.addedToChunk;
		if (nope) {
			if (!world.isRemote) {
				this.setDead();
				for (EntityPlayer player : world.playerEntities) {
					player.sendMessage(new TextWarningLeafia("Please summon entity_effect_torex only by /hbmleaf torex!"));
				}
			}
		}
		if (world.isRemote && !valid)
			this.setDead();
		if (!nope) {
			if (!world.isRemote) { // server (keeping track of bound entity)
				if (boundEntity != null) {
					if (boundEntity.isEntityAlive()) {
						subt++;
						calculationFinished = false;
						animTickI = 0; // reuse this as packet timeout timer
					} else {
						if (!calculationFinished) {
							if (animTickI++ < 40)
								calculationFinished = true;
							TorexFinishPacket packet = new TorexFinishPacket();
							packet.uuid = this.getUniqueID();
							PacketDispatcher.wrapper.sendToAll(packet);
						}
					}
				}
			} else { // local (rendering)
				animTick = this.ticksExisted * animationSpeedGeneral;
				animTickI = (int) animTick;

				//scaleCurrent += MathHelper.clampedLerp(1,animationSpeedGeneral,Math.pow(heatScaled,1.25))*MathHelper.clampedLerp(1d,0d,(this.ticksExisted-600)/1200d);

				double s = this.getScale();
				double cs = 1.5;
				if (this.ticksExisted == 1) this.setScale((float) s);

				if (humidity == -1) humidity = world.getBiome(this.getPosition()).getRainfall();

				if (lastSpawnY == -1) {
					lastSpawnY = initPosY - 3;
				}

				int spawnTarget = Math.max(world.getHeight((int) Math.floor(initPosX),(int) Math.floor(initPosZ)) - 3,1);
				double moveSpeed = 0.5D;

				if (Math.abs(spawnTarget - lastSpawnY) < moveSpeed) {
					lastSpawnY = spawnTarget;
				} else {
					lastSpawnY += moveSpeed * Math.signum(spawnTarget - lastSpawnY);
				}

				// spawn mush clouds
				double range = (torusWidth - rollerSize) * 0.5;
				double simSpeed = getSimulationSpeed();
				int lifetime = Math.min((int)Math.pow(Math.min(this.ticksExisted,1200),2) + 200,(maxAge+subt) - this.ticksExisted + 200);
				//int toSpawn = //this is just stupid (int) (0.6 * Math.min(Math.max(0,maxCloudlets - cloudlets.size()),Math.ceil(10 * simSpeed * simSpeed * Math.min(1,1200 / (double) lifetime))));
				boolean doSpawn = world.rand.nextInt(Math.max(0,(int)Math.floor(1/Math.max(simSpeed,0.001)-0.25))+1) == 0;
				//for (int i = 0; i < toSpawn; i++) {
				for (int i = 0; i < (doSpawn ? Math.ceil(simSpeed-0.1)*2 : 0); i++) {
					double x = initPosX + rand.nextGaussian() * range;
					double z = initPosZ + rand.nextGaussian() * range;
					Cloudlet cloud = new Cloudlet(x,lastSpawnY,z,(float) (rand.nextDouble() * 2D * Math.PI),0,lifetime);
					cloud.setScale((float) (Math.sqrt(s) * 3 + scaleCurrent * s),(float) (Math.sqrt(s) * 3 + scaleGrow * animationSpeedGeneral * cs * s),(float) (Math.sqrt(s) * 3 + scaleGrow * cs * s));
					cloudlets.add(cloud);
				}
				//if (toSpawn <= 0) {
				/*
					EntityPlayer player = Minecraft.getMinecraft().player;
						player.sendMessage(new TextComponentString("toSpawn "+toSpawn));
						player.sendMessage(new TextComponentString("lifetime "+lifetime));
						player.sendMessage(new TextComponentString("ticksExisted "+this.ticksExisted));
						player.sendMessage(new TextComponentString("max-current "+(maxCloudlets-cloudlets.size())));
*/
				//}

				if (this.ticksExisted < 120 * s) {
					world.setLastLightningBolt(2);
				}
				double tickOffset = getScale()*15*3.65;
				double ticksExistedO = ticksExisted-tickOffset;
				int animTickIO = animTickI-(int)tickOffset;
				// spawn shock clouds
				if (this.ticksExisted * animationSpeedShk < 150+tickOffset*animationSpeedShk && this.ticksExisted > tickOffset) {

					int cloudCount = Math.min((int) (ticksExistedO * animationSpeedShk) * 2,300);
					int shockLife = Math.max(400 - (int) (ticksExistedO * animationSpeedShk) * 20,50);

					for (int i = 0; i < cloudCount; i++) {
						Vec3 vec = Vec3.createVectorHelper((ticksExistedO + rand.nextDouble() * 2) * 1.5 * animationSpeedShk /*make it a little faster*/,0,0);
						float rot = (float) (Math.PI * 2 * rand.nextDouble());
						vec.rotateAroundY(rot);
						this.cloudlets.add(new Cloudlet(vec.xCoord + initPosX,world.getHeight((int) (vec.xCoord + initPosX) + 1,(int) (vec.zCoord + initPosZ)),vec.zCoord + initPosZ,rot,0,shockLife,TorexType.SHOCK)
								.setScale((float) s * 5F * 2,(float) s * 2F * 2).setMotion(MathHelper.clamp(0.25 * (int) (ticksExistedO * animationSpeedShk) - 5,0,1)));
					}
				}

				// spawn ring clouds
				if (this.ticksExisted < 200) {
					lifetime *= s;
					for (int i = 0; i < 2; i++) {
						Cloudlet cloud = new Cloudlet(initPosX,initPosY + coreHeight,initPosZ,(float) (rand.nextDouble() * 2D * Math.PI),0,lifetime,TorexType.RING);
						cloud.setScale(1F + (float)scaleCurrent * 0.0025F * (float) (cs * s),1F + animTick * 0.0025F * 5F * (float) (cs * s),1F + this.ticksExisted * 0.0025F * 5F * (float) (cs * s));
						cloudlets.add(cloud);
					}
				}

				if (this.humidity > 0 && animTick < 220+tickOffset && animTick > tickOffset) {
					// spawn lower condensation clouds
					spawnCondensationClouds(animTickIO,this.humidity,firstCondenseHeight,80,4,s,cs);

					// spawn upper condensation clouds
					spawnCondensationClouds(animTickIO,this.humidity,secondCondenseHeight,80,2,s,cs);
				}

				cloudlets.removeIf(x -> x.isDead);
				for (Cloudlet cloud : cloudlets) {
					cloud.update();
				}


				coreHeight += 0.15/(Math.max(this.ticksExisted-240,0)/(Math.pow(s,0.6)*1.6)*1.75+1)/* * s*/; //250
				torusWidth += 0.05/(Math.max(this.ticksExisted-300,0)/(Math.pow(s,0.6)*1.75)*1.75+1)/* * s*/; // 350
				rollerSize = torusWidth * 0.35;
				convectionHeight = coreHeight + rollerSize;
				scaleCurrent += (0.0025*simSpeed)/(Math.max(this.ticksExisted-300,0)/(Math.pow(s,0.6)*1.45)*1.75+1); // 350
				scaleGrow += (0.0025*6)/(Math.max(this.ticksExisted-600,0)/350d+1);

				int maxHeat = (int) (50 * s * s);
				heat = maxHeat - Math.pow((maxHeat * (this.ticksExisted - subt)) / maxAge,0.6);
				heatScaled = (maxHeat - Math.pow((maxHeat * this.ticksExisted) / maxAge,0.6))/maxHeat;

				localSendMessage(doSpawn,simSpeed);
			}
		}
		if(/*!world.isRemote && */this.ticksExisted-subt > maxAge) {
			this.setDead();
		}
		if (world.isRemote) {
			if (waitingTorexes.contains(this)) {
				if (this.isDead)
					waitingTorexes.remove(this);
			}
		}
	}
	@SideOnly(Side.CLIENT)
	void localSendMessage(boolean doSpawn,double simSpeed) { // stupid minecraft needs this as separate method
		EntityPlayer player = Minecraft.getMinecraft().player;
		if (player.getHeldItemMainhand().getItem() == ModItems.wand_d) {
			player.sendMessage(new TextComponentString("spawn: " + doSpawn));
			player.sendMessage(new TextComponentString("height: " + coreHeight));
			player.sendMessage(new TextComponentString("width: " + torusWidth));
			player.sendMessage(new TextComponentString("scale: " + scaleCurrent));
			player.sendMessage(new TextComponentString("sim: " + simSpeed));
		}
	}

	public void spawnCondensationClouds(int age, float humidity, int height, int count, int spreadAngle, double s, double cs){
		if((initPosY + age) > height) {
			
			for(int i = 0; i < (int)(5 * humidity * count/(double)spreadAngle); i++) {
				for(int j = 1; j < spreadAngle; j++) {
					float angle = (float) (Math.PI * 2 * rand.nextDouble());
					Vec3 vec = Vec3.createVectorHelper(0, age, 0);
					vec.rotateAroundZ((float)Math.acos((height-initPosY)/(age))+(float)Math.toRadians(humidity*humidity*90*j*(0.1*rand.nextDouble()-0.05)));
					vec.rotateAroundY(angle);
					Cloudlet cloud = new Cloudlet(initPosX + vec.xCoord, initPosY + vec.yCoord, initPosZ + vec.zCoord, angle, 0, (int) ((20 + age / 10) * (1 + rand.nextDouble() * 0.1)), TorexType.CONDENSATION);
					cloud.setScale(3F * (float) (cs * s), 4F * (float) (cs * s));
					cloudlets.add(cloud);
				}
			}
		}
	}
	
	public EntityNukeTorex setScale(float scale) {
		this.dataManager.set(SCALE, scale);
		this.coreHeight = this.coreHeight * scale;
		this.convectionHeight = this.convectionHeight * scale;
		this.torusWidth = this.torusWidth * scale;
		this.rollerSize = this.rollerSize * scale;
		this.maxAge = (int) (45 * 20 * scale);
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
	LeafiaEase startBoostEase = new LeafiaEase(LeafiaEase.Ease.EXPO,LeafiaEase.Direction.O);
	public double getSimulationSpeed() {
		// overhaul
		/*
		int simSlow = maxAge / 4;
		int life = this.ticksExisted-subt;

		double mul = MathHelper.clampedLerp(1D,0.25D,(this.ticksExisted-600)/3600d);
		if(life > maxAge) {
			return 0D;
		}
		
		if(life > simSlow) {
			if (!calculationFinished) {
				subt = life-simSlow;
				return mul;
			}
			return (1D-((double)(life - simSlow) / (double)(maxAge - simSlow)))*mul;
		}
		
		return mul;*/
		int life = this.ticksExisted-subt;
		int pauseTick = (int)(maxAge*0.25);
		int slowTick = (int)(maxAge*0.75);
		if (!this.calculationFinished && (this.ticksExisted >= pauseTick)) {
			subt = this.ticksExisted-pauseTick;
			life = pauseTick;
		}
		double out = 1;
		if (life >= slowTick)
			out *= MathHelper.clampedLerp(1,0,(life-slowTick)/(float)(maxAge-slowTick));
		double startBoostDuration = 20*10;
		int slowStart = 20*30;
		double slowDuration = 20*180;
		if (this.ticksExisted <= startBoostDuration) {
			out *= startBoostEase.get(this.ticksExisted/startBoostDuration,MathHelper.clamp((this.getScale()-0.5)*2/3+1,1,2),1,true);
		} else if (this.ticksExisted >= slowStart) {
			out *= MathHelper.clampedLerp(1,0.25,(this.ticksExisted-slowStart)/slowDuration);
		}
		return out;
	}
	
	public float getAlpha() {
		
		int fadeOut = maxAge * 3 / 4;
		int life = ticksExisted-subt;
		
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

		public double initPosX;
		public double initPosY;
		public double initPosZ;
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
		private float growingScaleEnd = 5F;
		private double curScale = 0;
		private double growDiv = 1;
		
		public Cloudlet(double initPosX, double initPosY, double initPosZ, float angle, int age, int maxAge) {
			this(initPosX, initPosY, initPosZ, angle, age, Math.min(maxAge,age+(int)(2400/Math.max(getSimulationSpeed(),0.001))), TorexType.STANDARD);
			this.growDiv = maxAge;
		}

		public Cloudlet(double initPosX, double initPosY, double initPosZ, float angle, int age, int maxAge, TorexType type) {
			this.initPosX = initPosX;
			this.initPosY = initPosY;
			this.initPosZ = initPosZ;
			this.age = age;
			this.cloudletLife = maxAge;
			this.angle = angle;
			this.rangeMod = 0.3F + rand.nextFloat() * 0.7F;
			this.colorMod = 0.8F + rand.nextFloat() * 0.2F;
			this.type = type;
			this.growDiv = maxAge;
			
			this.updateColor();
		}

		private double motionMult = 1F;
		private double motionConvectionMult = 0.5F;
		private double motionLiftMult = 0.625F;
		private double motionRingMult = 0.5F;
		private double motionCondensationMult = 1F;
		private double motionShockwaveMult = 1F;
		
		
		private void update() {
			age++;
			this.curScale += MathHelper.clampedLerp(1d,0d,(EntityNukeTorex.this.ticksExisted-600)/1200d)/this.growDiv*MathHelper.clampedLerp(this.growingScaleEnd,this.growingScale,EntityNukeTorex.this.heatScaled);
			
			if(age > cloudletLife) {
				//if ((this.type != TorexType.STANDARD) && (this.type != TorexType.RING))
				this.isDead = true;
			}

			this.prevPosX = this.initPosX;
			this.prevPosY = this.initPosY;
			this.prevPosZ = this.initPosZ;
			
			Vec3 simPos = Vec3.createVectorHelper(EntityNukeTorex.this.initPosX - this.initPosX, 0, EntityNukeTorex.this.initPosZ - this.initPosZ);
			double simPosX = EntityNukeTorex.this.initPosX + simPos.length();
			double simPosZ = EntityNukeTorex.this.initPosZ + 0D;

			double mult = this.motionMult;
			if (this.type.isMainEffect)
				mult *= getSimulationSpeed();
			if(this.type == TorexType.STANDARD) {
				Vec3 convection = getConvectionMotion(simPosX, simPosZ);
				Vec3 lift = getLiftMotion(simPosX, simPosZ);
				
				double factor = MathHelper.clamp((this.initPosY - EntityNukeTorex.this.initPosY) / EntityNukeTorex.this.coreHeight, 0, 1);
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
			
			this.initPosX += this.motionX * mult;
			this.initPosY += this.motionY * mult;
			this.initPosZ += this.motionZ * mult;
			
			this.updateColor();
		}
		
		private Vec3 getCondensationMotion() {
			Vec3 delta = Vec3.createVectorHelper(initPosX - EntityNukeTorex.this.initPosX, 0, initPosZ - EntityNukeTorex.this.initPosZ).normalize();
			double speed = motionCondensationMult * EntityNukeTorex.this.getScale() * 0.125D * animationSpeedGeneral;
			delta.xCoord *= speed;
			delta.yCoord = 0;
			delta.zCoord *= speed;
			return delta;
		}

		private Vec3 getShockwaveMotion() {
			Vec3 delta = Vec3.createVectorHelper(initPosX - EntityNukeTorex.this.initPosX, 0, initPosZ - EntityNukeTorex.this.initPosZ).normalize();
			double speed = motionShockwaveMult * EntityNukeTorex.this.getScale() * 0.25D * animationSpeedShk;
			delta.xCoord *= speed;
			delta.yCoord = 0;
			delta.zCoord *= speed;
			return delta;
		}
		
		private Vec3 getRingMotion(double simPosX, double simPosZ) {
			
			if(simPosX > EntityNukeTorex.this.initPosX + torusWidth * 2)
				return Vec3.createVectorHelper(0, 0, 0);
			
			/* the position of the torus' outer ring center */
			Vec3 torusPos = Vec3.createVectorHelper(
					(EntityNukeTorex.this.initPosX + torusWidth),
					(EntityNukeTorex.this.initPosY + coreHeight * 0.5),
					EntityNukeTorex.this.initPosZ);
			
			/* the difference between the cloudlet and the torus' ring center */
			Vec3 delta = Vec3.createVectorHelper(torusPos.xCoord - simPosX, torusPos.yCoord - this.initPosY, torusPos.zCoord - simPosZ);
			
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
					torusPos.yCoord + rot.yCoord - this.initPosY,
					torusPos.zCoord + rot.zCoord - simPosZ);
			
			motion = motion.normalize();
			motion.rotateAroundY(this.angle);
			double speed = motionRingMult * 0.5D * animationSpeedGeneral;
			motion.xCoord *= speed;
			motion.yCoord *= speed;
			motion.zCoord *= speed;
			
			return motion;
		}
		
		/* simulated on a 2D-plane along the X/Y axis */
		private Vec3 getConvectionMotion(double simPosX, double simPosZ) {
			
			if(simPosX > EntityNukeTorex.this.initPosX + torusWidth * 2)
				return Vec3.createVectorHelper(0, 0, 0);
			
			/* the position of the torus' outer ring center */
			Vec3 torusPos = Vec3.createVectorHelper(
					(EntityNukeTorex.this.initPosX + torusWidth),
					(EntityNukeTorex.this.initPosY + coreHeight),
					EntityNukeTorex.this.initPosZ);
			
			/* the difference between the cloudlet and the torus' ring center */
			Vec3 delta = Vec3.createVectorHelper(torusPos.xCoord - simPosX, torusPos.yCoord - this.initPosY, torusPos.zCoord - simPosZ);
			
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
					torusPos.yCoord + rot.yCoord - this.initPosY,
					torusPos.zCoord + rot.zCoord - simPosZ);
			
			motion = motion.normalize();
			motion.rotateAroundY(this.angle);

			motion.xCoord *= motionConvectionMult;
			motion.yCoord *= motionConvectionMult;
			motion.zCoord *= motionConvectionMult;
			
			return motion;
		}
		
		private Vec3 getLiftMotion(double simPosX, double simPosZ) {
			double scale = MathHelper.clamp(1D - (simPosX - (EntityNukeTorex.this.initPosX + torusWidth)), 0, 1) * motionLiftMult;
			
			Vec3 motion = Vec3.createVectorHelper(EntityNukeTorex.this.initPosX - this.initPosX, (EntityNukeTorex.this.initPosY + convectionHeight) - this.initPosY, EntityNukeTorex.this.initPosZ - this.initPosZ);
			
			motion = motion.normalize();
			motion.xCoord *= scale * animationSpeedGeneral;
			motion.yCoord *= scale * animationSpeedGeneral;
			motion.zCoord *= scale * animationSpeedGeneral;
			
			return motion;
		}
		
		private void updateColor() {
			this.prevColor = this.color;

			double exX = EntityNukeTorex.this.initPosX;
			double exY = EntityNukeTorex.this.initPosY + EntityNukeTorex.this.coreHeight;
			double exZ = EntityNukeTorex.this.initPosZ;

			double distX = exX - initPosX;
			double distY = exY - initPosY;
			double distZ = exZ - initPosZ;
			
			double distSq = distX * distX + distY * distY + distZ * distZ;
			distSq /= this.type == TorexType.SHOCK ? EntityNukeTorex.this.heat * 3 : EntityNukeTorex.this.heat;
			
			double col = 2D / Math.max(distSq, 1); //col goes from 2-0

			byte type = EntityNukeTorex.this.getType();
			
			this.color = EntityNukeTorex.this.getInterpColor(col, type);
		}
		
		public Vec3 getInterpPos(float interp) {
			return Vec3.createVectorHelper(
					this.prevPosX + (this.initPosX - this.prevPosX) * interp,
					this.prevPosY + (this.initPosY - this.prevPosY) * interp,
					this.prevPosZ + (this.initPosZ - this.prevPosZ) * interp
			);
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
			return startingScale + (float)curScale; //startingScale + ((float)age / (float)cloudletLife) * growingScale;
		}
		
		public Cloudlet setScale(float start, float grow, float growEnd) {
			this.startingScale = start;
			this.growingScale = grow;
			this.growingScaleEnd = growEnd;
			return this;
		}
		public Cloudlet setScale(float start, float grow) {
			return setScale(start,grow,grow);
		}
		
		public Cloudlet setMotion(double mult) {
			this.motionMult = mult;
			return this;
		}
	}
	
	public static enum TorexType {
		STANDARD(true),
		RING(true),
		CONDENSATION(false),
		SHOCK(false);
		public boolean isMainEffect;
		TorexType(boolean isMain) { this.isMainEffect = isMain; }
	}
	public static void spawnTorex(World world,EntityNukeTorex torex) {
		if (torex.boundEntity == null) {
			if (bindMe != null) {
				if (bindMe.isEntityAlive() && bindMe.ticksExisted < 10) {
					if ((bindMe.dimension == torex.dimension) && (Math.sqrt(bindMe.getPosition().distanceSq(torex.getPosition())) < 1.5)) {
						torex.boundEntity = bindMe;
						bindMe = null;
					}
				} else bindMe = null;
			}
		}
		torex.initPosX = torex.posX;
		torex.initPosY = torex.posY;
		torex.initPosZ = torex.posZ;
		torex.valid = true;
		world.weatherEffects.add(torex);
		TorexPacket packet = new TorexPacket();
		packet.entityId = torex.getEntityId();
		packet.uuid = torex.getUniqueID();
		packet.x = torex.initPosX;
		packet.y = torex.initPosY;
		packet.z = torex.initPosZ;
		torex.calculationFinished = torex.boundEntity == null;
		packet.doWait = torex.boundEntity != null;
		NBTTagCompound nbt = new NBTTagCompound();
		torex.writeEntityToNBT(nbt);
		packet.nbt = nbt;
		double amp = torex.getScale()*100;
		PacketDispatcher.wrapper.sendToAllAround(packet,new NetworkRegistry.TargetPoint(
						torex.dimension,
						packet.x,
						packet.y,
						packet.z,
						200+amp+Math.pow(amp,0.8)*8
				)
		);
		//PacketDispatcher.wrapper.sendToDimension(packet,torex.dimension);
	}
	
	public static void statFac(World world, double x, double y, double z, float scale) {
		statFac(world,x,y,z,scale,true);
	}
	public static void statFac(World world, double x, double y, double z, float scale, boolean sound) {
		scale = (float)Math.min(Math.pow(scale/10,0.9)*10,scale);
		EntityNukeTorex torex = new EntityNukeTorex(world).setScale(MathHelper.clamp(scale * 0.01F, 0.25F, 5F));
		torex.setPosition(x, y, z);
		torex.sound = sound;
		spawnTorex(world,torex);
	}
	
	public static void statFacBale(World world, double x, double y, double z, float scale) {
		statFacBale(world,x,y,z,scale,true);
	}
	public static void statFacBale(World world, double x, double y, double z, float scale, boolean sound) {
		EntityNukeTorex torex = new EntityNukeTorex(world).setScale(MathHelper.clamp(scale * 0.01F, 0.25F, 5F)).setType(1);
		torex.setPosition(x, y, z);
		torex.sound = sound;
		spawnTorex(world,torex);
	}
	public static class TorexFinishPacket extends RecordablePacket {
		private UUID uuid;
		public TorexFinishPacket() {
		}
		@Override
		public void fromBits(LeafiaBuf buf) {
			this.uuid = new UUID(buf.readLong(), buf.readLong());
		}
		@Override
		public void toBits(LeafiaBuf buf) {
			buf.writeLong(uuid.getMostSignificantBits());
			buf.writeLong(uuid.getLeastSignificantBits());
		}
		public static class Handler implements IMessageHandler<TorexFinishPacket, IMessage> {
			@Override
			@SideOnly(Side.CLIENT)
			public IMessage onMessage(TorexFinishPacket message, MessageContext ctx) {
				Minecraft.getMinecraft().addScheduledTask(() -> {
					for (EntityNukeTorex torex : waitingTorexes) {
						if (torex.getUniqueID().equals(message.uuid) || torex.backupUUID.equals(message.uuid)) {
							torex.calculationFinished = true;
							waitingTorexes.remove(torex);
							break;
						}
					}
				});
				return null;
			}
		}
	}
	public static class TorexPacket extends RecordablePacket {
		private int entityId;
		private double x;
		private double y;
		private double z;
		private NBTTagCompound nbt;
		private UUID uuid;
		private boolean doWait;
		public TorexPacket() {
		}
		@Override
		public void fromBits(LeafiaBuf buf) {
			this.entityId = buf.readInt();
			this.uuid = new UUID(buf.readLong(), buf.readLong());
			this.x = buf.readDouble();
			this.y = buf.readDouble();
			this.z = buf.readDouble();
			this.doWait = buf.readBoolean();
			this.nbt = buf.readNBT();
		}
		@Override
		public void toBits(LeafiaBuf buf) {
			buf.writeInt(this.entityId);
			buf.writeLong(uuid.getMostSignificantBits());
			buf.writeLong(uuid.getLeastSignificantBits());
			buf.writeDouble(this.x);
			buf.writeDouble(this.y);
			buf.writeDouble(this.z);
			buf.writeBoolean(this.doWait);
			buf.writeNBT(nbt);
		}
		public static class Handler implements IMessageHandler<TorexPacket, IMessage> {
			@Override
			@SideOnly(Side.CLIENT)
			public IMessage onMessage(TorexPacket message, MessageContext ctx) {
				Minecraft.getMinecraft().addScheduledTask(() -> {
					Minecraft mc = Minecraft.getMinecraft();
					EntityNukeTorex torex = new EntityNukeTorex(mc.world);
					torex.setEntityId(message.entityId);
					torex.setUniqueId(message.uuid);
					torex.valid = true;
					torex.setPosition(message.x,message.y,message.z);
					torex.initPosX = message.x;
					torex.initPosY = message.y;
					torex.initPosZ = message.z;
					EntityTracker.updateServerPosition(torex,message.x,message.y,message.z);
					torex.readEntityFromNBT(message.nbt);
					mc.world.addWeatherEffect(torex);
					if (message.doWait) {
						torex.calculationFinished = false;
						torex.backupUUID = message.uuid;
						waitingTorexes.add(torex);
					}
				});
				return null;
			}
		}
	}
}
