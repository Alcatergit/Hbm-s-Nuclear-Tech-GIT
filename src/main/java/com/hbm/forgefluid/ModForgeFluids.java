package com.hbm.forgefluid;

import java.awt.Color;
import java.util.HashMap;

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

	public static HashMap<Fluid, Integer> fluidColors = new HashMap<Fluid, Integer>();
	
	public static Fluid spentsteam = 			createFluid("spentsteam").setTemperature(40 + 273);
	public static Fluid steam = 				createFluid("steam").setTemperature(100 + 273);
	public static Fluid hotsteam = 				createFluid("hotsteam").setTemperature(300 + 273);
	public static Fluid superhotsteam = 		createFluid("superhotsteam").setTemperature(450 + 273);
	public static Fluid ultrahotsteam = 		createFluid("ultrahotsteam").setTemperature(600 + 273);
	public static Fluid coolant = 				createFluid("coolant").setTemperature(203);
	public static Fluid hotcoolant = 			createFluid("hotcoolant").setTemperature(400 + 273);

	public static Fluid heavywater = 			createFluid("heavywater");
	public static Fluid deuterium = 			createFluid("deuterium");
	public static Fluid tritium = 				createFluid("tritium");

	public static Fluid oil = 					createFluid("oil");
	public static Fluid hotoil = 				createFluid("hotoil").setTemperature(350+273);
	public static Fluid crackoil = 				createFluid("crackoil");
	public static Fluid hotcrackoil = 			createFluid("hotcrackoil").setTemperature(350+273);

	public static Fluid heavyoil = 				createFluid("heavyoil");
	public static Fluid bitumen = 				createFluid("bitumen");
	public static Fluid smear = 				createFluid("smear");
	public static Fluid heatingoil = 			createFluid("heatingoil");

	public static Fluid reclaimed = 			createFluid("reclaimed");
	public static Fluid petroil = 				createFluid("petroil");
	
	public static Fluid fracksol = 				createFluid("fracksol");
	//Drillgon200: Bruh I spelled this wrong, too.
	public static Fluid lubricant = 			createFluid("lubricant");

	//Yes yes I know, I spelled 'naphtha' wrong.
	public static Fluid naphtha = 				createFluid("naphtha");
	public static Fluid diesel = 				createFluid("diesel");

	public static Fluid lightoil = 				createFluid("lightoil");
	public static Fluid kerosene = 				createFluid("kerosene");

	public static Fluid gas = 					createFluid("gas");
	public static Fluid petroleum = 			createFluid("petroleum");

	public static Fluid aromatics = 			createFluid("aromatics");
	public static Fluid unsaturateds = 			createFluid("unsaturateds");

	public static Fluid chlorine =				createFluid("chlorine");
	public static Fluid phosgene =				createFluid("phosgene");
	public static Fluid woodoil =				createFluid("woodoil");
	public static Fluid coalcreosote =			createFluid("coalcreosote");
	public static Fluid coaloil =				createFluid("coaloil");
	public static Fluid coalgas =				createFluid("coalgas");
	public static Fluid coalgas_leaded =		createFluid("coalgas_leaded");
	public static Fluid petroil_leaded =		createFluid("petroil_leaded");
	public static Fluid gasoline_leaded =		createFluid("gasoline_leaded");
	public static Fluid syngas =				createFluid("syngas");

	public static Fluid biogas = 				createFluid("biogas");
	public static Fluid biofuel = 				createFluid("biofuel");

	public static Fluid ethanol = 				createFluid("ethanol");
	public static Fluid fishoil = 				createFluid("fishoil");
	public static Fluid sunfloweroil = 			createFluid("sunfloweroil");
	public static Fluid colloid = 				createFluid("colloid");

	public static Fluid nitan = 				createFluid("nitan");

	public static Fluid uf6 = 					createFluid("uf6");
	public static Fluid puf6 = 					createFluid("puf6");
	public static Fluid sas3 = 					createFluid("sas3");

	public static Fluid amat = 					createFluid("amat");
	public static Fluid aschrab = 				createFluid("aschrab");

	public static Fluid acid = 					createFluid("acid");
	public static Fluid sulfuric_acid = 		createFluid("sulfuric_acid");
	public static Fluid nitric_acid = 			createFluid("nitric_acid");
	public static Fluid solvent = 				createFluid("solvent");
	public static Fluid radiosolvent = 			createFluid("radiosolvent");
	public static Fluid nitroglycerin = 		createFluid("nitroglycerin");
	
	public static Fluid liquid_osmiridium = 	createFluid("liquid_osmiridium").setTemperature(573);
	public static Fluid watz = 					createFluidFlowing("watz").setDensity(2500).setViscosity(3000).setLuminosity(5).setTemperature(2773);
	public static Fluid cryogel = 				createFluid("cryogel").setTemperature(50);

	public static Fluid hydrogen = 				createFluid("hydrogen");
	public static Fluid oxygen = 				createFluid("oxygen");
	public static Fluid xenon = 				createFluid("xenon");
	public static Fluid balefire = 				createFluid("balefire").setTemperature(15000 + 273);

	public static Fluid mercury = 				createFluid("mercury");

	public static Fluid plasma_hd = 			createFluid("plasma_hd").setTemperature(25000 + 273);
	public static Fluid plasma_ht = 			createFluid("plasma_ht").setTemperature(30000 + 273);
	public static Fluid plasma_dt = 			createFluid("plasma_dt").setTemperature(32500 + 273);
	public static Fluid plasma_xm = 			createFluid("plasma_xm").setTemperature(45000 + 273);
	public static Fluid plasma_put = 			createFluid("plasma_put").setTemperature(50000 + 273);
	public static Fluid plasma_bf = 			createFluid("plasma_bf").setTemperature(85000 + 273);

	public static Fluid iongel =				createFluid("iongel");

	public static Fluid uu_matter = 			createFluid("ic2uu_matter").setTemperature(1000000 + 273);

	public static Fluid pain = 					createFluid("pain");
	public static Fluid wastefluid = 			createFluid("wastefluid");
	public static Fluid wastegas = 				createFluid("wastegas");
	public static Fluid gasoline = 				createFluid("gasoline");
	public static Fluid experience = 			createFluid("experience");
	public static Fluid enderjuice = 			createFluid("ender");

	//Block fluids
	public static Fluid toxic_fluid = new ToxicFluid("toxic_fluid").setDensity(2500).setViscosity(2000).setTemperature(70+273);
	public static Fluid radwater_fluid = new RadWaterFluid("radwater_fluid").setDensity(1000);
	public static Fluid mud_fluid = new MudFluid().setDensity(2500).setViscosity(3000).setLuminosity(5).setTemperature(1773);
	public static Fluid schrabidic = new SchrabidicFluid("schrabidic").setDensity(31200).setViscosity(500);
	public static Fluid corium_fluid = new CoriumFluid().setDensity(31200).setViscosity(2000).setTemperature(3000+273);
	public static Fluid volcanic_lava_fluid = new VolcanicFluid().setLuminosity(15).setDensity(3000).setViscosity(3000).setTemperature(1300+273);

	public static Fluid createFluid(String name){
		return new Fluid(name, new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/"+name), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/"+name), null, Color.WHITE);
	}

	public static Fluid createFluidFlowing(String name){
		return new Fluid(name, new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/"+name+"_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/"+name+"_flowing"), null, Color.WHITE);
	}
	
	public static void registerOrGet(Fluid f, String name){
		if(!FluidRegistry.registerFluid(f))
			f = FluidRegistry.getFluid(name);
	}

	public static void init() {
		registerOrGet(spentsteam, "spentsteam");
		registerOrGet(steam,"steam");
		registerOrGet(hotsteam,"hotsteam");
		registerOrGet(superhotsteam,"superhotsteam");
		registerOrGet(ultrahotsteam,"ultrahotsteam");
		registerOrGet(coolant,"coolant");
		registerOrGet(hotcoolant,"hotcoolant");

		registerOrGet(heavywater,"heavywater");
		registerOrGet(deuterium,"deuterium");
		registerOrGet(tritium,"tritium");

		registerOrGet(oil,"oil");
		registerOrGet(hotoil,"hotoil");
		registerOrGet(crackoil,"crackoil");
		registerOrGet(hotcrackoil,"hotcrackoil");

		registerOrGet(heavyoil,"heavyoil");
		registerOrGet(bitumen,"bitumen");
		registerOrGet(smear,"smear");
		registerOrGet(heatingoil,"heatingoil");

		registerOrGet(reclaimed,"reclaimed");
		registerOrGet(petroil,"petroil");

		registerOrGet(fracksol,"fracksol");

		registerOrGet(lubricant,"lubricant");

		registerOrGet(naphtha,"naphtha");
		registerOrGet(diesel,"diesel");

		registerOrGet(lightoil,"lightoil");
		registerOrGet(kerosene,"kerosene");

		registerOrGet(gas,"gas");
		registerOrGet(petroleum,"petroleum");

		registerOrGet(aromatics,"aromatics");
		registerOrGet(unsaturateds,"unsaturateds");

		registerOrGet(biogas,"biogas");
		registerOrGet(biofuel,"biofuel");

		registerOrGet(ethanol,"ethanol");
		registerOrGet(fishoil,"fishoil");
		registerOrGet(sunfloweroil,"sunfloweroil");
		registerOrGet(colloid,"colloid");

		registerOrGet(nitan,"nitan");

		registerOrGet(uf6,"uf6");
		registerOrGet(puf6,"puf6");
		registerOrGet(sas3,"sas3");

		registerOrGet(amat,"amat");
		registerOrGet(aschrab,"aschrab");

		registerOrGet(acid,"acid");
		registerOrGet(sulfuric_acid,"sulfuric_acid");
		registerOrGet(nitric_acid,"nitric_acid");
		registerOrGet(solvent,"solvent");
		registerOrGet(radiosolvent,"radiosolvent");
		registerOrGet(nitroglycerin,"nitroglycerin");
		registerOrGet(liquid_osmiridium,"liquid_osmiridium");
		registerOrGet(watz,"watz");
		registerOrGet(cryogel,"cryogel");

		registerOrGet(hydrogen,"hydrogen");
		registerOrGet(oxygen,"oxygen");
		registerOrGet(xenon,"xenon");
		registerOrGet(balefire,"balefire");

		registerOrGet(mercury,"mercury");

		registerOrGet(plasma_dt,"plasma_dt");
		registerOrGet(plasma_hd,"plasma_hd");
		registerOrGet(plasma_ht,"plasma_ht");
		registerOrGet(plasma_put,"plasma_put");
		registerOrGet(plasma_xm,"plasma_xm");
		registerOrGet(plasma_bf,"plasma_bf");
		registerOrGet(uu_matter,"ic2uu_matter");
		
		registerOrGet(pain,"pain");
		registerOrGet(wastefluid,"wastefluid");
		registerOrGet(wastegas,"wastegas");
		registerOrGet(gasoline,"gasoline");
		registerOrGet(experience,"experience");
		registerOrGet(enderjuice,"ender");

		registerOrGet(chlorine, "chlorine");
		registerOrGet(phosgene, "phosgene");
		registerOrGet(woodoil, "woodoil");
		registerOrGet(coalcreosote, "coalcreosote");
		registerOrGet(coaloil, "coaloil");
		registerOrGet(coalgas, "coalgas");
		registerOrGet(coalgas_leaded, "coalgas_leaded");
		registerOrGet(petroil_leaded, "petroil_leaded");
		registerOrGet(gasoline_leaded, "gasoline_leaded");
		registerOrGet(syngas, "syngas");
		registerOrGet(iongel, "iongel");
		
		registerOrGet(toxic_fluid,"toxic_fluid");
		registerOrGet(radwater_fluid,"radwater_fluid");
		registerOrGet(mud_fluid,"mud_fluid");
		registerOrGet(schrabidic,"schrabidic");
		registerOrGet(corium_fluid,"corium_fluid");
		registerOrGet(volcanic_lava_fluid,"volcanic_lava_fluid");

		ModBlocks.toxic_block = new ToxicBlock(ModForgeFluids.toxic_fluid, ModBlocks.fluidtoxic, ModDamageSource.radiation, "toxic_block").setResistance(500F);
		ModBlocks.radwater_block = new RadWaterBlock(ModForgeFluids.radwater_fluid, ModBlocks.fluidradwater, ModDamageSource.radiation, "radwater_block").setResistance(500F);
		ModBlocks.mud_block = new MudBlock(ModForgeFluids.mud_fluid, ModBlocks.fluidmud, ModDamageSource.mudPoisoning, "mud_block").setResistance(500F);
		ModBlocks.schrabidic_block = new SchrabidicBlock(schrabidic, ModBlocks.fluidschrabidic.setReplaceable(), ModDamageSource.radiation, "schrabidic_block").setResistance(500F);
		ModBlocks.corium_block = new CoriumBlock(corium_fluid, ModBlocks.fluidcorium, "corium_block").setResistance(500F);
		ModBlocks.volcanic_lava_block = new VolcanicBlock(volcanic_lava_fluid, ModBlocks.fluidvolcanic, "volcanic_lava_block").setResistance(500F);
		toxic_fluid.setBlock(ModBlocks.toxic_block);
		radwater_fluid.setBlock(ModBlocks.radwater_block);
		mud_fluid.setBlock(ModBlocks.mud_block);
		schrabidic.setBlock(ModBlocks.schrabidic_block);
		corium_fluid.setBlock(ModBlocks.corium_block);
		volcanic_lava_fluid.setBlock(ModBlocks.volcanic_lava_block);
		FluidRegistry.addBucketForFluid(toxic_fluid);
		FluidRegistry.addBucketForFluid(radwater_fluid);
		FluidRegistry.addBucketForFluid(mud_fluid);
		FluidRegistry.addBucketForFluid(schrabidic);
		FluidRegistry.addBucketForFluid(corium_fluid);
		FluidRegistry.addBucketForFluid(volcanic_lava_fluid);
	}

	//Stupid forge reads a bunch of default fluids from NBT when the world loads, which screws up my logic for replacing my fluids with fluids from other mods.
	//Forge does this in a place with apparently no events surrounding it. It calls a method in the mod container, but I've
	//been searching for an hour now and I have found no way to make your own custom mod container.
	//Would it have killed them to add a simple event there?!?
	public static void setFromRegistry() {
		spentsteam = FluidRegistry.getFluid("spentsteam");
		steam = FluidRegistry.getFluid("steam");
		hotsteam = FluidRegistry.getFluid("hotsteam");
		superhotsteam = FluidRegistry.getFluid("superhotsteam");
		ultrahotsteam = FluidRegistry.getFluid("ultrahotsteam");
		coolant = FluidRegistry.getFluid("coolant");
		hotcoolant = FluidRegistry.getFluid("hotcoolant");

		heavywater = FluidRegistry.getFluid("heavywater");
		deuterium = FluidRegistry.getFluid("deuterium");
		tritium = FluidRegistry.getFluid("tritium");

		oil = FluidRegistry.getFluid("oil");
		hotoil = FluidRegistry.getFluid("hotoil");
		crackoil = FluidRegistry.getFluid("crackoil");
		hotcrackoil = FluidRegistry.getFluid("hotcrackoil");

		heavyoil = FluidRegistry.getFluid("heavyoil");
		bitumen = FluidRegistry.getFluid("bitumen");
		smear = FluidRegistry.getFluid("smear");
		heatingoil = FluidRegistry.getFluid("heatingoil");

		reclaimed = FluidRegistry.getFluid("reclaimed");
		petroil = FluidRegistry.getFluid("petroil");

		fracksol = FluidRegistry.getFluid("fracksol");
		lubricant = FluidRegistry.getFluid("lubricant");

		naphtha = FluidRegistry.getFluid("naphtha");
		diesel = FluidRegistry.getFluid("diesel");

		lightoil = FluidRegistry.getFluid("lightoil");
		kerosene = FluidRegistry.getFluid("kerosene");

		gas = FluidRegistry.getFluid("gas");
		petroleum = FluidRegistry.getFluid("petroleum");

		aromatics = FluidRegistry.getFluid("aromatics");
		unsaturateds = FluidRegistry.getFluid("unsaturateds");

		biogas = FluidRegistry.getFluid("biogas");
		biofuel = FluidRegistry.getFluid("biofuel");

		ethanol = FluidRegistry.getFluid("ethanol");
		fishoil = FluidRegistry.getFluid("fishoil");
		sunfloweroil = FluidRegistry.getFluid("sunfloweroil");
		colloid = FluidRegistry.getFluid("colloid");

		nitan = FluidRegistry.getFluid("nitan");

		uf6 = FluidRegistry.getFluid("uf6");
		puf6 = FluidRegistry.getFluid("puf6");
		sas3 = FluidRegistry.getFluid("sas3");

		amat = FluidRegistry.getFluid("amat");
		aschrab = FluidRegistry.getFluid("aschrab");

		acid = FluidRegistry.getFluid("acid");
		sulfuric_acid = FluidRegistry.getFluid("sulfuric_acid");
		nitric_acid = FluidRegistry.getFluid("nitric_acid");
		solvent = FluidRegistry.getFluid("solvent");
		radiosolvent = FluidRegistry.getFluid("radiosolvent");
		nitroglycerin = FluidRegistry.getFluid("nitroglycerin");
		liquid_osmiridium = FluidRegistry.getFluid("liquid_osmiridium");
		watz = FluidRegistry.getFluid("watz");
		cryogel = FluidRegistry.getFluid("cryogel");

		hydrogen = FluidRegistry.getFluid("hydrogen");
		oxygen = FluidRegistry.getFluid("oxygen");
		xenon = FluidRegistry.getFluid("xenon");
		balefire = FluidRegistry.getFluid("balefire");

		mercury = FluidRegistry.getFluid("mercury");

		plasma_dt = FluidRegistry.getFluid("plasma_dt");
		plasma_hd = FluidRegistry.getFluid("plasma_hd");
		plasma_ht = FluidRegistry.getFluid("plasma_ht");
		plasma_put = FluidRegistry.getFluid("plasma_put");
		plasma_xm = FluidRegistry.getFluid("plasma_xm");
		plasma_bf = FluidRegistry.getFluid("plasma_bf");
		uu_matter = FluidRegistry.getFluid("ic2uu_matter");
		
		pain = FluidRegistry.getFluid("pain");
		wastefluid = FluidRegistry.getFluid("wastefluid");
		wastegas = FluidRegistry.getFluid("wastegas");
		gasoline = FluidRegistry.getFluid("gasoline");
		experience = FluidRegistry.getFluid("experience");
		enderjuice = FluidRegistry.getFluid("ender");

		chlorine = FluidRegistry.getFluid("chlorine");
		phosgene = FluidRegistry.getFluid("phosgene");
		woodoil = FluidRegistry.getFluid("woodoil");
		coalcreosote = FluidRegistry.getFluid("coalcreosote");
		coaloil = FluidRegistry.getFluid("coaloil");
		coalgas = FluidRegistry.getFluid("coalgas");
		coalgas_leaded = FluidRegistry.getFluid("coalgas_leaded");
		petroil_leaded = FluidRegistry.getFluid("petroil_leaded");
		gasoline_leaded = FluidRegistry.getFluid("gasoline_leaded");
		syngas = FluidRegistry.getFluid("syngas");
		iongel = FluidRegistry.getFluid("iongel");

		toxic_fluid = FluidRegistry.getFluid("toxic_fluid");
		radwater_fluid = FluidRegistry.getFluid("radwater_fluid");
		mud_fluid = FluidRegistry.getFluid("mud_fluid");
		schrabidic = FluidRegistry.getFluid("schrabidic");
		corium_fluid = FluidRegistry.getFluid("corium_fluid");
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
