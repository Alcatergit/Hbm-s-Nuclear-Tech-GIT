package com.hbm.entity.logic;

import com.hbm.main.MainRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

import java.util.ArrayList;
import java.util.List;

public class EntityChunky extends Entity implements IChunkLoader {

    private ForgeChunkManager.Ticket loaderTicket;
    private List<ChunkPos> loadedChunks = new ArrayList<ChunkPos>();

    public EntityChunky(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
	}

    @Override
    protected void entityInit() {
        init(ForgeChunkManager.requestTicket(MainRegistry.instance, world, ForgeChunkManager.Type.ENTITY));
    }

    @Override
    public void init(ForgeChunkManager.Ticket ticket) {
        if(!world.isRemote && ticket != null) {
            if(this.loaderTicket != ticket) ForgeChunkManager.releaseTicket(this.loaderTicket);
            this.loaderTicket = ticket;
            this.loaderTicket.setChunkListDepth(7);
            this.loaderTicket.bindEntity(this);
            forceLoadChunk(loaderTicket, chunkCoordX, chunkCoordZ);
        }
    }

    @Override
    public void loadNeighboringChunks(int newChunkX, int newChunkZ, int oldChunkX, int oldChunkZ) {
        if(!world.isRemote && this.loaderTicket != null && (newChunkX != oldChunkX || newChunkZ != oldChunkZ)) {

            loadedChunks.clear();
            loadedChunks.add(new ChunkPos(newChunkX, newChunkZ));
            if(this.motionX != 0 && this.motionZ != 0) {
                int offsetX = this.motionX > 0 ? 1 : -1;
                int offsetZ = this.motionZ > 0 ? 1 : -1;

                loadedChunks.add(new ChunkPos(newChunkX + offsetX, newChunkZ));
                loadedChunks.add(new ChunkPos(newChunkX, newChunkZ + offsetZ));
                loadedChunks.add(new ChunkPos(newChunkX + offsetX, newChunkZ + offsetZ));
            }
            for(ChunkPos chunk : this.loadedChunks) {
                ForgeChunkManager.forceChunk(this.loaderTicket, chunk);
            }
        }
    }

    @Override
    public void setDead() {
        super.setDead();
        ForgeChunkManager.releaseTicket(this.loaderTicket);
    }
}
