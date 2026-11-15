package com.hbm.entity.effect;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockMushHuge;
import com.hbm.blocks.generic.WasteDirt;
import com.hbm.blocks.generic.WasteLeaves;
import com.hbm.config.BombConfig;
import com.hbm.config.VersatileConfig;
import com.hbm.config.CompatibilityConfig;
import com.hbm.entity.logic.EntityChunky;
import com.hbm.blocks.generic.WasteLog;

import java.util.*;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class EntityFalloutUnderGround extends EntityChunky {
	private static final DataParameter<Integer> SCALE = EntityDataManager.createKey(EntityFalloutUnderGround.class, DataSerializers.VARINT);
	public boolean done;
	private int maxSamples;
	private int currentSample;
	private int radius;

	private double s0;
	private double s1;
	private double s2;
	private double s3;
	private double s4;
	private double s5;
	private double s6;
    private double s7;

	private double phi;

	public int falloutRainRadius1 = 0;
	public int falloutRainRadius2 = 0;
	public boolean falloutRainDoFallout = false;
	public boolean falloutRainDoFlood = false;

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
		super.entityInit();
		this.dataManager.register(SCALE, 0);
	}

	int age = 0;
	@Override
	public void onUpdate() {

		if(!world.isRemote) {
			if(!CompatibilityConfig.isWarDim(world)){
				this.done=true;
				this.setDead();
				return;
			}
			age++;
			if(age == 1200){
//				System.out.println("NTM F "+currentSample+" "+Math.round(10000D * 100D*currentSample/(double)this.maxSamples)/10000D+"% "+currentSample+"/"+this.maxSamples);
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
					falloutRain.setScale(falloutRainRadius1, falloutRainRadius2);
					this.world.spawnEntity(falloutRain);
				}

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
				world.setBlockState(pos, SELLAFIELD[tierIndex(l, true, 0, -1)].getStateFromMeta(world.rand.nextInt(4)));
				return;
			} else if(bblock == ModBlocks.sellafield_slaked || bblock == ModBlocks.sellafield_0 || bblock == ModBlocks.sellafield_1 || bblock == ModBlocks.sellafield_2 || bblock == ModBlocks.sellafield_3 || bblock == ModBlocks.sellafield_4 || bblock == ModBlocks.sellafield_core) {
				int nt = tierIndex(l, true, 0, -1);
				if(nt > Arrays.asList(SELLAFIELD).indexOf(bblock)) world.setBlockState(pos, SELLAFIELD[nt].getStateFromMeta(world.rand.nextInt(4)));
				return;
			} else if(META_TIERED.contains(bblock)) {
				int m = tierIndex(l, false, 0, -1);
				boolean podzol = bblock == ModBlocks.waste_dirt && b.getValue(WasteDirt.VARIANT) == WasteDirt.Variant.PODZOL;
				int current = podzol ? b.getValue(WasteDirt.META) : b.getBlock().getMetaFromState(b);
				if(m > current) world.setBlockState(pos, podzol ? WasteDirt.podzolState(m) : bblock.getStateFromMeta(m));
				return;

			} else if(bblock == Blocks.BEDROCK || bblock == ModBlocks.ore_bedrock_oil || bblock == ModBlocks.ore_bedrock_block){
				if(world.isAirBlock(pos.up())) world.setBlockState(pos.up(), ModBlocks.toxic_block.getDefaultState());
				return;
			
			} else if(bblock instanceof BlockLeaves bLeaf && !(bblock instanceof WasteLeaves)) {
				if(l > s1){
                    BlockPlanks.EnumType type = null;
                    try {
                        type = bLeaf.getWoodType(bLeaf.getMetaFromState(b));
                    } catch(UnsupportedOperationException ignored) {
                        //TK bag programming catch
                    }
                    if(type == null) type = BlockPlanks.EnumType.OAK;
                    world.setBlockState(pos, ModBlocks.waste_leaves.getDefaultState().withProperty(WasteLeaves.VARIANT, type));
				}else{
					world.setBlockToAir(pos);
				}
				continue;

			} else if(bblock instanceof BlockBush) {
				IBlockState d = world.getBlockState(pos.down());
				Block dblock = d.getBlock();
				boolean canStay = dblock.canSustainPlant(d, world, pos.down(), EnumFacing.UP, (IPlantable) bblock);
				if(!canStay || bblock instanceof BlockLilyPad){
					world.setBlockState(pos, Blocks.AIR.getDefaultState());
					continue;
				}
				if(dblock == Blocks.FARMLAND){
					placeBlockFromDist(l, ModBlocks.waste_dirt, pos.down());
					placeBlockFromDist(l, ModBlocks.waste_grass_tall, pos);
				} else if(dblock instanceof BlockGrass){
					placeBlockFromDist(l, ModBlocks.waste_earth, pos.down());
					placeBlockFromDist(l, ModBlocks.waste_grass_tall, pos);
				} else if(dblock instanceof BlockDirt){
					BlockDirt.DirtType meta = d.getValue(BlockDirt.VARIANT);
					if(meta == BlockDirt.DirtType.PODZOL) placeBlockFromDist(l, WasteDirt.podzolState(tierIndex(l, false, 0, -1)), pos.down());
					else placeBlockFromDist(l, ModBlocks.waste_dirt, pos.down());
					placeBlockFromDist(l, ModBlocks.waste_grass_tall, pos);
				} else if(dblock == Blocks.MYCELIUM){
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
					placeBlockFromDist(l, WasteDirt.podzolState(tierIndex(l, false, 0, -1)), pos);
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

			} else if(bblock == Blocks.COAL_ORE || matchesOre(bblock, "oreCoal")) {
				if(l < s6){
					int ra = rand.nextInt(150);
					if(ra < 7) {
						world.setBlockState(pos, Blocks.DIAMOND_ORE.getDefaultState());
					} else if(ra < 10) {
						world.setBlockState(pos, Blocks.EMERALD_ORE.getDefaultState());
					}
				}
				return;
			} else if(bblock == ModBlocks.ore_lignite || matchesOre(bblock, "oreLignite")) {
				if(l < s6){
					if(rand.nextInt(150) < 7) {
						world.setBlockState(pos, Blocks.DIAMOND_ORE.getDefaultState());
					}
				}
				return;
			} else if(bblock == ModBlocks.ore_beryllium || matchesOre(bblock, "oreBeryllium")) {
				if(l < s6){
					if(rand.nextInt(150) < 10) {
						world.setBlockState(pos, Blocks.EMERALD_ORE.getDefaultState());
					}
				}
				return;

			} else if(bblock == Blocks.BROWN_MUSHROOM_BLOCK || bblock == Blocks.RED_MUSHROOM_BLOCK) {
				if(l < s0){
					BlockHugeMushroom.EnumType meta = b.getValue(BlockHugeMushroom.VARIANT);
					world.setBlockState(pos, BlockMushHuge.getState(bblock == Blocks.RED_MUSHROOM_BLOCK, meta == BlockHugeMushroom.EnumType.STEM));
				}
				return;

			} else if(bblock instanceof BlockLog) {
				if(l < s0)
					world.setBlockState(pos, ((WasteLog)ModBlocks.waste_log).getSameRotationState(b));
				return;

			} else if(bblock instanceof BlockSponge) {
				if (b.getValue(BlockSponge.WET)) {
					world.setBlockState(pos, Blocks.SPONGE.getDefaultState(), 2);
				}
				return;

			} else if(b.getMaterial() == Material.WOOD && bblock != ModBlocks.waste_log && bblock != ModBlocks.waste_planks) {
				if(l < s0) {
					world.removeTileEntity(pos);
					world.setBlockState(pos, ModBlocks.waste_planks.getDefaultState());
				}
				return;
			} else if(b.getBlock() == Blocks.CACTUS) {
				world.setBlockState(pos, Blocks.AIR.getDefaultState());
				continue;

			} else if(b.getBlock() == Blocks.REEDS) {
				world.setBlockState(pos, Blocks.AIR.getDefaultState());
				continue;

			} else if(b.getBlock() == Blocks.VINE) {
				world.setBlockToAir(pos);
				continue;

			} else if(bblock == ModBlocks.ore_uranium || matchesOre(bblock, "oreUranium")) {
				if(l <= s6){
					if (rand.nextInt((int)(1+VersatileConfig.getSchrabOreChance())) == 0 || l < s7)
						world.setBlockState(pos, ModBlocks.ore_schrabidium.getDefaultState());
					else
						world.setBlockState(pos, ModBlocks.ore_uranium_scorched.getDefaultState());
				}
				return;

		} else if(bblock == ModBlocks.ore_nether_uranium || matchesOre(bblock, "oreNetherUranium")) {
				if(l <= s5){
					if(rand.nextInt((int)(1+VersatileConfig.getSchrabOreChance())) == 0)
						world.setBlockState(pos, ModBlocks.ore_nether_schrabidium.getDefaultState());
					else
						world.setBlockState(pos, ModBlocks.ore_nether_uranium_scorched.getDefaultState());
				}
				return;

		} else if(bblock == ModBlocks.ore_gneiss_uranium || matchesOre(bblock, "oreNetherUranium")) {
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

	private static final Block[] SELLAFIELD = {
		ModBlocks.sellafield_slaked, ModBlocks.sellafield_0, ModBlocks.sellafield_1,
		ModBlocks.sellafield_2, ModBlocks.sellafield_3, ModBlocks.sellafield_4, ModBlocks.sellafield_core
	};

	private static final Set<Block> META_TIERED = new HashSet<>(Arrays.asList(
			ModBlocks.fallout, ModBlocks.waste_earth, ModBlocks.waste_dirt,
			ModBlocks.waste_gravel, ModBlocks.waste_snow, ModBlocks.waste_snow_block,
			ModBlocks.waste_mycelium, ModBlocks.waste_sand, ModBlocks.waste_sand_red,
			ModBlocks.waste_trinitite, ModBlocks.waste_trinitite_red,
			ModBlocks.waste_sandstone, ModBlocks.waste_sandstone_red,
			ModBlocks.waste_terracotta, ModBlocks.waste_grass_tall));

		/** Maps a distance to a 0..6 tier index. sellafield=true uses the 0.1 jitter (meta uses 0.2); stoneDepth=0 with maxStoneDepth=-1 disables the depth term. */
	private int tierIndex(double dist, boolean sellafield, int stoneDepth, int maxStoneDepth){
		double spread = sellafield ? 0.1D : 0.2D;
		double ranDist = dist * (1D + world.rand.nextDouble() * spread);
		if(ranDist > s1 || stoneDepth == maxStoneDepth) return 0;
		if(ranDist > s2 || stoneDepth == maxStoneDepth - 1) return 1;
		if(ranDist > s3 || stoneDepth == maxStoneDepth - 2) return 2;
		if(ranDist > s4 || stoneDepth == maxStoneDepth - 3) return 3;
		if(ranDist > s5 || stoneDepth == maxStoneDepth - 4) return 4;
		if(ranDist > s6 || stoneDepth == maxStoneDepth - 5) return 5;
		return 6;
	}

	public void placeBlockFromDist(double dist, Block b, BlockPos pos){
		world.setBlockState(pos, b.getStateFromMeta(tierIndex(dist, false, 0, -1)));
	}

	public void placeBlockFromDist(double dist, IBlockState state, BlockPos pos){
		world.setBlockState(pos, state);
	}

	private static boolean matchesOre(Block block, String oreDictName) {
		return OreDictionary.containsMatch(false, OreDictionary.getOres(oreDictName), new ItemStack(block, 1, OreDictionary.WILDCARD_VALUE));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		setScale(nbt.getInteger("scale"));
		currentSample = nbt.getInteger("currentSample");
		falloutRainRadius1 = nbt.getInteger("fR1");
		falloutRainRadius2 = nbt.getInteger("fR2");
		falloutRainDoFallout = nbt.getBoolean("fRfallout");
		falloutRainDoFlood = nbt.getBoolean("fRflood");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("scale", getScale());
		nbt.setInteger("currentSample", currentSample);
		nbt.setInteger("fR1", falloutRainRadius1);
		nbt.setInteger("fR2", falloutRainRadius2);
		nbt.setBoolean("fRfallout", falloutRainDoFallout);
		nbt.setBoolean("fRflood", falloutRainDoFlood);
	}

	public void setScale(int i) {
		this.dataManager.set(SCALE, i);
		s0 = 0.84 * i;
		s1 = 0.74 * i;
		s2 = 0.64 * i;
		s3 = 0.54 * i;
		s4 = 0.44 * i;
		s5 = 0.34 * i;
		s6 = 0.24 * i;
        s7 = 0.05 * i;
        radius = i;
		maxSamples = (int)(Math.PI * Math.pow(i, 2));
	}

	public int getScale() {

		int scale = this.dataManager.get(SCALE);

		return scale == 0 ? 1 : scale;
	}
}