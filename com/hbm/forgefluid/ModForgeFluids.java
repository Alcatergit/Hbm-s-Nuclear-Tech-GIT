package com.hbm.forgefluid;

import java.awt.Color;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.fluid.MudBlock;
import com.hbm.blocks.fluid.MudFluid;
import com.hbm.blocks.fluid.ToxicBlock;
import com.hbm.blocks.fluid.ToxicFluid;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModForgeFluids {

	//TODO temps and stuff
	public static final Fluid steam = new Fluid("hbmsteam", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/steam_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/steam_flowing"), null, Color.WHITE);
	public static final Fluid hotsteam = new Fluid("hbmhotsteam", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hotsteam_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hotsteam_flowing"), null, Color.WHITE);
	public static final Fluid superhotsteam = new Fluid("hbmsuperhotsteam", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/superhotsteam_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/superhotsteam_flowing"), null, Color.WHITE);
	public static final Fluid coolant = new Fluid("hbmcoolant", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/coolant_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/coolant_flowing"), null, Color.WHITE);
	
	public static final Fluid deuterium = new Fluid("hbmdeuterium", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/deuterium_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/deuterium_flowing"), null, Color.WHITE);
	public static final Fluid tritium = new Fluid("hbmtritium", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/tritium_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/tritium_flowing"), null, Color.WHITE);
	
	public static final Fluid oil = new Fluid("hbmoil", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/oil_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/oil_flowing"), null, Color.WHITE);
	public static final Fluid hotoil = new Fluid("hbmhotoil", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hotoil_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hotoil_flowing"), null, Color.WHITE);

	public static final Fluid heavyoil = new Fluid("hbmheavyoil", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/heavyoil_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/heavyoil_flowing"), null, Color.WHITE);
	public static final Fluid bitumen = new Fluid("hbmbitumen", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/bitumen_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/bitumen_flowing"), null, Color.WHITE);
	public static final Fluid smear = new Fluid("hbmsmear", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/smear_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/smear_flowing"), null, Color.WHITE);
	public static final Fluid heatingoil = new Fluid("hbmheatingoil", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/heatingoil_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/heatingoil_flowing"), null, Color.WHITE);
	
	public static final Fluid reclaimed = new Fluid("hbmreclaimed", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/reclaimed_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/reclaimed_flowing"), null, Color.WHITE);
	public static final Fluid petroil = new Fluid("hbmpetroil", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/petroil_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/petroil_flowing"), null, Color.WHITE);
	
	public static final Fluid lubricant = new Fluid("hbmlubircant", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/lubricant_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/lubricant_flowing"), null, Color.WHITE);
	
	//Yes yes I know, I spelled 'naphtha' wrong.
	public static final Fluid naphtha = new Fluid("hbmnaphtha", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/napatha_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/napatha_flowing"), null, Color.WHITE);
	public static final Fluid diesel = new Fluid("hbmdiesel", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/diesel_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/diesel_flowing"), null, Color.WHITE);
	
	public static final Fluid lightoil = new Fluid("hbmlightoil", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/lightoil_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/lightoil_flowing"), null, Color.WHITE);
	public static final Fluid kerosene = new Fluid("hbmkerosene", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/kerosene_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/kerosene_flowing"), null, Color.WHITE);
	
	public static final Fluid gas = new Fluid("hbmgas", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/gas_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/gas_flowing"), null, Color.WHITE);
	public static final Fluid petroleum = new Fluid("hbmpetroleum", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/petroleum_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/petroleum_flowing"), null, Color.WHITE);
	
	public static final Fluid biogas = new Fluid("hbmbiogas", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/biogas_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/biogas_flowing"), null, Color.WHITE);
	public static final Fluid biofuel = new Fluid("hbmbiofuel", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/biofuel_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/biofuel_flowing"), null, Color.WHITE);
	
	public static final Fluid nitan = new Fluid("hbmnitan", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/nitan_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/nitan_flowing"), null, Color.WHITE);
	
	public static final Fluid uf6 = new Fluid("hbmuf6", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/uf6_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/uf6_flowing"), null, Color.WHITE);
	public static final Fluid puf6 = new Fluid("hbmpuf6", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/puf6_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/puf6_flowing"), null, Color.WHITE);
	public static final Fluid sas3 = new Fluid("hbmsas3", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/sas3_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/sas3_flowing"), null, Color.WHITE);
	
	public static final Fluid amat = new Fluid("hbmamat", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/amat_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/amat_flowing"), null, Color.WHITE);
	public static final Fluid aschrab = new Fluid("hbmaschrab", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/aschrab_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/aschrab_flowing"), null, Color.WHITE);
	
	public static final Fluid acid = new Fluid("hbmacid", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/acid_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/acid_flowing"), null, Color.WHITE);
	public static final Fluid watz = new Fluid("hbmwatz", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/watz_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/watz_flowing"), null, Color.WHITE);
	public static final Fluid cryogel = new Fluid("hbmcryogel", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/cryogel_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/cryogel_flowing"), null, Color.WHITE);
	
	public static final Fluid hydrogen = new Fluid("hbmhydrogen", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hydrogen_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hydrogen_flowing"), null, Color.WHITE);
	public static final Fluid oxygen = new Fluid("hbmoxygen", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/oxygen_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/oxygen_flowing"), null, Color.WHITE);
	public static final Fluid xenon = new Fluid("hbmxenon", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/xenon_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/xenon_flowing"), null, Color.WHITE);
	public static final Fluid balefire = new Fluid("hbmbalefire", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/balefire_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/balefire_flowing"), null, Color.WHITE);
	
	//Block fluids
	public static final Fluid toxic_fluid = new ToxicFluid().setDensity(2500).setViscosity(2000).setLuminosity(15).setTemperature(2773).setBlock(ModBlocks.toxic_block);
	public static final Fluid mud_fluid = new MudFluid().setDensity(2500).setViscosity(3000).setLuminosity(5).setTemperature(2773).setBlock(ModBlocks.mud_block);
	
	public static void init(){
		FluidRegistry.registerFluid(steam);
		FluidRegistry.registerFluid(hotsteam);
		FluidRegistry.registerFluid(superhotsteam);
		FluidRegistry.registerFluid(coolant);
		
		FluidRegistry.registerFluid(deuterium);
		FluidRegistry.registerFluid(tritium);
		
		FluidRegistry.registerFluid(oil);
		FluidRegistry.registerFluid(hotoil);
		
		FluidRegistry.registerFluid(heavyoil);
		FluidRegistry.registerFluid(bitumen);
		FluidRegistry.registerFluid(smear);
		FluidRegistry.registerFluid(heatingoil);
		
		FluidRegistry.registerFluid(reclaimed);
		FluidRegistry.registerFluid(petroil);
		
		FluidRegistry.registerFluid(lubricant);
		
		FluidRegistry.registerFluid(naphtha);
		FluidRegistry.registerFluid(diesel);
		
		FluidRegistry.registerFluid(lightoil);
		FluidRegistry.registerFluid(kerosene);
		
		FluidRegistry.registerFluid(gas);
		FluidRegistry.registerFluid(petroleum);
		
		FluidRegistry.registerFluid(biogas);
		FluidRegistry.registerFluid(biofuel);
		
		FluidRegistry.registerFluid(nitan);
		
		FluidRegistry.registerFluid(uf6);
		FluidRegistry.registerFluid(puf6);
		FluidRegistry.registerFluid(sas3);
		
		FluidRegistry.registerFluid(amat);
		FluidRegistry.registerFluid(aschrab);
		
		FluidRegistry.registerFluid(acid);
		FluidRegistry.registerFluid(watz);
		FluidRegistry.registerFluid(cryogel);
		
		FluidRegistry.registerFluid(hydrogen);
		FluidRegistry.registerFluid(oxygen);
		FluidRegistry.registerFluid(xenon);
		FluidRegistry.registerFluid(balefire);
		
		FluidRegistry.registerFluid(toxic_fluid);
		FluidRegistry.registerFluid(mud_fluid);
		ModBlocks.toxic_block = new ToxicBlock(ModForgeFluids.toxic_fluid, ModBlocks.fluidtoxic, ModDamageSource.radiation, "toxic_block").setResistance(500F);
		ModBlocks.mud_block = new MudBlock(ModForgeFluids.mud_fluid, ModBlocks.fluidmud, ModDamageSource.mudPoisoning, "mud_block").setResistance(500F);
	}
	
}
