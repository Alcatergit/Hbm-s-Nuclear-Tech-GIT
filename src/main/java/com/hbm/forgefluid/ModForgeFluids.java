package com.hbm.forgefluid;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.fluid.*;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;
import com.leafia.contents.blockfluids.fluoride.BlockLiquidFluoride;
import com.leafia.contents.blockfluids.fluoride.FluorideFluid;
import com.leafia.dev.fluids.LeafiaFluid;
import net.minecraft.block.material.Material;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.*;

@Mod.EventBusSubscriber(modid = RefStrings.MODID)
public class ModForgeFluids {

	public static Set<String> noBlockFluidNames = new HashSet<>();
	public static HashMap<Fluid, String> noBlockFluids = new HashMap<Fluid, String>();
	public static HashMap<Fluid, Integer> fluidColors = new HashMap<Fluid, Integer>();

	public static Fluid SPENTSTEAM = createFluid("spentsteam").setTemperature(40 + 273);
	public static Fluid STEAM = createFluid("steam").setTemperature(100 + 273).setDensity(1200);
	public static Fluid HOTSTEAM = createFluid("hotsteam").setTemperature(300 + 273).setDensity(3000);
	public static Fluid SUPERHOTSTEAM = createFluid("superhotsteam").setTemperature(450 + 273).setDensity(4500);
	public static Fluid ULTRAHOTSTEAM = createFluid("ultrahotsteam").setTemperature(600 + 273).setDensity(6000);
	public static Fluid DEATHSTEAM = createFluid("deathsteam","ultrahotsteam").setColor(Color.RED).setTemperature(900+273).setDensity(9000/2);
	// new Fluid\(\"(\w+)\"[^)]*.[^)]*.[^)]*.
	// createFluid("$1")
	public static Fluid COOLANT = createFluid("coolant");//.setTemperature(203);
	public static Fluid HOTCOOLANT = createFluid("hotcoolant").setTemperature(400 + 273);
	public static Fluid MALCOOLANT = createFluid("malcoolant","ultrahotsteam").setColor(Color.RED).setTemperature(1000 + 273).setGaseous(true).setDensity(10000);

	public static Fluid PERFLUOROMETHYL = createFluid("perfluoromethyl").setTemperature(15 + 273);

	public static Fluid HEAVYWATER = createFluid("heavywater");
	public static Fluid DEUTERIUM = createFluid("deuterium");
	public static Fluid TRITIUM = createFluid("tritium");

	public static Fluid OIL = createFluid("oil");
	public static Fluid HOTOIL = createFluid("hotoil").setTemperature(350+273);
	public static Fluid CRACKOIL = createFluid("crackoil");
	public static Fluid HOTCRACKOIL = createFluid("hotcrackoil").setTemperature(350+273);
	public static Fluid OIL_DS = createFluid("oil_ds");
	public static Fluid HOTOIL_DS = createFluid("hotoil_ds");
	public static Fluid CRACKOIL_DS = createFluid("crackoil_ds");
	public static Fluid HOTCRACKOIL_DS = createFluid("hotcrackoil_ds");
	public static Fluid OIL_COKER = createFluid("oil_coker");

	public static Fluid HEAVYOIL = createFluid("heavyoil");
	public static Fluid HEAVYOIL_VACUUM = createFluid("heavyoil_vacuum");
	public static Fluid BITUMEN = createFluid("bitumen");
	public static Fluid SMEAR = createFluid("smear");
	public static Fluid HEATINGOIL = createFluid("heatingoil");
	public static Fluid HEATINGOIL_VACUUM = createFluid("heatingoil_vacuum");

	public static Fluid RECLAIMED = createFluid("reclaimed");
	public static Fluid PETROIL = createFluid("petroil");

	public static Fluid FRACKSOL = createFluid("fracksol");
	//Drillgon200: Bruh I spelled this wrong, too.
	public static Fluid LUBRICANT = createFluid("lubricant");

	//Yes yes I know, I spelled 'naphtha' wrong.
	public static Fluid NAPHTHA = createFluid("naphtha");
	public static Fluid NAPHTHA_CRACK = createFluid("naphtha_crack");
	public static Fluid NAPHTHA_DS = createFluid("naphtha_ds");
	public static Fluid NAPHTHA_COKER = createFluid("naphtha_coker");

	public static Fluid DIESEL = createFluid("diesel");
	public static Fluid DIESEL_CRACK = createFluid("diesel_crack");
	public static Fluid DIESEL_REFORM = createFluid("diesel_reform");
	public static Fluid DIESEL_CRACK_REFORM = createFluid("diesel_crack_reform");

	public static Fluid LIGHTOIL = createFluid("lightoil");
	public static Fluid LIGHTOIL_CRACK = createFluid("lightoil_crack");
	public static Fluid LIGHTOIL_DS = createFluid("lightoil_ds");
	public static Fluid LIGHTOIL_VACUUM = createFluid("lightoil_vacuum");
	public static Fluid KEROSENE = createFluid("kerosene");
	public static Fluid KEROSENE_REFORM = createFluid("kerosene_reform");

	public static Fluid GAS = createFluid("gas").setTemperature(111);
	public static Fluid GAS_COKER = createFluid("gas_coker");
	public static Fluid PETROLEUM = createFluid("petroleum");

	public static Fluid AROMATICS = createFluid("aromatics");
	public static Fluid UNSATURATEDS = createFluid("unsaturateds");
	public static Fluid XYLENE = createFluid("xylene");

	public static Fluid CHLORINE = createFluid("chlorine");
	public static Fluid PHOSGENE = createFluid("phosgene");
	public static Fluid WOODOIL = createFluid("woodoil");
	public static Fluid COALCREOSOTE = createFluid("coalcreosote");
	public static Fluid COALOIL = createFluid("coaloil");
	public static Fluid COALGAS = createFluid("coalgas");
	public static Fluid COALGAS_LEADED = createFluid("coalgas_leaded");
	public static Fluid PETROIL_LEADED = createFluid("petroil_leaded");
	public static Fluid GASOLINE_LEADED = createFluid("gasoline_leaded");
	public static Fluid SYNGAS = createFluid("syngas");

	public static Fluid REFORMATE = createFluid("reformate");
	public static Fluid REFORMGAS = createFluid("reformgas");
	
	public static Fluid BIOGAS = createFluid("biogas");
	public static Fluid BIOFUEL = createFluid("biofuel");
	public static Fluid SOURGAS = 				createFluid("sourgas");

	public static Fluid ETHANOL = createFluid("ethanol");
	public static Fluid FISHOIL = createFluid("fishoil");
	public static Fluid SUNFLOWEROIL = createFluid("sunfloweroil");
	public static Fluid COLLOID = createFluid("colloid");

	public static Fluid NITAN = createFluid("nitan");

	public static Fluid UF6 = createFluid("uf6");
	public static Fluid PUF6 = createFluid("puf6");
	public static Fluid SAS3 = createFluid("sas3");

	public static Fluid AMAT = createFluid("amat");
	public static Fluid ASCHRAB = createFluid("aschrab")
			.addTraits("NTMTraitAntischrab","NTMTraitMagnetic");

	public static Fluid ACID = createFluid("acid");
	public static Fluid SULFURIC_ACID = createFluid("sulfuric_acid");
	public static Fluid NITRIC_ACID = createFluid("nitric_acid");
	public static Fluid SOLVENT = createFluid("solvent");
	public static Fluid RADIOSOLVENT = createFluid("radiosolvent");
	public static Fluid NITROGLYCERIN = createFluid("nitroglycerin");
	
	public static Fluid LIQUID_OSMIRIDIUM = createFluid("liquid_osmiridium").setTemperature(573);
	//public static Fluid WATZ = createFluid("watz").setDensity(2500).setViscosity(3000).setLuminosity(5).setTemperature(2773);
	public static Fluid CRYOGEL = createFluid("cryogel").setTemperature(50);

	public static Fluid HYDROGEN = createFluid("hydrogen").setTemperature(21);
	public static Fluid OXYGEN = createFluid("oxygen").setTemperature(90);
	public static Fluid CARBONDIOXIDE = createFluid("carbondioxide");//.setTemperature(90);
	public static Fluid XENON = createFluid("xenon").setTemperature(163);
	public static Fluid BALEFIRE = createFluid("balefire").setTemperature(15000 + 273);

	public static Fluid MERCURY = createFluid("mercury");

	public static Fluid PLASMA_HD = createFluid("plasma_hd").setTemperature(25000 + 273);
	public static Fluid PLASMA_HT = createFluid("plasma_ht").setTemperature(30000 + 273);
	public static Fluid PLASMA_DT = createFluid("plasma_dt").setTemperature(32500 + 273);
	public static Fluid PLASMA_MX = createFluid("plasma_xm").setTemperature(45000 + 273);
	public static Fluid PLASMA_PUT = createFluid("plasma_put").setTemperature(50000 + 273);
	public static Fluid PLASMA_BF = createFluid("plasma_bf").setTemperature(85000 + 273);

	public static Fluid IONGEL = createFluid("iongel");
	
	public static Fluid UU_MATTER = createFluid("ic2uu_matter").setTemperature(1000000 + 273);

	public static Fluid PAIN = createFluid("pain");
	public static Fluid WASTEFLUID = createFluid("wastefluid");
	public static Fluid WASTEGAS = createFluid("wastegas");
	public static Fluid GASOLINE = createFluid("gasoline");
	public static Fluid EXPERIENCE = createFluid("experience");
	public static Fluid ENDERJUICE = createFluid("ender");
	
	//Block fluids
	public static Fluid TOXIC_FLUID = new ToxicFluid("toxic_fluid").setDensity(2500).setViscosity(2000).setTemperature(70+273);
	public static Fluid RADWATER_FLUID = new RadWaterFluid("radwater_fluid").setDensity(1000);
	public static Fluid MUD_FLUID = new MudFluid().setDensity(2500).setViscosity(3000).setLuminosity(5).setTemperature(1773)
			.setEmptySound(SoundEvents.BLOCK_SLIME_PLACE).setFillSound(SoundEvents.BLOCK_SLIME_FALL);
	public static Fluid SCHRABIDIC = new SchrabidicFluid("schrabidic").setDensity(31200).setViscosity(500);
	public static Fluid CORIUM_FLUID = new CoriumFluid().setDensity(31200).setViscosity(2000).setTemperature(3000)
			.setEmptySound(SoundEvents.ITEM_BUCKET_EMPTY_LAVA).setFillSound(SoundEvents.ITEM_BUCKET_FILL_LAVA);
	public static Fluid VOLCANIC_LAVA_FLUID = new VolcanicFluid().setLuminosity(15).setDensity(3000).setViscosity(3000).setTemperature(1300)
			.setEmptySound(SoundEvents.ITEM_BUCKET_EMPTY_LAVA).setFillSound(SoundEvents.ITEM_BUCKET_FILL_LAVA);
	public static Fluid FLUORIDE = new FluorideFluid("fluoride").setDensity(1000);
	
	public static void init() {
		SPENTSTEAM = registerOrGet(SPENTSTEAM,"spentsteam");
		STEAM = registerOrGet(STEAM,"steam");
		HOTSTEAM = registerOrGet(HOTSTEAM,"hotsteam");
		SUPERHOTSTEAM = registerOrGet(SUPERHOTSTEAM,"superhotsteam");
		ULTRAHOTSTEAM = registerOrGet(ULTRAHOTSTEAM,"ultrahotsteam");
		DEATHSTEAM = registerOrGet(DEATHSTEAM,"deathsteam");
		COOLANT = registerOrGet(COOLANT,"coolant");
		HOTCOOLANT = registerOrGet(HOTCOOLANT,"hotcoolant");
		MALCOOLANT = registerOrGet(MALCOOLANT,"malcoolant");
		PERFLUOROMETHYL = registerOrGet(PERFLUOROMETHYL,"perfluoromethyl");

		HEAVYWATER = registerOrGet(HEAVYWATER,"heavywater");
		DEUTERIUM = registerOrGet(DEUTERIUM,"deuterium");
		TRITIUM = registerOrGet(TRITIUM,"tritium");

		OIL = registerOrGet(OIL,"oil");
		HOTOIL = registerOrGet(HOTOIL,"hotoil");
		CRACKOIL = registerOrGet(CRACKOIL,"crackoil");
		HOTCRACKOIL = registerOrGet(HOTCRACKOIL,"hotcrackoil");
		OIL_DS = registerOrGet(OIL_DS,"oil_ds");
		HOTOIL_DS = registerOrGet(HOTOIL_DS,"hotoil_ds");
		CRACKOIL_DS = registerOrGet(CRACKOIL_DS,"crackoil_ds");
		HOTCRACKOIL_DS = registerOrGet(HOTCRACKOIL_DS,"hotcrackoil_ds");
		OIL_COKER = registerOrGet(OIL_COKER,"oil_coker");

		HEAVYOIL = registerOrGet(HEAVYOIL,"heavyoil");
		HEAVYOIL_VACUUM = registerOrGet(HEAVYOIL_VACUUM,"heavyoil_vacuum");
		BITUMEN = registerOrGet(BITUMEN,"bitumen");
		SMEAR = registerOrGet(SMEAR,"smear");
		HEATINGOIL = registerOrGet(HEATINGOIL,"heatingoil");
		HEATINGOIL_VACUUM = registerOrGet(HEATINGOIL_VACUUM,"heatingoil_vacuum");

		RECLAIMED = registerOrGet(RECLAIMED,"reclaimed");
		PETROIL = registerOrGet(PETROIL,"petroil");

		FRACKSOL = registerOrGet(FRACKSOL,"fracksol");

		LUBRICANT = registerOrGet(LUBRICANT,"lubricant");

		NAPHTHA = registerOrGet(NAPHTHA,"naphtha");
		NAPHTHA_CRACK = registerOrGet(NAPHTHA_CRACK,"naphtha_crack");
		NAPHTHA_DS = registerOrGet(NAPHTHA_DS,"naphtha_ds");
		NAPHTHA_COKER = registerOrGet(NAPHTHA_COKER,"naphtha_coker");

		DIESEL = registerOrGet(DIESEL,"diesel");
		DIESEL_CRACK = registerOrGet(DIESEL_CRACK,"diesel_crack");
		DIESEL_REFORM = registerOrGet(DIESEL_REFORM,"diesel_reform");
		DIESEL_CRACK_REFORM = registerOrGet(DIESEL_CRACK_REFORM,"diesel_crack_reform");

		LIGHTOIL = registerOrGet(LIGHTOIL,"lightoil");
		LIGHTOIL_CRACK = registerOrGet(LIGHTOIL_CRACK,"lightoil_crack");
		LIGHTOIL_DS = registerOrGet(LIGHTOIL_DS,"lightoil_ds");
		LIGHTOIL_VACUUM = registerOrGet(LIGHTOIL_VACUUM,"lightoil_vacuum");

		KEROSENE = registerOrGet(KEROSENE,"kerosene");
		KEROSENE_REFORM = registerOrGet(KEROSENE_REFORM,"kerosene_reform");

		GAS = registerOrGet(GAS,"gas");
		GAS_COKER = registerOrGet(GAS_COKER,"gas_coker");
		PETROLEUM = registerOrGet(PETROLEUM,"petroleum");

		AROMATICS = registerOrGet(AROMATICS,"aromatics");
		UNSATURATEDS = registerOrGet(UNSATURATEDS,"unsaturateds");
		XYLENE = registerOrGet(XYLENE,"xylene");

		CHLORINE = registerOrGet(CHLORINE, "chlorine");
		PHOSGENE = registerOrGet(PHOSGENE, "phosgene");
		WOODOIL = registerOrGet(WOODOIL, "woodoil");
		COALCREOSOTE = registerOrGet(COALCREOSOTE, "coalcreosote");
		COALOIL = registerOrGet(COALOIL, "coaloil");
		COALGAS = registerOrGet(COALGAS, "coalgas");
		COALGAS_LEADED = registerOrGet(COALGAS_LEADED, "coalgas_leaded");
		PETROIL_LEADED = registerOrGet(PETROIL_LEADED, "petroil_leaded");
		GASOLINE_LEADED = registerOrGet(GASOLINE_LEADED, "gasoline_leaded");
		SYNGAS = registerOrGet(SYNGAS, "syngas");
		IONGEL = registerOrGet(IONGEL, "iongel");

		REFORMATE = registerOrGet(REFORMATE, "reformate");
		REFORMGAS = registerOrGet(REFORMGAS, "reformgas");

		BIOGAS = registerOrGet(BIOGAS, "biogas");
		BIOFUEL = registerOrGet(BIOFUEL, "biofuel");
		SOURGAS = registerOrGet(SOURGAS, "sourgas");

		ETHANOL = registerOrGet(ETHANOL, "ethanol");
		FISHOIL = registerOrGet(FISHOIL, "fishoil");
		SUNFLOWEROIL = registerOrGet(SUNFLOWEROIL, "sunfloweroil");
		COLLOID = registerOrGet(COLLOID, "colloid");

		NITAN = registerOrGet(NITAN, "nitan");

		UF6 = registerOrGet(UF6, "uf6");
		PUF6 = registerOrGet(PUF6, "puf6");
		SAS3 = registerOrGet(SAS3, "sas3");

		AMAT = registerOrGet(AMAT, "amat");
		ASCHRAB = registerOrGet(ASCHRAB, "aschrab");

		ACID = registerOrGet(ACID, "acid");
		SULFURIC_ACID = registerOrGet(SULFURIC_ACID, "sulfuric_acid");
		NITRIC_ACID = registerOrGet(NITRIC_ACID, "nitric_acid");
		SOLVENT = registerOrGet(SOLVENT, "solvent");
		RADIOSOLVENT = registerOrGet(RADIOSOLVENT, "radiosolvent");
		NITROGLYCERIN = registerOrGet(NITROGLYCERIN, "nitroglycerin");

		LIQUID_OSMIRIDIUM = registerOrGet(LIQUID_OSMIRIDIUM, "liquid_osmiridium");
		//WATZ = registerOrGet(WATZ, "watz");
		CRYOGEL = registerOrGet(CRYOGEL, "cryogel");

		HYDROGEN = registerOrGet(HYDROGEN, "hydrogen");
		OXYGEN = registerOrGet(OXYGEN, "oxygen");
		CARBONDIOXIDE = registerOrGet(CARBONDIOXIDE, "carbondioxide");
		XENON = registerOrGet(XENON, "xenon");
		BALEFIRE = registerOrGet(BALEFIRE, "balefire");

		MERCURY = registerOrGet(MERCURY, "mercury");

		PLASMA_DT = registerOrGet(PLASMA_DT, "plasma_dt");
		PLASMA_HD = registerOrGet(PLASMA_HD, "plasma_hd");
		PLASMA_HT = registerOrGet(PLASMA_HT, "plasma_ht");
		PLASMA_PUT = registerOrGet(PLASMA_PUT, "plasma_put");
		PLASMA_MX = registerOrGet(PLASMA_MX, "plasma_xm");
		PLASMA_BF = registerOrGet(PLASMA_BF, "plasma_bf");


		IONGEL = registerOrGet(IONGEL, "iongel");
		UU_MATTER = registerOrGet(UU_MATTER, "ic2uu_matter");

		PAIN = registerOrGet(PAIN,"pain");
		WASTEFLUID = registerOrGet(WASTEFLUID,"wastefluid");
		WASTEGAS = registerOrGet(WASTEGAS,"wastegas");
		GASOLINE = registerOrGet(GASOLINE,"gasoline");
		EXPERIENCE = registerOrGet(EXPERIENCE,"experience");
		ENDERJUICE = registerOrGet(ENDERJUICE,"ender");

		TOXIC_FLUID = registerOrGet(TOXIC_FLUID,"toxic_fluid");
		RADWATER_FLUID = registerOrGet(RADWATER_FLUID,"radwater_fluid");
		MUD_FLUID = registerOrGet(MUD_FLUID,"mud_fluid");
		SCHRABIDIC = registerOrGet(SCHRABIDIC,"schrabidic");
		CORIUM_FLUID = registerOrGet(CORIUM_FLUID,"corium_fluid");
		VOLCANIC_LAVA_FLUID = registerOrGet(VOLCANIC_LAVA_FLUID,"volcanic_lava_fluid");
		FLUORIDE = registerOrGet(FLUORIDE,"fluoride");

		ModBlocks.toxic_block = new ToxicBlock(ModForgeFluids.TOXIC_FLUID, ModBlocks.fluidtoxic, ModDamageSource.radiation, "toxic_block").setResistance(500F);
		ModBlocks.radwater_block = new RadWaterBlock(ModForgeFluids.RADWATER_FLUID,Material.WATER, ModDamageSource.radiation, "radwater_block").setResistance(500F);
		ModBlocks.mud_block = new MudBlock(MUD_FLUID, ModDamageSource.mudPoisoning, "mud_block", 0x98F500).setResistance(500F);
		ModBlocks.schrabidic_block = new SchrabidicBlock(SCHRABIDIC, ModBlocks.fluidschrabidic.setReplaceable(), ModDamageSource.radiation, "schrabidic_block").setResistance(500F);
		ModBlocks.corium_block = new BlockLiquidCorium(CORIUM_FLUID, ModBlocks.fluidcorium, "corium_block").setResistance(500F);
		ModBlocks.volcanic_lava_block = new VolcanicBlock(VOLCANIC_LAVA_FLUID, ModBlocks.fluidvolcanic, "volcanic_lava_block").setResistance(500F);
		ModBlocks.fluoride_block = new BlockLiquidFluoride(FLUORIDE, Material.LAVA, "fluoride_fluid");

		TOXIC_FLUID.setBlock(ModBlocks.toxic_block);
		RADWATER_FLUID.setBlock(ModBlocks.radwater_block);
		MUD_FLUID.setBlock(ModBlocks.mud_block);
		SCHRABIDIC.setBlock(ModBlocks.schrabidic_block);
		CORIUM_FLUID.setBlock(ModBlocks.corium_block);
		VOLCANIC_LAVA_FLUID.setBlock(ModBlocks.volcanic_lava_block);
		FLUORIDE.setBlock(ModBlocks.fluoride_block);
		FluidRegistry.addBucketForFluid(TOXIC_FLUID);
		FluidRegistry.addBucketForFluid(RADWATER_FLUID);
		FluidRegistry.addBucketForFluid(MUD_FLUID);
		FluidRegistry.addBucketForFluid(SCHRABIDIC);
		FluidRegistry.addBucketForFluid(CORIUM_FLUID);
		FluidRegistry.addBucketForFluid(VOLCANIC_LAVA_FLUID);
		FluidRegistry.addBucketForFluid(FLUORIDE);
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

	public static LeafiaFluid createFluid(String name){
		noBlockFluidNames.add(name);
		return new LeafiaFluid(name, new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/"+name), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/"+name), null, Color.WHITE);
	}

	public static LeafiaFluid createFluid(String name,String texture){
		noBlockFluidNames.add(name);
		return new LeafiaFluid(name, new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/"+texture), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/"+texture), null, Color.WHITE);
	}

	public static LeafiaFluid createFluid(String name,String still,String flowing){
		noBlockFluidNames.add(still);
		noBlockFluidNames.add(flowing);
		return new LeafiaFluid(name, new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/"+still), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/"+flowing), null, Color.WHITE);
	}

	public static LeafiaFluid createFluidFlowing(String name){
		noBlockFluidNames.add(name+"_still");
		noBlockFluidNames.add(name+"_flowing");
		return new LeafiaFluid(name, new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/"+name+"_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/"+name+"_flowing"), null, Color.WHITE);
	}

	public static void loadFluid(Fluid f, String name){
		f = FluidRegistry.getFluid(name);
	}

	public static Fluid registerOrGet(Fluid f, String name){
		if(!FluidRegistry.registerFluid(f)) {
			f = FluidRegistry.getFluid(name);
			noBlockFluids.put(f, name);
		}
		return f;
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
