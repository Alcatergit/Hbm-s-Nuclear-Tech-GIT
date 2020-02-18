package com.hbm.lib;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.GunConfiguration;

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
	public static SoundEvent silencerShoot;
	public static SoundEvent rpgReload;
	public static SoundEvent reloadGrenade;
	public static SoundEvent reloadShotgun;
	public static SoundEvent reloadMag;
	public static SoundEvent reloadRifle;
	public static SoundEvent reloadRevolver;
	public static SoundEvent boatWeapon;
	public static SoundEvent ricochet;
	public static SoundEvent grenadeBounce;
	public static SoundEvent alarmGambit;
	public static SoundEvent revolverShoot;
	public static SoundEvent heavyShoot;
	public static SoundEvent schrabidiumShoot;
	public static SoundEvent revolverShootAlt;
	public static SoundEvent hkShoot;
	public static SoundEvent shotgunShoot;
	public static SoundEvent uziShoot;
	public static SoundEvent calShoot;
	public static SoundEvent lacunaeShoot;
	public static SoundEvent fatmanShoot;
	public static SoundEvent osiprShoot;
	public static SoundEvent zomgShoot;
	public static SoundEvent jetpackTank;
	public static SoundEvent nullTau;
	public static SoundEvent immolatorIgnite;
	public static SoundEvent immolatorShoot;
	public static SoundEvent defabSpinup;
	public static SoundEvent cryolatorShoot;
	public static SoundEvent singFlyby;
	public static SoundEvent osiprCharging;
	public static SoundEvent leverActionReload;
	public static SoundEvent follyOpen;
	public static SoundEvent follyReload;
	public static SoundEvent follyClose;
	public static SoundEvent follyFire;
	public static SoundEvent follyBuzzer;
	public static SoundEvent follyAquired;
	public static SoundEvent chopperDrop;
	public static SoundEvent crateBreak;
	public static SoundEvent alarmAutopilot;
	public static SoundEvent itemUnpack;
	public static SoundEvent centrifugeOperate;
	
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
		trainHorn = register("alarm.trainhorn");
		bombDet = register("entity.bombDet");
		rocketTakeoff = register("entity.rocketTakeoff");
		silencerShoot = register("weapon.silencerShoot");
		GunConfiguration.RSOUND_LAUNCHER = rpgReload = register("weapon.rpgReload");
		GunConfiguration.RSOUND_GRENADE = reloadGrenade = register("weapon.hkReload");
		GunConfiguration.RSOUND_SHOTGUN = reloadShotgun = register("weapon.shotgunReload");
		GunConfiguration.RSOUND_MAG = reloadMag = register("weapon.magReload");
		GunConfiguration.RSOUND_RIFLE = reloadRifle = register("");
		GunConfiguration.RSOUND_REVOLVER = reloadRevolver = register("weapon.revolverReload");
		boatWeapon = register("weapon.boat");
		ricochet = register("weapon.ricochet");
		grenadeBounce = register("weapon.gBounce");
		alarmGambit = register("alarm.gambit");
		revolverShoot = register("weapon.revolverShoot");
		heavyShoot = register("weapon.heavyShoot");
		schrabidiumShoot = register("weapon.schrabidiumShoot");
		revolverShootAlt = register("weapon.revolverShootAlt");
		hkShoot = register("weapon.hkShoot");
		shotgunShoot = register("weapon.shotgunShoot");
		uziShoot = register("weapon.uziShoot");
		calShoot = register("weapon.calShoot");
		lacunaeShoot = register("weapon.lacunaeShoot");
		fatmanShoot = register("weapon.fatmanShoot");
		osiprShoot = register("weapon.osiprShoot");
		zomgShoot = register("weapon.zomgShoot");
		jetpackTank = register("item.jetpackTank");
		nullTau = register("misc.nullTau");
		immolatorIgnite = register("weapon.immolatorIgnite");
		immolatorShoot = register("weapon.immolatorShoot");
		defabSpinup = register("weapon.defabSpinup");
		cryolatorShoot = register("weapon.cryolatorShoot");
		singFlyby = register("weapon.singFlyby");
		osiprCharging = register("weapon.osiprCharging");
		leverActionReload = register("weapon.leverActionReload");
		follyOpen = register("weapon.follyOpen");
		follyReload = register("weapon.follyReload");
		follyClose = register("weapon.follyClose");
		follyFire = register("weapon.follyFire");
		follyBuzzer = register("weapon.follyBuzzer");
		follyAquired = register("weapon.follyAquired");
		chopperDrop = register("entity.chopperDrop");
		crateBreak = register("block.crateBreak");
		alarmAutopilot = register("alarm.autopilot");
		itemUnpack = register("item.unpack");
		centrifugeOperate = register("block.centrifugeOperate");
		
		
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
	
}
