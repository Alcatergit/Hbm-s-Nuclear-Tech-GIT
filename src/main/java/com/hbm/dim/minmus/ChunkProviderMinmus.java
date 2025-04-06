package com.hbm.dim.minmus;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.ChunkProviderCelestial;
import com.hbm.dim.mapgen.MapGenCrater;
import com.hbm.dim.mapgen.MapGenVanillaCaves;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;

import javax.annotation.Nullable;

public class ChunkProviderMinmus extends ChunkProviderCelestial {
	
	private MapGenBase caveGenerator = new MapGenVanillaCaves(ModBlocks.minmus_stone).withLava(ModBlocks.minmus_smooth);

	private MapGenCrater smallCrater = new MapGenCrater(5);
	private MapGenCrater largeCrater = new MapGenCrater(96);

	public ChunkProviderMinmus(World world, long seed, boolean hasMapFeatures) {
		super(world, seed, hasMapFeatures);

		smallCrater.setSize(6, 24);
		largeCrater.setSize(64, 128);

		smallCrater.regolith = largeCrater.regolith = ModBlocks.minmus_regolith;
		smallCrater.rock = largeCrater.rock = ModBlocks.minmus_stone;
		
		stoneBlock = ModBlocks.minmus_stone;
		seaBlock = ModBlocks.minmus_smooth;
		seaLevel = 63;
	}

	@Override
	public ChunkPrimer getChunkPrimer(int x, int z) {
		ChunkPrimer buffer = super.getChunkPrimer(x, z);

		caveGenerator.generate(worldObj, x, z, buffer);
		smallCrater.generate(worldObj, x, z, buffer);
		largeCrater.generate(worldObj, x, z, buffer);
		
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
