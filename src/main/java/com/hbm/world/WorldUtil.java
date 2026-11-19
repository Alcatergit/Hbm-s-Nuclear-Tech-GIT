package com.hbm.world;

import com.hbm.packet.BiomeSyncPacket;
import com.hbm.packet.PacketDispatcher;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class WorldUtil {

	/** Writes a single biome column into the chunk's biome array (vanilla byte[256]). */
	public static void setBiome(World world, int blockX, int blockZ, Biome biome) {
		if (world == null || biome == null) return;
		final int chunkX = blockX >> 4;
		final int chunkZ = blockZ >> 4;
		final Chunk chunk = world.getChunk(chunkX, chunkZ);
		final byte[] arr = chunk.getBiomeArray();
		if (arr.length != 256) return;
		final int idx = ((blockZ & 15) << 4) | (blockX & 15);
		arr[idx] = (byte) (Biome.getIdForBiome(biome) & 0xFF);
		chunk.markDirty();
	}

	/** Sends the chunk's biome array to all nearby clients. */
	public static void syncBiomeChange(World world, int chunkX, int chunkZ) {
		final Chunk chunk = world.getChunk(chunkX, chunkZ);
		PacketDispatcher.wrapper.sendToAllAround(
				new BiomeSyncPacket(chunkX, chunkZ, chunk.getBiomeArray()),
				new TargetPoint(world.provider.getDimension(), chunkX << 4, 128, chunkZ << 4, 1024D));
	}
}