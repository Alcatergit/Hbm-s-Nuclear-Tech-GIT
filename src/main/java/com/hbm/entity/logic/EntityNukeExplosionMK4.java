package com.hbm.entity.logic;

import java.util.ArrayList;
import java.util.List;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.main.MainRegistry;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import net.minecraft.util.math.ChunkPos;

import org.apache.logging.log4j.Level;

import com.hbm.config.BombConfig;
import com.hbm.config.GeneralConfig;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.explosion.ExplosionNukeRay;
import com.hbm.main.MainRegistry;
import com.hbm.saveddata.RadiationSavedData;

import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class EntityNukeExplosionMK4 extends Entity implements IChunkLoader {
	// Strength of the blast
	public int radius;
	// How many rays are calculated per tick
	public int speed;
	
	public boolean mute = false;

	public boolean fallout = true;
	private int falloutAdd = 0;
	private Ticket loaderTicket;

	ExplosionNukeRay explosion;

	public EntityNukeExplosionMK4(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityNukeExplosionMK4(World world, int radius, int speed) {
		super(world);
		this.radius = radius;
		this.speed = speed;
	}

	@Override
	public void onUpdate() {
		if(radius == 0) {
			this.setDead();
			return;
		}

		if(!world.isRemote && fallout && explosion != null) {
			RadiationSavedData.getData(world);

			// float radMax = (float) (length / 2F * Math.pow(length, 2) / 35F);
			float radMax = Math.min((float) (radius * Math.pow(radius, 1.5) * 17.5F), 1500000);
			// System.out.println(radMax);
			float rad = radMax / 4F;
			RadiationSavedData.incrementRad(world, this.getPosition(), rad, radMax);
		}

		if(!mute) {
			this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.AMBIENT, 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
			if(rand.nextInt(5) == 0)
				this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
		}
		ExplosionNukeGeneric.dealDamage(this.world, this.posX, this.posY, this.posZ, this.radius * 2);

		if(explosion == null) {

			explosion = new ExplosionNukeRay(world, (int) this.posX, (int) this.posY, (int) this.posZ, this.radius);
		}
		if(!explosion.isAusf3Complete) {
			explosion.collectTipMk6(speed);
		} else if(explosion.getStoredSize() > 0) {
			explosion.processTip(BombConfig.mk4);
		} else if(fallout) {
			EntityFalloutRain fallout = new EntityFalloutRain(this.world);
			fallout.posX = this.posX;
			fallout.posY = this.posY;
			fallout.posZ = this.posZ;
			fallout.setScale((int) (this.radius * 1.8 + falloutAdd) * BombConfig.falloutRange / 100);

			this.world.spawnEntity(fallout);

			this.setDead();
		} else {
			this.setDead();
		}
	}

	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, world, Type.ENTITY));
	}

	@Override
	public void init(Ticket ticket) {
		if(!world.isRemote) {
			
            if(ticket != null) {
            	
                if(loaderTicket == null) {
                	
                	loaderTicket = ticket;
                	loaderTicket.bindEntity(this);
                	loaderTicket.getModData();
                }

                ForgeChunkManager.forceChunk(loaderTicket, new ChunkPos(chunkCoordX, chunkCoordZ));
            }
        }
	}

	List<ChunkPos> loadedChunks = new ArrayList<ChunkPos>();
	@Override
	public void loadNeighboringChunks(int newChunkX, int newChunkZ) {
		if(!world.isRemote && loaderTicket != null)
        {
            for(ChunkPos chunk : loadedChunks)
            {
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

            for(ChunkPos chunk : loadedChunks)
            {
                ForgeChunkManager.forceChunk(loaderTicket, chunk);
            }
        }
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {

	}

	public static EntityNukeExplosionMK4 statFac(World world, int r, double x, double y, double z) {
		if(GeneralConfig.enableExtendedLogging && !world.isRemote)
			MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized explosion at " + x + " / " + y + " / " + z + " with diameter " + r + "!");

		if(r == 0)
			r = 25;

		EntityNukeExplosionMK4 mk4 = new EntityNukeExplosionMK4(world);
		mk4.radius = (int) (r);
		mk4.speed = (int) 1000*BombConfig.mk4/r;
		mk4.setPosition(x, y, z);
		if(BombConfig.disableNuclear)
			mk4.fallout = false;
		return mk4;
	}

	public static EntityNukeExplosionMK4 statFacExperimental(World world, int r, double x, double y, double z) {

		if(GeneralConfig.enableExtendedLogging && !world.isRemote)
			MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized eX explosion at " + x + " / " + y + " / " + z + " with diameter " + r + "!");

		r *= 2;

		EntityNukeExplosionMK4 mk4 = new EntityNukeExplosionMK4(world);
		mk4.radius = (int) (r);
		mk4.speed = (int) 1000*BombConfig.mk4/r;
		mk4.setPosition(x, y, z);
		if(BombConfig.disableNuclear)
			mk4.fallout = false;
		return mk4;
	}

	public static EntityNukeExplosionMK4 statFacNoRad(World world, int r, double x, double y, double z) {

		if(GeneralConfig.enableExtendedLogging && !world.isRemote)
			MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized nR explosion at " + x + " / " + y + " / " + z + " with diameter " + r + "!");

		r *= 2;

		EntityNukeExplosionMK4 mk4 = new EntityNukeExplosionMK4(world);
		mk4.radius = (int) (r);
		mk4.speed = (int) 1000*BombConfig.mk4/r;
		mk4.setPosition(x, y, z);
		mk4.fallout = false;
		return mk4;
	}
	
	public EntityNukeExplosionMK4 moreFallout(int fallout) {
		falloutAdd = fallout;
		return this;
	}
	
	public EntityNukeExplosionMK4 mute() {
		this.mute = true;
		return this;
	}
}
