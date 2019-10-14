package com.hbm.main;

import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.RefStrings;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = RefStrings.MODID)
public class ModEventHandler {

	public void soundRegistering(RegistryEvent.Register<SoundEvent> evt){
		for(SoundEvent e : HBMSoundHandler.ALL_SOUNDS){
			evt.getRegistry().register(e);
		}
	}
}
