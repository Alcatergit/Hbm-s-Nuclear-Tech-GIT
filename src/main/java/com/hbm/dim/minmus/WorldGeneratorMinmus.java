package com.hbm.dim.minmus;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.BlockEnums.EnumStoneType;
import com.hbm.config.SpaceConfig;
import com.hbm.config.WorldConfig;
import com.hbm.dim.CelestialBody;
import com.hbm.world.generator.DungeonToolbox;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGeneratorMinmus implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.getDimension() == SpaceConfig.minmusDimension) {
			generateMinmus(world, random, chunkX * 16, chunkZ * 16);
		}
	}

	private void generateMinmus(World world, Random rand, int i, int j) {
		int meta = CelestialBody.getMeta(world);
        //DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.malachiteSpawn, 16, 6, 40, ModBlocks.stone_resource, EnumStoneType.MALACHITE.ordinal(), ModBlocks.minmus_stone);
        DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.copperSpawn * 3, 12, 8, 56, ModBlocks.ore_copper, ModBlocks.minmus_stone);
	}



}