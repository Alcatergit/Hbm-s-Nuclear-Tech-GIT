package com.hbm.inventory;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.Spaghetti;

import net.minecraftforge.fluids.Fluid;

//TODO: clean this shit up
//Alcater: on it
//Alcater: almost done yay
@Spaghetti("everything")
public class MachineRecipes {

	// return: Fluid, amount produced, amount required, HE produced
	public static Object[] getTurbineOutput(Fluid type) {

		if (type == ModForgeFluids.STEAM) {
			return new Object[] { ModForgeFluids.SPENTSTEAM, 5, 500, 50 };
		} else if (type == ModForgeFluids.HOTSTEAM) {
			return new Object[] { ModForgeFluids.STEAM, 50, 5, 100 };
		} else if (type == ModForgeFluids.SUPERHOTSTEAM) {
			return new Object[] { ModForgeFluids.HOTSTEAM, 50, 5, 150 };
		} else if(type == ModForgeFluids.ULTRAHOTSTEAM){
			return new Object[] { ModForgeFluids.SUPERHOTSTEAM, 50, 5, 250 };
		}

		return null;
	}
}
