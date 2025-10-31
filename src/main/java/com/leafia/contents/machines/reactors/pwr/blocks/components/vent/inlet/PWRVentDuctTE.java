package com.leafia.contents.machines.reactors.pwr.blocks.components.vent.inlet;

import com.hbm.tileentity.conductor.TileEntityFFDuctBaseMk2;
import net.minecraftforge.fluids.Fluid;

public class PWRVentDuctTE extends TileEntityFFDuctBaseMk2 {
	public boolean listenersShouldUpdate = true;
	@Override
	public void setType(Fluid f) {
		super.setType(f);
		listenersShouldUpdate = true;
	}
}