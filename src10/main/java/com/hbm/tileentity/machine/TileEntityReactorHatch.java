package com.hbm.tileentity.machine;

import com.hbm.blocks.machine.ReactorHatch;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileEntityReactorHatch extends TileEntity implements IFluidHandler {

	@Override
	public IFluidTankProperties[] getTankProperties() {
		TileEntityMachineReactorLarge fillable = this.getReactorTE(world, pos);
		if(fillable != null)
			return fillable.getTankProperties();
		return new IFluidTankProperties[]{};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		TileEntityMachineReactorLarge fillable = this.getReactorTE(world, pos);
		if(fillable != null)
			return fillable.fill(resource, doFill);
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		TileEntityMachineReactorLarge fillable = this.getReactorTE(world, pos);
		if(fillable != null)
			return fillable.drain(resource, doDrain);
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		TileEntityMachineReactorLarge fillable = this.getReactorTE(world, pos);
		if(fillable != null)
			return fillable.drain(maxDrain, doDrain);
		return null;
	}
	
	private TileEntityMachineReactorLarge getReactorTE(World world, BlockPos pos) {
		EnumFacing e = world.getBlockState(pos).getValue(ReactorHatch.FACING);
		if(e == EnumFacing.NORTH)
		{
			TileEntity reactor = world.getTileEntity(pos.add(0, 0, 2));
			if(reactor instanceof TileEntityMachineReactorLarge)
			{
				if(((TileEntityMachineReactorLarge)reactor).checkBody())
				{
					return (TileEntityMachineReactorLarge)reactor;
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
		if(e == EnumFacing.SOUTH)
		{
			TileEntity reactor = world.getTileEntity(pos.add(0, 0, -2));
			if(reactor instanceof TileEntityMachineReactorLarge)
			{
				if(((TileEntityMachineReactorLarge)reactor).checkBody())
				{
					return (TileEntityMachineReactorLarge)reactor;
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
		if(e == EnumFacing.WEST)
		{
			TileEntity reactor = world.getTileEntity(pos.add(2, 0, 0));
			if(reactor instanceof TileEntityMachineReactorLarge)
			{
				if(((TileEntityMachineReactorLarge)reactor).checkBody())
				{
					return (TileEntityMachineReactorLarge)reactor;
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
		if(e == EnumFacing.EAST)
		{
			TileEntity reactor = world.getTileEntity(pos.add(-2, 0, 0));
			if(reactor instanceof TileEntityMachineReactorLarge)
			{
				if(((TileEntityMachineReactorLarge)reactor).checkBody())
				{
					return (TileEntityMachineReactorLarge)reactor;
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
