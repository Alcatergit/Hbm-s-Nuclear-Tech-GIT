package com.hbm.forgefluid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbm.render.misc.EnumSymbol;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidTypeHandler {

	private static Map<String, FluidProperties> fluidProperties = new HashMap<String, FluidProperties>();
	public static final FluidProperties NONE = new FluidProperties(0, 0, 0, EnumSymbol.NONE);
	
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
		return containsTrait(f, FluidTrait.AMAT);
	}
	
	public static boolean isCorrosive(Fluid f){
		return containsTrait(f, FluidTrait.CORROSIVE) || containsTrait(f, FluidTrait.CORROSIVE_2);
	}
	
	public static boolean isCorrosive2(Fluid f){
		return containsTrait(f, FluidTrait.CORROSIVE_2);
	}
	
	public static boolean isHot(Fluid f){
		if(f == null)
			return false;
		return f.getTemperature() >= 373;
	}
	
	public static boolean containsTrait(Fluid f, FluidTrait t){
		if(f == null)
			return false;
		FluidProperties p = fluidProperties.get(f.getName());
		if(p == null)
			return false;
		return p.traits.contains(t);
	}
	
	//Using strings so it's possible to specify properties for fluids from other mods
	public static void registerFluidProperties(){
		fluidProperties.put(FluidRegistry.WATER.getName(), new FluidProperties(0, 0, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.steam.getName(), new FluidProperties(3, 0 ,0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.hotsteam.getName(), new FluidProperties(4, 0 ,0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.superhotsteam.getName(), new FluidProperties(4, 0 ,0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.ultrahotsteam.getName(), new FluidProperties(4, 0, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.coolant.getName(), new FluidProperties(1, 0, 0, EnumSymbol.NONE));
		
		fluidProperties.put(FluidRegistry.LAVA.getName(), new FluidProperties(4, 0, 0, EnumSymbol.NOWATER));
		
		fluidProperties.put(ModForgeFluids.deuterium.getName(), new FluidProperties(3, 4 ,0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.tritium.getName(), new FluidProperties(3, 4 ,0, EnumSymbol.RADIATION));
		
		fluidProperties.put(ModForgeFluids.oil.getName(), new FluidProperties(2, 1, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.hotoil.getName(), new FluidProperties(2, 3, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.crackoil.getName(), new FluidProperties(2, 1, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.hotcrackoil.getName(), new FluidProperties(2, 3, 0, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.heavyoil.getName(), new FluidProperties(2, 1, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.bitumen.getName(), new FluidProperties(2, 0, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.smear.getName(), new FluidProperties(2, 1, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.heatingoil.getName(), new FluidProperties(2, 2, 0, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.reclaimed.getName(), new FluidProperties(2, 2, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.petroil.getName(), new FluidProperties(1, 3, 0, EnumSymbol.NONE));

		fluidProperties.put(ModForgeFluids.fracksol.getName(), new FluidProperties(1, 3, 3, EnumSymbol.ACID, FluidTrait.CORROSIVE));
		
		fluidProperties.put(ModForgeFluids.lubricant.getName(), new FluidProperties(2, 1, 0, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.naphtha.getName(), new FluidProperties(2, 1, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.diesel.getName(), new FluidProperties(1, 2, 0, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.lightoil.getName(), new FluidProperties(1, 2, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.kerosene.getName(), new FluidProperties(1, 2, 0, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.gas.getName(), new FluidProperties(1, 4, 1, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.petroleum.getName(), new FluidProperties(1, 4, 1, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.aromatics.getName(), new FluidProperties(1, 4, 1, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.unsaturateds.getName(), new FluidProperties(1, 4, 1, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.biogas.getName(), new FluidProperties(1, 4, 1, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.biofuel.getName(), new FluidProperties(1, 2, 0, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.nitan.getName(), new FluidProperties(2, 4, 1, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.uf6.getName(), new FluidProperties(4, 0, 2, EnumSymbol.RADIATION, FluidTrait.CORROSIVE));
		fluidProperties.put(ModForgeFluids.puf6.getName(), new FluidProperties(4, 0, 4, EnumSymbol.RADIATION, FluidTrait.CORROSIVE));
		fluidProperties.put(ModForgeFluids.sas3.getName(), new FluidProperties(5, 0, 4, EnumSymbol.RADIATION, FluidTrait.CORROSIVE));
		fluidProperties.put(ModForgeFluids.schrabidic.getName(), new FluidProperties(5, 0, 5, EnumSymbol.ACID, FluidTrait.CORROSIVE_2));
		
		fluidProperties.put(ModForgeFluids.amat.getName(), new FluidProperties(5, 0, 5,	EnumSymbol.ANTIMATTER, FluidTrait.AMAT));
		fluidProperties.put(ModForgeFluids.aschrab.getName(), new FluidProperties(5, 0, 5, EnumSymbol.ANTIMATTER, FluidTrait.AMAT));
		
		fluidProperties.put(ModForgeFluids.acid.getName(), new FluidProperties(3, 0, 3, EnumSymbol.OXIDIZER, FluidTrait.CORROSIVE));
		fluidProperties.put(ModForgeFluids.sulfuric_acid.getName(),	new FluidProperties(3, 0, 2, EnumSymbol.ACID, FluidTrait.CORROSIVE));
		fluidProperties.put(ModForgeFluids.liquid_osmiridium.getName(),	new FluidProperties(5, 0, 5, EnumSymbol.ACID, FluidTrait.CORROSIVE));
		

		fluidProperties.put(ModForgeFluids.watz.getName(), new FluidProperties(4, 0, 3, EnumSymbol.ACID, FluidTrait.CORROSIVE_2));
		fluidProperties.put(ModForgeFluids.cryogel.getName(), new FluidProperties(2, 0, 0, EnumSymbol.CROYGENIC));
		
		fluidProperties.put(ModForgeFluids.hydrogen.getName(), new FluidProperties(3, 4, 0, EnumSymbol.CROYGENIC));
		fluidProperties.put(ModForgeFluids.oxygen.getName(), new FluidProperties(3, 0, 0, EnumSymbol.CROYGENIC));
		fluidProperties.put(ModForgeFluids.xenon.getName(), new FluidProperties(0, 0, 0, EnumSymbol.ASPHYXIANT));
		fluidProperties.put(ModForgeFluids.balefire.getName(), new FluidProperties(4, 4, 3, EnumSymbol.RADIATION, FluidTrait.CORROSIVE));
		
		fluidProperties.put(ModForgeFluids.mercury.getName(), new FluidProperties(2, 0, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.pain.getName(), new FluidProperties(2, 0, 1, EnumSymbol.ACID, FluidTrait.CORROSIVE));
		
		fluidProperties.put(ModForgeFluids.wastefluid.getName(), new FluidProperties(2, 0, 1, EnumSymbol.RADIATION, FluidTrait.NO_CONTAINER));
		fluidProperties.put(ModForgeFluids.wastegas.getName(), new FluidProperties(2, 0, 1, EnumSymbol.RADIATION, FluidTrait.NO_CONTAINER));
		
		fluidProperties.put(ModForgeFluids.gasoline.getName(), new FluidProperties(2, 0, 1, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.spentsteam.getName(), new FluidProperties(2, 0, 1, EnumSymbol.NONE, FluidTrait.NO_CONTAINER));
		
		fluidProperties.put(ModForgeFluids.plasma_dt.getName(), new FluidProperties(0, 4, 0, EnumSymbol.RADIATION, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID));
		fluidProperties.put(ModForgeFluids.plasma_hd.getName(), new FluidProperties(0, 4, 0, EnumSymbol.RADIATION, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID));
		fluidProperties.put(ModForgeFluids.plasma_ht.getName(), new FluidProperties(0, 4, 0, EnumSymbol.RADIATION, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID));
		fluidProperties.put(ModForgeFluids.plasma_put.getName(), new FluidProperties(2, 3, 1, EnumSymbol.RADIATION, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID));
		fluidProperties.put(ModForgeFluids.plasma_xm.getName(), new FluidProperties(0, 4, 1, EnumSymbol.RADIATION, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID));
		fluidProperties.put(ModForgeFluids.plasma_bf.getName(), new FluidProperties(4, 5, 4, EnumSymbol.ANTIMATTER, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID));
	}
	
	public static class FluidProperties {
		
		public final int poison;
		public final int flammability;
		public final int reactivity;
		public final EnumSymbol symbol;
		public final List<FluidTrait> traits = new ArrayList<>();
		
		public FluidProperties(int p, int f, int r, EnumSymbol symbol, FluidTrait... traits) {
			this.poison = p;
			this.flammability = f;
			this.reactivity = r;
			this.symbol = symbol;
			for(FluidTrait trait : traits)
				this.traits.add(trait);
		}
	}
	
	public static enum FluidTrait {
		AMAT,
		CORROSIVE,
		CORROSIVE_2,
		NO_CONTAINER,
		NO_ID;
	}
}
