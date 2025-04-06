package com.hbm.dim;

import java.util.List;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldChunkManagerCelestial extends BiomeProvider {
	
	private GenLayer biomeLayer;
	private GenLayer biomeDetailLayer;

	private BiomeCache biomeCache;
	
	public WorldChunkManagerCelestial(BiomeGenLayers layers) {
		biomeCache = new BiomeCache(this);
		this.biomeLayer = layers.biomeLayer;
		this.biomeDetailLayer = layers.biomeDetailLayer;
	}
    
    // getBiomesToSpawnIn is never called for celestial bodies, so fuck that noise lmao

    @Override
    public Biome getBiome(BlockPos pos) {
        return this.biomeCache.getBiome(pos.getX(), pos.getZ(), null);
    }


	@SideOnly(Side.CLIENT)
	@Override
    public float getTemperatureAtHeight(float temperature, int y) {
        return temperature;
    }

    @Override
    public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int length) {
        if (biomes == null || biomes.length < width * length) {
            biomes = new Biome[width * length];
        }

        int[] biomeIds = this.biomeLayer.getInts(x, z, width, length);

        for (int i = 0; i < width * length; ++i) {
            biomes[i] = Biome.getBiome(biomeIds[i]);
        }

        return biomes;
    }

    @Override
    public Biome[] getBiomes(Biome[] biomes, int x, int z, int width, int length, boolean cacheFlag) {
        if (biomes == null || biomes.length < width * length) {
            biomes = new Biome[width * length];
        }

        if (cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0) {
            Biome[] cachedBiomes = this.biomeCache.getCachedBiomes(x, z);
            System.arraycopy(cachedBiomes, 0, biomes, 0, width * length);
            return biomes;
        } else {
            int[] biomeIds = this.biomeDetailLayer.getInts(x, z, width, length);

            for (int i = 0; i < width * length; ++i) {
                biomes[i] = Biome.getBiome(biomeIds[i]);
            }

            return biomes;
        }
    }

	// Used by structures, generally, to find if a given chunk contains any of the specified biomes
	@SuppressWarnings("rawtypes")
    @Override
    public boolean areBiomesViable(int x, int z, int radius, List<Biome> allowed) {
        int l = x - radius >> 2;
        int i1 = z - radius >> 2;
        int j1 = x + radius >> 2;
        int k1 = z + radius >> 2;
        int l1 = j1 - l + 1;
        int i2 = k1 - i1 + 1;
        int[] aint = this.biomeLayer.getInts(l, i1, l1, i2);

        for (int j2 = 0; j2 < l1 * i2; ++j2) {
            Biome biome = Biome.getBiome(aint[j2]);

            if (!allowed.contains(biome)) {
                return false;
            }
        }

        return true;
    }

	// Finds a chunk in a given area that has one of the specified biomes
	@SuppressWarnings("rawtypes")
    @Override
    public BlockPos findBiomePosition(int x, int z, int radius, List<Biome> biomes, Random random) {
        int l = x - radius >> 2;
        int i1 = z - radius >> 2;
        int j1 = x + radius >> 2;
        int k1 = z + radius >> 2;
        int l1 = j1 - l + 1;
        int i2 = k1 - i1 + 1;
        int[] aint = this.biomeLayer.getInts(l, i1, l1, i2);
        BlockPos blockpos = null;
        int j2 = 0;

        for (int k2 = 0; k2 < l1 * i2; ++k2) {
            int l2 = l + k2 % l1 << 2;
            int i3 = i1 + k2 / l1 << 2;
            Biome biome = Biome.getBiome(aint[k2]);

            if (biomes.contains(biome) && (blockpos == null || random.nextInt(j2 + 1) == 0)) {
                blockpos = new BlockPos(l2, 0, i3);
                ++j2;
            }
        }

        return blockpos;
    }

    @Override
    public void cleanupCache() {
        this.biomeCache.cleanupCache();
    }

    public static class BiomeGenLayers {

        private GenLayer biomeLayer;
        private GenLayer biomeDetailLayer;

        public BiomeGenLayers(GenLayer biomeLayer, GenLayer biomeDetailLayer, long seed) {
            this.biomeLayer = biomeLayer;
            this.biomeDetailLayer = biomeDetailLayer;

            biomeLayer.initWorldGenSeed(seed);
            biomeDetailLayer.initWorldGenSeed(seed);
        }

    }

}
