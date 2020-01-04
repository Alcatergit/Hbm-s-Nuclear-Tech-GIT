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
		DIESEL(ModForgeFluids.diesel, new ModelResourceLocation(RefStrings.MODID + ":canister_fuel", "inventory"));
		
		private Fluid fluid;
		private Pair<ModelResourceLocation, IBakedModel> renderPair;
		
		private EnumCanister(Fluid f, ModelResourceLocation r){
			this.fluid = f;
			this.renderPair = MutablePair.of(r, null);
		}
		public Fluid getFluid(){
			return fluid;
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
	


	
}
