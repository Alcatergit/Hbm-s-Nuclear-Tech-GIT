package com.hbm.entity.effect;

import java.lang.reflect.Field;
import java.util.*;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockMushHuge;
import com.hbm.blocks.generic.WasteLeaves;
import com.hbm.blocks.generic.WasteDirt;
import com.hbm.config.BombConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.config.VersatileConfig;
import com.hbm.config.CompatibilityConfig;
import com.hbm.entity.logic.EntityChunky;
import com.hbm.interfaces.IConstantRenderer;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.saveddata.AuxSavedData;
import com.hbm.blocks.generic.WasteLog;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.network.play.server.SPacketChunkData;

public class EntityFalloutRain extends EntityChunky implements IConstantRenderer {

	private static final DataParameter<Integer> SCALE = EntityDataManager.createKey(EntityFalloutRain.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> FADE_IN_TICKS = EntityDataManager.createKey(EntityFalloutRain.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> FADE_OUT_TICKS = EntityDataManager.createKey(EntityFalloutRain.class, DataSerializers.VARINT);
	private static final int FADE_DURATION = 100;
	public boolean done = false;
	public boolean doFallout = false;
	public boolean doFlood = false;
	public boolean doDrop = false;
	public int waterLevel = 0;

	private double s0;
	private double s1;
	private double s2;
	private double s3;
	private double s4;
	private double s5;
	private double s6;
    private double s7;
    private int fallingRadius;

	private boolean firstTick = true;
	private final List<Long> chunksToProcess = new ArrayList<>();
	private final List<Long> outerChunksToProcess = new ArrayList<>();
	private int falloutTickNumber = 0;

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
	}

	public EntityFalloutRain(World p_i1582_1_, int maxage) {
		super(p_i1582_1_);
		this.setSize(4, 20);
		this.isImmuneToFire = true;
	}

	public static int getInt(Object e){
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
		super.entityInit();
		this.dataManager.register(SCALE, 0);
		this.dataManager.register(FADE_IN_TICKS, FADE_DURATION);
		this.dataManager.register(FADE_OUT_TICKS, 0);
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
			if (this.dataManager.get(FADE_IN_TICKS) == 0 && this.dataManager.get(FADE_OUT_TICKS) == 0) {
				this.dataManager.set(FADE_OUT_TICKS, FADE_DURATION);
			}
		}
	}

	@Override
	public void onUpdate() {
		if(!world.isRemote) {
			flushSectionMasks();
			if(!CompatibilityConfig.isWarDim(world)){
				this.setDead();
				return;
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

			int fadeIn = this.dataManager.get(FADE_IN_TICKS);
			if (fadeIn > 0) {
				this.dataManager.set(FADE_IN_TICKS, fadeIn - 1);
			}

			int fadeOut = this.dataManager.get(FADE_OUT_TICKS);
			if (fadeOut > 0) {
				this.dataManager.set(FADE_OUT_TICKS, fadeOut - 1);
				if (fadeOut == 1) {
					this.setDead();
					this.done = true;
					if (RadiationConfig.rain > 0 && doFlood) {
						int scale = getScale();
						if ((doFallout && scale > 160) || scale > 200) {
							world.getWorldInfo().setThundering(true);
							world.getWorldInfo().setThunderTime(RadiationConfig.rain);
							AuxSavedData.setThunder(world, RadiationConfig.rain);
						} else if ((doFallout && scale > 80) || scale > 100) {
							world.getWorldInfo().setRaining(true);
							world.getWorldInfo().setRainTime(RadiationConfig.rain);
						}
					}
				}
			}
		}
	}

	private void letFall(World world, MutableBlockPos pos, int lastGapHeight, int contactHeight){
		int fallChance = RadiationConfig.blocksFallCh;
		if(fallChance < 1) return;
		if(fallChance < 100){
			int chance = world.rand.nextInt(100);
			if(chance < fallChance) return;
		}

		int bottomHeight = lastGapHeight;

		for (int y = lastGapHeight; y <= contactHeight; y++) {
			pos.setY(y);
			IBlockState state = world.getBlockState(pos);
			Block b = state.getBlock();

			if (b.isReplaceable(world, pos)) continue;

			float hardness = b.getExplosionResistance(null);

			if (hardness > 15) {
				bottomHeight = y + 1;
				continue;
			}

			if (hardness >= 0 && y != bottomHeight) {
				BlockPos target = new BlockPos(pos.getX(), bottomHeight, pos.getZ());
				IBlockState targetState = world.getBlockState(target);

				if (targetState.getBlock().isReplaceable(world, target)) {
					TileEntity te = world.getTileEntity(pos);
					NBTTagCompound teNBT = null;
					if (te != null) {
						teNBT = new NBTTagCompound();
						te.writeToNBT(teNBT);
						teNBT.setInteger("x", target.getX());
						teNBT.setInteger("y", target.getY());
						teNBT.setInteger("z", target.getZ());
						world.removeTileEntity(pos);
					}

					setBlockAndTrack(pos, Blocks.AIR.getDefaultState(), 3);
					setBlockAndTrack(target, state, 3);

					if (teNBT != null) {
						TileEntity newTE = world.getTileEntity(target);
						if (newTE != null) {
							newTE.readFromNBT(teNBT);
							newTE.validate();
							world.markBlockRangeForRenderUpdate(target, target);
							world.notifyBlockUpdate(target, state, state, 3);
						}
					}

					if (!(b.getCollisionBoundingBox(state, world, pos) == Block.NULL_AABB)) {
						float distance = y - bottomHeight;
						int i = MathHelper.ceil(distance - 1.0F);
						if (i > 0) {
							float fallHurtAmount = 2.0F * (hardness / 15F);
							float damage = Math.min(MathHelper.floor((float) i * fallHurtAmount), 40.0F);
							AxisAlignedBB blockBox = state.getCollisionBoundingBox(world, target);
							if (blockBox != Block.NULL_AABB) {
								List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, blockBox.offset(target));
								for (Entity entity : entities) {
									entity.attackEntityFrom(DamageSource.FALLING_BLOCK, damage);
								}
							}
						}
					}
				}
			}
			bottomHeight++;
		}
	}

    public int getMaxStoneDepth(double dist){
        if(dist > s1)
            return 0;
        else if(dist > s2)
            return 1;
        else if(dist > s3)
            return 2;
        else if(dist > s4)
            return 3;
        else if(dist > s5)
            return 4;
        else if(dist > s6)
            return 5;
        else if(dist <= s6)
            return 6;
        return 7;
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

	private int[] doFallout(MutableBlockPos pos, double dist){
		int stoneDepth = 0;
		int maxStoneDepth =getMaxStoneDepth(dist);

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
			} else {
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
				if(world.isAirBlock(pos.up())) setBlockAndTrack(pos.up(),ModBlocks.toxic_block.getDefaultState(), 3);
				break;
			}

			if(y == contactHeight-1 && bblock != ModBlocks.fallout && Math.abs(rand.nextGaussian() * (dist * dist) / (s0 * s0)) < 0.05 && rand.nextDouble() < 0.05 && ModBlocks.fallout.canPlaceBlockAt(world, pos.up())) {
				placeBlockFromDist(dist, ModBlocks.fallout, pos.up());
			}

			if(bblock == ModBlocks.waste_leaves){
				if(!(dist > s1 || (dist > fallingRadius && (world.rand.nextFloat() < (-5F*(fallingRadius/dist)+5F))))){
					setBlockAndTrack(pos, Blocks.AIR.getDefaultState(), 3);
				}
				continue;
			}

			if(bblock instanceof BlockLeaves bLeaf && !(bblock instanceof WasteLeaves)) {
				if(dist > s1 || (dist > fallingRadius && (world.rand.nextFloat() < (-5F*(fallingRadius/dist)+5F)))){
                    BlockPlanks.EnumType type = null;
                    try {
                        type = bLeaf.getWoodType(bLeaf.getMetaFromState(b));
                    } catch(UnsupportedOperationException ignored) {
                        //TK bag programming catch
                    }
                    if(type == null) type = BlockPlanks.EnumType.OAK;
                    setBlockAndTrack(pos,ModBlocks.waste_leaves.getDefaultState().withProperty(WasteLeaves.VARIANT, type), 3);
				} else {
					setBlockAndTrack(pos, Blocks.AIR.getDefaultState(), 3);
				}
				continue;
			}

			if(bblock == Blocks.BROWN_MUSHROOM || bblock == Blocks.RED_MUSHROOM){
				if(dist < s0)
					setBlockAndNotify(pos,ModBlocks.mush.getDefaultState());
				continue;
			}

			if(META_TIERED.contains(bblock)) {
				int m = tierIndex(dist, false, 0, -1);
				boolean podzol = bblock == ModBlocks.waste_dirt && b.getValue(WasteDirt.VARIANT) == WasteDirt.Variant.PODZOL;
				int current = podzol ? b.getValue(WasteDirt.META) : b.getBlock().getMetaFromState(b);
				if(m > current){
					setBlockAndNotify(pos, podzol ? WasteDirt.podzolState(m) : bblock.getStateFromMeta(m));
				}
				continue;
			}

			// if(b.getBlock() == Blocks.WATER) {
			// 	world.setBlockState(pos,ModBlocks.radwater_block.getDefaultState(), 2);
			// }

			if(bblock instanceof BlockOre && reachedStone && !lastReachedStone && dist < s1){
				setBlockAndTrack(pos,ModBlocks.toxic_block.getDefaultState(), 3);
				continue;
			}

			else if(bblock instanceof BlockStone || bblock == Blocks.COBBLESTONE) {
				setBlockAndTrack(pos, SELLAFIELD[tierIndex(dist, true, stoneDepth, maxStoneDepth)].getStateFromMeta(world.rand.nextInt(4)), 3);
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
					placeBlockFromDist(dist, WasteDirt.podzolState(tierIndex(dist, false, 0, -1)), pos);
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
				setBlockAndNotify(pos,ModBlocks.waste_ice.getDefaultState());
				continue;

			} else if(bblock instanceof BlockBush) {
				IBlockState d = world.getBlockState(pos.down());
				Block dblock = d.getBlock();
				boolean canStay = dblock.canSustainPlant(d, world, pos.down(), EnumFacing.UP, (IPlantable) bblock);
				if(!canStay || bblock instanceof BlockLilyPad){
					setBlockAndTrack(pos, Blocks.AIR.getDefaultState(), 3);
					continue;
				}
				if(dblock == Blocks.FARMLAND){
					placeBlockFromDist(dist, ModBlocks.waste_dirt, pos.down());
					placeBlockFromDist(dist, ModBlocks.waste_grass_tall, pos);
				} else if(dblock instanceof BlockGrass){
					placeBlockFromDist(dist, ModBlocks.waste_earth, pos.down());
					placeBlockFromDist(dist, ModBlocks.waste_grass_tall, pos);
				} else if(dblock instanceof BlockDirt){
					BlockDirt.DirtType meta = d.getValue(BlockDirt.VARIANT);
					if(meta == BlockDirt.DirtType.PODZOL) placeBlockFromDist(dist, WasteDirt.podzolState(tierIndex(dist, false, 0, -1)), pos.down()); else placeBlockFromDist(dist, ModBlocks.waste_dirt, pos.down());
					placeBlockFromDist(dist, ModBlocks.waste_grass_tall, pos);
				} else if(dblock == Blocks.MYCELIUM){
					placeBlockFromDist(dist, ModBlocks.waste_mycelium, pos.down());
					setBlockAndTrack(pos, ModBlocks.mush.getDefaultState(), 3);
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
				setBlockAndNotify(pos,Blocks.HARDENED_CLAY.getDefaultState());
				continue;
			}

			else if(bblock == Blocks.MOSSY_COBBLESTONE) {
				setBlockAndNotify(pos,Blocks.COAL_ORE.getDefaultState());
				continue;
			}

			else if(bblock == Blocks.COAL_ORE || matchesOre(bblock, "oreCoal")) {
				if(dist < s5){
					int ra = rand.nextInt(150);
					if(ra < 7) {
						setBlockAndNotify(pos,Blocks.DIAMOND_ORE.getDefaultState());
					} else if(ra < 10) {
						setBlockAndNotify(pos,Blocks.EMERALD_ORE.getDefaultState());
					}
				}
				continue;
			}
			else if(bblock == ModBlocks.ore_lignite || matchesOre(bblock, "oreLignite")) {
				if(dist < s5){
					if(rand.nextInt(150) < 7) {
						setBlockAndNotify(pos,Blocks.DIAMOND_ORE.getDefaultState());
					}
				}
				continue;
			}
			else if(bblock == ModBlocks.ore_beryllium || matchesOre(bblock, "oreBeryllium")) {
				if(dist < s5){
					if(rand.nextInt(150) < 10) {
						setBlockAndNotify(pos,Blocks.EMERALD_ORE.getDefaultState());
					}
				}
				continue;
			}

			else if(bblock == Blocks.BROWN_MUSHROOM_BLOCK || bblock == Blocks.RED_MUSHROOM_BLOCK) {
				if(dist < s0){
					BlockHugeMushroom.EnumType meta = b.getValue(BlockHugeMushroom.VARIANT);
					setBlockAndNotify(pos, BlockMushHuge.getState(bblock == Blocks.RED_MUSHROOM_BLOCK, meta == BlockHugeMushroom.EnumType.STEM));
				}
				continue;
			}

			else if(bblock instanceof BlockLog) {
				if(dist < s1)
					setBlockAndTrack(pos, ((WasteLog)ModBlocks.waste_log).getSameRotationState(b), 3);
				continue;
			}

			else if (bblock == Blocks.SPONGE) {
				if (b.getValue(BlockSponge.WET)) {
					setBlockAndNotify(pos, Blocks.SPONGE.getDefaultState());
				}
				continue;
			}

			else if(bmaterial == Material.WOOD && bblock != ModBlocks.waste_log && bblock != ModBlocks.waste_planks) {
				if(dist < s1) {
					world.removeTileEntity(pos);
					setBlockAndNotify(pos,ModBlocks.waste_planks.getDefaultState());
				}
				continue;
			}
			else if(b.getBlock() == ModBlocks.sellafield_slaked || b.getBlock() == ModBlocks.sellafield_0 || b.getBlock() == ModBlocks.sellafield_1 || b.getBlock() == ModBlocks.sellafield_2 || b.getBlock() == ModBlocks.sellafield_3 || b.getBlock() == ModBlocks.sellafield_4 || b.getBlock() == ModBlocks.sellafield_core) {
				int nt = tierIndex(dist, true, stoneDepth, maxStoneDepth);
				if(nt > Arrays.asList(SELLAFIELD).indexOf(bblock)){
					setBlockAndTrack(pos, SELLAFIELD[nt].getStateFromMeta(world.rand.nextInt(4)), 3);
				}
				continue;
			}
			else if(b.getBlock() == Blocks.CACTUS) {
				setBlockAndTrack(pos, Blocks.AIR.getDefaultState(), 3);
				continue;
			}
			else if(b.getBlock() == Blocks.REEDS) {
				setBlockAndTrack(pos, Blocks.AIR.getDefaultState(), 3);
				continue;
			}
			else if(b.getBlock() == Blocks.VINE) {
				setBlockAndTrack(pos, Blocks.AIR.getDefaultState(), 3);
				continue;
			}
			else if(bblock == ModBlocks.ore_uranium || matchesOre(bblock, "oreUranium")) {
				if(dist <= s5){
					if (rand.nextInt(VersatileConfig.getSchrabOreChance()) == 0 || dist < s7)
						setBlockAndNotify(pos,ModBlocks.ore_schrabidium.getDefaultState());
					else
						setBlockAndNotify(pos,ModBlocks.ore_uranium_scorched.getDefaultState());
				}
				break;
			}

			else if(bblock == ModBlocks.ore_nether_uranium || matchesOre(bblock, "oreNetherUranium")) {
				if(dist <= s5){
					if(rand.nextInt(VersatileConfig.getSchrabOreChance()) == 0)
						setBlockAndNotify(pos,ModBlocks.ore_nether_schrabidium.getDefaultState());
					else
						setBlockAndNotify(pos,ModBlocks.ore_nether_uranium_scorched.getDefaultState());
				}
				break;
			}

			else if(bblock == ModBlocks.ore_gneiss_uranium || matchesOre(bblock, "oreNetherUranium")) {
				if(dist <= s4){
					if(rand.nextInt(VersatileConfig.getSchrabOreChance()) == 0)
						setBlockAndNotify(pos,ModBlocks.ore_gneiss_schrabidium.getDefaultState());
					else
						setBlockAndNotify(pos,ModBlocks.ore_gneiss_uranium_scorched.getDefaultState());
				}
				break;
				// this piece stops the "stomp" from reaching below ground
			}
			else if(bblock == ModBlocks.brick_concrete) {
				if(rand.nextInt(80) == 0)
					setBlockAndNotify(pos,ModBlocks.brick_concrete_broken.getDefaultState());
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
		setBlockAndNotify(pos, b.getStateFromMeta(tierIndex(dist, false, 0, -1)));
	}

	public void placeBlockFromDist(double dist, IBlockState state, BlockPos pos){
		setBlockAndNotify(pos, state);
	}

	private void setBlockAndNotify(BlockPos pos, IBlockState state) {
		IBlockState oldState = world.getBlockState(pos);
		setBlockAndTrack(pos, state, 2);
		notifyNeighborsSkippingWater(pos, oldState.getBlock());
	}

	// Changed-section tracking for client light sync.
	private static final class LightSnapshot {
		final byte[] sky;
		final byte[] block;
		final boolean wasNull;

		LightSnapshot(boolean wasNull, byte[] sky, byte[] block) {
			this.wasNull = wasNull;
			this.sky = sky;
			this.block = block;
		}
	}

	private final Map<Long, Map<Integer, LightSnapshot>> lightSnapshots = new HashMap<>();

	private void snapshotForLight(BlockPos pos, IBlockState oldState, IBlockState newState) {
		if (oldState.getLightValue() == newState.getLightValue()
				&& oldState.getLightOpacity() == newState.getLightOpacity()) return;
		if (!(world instanceof WorldServer)) return;
		WorldServer ws = (WorldServer) world;
		int cx = pos.getX() >> 4;
		int cz = pos.getZ() >> 4;
		int sy = pos.getY() >> 4;
		snapshotChunk(ws, cx, cz, sy);
		snapshotChunk(ws, cx - 1, cz, sy);
		snapshotChunk(ws, cx + 1, cz, sy);
		snapshotChunk(ws, cx, cz - 1, sy);
		snapshotChunk(ws, cx, cz + 1, sy);
		snapshotChunk(ws, cx - 1, cz - 1, sy);
		snapshotChunk(ws, cx - 1, cz + 1, sy);
		snapshotChunk(ws, cx + 1, cz - 1, sy);
		snapshotChunk(ws, cx + 1, cz + 1, sy);
	}

	private void snapshotChunk(WorldServer ws, int cx, int cz, int sy) {
		Chunk chunk = ws.getChunkProvider().loadedChunks.get(ChunkPos.asLong(cx, cz));
		if (chunk == null) return;
		long cp = ChunkPos.asLong(cx, cz);
		Map<Integer, LightSnapshot> map = lightSnapshots.computeIfAbsent(cp, k -> new HashMap<>());
		for (int s = Math.max(0, sy - 1); s <= Math.min(15, sy + 1); s++) {
			if (map.containsKey(s)) continue;
			ExtendedBlockStorage ebs = chunk.getBlockStorageArray()[s];
			if (ebs == null || ebs == Chunk.NULL_BLOCK_STORAGE) {
				map.put(s, new LightSnapshot(true, null, null));
			} else {
				map.put(s, new LightSnapshot(false,
						ebs.getSkyLight() == null ? null : ebs.getSkyLight().getData().clone(),
						ebs.getBlockLight() == null ? null : ebs.getBlockLight().getData().clone()));
			}
		}
	}

	private static void mergeMask(Map<Long, Integer> map, long cp, int mask) {
		map.merge(cp, mask, (a, b) -> a | b);
	}

	private void flushSectionMasks() {
		if (lightSnapshots.isEmpty()) return;
		if (!(world instanceof WorldServer)) { lightSnapshots.clear(); return; }
		WorldServer ws = (WorldServer) world;
		Map<Long, Integer> diff = new HashMap<>();
		for (Map.Entry<Long, Map<Integer, LightSnapshot>> ce : lightSnapshots.entrySet()) {
			long cp = ce.getKey().longValue();
			Chunk chunk = ws.getChunkProvider().loadedChunks.get(cp);
			if (chunk == null) continue;
			for (Map.Entry<Integer, LightSnapshot> se : ce.getValue().entrySet()) {
				int s = se.getKey().intValue();
				LightSnapshot snap = se.getValue();
				ExtendedBlockStorage ebs = chunk.getBlockStorageArray()[s];
				boolean changed;
				if (snap.wasNull) {
					changed = ebs != Chunk.NULL_BLOCK_STORAGE;
				} else if (ebs == null || ebs == Chunk.NULL_BLOCK_STORAGE) {
					changed = true;
				} else {
					byte[] sky = ebs.getSkyLight() == null ? null : ebs.getSkyLight().getData();
					byte[] block = ebs.getBlockLight() == null ? null : ebs.getBlockLight().getData();
					changed = !Arrays.equals(snap.sky, sky) || !Arrays.equals(snap.block, block);
				}
				if (changed) mergeMask(diff, cp, 1 << s);
			}
		}
		lightSnapshots.clear();
		for (Map.Entry<Long, Integer> e : diff.entrySet()) sendOneChunk(e.getKey().longValue(), e.getValue().intValue());
	}

	private void sendOneChunk(long cp, int mask) {
		if (!(world instanceof WorldServer)) return;
		WorldServer ws = (WorldServer) world;
		Chunk chunk = ws.getChunkProvider().loadedChunks.get(cp);
		if (chunk == null) return;
		PlayerChunkMapEntry entry = ws.getPlayerChunkMap().getEntry(chunk.x, chunk.z);
		if (entry == null) return;
		ExtendedBlockStorage[] storages = chunk.getBlockStorageArray();
		ExtendedBlockStorage[] fabricated = null;
		for (int s = 0; s < 16; s++) {
			if ((mask & (1 << s)) == 0) continue;
			if (storages[s] != Chunk.NULL_BLOCK_STORAGE) continue;
			ExtendedBlockStorage ebs = new ExtendedBlockStorage(s << 4, ws.provider.hasSkyLight());
			if (ws.provider.hasSkyLight()) {
				int base = s << 4;
				int maxHeight = 0;
				for (int x = 0; x < 16; x++) {
					for (int z = 0; z < 16; z++) {
						maxHeight = Math.max(maxHeight, chunk.getHeightValue(x, z));
					}
				}
				if (base >= maxHeight) {
					java.util.Arrays.fill(ebs.getSkyLight().getData(), (byte) 0xFF);
				} else {
					for (int x = 0; x < 16; x++) {
						for (int z = 0; z < 16; z++) {
							int height = chunk.getHeightValue(x, z);
							for (int y = 0; y < 16; y++) {
								if (base + y >= height) ebs.setSkyLight(x, y, z, 15);
							}
						}
					}
				}
			}
			storages[s] = ebs;
			if (fabricated == null) fabricated = new ExtendedBlockStorage[16];
			fabricated[s] = ebs;
		}
		try {
			entry.sendPacket(new SPacketChunkData(chunk, mask));
		} finally {
			if (fabricated != null) {
				for (int s = 0; s < 16; s++) if (fabricated[s] != null) storages[s] = Chunk.NULL_BLOCK_STORAGE;
			}
		}
	}

	private void setBlockAndTrack(BlockPos pos, IBlockState state, int flags) {
		IBlockState oldState = world.getBlockState(pos);
		snapshotForLight(pos, oldState, state);
		world.setBlockState(pos, state, flags);
	}

	private void notifyNeighborsSkippingWater(BlockPos pos, Block oldBlock) {
		for(EnumFacing facing : EnumFacing.values()) {
			BlockPos np = pos.offset(facing);
			if(!world.isBlockLoaded(np)) continue;
			Block b = world.getBlockState(np).getBlock();
			if(b != Blocks.WATER && b != Blocks.FLOWING_WATER) {
				world.neighborChanged(np, oldBlock, pos);
			}
		}
	}

	public void placeBlockFromDist(double dist, Block b, BlockPos pos, int flags){
		setBlockAndTrack(pos, b.getStateFromMeta(tierIndex(dist, false, 0, -1)), flags);
	}

	private void flood(MutableBlockPos pos){
		if(CompatibilityConfig.doFillCraterWithWater && waterLevel > 1){
			for(int y = waterLevel-1; y > 1; y--) {
				pos.setY(y);
                Block b = world.getBlockState(pos).getBlock();
                if(world.isAirBlock(pos) || b == Blocks.FLOWING_WATER){
                    setBlockAndNotify(pos,Blocks.WATER.getDefaultState());
                } else if(b.getExplosionResistance(null) > 600_000){
                    return;
                }
			}
		}
	}

	private void drain(MutableBlockPos pos){
		for(int y = 255; y > 1; y--) {
			pos.setY(y);
			if(!world.isAirBlock(pos) && (world.getBlockState(pos).getBlock() == Blocks.WATER || world.getBlockState(pos).getBlock() == Blocks.FLOWING_WATER)){
				setBlockAndNotify(pos, Blocks.AIR.getDefaultState());
			}
		}
	}

	private void stomp(MutableBlockPos pos, double dist) {
		if(dist > s0){
			if(world.rand.nextFloat() > 0.05F+(5F*(s0/dist)-4F)){
				return;
			}
		}
		int[] gapData;
		if(doFallout)
			gapData = doFallout(pos, dist);
		else
			gapData = doNoFallout(pos, dist);

		if(dist < fallingRadius){
			if(doDrop && gapData[0] == 1)
				letFall(world, pos, gapData[1], gapData[2]);
			if(doFlood)
				flood(pos);
			else
				drain(pos);
		}
	}

	private static boolean matchesOre(Block block, String oreDictName) {
		return OreDictionary.containsMatch(false, OreDictionary.getOres(oreDictName), new ItemStack(block, 1, OreDictionary.WILDCARD_VALUE));
	}

	public float getCurrentAlpha() {
		int fadeIn = this.dataManager.get(FADE_IN_TICKS);
		int fadeOut = this.dataManager.get(FADE_OUT_TICKS);
		float alpha = 1.0F;
		if (fadeIn > 0) {
			alpha = (float)(FADE_DURATION - fadeIn) / FADE_DURATION;
		}
		if (fadeOut > 0) {
			alpha = (float)(fadeOut - 1) / FADE_DURATION;
		}
		return MathHelper.clamp(alpha, 0.0F, 1.0F);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		setScale(nbt.getInteger("scale"), nbt.getInteger("dropRadius"));
		if(nbt.hasKey("chunks"))
			chunksToProcess.addAll(readChunksFromIntArray(nbt.getIntArray("chunks")));
		if(nbt.hasKey("outerChunks"))
			outerChunksToProcess.addAll(readChunksFromIntArray(nbt.getIntArray("outerChunks")));
		doFallout = nbt.getBoolean("doFallout");
		doFlood = nbt.getBoolean("doFlood");
		if (nbt.hasKey("fadeIn")) this.dataManager.set(FADE_IN_TICKS, nbt.getInteger("fadeIn"));
		if (nbt.hasKey("fadeOut")) this.dataManager.set(FADE_OUT_TICKS, nbt.getInteger("fadeOut"));
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
		nbt.setInteger("dropRadius", fallingRadius);
		nbt.setBoolean("doFallout", doFallout);
		nbt.setBoolean("doFlood", doFlood);

		nbt.setIntArray("chunks", writeChunksToIntArray(chunksToProcess));
		nbt.setIntArray("outerChunks", writeChunksToIntArray(outerChunksToProcess));
		nbt.setInteger("fadeIn", this.dataManager.get(FADE_IN_TICKS));
		nbt.setInteger("fadeOut", this.dataManager.get(FADE_OUT_TICKS));
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
		this.dataManager.set(SCALE, i);
		this.s0 = 0.8D * i;
		this.s1 = 0.65D * i;
		this.s2 = 0.5D * i;
		this.s3 = 0.4D * i;
		this.s4 = 0.3D * i;
		this.s5 = 0.2D * i;
		this.s6 = 0.1D * i;
        this.s7 = 0.05D * i;
        this.fallingRadius = craterRadius > 15 ? craterRadius : 0;
		this.doDrop = this.fallingRadius > 20;
	}

	public int getScale() {

		int scale = this.dataManager.get(SCALE);

		return scale == 0 ? 1 : scale;
	}
}


