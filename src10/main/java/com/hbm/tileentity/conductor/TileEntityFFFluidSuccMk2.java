package com.hbm.tileentity.conductor;

import com.hbm.interfaces.IFluidPipeMk2;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

//Drillgon200: Thank Bob for making me realize I could make a new tile entity for this and not be an idiot.
public class TileEntityFFFluidSuccMk2 extends TileEntityFFFluidDuctMk2 implements ITickable {

	@Override
	public void update() {
		if(world.isRemote || network == null || network.getType() == null)
			return;
		for(EnumFacing e : EnumFacing.VALUES){
			TileEntity te = world.getTileEntity(pos.offset(e));
			if(te != null && !(te instanceof IFluidPipeMk2) && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, e.getOpposite())){
				IFluidHandler toDrain = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, e.getOpposite());
				int maxNetFill = network.fill(new FluidStack(network.getType(), Integer.MAX_VALUE), false);
				network.fill(toDrain.drain(new FluidStack(network.getType(), maxNetFill), true), true);
			}
		}
	}

}
