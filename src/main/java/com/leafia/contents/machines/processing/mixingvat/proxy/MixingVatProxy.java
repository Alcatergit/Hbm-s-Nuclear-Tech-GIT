package com.leafia.contents.machines.processing.mixingvat.proxy;

import api.hbm.energy.IEnergyUser;
import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyBase;
import com.leafia.contents.machines.processing.mixingvat.MixingVatTE;
import com.leafia.dev.LeafiaDebug.Tracker;
import com.leafia.dev.math.FiaMatrix;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import org.jetbrains.annotations.Nullable;

public class MixingVatProxy extends TileEntityProxyBase implements IEnergyUser, IFluidHandler {
	FiaMatrix getMatrix() {
		BlockDummyable dummyable = (BlockDummyable)getBlockType();
		BlockPos core = dummyable.findCore(world,pos);
		if (core == null) return null;
		FiaMatrix mat = dummyable.getRotationMat(world,core);
		return mat.add(new Vec3d(core).add(0.5,0.5,0.5));
	}
	FiaMatrix getDirection() {
		FiaMatrix mat = getMatrix();
		if (mat == null) return null;
		FiaMatrix relative = mat.toObjectSpace(new FiaMatrix(new Vec3d(pos).add(0.5,0.5,0.5)));
		if (relative.getZ() > -1)
			return mat.rotateY(180);
		else {
			if (relative.getX() > 0.5)
				return mat.rotateY(-90);
			else
				return mat.rotateY(90);
		}
	}
	EnumFacing getFacing() {
		FiaMatrix mat = getDirection();
		if (mat == null) return null;
		return EnumFacing.getFacingFromVector((float)mat.frontVector.x,(float)mat.frontVector.y,(float)mat.frontVector.z);
	}

	@Override
	public @Nullable <T> T getCapability(Capability<T> capability,@Nullable EnumFacing facing) {
		if (hasCapability(capability,facing)) {
			if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
				return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
			} else {
				MixingVatTE te = te();
				if (te != null)
					return te.getCapability(capability,null);
			}
		}
		return super.getCapability(capability,facing);
	}
	@Override
	public boolean hasCapability(Capability<?> capability,EnumFacing facing) {
		if(capability == CapabilityEnergy.ENERGY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return facing == null || facing.equals(getFacing());
		return super.hasCapability(capability, facing);
	}

	MixingVatTE te() {
		TileEntity te = getTE();
		if (te instanceof MixingVatTE vat)
			return vat;
		return null;
	}
	@Override
	public void setPower(long power) {
		MixingVatTE te = te();
		if (te != null)
			te.setPower(power);
	}

	@Override
	public long getPower() {
		MixingVatTE te = te();
		if (te != null)
			return te.getPower();
		return 0;
	}

	@Override
	public long getMaxPower() {
		MixingVatTE te = te();
		if (te != null)
			return te.getMaxPower();
		return 0;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		MixingVatTE te = te();
		if (te != null)
			return te.getTankProperties();
		return new IFluidTankProperties[0];
	}

	@Override
	public int fill(FluidStack resource,boolean doFill) {
		MixingVatTE te = te();
		if (te == null) return 0;

		FiaMatrix mat = getMatrix();
		if (mat == null) return 0;
		FiaMatrix relative = mat.toObjectSpace(new FiaMatrix(new Vec3d(pos).add(0.5,0.5,0.5)));
		if (relative.getZ() > -1)
			return te.fill(resource,doFill);
		return 0;
	}

	@Override
	public @Nullable FluidStack drain(FluidStack resource,boolean doDrain) {
		MixingVatTE te = te();
		if (te == null) return null;

		FiaMatrix mat = getMatrix();
		if (mat == null) return null;
		FiaMatrix relative = mat.toObjectSpace(new FiaMatrix(new Vec3d(pos).add(0.5,0.5,0.5)));
		if (relative.getZ() < -1)
			return te.drainOut(resource,doDrain);
		return null;
	}

	@Override
	public @Nullable FluidStack drain(int maxDrain,boolean doDrain) {
		MixingVatTE te = te();
		if (te == null) return null;

		FiaMatrix mat = getMatrix();
		if (mat == null) return null;
		FiaMatrix relative = mat.toObjectSpace(new FiaMatrix(new Vec3d(pos).add(0.5,0.5,0.5)));
		if (relative.getZ() < -1)
			return te.drainOut(maxDrain,doDrain);
		return null;
	}
}
