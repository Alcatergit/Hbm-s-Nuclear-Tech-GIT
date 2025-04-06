package com.hbm.dim.moho;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockVolcano;
import com.hbm.config.SpaceConfig;
import com.hbm.config.WorldConfig;
import com.hbm.dim.CelestialBody;
import com.hbm.world.generator.DungeonToolbox;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGeneratorMoho implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.getDimension() == SpaceConfig.mohoDimension) {
			generateMoho(world, random, chunkX * 16, chunkZ * 16);
		}
	}

	private void generateMoho(World world, Random rand, int i, int j) {
		int meta = CelestialBody.getMeta(world);
		//DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.mineralSpawn, 10, 12, 32, ModBlocks.ore_mineral, meta, ModBlocks.moho_stone);

		//DungeonToolbox.generateOre(world, rand, i, j, 14, 12, 5, 30, ModBlocks.ore_glowstone, meta, ModBlocks.moho_stone);
		//DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.netherPhosphorusSpawn, 6, 8, 64, ModBlocks.ore_fire, meta, ModBlocks.moho_stone);
		DungeonToolbox.generateOre(world, rand, i, j, 8, 4, 0, 24, ModBlocks.ore_australium, ModBlocks.moho_stone);

		//DungeonToolbox.generateOre(world, rand, i, j, 1, 12, 8, 32, ModBlocks.ore_shale, meta, ModBlocks.moho_stone);

		DungeonToolbox.generateOre(world, rand, i, j, 10, 32, 0, 128, ModBlocks.basalt, ModBlocks.moho_stone);
		
		// More basalt ores!
		//DungeonToolbox.generateOre(world, rand, i, j, 16, 6, 16, 64, ModBlocks.ore_basalt, 0, ModBlocks.basalt);
		//DungeonToolbox.generateOre(world, rand, i, j, 12, 8, 8, 32, ModBlocks.ore_basalt, 1, ModBlocks.basalt);
		//DungeonToolbox.generateOre(world, rand, i, j, 8, 9, 8, 48, ModBlocks.ore_basalt, 2, ModBlocks.basalt);
		//DungeonToolbox.generateOre(world, rand, i, j, 2, 4, 0, 24, ModBlocks.ore_basalt, 3, ModBlocks.basalt);
		//DungeonToolbox.generateOre(world, rand, i, j, 8, 10, 16, 64, ModBlocks.ore_basalt, 4, ModBlocks.basalt);

		for(int k = 0; k < 2; k++){
			int x = i + rand.nextInt(16);
			int z = j + rand.nextInt(16);
			int d = 16 + rand.nextInt(96);

			for(int y = d - 5; y <= d; y++) {
				BlockPos pos = new BlockPos(x, y, z);
				Block b = world.getBlockState(pos).getBlock();
				if(world.getBlockState(pos.up()).getBlock() == Blocks.AIR && (b == ModBlocks.moho_stone || b == ModBlocks.moho_regolith)) {
					world.setBlockState(pos, ModBlocks.geysir_nether.getDefaultState());
					world.setBlockState(pos.add(1, 0, 0), Blocks.NETHERRACK.getDefaultState());
					world.setBlockState(pos.add(-1, 0, 0), Blocks.NETHERRACK.getDefaultState());
					world.setBlockState(pos.add(0, 0, 1), Blocks.NETHERRACK.getDefaultState());
					world.setBlockState(pos.add(0, 0, -1), Blocks.NETHERRACK.getDefaultState());
					world.setBlockState(pos.add(1, -1, 0), Blocks.NETHERRACK.getDefaultState());
					world.setBlockState(pos.add(-1, -1, 0), Blocks.NETHERRACK.getDefaultState());
					world.setBlockState(pos.add(0, -1, 1), Blocks.NETHERRACK.getDefaultState());
					world.setBlockState(pos.add(0, -1, -1), Blocks.NETHERRACK.getDefaultState());
				}
			}
		}

		// Kick the volcanoes into action, and fix SOME floating lava
		// a full fix for floating lava would cause infinite cascades so we uh, don't
		for(int x = 0; x < 16; x++) {
			for(int z = 0; z < 16; z++) {
				for(int y = 32; y < 128; y++) {
					int ox = i + x;
					int oz = j + z;
					BlockPos oPos = new BlockPos(ox, y, oz);
					Block b = world.getBlockState(oPos).getBlock();

					if(b == Blocks.LAVA && world.getBlockState(oPos.down()).getBlock() == Blocks.AIR) {
						world.setBlockState(oPos.down(), Blocks.FLOWING_LAVA.getDefaultState(), 0);
						world.notifyBlockUpdate(oPos.down(), world.getBlockState(oPos.down()), Blocks.FLOWING_LAVA.getDefaultState(), 3);
					} else if(b == ModBlocks.volcano_core) {
						world.setBlockState(oPos, ModBlocks.volcano_core.getStateFromMeta(BlockVolcano.META_STATIC_EXTINGUISHING), 0);
						world.notifyBlockUpdate(oPos, world.getBlockState(oPos), ModBlocks.volcano_core.getDefaultState(), 3);
					}
				}
			}
		}
	}

}