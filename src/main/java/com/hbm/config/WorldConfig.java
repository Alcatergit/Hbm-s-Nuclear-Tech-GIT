package com.hbm.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class WorldConfig {

	public static int uraniumSpawn = 6;
	public static int thoriumSpawn = 7;
	public static int titaniumSpawn = 8;
	public static int sulfurSpawn = 5;
	public static int aluminiumSpawn = 7;
	public static int copperSpawn = 12;
	public static int fluoriteSpawn = 6;
	public static int niterSpawn = 6;
	public static int tungstenSpawn = 10;
	public static int leadSpawn = 6;
	public static int berylliumSpawn = 6;
	public static int ligniteSpawn = 2;
	public static int asbestosSpawn = 4;
	public static int rareSpawn = 6;
	public static int lithiumSpawn = 6;
	public static int oilcoalSpawn = 128;
	public static int gassshaleSpawn = 5;
	
	public static int netherUraniumuSpawn = 8;
	public static int netherTungstenSpawn = 10;
	public static int netherSulfurSpawn = 26;
	public static int netherPhosphorusSpawn = 24;
	public static int netherCoalSpawn = 24;
	public static int netherPlutoniumSpawn = 8;

	public static int endTikiteSpawn = 8;
	
	public static int radioStructure = 500;
	public static int antennaStructure = 250;
	public static int atomStructure = 500;
	public static int vertibirdStructure = 500;
	public static int dungeonStructure = 64;
	public static int relayStructure = 500;
	public static int satelliteStructure = 500;
	public static int bunkerStructure = 1000;
	public static int siloStructure = 1000;
	public static int factoryStructure = 1000;
	public static int dudStructure = 500;
	public static int spaceshipStructure = 1000;
	public static int barrelStructure = 5000;
	public static int geyserWater = 3000;
	public static int geyserChlorine = 3000;
	public static int geyserVapor = 500;
	public static int meteorStructure = 15000;
	public static int capsuleStructure = 100;
	public static int broadcaster = 5000;
	public static int minefreq = 64;
	public static int radfreq = 5000;
	public static int vaultfreq = 2500;
	public static int arcticStructure = 500;
	public static int jungleStructure = 2000;
	
	public static int meteorStrikeChance = 20 * 60 * 180;
	public static int meteorShowerChance = 20 * 60 * 5;
	public static int meteorShowerDuration = 6000;
	
	public static void loadFromConfig(Configuration config) {
		final String CATEGORY_OREGEN = "02_ores";
		uraniumSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.00_uraniumSpawnrate", "Amount of uranium ore veins per chunk", 7);
		titaniumSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.01_titaniumSpawnrate", "Amount of titanium ore veins per chunk", 8);
		sulfurSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.02_sulfurSpawnrate", "Amount of sulfur ore veins per chunk", 5);
		aluminiumSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.03_aluminiumSpawnrate", "Amount of aluminium ore veins per chunk", 7);
		copperSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.04_copperSpawnrate", "Amount of copper ore veins per chunk", 12);
		fluoriteSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.05_fluoriteSpawnrate", "Amount of fluorite ore veins per chunk", 6);
		niterSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.06_niterSpawnrate", "Amount of niter ore veins per chunk", 6);
		tungstenSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.07_tungstenSpawnrate", "Amount of tungsten ore veins per chunk", 10);
		leadSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.08_leadSpawnrate", "Amount of lead ore veins per chunk", 6);
		berylliumSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.09_berylliumSpawnrate", "Amount of beryllium ore veins per chunk", 6);
		thoriumSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.10_thoriumSpawnrate", "Amount of thorium ore veins per chunk", 7);
		ligniteSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.11_ligniteSpawnrate", "Amount of lignite ore veins per chunk", 2);
		asbestosSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.12_asbestosSpawnRate", "Amount of asbestos ore veins per chunk", 2);
		lithiumSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.13_lithiumSpawnRate", "Amount of schist lithium ore veins per chunk", 6);
		rareSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.14_rareEarthSpawnRate", "Amount of rare earth ore veins per chunk", 6);
		oilcoalSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.15_oilCoalSpawnRate", "Spawns an oily coal vein every nTH chunk", 128);
		gassshaleSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.16_gasShaleSpawnRate", "Amount of oil shale veins per chunk", 5);
		
		netherUraniumuSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.N00_uraniumSpawnrate", "Amount of nether uranium per chunk", 8);
		netherTungstenSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.N01_tungstenSpawnrate", "Amount of nether tungsten per chunk", 10);
		netherSulfurSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.N02_sulfurSpawnrate", "Amount of nether sulfur per chunk", 26);
		netherPhosphorusSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.N03_phosphorusSpawnrate", "Amount of nether phosphorus per chunk", 24);
		netherCoalSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.N04_coalSpawnrate", "Amount of nether coal per chunk", 24);
		netherPlutoniumSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.N05_plutoniumSpawnrate", "Amount of nether plutonium per chunk, if enabled", 8);

		endTikiteSpawn = CommonConfig.createConfigInt(config, CATEGORY_OREGEN, "2.E00_tikiteSpawnrate", "Amount of end trixite per chunk", 8);
		
        final String CATEGORY_DUNGEON = "04_dungeons";
		Property propRadio = config.get(CATEGORY_DUNGEON, "4.00_radioSpawn", 500);
		propRadio.setComment("Spawn radio station on every nTH chunk");
		radioStructure = propRadio.getInt();
		Property propAntenna = config.get(CATEGORY_DUNGEON, "4.01_antennaSpawn", 250);
		propAntenna.setComment("Spawn antenna on every nTH chunk");
		antennaStructure = propAntenna.getInt();
		Property propAtom = config.get(CATEGORY_DUNGEON, "4.02_atomSpawn", 500);
		propAtom.setComment("Spawn power plant on every nTH chunk");
		atomStructure = propAtom.getInt();
		Property propVertibird = config.get(CATEGORY_DUNGEON, "4.03_vertibirdSpawn", 500);
		propVertibird.setComment("Spawn vertibird on every nTH chunk");
		vertibirdStructure = propVertibird.getInt();
		Property propDungeon = config.get(CATEGORY_DUNGEON, "4.04_dungeonSpawn", 64);
		propDungeon.setComment("Spawn library dungeon on every nTH chunk");
		dungeonStructure = propDungeon.getInt();
		Property propRelay = config.get(CATEGORY_DUNGEON, "4.05_relaySpawn", 500);
		propRelay.setComment("Spawn relay on every nTH chunk");
		relayStructure = propRelay.getInt();
		Property propSatellite = config.get(CATEGORY_DUNGEON, "4.06_satelliteSpawn", 500);
		propSatellite.setComment("Spawn satellite dish on every nTH chunk");
		satelliteStructure = propSatellite.getInt();
		Property propBunker = config.get(CATEGORY_DUNGEON, "4.07_bunkerSpawn", 1000);
		propBunker.setComment("Spawn bunker on every nTH chunk");
		bunkerStructure = propBunker.getInt();
		Property propSilo = config.get(CATEGORY_DUNGEON, "4.08_siloSpawn", 1000);
		propSilo.setComment("Spawn missile silo on every nTH chunk");
		siloStructure = propSilo.getInt();
		Property propFactory = config.get(CATEGORY_DUNGEON, "4.09_factorySpawn", 1000);
		propFactory.setComment("Spawn factory on every nTH chunk");
		factoryStructure = propFactory.getInt();
		Property propDud = config.get(CATEGORY_DUNGEON, "4.10_dudSpawn", 500);
		propDud.setComment("Spawn dud on every nTH chunk");
		dudStructure = propDud.getInt();
		Property propSpaceship = config.get(CATEGORY_DUNGEON, "4.11_spaceshipSpawn", 1000);
		propSpaceship.setComment("Spawn spaceship on every nTH chunk");
		spaceshipStructure = propSpaceship.getInt();
		Property propBarrel = config.get(CATEGORY_DUNGEON, "4.12_barrelSpawn", 5000);
		propBarrel.setComment("Spawn waste tank on every nTH chunk");
		barrelStructure = propBarrel.getInt();
		Property propBroadcaster = config.get(CATEGORY_DUNGEON, "4.13_broadcasterSpawn", 5000);
		propBroadcaster.setComment("Spawn corrupt broadcaster on every nTH chunk");
		broadcaster = propBroadcaster.getInt();
		Property propMines = config.get(CATEGORY_DUNGEON, "4.14_landmineSpawn", 64);
		propMines.setComment("Spawn AP landmine on every nTH chunk");
		minefreq = propMines.getInt();
		Property propRad = config.get(CATEGORY_DUNGEON, "4.15_radHotsoptSpawn", 5000);
		propRad.setComment("Spawn radiation hotspot on every nTH chunk");
		radfreq = propRad.getInt();
		Property propVault = config.get(CATEGORY_DUNGEON, "4.16_vaultSpawn", 2500);
		propVault.setComment("Spawn locked safe on every nTH chunk");
		vaultfreq = propVault.getInt();
		Property pGW = config.get(CATEGORY_DUNGEON, "4.17_geyserWaterSpawn", 3000);
		pGW.setComment("Spawn water geyser on every nTH chunk");
		geyserWater = pGW.getInt();
		Property pGC = config.get(CATEGORY_DUNGEON, "4.18_geyserChlorineSpawn", 3000);
		pGC.setComment("Spawn poison geyser on every nTH chunk");
		geyserChlorine = pGC.getInt();
		Property pGV = config.get(CATEGORY_DUNGEON, "4.19_geyserVaporSpawn", 500);
		pGV.setComment("Spawn vapor geyser on every nTH chunk");
		geyserVapor = pGV.getInt();
		meteorStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "meteorStructure", "Spawn meteor dungeon on every nTH chunk", 15000);
		capsuleStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.21_capsuleSpawn", "Spawn landing capsule on every nTH chunk", 100);
		arcticStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.22_arcticVaultSpawn", "Spawn artic code vault on every nTH chunk", 500);
		jungleStructure = CommonConfig.createConfigInt(config, CATEGORY_DUNGEON, "4.23_jungleDungeonSpawn", "Spawn jungle dungeon on every nTH chunk", 2000);
		
		final String CATEGORY_METEOR = "05_meteors";
		Property propMeteorStrikeChance = config.get(CATEGORY_METEOR, "5.00_meteorStrikeChance", 20 * 60 * 60 * 5);
		propMeteorStrikeChance.setComment("The probability of a meteor spawning (an average of once every nTH ticks)");
		meteorStrikeChance = propMeteorStrikeChance.getInt();
		Property propMeteorShowerChance = config.get(CATEGORY_METEOR, "5.01_meteorShowerChance", 20 * 60 * 15);
		propMeteorShowerChance.setComment("The probability of a meteor spawning during meteor shower (an average of once every nTH ticks)");
		meteorShowerChance = propMeteorShowerChance.getInt();
		Property propMeteorShowerDuration = config.get(CATEGORY_METEOR, "5.02_meteorShowerDuration", 20 * 60 * 30);
		propMeteorShowerDuration.setComment("Max duration of meteor shower in ticks");
		meteorShowerDuration = propMeteorShowerDuration.getInt();
		
		radioStructure = CommonConfig.setDefZero(radioStructure, 1000);
		antennaStructure = CommonConfig.setDefZero(antennaStructure, 1000);
		atomStructure = CommonConfig.setDefZero(atomStructure, 1000);
		vertibirdStructure = CommonConfig.setDefZero(vertibirdStructure, 1000);
		dungeonStructure = CommonConfig.setDefZero(dungeonStructure, 1000);
		relayStructure = CommonConfig.setDefZero(relayStructure, 1000);
		satelliteStructure = CommonConfig.setDefZero(satelliteStructure, 1000);
		bunkerStructure = CommonConfig.setDefZero(bunkerStructure, 1000);
		siloStructure = CommonConfig.setDefZero(siloStructure, 1000);
		factoryStructure = CommonConfig.setDefZero(factoryStructure, 1000);
		dudStructure = CommonConfig.setDefZero(dudStructure, 1000);
		spaceshipStructure = CommonConfig.setDefZero(spaceshipStructure, 1000);
		barrelStructure = CommonConfig.setDefZero(barrelStructure, 1000);
		geyserWater = CommonConfig.setDefZero(geyserWater, 1000);
		geyserChlorine = CommonConfig.setDefZero(geyserChlorine, 1000);
		geyserVapor = CommonConfig.setDefZero(geyserVapor, 1000);
		broadcaster = CommonConfig.setDefZero(broadcaster, 1000);
		minefreq = CommonConfig.setDefZero(minefreq, 1000);
		radfreq = CommonConfig.setDefZero(radfreq, 1000);
		vaultfreq = CommonConfig.setDefZero(vaultfreq, 1000);
		meteorStructure = CommonConfig.setDefZero(meteorStructure, 15000);
		capsuleStructure = CommonConfig.setDefZero(capsuleStructure, 100);
		arcticStructure = CommonConfig.setDefZero(arcticStructure, 500);
		jungleStructure = CommonConfig.setDefZero(jungleStructure, 1000);
		
		meteorStrikeChance = CommonConfig.setDef(meteorStrikeChance, 1000);
		meteorShowerChance = CommonConfig.setDef(meteorShowerChance, 1000);
	}

}
