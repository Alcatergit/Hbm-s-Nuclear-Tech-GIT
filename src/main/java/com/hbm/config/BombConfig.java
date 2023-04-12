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
	
	public static int mk4 = 1024;
	public static int blastSpeed = 1024;
	public static int falloutRange = 100;
	public static int fChunkSpeed = 10;
	public static boolean evaporateWater = true;
	public static boolean fillCraterWithWater = true;
	public static int oceanHeight = 0;
	public static int limitExplosionLifespan = 0;
	public static boolean disableNuclear;
	
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
		
		final String CATEGORY_NUKE = "06_explosions";
		Property propLimitExplosionLifespan = config.get(CATEGORY_NUKE, "6.00_limitExplosionLifespan", 0);
		propLimitExplosionLifespan.setComment("How long an explosion can be unloaded until it dies in seconds. Based of system time. 0 disables the effect");
		limitExplosionLifespan = propLimitExplosionLifespan.getInt();
		// explosion speed
		Property propBlastSpeed = config.get(CATEGORY_NUKE, "6.01_blastSpeed", 1024);
		propBlastSpeed.setComment("Base speed of MK3 system (old and schrabidium) detonations (Blocks / tick)");
		blastSpeed = propBlastSpeed.getInt();
		// fallout range
		Property propFalloutRange = config.get(CATEGORY_NUKE, "6.02_blastSpeedNew", 1024);
		propFalloutRange.setComment("Base speed of MK4 system (new) detonations (Blocks / tick)");
		mk4 = propFalloutRange.getInt();
		// fallout speed
		Property falloutRangeProp = config.get(CATEGORY_NUKE, "6.03_falloutRange", 100);
		falloutRangeProp.setComment("Radius of fallout area (base radius * value in percent)");
		falloutRange = falloutRangeProp.getInt();
		// new explosion speed
		Property falloutChunkSpeed = config.get(CATEGORY_NUKE, "6.04_falloutChunkSpeed", 6);
		falloutChunkSpeed.setComment("Process a Chunk every nth tick by the fallout rain");
		fChunkSpeed = falloutChunkSpeed.getInt();
		//Whether fallout and nuclear radiation is enabled at all
		Property disableNuclearP = config.get(CATEGORY_NUKE, "6.06_disableNuclear", false);
		disableNuclearP.setComment("Disable the nuclear part of nukes");
		disableNuclear = disableNuclearP.getBoolean();
	}

}
