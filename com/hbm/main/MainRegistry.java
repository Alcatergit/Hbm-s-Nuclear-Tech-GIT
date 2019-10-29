package com.hbm.main;

import org.apache.logging.log4j.Logger;

import com.hbm.blocks.ModBlocks;
import com.hbm.command.CommandRadiation;
import com.hbm.creativetabs.BlockTab;
import com.hbm.creativetabs.ConsumableTab;
import com.hbm.creativetabs.ControlTab;
import com.hbm.creativetabs.MachineTab;
import com.hbm.creativetabs.MissileTab;
import com.hbm.creativetabs.NukeTab;
import com.hbm.creativetabs.PartsTab;
import com.hbm.creativetabs.TemplateTab;
import com.hbm.creativetabs.WeaponTab;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.entity.mob.EntityTaintedCreeper;
import com.hbm.entity.particle.EntityBSmokeFX;
import com.hbm.entity.particle.EntityDSmokeFX;
import com.hbm.entity.particle.EntityFogFX;
import com.hbm.entity.particle.EntitySSmokeFX;
import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.entity.projectile.EntityBurningFOEQ;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.entity.projectile.EntityShrapnel;
import com.hbm.handler.GuiHandler;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.tileentity.machine.TileEntityDiFurnace;
import com.hbm.tileentity.machine.TileEntityMachinePress;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = RefStrings.MODID, version = RefStrings.VERSION, name = RefStrings.NAME)
public class MainRegistry {

	@SidedProxy(clientSide = RefStrings.CLIENTSIDE, serverSide = RefStrings.SERVERSIDE)
	public static ServerProxy proxy;
	
	@Mod.Instance(RefStrings.MODID)
	public static MainRegistry instance;
	
	public static Logger logger;
	
	//Creative Tabs
	//ingots, nuggets, wires, machine parts
	public static CreativeTabs partsTab = new PartsTab(CreativeTabs.getNextID(), "tabParts");
	//items that belong in machines, fuels, etc
	public static CreativeTabs controlTab = new ControlTab(CreativeTabs.getNextID(), "tabControl");
	//templates, siren tracks
	public static CreativeTabs templateTab = new TemplateTab(CreativeTabs.getNextID(), "tabTemplate");
	//ore and mineral blocks
	public static CreativeTabs blockTab = new BlockTab(CreativeTabs.getNextID(), "tabBlocks");
	//machines, structure parts
	public static CreativeTabs machineTab = new MachineTab(CreativeTabs.getNextID(), "tabMachine");
	//bombs
	public static CreativeTabs nukeTab = new NukeTab(CreativeTabs.getNextID(), "tabNuke");
	//missiles, satellites
	public static CreativeTabs missileTab = new MissileTab(CreativeTabs.getNextID(), "tabMissile");
	//turrets, weapons, ammo
	public static CreativeTabs weaponTab = new WeaponTab(CreativeTabs.getNextID(), "tabWeapon");
	//drinks, kits, tools
	public static CreativeTabs consumableTab = new ConsumableTab(CreativeTabs.getNextID(), "tabConsumable");
	
	public static boolean enableDebugMode = true;
	public static boolean enableMycelium = false;
	public static boolean enablePlutoniumOre = false;
	public static boolean enableDungeons = true;
	public static boolean enableMDOres = true;
	public static boolean enableMines = true;
	public static boolean enableRad = true;
	public static boolean enableNITAN = true;
	public static boolean enableNukeClouds = true;
	public static boolean enableAutoCleanup = false;
	public static boolean enableMeteorStrikes = true;
	public static boolean enableMeteorShowers = true;
	public static boolean enableMeteorTails = true;
	public static boolean enableSpecialMeteors = true;
	public static boolean enableBomberShortMode = false;
	public static boolean enableVaults = true;
	public static boolean enableRads = true;
	public static boolean enableCataclysm = false;
	public static boolean enableExtendedLogging = false;
	public static boolean enableHardcoreTaint = false;
	public static boolean enableGuns = true;

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
	
	public static int gadgetRadius = 150;
	public static int boyRadius = 120;
	public static int manRadius = 175;
	public static int mikeRadius = 250;
	public static int tsarRadius = 500;
	public static int prototypeRadius = 150;
	public static int fleijaRadius = 50;
	public static int soliniumRadius = 75;
	public static int n2Radius = 100;
	public static int missileRadius = 100;
	public static int mirvRadius = 100;
	public static int fatmanRadius = 35;
	public static int nukaRadius = 25;
	public static int aSchrabRadius = 20;

	public static int blastSpeed = 1024;
	public static int falloutRange = 100;
	public static int fSpeed = 256;
	//public static int falloutDura = 100;
	
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
	
	public static int broadcaster = 5000;
	public static int minefreq = 64;
	public static int radfreq = 5000;
	public static int vaultfreq = 2500;
	
	public static int meteorStrikeChance = 20 * 60 * 180;
	public static int meteorShowerChance = 20 * 60 * 5;
	public static int meteorShowerDuration = 6000;
	public static int limitExplosionLifespan = 0;
	public static int radarRange = 1000;
	public static int radarBuffer = 30;
	public static int radarAltitude = 55;
	public static int ciwsHitrate = 50;

	public static int mk4 = 1024;
	public static int rain = 0;
	public static int cont = 0;
	public static int fogRad = 100;
	public static int fogCh = 20;
	public static float hellRad = 0.1F;

	public static int generalOverride = 11;
	public static int polaroidID = 1;

	public static int taintID = 62;
	public static int radiationID = 63;
	public static int bangID = 64;
	public static int mutationID = 65;
	public static int radxID = 66;
	public static int leadID = 67;

	public static int x;
	public static int y;
	public static int z;
	public static long time;
	
	//Armor Materials
	public static ArmorMaterial enumArmorMaterialT45 = EnumHelper.addArmorMaterial("T45", "T45", 1000, new int[] {2, 5, 4, 1}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	
	//Tool Materials
	public static ToolMaterial enumToolMaterialSchrabidium = EnumHelper.addToolMaterial("SCHRABIDIUM", 3, 10000, 50.0F, 100.0F, 200);
	public static ToolMaterial enumToolMaterialHammer = EnumHelper.addToolMaterial("SCHRABIDIUMHAMMER", 3, 0, 50.0F, 999999996F, 200);
	public static ToolMaterial enumToolMaterialChainsaw = EnumHelper.addToolMaterial("CHAINSAW", 3, 1500, 50.0F, 22.0F, 0);
	public static ToolMaterial enumToolMaterialSteel = EnumHelper.addToolMaterial("STEEL", 2, 500, 7.5F, 2.0F, 10);
	public static ToolMaterial enumToolMaterialTitanium = EnumHelper.addToolMaterial("TITANIUM", 3, 750, 9.0F, 2.5F, 15);
	public static ToolMaterial enumToolMaterialAlloy= EnumHelper.addToolMaterial("ALLOY", 3, 2000, 15.0F, 5.0F, 5);
	public static ToolMaterial enumToolMaterialCmb = EnumHelper.addToolMaterial("CMB", 3, 8500, 40.0F, 55F, 100);
	public static ToolMaterial enumToolMaterialElec = EnumHelper.addToolMaterial("ELEC", 3, 4700, 30.0F, 12.0F, 2);
	public static ToolMaterial enumToolMaterialDesh = EnumHelper.addToolMaterial("DESH", 2, 0, 7.5F, 2.0F, 10);

	public static ToolMaterial enumToolMaterialSaw = EnumHelper.addToolMaterial("SAW", 2, 750, 2.0F, 3.5F, 25);
	public static ToolMaterial enumToolMaterialBat = EnumHelper.addToolMaterial("BAT", 0, 500, 1.5F, 3F, 25);
	public static ToolMaterial enumToolMaterialBatNail = EnumHelper.addToolMaterial("BATNAIL", 0, 450, 1.0F, 4F, 25);
	public static ToolMaterial enumToolMaterialGolfClub = EnumHelper.addToolMaterial("GOLFCLUB", 1, 1000, 2.0F, 5F, 25);
	public static ToolMaterial enumToolMaterialPipeRusty = EnumHelper.addToolMaterial("PIPERUSTY", 1, 350, 1.5F, 4.5F, 25);
	public static ToolMaterial enumToolMaterialPipeLead = EnumHelper.addToolMaterial("PIPELEAD", 1, 250, 1.5F, 5.5F, 25);

	public static ToolMaterial enumToolMaterialBottleOpener = EnumHelper.addToolMaterial("OPENER", 1, 250, 1.5F, 0.5F, 200);
	public static ToolMaterial enumToolMaterialSledge = EnumHelper.addToolMaterial("SHIMMERSLEDGE", 1, 0, 25.0F, 26F, 200);

	public static ToolMaterial enumToolMaterialMultitool = EnumHelper.addToolMaterial("MULTITOOL", 3, 5000, 25F, 5.5F, 25);
	
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	if(logger == null)
    		logger = event.getModLog();
    	MinecraftForge.EVENT_BUS.register(new ModEventHandler());
    	MinecraftForge.TERRAIN_GEN_BUS.register(new ModEventHandler());
		MinecraftForge.ORE_GEN_BUS.register(new ModEventHandler());
		PacketDispatcher.registerPackets();
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		proxy.registerRenderInfo();
    	ModItems.preInit();
    	ModBlocks.preInit();
    	HbmPotion.init();

    	proxy.preInit(event);
    	NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    	GameRegistry.registerTileEntity(TileEntityDiFurnace.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_difurnace"));
    	GameRegistry.registerTileEntity(TileEntityMachinePress.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_press"));
    	int i = 0;
    	EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_nuke_mk4"), EntityNukeExplosionMK4.class, "entity_nuke_mk4", i++, MainRegistry.instance, 1000, 1, true);
    	EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_nuclear_fog"), EntityFogFX.class, "entity_nuclear_fog", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_d_smoke_fx"), EntityDSmokeFX.class, "entity_d_smoke_fx", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_nuke_cloud_small"), EntityNukeCloudSmall.class, "entity_nuke_cloud_small", i++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_fallout_rain"), EntityFalloutRain.class, "entity_fallout_rain", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_smoke_fx"), EntitySmokeFX.class, "entity_smoke_fx", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_b_smoke_fx"), EntityBSmokeFX.class, "entity_b_smoke_fx", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_shrapnel"), EntityShrapnel.class, "enity_shrapnel", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_s_smoke_fx"), EntitySSmokeFX.class, "entity_s_smoke_fx", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_rubble"), EntityRubble.class, "entity_rubble", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_burning_foeq"), EntityBurningFOEQ.class, "entity_burning_foeq", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_tainted_creeper"), EntityTaintedCreeper.class, "entity_tainted_creeper", i++, MainRegistry.instance, 80, 3, true, 0x813b9b, 0xd71fdd);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_nuclear_creeper"), EntityNuclearCreeper.class, "entity_nuclear_creeper", i++, MainRegistry.instance, 80, 3, true, 0x204131, 0x75CE00);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	ModItems.init();
    	ModBlocks.init();
    	registerOreDict();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	ModItems.postInit();
    	ModBlocks.postInit();
    	CraftingManager.init();
    }
    
    @EventHandler
    public void serverStarting(FMLServerStartingEvent evt){
    	evt.registerServerCommand(new CommandRadiation());
    }
    
    public void registerOreDict(){
    	OreDictionary.registerOre("plateGold", ModItems.plate_gold);
    }
    
}
