package com.hbm.dim.minmus.biome;

import com.hbm.blocks.ModBlocks;

public class BiomeGenMinmusBasin extends BiomeGenBaseMinmus {

	public BiomeGenMinmusBasin(BiomeProperties properties) {
		super(properties);
        this.topBlock = ModBlocks.minmus_smooth.getDefaultState();
        this.fillerBlock = ModBlocks.minmus_regolith.getDefaultState();
	}

}