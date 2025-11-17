package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityLoadedBase;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileEntityFWatzHatch extends TileEntityLoadedBase implements IFluidHandler {

    TileEntityFWatzCore fwatz;

    @Override
	public IFluidTankProperties[] getTankProperties() {
		TileEntityFWatzCore fillable = this.getReactorTE(world, pos);
		if(fillable != null && fillable.isOk)
			return fillable.getTankProperties();
		return new IFluidTankProperties[]{};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		TileEntityFWatzCore fillable = this.getReactorTE(world, pos);
		if(fillable != null && fillable.isOk)
			return fillable.fill(resource, doFill);
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		TileEntityFWatzCore fillable = this.getReactorTE(world, pos);
		if(fillable != null && fillable.isOk)
			return fillable.drain(resource, doDrain);
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		TileEntityFWatzCore fillable = this.getReactorTE(world, pos);
		if(fillable != null && fillable.isOk)
			return fillable.drain(maxDrain, doDrain);
		return null;
	}
	
	private TileEntityFWatzCore getReactorTE(World world, BlockPos pos) {
        if(fwatz != null && fwatz.isOk){
            return fwatz;
        }
		EnumFacing e = world.getBlockState(pos).getValue(BlockHorizontal.FACING);
        TileEntity te = world.getTileEntity(pos.add(e.getXOffset()*-6, -1, e.getZOffset()*-6));
        if(te instanceof TileEntityFWatzCore core) {
            if(core.isOk) {
                fwatz = core;
                return core;
            } else {
                return null;
            }
        }
        te = world.getTileEntity(pos.add(e.getXOffset()*-6, 3, e.getZOffset()*-6));
        if(te instanceof TileEntityFWatzCore core) {
            if(core.isOk) {
                fwatz = core;
                return core;
            } else {
                return null;
            }
        }
        return null;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        TileEntityFWatzCore core = this.getReactorTE(world, pos);
        if(core != null && core.isOk)
            return core.hasCapability(capability, facing);
        return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        TileEntityFWatzCore core = this.getReactorTE(world, pos);
        if(core != null && core.isOk)
            return core.getCapability(capability, facing);
        return super.getCapability(capability, facing);
	}
}
