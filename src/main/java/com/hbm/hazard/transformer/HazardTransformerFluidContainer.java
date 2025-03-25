package com.hbm.hazard.transformer;

import java.util.List;

import com.hbm.hazard.HazardEntry;
import com.hbm.hazard.HazardSystem;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class HazardTransformerFluidContainer extends HazardTransformerBase {

	@Override
	public void transformPre(ItemStack stack, List<HazardEntry> entries) { }

	@Override
	public void transformPost(ItemStack stack, List<HazardEntry> entries) {
		
		if(!stack.hasTagCompound()) return;
		FluidStack fluid = getFluid(stack);
		if(fluid == null || stack.getItem() == ModItems.fluid_tank_lead_full) return;
		List<HazardEntry> fluidProperties = HazardSystem.getHazardsFromFluid(fluid.getFluid().getName());
		for(HazardEntry e : fluidProperties) {
			entries.add(e.clone(fluid.amount/1000F));
		}
	}

	public static FluidStack getFluid(ItemStack stack) {
		if(stack != null && !stack.isEmpty()) {
			if(FluidUtil.getFluidContained(stack) != null)
				return FluidUtil.getFluidContained(stack);
		}
		return null;
	}
}
