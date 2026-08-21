package com.hbm.entity.logic;

import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.grenade.EntityGrenadeASchrab;
import com.hbm.entity.grenade.EntityGrenadeNuclear;
import com.hbm.entity.missile.EntityMIRV;
import com.hbm.entity.mob.EntityGlowingOne;
import com.hbm.entity.mob.EntityThermonuclearCat;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.entity.projectile.EntityExplosiveBeam;
import com.hbm.entity.projectile.EntityMiniMIRV;
import com.hbm.entity.projectile.EntityMiniNuke;
import com.hbm.handler.ArmorUtil;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.AdvancementManager;

import com.hbm.render.amlfrom1710.Vec3;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.biome.*;

import org.apache.logging.log4j.Level;

import com.hbm.config.BombConfig;
import com.hbm.config.GeneralConfig;
import com.hbm.config.CompatibilityConfig;
import com.hbm.util.ContaminationUtil;
import com.hbm.entity.effect.EntityFalloutUnderGround;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.explosion.ExplosionNukeRayBatched;
import com.hbm.main.MainRegistry;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import java.util.List;

import static com.hbm.entity.mob.EntityGlowingOne.convertToGlow;
import static com.hbm.entity.mob.EntityThermonuclearCat.convertToThermo;

public class EntityNukeExplosionMK5 extends EntityChunky {
	//Strength of the blast
	public int strength;
	//Radius
	public int radius;

	public boolean mute = false;
	public boolean spawnFire = false;

	private boolean fallingStarted = false;
	public boolean fallout = true;
	private boolean floodPlease = false;
	private int falloutAdd = 0;

	ExplosionNukeRayBatched explosion;
	EntityFalloutRain falloutRain;

	public EntityNukeExplosionMK5(World world) {
		super(world);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if(world.isRemote) return;

		if(strength == 0 || !CompatibilityConfig.isWarDim(world)) {
			this.setDead();
			return;
		}

		if(ticksExisted == 1 && fallout && radius > 60){
			for(EntityPlayer player : world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(this.posX, this.posY, this.posZ, this.posX, this.posY, this.posZ).grow(radius * 2, radius * 2, radius * 2))) {
				AdvancementManager.grantAchievement(player, AdvancementManager.progress_nuke);
			}
		}

		double weatherFactor = getWeatherAttenuationFactor(world, this.posX, this.posY, this.posZ);
		dealDamage(world, this.posX, this.posY, this.posZ, this.radius * 2.0F, weatherFactor);

		//make some noise
		if(!mute) {
			if(this.radius > 30){
				this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.AMBIENT, Math.min(1, ticksExisted/200F) * this.radius * 0.05F, 0.8F + this.rand.nextFloat() * 0.2F);
			}else{
				this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, Math.min(1, ticksExisted/100F) * Math.max(2F, this.radius * 0.1F), 0.8F + this.rand.nextFloat() * 0.2F);
			}
		}

		//Create Explosion Rays
		if(explosion == null) {
			explosion = new ExplosionNukeRayBatched(world, this.posX, this.posY, this.posZ, this.strength, this.radius, this.floodPlease);
		}

		//Calculating crater
		if(!explosion.isAusf3Complete) {
			explosion.collectTip(BombConfig.mk5);

		//Excecuting destruction
		} else if(!explosion.perChunk.isEmpty()) {
			explosion.processChunk(BombConfig.mk5);

		} else {
			if(!fallingStarted) {
				if (fallout) {
					EntityFalloutUnderGround falloutBall = new EntityFalloutUnderGround(this.world);
					falloutBall.posX = this.posX;
					falloutBall.posY = this.posY;
					falloutBall.posZ = this.posZ;
					falloutBall.setScale((int) (this.radius * (BombConfig.falloutRange / 100F) + falloutAdd));

					falloutBall.falloutRainDoFallout = fallout && !explosion.isContained;
					falloutBall.falloutRainDoFlood = floodPlease;
					falloutBall.falloutRainRadius1 = (int) ((this.radius * 2.5F + falloutAdd) * BombConfig.falloutRange * 0.01F);
					falloutBall.falloutRainRadius2 = this.radius + 4;
					this.world.spawnEntity(falloutBall);
				} else {
					EntityFalloutRain falloutRain = new EntityFalloutRain(this.world);
					falloutRain.doFallout = false;
					falloutRain.doFlood = floodPlease;
					falloutRain.posX = this.posX;
					falloutRain.posY = this.posY;
					falloutRain.posZ = this.posZ;
					falloutRain.setScale((int) ((this.radius * 2.5F + falloutAdd) * BombConfig.falloutRange * 0.01F), this.radius + 4);
					this.world.spawnEntity(falloutRain);
				}
				fallingStarted = true;
			} else if (this.ticksExisted > this.radius * 3){ // wait for thermal radiation (fire damage) to complete before removing the entity
				this.setDead();
			}
		}
	}

	public void dealDamage(World world, double x, double y, double z, double radius, double weatherFactor) {
		List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(x-radius, y-radius, z-radius, x+radius, y+radius, z+radius));

		for(Entity e : entities) {
			Vec3 vec = Vec3.createVectorHelper(e.posX - x, (e.posY + e.getEyeHeight()) - y, e.posZ - z);
			double len = vec.length();

			if(len <= radius) {
				boolean isExplosionExempt =
						e instanceof EntityNukeTorex ||
								e instanceof EntityNukeExplosionMK5 ||
								e instanceof EntityMIRV ||
								e instanceof EntityMiniNuke ||
								e instanceof EntityMiniMIRV ||
								e instanceof EntityGrenadeASchrab ||
								e instanceof EntityGrenadeNuclear ||
								e instanceof EntityExplosiveBeam ||
								e instanceof EntityBulletBase;
				boolean isOcelot = e instanceof EntityOcelot;
				boolean isPlayerImmune = e instanceof EntityPlayer && (((EntityPlayer) e).isCreative() || ((EntityPlayer) e).isSpectator() || ArmorUtil.checkArmor(((EntityPlayer) e), ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots));

				vec = vec.normalize();
				double dmgLen = Math.max(len, radius * 0.05D);

				float res = 0;

				for(int i = 1; i < len; i++) {
					int ix = (int)Math.floor(x + vec.xCoord * i);
					int iy = (int)Math.floor(y + vec.yCoord * i);
					int iz = (int)Math.floor(z + vec.zCoord * i);
					res += world.getBlockState(new BlockPos(ix, iy, iz)).getBlock().getExplosionResistance(null);
				}
				boolean isLiving = e instanceof EntityLivingBase;

				if(res < 1)
					res = 1;

				if(!isExplosionExempt && isLiving && fallout && this.ticksExisted <= Math.max((int)Math.ceil(this.radius * 0.02), 1)){
					float eRads = (float)Math.min(10_000_000, Math.pow(radius, 3) * (float)Math.pow(0.5, (double)2 * this.ticksExisted / radius) + strength);
					eRads *= (float)Math.exp(-dmgLen * weatherFactor / 150.0D);
					eRads /= (float)(dmgLen * dmgLen * Math.sqrt(res));

					ContaminationUtil.contaminate((EntityLivingBase)e, ContaminationUtil.HazardType.RADIATION, ContaminationUtil.ContaminationType.CREATIVE, eRads);
					if (eRads >= 100 && e instanceof EntityZombie) {
						if(e instanceof EntityGlowingOne) continue;
						convertToGlow(world, (EntityZombie) e);
					}
					if (eRads >= 100 && e instanceof EntityOcelot && this.radius > 120) {
						if(e instanceof EntityThermonuclearCat) continue;
						convertToThermo(world, (EntityOcelot) e);
					}
				}

				int thermalDuration = this.radius * 3;
				double currentThermalRadius = radius * (1.0 - Math.pow((double)(this.ticksExisted - 1) / thermalDuration, 0.5));

				if ((!isExplosionExempt && !isOcelot && !isPlayerImmune) && this.radius > 25 && this.ticksExisted <= thermalDuration && res < 2000 && len <= currentThermalRadius) {
					float fireDamage = (float) ((0.35F * Math.pow(radius + 10, 3) * Math.pow(0.5, 0.5 * this.ticksExisted / radius) * Math.exp(-dmgLen * weatherFactor / 80.0D)) / (float) (dmgLen * dmgLen * res));
					if (fireDamage > 0.025) {
						if (fireDamage > 0.1 && e instanceof EntityPlayer p) {
							if (p.getHeldItemMainhand().getItem() == ModItems.marshmallow && p.getRNG().nextInt((int) len) == 0) {
								p.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(ModItems.marshmallow_roasted));
							}
							if (p.getHeldItemOffhand().getItem() == ModItems.marshmallow && p.getRNG().nextInt((int) len) == 0) {
								p.setHeldItem(EnumHand.OFF_HAND, new ItemStack(ModItems.marshmallow_roasted));
							}
						}
						if (!e.isImmuneToFire()) {
							e.setFire(5);
							e.attackEntityFrom(ModDamageSource.IN_FIRE, fireDamage);
						}
					}
				}

				int blastDuration = (int)Math.ceil(80 * Math.cbrt(this.radius / 100.0));
				double shockSpeed = 2D * this.radius / (double)blastDuration;
				double currentBlastRadius = this.ticksExisted * Math.max(2D, shockSpeed);

				if ((!isExplosionExempt && !isOcelot && !isPlayerImmune) && this.ticksExisted <= (shockSpeed < 2D ? this.radius : blastDuration) && res < 10000 && len < currentBlastRadius) {
					float blastDamage = (float)(Math.pow(radius + 10, 3) * (this.radius > 25 ? 0.5F : 0.85F)) / (float)(dmgLen * dmgLen * dmgLen * res);
					if(blastDamage > 0.025){
						if(fallout) e.attackEntityFrom(ModDamageSource.nuclearBlast, blastDamage);
						else e.attackEntityFrom(ModDamageSource.blast, blastDamage);
					}
					e.motionX += vec.xCoord * 0.075D * blastDamage;
					e.motionY += vec.yCoord * 0.075D * blastDamage;
					e.motionZ += vec.zCoord * 0.075D * blastDamage;
				}
			}
		}
	}

	private static double getWeatherAttenuationFactor(World world, double x, double y, double z) {
		BlockPos pos = new BlockPos(x, y, z);
		float rainfall = world.getBiome(pos).getRainfall();
		double factor = 0.6D + 0.9D * (double)rainfall;

		if(world.isRaining()) {
			if(world.isThundering())
				factor *= 1.8D;
			else
				factor *= 1.4D;
		}

		return Math.max(0.3D, Math.min(factor, 3.0D));
	}

	public static boolean isWet(World world, BlockPos pos){
		Biome b = world.getBiome(pos);
		return b.getTempCategory() == Biome.TempCategory.OCEAN || b.isHighHumidity() || b instanceof BiomeOcean || b instanceof BiomeBeach || b instanceof BiomeRiver || b instanceof BiomeJungle || b instanceof BiomeSwamp;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		radius = nbt.getInteger("radius");
		strength = nbt.getInteger("strength");
		falloutAdd = nbt.getInteger("falloutAdd");
		fallout = nbt.getBoolean("fallout");
		floodPlease = nbt.getBoolean("floodPlease");
		spawnFire = nbt.getBoolean("spawnFire");
		mute = nbt.getBoolean("mute");
		ticksExisted = nbt.getInteger("ticksExisted");
		if(nbt.hasKey("fs")) fallingStarted = nbt.getBoolean("fs");
		if(explosion == null) {
			explosion = new ExplosionNukeRayBatched(world, this.posX, this.posY, this.posZ, this.strength, this.radius, this.floodPlease);
		}
		explosion.readEntityFromNBT(nbt);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("radius", radius);
		nbt.setInteger("strength", strength);
		nbt.setInteger("falloutAdd", falloutAdd);
		nbt.setBoolean("fallout", fallout);
		nbt.setBoolean("floodPlease", floodPlease);
		nbt.setBoolean("spawnFire", spawnFire);
		nbt.setBoolean("mute", mute);
		nbt.setBoolean("fs", fallingStarted);
		nbt.setInteger("ticksExisted", ticksExisted);
		if(explosion != null) {
			explosion.writeEntityToNBT(nbt);
		}
	}

	public static EntityNukeExplosionMK5 statFac(World world, int r, double x, double y, double z) {
		if(GeneralConfig.enableExtendedLogging && !world.isRemote)
			MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized explosion at " + x + " / " + y + " / " + z + " with radius " + r + "!");

		if(r == 0)
			r = 25;

		EntityNukeExplosionMK5 mk5 = new EntityNukeExplosionMK5(world);

		mk5.strength = r<<1;
		mk5.radius = r;

		mk5.setPosition(x, y, z);
		if(CompatibilityConfig.doFillCraterWithWater) mk5.floodPlease = isWet(world, new BlockPos(x, y, z));
		if(BombConfig.disableNuclear) mk5.fallout = false;
		return mk5;
	}

	public static EntityNukeExplosionMK5 statFacNoRad(World world, int r, double x, double y, double z) {

		EntityNukeExplosionMK5 mk5 = statFac(world, r, x, y ,z);
		mk5.fallout = false;
		return mk5;
	}

	public static EntityNukeExplosionMK5 statFacNoRadFire(World world, int r, double x, double y, double z) {

		EntityNukeExplosionMK5 mk5 = statFac(world, r, x, y ,z);
		mk5.fallout = false;
		mk5.spawnFire = true;
		return mk5;
	}

	public EntityNukeExplosionMK5 moreFallout(int fallout) {
		falloutAdd = fallout;
		return this;
	}

	public EntityNukeExplosionMK5 mute() {
		this.mute = true;
		return this;
	}
}
