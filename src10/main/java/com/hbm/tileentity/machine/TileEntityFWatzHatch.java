package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityLoadedBase;

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

public class TileEntityFWatzHatch extends TileEntityLoadedBase implements IFluidHandler {

	@Override
	public IFluidTankProperties[] getTankProperties() {
		TileEntityFWatzCore fillable = this.getReactorTE(world, pos);
		if(fillable != null)
			return fillable.getTankProperties();
		return new IFluidTankProperties[]{};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		TileEntityFWatzCore fillable = this.getReactorTE(world, pos);
		if(fillable != null)
			return fillable.fill(resource, doFill);
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		TileEntityFWatzCore fillable = this.getReactorTE(world, pos);
		if(fillable != null)
			return fillable.drain(resource, doDrain);
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		TileEntityFWatzCore fillable = this.getReactorTE(world, pos);
		if(fillable != null)
			return fillable.drain(maxDrain, doDrain);
		return null;
	}
	
	private TileEntityFWatzCore getReactorTE(World world, BlockPos pos) {
		EnumFacing e = world.getBlockState(pos).getValue(BlockHorizontal.FACING);
		if(e == EnumFacing.NORTH) {
			TileEntity te = world.getTileEntity(pos.add(0, 1, 6));
			if(te instanceof TileEntityFWatzCore) {
				if(((TileEntityFWatzCore)te).isStructureValid(world)) {
					return (TileEntityFWatzCore)te;
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
		if(e == EnumFacing.SOUTH) {
			TileEntity te = world.getTileEntity(pos.add(0, 1, -6));
			if(te instanceof TileEntityFWatzCore) {
				if(((TileEntityFWatzCore)te).isStructureValid(world)) {
					return (TileEntityFWatzCore)te;
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
		if(e == EnumFacing.WEST) {
			TileEntity te = world.getTileEntity(pos.add(6, 1, 0));
			if(te instanceof TileEntityFWatzCore) {
				if(((TileEntityFWatzCore)te).isStructureValid(world)) {
					return (TileEntityFWatzCore)te;
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
		if(e == EnumFacing.EAST) {
			TileEntity te = world.getTileEntity(pos.add(-6, 1, 0));
			if(te instanceof TileEntityFWatzCore) {
				if(((TileEntityFWatzCore)te).isStructureValid(world)) {
					return (TileEntityFWatzCore)te;
				} else {
					return null;
				}
			}
		}
		return null;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this) : super.getCapability(capability, facing);
	}
}
