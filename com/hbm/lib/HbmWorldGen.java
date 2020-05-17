package com.hbm.lib;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockStorageCrate;
import com.hbm.blocks.machine.PinkCloudBroadcaster;
import com.hbm.handler.WeightedRandomChestContentFrom1710;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntitySafe;
import com.hbm.world.Antenna;
import com.hbm.world.Barrel;
import com.hbm.world.Bunker;
import com.hbm.world.CrashedVertibird;
import com.hbm.world.DesertAtom001;
import com.hbm.world.Dud;
import com.hbm.world.Factory;
import com.hbm.world.Geyser;
import com.hbm.world.GeyserLarge;
import com.hbm.world.LibraryDungeon;
import com.hbm.world.OilBubble;
import com.hbm.world.OilSandBubble;
import com.hbm.world.Radio01;
import com.hbm.world.Relay;
import com.hbm.world.Satellite;
import com.hbm.world.Sellafield;
import com.hbm.world.Silo;
import com.hbm.world.Spaceship;
import com.hbm.world.Vertibird;
import com.hbm.world.generator.CellularDungeonFactory;

import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
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

		if(MainRegistry.enableDungeons) {
			//Drillgon200: Helps with cascading world gen.
			i += 8;
			j += 8;
			Biome biome = world.getBiome(new BlockPos(i, 0, j));
			
			if (biome == Biomes.PLAINS || biome == Biomes.DESERT) {
				if (rand.nextInt(MainRegistry.radioStructure) == 0) {
					for (int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeight(x, z);

						new Radio01().generate(world, rand, new BlockPos(x, y, z));
					}
				}
			}
			if (biome == Biomes.PLAINS || biome == Biomes.FOREST || biome == Biomes.DESERT
					|| biome == Biomes.SWAMPLAND || biome == Biomes.EXTREME_HILLS) {
				if (rand.nextInt(MainRegistry.antennaStructure) == 0) {
					for (int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeight(x, z);

						new Antenna().generate(world, rand, new BlockPos(x, y, z));
					}
				}
			}
			if (biome == Biomes.DESERT || biome == Biomes.BEACH || biome == Biomes.MESA
					|| biome == Biomes.MESA_CLEAR_ROCK || biome == Biomes.MESA_ROCK) {
				if (rand.nextInt(MainRegistry.atomStructure) == 0) {
					for (int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeight(x, z);

						new DesertAtom001().generate(world, rand, new BlockPos(x, y, z));
					}
				}
			}
			if (rand.nextInt(MainRegistry.bunkerStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeight(x, z);

				new Bunker().generate(world, rand, new BlockPos(x, y, z));
			}
			if (biome == Biomes.PLAINS || biome == Biomes.DESERT) {
				if (rand.nextInt(MainRegistry.relayStructure) == 0) {
					for (int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeight(x, z);

						new Relay().generate(world, rand, new BlockPos(x, y, z));
					}
				}
			}
			if (rand.nextInt(MainRegistry.siloStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeight(x, z);

				new Silo().generate(world, rand, new BlockPos(x, y, z));
			}
			if (rand.nextInt(MainRegistry.factoryStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeight(x, z);

				new Factory().generate(world, rand, new BlockPos(x, y, z));
			}
			if (rand.nextInt(MainRegistry.dudStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeight(x, z);

				new Dud().generate(world, rand, new BlockPos(x, y, z));
			}
			if (biome == Biomes.DESERT && rand.nextInt(MainRegistry.barrelStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeight(x, z);

				new Barrel().generate(world, rand, new BlockPos(x, y, z));
			}
			if (biome == Biomes.DESERT) {
				if (rand.nextInt(MainRegistry.vertibirdStructure) == 0) {
					for (int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeight(x, z);

						if (rand.nextInt(2) == 0) {
							new Vertibird().generate(world, rand, new BlockPos(x, y, z));
						} else {
							new CrashedVertibird().generate(world, rand, new BlockPos(x, y, z));
						}

					}
				}
			}
			if (biome == Biomes.PLAINS || biome == Biomes.DESERT) {
				if (rand.nextInt(MainRegistry.satelliteStructure) == 0) {
					for (int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeight(x, z);

						new Satellite().generate(world, rand, new BlockPos(x, y, z));
					}
				}
			}
			if (rand.nextInt(MainRegistry.spaceshipStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeight(x, z);

				new Spaceship().generate(world, rand, new BlockPos(x, y, z));
			}
			
			if (MainRegistry.enableRad && rand.nextInt(MainRegistry.radfreq) == 0 && biome == Biomes.DESERT) {

				for (int a = 0; a < 1; a++) {
					int x = i + rand.nextInt(16);
					int z = j + rand.nextInt(16);

					double r = rand.nextInt(15) + 10;

					if (rand.nextInt(50) == 0)
						r = 50;

					new Sellafield().generate(world, x, z, r, r * 0.35D);

					if (MainRegistry.enableDebugMode)
						MainRegistry.logger.info("[Debug] Successfully spawned raditation hotspot at " + x + " " + z);
				}
			}
			
			if (MainRegistry.enableMines && rand.nextInt(MainRegistry.minefreq) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeight(x, z);

				if (world.getBlockState(new BlockPos(x, y, z)).isSideSolid(world, new BlockPos(x, y, z), EnumFacing.UP)) {
					world.setBlockState(new BlockPos(x, y + 1, z), ModBlocks.mine_ap.getDefaultState());

					if (MainRegistry.enableDebugMode)
						MainRegistry.logger.info("[Debug] Successfully spawned landmine at " + x + " " + (y + 1) + " " + z);
				}
			}
			if (rand.nextInt(MainRegistry.broadcaster) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeight(x, z);

				if (world.getBlockState(new BlockPos(x, y, z)).isSideSolid(world, new BlockPos(x, y, z), EnumFacing.UP))
					world.setBlockState(new BlockPos(x, y + 1, z), ModBlocks.broadcaster_pc.getDefaultState().withProperty(PinkCloudBroadcaster.FACING, EnumFacing.getFront(rand.nextInt(4) + 2)), 2);

				if (MainRegistry.enableDebugMode)
					MainRegistry.logger.info("[Debug] Successfully spawned corrupted broadcaster at " + x + " " + (y + 1) + " " + z);
			}
			if (rand.nextInt(MainRegistry.dungeonStructure) == 0) {
				int x = i + rand.nextInt(16);
				int y = rand.nextInt(256);
				int z = j + rand.nextInt(16);
				new LibraryDungeon().generate(world, rand, new BlockPos(x, y, z));
			}
			
			if (biome == Biomes.PLAINS && rand.nextInt(MainRegistry.geyserWater) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeight(x, z);

				if (world.getBlockState(new BlockPos(x, y - 1, z)).getBlock() == Blocks.GRASS)
					new Geyser().generate(world, rand, new BlockPos(x, y, z));
			}
			if (biome == Biomes.DESERT && rand.nextInt(MainRegistry.geyserChlorine) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeight(x, z);

				if (world.getBlockState(new BlockPos(x, y - 1, z)).getBlock() == Blocks.SAND)
					new GeyserLarge().generate(world, rand, new BlockPos(x, y, z));
			}
			if (rand.nextInt(MainRegistry.geyserVapor) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeight(x, z);

				if (world.getBlockState(new BlockPos(x, y - 1, z)).getBlock() == Blocks.STONE)
					world.setBlockState(new BlockPos(x, y - 1, z), ModBlocks.geysir_vapor.getDefaultState());
			}
			if (rand.nextInt(1000) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				
				for(int k = 0; k < 256; k++) {
					IBlockState state = world.getBlockState(new BlockPos(x, k, z));
					if(state.getBlock() == Blocks.LOG && state.getValue(BlockOldLog.VARIANT) == BlockPlanks.EnumType.OAK)
						world.setBlockState(new BlockPos(x, k, z), ModBlocks.pink_log.getDefaultState());
				}
			}
			if (MainRegistry.enableVaults && rand.nextInt(MainRegistry.vaultfreq) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeight(x, z);

				if (world.getBlockState(new BlockPos(x, y, z)).isSideSolid(world, new BlockPos(x, y, z), EnumFacing.UP)) {
					world.setBlockState(new BlockPos(x, y + 1, z), ModBlocks.safe.getDefaultState().withProperty(BlockStorageCrate.FACING, EnumFacing.getFront(rand.nextInt(4) + 2)), 2);

					switch (rand.nextInt(10)) {
					case 0:
					case 1:
					case 2:
					case 3:
						((TileEntitySafe) world.getTileEntity(new BlockPos(x, y + 1, z))).setPins(rand.nextInt(999) + 1);
						((TileEntitySafe) world.getTileEntity(new BlockPos(x, y + 1, z))).setMod(1);
						((TileEntitySafe) world.getTileEntity(new BlockPos(x, y + 1, z))).lock();
						WeightedRandomChestContentFrom1710.generateChestContents(rand, HbmChestContents.getLoot(10),
								(TileEntitySafe) world.getTileEntity(new BlockPos(x, y + 1, z)), rand.nextInt(4) + 3);
						break;
					case 4:
					case 5:
					case 6:
						((TileEntitySafe) world.getTileEntity(new BlockPos(x, y + 1, z))).setPins(rand.nextInt(999) + 1);
						((TileEntitySafe) world.getTileEntity(new BlockPos(x, y + 1, z))).setMod(0.1);
						((TileEntitySafe) world.getTileEntity(new BlockPos(x, y + 1, z))).lock();
						WeightedRandomChestContentFrom1710.generateChestContents(rand, HbmChestContents.getLoot(11),
								(TileEntitySafe) world.getTileEntity(new BlockPos(x, y + 1, z)), rand.nextInt(3) + 2);
						break;
					case 7:
					case 8:
						((TileEntitySafe) world.getTileEntity(new BlockPos(x, y + 1, z))).setPins(rand.nextInt(999) + 1);
						((TileEntitySafe) world.getTileEntity(new BlockPos(x, y + 1, z))).setMod(0.02);
						((TileEntitySafe) world.getTileEntity(new BlockPos(x, y + 1, z))).lock();
						WeightedRandomChestContentFrom1710.generateChestContents(rand, HbmChestContents.getLoot(12),
								(TileEntitySafe) world.getTileEntity(new BlockPos(x, y + 1, z)), rand.nextInt(3) + 1);
						break;
					case 9:
						((TileEntitySafe) world.getTileEntity(new BlockPos(x, y + 1, z))).setPins(rand.nextInt(999) + 1);
						((TileEntitySafe) world.getTileEntity(new BlockPos(x, y + 1, z))).setMod(0.0);
						((TileEntitySafe) world.getTileEntity(new BlockPos(x, y + 1, z))).lock();
						WeightedRandomChestContentFrom1710.generateChestContents(rand, HbmChestContents.getLoot(13),
								(TileEntitySafe) world.getTileEntity(new BlockPos(x, y + 1, z)), rand.nextInt(2) + 1);
						break;
					}

					if (MainRegistry.enableDebugMode)
						MainRegistry.logger.info("[Debug] Successfully spawned safe at " + x + " " + (y + 1) + " " + z);
				}
				
				if (rand.nextInt(MainRegistry.meteorStructure) == 0) {
					int x1 = i + rand.nextInt(16);
					int z1 = j + rand.nextInt(16);
					
					CellularDungeonFactory.test.generate(world, x, 10, z, rand);
					
					if(MainRegistry.enableDebugMode)
						MainRegistry.logger.info("[Debug] Successfully spawned meteor dungeon at " + x + " 10 " + z);
					
					int y1 = world.getHeight(x, z);
					
					for(int f = 1; f < 4; f++)
						world.setBlockState(new BlockPos(x1, y1 + f, z1), ModBlocks.meteor_pillar.getDefaultState());
					world.setBlockState(new BlockPos(x1, y1 + 4, z1), ModBlocks.meteor_brick_chiseled.getDefaultState());
					
					for(int f = 0; f < 10; f++) {

						x1 = i + rand.nextInt(65) - 32;
						z1 = j + rand.nextInt(65) - 32;
						y1 = world.getHeight(x1, z1);
						
						if(world.getBlockState(new BlockPos(x1, y1, z1)).isSideSolid(world, new BlockPos(x1, y1, z1), EnumFacing.UP)) {
							world.setBlockState(new BlockPos(x1, y1 + 1, z1), Blocks.SKULL.getDefaultState().withProperty(BlockSkull.FACING, EnumFacing.UP), 2);
							TileEntitySkull skull = (TileEntitySkull)world.getTileEntity(new BlockPos(x1, y1 + 1, z1));
							
							if(skull != null)
								skull.setType(rand.nextInt(16));
						}
					}
				}

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
		
		if(rand.nextInt(25) == 0) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(25);
			int randPosZ = j + rand.nextInt(16);

			OilBubble.spawnOil(world, randPosX, randPosY, randPosZ, 7 + rand.nextInt(9));
		}
		if (MainRegistry.enableNITAN) {

			if (i <= 10000 && i + 16 >= 10000 && j <= 10000 && j + 16 >= 10000) {
				if (world.getBlockState(new BlockPos(10000, 250, 10000)).getBlock() == Blocks.AIR) {
					world.setBlockState(new BlockPos(10000, 250, 10000), Blocks.CHEST.getDefaultState());
					if (world.getBlockState(new BlockPos(10000, 250, 10000)).getBlock() == Blocks.CHEST) {
						WeightedRandomChestContentFrom1710.generateChestContents(rand, HbmChestContents.getLoot(9),
								(TileEntityChest) world.getTileEntity(new BlockPos(10000, 250, 10000)), 29);
					}
				}
			}
			if (i <= 0 && i + 16 >= 0 && j <= 10000 && j + 16 >= 10000) {
				if (world.getBlockState(new BlockPos(0, 250, 10000)).getBlock() == Blocks.AIR) {
					world.setBlockState(new BlockPos(0, 250, 10000), Blocks.CHEST.getDefaultState());
					if (world.getBlockState(new BlockPos(0, 250, 10000)).getBlock() == Blocks.CHEST) {
						WeightedRandomChestContentFrom1710.generateChestContents(rand, HbmChestContents.getLoot(9),
								(TileEntityChest) world.getTileEntity(new BlockPos(0, 250, 10000)), 29);
					}
				}
			}
			if (i <= -10000 && i + 16 >= -10000 && j <= 10000 && j + 16 >= 10000) {
				if (world.getBlockState(new BlockPos(-10000, 250, 10000)).getBlock() == Blocks.AIR) {
					world.setBlockState(new BlockPos(-10000, 250, 10000), Blocks.CHEST.getDefaultState());
					if (world.getBlockState(new BlockPos(-10000, 250, 10000)).getBlock() == Blocks.CHEST) {
						WeightedRandomChestContentFrom1710.generateChestContents(rand, HbmChestContents.getLoot(9),
								(TileEntityChest) world.getTileEntity(new BlockPos(-10000, 250, 10000)), 29);
					}
				}
			}
			if (i <= 10000 && i + 16 >= 10000 && j <= 0 && j + 16 >= 0) {
				if (world.getBlockState(new BlockPos(10000, 250, 0)).getBlock() == Blocks.AIR) {
					world.setBlockState(new BlockPos(10000, 250, 0), Blocks.CHEST.getDefaultState());
					if (world.getBlockState(new BlockPos(10000, 250, 0)).getBlock() == Blocks.CHEST) {
						WeightedRandomChestContentFrom1710.generateChestContents(rand, HbmChestContents.getLoot(9),
								(TileEntityChest) world.getTileEntity(new BlockPos(10000, 250, 0)), 29);
					}
				}
			}
			if (i <= -10000 && i + 16 >= -10000 && j <= 0 && j + 16 >= 0) {
				if (world.getBlockState(new BlockPos(-10000, 250, 0)).getBlock() == Blocks.AIR) {
					world.setBlockState(new BlockPos(-10000, 250, 0), Blocks.CHEST.getDefaultState());
					if (world.getBlockState(new BlockPos(-10000, 250, 0)).getBlock() == Blocks.CHEST) {
						WeightedRandomChestContentFrom1710.generateChestContents(rand, HbmChestContents.getLoot(9),
								(TileEntityChest) world.getTileEntity(new BlockPos(-10000, 250, 0)), 29);
					}
				}
			}
			if (i <= 10000 && i + 16 >= 10000 && j <= -10000 && j + 16 >= -10000) {
				if (world.getBlockState(new BlockPos(10000, 250, -10000)).getBlock() == Blocks.AIR) {
					world.setBlockState(new BlockPos(10000, 250, -10000), Blocks.CHEST.getDefaultState());
					if (world.getBlockState(new BlockPos(10000, 250, -10000)).getBlock() == Blocks.CHEST) {
						WeightedRandomChestContentFrom1710.generateChestContents(rand, HbmChestContents.getLoot(9),
								(TileEntityChest) world.getTileEntity(new BlockPos(10000, 250, -10000)), 29);
					}
				}
			}
			if (i <= 0 && i + 16 >= 0 && j <= -10000 && j + 16 >= -10000) {
				if (world.getBlockState(new BlockPos(0, 250, -10000)).getBlock() == Blocks.AIR) {
					world.setBlockState(new BlockPos(0, 250, -10000), Blocks.CHEST.getDefaultState());
					if (world.getBlockState(new BlockPos(0, 250, -10000)).getBlock() == Blocks.CHEST) {
						WeightedRandomChestContentFrom1710.generateChestContents(rand, HbmChestContents.getLoot(9),
								(TileEntityChest) world.getTileEntity(new BlockPos(0, 250, -10000)), 29);
					}
				}
			}
			if (i <= -10000 && i + 16 >= -10000 && j <= -10000 && j + 16 >= -10000) {
				if (world.getBlockState(new BlockPos(-10000, 250, -10000)).getBlock() == Blocks.AIR) {
					world.setBlockState(new BlockPos(-10000, 250, -10000), Blocks.CHEST.getDefaultState());
					if (world.getBlockState(new BlockPos(-10000, 250, -10000)).getBlock() == Blocks.CHEST) {
						WeightedRandomChestContentFrom1710.generateChestContents(rand, HbmChestContents.getLoot(9),
								(TileEntityChest) world.getTileEntity(new BlockPos(-10000, 250, -10000)), 29);
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
