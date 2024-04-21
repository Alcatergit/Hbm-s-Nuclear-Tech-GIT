package com.hbm.entity.effect;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.config.VersatileConfig;
import com.hbm.config.CompatibilityConfig;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.interfaces.IConstantRenderer;
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

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockSnowBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class EntityFalloutUnderGround extends Entity implements IChunkLoader {
	private static final DataParameter<Integer> SCALE = EntityDataManager.createKey(EntityFalloutUnderGround.class, DataSerializers.VARINT);
	public boolean done;
	private int maxSamples;
	private int currentSample;
	private int radius;

	private Ticket loaderTicket;

	private double s0;
	private double s1;
	private double s2;
	private double s3;
	private double s4;
	private double s5;
	private double s6;

	private double phi;

	public int falloutRainRadius1 = 0;
	public int falloutRainRadius2 = 0;
	public boolean falloutRainDoFallout = false;
	public boolean falloutRainDoFlood = false;
	public boolean falloutRainFire = false;

	public EntityFalloutUnderGround(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(4, 20);
		this.ignoreFrustumCheck = false;
		this.isImmuneToFire = true;
		this.phi = Math.PI * (3 - Math.sqrt(5));
		this.done = false;
		this.currentSample = 0;
	}

	public EntityFalloutUnderGround(World p_i1582_1_, int maxage) {
		super(p_i1582_1_);
		this.setSize(4, 20);
		this.isImmuneToFire = true;
		this.phi = (float)(Math.PI * (3 - Math.sqrt(5)));
		this.done = false;
		this.currentSample = 0;
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

	int age = 0;
	@Override
	public void onUpdate() {

		if(!world.isRemote) {
			if(!CompatibilityConfig.isWarDim(world)){
				this.done=true;
				unloadAllChunks();
				this.setDead();
				return;
			}
			age++;
			if(age == 120){
				System.out.println("NTM F "+currentSample+" "+Math.round(10000D * 100D*currentSample/(double)this.maxSamples)/10000D+"% "+currentSample+"/"+this.maxSamples);
				age = 0;
			}
			MutableBlockPos pos = new BlockPos.MutableBlockPos();
			int rayCounter = 0;
			long start = System.currentTimeMillis();

			double fy, fr, theta;
			for(int sample = currentSample; sample < this.maxSamples; sample++){
				this.currentSample = sample;
				if(rayCounter % 50 == 0 && System.currentTimeMillis()+1 > start + BombConfig.mk5){
					break;
				}
				fy = (2D * sample / (maxSamples - 1D)) - 1D;  // y goes from 1 to -1
		        fr = Math.sqrt(1D - fy * fy);  // radius at y
		        theta = phi * sample;  // golden angle increment

		        stompRadRay(pos, Math.cos(theta) * fr, fy, Math.sin(theta) * fr);
		        rayCounter++;
		    }

			if(this.currentSample >= this.maxSamples-1) {
				if(falloutRainRadius1 > 0){
					EntityFalloutRain falloutRain = new EntityFalloutRain(this.world);
					falloutRain.doFallout = falloutRainDoFallout;
					falloutRain.doFlood = falloutRainDoFlood;
					falloutRain.posX = this.posX;
					falloutRain.posY = this.posY;
					falloutRain.posZ = this.posZ;
					falloutRain.spawnFire = falloutRainFire;
					falloutRain.setScale(falloutRainRadius1, falloutRainRadius2);
					this.world.spawnEntity(falloutRain);
				}
				unloadAllChunks();
				this.setDead();
			}
		}
	}

	IBlockState b;
	Block bblock;
	private void stompRadRay(MutableBlockPos pos, double directionX, double directionY, double directionZ) {
		for(int l = 0; l < radius; l++) {
			pos.setPos(posX+directionX*l, posY+directionY*l, posZ+directionZ*l);

			if(pos.getY() < 0 || pos.getY() > 255) return;

			if(world.isAirBlock(pos))
				continue;

			b = world.getBlockState(pos);
			bblock = b.getBlock();

			if(bblock instanceof BlockStone || bblock == Blocks.COBBLESTONE) {
				double ranDist = l * (1D + world.rand.nextDouble()*0.1D);
				if(ranDist > s1)
					world.setBlockState(pos, ModBlocks.sellafield_slaked.getStateFromMeta(world.rand.nextInt(4)));
				else if(ranDist > s2)
					world.setBlockState(pos, ModBlocks.sellafield_0.getStateFromMeta(world.rand.nextInt(4)));
				else if(ranDist > s3)
					world.setBlockState(pos, ModBlocks.sellafield_1.getStateFromMeta(world.rand.nextInt(4)));
				else if(ranDist > s4)
					world.setBlockState(pos, ModBlocks.sellafield_2.getStateFromMeta(world.rand.nextInt(4)));
				else if(ranDist > s5)
					world.setBlockState(pos, ModBlocks.sellafield_3.getStateFromMeta(world.rand.nextInt(4)));
				else if(ranDist > s6)
					world.setBlockState(pos, ModBlocks.sellafield_4.getStateFromMeta(world.rand.nextInt(4)));
				else if(ranDist <= s6)
					world.setBlockState(pos, ModBlocks.sellafield_core.getStateFromMeta(world.rand.nextInt(4)));
				return;

			} else if(bblock == Blocks.BEDROCK || bblock == ModBlocks.ore_bedrock_oil || bblock == ModBlocks.ore_bedrock_block){
				if(world.isAirBlock(pos.up())) world.setBlockState(pos.up(), ModBlocks.toxic_block.getDefaultState());
				return;
			
			} else if(bblock instanceof BlockLeaves) {
				if(l > s1){
					world.setBlockState(pos, ModBlocks.waste_leaves.getDefaultState());
				}else{
					world.setBlockToAir(pos);
				}
				continue;

			} else if(bblock instanceof BlockBush) {
				if(world.getBlockState(pos.down()).getBlock() == Blocks.FARMLAND){
					placeBlockFromDist(l, ModBlocks.waste_dirt, pos.down());
					placeBlockFromDist(l, ModBlocks.waste_grass_tall, pos);
				} else if(world.getBlockState(pos.down()).getBlock() instanceof BlockGrass){
					placeBlockFromDist(l, ModBlocks.waste_earth, pos.down());
					placeBlockFromDist(l, ModBlocks.waste_grass_tall, pos);
				} else if(world.getBlockState(pos.down()).getBlock() == Blocks.MYCELIUM){
					placeBlockFromDist(l, ModBlocks.waste_mycelium, pos.down());
					world.setBlockState(pos, ModBlocks.mush.getDefaultState());
				}
				continue;

			} else if(bblock instanceof BlockGrass) {
				placeBlockFromDist(l, ModBlocks.waste_earth, pos);
				return;
			} else if(bblock instanceof BlockDirt) {
				BlockDirt.DirtType meta = b.getValue(BlockDirt.VARIANT);
				if(meta == BlockDirt.DirtType.DIRT)
					placeBlockFromDist(l, ModBlocks.waste_dirt, pos);
				else if(meta == BlockDirt.DirtType.COARSE_DIRT)
					placeBlockFromDist(l, ModBlocks.waste_gravel, pos);
				else if(meta == BlockDirt.DirtType.PODZOL)
					placeBlockFromDist(l, ModBlocks.waste_mycelium, pos);
				return;
			} else if(bblock == Blocks.FARMLAND) {
				placeBlockFromDist(l, ModBlocks.waste_dirt, pos);
				continue;
			} else if(bblock instanceof BlockSnow) {
				placeBlockFromDist(l, ModBlocks.waste_snow, pos);
				continue;

			} else if(bblock instanceof BlockSnowBlock) {
				placeBlockFromDist(l, ModBlocks.waste_snow_block, pos);
				continue;

			} else if(bblock instanceof BlockIce) {
				world.setBlockState(pos, ModBlocks.waste_ice.getDefaultState());
				continue;

			} else if(bblock == Blocks.MYCELIUM) {
				placeBlockFromDist(l, ModBlocks.waste_mycelium, pos);
				return;

			} else if(bblock instanceof BlockGravel) {
				placeBlockFromDist(l, ModBlocks.waste_gravel, pos);
				return;

			} else if(bblock == Blocks.SANDSTONE) {
				placeBlockFromDist(l, ModBlocks.waste_sandstone, pos);
				return;
			} else if(bblock == Blocks.RED_SANDSTONE) {
				placeBlockFromDist(l, ModBlocks.waste_sandstone_red, pos);
				return;
			} else if(bblock == Blocks.HARDENED_CLAY || bblock == Blocks.STAINED_HARDENED_CLAY) {
				placeBlockFromDist(l, ModBlocks.waste_terracotta, pos);
				return;

			} else if(bblock instanceof BlockSand) {
				BlockSand.EnumType meta = b.getValue(BlockSand.VARIANT);
				if(rand.nextInt(60) == 0) {
					placeBlockFromDist(l, meta == BlockSand.EnumType.SAND ? ModBlocks.waste_trinitite : ModBlocks.waste_trinitite_red, pos);
				} else {
					placeBlockFromDist(l, meta == BlockSand.EnumType.SAND ? ModBlocks.waste_sand : ModBlocks.waste_sand_red, pos);
				}
				return;

			} else if(bblock == Blocks.CLAY) {
				world.setBlockState(pos, Blocks.HARDENED_CLAY.getDefaultState());
				return;

			} else if(bblock == Blocks.MOSSY_COBBLESTONE) {
				world.setBlockState(pos, Blocks.COAL_ORE.getDefaultState());
				return;

			} else if(bblock == Blocks.COAL_ORE) {
				if(l < s6){
					int ra = rand.nextInt(150);
					if(ra < 7) {
						world.setBlockState(pos, Blocks.DIAMOND_ORE.getDefaultState());
					} else if(ra < 10) {
						world.setBlockState(pos, Blocks.EMERALD_ORE.getDefaultState());
					}
				}
				return;

			} else if(bblock == Blocks.BROWN_MUSHROOM_BLOCK || bblock == Blocks.RED_MUSHROOM_BLOCK) {
				if(l < s0){
					BlockHugeMushroom.EnumType meta = b.getValue(BlockHugeMushroom.VARIANT);
					if(meta == BlockHugeMushroom.EnumType.STEM) {
						world.setBlockState(pos, ModBlocks.mush_block_stem.getDefaultState());
					} else {
						world.setBlockState(pos, ModBlocks.mush_block.getDefaultState());
					}
				}
				return;

			} else if(bblock instanceof BlockLog) {
				if(l < s0)
					world.setBlockState(pos, ((WasteLog)ModBlocks.waste_log).getSameRotationState(b));
				return;

			} else if(b.getMaterial() == Material.WOOD && bblock != ModBlocks.waste_log && bblock != ModBlocks.waste_planks) {
				if(l < s0)
					world.setBlockState(pos, ModBlocks.waste_planks.getDefaultState());
				return;
			} else if(b.getBlock() == Blocks.VINE) {
				world.setBlockToAir(pos);
				continue;

			} else if(bblock == ModBlocks.ore_uranium) {
				if(l <= s6){
					if (rand.nextInt((int)(1+VersatileConfig.getSchrabOreChance())) == 0)
						world.setBlockState(pos, ModBlocks.ore_schrabidium.getDefaultState());
					else
						world.setBlockState(pos, ModBlocks.ore_uranium_scorched.getDefaultState());
				}
				return;

			} else if(bblock == ModBlocks.ore_nether_uranium) {
				if(l <= s5){
					if(rand.nextInt((int)(1+VersatileConfig.getSchrabOreChance())) == 0)
						world.setBlockState(pos, ModBlocks.ore_nether_schrabidium.getDefaultState());
					else
						world.setBlockState(pos, ModBlocks.ore_nether_uranium_scorched.getDefaultState());
				}
				return;

			} else if(bblock == ModBlocks.ore_gneiss_uranium) {
				if(l <= s4){
					if(rand.nextInt((int)(1+VersatileConfig.getSchrabOreChance()/2)) == 0)
						world.setBlockState(pos, ModBlocks.ore_gneiss_schrabidium.getDefaultState());
					else
						world.setBlockState(pos, ModBlocks.ore_gneiss_uranium_scorched.getDefaultState());
				}
				return;

			} else if(bblock == ModBlocks.brick_concrete) {
				if(rand.nextInt(60) == 0)
					world.setBlockState(pos, ModBlocks.brick_concrete_broken.getDefaultState());
				return;
			} else if(b.getMaterial() == Material.ROCK || b.getMaterial() == Material.IRON){
				return;
			}
		}
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

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		setScale(nbt.getInteger("scale"));
		currentSample = nbt.getInteger("currentSample");
		falloutRainRadius1 = nbt.getInteger("fR1");
		falloutRainRadius2 = nbt.getInteger("fR2");
		falloutRainDoFallout = nbt.getBoolean("fRfallout");
		falloutRainDoFlood = nbt.getBoolean("fRflood");
		falloutRainFire = nbt.getBoolean("fRfire");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("scale", getScale());
		nbt.setInteger("currentSample", currentSample);
		nbt.setInteger("fR1", falloutRainRadius1);
		nbt.setInteger("fR2", falloutRainRadius2);
		nbt.setBoolean("fRfallout", falloutRainDoFallout);
		nbt.setBoolean("fRflood", falloutRainDoFlood);
		nbt.setBoolean("fRfire", falloutRainFire);
	}

	public void setScale(int i) {
		this.dataManager.set(SCALE, Integer.valueOf(i));
		s0 = 0.84 * i;
		s1 = 0.74 * i;
		s2 = 0.64 * i;
		s3 = 0.54 * i;
		s4 = 0.44 * i;
		s5 = 0.34 * i;
		s6 = 0.24 * i;
		radius = i;
		maxSamples = (int)(Math.PI * Math.pow(i, 2));
	}

	public int getScale() {

		int scale = this.dataManager.get(SCALE);

		return scale == 0 ? 1 : scale;
	}
}
