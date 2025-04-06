package com.hbm.dim.eve.GenLayerEve;

import com.hbm.dim.eve.biome.BiomeGenBaseEve;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerEveRiverMix extends GenLayerRiverMix {

	private GenLayer biomePatternGeneratorChain;
	private GenLayer riverPatternGeneratorChain;

	public GenLayerEveRiverMix(long seed, GenLayer biomePatternGeneratorChain, GenLayer riverPatternGeneratorChain) {
		super(seed, biomePatternGeneratorChain, riverPatternGeneratorChain);

		this.biomePatternGeneratorChain = biomePatternGeneratorChain;
		this.riverPatternGeneratorChain = riverPatternGeneratorChain;
	}

	@Override
	public void initWorldGenSeed(long seed) {
		this.biomePatternGeneratorChain.initWorldGenSeed(seed);
		this.riverPatternGeneratorChain.initWorldGenSeed(seed);
		super.initWorldGenSeed(seed);
	}

	@Override
	public int[] getInts(int x, int z, int width, int length) {
		int[] inputBiomeIds = this.biomePatternGeneratorChain.getInts(x, z, width, length);
		int[] riverBiomeIds = this.riverPatternGeneratorChain.getInts(x, z, width, length);
		int[] outputBiomeIds = IntCache.getIntCache(width * length);

		for(int i1 = 0; i1 < width * length; ++i1) {
			if(inputBiomeIds[i1] != Biome.getIdForBiome(BiomeGenBaseEve.eveOcean)) {
				if(riverBiomeIds[i1] == Biome.getIdForBiome(Biomes.RIVER)) {
					if(BiomeGenBaseEve.getBiomeForId(inputBiomeIds[i1]) != null) {
						outputBiomeIds[i1] = Biome.getIdForBiome(BiomeGenBaseEve.eveRiver);
					} else if(inputBiomeIds[i1] != Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND) && inputBiomeIds[i1] != Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND_SHORE)) {
						outputBiomeIds[i1] = riverBiomeIds[i1] & 255;
					} else {
						outputBiomeIds[i1] = Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND_SHORE);
					}
				} else {
					outputBiomeIds[i1] = inputBiomeIds[i1];
				}
			} else {
				outputBiomeIds[i1] = inputBiomeIds[i1];
			}
		}

		return outputBiomeIds;
	}
}