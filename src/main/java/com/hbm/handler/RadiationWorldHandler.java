package com.hbm.handler;

import java.util.Collection;
import java.util.Map.Entry;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.handler.RadiationSystemNT.RadPocket;
import com.hbm.saveddata.RadiationSaveStructure;
import com.hbm.saveddata.RadiationSavedData;

import net.minecraft.init.Blocks;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.ChunkProviderServer;

public class RadiationWorldHandler {

	public static void handleWorldDestruction(World world) {

		//TODO fix this up for new radiation system
		if(!(world instanceof WorldServer))
			return;
		if(!RadiationConfig.worldRadEffects || !GeneralConfig.enableRads)
			return;

		int count = 50;//MainRegistry.worldRad;
		int threshold = 5;//MainRegistry.worldRadThreshold;
		
		if(GeneralConfig.advancedRadiation){
			Collection<RadPocket> activePockets = RadiationSystemNT.getActiveCollection(world);
			if(activePockets.size() == 0)
				return;
			int randIdx = world.rand.nextInt(activePockets.size());
			int itr = 0;
			for(RadPocket p : activePockets){
				if(itr == randIdx){
					if(p.radiation < threshold)
						return;
					BlockPos startPos = p.getSubChunkPos();
					RadPocket[] pocketsByBlock = p.parent.pocketsByBlock;
					for(int i = 0; i < 16; i ++){
						for(int j = 0; j < 16; j ++){
							for(int k = 0; k < 16; k ++){
								if(world.rand.nextInt(3) != 0)
									continue;
								if(pocketsByBlock != null && pocketsByBlock[i*16*16+j*16+k] != p){
									continue;
								}
								BlockPos pos = startPos.add(i, j, k);
								IBlockState b = world.getBlockState(pos);
								Block bblock = b.getBlock();

								if(!world.isAirBlock(pos)){
									if(bblock == Blocks.GRASS) {
										world.setBlockState(pos, ModBlocks.waste_earth.getDefaultState());
									
									} else if(bblock == Blocks.DIRT) {
										world.setBlockState(pos, ModBlocks.waste_dirt.getDefaultState());

									} else if(bblock == Blocks.SAND) {
										BlockSand.EnumType meta = b.getValue(BlockSand.VARIANT);
										if(world.rand.nextInt(60) == 0) {
											world.setBlockState(pos, meta == BlockSand.EnumType.SAND ? ModBlocks.waste_trinitite.getDefaultState() : ModBlocks.waste_trinitite_red.getDefaultState());
										} else {
											world.setBlockState(pos, meta == BlockSand.EnumType.SAND ? ModBlocks.waste_sand.getDefaultState() : ModBlocks.waste_sand_red.getDefaultState());
										}
									} else if(bblock == Blocks.GRAVEL) {
										world.setBlockState(pos, ModBlocks.waste_gravel.getDefaultState());

									} else if(bblock == Blocks.MYCELIUM) {
										world.setBlockState(pos, ModBlocks.waste_mycelium.getDefaultState());

									} else if(bblock == Blocks.SNOW_LAYER) {
										world.setBlockState(pos, ModBlocks.fallout.getDefaultState());

									} else if(bblock == Blocks.SNOW) {
										world.setBlockState(pos, ModBlocks.block_fallout.getDefaultState());

									} else if(bblock instanceof BlockBush) {
										world.setBlockState(pos, ModBlocks.waste_grass_tall.getDefaultState());
									
									} else if(bblock instanceof BlockLeaves) {
										world.setBlockToAir(pos);
										world.scheduleBlockUpdate(pos, world.getBlockState(pos).getBlock(), 0, 2);
									}
								}
							}
						}
					}
					break;
				}
				itr ++;
			}
			return;
		}
		
		WorldServer serv = (WorldServer)world;

		RadiationSavedData data = RadiationSavedData.getData(serv);
		ChunkProviderServer provider = (ChunkProviderServer) serv.getChunkProvider();

		Object[] entries = data.contamination.entrySet().toArray();

		if(entries.length == 0)
			return;

		Entry<ChunkPos, RadiationSaveStructure> randEnt = (Entry<ChunkPos, RadiationSaveStructure>) entries[world.rand.nextInt(entries.length)];

		ChunkPos coords = randEnt.getKey();


		if(randEnt == null || randEnt.getValue().radiation < threshold)
			return;

		if(provider.chunkExists(coords.x, coords.z)) {

			for(int a = 0; a < 16; a ++) {
				for(int b = 0; b < 16; b ++) {

					if(world.rand.nextInt(3) != 0)
						continue;

					int x = coords.getXStart() + a;
					int z = coords.getZStart() + b;
					int y = world.getHeight(x, z) - world.rand.nextInt(2);
					BlockPos pos = new BlockPos(x, y, z);
					IBlockState c = world.getBlockState(pos);
					Block bblock = c.getBlock();

					if(!world.isAirBlock(pos)){
						if(bblock == Blocks.GRASS) {
							world.setBlockState(pos, ModBlocks.waste_earth.getDefaultState());
						
						} else if(bblock == Blocks.DIRT) {
							world.setBlockState(pos, ModBlocks.waste_dirt.getDefaultState());

						} else if(bblock == Blocks.SAND) {
							BlockSand.EnumType meta = c.getValue(BlockSand.VARIANT);
							if(world.rand.nextInt(60) == 0) {
								world.setBlockState(pos, meta == BlockSand.EnumType.SAND ? ModBlocks.waste_trinitite.getDefaultState() : ModBlocks.waste_trinitite_red.getDefaultState());
							} else {
								world.setBlockState(pos, meta == BlockSand.EnumType.SAND ? ModBlocks.waste_sand.getDefaultState() : ModBlocks.waste_sand_red.getDefaultState());
							}
						} else if(bblock == Blocks.GRAVEL) {
							world.setBlockState(pos, ModBlocks.waste_gravel.getDefaultState());

						} else if(bblock == Blocks.MYCELIUM) {
							world.setBlockState(pos, ModBlocks.waste_mycelium.getDefaultState());

						} else if(bblock == Blocks.SNOW_LAYER) {
							world.setBlockState(pos, ModBlocks.fallout.getDefaultState());

						} else if(bblock == Blocks.SNOW) {
							world.setBlockState(pos, ModBlocks.block_fallout.getDefaultState());

						} else if(bblock instanceof BlockBush) {
							world.setBlockState(pos, ModBlocks.waste_grass_tall.getDefaultState());
						
						} else if(bblock instanceof BlockLeaves) {
							world.setBlockToAir(pos);
							world.scheduleBlockUpdate(pos, world.getBlockState(pos).getBlock(), 0, 2);
						}
					}
				}
			}
		}
	}
}
