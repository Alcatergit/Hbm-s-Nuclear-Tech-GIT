
package com.hbm.entity.logic;

import java.util.ArrayList;
import java.util.List;

import com.hbm.config.BombConfig;
import com.hbm.config.CompatibilityConfig;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.entity.mob.EntityGlowingOne;
import com.hbm.main.MainRegistry;

import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import net.minecraft.util.math.ChunkPos;

import org.apache.logging.log4j.Level;

import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.config.BombConfig;
import com.hbm.config.GeneralConfig;
import com.hbm.config.CompatibilityConfig;
import com.hbm.util.ContaminationUtil;
import com.hbm.entity.effect.EntityFalloutUnderGround;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.explosion.ExplosionNukeRayBatched;
import com.hbm.main.MainRegistry;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;

public class EntityNukeExplosionMK5 extends Entity implements IChunkLoader {
	//Strength of the blast
	public int strength;
	//How many rays are calculated per tick
	public int speed;
	public int radius;
	
	public boolean mute = false;
	public boolean spawnFire = false;

	public boolean fallout = true;
	private boolean floodPlease = false;
	private int falloutAdd = 0;
	private Ticket loaderTicket;

	ExplosionNukeRayBatched explosion;
	EntityFalloutUnderGround falloutBall;
	EntityFalloutRain falloutRain;

	private int nukeTickNumber = 0;


	public EntityNukeExplosionMK5(World world) {
		super(world);
	}

	public EntityNukeExplosionMK5(World world, int strength, int speed, int radius) {
		super(world);
		this.strength = strength;
		this.speed = speed;
		this.radius = radius;
	}

	@Override
	public void onUpdate() {
		if(strength == 0 || !CompatibilityConfig.isWarDim(world)) {
			this.clearLoadedChunks();
			this.unloadMainChunk();
			this.setDead();
			return;
		}
		//load own chunk
		loadMainChunk();
		
		float rads = 0;
		
		//radiate until there is fallout rain
		if(!world.isRemote && fallout && falloutRain == null) {
			rads = 0.2F * (float)Math.pow(radius, 3) * (float)Math.pow(0.5, this.ticksExisted*0.025);
			if(ticksExisted == 42)
				EntityGlowingOne.convertInRadiusToGlow(world, this.posX, this.posY, this.posZ, radius * 1.5);
		}

		ContaminationUtil.radiate(world, this.posX, this.posY, this.posZ, radius * 2, rads, 0F, (float)Math.pow(radius, 3) * (float)Math.pow(0.5, this.ticksExisted*0.0125), (float)Math.pow(radius, 3) * 0.01F, this.ticksExisted * 1.5F);

		//make some noise
		if(!mute) {
			if(this.radius > 15){
				this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.AMBIENT, this.radius * 0.05F, 0.8F + this.rand.nextFloat() * 0.2F);
			}else{
				if(rand.nextInt(5) == 0)
					this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, this.radius * 0.05F, 0.8F + this.rand.nextFloat() * 0.2F);
			}
		}

		//Create Explosion Rays
		if(explosion == null) {
			explosion = new ExplosionNukeRayBatched(world, (int) this.posX, (int) this.posY, (int) this.posZ, this.strength, this.radius);
		}

		//Calculating crater
		if(!explosion.isAusf3Complete) {
			explosion.collectTip(speed * 10);

		//Excecuting destruction
		} else if(explosion.perChunk.size() > 0) {
			if(nukeTickNumber >= BombConfig.nukeTickSpacing){
				long start = System.currentTimeMillis();
				while(explosion.perChunk.size() > 0 && System.currentTimeMillis() < start + BombConfig.mk5){
					explosion.processChunk();
				}
				nukeTickNumber = 0;
			}
			nukeTickNumber++;
		
		} else {
			boolean craterReady = true;
			if(fallout) {
				//Do radial Fallout
				if(falloutBall == null){
					falloutBall = new EntityFalloutUnderGround(this.world);
					falloutBall.posX = this.posX;
					falloutBall.posY = this.posY;
					falloutBall.posZ = this.posZ;
					falloutBall.setScale((int) (this.radius * (BombConfig.falloutRange / 100F) + falloutAdd));
					this.world.spawnEntity(falloutBall);
				}
				//Wait for falloutBall to be done
				craterReady = falloutBall.done;
			}
			if(!craterReady) return;

			
			falloutRain = new EntityFalloutRain(this.world);
			falloutRain.doFallout = fallout && !explosion.isContained;
			falloutRain.doFlood = floodPlease;
			falloutRain.posX = this.posX;
			falloutRain.posY = this.posY;
			falloutRain.posZ = this.posZ;
			if(spawnFire)
				falloutRain.spawnFire = true;
			falloutRain.setScale((int) ((this.radius * 2.5F + falloutAdd) * BombConfig.falloutRange * 0.01F), this.radius+4);
			this.world.spawnEntity(falloutRain);
			this.clearLoadedChunks();
			unloadMainChunk();
			this.setDead();
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

	private static boolean isWet(World world, BlockPos pos){
		Biome b = world.getBiome(pos);
		return b.getTempCategory() == Biome.TempCategory.OCEAN || b.isHighHumidity() || b == Biomes.BEACH || b == Biomes.OCEAN || b == Biomes.RIVER  || b == Biomes.DEEP_OCEAN || b == Biomes.FROZEN_OCEAN || b == Biomes.FROZEN_RIVER || b == Biomes.STONE_BEACH || b == Biomes.SWAMPLAND;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {

	}

	public static EntityNukeExplosionMK5 statFac(World world, int r, double x, double y, double z) {
		if(GeneralConfig.enableExtendedLogging && !world.isRemote)
			MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized explosion at " + x + " / " + y + " / " + z + " with radius " + r + "!");

		if(r == 0)
			r = 25;

		EntityNukeExplosionMK5 mk5 = new EntityNukeExplosionMK5(world);

		mk5.strength = (int)(2*r);
		mk5.speed = (int)Math.ceil(100000 / mk5.strength);
		mk5.radius = r;

		mk5.setPosition(x, y, z);
		mk5.floodPlease = isWet(world, new BlockPos(x, y, z));
		if(BombConfig.disableNuclear)
			mk5.fallout = false;
		return mk5;
	}

	public static EntityNukeExplosionMK5 statFacFire(World world, int r, double x, double y, double z) {
		
		EntityNukeExplosionMK5 mk5 = statFac(world, r, x, y ,z);
		mk5.spawnFire = true;
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
