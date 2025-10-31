package com.leafia.contents.network.fluid.valves;

import com.hbm.tileentity.conductor.TileEntityFFDuctBaseMk2;

public class FluidBoxValveTE extends TileEntityFFDuctBaseMk2 {
	public boolean getState() { return this.getBlockMetadata() == 1; }
	@Override
	public boolean isValidForBuilding() {
		return super.isValidForBuilding() && getState();
	}
}
