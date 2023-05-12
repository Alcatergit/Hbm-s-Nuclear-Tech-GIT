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
		DIESEL(ModForgeFluids.diesel, new ModelResourceLocation(RefStrings.MODID + ":canister_fuel", "inventory")),
		OIL(ModForgeFluids.oil, new ModelResourceLocation(RefStrings.MODID + ":canister_oil", "inventory")),
		PETROIL(ModForgeFluids.petroil, new ModelResourceLocation(RefStrings.MODID + ":canister_petroil", "inventory")),
		FRACKSOL(ModForgeFluids.fracksol, new ModelResourceLocation(RefStrings.MODID + ":canister_fracksol", "inventory")),
		KEROSENE(ModForgeFluids.kerosene, new ModelResourceLocation(RefStrings.MODID + ":canister_kerosene", "inventory")),
		NITAN(ModForgeFluids.nitan, new ModelResourceLocation(RefStrings.MODID + ":canister_superfuel", "inventory")),
		BIOFUEL(ModForgeFluids.biofuel, new ModelResourceLocation(RefStrings.MODID + ":canister_biofuel", "inventory")),
		CANOLA(ModForgeFluids.lubricant, new ModelResourceLocation(RefStrings.MODID + ":canister_canola", "inventory")),
		REOIL(ModForgeFluids.reclaimed, new ModelResourceLocation(RefStrings.MODID + ":canister_reoil", "inventory")),
		HEAVYOIL(ModForgeFluids.heavyoil, new ModelResourceLocation(RefStrings.MODID + ":canister_heavyoil", "inventory")),
		BITUMEN(ModForgeFluids.bitumen, new ModelResourceLocation(RefStrings.MODID + ":canister_bitumen", "inventory")),
		SMEAR(ModForgeFluids.smear, new ModelResourceLocation(RefStrings.MODID + ":canister_smear", "inventory")),
		HEATINGOIL(ModForgeFluids.heatingoil, new ModelResourceLocation(RefStrings.MODID + ":canister_heatingoil", "inventory")),
		NAPHTHA(ModForgeFluids.naphtha, new ModelResourceLocation(RefStrings.MODID + ":canister_naphtha", "inventory")),
		LIGHTOIL(ModForgeFluids.lightoil, new ModelResourceLocation(RefStrings.MODID + ":canister_lightoil", "inventory")),
		GASOLINE(ModForgeFluids.gasoline, new ModelResourceLocation(RefStrings.MODID + ":canister_gasoline", "inventory"));
		
		private Fluid fluid;
		private Pair<ModelResourceLocation, IBakedModel> renderPair;
		private String translateKey;
		
		private EnumCanister(Fluid f, ModelResourceLocation r){
			this.fluid = f;
			this.renderPair = MutablePair.of(r, null);
			this.translateKey = "item." + r.getResourcePath() + ".name";
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
		UF6(ModForgeFluids.uf6, new ModelResourceLocation(RefStrings.MODID + ":cell_uf6", "inventory")),
		PUF6(ModForgeFluids.puf6, new ModelResourceLocation(RefStrings.MODID + ":cell_puf6", "inventory")),
		ANTIMATTER(ModForgeFluids.amat, new ModelResourceLocation(RefStrings.MODID + ":cell_antimatter", "inventory")),
		DEUTERIUM(ModForgeFluids.deuterium, new ModelResourceLocation(RefStrings.MODID + ":cell_deuterium", "inventory")),
		TRITIUM(ModForgeFluids.tritium, new ModelResourceLocation(RefStrings.MODID + ":cell_tritium", "inventory")),
		SAS3(ModForgeFluids.sas3, new ModelResourceLocation(RefStrings.MODID + ":cell_sas3", "inventory")),
		ANTISCHRABIDIUM(ModForgeFluids.aschrab, new ModelResourceLocation(RefStrings.MODID + ":cell_anti_schrabidium", "inventory"));
		
		private Fluid fluid;
		private Pair<ModelResourceLocation, IBakedModel> renderPair;
		private String translateKey;
		
		private EnumCell(Fluid f, ModelResourceLocation r){
			this.fluid = f;
			this.renderPair = MutablePair.of(r, null);
			this.translateKey = "item." + r.getResourcePath() + ".name";
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
		NATURAL(ModForgeFluids.gas, new ModelResourceLocation(RefStrings.MODID + ":gas_full", "inventory")),
		PETROLEUM(ModForgeFluids.petroleum, new ModelResourceLocation(RefStrings.MODID + ":gas_petroleum", "inventory")),
		BIOGAS(ModForgeFluids.biogas, new ModelResourceLocation(RefStrings.MODID + ":gas_biogas", "inventory")),
		HYDROGEN(ModForgeFluids.hydrogen, new ModelResourceLocation(RefStrings.MODID + ":gas_hydrogen", "inventory")),
		DEUTERIUM(ModForgeFluids.deuterium, new ModelResourceLocation(RefStrings.MODID + ":gas_deuterium", "inventory")),
		TRITIUM(ModForgeFluids.tritium, new ModelResourceLocation(RefStrings.MODID + ":gas_tritium", "inventory")),
		OXYGEN(ModForgeFluids.oxygen, new ModelResourceLocation(RefStrings.MODID + ":gas_oxygen", "inventory"));
		
		private Fluid fluid;
		private Pair<ModelResourceLocation, IBakedModel> renderPair;
		private String translateKey;
		
		private EnumGasCanister(Fluid f, ModelResourceLocation r){
			this.fluid = f;
			this.renderPair = MutablePair.of(r, null);
			this.translateKey = "item." + r.getResourcePath() + ".name";
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
