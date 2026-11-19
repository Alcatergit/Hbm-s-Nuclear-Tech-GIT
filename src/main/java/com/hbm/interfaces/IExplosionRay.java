package com.hbm.interfaces;

import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

/**
 * Interface for procedural explosions.
 */
public interface IExplosionRay {
    /**
     * Called every tick.
     * All heavy calculations are recommended to be done off the main thread.
     *
     * @param processTimeMs maximum time to process in this tick
     */
    void update(int processTimeMs);

    /**
     * Immediately cancels the explosion.
     */
    void cancel();

    /**
     * @return true if the explosion is finished or canceled.
     */
    boolean isComplete();

    default boolean hasFailed() {
        return false;
    }

    /**
     * @return true if all rays stop within the maximum radius.
     */
    boolean isContained();

    void setDetonator(UUID detonator);

    void readEntityFromNBT(NBTTagCompound nbt);

    void writeEntityToNBT(NBTTagCompound nbt);
}