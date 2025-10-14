
package com.hbm.entity.logic;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.mob.EntityGlowingOne;
import com.hbm.main.AdvancementManager;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.biome.*;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import net.minecraft.util.math.ChunkPos;

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
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;

public class EntityNukeExplosionMK5 extends Entity implements IChunkLoader {
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
	private Ticket loaderTicket;

	ExplosionNukeRayBatched explosion;
	EntityFalloutRain falloutRain;

	public static final double shockSpeed = 2; //in blocks/t


	public EntityNukeExplosionMK5(World world) {
		super(world);
	}

	@Override
	public void onUpdate() {
		if(world.isRemote) return;

		if(strength == 0 || !CompatibilityConfig.isWarDim(world)) {
			this.clearLoadedChunks();
			this.unloadMainChunk();
			this.setDead();
			return;
		}
		//load own chunk
		loadMainChunk();
		
		float rads, fire, blast;
		rads = 0;
		//radiate until there is fallout rain
		if(fallout && falloutRain == null) {
			rads = (float)Math.min(10_000_000, Math.pow(radius, 3) * (float)Math.pow(0.5, (double) 2 * this.ticksExisted / radius) + strength);
			if(ticksExisted == 1){
				EntityGlowingOne.convertInRadiusToGlow(world, this.posX, this.posY, this.posZ, radius * 1.5);
                if(radius > 60){
                    for(EntityPlayer player : world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(this.posX, this.posY, this.posZ, this.posX, this.posY, this.posZ).grow(radius * 2, radius * 2, radius * 2))) {
                        AdvancementManager.grantAchievement(player, AdvancementManager.progress_nuke);
                    }
                }
            }
		}
		
		if(ticksExisted < 2400){
			fire = (float)(fallout ? 10F: 0.5F * Math.pow(radius + 10, 3) * Math.pow(0.5, 0.5 * this.ticksExisted / radius));
			blast = (float)Math.pow(radius + 10, 3) * 0.1F;
			ContaminationUtil.radiate(world, this.posX, this.posY, this.posZ, Math.min(1000, radius * 2), rads, 0F, fire, blast, this.ticksExisted * shockSpeed);
		}
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
			} else if (this.ticksExisted * shockSpeed > 160){ //wait for shockwave to complete

				this.clearLoadedChunks();
				this.unloadMainChunk();
				this.setDead();
			}
		}
	}

	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, world, Type.ENTITY));
	}

	@Override
	public void init(Ticket ticket) {
		if(!world.isRemote && ticket != null) {
            	
            if(loaderTicket == null) {
            	loaderTicket = ticket;
            	loaderTicket.bindEntity(this);
            	loaderTicket.getModData();
            }

            ForgeChunkManager.forceChunk(loaderTicket, new ChunkPos(chunkCoordX, chunkCoordZ));
        }
	}


	List<ChunkPos> loadedChunks = new ArrayList<ChunkPos>();
	@Override
	public void loadNeighboringChunks(int newChunkX, int newChunkZ) {
		if(!world.isRemote && loaderTicket != null)
        {
            for(ChunkPos chunk : loadedChunks) {
                ForgeChunkManager.unforceChunk(loaderTicket, chunk);
            }

            loadedChunks.clear();
            loadedChunks.add(new ChunkPos(newChunkX, newChunkZ));
            loadedChunks.add(new ChunkPos(newChunkX + 1, newChunkZ + 1));
            loadedChunks.add(new ChunkPos(newChunkX - 1, newChunkZ - 1));
            loadedChunks.add(new ChunkPos(newChunkX + 1, newChunkZ - 1));
            loadedChunks.add(new ChunkPos(newChunkX - 1, newChunkZ + 1));
            loadedChunks.add(new ChunkPos(newChunkX + 1, newChunkZ));
            loadedChunks.add(new ChunkPos(newChunkX, newChunkZ + 1));
            loadedChunks.add(new ChunkPos(newChunkX - 1, newChunkZ));
            loadedChunks.add(new ChunkPos(newChunkX, newChunkZ - 1));

            for(ChunkPos chunk : loadedChunks) {
                ForgeChunkManager.forceChunk(loaderTicket, chunk);
            }
        }
	}

	public void clearLoadedChunks() {
		if(!world.isRemote && loaderTicket != null && loadedChunks != null) {
			for(ChunkPos chunk : loadedChunks) {
				ForgeChunkManager.unforceChunk(loaderTicket, chunk);
			}
		}
	}

	private ChunkPos mainChunk;
	public void loadMainChunk() {
		if(!world.isRemote && loaderTicket != null && this.mainChunk == null) {
			this.mainChunk = new ChunkPos((int) Math.floor(this.posX / 16D), (int) Math.floor(this.posZ / 16D));
			ForgeChunkManager.forceChunk(loaderTicket, this.mainChunk);
		}
	}
	public void unloadMainChunk() {
		if(!world.isRemote && loaderTicket != null && this.mainChunk != null) {
			ForgeChunkManager.unforceChunk(loaderTicket, this.mainChunk);
		}
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
		mk5.floodPlease = isWet(world, new BlockPos(x, y, z));
		if(BombConfig.disableNuclear)
			mk5.fallout = false;
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
