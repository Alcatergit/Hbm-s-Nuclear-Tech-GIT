package com.hbm.forgefluid;

import java.util.HashMap;
import java.util.Map;

import com.hbm.render.misc.EnumSymbol;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidTypeHandler {

	private static Map<String, FluidProperties> fluidProperties = new HashMap<String, FluidProperties>();
	public static final FluidProperties NONE = new FluidProperties(0, 0, 0, EnumSymbol.NONE, false, false, false);
	
	public static FluidProperties getProperties(Fluid f){
		if(f == null)
			return NONE;
		FluidProperties p = fluidProperties.get(f.getName());
		return p != null ? p : NONE;
	}
	
	public static FluidProperties getProperties(FluidStack f){
		if(f == null)
			return NONE;
		return getProperties(f.getFluid());
	}
	
	public static boolean isAntimatter(Fluid f){
		if(f == null)
			return NONE.antimatter;
		FluidProperties p = fluidProperties.get(f.getName());
		if(p == null)
			return NONE.antimatter;
		return p.antimatter;
	}
	
	public static boolean isCorrosive(Fluid f){
		if(f == null)
			return NONE.corrosive;
		FluidProperties p = fluidProperties.get(f.getName());
		if(p == null)
			return NONE.corrosive;
		return p.corrosive;
	}
	
	public static boolean isHot(Fluid f){
		if(f == null)
			return NONE.hot;
		if(f.getTemperature() >= 373)
			return true;
		FluidProperties p = fluidProperties.get(f.getName());
		if(p == null)
			return NONE.hot;
		return p.hot;
	}
	
	//Using strings so it's possible to specify properties for fluids from other mods
	public static void registerFluidProperties(){
		fluidProperties.put(ModForgeFluids.amat.getName(), new FluidProperties(5, 0, 5,	EnumSymbol.ANTIMATTER, false, false, true));
	}
	
	public static class FluidProperties {
		
		public final boolean antimatter;
		public final boolean corrosive;
		public final boolean hot;
		
		public final int poison;
		public final int flammability;
		public final int reactivity;
		public final EnumSymbol symbol;
		
		
		public FluidProperties(int p, int f, int r, EnumSymbol symbol, boolean hot, boolean corrosive, boolean antimatter) {
			this.antimatter = antimatter;
			this.corrosive = corrosive;
			this.hot = hot;
			this.poison = p;
			this.flammability = f;
			this.reactivity = r;
			this.symbol = symbol;
		}
	}
}
