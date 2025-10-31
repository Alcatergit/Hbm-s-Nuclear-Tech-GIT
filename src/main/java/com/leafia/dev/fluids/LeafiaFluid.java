package com.leafia.dev.fluids;

import com.hbm.forgefluid.ModFluidProperties.FluidProperties;
import com.hbm.render.misc.EnumSymbol;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LeafiaFluid extends Fluid {
	public static final FluidProperties DEFAULT_TRAITS = new FluidProperties(0,0,0,EnumSymbol.NONE);
	static final Map<String,FluidProperties> traits = new HashMap<>();
	@Nullable public static LeafiaFluid cast(FluidStack stack) {
		if (stack.getFluid() instanceof LeafiaFluid) return (LeafiaFluid)stack.getFluid();
		else return null;
	}
	public LeafiaFluid(String fluidName,ResourceLocation still,ResourceLocation flowing) {
		super(fluidName,still,flowing);
	}
	public LeafiaFluid(String fluidName,ResourceLocation still,ResourceLocation flowing,Color color) {
		super(fluidName,still,flowing,color);
	}
	public LeafiaFluid(String fluidName,ResourceLocation still,ResourceLocation flowing,int color) {
		super(fluidName,still,flowing,color);
	}
	public LeafiaFluid(String fluidName,ResourceLocation still,ResourceLocation flowing,@Nullable ResourceLocation overlay) {
		super(fluidName,still,flowing,overlay);
	}
	public LeafiaFluid(String fluidName,ResourceLocation still,ResourceLocation flowing,@Nullable ResourceLocation overlay,Color color) {
		super(fluidName,still,flowing,overlay,color);
	}
	public LeafiaFluid(String fluidName,ResourceLocation still,ResourceLocation flowing,@Nullable ResourceLocation overlay,int color) {
		super(fluidName,still,flowing,overlay,color);
	}

	public FluidProperties getTraits() { return traits.getOrDefault(this.getName(),DEFAULT_TRAITS); };
	public LeafiaFluid addTraits(String... trait) {
		if (!traits.containsKey(this.getName()))
			traits.put(this.getName(),new FluidProperties(0,0,0,EnumSymbol.NONE));
		FluidProperties properties = traits.get(this.getName());
		properties.fiaTraits.addAll(Arrays.asList(trait));
		return this;
	}
	@Override
	public LeafiaFluid setTemperature(int temperature) {
		super.setTemperature(temperature);
		return this;
	}
}
