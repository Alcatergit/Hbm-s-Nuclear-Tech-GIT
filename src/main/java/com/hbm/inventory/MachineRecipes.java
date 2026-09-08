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
			return new Object[] { ModForgeFluids.SPENTSTEAM, 1, 100, 200 };
		} else if (type == ModForgeFluids.HOTSTEAM) {
			return new Object[] { ModForgeFluids.STEAM, 10, 1, 2 };
		} else if (type == ModForgeFluids.SUPERHOTSTEAM) {
			return new Object[] { ModForgeFluids.HOTSTEAM, 10, 1, 18 };
		} else if(type == ModForgeFluids.ULTRAHOTSTEAM){
			return new Object[] { ModForgeFluids.SUPERHOTSTEAM, 10, 1, 120 };
		}

		return null;
	}
}
