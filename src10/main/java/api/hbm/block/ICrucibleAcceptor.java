package api.hbm.block;

import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.lib.ForgeDirection;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;

public interface ICrucibleAcceptor {

	/*
	 * Pouring: The metal leaves the channel/crucible and usually (but not always) falls down. The additional double coords give a more precise impact location.
	 * Also useful for entities like large crucibles since they are filled from the top.
	 */
	//public boolean canAcceptPour(World world, BlockPos pos, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack);
    boolean canAcceptPartialPour(World world, BlockPos pos, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack);
	MaterialStack pour(World world, BlockPos pos, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack);

	/*
	 * Flowing: The "safe" transfer of metal using a channel or other means, usually from block to block and usually horizontally (but not necessarily).
	 * May also be used for entities like minecarts that could be loaded from the side.
	 */
	//public boolean canAcceptFlow(World world, BlockPos pos, ForgeDirection side, MaterialStack stack);
    boolean canAcceptPartialFlow(World world, BlockPos pos, ForgeDirection side, MaterialStack stack);
	MaterialStack flow(World world, BlockPos pos, ForgeDirection side, MaterialStack stack);
}
