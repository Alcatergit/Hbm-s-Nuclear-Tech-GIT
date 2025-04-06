package api.hbm.block;

import com.hbm.inventory.material.Mats;
import com.hbm.lib.ForgeDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ICrucibleAcceptor {

    /*
     * Pouring: The metal leaves the channel/crucible and usually (but not always) falls down. The additional double coords give a more precise impact location.
     * Also useful for entities like large crucibles since they are filled from the top.
     */
    //public boolean canAcceptPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack);
    public boolean canAcceptPartialPour(World world, BlockPos pos, double dX, double dY, double dZ, ForgeDirection side, Mats.MaterialStack stack);
    public Mats.MaterialStack pour(World world, BlockPos pos, double dX, double dY, double dZ, ForgeDirection side, Mats.MaterialStack stack);

    /*
     * Flowing: The "safe" transfer of metal using a channel or other means, usually from block to block and usually horizontally (but not necessarily).
     * May also be used for entities like minecarts that could be loaded from the side.
     */
    //public boolean canAcceptFlow(World world, BlockPos pos, ForgeDirection side, MaterialStack stack);
    public boolean canAcceptPartialFlow(World world, BlockPos pos, ForgeDirection side, Mats.MaterialStack stack);
    public Mats.MaterialStack flow(World world, BlockPos pos, ForgeDirection side, Mats.MaterialStack stack);
}
