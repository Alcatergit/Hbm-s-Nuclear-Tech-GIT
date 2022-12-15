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

import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.config.BombConfig;
import com.hbm.config.GeneralConfig;
import com.hbm.entity.effect.EntityFalloutUnderGround;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.entity.effect.EntityRainDrop;
import com.hbm.entity.effect.EntityDrying;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.explosion.ExplosionNukeRay;
import com.hbm.main.MainRegistry;
import com.hbm.saveddata.RadiationSavedData;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

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

public class EntityNukeExplosionMK4 extends Entity implements IChunkLoader {
	// Strength of the blast
	public int radius;
	// How many rays are calculated per tick
	public int speed;
	
	public boolean mute = false;

	public boolean fallout = true;
	private boolean floodPlease = false;
	private int falloutAdd = 0;
	private Ticket loaderTicket;

	ExplosionNukeRay explosion;
	EntityFalloutUnderGround falloutBall;
	EntityDrying dryingBomb;
	EntityFalloutRain falloutRain;
	EntityRainDrop rainDrop;
	EntityDrying waterBomb;

	public EntityNukeExplosionMK4(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityNukeExplosionMK4(World world, int radius, int speed) {
		super(world);
		this.radius = radius;
		this.speed = speed;
	}

	private void radiate(float rads, double range) {
		
		List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range));
		MutableBlockPos rayPos = new BlockPos.MutableBlockPos();
		for(EntityLivingBase e : entities) {
			
			Vec3 vec = Vec3.createVectorHelper(e.posX - posX, (e.posY + e.getEyeHeight()) - posY, e.posZ - posZ);
			double len = vec.lengthVector();
			vec = vec.normalize();
			
			float res = 0;
			
			for(int i = 1; i < len; i++) {

				int ix = (int)Math.floor(posX + vec.xCoord * i);
				int iy = (int)Math.floor(posY + vec.yCoord * i);
				int iz = (int)Math.floor(posZ + vec.zCoord * i);
				
				res += world.getBlockState(rayPos.setPos(posX+vec.xCoord * i, posY+vec.yCoord * i, posZ+vec.zCoord * i)).getBlock().getExplosionResistance(null);
			}
			
			if(res < 1)
				res = 1;
			
			float eRads = rads;
			eRads /= (float)res;
			eRads /= (float)(len * len);
			
			ContaminationUtil.contaminate(e, HazardType.RADIATION, ContaminationType.CREATIVE, eRads);
		}
	}

	@Override
	public void onUpdate() {
		if(radius == 0) {
			this.setDead();
			return;
		}

		if(!world.isRemote && fallout && falloutRain == null) {
			RadiationSavedData.getData(world);

			// float radMax = (float) (length / 2F * Math.pow(length, 2) / 35F);
			float radMax = Math.min((float) (Math.pow(radius, 2.5) * 17.5F), 1500000);
			// System.out.println(radMax);
			float rad = radMax / 10F;
			RadiationSavedData.incrementRad(world, this.getPosition(), rad, radMax);
			this.radiate(radMax * 0.1F, radius);
		}

		if(!mute) {
			this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.AMBIENT, 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
			if(rand.nextInt(5) == 0)
				this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
		}
		ExplosionNukeGeneric.dealDamage(this.world, this.posX, this.posY, this.posZ, this.radius * 2);

		if(dryingBomb == null){
			dryingBomb = new EntityDrying(this.world);
			dryingBomb.posX = this.posX;
			dryingBomb.posY = this.posY;
			dryingBomb.posZ = this.posZ;
			dryingBomb.setScale(this.radius+16);
			this.world.spawnEntity(dryingBomb);
		}
		if(dryingBomb.done){

			if(explosion == null) {

				explosion = new ExplosionNukeRay(world, (int) this.posX, (int) this.posY, (int) this.posZ, this.radius);
			}
			if(!explosion.isAusf3Complete) {
				explosion.collectTipMk6(speed);
			} else if(explosion.getStoredSize() > 0) {
				explosion.processTip(BombConfig.mk4);
			} else if(fallout) {
				if(falloutBall == null){
					falloutBall = new EntityFalloutUnderGround(this.world);
					falloutBall.posX = this.posX;
					falloutBall.posY = this.posY;
					falloutBall.posZ = this.posZ;
					falloutBall.setScale((int) (this.radius * (BombConfig.falloutRange / 100F) + falloutAdd));
					this.world.spawnEntity(falloutBall);
				}
				if(falloutBall.done){
					if(floodPlease){
						if(waterBomb == null){
							waterBomb = new EntityDrying(this.world);
							waterBomb.posX = this.posX;
							waterBomb.posY = this.posY;
							waterBomb.posZ = this.posZ;
							waterBomb.dryingmode = false;
							waterBomb.setScale(this.radius+18);
							this.world.spawnEntity(waterBomb);
						} else if(waterBomb.done){
							if(!explosion.isContained){
								falloutRain = new EntityFalloutRain(this.world);
								falloutRain.posX = this.posX;
								falloutRain.posY = this.posY;
								falloutRain.posZ = this.posZ;
								falloutRain.setScale((int) (this.radius * (1F+(BombConfig.falloutRange / 100F)) + falloutAdd), this.radius+32);
								this.world.spawnEntity(falloutRain);
							}
							this.setDead();
						}
					} else {
						if(!explosion.isContained){
							falloutRain = new EntityFalloutRain(this.world);
							falloutRain.posX = this.posX;
							falloutRain.posY = this.posY;
							falloutRain.posZ = this.posZ;
							falloutRain.setScale((int) (this.radius * (1F+(BombConfig.falloutRange / 100F)) + falloutAdd), this.radius+32);
							this.world.spawnEntity(falloutRain);
						}
						this.setDead();
					}
				}
			} else {
				if(floodPlease){
					if(waterBomb == null){
						waterBomb = new EntityDrying(this.world);
						waterBomb.posX = this.posX;
						waterBomb.posY = this.posY;
						waterBomb.posZ = this.posZ;
						waterBomb.dryingmode = false;
						waterBomb.setScale(this.radius+18);
						this.world.spawnEntity(waterBomb);
					} else if(waterBomb.done){
						rainDrop = new EntityRainDrop(this.world);
						rainDrop.posX = this.posX;
						rainDrop.posY = this.posY;
						rainDrop.posZ = this.posZ;
						rainDrop.setScale((int)this.radius+16);
						this.world.spawnEntity(rainDrop);
						this.setDead();
					}
				}else {
					rainDrop = new EntityRainDrop(this.world);
					rainDrop.posX = this.posX;
					rainDrop.posY = this.posY;
					rainDrop.posZ = this.posZ;
					rainDrop.setScale((int)this.radius+16);
					this.world.spawnEntity(rainDrop);
					this.setDead();
				}
			}
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

	public static EntityNukeExplosionMK4 statFac(World world, int r, double x, double y, double z) {
		if(GeneralConfig.enableExtendedLogging && !world.isRemote)
			MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized explosion at " + x + " / " + y + " / " + z + " with radius " + r + "!");

		if(r == 0)
			r = 25;

		EntityNukeExplosionMK4 mk4 = new EntityNukeExplosionMK4(world);
		mk4.radius = (int) (r);
		mk4.speed = (int) 1000*BombConfig.mk4/r;
		mk4.setPosition(x, y, z);
		mk4.floodPlease = isWet(world, new BlockPos(x, y, z));
		if(BombConfig.disableNuclear)
			mk4.fallout = false;
		return mk4;
	}

	public static EntityNukeExplosionMK4 statFacExperimental(World world, int r, double x, double y, double z) {

		if(GeneralConfig.enableExtendedLogging && !world.isRemote)
			MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized eX explosion at " + x + " / " + y + " / " + z + " with radius " + r + "!");

		EntityNukeExplosionMK4 mk4 = new EntityNukeExplosionMK4(world);
		mk4.radius = (int) (r);
		mk4.speed = (int) 1000*BombConfig.mk4/r;
		mk4.setPosition(x, y, z);
		mk4.floodPlease = isWet(world, new BlockPos(x, y, z));
		if(BombConfig.disableNuclear)
			mk4.fallout = false;
		return mk4;
	}

	public static EntityNukeExplosionMK4 statFacNoRad(World world, int r, double x, double y, double z) {

		if(GeneralConfig.enableExtendedLogging && !world.isRemote)
			MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized nR explosion at " + x + " / " + y + " / " + z + " with radius " + r + "!");

		EntityNukeExplosionMK4 mk4 = new EntityNukeExplosionMK4(world);
		mk4.radius = (int) (r);
		mk4.speed = (int) 1000*BombConfig.mk4/r;
		mk4.setPosition(x, y, z);
		mk4.floodPlease = isWet(world, new BlockPos(x, y, z));
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
