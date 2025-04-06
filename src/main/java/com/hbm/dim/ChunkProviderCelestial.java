package com.hbm.dim;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.util.Compat;
import com.hbm.world.WorldUtil;
import com.hbm.world.biome.BiomeGenCraterBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.Loader;

public abstract class ChunkProviderCelestial implements IChunkGenerator {

	/**
	 * This class creates chunks, filling them with blocks via various noise funcs
	 * The variables directly below modify the generation parameters
	 */

	// Default block fills
	protected Block stoneBlock;
	protected Block seaBlock; // Doesn't have to be liquid, if you want like, basalt seas
	protected int seaLevel;

	// Noise frequency
	protected Vec3 firstOrderFreq = Vec3.createVectorHelper(684.412D, 684.412D, 684.412D);
	protected Vec3 secondOrderFreq = Vec3.createVectorHelper(684.412D, 684.412D, 684.412D);
	protected Vec3 thirdOrderFreq = Vec3.createVectorHelper(8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
	protected Vec3 heightOrderFreq = Vec3.createVectorHelper(200.0D, 200.0D, 0.5D);

	// Embiggenify
	protected boolean amplified = false;
	
	//round
	protected boolean reclamp = true;
	

	// Now for the regular stuff, changing these won't change gen, just break things
	protected World worldObj;
	protected final boolean mapFeaturesEnabled;
	protected Random rand;
	
	// Generation buffers and the like, no need to modify or have visibility to these
	private NoiseGeneratorOctaves firstOrder;
	private NoiseGeneratorOctaves secondOrder;
	private NoiseGeneratorOctaves thirdOrder;
	private NoiseGeneratorOctaves perlin;
	private NoiseGeneratorPerlin realPerlin;
	private NoiseGeneratorOctaves heightOrder;

	protected Biome[] biomesForGeneration;
	private final double[] terrainBuffer;
	private final float[] parabolicField;
	private double[] stoneNoise = new double[256];

	private double[] firstOrderBuffer;
	private double[] secondOrderBuffer;
	private double[] thirdOrderBuffer;
	private double[] heightOrderBuffer;

	public ChunkProviderCelestial(World world, long seed, boolean hasMapFeatures) {
		this.worldObj = world;
		this.mapFeaturesEnabled = hasMapFeatures;

		this.rand = new Random(seed);
		this.firstOrder = new NoiseGeneratorOctaves(this.rand, 16);
		this.secondOrder = new NoiseGeneratorOctaves(this.rand, 16);
		this.thirdOrder = new NoiseGeneratorOctaves(this.rand, 8);
		this.perlin = new NoiseGeneratorOctaves(this.rand, 4);
		this.heightOrder = new NoiseGeneratorOctaves(this.rand, 16);
		this.realPerlin = new NoiseGeneratorPerlin(this.rand, 4);

		this.terrainBuffer = new double[825];
		this.parabolicField = new float[25];

		for(int j = -2; j <= 2; ++j) {
			for(int k = -2; k <= 2; ++k) {
				float f = 10.0F / MathHelper.sqrt((float) (j * j + k * k) + 0.2F);
				this.parabolicField[j + 2 + (k + 2) * 5] = f;
			}
		}

		stoneBlock = Blocks.STONE;
		seaBlock = Blocks.AIR;
		seaLevel = 63;

		InitNoiseGensEvent.Context ctx = new InitNoiseGensEvent.Context(firstOrder, secondOrder, thirdOrder, perlin, heightOrder);
		ctx = TerrainGen.getModdedNoiseGenerators(world, this.rand, ctx);
		this.firstOrder = ctx.getLPerlin1();
		this.secondOrder = ctx.getLPerlin2();
		this.thirdOrder = ctx.getPerlin();
		this.perlin = ctx.getScale();
		this.heightOrder = ctx.getDepth();
	}

	/**
	 * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
	 * specified chunk from the map seed and chunk seed
	 */
	protected ChunkPrimer getChunkPrimer(int x, int z) {
		ChunkPrimer chunkPrimer = new ChunkPrimer();
		generateBlocks(x, z, chunkPrimer);
		biomesForGeneration = this.worldObj.getBiomeProvider().getBiomes(biomesForGeneration, x * 16, z * 16, 16, 16);
		replaceBlocksForBiome(x, z, chunkPrimer, biomesForGeneration);
		return chunkPrimer;
	}

	// Fills in the chunk with stone, water, and air
	protected void generateBlocks(int x, int z, ChunkPrimer chunkPrimer) {
		biomesForGeneration = worldObj.getBiomeProvider().getBiomesForGeneration(biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
		generateNoiseField(x * 4, 0, z * 4);

		for (int k = 0; k < 4; ++k) {
			int l = k * 5;
			int i1 = (k + 1) * 5;

			for (int j1 = 0; j1 < 4; ++j1) {
				int k1 = (l + j1) * 33;
				int l1 = (l + j1 + 1) * 33;
				int i2 = (i1 + j1) * 33;
				int j2 = (i1 + j1 + 1) * 33;

				for (int k2 = 0; k2 < 32; ++k2) {
					double d0 = 0.125D;
					double d1 = terrainBuffer[k1 + k2];
					double d2 = terrainBuffer[l1 + k2];
					double d3 = terrainBuffer[i2 + k2];
					double d4 = terrainBuffer[j2 + k2];
					double d5 = (terrainBuffer[k1 + k2 + 1] - d1) * d0;
					double d6 = (terrainBuffer[l1 + k2 + 1] - d2) * d0;
					double d7 = (terrainBuffer[i2 + k2 + 1] - d3) * d0;
					double d8 = (terrainBuffer[j2 + k2 + 1] - d4) * d0;

					for (int l2 = 0; l2 < 8; ++l2) {
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;

						for (int i3 = 0; i3 < 4; ++i3) {
							int xPos = i3 + k * 4;
							int yPos = k2 * 8 + l2;
							int zPos = j1 * 4;
							double d14 = 0.25D;
							double d16 = (d11 - d10) * d14;
							double d15 = d10 - d16;

							for (int k3 = 0; k3 < 4; ++k3) {
								if ((d15 += d16) > 0.0D) {
									chunkPrimer.setBlockState(xPos, yPos, zPos + k3, stoneBlock.getDefaultState());
								} else if (yPos < seaLevel) {
									chunkPrimer.setBlockState(xPos, yPos, zPos + k3, seaBlock.getDefaultState());
								} else {
									chunkPrimer.setBlockState(xPos, yPos, zPos + k3, Blocks.AIR.getDefaultState());
								}
							}

							d10 += d12;
							d11 += d13;
						}

						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
	}

	// Fills up a buffer with "chances for this block to be stone" using 3D noise and biome specific information
	protected void generateNoiseField(int x, int y, int z) {
		firstOrderBuffer = firstOrder.generateNoiseOctaves(firstOrderBuffer, x, y, z, 5, 33, 5, firstOrderFreq.xCoord, firstOrderFreq.yCoord, firstOrderFreq.zCoord);
		secondOrderBuffer = secondOrder.generateNoiseOctaves(secondOrderBuffer, x, y, z, 5, 33, 5, secondOrderFreq.xCoord, secondOrderFreq.yCoord, secondOrderFreq.zCoord);
		thirdOrderBuffer = thirdOrder.generateNoiseOctaves(thirdOrderBuffer, x, y, z, 5, 33, 5, thirdOrderFreq.xCoord, thirdOrderFreq.yCoord, thirdOrderFreq.zCoord);
		heightOrderBuffer = heightOrder.generateNoiseOctaves(heightOrderBuffer, x, z, 5, 5, heightOrderFreq.xCoord, heightOrderFreq.yCoord, heightOrderFreq.zCoord);

		int l = 0;
		int i1 = 0;

		for(int j1 = 0; j1 < 5; ++j1) {
			for(int k1 = 0; k1 < 5; ++k1) {
				float f = 0.0F;
				float f1 = 0.0F;
				float f2 = 0.0F;
				byte b0 = 2;
				Biome biomegenbase = biomesForGeneration[j1 + 2 + (k1 + 2) * 10];

				for(int l1 = -b0; l1 <= b0; ++l1) {
					for(int i2 = -b0; i2 <= b0; ++i2) {
						Biome biomegenbase1 = biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
						float f3 = biomegenbase1.getBaseHeight();
						float f4 = biomegenbase1.getHeightVariation();

                        if(amplified && f3 > 0.0F) {
                            f3 = 1.0F + f3 * 2.0F;
                            f4 = 1.0F + f4 * 4.0F;
                        }

						float f5 = parabolicField[l1 + 2 + (i2 + 2) * 5] / (f3 + 2.0F);

						if(biomegenbase1.getBaseHeight() > biomegenbase.getBaseHeight()) {
							f5 /= 2.0F;
						}

						f += f4 * f5;
						f1 += f3 * f5;
						f2 += f5;
					}
				}

				f /= f2;
				f1 /= f2;
				f = f * 0.9F + 0.1F;
				f1 = (f1 * 4.0F - 1.0F) / 8.0F;
				double d12 = heightOrderBuffer[i1] / 8000.0D;

				if(d12 < 0.0D) {
					d12 = -d12 * 0.3D;
				}

				d12 = d12 * 3.0D - 2.0D;

				if(d12 < 0.0D) {
					d12 /= 2.0D;

					if(d12 < -1.0D) {
						d12 = -1.0D;
					}

					d12 /= 1.4D;
					d12 /= 2.0D;
				} else {
					if(d12 > 1.0D) {
						d12 = 1.0D;
					}

					d12 /= 8.0D;
				}

				++i1;
				double d13 = (double) f1;
				double d14 = (double) f;
				d13 += d12 * 0.2D;
				d13 = d13 * 8.5D / 8.0D;
				double d5 = 8.5D + d13 * 4.0D;

				for(int j2 = 0; j2 < 33; ++j2) {
					double d6 = ((double) j2 - d5) * 12.0D * 128.0D / 256.0D / d14;

					if(d6 < 0.0D) {
						d6 *= 4.0D;
					}

					double d7 = firstOrderBuffer[l] / 512.0D; 
					double d8 = secondOrderBuffer[l] / 512.0D;
					double d9 = (thirdOrderBuffer[l] / 10.0D + 1.0D) / 2.0D;
					//srry, there has to be a better way to smooth out things, we got the perlin tools to do so but i have no idea how to invoke those tools here.
					//maybe sometime soon...?
					double d10 = reclamp ? MathHelper.clampedLerp(d7, d8, d9) - d6 : d8 - d6;
					if(j2 > 29) {
						double d11 = (double) ((float) (j2 - 29) / 3.0F);
						d10 = d10 * (1.0D - d11) + -10.0D * d11;
					}

					terrainBuffer[l] = d10;
					++l;
				}
			}
		}
	}

	protected void replaceBlocksForBiome(int x, int z, ChunkPrimer chunkPrimer, Biome[] biomes) {
		double d0 = 0.03125D;
		stoneNoise = realPerlin.getRegion(stoneNoise, (double) (x * 16), (double) (z * 16), 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

		for (int k = 0; k < 16; ++k) {
			for (int l = 0; l < 16; ++l) {
				Biome biome = biomes[l + k * 16];
				biome.genTerrainBlocks(worldObj, rand, chunkPrimer, x * 16 + k, z * 16 + l, stoneNoise[l + k * 16]);
			}
		}
	}

	@Override
	public Chunk generateChunk(int x, int z) {
		rand.setSeed((long) x * 341873128712L + (long) z * 132897987541L);

		ChunkPrimer ablock = getChunkPrimer(x, z);
		Chunk chunk = new Chunk(worldObj, ablock, x, z);
		byte[] biomes = chunk.getBiomeArray();
		for(int k = 0; k < biomes.length; ++k) {
			biomes[k] = (byte) Biome.getIdForBiome(biomesForGeneration[k]);
		}

		chunk.generateSkylightMap();
		return chunk;
	}

	/**
	 * Populates chunk with ores etc etc
	 */
	@Override
	public void populate(int x, int z) {
		BlockFalling.fallInstantly = true;

		int k = x * 16;
		int l = z * 16;
		BlockPos blockpos = new BlockPos(k, 0, l);
		Biome biome = this.worldObj.getBiome(blockpos.add(16, 0, 16));
		rand.setSeed(worldObj.getSeed());
		long i1 = rand.nextLong() / 2L * 2L + 1L;
		long j1 = rand.nextLong() / 2L * 2L + 1L;
		rand.setSeed((long) x * i1 + (long) z * j1 ^ worldObj.getSeed());
		biome.decorate(this.worldObj, this.rand, blockpos);
		WorldEntitySpawner.performWorldGenSpawning(this.worldObj, biome, k + 8, l + 8, 16, 16, this.rand);

		BlockFalling.fallInstantly = false;
	}

	/**
	 * Returns a list of creatures of the specified type that can spawn at the given
	 * location.
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        Biome biomegenbase = this.worldObj.getBiome(pos);
		if(biomegenbase instanceof BiomeGenCraterBase) return new ArrayList<Biome.SpawnListEntry>();
        return biomegenbase.getSpawnableList(creatureType);
	}

	/**
	 * I have no fucking clue, just return false
	 */
	@Override
	public boolean isInsideStructure(World world, String shitfuck, BlockPos pos) {
		return false;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {

	}

	public static class BlockMetaBuffer {
		public Block[] blocks = new Block[65536];
		public byte[] metas = new byte[65536];
	}

}
