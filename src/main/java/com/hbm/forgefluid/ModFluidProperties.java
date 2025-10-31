package com.hbm.forgefluid;

import com.hbm.render.misc.EnumSymbol;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModFluidProperties {
	// formerly FluidTypeHandler
	// renamed to ModFluidProperties to better hit the search results

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

	public static float getDFCEfficiency(Fluid f){
		FluidProperties prop = getProperties(f);
		return prop.dfcFuel;
	}

	public static boolean explodeTier0(Fluid f) {
		return containsTrait(f,FluidTrait.HIGH_PRESSURE) || explodeTier1(f);
	}
	public static boolean explodeTier1(Fluid f) {
		return containsTrait(f,FluidTrait.EXTREME_PRESSURE);
	}
	public static boolean explodeTier2(Fluid f) { return false; } // for dummies like me

	public static boolean isAntimatter(Fluid f){
		return containsTrait(f, FluidTrait.AMAT);
	}
	
	public static boolean isCorrosivePlastic(Fluid f){
		return containsTrait(f, FluidTrait.CORROSIVE) || containsTrait(f, FluidTrait.CORROSIVE_2);
	}
	
	public static boolean isCorrosiveIron(Fluid f){
		return containsTrait(f, FluidTrait.CORROSIVE_2);
	}
	
	public static boolean isHot(Fluid f){
		if(f == null)
			return false;
		return f.getTemperature() >= 373;
	}

	public static boolean noID(Fluid f){
		return containsTrait(f, FluidTrait.NO_ID);
	}

	public static boolean noContainer(Fluid f){
		return containsTrait(f, FluidTrait.NO_CONTAINER);
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
		fluidProperties.put(ModForgeFluids.SPENTSTEAM.getName(), new FluidProperties(0, 0, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.STEAM.getName(), new FluidProperties(0, 0, 1, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.HOTSTEAM.getName(), new FluidProperties(0, 0 ,2, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.SUPERHOTSTEAM.getName(), new FluidProperties(0, 0 ,3, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.ULTRAHOTSTEAM.getName(), new FluidProperties(0, 0, 4, EnumSymbol.NONE, FluidTrait.HIGH_PRESSURE));
		fluidProperties.put(ModForgeFluids.DEATHSTEAM.getName(), new FluidProperties(0, 0, 5, EnumSymbol.NONE, FluidTrait.EXTREME_PRESSURE));

		fluidProperties.put(ModForgeFluids.COOLANT.getName(), new FluidProperties(1, 0, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.HOTCOOLANT.getName(), new FluidProperties(1, 0, 4, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.MALCOOLANT.getName(), new FluidProperties(1, 0, 5, EnumSymbol.NONE, FluidTrait.EXTREME_PRESSURE));
		
		fluidProperties.put(FluidRegistry.LAVA.getName(), new FluidProperties(4, 0, 0, EnumSymbol.NOWATER));
		
		fluidProperties.put(ModForgeFluids.HEAVYWATER.getName(), new FluidProperties(1, 0, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.HYDROGEN.getName(), new FluidProperties(1, 4, 0, 1F, EnumSymbol.CROYGENIC));
		fluidProperties.put(ModForgeFluids.DEUTERIUM.getName(), new FluidProperties(2, 4, 0, 1.2F, EnumSymbol.CROYGENIC));
		fluidProperties.put(ModForgeFluids.TRITIUM.getName(), new FluidProperties(3, 4, 0, 1.3F, EnumSymbol.RADIATION));
		
		fluidProperties.put(ModForgeFluids.OIL.getName(), new FluidProperties(2, 1, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.HOTOIL.getName(), new FluidProperties(2, 3, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.CRACKOIL.getName(), new FluidProperties(2, 1, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.HOTCRACKOIL.getName(), new FluidProperties(2, 3, 0, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.HEAVYOIL.getName(), new FluidProperties(2, 1, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.BITUMEN.getName(), new FluidProperties(2, 0, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.SMEAR.getName(), new FluidProperties(2, 1, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.HEATINGOIL.getName(), new FluidProperties(2, 2, 0, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.RECLAIMED.getName(), new FluidProperties(2, 2, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.PETROIL.getName(), new FluidProperties(1, 3, 0, EnumSymbol.NONE));

		fluidProperties.put(ModForgeFluids.FRACKSOL.getName(), new FluidProperties(1, 3, 3, EnumSymbol.ACID, FluidTrait.CORROSIVE));
		
		fluidProperties.put(ModForgeFluids.LUBRICANT.getName(), new FluidProperties(2, 1, 0, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.NAPHTHA.getName(), new FluidProperties(2, 1, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.DIESEL.getName(), new FluidProperties(1, 2, 0, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.LIGHTOIL.getName(), new FluidProperties(1, 2, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.KEROSENE.getName(), new FluidProperties(1, 2, 0, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.GAS.getName(), new FluidProperties(1, 4, 1, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.PETROLEUM.getName(), new FluidProperties(1, 4, 1, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.AROMATICS.getName(), new FluidProperties(1, 4, 1, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.UNSATURATEDS.getName(), new FluidProperties(1, 4, 1, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.BIOGAS.getName(), new FluidProperties(1, 4, 1, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.BIOFUEL.getName(), new FluidProperties(1, 2, 0, EnumSymbol.NONE));

		fluidProperties.put(ModForgeFluids.ETHANOL.getName(), new FluidProperties(2, 3, 1, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.FISHOIL.getName(), new FluidProperties(0, 1, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.SUNFLOWEROIL.getName(), new FluidProperties(0, 1, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.COLLOID.getName(), new FluidProperties(0, 0, 0, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.NITAN.getName(), new FluidProperties(2, 4, 1, 1.6F, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.UF6.getName(), new FluidProperties(4, 0, 2, 1.3F, EnumSymbol.RADIATION, FluidTrait.CORROSIVE));
		fluidProperties.put(ModForgeFluids.PUF6.getName(), new FluidProperties(4, 0, 4, 1.4F, EnumSymbol.RADIATION, FluidTrait.CORROSIVE));
		fluidProperties.put(ModForgeFluids.SAS3.getName(), new FluidProperties(5, 0, 4, 1.5F, EnumSymbol.RADIATION, FluidTrait.CORROSIVE));
		fluidProperties.put(ModForgeFluids.SCHRABIDIC.getName(), new FluidProperties(5, 0, 5, 1.7F, EnumSymbol.ACID, FluidTrait.CORROSIVE_2));
		
		fluidProperties.put(ModForgeFluids.AMAT.getName(), new FluidProperties(6, 0, 6, 2.2F, EnumSymbol.ANTIMATTER, FluidTrait.AMAT));
		fluidProperties.put(ModForgeFluids.ASCHRAB.getName(), new FluidProperties(6, 1, 6, 2.5F, EnumSymbol.ANTIMATTER, FluidTrait.AMAT));
		
		fluidProperties.put(ModForgeFluids.ACID.getName(), new FluidProperties(3, 0, 1, 1.05F, EnumSymbol.OXIDIZER, FluidTrait.CORROSIVE));
		fluidProperties.put(ModForgeFluids.SULFURIC_ACID.getName(),	new FluidProperties(3, 0, 2, 1.3F, EnumSymbol.ACID, FluidTrait.CORROSIVE));
		fluidProperties.put(ModForgeFluids.NITRIC_ACID.getName(),	new FluidProperties(3, 0, 3, 1.4F, EnumSymbol.ACID, FluidTrait.CORROSIVE_2));
		fluidProperties.put(ModForgeFluids.SOLVENT.getName(),	new FluidProperties(2, 3, 0, 1.45F, EnumSymbol.ACID, FluidTrait.CORROSIVE));
		fluidProperties.put(ModForgeFluids.RADIOSOLVENT.getName(),	new FluidProperties(3, 3, 0, 1.6F, EnumSymbol.ACID, FluidTrait.CORROSIVE_2));
		fluidProperties.put(ModForgeFluids.NITROGLYCERIN.getName(), new FluidProperties(0, 4, 4, 1.5F, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.LIQUID_OSMIRIDIUM.getName(),	new FluidProperties(5, 0, 5, 1.8F, EnumSymbol.OXIDIZER, FluidTrait.CORROSIVE_2));
		
		fluidProperties.put(ModForgeFluids.MUD_FLUID.getName(), new FluidProperties(4, 0, 3, 1.5F, EnumSymbol.OXIDIZER, FluidTrait.CORROSIVE_2));
		fluidProperties.put(ModForgeFluids.CRYOGEL.getName(), new FluidProperties(2, 0, 0, EnumSymbol.CROYGENIC));
		
		fluidProperties.put(ModForgeFluids.OXYGEN.getName(), new FluidProperties(3, 0, 0, 1.1F, EnumSymbol.CROYGENIC));
		fluidProperties.put(ModForgeFluids.CARBONDIOXIDE.getName(), new FluidProperties(3, 0, 0, EnumSymbol.ASPHYXIANT));
		fluidProperties.put(ModForgeFluids.XENON.getName(), new FluidProperties(0, 0, 0, 1.25F, EnumSymbol.ASPHYXIANT));
		fluidProperties.put(ModForgeFluids.BALEFIRE.getName(), new FluidProperties(4, 4, 5, 2.4F, EnumSymbol.RADIATION, FluidTrait.CORROSIVE));
		
		fluidProperties.put(ModForgeFluids.MERCURY.getName(), new FluidProperties(2, 0, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.PAIN.getName(), new FluidProperties(2, 0, 1, EnumSymbol.ACID, FluidTrait.CORROSIVE));
		
		fluidProperties.put(ModForgeFluids.WASTEFLUID.getName(), new FluidProperties(2, 0, 1, EnumSymbol.RADIATION));
		fluidProperties.put(ModForgeFluids.WASTEGAS.getName(), new FluidProperties(2, 0, 1, EnumSymbol.RADIATION));
		
		fluidProperties.put(ModForgeFluids.GASOLINE.getName(), new FluidProperties(2, 0, 1, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.EXPERIENCE.getName(), new FluidProperties(0, 0, 0, 1.1F, EnumSymbol.NONE));
		
		fluidProperties.put(ModForgeFluids.PLASMA_DT.getName(), new FluidProperties(0, 4, 0, EnumSymbol.RADIATION, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID));
		fluidProperties.put(ModForgeFluids.PLASMA_HD.getName(), new FluidProperties(0, 4, 0, EnumSymbol.RADIATION, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID));
		fluidProperties.put(ModForgeFluids.PLASMA_HT.getName(), new FluidProperties(0, 4, 0, EnumSymbol.RADIATION, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID));
		fluidProperties.put(ModForgeFluids.PLASMA_PUT.getName(), new FluidProperties(2, 3, 1, EnumSymbol.RADIATION, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID));
		fluidProperties.put(ModForgeFluids.PLASMA_MX.getName(), new FluidProperties(0, 4, 1, EnumSymbol.RADIATION, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID));
		fluidProperties.put(ModForgeFluids.PLASMA_BF.getName(), new FluidProperties(4, 5, 4, EnumSymbol.RADIATION, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID));
		fluidProperties.put(ModForgeFluids.UU_MATTER.getName(),	new FluidProperties(6, 2, 6, 2.0F, EnumSymbol.ACID, FluidTrait.CORROSIVE));

		fluidProperties.put(ModForgeFluids.TOXIC_FLUID.getName(), new FluidProperties(3, 0, 4, EnumSymbol.RADIATION, FluidTrait.CORROSIVE_2));
		fluidProperties.put(ModForgeFluids.RADWATER_FLUID.getName(), new FluidProperties(2, 0, 0, EnumSymbol.RADIATION));
		fluidProperties.put(ModForgeFluids.MUD_FLUID.getName(), new FluidProperties(4, 0, 1, EnumSymbol.ACID, FluidTrait.CORROSIVE_2));
		fluidProperties.put(ModForgeFluids.CORIUM_FLUID.getName(), new FluidProperties(4, 0, 2, EnumSymbol.RADIATION, FluidTrait.CORROSIVE_2));
		fluidProperties.put(ModForgeFluids.VOLCANIC_LAVA_FLUID.getName(), new FluidProperties(4, 1, 1, EnumSymbol.NOWATER));
	
	}
	
	public static class FluidProperties {
		
		public final int poison;
		public final int flammability;
		public final int reactivity;
		public final float dfcFuel;
		public final EnumSymbol symbol;
		public final List<FluidTrait> traits = new ArrayList<>();

		public final List<String> fiaTraits = new ArrayList<>();

		public FluidProperties(int p, int f, int r, EnumSymbol symbol, FluidTrait... traits) {
			this(p, f, r, 0, symbol, traits);
		}
		
		public FluidProperties(int p, int f, int r, float dfc, EnumSymbol symbol, FluidTrait... traits) {
			this.poison = p;
			this.flammability = f;
			this.reactivity = r;
			this.dfcFuel = dfc;
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
		NO_ID,
		HIGH_PRESSURE,
		EXTREME_PRESSURE;
	}
}
