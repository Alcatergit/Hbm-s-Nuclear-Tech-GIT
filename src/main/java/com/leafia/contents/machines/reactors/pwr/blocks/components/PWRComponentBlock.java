package com.leafia.contents.machines.reactors.pwr.blocks.components;

import com.leafia.contents.machines.reactors.pwr.PWRDiagnosis;
import com.leafia.dev.LeafiaDebug;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface PWRComponentBlock {
    default boolean shouldRenderOnGUI() {
        return false;
    }
    boolean tileEntityShouldCreate(World world,BlockPos pos);
    @Nullable
    default PWRComponentEntity getPWR(World world,BlockPos pos) {
        if (!tileEntityShouldCreate(world,pos)) return null;
        TileEntity entity = world.getTileEntity(pos);
        if (entity != null) {
            if (entity instanceof PWRComponentEntity) {
                return (PWRComponentEntity)entity;
            }
        }
        return null;
    };
    @Nullable
    default TileEntity getEntity(World world,BlockPos pos) {
        if (!tileEntityShouldCreate(world,pos)) return null;
        TileEntity entity = world.getTileEntity(pos);
        if (entity != null) {
            if (entity instanceof PWRComponentEntity) {
                return entity;
            }
        }
        return null;
    };
    default void beginDiagnosis(World world,BlockPos pos,BlockPos trigger) {
        if (world.isRemote) return;
        PWRDiagnosis.cleanup();
        if (PWRDiagnosis.preventScan.contains(pos)) {
            LeafiaDebug.debugLog(world,"Cancelled possible duplicate diagnosis");
            return;
        }
        PWRDiagnosis diagnosis = new PWRDiagnosis(world,trigger);
        diagnosis.addPosition(pos);
    }
}
