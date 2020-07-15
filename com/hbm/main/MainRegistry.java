package com.hbm.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GLContext;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockCrate;
import com.hbm.blocks.generic.EntityGrenadeTau;
import com.hbm.capability.RadiationCapability;
import com.hbm.command.CommandHbm;
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
import com.hbm.entity.effect.EntityCloudSolinium;
import com.hbm.entity.effect.EntityCloudTom;
import com.hbm.entity.effect.EntityEMPBlast;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.entity.effect.EntityNukeCloudBig;
import com.hbm.entity.effect.EntityNukeCloudNoShroom;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.effect.EntityRagingVortex;
import com.hbm.entity.effect.EntityVortex;
import com.hbm.entity.grenade.EntityGrenadeASchrab;
import com.hbm.entity.grenade.EntityGrenadeBlackHole;
import com.hbm.entity.grenade.EntityGrenadeBreach;
import com.hbm.entity.grenade.EntityGrenadeBurst;
import com.hbm.entity.grenade.EntityGrenadeCloud;
import com.hbm.entity.grenade.EntityGrenadeCluster;
import com.hbm.entity.grenade.EntityGrenadeElectric;
import com.hbm.entity.grenade.EntityGrenadeFire;
import com.hbm.entity.grenade.EntityGrenadeFlare;
import com.hbm.entity.grenade.EntityGrenadeFrag;
import com.hbm.entity.grenade.EntityGrenadeGas;
import com.hbm.entity.grenade.EntityGrenadeGascan;
import com.hbm.entity.grenade.EntityGrenadeGeneric;
import com.hbm.entity.grenade.EntityGrenadeIFBouncy;
import com.hbm.entity.grenade.EntityGrenadeIFBrimstone;
import com.hbm.entity.grenade.EntityGrenadeIFConcussion;
import com.hbm.entity.grenade.EntityGrenadeIFGeneric;
import com.hbm.entity.grenade.EntityGrenadeIFHE;
import com.hbm.entity.grenade.EntityGrenadeIFHopwire;
import com.hbm.entity.grenade.EntityGrenadeIFImpact;
import com.hbm.entity.grenade.EntityGrenadeIFIncendiary;
import com.hbm.entity.grenade.EntityGrenadeIFMystery;
import com.hbm.entity.grenade.EntityGrenadeIFNull;
import com.hbm.entity.grenade.EntityGrenadeIFSpark;
import com.hbm.entity.grenade.EntityGrenadeIFSticky;
import com.hbm.entity.grenade.EntityGrenadeIFToxic;
import com.hbm.entity.grenade.EntityGrenadeLemon;
import com.hbm.entity.grenade.EntityGrenadeMIRV;
import com.hbm.entity.grenade.EntityGrenadeMk2;
import com.hbm.entity.grenade.EntityGrenadeNuclear;
import com.hbm.entity.grenade.EntityGrenadeNuke;
import com.hbm.entity.grenade.EntityGrenadePC;
import com.hbm.entity.grenade.EntityGrenadePlasma;
import com.hbm.entity.grenade.EntityGrenadePoison;
import com.hbm.entity.grenade.EntityGrenadePulse;
import com.hbm.entity.grenade.EntityGrenadeSchrabidium;
import com.hbm.entity.grenade.EntityGrenadeShrapnel;
import com.hbm.entity.grenade.EntityGrenadeSmart;
import com.hbm.entity.grenade.EntityGrenadeStrong;
import com.hbm.entity.grenade.EntityGrenadeZOMG;
import com.hbm.entity.item.EntityMovingItem;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.entity.logic.EntityBlast;
import com.hbm.entity.logic.EntityBomber;
import com.hbm.entity.logic.EntityDeathBlast;
import com.hbm.entity.logic.EntityEMP;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.entity.logic.EntityNukeExplosionPlus;
import com.hbm.entity.logic.EntityTomBlast;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.entity.missile.EntityBobmazon;
import com.hbm.entity.missile.EntityBombletSelena;
import com.hbm.entity.missile.EntityBombletTheta;
import com.hbm.entity.missile.EntityBooster;
import com.hbm.entity.missile.EntityCarrier;
import com.hbm.entity.missile.EntityMIRV;
import com.hbm.entity.missile.EntityMinerRocket;
import com.hbm.entity.missile.EntityMissileAntiBallistic;
import com.hbm.entity.missile.EntityMissileBHole;
import com.hbm.entity.missile.EntityMissileBunkerBuster;
import com.hbm.entity.missile.EntityMissileBurst;
import com.hbm.entity.missile.EntityMissileBusterStrong;
import com.hbm.entity.missile.EntityMissileCluster;
import com.hbm.entity.missile.EntityMissileClusterStrong;
import com.hbm.entity.missile.EntityMissileCustom;
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
import com.hbm.entity.missile.EntitySoyuz;
import com.hbm.entity.missile.EntitySoyuzCapsule;
import com.hbm.entity.mob.EntityCyberCrab;
import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.entity.mob.EntityTaintCrab;
import com.hbm.entity.mob.EntityTaintedCreeper;
import com.hbm.entity.mob.EntityTeslaCrab;
import com.hbm.entity.particle.EntityBSmokeFX;
import com.hbm.entity.particle.EntityChlorineFX;
import com.hbm.entity.particle.EntityCloudFX;
import com.hbm.entity.particle.EntityDSmokeFX;
import com.hbm.entity.particle.EntityFogFX;
import com.hbm.entity.particle.EntityGasFX;
import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.entity.particle.EntityOilSpillFX;
import com.hbm.entity.particle.EntityOrangeFX;
import com.hbm.entity.particle.EntityPinkCloudFX;
import com.hbm.entity.particle.EntitySSmokeFX;
import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.entity.particle.EntityTSmokeFX;
import com.hbm.entity.projectile.EntityAAShell;
import com.hbm.entity.projectile.EntityBaleflare;
import com.hbm.entity.projectile.EntityBombletZeta;
import com.hbm.entity.projectile.EntityBoxcar;
import com.hbm.entity.projectile.EntityBuilding;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.entity.projectile.EntityBurningFOEQ;
import com.hbm.entity.projectile.EntityChopperMine;
import com.hbm.entity.projectile.EntityCombineBall;
import com.hbm.entity.projectile.EntityDischarge;
import com.hbm.entity.projectile.EntityDuchessGambit;
import com.hbm.entity.projectile.EntityExplosiveBeam;
import com.hbm.entity.projectile.EntityFallingNuke;
import com.hbm.entity.projectile.EntityFire;
import com.hbm.entity.projectile.EntityLN2;
import com.hbm.entity.projectile.EntityLaser;
import com.hbm.entity.projectile.EntityLaserBeam;
import com.hbm.entity.projectile.EntityMeteor;
import com.hbm.entity.projectile.EntityMinerBeam;
import com.hbm.entity.projectile.EntityMiniMIRV;
import com.hbm.entity.projectile.EntityMiniNuke;
import com.hbm.entity.projectile.EntityModBeam;
import com.hbm.entity.projectile.EntityOilSpill;
import com.hbm.entity.projectile.EntityPlasmaBeam;
import com.hbm.entity.projectile.EntityRailgunBlast;
import com.hbm.entity.projectile.EntityRainbow;
import com.hbm.entity.projectile.EntityRocket;
import com.hbm.entity.projectile.EntityRocketHoming;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.entity.projectile.EntitySchrab;
import com.hbm.entity.projectile.EntityShrapnel;
import com.hbm.entity.projectile.EntitySparkBeam;
import com.hbm.entity.projectile.EntityTom;
import com.hbm.entity.projectile.EntityWaterSplash;
import com.hbm.forgefluid.FFPipeNetwork;
import com.hbm.forgefluid.FluidTypeHandler;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.handler.BobmazonOfferFactory;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.GuiHandler;
import com.hbm.handler.HazmatRegistry;
import com.hbm.handler.VersionChecker;
import com.hbm.inventory.CentrifugeRecipes;
import com.hbm.inventory.CrystallizerRecipes;
import com.hbm.inventory.ShredderRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.HbmWorld;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.saveddata.satellites.Satellite;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.TileEntityProxyEnergy;
import com.hbm.tileentity.TileEntityProxyInventory;
import com.hbm.tileentity.bomb.TileEntityBombMulti;
import com.hbm.tileentity.bomb.TileEntityCompactLauncher;
import com.hbm.tileentity.bomb.TileEntityCrashedBomb;
import com.hbm.tileentity.bomb.TileEntityLandmine;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;
import com.hbm.tileentity.bomb.TileEntityLaunchTable;
import com.hbm.tileentity.bomb.TileEntityNukeBalefire;
import com.hbm.tileentity.bomb.TileEntityNukeBoy;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;
import com.hbm.tileentity.bomb.TileEntityNukeFleija;
import com.hbm.tileentity.bomb.TileEntityNukeGadget;
import com.hbm.tileentity.bomb.TileEntityNukeMan;
import com.hbm.tileentity.bomb.TileEntityNukeMike;
import com.hbm.tileentity.bomb.TileEntityNukeN2;
import com.hbm.tileentity.bomb.TileEntityNukeN45;
import com.hbm.tileentity.bomb.TileEntityNukePrototype;
import com.hbm.tileentity.bomb.TileEntityNukeSolinium;
import com.hbm.tileentity.bomb.TileEntityNukeTsar;
import com.hbm.tileentity.bomb.TileEntityRailgun;
import com.hbm.tileentity.bomb.TileEntityTurretCIWS;
import com.hbm.tileentity.bomb.TileEntityTurretCheapo;
import com.hbm.tileentity.bomb.TileEntityTurretFlamer;
import com.hbm.tileentity.bomb.TileEntityTurretHeavy;
import com.hbm.tileentity.bomb.TileEntityTurretLight;
import com.hbm.tileentity.bomb.TileEntityTurretRocket;
import com.hbm.tileentity.bomb.TileEntityTurretSpitfire;
import com.hbm.tileentity.bomb.TileEntityTurretTau;
import com.hbm.tileentity.conductor.TileEntityCable;
import com.hbm.tileentity.conductor.TileEntityCableSwitch;
import com.hbm.tileentity.conductor.TileEntityFFFluidDuct;
import com.hbm.tileentity.conductor.TileEntityFFFluidDuctMk2;
import com.hbm.tileentity.conductor.TileEntityFFFluidSuccMk2;
import com.hbm.tileentity.conductor.TileEntityFFGasDuct;
import com.hbm.tileentity.conductor.TileEntityFFGasDuctSolid;
import com.hbm.tileentity.conductor.TileEntityFFOilDuct;
import com.hbm.tileentity.conductor.TileEntityFFOilDuctSolid;
import com.hbm.tileentity.deco.TileEntityDecoBlock;
import com.hbm.tileentity.deco.TileEntityDecoBlockAlt;
import com.hbm.tileentity.deco.TileEntityDecoPoleSatelliteReceiver;
import com.hbm.tileentity.deco.TileEntityGeysir;
import com.hbm.tileentity.deco.TileEntityObjTester;
import com.hbm.tileentity.deco.TileEntityTestRender;
import com.hbm.tileentity.deco.TileEntityVent;
import com.hbm.tileentity.generic.TileEntityCloudResidue;
import com.hbm.tileentity.generic.TileEntityTaint;
import com.hbm.tileentity.machine.*;
import com.hbm.tileentity.machine.TileEntityMachineReactorLarge.ReactorFuelType;
import com.hbm.world.generator.CellularDungeonFactory;

import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = RefStrings.MODID, version = RefStrings.VERSION, name = RefStrings.NAME)
public class MainRegistry {

	static {
		HBMSoundHandler.init();
		FluidRegistry.enableUniversalBucket();
	}

	@SidedProxy(clientSide = RefStrings.CLIENTSIDE, serverSide = RefStrings.SERVERSIDE)
	public static ServerProxy proxy;

	@Mod.Instance(RefStrings.MODID)
	public static MainRegistry instance;

	public static Logger logger;

	public static List<FFPipeNetwork> allPipeNetworks = new ArrayList<FFPipeNetwork>();

	// Creative Tabs
	// ingots, nuggets, wires, machine parts
	public static CreativeTabs partsTab = new PartsTab(CreativeTabs.getNextID(), "tabParts");
	// items that belong in machines, fuels, etc
	public static CreativeTabs controlTab = new ControlTab(CreativeTabs.getNextID(), "tabControl");
	// templates, siren tracks
	public static CreativeTabs templateTab = new TemplateTab(CreativeTabs.getNextID(), "tabTemplate");
	// ore and mineral blocks
	public static CreativeTabs blockTab = new BlockTab(CreativeTabs.getNextID(), "tabBlocks");
	// machines, structure parts
	public static CreativeTabs machineTab = new MachineTab(CreativeTabs.getNextID(), "tabMachine");
	// bombs
	public static CreativeTabs nukeTab = new NukeTab(CreativeTabs.getNextID(), "tabNuke");
	// missiles, satellites
	public static CreativeTabs missileTab = new MissileTab(CreativeTabs.getNextID(), "tabMissile");
	// turrets, weapons, ammo
	public static CreativeTabs weaponTab = new WeaponTab(CreativeTabs.getNextID(), "tabWeapon");
	// drinks, kits, tools
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
	public static boolean enableVirus = true;
	public static boolean enableCrosshairs = true;

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
	public static boolean disableNuclear;
	// public static int falloutDura = 100;

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

	public static int railgunDamage = 100;
	public static int railgunBuffer = 500000000;
	public static int railgunUse = 250000000;

	public static int fireDuration = 4 * 20;

	public static int generalOverride = 0;
	public static int polaroidID = 1;
	
	public static List<String> templateBlacklist = new ArrayList<String>();
	
	public static boolean dropCell = true;
	public static boolean dropSing = true;
	public static boolean dropStar = true;
	public static boolean dropCrys = true;
	public static boolean dropDead = true;

	public static int recursionDepth = 500;
	public static boolean recursiveStone = true;
	public static boolean recursiveNetherrack = true;

	public static boolean useShaders = false;
	public static boolean useShaders2 = true;

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

	// Armor Materials
	// Drillgon200: I have no idea what the two strings and the number at the
	// end are.
	public static ArmorMaterial enumArmorMaterialT45 = EnumHelper.addArmorMaterial(RefStrings.MODID + ":T45", RefStrings.MODID + ":T45", 1000, new int[] { 3, 6, 8, 3 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial enumArmorMaterialHazmat = EnumHelper.addArmorMaterial(RefStrings.MODID + ":HAZMAT", RefStrings.MODID + ":HAZMAT", 60, new int[] { 1, 4, 5, 2 }, 5, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	public static ArmorMaterial enumArmorMaterialHazmat2 = EnumHelper.addArmorMaterial(RefStrings.MODID + ":HAZMAT2", RefStrings.MODID + ":HAZMAT2", 60, new int[] { 1, 4, 5, 2 }, 5, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	public static ArmorMaterial enumArmorMaterialHazmat3 = EnumHelper.addArmorMaterial(RefStrings.MODID + ":HAZMAT3", RefStrings.MODID + ":HAZMAT3", 60, new int[] { 1, 4, 5, 2 }, 5, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	public static ArmorMaterial enumArmorMaterialPaa = EnumHelper.addArmorMaterial(RefStrings.MODID + ":PAA", RefStrings.MODID + ":PAA", 75, new int[] { 3, 6, 8, 3 }, 25, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial enumArmorMaterialSchrabidium = EnumHelper.addArmorMaterial(RefStrings.MODID + ":SCHRABIDIUM", RefStrings.MODID + ":SCHRABIDIUM", 100, new int[] { 3, 6, 8, 3 }, 50, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial enumArmorMaterialEuphemium = EnumHelper.addArmorMaterial(RefStrings.MODID + ":EUPHEMIUM", RefStrings.MODID + ":EUPHEMIUM", 15000000, new int[] { 3, 6, 8, 3 }, 100, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial enumArmorMaterialSteel = EnumHelper.addArmorMaterial(RefStrings.MODID + ":STEEL", RefStrings.MODID + ":STEEL", 20, new int[] { 2, 5, 6, 2 }, 5, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	public static ArmorMaterial enumArmorMaterialAlloy = EnumHelper.addArmorMaterial(RefStrings.MODID + ":ALLOY", RefStrings.MODID + ":ALLOY", 40, new int[] { 3, 6, 8, 3 }, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	public static ArmorMaterial enumArmorMaterialAusIII = EnumHelper.addArmorMaterial(RefStrings.MODID + ":AUSIII", RefStrings.MODID + ":AUSIII", 375, new int[] {2, 5, 6, 2}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	public static ArmorMaterial enumArmorMaterialTitanium = EnumHelper.addArmorMaterial(RefStrings.MODID + ":TITANIUM", RefStrings.MODID + ":TITANIUM", 25, new int[] {3, 6, 8, 3}, 9, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial enumArmorMaterialCmb = EnumHelper.addArmorMaterial(RefStrings.MODID + ":CMB", RefStrings.MODID + ":CMB", 60, new int[] {3, 6, 8, 3}, 50, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial enumArmorMaterialSecurity = EnumHelper.addArmorMaterial(RefStrings.MODID + ":SECURITY", RefStrings.MODID + ":SECURITY", 100, new int[] {3, 6, 8, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial enumArmorMaterialAsbestos = EnumHelper.addArmorMaterial(RefStrings.MODID + ":ASBESTOS", RefStrings.MODID + ":ASBESTOS", 20, new int[] {1, 3, 4, 1}, 5, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	public static ArmorMaterial aMatCobalt = EnumHelper.addArmorMaterial(RefStrings.MODID + ":COBALT", RefStrings.MODID + ":COBALT", 70, new int[] {3, 6, 8, 3}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial aMatStarmetal = EnumHelper.addArmorMaterial(RefStrings.MODID + ":STARMETAL", RefStrings.MODID + ":STARMETAL", 150, new int[] {3, 6, 8, 3}, 100, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	
	// Tool Materials
	public static ToolMaterial enumToolMaterialSchrabidium = EnumHelper.addToolMaterial(RefStrings.MODID + ":SCHRABIDIUM", 3, 10000, 50.0F, 100.0F, 200);
	public static ToolMaterial enumToolMaterialHammer = EnumHelper.addToolMaterial(RefStrings.MODID + ":SCHRABIDIUMHAMMER", 3, 0, 50.0F, 999999996F, 200);
	public static ToolMaterial enumToolMaterialChainsaw = EnumHelper.addToolMaterial(RefStrings.MODID + ":CHAINSAW", 3, 1500, 50.0F, 22.0F, 0);
	public static ToolMaterial enumToolMaterialSteel = EnumHelper.addToolMaterial(RefStrings.MODID + ":STEEL", 2, 500, 7.5F, 2.0F, 10);
	public static ToolMaterial enumToolMaterialTitanium = EnumHelper.addToolMaterial(RefStrings.MODID + ":TITANIUM", 3, 750, 9.0F, 2.5F, 15);
	public static ToolMaterial enumToolMaterialAlloy = EnumHelper.addToolMaterial(RefStrings.MODID + ":ALLOY", 3, 2000, 15.0F, 5.0F, 5);
	public static ToolMaterial enumToolMaterialCmb = EnumHelper.addToolMaterial(RefStrings.MODID + ":CMB", 3, 8500, 40.0F, 55F, 100);
	public static ToolMaterial enumToolMaterialElec = EnumHelper.addToolMaterial(RefStrings.MODID + ":ELEC", 3, 0, 30.0F, 12.0F, 2);
	public static ToolMaterial enumToolMaterialDesh = EnumHelper.addToolMaterial(RefStrings.MODID + ":DESH", 2, 0, 7.5F, 2.0F, 10);
	public static ToolMaterial enumToolMaterialCobalt = EnumHelper.addToolMaterial(RefStrings.MODID + ":COBALT", 3, 750, 9.0F, 2.5F, 15);

	public static ToolMaterial enumToolMaterialSaw = EnumHelper.addToolMaterial(RefStrings.MODID + ":SAW", 2, 750, 2.0F, 3.5F, 25);
	public static ToolMaterial enumToolMaterialBat = EnumHelper.addToolMaterial(RefStrings.MODID + ":BAT", 0, 500, 1.5F, 3F, 25);
	public static ToolMaterial enumToolMaterialBatNail = EnumHelper.addToolMaterial(RefStrings.MODID + ":BATNAIL", 0, 450, 1.0F, 4F, 25);
	public static ToolMaterial enumToolMaterialGolfClub = EnumHelper.addToolMaterial(RefStrings.MODID + ":GOLFCLUB", 1, 1000, 2.0F, 5F, 25);
	public static ToolMaterial enumToolMaterialPipeRusty = EnumHelper.addToolMaterial(RefStrings.MODID + ":PIPERUSTY", 1, 350, 1.5F, 4.5F, 25);
	public static ToolMaterial enumToolMaterialPipeLead = EnumHelper.addToolMaterial(RefStrings.MODID + ":PIPELEAD", 1, 250, 1.5F, 5.5F, 25);

	public static ToolMaterial enumToolMaterialBottleOpener = EnumHelper.addToolMaterial(RefStrings.MODID + ":OPENER", 1, 250, 1.5F, 0.5F, 200);
	public static ToolMaterial enumToolMaterialSledge = EnumHelper.addToolMaterial(RefStrings.MODID + ":SHIMMERSLEDGE", 1, 0, 25.0F, 26F, 200);

	public static ToolMaterial enumToolMaterialMultitool = EnumHelper.addToolMaterial(RefStrings.MODID + ":MULTITOOL", 3, 5000, 25F, 5.5F, 25);

	Random rand = new Random();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		if(logger == null)
			logger = event.getModLog();

		if(generalOverride > 0 && generalOverride < 19) {
			polaroidID = generalOverride;
		} else {
			polaroidID = rand.nextInt(18) + 1;
			while(polaroidID == 4 || polaroidID == 9)
				polaroidID = rand.nextInt(18) + 1;
		}

		MinecraftForge.EVENT_BUS.register(new ModEventHandler());
		MinecraftForge.TERRAIN_GEN_BUS.register(new ModEventHandler());
		MinecraftForge.ORE_GEN_BUS.register(new ModEventHandler());
		PacketDispatcher.registerPackets();

		reloadConfig();

		CapabilityManager.INSTANCE.register(RadiationCapability.IEntityRadioactive.class, new RadiationCapability.EntityRadioactiveStorage(), RadiationCapability.EntityRadioactive.FACTORY);
		ModForgeFluids.init();
		ModItems.preInit();
		ModBlocks.preInit();
		HbmPotion.init();
		BulletConfigSyncingUtil.loadConfigsForSync();
		CellularDungeonFactory.init();
		Satellite.register();
		VersionChecker.checkVersion();

		proxy.registerRenderInfo();
		HbmWorld.mainRegistry();
		proxy.preInit(event);
		Library.initSuperusers();
		
		enumArmorMaterialSchrabidium.setRepairItem(new ItemStack(ModItems.ingot_schrabidium));
		enumArmorMaterialHazmat.setRepairItem(new ItemStack(ModItems.hazmat_cloth));
		enumArmorMaterialHazmat2.setRepairItem(new ItemStack(ModItems.hazmat_cloth_red));
		enumArmorMaterialHazmat3.setRepairItem(new ItemStack(ModItems.hazmat_cloth_grey));
		enumArmorMaterialT45.setRepairItem(new ItemStack(ModItems.plate_titanium));
		enumArmorMaterialTitanium.setRepairItem(new ItemStack(ModItems.ingot_titanium));
		enumArmorMaterialSteel.setRepairItem(new ItemStack(ModItems.ingot_steel));
		enumArmorMaterialAlloy.setRepairItem(new ItemStack(ModItems.ingot_advanced_alloy));
		enumArmorMaterialPaa.setRepairItem(new ItemStack(ModItems.plate_paa));
		enumArmorMaterialCmb.setRepairItem(new ItemStack(ModItems.ingot_combine_steel));
		enumArmorMaterialAusIII.setRepairItem(new ItemStack(ModItems.ingot_australium));
		enumArmorMaterialSecurity.setRepairItem(new ItemStack(ModItems.plate_kevlar));
		enumToolMaterialSchrabidium.setRepairItem(new ItemStack(ModItems.ingot_schrabidium));
		enumToolMaterialHammer.setRepairItem(new ItemStack(Item.getItemFromBlock(ModBlocks.block_schrabidium)));
		enumToolMaterialChainsaw.setRepairItem(new ItemStack(ModItems.ingot_steel));
		enumToolMaterialTitanium.setRepairItem(new ItemStack(ModItems.ingot_titanium));
		enumToolMaterialSteel.setRepairItem(new ItemStack(ModItems.ingot_steel));
		enumToolMaterialAlloy.setRepairItem(new ItemStack(ModItems.ingot_advanced_alloy));
		enumToolMaterialCmb.setRepairItem(new ItemStack(ModItems.ingot_combine_steel));
		enumToolMaterialBottleOpener.setRepairItem(new ItemStack(ModItems.plate_steel));
		enumToolMaterialDesh.setRepairItem(new ItemStack(ModItems.ingot_desh));
		enumArmorMaterialAsbestos.setRepairItem(new ItemStack(ModItems.asbestos_cloth));
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		GameRegistry.registerTileEntity(TileEntityDummy.class, new ResourceLocation(RefStrings.MODID, "tileentity_dummy"));
		GameRegistry.registerTileEntity(TileEntityMachineAssembler.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_assembler"));
		GameRegistry.registerTileEntity(TileEntityDiFurnace.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_difurnace"));
		GameRegistry.registerTileEntity(TileEntityMachinePress.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_press"));
		GameRegistry.registerTileEntity(TileEntityTaint.class, new ResourceLocation(RefStrings.MODID, "tileentity_taint"));
		GameRegistry.registerTileEntity(TileEntityTestRender.class, new ResourceLocation(RefStrings.MODID, "tileentity_testrenderer"));
		GameRegistry.registerTileEntity(TileEntityMachineChemplant.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_chemplant"));
		GameRegistry.registerTileEntity(TileEntityDummyPort.class, new ResourceLocation(RefStrings.MODID, "tileentity_dummy_port"));
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
		GameRegistry.registerTileEntity(TileEntityMachineEPress.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_epress"));
		GameRegistry.registerTileEntity(TileEntityPylonRedWire.class, new ResourceLocation(RefStrings.MODID, "tileentity_pylon_red_wire"));
		GameRegistry.registerTileEntity(TileEntityWireCoated.class, new ResourceLocation(RefStrings.MODID, "tileentity_wire_coated"));
		GameRegistry.registerTileEntity(TileEntityMachineCentrifuge.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_centrifuge"));
		GameRegistry.registerTileEntity(TileEntityMachineGasCent.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_gascent"));
		GameRegistry.registerTileEntity(TileEntityMachineUF6Tank.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_uf6_tank"));
		GameRegistry.registerTileEntity(TileEntityMachinePuF6Tank.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_puf6_tank"));
		GameRegistry.registerTileEntity(TileEntityRailgun.class, new ResourceLocation(RefStrings.MODID, "tileentity_railgun"));
		GameRegistry.registerTileEntity(TileEntityMachineReactor.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_reactor"));
		GameRegistry.registerTileEntity(TileEntityMachineShredder.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_shredder"));
		GameRegistry.registerTileEntity(TileEntityDummyFluidPort.class, new ResourceLocation(RefStrings.MODID, "tileentity_dummy_fluid_port"));
		GameRegistry.registerTileEntity(TileEntityMachineFluidTank.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_fluidtank"));
		GameRegistry.registerTileEntity(TileEntityCableSwitch.class, new ResourceLocation(RefStrings.MODID, "tileentity_cable_switch"));
		GameRegistry.registerTileEntity(TileEntityFFOilDuctSolid.class, new ResourceLocation(RefStrings.MODID, "tileentity_ff_oil_duct_solid"));
		GameRegistry.registerTileEntity(TileEntityFFGasDuctSolid.class, new ResourceLocation(RefStrings.MODID, "tileentity_ff_gas_duct_solid"));
		GameRegistry.registerTileEntity(TileEntityFFOilDuct.class, new ResourceLocation(RefStrings.MODID, "tileentity_ff_oil_duct"));
		GameRegistry.registerTileEntity(TileEntityFFGasDuct.class, new ResourceLocation(RefStrings.MODID, "tileentity_ff_gas_duct"));
		GameRegistry.registerTileEntity(TileEntityMachineRefinery.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_refinery"));
		GameRegistry.registerTileEntity(TileEntityMachineCyclotron.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_cyclotron"));
		GameRegistry.registerTileEntity(TileEntityMachineSchrabidiumTransmutator.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_schrabidium_transmutator"));
		GameRegistry.registerTileEntity(TileEntityMachineSiren.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_siren"));
		GameRegistry.registerTileEntity(TileEntityBroadcaster.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_broadcaster"));
		GameRegistry.registerTileEntity(TileEntityGeiger.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_geiger"));
		GameRegistry.registerTileEntity(TileEntityHatch.class, new ResourceLocation(RefStrings.MODID, "tileentity_hatch"));
		GameRegistry.registerTileEntity(TileEntityVaultDoor.class, new ResourceLocation(RefStrings.MODID, "tileentity_vault_door"));
		GameRegistry.registerTileEntity(TileEntityBlastDoor.class, new ResourceLocation(RefStrings.MODID, "tileentity_blastdoor"));
		GameRegistry.registerTileEntity(TileEntityCrateIron.class, new ResourceLocation(RefStrings.MODID, "tileentity_crate_iron"));
		GameRegistry.registerTileEntity(TileEntityCrateSteel.class, new ResourceLocation(RefStrings.MODID, "tileentity_crate_steel"));
		GameRegistry.registerTileEntity(TileEntitySafe.class, new ResourceLocation(RefStrings.MODID, "tileentity_safe"));
		GameRegistry.registerTileEntity(TileEntityMachineKeyForge.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_key_forge"));
		GameRegistry.registerTileEntity(TileEntityNukeFurnace.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_furnace"));
		GameRegistry.registerTileEntity(TileEntityRtgFurnace.class, new ResourceLocation(RefStrings.MODID, "tileentity_rtg_furnace"));
		GameRegistry.registerTileEntity(TileEntityMachineSeleniumEngine.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_selenium"));
		GameRegistry.registerTileEntity(TileEntityReactorControl.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_reactor_control"));
		GameRegistry.registerTileEntity(TileEntityMachineRadGen.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_radgen"));
		GameRegistry.registerTileEntity(TileEntityMachineAmgen.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_amgen"));
		GameRegistry.registerTileEntity(TileEntityMachineSPP.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_spp"));
		GameRegistry.registerTileEntity(TileEntityMachineArcFurnace.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_arc_furnace"));
		GameRegistry.registerTileEntity(TileEntityMachineElectricFurnace.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_electric_furnace"));
		GameRegistry.registerTileEntity(TileEntityWasteDrum.class, new ResourceLocation(RefStrings.MODID, "tileentity_waste_drum"));
		GameRegistry.registerTileEntity(TileEntityMachineOilWell.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_oil_well"));
		GameRegistry.registerTileEntity(TileEntityMachinePumpjack.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_pumpjack"));
		GameRegistry.registerTileEntity(TileEntityMachineGasFlare.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_gas_flare"));
		GameRegistry.registerTileEntity(TileEntityMachineMiningDrill.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_mining_drill"));
		GameRegistry.registerTileEntity(TileEntityMachineTurbofan.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_turbofan"));
		GameRegistry.registerTileEntity(TileEntityMachineCMBFactory.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_cmb_factory"));
		GameRegistry.registerTileEntity(TileEntityMachineTeleporter.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_teleporter"));
		GameRegistry.registerTileEntity(TileEntityRadiobox.class, new ResourceLocation(RefStrings.MODID, "tileentity_radiobox"));
		GameRegistry.registerTileEntity(TileEntityRadioRec.class, new ResourceLocation(RefStrings.MODID, "tileentity_radiorec"));
		GameRegistry.registerTileEntity(TileEntityDecon.class, new ResourceLocation(RefStrings.MODID, "tileentity_decon"));
		GameRegistry.registerTileEntity(TileEntityVent.class, new ResourceLocation(RefStrings.MODID, "tileentity_vent"));
		GameRegistry.registerTileEntity(TileEntityChlorineSeal.class, new ResourceLocation(RefStrings.MODID, "tileentity_chlorine_seal"));
		GameRegistry.registerTileEntity(TileEntityStructureMarker.class, new ResourceLocation(RefStrings.MODID, "tileentity_structure_marker"));
		GameRegistry.registerTileEntity(TileEntityCoreTitanium.class, new ResourceLocation(RefStrings.MODID, "tileentity_core_titanium"));
		GameRegistry.registerTileEntity(TileEntityCoreAdvanced.class, new ResourceLocation(RefStrings.MODID, "tileentity_core_advanced"));
		GameRegistry.registerTileEntity(TileEntityReactorHatch.class, new ResourceLocation(RefStrings.MODID, "tileentity_reactor_hatch"));
		GameRegistry.registerTileEntity(TileEntityMachineReactorLarge.class, new ResourceLocation(RefStrings.MODID, "tileentity_reactor_large"));
		GameRegistry.registerTileEntity(TileEntityFusionHatch.class, new ResourceLocation(RefStrings.MODID, "tileentity_fusion_hatch"));
		GameRegistry.registerTileEntity(TileEntityFusionMultiblock.class, new ResourceLocation(RefStrings.MODID, "tileentity_fusion_multiblock"));
		GameRegistry.registerTileEntity(TileEntityWatzHatch.class, new ResourceLocation(RefStrings.MODID, "tileentity_watz_hatch"));
		GameRegistry.registerTileEntity(TileEntityWatzCore.class, new ResourceLocation(RefStrings.MODID, "tileentity_watz_core"));
		GameRegistry.registerTileEntity(TileEntityFWatzHatch.class, new ResourceLocation(RefStrings.MODID, "tileentity_fwatz_hatch"));
		GameRegistry.registerTileEntity(TileEntityFWatzCore.class, new ResourceLocation(RefStrings.MODID, "tileentity_fwatz_core"));
		GameRegistry.registerTileEntity(TileEntityNukeGadget.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_gadget"));
		GameRegistry.registerTileEntity(TileEntityNukeBoy.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_boy"));
		GameRegistry.registerTileEntity(TileEntityNukeMike.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_mike"));
		GameRegistry.registerTileEntity(TileEntityNukeTsar.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_tsar"));
		GameRegistry.registerTileEntity(TileEntityNukePrototype.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_prototype"));
		GameRegistry.registerTileEntity(TileEntityNukeSolinium.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_solinium"));
		GameRegistry.registerTileEntity(TileEntityNukeN2.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_n2"));
		GameRegistry.registerTileEntity(TileEntityNukeN45.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_n45"));
		GameRegistry.registerTileEntity(TileEntityNukeCustom.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_custom"));
		GameRegistry.registerTileEntity(TileEntityBombMulti.class, new ResourceLocation(RefStrings.MODID, "tileentity_bomb_multi"));
		GameRegistry.registerTileEntity(TileEntityCrashedBomb.class, new ResourceLocation(RefStrings.MODID, "tileentity_crashed_bomb"));
		GameRegistry.registerTileEntity(TileEntityLandmine.class, new ResourceLocation(RefStrings.MODID, "tileentity_landmine"));
		GameRegistry.registerTileEntity(TileEntityMachineTeleLinker.class, new ResourceLocation(RefStrings.MODID, "tileentity_telelinker"));
		GameRegistry.registerTileEntity(TileEntityMachineMissileAssembly.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_missile_assembly"));
		GameRegistry.registerTileEntity(TileEntityCompactLauncher.class, new ResourceLocation(RefStrings.MODID, "tileentity_compact_launcher"));
		GameRegistry.registerTileEntity(TileEntityMultiblock.class, new ResourceLocation(RefStrings.MODID, "tileentity_launcher_multiblock"));
		GameRegistry.registerTileEntity(TileEntityLaunchTable.class, new ResourceLocation(RefStrings.MODID, "tileentity_launch_table"));
		GameRegistry.registerTileEntity(TileEntityAMSEmitter.class, new ResourceLocation(RefStrings.MODID, "tileentity_ams_emitter"));
		GameRegistry.registerTileEntity(TileEntityAMSLimiter.class, new ResourceLocation(RefStrings.MODID, "tileentity_ams_limiter"));
		GameRegistry.registerTileEntity(TileEntityAMSBase.class, new ResourceLocation(RefStrings.MODID, "tileentity_ams_base"));
		GameRegistry.registerTileEntity(TileEntityMachineSatLinker.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_sat_linker"));
		GameRegistry.registerTileEntity(TileEntityMachineSatDock.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_sat_dock"));
		GameRegistry.registerTileEntity(TileEntityMachineDiesel.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_diesel"));
		GameRegistry.registerTileEntity(TileEntityForceField.class, new ResourceLocation(RefStrings.MODID, "tileentity_force_field"));
		GameRegistry.registerTileEntity(TileEntityMachineRadar.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_radar"));
		GameRegistry.registerTileEntity(TileEntityDecoPoleSatelliteReceiver.class, new ResourceLocation(RefStrings.MODID, "tileentity_deco_pole_satellite_receiver"));
		GameRegistry.registerTileEntity(TileEntityGeysir.class, new ResourceLocation(RefStrings.MODID, "tileentity_geyser"));
		GameRegistry.registerTileEntity(TileEntityObjTester.class, new ResourceLocation(RefStrings.MODID, "tileentity_obj_tester"));
		GameRegistry.registerTileEntity(TileEntityDecoBlockAlt.class, new ResourceLocation(RefStrings.MODID, "tileentity_deco_block_alt"));
		GameRegistry.registerTileEntity(TileEntityFFFluidDuctMk2.class, new ResourceLocation(RefStrings.MODID, "tileentity_ff_fludi_duct_mk2"));
		GameRegistry.registerTileEntity(TileEntityBarrel.class, new ResourceLocation(RefStrings.MODID, "tileentity_barrel"));
		GameRegistry.registerTileEntity(TileEntityTesla.class, new ResourceLocation(RefStrings.MODID, "tileentity_tesla"));
		GameRegistry.registerTileEntity(TileEntityCyberCrab.class, new ResourceLocation(RefStrings.MODID, "tileentity_cybercrab"));
		GameRegistry.registerTileEntity(TileEntityCoreEmitter.class, new ResourceLocation(RefStrings.MODID, "tileentity_core_emitter"));
		GameRegistry.registerTileEntity(TileEntityCoreReceiver.class, new ResourceLocation(RefStrings.MODID, "tileentity_core_receiver"));
		GameRegistry.registerTileEntity(TileEntityCoreInjector.class, new ResourceLocation(RefStrings.MODID, "tileentity_core_injector"));
		GameRegistry.registerTileEntity(TileEntityCoreStabilizer.class, new ResourceLocation(RefStrings.MODID, "tileentity_core_stabilizer"));
		GameRegistry.registerTileEntity(TileEntityCore.class, new ResourceLocation(RefStrings.MODID, "tileentity_core_core"));
		GameRegistry.registerTileEntity(TileEntitySoyuzCapsule.class, new ResourceLocation(RefStrings.MODID, "tileentity_soyuz_capsule"));
		GameRegistry.registerTileEntity(TileEntitySoyuzLauncher.class, new ResourceLocation(RefStrings.MODID, "tileentity_soyuz_launcher"));
		GameRegistry.registerTileEntity(TileEntityFFFluidSuccMk2.class, new ResourceLocation(RefStrings.MODID, "tileentity_ff_succ_mk2"));
		GameRegistry.registerTileEntity(TileEntityMachineCrystallizer.class, new ResourceLocation(RefStrings.MODID, "tileentity_acidomatic"));
		GameRegistry.registerTileEntity(TileEntitySoyuzStruct.class, new ResourceLocation(RefStrings.MODID, "tileentity_soyuz_struct"));
		GameRegistry.registerTileEntity(TileEntityITERStruct.class, new ResourceLocation(RefStrings.MODID, "tileentity_iter_struct"));
		GameRegistry.registerTileEntity(TileEntityMachineMiningLaser.class, new ResourceLocation(RefStrings.MODID, "tileentity_mining_laser"));
		GameRegistry.registerTileEntity(TileEntityProxyInventory.class, new ResourceLocation(RefStrings.MODID, "tileentity_proxy_inventory"));
		GameRegistry.registerTileEntity(TileEntityProxyEnergy.class, new ResourceLocation(RefStrings.MODID, "tileentity_proxy_power"));
		GameRegistry.registerTileEntity(TileEntityNukeBalefire.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_fstbmb"));
		GameRegistry.registerTileEntity(TileEntityProxyCombo.class, new ResourceLocation(RefStrings.MODID, "tileentity_proxy_combo"));
		GameRegistry.registerTileEntity(TileEntityMicrowave.class, new ResourceLocation(RefStrings.MODID, "tileentity_microwave"));

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
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_cloud_fx"), EntityCloudFX.class, "entity_cloud_fx", i++, MainRegistry.instance, 1000, 1, true);
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
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_spark_beam"), EntitySparkBeam.class, "entity_spark_beam", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_mod_beam"), EntityModBeam.class, "entity_mod_beam", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_vortex"), EntityVortex.class, "entity_vortex", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_raging_vortex"), EntityRagingVortex.class, "entity_raging_vortex", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_mini_nuke"), EntityMiniNuke.class, "entity_mini_nuke", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_mini_mirv"), EntityMiniMIRV.class, "entity_mini_mirv", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_baleflare"), EntityBaleflare.class, "entity_baleflare", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_balefire"), EntityBalefire.class, "entity_balefire", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_rainbow"), EntityRainbow.class, "entity_rainbow", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_plasma_beam"), EntityPlasmaBeam.class, "entity_plasma_beam", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_ln2"), EntityLN2.class, "entity_ln2", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_combine_ball"), EntityCombineBall.class, "entity_combine_ball", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_discharge"), EntityDischarge.class, "entity_discharge", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_schrab"), EntitySchrab.class, "entity_schrab", i++, MainRegistry.instance, 1000, 1, true);

		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_generic"), EntityGrenadeGeneric.class, "entity_grenade_generic", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_strong"), EntityGrenadeStrong.class, "entity_grenade_strong", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_frag"), EntityGrenadeFrag.class, "entity_grenade_frag", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_fire"), EntityGrenadeFire.class, "entity_grenade_fire", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_cluster"), EntityGrenadeCluster.class, "entity_grenade_cluster", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_flare"), EntityGrenadeFlare.class, "entity_grenade_flare", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_electric"), EntityGrenadeElectric.class, "entity_grenade_electric", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_poison"), EntityGrenadePoison.class, "entity_grenade_poison", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_gas"), EntityGrenadeGas.class, "entity_grenade_gas", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_schrabidium"), EntityGrenadeSchrabidium.class, "entity_grenade_schrabidium", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_pulse"), EntityGrenadePulse.class, "entity_grenade_pulse", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_plasma"), EntityGrenadePlasma.class, "entity_grenade_plasma", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_tau"), EntityGrenadeTau.class, "entity_grenade_tau", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_cloud"), EntityGrenadeCloud.class, "entity_grenade_cloud", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_pc"), EntityGrenadePC.class, "entity_grenade_pc", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_smart"), EntityGrenadeSmart.class, "entity_grenade_smart", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_mirv"), EntityGrenadeMIRV.class, "entity_grenade_mirv", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_breach"), EntityGrenadeBreach.class, "entity_grenade_breach", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_burst"), EntityGrenadeBurst.class, "entity_grenade_burst", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_lemon"), EntityGrenadeLemon.class, "entity_grenade_lemon", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_mk2"), EntityGrenadeMk2.class, "entity_grenade_mk2", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_aschrab"), EntityGrenadeASchrab.class, "entity_grenade_aschrab", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_zomg"), EntityGrenadeZOMG.class, "entity_grenade_zomg", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_shrapnel"), EntityGrenadeShrapnel.class, "entity_grenade_shrapnel", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_black_hole"), EntityGrenadeBlackHole.class, "entity_grenade_black_hole", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_gascan"), EntityGrenadeGascan.class, "entity_grenade_gascan", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_nuke"), EntityGrenadeNuke.class, "entity_grenade_nuke", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_nuclear"), EntityGrenadeNuclear.class, "entity_grenade_nuclear", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_generic"), EntityGrenadeIFGeneric.class, "entity_grenade_if_generic", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_he"), EntityGrenadeIFHE.class, "entity_grenade_if_he", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_bouncy"), EntityGrenadeIFBouncy.class, "entity_grenade_if_bouncy", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_sticky"), EntityGrenadeIFSticky.class, "entity_grenade_if_sticky", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_impact"), EntityGrenadeIFImpact.class, "entity_grenade_if_impact", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_incendiary"), EntityGrenadeIFIncendiary.class, "entity_grenade_if_incendiary", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_toxic"), EntityGrenadeIFToxic.class, "entity_grenade_if_toxic", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_concussion"), EntityGrenadeIFConcussion.class, "entity_grenade_if_concussion", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_brimstone"), EntityGrenadeIFBrimstone.class, "entity_grenade_if_brimstone", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_mystery"), EntityGrenadeIFMystery.class, "entity_grenade_if_mystery", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_spark"), EntityGrenadeIFSpark.class, "entity_grenade_if_spark", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_hopwire"), EntityGrenadeIFHopwire.class, "entity_grenade_if_hopwire", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_null"), EntityGrenadeIFNull.class, "entity_grenade_if_null", i++, MainRegistry.instance, 250, 1, true);

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

		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_deathblast"), EntityBlast.class, "entity_deathblast", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_railgun_pellet"), EntityRailgunBlast.class, "entity_railgun_pellet", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_gas_fx"), EntityGasFX.class, "entity_gas_fx", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_oil_spill"), EntityOilSpill.class, "entity_oil_spill", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_oil_spill_fx"), EntityOilSpillFX.class, "entity_oil_spill_fx", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_clound_solinium"), EntityCloudSolinium.class, "entity_clound_solinium", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_nuke_cloud_big"), EntityNukeCloudBig.class, "entity_nuke_cloud_big", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_nuke_explosion_advanced"), EntityNukeExplosionPlus.class, "entity_nuke_explosion_advanced", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_falling_bomb"), EntityFallingNuke.class, "entity_falling_bomb", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_custom_missile"), EntityMissileCustom.class, "entity_custom_missile", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_laser_blast"), EntityDeathBlast.class, "entity_laser_blast", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_miner_rocket"), EntityMinerRocket.class, "entity_miner_rocket", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_meteor"), EntityMeteor.class, "entity_meteor", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_bobmazon"), EntityBobmazon.class, "entity_bobmazon", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_cyber_crab"), EntityCyberCrab.class, "entity_cyber_crab", i++, MainRegistry.instance, 250, 1, true, 0xAAAAAA, 0x444444);
		//Drillgon200: The hunter chopper is messed up and janky and I don't know what to about it. I'd probably have to recode the whole thing, and I don't have time for that.
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_hunter_chopper"), EntityHunterChopper.class, "entity_hunter_chopper", i++, MainRegistry.instance, 1000, 1, true, 0x000020, 0x2D2D72);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_chopper_mine"), EntityChopperMine.class, "entity_chopper_mine", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_water_splash"), EntityWaterSplash.class, "entity_water_splash", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_miner_beam"), EntityMinerBeam.class, "entity_miner_beam", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_laser_beam"), EntityLaserBeam.class, "entity_laser_beam", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_mirvlet"), EntityMIRV.class, "entity_mirvlet", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_building"), EntityBuilding.class, "entity_building", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_taint_crab"), EntityTaintCrab.class, "entity_taint_crab", i++, MainRegistry.instance, 250, 1, true, 0xAAAAAA, 0xFF00FF);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_tesla_crab"), EntityTeslaCrab.class, "entity_tesla_crab", i++, MainRegistry.instance, 250, 1, true, 0xAAAAAA, 0x440000);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_tom_the_moonstone"), EntityTom.class, "entity_tom_the_moonstone", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_tom_bust"), EntityTomBlast.class, "entity_tom_bust", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_soyuz_capsule"), EntitySoyuzCapsule.class, "entity_soyuz_capsule", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_soyuz"), EntitySoyuz.class, "entity_soyuz", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_laser"), EntityLaser.class, "entity_laser", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_c_item"), EntityMovingItem.class, "entity_c_item", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_moonstone_blast"), EntityCloudTom.class, "entity_moonstone_blast", i++, MainRegistry.instance, 1000, 1, true);
		
		ForgeChunkManager.setForcedChunkLoadingCallback(this, new LoadingCallback() {

			@Override
			public void ticketsLoaded(List<Ticket> tickets, World world) {
				for(Ticket ticket : tickets) {

					if(ticket.getEntity() instanceof IChunkLoader) {
						((IChunkLoader) ticket.getEntity()).init(ticket);
					}
				}
			}
		});
		
		registerDispenserBehaviors();
	}

	public static void reloadConfig() {
		Configuration config = new Configuration(new File(proxy.getDataDir().getPath() + "/config/hbm/hbm.cfg"));
		config.load();

		final String CATEGORY_GENERAL = "01_general";
		enableDebugMode = config.get(CATEGORY_GENERAL, "1.00_enableDebugMode", false).getBoolean(false);
		enableMycelium = config.get(CATEGORY_GENERAL, "1.01_enableMyceliumSpread", false).getBoolean(false);
		enablePlutoniumOre = config.get(CATEGORY_GENERAL, "1.02_enablePlutoniumNetherOre", false).getBoolean(false);
		enableDungeons = config.get(CATEGORY_GENERAL, "1.03_enableDungeonSpawn", true).getBoolean(true);
		enableMDOres = config.get(CATEGORY_GENERAL, "1.04_enableOresInModdedDimensions", true).getBoolean(true);
		enableMines = config.get(CATEGORY_GENERAL, "1.05_enableLandmineSpawn", true).getBoolean(true);
		enableRad = config.get(CATEGORY_GENERAL, "1.06_enableRadHotspotSpawn", true).getBoolean(true);
		enableNITAN = config.get(CATEGORY_GENERAL, "1.07_enableNITANChestSpawn", true).getBoolean(true);
		enableNukeClouds = config.get(CATEGORY_GENERAL, "1.08_enableMushroomClouds", true).getBoolean(true);
		enableAutoCleanup = config.get(CATEGORY_GENERAL, "1.09_enableAutomaticRadCleanup", false).getBoolean(false);
		enableMeteorStrikes = config.get(CATEGORY_GENERAL, "1.10_enableMeteorStrikes", true).getBoolean(true);
		enableMeteorShowers = config.get(CATEGORY_GENERAL, "1.11_enableMeteorShowers", true).getBoolean(true);
		enableMeteorTails = config.get(CATEGORY_GENERAL, "1.12_enableMeteorTails", true).getBoolean(true);
		enableSpecialMeteors = config.get(CATEGORY_GENERAL, "1.13_enableSpecialMeteors", false).getBoolean(false);
		enableBomberShortMode = config.get(CATEGORY_GENERAL, "1.14_enableBomberShortMode", false).getBoolean(false);
		enableVaults = config.get(CATEGORY_GENERAL, "1.15_enableVaultSpawn", true).getBoolean(true);
		enableRads = config.get(CATEGORY_GENERAL, "1.16_enableNewRadiation", true).getBoolean(true);
		enableCataclysm = config.get(CATEGORY_GENERAL, "1.17_enableCataclysm", false).getBoolean(false);
		enableExtendedLogging = config.get(CATEGORY_GENERAL, "1.18_enableExtendedLogging", false).getBoolean(false);
		enableHardcoreTaint = config.get(CATEGORY_GENERAL, "1.19_enableHardcoreTaint", false).getBoolean(false);
		enableGuns = config.get(CATEGORY_GENERAL, "1.20_enableGuns", true).getBoolean(true);
		enableVirus = config.get(CATEGORY_GENERAL, "1.21_enableVirus", false).getBoolean(false);
        enableCrosshairs = config.get(CATEGORY_GENERAL, "1.22_enableCrosshairs", true).getBoolean(true);
		Property shaders = config.get(CATEGORY_GENERAL, "1.23_enableShaders", false);
		shaders.setComment("Experimental, don't use");
		useShaders = shaders.getBoolean(false);

		if(FMLCommonHandler.instance().getSide() == Side.CLIENT)
			if(!OpenGlHelper.shadersSupported) {
				logger.log(Level.WARN, "GLSL shaders are not supported; not using shaders");
				useShaders = false;
			} else if(!GLContext.getCapabilities().OpenGL30) {
				logger.log(Level.WARN, "OpenGL 3.0 is not supported; not using shaders");
				useShaders = false;
			}
		useShaders = false;

		final String CATEGORY_OREGEN = "02_ores";
		Property PuraniumSpawn = config.get(CATEGORY_OREGEN, "2.00_uraniumSpawnrate", 6);
		PuraniumSpawn.setComment("Ammount of uranium ore veins per chunk");
		uraniumSpawn = PuraniumSpawn.getInt();
		Property PtitaniumSpawn = config.get(CATEGORY_OREGEN, "2.01_titaniumSpawnrate", 8);
		PtitaniumSpawn.setComment("Ammount of titanium ore veins per chunk");
		titaniumSpawn = PtitaniumSpawn.getInt();
		Property PsulfurSpawn = config.get(CATEGORY_OREGEN, "2.02_sulfurSpawnrate", 5);
		PsulfurSpawn.setComment("Ammount of sulfur ore veins per chunk");
		sulfurSpawn = PsulfurSpawn.getInt();
		Property PaluminiumSpawn = config.get(CATEGORY_OREGEN, "2.03_aluminiumSpawnrate", 7);
		PaluminiumSpawn.setComment("Ammount of aluminium ore veins per chunk");
		aluminiumSpawn = PaluminiumSpawn.getInt();
		Property PcopperSpawn = config.get(CATEGORY_OREGEN, "2.04_copperSpawnrate", 12);
		PcopperSpawn.setComment("Ammount of copper ore veins per chunk");
		copperSpawn = PcopperSpawn.getInt();
		Property PFluoriteSpawn = config.get(CATEGORY_OREGEN, "2.05_fluoriteSpawnrate", 6);
		PFluoriteSpawn.setComment("Ammount of fluorite ore veins per chunk");
		fluoriteSpawn = PFluoriteSpawn.getInt();
		Property PNiterSpawn = config.get(CATEGORY_OREGEN, "2.06_niterSpawnrate", 6);
		PNiterSpawn.setComment("Ammount of niter ore veins per chunk");
		niterSpawn = PNiterSpawn.getInt();
		Property PtungstenSpawn = config.get(CATEGORY_OREGEN, "2.07_tungstenSpawnrate", 10);
		PtungstenSpawn.setComment("Ammount of tungsten ore veins per chunk");
		tungstenSpawn = PtungstenSpawn.getInt();
		Property PleadSpawn = config.get(CATEGORY_OREGEN, "2.08_leadSpawnrate", 6);
		PleadSpawn.setComment("Ammount of lead ore veins per chunk");
		leadSpawn = PleadSpawn.getInt();
		Property PberylliumSpawn = config.get(CATEGORY_OREGEN, "2.09_berylliumSpawnrate", 6);
		PberylliumSpawn.setComment("Ammount of beryllium ore veins per chunk");
		berylliumSpawn = PberylliumSpawn.getInt();
		Property PthoriumSpawn = config.get(CATEGORY_OREGEN, "2.10_thoriumSpawnrate", 7);
		PthoriumSpawn.setComment("Ammount of thorium ore veins per chunk");
		thoriumSpawn = PthoriumSpawn.getInt();
		Property ligniteSpawnP = config.get(CATEGORY_OREGEN, "2.11_ligniteSpawnrate", 2);
		ligniteSpawnP.setComment("Ammount of lignite ore veins per chunk");
		ligniteSpawn = ligniteSpawnP.getInt();

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
		Property propMirv = config.get(CATEGORY_NUKES, "3.08_mirvRadius", 100);
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
		Property propSolinium = config.get(CATEGORY_NUKES, "3.12_soliniumRadius", 75);
		propSolinium.setComment("Radius of the blue rinse");
		soliniumRadius = propSolinium.getInt();
		Property propN2 = config.get(CATEGORY_NUKES, "3.13_n2Radius", 130);
		propN2.setComment("Radius of the N2 mine");
		n2Radius = propN2.getInt();

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
		meteorStructure = createConfigInt(config, CATEGORY_DUNGEON, "meteorStructure", "Spawn meteor dungeon on every nTH chunk", 15000);
		capsuleStructure = createConfigInt(config, CATEGORY_DUNGEON, "4.21_capsuleSpawn", "Spawn landing capsule on every nTH chunk", 100);
		
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
		Property falloutSpeed = config.get(CATEGORY_NUKE, "6.04_falloutSpeed", 256);
		falloutSpeed.setComment("Blocks processed per tick by the fallout rain");
		fSpeed = falloutSpeed.getInt();
		//Whether fallout and nuclear radiation is enabled at all
		Property disableNuclear = config.get(CATEGORY_NUKE, "6.05_disableNuclear", false);
		disableNuclear.setComment("Disable the nuclear part of nukes");
		MainRegistry.disableNuclear = disableNuclear.getBoolean();
		// afterrain duration
		Property radRain = config.get(CATEGORY_NUKE, "6.06_falloutRainDuration", 0);
		radRain.setComment("Duration of the thunderstorm after fallout in ticks (only large explosions)");
		rain = radRain.getInt();
		// afterrain radiation
		Property rainCont = config.get(CATEGORY_NUKE, "6.07_falloutRainRadiation", 0);
		rainCont.setComment("Radiation in 100th RADs created by fallout rain");
		cont = rainCont.getInt();
		// fog threshold
		Property fogThresh = config.get(CATEGORY_NUKE, "6.08_fogThreshold", 100);
		fogThresh.setComment("Radiation in RADs required for fog to spawn");
		fogRad = fogThresh.getInt();
		// fog chance
		Property fogChance = config.get(CATEGORY_NUKE, "6.09_fogChance", 10);
		fogChance.setComment("1:n chance of fog spawning every second");
		fogCh = fogChance.getInt();
		// nether radiation
		Property netherRad = config.get(CATEGORY_NUKE, "6.10_netherRad", 0);
		netherRad.setComment("RAD/s in the nether in hundredths");
		hellRad = netherRad.getInt() * 0.01F;
		// railgun
		Property railDamage = config.get(CATEGORY_NUKE, "6.11_railgunDamage", 1000);
		railDamage.setComment("How much damage a railgun death blast does per tick");
		railgunDamage = railDamage.getInt();
		Property railBuffer = config.get(CATEGORY_NUKE, "6.12_railgunBuffer", 500000000);
		railBuffer.setComment("How much RF the railgun can store");
		railgunDamage = railBuffer.getInt();
		Property railUse = config.get(CATEGORY_NUKE, "6.13_railgunConsumption", 250000000);
		railUse.setComment("How much RF the railgun requires per shot");
		railgunDamage = railUse.getInt();

		final String CATEGORY_MISSILE = "07_missile_machines";
		Property propRadarRange = config.get(CATEGORY_MISSILE, "7.00_radarRange", 1000);
		propRadarRange.setComment("Range of the radar, 50 will result in 100x100 block area covered");
		radarRange = propRadarRange.getInt();
		Property propRadarBuffer = config.get(CATEGORY_MISSILE, "7.01_radarBuffer", 30);
		propRadarBuffer.setComment("How high entities have to be above the radar to be detected");
		radarBuffer = propRadarBuffer.getInt();
		Property propRadarAltitude = config.get(CATEGORY_MISSILE, "7.02_radarAltitude", 55);
		propRadarAltitude.setComment("Y height required for the radar to work");
		radarAltitude = propRadarAltitude.getInt();
		Property propCiwsHitrate = config.get(CATEGORY_MISSILE, "7.03_ciwsAccuracy", 50);
		propCiwsHitrate.setComment("Additional modifier for CIWS accuracy");
		ciwsHitrate = propRadarAltitude.getInt();

		Property fireDurationP = config.get(CATEGORY_MISSILE, "fireDuration", 4 * 20);
		fireDurationP.setComment("How long the fire blast will last");
		fireDuration = fireDurationP.getInt();

		final String CATEGORY_POTION = "08_potion_effects";
		Property propTaintID = config.get(CATEGORY_POTION, "8.00_taintPotionID", 62);
		propTaintID.setComment("What potion ID the taint effect will have");
		taintID = propTaintID.getInt();
		Property propRadiationID = config.get(CATEGORY_POTION, "8.01_radiationPotionID", 63);
		propRadiationID.setComment("What potion ID the radiation effect will have");
		radiationID = propRadiationID.getInt();
		Property propBangID = config.get(CATEGORY_POTION, "8.02_bangPotionID", 64);
		propBangID.setComment("What potion ID the B93 timebomb effect will have");
		bangID = propBangID.getInt();
		Property propMutationID = config.get(CATEGORY_POTION, "8.03_mutationPotionID", 65);
		propMutationID.setComment("What potion ID the taint mutation effect will have");
		mutationID = propMutationID.getInt();
		Property propRadxID = config.get(CATEGORY_POTION, "8.04_radxPotionID", 66);
		propRadxID.setComment("What potion ID the Rad-X effect will have");
		radxID = propRadxID.getInt();
		Property propLeadID = config.get(CATEGORY_POTION, "8.05_leadPotionID", 67);
		propLeadID.setComment("What potion ID the lead poisoning effect will have");
		leadID = propLeadID.getInt();

		final String CATEGORY_MACHINE = "09_machines";
        templateBlacklist = Arrays.asList(createConfigStringList(config, CATEGORY_MACHINE, "9.00_templateBlacklist", "Which machine templates should be prohibited from being created (args: enum names)"));

        final String CATEGORY_DROPS = "10_dangerous_drops";
        dropCell = createConfigBool(config, CATEGORY_DROPS, "10.00_dropCell", "Whether antimatter cells should explode when dropped", true);
        dropSing = createConfigBool(config, CATEGORY_DROPS, "10.01_dropBHole", "Whether singularities and blaack holes should spawn when dropped", true);
        dropStar = createConfigBool(config, CATEGORY_DROPS, "10.02_dropStar", "Whether rigged star blaster cells should explode when dropped", true);
        dropCrys = createConfigBool(config, CATEGORY_DROPS, "10.04_dropCrys", "Whether xen crystals should move blocks when dropped", true);
        dropDead = createConfigBool(config, CATEGORY_DROPS, "10.05_dropDead", "Whether dead man's explosives should explode when dropped", true);
		
        final String CATEGORY_TOOLS = "11_tools";
        recursionDepth = createConfigInt(config, CATEGORY_TOOLS, "11.00_recursionDepth", "Limits veinminer's recursive function. Usually not an issue, unless you're using bukkit which is especially sensitive for some reason.", 1000);
        recursiveStone = createConfigBool(config, CATEGORY_TOOLS, "11.01_recursionDepth", "Determines whether veinminer can break stone", false);
        recursiveNetherrack = createConfigBool(config, CATEGORY_TOOLS, "11.02_recursionDepth", "Determines whether veinminer can break netherrack", false);
        
		config.save();

		radioStructure = setDef(radioStructure, 1000);
		antennaStructure = setDef(antennaStructure, 1000);
		atomStructure = setDef(atomStructure, 1000);
		vertibirdStructure = setDef(vertibirdStructure, 1000);
		dungeonStructure = setDef(dungeonStructure, 1000);
		relayStructure = setDef(relayStructure, 1000);
		satelliteStructure = setDef(satelliteStructure, 1000);
		bunkerStructure = setDef(bunkerStructure, 1000);
		siloStructure = setDef(siloStructure, 1000);
		factoryStructure = setDef(factoryStructure, 1000);
		dudStructure = setDef(dudStructure, 1000);
		spaceshipStructure = setDef(spaceshipStructure, 1000);
		barrelStructure = setDef(barrelStructure, 1000);
		geyserWater = setDef(geyserWater, 1000);
		geyserChlorine = setDef(geyserChlorine, 1000);
		geyserVapor = setDef(geyserVapor, 1000);
		broadcaster = setDef(broadcaster, 1000);
		minefreq = setDef(minefreq, 1000);
		radfreq = setDef(radfreq, 1000);
		vaultfreq = setDef(vaultfreq, 1000);
		meteorStrikeChance = setDef(meteorStrikeChance, 1000);
		meteorShowerChance = setDef(meteorShowerChance, 1000);
		fogCh = setDef(fogCh, 20);
		capsuleStructure = setDef(capsuleStructure, 100);
		meteorStructure = setDef(meteorStructure, 15000);
	}

	private static int setDef(int value, int def) {

		if(value <= 0) {
			logger.error("Fatal error config: Randomizer value has been set to zero, despite bound having to be positive integer!");
			logger.error(String.format("Errored value will default back to %d, PLEASE REVIEW CONFIGURATION DESCRIPTION BEFORE MEDDLING WITH VALUES!", def));
			return def;
		}

		return value;
	}
	
	private static int createConfigInt(Configuration config, String category, String name, String comment, int def) {

        Property prop = config.get(category, name, def);
        prop.setComment(comment);
        return prop.getInt();
	}
	
	private static boolean createConfigBool(Configuration config, String category, String name, String comment, boolean def) {

        Property prop = config.get(category, name, def);
        prop.setComment(comment);
        return prop.getBoolean();
	}
	
	private static String[] createConfigStringList(Configuration config, String category, String name, String comment) {

        Property prop = config.get(category, name, new String[] { "PLACEHOLDER" } );
        prop.setComment(comment);
        return prop.getStringList();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		ModItems.init();
		ModBlocks.init();
		registerOreDict();
		registerHazmatArmors();
		registerReactorFuels();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		ModItems.postInit();
		ModBlocks.postInit();
		ItemAssemblyTemplate.loadRecipesFromConfig();
		CraftingManager.init();
		FluidTypeHandler.registerFluidProperties();
		ShredderRecipes.registerShredder();
		ShredderRecipes.registerOverrides();
		CrystallizerRecipes.register();
		CentrifugeRecipes.register();
		BlockCrate.setDrops();
		//Drillgon200: expand the max entity radius for the hunter chopper
		if(World.MAX_ENTITY_RADIUS < 5)
			World.MAX_ENTITY_RADIUS = 5;
		proxy.postInit(event);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent evt) {
		evt.registerServerCommand(new CommandRadiation());
		evt.registerServerCommand(new CommandHbm());
		AdvancementManager.init(evt.getServer());
		//MUST be initialized AFTER achievements!!
		BobmazonOfferFactory.reset();
		BobmazonOfferFactory.init();
	}

	public void registerOreDict() {
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
		OreDictionary.registerOre("ingotRedCopperAlloy", ModItems.ingot_red_copper);
		OreDictionary.registerOre("ingotAdvanced", ModItems.ingot_advanced_alloy);
		OreDictionary.registerOre("ingotAdvancedAlloy", ModItems.ingot_advanced_alloy);
		OreDictionary.registerOre("ingotTungsten", ModItems.ingot_tungsten);
		OreDictionary.registerOre("ingotAluminum", ModItems.ingot_aluminium);
		OreDictionary.registerOre("ingotBeryllium", ModItems.ingot_beryllium);
		OreDictionary.registerOre("ingotCobalt", ModItems.ingot_cobalt);
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
		OreDictionary.registerOre("plateSaturnite", ModItems.plate_saturnite);
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
		OreDictionary.registerOre("dustRedCopperAlloy", ModItems.powder_red_copper);
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
		OreDictionary.registerOre("oreSalpeter", ModBlocks.ore_niter);
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
		OreDictionary.registerOre("blockRedCopperAlloy", ModBlocks.block_red_copper);
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
		HazmatRegistry.instance.registerHazmat(ModItems.cmb_helmet, 0.5F);
		HazmatRegistry.instance.registerHazmat(ModItems.cmb_plate, 1.1F);
		HazmatRegistry.instance.registerHazmat(ModItems.cmb_legs, 0.8F);
		HazmatRegistry.instance.registerHazmat(ModItems.cmb_boots, 0.2F);
		
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
		
		HazmatRegistry.instance.registerHazmat(ModItems.paa_plate, 0.8F);
		HazmatRegistry.instance.registerHazmat(ModItems.paa_legs, 0.6F);
		HazmatRegistry.instance.registerHazmat(ModItems.paa_boots, 0.2F);

		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_paa_helmet, 0.6F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_paa_plate, 1.2F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_paa_legs, 0.9F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_paa_boots, 0.3F);
		
		HazmatRegistry.instance.registerHazmat(ModItems.security_helmet, 0.2F);
		HazmatRegistry.instance.registerHazmat(ModItems.security_plate, 0.4F);
		HazmatRegistry.instance.registerHazmat(ModItems.security_legs, 0.3F);
		HazmatRegistry.instance.registerHazmat(ModItems.security_boots, 0.1F);
		
		HazmatRegistry.instance.registerHazmat(ModItems.starmetal_helmet, 0.6F);
		HazmatRegistry.instance.registerHazmat(ModItems.starmetal_plate, 1.2F);
		HazmatRegistry.instance.registerHazmat(ModItems.starmetal_legs, 0.9F);
		HazmatRegistry.instance.registerHazmat(ModItems.starmetal_boots, 0.3F);

		HazmatRegistry.instance.registerHazmat(ModItems.jackt, 0.3F);
		HazmatRegistry.instance.registerHazmat(ModItems.jackt2, 0.3F);

		HazmatRegistry.instance.registerHazmat(ModItems.gas_mask, 0.15F);
		HazmatRegistry.instance.registerHazmat(ModItems.gas_mask_m65, 0.175F);

		HazmatRegistry.instance.registerHazmat(ModItems.steel_helmet, 0.04F);
		HazmatRegistry.instance.registerHazmat(ModItems.steel_plate, 0.08F);
		HazmatRegistry.instance.registerHazmat(ModItems.steel_legs, 0.06F);
		HazmatRegistry.instance.registerHazmat(ModItems.steel_boots, 0.02F);

		HazmatRegistry.instance.registerHazmat(ModItems.cobalt_helmet, 0.1F);
		HazmatRegistry.instance.registerHazmat(ModItems.cobalt_plate, 0.2F);
		HazmatRegistry.instance.registerHazmat(ModItems.cobalt_legs, 0.15F);
		HazmatRegistry.instance.registerHazmat(ModItems.cobalt_boots, 0.05F);
		
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

		HazmatRegistry.instance.registerHazmat(ModItems.schrabidium_helmet, 0.6F);
		HazmatRegistry.instance.registerHazmat(ModItems.schrabidium_plate, 1.2F);
		HazmatRegistry.instance.registerHazmat(ModItems.schrabidium_legs, 0.9F);
		HazmatRegistry.instance.registerHazmat(ModItems.schrabidium_boots, 0.3F);

		HazmatRegistry.instance.registerHazmat(ModItems.euphemium_helmet, 6F);
		HazmatRegistry.instance.registerHazmat(ModItems.euphemium_plate, 12F);
		HazmatRegistry.instance.registerHazmat(ModItems.euphemium_legs, 9F);
		HazmatRegistry.instance.registerHazmat(ModItems.euphemium_boots, 3F);

	}
	
	private void registerReactorFuels(){
		TileEntityMachineReactorLarge.registerFuelEntry(1, ReactorFuelType.URANIUM, ModItems.nugget_uranium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(9, ReactorFuelType.URANIUM, ModItems.ingot_uranium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(6, ReactorFuelType.URANIUM, ModItems.rod_uranium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(12, ReactorFuelType.URANIUM, ModItems.rod_dual_uranium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(24, ReactorFuelType.URANIUM, ModItems.rod_quad_uranium_fuel);
		TileEntityMachineReactorLarge.registerWasteEntry(6, ReactorFuelType.URANIUM, ModItems.rod_empty, ModItems.rod_uranium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(12, ReactorFuelType.URANIUM, ModItems.rod_dual_empty, ModItems.rod_dual_uranium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(24, ReactorFuelType.URANIUM, ModItems.rod_quad_empty, ModItems.rod_quad_uranium_fuel_depleted);

		TileEntityMachineReactorLarge.registerFuelEntry(1, ReactorFuelType.PLUTONIUM, ModItems.nugget_plutonium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(9, ReactorFuelType.PLUTONIUM, ModItems.ingot_plutonium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(6, ReactorFuelType.PLUTONIUM, ModItems.rod_plutonium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(12, ReactorFuelType.PLUTONIUM, ModItems.rod_dual_plutonium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(24, ReactorFuelType.PLUTONIUM, ModItems.rod_quad_plutonium_fuel);
		TileEntityMachineReactorLarge.registerWasteEntry(6, ReactorFuelType.PLUTONIUM, ModItems.rod_empty, ModItems.rod_plutonium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(12, ReactorFuelType.PLUTONIUM, ModItems.rod_dual_empty, ModItems.rod_dual_plutonium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(24, ReactorFuelType.PLUTONIUM, ModItems.rod_quad_empty, ModItems.rod_quad_plutonium_fuel_depleted);

		TileEntityMachineReactorLarge.registerFuelEntry(1, ReactorFuelType.MOX, ModItems.nugget_mox_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(9, ReactorFuelType.MOX, ModItems.ingot_mox_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(6, ReactorFuelType.MOX, ModItems.rod_mox_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(12, ReactorFuelType.MOX, ModItems.rod_dual_mox_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(24, ReactorFuelType.MOX, ModItems.rod_quad_mox_fuel);
		TileEntityMachineReactorLarge.registerWasteEntry(6, ReactorFuelType.MOX, ModItems.rod_empty, ModItems.rod_mox_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(12, ReactorFuelType.MOX, ModItems.rod_dual_empty, ModItems.rod_dual_mox_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(24, ReactorFuelType.MOX, ModItems.rod_quad_empty, ModItems.rod_quad_mox_fuel_depleted);

		TileEntityMachineReactorLarge.registerFuelEntry(10, ReactorFuelType.SCHRABIDIUM, ModItems.nugget_schrabidium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(90, ReactorFuelType.SCHRABIDIUM, ModItems.ingot_schrabidium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(60, ReactorFuelType.SCHRABIDIUM, ModItems.rod_schrabidium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(120, ReactorFuelType.SCHRABIDIUM, ModItems.rod_dual_schrabidium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(240, ReactorFuelType.SCHRABIDIUM, ModItems.rod_quad_schrabidium_fuel);
		TileEntityMachineReactorLarge.registerWasteEntry(60, ReactorFuelType.SCHRABIDIUM, ModItems.rod_empty, ModItems.rod_schrabidium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(120, ReactorFuelType.SCHRABIDIUM, ModItems.rod_dual_empty, ModItems.rod_dual_schrabidium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(240, ReactorFuelType.SCHRABIDIUM, ModItems.rod_quad_empty, ModItems.rod_quad_schrabidium_fuel_depleted);

		TileEntityMachineReactorLarge.registerFuelEntry(1, ReactorFuelType.THORIUM, ModItems.nugget_thorium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(9, ReactorFuelType.THORIUM, ModItems.ingot_thorium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(6, ReactorFuelType.THORIUM, ModItems.rod_thorium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(12, ReactorFuelType.THORIUM, ModItems.rod_dual_thorium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(24, ReactorFuelType.THORIUM, ModItems.rod_quad_thorium_fuel);
		TileEntityMachineReactorLarge.registerWasteEntry(6, ReactorFuelType.THORIUM, ModItems.rod_empty, ModItems.rod_thorium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(12, ReactorFuelType.THORIUM, ModItems.rod_dual_empty, ModItems.rod_dual_thorium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(24, ReactorFuelType.THORIUM, ModItems.rod_quad_empty, ModItems.rod_quad_thorium_fuel_depleted);
	}
	
	private void registerDispenserBehaviors(){
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_generic, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeGeneric(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_strong, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeStrong(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_frag, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeFrag(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_fire, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeFire(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_cluster, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeCluster(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_flare, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeFlare(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_electric, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeElectric(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_poison, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadePoison(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_gas, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeGas(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_schrabidium, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeSchrabidium(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_nuke, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeNuke(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_nuclear, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeNuclear(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_pulse, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadePulse(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_plasma, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadePlasma(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_tau, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeTau(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_lemon, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeLemon(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_mk2, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeMk2(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_aschrab, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeASchrab(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_zomg, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeZOMG(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_shrapnel, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeShrapnel(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_black_hole, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeBlackHole(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_gascan, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeGascan(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_cloud, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeCloud(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_pink_cloud, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadePC(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_smart, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeSmart(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_mirv, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeMIRV(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_breach, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeBreach(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_burst, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeBurst(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_generic, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFGeneric(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_he, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFHE(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_bouncy, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFBouncy(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_sticky, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFSticky(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_impact, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFImpact(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_incendiary, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFIncendiary(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_toxic, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFToxic(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_concussion, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFConcussion(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_brimstone, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFBrimstone(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_mystery, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFMystery(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_spark, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFSpark(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_hopwire, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFHopwire(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_null, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFNull(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
	}

}
