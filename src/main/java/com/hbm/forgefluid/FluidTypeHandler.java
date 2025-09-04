package com.hbm.forgefluid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbm.inventory.FluidCombustionRecipes.FuelGrade;
import com.hbm.inventory.FluidCombustionRecipes;
import com.hbm.inventory.FluidFlameRecipes;
import com.hbm.render.misc.EnumSymbol;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidTypeHandler {

	private static Map<String, FluidProperties> fluidProperties = new HashMap<String, FluidProperties>();
	public static final FluidProperties NONE = new FluidProperties(0, 0, 0, EnumSymbol.NONE);

	//Using strings so it's possible to specify properties for fluids from other mods
	public static void registerFluidProperties(){
		fluidProperties.put(FluidRegistry.WATER.getName(), new FluidProperties(0, 0, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.SPENTSTEAM.getName(), new FluidProperties(0, 0, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.STEAM.getName(), new FluidProperties(0, 0, 1, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.HOTSTEAM.getName(), new FluidProperties(0, 0 ,2, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.SUPERHOTSTEAM.getName(), new FluidProperties(0, 0 ,3, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.ULTRAHOTSTEAM.getName(), new FluidProperties(0, 0, 4, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.COOLANT.getName(), new FluidProperties(1, 0, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.HOTCOOLANT.getName(), new FluidProperties(1, 0, 4, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.PERFLUOROMETHYL.getName(), new FluidProperties(1, 0, 1, EnumSymbol.NONE));

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
		fluidProperties.put(ModForgeFluids.CHLORINE.getName(), new FluidProperties(3, 0, 0, EnumSymbol.OXIDIZER, FluidTrait.CORROSIVE));
		fluidProperties.put(ModForgeFluids.PHOSGENE.getName(), new FluidProperties(4, 0, 1, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.WOODOIL.getName(), new FluidProperties(2, 2, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.COALCREOSOTE.getName(), new FluidProperties(3, 2, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.COALOIL.getName(), new FluidProperties(2, 1, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.COALGAS.getName(), new FluidProperties(1, 2, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.COALGAS_LEADED.getName(), new FluidProperties(1, 2, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.PETROIL_LEADED.getName(), new FluidProperties(1, 3, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.GASOLINE_LEADED.getName(), new FluidProperties(1, 2, 0, EnumSymbol.NONE));
		fluidProperties.put(ModForgeFluids.SYNGAS.getName(), new FluidProperties(1, 4, 2, EnumSymbol.NONE));

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

		fluidProperties.put(ModForgeFluids.IONGEL.getName(), new FluidProperties(1, 0, 4, EnumSymbol.NONE));

		fluidProperties.put(ModForgeFluids.UU_MATTER.getName(),	new FluidProperties(6, 2, 6, 2.0F, EnumSymbol.ACID, FluidTrait.CORROSIVE));

		fluidProperties.put(ModForgeFluids.TOXIC_FLUID.getName(), new FluidProperties(3, 0, 4, EnumSymbol.RADIATION, FluidTrait.CORROSIVE_2));
		fluidProperties.put(ModForgeFluids.RADWATER_FLUID.getName(), new FluidProperties(2, 0, 0, EnumSymbol.RADIATION));
		fluidProperties.put(ModForgeFluids.MUD_FLUID.getName(), new FluidProperties(4, 0, 1, EnumSymbol.ACID, FluidTrait.CORROSIVE_2));
		fluidProperties.put(ModForgeFluids.CORIUM_FLUID.getName(), new FluidProperties(4, 0, 2, EnumSymbol.RADIATION, FluidTrait.CORROSIVE_2));
		fluidProperties.put(ModForgeFluids.VOLCANIC_LAVA_FLUID.getName(), new FluidProperties(4, 1, 1, EnumSymbol.NOWATER));


		/// FINAL ///

		long baseline = 100_000L; //we do not know
		double demandVeryLow = 0.5D; //for waste gasses
		double demandLow = 1.0D; //for fuel oils
		double demandMedium = 1.5D; //for processing oils like petroleum and BTX
		double demandHigh = 2.0D; //kerosene and jet fuels
		double complexityRefinery = 1.1D;
		double complexityFraction = 1.05D;
		double complexityCracking = 1.25D;
		double complexityCoker = 1.25D;
		double complexityChemplant = 1.1D;
		double complexityLubed = 1.15D;
		double complexityLeaded = 1.5D;
		double complexityVacuum = 3.0D;
		double complexityReform = 2.5D;
		double complexityHydro = 2.0D;
		double flammabilityLow = 0.25D; //unrefined or low refined oils
		double flammabilityNormal = 1.0D; //refined oils
		double flammabilityHigh = 2.0D; //satan's asshole

		/// the almighty Excel spreadsheet has spoken! ///
		registerCalculatedFuel(ModForgeFluids.OIL, (baseline * flammabilityLow * demandLow));
		registerCalculatedFuel(ModForgeFluids.HOTOIL, (baseline * flammabilityLow * demandLow));
		registerCalculatedFuel(ModForgeFluids.OIL_DS, (baseline * flammabilityLow * demandLow * complexityHydro));
		registerCalculatedFuel(ModForgeFluids.HOTOIL_DS, (baseline * flammabilityLow * demandLow * complexityHydro));
		registerCalculatedFuel(ModForgeFluids.CRACKOIL, (baseline * flammabilityLow * demandLow * complexityCracking));
		registerCalculatedFuel(ModForgeFluids.HOTCRACKOIL, (baseline * flammabilityLow * demandLow * complexityCracking));
		registerCalculatedFuel(ModForgeFluids.CRACKOIL_DS, (baseline * flammabilityLow * demandLow * complexityCracking * complexityHydro));
		registerCalculatedFuel(ModForgeFluids.HOTCRACKOIL_DS, (baseline * flammabilityLow * demandLow * complexityCracking * complexityHydro));
		registerCalculatedFuel(ModForgeFluids.OIL_COKER, (baseline * flammabilityLow * demandLow * complexityCoker));
		registerCalculatedFuel(ModForgeFluids.GAS, (baseline * flammabilityNormal * demandVeryLow), 1.5, FluidCombustionRecipes.FuelGrade.GAS);
		registerCalculatedFuel(ModForgeFluids.GAS_COKER, (baseline * flammabilityNormal * demandVeryLow * complexityCoker), 1.5, FuelGrade.GAS);
		registerCalculatedFuel(ModForgeFluids.HEAVYOIL, (baseline / 0.5 * flammabilityLow * demandLow * complexityRefinery), 1.25D, FuelGrade.LOW);
		registerCalculatedFuel(ModForgeFluids.SMEAR, (baseline / 0.35 * flammabilityLow * demandLow * complexityRefinery * complexityFraction), 1.25D, FuelGrade.LOW);
		registerCalculatedFuel(ModForgeFluids.RECLAIMED, (baseline / 0.28 * flammabilityLow * demandLow * complexityRefinery * complexityFraction * complexityChemplant), 1.25D, FuelGrade.LOW);
		registerCalculatedFuel(ModForgeFluids.PETROIL, (baseline / 0.28 * flammabilityLow * demandLow * complexityRefinery * complexityFraction * complexityChemplant * complexityLubed), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(ModForgeFluids.PETROIL_LEADED, (baseline / 0.28 * flammabilityLow * demandLow * complexityRefinery * complexityFraction * complexityChemplant * complexityLubed * complexityLeaded), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(ModForgeFluids.HEATINGOIL, (baseline / 0.31 * flammabilityNormal * demandLow * complexityRefinery * complexityFraction * complexityFraction), 1.25D, FuelGrade.LOW);
		registerCalculatedFuel(ModForgeFluids.NAPHTHA, (baseline / 0.25 * flammabilityLow * demandLow * complexityRefinery), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(ModForgeFluids.NAPHTHA_DS, (baseline / 0.25 * flammabilityLow * demandLow * complexityRefinery * complexityHydro), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(ModForgeFluids.NAPHTHA_CRACK, (baseline / 0.40 * flammabilityLow * demandLow * complexityRefinery * complexityCracking), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(ModForgeFluids.NAPHTHA_COKER, (baseline / 0.25 * flammabilityLow * demandLow * complexityCoker), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(ModForgeFluids.GASOLINE, (baseline / 0.20 * flammabilityNormal * demandLow * complexityRefinery * complexityChemplant), 2.5D, FuelGrade.HIGH);
		registerCalculatedFuel(ModForgeFluids.GASOLINE_LEADED, (baseline / 0.20 * flammabilityNormal * demandLow * complexityRefinery * complexityChemplant * complexityLeaded), 2.5D, FuelGrade.HIGH);
		double diesel = (baseline / 0.21 * flammabilityNormal * demandLow * complexityRefinery * complexityFraction);
		double diesel_crack = (baseline / 0.28 * flammabilityNormal * demandLow * complexityRefinery * complexityCracking * complexityFraction);
		registerCalculatedFuel(ModForgeFluids.DIESEL, diesel, 2.5D, FuelGrade.HIGH);
		registerCalculatedFuel(ModForgeFluids.DIESEL_CRACK, diesel_crack, 2.5D, FuelGrade.HIGH);
		registerCalculatedFuel(ModForgeFluids.DIESEL_REFORM, diesel * complexityReform, 2.5D, FuelGrade.HIGH);
		registerCalculatedFuel(ModForgeFluids.DIESEL_CRACK_REFORM, diesel_crack * complexityReform, 2.5D, FuelGrade.HIGH);
		registerCalculatedFuel(ModForgeFluids.LIGHTOIL, (baseline / 0.15 * flammabilityNormal * demandHigh * complexityRefinery), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(ModForgeFluids.LIGHTOIL_DS, (baseline / 0.15 * flammabilityNormal * demandHigh * complexityRefinery * complexityHydro), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(ModForgeFluids.LIGHTOIL_CRACK, (baseline / 0.30 * flammabilityNormal * demandHigh * complexityRefinery * complexityCracking), 1.5D, FuelGrade.MEDIUM);
		double kerosene = (baseline / 0.09 * flammabilityNormal * demandHigh * complexityRefinery * complexityFraction);
		registerCalculatedFuel(ModForgeFluids.KEROSENE, kerosene, 1.5D, FuelGrade.AERO);
		registerCalculatedFuel(ModForgeFluids.KEROSENE_REFORM, kerosene * complexityReform, 1.5D, FuelGrade.AERO);
		registerCalculatedFuel(ModForgeFluids.PETROLEUM, (baseline / 0.10 * flammabilityNormal * demandMedium * complexityRefinery), 1.5, FuelGrade.GAS);
		registerCalculatedFuel(ModForgeFluids.AROMATICS, (baseline / 0.15 * flammabilityLow * demandHigh * complexityRefinery * complexityCracking));
		registerCalculatedFuel(ModForgeFluids.UNSATURATEDS, (baseline / 0.15 * flammabilityHigh * demandHigh * complexityRefinery * complexityCracking));
		//registerCalculatedFuel(ModForgeFluids.LPG, (baseline / 0.1 * flammabilityNormal * demandMedium * complexityRefinery * complexityChemplant), 2.5, FuelGrade.HIGH);
		registerCalculatedFuel(ModForgeFluids.NITAN, kerosene * 25L, 2.5, FuelGrade.HIGH);
		registerCalculatedFuel(ModForgeFluids.BALEFIRE, kerosene * 100L, 2.5, FuelGrade.HIGH);
		registerCalculatedFuel(ModForgeFluids.HEAVYOIL_VACUUM, (baseline / 0.4 * flammabilityLow * demandLow * complexityVacuum), 1.25D, FuelGrade.LOW);
		registerCalculatedFuel(ModForgeFluids.REFORMATE, (baseline / 0.25 * flammabilityNormal * demandHigh * complexityVacuum), 2.5D, FuelGrade.HIGH);
		registerCalculatedFuel(ModForgeFluids.LIGHTOIL_VACUUM, (baseline / 0.20 * flammabilityNormal * demandHigh * complexityVacuum), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(ModForgeFluids.SOURGAS, (baseline / 0.15 * flammabilityLow * demandVeryLow * complexityVacuum));
		registerCalculatedFuel(ModForgeFluids.XYLENE, (baseline / 0.15 * flammabilityNormal * demandMedium * complexityVacuum * complexityFraction), 2.5D, FuelGrade.HIGH);
		registerCalculatedFuel(ModForgeFluids.HEATINGOIL_VACUUM, (baseline / 0.24 * flammabilityNormal * demandLow * complexityVacuum * complexityFraction), 1.25D, FuelGrade.LOW);
		registerCalculatedFuel(ModForgeFluids.REFORMGAS, (baseline / 0.06 * flammabilityHigh * demandLow * complexityVacuum * complexityFraction), 1.5D, FuelGrade.GAS);

		//all hail the spreadsheet
		//the spreadsheet must not be questioned
		//none may enter the orb- i mean the spreadsheet

		double coaloil = 400_000 * 10 * flammabilityLow * demandLow * complexityChemplant; // 200TU/t for 2000 ticks
		registerCalculatedFuel(ModForgeFluids.COALOIL, coaloil);
		registerCalculatedFuel(ModForgeFluids.COALGAS, (coaloil / 0.3 * flammabilityNormal * demandMedium * complexityChemplant * complexityFraction), 1.5, FuelGrade.MEDIUM);
		registerCalculatedFuel(ModForgeFluids.COALGAS_LEADED, (coaloil / 0.3 * flammabilityNormal * demandMedium * complexityChemplant * complexityFraction * complexityLeaded), 1.5, FuelGrade.MEDIUM);

		registerCalculatedFuel(ModForgeFluids.ETHANOL, 275_000D /* diesel / 2 */, 2.5D, FuelGrade.HIGH);

		registerCalculatedFuel(ModForgeFluids.BIOGAS, 250_000D * flammabilityLow /* biofuel with half compression, terrible flammability */, 1.25, FuelGrade.GAS);
		registerCalculatedFuel(ModForgeFluids.BIOFUEL, 500_000D /* slightly below diesel */, 2.5D, FuelGrade.HIGH);

		registerCalculatedFuel(ModForgeFluids.WOODOIL, 110_000 /* 20_000 TU per 250mB + a bonus */);
		registerCalculatedFuel(ModForgeFluids.COALCREOSOTE, 250_000 /* 20_000 TU per 100mB + a bonus */);
		registerCalculatedFuel(ModForgeFluids.FISHOIL, 75_000);
		registerCalculatedFuel(ModForgeFluids.SUNFLOWEROIL, 50_000);

		registerCalculatedFuel(ModForgeFluids.SOLVENT, 100_000); // flammable, sure, but not combustable
		registerCalculatedFuel(ModForgeFluids.RADIOSOLVENT, 150_000);

		registerCalculatedFuel(ModForgeFluids.SYNGAS, coaloil * 1.5, 1.25, FuelGrade.GAS); //same as coal oil, +50% bonus
		registerCalculatedFuel(ModForgeFluids.HYDROGEN, 5_000, 3, FuelGrade.GAS); // whatever
		registerCalculatedFuel(ModForgeFluids.DEUTERIUM, 5_000, 3, FuelGrade.GAS); // whatever
		registerCalculatedFuel(ModForgeFluids.TRITIUM, 5_000, 3, FuelGrade.GAS); // whatever
	}
	private static void registerCalculatedFuel(Fluid fluid, double base) {
		registerCalculatedFuel(fluid, base, 0, null);
	}
	private static void registerCalculatedFuel(Fluid fluid, double base, double combustMult, FluidCombustionRecipes.FuelGrade grade) {

		long flammable = (long) (base / 1000D);
		long combustible = (long) (base * combustMult);

		flammable = round(flammable);
		combustible = round(combustible);

		if(flammable > 0) FluidFlameRecipes.addBurnableFluid(fluid, flammable);

		if(combustible > 0 && grade != null)
			FluidCombustionRecipes.addFuel(fluid, grade, combustible);
	}

	/** ugly but it does the thing well enough */
	private static long round(long l) {
		if(l > 1_000_000_000L) return l - (l % 10_000_000L);
		if(l > 100_000_000L) return l - (l % 1_000_000L);
		if(l > 10_000_000L) return l - (l % 100_000L);
		if(l > 1_000_000L) return l - (l % 10_000L);
		if(l > 100_000L) return l - (l % 1_000L);
		if(l > 10_000L) return l - (l % 100L);
		if(l > 1_000L) return l - (l % 10L);
		return l;
	}
	
	public static class FluidProperties {
		
		public final int poison;
		public final int flammability;
		public final int reactivity;
		public final float dfcFuel;
		public final EnumSymbol symbol;
		public final List<FluidTrait> traits = new ArrayList<>();

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
		NO_ID;
	}


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
}
