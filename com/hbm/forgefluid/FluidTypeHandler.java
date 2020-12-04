package com.hbm.forgefluid;

import java.util.HashMap;
import java.util.Map;

import com.hbm.render.misc.EnumSymbol;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
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
		fluidProperties.put(FluidRegistry.WATER.getName(), new FluidProperties(0, 0, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.steam.getName(), new FluidProperties(3, 0 ,0, EnumSymbol.NONE, true, false, false));
		fluidProperties.put(ModForgeFluids.hotsteam.getName(), new FluidProperties(4, 0 ,0, EnumSymbol.NONE, true, false, false));
		fluidProperties.put(ModForgeFluids.superhotsteam.getName(), new FluidProperties(4, 0 ,0, EnumSymbol.NONE, true, false, false));
		fluidProperties.put(ModForgeFluids.ultrahotsteam.getName(), new FluidProperties(4, 0, 0, EnumSymbol.NONE, true, false, false));
		fluidProperties.put(ModForgeFluids.coolant.getName(), new FluidProperties(1, 0, 0, EnumSymbol.NONE));
		
		fluidProperties.put(FluidRegistry.LAVA.getName(), new FluidProperties(4, 0, 0, EnumSymbol.NOWATER, true, false, false));
		
		fluidProperties.put(ModForgeFluids.deuterium.getName(), new FluidProperties(3, 4 ,0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.tritium.getName(), new FluidProperties(3, 4 ,0, EnumSymbol.RADIATION));
		
		fluidProperties.put(ModForgeFluids.oil.getName(), new FluidProperties(2, 1, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.hotoil.getName(), new FluidProperties(2, 3, 0, EnumSymbol.NONE, true, false, false));
		
		fluidProperties.put(ModForgeFluids.heavyoil.getName(), new FluidProperties(2, 1, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.bitumen.getName(), new FluidProperties(2, 0, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.smear.getName(), new FluidProperties(2, 1, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.heatingoil.getName(), new FluidProperties(2, 2, 0, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.reclaimed.getName(), new FluidProperties(2, 2, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.petroil.getName(), new FluidProperties(1, 3, 0, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.lubricant.getName(), new FluidProperties(2, 1, 0, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.naphtha.getName(), new FluidProperties(2, 1, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.diesel.getName(), new FluidProperties(1, 2, 0, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.lightoil.getName(), new FluidProperties(1, 2, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.kerosene.getName(), new FluidProperties(1, 2, 0, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.gas.getName(), new FluidProperties(1, 4, 1, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.petroleum.getName(), new FluidProperties(1, 4, 1, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.biogas.getName(), new FluidProperties(1, 4, 1, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.biofuel.getName(), new FluidProperties(1, 2, 0, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.nitan.getName(), new FluidProperties(2, 4, 1, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.uf6.getName(), new FluidProperties(4, 0, 2, EnumSymbol.RADIATION, false, true, false));
		fluidProperties.put(ModForgeFluids.puf6.getName(), new FluidProperties(4, 0, 4, EnumSymbol.RADIATION, false, true, false));
		fluidProperties.put(ModForgeFluids.sas3.getName(), new FluidProperties(5, 0, 4, EnumSymbol.RADIATION, false, true, false));
		
		fluidProperties.put(ModForgeFluids.amat.getName(), new FluidProperties(5, 0, 5,	EnumSymbol.ANTIMATTER, false, false, true));
		fluidProperties.put(ModForgeFluids.aschrab.getName(), new FluidProperties(5, 0, 5, EnumSymbol.ANTIMATTER, false, false, true));
		
		fluidProperties.put(ModForgeFluids.acid.getName(), new FluidProperties(3, 0, 3, EnumSymbol.OXIDIZER, false, true, false));
		fluidProperties.put(ModForgeFluids.watz.getName(), new FluidProperties(4, 0, 3, EnumSymbol.ACID, false, true, false));
		fluidProperties.put(ModForgeFluids.cryogel.getName(), new FluidProperties(2, 0, 0, EnumSymbol.CROYGENIC));
		
		fluidProperties.put(ModForgeFluids.hydrogen.getName(), new FluidProperties(3, 4, 0, EnumSymbol.CROYGENIC));
		fluidProperties.put(ModForgeFluids.oxygen.getName(), new FluidProperties(3, 0, 0, EnumSymbol.CROYGENIC));
		fluidProperties.put(ModForgeFluids.xenon.getName(), new FluidProperties(0, 0, 0, EnumSymbol.ASPHYXIANT));
		fluidProperties.put(ModForgeFluids.balefire.getName(), new FluidProperties(4, 4, 3, EnumSymbol.RADIATION, true, true, false));
		
		fluidProperties.put(ModForgeFluids.mercury.getName(), new FluidProperties(2, 0, 0, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.plasma_dt.getName(), new FluidProperties(0, 4, 0, EnumSymbol.RADIATION, true, false, true));
		fluidProperties.put(ModForgeFluids.plasma_hd.getName(), new FluidProperties(0, 4, 0, EnumSymbol.RADIATION, true, false, true));
		fluidProperties.put(ModForgeFluids.plasma_ht.getName(), new FluidProperties(0, 4, 0, EnumSymbol.RADIATION, true, false, true));
		fluidProperties.put(ModForgeFluids.plasma_xm.getName(), new FluidProperties(0, 4, 1, EnumSymbol.RADIATION, true, false, true));
		fluidProperties.put(ModForgeFluids.plasma_bf.getName(), new FluidProperties(4, 5, 4, EnumSymbol.ANTIMATTER, true, false, true));
	}
	
	public static class FluidProperties {
		
		public final boolean antimatter;
		public final boolean corrosive;
		public final boolean hot;
		
		public final int poison;
		public final int flammability;
		public final int reactivity;
		public final EnumSymbol symbol;
		
		public FluidProperties(int p, int f, int r, EnumSymbol symbol) {
			this.antimatter = false;
			this.corrosive = false;
			this.hot = false;
			this.poison = p;
			this.flammability = f;
			this.reactivity = r;
			this.symbol = symbol;
		}
		
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
