package com.hbm.tileentity.machine;

import com.hbm.forgefluid.FluidTypeHandler;
import com.hbm.forgefluid.FluidTypeHandler.FluidTrait;
import com.hbm.tileentity.TileEntityProxyCombo;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

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
		List<BlockPos> positions = new ArrayList<>();
		for(int x = -3; x <= 3; x++) {
			for(int z = -3; z <= 3; z++) {
				if(x == 0 && z == 0) continue;
				if(Math.abs(x) + Math.abs(z) != 3) continue;
				BlockPos checkPos = pos.add(x, 0, z);
				if(world.getTileEntity(checkPos) instanceof TileEntityProxyCombo) {
					if(Math.abs(x) > Math.abs(z)) {
						positions.add(checkPos.add(Integer.compare(x, 0), 0, 0));
					} else {
						positions.add(checkPos.add(0, 0, Integer.compare(z, 0)));
					}
				}
			}
		}
		return positions.toArray(new BlockPos[0]);
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
