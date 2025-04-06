package com.hbm.dim.mapgen;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenRavine;

public class MapgenRavineButBased extends MapGenRavine {

	public Block stoneBlock;

	public MapgenRavineButBased() {
		super();
		this.stoneBlock = Blocks.STONE;
	}

	@Override
	protected void digBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop) {
		Biome biome = world.getBiomeForCoordsBody(new BlockPos(x + chunkX * 16, y, z + chunkZ * 16));
		IBlockState top = biome.topBlock;
		IBlockState filler = biome.fillerBlock;
		IBlockState block = data.getBlockState(x, y, z);

		if(block == stoneBlock.getDefaultState() || block == filler || block == top) {
			if(y < 10) {
				data.setBlockState(x, y, z, Blocks.FLOWING_LAVA.getDefaultState());
			} else {
				data.setBlockState(x, y, z, Blocks.AIR.getDefaultState());

				if (foundTop && data.getBlockState(x, y - 1, z) == filler) {
					data.setBlockState(x, y - 1, z, top);
				}
			}
		}
	}

}
