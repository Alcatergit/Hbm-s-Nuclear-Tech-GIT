package com.hbm.tileentity.machine;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileEntityFusionHatch extends TileEntity implements IFluidHandler {

	@Override
	public IFluidTankProperties[] getTankProperties() {
		TileEntityFusionMultiblock fillable = this.getReactorTE(world, pos);
		if(fillable != null)
			return fillable.getTankProperties();
		return new IFluidTankProperties[]{};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		TileEntityFusionMultiblock fillable = this.getReactorTE(world, pos);
		if(fillable != null)
			return fillable.fill(resource, doFill);
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		TileEntityFusionMultiblock fillable = this.getReactorTE(world, pos);
		if(fillable != null)
			return fillable.drain(resource, doDrain);
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		TileEntityFusionMultiblock fillable = this.getReactorTE(world, pos);
		if(fillable != null)
			return fillable.drain(maxDrain, doDrain);
		return null;
	}
	
	private TileEntityFusionMultiblock getReactorTE(World world, BlockPos pos) {
		EnumFacing e = world.getBlockState(pos).getValue(BlockHorizontal.FACING);
		if(e == EnumFacing.NORTH)
		{
			if(world.getTileEntity(pos.add(0, 0, 8)) != null && world.getTileEntity(pos.add(0, 0, 8)) instanceof TileEntityFusionMultiblock)
			{
				if(((TileEntityFusionMultiblock)world.getTileEntity(pos.add(0, 0, 8))).isStructureValid(world))
				{
					return (TileEntityFusionMultiblock)world.getTileEntity(pos.add(0, 0, 8));
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
		if(e == EnumFacing.SOUTH)
		{
			if(world.getTileEntity(pos.add(0, 0, -8)) != null && world.getTileEntity(pos.add(0, 0, -8)) instanceof TileEntityFusionMultiblock)
			{
				if(((TileEntityFusionMultiblock)world.getTileEntity(pos.add(0, 0, -8))).isStructureValid(world))
				{
					return (TileEntityFusionMultiblock)world.getTileEntity(pos.add(0, 0, -8));
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
		if(e == EnumFacing.WEST)
		{
			if(world.getTileEntity(pos.add(8, 0, 0)) != null && world.getTileEntity(pos.add(8, 0, 0)) instanceof TileEntityFusionMultiblock)
			{
				if(((TileEntityFusionMultiblock)world.getTileEntity(pos.add(8, 0, 0))).isStructureValid(world))
				{
					return (TileEntityFusionMultiblock)world.getTileEntity(pos.add(8, 0, 0));
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
		if(e == EnumFacing.EAST)
		{
			if(world.getTileEntity(pos.add(-8, 0, 0)) != null && world.getTileEntity(pos.add(-8, 0, 0)) instanceof TileEntityFusionMultiblock)
			{
				if(((TileEntityFusionMultiblock)world.getTileEntity(pos.add(-8, 0, 0))).isStructureValid(world))
				{
					return (TileEntityFusionMultiblock)world.getTileEntity(pos.add(-8, 0, 0));
				} else {
					return null;
				}
			}
		}
		return null;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this) : super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

}
