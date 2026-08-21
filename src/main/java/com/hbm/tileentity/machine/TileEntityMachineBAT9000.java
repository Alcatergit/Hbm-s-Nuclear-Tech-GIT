package com.hbm.tileentity.machine;

import com.hbm.forgefluid.FluidTypeHandler;
import com.hbm.forgefluid.FluidTypeHandler.FluidTrait;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMachineBAT9000 extends TileEntityBarrel {

	public TileEntityMachineBAT9000() {
		super(2048000);
	}
	
	@Override
	public String getName() {
		return "container.bat9000";
	}
	
	@Override
	public void checkFluidInteraction() {
		if(tank.getFluid() != null && FluidTypeHandler.containsTrait(tank.getFluid().getFluid(), FluidTrait.AMAT)) {
			world.destroyBlock(pos, false);
			world.newExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, true, true);
		}
	}

	@Override
	public BlockPos[] getConnectionPositions() {
		BlockPos[] ports = { pos };
		return new BlockPos[] {
				ports[0].east(5), ports[0].east(3).south(1), ports[0].east(3).north(1),
				ports[0].west(5), ports[0].west(3).south(1), ports[0].west(3).north(1),
				ports[0].south(5), ports[0].east(1).south(3), ports[0].west(1).south(3),
				ports[0].north(5), ports[0].east(1).north(3), ports[0].west(1).north(3),
		};
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = new AxisAlignedBB(
					pos.getX() - 2,
					pos.getY(),
					pos.getZ() - 2,
					pos.getX() + 3,
					pos.getY() + 5,
					pos.getZ() + 3
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
