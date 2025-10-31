package com.hbm.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class BombConfig {

	public static int gadgetRadius = 150;
	public static int boyRadius = 120;
	public static int manRadius = 175;
	public static int tsarRadius = 500;
	public static int mikeRadius = 250;
	public static int prototypeRadius = 150;
	public static int fleijaRadius = 50;
	public static int soliniumRadius = 150;
	public static int n2Radius = 200;
	public static int missileRadius = 100;
	public static int mirvRadius = 70;
	public static int fatmanRadius = 35;
	public static int nukaRadius = 25;
	public static int aSchrabRadius = 20;
	public static int tomRadius = 600;
	public static int tomImpactRadius = 1000000;
	public static double tomImpactExponent = 0.1;
	public static double tomImpactTimeInfernal = 20;
	public static double tomImpactTimeVolcanic = 5;
	public static double tomImpactTimeSeismic = 5;
	public static double tomImpactTimeDarkness = 8.33;
	public static int riggedStarRange = 50;
	public static int riggedStarTicks = 60 * 20;

	public static int maxCustomTNTRadius = 150;
	public static int maxCustomNukeRadius = 250;
	public static int maxCustomHydroRadius = 400;
	public static int maxCustomDirtyRadius = 200;
	public static int maxCustomBaleRadius = 750;
	public static int maxCustomSchrabRadius = 500;
	public static int maxCustomSolRadius = 1000;
	public static int maxCustomEuphLvl = 20;
	
	public static int mk5 = 40;
	public static int blastSpeed = 1024;
	public static int falloutRange = 100;
	public static int fChunkSpeed = 5;
	public static int falloutMS = 40;
	public static boolean spawnFire = false;
	public static int limitExplosionLifespan = 0;
	public static boolean disableNuclear = false;
	public static boolean enableNukeClouds = true;
	
	public static void loadFromConfig(Configuration config) {
		final String CATEGORY_NUKES = "03_nukes";
		Property propGadget = config.get(CATEGORY_NUKES, "3.00_gadgetRadius", 150);
		propGadget.setComment("Radius of the Gadget");
		gadgetRadius = propGadget.getInt();
		Property propBoy = config.get(CATEGORY_NUKES, "3.01_boyRadius", 120);
		propBoy.setComment("Radius of Little Boy");
		boyRadius = propBoy.getInt();
		Property propMan = config.get(CATEGORY_NUKES, "3.02_manRadius", 175);
		propMan.setComment("Radius of Fat Man");
		manRadius = propMan.getInt();
		Property propMike = config.get(CATEGORY_NUKES, "3.03_mikeRadius", 250);
		propMike.setComment("Radius of Ivy Mike");
		mikeRadius = propMike.getInt();
		Property propTsar = config.get(CATEGORY_NUKES, "3.04_tsarRadius", 500);
		propTsar.setComment("Radius of the Tsar Bomba");
		tsarRadius = propTsar.getInt();
		Property propPrototype = config.get(CATEGORY_NUKES, "3.05_prototypeRadius", 150);
		propPrototype.setComment("Radius of the Prototype");
		prototypeRadius = propPrototype.getInt();
		Property propFleija = config.get(CATEGORY_NUKES, "3.06_fleijaRadius", 50);
		propFleija.setComment("Radius of F.L.E.I.J.A.");
		fleijaRadius = propFleija.getInt();
		Property propMissile = config.get(CATEGORY_NUKES, "3.07_missileRadius", 100);
		propMissile.setComment("Radius of the nuclear missile");
		missileRadius = propMissile.getInt();
		Property propMirv = config.get(CATEGORY_NUKES, "3.08_mirvRadius", 70);
		propMirv.setComment("Radius of a MIRV");
		mirvRadius = propMirv.getInt();
		Property propFatman = config.get(CATEGORY_NUKES, "3.09_fatmanRadius", 35);
		propFatman.setComment("Radius of the Fatman Launcher");
		fatmanRadius = propFatman.getInt();
		Property propNuka = config.get(CATEGORY_NUKES, "3.10_nukaRadius", 25);
		propNuka.setComment("Radius of the nuka grenade");
		nukaRadius = propNuka.getInt();
		Property propASchrab = config.get(CATEGORY_NUKES, "3.11_aSchrabRadius", 20);
		propASchrab.setComment("Radius of dropped anti schrabidium");
		aSchrabRadius = propASchrab.getInt();
		Property propSolinium = config.get(CATEGORY_NUKES, "3.12_soliniumRadius", 150);
		propSolinium.setComment("Radius of the blue rinse");
		soliniumRadius = propSolinium.getInt();
		Property propN2 = config.get(CATEGORY_NUKES, "3.13_n2Radius", 200);
		propN2.setComment("Radius of the N2 mine");
		n2Radius = propN2.getInt();
		Property propTom = config.get(CATEGORY_NUKES, "3.14000_tomRadius", 600);
		propTom.setComment("Radius of explosion created by commanding Gerald. Default is 600");
		tomRadius = propTom.getInt();
		Property propTomI = config.get(CATEGORY_NUKES, "3.14001_tomApocalypseRadius", 1000000);
		propTomI.setComment("How far the said explosion should make the world well, less preferably habitable. Default is 1 million");
		tomImpactRadius = propTomI.getInt();
		Property propTomE = config.get(CATEGORY_NUKES, "3.14002_tomApocalypseExponent", 0.1);
		propTomE.setComment("The lesser it is, the farther you have to get away until a significant drop on the effects. 1 for a linear function. Default is 0.1");
		tomImpactExponent = propTomE.getDouble();
		Property propTomD0 = config.get(CATEGORY_NUKES, "3.14010_tomApocalypseDurationInfernal", 20);
		propTomD0.setComment("How long should entities be burned for being outside, in real life *minutes. Default is 20 minutes");
		tomImpactTimeInfernal = propTomD0.getDouble();
		Property propTomD1 = config.get(CATEGORY_NUKES, "3.14011_tomApocalypseDurationVolcanic", 5);
		propTomD1.setComment("How long should there be eruptions, hot metals and frequent meteors, in real life days. Default is 5 days");
		tomImpactTimeVolcanic = propTomD1.getDouble();
		Property propTomD2 = config.get(CATEGORY_NUKES, "3.14012_tomApocalypseDurationSeismic", 5);
		propTomD2.setComment("How long should there be randomly collapsing blocks. Default is 5 days");
		tomImpactTimeSeismic = propTomD2.getDouble();
		Property propTomD3 = config.get(CATEGORY_NUKES, "3.14013_tomApocalypseDurationDarkness", 8.33);
		propTomD3.setComment("How long entities should get burned outside, in real life days. Default is 8.33 days");
		tomImpactTimeDarkness = propTomD3.getDouble();

		Property propRS1 = config.get(CATEGORY_NUKES, "3.14_riggedStarRadius", 50);
		propRS1.setComment("Radius of the Rigged Star Blaster Energy Cell");
		riggedStarRange = propRS1.getInt();
		Property propRS2 = config.get(CATEGORY_NUKES, "3.15_riggedStarFuse", 1200);
		propRS2.setComment("Time in ticks before the Rigged Star Blaster Energy Cell explodes after being dropped - default 60s");
		riggedStarTicks = propRS2.getInt();

		Property propTNT = config.get(CATEGORY_NUKES, "4.00_maxCustomTNTRadius", 150);
		propTNT.setComment("Maximum TNT radius of custom nukes - default 150m");
		maxCustomTNTRadius = propTNT.getInt();

		Property propNuke = config.get(CATEGORY_NUKES, "4.01_maxCustomNukeRadius", 250);
		propNuke.setComment("Maximum Nuke radius of custom nukes - default 250m");
		maxCustomNukeRadius = propNuke.getInt();

		Property propHydro = config.get(CATEGORY_NUKES, "4.02_maxCustomHydroRadius", 400);
		propHydro.setComment("Maximum Thermonuclear radius of custom nukes - default 400m");
		maxCustomHydroRadius = propHydro.getInt();

		Property propDirty = config.get(CATEGORY_NUKES, "4.04_maxCustomDirtyRadius", 200);
		propDirty.setComment("Maximum fallout additional radius that can be added to custom nukes - default 200m");
		maxCustomDirtyRadius = propDirty.getInt();
		
		Property propBale = config.get(CATEGORY_NUKES, "4.03_maxCustomBaleRadius", 750);
		propBale.setComment("Maximum balefire radius of custom nukes - default 750m");
		maxCustomBaleRadius = propBale.getInt();

		Property propSchrab = config.get(CATEGORY_NUKES, "4.05_maxCustomSchrabRadius", 500);
		propSchrab.setComment("Maximum Antischrabidium radius of custom nukes - default 500m");
		maxCustomSchrabRadius = propSchrab.getInt();

		Property propSol = config.get(CATEGORY_NUKES, "4.06_maxCustomSolRadius", 1000);
		propSol.setComment("Maximum Solinium radius of custom nukes - default 1000m");
		maxCustomSolRadius = propSol.getInt();
		
		Property propEuph = config.get(CATEGORY_NUKES, "4.07_maxCustomEuphLvl", 20);
		propEuph.setComment("Maximum Euphemium Lvl of custom nukes (1Lvl = 100 Rays) - default 20");
		maxCustomEuphLvl = propEuph.getInt();
		
		
		final String CATEGORY_NUKE = "06_explosions";
		Property propLimitExplosionLifespan = config.get(CATEGORY_NUKE, "6.00_limitExplosionLifespan", 0);
		propLimitExplosionLifespan.setComment("How long an explosion can be unloaded until it dies in seconds. Based of system time. 0 disables the effect");
		limitExplosionLifespan = propLimitExplosionLifespan.getInt();
		// explosion speed
		Property propBlastSpeed = config.get(CATEGORY_NUKE, "6.01_blastSpeed", 1024);
		propBlastSpeed.setComment("Base speed of MK3 system (old and schrabidium) detonations (Blocks / tick)");
		blastSpeed = propBlastSpeed.getInt();
		// fallout range
		Property propFalloutRange = config.get(CATEGORY_NUKE, "6.02_mk5BlastTime", 30);
		propFalloutRange.setComment("Maximum amount of milliseconds per tick allocated for mk5 chunk processing");
		mk5 = propFalloutRange.getInt();
		// fallout speed
		Property falloutRangeProp = config.get(CATEGORY_NUKE, "6.03_falloutRange", 100);
		falloutRangeProp.setComment("Radius of fallout area (base radius * value in percent)");
		falloutRange = falloutRangeProp.getInt();
		// fallout speed
		Property falloutChunkSpeed = config.get(CATEGORY_NUKE, "6.04_falloutChunkSpeed", 5);
		falloutChunkSpeed.setComment("Process a Chunk every nth tick by the fallout rain");
		fChunkSpeed = falloutChunkSpeed.getInt();
		// new explosion speed
		Property falloutMSProp = config.get(CATEGORY_NUKE, "6.04_falloutTime", 30);
		falloutMSProp.setComment("Maximum amount of milliseconds per tick allocated for fallout chunk processing");
		falloutMS = falloutMSProp.getInt();
		Property spawnFireP = config.get(CATEGORY_NUKE, "6.05_falloutFireSpawn", false);
		spawnFireP.setComment("Weither to spawn fire after the nuke. Is off to increase TPS");
		spawnFire = spawnFireP.getBoolean();
		//Whether fallout and nuclear radiation is enabled at all
		Property disableNuclearP = config.get(CATEGORY_NUKE, "6.06_disableNuclear", false);
		disableNuclearP.setComment("Disable the nuclear part of nukes");
		disableNuclear = disableNuclearP.getBoolean();

		enableNukeClouds = config.get(CATEGORY_NUKE, "6.07_enableMushroomClouds", true).getBoolean(true);
		
	}
}
