package com.hbm.entity.effect;

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
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
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

public class EntityFalloutRain extends Entity implements IConstantRenderer, IChunkLoader {
	private static final DataParameter<Integer> SCALE = EntityDataManager.createKey(EntityFalloutRain.class, DataSerializers.VARINT);
	public int revProgress;
	public int radProgress;

	private Ticket loaderTicket;

	public EntityFalloutRain(World p_i1582_1_) {
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

	public EntityFalloutRain(World p_i1582_1_, int maxAge) {
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
			MutableBlockPos pos = new BlockPos.MutableBlockPos();
			for(int i = 0; i < BombConfig.fSpeed; i++) {

				Vec3 vec = Vec3.createVectorHelper(radProgress * 0.5, 0, 0);
				double circum = radProgress * 2 * Math.PI * 2;

				///
				if(circum == 0)
					circum = 1;
				///

				double part = 360D / circum;

				vec.rotateAroundY((float) (part * revProgress));

				int x = (int) (posX + vec.xCoord);
				int z = (int) (posZ + vec.zCoord);

				// int y = world.getHeightValue(x, z) - 1;

				// if(world.getBlock(x, y, z) == Blocks.grass)
				// world.setBlock(x, y, z, ModBlocks.waste_earth);

				double dist = radProgress * 100 / getScale() * 0.5;
				pos.setPos(x, 0, z);
				stomp(pos, dist);

				revProgress++;

				if(revProgress > circum) {
					revProgress = 0;
					radProgress++;
				}

				if(radProgress > getScale() * 2D) {

					this.setDead();
				}
			}

			if(this.isDead) {
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

	private void stomp(MutableBlockPos pos, double dist) {
		int stoneDepth = 0;
		int maxStoneDepth = 0;
		if(dist > 88)
			maxStoneDepth = 0;
		else if(dist > 48)
			maxStoneDepth = 1;
		else if(dist > 26)
			maxStoneDepth = 2;
		else if(dist > 15)
			maxStoneDepth = 3;
		else if(dist > 8)
			maxStoneDepth = 4;
		else if(dist > 5)
			maxStoneDepth = 5;
		else if(dist <= 5)
			maxStoneDepth = 6;

		boolean reachedStone = false;
		for(int y = 255; y >= 0; y--) {
			pos.setY(y);
			IBlockState b = world.getBlockState(pos);
			// int meta = world.getBlockMetadata(x, y, z);
			
			if(reachedStone){
				stoneDepth++;
			}
			else{
				reachedStone = b.getMaterial() == Material.ROCK;
			}
			if(reachedStone && stoneDepth > maxStoneDepth){
					return;
			}
			
			if(b.getMaterial() == Material.AIR)
				continue;

			if(b.getBlock().isFlammable(world, pos, EnumFacing.UP)) {
				if(rand.nextInt(5) == 0)
					world.setBlockState(pos.add(0, 1, 0), Blocks.FIRE.getDefaultState());
			}

			if(b.getBlock() == Blocks.LEAVES || b.getBlock() == Blocks.LEAVES2) {
				world.setBlockToAir(pos);
			}

			// if(b.getBlock() == Blocks.WATER) {
			// 	world.setBlockState(pos, ModBlocks.radwater_block.getDefaultState());
			// }

			else if(b.getBlock() == Blocks.STONE) {
				if(dist > 88 || stoneDepth==maxStoneDepth)
					world.setBlockState(pos, ModBlocks.sellafield_slaked.getDefaultState());
				else if(dist > 48 || stoneDepth==maxStoneDepth-1)
					world.setBlockState(pos, ModBlocks.sellafield_0.getDefaultState());
				else if(dist > 26 || stoneDepth==maxStoneDepth-2)
					world.setBlockState(pos, ModBlocks.sellafield_1.getDefaultState());
				else if(dist > 15 || stoneDepth==maxStoneDepth-3)
					world.setBlockState(pos, ModBlocks.sellafield_2.getDefaultState());
				else if(dist > 8 || stoneDepth==maxStoneDepth-4)
					world.setBlockState(pos, ModBlocks.sellafield_3.getDefaultState());
				else if(dist > 5 || stoneDepth==maxStoneDepth-5)
					world.setBlockState(pos, ModBlocks.sellafield_4.getDefaultState());
				else if(dist <= 5 || stoneDepth==maxStoneDepth-6)
					world.setBlockState(pos, ModBlocks.sellafield_core.getDefaultState());
				else
					return;

			} else if(b.getBlock() == Blocks.GRASS) {
				world.setBlockState(pos, ModBlocks.waste_earth.getDefaultState());

			} else if(b.getBlock() == Blocks.DIRT) {
				world.setBlockState(pos, ModBlocks.waste_dirt.getDefaultState());

			} else if(b.getBlock() == Blocks.SNOW_LAYER) {
				world.setBlockState(pos, ModBlocks.fallout.getDefaultState());

			} else if(b.getBlock() == Blocks.SNOW) {
				world.setBlockState(pos, ModBlocks.block_fallout.getDefaultState());
			}
			 else if(b.getBlock() instanceof BlockBush || b.getBlock() == Blocks.TALLGRASS) {
				world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState());

			} else if(b.getBlock() == Blocks.MYCELIUM) {
				world.setBlockState(pos, ModBlocks.waste_mycelium.getDefaultState());
				return;
			} else if(b.getBlock() == Blocks.SAND) {

				if(rand.nextInt(60) == 0) {
					BlockSand.EnumType meta = b.getValue(BlockSand.VARIANT);
					world.setBlockState(pos, meta == BlockSand.EnumType.SAND ? ModBlocks.waste_trinitite.getDefaultState() : ModBlocks.waste_trinitite_red.getDefaultState());
				}
				return;
			}

			else if(b.getBlock() == Blocks.CLAY) {
				world.setBlockState(pos, Blocks.HARDENED_CLAY.getDefaultState());
				return;
			}

			else if(b.getBlock() == Blocks.MOSSY_COBBLESTONE) {
				world.setBlockState(pos, Blocks.COAL_ORE.getDefaultState());
				return;
			}

			else if(b.getBlock() == Blocks.COAL_ORE) {
				int ra = rand.nextInt(150);
				if(ra < 7) {
					world.setBlockState(pos, Blocks.DIAMOND_ORE.getDefaultState());
				} else if(ra < 10) {
					world.setBlockState(pos, Blocks.EMERALD_ORE.getDefaultState());
				}
				return;
			}

			else if(b.getBlock() == Blocks.LOG || b.getBlock() == Blocks.LOG2) {
				world.setBlockState(pos, ModBlocks.waste_log.getDefaultState());
			}

			else if(b.getBlock() == Blocks.BROWN_MUSHROOM_BLOCK || b.getBlock() == Blocks.RED_MUSHROOM_BLOCK) {
				BlockHugeMushroom.EnumType meta = b.getValue(BlockHugeMushroom.VARIANT);
				if(meta == BlockHugeMushroom.EnumType.STEM) {
					world.setBlockState(pos, ModBlocks.waste_log.getDefaultState());
				} else {
					world.setBlockToAir(pos);
				}
			}

			else if(b.getMaterial() == Material.WOOD && b.getBlock() != ModBlocks.waste_log) {
				world.setBlockState(pos, ModBlocks.waste_planks.getDefaultState());
			}
			// else if(b.getBlock() == ModBlocks.sellafield_4) {
			// 	world.setBlockState(pos, ModBlocks.sellafield_core.getDefaultState());
			// }
			// else if(b.getBlock() == ModBlocks.sellafield_3) {
			// 	world.setBlockState(pos, ModBlocks.sellafield_4.getDefaultState());
			// }
			// else if(b.getBlock() == ModBlocks.sellafield_2) {
			// 	world.setBlockState(pos, ModBlocks.sellafield_3.getDefaultState());
			// }
			// else if(b.getBlock() == ModBlocks.sellafield_1) {
			// 	world.setBlockState(pos, ModBlocks.sellafield_2.getDefaultState());
			// }
			// else if(b.getBlock() == ModBlocks.sellafield_0) {
			// 	world.setBlockState(pos, ModBlocks.sellafield_1.getDefaultState());
			// }
			// else if(b.getBlock() == ModBlocks.sellafield_slaked) {
			// 	world.setBlockState(pos, ModBlocks.sellafield_0.getDefaultState());
			// }
			else if(b.getBlock() == ModBlocks.ore_uranium) {
				if (rand.nextInt(VersatileConfig.getSchrabOreChance()) == 0)
					world.setBlockState(pos, ModBlocks.ore_schrabidium.getDefaultState());
				else
					world.setBlockState(pos, ModBlocks.ore_uranium_scorched.getDefaultState());
				return;
			}

			else if(b.getBlock() == ModBlocks.ore_nether_uranium) {
				if(rand.nextInt(VersatileConfig.getSchrabOreChance()) == 0)
					world.setBlockState(pos, ModBlocks.ore_nether_schrabidium.getDefaultState());
				else
					world.setBlockState(pos, ModBlocks.ore_nether_uranium_scorched.getDefaultState());
				return;

			}

			else if(b == ModBlocks.ore_gneiss_uranium) {
				if(rand.nextInt(VersatileConfig.getSchrabOreChance()) == 0)
					world.setBlockState(pos, ModBlocks.ore_gneiss_schrabidium.getDefaultState());
				else
					world.setBlockState(pos, ModBlocks.ore_gneiss_uranium_scorched.getDefaultState());
				return;
				// this piece stops the "stomp" from reaching below ground
			}
			else if(b == ModBlocks.brick_concrete) {
				if(rand.nextInt(80) == 0)
					world.setBlockState(pos, ModBlocks.brick_concrete_broken.getDefaultState());
				return;
				// this piece stops the "stomp" from reaching below ground
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
	}

	public int getScale() {

		int scale = this.dataManager.get(SCALE);

		return scale == 0 ? 1 : scale;
	}
}
