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
	public static SoundEvent rpgShoot;
	public static SoundEvent rifleShoot;
	public static SoundEvent defabShoot;
	public static SoundEvent flamethrowerIgnite;
	public static SoundEvent flamethrowerShoot;
	public static SoundEvent tauShoot;
	public static SoundEvent oldExplosion;
	public static SoundEvent ciwsSpindown;
	public static SoundEvent ciwsSpinup;
	public static SoundEvent ciwsFiringLoop;
	public static SoundEvent reloadTurret;
	public static SoundEvent planeShotDown;
	public static SoundEvent bombWhistle;
	public static SoundEvent planeCrash;
	public static SoundEvent missileTakeoff;
	public static SoundEvent bomberSmallLoop;
	public static SoundEvent bomberLoop;
	public static SoundEvent stingerLockon;
	public static SoundEvent trainHorn;
	public static SoundEvent bombDet;
	public static SoundEvent rocketTakeoff;
	
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
		rpgShoot = register("weapon.rpgShoot");
		reloadTurret = register("weapon.reloadTurret");
		rifleShoot = register("weapon.rifleShoot");
		defabShoot = register("weapon.defabShoot");
		flamethrowerIgnite = register("weapon.flamethrowerIgnite");
		flamethrowerShoot = register("weapon.flamethrowerShoot");
		tauShoot = register("weapon.tauShoot");
		oldExplosion = register("entity.oldExplosion");
		ciwsSpindown = register("weapon.ciwsSpindown");
		ciwsSpinup = register("weapon.ciwsSpinup");
		ciwsFiringLoop = register("weapon.ciwsFiringLoop");
		planeShotDown = register("entity.planeShotDown");
		bombWhistle = register("entity.bombWhistle");
		planeCrash = register("entity.planeCrash");
		missileTakeoff = register("weapon.missileTakeOff");
		bomberSmallLoop = register("entity.bomberSmallLoop");
		bomberLoop = register("entity.bomberLoop");
		stingerLockon = register("weapon.stingerLockOn");
		trainHorn = register("alarm.trainHorn");
		bombDet = register("entity.bombDet");
		rocketTakeoff = register("entity.rocketTakeoff");
		
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
