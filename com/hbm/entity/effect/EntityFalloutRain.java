package com.hbm.entity.effect;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.saveddata.AuxSavedData;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class EntityFalloutRain extends Entity {
	private static final DataParameter<Integer> SCALE = EntityDataManager.createKey(EntityFalloutRain.class, DataSerializers.VARINT);
	public int revProgress;
	public int radProgress;

	public EntityFalloutRain(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(4, 20);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}

	public EntityFalloutRain(World p_i1582_1_, int maxAge) {
		super(p_i1582_1_);
		this.setSize(4, 20);
		this.isImmuneToFire = true;
	}

    @Override
	public void onUpdate() {

        if(!world.isRemote) {
        	MutableBlockPos pos = new BlockPos.MutableBlockPos();
        	for(int i = 0; i < MainRegistry.fSpeed; i++) {
        		
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
	        	
	        	//int y = world.getHeightValue(x, z) - 1;
	        	
	        	//if(world.getBlock(x, y, z) == Blocks.grass)
	        	//	world.setBlock(x, y, z, ModBlocks.waste_earth);
	        	
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
        		if(MainRegistry.rain > 0 && getScale() > 150) {
        			world.getWorldInfo().setRaining(true);
    				world.getWorldInfo().setThundering(true);
    				world.getWorldInfo().setRainTime(MainRegistry.rain);
    				world.getWorldInfo().setThunderTime(MainRegistry.rain);
    				AuxSavedData.setThunder(world, MainRegistry.rain);
        		}
        	}
        }
    }
    
    private void stomp(MutableBlockPos pos, double dist) {
    	
    	int depth = 0;
    	
    	for(int y = 255; y >= 0; y--) {
    		pos.setY(y);
    		IBlockState b =  world.getBlockState(pos);
    		int meta = world.getBlockMetadata(x, y, z);
    		
    		if(b.getMaterial() == Material.AIR)
    			continue;
    		
    		if(b.getBlock().isFlammable(world, pos, EnumFacing.UP)) {
    			if(rand.nextInt(5) == 0)
    				world.setBlockState(pos.add(0, 1, 0), Blocks.FIRE.getDefaultState());
    		}
    		
			if (b.getBlock() == Blocks.LEAVES || b.getBlock() == Blocks.LEAVES2) {
				world.setBlockToAir(pos);
			}
    		
			else if(b.getBlock() == Blocks.STONE) {
				
				depth++;
				
				if(dist < 5)
					world.setBlockState(pos, ModBlocks.sellafield_1.getDefaultState());
				else if(dist < 15)
					world.setBlockState(pos, ModBlocks.sellafield_0.getDefaultState());
				else if(dist < 75)
					world.setBlockState(pos, ModBlocks.sellafield_slaked.getDefaultState());
				else
					return;
				
    			if(depth > 2)
    				return;
			
			}else if(b.getBlock() == Blocks.GRASS) {
    			world.setBlockState(pos, ModBlocks.waste_earth.getDefaultState());
    			return;
    			
    		} else if(b.getBlock() == Blocks.MYCELIUM) {
    			world.setBlockState(pos, ModBlocks.waste_mycelium.getDefaultState());
    			return;
    		} else if(b.getBlock() == Blocks.SAND) {
    			
    			if(rand.nextInt(60) == 0)
    				world.setBlockState(pos, meta == 0 ? ModBlocks.waste_trinitite : ModBlocks.waste_trinitite_red);
    			return;
    		}

			else if (b.getBlock() == Blocks.CLAY) {
				world.setBlockState(pos, Blocks.HARDENED_CLAY.getDefaultState());
    			return;
			}

			else if (b.getBlock() == Blocks.MOSSY_COBBLESTONE) {
				world.setBlockState(pos, Blocks.COAL_ORE.getDefaultState());
    			return;
			}

			else if (b.getBlock() == Blocks.COAL_ORE) {
				int ra = rand.nextInt(150);
				if (ra < 7) {
					world.setBlockState(pos, Blocks.DIAMOND_ORE.getDefaultState());
				} else if (ra < 10) {
					world.setBlockState(pos, Blocks.EMERALD_ORE.getDefaultState());
				}
    			return;
			}

			else if (b.getBlock() == Blocks.LOG || b.getBlock() == Blocks.LOG2) {
				world.setBlockState(pos, ModBlocks.waste_log.getDefaultState());
			}

			else if (b.getBlock() == Blocks.BROWN_MUSHROOM_BLOCK || b.getBlock() == Blocks.RED_MUSHROOM_BLOCK) {
				if (meta == 10) {
					world.setBlockState(pos, ModBlocks.waste_log.getDefaultState());
				} else {
					world.setBlockToAir(pos);
				}
			}
			
			else if (b.getMaterial() == Material.WOOD && b.isOpaqueCube() && b.getBlock() != ModBlocks.waste_log) {
				world.setBlockState(pos, ModBlocks.waste_planks.getDefaultState());
			}

			else if (b.getBlock() == ModBlocks.ore_uranium) {
				if (rand.nextInt(90) == 0)
					world.setBlock(pos, ModBlocks.ore_schrabidium.getDefaultState());
    			return;
			}

			else if (b.getBlock() == ModBlocks.ore_nether_uranium) {
				if (rand.nextInt(90) == 0)
					world.setBlockState(pos, ModBlocks.ore_nether_schrabidium.getDefaultState());
    			return;
    			
    		//this piece stops the "stomp" from reaching below ground
			} else if(b.isNormalCube()) {

				return;
			}
    	}
    }

	@Override
	protected void entityInit() {
		this.dataManager.register(SCALE, Integer.valueOf(0));
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
