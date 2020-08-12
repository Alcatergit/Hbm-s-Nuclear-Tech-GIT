package com.hbm.tileentity;

import com.hbm.interfaces.IConsumer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityProxyCombo extends TileEntityProxyBase implements IConsumer {

	TileEntity tile;
	boolean inventory;
	boolean power;
	boolean fluid;
	
	public TileEntityProxyCombo() {
	}
	
	public TileEntityProxyCombo(boolean inventory, boolean power, boolean fluid) {
		this.inventory = inventory;
		this.power = power;
		this.fluid = fluid;
	}

	// fewer messy recursive operations
	public TileEntity getTile() {

		if(tile == null) {
			tile = this.getTE();
		}

		return tile;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(tile == null) {
			tile = this.getTE();
		}
		if(inventory && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			return tile.getCapability(capability, facing);
		}
		if(power && capability == CapabilityEnergy.ENERGY){
			return tile.getCapability(capability, facing);
		}
		if(fluid && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return tile.getCapability(capability, facing);
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(tile == null) {
			tile = this.getTE();
		}
		if(inventory && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			return tile.hasCapability(capability, facing);
		}
		if(power && capability == CapabilityEnergy.ENERGY){
			return tile.hasCapability(capability, facing);
		}
		if(fluid && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return tile.hasCapability(capability, facing);
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public void setPower(long i) {

		if(!power)
			return;

		if(getTile() instanceof IConsumer) {
			((IConsumer)getTile()).setPower(i);
		}
	}

	@Override
	public long getPower() {

		if(!power)
			return 0;

		if(getTile() instanceof IConsumer) {
			return ((IConsumer)getTile()).getPower();
		}

		return 0;
	}

	@Override
	public long getMaxPower() {

		if(!power)
			return 0;

		if(getTile() instanceof IConsumer) {
			return ((IConsumer)getTile()).getMaxPower();
		}

		return 0;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		inventory = compound.getBoolean("inv");
		fluid = compound.getBoolean("flu");
		power = compound.getBoolean("pow");
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("inv", inventory);
		compound.setBoolean("flu", fluid);
		compound.setBoolean("pow", power);
		return super.writeToNBT(compound);
	}
}
