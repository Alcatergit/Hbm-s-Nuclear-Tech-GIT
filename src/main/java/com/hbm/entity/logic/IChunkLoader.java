package com.hbm.entity.logic;

import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public interface IChunkLoader {

	void init(Ticket ticket);
	void loadNeighboringChunks(int newChunkX, int newChunkZ, int oldChunkX, int oldChunkZ);

    default void forceLoadChunk(Ticket loaderTicket, int chunkCoordX, int chunkCoordZ) {
        if(loaderTicket != null) ForgeChunkManager.forceChunk(loaderTicket, new ChunkPos(chunkCoordX, chunkCoordZ));
    }

    default void unForceLoadChunk(Ticket loaderTicket, int chunkCoordX, int chunkCoordZ) {
        if(loaderTicket != null) ForgeChunkManager.unforceChunk(loaderTicket, new ChunkPos(chunkCoordX, chunkCoordZ));
    }

    default void unForceLoadAllChunks(Ticket loaderTicket) {
        if(loaderTicket != null){
            for(ChunkPos chunk : loaderTicket.getChunkList()) {
                ForgeChunkManager.unforceChunk(loaderTicket, chunk);
            }
        }
    }
}
