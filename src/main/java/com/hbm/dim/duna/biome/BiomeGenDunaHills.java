package com.hbm.dim.duna.biome;

import java.util.Arrays;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class BiomeGenDunaHills extends BiomeGenBaseDuna {

	private byte[] field_150621_aC;
	private long seed;
	private NoiseGeneratorPerlin perlin1;
	private NoiseGeneratorPerlin perlin2;
	private NoiseGeneratorPerlin perlin3;
	private boolean field_150626_aH;
	private boolean field_150620_aI;

	public BiomeGenDunaHills(BiomeProperties properties) {
		super(properties);
	}

	// Ripped from BiomeGenMesa
	@Override
	public void genTerrainBlocks(World world, Random rand, ChunkPrimer chunkPrimer, int x, int z, double noise) {
		if (this.field_150621_aC == null || this.seed != world.getSeed()) {
			this.generateBuffers(world.getSeed());
		}

		if (this.perlin1 == null || this.perlin2 == null || this.seed != world.getSeed()) {
			Random random1 = new Random(this.seed);
			this.perlin1 = new NoiseGeneratorPerlin(random1, 4);
			this.perlin2 = new NoiseGeneratorPerlin(random1, 1);
		}

		this.seed = world.getSeed();
		double d5 = 0.0D;
		int k;
		int l;

		if (this.field_150626_aH) {
			k = (x & -16) + (z & 15);
			l = (z & -16) + (x & 15);
			double d1 = Math.min(Math.abs(noise),
					this.perlin1.getValue((double) k * 0.25D, (double) l * 0.25D));

			if (d1 > 0.0D) {
				double d2 = 0.001953125D;
				double d3 = Math.abs(this.perlin2.getValue((double) k * d2, (double) l * d2));
				d5 = d1 * d1 * 2.5D;
				double d4 = Math.ceil(d3 * 50.0D) + 14.0D;

				if (d5 > d4) {
					d5 = d4;
				}

				d5 += 64.0D;
			}
		}

		k = x & 15;
		l = z & 15;
		IBlockState topBlockState = ModBlocks.ferric_clay.getDefaultState();
		IBlockState fillerBlockState = this.fillerBlock;
		int i1 = (int) (noise / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		boolean flag1 = Math.cos(noise / 3.0D * Math.PI) > 0.0D;
		int j1 = -1;
		boolean flag2 = false;
		int maxHeight = 256; // Максимальная высота мира

		for (int l1 = maxHeight - 1; l1 >= 0; --l1) {
			BlockPos pos = new BlockPos(x, l1, z);
			IBlockState currentState = chunkPrimer.getBlockState(k, l1, l);

			if ((currentState.getBlock() == Blocks.AIR) && l1 < (int) d5) {
				chunkPrimer.setBlockState(k, l1, l, ModBlocks.duna_rock.getDefaultState());
			}

			if (l1 <= 0 + rand.nextInt(5)) {
				chunkPrimer.setBlockState(k, l1, l, Blocks.BEDROCK.getDefaultState());
			} else {
				if (currentState.getBlock() == ModBlocks.duna_rock) {
					if (j1 == -1) {
						flag2 = false;

						if (i1 <= 0) {
							topBlockState = Blocks.AIR.getDefaultState();
							fillerBlockState = ModBlocks.duna_rock.getDefaultState();
						} else if (l1 >= 59 && l1 <= 64) {
							topBlockState = ModBlocks.ferric_clay.getDefaultState();
							fillerBlockState = this.fillerBlock;
						}

						if (l1 < 63 && (topBlockState.getBlock() == Blocks.AIR)) {
							topBlockState = Blocks.WATER.getDefaultState();
						}

						j1 = i1 + Math.max(0, l1 - 63);

						if (l1 >= 62) {
							if (this.field_150620_aI && l1 > 86 + i1 * 2) {
								if (flag1) {
									chunkPrimer.setBlockState(k, l1, l, ModBlocks.duna_sands.getDefaultState());
								} else {
									chunkPrimer.setBlockState(k, l1, l, ModBlocks.duna_sands.getDefaultState());
								}
							} else if (l1 > 66 + i1) {
								byte b0 = 16;

								if (l1 >= 64 && l1 <= 127) {
									if (!flag1) {
										b0 = this.func_150618_d(x, l1, z);
									}
								} else {
									b0 = 1;
								}

								if (b0 < 16) {
									chunkPrimer.setBlockState(k, l1, l, Blocks.STAINED_HARDENED_CLAY.getDefaultState());
								} else {
									chunkPrimer.setBlockState(k, l1, l, ModBlocks.duna_rock.getDefaultState());
								}
							} else {
								chunkPrimer.setBlockState(k, l1, l, this.topBlock);
								flag2 = true;
							}
						} else {
							chunkPrimer.setBlockState(k, l1, l, fillerBlockState);
						}
					} else if (j1 > 0) {
						--j1;

						if (flag2) {
							chunkPrimer.setBlockState(k, l1, l, ModBlocks.ferric_clay.getDefaultState());
						} else {
							byte b0 = this.func_150618_d(x, l1, z);

							if (b0 == 4) { // yellow is ugly
								chunkPrimer.setBlockState(k, l1, l, ModBlocks.ferric_clay.getDefaultState());
							} else if (b0 == 0) { // white is good for yer teeth
								//chunkPrimer.setBlockState(k, l1, l, ModBlocks.stone_resource.getDefaultState());
							} else if (b0 < 16) {
								chunkPrimer.setBlockState(k, l1, l, Blocks.STAINED_HARDENED_CLAY.getDefaultState());
							} else {
								chunkPrimer.setBlockState(k, l1, l, ModBlocks.duna_rock.getDefaultState());
							}
						}
					}
				} else {
					j1 = -1;
				}
			}
		}
	}

	public void generateBuffers(long seed) {
		this.field_150621_aC = new byte[64];
		Arrays.fill(this.field_150621_aC, (byte) 16);
		Random random = new Random(seed);
		this.perlin3 = new NoiseGeneratorPerlin(random, 1);
		int j;

		for (j = 0; j < 64; ++j) {
			j += random.nextInt(5) + 1;

			if (j < 64) {
				this.field_150621_aC[j] = 1;
			}
		}

		j = random.nextInt(4) + 2;
		int k;
		int l;
		int i1;
		int j1;

		for (k = 0; k < j; ++k) {
			l = random.nextInt(3) + 1;
			i1 = random.nextInt(64);

			for (j1 = 0; i1 + j1 < 64 && j1 < l; ++j1) {
				this.field_150621_aC[i1 + j1] = 4;
			}
		}

		k = random.nextInt(4) + 2;
		int k1;

		for (l = 0; l < k; ++l) {
			i1 = random.nextInt(3) + 2;
			j1 = random.nextInt(64);

			for (k1 = 0; j1 + k1 < 64 && k1 < i1; ++k1) {
				this.field_150621_aC[j1 + k1] = 12;
			}
		}

		l = random.nextInt(4) + 2;

		for (i1 = 0; i1 < l; ++i1) {
			j1 = random.nextInt(3) + 1;
			k1 = random.nextInt(64);

			for (int l1 = 0; k1 + l1 < 64 && l1 < j1; ++l1) {
				this.field_150621_aC[k1 + l1] = 14;
			}
		}

		i1 = random.nextInt(3) + 3;
		j1 = 0;

		for (k1 = 0; k1 < i1; ++k1) {
			byte b0 = 1;
			j1 += random.nextInt(16) + 4;

			for (int i2 = 0; j1 + i2 < 64 && i2 < b0; ++i2) {
				this.field_150621_aC[j1 + i2] = 0;

				if (j1 + i2 > 1 && random.nextBoolean()) {
					this.field_150621_aC[j1 + i2 - 1] = 8;
				}

				if (j1 + i2 < 63 && random.nextBoolean()) {
					this.field_150621_aC[j1 + i2 + 1] = 8;
				}
			}
		}
	}

	private byte func_150618_d(int p_150618_1_, int p_150618_2_, int p_150618_3_) {
		int l = (int)Math.round(this.perlin3.getValue((double)p_150618_1_ * 1.0D / 512.0D, (double)p_150618_1_ * 1.0D / 512.0D) * 2.0D);
		return this.field_150621_aC[(p_150618_2_ + l + 64) % 64];
	}

}