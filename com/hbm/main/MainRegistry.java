package com.hbm.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import com.hbm.blocks.ModBlocks;
import com.hbm.capability.RadiationCapability;
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
import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.effect.EntityEMPBlast;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.entity.effect.EntityNukeCloudNoShroom;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityBomber;
import com.hbm.entity.logic.EntityEMP;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.entity.missile.EntityBombletSelena;
import com.hbm.entity.missile.EntityBombletTheta;
import com.hbm.entity.missile.EntityBooster;
import com.hbm.entity.missile.EntityCarrier;
import com.hbm.entity.missile.EntityMissileAntiBallistic;
import com.hbm.entity.missile.EntityMissileBHole;
import com.hbm.entity.missile.EntityMissileBunkerBuster;
import com.hbm.entity.missile.EntityMissileBurst;
import com.hbm.entity.missile.EntityMissileBusterStrong;
import com.hbm.entity.missile.EntityMissileCluster;
import com.hbm.entity.missile.EntityMissileClusterStrong;
import com.hbm.entity.missile.EntityMissileDoomsday;
import com.hbm.entity.missile.EntityMissileDrill;
import com.hbm.entity.missile.EntityMissileEMP;
import com.hbm.entity.missile.EntityMissileEMPStrong;
import com.hbm.entity.missile.EntityMissileEndo;
import com.hbm.entity.missile.EntityMissileExo;
import com.hbm.entity.missile.EntityMissileGeneric;
import com.hbm.entity.missile.EntityMissileIncendiary;
import com.hbm.entity.missile.EntityMissileIncendiaryStrong;
import com.hbm.entity.missile.EntityMissileInferno;
import com.hbm.entity.missile.EntityMissileMicro;
import com.hbm.entity.missile.EntityMissileMirv;
import com.hbm.entity.missile.EntityMissileNuclear;
import com.hbm.entity.missile.EntityMissileRain;
import com.hbm.entity.missile.EntityMissileSchrabidium;
import com.hbm.entity.missile.EntityMissileStrong;
import com.hbm.entity.missile.EntityMissileTaint;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.entity.mob.EntityTaintedCreeper;
import com.hbm.entity.particle.EntityBSmokeFX;
import com.hbm.entity.particle.EntityChlorineFX;
import com.hbm.entity.particle.EntityDSmokeFX;
import com.hbm.entity.particle.EntityFogFX;
import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.entity.particle.EntityOrangeFX;
import com.hbm.entity.particle.EntityPinkCloudFX;
import com.hbm.entity.particle.EntitySSmokeFX;
import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.entity.particle.EntityTSmokeFX;
import com.hbm.entity.projectile.EntityAAShell;
import com.hbm.entity.projectile.EntityBombletZeta;
import com.hbm.entity.projectile.EntityBoxcar;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.entity.projectile.EntityBurningFOEQ;
import com.hbm.entity.projectile.EntityDuchessGambit;
import com.hbm.entity.projectile.EntityExplosiveBeam;
import com.hbm.entity.projectile.EntityFire;
import com.hbm.entity.projectile.EntityRocket;
import com.hbm.entity.projectile.EntityRocketHoming;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.entity.projectile.EntityShrapnel;
import com.hbm.forgefluid.FFPipeNetwork;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.GuiHandler;
import com.hbm.handler.HazmatRegistry;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemAssemblyTemplate;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;
import com.hbm.tileentity.bomb.TileEntityNukeFleija;
import com.hbm.tileentity.bomb.TileEntityNukeMan;
import com.hbm.tileentity.bomb.TileEntityTurretCIWS;
import com.hbm.tileentity.bomb.TileEntityTurretCheapo;
import com.hbm.tileentity.bomb.TileEntityTurretFlamer;
import com.hbm.tileentity.bomb.TileEntityTurretHeavy;
import com.hbm.tileentity.bomb.TileEntityTurretLight;
import com.hbm.tileentity.bomb.TileEntityTurretRocket;
import com.hbm.tileentity.bomb.TileEntityTurretSpitfire;
import com.hbm.tileentity.bomb.TileEntityTurretTau;
import com.hbm.tileentity.conductor.TileEntityCable;
import com.hbm.tileentity.conductor.TileEntityFFFluidDuct;
import com.hbm.tileentity.deco.TileEntityDecoBlock;
import com.hbm.tileentity.deco.TileEntityTestRender;
import com.hbm.tileentity.generic.TileEntityCloudResidue;
import com.hbm.tileentity.generic.TileEntityTaint;
import com.hbm.tileentity.machine.TileEntityConverterHeRf;
import com.hbm.tileentity.machine.TileEntityConverterRfHe;
import com.hbm.tileentity.machine.TileEntityDiFurnace;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityDummyPort;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;
import com.hbm.tileentity.machine.TileEntityMachineBattery;
import com.hbm.tileentity.machine.TileEntityMachineBoiler;
import com.hbm.tileentity.machine.TileEntityMachineBoilerElectric;
import com.hbm.tileentity.machine.TileEntityMachineChemplant;
import com.hbm.tileentity.machine.TileEntityMachineCoal;
import com.hbm.tileentity.machine.TileEntityMachineGenerator;
import com.hbm.tileentity.machine.TileEntityMachinePress;
import com.hbm.tileentity.machine.TileEntityMachineRTG;
import com.hbm.tileentity.machine.TileEntityMachineReactorSmall;
import com.hbm.tileentity.machine.TileEntityMachineTransformer;
import com.hbm.tileentity.machine.TileEntityMachineTurbine;

import net.minecraft.advancements.Advancement;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = RefStrings.MODID, version = RefStrings.VERSION, name = RefStrings.NAME)
public class MainRegistry {

	@SidedProxy(clientSide = RefStrings.CLIENTSIDE, serverSide = RefStrings.SERVERSIDE)
	public static ServerProxy proxy;
	
	@Mod.Instance(RefStrings.MODID)
	public static MainRegistry instance;
	
	public static Logger logger;
	
	public static List<FFPipeNetwork> allPipeNetworks = new ArrayList<FFPipeNetwork>();
	
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
	//Drillgon200: I have no idea what the two strings and the number at the end are.
	public static ArmorMaterial enumArmorMaterialT45 = EnumHelper.addArmorMaterial(RefStrings.MODID + ":T45", RefStrings.MODID + ":T45", 1000, new int[] {3, 6, 8, 3}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial enumArmorMaterialHazmat = EnumHelper.addArmorMaterial(RefStrings.MODID + ":HAZMAT", RefStrings.MODID + ":HAZMAT", 60, new int[] {1, 4, 5, 2}, 5, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	public static ArmorMaterial enumArmorMaterialHazmat2 = EnumHelper.addArmorMaterial(RefStrings.MODID + ":HAZMAT2", RefStrings.MODID + ":HAZMAT2", 60, new int[] {1, 4, 5, 2}, 5, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	public static ArmorMaterial enumArmorMaterialHazmat3 = EnumHelper.addArmorMaterial(RefStrings.MODID + ":HAZMAT3", RefStrings.MODID + ":HAZMAT3", 60, new int[] {1, 4, 5, 2}, 5, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	public static ArmorMaterial enumArmorMaterialPaa = EnumHelper.addArmorMaterial(RefStrings.MODID + ":PAA", RefStrings.MODID + ":PAA", 75, new int[] {3, 6, 8, 3}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial enumArmorMaterialSchrabidium = EnumHelper.addArmorMaterial(RefStrings.MODID + ":SCHRABIDIUM", RefStrings.MODID + ":SCHRABIDIUM", 100, new int[] {3, 6, 8, 3}, 50, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial enumArmorMaterialEuphemium = EnumHelper.addArmorMaterial(RefStrings.MODID + ":EUPHEMIUM", RefStrings.MODID + ":EUPHEMIUM", 150000000, new int[] {3, 6, 8, 3}, 100, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial enumArmorMaterialSteel = EnumHelper.addArmorMaterial(RefStrings.MODID + ":STEEL", RefStrings.MODID + ":STEEL", 20, new int[] {2, 5, 6, 2}, 5, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	public static ArmorMaterial enumArmorMaterialAlloy = EnumHelper.addArmorMaterial(RefStrings.MODID + ":ALLOY", RefStrings.MODID + ":ALLOY", 40, new int[] {3, 6, 8, 3}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	
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
	
	static {
		FluidRegistry.enableUniversalBucket();
	}
	
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	HBMSoundHandler.loadClass();
    	if(logger == null)
    		logger = event.getModLog();
    	MinecraftForge.EVENT_BUS.register(new ModEventHandler());
    	MinecraftForge.TERRAIN_GEN_BUS.register(new ModEventHandler());
		MinecraftForge.ORE_GEN_BUS.register(new ModEventHandler());
		PacketDispatcher.registerPackets();
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		
		
		CapabilityManager.INSTANCE.register(RadiationCapability.IEntityRadioactive.class, new RadiationCapability.EntityRadioactiveStorage(), RadiationCapability.EntityRadioactive.FACTORY);
		ModForgeFluids.init();
    	ModItems.preInit();
    	ModBlocks.preInit();
    	HbmPotion.init();
    	BulletConfigSyncingUtil.loadConfigsForSync();
    	
    	proxy.registerRenderInfo();
    	proxy.preInit(event);
    	Library.initSuperusers();
    	NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    	GameRegistry.registerTileEntity(TileEntityDummy.class, new ResourceLocation(RefStrings.MODID, "tileentity_dummy"));
    	GameRegistry.registerTileEntity(TileEntityMachineAssembler.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_assembler"));
    	GameRegistry.registerTileEntity(TileEntityDiFurnace.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_difurnace"));
    	GameRegistry.registerTileEntity(TileEntityMachinePress.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_press"));
    	GameRegistry.registerTileEntity(TileEntityTaint.class, new ResourceLocation(RefStrings.MODID, "tileentity_taint"));
    	GameRegistry.registerTileEntity(TileEntityTestRender.class, new ResourceLocation(RefStrings.MODID, "tileentity_testrenderer"));
    	GameRegistry.registerTileEntity(TileEntityMachineChemplant.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_chemplant"));
    	GameRegistry.registerTileEntity(TileEntityDummyPort.class, new ResourceLocation(RefStrings.MODID, "tileentity_dummy_fluid_port"));
    	GameRegistry.registerTileEntity(TileEntityCloudResidue.class, new ResourceLocation(RefStrings.MODID, "tileentity_cloud_residue"));
    	GameRegistry.registerTileEntity(TileEntityNukeMan.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_man"));
    	GameRegistry.registerTileEntity(TileEntityNukeFleija.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_fleija"));
    	GameRegistry.registerTileEntity(TileEntityMachineCoal.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_coal"));
    	GameRegistry.registerTileEntity(TileEntityMachineGenerator.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_generator"));
    	GameRegistry.registerTileEntity(TileEntityMachineReactorSmall.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_reactor_small"));
    	GameRegistry.registerTileEntity(TileEntityMachineRTG.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_rtg_grey"));
    	GameRegistry.registerTileEntity(TileEntityCable.class, new ResourceLocation(RefStrings.MODID, "tileentity_cable"));
    	GameRegistry.registerTileEntity(TileEntityMachineBattery.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_battery"));
    	GameRegistry.registerTileEntity(TileEntityMachineTransformer.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_transformer"));
    	GameRegistry.registerTileEntity(TileEntityConverterHeRf.class, new ResourceLocation(RefStrings.MODID, "tileentity_converter_he_rf"));
    	GameRegistry.registerTileEntity(TileEntityConverterRfHe.class, new ResourceLocation(RefStrings.MODID, "tileentity_converter_rf_he"));
    	GameRegistry.registerTileEntity(TileEntityMachineTurbine.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_turbine"));
    	GameRegistry.registerTileEntity(TileEntityFFFluidDuct.class, new ResourceLocation(RefStrings.MODID, "tileentity_ff_fluidduct"));
    	GameRegistry.registerTileEntity(TileEntityTurretCheapo.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_cheapo"));
    	GameRegistry.registerTileEntity(TileEntityTurretRocket.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_rocket"));
    	GameRegistry.registerTileEntity(TileEntityTurretLight.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_light"));
    	GameRegistry.registerTileEntity(TileEntityTurretHeavy.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_heavy"));
    	GameRegistry.registerTileEntity(TileEntityTurretFlamer.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_flamer"));
    	GameRegistry.registerTileEntity(TileEntityTurretTau.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_tau"));
    	GameRegistry.registerTileEntity(TileEntityTurretSpitfire.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_spitfire"));
    	GameRegistry.registerTileEntity(TileEntityTurretCIWS.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_ciws"));
    	GameRegistry.registerTileEntity(TileEntityDecoBlock.class, new ResourceLocation(RefStrings.MODID, "tileentity_deco_block"));
    	GameRegistry.registerTileEntity(TileEntityLaunchPad.class, new ResourceLocation(RefStrings.MODID, "tileentity_launch_pad"));
    	GameRegistry.registerTileEntity(TileEntityMachineBoiler.class, new ResourceLocation(RefStrings.MODID));
    	GameRegistry.registerTileEntity(TileEntityMachineBoilerElectric.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_boiler_electric"));
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
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_nuke_mk3"), EntityNukeExplosionMK3.class, "entity_nuke_mk3", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_fleija_rainbow"), EntityCloudFleijaRainbow.class, "entity_fleija_rainbow", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_explosive_beam"), EntityExplosiveBeam.class, "entity_explosive_beam", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_tainted_creeper"), EntityTaintedCreeper.class, "entity_tainted_creeper", i++, MainRegistry.instance, 80, 3, true, 0x813b9b, 0xd71fdd);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_nuclear_creeper"), EntityNuclearCreeper.class, "entity_nuclear_creeper", i++, MainRegistry.instance, 80, 3, true, 0x204131, 0x75CE00);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_nuke_cloud_no"), EntityNukeCloudNoShroom.class, "entity_nuke_cloud_no", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_cloud_fleija"), EntityCloudFleija.class, "entity_cloud_fleija", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_bullet"), EntityBullet.class, "entity_bullet", i++, MainRegistry.instance, 250, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_gasflame_fx"), EntityGasFlameFX.class, "entity_gasflame_fx", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_rocket"), EntityRocket.class, "entity_rocket", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_fire"), EntityFire.class, "entity_fire", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_aa_shell"), EntityAAShell.class, "entity_aa_shell", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_bomber"), EntityBomber.class, "entity_bomber", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_agent_orange"), EntityOrangeFX.class, "entity_agent_orange", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_pink_cloud_fx"), EntityPinkCloudFX.class, "entity_pink_cloud_fx", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_chlorine_fx"), EntityChlorineFX.class, "entity_chlorine_fx", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_stinger"), EntityRocketHoming.class, "entity_stinger", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_t_smoke_fx"), EntityTSmokeFX.class, "entity_t_smoke_fx", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_boxcar"), EntityBoxcar.class, "entity_boxcar", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_zeta"), EntityBombletZeta.class, "entity_zeta", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_emp"), EntityEMP.class, "entity_emp", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_theta"), EntityBombletTheta.class, "entity_theta", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_selena"), EntityBombletSelena.class, "entity_selena", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_black_hole"), EntityBlackHole.class, "entity_black_hole", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_emp_blast"), EntityEMPBlast.class, "entity_emp_blast", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_bullet_mk2"), EntityBulletBase.class, "entity_bullet_mk2", i++, MainRegistry.instance, 250, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_duchessgambit"), EntityDuchessGambit.class, "entity_duchessgambit", i++, MainRegistry.instance, 1000, 1, true);
	    
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_generic"), EntityMissileGeneric.class, "entity_missile_generic", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_incendiary"), EntityMissileIncendiary.class, "entity_missile_incendiary", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_cluster"), EntityMissileCluster.class, "entity_missile_cluster", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_bunker_buster"), EntityMissileBunkerBuster.class, "entity_missile_bunker_buster", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_strong"), EntityMissileStrong.class, "entity_missile_strong", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_incendiary_strong"), EntityMissileIncendiaryStrong.class, "entity_missile_incendiary_strong", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_cluster_strong"), EntityMissileClusterStrong.class, "entity_missile_cluster_strong", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_buster_strong"), EntityMissileBusterStrong.class, "entity_missile_buster_strong", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_emp_strong"), EntityMissileEMPStrong.class, "entity_missile_emp_strong", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_burst"), EntityMissileBurst.class, "entity_missile_burst", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_inferno"), EntityMissileInferno.class, "entity_missile_inferno", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_rain"), EntityMissileRain.class, "entity_missile_rain", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_drill"), EntityMissileDrill.class, "entity_missile_drill", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_nuclear"), EntityMissileNuclear.class, "entity_missile_nuclear", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_mirv"), EntityMissileMirv.class, "entity_missile_mirv", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_endo"), EntityMissileEndo.class, "entity_missile_endo", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_exo"), EntityMissileExo.class, "entity_missile_exo", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_doomsday"), EntityMissileDoomsday.class, "entity_missile_doomsday", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_taint"), EntityMissileTaint.class, "entity_missile_taint", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_micro"), EntityMissileMicro.class, "entity_missile_micro", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_bhole"), EntityMissileBHole.class, "entity_missile_bhole", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_schrab"), EntityMissileSchrabidium.class, "entity_missile_schrab", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_emp"), EntityMissileEMP.class, "entity_missile_emp", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_ab"), EntityMissileAntiBallistic.class, "entity_missile_ab", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_carrier"), EntityCarrier.class, "entity_carrier", i++, MainRegistry.instance, 1000, 1, true);
	    EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_booster"), EntityBooster.class, "entity_booster", i++, MainRegistry.instance, 1000, 1, true);
	    
	    ForgeChunkManager.setForcedChunkLoadingCallback(this, new LoadingCallback() {
			
	        @Override
	        public void ticketsLoaded(List<Ticket> tickets, World world) {
	            for(Ticket ticket : tickets) {
	            	
	                if(ticket.getEntity() instanceof IChunkLoader) {
	                    ((IChunkLoader)ticket.getEntity()).init(ticket);
	                }
	            }
	        }
	    });
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	
    	ModItems.init();
    	ModBlocks.init();
    	registerOreDict();
    	registerHazmatArmors();
    }

    

	@EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	ModItems.postInit();
    	ModBlocks.postInit();
    	ItemAssemblyTemplate.loadRecipesFromConfig();
    	CraftingManager.init();
    	proxy.postInit(event);
    }
    
    @EventHandler
    public void serverStarting(FMLServerStartingEvent evt){
    	evt.registerServerCommand(new CommandRadiation());
    	AdvancementManager.init(evt.getServer());
    }
    
    public void registerOreDict(){
    	OreDictionary.registerOre("ingotUranium", ModItems.ingot_uranium);
		OreDictionary.registerOre("ingotUranium233", ModItems.ingot_u233);
		OreDictionary.registerOre("ingotUranium235", ModItems.ingot_u235);
		OreDictionary.registerOre("ingotUranium238", ModItems.ingot_u238);
		OreDictionary.registerOre("ingotThorium", ModItems.ingot_th232);
		OreDictionary.registerOre("ingotThorium232", ModItems.ingot_th232);
		OreDictionary.registerOre("ingotPlutonium", ModItems.ingot_plutonium);
		OreDictionary.registerOre("ingotPlutonium238", ModItems.ingot_pu238);
		OreDictionary.registerOre("ingotPlutonium239", ModItems.ingot_pu239);
		OreDictionary.registerOre("ingotPlutonium240", ModItems.ingot_pu240);
		OreDictionary.registerOre("U233", ModItems.ingot_u233);
		OreDictionary.registerOre("U235", ModItems.ingot_u235);
		OreDictionary.registerOre("U238", ModItems.ingot_u238);
		OreDictionary.registerOre("Th232", ModItems.ingot_th232);
		OreDictionary.registerOre("Pu238", ModItems.ingot_pu238);
		OreDictionary.registerOre("Pu239", ModItems.ingot_pu239);
		OreDictionary.registerOre("Pu240", ModItems.ingot_pu240);
		OreDictionary.registerOre("ingotTitanium", ModItems.ingot_titanium);
		OreDictionary.registerOre("ingotSchrabidium", ModItems.ingot_schrabidium);
		OreDictionary.registerOre("dustSchrabidium", ModItems.powder_schrabidium);
		OreDictionary.registerOre("dustSulfur", ModItems.sulfur);
		OreDictionary.registerOre("dustNiter", ModItems.niter);
		OreDictionary.registerOre("dustSalpeter", ModItems.niter);
		OreDictionary.registerOre("dustLead", ModItems.powder_lead);
		OreDictionary.registerOre("dustNeptunium", ModItems.powder_neptunium);
		OreDictionary.registerOre("ingotCopper", ModItems.ingot_copper);
		OreDictionary.registerOre("ingotRedAlloy", ModItems.ingot_red_copper);
		OreDictionary.registerOre("ingotRedstoneAlloy", ModItems.ingot_red_copper);
		OreDictionary.registerOre("ingotAdvanced", ModItems.ingot_advanced_alloy);
		OreDictionary.registerOre("ingotAdvancedAlloy", ModItems.ingot_advanced_alloy);
		OreDictionary.registerOre("ingotTungsten", ModItems.ingot_tungsten);
		OreDictionary.registerOre("ingotAluminum", ModItems.ingot_aluminium);
		OreDictionary.registerOre("ingotBeryllium", ModItems.ingot_beryllium);
		OreDictionary.registerOre("ingotNeptunium", ModItems.ingot_neptunium);
		OreDictionary.registerOre("ingotLead", ModItems.ingot_lead);
		OreDictionary.registerOre("ingotLithium", ModItems.lithium);
		OreDictionary.registerOre("ingotMagnetizedTungsten", ModItems.ingot_magnetized_tungsten);
		OreDictionary.registerOre("ingotCMBSteel", ModItems.ingot_combine_steel);
		OreDictionary.registerOre("ingotDuraSteel", ModItems.ingot_dura_steel);
		OreDictionary.registerOre("ingotPolymer", ModItems.ingot_polymer);
		OreDictionary.registerOre("ingotLanthanium", ModItems.ingot_lanthanium);
		OreDictionary.registerOre("ingotActinium", ModItems.ingot_actinium);
		OreDictionary.registerOre("ingotDesh", ModItems.ingot_desh);
		OreDictionary.registerOre("ingotSaturnite", ModItems.ingot_saturnite);
		OreDictionary.registerOre("ingotEuphemium", ModItems.ingot_euphemium);
		OreDictionary.registerOre("ingotDineutronium", ModItems.ingot_dineutronium);
		OreDictionary.registerOre("ingotStarmetal", ModItems.ingot_starmetal);
		OreDictionary.registerOre("dustFluorite", ModItems.fluorite);
		OreDictionary.registerOre("nuggetLead", ModItems.nugget_lead);
		OreDictionary.registerOre("nuggetUranium", ModItems.nugget_uranium);
		OreDictionary.registerOre("nuggetUranium233", ModItems.nugget_u233);
		OreDictionary.registerOre("nuggetUranium235", ModItems.nugget_u235);
		OreDictionary.registerOre("nuggetUranium238", ModItems.nugget_u238);
		OreDictionary.registerOre("nuggetThorium", ModItems.nugget_th232);
		OreDictionary.registerOre("nuggetThorium232", ModItems.nugget_th232);
		OreDictionary.registerOre("nuggetPlutonium", ModItems.nugget_plutonium);
		OreDictionary.registerOre("nuggetPlutonium238", ModItems.nugget_pu238);
		OreDictionary.registerOre("nuggetPlutonium239", ModItems.nugget_pu239);
		OreDictionary.registerOre("nuggetPlutonium240", ModItems.nugget_pu240);
		OreDictionary.registerOre("tinyU233", ModItems.nugget_u233);
		OreDictionary.registerOre("tinyU235", ModItems.nugget_u235);
		OreDictionary.registerOre("tinyU238", ModItems.nugget_u238);
		OreDictionary.registerOre("tinyTh232", ModItems.nugget_th232);
		OreDictionary.registerOre("tinyPu238", ModItems.nugget_pu238);
		OreDictionary.registerOre("tinyPu239", ModItems.nugget_pu239);
		OreDictionary.registerOre("tinyPu240", ModItems.nugget_pu240);
		OreDictionary.registerOre("nuggetNeptunium", ModItems.nugget_neptunium);
		OreDictionary.registerOre("nuggetSchrabidium", ModItems.nugget_schrabidium);
		OreDictionary.registerOre("plateTitanium", ModItems.plate_titanium);
		OreDictionary.registerOre("plateAluminum", ModItems.plate_aluminium);
		OreDictionary.registerOre("plateDenseLead", ModItems.neutron_reflector);
		OreDictionary.registerOre("ingotSteel", ModItems.ingot_steel);
		OreDictionary.registerOre("plateSteel", ModItems.plate_steel);
		OreDictionary.registerOre("plateLead", ModItems.plate_lead);
		OreDictionary.registerOre("plateCopper", ModItems.plate_copper);
		OreDictionary.registerOre("plateIron", ModItems.plate_iron);
		OreDictionary.registerOre("plateGold", ModItems.plate_gold);
		OreDictionary.registerOre("plateAdvanced", ModItems.plate_advanced_alloy);
		OreDictionary.registerOre("plateSchrabidium", ModItems.plate_schrabidium);
		OreDictionary.registerOre("plateCMBSteel", ModItems.plate_combine_steel);
		OreDictionary.registerOre("plateDesh", ModItems.plate_desh);
		OreDictionary.registerOre("plateSaturnite", ModItems.plate_saturnite);
		OreDictionary.registerOre("plateEuphemium", ModItems.plate_euphemium);
		OreDictionary.registerOre("plateDineutronium", ModItems.plate_dineutronium);
		OreDictionary.registerOre("dustIron", ModItems.powder_iron);
		OreDictionary.registerOre("dustGold", ModItems.powder_gold);
		OreDictionary.registerOre("dustUranium", ModItems.powder_uranium);
		OreDictionary.registerOre("dustThorium", ModItems.powder_thorium);
		OreDictionary.registerOre("dustPlutonium", ModItems.powder_plutonium);
		OreDictionary.registerOre("dustTitanium", ModItems.powder_titanium);
		OreDictionary.registerOre("dustTungsten", ModItems.powder_tungsten);
		OreDictionary.registerOre("dustCopper", ModItems.powder_copper);
		OreDictionary.registerOre("dustBeryllium", ModItems.powder_beryllium);
		OreDictionary.registerOre("dustAluminum", ModItems.powder_aluminium);
		OreDictionary.registerOre("dustDiamond", ModItems.powder_diamond);
		OreDictionary.registerOre("dustEmerald", ModItems.powder_emerald);
		OreDictionary.registerOre("dustLapis", ModItems.powder_lapis);
		OreDictionary.registerOre("dustCoal", ModItems.powder_coal);
		OreDictionary.registerOre("dustLignite", ModItems.powder_lignite);
		OreDictionary.registerOre("dustAdvanced", ModItems.powder_advanced_alloy);
		OreDictionary.registerOre("dustAdvancedAlloy", ModItems.powder_advanced_alloy);
		OreDictionary.registerOre("dustCMBSteel", ModItems.powder_combine_steel);
		OreDictionary.registerOre("dustMagnetizedTungsten", ModItems.powder_magnetized_tungsten);
		OreDictionary.registerOre("dustRedAlloy", ModItems.powder_red_copper);
		OreDictionary.registerOre("dustRedstoneAlloy", ModItems.powder_red_copper);
		OreDictionary.registerOre("dustSteel", ModItems.powder_steel);
		OreDictionary.registerOre("dustLithium", ModItems.powder_lithium);
		OreDictionary.registerOre("dustNetherQuartz", ModItems.powder_quartz);
		OreDictionary.registerOre("dustDuraSteel", ModItems.powder_dura_steel);
		OreDictionary.registerOre("dustPolymer", ModItems.powder_polymer);
		OreDictionary.registerOre("dustLanthanium", ModItems.powder_lanthanium);
		OreDictionary.registerOre("dustActinium", ModItems.powder_actinium);
		OreDictionary.registerOre("dustDesh", ModItems.powder_desh);
		OreDictionary.registerOre("dustEuphemium", ModItems.powder_euphemium);
		OreDictionary.registerOre("dustDineutronium", ModItems.powder_dineutronium);

		OreDictionary.registerOre("dustNeptunium", ModItems.powder_neptunium);
		OreDictionary.registerOre("dustIodine", ModItems.powder_iodine);
		OreDictionary.registerOre("dustThorium", ModItems.powder_thorium);
		OreDictionary.registerOre("dustAstatine", ModItems.powder_astatine);
		OreDictionary.registerOre("dustNeodymium", ModItems.powder_neodymium);
		OreDictionary.registerOre("dustCaesium", ModItems.powder_caesium);
		OreDictionary.registerOre("dustStrontium", ModItems.powder_strontium);
		OreDictionary.registerOre("dustCobalt", ModItems.powder_cobalt);
		OreDictionary.registerOre("dustBromine", ModItems.powder_bromine);
		OreDictionary.registerOre("dustNiobium", ModItems.powder_niobium);
		OreDictionary.registerOre("dustTennessine", ModItems.powder_tennessine);
		OreDictionary.registerOre("dustCerium", ModItems.powder_cerium);

		OreDictionary.registerOre("nuggetNeodymium", ModItems.fragment_neodymium);
		OreDictionary.registerOre("nuggetCobalt", ModItems.fragment_cobalt);
		OreDictionary.registerOre("nuggetNiobium", ModItems.fragment_niobium);
		OreDictionary.registerOre("nuggetCerium", ModItems.fragment_cerium);
		OreDictionary.registerOre("nuggetLanthanium", ModItems.fragment_lanthanium);
		OreDictionary.registerOre("nuggetActinium", ModItems.fragment_actinium);

		OreDictionary.registerOre("gemCoal", Items.COAL);
		OreDictionary.registerOre("gemLignite", ModItems.lignite);

		OreDictionary.registerOre("oreUranium", ModBlocks.ore_uranium);
		OreDictionary.registerOre("oreThorium", ModBlocks.ore_thorium);
		OreDictionary.registerOre("oreTitanium", ModBlocks.ore_titanium);
		OreDictionary.registerOre("oreSchrabidium", ModBlocks.ore_schrabidium);
		OreDictionary.registerOre("oreSulfur", ModBlocks.ore_sulfur);
		OreDictionary.registerOre("oreNiter", ModBlocks.ore_niter);
		OreDictionary.registerOre("oreSapeter", ModBlocks.ore_niter);
		OreDictionary.registerOre("oreCopper", ModBlocks.ore_copper);
		OreDictionary.registerOre("oreTungsten", ModBlocks.ore_tungsten);
		OreDictionary.registerOre("oreAluminum", ModBlocks.ore_aluminium);
		OreDictionary.registerOre("oreFluorite", ModBlocks.ore_fluorite);
		OreDictionary.registerOre("oreLead", ModBlocks.ore_lead);
		OreDictionary.registerOre("oreBeryllium", ModBlocks.ore_beryllium);
		OreDictionary.registerOre("oreLignite", ModBlocks.ore_lignite);
		OreDictionary.registerOre("oreRareEarth", ModBlocks.ore_rare);

		OreDictionary.registerOre("oreUranium", ModBlocks.ore_nether_uranium);
		OreDictionary.registerOre("orePlutonium", ModBlocks.ore_nether_plutonium);
		OreDictionary.registerOre("oreTungsten", ModBlocks.ore_nether_tungsten);
		OreDictionary.registerOre("oreSulfur", ModBlocks.ore_nether_sulfur);
		OreDictionary.registerOre("oreSchrabidium", ModBlocks.ore_nether_schrabidium);

		OreDictionary.registerOre("oreUranium", ModBlocks.ore_meteor_uranium);
		OreDictionary.registerOre("oreThorium", ModBlocks.ore_meteor_thorium);
		OreDictionary.registerOre("oreTitanium", ModBlocks.ore_meteor_titanium);
		OreDictionary.registerOre("oreSulfur", ModBlocks.ore_meteor_sulfur);
		OreDictionary.registerOre("oreCopper", ModBlocks.ore_meteor_copper);
		OreDictionary.registerOre("oreTungsten", ModBlocks.ore_meteor_tungsten);
		OreDictionary.registerOre("oreAluminum", ModBlocks.ore_meteor_aluminium);
		OreDictionary.registerOre("oreLead", ModBlocks.ore_meteor_lead);
		OreDictionary.registerOre("oreLithium", ModBlocks.ore_meteor_lithium);
		OreDictionary.registerOre("oreStarmetal", ModBlocks.ore_meteor_starmetal);

		OreDictionary.registerOre("blockThorium", ModBlocks.block_thorium);
		OreDictionary.registerOre("blockUranium", ModBlocks.block_uranium);
		OreDictionary.registerOre("blockTitanium", ModBlocks.block_titanium);
		OreDictionary.registerOre("blockSulfur", ModBlocks.block_sulfur);
		OreDictionary.registerOre("blockNiter", ModBlocks.block_niter);
		OreDictionary.registerOre("blockSalpeter", ModBlocks.block_niter);
		OreDictionary.registerOre("blockCopper", ModBlocks.block_copper);
		OreDictionary.registerOre("blockRedAlloy", ModBlocks.block_red_copper);
		OreDictionary.registerOre("blockRedstoneAlloy", ModBlocks.block_red_copper);
		OreDictionary.registerOre("blockAdvanced", ModBlocks.block_advanced_alloy);
		OreDictionary.registerOre("blockTungsten", ModBlocks.block_tungsten);
		OreDictionary.registerOre("blockAluminum", ModBlocks.block_aluminium);
		OreDictionary.registerOre("blockFluorite", ModBlocks.block_fluorite);
		OreDictionary.registerOre("blockSteel", ModBlocks.block_steel);
		OreDictionary.registerOre("blockLead", ModBlocks.block_lead);
		OreDictionary.registerOre("blockBeryllium", ModBlocks.block_beryllium);
		OreDictionary.registerOre("blockSchrabidium", ModBlocks.block_schrabidium);
		OreDictionary.registerOre("blockCMBSteel", ModBlocks.block_combine_steel);
		OreDictionary.registerOre("blockMagnetizedTungsten", ModBlocks.block_magnetized_tungsten);
		OreDictionary.registerOre("blockDesh", ModBlocks.block_desh);
    }
    
    private void registerHazmatArmors() {
    	HazmatRegistry.instance.registerHazmat(ModItems.hazmat_helmet, 0.2F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_plate, 0.4F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_legs, 0.3F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_boots, 0.1F);
		
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_helmet_red, 0.3F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_plate_red, 0.6F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_legs_red, 0.45F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_boots_red, 0.15F);
		
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_helmet_grey, 0.4F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_plate_grey, 0.8F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_legs_grey, 0.6F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_boots_grey, 0.2F);
		
		HazmatRegistry.instance.registerHazmat(ModItems.t45_helmet, 0.4F);
		HazmatRegistry.instance.registerHazmat(ModItems.t45_plate, 0.8F);
		HazmatRegistry.instance.registerHazmat(ModItems.t45_legs, 0.6F);
		HazmatRegistry.instance.registerHazmat(ModItems.t45_boots, 0.2F);
		
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_paa_helmet, 0.6F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_paa_plate, 1.2F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_paa_legs, 0.9F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_paa_boots, 0.3F);

		HazmatRegistry.instance.registerHazmat(ModItems.jackt, 0.3F);
		HazmatRegistry.instance.registerHazmat(ModItems.jackt2, 0.3F);
		
		HazmatRegistry.instance.registerHazmat(ModItems.gas_mask, 0.15F);
		HazmatRegistry.instance.registerHazmat(ModItems.gas_mask_m65, 0.175F);
		
		HazmatRegistry.instance.registerHazmat(ModItems.steel_helmet, 0.04F);
		HazmatRegistry.instance.registerHazmat(ModItems.steel_plate, 0.08F);
		HazmatRegistry.instance.registerHazmat(ModItems.steel_legs, 0.06F);
		HazmatRegistry.instance.registerHazmat(ModItems.steel_boots, 0.02F);
		
		HazmatRegistry.instance.registerHazmat(Items.IRON_HELMET, 0.04F);
		HazmatRegistry.instance.registerHazmat(Items.IRON_CHESTPLATE, 0.08F);
		HazmatRegistry.instance.registerHazmat(Items.IRON_LEGGINGS, 0.06F);
		HazmatRegistry.instance.registerHazmat(Items.IRON_BOOTS, 0.02F);
		
		HazmatRegistry.instance.registerHazmat(Items.GOLDEN_HELMET, 0.04F);
		HazmatRegistry.instance.registerHazmat(Items.GOLDEN_CHESTPLATE, 0.08F);
		HazmatRegistry.instance.registerHazmat(Items.GOLDEN_LEGGINGS, 0.06F);
		HazmatRegistry.instance.registerHazmat(Items.GOLDEN_BOOTS, 0.02F);
		
		HazmatRegistry.instance.registerHazmat(ModItems.alloy_helmet, 0.08F);
		HazmatRegistry.instance.registerHazmat(ModItems.alloy_plate, 0.16F);
		HazmatRegistry.instance.registerHazmat(ModItems.alloy_legs, 0.12F);
		HazmatRegistry.instance.registerHazmat(ModItems.alloy_boots, 0.04F);
		
		HazmatRegistry.instance.registerHazmat(ModItems.schrabidium_helmet, 1.2F);
		HazmatRegistry.instance.registerHazmat(ModItems.schrabidium_plate, 2.4F);
		HazmatRegistry.instance.registerHazmat(ModItems.schrabidium_legs, 1.8F);
		HazmatRegistry.instance.registerHazmat(ModItems.schrabidium_boots, 0.6F);
		
		HazmatRegistry.instance.registerHazmat(ModItems.euphemium_helmet, 6F);
		HazmatRegistry.instance.registerHazmat(ModItems.euphemium_plate, 12F);
		HazmatRegistry.instance.registerHazmat(ModItems.euphemium_legs, 9F);
		HazmatRegistry.instance.registerHazmat(ModItems.euphemium_boots, 3F);
		
	}
    
}
