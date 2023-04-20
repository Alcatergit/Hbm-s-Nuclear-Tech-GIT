package com.hbm.config;

import java.util.HashMap;
import java.util.HashSet;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class CompatibilityConfig {

	public static HashMap dimensionRad;

	public static HashMap uraniumSpawn;
	public static HashMap thoriumSpawn;
	public static HashMap titaniumSpawn;
	public static HashMap sulfurSpawn;
	public static HashMap aluminiumSpawn;
	public static HashMap copperSpawn;
	public static HashMap fluoriteSpawn;
	public static HashMap niterSpawn;
	public static HashMap tungstenSpawn;
	public static HashMap leadSpawn;
	public static HashMap berylliumSpawn;
	public static HashMap ligniteSpawn;
	public static HashMap asbestosSpawn;
	public static HashMap rareSpawn;
	public static HashMap lithiumSpawn;
	public static HashMap cinnebarSpawn;
	public static HashMap oilcoalSpawn;
	public static HashMap gassshaleSpawn;
	public static HashMap gasbubbleSpawn;
	public static HashMap explosivebubbleSpawn;
	public static HashMap cobaltSpawn;
	
	public static HashMap ironClusterSpawn;
	public static HashMap titaniumClusterSpawn;
	public static HashMap aluminiumClusterSpawn;

	public static HashMap reiiumSpawn;
	public static HashMap weidaniumSpawn;
	public static HashMap australiumSpawn;
	public static HashMap verticiumSpawn;
	public static HashMap unobtainiumSpawn;
	public static HashMap daffergonSpawn;
	
	public static HashMap netherUraniumSpawn;
	public static HashMap netherTungstenSpawn;
	public static HashMap netherSulfurSpawn;
	public static HashMap netherPhosphorusSpawn;
	public static HashMap netherCoalSpawn;
	public static HashMap netherPlutoniumSpawn;
	public static HashMap netherCobaltSpawn;

	public static HashMap endTixiteSpawn;

	public static HashMap bedrockOilSpawn;
	
	public static HashMap radioStructure;
	public static HashMap antennaStructure;
	public static HashMap atomStructure;
	public static HashMap vertibirdStructure;
	public static HashMap dungeonStructure;
	public static HashMap relayStructure;
	public static HashMap satelliteStructure;
	public static HashMap bunkerStructure;
	public static HashMap siloStructure;
	public static HashMap factoryStructure;
	public static HashMap dudStructure;
	public static HashMap spaceshipStructure;
	public static HashMap barrelStructure;
	public static HashMap geyserWater;
	public static HashMap geyserChlorine;
	public static HashMap geyserVapor;
	public static HashMap geyserNether;
	public static HashMap meteorStructure;
	public static HashMap capsuleStructure;
	public static HashMap broadcaster;
	public static HashMap minefreq;
	public static HashMap radminefreq;
	public static HashMap radfreq;
	public static HashMap vaultfreq;
	public static HashMap arcticStructure;
	public static HashMap jungleStructure;
	public static HashMap pyramidStructure;
	
	public static HashMap meteorStrikeChance;
	public static HashMap meteorShowerChance;
	public static HashMap meteorShowerDuration;

	public static HashMap mobModRadresistance;
	public static HashSet mobModRadimmune;

	public static HashMap mobRadresistance;
	public static HashSet mobRadimmune;

	public static boolean mobGear = true;

	public static boolean modLoot = true;

	public static boolean doEvaporateWater = true;
	public static HashSet evaporateWater;
	public static boolean doFillCraterWithWater = true;
	public static HashMap fillCraterWithWater;

	
	public static void loadFromConfig(Configuration config) {
		final String CATEGORY_DIMRAD = "01_dimension_radiation";
		final String CATEGORY_DIMORE = "02_dimension_ores";
		final String CATEGORY_DIMSTRUC = "03_dimension_structures";
		final String CATEGORY_DUNGEON = "04_dimension_dungeons";
		final String CATEGORY_METEOR = "05_dimension_meteors";
		final String CATEGORY_MOB = "06_mobs";
		final String CATEGORY_NUKES = "07_nukes";

		String dimRadComment = "Amount of background radiation in the dimension in Rad/s - <dimID:Rad> (Int:Float)";
		dimensionRad = CommonConfig.createConfigHashMap(config, CATEGORY_DIMRAD, "01.01_dimensionRadiation", dimRadComment, "Int", "Float", new String[]{ "0:0.0", "-1:0.666", "1:0.001", "-28:0.245", "-27:0.0288", "-26:0.0288", "-29:0.0212", "-30:10", "-31:0.1" }, ":");

		//Ores
		uraniumSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.01_uraniumSpawnrate", "Amount of uranium ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:7" }, ":");
		titaniumSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.02_titaniumSpawnrate", "Amount of titanium ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:8" }, ":");
		sulfurSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.03_sulfurSpawnrate", "Amount of sulfur ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:5" }, ":");
		aluminiumSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.04_aluminiumSpawnrate", "Amount of aluminium ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:7" }, ":");
		copperSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.05_copperSpawnrate", "Amount of copper ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:12" }, ":");
		fluoriteSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.06_fluoriteSpawnrate", "Amount of fluorite ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:6" }, ":");
		niterSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.07_niterSpawnrate", "Amount of niter ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:6" }, ":");
		tungstenSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.08_tungstenSpawnrate", "Amount of tungsten ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:10" }, ":");
		leadSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.09_leadSpawnrate", "Amount of lead ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:6" }, ":");
		berylliumSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.10_berylliumSpawnrate", "Amount of beryllium ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:6" }, ":");
		thoriumSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.11_thoriumSpawnrate", "Amount of thorium ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:7" }, ":");
		ligniteSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.12_ligniteSpawnrate", "Amount of lignite ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:2" }, ":");
		asbestosSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.13_asbestosSpawnRate", "Amount of asbestos ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:2" }, ":");
		lithiumSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.14_lithiumSpawnRate", "Amount of schist lithium ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:6" }, ":");
		rareSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.15_rareEarthSpawnRate", "Amount of rare earth ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:6" }, ":");
		oilcoalSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.16_oilCoalSpawnRate", "Spawns an oily coal vein every nTH chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:128" }, ":");
		gassshaleSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.17_gasShaleSpawnRate", "Amount of oil shale veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:5" }, ":");
		explosivebubbleSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.18_explosiveBubbleSpawnRate", "Spawns an explosive gas bubble every nTH chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:80" }, ":");
		gasbubbleSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.19_gasBubbleSpawnRate", "Spawns a gas bubble every nTH chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:40" }, ":");
		cinnebarSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.20_cinnebarSpawnRate", "Amount of cinnebar ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:1" }, ":");
		cobaltSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.21_cobaltSpawnRate", "Amount of cobalt ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:2" }, ":");
		
		ironClusterSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.22_ironClusterSpawn", "Amount of iron cluster veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:4" }, ":");
		titaniumClusterSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.23_titaniumClusterSpawn", "Amount of titanium cluster veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:2" }, ":");
		aluminiumClusterSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.24_aluminiumClusterSpawn", "Amount of aluminium cluster veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:3" }, ":");
		
		reiiumSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.25_reiiumSpawnRate", "Amount of reiium ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "-29:1" }, ":");
		weidaniumSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.26_weidaniumSpawnRate", "Amount of weidanium ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "-31:1" }, ":");
		australiumSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.27_australiumSpawnRate", "Amount of australium ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "-31:1" }, ":");
		verticiumSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.28_verticiumSpawnRate", "Amount of verticium ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "-30:1" }, ":");
		unobtainiumSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.29_unobtainiumSpawnRate", "Amount of unobtainium ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "-28:1" }, ":");
		daffergonSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.30_daffergonSpawnRate", "Amount of daffergon ore veins per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "-30:1" }, ":");
		
		bedrockOilSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "01.31_bedrockOilSpawnRate", "Amount of end bedrock oil spawns per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "0:200" }, ":");
		
		netherUraniumSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "02.N00_uraniumSpawnrate", "Amount of nether uranium per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "-1:8" }, ":");
		netherTungstenSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "02.N01_tungstenSpawnrate", "Amount of nether tungsten per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "-1:10" }, ":");
		netherSulfurSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "02.N02_sulfurSpawnrate", "Amount of nether sulfur per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "-1:26" }, ":");
		netherPhosphorusSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "02.N03_phosphorusSpawnrate", "Amount of nether phosphorus per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "-1:24" }, ":");
		netherCoalSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "02.N04_coalSpawnrate", "Amount of nether coal per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "-1:24" }, ":");
		netherPlutoniumSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "02.N05_plutoniumSpawnrate", "Amount of nether plutonium per chunk, if enabled - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "-1:8" }, ":");
		netherCobaltSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "02.N06_cobaltSpawnrate", "Amount of nether cobalt per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "-1:2" }, ":");
		
		endTixiteSpawn = CommonConfig.createConfigHashMap(config, CATEGORY_DIMORE, "03.E01_tixiteSpawnrate", "Amount of end trixite per chunk - <dimID:amount> (Int:Int)", "Int", "Int", new String[]{ "1:8" }, ":");
		

		//Structures
		radioStructure = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.01_radioSpawn", "Spawn radio station on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:1000" }, ":");
		antennaStructure = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.02_antennaSpawn", "Spawn antenna on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:750" }, ":");
		atomStructure = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.03_atomSpawn", "Spawn power plant on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:500" }, ":");
		vertibirdStructure = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.04_vertibirdSpawn", "Spawn vertibird on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:500" }, ":");
		dungeonStructure = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.05_dungeonSpawn", "Spawn library dungeon on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:64" }, ":");
		relayStructure = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.06_relaySpawn", "Spawn relay on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:500" }, ":");
		satelliteStructure = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.07_satelliteSpawn", "Spawn satellite dish on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:500" }, ":");
		bunkerStructure = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.08_bunkerSpawn", "Spawn bunker on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:1000" }, ":");
		siloStructure = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.09_siloSpawn", "Spawn missile silo on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:1000" }, ":");
		factoryStructure = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.10_factorySpawn", "Spawn factory on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:1000" }, ":");
		dudStructure = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.11_dudSpawn", "Spawn dud on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:500" }, ":");
		spaceshipStructure = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.12_spaceshipSpawn", "Spawn spaceship on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:1000" }, ":");
		barrelStructure = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.13_barrelSpawn", "Spawn waste tank on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:5000" }, ":");
		broadcaster = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.14_broadcasterSpawn", "Spawn corrupt broadcaster on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:5000" }, ":");
		minefreq = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.15_landmineSpawn", "Spawn AP landmine on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:64" }, ":");
		radminefreq = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.16_sellafiteChunkSpawn", "Spawn sellafield block on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:128" }, ":");
		radfreq = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.17_radHotsoptSpawn", "Spawn big radiation hotspot on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:5000" }, ":");
		vaultfreq = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.18_vaultSpawn", "Spawn locked safe on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:2500" }, ":");
		
		geyserWater = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.19_geyserWaterSpawn", "Spawn water geyser on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:3000" }, ":");
		geyserChlorine = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.20_geyserChlorineSpawn", "Spawn poison geyser on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:3000" }, ":");
		geyserVapor = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.21_geyserVaporSpawn", "Spawn vapor geyser on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:500" }, ":");
		geyserNether = CommonConfig.createConfigHashMap(config, CATEGORY_DIMSTRUC, "03.22_geyserNetherSpawn", "Spawn nether geyser on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "-1:2" }, ":");
		
		meteorStructure = CommonConfig.createConfigHashMap(config, CATEGORY_DUNGEON, "03.23_meteorStructureSpan", "Spawn meteor dungeon on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:15000" }, ":");
		capsuleStructure = CommonConfig.createConfigHashMap(config, CATEGORY_DUNGEON, "03.24_capsuleSpawn", "Spawn landing capsule on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:100" }, ":");
		arcticStructure = CommonConfig.createConfigHashMap(config, CATEGORY_DUNGEON, "03.25_arcticVaultSpawn", "Spawn artic code vault on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:500" }, ":");
		jungleStructure = CommonConfig.createConfigHashMap(config, CATEGORY_DUNGEON, "03.26_jungleDungeonSpawn", "Spawn jungle dungeon on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:2000" }, ":");
		pyramidStructure = CommonConfig.createConfigHashMap(config, CATEGORY_DUNGEON, "03.27_pyramidSpawn", "Spawn pyramid on every nTH chunk - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:4000" }, ":");
		
		meteorStrikeChance = CommonConfig.createConfigHashMap(config, CATEGORY_METEOR, "05.01_meteorStrikeChance", "The probability of a meteor spawning per tick (an average of once every nTH ticks) - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:"+20 * 60 * 60 * 5, "-28:"+20 * 60, "-27:"+20 * 60 * 10, "-26:"+20 * 60 * 10, "-29:"+20 * 60 * 13, "-30:"+20 * 60 * 1000, "-31:"+20 * 60 * 35 }, ":");
		meteorShowerChance = CommonConfig.createConfigHashMap(config, CATEGORY_METEOR, "05.02_meteorShowerChance", "The probability of a meteor spawning during meteor shower per tick (an average of once every nTH ticks) - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:"+20 * 60 * 15, "-28:"+20 * 60, "-27:"+20 * 60 * 10, "-26:"+20 * 60 * 10, "-29:"+20 * 60 * 13, "-30:"+20 * 60 * 1000, "-31:"+20 * 60 * 35 }, ":");
		meteorShowerDuration = CommonConfig.createConfigHashMap(config, CATEGORY_METEOR, "05.03_meteorShowerDuration", "Max duration of meteor shower in ticks - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:"+20 * 60 * 30, "-28:"+20 * 60 * 60, "-27:"+20 * 60 * 3, "-26:"+20 * 60 * 3, "-29:"+20 * 60 * 60, "-30:"+20*10, "-31:"+20 * 60 }, ":");
		

		String mobModRadComment = "Amount of radiation resistance the all mobs of that mod get. Radresistance s is calculated as s=(1-0.2^r). So a resistance value of 3.0 means that 99.2%=(1-0.2^3.0) of the radiation gets blocked. - <mod=radresistance> (String:Float)";
		mobModRadresistance = CommonConfig.createConfigHashMap(config, CATEGORY_MOB, "06.01_mobModRadresistance", mobModRadComment, "String", "Float", new String[]{ "biomesoplenty=0.5" }, "=");
		mobModRadimmune = CommonConfig.createConfigHashSet(config, CATEGORY_MOB, "06.02_mobModRadimmune", "List of mods whose entities should all be immune to radiation. - <mob> (String)", "String", new String[]{ "srparasites" });

		String mobRadComment = "Amount of radiation resistance the mob gets. Radresistance s is calculated as s=(1-0.2^r). So a resistance value of 3.0 means that 99.2%=(1-0.2^3.0) of the radiation gets blocked. - <mod:mob=radresistance> (String:Float)";
		mobRadresistance = CommonConfig.createConfigHashMap(config, CATEGORY_MOB, "06.03_mobRadresistance", mobRadComment, "String", "Float", new String[]{ "biomesoplenty:entity.wasp=2.0" }, "=");
		mobRadimmune = CommonConfig.createConfigHashSet(config, CATEGORY_MOB, "06.04_mobRadimmune", "List of mobs that are immune to radiation. - <mob> (String)", "String", new String[]{ "minecraft:entity.Slime", "minecraft:entity.Vex" });
	
		mobGear = CommonConfig.createConfigBool(config, CATEGORY_MOB, "06.05_mobGear", "If true then mobs will be given gear (armor/weapons/gasmasks) from this mod when spawned", true);
		modLoot = CommonConfig.createConfigBool(config, CATEGORY_MOB, "06.06_modLoot", "If true then this mod will generarte loot for chests", true);
	

		doEvaporateWater =  CommonConfig.createConfigBool(config, CATEGORY_NUKES, "07.01_doEvaporateWater", "If true then nukes will evaporate water in range if it is in a wet place. It creates a short lagg spike instead of long medium lagg.", true);
		evaporateWater = CommonConfig.createConfigHashSet(config, CATEGORY_NUKES, "07.02_evaporateWater", "List of dimIDs where nukes evaporate water in range. - <dimID> (Int)", "Int", new String[]{ "0" });
		doFillCraterWithWater =  CommonConfig.createConfigBool(config, CATEGORY_NUKES, "07.04_doFillCraterWithWater", "If true then nukes will fill the crater with water if it is in a wet place. It creates a bit of lagg but looks better than without it.", true);
		fillCraterWithWater = CommonConfig.createConfigHashMap(config, CATEGORY_NUKES, "07.04_fillCraterWithWater", "Waterlevel per dimension which the nuke uses to fill the crater. {+n=>waterlevel height, 0=>dimension waterlevel, -n=> n blocks below dimension waterlevel } - <dimID:n> (Int:Int)", "Int", "Int", new String[]{ "0:0" }, ":");
	}
}
