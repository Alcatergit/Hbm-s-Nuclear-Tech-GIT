package com.hbm.inventory;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class FusionRecipes {

	public static int getByproductChance(Fluid plasma) {
		if(plasma == ModForgeFluids.PLASMA_DT){
			return 1200; 
		} else if(plasma == ModForgeFluids.PLASMA_HD){
			return 1200;
		} else if(plasma == ModForgeFluids.PLASMA_HT){
			return 1200;
		} else if(plasma == ModForgeFluids.PLASMA_MX){
			return 2400;
		} else if(plasma == ModForgeFluids.PLASMA_PUT){
			return 2400;
		} else if(plasma == ModForgeFluids.PLASMA_BF){
			return 150;
		}
		return 0;
	}

	public static ItemStack getByproduct(Fluid plasma) {
		if(plasma == ModForgeFluids.PLASMA_DT){
			return new ItemStack(ModItems.pellet_charged); 
		} else if(plasma == ModForgeFluids.PLASMA_HD){
			return new ItemStack(ModItems.pellet_charged);
		} else if(plasma == ModForgeFluids.PLASMA_HT){
			return new ItemStack(ModItems.pellet_charged);
		} else if(plasma == ModForgeFluids.PLASMA_MX){
			return new ItemStack(ModItems.powder_chlorophyte);
		} else if(plasma == ModForgeFluids.PLASMA_PUT){
			return new ItemStack(ModItems.powder_xe135);
		} else if(plasma == ModForgeFluids.PLASMA_BF){
			return new ItemStack(ModItems.powder_balefire);
		}
		return ItemStack.EMPTY;
	}
	
	public static int getBreedingLevel(Fluid plasma) {
		if(plasma == ModForgeFluids.PLASMA_DT){
			return 2;
		} else if(plasma == ModForgeFluids.PLASMA_HD){
			return 1;
		} else if(plasma == ModForgeFluids.PLASMA_HT){
			return 1;
		} else if(plasma == ModForgeFluids.PLASMA_MX){
			return 3;
		} else if(plasma == ModForgeFluids.PLASMA_PUT){
			return 4;
		} else if(plasma == ModForgeFluids.PLASMA_BF){
			return 5;
		}
		return 0;
	}
	
	public static int getSteamProduction(Fluid plasma) {
		if(plasma == ModForgeFluids.PLASMA_DT){
			return 225;
		} else if(plasma == ModForgeFluids.PLASMA_HD){
			return 150;
		} else if(plasma == ModForgeFluids.PLASMA_HT){
			return 188;
		} else if(plasma == ModForgeFluids.PLASMA_MX){
			return 450;
		} else if(plasma == ModForgeFluids.PLASMA_PUT){
			return 600;
		} else if(plasma == ModForgeFluids.PLASMA_BF){
			return 1200;
		}
		return 0;
	}

}
