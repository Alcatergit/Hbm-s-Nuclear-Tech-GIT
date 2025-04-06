package com.hbm.dim.moho;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.ChunkProviderCelestial;
import com.hbm.dim.mapgen.ExperimentalCaveGenerator;
import com.hbm.dim.mapgen.MapGenCrater;
import com.hbm.dim.mapgen.MapGenVolcano;
import com.hbm.dim.mapgen.MapgenRavineButBased;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;

import javax.annotation.Nullable;

public class ChunkProviderMoho extends ChunkProviderCelestial {

	private ExperimentalCaveGenerator caveGenV2 = new ExperimentalCaveGenerator(1, 52, 10.0F);
	private MapgenRavineButBased rgen = new MapgenRavineButBased();

	private MapGenCrater smallCrater = new MapGenCrater(6);
	private MapGenCrater largeCrater = new MapGenCrater(64);
	private MapGenVolcano volcano = new MapGenVolcano(72);

	public ChunkProviderMoho(World world, long seed, boolean hasMapFeatures) {
		super(world, seed, hasMapFeatures);

		smallCrater.setSize(8, 32);
		largeCrater.setSize(96, 128);
		volcano.setSize(64, 128);

		smallCrater.regolith = largeCrater.regolith = ModBlocks.moho_regolith;
		smallCrater.rock = largeCrater.rock = ModBlocks.moho_stone;

		caveGenV2.stoneBlock = ModBlocks.moho_stone;
		rgen.stoneBlock = ModBlocks.moho_stone;
		stoneBlock = ModBlocks.moho_stone;
		seaBlock = Blocks.LAVA;
	}

	@Override
	public ChunkPrimer getChunkPrimer(int x, int z) {
		ChunkPrimer buffer = super.getChunkPrimer(x, z);

		// how many times do I gotta say BEEEEG
		caveGenV2.generate(worldObj, x, z, buffer);
		rgen.generate(worldObj, x, z, buffer);
		smallCrater.generate(worldObj, x, z, buffer);
		largeCrater.generate(worldObj, x, z, buffer);
		volcano.generate(worldObj, x, z, buffer);

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
