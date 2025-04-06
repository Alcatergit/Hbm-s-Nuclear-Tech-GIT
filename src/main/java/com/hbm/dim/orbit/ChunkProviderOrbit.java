package com.hbm.dim.orbit;

import java.util.List;

import com.hbm.config.SpaceConfig;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

public class ChunkProviderOrbit implements IChunkGenerator {

	protected World worldObj;

	public ChunkProviderOrbit(World world) {
		this.worldObj = world;
	}

	@Override
	public Chunk generateChunk(int x, int z) {
		Chunk chunk = new Chunk(worldObj, new ChunkPrimer(), x, z);
			byte[] biomes = chunk.getBiomeArray();
			for(int k = 0; k < biomes.length; ++k) {
				biomes[k] = (byte) SpaceConfig.orbitBiome;
			}

		chunk.generateSkylightMap();
		return chunk;
	}

	/**
	 * Populates chunk with ores etc etc
	 */
	@Override
	public void populate(int x, int z) {
	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z){return false;}

	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos){return false;}

	/**
	 * Returns a list of creatures of the specified type that can spawn at the given
	 * location.
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		return null;
	}

	/**
	 * I have no fucking clue, just return null
	 */
	@Override
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
		return null;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {

	}

}
