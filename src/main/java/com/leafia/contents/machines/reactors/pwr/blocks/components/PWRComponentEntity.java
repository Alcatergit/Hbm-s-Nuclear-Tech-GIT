package com.leafia.contents.machines.reactors.pwr.blocks.components;

import com.leafia.contents.machines.reactors.pwr.PWRData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface PWRComponentEntity {
    @Nullable
    static PWRData getCoreFromPos(World world,@Nullable BlockPos corePos) {
        if (corePos != null) {
            if (world.getBlockState(corePos).getBlock() instanceof PWRComponentBlock) {
                if (((PWRComponentBlock) world.getBlockState(corePos).getBlock()).tileEntityShouldCreate(world,corePos)) {
                    TileEntity entity = world.getTileEntity(corePos);
                    if (entity != null) {
                        if (entity instanceof PWRComponentEntity) {
                            return ((PWRComponentEntity) entity).getCore();
                        }
                    }
                }
            }
        }
        return null;
    }
    void setCoreLink(@Nullable BlockPos pos);
    @Nullable
    PWRData getLinkedCore();
    void assignCore(@Nullable PWRData data);
    PWRData getCore();
    default void onDiagnosis() {};
}