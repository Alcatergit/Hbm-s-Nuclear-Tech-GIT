package com.hbm.oc;

import li.cil.oc.api.Network;
import li.cil.oc.api.driver.NamedBlock;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.AbstractManagedEnvironment;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class HBMDriver<TEntity extends TileEntity> extends AbstractManagedEnvironment implements NamedBlock {

    private Class<? extends TEntity> tileEntityClass;
    protected World world;
    private BlockPos pos;

    public HBMDriver(Class<? extends TEntity> tileEntityClass, World w, BlockPos pos) {
        this.tileEntityClass = tileEntityClass;
        this.world = w;
        this.pos = pos;
        setNode(Network.newNode(this, Visibility.Network).withComponent(preferredName(), Visibility.Network).create());
    }

    @SuppressWarnings("unchecked")
    public TEntity getTileEntity() {
        TileEntity entity = world.getTileEntity(pos);
        if (entity != null && tileEntityClass.isInstance(entity)) {
            return (TEntity) entity;
        }
        return null;
    }
}
