package com.hbm.main;

import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.RefStrings;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class ModEventHandler {

	@SubscribeEvent
	public void soundRegistering(RegistryEvent.Register<SoundEvent> evt){
		HBMSoundHandler.init();
		for(SoundEvent e : HBMSoundHandler.ALL_SOUNDS){
			evt.getRegistry().register(e);
		}
	}
	
	@SubscribeEvent
	public void worldTick(WorldTickEvent evt){
		if(evt.world != null && !evt.world.isRemote && MainRegistry.enableRads) {
			
		}
	}
	
	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent evt){
		
	}
}
