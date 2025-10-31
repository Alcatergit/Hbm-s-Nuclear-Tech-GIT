package com.custom;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nullable;

public class TypedFluidTank {
	public Fluid type;
	public final FluidTank tank;

	public TypedFluidTank(Fluid type, FluidTank tank) {
		this.type = type;
		this.tank = tank;
	}

	public void setType(@Nullable Fluid type) {
		if(type == null) {
			this.tank.setFluid(null);
		}

		if(this.type == type) {
			return;
		}

		this.type = type;
		this.tank.setFluid(null);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if(this.type != null) {
			nbt.setString("type", this.type.getName());
		}

		this.tank.writeToNBT(nbt);

		return nbt;
	}

	public void readFromNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("type")) {
			this.type = FluidRegistry.getFluid(nbt.getString("type"));
		}
		this.tank.readFromNBT(nbt);
	}

	public FluidTank getTank() {
		return tank;
	}

	public Fluid getType() {
		return type;
	}
}