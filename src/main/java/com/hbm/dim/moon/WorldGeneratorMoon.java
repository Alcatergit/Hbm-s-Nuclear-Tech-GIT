package com.hbm.dim.moon;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.config.WorldConfig;
import com.hbm.dim.CelestialBody;
import com.hbm.world.generator.DungeonToolbox;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGeneratorMoon implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.getDimension() == SpaceConfig.moonDimension) {
			generateMoon(world, random, chunkX * 16, chunkZ * 16); 
		}
	}

	private void generateMoon(World world, Random rand, int i, int j) {
		int meta = CelestialBody.getMeta(world);

		//DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.nickelSpawn, 8, 1, 43, ModBlocks.ore_nickel, meta, ModBlocks.moon_rock);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.titaniumSpawn, 12, 4, 27, ModBlocks.ore_titanium, ModBlocks.moon_rock);
		//DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.lithiumSpawn,  6, 4, 8, ModBlocks.ore_lithium, ModBlocks.moon_rock);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.aluminiumSpawn,  6, 5, 40, ModBlocks.ore_aluminium, ModBlocks.moon_rock);
        DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.fluoriteSpawn, 4, 5, 45, ModBlocks.ore_fluorite, ModBlocks.moon_rock);
        //DungeonToolbox.generateOre(world, rand, i, j, 10, 13, 5, 64, ModBlocks.ore_quartz, meta, ModBlocks.moon_rock);

        //DungeonToolbox.generateOre(world, rand, i, j, 1, 12, 8, 32, ModBlocks.ore_shale, meta, ModBlocks.moon_rock);
	}
}