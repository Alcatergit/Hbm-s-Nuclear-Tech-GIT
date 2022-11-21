package com.hbm.entity.effect;

import java.util.*;

import com.hbm.config.RadiationConfig;
import com.hbm.config.BombConfig;
import com.hbm.interfaces.IConstantRenderer;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.saveddata.AuxSavedData;

//Chunkloading stuff
import java.util.ArrayList;
import java.util.List;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.main.MainRegistry;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import net.minecraft.util.math.ChunkPos;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class EntityDrying extends Entity implements IConstantRenderer, IChunkLoader {
	private static final DataParameter<Integer> SCALE = EntityDataManager.createKey(EntityFalloutRain.class, DataSerializers.VARINT);
	public int revProgress;
	public int radProgress;
	public boolean done=false;

	public boolean dryingmode=true;

	private Ticket loaderTicket;


	private int maxArea = 1;
	private int areaProcessed = 0;

	private int speed = (int)(BombConfig.mk4/2F);

	private int xlast;

	public EntityDrying(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(4, 20);
		this.ignoreFrustumCheck = false;
		this.isImmuneToFire = true;

	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(this.posX, this.posY, this.posZ, this.posX, this.posY, this.posZ);
	}

	@Override
	public boolean isInRangeToRender3d(double x, double y, double z) {
		return true;
	}

	@Override
	public boolean isInRangeToRenderDist(double distance) {
		return true;
	}

	public EntityDrying(World p_i1582_1_, int maxage) {
		super(p_i1582_1_);
		this.setSize(4, 20);
		this.isImmuneToFire = true;
	}

	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, world, Type.ENTITY));
		this.dataManager.register(SCALE, Integer.valueOf(0));
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
	public void onUpdate() {

		if(!world.isRemote) {
			int radius = getScale();
			int counter = 0;

			if(this.areaProcessed < this.maxArea && this.xlast < radius){
				for(int x = (int)(this.posX + this.xlast); x <= this.posX + radius; x++) {
					for(int z = (int)(this.posZ - radius) ; z <= this.posZ + radius; z++) {
						if(counter <= speed){
							this.dry(new MutableBlockPos(x, 0, z));
							this.areaProcessed+=1;
							counter+=1;
						}else{
							return;
						}
					}
					this.xlast+=1;
				}
			}else{
				done=true;
				this.setDead();
				if(RadiationConfig.rain > 0 && getScale() > 150) {
					world.getWorldInfo().setRaining(true);
					world.getWorldInfo().setThundering(true);
					world.getWorldInfo().setRainTime(RadiationConfig.rain);
					world.getWorldInfo().setThunderTime(RadiationConfig.rain);
					AuxSavedData.setThunder(world, RadiationConfig.rain);
				}
			}
		}
	}

	private void dry(MutableBlockPos pos) {
		if(dryingmode){
			for(int y = 255; y > 1; y--) {
				pos.setY(y);
				if(!world.isAirBlock(pos)){
					Block b = world.getBlockState(pos).getBlock();
					if(b == Blocks.WATER || b == Blocks.FLOWING_WATER){
						world.setBlockToAir(pos);
					}
				}
			}
		} else {
			for(int y = 62; y > 1; y--) {
				pos.setY(y);
				if(world.isAirBlock(pos)){
					world.setBlockState(pos, Blocks.WATER.getDefaultState());
				} else if(world.getBlockState(pos).getBlock() == Blocks.FLOWING_WATER){
					world.setBlockState(pos, Blocks.WATER.getDefaultState());
				} 
			}
		}
	}

	

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		setScale(p_70037_1_.getInteger("scale"));
		revProgress = p_70037_1_.getInteger("revProgress");
		radProgress = p_70037_1_.getInteger("radProgress");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		p_70014_1_.setInteger("scale", getScale());
		p_70014_1_.setInteger("revProgress", revProgress);
		p_70014_1_.setInteger("radProgress", radProgress);

	}

	public void setScale(int i) {
		this.dataManager.set(SCALE, Integer.valueOf(i));
		this.maxArea = i * i * 8;
		this.xlast = -i;
		if(i > 150)
			this.speed = (int)(BombConfig.mk4);
	}

	public int getScale() {

		int scale = this.dataManager.get(SCALE);

		return scale == 0 ? 1 : scale;
	}
}
