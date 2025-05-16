package com.hbm.forgefluid;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.fluid.CoriumBlock;
import com.hbm.blocks.fluid.CoriumFluid;
import com.hbm.blocks.fluid.MudBlock;
import com.hbm.blocks.fluid.MudFluid;
import com.hbm.blocks.fluid.SchrabidicBlock;
import com.hbm.blocks.fluid.SchrabidicFluid;
import com.hbm.blocks.fluid.ToxicBlock;
import com.hbm.blocks.fluid.ToxicFluid;
import com.hbm.blocks.fluid.RadWaterBlock;
import com.hbm.blocks.fluid.RadWaterFluid;
import com.hbm.blocks.fluid.VolcanicBlock;
import com.hbm.blocks.fluid.VolcanicFluid;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = RefStrings.MODID)
public class ModForgeFluids {

	public static List<String> noBlockFluidNames = new ArrayList<String>();
	public static HashMap<Fluid, String> noBlockFluids = new HashMap<Fluid, String>();
	public static HashMap<Fluid, Integer> fluidColors = new HashMap<Fluid, Integer>();
	
	public static Fluid SPENTSTEAM = 			createFluid("spentsteam").setTemperature(40 + 273);
	public static Fluid STEAM = 				createFluid("steam").setTemperature(100 + 273);
	public static Fluid HOTSTEAM = 				createFluid("hotsteam").setTemperature(300 + 273);
	public static Fluid SUPERHOTSTEAM = 		createFluid("superhotsteam").setTemperature(450 + 273);
	public static Fluid ULTRAHOTSTEAM = 		createFluid("ultrahotsteam").setTemperature(600 + 273);
	public static Fluid COOLANT = 				createFluid("coolant").setTemperature(203);
	public static Fluid HOTCOOLANT = 			createFluid("hotcoolant").setTemperature(400 + 273);
	public static Fluid PERFLUOROMETHYL =		createFluid("perfluoromethyl").setTemperature(15 + 273);

	public static Fluid HEAVYWATER = 			createFluid("heavywater");
	public static Fluid DEUTERIUM = 			createFluid("deuterium");
	public static Fluid TRITIUM = 				createFluid("tritium");

	public static Fluid OIL = 					createFluid("oil");
	public static Fluid HOTOIL = 				createFluid("hotoil").setTemperature(350+273);
	public static Fluid CRACKOIL = 				createFluid("crackoil");
	public static Fluid HOTCRACKOIL = 			createFluid("hotcrackoil").setTemperature(350+273);
	public static Fluid OIL_DS = 				createFluid("oil_ds");
	public static Fluid HOTOIL_DS = 			createFluid("hotoil_ds");
	public static Fluid CRACKOIL_DS = 			createFluid("crackoil_ds");
	public static Fluid HOTCRACKOIL_DS = 		createFluid("hotcrackoil_ds");
	public static Fluid OIL_COKER = 			createFluid("oil_coker");

	public static Fluid HEAVYOIL = 				createFluid("heavyoil");
	public static Fluid HEAVYOIL_VACUUM = 		createFluid("heavyoil_vacuum");
	public static Fluid BITUMEN = 				createFluid("bitumen");
	public static Fluid SMEAR = 				createFluid("smear");
	public static Fluid HEATINGOIL = 			createFluid("heatingoil");
	public static Fluid HEATINGOIL_VACUUM = 	createFluid("heatingoil_vacuum");

	public static Fluid RECLAIMED = 			createFluid("reclaimed");
	public static Fluid PETROIL = 				createFluid("petroil");
	
	public static Fluid FRACKSOL = 				createFluid("fracksol");
	//Drillgon200: Bruh I spelled this wrong, too.
	public static Fluid LUBRICANT = 			createFluid("lubricant");

	//Yes yes I know, I spelled 'naphtha' wrong.
	public static Fluid NAPHTHA = 				createFluid("naphtha");
	public static Fluid NAPHTHA_CRACK = 		createFluid("naphtha_crack");
	public static Fluid NAPHTHA_DS = 			createFluid("naphtha_ds");
	public static Fluid NAPHTHA_COKER = 		createFluid("naphtha_coker");

	public static Fluid DIESEL = 				createFluid("diesel");
	public static Fluid DIESEL_CRACK = 			createFluid("diesel_crack");
	public static Fluid DIESEL_REFORM = 		createFluid("diesel_reform");
	public static Fluid DIESEL_CRACK_REFORM = 	createFluid("diesel_crack_reform");

	public static Fluid LIGHTOIL = 				createFluid("lightoil");
	public static Fluid LIGHTOIL_CRACK = 		createFluid("lightoil_crack");
	public static Fluid LIGHTOIL_DS = 			createFluid("lightoil_ds");
	public static Fluid LIGHTOIL_VACUUM = 		createFluid("lightoil_vacuum");
	public static Fluid KEROSENE = 				createFluid("kerosene");
	public static Fluid KEROSENE_REFORM = 		createFluid("kerosene_reform");

	public static Fluid GAS = 					createFluid("gas");
	public static Fluid GAS_COKER = 			createFluid("gas_coker");
	public static Fluid PETROLEUM = 			createFluid("petroleum");

	public static Fluid AROMATICS = 			createFluid("aromatics");
	public static Fluid UNSATURATEDS = 			createFluid("unsaturateds");
	public static Fluid XYLENE = 				createFluid("xylene");

	public static Fluid CHLORINE =				createFluid("chlorine");
	public static Fluid PHOSGENE =				createFluid("phosgene");
	public static Fluid WOODOIL =				createFluid("woodoil");
	public static Fluid COALCREOSOTE =			createFluid("coalcreosote");
	public static Fluid COALOIL =				createFluid("coaloil");
	public static Fluid COALGAS =				createFluid("coalgas");
	public static Fluid COALGAS_LEADED =		createFluid("coalgas_leaded");
	public static Fluid PETROIL_LEADED =		createFluid("petroil_leaded");
	public static Fluid GASOLINE_LEADED =		createFluid("gasoline_leaded");
	public static Fluid SYNGAS =				createFluid("syngas");

	public static Fluid REFORMATE = 			createFluid("reformate");
	public static Fluid REFORMGAS = 			createFluid("reformgas");

	public static Fluid BIOGAS = 				createFluid("biogas");
	public static Fluid BIOFUEL = 				createFluid("biofuel");
	public static Fluid SOURGAS = 				createFluid("sourgas");

	public static Fluid ETHANOL = 				createFluid("ethanol");
	public static Fluid FISHOIL = 				createFluid("fishoil");
	public static Fluid SUNFLOWEROIL = 			createFluid("sunfloweroil");
	public static Fluid COLLOID = 				createFluid("colloid");

	public static Fluid NITAN = 				createFluid("nitan");

	public static Fluid UF6 = 					createFluid("uf6");
	public static Fluid PUF6 = 					createFluid("puf6");
	public static Fluid SAS3 = 					createFluid("sas3");

	public static Fluid AMAT = 					createFluid("amat");
	public static Fluid ASCHRAB = 				createFluid("aschrab");

	public static Fluid ACID = 					createFluid("acid");
	public static Fluid SULFURIC_ACID = 		createFluid("sulfuric_acid");
	public static Fluid NITRIC_ACID = 			createFluid("nitric_acid");
	public static Fluid SOLVENT = 				createFluid("solvent");
	public static Fluid RADIOSOLVENT = 			createFluid("radiosolvent");
	public static Fluid NITROGLYCERIN = 		createFluid("nitroglycerin");
	
	public static Fluid LIQUID_OSMIRIDIUM = 	createFluid("liquid_osmiridium").setTemperature(573);
	public static Fluid WATZ = 					createFluidFlowing("watz").setDensity(2500).setViscosity(3000).setLuminosity(5).setTemperature(2773);
	public static Fluid CRYOGEL = 				createFluid("cryogel").setTemperature(50);

	public static Fluid HYDROGEN = 				createFluid("hydrogen");
	public static Fluid OXYGEN = 				createFluid("oxygen");
	public static Fluid XENON = 				createFluid("xenon");
	public static Fluid BALEFIRE = 				createFluid("balefire").setTemperature(15000 + 273);

	public static Fluid MERCURY = 				createFluid("mercury");

	public static Fluid PLASMA_HD = 			createFluid("plasma_hd").setTemperature(25000 + 273);
	public static Fluid PLASMA_HT = 			createFluid("plasma_ht").setTemperature(30000 + 273);
	public static Fluid PLASMA_DT = 			createFluid("plasma_dt").setTemperature(32500 + 273);
	public static Fluid PLASMA_MX = 			createFluid("plasma_xm").setTemperature(45000 + 273);
	public static Fluid PLASMA_PUT = 			createFluid("plasma_put").setTemperature(50000 + 273);
	public static Fluid PLASMA_BF = 			createFluid("plasma_bf").setTemperature(85000 + 273);

	public static Fluid IONGEL =				createFluid("iongel");

	public static Fluid UU_MATTER = 			createFluid("ic2uu_matter").setTemperature(1000000 + 273);

	public static Fluid PAIN = 					createFluid("pain");
	public static Fluid WASTEFLUID = 			createFluid("wastefluid");
	public static Fluid WASTEGAS = 				createFluid("wastegas");
	public static Fluid GASOLINE = 				createFluid("gasoline");
	public static Fluid EXPERIENCE = 			createFluid("experience");
	public static Fluid ENDERJUICE = 			createFluid("ender");

	//Block fluids
	public static Fluid TOXIC_FLUID = new ToxicFluid("toxic_fluid").setDensity(2500).setViscosity(2000).setTemperature(70+273);
	public static Fluid RADWATER_FLUID = new RadWaterFluid("radwater_fluid").setDensity(1000);
	public static Fluid MUD_FLUID = new MudFluid().setDensity(2500).setViscosity(3000).setLuminosity(5).setTemperature(1773);
	public static Fluid SCHRABIDIC = new SchrabidicFluid("schrabidic").setDensity(31200).setViscosity(500);
	public static Fluid CORIUM_FLUID = new CoriumFluid().setDensity(31200).setViscosity(2000).setTemperature(3000+273);
	public static Fluid VOLCANIC_LAVA_FLUID = new VolcanicFluid().setLuminosity(15).setDensity(3000).setViscosity(3000).setTemperature(1300+273);

	public static void init() {

		registerOrGet(SPENTSTEAM,"spentsteam");
		registerOrGet(STEAM,"steam");
		registerOrGet(HOTSTEAM,"hotsteam");
		registerOrGet(SUPERHOTSTEAM,"superhotsteam");
		registerOrGet(ULTRAHOTSTEAM,"ultrahotsteam");
		registerOrGet(COOLANT,"coolant");
		registerOrGet(HOTCOOLANT,"hotcoolant");
		registerOrGet(PERFLUOROMETHYL,"perfluoromethyl");

		registerOrGet(HEAVYWATER,"heavywater");
		registerOrGet(DEUTERIUM,"deuterium");
		registerOrGet(TRITIUM,"tritium");

		registerOrGet(OIL,"oil");
		registerOrGet(HOTOIL,"hotoil");
		registerOrGet(CRACKOIL,"crackoil");
		registerOrGet(HOTCRACKOIL,"hotcrackoil");
		registerOrGet(OIL_DS,"oil_ds");
		registerOrGet(HOTOIL_DS,"hotoil_ds");
		registerOrGet(CRACKOIL_DS,"crackoil_ds");
		registerOrGet(HOTCRACKOIL_DS,"hotcrackoil_ds");
		registerOrGet(OIL_COKER,"oil_coker");
		
		registerOrGet(HEAVYOIL,"heavyoil");
		registerOrGet(HEAVYOIL_VACUUM,"heavyoil_vacuum");
		registerOrGet(BITUMEN,"bitumen");
		registerOrGet(SMEAR,"smear");
		registerOrGet(HEATINGOIL,"heatingoil");
		registerOrGet(HEATINGOIL_VACUUM,"heatingoil_vacuum");

		registerOrGet(RECLAIMED,"reclaimed");
		registerOrGet(PETROIL,"petroil");

		registerOrGet(FRACKSOL,"fracksol");

		registerOrGet(LUBRICANT,"lubricant");

		registerOrGet(NAPHTHA,"naphtha");
		registerOrGet(NAPHTHA_CRACK,"naphtha_crack");
		registerOrGet(NAPHTHA_DS,"naphtha_ds");
		registerOrGet(NAPHTHA_COKER,"naphtha_coker");
		
		registerOrGet(DIESEL,"diesel");
		registerOrGet(DIESEL_CRACK,"diesel_crack");
		registerOrGet(DIESEL_REFORM,"diesel_reform");
		registerOrGet(DIESEL_CRACK_REFORM,"diesel_crack_reform");

		registerOrGet(LIGHTOIL,"lightoil");
		registerOrGet(LIGHTOIL_CRACK,"lightoil_crack");
		registerOrGet(LIGHTOIL_DS,"lightoil_ds");
		registerOrGet(LIGHTOIL_VACUUM,"lightoil_vacuum");
		
		registerOrGet(KEROSENE,"kerosene");
		registerOrGet(KEROSENE_REFORM,"kerosene_reform");

		registerOrGet(GAS,"gas");
		registerOrGet(GAS_COKER,"gas_coker");
		registerOrGet(PETROLEUM,"petroleum");

		registerOrGet(AROMATICS,"aromatics");
		registerOrGet(UNSATURATEDS,"unsaturateds");
		registerOrGet(XYLENE,"xylene");

		registerOrGet(CHLORINE, "chlorine");
		registerOrGet(PHOSGENE, "phosgene");
		registerOrGet(WOODOIL, "woodoil");
		registerOrGet(COALCREOSOTE, "coalcreosote");
		registerOrGet(COALOIL, "coaloil");
		registerOrGet(COALGAS, "coalgas");
		registerOrGet(COALGAS_LEADED, "coalgas_leaded");
		registerOrGet(PETROIL_LEADED, "petroil_leaded");
		registerOrGet(GASOLINE_LEADED, "gasoline_leaded");
		registerOrGet(SYNGAS, "syngas");
		registerOrGet(IONGEL, "iongel");
		
		registerOrGet(REFORMATE, "reformate");
		registerOrGet(REFORMGAS, "reformgas");

		registerOrGet(BIOGAS, "biogas");
		registerOrGet(BIOFUEL, "biofuel");
		registerOrGet(SOURGAS, "sourgas");

		registerOrGet(ETHANOL, "ethanol");
		registerOrGet(FISHOIL, "fishoil");
		registerOrGet(SUNFLOWEROIL, "sunfloweroil");
		registerOrGet(COLLOID, "colloid");

		registerOrGet(NITAN, "nitan");

		registerOrGet(UF6, "uf6");
		registerOrGet(PUF6, "puf6");
		registerOrGet(SAS3, "sas3");

		registerOrGet(AMAT, "amat");
		registerOrGet(ASCHRAB, "aschrab");

		registerOrGet(ACID, "acid");
		registerOrGet(SULFURIC_ACID, "sulfuric_acid");
		registerOrGet(NITRIC_ACID, "nitric_acid");
		registerOrGet(SOLVENT, "solvent");
		registerOrGet(RADIOSOLVENT, "radiosolvent");
		registerOrGet(NITROGLYCERIN, "nitroglycerin");

		registerOrGet(LIQUID_OSMIRIDIUM, "liquid_osmiridium");
		registerOrGet(WATZ, "watz");
		registerOrGet(CRYOGEL, "cryogel");

		registerOrGet(HYDROGEN, "hydrogen");
		registerOrGet(OXYGEN, "oxygen");
		registerOrGet(XENON, "xenon");
		registerOrGet(BALEFIRE, "balefire");

		registerOrGet(MERCURY, "mercury");

		registerOrGet(PLASMA_DT, "plasma_dt");
		registerOrGet(PLASMA_HD, "plasma_hd");
		registerOrGet(PLASMA_HT, "plasma_ht");
		registerOrGet(PLASMA_PUT, "plasma_put");
		registerOrGet(PLASMA_MX, "plasma_xm");
		registerOrGet(PLASMA_BF, "plasma_bf");


		registerOrGet(IONGEL, "iongel");
		registerOrGet(UU_MATTER, "ic2uu_matter");
		
		registerOrGet(PAIN,"pain");
		registerOrGet(WASTEFLUID,"wastefluid");
		registerOrGet(WASTEGAS,"wastegas");
		registerOrGet(GASOLINE,"gasoline");
		registerOrGet(EXPERIENCE,"experience");
		registerOrGet(ENDERJUICE,"ender");

		registerOrGet(TOXIC_FLUID,"toxic_fluid");
		registerOrGet(RADWATER_FLUID,"radwater_fluid");
		registerOrGet(MUD_FLUID,"mud_fluid");
		registerOrGet(SCHRABIDIC,"schrabidic");
		registerOrGet(CORIUM_FLUID,"corium_fluid");
		registerOrGet(VOLCANIC_LAVA_FLUID,"volcanic_lava_fluid");

		ModBlocks.toxic_block = new ToxicBlock(ModForgeFluids.TOXIC_FLUID, ModBlocks.fluidtoxic, ModDamageSource.radiation, "toxic_block").setResistance(500F);
		ModBlocks.radwater_block = new RadWaterBlock(ModForgeFluids.RADWATER_FLUID, ModBlocks.fluidradwater, ModDamageSource.radiation, "radwater_block").setResistance(500F);
		ModBlocks.mud_block = new MudBlock(ModForgeFluids.MUD_FLUID, ModBlocks.fluidmud, ModDamageSource.mudPoisoning, "mud_block").setResistance(500F);
		ModBlocks.schrabidic_block = new SchrabidicBlock(SCHRABIDIC, ModBlocks.fluidschrabidic.setReplaceable(), ModDamageSource.radiation, "schrabidic_block").setResistance(500F);
		ModBlocks.corium_block = new CoriumBlock(CORIUM_FLUID, ModBlocks.fluidcorium, "corium_block").setResistance(500F);
		ModBlocks.volcanic_lava_block = new VolcanicBlock(VOLCANIC_LAVA_FLUID, ModBlocks.fluidvolcanic, "volcanic_lava_block").setResistance(500F);
		TOXIC_FLUID.setBlock(ModBlocks.toxic_block);
		RADWATER_FLUID.setBlock(ModBlocks.radwater_block);
		MUD_FLUID.setBlock(ModBlocks.mud_block);
		SCHRABIDIC.setBlock(ModBlocks.schrabidic_block);
		CORIUM_FLUID.setBlock(ModBlocks.corium_block);
		VOLCANIC_LAVA_FLUID.setBlock(ModBlocks.volcanic_lava_block);
		FluidRegistry.addBucketForFluid(TOXIC_FLUID);
		FluidRegistry.addBucketForFluid(RADWATER_FLUID);
		FluidRegistry.addBucketForFluid(MUD_FLUID);
		FluidRegistry.addBucketForFluid(SCHRABIDIC);
		FluidRegistry.addBucketForFluid(CORIUM_FLUID);
		FluidRegistry.addBucketForFluid(VOLCANIC_LAVA_FLUID);
	}

	//Stupid forge reads a bunch of default fluids from NBT when the world loads, which screws up my logic for replacing my fluids with fluids from other mods.
	//Forge does this in a place with apparently no events surrounding it. It calls a method in the mod container, but I've
	//been searching for an hour now and I have found no way to make your own custom mod container.
	//Would it have killed them to add a simple event there?!?
	public static void setFromRegistry() {
		for(Map.Entry<Fluid, String> entry : noBlockFluids.entrySet()) {
			loadFluid(entry.getKey(), entry.getValue());
		}
	}

	public static Fluid createFluid(String name){
		noBlockFluidNames.add(name);
		return new Fluid(name, new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/"+name), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/"+name), null, Color.WHITE);
	}

	public static Fluid createFluidFlowing(String name){
		noBlockFluidNames.add(name+"_still");
		noBlockFluidNames.add(name+"_flowing");
		return new Fluid(name, new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/"+name+"_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/"+name+"_flowing"), null, Color.WHITE);
	}

	public static void loadFluid(Fluid f, String name){
		f = FluidRegistry.getFluid(name);
	}

	public static void registerOrGet(Fluid f, String name){
		if(!FluidRegistry.registerFluid(f)) {
			f = FluidRegistry.getFluid(name);
			noBlockFluids.put(f, name);
		}
	}

	@SubscribeEvent
	public static void worldLoad(WorldEvent.Load evt) {
		setFromRegistry();
	}

	public static void registerFluidColors(){
		for(Fluid f : FluidRegistry.getRegisteredFluids().values()){
			fluidColors.put(f, FFUtils.getColorFromFluid(f));
		}
	}

	public static int getFluidColor(Fluid f){
		if(f == null)
			return 0;
		Integer color = fluidColors.get(f);
		if(color == null)
			return 0xFFFFFF;
		return color;
	}
}
