package com.hbm.dim;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.*;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

public class BiomeDecoratorCelestial extends BiomeDecorator {

	// Same as BiomeDecorator, but skips any vanilla plant life and supports different stone types

	public int lavaCount = 20;

	public int waterPlantsPerChunk = 0;
	public WorldGenerator genPlants;

	// ACTUAL lakes, not the single block stuff
	// honestly MCP couldja give things better names pls?
	public int lakeChancePerChunk = 0;
	public Block lakeBlock = Blocks.WATER;
	
	private final Block stoneBlock;

	public BiomeDecoratorCelestial(Block stoneBlock) {
		this.stoneBlock = stoneBlock;
		this.genPlants = new WorldGenWaterPlant();
	}

	@Override
	protected void genDecorations(Biome biome, World worldIn, Random random) {
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(worldIn, random, chunkPos));
		this.generateOres(worldIn, random);

		boolean doGen = TerrainGen.decorate(worldIn, random, chunkPos, LAKE_WATER);
		if(doGen && this.generateFalls) {
			for (int i = 0; i < lavaCount; ++i) {
				int x = this.chunkPos.getX() + random.nextInt(16) + 8;
				int y = random.nextInt(random.nextInt(random.nextInt(240) + 8) + 8);
				int z = this.chunkPos.getZ() + random.nextInt(16) + 8;
				(new WorldGenLiquidsCelestial(Blocks.FLOWING_LAVA, stoneBlock)).generate(worldIn, random, new BlockPos(x, y, z));
			}
		}

		if(doGen && this.lakeChancePerChunk > 0) {
			if(random.nextInt(lakeChancePerChunk) == 0) {
				int x = this.chunkPos.getX() + random.nextInt(16) + 8;
				int y = random.nextInt(256);
				int z = this.chunkPos.getZ() + random.nextInt(16) + 8;
				(new WorldGenLakes(lakeBlock)).generate(worldIn, random, new BlockPos(x, y, z));
			}
		}

		doGen = TerrainGen.decorate(worldIn, random, chunkPos, REED);
		if(doGen && this.waterPlantsPerChunk > 0) {
			for (int i = 0; i < waterPlantsPerChunk; ++i) {
				int x = this.chunkPos.getX() + random.nextInt(16) + 8;
				int z = this.chunkPos.getZ() + random.nextInt(16) + 8;
				int y = random.nextInt(64);
				genPlants.generate(worldIn, random, new BlockPos(x, y, z));
			}
		}

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(worldIn, random, chunkPos));
	}

}
