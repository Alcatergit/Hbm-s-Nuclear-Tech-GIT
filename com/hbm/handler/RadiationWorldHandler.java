package com.hbm.handler;

import java.util.Collection;
import java.util.Map.Entry;

import com.hbm.saveddata.RadiationSaveStructure;
import com.hbm.saveddata.RadiationSavedData;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;

public class RadiationWorldHandler {

	public static void handleWorldDestruction(World world) {

		if(!(world instanceof WorldServer))
			return;

		WorldServer serv = (WorldServer)world;

		RadiationSavedData data = RadiationSavedData.getData(serv);
		ChunkProviderServer provider = (ChunkProviderServer) serv.getChunkProvider();

		int count = 50;//MainRegistry.worldRad;
		int threshold = 5;//MainRegistry.worldRadThreshold;

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
							world.setBlockState(pos, Blocks.DIRT.getDefaultState());
						} else if(world.getBlockState(pos).getBlock() == Blocks.TALLGRASS) {
							world.setBlockState(pos, Blocks.AIR.getDefaultState());
						} else if(world.getBlockState(pos).getBlock() == Blocks.LEAVES) {
							world.setBlockState(pos, Blocks.AIR.getDefaultState());
						} else if(world.getBlockState(pos).getBlock() == Blocks.LEAVES2) {
							world.setBlockState(pos, Blocks.AIR.getDefaultState());
						}
					}
				}
			}
		}
	}
}
