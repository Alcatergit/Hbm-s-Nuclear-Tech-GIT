package api.hbm.block;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public interface IEnterableBlock {
    boolean canItemEnter(World world, int x, int y, int z, EnumFacing dir, IConveyorItem entity);
    void onItemEnter(World world, int x, int y, int z, EnumFacing dir, IConveyorItem entity);

    boolean canPackageEnter(World world, int x, int y, int z, EnumFacing dir, IConveyorPackage entity);
    void onPackageEnter(World world, int x, int y, int z, EnumFacing dir, IConveyorPackage entity);
}
