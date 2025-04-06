package com.hbm.dim.duna;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.ChunkProviderCelestial;
import com.hbm.dim.duna.biome.BiomeGenBaseDuna;
import com.hbm.dim.mapgen.ExperimentalCaveGenerator;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;

import javax.annotation.Nullable;

public class ChunkProviderDuna extends ChunkProviderCelestial {

	private ExperimentalCaveGenerator caveGenV2 = new ExperimentalCaveGenerator(2, 40, 8.0F);

    public ChunkProviderDuna(World world, long seed, boolean hasMapFeatures) {
        super(world, seed, hasMapFeatures);
		stoneBlock = ModBlocks.duna_rock;

        caveGenV2.lavaBlock = ModBlocks.basalt;
        caveGenV2.stoneBlock = ModBlocks.duna_rock;
    }

    @Override
	public ChunkPrimer getChunkPrimer(int x, int z) {
        ChunkPrimer buffer = super.getChunkPrimer(x, z);

        if(biomesForGeneration[0] == BiomeGenBaseDuna.dunaLowlands) {
            // BEEG CAVES UNDER THE CANYONS
            this.caveGenV2.generate(worldObj, x, z, buffer);
        }
		
		return buffer;
	}

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z){return false;}
    @Override
    @Nullable
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored){return null;}
    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z){};
    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos){return false;}

}