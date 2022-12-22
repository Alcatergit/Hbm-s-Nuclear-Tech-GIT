package com.hbm.tileentity.machine;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.FluidTypeHandler;
import com.hbm.forgefluid.FluidTypeHandler.FluidTrait;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidTank;
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

	public void fillFluid(BlockPos pos1, FluidTank tank) {
		FFUtils.fillFluid(this, tank, world, pos1, 512000);
	}

	@Override
	public void fillFluidInit(FluidTank type) {
		fillFluid(new BlockPos(this.pos.getX() + 1, this.pos.getY(), this.pos.getZ() + 3), type);
		fillFluid(new BlockPos(this.pos.getX() - 1, this.pos.getY(), this.pos.getZ() + 3), type);
		fillFluid(new BlockPos(this.pos.getX() + 1, this.pos.getY(), this.pos.getZ() - 3), type);
		fillFluid(new BlockPos(this.pos.getX() - 1, this.pos.getY(), this.pos.getZ() - 3), type);
		fillFluid(new BlockPos(this.pos.getX() + 3, this.pos.getY(), this.pos.getZ() + 1), type);
		fillFluid(new BlockPos(this.pos.getX() - 3, this.pos.getY(), this.pos.getZ() + 1), type);
		fillFluid(new BlockPos(this.pos.getX() + 3, this.pos.getY(), this.pos.getZ() - 1), type);
		fillFluid(new BlockPos(this.pos.getX() - 3, this.pos.getY(), this.pos.getZ() - 1), type);
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