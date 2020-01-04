/*
 * (C) 2014-2018 Team CoFH / CoFH / Cult of the Full Hub
 * http://www.teamcofh.com
 */
package cofh.redstoneflux;

import cofh.redstoneflux.internal.OldAPIChecker;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod (modid = RedstoneFluxProps.MOD_ID, name = RedstoneFluxProps.MOD_NAME, version = RedstoneFluxProps.VERSION, certificateFingerprint = "8a6abf2cb9e141b866580d369ba6548732eff25f")
public class RedstoneFlux {

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		OldAPIChecker.check();
	}

}
