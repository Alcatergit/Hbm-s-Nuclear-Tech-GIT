package com.hbm.blocks;

import com.hbm.interfaces.IFluidVisualConnectable;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class BlockBaseVisualFluidConnectable extends BlockBase implements IFluidVisualConnectable {

	private Fluid[] fluids;
	
	public BlockBaseVisualFluidConnectable(Material m, String s){
		super(m, s);
	}
	
	public BlockBaseVisualFluidConnectable addFluids(Fluid... fluids){
		this.fluids = fluids;
		return this;
	}

	@Override
	public boolean shouldConnect(Fluid f){
		if(fluids == null)
			return true;
		for(int i = 0; i < fluids.length; i ++){
			if(fluids[i] == f)
				return true;
		}
		return false;
	}

}
