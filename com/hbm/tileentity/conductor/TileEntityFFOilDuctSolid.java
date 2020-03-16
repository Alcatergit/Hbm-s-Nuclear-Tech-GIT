package com.hbm.tileentity.conductor;

import com.hbm.forgefluid.ModForgeFluids;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;

public class TileEntityFFOilDuctSolid extends TileEntityFFDuctBase {

	public TileEntityFFOilDuctSolid() {
		thisIsATest = true;
		this.type = ModForgeFluids.oil;
	}

	@Override
	public void setType(Fluid fluid) {
		this.type = ModForgeFluids.oil;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		type = ModForgeFluids.oil;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		return super.writeToNBT(nbt);
	}
}
