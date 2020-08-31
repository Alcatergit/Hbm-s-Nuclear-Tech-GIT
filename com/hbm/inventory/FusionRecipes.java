package com.hbm.inventory;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class FusionRecipes {

	public static int getByproductChance(Fluid plasma) {
		if(plasma == ModForgeFluids.plasma_xm){
			return 3 * 60 * 20; 
		} else if(plasma == ModForgeFluids.plasma_bf){
			return 10 * 60 * 20;
		}
		return 0;
	}

	public static ItemStack getByproduct(Fluid plasma) {
		if(plasma == ModForgeFluids.plasma_xm){
			return new ItemStack(ModItems.powder_chlorophyte); 
		} else if(plasma == ModForgeFluids.plasma_bf){
			return new ItemStack(ModItems.powder_balefire);
		}
		return ItemStack.EMPTY;
	}
	
	public static int getBreedingLevel(Fluid plasma) {
		if(plasma == ModForgeFluids.plasma_dt){
			return 1;
		} else if(plasma == ModForgeFluids.plasma_hd){
			return 1;
		} else if(plasma == ModForgeFluids.plasma_ht){
			return 1;
		} else if(plasma == ModForgeFluids.plasma_xm){
			return 3;
		} else if(plasma == ModForgeFluids.plasma_bf){
			return 4;
		}
		return 0;
	}
	
	public static int getSteamProduction(Fluid plasma) {
		if(plasma == ModForgeFluids.plasma_dt){
			return 30;
		} else if(plasma == ModForgeFluids.plasma_hd){
			return 20;
		} else if(plasma == ModForgeFluids.plasma_ht){
			return 25;
		} else if(plasma == ModForgeFluids.plasma_xm){
			return 60;
		} else if(plasma == ModForgeFluids.plasma_bf){
			return 160;
		}
		return 0;
	}

}
