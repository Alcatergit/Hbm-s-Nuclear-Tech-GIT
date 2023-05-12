package api.hbm.energy;

import com.hbm.lib.ForgeDirection;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IEnergyGenerator extends IEnergyUser {

	/**
	 * Standard implementation for machines that can only send energy but never receive it.
	 * @param power
	 */
	@Override
	public default long transferPower(long power) {
		return power;
	}

	/* should stop making non-receivers from interfering by applying their weight which doesn't even matter */
	@Override
	public default long getTransferWeight() {
		return 0;
	}

	public default void sendPower(World world, BlockPos pos){
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			this.sendPower(world, pos.add(dir.offsetX, dir.offsetY, dir.offsetZ), dir);
	}
}
