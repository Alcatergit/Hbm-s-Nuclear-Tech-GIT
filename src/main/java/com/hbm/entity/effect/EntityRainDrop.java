package com.hbm.entity.effect;

import java.util.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.config.VersatileConfig;
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


import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class EntityRainDrop extends Entity implements IConstantRenderer, IChunkLoader {
	private static final DataParameter<Integer> SCALE = EntityDataManager.createKey(EntityRainDrop.class, DataSerializers.VARINT);
	public int revProgress;
	public int radProgress;
	public boolean done=false;

	private Ticket loaderTicket;

	private double fallingRadius;

	private boolean firstTick = true;
	private final List<Long> chunksToProcess = new ArrayList<>();
	private final List<Long> outerChunksToProcess = new ArrayList<>();

	private static int tickDelayStatic = BombConfig.fChunkSpeed;
	private int tickDelay = 0;

	public EntityRainDrop(World p_i1582_1_) {
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

	public EntityRainDrop(World p_i1582_1_, int maxage) {
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

	private void unloadAllChunks() {
		if(loaderTicket != null){
			for(ChunkPos chunk : loadedChunks) {
		        ForgeChunkManager.unforceChunk(loaderTicket, chunk);
		    }
		}
	}

	private void gatherChunks() {
		Set<Long> chunks = new LinkedHashSet<>(); // LinkedHashSet preserves insertion order
		Set<Long> outerChunks = new LinkedHashSet<>();
		int outerRange = getScale();
		// Basically defines something like the step size, but as indirect proportion. The actual angle used for rotation will always end up at 360° for angle == adjustedMaxAngle
		// So yea, I mathematically worked out that 20 is a good value for this, with the minimum possible being 18 in order to reach all chunks
		int adjustedMaxAngle = 20 * outerRange / 32; // step size = 20 * chunks / 2
		for (int angle = 0; angle <= adjustedMaxAngle; angle++) {
			Vec3 vector = Vec3.createVectorHelper(outerRange, 0, 0);
			vector.rotateAroundY((float) (angle * Math.PI / 180.0 / (adjustedMaxAngle / 360.0))); // Ugh, mutable data classes (also, ugh, radians; it uses degrees in 1.18; took me two hours to debug)
			outerChunks.add(ChunkPos.asLong((int) (posX + vector.xCoord) >> 4, (int) (posZ + vector.zCoord) >> 4));
		}
		for (int distance = 0; distance <= outerRange; distance += 8) for (int angle = 0; angle <= adjustedMaxAngle; angle++) {
			Vec3 vector = Vec3.createVectorHelper(distance, 0, 0);
			vector.rotateAroundY((float) (angle * Math.PI / 180.0 / (adjustedMaxAngle / 360.0)));
			long chunkCoord = ChunkPos.asLong((int) (posX + vector.xCoord) >> 4, (int) (posZ + vector.zCoord) >> 4);
			if (!outerChunks.contains(chunkCoord)) chunks.add(chunkCoord);
		}

		chunksToProcess.addAll(chunks);
		outerChunksToProcess.addAll(outerChunks);
		Collections.reverse(chunksToProcess); // So it starts nicely from the middle
		Collections.reverse(outerChunksToProcess);
	}

	@Override
	public void onUpdate() {

		if(!world.isRemote) {
			if(firstTick) {
				if (chunksToProcess.isEmpty() && outerChunksToProcess.isEmpty()) gatherChunks();
				firstTick = false;
			}


			if(tickDelay == 0) {
				tickDelay = tickDelayStatic;
				
				if (!chunksToProcess.isEmpty()) {
					long chunkPos = chunksToProcess.remove(chunksToProcess.size() - 1); // Just so it doesn't shift the whole list every time
					int chunkPosX = (int) (chunkPos & Integer.MAX_VALUE);
					int chunkPosZ = (int) (chunkPos >> 32 & Integer.MAX_VALUE);
					for(int x = chunkPosX << 4; x < (chunkPosX << 4) + 16; x++) {
						for(int z = chunkPosZ << 4; z < (chunkPosZ << 4) + 16; z++) {
							stomp(new MutableBlockPos(x, 0, z), Math.hypot(x - posX, z - posZ) * 100F / (float)getScale());
						}
					}
					
				} else if (!outerChunksToProcess.isEmpty()) {
					long chunkPos = outerChunksToProcess.remove(outerChunksToProcess.size() - 1);
					int chunkPosX = (int) (chunkPos & Integer.MAX_VALUE);
					int chunkPosZ = (int) (chunkPos >> 32 & Integer.MAX_VALUE);
					for(int x = chunkPosX << 4; x < (chunkPosX << 4) + 16; x++) {
						for(int z = chunkPosZ << 4; z < (chunkPosZ << 4) + 16; z++) {
							double distance = Math.hypot(x - posX, z - posZ);
							if(distance <= getScale()) {
								stomp(new MutableBlockPos(x, 0, z), distance * 100F / (float)getScale());
							}
						}
					}
					
				} else {
					setDead();
				}
			}

			tickDelay--;


			if(this.isDead) {
				unloadAllChunks();
				this.done = true;
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

	private void letFall(World world, MutableBlockPos pos, int lastGapHeight, int contactHeight){
		int fallChance = RadiationConfig.blocksFallCh;
		if(fallChance < 1)
			return;
		if(fallChance < 100){
			int chance = world.rand.nextInt(100);
			if(chance < fallChance)
				return;
		}
		
		int bottomHeight = lastGapHeight;
		MutableBlockPos gapPos = new MutableBlockPos(pos.getX(), 0, pos.getZ());
		
		for(int i = lastGapHeight; i <= contactHeight; i++) {
			pos.setY(i);
			Block b = world.getBlockState(pos).getBlock();
			if(!b.isReplaceable(world, pos)){

				float hardness = b.getExplosionResistance(null);
				if(hardness > 0 && hardness < 10 && i!=bottomHeight){
					gapPos.setY(bottomHeight);
					world.setBlockState(gapPos, world.getBlockState(pos));
					world.setBlockToAir(pos);
				}
				bottomHeight++;
			}	
		}
	}

	private void stomp(MutableBlockPos pos, double dist) {
		int stoneDepth = 0;
		int maxStoneDepth = 6;

		boolean lastReachedStone = false;
		boolean reachedStone = false;
		int contactHeight = 420;
		int lastGapHeight = 420;
		boolean gapFound = false;
		for(int y = 255; y >= 0; y--) {
			pos.setY(y);
			IBlockState b = world.getBlockState(pos);
			Block bblock = b.getBlock();
			Material bmaterial = b.getMaterial();
			lastReachedStone = reachedStone;

			if(bblock.isCollidable() && contactHeight == 420)
				contactHeight = Math.min(y+1, 255);
			
			if(reachedStone && bmaterial != Material.AIR){
				stoneDepth++;
			}
			else{
				reachedStone = b.getMaterial() == Material.ROCK;
			}
			if(reachedStone && stoneDepth > maxStoneDepth){
				break;
			}
			
			if(bmaterial == Material.AIR || bmaterial.isLiquid()){
				if(y < contactHeight){
					gapFound = true;
					lastGapHeight = y;
				}
			}
		}
		if(gapFound)
			letFall(world, pos, lastGapHeight, contactHeight);
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
		this.dataManager.set(SCALE, Integer.valueOf(i+16));
	}

	public int getScale() {

		int scale = this.dataManager.get(SCALE);

		return scale == 0 ? 1 : scale;
	}
}
