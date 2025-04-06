package com.hbm.dim.Ike;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.BiomeDecoratorCelestial;
import com.hbm.dim.BiomeGenBaseCelestial;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class BiomeGenIke extends BiomeGenBaseCelestial {

	public BiomeGenIke(BiomeProperties properties) {
		super(properties);
		properties.setBaseBiome("Ike");
		properties.setRainDisabled();

		BiomeDecoratorCelestial decorator = new BiomeDecoratorCelestial(ModBlocks.ike_stone);
		decorator.lakeChancePerChunk = 8;
		decorator.lakeBlock = ModBlocks.bromine_block;
		this.decorator = decorator;
		this.decorator.generateFalls = false;

		this.topBlock = ModBlocks.ike_regolith.getDefaultState();
		this.fillerBlock = ModBlocks.ike_regolith.getDefaultState(); // thiccer regolith due to uhhhhhh...................
		this.creatures.clear();
	}

	@Override
	public void genTerrainBlocks(World world, Random rand, ChunkPrimer chunkPrimer, int x, int z, double noise) {
		IBlockState topBlockState = this.topBlock;
		IBlockState fillerBlockState = this.fillerBlock;
		int k = -1;
		int l = (int) (noise / 8.0D + 8.0D + rand.nextDouble() * 0.50D);
		int i1 = x & 15;
		int j1 = z & 15;
		int maxHeight = 256; // Максимальная высота мира

		for (int l1 = maxHeight - 1; l1 >= 0; --l1) {
			BlockPos pos = new BlockPos(x, l1, z);
			IBlockState currentState = chunkPrimer.getBlockState(i1, l1, j1);

			if (l1 <= 0 + rand.nextInt(5)) {
				chunkPrimer.setBlockState(i1, l1, j1, Blocks.BEDROCK.getDefaultState());
			} else {
				if (currentState.getBlock() != Blocks.AIR) {
					if (currentState.getBlock() == ModBlocks.ike_stone) {
						if (k == -1) {
							if (l <= 0) {
								topBlockState = Blocks.AIR.getDefaultState();
								fillerBlockState = ModBlocks.ike_stone.getDefaultState();
							} else if (l1 >= 59 && l1 <= 64) {
								topBlockState = this.topBlock;
								fillerBlockState = this.fillerBlock;
							}

							if (l1 < 63 && (topBlockState.getBlock() == Blocks.AIR)) {
								if (this.getTemperature(pos) < 0.15F) {
									topBlockState = this.topBlock;
								} else {
									topBlockState = this.topBlock;
								}
							}

							k = l;

							if (l1 >= 62) {
								chunkPrimer.setBlockState(i1, l1, j1, topBlockState);
							} else if (l1 < 62) {
								topBlockState = Blocks.AIR.getDefaultState();
								fillerBlockState = ModBlocks.ike_stone.getDefaultState();
								chunkPrimer.setBlockState(i1, l1, j1, Blocks.GRAVEL.getDefaultState());
							} else {
								chunkPrimer.setBlockState(i1, l1, j1, fillerBlockState);
							}
						} else if (k > 0) {
							--k;
							chunkPrimer.setBlockState(i1, l1, j1, fillerBlockState);

							if (k == 0 && fillerBlockState.getBlock() == Blocks.SAND) {
								k = rand.nextInt(4) + Math.max(0, l1 - 63);
								fillerBlockState = Blocks.SANDSTONE.getDefaultState();
							}
						}
					}
				} else {
					k = -1;
				}
			}
		}
	}
}