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
	public static SoundEvent techBleep;
	public static SoundEvent techBoop;
	public static SoundEvent reactorStart;
	public static SoundEvent reactorStop;
	public static SoundEvent chemplantOperate;
	public static SoundEvent potatOSRandom;
	public static SoundEvent weaponSpinDown;
	public static SoundEvent weaponSpinUp;
	public static SoundEvent sawShoot;
	public static SoundEvent reloadTurret;
	
	public static SoundEvent lambdaCore = registerBypass("music.recordlambdacore");
	public static SoundEvent sectorSweep = registerBypass("music.recordsectorsweep");
	public static SoundEvent vortalCombat = registerBypass("music.recordvortalcombat");
	
	public static void init() {
		
		
		assemblerOperate = register("block.assembleroperate");
		pressOperate = register("block.pressoperate");
		laserBang = register("weapon.laserBang");
		blockDebris = register("block.debris");
		syringeUse = register("item.syringe");
		sparkShoot = register("weapon.sparkShoot");
		b92Reload = register("weapon.b92Reload");
		techBleep = register("item.techBleep");
		techBoop = register("item.techBoop");
		reactorStart = register("block.reactorStart");
		reactorStop = register("block.reactorStop");
		chemplantOperate = register("block.chemplantOperate");
		potatOSRandom = register("potatos.random");
		weaponSpinDown = register("weapon.spindown");
		weaponSpinUp = register("weapon.spinup");
		sawShoot = register("weapon.sawShoot");
		reloadTurret = register("weapon.reloadTurret");
		
	}
	
	public static SoundEvent register(String name) {
		SoundEvent e = new SoundEvent(new ResourceLocation(RefStrings.MODID, name));
		e.setRegistryName(name);
		ALL_SOUNDS.add(e);
		return e;
	}
	
	public static SoundEvent registerBypass(String name){
		SoundEvent e = new SoundEvent(new ResourceLocation(RefStrings.MODID, name));
		e.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(e);
		return e;
	}
	
	public static void loadClass(){}
}
