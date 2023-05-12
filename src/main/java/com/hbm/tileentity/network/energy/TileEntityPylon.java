package com.hbm.tileentity.network.energy;

import java.util.ArrayList;
import java.util.List;

import com.hbm.lib.ForgeDirection;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.util.math.BlockPos;

public class TileEntityPylon extends TileEntityPylonBase {

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.SINGLE;
	}

	@Override
	public Vec3[] getMountPos() {
		return new Vec3[]{new Vec3(0.5D, 5.4D, 0.5D)};
	}

	@Override
	public int getMaxWireLength() {
		return 25;
	}
	
	@Override
	public List<BlockPos> getConnectionPoints() {
		List<BlockPos> positions = new ArrayList(connected);
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			positions.add(pos.add(dir.offsetX, dir.offsetY, dir.offsetZ));
		}
		return positions;
	}
}
