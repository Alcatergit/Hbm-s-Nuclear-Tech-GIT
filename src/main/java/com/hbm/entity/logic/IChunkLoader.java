package com.hbm.entity.logic;

import net.minecraftforge.common.ForgeChunkManager.Ticket;

public interface IChunkLoader {

	void init(Ticket ticket);
	void loadNeighboringChunks(int newChunkX, int newChunkZ);
}
