package com.hbm.lib;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public final class HBMSoundHandler {
	
	public static List<SoundEvent> ALL_SOUNDS = new ArrayList<SoundEvent>();
	
	public static SoundEvent assemblerOperate;

	
	public static void init() {
		
		
		assemblerOperate = register("block.assemblerOperate");
	}
	
	public static SoundEvent register(String name) {
		ResourceLocation loc = new ResourceLocation(RefStrings.MODID + ":" + name);
		SoundEvent e = new SoundEvent(loc);
		ALL_SOUNDS.add(e);
		return e;
	}
}
