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
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
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
								if(world.getBlockState(pos).getBlock() == Blocks.GRASS) {
									world.setBlockState(pos, ModBlocks.waste_earth.getDefaultState());
								} else if(world.getBlockState(pos).getBlock() == Blocks.DIRT) {
									world.setBlockState(pos, ModBlocks.waste_dirt.getDefaultState());
								} else if(world.getBlockState(pos).getBlock() instanceof BlockBush) {
									world.setBlockState(pos, ModBlocks.waste_grass_tall.getDefaultState());
								} else if(world.getBlockState(pos).getBlock() instanceof BlockLeaves) {
									world.setBlockState(pos, Blocks.AIR.getDefaultState());
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

		for(int i = 0; i < 1; i++) {


			if(randEnt == null || randEnt.getValue().radiation < threshold)
				continue;

			if(provider.chunkExists(coords.x, coords.z)) {

				for(int a = 0; a < 16; a ++) {
					for(int b = 0; b < 16; b ++) {

						if(world.rand.nextInt(3) != 0)
							continue;

						int x = coords.getXStart() + a;
						int z = coords.getZStart() + b;
						int y = world.getHeight(x, z) - world.rand.nextInt(2);
						BlockPos pos = new BlockPos(x, y, z);

						if(world.getBlockState(pos).getBlock() == Blocks.GRASS) {
							world.setBlockState(pos, ModBlocks.waste_earth.getDefaultState());
						} else if(world.getBlockState(pos).getBlock() == Blocks.DIRT) {
							world.setBlockState(pos, ModBlocks.waste_dirt.getDefaultState());
						} else if(world.getBlockState(pos).getBlock() instanceof BlockBush) {
							world.setBlockState(pos, ModBlocks.waste_grass_tall.getDefaultState());
						} else if(world.getBlockState(pos).getBlock() instanceof BlockLeaves) {
							world.setBlockState(pos, Blocks.AIR.getDefaultState());
						}
					}
				}
			}
		}
	}
}
