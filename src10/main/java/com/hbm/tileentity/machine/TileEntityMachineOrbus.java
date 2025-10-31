package com.hbm.tileentity.machine;

import com.hbm.forgefluid.FFUtils;
import com.hbm.blocks.BlockDummyable;
import com.hbm.lib.ForgeDirection;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidTank;
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

	public void fillFluid(BlockPos pos1, FluidTank tank) {
		FFUtils.fillFluid(this, tank, world, pos1, 64000);
	}

	@Override
	public void fillFluidInit(FluidTank type) {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
		ForgeDirection d2 = dir.getRotation(ForgeDirection.DOWN);

		for(int i = -1; i < 7; i += 7) {
			this.fillFluid(new BlockPos(pos.getX(), pos.getY() + i, pos.getZ()), this.tank);
			this.fillFluid(new BlockPos(pos.getX() + dir.offsetX, pos.getY() + i, pos.getZ() + dir.offsetZ), this.tank);
			this.fillFluid(new BlockPos(pos.getX() + d2.offsetX, pos.getY() + i, pos.getZ() + d2.offsetZ), this.tank);
			this.fillFluid(new BlockPos(pos.getX() + dir.offsetX + d2.offsetX, pos.getY() + i, pos.getZ() + dir.offsetZ + d2.offsetZ), this.tank);
		}
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