package com.hbm.dim.dres.biome;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class BiomeGenDresPlains extends BiomeGenBaseDres {

	public BiomeGenDresPlains(BiomeProperties properties) {
		super(properties);
	}

	@Override
	public void genTerrainBlocks(World world, Random rand, ChunkPrimer chunkPrimer, int x, int z, double noise) {
		IBlockState topState = this.topBlock;
		IBlockState fillerState = this.fillerBlock;
		int k = -1;
		int l = (int) (noise / 8.0D + 8.0D + rand.nextDouble() * 0.50D);
		int i1 = x & 15;
		int j1 = z & 15;
		int k1 = 256;

		for (int l1 = k1 - 1; l1 >= 0; --l1) {
			BlockPos pos = new BlockPos(i1, l1, j1);
			IBlockState currentState = chunkPrimer.getBlockState(i1, l1, j1);

			if (currentState.getBlock() != Blocks.AIR) {
				if (currentState.getBlock() == ModBlocks.dres_rock) {
					if (k == -1) {
						if (l <= 0) {
							topState = Blocks.AIR.getDefaultState();
							fillerState = ModBlocks.sellafield_slaked.getDefaultState();
						} else if (l1 >= 59 && l1 <= 64) {
							topState = this.topBlock;
							fillerState = this.fillerBlock;
						}

						if (l1 < 63 && (topState.getBlock() == Blocks.AIR)) {
							topState = this.topBlock;
						}

						k = l;

						if (l1 >= 82) {
							chunkPrimer.setBlockState(i1, l1, j1, topState);
						} else if (l1 < 80) {
							topState = Blocks.AIR.getDefaultState();
							fillerState = ModBlocks.sellafield_slaked.getDefaultState();
							if (Math.random() > 0.4) {
								chunkPrimer.setBlockState(i1, l1, j1, ModBlocks.sellafield_slaked.getDefaultState());
							} else {
								chunkPrimer.setBlockState(i1, l1, j1, ModBlocks.sellafield_slaked.getDefaultState());
							}
						} else {
							chunkPrimer.setBlockState(i1, l1, j1, fillerState);
						}
					} else if (k > 0) {
						--k;
						chunkPrimer.setBlockState(i1, l1, j1, fillerState);

						if (k == 0 && fillerState.getBlock() == Blocks.SAND) {
							k = rand.nextInt(4) + Math.max(0, l1 - 63);
							fillerState = Blocks.SANDSTONE.getDefaultState();
						}
					}
				}
			} else {
				k = -1;
			}
		}
	}

}