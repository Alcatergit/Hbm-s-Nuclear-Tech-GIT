package com.hbm.tileentity.machine;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMachineOrbus extends TileEntityBarrel {

	public TileEntityMachineOrbus() {
		super(512000);
	}
	
	@Override
	public String getName() {
		return "container.orbus";
	}
	
	@Override
	public void checkFluidInteraction() { } //NO!

	@Override
	public BlockPos[] getConnectionPositions() {
		BlockPos[] ports = { pos, pos.add(1, 0, 0), pos.add(0, 0, 1), pos.add(1, 0, 1) };
		return new BlockPos[] {
				ports[0].down(), ports[1].down(), ports[2].down(), ports[3].down(),
				ports[0].up(5), ports[1].up(5), ports[2].up(5), ports[3].up(5),
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
					pos.getX() + 2,
					pos.getY() + 5,
					pos.getZ() + 2
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
