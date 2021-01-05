package com.hbm.tileentity.machine;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.ITankPacketAcceptor;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntitySolarBoiler extends TileEntity implements ITickable, IFluidHandler, ITankPacketAcceptor {

	private FluidTank water;
	private FluidTank steam;
	public int heat;
	
	public TileEntitySolarBoiler() {
		water = new FluidTank(16000);
		steam = new FluidTank(1600000);
	}
	
	@Override
	public void update() {
		if(!world.isRemote) {

			//if(world.getTotalWorldTime() % 5 == 0) {
			fillFluidInit(steam);
			//}

			int process = heat / 10;
			process = Math.min(process, water.getFluidAmount());
			process = Math.min(process, (steam.getCapacity() - steam.getFluidAmount()) / 100);

			if(process < 0)
				process = 0;

			water.drain(process, true);
			steam.fill(new FluidStack(ModForgeFluids.steam, process*100), true);
			markDirty();

			heat = 0;
		}
	}
	
	public void fillFluidInit(FluidTank tank) {
		fillFluid(pos.up(3), tank);
		fillFluid(pos.down(), tank);
	}
	
	public void fillFluid(BlockPos pos, FluidTank tank) {
		FFUtils.fillFluid(this, tank, world, pos, 1600000);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? true : super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{water.getTankProperties()[0], steam.getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(resource != null && resource.getFluid() == FluidRegistry.WATER){
			int amount = water.getFluidAmount();
			int fill = water.fill(resource, doFill);
			if(amount != water.getFluidAmount())
				markDirty();
			return fill;
		}
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(resource != null && resource.getFluid() == ModForgeFluids.steam){
			int amount = steam.getFluidAmount();
			FluidStack stack = steam.drain(resource, doDrain);
			if(amount != steam.getFluidAmount())
				markDirty();
			return stack;
		}
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		int amount = steam.getFluidAmount();
		FluidStack stack = steam.drain(maxDrain, doDrain);
		if(amount != steam.getFluidAmount())
			markDirty();
		return stack;
	}

	@Override
	public void recievePacket(NBTTagCompound[] tags) {
		if(tags.length == 2){
			water.readFromNBT(tags[0]);
			steam.readFromNBT(tags[1]);
		}
	}
	
	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = new AxisAlignedBB(
					pos.getX() - 1,
					pos.getY(),
					pos.getZ() - 1,
					pos.getX() + 2,
					pos.getY() + 3,
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
