package com.leafia.contents.machines.reactors.msr.components.plug;

import com.hbm.blocks.ModBlocks;
import com.hbm.forgefluid.ModForgeFluids;
import com.leafia.contents.machines.reactors.msr.components.MSRTEBase;
import com.leafia.contents.machines.reactors.msr.components.ejector.MSREjectorBlock;
import com.leafia.dev.LeafiaDebug;
import com.leafia.dev.LeafiaDebug.Tracker;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MSRPlugTE extends MSRTEBase implements IFluidHandler {
	public boolean molten = false;
	EnumFacing getDirection() {
		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() instanceof MSRPlugBlock)
			return state.getValue(BlockHorizontal.FACING);
		return EnumFacing.NORTH;
	}
	public MSRPlugTE() {
		tank = new FluidTank(10000);
	}
	@Override
	public <T> T getCapability(Capability<T> capability,EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && (facing == null || facing.equals(getDirection()) || facing.equals(EnumFacing.DOWN))) || super.hasCapability(capability, facing);
	}
	@Override
	public IFluidTankProperties[] getTankProperties() {
		return tank.getTankProperties();
	}
	@Override
	public int fill(FluidStack resource,boolean doFill) {
		if (resource.getFluid().equals(ModForgeFluids.FLUORIDE)) {
			if (doFill) {
				if (tank.getFluid() != null) {
					transferStats(resource,tank.getFluid(),1);
					return tank.fill(new FluidStack(tank.getFluid(),resource.amount),doFill);
				} else
					return tank.fill(resource,doFill);
			} else {
				if (tank.getFluid() != null)
					return tank.fill(new FluidStack(tank.getFluid(),resource.amount),false);
				else
					return tank.fill(resource,false);
			}
		} else
			return 0;
	}
	@Override
	public @Nullable FluidStack drain(FluidStack resource,boolean doDrain) {
		return null;
	}
	@Override
	public @Nullable FluidStack drain(int maxDrain,boolean doDrain) {
		return null;
	}
	@Override
	public String getPacketIdentifier() {
		return "MSRPlug";
	}
	/*@Override
	public void sendFluids() {
		Tracker._startProfile(this,"sendFluids");
		int demand = 0;
		List<MSRTEBase> list = new ArrayList<>();
		for (EnumFacing facing : EnumFacing.values()) {
			BlockPos target = pos.add(facing.getDirectionVec());
			if (world.getTileEntity(target) instanceof MSRTEBase te && !(te instanceof MSRPlugTE)) {
				demand += te.tank.getCapacity()-te.tank.getFluidAmount();
				list.add(te);
			}
		}
		demand = Math.min(demand,tank.getFluidAmount());
		if (!list.isEmpty()) {
			demand /= list.size();
			if (demand > 0) {
				for (MSRTEBase te : list) {
					//Tracker._tracePosition(this,te.pos,"+"+demand+"mB");
					transferStats(tank.getFluid(),te.tank.getFluid(),list.size());
					assert tank.getFluid() != null;
					tank.drain(te.tank.fill(new FluidStack(te.tank.getFluid() == null ? tank.getFluid() : te.tank.getFluid(),demand),true),true);
				}
			}
		}
		Tracker._endProfile(this);
	}*/
	@Override
	public void update() {
		if (!world.isRemote) {
			sendFluids();
			if (tank.getFluid() != null) {
				if (nbtProtocol(tank.getFluid().tag).getDouble("heat") > 4000-baseTemperature)
					molten = true;
				Material mat = world.getBlockState(pos.down()).getMaterial();
				if (molten && mat.isReplaceable() && !mat.isLiquid()) {
					this.world.playSound(null,pos,SoundEvents.ENTITY_GENERIC_SPLASH,SoundCategory.BLOCKS,3.0F,0.5F);
					world.setBlockState(pos.down(),ModBlocks.fluoride_block.getDefaultState());
				}
				if (world.getBlockState(pos.down()).getBlock() == ModBlocks.fluoride_block)
					tank.drain(1000,true);
			}
			LeafiaDebug.debugPos(world,pos,0.05f,0xFFFF00,tank.getFluidAmount()+"mB");
			generateTankPacket().__write(0,molten).__sendToAffectedClients();
		}
	}

	@Override
	public void onReceivePacketLocal(byte key,Object value) {
		super.onReceivePacketLocal(key,value);
		if (key == 0)
			molten = (boolean)value;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		molten = compound.getBoolean("molten");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("molten",molten);
		return super.writeToNBT(compound);
	}
}
