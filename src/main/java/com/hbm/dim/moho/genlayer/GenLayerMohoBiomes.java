package com.hbm.dim.moho.genlayer;

import com.hbm.dim.moho.biome.BiomeGenBaseMoho;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerMohoBiomes extends GenLayer {
	
	// 4 times as much crag
	private static final Biome[] biomes = new Biome[] { BiomeGenBaseMoho.mohoCrag, BiomeGenBaseMoho.mohoCrag, BiomeGenBaseMoho.mohoCrag, BiomeGenBaseMoho.mohoCrag, BiomeGenBaseMoho.mohoBasalt };

	public GenLayerMohoBiomes(long l) {
		super(l);
	}

	@Override
	public int[] getInts(int x, int z, int width, int depth) {
		int[] dest = IntCache.getIntCache(width * depth);

		for(int k = 0; k < depth; ++k) {
			for(int i = 0; i < width; ++i) {
				initChunkSeed(x + i, z + k);
				dest[i + k * width] = Biome.getIdForBiome(biomes[nextInt(biomes.length)]);
			}
		}

		return dest;
	}
}