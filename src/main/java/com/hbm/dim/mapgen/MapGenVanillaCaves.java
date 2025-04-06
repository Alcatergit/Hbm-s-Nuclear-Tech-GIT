package com.hbm.dim.mapgen;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenCaves;

public class MapGenVanillaCaves extends MapGenCaves {

	// Same as vanilla cavegen but supports celestial stone
	private final Block stoneBlock;

	private Block lavaBlock = Blocks.LAVA;

	public MapGenVanillaCaves(Block stoneBlock) {
		this.stoneBlock = stoneBlock;
	}

	public MapGenVanillaCaves withLava(Block lavaBlock) {
		this.lavaBlock = lavaBlock;
		return this;
	}

	@Override
	protected void digBlock(ChunkPrimer primer, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop, IBlockState state, IBlockState up) {
		Biome biome = world.getBiomeForCoordsBody(new BlockPos(x + chunkX * 16, y, z + chunkZ * 16));
		IBlockState block = primer.getBlockState(x, y, z);

		if(block == stoneBlock.getDefaultState() || block == biome.fillerBlock || block == biome.topBlock) {
			if(y < 10) {
				primer.setBlockState(x, y, z, lavaBlock.getDefaultState());
			} else {
				primer.setBlockState(x, y, z, Blocks.AIR.getDefaultState());

				if(foundTop && primer.getBlockState(x, y - 1, z) == biome.fillerBlock) {
					primer.setBlockState(x, y - 1, z, biome.topBlock);
				}
			}
		}
	}

}
