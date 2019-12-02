package com.hbm.lib;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public final class HBMSoundHandler {
	
	public static List<SoundEvent> ALL_SOUNDS = new ArrayList<SoundEvent>();
	
	public static SoundEvent assemblerOperate;
	public static SoundEvent pressOperate;
	public static SoundEvent laserBang;
	public static SoundEvent blockDebris;
	public static SoundEvent syringeUse;
	public static SoundEvent sparkShoot;
	public static SoundEvent b92Reload;
	
	public static void init() {
		
		
		assemblerOperate = register("block.assembleroperate");
		pressOperate = register("block.pressoperate");
		laserBang = register("weapon.laserBang");
		blockDebris = register("block.debris");
		syringeUse = register("item.syringe");
		sparkShoot = register("weapon.sparkShoot");
		b92Reload = register("weapon.b92Reload");
	}
	
	public static SoundEvent register(String name) {
		SoundEvent e = new SoundEvent(new ResourceLocation(RefStrings.MODID, name));
		e.setRegistryName(name);
		ALL_SOUNDS.add(e);
		return e;
	}
}
