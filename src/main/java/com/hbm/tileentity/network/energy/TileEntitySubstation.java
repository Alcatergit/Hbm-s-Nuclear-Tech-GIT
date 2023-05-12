package com.hbm.tileentity.network.energy;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.render.amlfrom1710.Vec3;

import api.hbm.energy.IEnergyConductor;
import net.minecraft.util.math.BlockPos;

public class TileEntitySubstation extends TileEntityPylonBase {

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.QUAD;
	}

	@Override
	public Vec3[] getMountPos() {
		
		double topOff = 5.25D;
		Vec3 vec = Vec3.createVectorHelper(1, 0, 0);

		switch(getBlockMetadata() - BlockDummyable.offset) {
		case 2: vec.rotateAroundY((float) Math.PI * 0.0F); break;
		case 4: vec.rotateAroundY((float) Math.PI * 0.5F); break;
		case 3: vec.rotateAroundY((float) Math.PI * 0.0F); break;
		case 5: vec.rotateAroundY((float) Math.PI * 0.5F); break;
		}
		
		return new Vec3[] {
				new Vec3(0.5D + vec.xCoord * 0.5D, topOff, 0.5D + vec.zCoord * 0.5D),
				new Vec3(0.5D + vec.xCoord * 1.5D, topOff, 0.5D + vec.zCoord * 1.5D),
				new Vec3(0.5D - vec.xCoord * 0.5D, topOff, 0.5D - vec.zCoord * 0.5D),
				new Vec3(0.5D - vec.xCoord * 1.5D, topOff, 0.5D - vec.zCoord * 1.5D),
		};
	}

	@Override
	public BlockPos getConnectionPoint() {
		return new BlockPos(pos.add(0.5, 5.25, 0.5));
	}

	@Override
	public int getMaxWireLength() {
		return 20;
	}
	
	@Override
	public List<BlockPos> getConnectionPoints() {
		List<BlockPos> connPos = new ArrayList(connected);
		connPos.add(pos.add(2, 0, -1));
		connPos.add(pos.add(2, 0, 1));
		connPos.add(pos.add(- 2, 0, -1));
		connPos.add(pos.add(- 2, 0, 1));
		connPos.add(pos.add(- 1, 0, 2));
		connPos.add(pos.add(1, 0, 2));
		connPos.add(pos.add(- 1, 0, -2));
		connPos.add(pos.add(1, 0, -2));
		return connPos;
	}

	@Override
	public boolean hasProxies() {
		return true;
	}

	@Override
	public List<Integer> getProxies() {
		List<Integer> proxies = new ArrayList();
		proxies.add(IEnergyConductor.getIdentityFromPos(pos.add(1, 0, 1)));
		proxies.add(IEnergyConductor.getIdentityFromPos(pos.add(1, 0, -1)));
		proxies.add(IEnergyConductor.getIdentityFromPos(pos.add(-1, 0, 1)));
		proxies.add(IEnergyConductor.getIdentityFromPos(pos.add(-1, 0, -1)));
		return proxies;
	}
}
