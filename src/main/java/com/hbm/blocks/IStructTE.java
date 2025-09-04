package com.hbm.blocks;

import net.minecraft.tileentity.TileEntity;

@FunctionalInterface
public interface IStructTE<T extends TileEntity> {
    T newInstance();

}
