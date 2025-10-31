package com.leafia.contents.machines.reactors.pwr.blocks.components.vent.outlet;

import net.minecraft.tileentity.TileEntity;

public class PWRVentOutletTE extends TileEntity {
	//TODO: Make it accept any fluids using identifier
	// Cooling rate would be difference between rodHeat and fluidTemperature
	// The vent will then search for random position in front of it and query the block type
	// If the block is solid, then the cooling rate will be multiplied by 0
	// If liquid, multiplied by 0.5 and then subtract rate by the temperature of said liquid

	// The higher the cooled rate is, the hotter the particle will be

	// Liquids like pyrotheum would heat it instead according to the formula,
	// and also having hot liquids like pyrotheum in front of the vent would make the cooling less effective or even end up heating it instead according to the formula

	// cooling rate would be (rodHeat-fluidTemperature)*-1;
}