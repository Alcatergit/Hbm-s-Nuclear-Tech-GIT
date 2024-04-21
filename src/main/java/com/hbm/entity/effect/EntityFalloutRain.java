package com.hbm.entity.effect;

import java.util.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.config.VersatileConfig;
import com.hbm.config.CompatibilityConfig;
import com.hbm.interfaces.IConstantRenderer;
import com.hbm.entity.effect.EntityFalloutUnderGround;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.saveddata.AuxSavedData;

//Chunkloading stuff
import java.util.ArrayList;
import java.util.List;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.main.MainRegistry;
import com.hbm.blocks.generic.WasteLog;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import net.minecraft.util.math.ChunkPos;


import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockSnowBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLeaves;
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
import net.minecraft.client.Minecraft;

public class EntityFalloutRain extends Entity implements IConstantRenderer, IChunkLoader {
	private static final DataParameter<Integer> SCALE = EntityDataManager.createKey(EntityFalloutRain.class, DataSerializers.VARINT);
	public boolean done = false;
	public boolean doFallout = false;
	public boolean doFlood = false;
	public boolean doDrop = false;
	public int waterLevel = 0;
	public boolean spawnFire = false;

	private Ticket loaderTicket;

	private double s0;
	private double s1;
	private double s2;
	private double s3;
	private double s4;
	private double s5;
	private double s6;
	private int fallingRadius;

	private boolean firstTick = true;
	private final List<Long> chunksToProcess = new ArrayList<>();
	private final List<Long> outerChunksToProcess = new ArrayList<>();
	private int falloutTickNumber = 0;

	public int falloutBallRadius = 0;

	public EntityFalloutRain(World world) {
		super(world);
		this.setSize(4, 20);
		this.ignoreFrustumCheck = false;
		this.isImmuneToFire = true;

		this.waterLevel = getInt(CompatibilityConfig.fillCraterWithWater.get(world.provider.getDimension()));
		if(this.waterLevel == 0){
			this.waterLevel = world.getSeaLevel();
		} else if(this.waterLevel < 0 && this.waterLevel > -world.getSeaLevel()){
			this.waterLevel = world.getSeaLevel() - this.waterLevel;
		}
		this.spawnFire = BombConfig.spawnFire;
	}

	public EntityFalloutRain(World p_i1582_1_, int maxage) {
		super(p_i1582_1_);
		this.setSize(4, 20);
		this.isImmuneToFire = true;
	}

	private static int getInt(Object e){
		if(e == null)
			return 0;
		return (int)e;
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

	private void gatherChunks() {
		Set<Long> chunks = new LinkedHashSet<>(); // LinkedHashSet preserves insertion order
		Set<Long> outerChunks = new LinkedHashSet<>();
		int outerRange = doFallout ? getScale() : fallingRadius;
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

	private void unloadAllChunks() {
		if(loaderTicket != null){
			for(ChunkPos chunk : loadedChunks) {
		        ForgeChunkManager.unforceChunk(loaderTicket, chunk);
		    }
		}
	}

	public void stompAround(){
		if (!chunksToProcess.isEmpty()) {
			long chunkPos = chunksToProcess.remove(chunksToProcess.size() - 1); // Just so it doesn't shift the whole list every time
			int chunkPosX = (int) (chunkPos & Integer.MAX_VALUE);
			int chunkPosZ = (int) (chunkPos >> 32 & Integer.MAX_VALUE);
			for(int x = chunkPosX << 4; x < (chunkPosX << 4) + 16; x++) {
				for(int z = chunkPosZ << 4; z < (chunkPosZ << 4) + 16; z++) {
					stomp(new MutableBlockPos(x, 0, z), Math.hypot(x - posX, z - posZ));
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
						stomp(new MutableBlockPos(x, 0, z), distance);
					}
				}
			}
			
		} else {
			setDead();
		}
	}

	@Override
	public void onUpdate() {

		if(!world.isRemote) {
			if(!CompatibilityConfig.isWarDim(world)){
				this.setDead();
			} else if(firstTick) {
				if(chunksToProcess.isEmpty() && outerChunksToProcess.isEmpty()) gatherChunks();
				firstTick = false;
			}
			if(falloutTickNumber >= BombConfig.fChunkSpeed){
				if(!this.isDead) {
					long start = System.currentTimeMillis();
					while(!this.isDead && System.currentTimeMillis() < start + BombConfig.falloutMS){
						stompAround();
					}
				}
				falloutTickNumber = 0;
			}
			falloutTickNumber++;

			if(this.isDead) {
				if(falloutBallRadius > 0){
					EntityFalloutUnderGround falloutBall = new EntityFalloutUnderGround(this.world);
					falloutBall.posX = this.posX;
					falloutBall.posY = this.posY;
					falloutBall.posZ = this.posZ;
					falloutBall.setScale(falloutBallRadius);
					this.world.spawnEntity(falloutBall);
				}
				unloadAllChunks();
				this.done = true;
				if(RadiationConfig.rain > 0 && doFlood) {
					if((doFallout && getScale() > 100) || (doFlood && getScale() > 50)){
						world.getWorldInfo().setRaining(true);
						world.getWorldInfo().setRainTime(RadiationConfig.rain);
					}
					if((doFallout && getScale() > 150) || (doFlood && getScale() > 100)){
						world.getWorldInfo().setThundering(true);
						world.getWorldInfo().setThunderTime(RadiationConfig.rain);
						AuxSavedData.setThunder(world, RadiationConfig.rain);
					}
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
				if(hardness >= 0 && hardness < 50 && i != bottomHeight){
					gapPos.setY(bottomHeight);
					world.setBlockState(gapPos, world.getBlockState(pos));
					world.setBlockToAir(pos);
				}
				bottomHeight++;
			}	
		}
	}

	private int[] doFallout(MutableBlockPos pos, double dist){
		int stoneDepth = 0;
		int maxStoneDepth = 0;

		if(dist > s1)
			maxStoneDepth = 0;
		else if(dist > s2)
			maxStoneDepth = 1;
		else if(dist > s3)
			maxStoneDepth = 2;
		else if(dist > s4)
			maxStoneDepth = 3;
		else if(dist > s5)
			maxStoneDepth = 4;
		else if(dist > s6)
			maxStoneDepth = 5;
		else if(dist <= s6)
			maxStoneDepth = 6;

		boolean lastReachedStone = false;
		boolean reachedStone = false;
		int contactHeight = 420;
		int lastGapHeight = 420;
		boolean gapFound = false;

		IBlockState b;
		Block bblock;
		Material bmaterial;
		for(int y = 255; y >= 0; y--) {
			pos.setY(y);
			b = world.getBlockState(pos);
			bblock = b.getBlock();
			bmaterial = b.getMaterial();
			lastReachedStone = reachedStone;

			if(bblock != Blocks.AIR && contactHeight == 420)
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
				continue;
			}

			if(bblock == Blocks.BEDROCK || bblock == ModBlocks.ore_bedrock_oil || bblock == ModBlocks.ore_bedrock_block){
				if(world.isAirBlock(pos.up())) world.setBlockState(pos.up(), ModBlocks.toxic_block.getDefaultState());
				break;
			}

			if(y == contactHeight-1 && bblock != ModBlocks.fallout && Math.abs(rand.nextGaussian() * (dist * dist) / (s0 * s0)) < 0.05 && rand.nextDouble() < 0.05 && ModBlocks.fallout.canPlaceBlockAt(world, pos.up())) {
				placeBlockFromDist(dist, ModBlocks.fallout, pos.up());
			}

			if(spawnFire && dist < s2 && bblock.isFlammable(world, pos, EnumFacing.UP) && world.isAirBlock(pos.up())) {
				world.setBlockState(pos.up(), Blocks.FIRE.getDefaultState());
			}

			if(bblock == ModBlocks.waste_leaves){
				if(!(dist > s1 || (dist > fallingRadius && (world.rand.nextFloat() < (-5F*(fallingRadius/dist)+5F))))){
					world.setBlockToAir(pos);
				}
				continue;
			}

			if(bblock instanceof BlockLeaves) {
				if(dist > s1 || (dist > fallingRadius && (world.rand.nextFloat() < (-5F*(fallingRadius/dist)+5F)))){
					world.setBlockState(pos, ModBlocks.waste_leaves.getDefaultState());
				} else {
					world.setBlockToAir(pos);
				}
				continue;
			}

			if(bblock == Blocks.BROWN_MUSHROOM || bblock == Blocks.RED_MUSHROOM){
				if(dist < s0)
					world.setBlockState(pos, ModBlocks.mush.getDefaultState());
				continue;
			}

			// if(b.getBlock() == Blocks.WATER) {
			// 	world.setBlockState(pos, ModBlocks.radwater_block.getDefaultState());
			// }

			if(bblock instanceof BlockOre && reachedStone && !lastReachedStone && dist < s1){
				world.setBlockState(pos, ModBlocks.toxic_block.getDefaultState());
				continue;
			}

			else if(bblock instanceof BlockStone || bblock == Blocks.COBBLESTONE) {
				double ranDist = dist * (1D + world.rand.nextDouble()*0.1D);
				if(ranDist > s1 || stoneDepth==maxStoneDepth)
					world.setBlockState(pos, ModBlocks.sellafield_slaked.getStateFromMeta(world.rand.nextInt(4)));
				else if(ranDist > s2 || stoneDepth==maxStoneDepth-1)
					world.setBlockState(pos, ModBlocks.sellafield_0.getStateFromMeta(world.rand.nextInt(4)));
				else if(ranDist > s3 || stoneDepth==maxStoneDepth-2)
					world.setBlockState(pos, ModBlocks.sellafield_1.getStateFromMeta(world.rand.nextInt(4)));
				else if(ranDist > s4 || stoneDepth==maxStoneDepth-3)
					world.setBlockState(pos, ModBlocks.sellafield_2.getStateFromMeta(world.rand.nextInt(4)));
				else if(ranDist > s5 || stoneDepth==maxStoneDepth-4)
					world.setBlockState(pos, ModBlocks.sellafield_3.getStateFromMeta(world.rand.nextInt(4)));
				else if(ranDist > s6 || stoneDepth==maxStoneDepth-5)
					world.setBlockState(pos, ModBlocks.sellafield_4.getStateFromMeta(world.rand.nextInt(4)));
				else if(ranDist <= s6 || stoneDepth==maxStoneDepth-6)
					world.setBlockState(pos, ModBlocks.sellafield_core.getStateFromMeta(world.rand.nextInt(4)));
				else
					break;
				continue;

			} else if(bblock instanceof BlockGrass) {
				placeBlockFromDist(dist, ModBlocks.waste_earth, pos);
				continue;

			} else if(bblock instanceof BlockGravel) {
				placeBlockFromDist(dist, ModBlocks.waste_gravel, pos);
				continue;

			} else if(bblock instanceof BlockDirt) {
				BlockDirt.DirtType meta = b.getValue(BlockDirt.VARIANT);
				if(meta == BlockDirt.DirtType.DIRT)
					placeBlockFromDist(dist, ModBlocks.waste_dirt, pos);
				else if(meta == BlockDirt.DirtType.COARSE_DIRT)
					placeBlockFromDist(dist, ModBlocks.waste_gravel, pos);
				else if(meta == BlockDirt.DirtType.PODZOL)
					placeBlockFromDist(dist, ModBlocks.waste_mycelium, pos);
				continue;
			} else if(bblock == Blocks.FARMLAND) {
				placeBlockFromDist(dist, ModBlocks.waste_dirt, pos);
				continue;
			} else if(bblock instanceof BlockSnow) {
				placeBlockFromDist(dist, ModBlocks.waste_snow, pos);
				continue;

			} else if(bblock instanceof BlockSnowBlock) {
				placeBlockFromDist(dist, ModBlocks.waste_snow_block, pos);
				continue;

			} else if(bblock instanceof BlockIce) {
				world.setBlockState(pos, ModBlocks.waste_ice.getDefaultState());
				continue;

			} else if(bblock instanceof BlockBush) {
				if(world.getBlockState(pos.down()).getBlock() == Blocks.FARMLAND){
					placeBlockFromDist(dist, ModBlocks.waste_dirt, pos.down());
					placeBlockFromDist(dist, ModBlocks.waste_grass_tall, pos);
				} else if(world.getBlockState(pos.down()).getBlock() instanceof BlockGrass){
					placeBlockFromDist(dist, ModBlocks.waste_earth, pos.down());
					placeBlockFromDist(dist, ModBlocks.waste_grass_tall, pos);
				} else if(world.getBlockState(pos.down()).getBlock() == Blocks.MYCELIUM){
					placeBlockFromDist(dist, ModBlocks.waste_mycelium, pos.down());
					world.setBlockState(pos, ModBlocks.mush.getDefaultState());
				}
				continue;

			} else if(bblock == Blocks.MYCELIUM) {
				placeBlockFromDist(dist, ModBlocks.waste_mycelium, pos);
				continue;

			} else if(bblock == Blocks.SANDSTONE) {
				placeBlockFromDist(dist, ModBlocks.waste_sandstone, pos);
				continue;
			} else if(bblock == Blocks.RED_SANDSTONE) {
				placeBlockFromDist(dist, ModBlocks.waste_sandstone_red, pos);
				continue;
			} else if(bblock == Blocks.HARDENED_CLAY || bblock == Blocks.STAINED_HARDENED_CLAY) {
				placeBlockFromDist(dist, ModBlocks.waste_terracotta, pos);
				continue;
			} else if(bblock instanceof BlockSand) {
				BlockSand.EnumType meta = b.getValue(BlockSand.VARIANT);
				if(rand.nextInt(60) == 0) {
					placeBlockFromDist(dist, meta == BlockSand.EnumType.SAND ? ModBlocks.waste_trinitite : ModBlocks.waste_trinitite_red, pos);
				} else {
					placeBlockFromDist(dist, meta == BlockSand.EnumType.SAND ? ModBlocks.waste_sand : ModBlocks.waste_sand_red, pos);
				}
				continue;
			}

			else if(bblock == Blocks.CLAY) {
				world.setBlockState(pos, Blocks.HARDENED_CLAY.getDefaultState());
				continue;
			}

			else if(bblock == Blocks.MOSSY_COBBLESTONE) {
				world.setBlockState(pos, Blocks.COAL_ORE.getDefaultState());
				continue;
			}

			else if(bblock == Blocks.COAL_ORE) {
				if(dist < s5){
					int ra = rand.nextInt(150);
					if(ra < 7) {
						world.setBlockState(pos, Blocks.DIAMOND_ORE.getDefaultState());
					} else if(ra < 10) {
						world.setBlockState(pos, Blocks.EMERALD_ORE.getDefaultState());
					}
				}
				continue;
			}

			else if(bblock == Blocks.BROWN_MUSHROOM_BLOCK || bblock == Blocks.RED_MUSHROOM_BLOCK) {
				if(dist < s0){
					BlockHugeMushroom.EnumType meta = b.getValue(BlockHugeMushroom.VARIANT);
					if(meta == BlockHugeMushroom.EnumType.STEM) {
						world.setBlockState(pos, ModBlocks.mush_block_stem.getDefaultState());
					} else {
						world.setBlockState(pos, ModBlocks.mush_block.getDefaultState());
					}
				}
				continue;
			}

			else if(bblock instanceof BlockLog) {
				if(dist < s0)
					world.setBlockState(pos, ((WasteLog)ModBlocks.waste_log).getSameRotationState(b));
				continue;
			}

			else if(bmaterial == Material.WOOD && bblock != ModBlocks.waste_log && bblock != ModBlocks.waste_planks) {
				if(dist < s0)
					world.setBlockState(pos, ModBlocks.waste_planks.getDefaultState());
				continue;
			}
			else if(b.getBlock() == ModBlocks.sellafield_4) {
				world.setBlockState(pos, ModBlocks.sellafield_core.getStateFromMeta(world.rand.nextInt(4)));
				continue;
			}
			else if(b.getBlock() == ModBlocks.sellafield_3) {
				world.setBlockState(pos, ModBlocks.sellafield_4.getStateFromMeta(world.rand.nextInt(4)));
				continue;
			}
			else if(b.getBlock() == ModBlocks.sellafield_2) {
				world.setBlockState(pos, ModBlocks.sellafield_3.getStateFromMeta(world.rand.nextInt(4)));
				continue;
			}
			else if(b.getBlock() == ModBlocks.sellafield_1) {
				world.setBlockState(pos, ModBlocks.sellafield_2.getStateFromMeta(world.rand.nextInt(4)));
				continue;
			}
			else if(b.getBlock() == ModBlocks.sellafield_0) {
				world.setBlockState(pos, ModBlocks.sellafield_1.getStateFromMeta(world.rand.nextInt(4)));
				continue;
			}
			else if(b.getBlock() == ModBlocks.sellafield_slaked) {
				world.setBlockState(pos, ModBlocks.sellafield_0.getStateFromMeta(world.rand.nextInt(4)));
				continue;
			}
			else if(b.getBlock() == Blocks.VINE) {
				world.setBlockToAir(pos);
				continue;
			}
			else if(bblock == ModBlocks.ore_uranium) {
				if(dist <= s6){
					if (rand.nextInt(VersatileConfig.getSchrabOreChance()) == 0)
						world.setBlockState(pos, ModBlocks.ore_schrabidium.getDefaultState());
					else
						world.setBlockState(pos, ModBlocks.ore_uranium_scorched.getDefaultState());
				}
				break;
			}

			else if(bblock == ModBlocks.ore_nether_uranium) {
				if(dist <= s5){
					if(rand.nextInt(VersatileConfig.getSchrabOreChance()) == 0)
						world.setBlockState(pos, ModBlocks.ore_nether_schrabidium.getDefaultState());
					else
						world.setBlockState(pos, ModBlocks.ore_nether_uranium_scorched.getDefaultState());
				}
				break;

			}

			else if(bblock == ModBlocks.ore_gneiss_uranium) {
				if(dist <= s4){
					if(rand.nextInt(VersatileConfig.getSchrabOreChance()) == 0)
						world.setBlockState(pos, ModBlocks.ore_gneiss_schrabidium.getDefaultState());
					else
						world.setBlockState(pos, ModBlocks.ore_gneiss_uranium_scorched.getDefaultState());
				}
				break;
				// this piece stops the "stomp" from reaching below ground
			}
			else if(bblock == ModBlocks.brick_concrete) {
				if(rand.nextInt(80) == 0)
					world.setBlockState(pos, ModBlocks.brick_concrete_broken.getDefaultState());
				break;
				// this piece stops the "stomp" from reaching below ground
			} 
			else if(bblock.getExplosionResistance(null) > 300){
				break;
			}
		}
		return new int[]{gapFound ? 1 : 0, lastGapHeight, contactHeight};
	}

	private int[] doNoFallout(MutableBlockPos pos, double dist){
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
		return new int[]{gapFound ? 1 : 0, lastGapHeight, contactHeight};
	}

	public void placeBlockFromDist(double dist, Block b, BlockPos pos){
		double ranDist = dist * (1D + world.rand.nextDouble()*0.2);
		if(ranDist > s1)
			world.setBlockState(pos, b.getStateFromMeta(0));
		else if(ranDist > s2)
			world.setBlockState(pos, b.getStateFromMeta(1));
		else if(ranDist > s3)
			world.setBlockState(pos, b.getStateFromMeta(2));
		else if(ranDist > s4)
			world.setBlockState(pos, b.getStateFromMeta(3));
		else if(ranDist > s5)
			world.setBlockState(pos, b.getStateFromMeta(4));
		else if(ranDist > s6)
			world.setBlockState(pos, b.getStateFromMeta(5));
		else if(ranDist <= s6)
			world.setBlockState(pos, b.getStateFromMeta(6));
	}

	private void flood(MutableBlockPos pos){
		if(CompatibilityConfig.doFillCraterWithWater && waterLevel > 1){
			for(int y = waterLevel-1; y > 1; y--) {
				pos.setY(y);
				if(world.isAirBlock(pos) || world.getBlockState(pos).getBlock() == Blocks.FLOWING_WATER){
					world.setBlockState(pos, Blocks.WATER.getDefaultState());
				}
			}
		}
	}

	private void drain(MutableBlockPos pos){
		for(int y = 255; y > 1; y--) {
			pos.setY(y);
			if(!world.isAirBlock(pos) && (world.getBlockState(pos).getBlock() == Blocks.WATER || world.getBlockState(pos).getBlock() == Blocks.FLOWING_WATER)){
				world.setBlockToAir(pos);
			}
		}
	}

	private void stomp(MutableBlockPos pos, double dist) {
		if(dist > s0){
			if(world.rand.nextFloat() > 0.05F+(5F*(s0/dist)-4F)){
				return;
			}
		}
		int[] gapData = null;
		if(doFallout)
			gapData = doFallout(pos, dist);
		else
			gapData = doNoFallout(pos, dist);

		if(dist < fallingRadius){
			if(doDrop && gapData != null && gapData[0] == 1)
				letFall(world, pos, gapData[1], gapData[2]);
			if(doFlood)
				flood(pos);
			else
				drain(pos);
		}
	}

	

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		setScale(nbt.getInteger("scale"), nbt.getInteger("dropRadius"));
		falloutBallRadius = nbt.getInteger("fBall");
		if(nbt.hasKey("chunks"))
			chunksToProcess.addAll(readChunksFromIntArray(nbt.getIntArray("chunks")));
		if(nbt.hasKey("outerChunks"))
			outerChunksToProcess.addAll(readChunksFromIntArray(nbt.getIntArray("outerChunks")));
		doFallout = nbt.getBoolean("doFallout");
		doFlood = nbt.getBoolean("doFlood");
	}

	private Collection<Long> readChunksFromIntArray(int[] data) {
		List<Long> coords = new ArrayList<>();
		boolean firstPart = true;
		int x = 0;
		for (int coord : data) {
			if (firstPart)
				x = coord;
			else
				coords.add(ChunkPos.asLong(x, coord));
			firstPart = !firstPart;
		}
		return coords;
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("scale", getScale());
		nbt.setInteger("fBall", falloutBallRadius);
		nbt.setInteger("dropRadius", fallingRadius);
		nbt.setBoolean("doFallout", doFallout);
		nbt.setBoolean("doFlood", doFlood);

		nbt.setIntArray("chunks", writeChunksToIntArray(chunksToProcess));
		nbt.setIntArray("outerChunks", writeChunksToIntArray(outerChunksToProcess));
	}

	private int[] writeChunksToIntArray(List<Long> coords) {
		int[] data = new int[coords.size() * 2];
		for (int i = 0; i < coords.size(); i++) {
			data[i * 2] = (int) (coords.get(i) & Integer.MAX_VALUE);
			data[i * 2 + 1] = (int) (coords.get(i) >> 32 & Integer.MAX_VALUE);
		}
		return data;
	}

	public void setScale(int i, int craterRadius) {
		this.dataManager.set(SCALE, Integer.valueOf(i));
		this.s0 = 0.8D * i;
		this.s1 = 0.65D * i;
		this.s2 = 0.5D * i;
		this.s3 = 0.4D * i;
		this.s4 = 0.3D * i;
		this.s5 = 0.2D * i;
		this.s6 = 0.1D * i;
		this.fallingRadius = craterRadius;
		this.doDrop = this.fallingRadius > 20;
	}

	public int getScale() {

		int scale = this.dataManager.get(SCALE);

		return scale == 0 ? 1 : scale;
	}
}
