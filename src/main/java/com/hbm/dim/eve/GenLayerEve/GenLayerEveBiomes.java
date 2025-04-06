package com.hbm.dim.eve.GenLayerEve;

import com.hbm.dim.eve.biome.BiomeGenBaseEve;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerEveBiomes extends GenLayer {

    private static final Biome[] biomes = new Biome[] { BiomeGenBaseEve.evePlains, BiomeGenBaseEve.eveOcean, BiomeGenBaseEve.eveMountains };
    protected Biome[] rareBiomes = { BiomeGenBaseEve.eveSeismicPlains };

    public GenLayerEveBiomes(long l) {
        super(l);
    }

    @Override
    public int[] getInts(int x, int z, int width, int depth) {
        int[] dest = IntCache.getIntCache(width * depth);

        for(int dz = 0; dz < depth; dz++) {
            for(int dx = 0; dx < width; dx++) {
                this.initChunkSeed(dx + x, dz + z);

                if(this.nextInt(20) == 0) {
                    dest[dx + dz * width] = Biome.getIdForBiome(this.rareBiomes[this.nextInt(this.rareBiomes.length)]);
                } else {
                    dest[dx + dz * width] = Biome.getIdForBiome(biomes[this.nextInt(biomes.length)]);
                }
            }
        }
        return dest;
    }
}
