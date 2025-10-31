package com.hbm.forgefluid;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class SpecialContainerFillLists {
	
	//Drillgon200: I don't even know what I'm trying to do here, but hopefully it works.
	public enum EnumCanister {
		EMPTY(null, new ModelResourceLocation(RefStrings.MODID + ":canister_empty", "inventory")),
		DIESEL(ModForgeFluids.DIESEL, new ModelResourceLocation(RefStrings.MODID + ":canister_fuel", "inventory")),
		OIL(ModForgeFluids.OIL, new ModelResourceLocation(RefStrings.MODID + ":canister_oil", "inventory")),
		PETROIL(ModForgeFluids.PETROIL, new ModelResourceLocation(RefStrings.MODID + ":canister_petroil", "inventory")),
		FRACKSOL(ModForgeFluids.FRACKSOL, new ModelResourceLocation(RefStrings.MODID + ":canister_fracksol", "inventory")),
		KEROSENE(ModForgeFluids.KEROSENE, new ModelResourceLocation(RefStrings.MODID + ":canister_kerosene", "inventory")),
		NITAN(ModForgeFluids.NITAN, new ModelResourceLocation(RefStrings.MODID + ":canister_superfuel", "inventory")),
		BIOFUEL(ModForgeFluids.BIOFUEL, new ModelResourceLocation(RefStrings.MODID + ":canister_biofuel", "inventory")),
		CANOLA(ModForgeFluids.LUBRICANT, new ModelResourceLocation(RefStrings.MODID + ":canister_canola", "inventory")),
		REOIL(ModForgeFluids.RECLAIMED, new ModelResourceLocation(RefStrings.MODID + ":canister_reoil", "inventory")),
		HEAVYOIL(ModForgeFluids.HEAVYOIL, new ModelResourceLocation(RefStrings.MODID + ":canister_heavyoil", "inventory")),
		BITUMEN(ModForgeFluids.BITUMEN, new ModelResourceLocation(RefStrings.MODID + ":canister_bitumen", "inventory")),
		SMEAR(ModForgeFluids.SMEAR, new ModelResourceLocation(RefStrings.MODID + ":canister_smear", "inventory")),
		HEATINGOIL(ModForgeFluids.HEATINGOIL, new ModelResourceLocation(RefStrings.MODID + ":canister_heatingoil", "inventory")),
		NAPHTHA(ModForgeFluids.NAPHTHA, new ModelResourceLocation(RefStrings.MODID + ":canister_naphtha", "inventory")),
		LIGHTOIL(ModForgeFluids.LIGHTOIL, new ModelResourceLocation(RefStrings.MODID + ":canister_lightoil", "inventory")),
		GASOLINE(ModForgeFluids.GASOLINE, new ModelResourceLocation(RefStrings.MODID + ":canister_gasoline", "inventory"));
		
		private final Fluid fluid;
		private final Pair<ModelResourceLocation, IBakedModel> renderPair;
		private final String translateKey;
		
		EnumCanister(Fluid f, ModelResourceLocation r){
			this.fluid = f;
			this.renderPair = MutablePair.of(r, null);
			this.translateKey = "item." + r.getPath() + ".name";
		}
		public Fluid getFluid(){
			return fluid;
		}
		public String getTranslateKey(){
			return translateKey;
		}
		public IBakedModel getRenderModel(){
			return renderPair.getRight();
		}
		public void putRenderModel(IBakedModel model){
			renderPair.setValue(model);
		}
		public ModelResourceLocation getResourceLocation(){
			return renderPair.getLeft();
		}
		public static boolean contains(Fluid f){
			if(f == null)
				return false;
			for(EnumCanister e : EnumCanister.values()){
				if(e.getFluid() == f)
					return true;
			}
			return false;
		}
		public static EnumCanister getEnumFromFluid(Fluid f){
			if(f == null)
				return EnumCanister.EMPTY;
			for(EnumCanister e : EnumCanister.values()){
				if(e.getFluid() == f){
					return e;
				}
			}
			return null;
		}
		public static Fluid[] getFluids() {
			Fluid[] f = new Fluid[EnumCanister.values().length];
			for(int i = 0; i < EnumCanister.values().length; i ++){
				f[i] = EnumCanister.values()[i].getFluid();
			}
			return f;
		}
	}
	
	public enum EnumCell {
		EMPTY(null, new ModelResourceLocation(RefStrings.MODID + ":cell_empty", "inventory")),
		UF6(ModForgeFluids.UF6, new ModelResourceLocation(RefStrings.MODID + ":cell_uf6", "inventory")),
		PUF6(ModForgeFluids.PUF6, new ModelResourceLocation(RefStrings.MODID + ":cell_puf6", "inventory")),
		ANTIMATTER(ModForgeFluids.AMAT, new ModelResourceLocation(RefStrings.MODID + ":cell_antimatter", "inventory")),
		DEUTERIUM(ModForgeFluids.DEUTERIUM, new ModelResourceLocation(RefStrings.MODID + ":cell_deuterium", "inventory")),
		TRITIUM(ModForgeFluids.TRITIUM, new ModelResourceLocation(RefStrings.MODID + ":cell_tritium", "inventory")),
		SAS3(ModForgeFluids.SAS3, new ModelResourceLocation(RefStrings.MODID + ":cell_sas3", "inventory")),
		ANTISCHRABIDIUM(ModForgeFluids.ASCHRAB, new ModelResourceLocation(RefStrings.MODID + ":cell_anti_schrabidium", "inventory"));
		
		private final Fluid fluid;
		private final Pair<ModelResourceLocation, IBakedModel> renderPair;
		private final String translateKey;
		
		EnumCell(Fluid f, ModelResourceLocation r){
			this.fluid = f;
			this.renderPair = MutablePair.of(r, null);
			this.translateKey = "item." + r.getPath() + ".name";
		}
		public Fluid getFluid(){
			return fluid;
		}
		public String getTranslateKey(){
			return translateKey;
		}
		public IBakedModel getRenderModel(){
			return renderPair.getRight();
		}
		public void putRenderModel(IBakedModel model){
			renderPair.setValue(model);
		}
		public ModelResourceLocation getResourceLocation(){
			return renderPair.getLeft();
		}
		public static boolean contains(Fluid f){
			if(f == null)
				return false;
			for(EnumCell e : EnumCell.values()){
				if(e.getFluid() == f)
					return true;
			}
			return false;
		}
		public static EnumCell getEnumFromFluid(Fluid f){
			if(f == null)
				return EnumCell.EMPTY;
			for(EnumCell e : EnumCell.values()){
				if(e.getFluid() == f){
					return e;
				}
			}
			return null;
		}
		public static Fluid[] getFluids() {
			Fluid[] f = new Fluid[EnumCell.values().length];
			for(int i = 0; i < EnumCell.values().length; i ++){
				f[i] = EnumCell.values()[i].getFluid();
			}
			return f;
		}
	}
	
	public enum EnumGasCanister {
		EMPTY(null, new ModelResourceLocation(RefStrings.MODID + ":gas_empty", "inventory")),
		NATURAL(ModForgeFluids.GAS, new ModelResourceLocation(RefStrings.MODID + ":gas_full", "inventory")),
		PETROLEUM(ModForgeFluids.PETROLEUM, new ModelResourceLocation(RefStrings.MODID + ":gas_petroleum", "inventory")),
		BIOGAS(ModForgeFluids.BIOGAS, new ModelResourceLocation(RefStrings.MODID + ":gas_biogas", "inventory")),
		HYDROGEN(ModForgeFluids.HYDROGEN, new ModelResourceLocation(RefStrings.MODID + ":gas_hydrogen", "inventory")),
		DEUTERIUM(ModForgeFluids.DEUTERIUM, new ModelResourceLocation(RefStrings.MODID + ":gas_deuterium", "inventory")),
		TRITIUM(ModForgeFluids.TRITIUM, new ModelResourceLocation(RefStrings.MODID + ":gas_tritium", "inventory")),
		OXYGEN(ModForgeFluids.OXYGEN, new ModelResourceLocation(RefStrings.MODID + ":gas_oxygen", "inventory"));
		
		private final Fluid fluid;
		private final Pair<ModelResourceLocation, IBakedModel> renderPair;
		private final String translateKey;
		
		EnumGasCanister(Fluid f, ModelResourceLocation r){
			this.fluid = f;
			this.renderPair = MutablePair.of(r, null);
			this.translateKey = "item." + r.getPath() + ".name";
		}
		public Fluid getFluid(){
			return fluid;
		}
		public String getTranslateKey(){
			return translateKey;
		}
		public IBakedModel getRenderModel(){
			return renderPair.getRight();
		}
		public void putRenderModel(IBakedModel model){
			renderPair.setValue(model);
		}
		public ModelResourceLocation getResourceLocation(){
			return renderPair.getLeft();
		}
		public static boolean contains(Fluid f){
			if(f == null)
				return false;
			for(EnumGasCanister e : EnumGasCanister.values()){
				if(e.getFluid() == f)
					return true;
			}
			return false;
		}
		public static EnumGasCanister getEnumFromFluid(Fluid f){
			if(f == null)
				return EnumGasCanister.EMPTY;
			for(EnumGasCanister e : EnumGasCanister.values()){
				if(e.getFluid() == f){
					return e;
				}
			}
			return null;
		}
		public static Fluid[] getFluids() {
			Fluid[] f = new Fluid[EnumGasCanister.values().length];
			for(int i = 0; i < EnumGasCanister.values().length; i ++){
				f[i] = EnumGasCanister.values()[i].getFluid();
			}
			return f;
		}
	}

	
}
