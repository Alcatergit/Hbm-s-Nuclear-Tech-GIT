package com.hbm.dim.duna.GenLayerDuna;

import com.hbm.dim.duna.biome.BiomeGenBaseDuna;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerDunaBiomes extends GenLayer {

	private static final Biome[] biomes = new Biome[] { BiomeGenBaseDuna.dunaPlains, BiomeGenBaseDuna.dunaLowlands, BiomeGenBaseDuna.dunaPolar, BiomeGenBaseDuna.dunaHills, BiomeGenBaseDuna.dunaPolarHills };

	public GenLayerDunaBiomes(long l) {
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
