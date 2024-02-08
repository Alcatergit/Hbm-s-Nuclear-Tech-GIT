package com.hbm.inventory;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class FusionRecipes {

	public static int getByproductChance(Fluid plasma) {
		if(plasma == ModForgeFluids.plasma_dt){
			return 1200; 
		} else if(plasma == ModForgeFluids.plasma_hd){
			return 1200;
		} else if(plasma == ModForgeFluids.plasma_ht){
			return 1200;
		} else if(plasma == ModForgeFluids.plasma_xm){
			return 2400;
		} else if(plasma == ModForgeFluids.plasma_put){
			return 2400;
		} else if(plasma == ModForgeFluids.plasma_bf){
			return 150;
		}
		return 0;
	}

	public static ItemStack getByproduct(Fluid plasma) {
		if(plasma == ModForgeFluids.plasma_dt){
			return new ItemStack(ModItems.pellet_charged); 
		} else if(plasma == ModForgeFluids.plasma_hd){
			return new ItemStack(ModItems.pellet_charged);
		} else if(plasma == ModForgeFluids.plasma_ht){
			return new ItemStack(ModItems.pellet_charged);
		} else if(plasma == ModForgeFluids.plasma_xm){
			return new ItemStack(ModItems.powder_chlorophyte);
		} else if(plasma == ModForgeFluids.plasma_put){
			return new ItemStack(ModItems.powder_xe135);
		} else if(plasma == ModForgeFluids.plasma_bf){
			return new ItemStack(ModItems.powder_balefire);
		}
		return ItemStack.EMPTY;
	}
	
	public static int getBreedingLevel(Fluid plasma) {
		if(plasma == ModForgeFluids.plasma_dt){
			return 2;
		} else if(plasma == ModForgeFluids.plasma_hd){
			return 1;
		} else if(plasma == ModForgeFluids.plasma_ht){
			return 1;
		} else if(plasma == ModForgeFluids.plasma_xm){
			return 3;
		} else if(plasma == ModForgeFluids.plasma_put){
			return 4;
		} else if(plasma == ModForgeFluids.plasma_bf){
			return 5;
		}
		return 0;
	}
	
	public static int getSteamProduction(Fluid plasma) {
		if(plasma == ModForgeFluids.plasma_dt){
			return 225;
		} else if(plasma == ModForgeFluids.plasma_hd){
			return 150;
		} else if(plasma == ModForgeFluids.plasma_ht){
			return 188;
		} else if(plasma == ModForgeFluids.plasma_xm){
			return 450;
		} else if(plasma == ModForgeFluids.plasma_put){
			return 600;
		} else if(plasma == ModForgeFluids.plasma_bf){
			return 1200;
		}
		return 0;
	}

}
