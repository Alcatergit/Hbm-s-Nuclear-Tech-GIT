package com.hbm.lib;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.world.OilBubble;
import com.hbm.world.OilSandBubble;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class HbmWorldGen implements IWorldGenerator {

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.getDimension()) {
		case -1:
			generateNether(world, rand, chunkX * 16, chunkZ * 16);
			break;
		case 0:
			generateSurface(world, rand, chunkX * 16, chunkZ * 16);
			break;
		case 1:
			generateEnd(world, rand, chunkX * 16, chunkZ * 16);
			break;
		default:
			if(MainRegistry.enableMDOres)
				generateSurface(world, rand, chunkX * 16, chunkZ * 16);
			break;
		}
	}

	private void generateSurface(World world, Random rand, int i, int j) {
		for(int k = 0; k < MainRegistry.uraniumSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			// Max height of generation
			int randPosY = rand.nextInt(25);
			int randPosZ = j + rand.nextInt(16);

			// Ore, amount of ore in one stain
			(new WorldGenMinable(ModBlocks.ore_uranium.getDefaultState(), 5)).generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}
		for(int k = 0; k < MainRegistry.thoriumSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			// Max height of generation
			int randPosY = rand.nextInt(30);
			int randPosZ = j + rand.nextInt(16);

			// Ore, amount of ore in one stain
			(new WorldGenMinable(ModBlocks.ore_thorium.getDefaultState(), 5)).generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}

		for(int k = 0; k < MainRegistry.titaniumSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(35);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_titanium.getDefaultState(), 6)).generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}

		for(int k = 0; k < MainRegistry.sulfurSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(35);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_sulfur.getDefaultState(), 8)).generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}

		for(int k = 0; k < MainRegistry.aluminiumSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(45);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_aluminium.getDefaultState(), 6)).generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}

		for(int k = 0; k < MainRegistry.copperSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(50);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_copper.getDefaultState(), 6)).generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}

		for(int k = 0; k < MainRegistry.fluoriteSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(40);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_fluorite.getDefaultState(), 4)).generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}

		for(int k = 0; k < MainRegistry.niterSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(35);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_niter.getDefaultState(), 6)).generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}

		for(int k = 0; k < MainRegistry.tungstenSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(35);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_tungsten.getDefaultState(), 8)).generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}

		for(int k = 0; k < MainRegistry.leadSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(35);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_lead.getDefaultState(), 9)).generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}

		for(int k = 0; k < MainRegistry.berylliumSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(35);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_beryllium.getDefaultState(), 4)).generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}

		// Drillgon200: These are to be removed in the 1.12.2 release.
		/*for (int k = 0; k < 6; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(35);
			int randPosZ = j + rand.nextInt(16);
		
			if (randPosX <= 50 && randPosX >= -50 && randPosZ <= 50 && randPosZ >= -50)
				(new WorldGenMinable(ModBlocks.ore_reiium, 12)).generate(world, rand, randPosX, randPosY, randPosZ);
		}
		
		for (int k = 0; k < 80; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(128);
			int randPosZ = j + rand.nextInt(16);
		
			if (randPosX <= 250 && randPosX >= 150 && randPosZ <= 250 && randPosZ >= 150)
				(new WorldGenMinable(ModBlocks.ore_unobtainium, 4)).generate(world, rand, randPosX, randPosY, randPosZ);
		}
		
		for (int k = 0; k < rand.nextInt(4); k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(15) + 15;
			int randPosZ = j + rand.nextInt(16);
		
			if (randPosX <= -350 && randPosX >= -450 && randPosZ <= -350 && randPosZ >= -450)
				(new WorldGenMinable(ModBlocks.ore_australium, 50)).generate(world, rand, randPosX, randPosY, randPosZ);
		}
		
		for (int k = 0; k < 12; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(25);
			int randPosZ = j + rand.nextInt(16);
		
			if (randPosX <= 50 && randPosX >= -50 && randPosZ <= 350 && randPosZ >= 250)
				(new WorldGenMinable(ModBlocks.ore_weidanium, 6)).generate(world, rand, randPosX, randPosY, randPosZ);
		}
		
		for (int k = 0; k < 24; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(10);
			int randPosZ = j + rand.nextInt(16);
		
			if (randPosX <= 450 && randPosX >= 350 && randPosZ <= -150 && randPosZ >= -250)
				(new WorldGenMinable(ModBlocks.ore_daffergon, 16)).generate(world, rand, randPosX, randPosY, randPosZ);
		}
		
		for (int k = 0; k < 12; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(25) + 25;
			int randPosZ = j + rand.nextInt(16);
		
			if (randPosX <= -250 && randPosX >= -350 && randPosZ <= 250 && randPosZ >= 150)
				(new WorldGenMinable(ModBlocks.ore_verticium, 16)).generate(world, rand, randPosX, randPosY, randPosZ);
		}*/

		for(int k = 0; k < MainRegistry.niterSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(25);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_rare.getDefaultState(), 5)).generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}

		for(int k = 0; k < MainRegistry.ligniteSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(25) + 35;
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_lignite.getDefaultState(), 24)).generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}

		// TODO Structures.
		if(MainRegistry.enableDungeons) {
			Biome biome = world.getBiome(new BlockPos(i, 0, j));

			if(rand.nextInt(25) == 0) {
				int randPosX = i + rand.nextInt(16);
				int randPosY = rand.nextInt(25);
				int randPosZ = j + rand.nextInt(16);

				OilBubble.spawnOil(world, randPosX, randPosY, randPosZ, 7 + rand.nextInt(9));
			}
			if(biome == Biomes.DESERT) {
				if(rand.nextInt(200) == 0) {
					for(int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeight(x, z);

						OilSandBubble.spawnOil(world, x, y, z, 15 + rand.nextInt(31));
					}
				}
			}
		}
	}

	private void generateNether(World world, Random rand, int i, int j) {
		for(int k = 0; k < 8; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(127);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_nether_uranium.getDefaultState(), 6, BlockMatcher.forBlock(Blocks.NETHERRACK))).generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}
		if(MainRegistry.enablePlutoniumOre) {
			for(int k = 0; k < 6; k++) {
				int randPosX = i + rand.nextInt(16);
				int randPosY = rand.nextInt(127);
				int randPosZ = j + rand.nextInt(16);

				(new WorldGenMinable(ModBlocks.ore_nether_plutonium.getDefaultState(), 4, BlockMatcher.forBlock(Blocks.NETHERRACK))).generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
			}
		}
		for(int k = 0; k < 10; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(127);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_nether_tungsten.getDefaultState(), 10, BlockMatcher.forBlock(Blocks.NETHERRACK))).generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}
		for(int k = 0; k < 26; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(127);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_nether_sulfur.getDefaultState(), 12, BlockMatcher.forBlock(Blocks.NETHERRACK))).generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}
		for(int k = 0; k < 24; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(127);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_nether_fire.getDefaultState(), 3, BlockMatcher.forBlock(Blocks.NETHERRACK))).generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}
	}

	private void generateEnd(World world, Random rand, int i, int j) {
		for(int k = 0; k < 8; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(127);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_tikite.getDefaultState(), 6, BlockMatcher.forBlock(Blocks.END_STONE))).generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}
	}

}
