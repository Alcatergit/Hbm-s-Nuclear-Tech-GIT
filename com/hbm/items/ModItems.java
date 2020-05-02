package com.hbm.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockModDoor;
import com.hbm.blocks.items.ItemBlockScrap;
import com.hbm.handler.guncfg.Gun12GaugeFactory;
import com.hbm.handler.guncfg.Gun20GaugeFactory;
import com.hbm.handler.guncfg.Gun22LRFactory;
import com.hbm.handler.guncfg.Gun357MagnumFactory;
import com.hbm.handler.guncfg.Gun44MagnumFactory;
import com.hbm.handler.guncfg.Gun50AEFactory;
import com.hbm.handler.guncfg.Gun50BMGFactory;
import com.hbm.handler.guncfg.Gun5mmFactory;
import com.hbm.handler.guncfg.Gun9mmFactory;
import com.hbm.handler.guncfg.GunGrenadeFactory;
import com.hbm.handler.guncfg.GunRocketFactory;
import com.hbm.items.bomb.ItemBoy;
import com.hbm.items.bomb.ItemFleija;
import com.hbm.items.bomb.ItemGadget;
import com.hbm.items.bomb.ItemMan;
import com.hbm.items.bomb.ItemManMike;
import com.hbm.items.bomb.ItemMike;
import com.hbm.items.bomb.ItemN2;
import com.hbm.items.bomb.ItemSolinium;
import com.hbm.items.bomb.ItemTsar;
import com.hbm.items.food.ItemAppleEuphemium;
import com.hbm.items.food.ItemAppleSchrabidium;
import com.hbm.items.food.ItemCanteen;
import com.hbm.items.food.ItemCottonCandy;
import com.hbm.items.food.ItemEnergy;
import com.hbm.items.food.ItemLemon;
import com.hbm.items.food.ItemNugget;
import com.hbm.items.food.ItemPill;
import com.hbm.items.food.ItemSchnitzelVegan;
import com.hbm.items.food.ItemTemFlakes;
import com.hbm.items.food.ItemWaffle;
import com.hbm.items.gear.ArmorAsbestos;
import com.hbm.items.gear.ArmorAustralium;
import com.hbm.items.gear.ArmorEuphemium;
import com.hbm.items.gear.ArmorHazmat;
import com.hbm.items.gear.ArmorModel;
import com.hbm.items.gear.ArmorSchrabidium;
import com.hbm.items.gear.ArmorT45;
import com.hbm.items.gear.AxeSchrabidium;
import com.hbm.items.gear.BigSword;
import com.hbm.items.gear.HoeSchrabidium;
import com.hbm.items.gear.JetpackBooster;
import com.hbm.items.gear.JetpackBreak;
import com.hbm.items.gear.JetpackRegular;
import com.hbm.items.gear.JetpackVectorized;
import com.hbm.items.gear.MaskOfInfamy;
import com.hbm.items.gear.ModArmor;
import com.hbm.items.gear.ModAxe;
import com.hbm.items.gear.ModHoe;
import com.hbm.items.gear.ModPickaxe;
import com.hbm.items.gear.ModSpade;
import com.hbm.items.gear.ModSword;
import com.hbm.items.gear.PickaxeSchrabidium;
import com.hbm.items.gear.RedstoneSword;
import com.hbm.items.gear.SpadeSchrabidium;
import com.hbm.items.gear.SwordSchrabidium;
import com.hbm.items.gear.WeaponSpecial;
import com.hbm.items.special.ItemAMSCore;
import com.hbm.items.special.ItemBattery;
import com.hbm.items.special.ItemBlades;
import com.hbm.items.special.ItemCapacitor;
import com.hbm.items.special.ItemCatalyst;
import com.hbm.items.special.ItemCell;
import com.hbm.items.special.ItemChopper;
import com.hbm.items.special.ItemCustomLore;
import com.hbm.items.special.ItemDrop;
import com.hbm.items.special.ItemFuel;
import com.hbm.items.special.ItemFuelRod;
import com.hbm.items.special.ItemGlitch;
import com.hbm.items.special.ItemLootCrate;
import com.hbm.items.special.ItemModRecord;
import com.hbm.items.special.ItemPolaroid;
import com.hbm.items.special.ItemPotatos;
import com.hbm.items.special.ItemRadioactive;
import com.hbm.items.special.ItemStarterKit;
import com.hbm.items.special.ItemSyringe;
import com.hbm.items.special.ItemTeleLink;
import com.hbm.items.special.ItemUnstable;
import com.hbm.items.special.WatzFuel;
import com.hbm.items.special.weapon.GunB92;
import com.hbm.items.tool.ItemAnalyzer;
import com.hbm.items.tool.ItemAssemblyTemplate;
import com.hbm.items.tool.ItemBombCaller;
import com.hbm.items.tool.ItemCassette;
import com.hbm.items.tool.ItemCatalog;
import com.hbm.items.tool.ItemChemistryIcon;
import com.hbm.items.tool.ItemChemistryTemplate;
import com.hbm.items.tool.ItemCounterfitKeys;
import com.hbm.items.tool.ItemCrateCaller;
import com.hbm.items.tool.ItemDesignator;
import com.hbm.items.tool.ItemDesignatorManual;
import com.hbm.items.tool.ItemDesignatorRange;
import com.hbm.items.tool.ItemDetonator;
import com.hbm.items.tool.ItemFFFluidDuct;
import com.hbm.items.tool.ItemFluidCanister;
import com.hbm.items.tool.ItemFluidContainerInfinite;
import com.hbm.items.tool.ItemFluidIcon;
import com.hbm.items.tool.ItemFluidTank;
import com.hbm.items.tool.ItemForgeFluidIdentifier;
import com.hbm.items.tool.ItemGasCanister;
import com.hbm.items.tool.ItemGeigerCounter;
import com.hbm.items.tool.ItemKey;
import com.hbm.items.tool.ItemLaserDetonator;
import com.hbm.items.tool.ItemLeadBox;
import com.hbm.items.tool.ItemLock;
import com.hbm.items.tool.ItemMS;
import com.hbm.items.tool.ItemMatch;
import com.hbm.items.tool.ItemMeteorRemote;
import com.hbm.items.tool.ItemModDoor;
import com.hbm.items.tool.ItemMultiDetonator;
import com.hbm.items.tool.ItemMultitoolPassive;
import com.hbm.items.tool.ItemMultitoolTool;
import com.hbm.items.tool.ItemOilDetector;
import com.hbm.items.tool.ItemReactorSensor;
import com.hbm.items.tool.ItemSatChip;
import com.hbm.items.tool.ItemSatInterface;
import com.hbm.items.tool.ItemSurveyScanner;
import com.hbm.items.tool.ItemTemplateFolder;
import com.hbm.items.tool.ItemTurretBiometry;
import com.hbm.items.tool.ItemTurretChip;
import com.hbm.items.tool.ItemTurretControl;
import com.hbm.items.tool.ItemWand;
import com.hbm.items.tool.ItemWandD;
import com.hbm.items.tool.ItemWandS;
import com.hbm.items.tool.ItemWiring;
import com.hbm.items.weapon.GunB92Cell;
import com.hbm.items.weapon.GunB93;
import com.hbm.items.weapon.GunBaleFlare;
import com.hbm.items.weapon.GunBoltAction;
import com.hbm.items.weapon.GunCryolator;
import com.hbm.items.weapon.GunDampfmaschine;
import com.hbm.items.weapon.GunDefabricator;
import com.hbm.items.weapon.GunEMPRay;
import com.hbm.items.weapon.GunEuthanasia;
import com.hbm.items.weapon.GunFatman;
import com.hbm.items.weapon.GunFolly;
import com.hbm.items.weapon.GunHP;
import com.hbm.items.weapon.GunImmolator;
import com.hbm.items.weapon.GunJack;
import com.hbm.items.weapon.GunLeverActionS;
import com.hbm.items.weapon.GunMIRV;
import com.hbm.items.weapon.GunMP;
import com.hbm.items.weapon.GunOSIPR;
import com.hbm.items.weapon.GunProtoMirv;
import com.hbm.items.weapon.GunSpark;
import com.hbm.items.weapon.GunStinger;
import com.hbm.items.weapon.GunSuicide;
import com.hbm.items.weapon.GunXVL1456;
import com.hbm.items.weapon.GunZOMG;
import com.hbm.items.weapon.ItemAmmo;
import com.hbm.items.weapon.ItemClip;
import com.hbm.items.weapon.ItemCustomMissile;
import com.hbm.items.weapon.ItemGrenade;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemTurretAmmo;
import com.hbm.items.weapon.WeaponizedCell;
import com.hbm.items.weapon.ItemMissile.FuelType;
import com.hbm.items.weapon.ItemMissile.PartSize;
import com.hbm.items.weapon.ItemMissile.Rarity;
import com.hbm.items.weapon.ItemMissile.WarheadType;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModItems {
	
	
	public static final List<Item> ALL_ITEMS = new ArrayList<Item>();
	
	public static final int guiID_item_folder = 99;
	public static final int guiID_item_designator = 100;
	public static final int guiID_item_sat_interface = 101;
	public static final int guiID_item_bobmazon = 103;
	
	public static final Item redstone_sword = new RedstoneSword(ToolMaterial.STONE, "redstone_sword").setCreativeTab(CreativeTabs.COMBAT);
	public static final Item big_sword = new BigSword(ToolMaterial.DIAMOND, "big_sword").setCreativeTab(CreativeTabs.COMBAT);
	
	//Syringe
	public static final Item syringe_empty = new ItemBase("syringe_empty").setFull3D().setCreativeTab(MainRegistry.consumableTab);
	public static final Item syringe_awesome = new ItemSyringe("syringe_awesome").setCreativeTab(MainRegistry.consumableTab);
	public static final Item syringe_antidote = new ItemSyringe("syringe_antidote").setFull3D().setCreativeTab(MainRegistry.consumableTab);
	public static final Item syringe_metal_empty = new ItemBase("syringe_metal_empty").setFull3D().setCreativeTab(MainRegistry.consumableTab);
	public static final Item syringe_poison = new ItemSyringe("syringe_poison").setFull3D().setCreativeTab(MainRegistry.consumableTab);
	public static final Item syringe_metal_medx = new ItemSyringe("syringe_metal_medx").setFull3D().setCreativeTab(MainRegistry.consumableTab);
	public static final Item syringe_metal_psycho = new ItemSyringe("syringe_metal_psycho").setFull3D().setCreativeTab(MainRegistry.consumableTab);
	public static final Item syringe_metal_super = new ItemSyringe("syringe_metal_super").setFull3D().setCreativeTab(MainRegistry.consumableTab);
	public static final Item syringe_taint = new ItemSyringe("syringe_taint").setFull3D().setCreativeTab(MainRegistry.consumableTab);
	public static final Item syringe_metal_stimpak = new ItemSyringe("syringe_metal_stimpak").setFull3D().setCreativeTab(MainRegistry.consumableTab);
	public static final Item med_bag = new ItemSyringe("med_bag").setCreativeTab(MainRegistry.consumableTab);
	public static final Item radaway = new ItemSyringe("radaway").setCreativeTab(MainRegistry.consumableTab);
	public static final Item radaway_strong = new ItemSyringe("radaway_strong").setCreativeTab(MainRegistry.consumableTab);
	public static final Item radaway_flush = new ItemSyringe("radaway_flush").setCreativeTab(MainRegistry.consumableTab);
	public static final Item radx = new ItemPill(0, "radx").setCreativeTab(MainRegistry.consumableTab);
	public static final Item pill_iodine = new ItemPill(0, "pill_iodine").setCreativeTab(MainRegistry.consumableTab);
	public static final Item plan_c = new ItemPill(0, "plan_c").setCreativeTab(MainRegistry.consumableTab);
	public static final Item stealth_boy = new ItemStarterKit("stealth_boy").setCreativeTab(MainRegistry.consumableTab);
	public static final Item gas_mask_filter = new ItemSyringe("gas_mask_filter").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	public static final Item jetpack_tank = new ItemSyringe("jetpack_tank").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	public static final Item gun_kit_1 = new ItemSyringe("gun_kit_1").setMaxStackSize(16).setCreativeTab(MainRegistry.consumableTab);
	public static final Item gun_kit_2 = new ItemSyringe("gun_kit_2").setMaxStackSize(16).setCreativeTab(MainRegistry.consumableTab);
	public static final Item euphemium_kit = new ItemStarterKit("euphemium_kit").setMaxStackSize(1).setCreativeTab(null);
	
	//Energy items
	public static final Item factory_core_titanium = new ItemBattery(70400, 10, 0, "factory_core_titanium").setMaxStackSize(1);
	public static final Item factory_core_advanced = new ItemBattery(41600, 10, 0, "factory_core_advanced").setMaxStackSize(1);
	public static final Item dynosphere_base = new ItemBase("dynosphere_base").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item dynosphere_desh = new ItemBattery(10000L, 100, 0, "dynosphere_desh").setMaxStackSize(1);
	public static final Item dynosphere_desh_charged = new ItemBase("dynosphere_desh_charged").setCreativeTab(MainRegistry.controlTab);
	public static final Item dynosphere_schrabidium = new ItemBattery(1000000L, 1000, 0, "dynosphere_schrabidium").setMaxStackSize(1);
	public static final Item dynosphere_schrabidium_charged = new ItemBase("dynosphere_schrabidium_charged").setCreativeTab(MainRegistry.controlTab);
	public static final Item dynosphere_euphemium = new ItemBattery(100000000L, 10000, 0, "dynosphere_euphemium").setMaxStackSize(1);
	public static final Item dynosphere_euphemium_charged = new ItemBase("dynosphere_euphemium_charged").setCreativeTab(MainRegistry.controlTab);
	public static final Item dynosphere_dineutronium = new ItemBattery(10000000000L, 100000, 0, "dynosphere_dineutronium").setMaxStackSize(1);
	public static final Item dynosphere_dineutronium_charged = new ItemBase("dynosphere_dineutronium_charged").setCreativeTab(MainRegistry.controlTab);
	public static final Item fusion_core = new ItemBattery(200000, 0, 25, "fusion_core").setMaxStackSize(1);
	public static final Item energy_core = new ItemBattery(100000, 0, 10, "energy_core").setMaxStackSize(1);
	public static final Item fusion_core_infinite = new ItemBase("fusion_core_infinite").setMaxStackSize(1);
	public static final Item battery_generic = new ItemBattery(50, 1, 1, "battery_generic").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_advanced = new ItemBattery(200, 5, 5, "battery_advanced").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_lithium = new ItemBattery(2500, 10, 10, "battery_lithium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_schrabidium = new ItemBattery(10000, 50, 50, "battery_schrabidium").setMaxStackSize(1);
	public static final Item battery_spark = new ItemBattery(1000000, 20000, 20000, "battery_spark").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_creative = new ItemBattery(0, 0, 0, "battery_creative").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);

	public static final Item battery_red_cell = new ItemBattery(150, 1, 1, "battery_red_cell").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_red_cell_6 = new ItemBattery(150 * 6, 1, 1, "battery_red_cell_6").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_red_cell_24 = new ItemBattery(150 * 24, 1, 1, "battery_red_cell_24").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_advanced_cell = new ItemBattery(600, 5, 5, "battery_advanced_cell").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_advanced_cell_4 = new ItemBattery(600 * 4, 5, 5, "battery_advanced_cell_4").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_advanced_cell_12 = new ItemBattery(600 * 12, 5, 5, "battery_advanced_cell_12").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_lithium_cell = new ItemBattery(7500, 10, 10, "battery_lithium_cell").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_lithium_cell_3 = new ItemBattery(7500 * 3, 10, 10, "battery_lithium_cell_3").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_lithium_cell_6 = new ItemBattery(7500 * 6, 10, 10, "battery_lithium_cell_6").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_schrabidium_cell = new ItemBattery(30000, 50, 50, "battery_schrabidium_cell").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_schrabidium_cell_2 = new ItemBattery(30000 * 2, 50, 50, "battery_schrabidium_cell_2").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_schrabidium_cell_4 = new ItemBattery(30000 * 4, 50, 50, "battery_schrabidium_cell_4").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_spark_cell_6 = new ItemBattery(1000000 * 6, 20000, 20000, "battery_spark_cell_6").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_spark_cell_25 = new ItemBattery(1000000 * 25, 20000, 20000, "battery_spark_cell_25").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_spark_cell_100 = new ItemBattery(1000000L * 100L, 20000, 20000, "battery_spark_cell_100").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_spark_cell_1000 = new ItemBattery(1000000L * 1000L, 200000, 200000, "battery_spark_cell_1000").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_spark_cell_2500 = new ItemBattery(1000000L * 2500L, 200000, 200000, "battery_spark_cell_2500").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_spark_cell_10000 = new ItemBattery(1000000L * 10000L, 2000000, 2000000, "battery_spark_cell_10000").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_spark_cell_power = new ItemBattery(1000000L * 1000000L, 2000000, 2000000, "battery_spark_cell_power").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);

	public static final Item battery_potato = new ItemBattery(1, 0, 1, "battery_potato").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_potatos = new ItemPotatos(50, 0, 1, "battery_potatos").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_su = new ItemBattery(15, 0, 1, "battery_su").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_su_l = new ItemBattery(35, 0, 1, "battery_su_l").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_steam = new ItemBattery(600, 3, 60, "battery_steam").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_steam_large = new ItemBattery(1000, 5, 100, "battery_steam_large").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	
	public static final Item fuse = new ItemCustomLore("fuse").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item screwdriver = new ItemCustomLore("screwdriver").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.controlTab);
	public static final Item redcoil_capacitor = new ItemCapacitor(10, "redcoil_capacitor").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item titanium_filter = new ItemCapacitor(72000, "titanium_filter").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item arc_electrode = new ItemCustomLore("arc_electrode").setMaxDamage(250).setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setFull3D();
	public static final Item arc_electrode_burnt = new ItemBase("arc_electrode_burnt").setMaxStackSize(1).setFull3D();
	public static final Item arc_electrode_desh = new ItemCustomLore("arc_electrode_desh").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setFull3D();
	
	public static final Item singularity = new ItemDrop("singularity").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste);
	public static final Item singularity_counter_resonant = new ItemDrop("singularity_counter_resonant").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste);
	public static final Item singularity_super_heated = new ItemDrop("singularity_super_heated").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste);
	public static final Item black_hole = new ItemDrop("black_hole").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste);
	public static final Item singularity_spark = new ItemDrop("singularity_spark").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste);
	public static final Item pellet_antimatter = new ItemDrop("pellet_antimatter").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell);
	public static final Item crystal_xen = new ItemDrop("crystal_xen").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item overfuse = new ItemCustomLore("overfuse").setMaxStackSize(1).setFull3D();
	
	public static final Item piston_selenium = new ItemBase("piston_selenium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	
	public static final Item crystal_energy = new ItemCustomLore("crystal_energy").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_coolant = new ItemCustomLore("pellet_coolant").setMaxDamage(41400).setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	
	public static final Item tank_steel = new ItemBase("tank_steel").setCreativeTab(MainRegistry.partsTab);
	
	public static final Item ams_catalyst_blank = new ItemBase("ams_catalyst_blank").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item ams_catalyst_aluminium = new ItemCatalyst(0xCCCCCC, 1000000, 1.15F, 0.85F, 1.15F, "ams_catalyst_aluminium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item ams_catalyst_beryllium = new ItemCatalyst(0x97978B, 0, 1.25F, 0.95F, 1.05F, "ams_catalyst_beryllium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item ams_catalyst_caesium = new ItemCatalyst(0x6400FF, 2500000, 1.00F, 0.85F, 1.15F, "ams_catalyst_caesium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item ams_catalyst_cerium = new ItemCatalyst(0x1D3FFF, 1000000, 1.15F, 1.15F, 0.85F, "ams_catalyst_cerium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item ams_catalyst_cobalt = new ItemCatalyst(0x789BBE, 0, 1.25F, 1.05F, 0.95F, "ams_catalyst_cobalt").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item ams_catalyst_copper = new ItemCatalyst(0xAADE29, 0, 1.25F, 1.00F, 1.00F, "ams_catalyst_copper").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item ams_catalyst_dineutronium = new ItemCatalyst(0x334077, 2500000, 1.00F, 1.15F, 0.85F, "ams_catalyst_dineutronium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item ams_catalyst_euphemium = new ItemCatalyst(0xFF9CD2, 2500000, 1.00F, 1.00F, 1.00F, "ams_catalyst_euphemium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item ams_catalyst_iron = new ItemCatalyst(0xFF7E22, 1000000, 1.15F, 0.95F, 1.05F, "ams_catalyst_iron").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item ams_catalyst_lithium = new ItemCatalyst(0xFF2727, 0, 1.25F, 0.85F, 1.15F, "ams_catalyst_lithium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item ams_catalyst_niobium = new ItemCatalyst(0x3BF1B6, 1000000, 1.15F, 1.05F, 0.95F, "ams_catalyst_niobium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item ams_catalyst_schrabidium = new ItemCatalyst(0x32FFFF, 2500000, 1.00F, 1.05F, 0.95F, "ams_catalyst_schrabidium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item ams_catalyst_strontium = new ItemCatalyst(0xDD0D35, 1000000, 1.15F, 1.00F, 1.00F, "ams_catalyst_strontium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item ams_catalyst_thorium = new ItemCatalyst(0x653B22, 2500000, 1.00F, 0.95F, 1.05F, "ams_catalyst_thorium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item ams_catalyst_tungsten = new ItemCatalyst(0xF5FF48, 0, 1.25F, 1.15F, 0.85F, "ams_catalyst_tungsten").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	
	//Fluid handling items
	public static final Item canister_generic = new ItemFluidCanister("canister_fuel", 1000).setCreativeTab(MainRegistry.controlTab);
	public static final Item canister_napalm = new ItemCustomLore("canister_napalm").setCreativeTab(MainRegistry.controlTab);
	
	public static final Item fluid_tank_full = new ItemFluidTank("fluid_tank_full", 1000).setCreativeTab(MainRegistry.controlTab);
	public static final Item fluid_barrel_full = new ItemFluidTank("fluid_barrel_full", 16000).setCreativeTab(MainRegistry.controlTab);
	public static final Item fluid_barrel_infinite = new ItemFluidContainerInfinite("fluid_barrel_infinite").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item siren_track = new ItemCassette("siren_track").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab);
	public static final Item ff_fluid_duct = new ItemFFFluidDuct("ff_fluid_duct").setCreativeTab(MainRegistry.templateTab);
	public static final Item inf_water = new ItemFluidContainerInfinite("inf_water").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	
	public static final Item gas_canister = new ItemGasCanister("gas_empty", 4000).setCreativeTab(MainRegistry.controlTab);
	
	//Activators
	public static final Item detonator = new ItemDetonator("detonator").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab);
	public static final Item detonator_multi = new ItemMultiDetonator("detonator_multi").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab);
	public static final Item detonator_laser = new ItemLaserDetonator("detonator_laser").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab);
	public static final Item detonator_deadman = new ItemDrop("detonator_deadman").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab);
	public static final Item detonator_de = new ItemDrop("detonator_de").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab);
	public static final Item igniter = new ItemCustomLore("igniter").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab);
	public static final Item linker = new ItemTeleLink("linker").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	public static final Item chopper = new ItemChopper("chopper").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	
	public static final Item bomb_caller = new ItemBombCaller("bomb_caller").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.consumableTab);
	public static final Item crate_caller = new ItemCrateCaller("crate_caller").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.consumableTab);
	public static final Item meteor_remote = new ItemMeteorRemote("meteor_remote").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.consumableTab);
	public static final Item reactor_sensor = new ItemReactorSensor("reactor_sensor").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	
	public static final Item oil_detector = new ItemOilDetector("oil_detector").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	
	//Armor
	public static final Item hazmat_helmet = new ArmorHazmat(MainRegistry.enumArmorMaterialHazmat, -1, EntityEquipmentSlot.HEAD, "hazmat_helmet").setMaxStackSize(1);
	public static final Item hazmat_plate = new ArmorHazmat(MainRegistry.enumArmorMaterialHazmat, -1, EntityEquipmentSlot.CHEST, "hazmat_plate").setMaxStackSize(1);
	public static final Item hazmat_legs = new ArmorHazmat(MainRegistry.enumArmorMaterialHazmat, -1, EntityEquipmentSlot.LEGS, "hazmat_legs").setMaxStackSize(1);
	public static final Item hazmat_boots = new ArmorHazmat(MainRegistry.enumArmorMaterialHazmat, -1, EntityEquipmentSlot.FEET, "hazmat_boots").setMaxStackSize(1);
	
	public static final Item hazmat_helmet_red = new ArmorModel(MainRegistry.enumArmorMaterialHazmat2, -1, EntityEquipmentSlot.HEAD, "hazmat_helmet_red").setMaxStackSize(1);
	public static final Item hazmat_plate_red = new ArmorHazmat(MainRegistry.enumArmorMaterialHazmat2, -1, EntityEquipmentSlot.CHEST, "hazmat_plate_red").setMaxStackSize(1);
	public static final Item hazmat_legs_red = new ArmorHazmat(MainRegistry.enumArmorMaterialHazmat2, -1, EntityEquipmentSlot.LEGS, "hazmat_legs_red").setMaxStackSize(1);
	public static final Item hazmat_boots_red = new ArmorHazmat(MainRegistry.enumArmorMaterialHazmat2, -1, EntityEquipmentSlot.FEET, "hazmat_boots_red").setMaxStackSize(1);
	
	public static final Item hazmat_helmet_grey = new ArmorModel(MainRegistry.enumArmorMaterialHazmat3, -1, EntityEquipmentSlot.HEAD, "hazmat_helmet_grey").setMaxStackSize(1);
	public static final Item hazmat_plate_grey = new ArmorHazmat(MainRegistry.enumArmorMaterialHazmat3, -1, EntityEquipmentSlot.CHEST, "hazmat_plate_grey").setMaxStackSize(1);
	public static final Item hazmat_legs_grey = new ArmorHazmat(MainRegistry.enumArmorMaterialHazmat3, -1, EntityEquipmentSlot.LEGS, "hazmat_legs_grey").setMaxStackSize(1);
	public static final Item hazmat_boots_grey = new ArmorHazmat(MainRegistry.enumArmorMaterialHazmat3, -1, EntityEquipmentSlot.FEET, "hazmat_boots_grey").setMaxStackSize(1);
	
	public static final Item hazmat_paa_helmet = new ArmorHazmat(MainRegistry.enumArmorMaterialPaa, -1, EntityEquipmentSlot.HEAD, "hazmat_paa_helmet").setMaxStackSize(1);
	public static final Item hazmat_paa_plate = new ArmorHazmat(MainRegistry.enumArmorMaterialPaa, -1, EntityEquipmentSlot.CHEST, "hazmat_paa_plate").setMaxStackSize(1);
	public static final Item hazmat_paa_legs = new ArmorHazmat(MainRegistry.enumArmorMaterialPaa, -1, EntityEquipmentSlot.LEGS, "hazmat_paa_legs").setMaxStackSize(1);
	public static final Item hazmat_paa_boots = new ArmorHazmat(MainRegistry.enumArmorMaterialPaa, -1, EntityEquipmentSlot.FEET, "hazmat_paa_boots").setMaxStackSize(1);
	
	public static final Item australium_iii = new ArmorAustralium(MainRegistry.enumArmorMaterialAusIII, -1, EntityEquipmentSlot.CHEST, "australium_iii").setMaxStackSize(1);
	
	public static final Item asbestos_helmet = new ArmorAsbestos(MainRegistry.enumArmorMaterialSteel, -1, EntityEquipmentSlot.HEAD, "asbestos_helmet").setMaxStackSize(1);
	public static final Item asbestos_plate = new ArmorAsbestos(MainRegistry.enumArmorMaterialSteel, -1, EntityEquipmentSlot.CHEST, "asbestos_plate").setMaxStackSize(1);
	public static final Item asbestos_legs = new ArmorAsbestos(MainRegistry.enumArmorMaterialSteel, -1, EntityEquipmentSlot.LEGS, "asbestos_legs").setMaxStackSize(1);
	public static final Item asbestos_boots = new ArmorAsbestos(MainRegistry.enumArmorMaterialSteel, -1, EntityEquipmentSlot.FEET, "asbestos_boots").setMaxStackSize(1);
	
	public static final Item schrabidium_helmet = new ArmorSchrabidium(MainRegistry.enumArmorMaterialSchrabidium, -1, EntityEquipmentSlot.HEAD, "schrabidium_helmet").setMaxStackSize(1);
	public static final Item schrabidium_plate = new ArmorSchrabidium(MainRegistry.enumArmorMaterialSchrabidium, -1, EntityEquipmentSlot.CHEST, "schrabidium_plate").setMaxStackSize(1);
	public static final Item schrabidium_legs = new ArmorSchrabidium(MainRegistry.enumArmorMaterialSchrabidium, -1, EntityEquipmentSlot.LEGS, "schrabidium_legs").setMaxStackSize(1);
	public static final Item schrabidium_boots = new ArmorSchrabidium(MainRegistry.enumArmorMaterialSchrabidium, -1, EntityEquipmentSlot.FEET, "schrabidium_boots").setMaxStackSize(1);
	
	public static final Item euphemium_helmet = new ArmorEuphemium(MainRegistry.enumArmorMaterialEuphemium, -1, EntityEquipmentSlot.HEAD, "euphemium_helmet").setMaxStackSize(1);
	public static final Item euphemium_plate = new ArmorEuphemium(MainRegistry.enumArmorMaterialEuphemium, -1, EntityEquipmentSlot.CHEST, "euphemium_plate").setMaxStackSize(1);
	public static final Item euphemium_legs = new ArmorEuphemium(MainRegistry.enumArmorMaterialEuphemium, -1, EntityEquipmentSlot.LEGS, "euphemium_legs").setMaxStackSize(1);
	public static final Item euphemium_boots = new ArmorEuphemium(MainRegistry.enumArmorMaterialEuphemium, -1, EntityEquipmentSlot.FEET, "euphemium_boots").setMaxStackSize(1);
	
	public static final Item jackt = new ModArmor(MainRegistry.enumArmorMaterialSteel, -1, EntityEquipmentSlot.CHEST, "jackt").setMaxStackSize(1);
	public static final Item jackt2 = new ModArmor(MainRegistry.enumArmorMaterialSteel, -1, EntityEquipmentSlot.CHEST, "jackt2").setMaxStackSize(1);
	
	public static final Item alloy_helmet = new ModArmor(MainRegistry.enumArmorMaterialAlloy, -1, EntityEquipmentSlot.HEAD, "alloy_helmet").setMaxStackSize(1);
	public static final Item alloy_plate = new ModArmor(MainRegistry.enumArmorMaterialAlloy, -1, EntityEquipmentSlot.CHEST, "alloy_plate").setMaxStackSize(1);
	public static final Item alloy_legs = new ModArmor(MainRegistry.enumArmorMaterialAlloy, -1, EntityEquipmentSlot.LEGS, "alloy_legs").setMaxStackSize(1);
	public static final Item alloy_boots = new ModArmor(MainRegistry.enumArmorMaterialAlloy, -1, EntityEquipmentSlot.FEET, "alloy_boots").setMaxStackSize(1);
	
	public static final Item cmb_helmet = new ModArmor(MainRegistry.enumArmorMaterialCmb, -1, EntityEquipmentSlot.HEAD, "cmb_helmet").setMaxStackSize(1);
	public static final Item cmb_plate = new ModArmor(MainRegistry.enumArmorMaterialCmb, -1, EntityEquipmentSlot.CHEST, "cmb_plate").setMaxStackSize(1);
	public static final Item cmb_legs = new ModArmor(MainRegistry.enumArmorMaterialCmb, -1, EntityEquipmentSlot.LEGS, "cmb_legs").setMaxStackSize(1);
	public static final Item cmb_boots = new ModArmor(MainRegistry.enumArmorMaterialCmb, -1, EntityEquipmentSlot.FEET, "cmb_boots").setMaxStackSize(1);
	
	public static final Item paa_plate = new ModArmor(MainRegistry.enumArmorMaterialPaa, -1, EntityEquipmentSlot.CHEST, "paa_plate").setMaxStackSize(1);
	public static final Item paa_legs = new ModArmor(MainRegistry.enumArmorMaterialPaa, -1, EntityEquipmentSlot.LEGS, "paa_legs").setMaxStackSize(1);
	public static final Item paa_boots = new ModArmor(MainRegistry.enumArmorMaterialPaa, -1, EntityEquipmentSlot.FEET, "paa_boots").setMaxStackSize(1);
	
	public static final Item security_helmet = new ModArmor(MainRegistry.enumArmorMaterialSecurity, 7, EntityEquipmentSlot.HEAD, "security_helmet").setMaxStackSize(1);
	public static final Item security_plate = new ModArmor(MainRegistry.enumArmorMaterialSecurity, 7, EntityEquipmentSlot.CHEST, "security_plate").setMaxStackSize(1);
	public static final Item security_legs = new ModArmor(MainRegistry.enumArmorMaterialSecurity, 7, EntityEquipmentSlot.LEGS, "security_legs").setMaxStackSize(1);
	public static final Item security_boots = new ModArmor(MainRegistry.enumArmorMaterialSecurity, 7, EntityEquipmentSlot.FEET, "security_boots").setMaxStackSize(1);
	
	public static final Item steel_helmet = new ModArmor(MainRegistry.enumArmorMaterialSteel, -1, EntityEquipmentSlot.HEAD, "steel_helmet").setMaxStackSize(1);
	public static final Item steel_plate = new ModArmor(MainRegistry.enumArmorMaterialSteel, -1, EntityEquipmentSlot.CHEST, "steel_plate").setMaxStackSize(1);
	public static final Item steel_legs = new ModArmor(MainRegistry.enumArmorMaterialSteel, -1, EntityEquipmentSlot.LEGS, "steel_legs").setMaxStackSize(1);
	public static final Item steel_boots = new ModArmor(MainRegistry.enumArmorMaterialSteel, -1, EntityEquipmentSlot.FEET, "steel_boots").setMaxStackSize(1);
	
	public static final Item titanium_helmet = new ModArmor(MainRegistry.enumArmorMaterialTitanium, -1, EntityEquipmentSlot.HEAD, "titanium_helmet").setMaxStackSize(1);
	public static final Item titanium_plate = new ModArmor(MainRegistry.enumArmorMaterialTitanium, -1, EntityEquipmentSlot.CHEST, "titanium_plate").setMaxStackSize(1);
	public static final Item titanium_legs = new ModArmor(MainRegistry.enumArmorMaterialTitanium, -1, EntityEquipmentSlot.LEGS, "titanium_legs").setMaxStackSize(1);
	public static final Item titanium_boots = new ModArmor(MainRegistry.enumArmorMaterialTitanium, -1, EntityEquipmentSlot.FEET, "titanium_boots").setMaxStackSize(1);
	
	public static final Item t45_helmet = new ArmorT45(MainRegistry.enumArmorMaterialT45, -1, EntityEquipmentSlot.HEAD, "t45_helmet").setCreativeTab(CreativeTabs.COMBAT);
	public static final Item t45_plate = new ArmorT45(MainRegistry.enumArmorMaterialT45, -1, EntityEquipmentSlot.CHEST, "t45_plate").setCreativeTab(CreativeTabs.COMBAT);
	public static final Item t45_legs = new ArmorT45(MainRegistry.enumArmorMaterialT45, -1, EntityEquipmentSlot.LEGS, "t45_legs").setCreativeTab(CreativeTabs.COMBAT);
	public static final Item t45_boots = new ArmorT45(MainRegistry.enumArmorMaterialT45, -1, EntityEquipmentSlot.FEET, "t45_boots").setCreativeTab(CreativeTabs.COMBAT);
	
	public static final Item chainsaw = new ModAxe(MainRegistry.enumToolMaterialChainsaw, "chainsaw").setMaxStackSize(1);
	
	public static final Item goggles = new ArmorModel(ArmorMaterial.IRON, -1, EntityEquipmentSlot.HEAD, "goggles").setMaxStackSize(1);
	public static final Item gas_mask = new ArmorModel(ArmorMaterial.IRON, -1, EntityEquipmentSlot.HEAD, "gas_mask").setMaxStackSize(1);
	public static final Item gas_mask_m65 = new ArmorModel(ArmorMaterial.IRON, -1, EntityEquipmentSlot.HEAD, "gas_mask_m65").setMaxStackSize(1);
	
	public static final Item jetpack_boost = new JetpackBooster(MainRegistry.enumArmorMaterialSteel, -1, EntityEquipmentSlot.CHEST, "jetpack_boost").setMaxStackSize(1);
	public static final Item jetpack_break = new JetpackBreak(MainRegistry.enumArmorMaterialSteel, -1, EntityEquipmentSlot.CHEST, "jetpack_break").setMaxStackSize(1);
	public static final Item jetpack_fly = new JetpackRegular(MainRegistry.enumArmorMaterialSteel, -1, EntityEquipmentSlot.CHEST, "jetpack_fly").setMaxStackSize(1);
	public static final Item jetpack_vector = new JetpackVectorized(MainRegistry.enumArmorMaterialSteel, -1, EntityEquipmentSlot.CHEST, "jetpack_vector").setMaxStackSize(1);
	
	public static final Item schrabidium_hammer = new WeaponSpecial(MainRegistry.enumToolMaterialHammer, "schrabidium_hammer").setMaxStackSize(1);
	public static final Item shimmer_sledge = new WeaponSpecial(MainRegistry.enumToolMaterialSledge, "shimmer_sledge").setMaxStackSize(1);
	public static final Item shimmer_axe = new WeaponSpecial(MainRegistry.enumToolMaterialSledge, "shimmer_axe").setMaxStackSize(1);
	public static final Item ullapool_caber = new WeaponSpecial(MainRegistry.enumToolMaterialSteel, "ullapool_caber").setCreativeTab(MainRegistry.weaponTab);
	public static final Item euphemium_stopper = new ItemSyringe("euphemium_stopper").setMaxStackSize(1).setFull3D().setCreativeTab(null);
	public static final Item matchstick = new ItemMatch("matchstick").setCreativeTab(CreativeTabs.TOOLS).setFull3D();
	public static final Item wrench = new WeaponSpecial(MainRegistry.enumToolMaterialSteel, "wrench").setMaxStackSize(1);
	public static final Item wrench_flipped = new WeaponSpecial(MainRegistry.enumToolMaterialElec, "wrench_flipped").setMaxStackSize(1);
	public static final Item memespoon = new WeaponSpecial(MainRegistry.enumToolMaterialSteel, "memespoon").setMaxStackSize(1);
	
	public static final Item multitool_hit = new ItemMultitoolPassive("multitool_hit").setCreativeTab(null);
	public static final Item multitool_dig = new ItemMultitoolTool(4.0F, MainRegistry.enumToolMaterialMultitool, Collections.emptySet(), "multitool_dig").setFull3D().setCreativeTab(MainRegistry.consumableTab);
	public static final Item multitool_silk = new ItemMultitoolTool(4.0F, MainRegistry.enumToolMaterialMultitool, Collections.emptySet(), "multitool_silk").setFull3D().setCreativeTab(null);
	public static final Item multitool_ext = new ItemMultitoolPassive("multitool_ext").setCreativeTab(null);
	public static final Item multitool_miner = new ItemMultitoolPassive("multitool_miner").setCreativeTab(null);
	public static final Item multitool_beam = new ItemMultitoolPassive("multitool_beam").setCreativeTab(null);
	public static final Item multitool_sky = new ItemMultitoolPassive("multitool_sky").setCreativeTab(null);
	public static final Item multitool_mega = new ItemMultitoolPassive("multitool_mega").setCreativeTab(null);
	public static final Item multitool_joule = new ItemMultitoolPassive("multitool_joule").setCreativeTab(null);
	public static final Item multitool_decon = new ItemMultitoolPassive("multitool_decon").setCreativeTab(null);
	
	//Guns
	public static final Item gun_b92 = new GunB92("gun_b92").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_b93 = new GunB93("gun_b93").setCreativeTab(MainRegistry.weaponTab);
	
	public static final Item gun_revolver_iron = new ItemGunBase(Gun357MagnumFactory.getRevolverIronConfig(), "gun_revolver_iron").setMaxDamage(100).setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver = new ItemGunBase(Gun357MagnumFactory.getRevolverConfig(), "gun_revolver").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver_saturnite = new ItemGunBase(Gun357MagnumFactory.getRevolverSaturniteConfig(), "gun_revolver_saturnite").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver_gold = new ItemGunBase(Gun357MagnumFactory.getRevolverGoldConfig(), "gun_revolver_gold").setMaxDamage(1000).setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver_lead = new ItemGunBase(Gun357MagnumFactory.getRevolverLeadConfig(), "gun_revolver_lead").setMaxDamage(250).setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver_schrabidium = new ItemGunBase(Gun357MagnumFactory.getRevolverSchrabidiumConfig(), "gun_revolver_schrabidium").setMaxDamage(100000).setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver_cursed = new ItemGunBase(Gun357MagnumFactory.getRevolverCursedConfig(), "gun_revolver_cursed").setMaxDamage(5000).setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver_nightmare = new ItemGunBase(Gun357MagnumFactory.getRevolverNightmareConfig(), "gun_revolver_nightmare").setMaxDamage(6).setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver_nightmare2 = new ItemGunBase(Gun357MagnumFactory.getRevolverNightmare2Config(), "gun_revolver_nightmare2").setMaxDamage(6).setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver_pip = new ItemGunBase(Gun44MagnumFactory.getMacintoshConfig(), "gun_revolver_pip").setMaxDamage(1000).setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver_nopip = new ItemGunBase(Gun44MagnumFactory.getNovacConfig(), "gun_revolver_nopip").setMaxDamage(1000).setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver_blackjack = new ItemGunBase(Gun44MagnumFactory.getBlackjackConfig(), "gun_revolver_blackjack").setMaxDamage(1000).setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver_red = new ItemGunBase(Gun44MagnumFactory.getRedConfig(), "gun_revolver_red").setMaxDamage(1000).setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_deagle = new ItemGunBase(Gun50AEFactory.getDeagleConfig(), "gun_deagle").setFull3D().setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_uboinik = new ItemGunBase(Gun12GaugeFactory.getUboinikConfig(), "gun_uboinik").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_calamity = new ItemGunBase(Gun50BMGFactory.getCalamityConfig(), "gun_calamity").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_calamity_dual = new ItemGunBase(Gun50BMGFactory.getSaddleConfig(), "gun_calamity_dual").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_minigun = new ItemGunBase(Gun5mmFactory.get53Config(), "gun_minigun").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_avenger = new ItemGunBase(Gun5mmFactory.get57Config(), "gun_avenger").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_lacunae = new ItemGunBase(Gun5mmFactory.getLacunaeConfig(), "gun_lacunae").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_bolt_action = new ItemGunBase(Gun20GaugeFactory.getBoltConfig(), "gun_bolt_action").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_bolt_action_green = new ItemGunBase(Gun20GaugeFactory.getBoltGreenConfig(), "gun_bolt_action_green").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_uzi = new ItemGunBase(Gun22LRFactory.getUziConfig(), "gun_uzi").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_uzi_silencer = new ItemGunBase(Gun22LRFactory.getUziConfig().silenced(), "gun_uzi_silencer").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_uzi_saturnite = new ItemGunBase(Gun22LRFactory.getSaturniteConfig(), "gun_uzi_saturnite").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_uzi_saturnite_silencer = new ItemGunBase(Gun22LRFactory.getSaturniteConfig().silenced(), "gun_uzi_saturnite_silencer").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_mp40 = new ItemGunBase(Gun9mmFactory.getMP40Config(), "gun_mp40").setCreativeTab(MainRegistry.weaponTab);
	
	public static final Item gun_rpg = new ItemGunBase(GunRocketFactory.getGustavConfig(), "gun_rpg").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_karl = new ItemGunBase(GunRocketFactory.getKarlConfig(), "gun_karl").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_panzerschreck = new ItemGunBase(GunRocketFactory.getPanzConfig(), "gun_panzerschreck").setCreativeTab(MainRegistry.weaponTab);
	
	public static final Item gun_lever_action = new ItemGunBase(Gun20GaugeFactory.getMareConfig(), "gun_lever_action").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_lever_action_dark = new ItemGunBase(Gun20GaugeFactory.getMareDarkConfig(), "gun_lever_action_dark").setCreativeTab(MainRegistry.weaponTab);
	
	public static final Item gun_hk69 = new ItemGunBase(GunGrenadeFactory.getHK69Config(), "gun_hk69").setCreativeTab(MainRegistry.weaponTab);
	
	public static final Item gun_spark = new GunSpark("gun_spark").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_fatman = new GunFatman("gun_fatman").setMaxDamage(2500).setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_proto = new GunProtoMirv("gun_proto").setMaxDamage(2500).setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_mirv = new GunMIRV("gun_mirv").setMaxDamage(2500).setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_bf = new GunBaleFlare("gun_bf").setMaxDamage(2500).setCreativeTab(null);
	
	public static final Item gun_zomg = new GunZOMG("gun_zomg").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_xvl1456 = new GunXVL1456("gun_xvl1456").setCreativeTab(MainRegistry.weaponTab);
	//Drillgon200: The SQUID!
	public static final Item gun_hp = new GunHP("gun_hp").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_defabricator = new GunDefabricator("gun_defabricator").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_euthanasia = new GunEuthanasia("gun_euthanasia").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_stinger = new GunStinger("gun_stinger").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_skystinger = new GunStinger("gun_skystinger").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_mp = new GunMP("gun_mp").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_cryolator = new GunCryolator("gun_cryolator").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_jack = new GunJack("gun_jack").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_immolator = new GunImmolator("gun_immolator").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_osipr = new GunOSIPR("gun_osipr").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_emp = new GunEMPRay("gun_emp").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_moist_nugget = new ItemNugget(3, false, "gun_moist_nugget").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_super_shotgun = new ItemCustomLore("gun_super_shotgun").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver_inverted = new GunSuicide("gun_revolver_inverted").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_lever_action_sonata = new GunLeverActionS("gun_lever_action_sonata").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_bolt_action_saturnite = new GunBoltAction("gun_bolt_action_saturnite").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_folly = new GunFolly("gun_folly").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_dampfmaschine = new GunDampfmaschine("gun_dampfmaschine").setCreativeTab(MainRegistry.weaponTab);
	
	//Materials
	public static final Item ingot_schrabidium = new ItemRadioactive("ingot_schrabidium").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_advanced_alloy = new ItemBase("ingot_advanced_alloy").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_steel = new ItemBase("ingot_steel").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_titanium = new ItemBase("ingot_titanium").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_copper = new ItemBase("ingot_copper").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_red_copper = new ItemBase("ingot_red_copper").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_tungsten = new ItemBase("ingot_tungsten").setCreativeTab(MainRegistry.partsTab);
	//Those darn non-American spellings...
	public static final Item ingot_aluminium = new ItemBase("ingot_aluminium").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_beryllium = new ItemBase("ingot_beryllium").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_lead = new ItemBase("ingot_lead").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_asbestos = new ItemCustomLore("ingot_asbestos").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_magnetized_tungsten = new ItemBase("ingot_magnetized_tungsten").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_combine_steel = new ItemCustomLore("ingot_combine_steel").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_dura_steel = new ItemCustomLore("ingot_dura_steel").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_polymer = new ItemCustomLore("ingot_polymer").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_lanthanium = new ItemCustomLore("ingot_lanthanium").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_actinium = new ItemCustomLore("ingot_actinium").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_desh = new ItemCustomLore("ingot_desh").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_saturnite = new ItemCustomLore("ingot_saturnite").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_euphemium = new ItemCustomLore("ingot_euphemium").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_dineutronium = new ItemCustomLore("ingot_dineutronium").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_starmetal = new ItemCustomLore("ingot_starmetal").setCreativeTab(MainRegistry.partsTab);
	public static final Item fluorite = new ItemBase("fluorite").setCreativeTab(MainRegistry.partsTab);
	public static final Item lithium = new ItemBase("lithium").setCreativeTab(MainRegistry.partsTab);
	public static final Item sulfur = new ItemBase("sulfur").setCreativeTab(MainRegistry.partsTab);
	public static final Item niter = new ItemBase("niter").setCreativeTab(MainRegistry.partsTab);
	
	public static final Item nugget_schrabidium = new ItemRadioactive("nugget_schrabidium").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_beryllium = new ItemBase("nugget_beryllium").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_solinium = new ItemRadioactive("nugget_solinium").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_euphemium = new ItemCustomLore("nugget_euphemium").setCreativeTab(MainRegistry.partsTab);
	
	
	//Radioactive Materials
	//Drillgon200: I'll have to deal with you guys more later...
	public static final Item ingot_australium = new ItemCustomLore("ingot_australium").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_weidanium = new ItemCustomLore("ingot_weidanium").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_reiium = new ItemCustomLore("ingot_reiium").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_unobtainium = new ItemCustomLore("ingot_unobtainium").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_daffergon = new ItemCustomLore("ingot_daffergon").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_verticium = new ItemCustomLore("ingot_verticium").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_australium = new ItemCustomLore("nugget_australium").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_weidanium = new ItemCustomLore("nugget_weidanium").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_reiium = new ItemCustomLore("nugget_reiium").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_unobtainium = new ItemCustomLore("nugget_unobtainium").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_daffergon = new ItemCustomLore("nugget_daffergon").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_verticium = new ItemCustomLore("nugget_verticium").setCreativeTab(MainRegistry.partsTab);
	
	public static final Item trinitite = new ItemRadioactive("trinitite");
	public static final Item nugget_plutonium = new ItemRadioactive("nugget_plutonium").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_uranium = new ItemRadioactive("nugget_uranium").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_u233 = new ItemRadioactive("nugget_u233");
	public static final Item nugget_u235 = new ItemRadioactive("nugget_u235");
	public static final Item nugget_u238 = new ItemRadioactive("nugget_u238").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_pu238 = new ItemRadioactive("nugget_pu238");
	public static final Item nugget_pu239 = new ItemRadioactive("nugget_pu239");
	public static final Item nugget_pu240 = new ItemRadioactive("nugget_pu240").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_th232 = new ItemBase("nugget_th232").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_neptunium = new ItemRadioactive("nugget_neptunium");
	public static final Item nugget_uranium_fuel = new ItemRadioactive("nugget_uranium_fuel").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_plutonium_fuel = new ItemRadioactive("nugget_plutonium_fuel").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_mox_fuel = new ItemRadioactive("nugget_mox_fuel").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_schrabidium_fuel = new ItemRadioactive("nugget_schrabidium_fuel").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_thorium_fuel = new ItemRadioactive("nugget_thorium_fuel").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_hes = new ItemRadioactive("nugget_hes").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_les = new ItemRadioactive("nugget_les").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_lead = new ItemBase("nugget_lead").setCreativeTab(MainRegistry.partsTab);
	
	public static final Item pellet_schrabidium = new WatzFuel(50000, 140000, 0.975F, 200, 1.05F, 1.05F, "pellet_schrabidium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_hes = new WatzFuel(108000, 65000, 1F, 85, 1, 1.025F, "pellet_hes").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_mes = new WatzFuel(216000, 23000, 1.025F, 50, 1, 1F, "pellet_mes").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_les = new WatzFuel(432000, 7000, 1.05F, 15, 1, 0.975F, "pellet_les").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_beryllium = new WatzFuel(864000, 50, 1.05F, 0, 0.95F, 1.025F, "pellet_beryllium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_neptunium = new WatzFuel(216000, 3000, 1.1F, 25, 1.1F, 1.005F, "pellet_neptunium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_lead = new WatzFuel(1728000, 0, 0.95F, 0, 0.95F, 0.95F, "pellet_lead").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_advanced = new WatzFuel(216000, 1000, 1.1F, 0, 0.995F, 0.99F, "pellet_advanced").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	
	public static final Item ingot_th232 = new ItemBase("ingot_th232").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_uranium = new ItemRadioactive("ingot_uranium").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_plutonium = new ItemRadioactive("ingot_plutonium").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_u233 = new ItemRadioactive("ingot_u233").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_u235 = new ItemRadioactive("ingot_u235");
	public static final Item ingot_u238 = new ItemRadioactive("ingot_u238").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_u238m2 = new ItemUnstable(350, 200, "ingot_u238m2").setCreativeTab(null);
	
	public static final Item ingot_pu239 = new ItemRadioactive("ingot_pu239").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_pu240 = new ItemRadioactive("ingot_pu240").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_pu238 = new ItemRadioactive("ingot_pu238");
	public static final Item ingot_uranium_fuel = new ItemRadioactive("ingot_uranium_fuel").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_plutonium_fuel = new ItemRadioactive("ingot_plutonium_fuel").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_mox_fuel = new ItemRadioactive("ingot_mox_fuel").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_thorium_fuel = new ItemRadioactive("ingot_thorium_fuel").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_schrabidium_fuel = new ItemRadioactive("ingot_schrabidium_fuel").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_neptunium = new ItemRadioactive("ingot_neptunium").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_solinium = new ItemRadioactive("ingot_solinium").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_hes = new ItemRadioactive("ingot_hes").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_les = new ItemRadioactive("ingot_les").setCreativeTab(MainRegistry.partsTab);
	
	public static final Item wiring_red_copper = new ItemWiring("wiring_red_copper").setCreativeTab(MainRegistry.partsTab);
	
	public static final Item nuclear_waste = new ItemRadioactive("nuclear_waste");
	public static final Item nuclear_waste_tiny = new ItemRadioactive("nuclear_waste_tiny").setCreativeTab(MainRegistry.partsTab);
	public static final Item waste_uranium = new ItemRadioactive("waste_uranium").setCreativeTab(MainRegistry.partsTab);
	public static final Item waste_thorium = new ItemRadioactive("waste_thorium").setCreativeTab(MainRegistry.partsTab);
	public static final Item waste_plutonium = new ItemRadioactive("waste_plutonium").setCreativeTab(MainRegistry.partsTab);
	public static final Item waste_mox = new ItemRadioactive("waste_mox").setCreativeTab(MainRegistry.partsTab);
	public static final Item waste_schrabidium = new ItemRadioactive("waste_schrabidium").setCreativeTab(MainRegistry.partsTab);
	public static final Item waste_uranium_hot = new ItemRadioactive("waste_uranium_hot").setCreativeTab(MainRegistry.partsTab);
	public static final Item waste_thorium_hot = new ItemRadioactive("waste_thorium_hot").setCreativeTab(MainRegistry.partsTab);
	public static final Item waste_plutonium_hot = new ItemRadioactive("waste_plutonium_hot").setCreativeTab(MainRegistry.partsTab);
	public static final Item waste_mox_hot = new ItemRadioactive("waste_mox_hot").setCreativeTab(MainRegistry.partsTab);
	public static final Item waste_schrabidium_hot = new ItemRadioactive("waste_schrabidium_hot").setCreativeTab(MainRegistry.partsTab);
	public static final Item scrap = new ItemFuel("scrap", 800).setCreativeTab(MainRegistry.partsTab);
	public static final Item dust = new ItemFuel("dust", 400).setCreativeTab(MainRegistry.partsTab);
	public static final Item containment_box = new ItemLeadBox("containment_box").setCreativeTab(null);
	
	public static final Item pellet_rtg = new ItemRadioactive("pellet_rtg").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_rtg_weak = new ItemRadioactive("pellet_rtg_weak").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item tritium_deuterium_cake = new ItemCustomLore("tritium_deuterium_cake").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	
	//That's a lot of rods
	public static final Item rod_empty = new ItemBase("rod_empty").setCreativeTab(MainRegistry.controlTab);
	public static final Item rod_dual_empty = new ItemBase("rod_dual_empty").setCreativeTab(MainRegistry.controlTab);
	public static final Item rod_quad_empty = new ItemBase("rod_quad_empty").setCreativeTab(MainRegistry.controlTab);
	public static final Item rod_thorium_fuel_depleted = new ItemRadioactive("rod_thorium_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_dual_thorium_fuel_depleted = new ItemRadioactive("rod_dual_thorium_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_quad_thorium_fuel_depleted = new ItemRadioactive("rod_quad_thorium_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_uranium_fuel_depleted = new ItemRadioactive("rod_uranium_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_dual_uranium_fuel_depleted = new ItemRadioactive("rod_dual_uranium_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_quad_uranium_fuel_depleted = new ItemRadioactive("rod_quad_uranium_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_plutonium_fuel_depleted = new ItemRadioactive("rod_plutonium_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_dual_plutonium_fuel_depleted = new ItemRadioactive("rod_dual_plutonium_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_quad_plutonium_fuel_depleted = new ItemRadioactive("rod_quad_plutonium_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_mox_fuel_depleted = new ItemRadioactive("rod_mox_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_dual_mox_fuel_depleted = new ItemRadioactive("rod_dual_mox_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_quad_mox_fuel_depleted = new ItemRadioactive("rod_quad_mox_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_schrabidium_fuel_depleted = new ItemRadioactive("rod_schrabidium_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_dual_schrabidium_fuel_depleted = new ItemRadioactive("rod_dual_schrabidium_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_quad_schrabidium_fuel_depleted = new ItemRadioactive("rod_quad_schrabidium_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_waste = new ItemRadioactive("rod_waste").setCreativeTab(null).setContainerItem(ModItems.rod_empty);
	public static final Item rod_dual_waste = new ItemRadioactive("rod_dual_waste").setCreativeTab(null).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_quad_waste = new ItemRadioactive("rod_quad_waste").setCreativeTab(null).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_dual_th232 = new ItemRadioactive("rod_dual_th232").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_dual_uranium = new ItemRadioactive("rod_dual_uranium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_dual_u233 = new ItemRadioactive("rod_dual_u233").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_dual_u235 = new ItemRadioactive("rod_dual_u235").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_dual_u238 = new ItemRadioactive("rod_dual_u238").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_dual_plutonium = new ItemRadioactive("rod_dual_plutonium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_dual_pu238 = new ItemRadioactive("rod_dual_pu238").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_dual_pu239 = new ItemRadioactive("rod_dual_pu239").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_dual_pu240 = new ItemRadioactive("rod_dual_pu240").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_dual_neptunium = new ItemRadioactive("rod_dual_neptunium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_dual_lead = new ItemBase("rod_dual_lead").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_dual_schrabidium = new ItemRadioactive("rod_dual_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_dual_solinium = new ItemRadioactive("rod_dual_solinium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_quad_th232 = new ItemRadioactive("rod_quad_th232").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_quad_uranium = new ItemRadioactive("rod_quad_uranium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_quad_u233 = new ItemRadioactive("rod_quad_u233").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_quad_u235 = new ItemRadioactive("rod_quad_u235").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_quad_u238 = new ItemRadioactive("rod_quad_u238").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_quad_plutonium = new ItemRadioactive("rod_quad_plutonium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_quad_pu238 = new ItemRadioactive("rod_quad_pu238").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_quad_pu239 = new ItemRadioactive("rod_quad_pu239").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_quad_pu240 = new ItemRadioactive("rod_quad_pu240").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_quad_neptunium = new ItemRadioactive("rod_quad_neptunium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_quad_lead = new ItemBase("rod_quad_lead").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_quad_schrabidium = new ItemRadioactive("rod_quad_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_quad_solinium = new ItemRadioactive("rod_quad_solinium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_thorium_fuel = new ItemFuelRod(100000, 10, "rod_thorium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_dual_thorium_fuel = new ItemFuelRod(100000, 20, "rod_dual_thorium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_quad_thorium_fuel = new ItemFuelRod(100000, 40, "rod_quad_thorium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_uranium_fuel = new ItemFuelRod(100000, 15, "rod_uranium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_dual_uranium_fuel = new ItemFuelRod(100000, 30, "rod_dual_uranium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_quad_uranium_fuel = new ItemFuelRod(100000, 60, "rod_quad_uranium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_plutonium_fuel = new ItemFuelRod(75000, 25, "rod_plutonium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_dual_plutonium_fuel = new ItemFuelRod(75000, 50, "rod_dual_plutonium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_quad_plutonium_fuel = new ItemFuelRod(75000, 100, "rod_quad_plutonium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_mox_fuel = new ItemFuelRod(150000, 10, "rod_mox_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_dual_mox_fuel = new ItemFuelRod(150000, 20, "rod_dual_mox_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_quad_mox_fuel = new ItemFuelRod(150000, 40, "rod_quad_mox_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_schrabidium_fuel = new ItemFuelRod(500000, 250, "rod_schrabidium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_dual_schrabidium_fuel = new ItemFuelRod(500000, 500, "rod_dual_schrabidium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_quad_schrabidium_fuel = new ItemFuelRod(500000, 1000, "rod_quad_schrabidium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	
	public static final Item rod_th232 = new ItemRadioactive("rod_th232").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_uranium = new ItemRadioactive("rod_uranium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_u233 = new ItemRadioactive("rod_u233").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_u235 = new ItemRadioactive("rod_u235").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_u238 = new ItemRadioactive("rod_u238").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_plutonium = new ItemRadioactive("rod_plutonium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_pu238 = new ItemRadioactive("rod_pu238").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_pu239 = new ItemRadioactive("rod_pu239").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_pu240 = new ItemRadioactive("rod_pu240").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_neptunium = new ItemRadioactive("rod_neptunium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_lead = new ItemBase("rod_lead").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_schrabidium = new ItemRadioactive("rod_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_solinium = new ItemRadioactive("rod_solinium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_euphemium = new ItemRadioactive("rod_euphemium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_quad_euphemium = new ItemCustomLore("rod_quad_euphemium").setCreativeTab(MainRegistry.partsTab).setMaxStackSize(1).setContainerItem(ModItems.rod_quad_empty);
	public static final Item rod_australium = new ItemRadioactive("rod_australium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_weidanium = new ItemRadioactive("rod_weidanium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_reiium = new ItemRadioactive("rod_reiium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_unobtainium = new ItemRadioactive("rod_unobtainium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_daffergon = new ItemRadioactive("rod_daffergon").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_verticium = new ItemRadioactive("rod_verticium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	
	public static final Item rod_water = new ItemCustomLore("rod_water").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_dual_water = new ItemCustomLore("rod_dual_water").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_quad_water = new ItemCustomLore("rod_quad_water").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);

	public static final Item rod_coolant = new ItemCustomLore("rod_coolant").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_dual_coolant = new ItemCustomLore("rod_dual_coolant").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_quad_coolant = new ItemCustomLore("rod_quad_coolant").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);

	public static final Item rod_lithium = new ItemCustomLore("rod_lithium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_dual_lithium = new ItemCustomLore("rod_dual_lithium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_quad_lithium = new ItemCustomLore("rod_quad_lithium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);

	public static final Item rod_tritium = new ItemRadioactive("rod_tritium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_dual_tritium = new ItemRadioactive("rod_dual_tritium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty);
	public static final Item rod_quad_tritium = new ItemRadioactive("rod_quad_tritium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty);
	
	//Generic Items
	public static final Item pellet_coal = new ItemBase("pellet_coal").setCreativeTab(MainRegistry.partsTab);
	public static final Item chlorine_pinwheel = new ItemBase("chlorine_pinwheel").setCreativeTab(MainRegistry.partsTab);
	public static final Item component_limiter = new ItemBase("component_limiter").setCreativeTab(MainRegistry.partsTab);
	public static final Item component_emitter = new ItemBase("component_emitter").setCreativeTab(MainRegistry.partsTab);
	public static final Item biomass = new ItemBase("biomass").setCreativeTab(MainRegistry.partsTab);
	public static final Item biomass_compressed = new ItemFuel("biomass_compressed", 800).setCreativeTab(MainRegistry.partsTab);
	public static final Item cordite = new ItemBase("cordite").setCreativeTab(MainRegistry.partsTab);
	public static final Item ballistite = new ItemBase("ballistite").setCreativeTab(MainRegistry.partsTab);
	public static final Item neutron_reflector = new ItemBase("neutron_reflector").setCreativeTab(MainRegistry.partsTab);
	public static final Item rtg_unit = new ItemBase("rtg_unit").setCreativeTab(MainRegistry.partsTab);
	public static final Item thermo_unit_empty = new ItemBase("thermo_unit_empty").setCreativeTab(MainRegistry.partsTab);
	public static final Item thermo_unit_endo = new ItemBase("thermo_unit_endo").setCreativeTab(MainRegistry.partsTab);
	public static final Item thermo_unit_exo = new ItemBase("thermo_unit_exo").setCreativeTab(MainRegistry.partsTab);
	public static final Item levitation_unit = new ItemBase("levitation_unit").setCreativeTab(MainRegistry.partsTab);
	public static final Item magnetron = new ItemCustomLore("magnetron").setCreativeTab(MainRegistry.partsTab);
	public static final Item pellet_buckshot = new ItemBase("pellet_buckshot").setCreativeTab(MainRegistry.partsTab);
	public static final Item pellet_flechette = new ItemBase("pellet_flechette").setCreativeTab(MainRegistry.partsTab);
	
	//Powders
	public static final Item powder_lignite = new ItemFuel("powder_lignite", 1200).setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_lead = new ItemBase("powder_lead").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_coal = new ItemFuel("powder_coal", 1600).setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_yellowcake = new ItemRadioactive("powder_yellowcake").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_neptunium = new ItemRadioactive("powder_neptunium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_uranium = new ItemRadioactive("powder_uranium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_plutonium = new ItemRadioactive("powder_plutonium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_thorium = new ItemCustomLore("powder_thorium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_schrabidium = new ItemRadioactive("powder_schrabidium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_iron = new ItemBase("powder_iron").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_gold = new ItemBase("powder_gold").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_titanium = new ItemBase("powder_titanium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_tungsten = new ItemBase("powder_tungsten").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_copper = new ItemBase("powder_copper").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_beryllium = new ItemBase("powder_beryllium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_aluminium = new ItemBase("powder_aluminium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_diamond = new ItemBase("powder_diamond").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_emerald = new ItemBase("powder_emerald").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_lapis = new ItemBase("powder_lapis").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_advanced_alloy = new ItemBase("powder_advanced_alloy").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_combine_steel = new ItemBase("powder_combine_steel").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_magnetized_tungsten = new ItemBase("powder_magnetized_tungsten").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_red_copper = new ItemBase("powder_red_copper").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_steel = new ItemBase("powder_steel").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_lithium = new ItemBase("powder_lithium").setCreativeTab(MainRegistry.partsTab);
	public static final Item redstone_depleted = new ItemBase("redstone_depleted").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_quartz = new ItemBase("powder_quartz").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_dura_steel = new ItemCustomLore("powder_dura_steel").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_polymer = new ItemCustomLore("powder_polymer").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_lanthanium = new ItemCustomLore("powder_lanthanium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_actinium = new ItemCustomLore("powder_actinium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_desh = new ItemBase("powder_desh").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_euphemium = new ItemCustomLore("powder_euphemium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_dineutronium = new ItemCustomLore("powder_dineutronium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_iodine = new ItemCustomLore("powder_iodine").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_astatine = new ItemCustomLore("powder_astatine").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_neodymium = new ItemCustomLore("powder_neodymium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_caesium = new ItemCustomLore("powder_caesium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_australium = new ItemCustomLore("powder_australium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_weidanium = new ItemCustomLore("powder_weidanium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_reiium = new ItemCustomLore("powder_reiium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_unobtainium = new ItemCustomLore("powder_unobtainium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_daffergon = new ItemCustomLore("powder_daffergon").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_verticium = new ItemCustomLore("powder_verticium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_strontium = new ItemCustomLore("powder_strontium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_cobalt = new ItemCustomLore("powder_cobalt").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_bromine = new ItemCustomLore("powder_bromine").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_niobium = new ItemCustomLore("powder_niobium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_tennessine = new ItemCustomLore("powder_tennessine").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_cerium = new ItemCustomLore("powder_cerium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_ice = new ItemCustomLore("powder_ice").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_desh_mix = new ItemBase("powder_desh_mix").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_nitan_mix = new ItemBase("powder_nitan_mix").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_spark_mix = new ItemBase("powder_spark_mix").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_fire = new ItemFuel("powder_fire", 6400).setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_meteorite = new ItemBase("powder_meteorite").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_magic = new ItemBase("powder_magic").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_cloud = new ItemBase("powder_cloud").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_poison = new ItemCustomLore("powder_poison").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_thermite = new ItemCustomLore("powder_thermite").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_power = new ItemCustomLore("powder_power").setCreativeTab(MainRegistry.partsTab);
	
	public static final Item powder_neodymium_tiny = new ItemBase("powder_neodymium_tiny").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_cobalt_tiny = new ItemBase("powder_cobalt_tiny").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_niobium_tiny = new ItemBase("powder_niobium_tiny").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_cerium_tiny = new ItemBase("powder_cerium_tiny").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_lithium_tiny = new ItemBase("powder_lithium_tiny").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_lanthanium_tiny = new ItemBase("powder_lanthanium_tiny").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_actinium_tiny = new ItemBase("powder_actinium_tiny").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_meteorite_tiny = new ItemBase("powder_meteorite_tiny").setCreativeTab(MainRegistry.partsTab);
	
	public static final Item pellet_cluster = new ItemCustomLore("pellet_cluster").setCreativeTab(MainRegistry.partsTab);
	public static final Item pellet_gas = new ItemCustomLore("pellet_gas").setCreativeTab(MainRegistry.partsTab);
	
	//Misc/crafting items
	public static final Item toothpicks = new ItemBase("toothpicks").setCreativeTab(MainRegistry.partsTab);
	public static final Item ducttape = new ItemBase("ducttape").setCreativeTab(MainRegistry.partsTab);
	public static final Item catalyst_clay = new ItemBase("catalyst_clay").setCreativeTab(MainRegistry.partsTab);
	public static final Item motor = new ItemBase("motor").setCreativeTab(MainRegistry.partsTab);
	public static final Item photo_panel = new ItemBase("photo_panel").setCreativeTab(MainRegistry.partsTab);
	public static final Item sat_base = new ItemBase("sat_base").setCreativeTab(MainRegistry.partsTab);
	public static final Item thruster_nuclear = new ItemBase("thruster_nuclear").setCreativeTab(MainRegistry.partsTab);
	public static final Item blade_titanium = new ItemBase("blade_titanium").setCreativeTab(MainRegistry.partsTab);
	public static final Item turbine_titanium = new ItemBase("turbine_titanium").setCreativeTab(MainRegistry.partsTab);
	public static final Item blade_tungsten = new ItemBase("blade_tungsten").setCreativeTab(MainRegistry.partsTab);
	public static final Item turbine_tungsten = new ItemBase("turbine_tungsten").setCreativeTab(MainRegistry.partsTab);
	public static final Item bolt_tungsten = new ItemBase("bolt_tungsten").setCreativeTab(MainRegistry.partsTab);
	public static final Item board_copper = new ItemBase("board_copper").setCreativeTab(MainRegistry.partsTab);
	public static final Item bolt_dura_steel = new ItemBase("bolt_dura_steel").setCreativeTab(MainRegistry.partsTab);
	public static final Item pipes_steel = new ItemBase("pipes_steel").setCreativeTab(MainRegistry.partsTab);
	public static final Item drill_titanium = new ItemBase("drill_titanium").setCreativeTab(MainRegistry.partsTab);
	public static final Item bolt_compound = new ItemBase("bolt_compound").setCreativeTab(MainRegistry.partsTab);
	public static final Item hazmat_cloth = new ItemBase("hazmat_cloth").setCreativeTab(MainRegistry.partsTab);
	public static final Item hazmat_cloth_red = new ItemBase("hazmat_cloth_red").setCreativeTab(MainRegistry.partsTab);
	public static final Item hazmat_cloth_grey = new ItemBase("hazmat_cloth_grey").setCreativeTab(MainRegistry.partsTab);
	public static final Item asbestos_cloth = new ItemBase("asbestos_cloth").setCreativeTab(MainRegistry.partsTab);
	public static final Item filter_coal = new ItemBase("filter_coal").setCreativeTab(MainRegistry.partsTab);
	
	//TODO remove
	public static final Item magnet_dee = new ItemBase("magnet_dee").setCreativeTab(MainRegistry.partsTab);
	public static final Item magnet_circular = new ItemBase("magnet_circular").setCreativeTab(MainRegistry.partsTab);
	public static final Item cyclotron_tower = new ItemBase("cyclotron_tower").setCreativeTab(MainRegistry.partsTab);
	public static final Item centrifuge_element = new ItemBase("centrifuge_element").setCreativeTab(MainRegistry.partsTab);
	public static final Item centrifuge_tower = new ItemBase("centrifuge_tower").setCreativeTab(MainRegistry.partsTab);
	public static final Item reactor_core = new ItemBase("reactor_core").setCreativeTab(MainRegistry.partsTab);
	public static final Item thermo_element = new ItemBase("thermo_element").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	
	//Fuels
	public static final Item solid_fuel = new ItemFuel("solid_fuel", 3200).setCreativeTab(MainRegistry.partsTab);
	public static final Item rocket_fuel = new ItemBase("rocket_fuel").setCreativeTab(MainRegistry.partsTab);
	public static final Item briquette_lignite = new ItemFuel("briquette_lignite", 1600).setCreativeTab(MainRegistry.partsTab);
	public static final Item coke = new ItemFuel("coke", 3200).setCreativeTab(MainRegistry.partsTab);
	public static final Item lignite = new ItemFuel("lignite", 1200).setCreativeTab(MainRegistry.partsTab);
	
	//Fragments?
	public static final Item fragment_neodymium = new ItemBase("fragment_neodymium").setCreativeTab(MainRegistry.partsTab);
	public static final Item fragment_cobalt = new ItemBase("fragment_cobalt").setCreativeTab(MainRegistry.partsTab);
	public static final Item fragment_niobium = new ItemBase("fragment_niobium").setCreativeTab(MainRegistry.partsTab);
	public static final Item fragment_cerium = new ItemBase("fragment_cerium").setCreativeTab(MainRegistry.partsTab);
	public static final Item fragment_lanthanium = new ItemBase("fragment_lanthanium").setCreativeTab(MainRegistry.partsTab);
	public static final Item fragment_actinium = new ItemBase("fragment_actinium").setCreativeTab(MainRegistry.partsTab);
	public static final Item fragment_meteorite = new ItemBase("fragment_meteorite").setCreativeTab(MainRegistry.partsTab);
	
	//Cells
	public static final Item cell = new ItemCell("cell").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	
	//Consume
	public static final Item bottle_opener = new WeaponSpecial(MainRegistry.enumToolMaterialBottleOpener, "bottle_opener").setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1);
	public static final Item bottle_empty = new ItemBase("bottle_empty").setCreativeTab(MainRegistry.consumableTab);
	
	
	public static final Item bottle_nuka = new ItemEnergy("bottle_nuka").setContainerItem(bottle_empty).setCreativeTab(MainRegistry.consumableTab);
	public static final Item bottle_cherry = new ItemEnergy("bottle_cherry").setContainerItem(ModItems.bottle_empty).setCreativeTab(MainRegistry.consumableTab);
	public static final Item bottle_quantum = new ItemEnergy("bottle_quantum").setContainerItem(ModItems.bottle_empty).setCreativeTab(MainRegistry.consumableTab);
	public static final Item bottle_sparkle = new ItemEnergy("bottle_sparkle").setContainerItem(ModItems.bottle_empty).setCreativeTab(MainRegistry.consumableTab);
	public static final Item bottle_rad = new ItemEnergy("bottle_rad").setContainerItem(ModItems.bottle_empty).setCreativeTab(MainRegistry.consumableTab);
	public static final Item bottle2_empty = new ItemBase("bottle2_empty").setCreativeTab(MainRegistry.consumableTab);
	public static final Item bottle2_korl = new ItemEnergy("bottle2_korl").setContainerItem(ModItems.bottle2_empty).setCreativeTab(MainRegistry.consumableTab);
	public static final Item bottle2_fritz = new ItemEnergy("bottle2_fritz").setContainerItem(ModItems.bottle2_empty).setCreativeTab(MainRegistry.consumableTab);
	public static final Item bottle2_korl_special = new ItemEnergy("bottle2_korl_special").setContainerItem(ModItems.bottle2_empty).setCreativeTab(MainRegistry.consumableTab);
	public static final Item bottle2_fritz_special = new ItemEnergy("bottle2_fritz_special").setContainerItem(ModItems.bottle2_empty).setCreativeTab(MainRegistry.consumableTab);
	public static final Item bottle2_sunset = new ItemEnergy("bottle2_sunset").setContainerItem(ModItems.bottle2_empty).setCreativeTab(MainRegistry.consumableTab);
	public static final Item chocolate_milk = new ItemEnergy("chocolate_milk").setCreativeTab(MainRegistry.consumableTab);
	public static final Item cap_nuka = new ItemBase("cap_nuka").setCreativeTab(MainRegistry.consumableTab);
	public static final Item cap_quantum = new ItemBase("cap_quantum").setCreativeTab(MainRegistry.consumableTab);
	public static final Item cap_sparkle = new ItemBase("cap_sparkle").setCreativeTab(MainRegistry.consumableTab);
	public static final Item cap_rad = new ItemBase("cap_rad").setCreativeTab(MainRegistry.consumableTab);
	public static final Item cap_korl = new ItemBase("cap_korl").setCreativeTab(MainRegistry.consumableTab);
	public static final Item cap_fritz = new ItemBase("cap_fritz").setCreativeTab(MainRegistry.consumableTab);
	public static final Item cap_sunset = new ItemBase("cap_sunset").setCreativeTab(MainRegistry.consumableTab);
	public static final Item cap_star = new ItemBase("cap_star").setCreativeTab(MainRegistry.consumableTab);
	public static final Item ring_pull = new ItemBase("ring_pull").setCreativeTab(MainRegistry.consumableTab);
	
	
	public static final Item bomb_waffle = new ItemWaffle(20, false, "bomb_waffle").setCreativeTab(MainRegistry.consumableTab);
	public static final Item schnitzel_vegan = new ItemSchnitzelVegan(0, true, "schnitzel_vegan").setCreativeTab(MainRegistry.consumableTab);
	public static final Item cotton_candy = new ItemCottonCandy(5, false, "cotton_candy").setCreativeTab(MainRegistry.consumableTab).setFull3D();
	public static final Item apple_lead = new ItemAppleSchrabidium(5, 0, false, "apple_lead").setCreativeTab(MainRegistry.consumableTab);
	public static final Item apple_lead1 = new ItemAppleSchrabidium(5, 0, false, "apple_lead1").setCreativeTab(MainRegistry.consumableTab);
	public static final Item apple_lead2 = new ItemAppleSchrabidium(5, 0, false, "apple_lead2").setCreativeTab(MainRegistry.consumableTab);
	public static final Item apple_schrabidium = new ItemAppleSchrabidium(20, 100, false, "apple_schrabidium").setCreativeTab(MainRegistry.consumableTab);
	public static final Item apple_schrabidium1 = new ItemAppleSchrabidium(20, 100, false, "apple_schrabidium1").setCreativeTab(MainRegistry.consumableTab);
	public static final Item apple_schrabidium2 = new ItemAppleSchrabidium(20, 100, false, "apple_schrabidium2").setCreativeTab(MainRegistry.consumableTab);
	public static final Item tem_flakes = new ItemTemFlakes(0, 0, false, "tem_flakes").setCreativeTab(MainRegistry.consumableTab);
	public static final Item tem_flakes1 = new ItemTemFlakes(0, 0, false, "tem_flakes1").setCreativeTab(MainRegistry.consumableTab);
	public static final Item tem_flakes2 = new ItemTemFlakes(0, 0, false, "tem_flakes2").setCreativeTab(MainRegistry.consumableTab);
	public static final Item glowing_stew = new ItemSoupBase(6, "glowing_stew").setCreativeTab(MainRegistry.consumableTab);
	public static final Item lemon = new ItemLemon(3, 5, false, "lemon").setCreativeTab(MainRegistry.consumableTab);
	public static final Item definitelyfood = new ItemLemon(2, 5, false, "definitelyfood").setCreativeTab(MainRegistry.consumableTab);
	public static final Item med_ipecac = new ItemLemon(0, 0, false, "med_ipecac").setCreativeTab(MainRegistry.consumableTab);
	public static final Item med_ptsd = new ItemLemon(0, 0, false, "med_ptsd").setCreativeTab(MainRegistry.consumableTab);
	public static final Item med_schizophrenia = new ItemLemon(0, 0, false, "med_schizophrenia").setCreativeTab(MainRegistry.consumableTab);
	public static final Item loops = new ItemLemon(4, 5, false, "loops").setCreativeTab(MainRegistry.consumableTab);
	public static final Item loop_stew = new ItemLemon(10, 10, false, "loop_stew").setCreativeTab(MainRegistry.consumableTab);
	public static final Item fooditem = new ItemLemon(2, 5, false, "fooditem").setCreativeTab(MainRegistry.consumableTab);
	public static final Item twinkie = new ItemLemon(3, 5, false, "twinkie").setCreativeTab(MainRegistry.consumableTab);
	public static final Item static_sandwich = new ItemLemon(6, 5, false, "static_sandwich").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canteen_13 = new ItemCanteen(1 * 60 * 20, "canteen_13").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canteen_vodka = new ItemCanteen(3 * 60 * 20, "canteen_vodka").setCreativeTab(MainRegistry.consumableTab);
	
	public static final Item canned_beef = new ItemLemon(8, 5, false, "canned_beef").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_tuna = new ItemLemon(4, 5, false, "canned_tuna").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_mystery = new ItemLemon(6, 5, false, "canned_mystery").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_pashtet = new ItemLemon(4, 5, false, "canned_pashtet").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_cheese = new ItemLemon(3, 5, false, "canned_cheese").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_jizz = new ItemLemon(15, 5, false, "canned_jizz").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_milk = new ItemLemon(5, 5, false, "canned_milk").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_ass = new ItemLemon(6, 5, false, "canned_ass").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_pizza = new ItemLemon(8, 5, false, "canned_pizza").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_tube = new ItemLemon(2, 5, false, "canned_tube").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_tomato = new ItemLemon(4, 5, false, "canned_tomato").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_asbestos = new ItemLemon(7, 5, false, "canned_asbestos").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_bhole = new ItemLemon(10, 5, false, "canned_bhole").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_hotdogs = new ItemLemon(5, 5, false, "canned_hotdogs").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_leftovers = new ItemLemon(1, 5, false, "canned_leftovers").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_yogurt = new ItemLemon(3, 5, false, "canned_yogurt").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_stew = new ItemLemon(5, 5, false, "canned_stew").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_chinese = new ItemLemon(6, 5, false, "canned_chinese").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_oil = new ItemLemon(3, 5, false, "canned_oil").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_fist = new ItemLemon(6, 5, false, "canned_fist").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_spam = new ItemLemon(8, 5, false, "canned_spam").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_fried = new ItemLemon(10, 5, false, "canned_fried").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_napalm = new ItemLemon(6, 5, false, "canned_napalm").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_diesel = new ItemLemon(6, 5, false, "canned_diesel").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_kerosene = new ItemLemon(6, 5, false, "canned_kerosene").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_recursion = new ItemLemon(1, 5, false, "canned_recursion").setCreativeTab(MainRegistry.consumableTab);
	public static final Item canned_bark = new ItemLemon(2, 5, false, "canned_bark").setCreativeTab(MainRegistry.consumableTab);
	public static final Item can_key = new ItemBase("can_key").setCreativeTab(MainRegistry.consumableTab);
	public static final Item pudding = new ItemLemon(6, 15, false, "pudding").setCreativeTab(MainRegistry.consumableTab);
	
	public static final Item can_empty = new ItemBase("can_empty").setCreativeTab(MainRegistry.consumableTab);
	public static final Item can_smart = new ItemEnergy("can_smart").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab);
	public static final Item can_creature = new ItemEnergy("can_creature").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab);
	public static final Item can_redbomb = new ItemEnergy("can_redbomb").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab);
	public static final Item can_mrsugar = new ItemEnergy("can_mrsugar").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab);
	public static final Item can_overcharge = new ItemEnergy("can_overcharge").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab);
	public static final Item can_luna = new ItemEnergy("can_luna").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab);
	public static final Item can_bepis = new ItemEnergy("can_bepis").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab);;
	public static final Item can_breen = new ItemEnergy("can_breen").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab);
	
	//Tools
	public static final Item alloy_pickaxe = new ModPickaxe(MainRegistry.enumToolMaterialAlloy, "alloy_pickaxe").setCreativeTab(CreativeTabs.TOOLS);
	public static final Item alloy_axe = new ModAxe(MainRegistry.enumToolMaterialAlloy, "alloy_axe").setCreativeTab(CreativeTabs.TOOLS);
	public static final Item alloy_hoe = new ModHoe(MainRegistry.enumToolMaterialAlloy, "alloy_hoe").setCreativeTab(CreativeTabs.TOOLS);
	public static final Item alloy_shovel = new ModSpade(MainRegistry.enumToolMaterialAlloy, "alloy_shovel").setCreativeTab(CreativeTabs.TOOLS);
	public static final Item alloy_sword = new ModSword(MainRegistry.enumToolMaterialAlloy, "alloy_sword").setCreativeTab(CreativeTabs.COMBAT);
	
	public static final Item cmb_sword = new ModSword(MainRegistry.enumToolMaterialCmb, "cmb_sword").setMaxStackSize(1);
	public static final Item cmb_pickaxe = new ModPickaxe(MainRegistry.enumToolMaterialCmb, "cmb_pickaxe").setMaxStackSize(1);
	public static final Item cmb_axe = new ModAxe(MainRegistry.enumToolMaterialCmb, "cmb_axe").setMaxStackSize(1);
	public static final Item cmb_shovel = new ModSpade(MainRegistry.enumToolMaterialCmb, "cmb_shovel").setMaxStackSize(1);
	public static final Item cmb_hoe = new ModHoe(MainRegistry.enumToolMaterialCmb, "cmb_hoe").setMaxStackSize(1);
	
	public static final Item elec_sword = new ModSword(MainRegistry.enumToolMaterialElec, "elec_sword").setMaxStackSize(1);
	public static final Item elec_pickaxe = new ModPickaxe(MainRegistry.enumToolMaterialElec, "elec_pickaxe").setMaxStackSize(1);
	public static final Item elec_axe = new ModAxe(MainRegistry.enumToolMaterialElec, "elec_axe").setMaxStackSize(1);
	public static final Item elec_shovel = new ModSpade(MainRegistry.enumToolMaterialElec, "elec_shovel").setMaxStackSize(1);
	
	public static final Item desh_sword = new ModSword(MainRegistry.enumToolMaterialDesh, "desh_sword").setMaxStackSize(1);
	public static final Item desh_pickaxe = new ModPickaxe(MainRegistry.enumToolMaterialDesh, "desh_pickaxe").setMaxStackSize(1);
	public static final Item desh_axe = new ModAxe(MainRegistry.enumToolMaterialDesh, "desh_axe").setMaxStackSize(1);
	public static final Item desh_shovel = new ModSpade(MainRegistry.enumToolMaterialDesh, "desh_shovel").setMaxStackSize(1);
	public static final Item desh_hoe = new ModHoe(MainRegistry.enumToolMaterialDesh, "desh_hoe").setMaxStackSize(1);
	
	public static final Item schrabidium_pickaxe = new PickaxeSchrabidium(MainRegistry.enumToolMaterialSchrabidium, "schrabidium_pickaxe").setCreativeTab(CreativeTabs.TOOLS);
	public static final Item schrabidium_axe = new AxeSchrabidium(MainRegistry.enumToolMaterialSchrabidium, "schrabidium_axe").setCreativeTab(CreativeTabs.TOOLS);
	public static final Item schrabidium_hoe = new HoeSchrabidium(MainRegistry.enumToolMaterialSchrabidium, "schrabidium_hoe").setCreativeTab(CreativeTabs.TOOLS);
	public static final Item schrabidium_shovel = new SpadeSchrabidium(MainRegistry.enumToolMaterialSchrabidium, "schrabidium_shovel").setCreativeTab(CreativeTabs.TOOLS);
	public static final Item schrabidium_sword = new SwordSchrabidium(MainRegistry.enumToolMaterialSchrabidium, "schrabidium_sword").setCreativeTab(CreativeTabs.COMBAT);
	
	public static final Item titanium_sword = new ModSword(MainRegistry.enumToolMaterialTitanium, "titanium_sword").setMaxStackSize(1);
	public static final Item titanium_pickaxe = new ModPickaxe(MainRegistry.enumToolMaterialTitanium, "titanium_pickaxe").setMaxStackSize(1);
	public static final Item titanium_axe = new ModAxe(MainRegistry.enumToolMaterialTitanium, "titanium_axe").setMaxStackSize(1);
	public static final Item titanium_shovel = new ModSpade(MainRegistry.enumToolMaterialTitanium, "titanium_shovel").setMaxStackSize(1);
	public static final Item titanium_hoe = new ModHoe(MainRegistry.enumToolMaterialTitanium, "titanium_hoe").setMaxStackSize(1);
	
	public static final Item steel_sword = new ModSword(MainRegistry.enumToolMaterialSteel, "steel_sword").setMaxStackSize(1);
	public static final Item steel_pickaxe = new ModPickaxe(MainRegistry.enumToolMaterialSteel, "steel_pickaxe").setMaxStackSize(1);
	public static final Item steel_axe = new ModAxe(MainRegistry.enumToolMaterialSteel, "steel_axe").setMaxStackSize(1);
	public static final Item steel_shovel = new ModSpade(MainRegistry.enumToolMaterialSteel, "steel_shovel").setMaxStackSize(1);
	public static final Item steel_hoe = new ModHoe(MainRegistry.enumToolMaterialSteel, "steel_hoe").setMaxStackSize(1);
	
	public static final Item crowbar = new ModSword(MainRegistry.enumToolMaterialSteel, "crowbar");
	
	public static final Item saw = new ModSword(MainRegistry.enumToolMaterialSaw, "weapon_saw").setFull3D();
	public static final Item bat = new ModSword(MainRegistry.enumToolMaterialBat, "weapon_bat").setFull3D();
	public static final Item bat_nail = new ModSword(MainRegistry.enumToolMaterialBatNail, "weapon_bat_nail").setFull3D();
	public static final Item golf_club = new ModSword(MainRegistry.enumToolMaterialGolfClub, "weapon_golf_club").setFull3D();
	public static final Item pipe_rusty = new ModSword(MainRegistry.enumToolMaterialPipeRusty, "weapon_pipe_rusty").setFull3D();
	public static final Item pipe_lead = new ModSword(MainRegistry.enumToolMaterialPipeLead, "weapon_pipe_lead").setFull3D();
	public static final Item reer_graar = new ModSword(MainRegistry.enumToolMaterialTitanium, "reer_graar").setFull3D();
	
	public static final Item mask_of_infamy = new MaskOfInfamy(ArmorMaterial.IRON, -1, EntityEquipmentSlot.HEAD, "mask_of_infamy").setMaxStackSize(1);
	
	//Templates
	public static final Item template_folder = new ItemTemplateFolder("template_folder").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab);
	public static final Item assembly_template = new ItemAssemblyTemplate("assembly_template").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab);
	public static final Item chemistry_template = new ItemChemistryTemplate("chemistry_template").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab);
	public static final Item chemistry_icon = new ItemChemistryIcon("chemistry_icon").setMaxStackSize(1).setCreativeTab(null);
	
	public static final Item bobmazon_materials = new ItemCatalog("bobmazon_materials").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab);
	public static final Item bobmazon_machines = new ItemCatalog("bobmazon_machines").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab);
	public static final Item bobmazon_weapons = new ItemCatalog("bobmazon_weapons").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab);
	public static final Item bobmazon_tools = new ItemCatalog("bobmazon_tools").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab);
	public static final Item bobmazon_hidden = new ItemCatalog("bobmazon_hidden").setMaxStackSize(1).setCreativeTab(null);
	
	//Plates
	public static final Item plate_iron = new ItemBase("plate_iron").setCreativeTab(MainRegistry.partsTab);
	public static final Item plate_steel = new ItemBase("plate_steel").setCreativeTab(MainRegistry.partsTab);
	public static final Item plate_gold = new ItemBase("plate_gold").setCreativeTab(MainRegistry.partsTab);
	public static final Item plate_lead = new ItemBase("plate_lead").setCreativeTab(MainRegistry.partsTab);
	public static final Item plate_copper = new ItemBase("plate_copper").setCreativeTab(MainRegistry.partsTab);
	public static final Item plate_advanced_alloy = new ItemBase("plate_advanced_alloy").setCreativeTab(MainRegistry.partsTab);
	public static final Item plate_combine_steel = new ItemBase("plate_combine_steel").setCreativeTab(MainRegistry.partsTab);
	public static final Item plate_mixed = new ItemBase("plate_mixed").setCreativeTab(MainRegistry.partsTab);
	public static final Item plate_paa = new ItemCustomLore("plate_paa").setCreativeTab(MainRegistry.partsTab);
	public static final Item plate_dalekanium = new ItemBase("plate_dalekanium").setCreativeTab(MainRegistry.partsTab);
	public static final Item plate_euphemium = new ItemBase("plate_euphemium").setCreativeTab(MainRegistry.partsTab);
	public static final Item plate_polymer = new ItemBase("plate_polymer").setCreativeTab(MainRegistry.partsTab);
	public static final Item plate_kevlar = new ItemBase("plate_kevlar").setCreativeTab(MainRegistry.partsTab);
	public static final Item plate_dineutronium = new ItemBase("plate_dineutronium").setCreativeTab(MainRegistry.partsTab);
	public static final Item plate_desh = new ItemBase("plate_desh").setCreativeTab(MainRegistry.partsTab);
	public static final Item plate_saturnite = new ItemBase("plate_saturnite").setCreativeTab(MainRegistry.partsTab);
	public static final Item plate_titanium = new ItemBase("plate_titanium").setCreativeTab(MainRegistry.partsTab);
	public static final Item plate_aluminium = new ItemBase("plate_aluminium").setCreativeTab(MainRegistry.partsTab);
	public static final Item plate_schrabidium = new ItemBase("plate_schrabidium").setCreativeTab(MainRegistry.partsTab);
	
	//Circuits
	public static final Item circuit_raw = new ItemBase("circuit_raw").setCreativeTab(MainRegistry.partsTab);
	public static final Item circuit_aluminium = new ItemBase("circuit_aluminium").setCreativeTab(MainRegistry.partsTab);
	public static final Item circuit_copper = new ItemBase("circuit_copper").setCreativeTab(MainRegistry.partsTab);
	public static final Item circuit_red_copper = new ItemBase("circuit_red_copper").setCreativeTab(MainRegistry.partsTab);
	public static final Item circuit_gold = new ItemBase("circuit_gold").setCreativeTab(MainRegistry.partsTab);
	public static final Item circuit_schrabidium = new ItemCustomLore("circuit_schrabidium").setCreativeTab(MainRegistry.partsTab);
	public static final Item circuit_targeting_tier1 = new ItemBase("circuit_targeting_tier1").setCreativeTab(MainRegistry.partsTab);
	public static final Item circuit_targeting_tier2 = new ItemBase("circuit_targeting_tier2").setCreativeTab(MainRegistry.partsTab);
	public static final Item circuit_targeting_tier3 = new ItemBase("circuit_targeting_tier3").setCreativeTab(MainRegistry.partsTab);
	public static final Item circuit_targeting_tier4 = new ItemBase("circuit_targeting_tier4").setCreativeTab(MainRegistry.partsTab);
	public static final Item circuit_targeting_tier5 = new ItemBase("circuit_targeting_tier5").setCreativeTab(MainRegistry.partsTab);
	public static final Item circuit_targeting_tier6 = new ItemBase("circuit_targeting_tier6").setCreativeTab(MainRegistry.partsTab);
	public static final Item mechanism_revolver_1 = new ItemBase("mechanism_revolver_1").setCreativeTab(MainRegistry.partsTab);
	public static final Item mechanism_revolver_2 = new ItemBase("mechanism_revolver_2").setCreativeTab(MainRegistry.partsTab);
	public static final Item mechanism_rifle_1 = new ItemBase("mechanism_rifle_1").setCreativeTab(MainRegistry.partsTab);
	public static final Item mechanism_rifle_2 = new ItemBase("mechanism_rifle_2").setCreativeTab(MainRegistry.partsTab);
	public static final Item mechanism_launcher_1 = new ItemBase("mechanism_launcher_1").setCreativeTab(MainRegistry.partsTab);
	public static final Item mechanism_launcher_2 = new ItemBase("mechanism_launcher_2").setCreativeTab(MainRegistry.partsTab);
	public static final Item mechanism_special = new ItemBase("mechanism_special").setCreativeTab(MainRegistry.partsTab);
	public static final Item primer_357 = new ItemBase("primer_357").setCreativeTab(MainRegistry.partsTab);
	public static final Item primer_44 = new ItemBase("primer_44").setCreativeTab(MainRegistry.partsTab);
	public static final Item primer_9 = new ItemBase("primer_9").setCreativeTab(MainRegistry.partsTab);
	public static final Item primer_50 = new ItemBase("primer_50").setCreativeTab(MainRegistry.partsTab);
	public static final Item primer_buckshot = new ItemBase("primer_buckshot").setCreativeTab(MainRegistry.partsTab);
	public static final Item casing_357 = new ItemBase("casing_357").setCreativeTab(MainRegistry.partsTab);
	public static final Item casing_44 = new ItemBase("casing_44").setCreativeTab(MainRegistry.partsTab);
	public static final Item casing_9 = new ItemBase("casing_9").setCreativeTab(MainRegistry.partsTab);
	public static final Item casing_50 = new ItemBase("casing_50").setCreativeTab(MainRegistry.partsTab);
	public static final Item casing_buckshot = new ItemBase("casing_buckshot").setCreativeTab(MainRegistry.partsTab);
	
	//Wires and things
	public static final Item wire_advanced_alloy = new ItemBase("wire_advanced_alloy").setCreativeTab(MainRegistry.partsTab);
	public static final Item coil_advanced_alloy = new ItemBase("coil_advanced_alloy").setCreativeTab(MainRegistry.partsTab);
	public static final Item coil_advanced_torus = new ItemBase("coil_advanced_torus").setCreativeTab(MainRegistry.partsTab);
	public static final Item coil_gold = new ItemBase("coil_gold").setCreativeTab(MainRegistry.partsTab);
	public static final Item coil_gold_torus = new ItemBase("coil_gold_torus").setCreativeTab(MainRegistry.partsTab);
	public static final Item wire_red_copper = new ItemBase("wire_red_copper").setCreativeTab(MainRegistry.partsTab);
	public static final Item wire_tungsten = new ItemCustomLore("wire_tungsten").setCreativeTab(MainRegistry.partsTab);
	public static final Item coil_tungsten = new ItemBase("coil_tungsten").setCreativeTab(MainRegistry.partsTab);
	public static final Item wire_aluminium = new ItemBase("wire_aluminium").setCreativeTab(MainRegistry.partsTab);
	public static final Item wire_copper = new ItemBase("wire_copper").setCreativeTab(MainRegistry.partsTab);
	public static final Item coil_copper = new ItemBase("coil_copper").setCreativeTab(MainRegistry.partsTab);
	public static final Item coil_copper_torus = new ItemBase("coil_copper_torus").setCreativeTab(MainRegistry.partsTab);
	public static final Item wire_gold = new ItemBase("wire_gold").setCreativeTab(MainRegistry.partsTab);
	public static final Item wire_schrabidium = new ItemRadioactive("wire_schrabidium").setCreativeTab(MainRegistry.partsTab);
	public static final Item wire_magnetized_tungsten = new ItemBase("wire_magnetized_tungsten").setCreativeTab(MainRegistry.partsTab);
	public static final Item coil_magnetized_tungsten = new ItemBase("coil_magnetized_tungsten").setCreativeTab(MainRegistry.partsTab);
	
	//Stamps
	public static final Item stamp_stone_flat = new ItemBlades("stamp_stone_flat", 5);
	public static final Item stamp_stone_plate = new ItemBlades("stamp_stone_plate", 5);
	public static final Item stamp_stone_wire = new ItemBlades("stamp_stone_wire", 5);
	public static final Item stamp_stone_circuit = new ItemBlades("stamp_stone_circuit", 5);
	public static final Item stamp_iron_flat = new ItemBlades("stamp_iron_flat", 25);
	public static final Item stamp_iron_plate = new ItemBlades("stamp_iron_plate", 25);
	public static final Item stamp_iron_wire = new ItemBlades("stamp_iron_wire", 25);
	public static final Item stamp_iron_circuit = new ItemBlades("stamp_iron_circuit", 25);
	public static final Item stamp_steel_flat = new ItemBlades("stamp_steel_flat", 50);
	public static final Item stamp_steel_plate = new ItemBlades("stamp_steel_plate", 50);
	public static final Item stamp_steel_wire = new ItemBlades("stamp_steel_wire", 50);
	public static final Item stamp_steel_circuit = new ItemBlades("stamp_steel_circuit", 50);
	public static final Item stamp_titanium_flat = new ItemBlades("stamp_titanium_flat", 65);
	public static final Item stamp_titanium_plate = new ItemBlades("stamp_titanium_plate", 65);
	public static final Item stamp_titanium_wire = new ItemBlades("stamp_titanium_wire", 65);
	public static final Item stamp_titanium_circuit = new ItemBlades("stamp_titanium_circuit", 65);
	public static final Item stamp_obsidian_flat = new ItemBlades("stamp_obsidian_flat", 100);
	public static final Item stamp_obsidian_plate = new ItemBlades("stamp_obsidian_plate", 100);
	public static final Item stamp_obsidian_wire = new ItemBlades("stamp_obsidian_wire", 100);
	public static final Item stamp_obsidian_circuit = new ItemBlades("stamp_obsidian_circuit", 100);
	public static final Item stamp_schrabidium_flat = new ItemBlades("stamp_schrabidium_flat", 1024);
	public static final Item stamp_schrabidium_plate = new ItemBlades("stamp_schrabidium_plate", 1024);
	public static final Item stamp_schrabidium_wire = new ItemBlades("stamp_schrabidium_wire", 1024);
	public static final Item stamp_schrabidium_circuit = new ItemBlades("stamp_schrabidium_circuit", 1024);
	public static final Item stamp_357 = new ItemBlades("stamp_357", 512);
	public static final Item stamp_44 = new ItemBlades("stamp_44", 512);
	public static final Item stamp_9 = new ItemBlades("stamp_9", 512);
	public static final Item stamp_50 = new ItemBlades("stamp_50", 512);
	
	public static final Item blades_aluminum = new ItemBlades("blades_aluminum", 10);
	public static final Item blades_gold = new ItemBlades("blades_gold", 25);
	public static final Item blades_iron = new ItemBlades("blades_iron", 35);
	public static final Item blades_steel = new ItemBlades("blades_steel", 50);
	public static final Item blades_titanium = new ItemBlades("blades_titanium", 65);
	public static final Item blades_advanced_alloy = new ItemBlades("blades_advanced_alloy", 85);
	public static final Item blades_combine_steel = new ItemBlades("blades_combine_steel", 150);
	public static final Item blades_schrabidium = new ItemBlades("blades_schrabidium", 250);
	
	public static final Item part_lithium = new ItemBase("part_lithium").setCreativeTab(MainRegistry.controlTab);
	public static final Item part_beryllium = new ItemBase("part_beryllium").setCreativeTab(MainRegistry.controlTab);
	public static final Item part_carbon = new ItemBase("part_carbon").setCreativeTab(MainRegistry.controlTab);
	public static final Item part_copper = new ItemBase("part_copper").setCreativeTab(MainRegistry.controlTab);
	public static final Item part_plutonium = new ItemBase("part_plutonium").setCreativeTab(MainRegistry.controlTab);
	
	//Gun ammo assemblies and ammo
	public static final Item assembly_iron = new ItemBase("assembly_iron").setCreativeTab(MainRegistry.partsTab);
	public static final Item assembly_steel = new ItemBase("assembly_steel").setCreativeTab(MainRegistry.partsTab);
	public static final Item assembly_lead = new ItemBase("assembly_lead").setCreativeTab(MainRegistry.partsTab);
	public static final Item assembly_gold = new ItemBase("assembly_gold").setCreativeTab(MainRegistry.partsTab);
	public static final Item assembly_schrabidium = new ItemBase("assembly_schrabidium").setCreativeTab(MainRegistry.partsTab);
	public static final Item assembly_nightmare = new ItemBase("assembly_nightmare").setCreativeTab(MainRegistry.partsTab);
	public static final Item assembly_desh = new ItemBase("assembly_desh").setCreativeTab(MainRegistry.partsTab);
	public static final Item assembly_nopip = new ItemBase("assembly_nopip").setCreativeTab(MainRegistry.partsTab);
	public static final Item assembly_smg = new ItemBase("assembly_smg").setCreativeTab(MainRegistry.partsTab);
	public static final Item assembly_uzi = new ItemBase("assembly_uzi").setCreativeTab(MainRegistry.partsTab);
	public static final Item assembly_actionexpress = new ItemBase("assembly_actionexpress").setCreativeTab(MainRegistry.partsTab);
	public static final Item assembly_calamity = new ItemBase("assembly_calamity").setCreativeTab(MainRegistry.partsTab);
	public static final Item assembly_lacunae = new ItemBase("assembly_lacunae").setCreativeTab(MainRegistry.partsTab);
	public static final Item gun_revolver_iron_ammo = new ItemBase("gun_revolver_iron_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver_ammo = new ItemBase("gun_revolver_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver_lead_ammo = new ItemBase("gun_revolver_lead_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver_gold_ammo = new ItemBase("gun_revolver_gold_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver_schrabidium_ammo = new ItemRadioactive("gun_revolver_schrabidium_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver_nightmare_ammo = new ItemCustomLore("gun_revolver_nightmare_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver_nightmare2_ammo = new ItemCustomLore("gun_revolver_nightmare2_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_357_desh = new ItemAmmo("ammo_357_desh").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver_cursed_ammo = new ItemCustomLore("gun_revolver_cursed_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver_pip_ammo = new ItemCustomLore("gun_revolver_pip_ammo").setCreativeTab(null);
	public static final Item gun_revolver_nopip_ammo = new ItemBase("gun_revolver_nopip_ammo").setCreativeTab(null);
	public static final Item gun_mp_ammo = new ItemCustomLore("gun_mp_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_rpg_ammo = new ItemBase("gun_rpg_ammo").setCreativeTab(null);
	public static final Item gun_spark_ammo = new ItemBase("gun_spark_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_euthanasia_ammo = new ItemBase("gun_euthanasia_ammo").setCreativeTab(MainRegistry.weaponTab);
	
	public static final Item ammo_20gauge = new ItemAmmo("ammo_20gauge").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_20gauge_slug = new ItemAmmo("ammo_20gauge_slug").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_20gauge_flechette = new ItemAmmo("ammo_20gauge_flechette").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_20gauge_incendiary = new ItemAmmo("ammo_20gauge_incendiary").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_20gauge_explosive = new ItemAmmo("ammo_20gauge_explosive").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_20gauge_caustic = new ItemAmmo("ammo_20gauge_caustic").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_20gauge_shock = new ItemAmmo("ammo_20gauge_shock").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_20gauge_wither = new ItemAmmo("ammo_20gauge_wither").setCreativeTab(MainRegistry.weaponTab);
	
	public static final Item ammo_rocket = new ItemAmmo("ammo_rocket").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_rocket_he = new ItemAmmo("ammo_rocket_he").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_rocket_incendiary = new ItemAmmo("ammo_rocket_incendiary").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_rocket_shrapnel = new ItemAmmo("ammo_rocket_shrapnel").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_rocket_emp = new ItemAmmo("ammo_rocket_emp").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_rocket_glare = new ItemAmmo("ammo_rocket_glare").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_rocket_toxic = new ItemAmmo("ammo_rocket_toxic").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_rocket_sleek = new ItemAmmo("ammo_rocket_sleek").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_rocket_nuclear = new ItemAmmo("ammo_rocket_nuclear").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_rocket_rpc = new ItemAmmo("ammo_rocket_rpc").setCreativeTab(MainRegistry.weaponTab);
	
	public static final Item ammo_grenade = new ItemAmmo("ammo_grenade").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_grenade_he = new ItemAmmo("ammo_grenade_he").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_grenade_incendiary = new ItemAmmo("ammo_grenade_incendiary").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_grenade_toxic = new ItemAmmo("ammo_grenade_toxic").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_grenade_concussion = new ItemAmmo("ammo_grenade_concussion").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_grenade_finned = new ItemAmmo("ammo_grenade_finned").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_grenade_sleek = new ItemAmmo("ammo_grenade_sleek").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_grenade_nuclear = new ItemAmmo("ammo_grenade_nuclear").setCreativeTab(MainRegistry.weaponTab);
	
	public static final Item ammo_12gauge = new ItemAmmo("ammo_12gauge").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_12gauge_incendiary = new ItemAmmo("ammo_12gauge_incendiary").setCreativeTab(MainRegistry.weaponTab);
	
	public static final Item ammo_22lr = new ItemAmmo("ammo_22lr").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_22lr_ap = new ItemAmmo("ammo_22lr_ap").setCreativeTab(MainRegistry.weaponTab);
	
	public static final Item ammo_44 = new ItemAmmo("ammo_44").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_44_ap = new ItemAmmo("ammo_44_ap").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_44_du = new ItemAmmo("ammo_44_du").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_44_pip = new ItemAmmo("ammo_44_pip").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_44_bj = new ItemAmmo("ammo_44_bj").setCreativeTab(MainRegistry.weaponTab);
	
	public static final Item ammo_9mm = new ItemAmmo("ammo_9mm").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_9mm_ap = new ItemAmmo("ammo_9mm_ap").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_9mm_du = new ItemAmmo("ammo_9mm_du").setCreativeTab(MainRegistry.weaponTab);
	
	public static final Item ammo_50bmg = new ItemAmmo("ammo_50bmg").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_50bmg_incendiary = new ItemAmmo("ammo_50bmg_incendiary").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_50bmg_explosive = new ItemAmmo("ammo_50bmg_explosive").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_50bmg_du = new ItemAmmo("ammo_50bmg_du").setCreativeTab(MainRegistry.weaponTab);
	
	public static final Item ammo_5mm = new ItemAmmo("ammo_5mm").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_5mm_explosive = new ItemAmmo("ammo_5mm_explosive").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_5mm_du = new ItemAmmo("ammo_5mm_du").setCreativeTab(MainRegistry.weaponTab);
	
	public static final Item ammo_50ae = new ItemAmmo("ammo_50ae").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_50ae_ap = new ItemAmmo("ammo_50ae_ap").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_50ae_du = new ItemAmmo("ammo_50ae_du").setCreativeTab(MainRegistry.weaponTab);
	
	public static final Item gun_b92_ammo = new GunB92Cell("gun_b92_ammo").setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_fatman_ammo = new ItemBase("gun_fatman_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_mirv_ammo = new ItemBase("gun_mirv_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_bf_ammo = new ItemBase("gun_bf_ammo").setCreativeTab(null);
	public static final Item gun_stinger_ammo = new ItemBase("gun_stinger_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_xvl1456_ammo = new ItemBase("gun_xvl1456_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_hp_ammo = new ItemBase("gun_hp_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_defabricator_ammo = new ItemBase("gun_defabricator_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_cryolator_ammo = new ItemBase("gun_cryolator_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_jack_ammo = new ItemBase("gun_jack_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_immolator_ammo = new ItemBase("gun_immolator_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_osipr_ammo = new ItemBase("gun_osipr_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_osipr_ammo2 = new ItemBase("gun_osipr_ammo2").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_emp_ammo = new ItemBase("gun_emp_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_folly = new ItemAmmo("ammo_folly").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_folly_nuclear = new ItemAmmo("ammo_folly_nuclear").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_folly_du = new ItemAmmo("ammo_folly_du").setCreativeTab(MainRegistry.weaponTab);
	public static final Item folly_shell = new ItemBase("folly_shell").setCreativeTab(MainRegistry.partsTab);
	public static final Item folly_bullet = new ItemBase("folly_bullet").setCreativeTab(MainRegistry.partsTab);
	public static final Item folly_bullet_nuclear = new ItemBase("folly_bullet_nuclear").setCreativeTab(MainRegistry.partsTab);
	public static final Item folly_bullet_du = new ItemBase("folly_bullet_du").setCreativeTab(MainRegistry.partsTab);
	public static final Item gun_calamity_ammo = new ItemCustomLore("gun_calamity_ammo").setCreativeTab(null);
	public static final Item gun_lacunae_ammo = new ItemCustomLore("gun_lacunae_ammo").setCreativeTab(null);
	public static final Item gun_mp40_ammo = new ItemBase("gun_mp40_ammo").setCreativeTab(null);
	public static final Item gun_uzi_ammo = new ItemBase("gun_uzi_ammo").setCreativeTab(null);
	public static final Item gun_uboinik_ammo = new ItemBase("gun_uboinik_ammo").setCreativeTab(null);
	public static final Item gun_lever_action_ammo = new ItemBase("gun_lever_action_ammo").setCreativeTab(null);
	public static final Item gun_bolt_action_ammo = new ItemBase("gun_bolt_action_ammo").setCreativeTab(null);
	
	public static final Item energy_ball = new ItemBase("energy_ball");
	public static final Item charge_railgun = new ItemCustomLore("charge_railgun").setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab);
	
	public static final Item clip_revolver_iron = new ItemClip("clip_revolver_iron").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_revolver = new ItemClip("clip_revolver").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_revolver_gold = new ItemClip("clip_revolver_gold").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_revolver_lead = new ItemClip("clip_revolver_lead").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_revolver_schrabidium = new ItemClip("clip_revolver_schrabidium").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_revolver_cursed = new ItemClip("clip_revolver_cursed").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_revolver_nightmare = new ItemClip("clip_revolver_nightmare").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_revolver_nightmare2 = new ItemClip("clip_revolver_nightmare2").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_revolver_pip = new ItemClip("clip_revolver_pip").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_revolver_nopip = new ItemClip("clip_revolver_nopip").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_rpg = new ItemClip("clip_rpg").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_stinger = new ItemClip("clip_stinger").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_fatman = new ItemClip("clip_fatman").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_mirv = new ItemClip("clip_mirv").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_bf = new ItemClip("clip_bf").setCreativeTab(null);
	public static final Item clip_mp40 = new ItemClip("clip_mp40").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_uzi = new ItemClip("clip_uzi").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_uboinik = new ItemClip("clip_uboinik").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_lever_action = new ItemClip("clip_lever_action").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_bolt_action = new ItemClip("clip_bolt_action").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_osipr = new ItemClip("clip_osipr").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_immolator = new ItemClip("clip_immolator").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_cryolator = new ItemClip("clip_cryolator").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_mp = new ItemClip("clip_mp").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_xvl1456 = new ItemClip("clip_xvl1456").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_emp = new ItemClip("clip_emp").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_jack = new ItemClip("clip_jack").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_spark = new ItemClip("clip_spark").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_hp = new ItemClip("clip_hp").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_euthanasia = new ItemClip("clip_euthanasia").setCreativeTab(MainRegistry.weaponTab);
	public static final Item clip_defabricator = new ItemClip("clip_defabricator").setCreativeTab(MainRegistry.weaponTab);

	public static final Item ammo_container = new ItemClip("ammo_container").setCreativeTab(MainRegistry.weaponTab);
	
	//Grenade
	public static final Item grenade_generic = new ItemGrenade(4, "grenade_generic").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_strong = new ItemGrenade(5, "grenade_strong").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_frag = new ItemGrenade(4, "grenade_frag").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_fire = new ItemGrenade(4, "grenade_fire").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_shrapnel = new ItemGrenade(4, "grenade_shrapnel").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_cluster = new ItemGrenade(5, "grenade_cluster").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_flare = new ItemGrenade(0, "grenade_flare").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_electric = new ItemGrenade(5, "grenade_electric").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_poison = new ItemGrenade(4, "grenade_poison").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_gas = new ItemGrenade(4, "grenade_gas").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_cloud = new ItemGrenade(-1, "grenade_cloud").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_pink_cloud = new ItemGrenade(-1, "grenade_pink_cloud").setCreativeTab(null);
	public static final Item grenade_smart = new ItemGrenade(-1, "grenade_smart").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_mirv = new ItemGrenade(1, "grenade_mirv").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_breach = new ItemGrenade(-1, "grenade_breach").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_burst = new ItemGrenade(1, "grenade_burst").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_pulse = new ItemGrenade(4, "grenade_pulse").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_plasma = new ItemGrenade(5, "grenade_plasma").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_tau = new ItemGrenade(5, "grenade_tau").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_schrabidium = new ItemGrenade(7, "grenade_schrabidium").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_nuke = new ItemGrenade(-1, "grenade_nuke").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_lemon = new ItemGrenade(4, "grenade_lemon").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_gascan = new ItemGrenade(-1, "grenade_gascan").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_mk2 = new ItemGrenade(5, "grenade_mk2").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_aschrab = new ItemGrenade(-1, "grenade_aschrab").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_nuclear = new ItemGrenade(7, "grenade_nuclear").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_zomg = new ItemGrenade(7, "grenade_zomg").setCreativeTab(MainRegistry.weaponTab);
	public static final Item grenade_black_hole = new ItemGrenade(7, "grenade_black_hole").setCreativeTab(MainRegistry.weaponTab);
	
	public static final Item grenade_if_generic = new ItemGrenade(4, "grenade_if_generic").setCreativeTab(null);
	public static final Item grenade_if_he = new ItemGrenade(5, "grenade_if_he").setCreativeTab(null);
	public static final Item grenade_if_bouncy = new ItemGrenade(4, "grenade_if_bouncy").setCreativeTab(null);
	public static final Item grenade_if_sticky = new ItemGrenade(4, "grenade_if_sticky").setCreativeTab(null);
	public static final Item grenade_if_impact = new ItemGrenade(-1, "grenade_if_impact").setCreativeTab(null);
	public static final Item grenade_if_incendiary = new ItemGrenade(4, "grenade_if_incendiary").setCreativeTab(null);
	public static final Item grenade_if_toxic = new ItemGrenade(4, "grenade_if_toxic").setCreativeTab(null);
	public static final Item grenade_if_concussion = new ItemGrenade(4, "grenade_if_concussion").setCreativeTab(null);
	public static final Item grenade_if_brimstone = new ItemGrenade(5, "grenade_if_brimstone").setCreativeTab(null);
	public static final Item grenade_if_mystery = new ItemGrenade(5, "grenade_if_mystery").setCreativeTab(null);
	public static final Item grenade_if_spark = new ItemGrenade(7, "grenade_if_spark").setCreativeTab(null);
	public static final Item grenade_if_hopwire = new ItemGrenade(7, "grenade_if_hopwire").setCreativeTab(null);
	public static final Item grenade_if_null = new ItemGrenade(7, "grenade_if_null").setCreativeTab(null);
	
	public static final Item weaponized_starblaster_cell = new WeaponizedCell("weaponized_starblaster_cell").setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab);
	
	//Turret
	public static final Item turret_control = new ItemTurretControl("turret_control").setFull3D().setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab);
	public static final Item turret_chip = new ItemTurretChip("turret_chip").setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab);
	public static final Item turret_biometry = new ItemTurretBiometry("turret_biometry").setFull3D().setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab);
	public static final Item turret_light_ammo = new ItemTurretAmmo(ModBlocks.turret_light, 100, "turret_light_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item turret_heavy_ammo = new ItemTurretAmmo(ModBlocks.turret_heavy, 25, "turret_heavy_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item turret_rocket_ammo = new ItemTurretAmmo(ModBlocks.turret_rocket, 8, "turret_rocket_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item turret_flamer_ammo = new ItemTurretAmmo(ModBlocks.turret_flamer, 200, "turret_flamer_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item turret_tau_ammo = new ItemTurretAmmo(ModBlocks.turret_tau, 100, "turret_tau_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item turret_spitfire_ammo = new ItemTurretAmmo(ModBlocks.turret_spitfire, 2, "turret_spitfire_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item turret_cwis_ammo = new ItemTurretAmmo(ModBlocks.turret_cwis, 250, "turret_cwis_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item turret_cheapo_ammo = new ItemTurretAmmo(ModBlocks.turret_cheapo, 100, "turret_cheapo_ammo").setCreativeTab(MainRegistry.weaponTab);
	
	public static final Item geiger_counter = new ItemGeigerCounter("geiger_counter").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	public static final Item survey_scanner = new ItemSurveyScanner("survey_scanner").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	
	//Keys + locks
	public static final Item key = new ItemKey("key").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	public static final Item key_red = new ItemCustomLore("key_red").setMaxStackSize(1).setCreativeTab(null);
	public static final Item key_kit = new ItemCounterfitKeys("key_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	public static final Item key_fake = new ItemKey("key_fake").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	public static final Item pin = new ItemCustomLore("pin").setMaxStackSize(8).setCreativeTab(MainRegistry.consumableTab);
	public static final Item padlock_rusty = new ItemLock(1, "padlock_rusty").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	public static final Item padlock = new ItemLock(0.1, "padlock").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	public static final Item padlock_reinforced = new ItemLock(0.02, "padlock_reinforced").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	public static final Item padlock_unbreakable = new ItemLock(0, "padlock_unbreakable").setMaxStackSize(1).setCreativeTab(null);
	
	//Upgrade
	public static final Item upgrade_template = new ItemCustomLore("upgrade_template").setMaxStackSize(1).setCreativeTab(MainRegistry.partsTab);
	public static final Item upgrade_speed_1 = new ItemCustomLore("upgrade_speed_1").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item upgrade_speed_2 = new ItemCustomLore("upgrade_speed_2").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item upgrade_speed_3 = new ItemCustomLore("upgrade_speed_3").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item upgrade_effect_1 = new ItemCustomLore("upgrade_effect_1").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item upgrade_effect_2 = new ItemCustomLore("upgrade_effect_2").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item upgrade_effect_3 = new ItemCustomLore("upgrade_effect_3").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item upgrade_power_1 = new ItemCustomLore("upgrade_power_1").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item upgrade_power_2 = new ItemCustomLore("upgrade_power_2").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item upgrade_power_3 = new ItemCustomLore("upgrade_power_3").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item upgrade_fortune_1 = new ItemCustomLore("upgrade_fortune_1").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item upgrade_fortune_2 = new ItemCustomLore("upgrade_fortune_2").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item upgrade_fortune_3 = new ItemCustomLore("upgrade_fortune_3").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item upgrade_afterburn_1 = new ItemCustomLore("upgrade_afterburn_1").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item upgrade_afterburn_2 = new ItemCustomLore("upgrade_afterburn_2").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item upgrade_afterburn_3 = new ItemCustomLore("upgrade_afterburn_3").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item upgrade_radius = new ItemCustomLore("upgrade_radius").setMaxStackSize(16).setCreativeTab(MainRegistry.controlTab);
	public static final Item upgrade_health = new ItemCustomLore("upgrade_health").setMaxStackSize(16).setCreativeTab(MainRegistry.controlTab);
	
	//AMS
	public static final Item ams_focus_blank = new ItemBase("ams_focus_blank").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item ams_focus_limiter = new ItemCustomLore("ams_focus_limiter").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item ams_focus_booster = new ItemCustomLore("ams_focus_booster").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item ams_muzzle = new ItemCustomLore("ams_muzzle").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item ams_core_sing = new ItemAMSCore(1000000000L, 200, 10, "ams_core_sing").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item ams_core_wormhole = new ItemAMSCore(1500000000L, 200, 15, "ams_core_wormhole").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item ams_core_eyeofharmony = new ItemAMSCore(2500000000L, 300, 10, "ams_core_eyeofharmony").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item ams_core_thingy = new ItemAMSCore(5000000000L, 250, 5, "ams_core_thingy").setMaxStackSize(1).setCreativeTab(null);
	
	//Special tools
	public static final Item forge_fluid_identifier = new ItemForgeFluidIdentifier("forge_fluid_identifier").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab);
	public static final Item fluid_icon = new ItemFluidIcon("fluid_icon").setCreativeTab(null);
	
	//Nuke parts
	public static final Item gadget_explosive = new ItemBase("gadget_explosive").setCreativeTab(MainRegistry.nukeTab);
	public static final Item gadget_explosive8 = new ItemGadget("gadget_explosive8").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item gadget_wireing = new ItemGadget("gadget_wireing").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item gadget_core = new ItemGadget("gadget_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	
	public static final Item boy_igniter = new ItemBoy("boy_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item boy_propellant = new ItemBoy("boy_propellant").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item boy_bullet = new ItemBoy("boy_bullet").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item boy_target = new ItemBoy("boy_target").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item boy_shielding = new ItemBoy("boy_shielding").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	
	public static final Item man_core = new ItemManMike("man_core");
	public static final Item man_explosive = new ItemBase("man_explosive").setCreativeTab(MainRegistry.nukeTab);
	public static final Item man_explosive8 = new ItemManMike("man_explosive8").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item man_igniter = new ItemMan("man_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	
	public static final Item mike_core = new ItemMike("mike_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item mike_deut = new ItemMike("mike_deut").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setContainerItem(ModItems.tank_steel);
	public static final Item mike_cooling_unit = new ItemMike("mike_cooling_unit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	
	public static final Item tsar_core = new ItemTsar("tsar_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	
	public static final Item fleija_igniter = new ItemFleija("fleija_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item fleija_propellant = new ItemFleija("fleija_propellant").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item fleija_core = new ItemFleija("fleija_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	
	public static final Item solinium_core = new ItemSolinium("solinium_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item solinium_igniter = new ItemSolinium("solinium_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item solinium_propellant = new ItemSolinium("solinium_propellant").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	
	public static final Item n2_charge = new ItemN2("n2_charge").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	
	public static final Item custom_tnt = new ItemCustomLore("custom_tnt").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item custom_nuke = new ItemCustomLore("custom_nuke").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item custom_hydro = new ItemCustomLore("custom_hydro").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item custom_amat = new ItemCustomLore("custom_amat").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item custom_dirty = new ItemCustomLore("custom_dirty").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item custom_schrab = new ItemCustomLore("custom_schrab").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item custom_fall = new ItemCustomLore("custom_fall").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	
	//Kits
	public static final Item grenade_kit = new ItemStarterKit("grenade_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab);
	public static final Item gadget_kit = new ItemStarterKit("gadget_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item boy_kit = new ItemStarterKit("boy_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item man_kit = new ItemStarterKit("man_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item mike_kit = new ItemStarterKit("mike_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item tsar_kit = new ItemStarterKit("tsar_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item prototype_kit = new ItemStarterKit("prototype_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item fleija_kit = new ItemStarterKit("fleija_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item solinium_kit = new ItemStarterKit("solinium_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item multi_kit = new ItemStarterKit("multi_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item custom_kit = new ItemStarterKit("custom_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item missile_kit = new ItemStarterKit("missile_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item t45_kit = new ItemStarterKit("t45_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	
	
	public static final Item hazmat_kit = new ItemStarterKit("hazmat_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	public static final Item hazmat_red_kit = new ItemStarterKit("hazmat_red_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	public static final Item hazmat_grey_kit = new ItemStarterKit("hazmat_grey_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	
	public static final Item nuke_starter_kit = new ItemStarterKit("nuke_starter_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	public static final Item nuke_advanced_kit = new ItemStarterKit("nuke_advanced_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	
	public static final Item loot_10 = new ItemLootCrate("loot_10").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item loot_15 = new ItemLootCrate("loot_15").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item loot_misc = new ItemLootCrate("loot_misc").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	
	//Satellites
	public static final Item sat_mapper = new ItemSatChip("sat_mapper").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item sat_scanner = new ItemSatChip("sat_scanner").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item sat_radar = new ItemSatChip("sat_radar").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item sat_laser = new ItemSatChip("sat_laser").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item sat_foeq = new ItemSatChip("sat_foeq").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item sat_resonator = new ItemSatChip("sat_resonator").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item sat_miner = new ItemSatChip("sat_miner").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item sat_gerald = new ItemSatChip("sat_gerald").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item sat_chip = new ItemSatChip("sat_chip").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item sat_interface = new ItemSatInterface("sat_interface").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	
	//Missiles
	public static final Item designator = new ItemDesignator("designator").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item designator_range = new ItemDesignatorRange("designator_range").setFull3D().setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item designator_manual = new ItemDesignatorManual("designator_manual").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	
	public static final Item missile_assembly = new ItemBase("missile_assembly").setMaxStackSize(1).setCreativeTab(MainRegistry.partsTab);
	public static final Item missile_generic = new ItemBase("missile_generic").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_incendiary = new ItemBase("missile_incendiary").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_cluster = new ItemBase("missile_cluster").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_buster = new ItemBase("missile_buster").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_strong = new ItemBase("missile_strong").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_incendiary_strong = new ItemBase("missile_incendiary_strong").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_cluster_strong = new ItemBase("missile_cluster_strong").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_buster_strong = new ItemBase("missile_buster_strong").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_emp_strong = new ItemBase("missile_emp_strong").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_burst = new ItemBase("missile_burst").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_inferno = new ItemBase("missile_inferno").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_rain = new ItemBase("missile_rain").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_drill = new ItemBase("missile_drill").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_nuclear = new ItemBase("missile_nuclear").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_nuclear_cluster = new ItemBase("missile_nuclear_cluster").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_endo = new ItemBase("missile_endo").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_exo = new ItemBase("missile_exo").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_doomsday = new ItemBase("missile_doomsday").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_taint = new ItemBase("missile_taint").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_micro = new ItemBase("missile_micro").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_bhole = new ItemBase("missile_bhole").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_schrabidium = new ItemBase("missile_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_emp = new ItemBase("missile_emp").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_anti_ballistic = new ItemBase("missile_anti_ballistic").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_carrier = new ItemBase("missile_carrier").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	
	public static final Item warhead_generic_small = new ItemBase("warhead_generic_small").setCreativeTab(MainRegistry.partsTab);
	public static final Item warhead_incendiary_small = new ItemBase("warhead_incendiary_small").setCreativeTab(MainRegistry.partsTab);
	public static final Item warhead_cluster_small = new ItemBase("warhead_cluster_small").setCreativeTab(MainRegistry.partsTab);
	public static final Item warhead_buster_small = new ItemBase("warhead_buster_small").setCreativeTab(MainRegistry.partsTab);
	public static final Item warhead_generic_medium = new ItemBase("warhead_generic_medium").setCreativeTab(MainRegistry.partsTab);
	public static final Item warhead_incendiary_medium = new ItemBase("warhead_incendiary_medium").setCreativeTab(MainRegistry.partsTab);
	public static final Item warhead_cluster_medium = new ItemBase("warhead_cluster_medium").setCreativeTab(MainRegistry.partsTab);
	public static final Item warhead_buster_medium = new ItemBase("warhead_buster_medium").setCreativeTab(MainRegistry.partsTab);
	public static final Item warhead_generic_large = new ItemBase("warhead_generic_large").setCreativeTab(MainRegistry.partsTab);
	public static final Item warhead_incendiary_large = new ItemBase("warhead_incendiary_large").setCreativeTab(MainRegistry.partsTab);
	public static final Item warhead_cluster_large = new ItemBase("warhead_cluster_large").setCreativeTab(MainRegistry.partsTab);
	public static final Item warhead_buster_large = new ItemBase("warhead_buster_large").setCreativeTab(MainRegistry.partsTab);
	public static final Item warhead_nuclear = new ItemBase("warhead_nuclear").setCreativeTab(MainRegistry.partsTab);
	public static final Item warhead_mirvlet = new ItemBase("warhead_mirvlet").setCreativeTab(null);
	public static final Item warhead_mirv = new ItemBase("warhead_mirv").setCreativeTab(MainRegistry.partsTab);
	public static final Item warhead_thermo_endo = new ItemBase("warhead_thermo_endo").setCreativeTab(MainRegistry.partsTab);
	public static final Item warhead_thermo_exo = new ItemBase("warhead_thermo_exo").setCreativeTab(MainRegistry.partsTab);
	public static final Item thruster_small = new ItemBase("thruster_small").setCreativeTab(MainRegistry.partsTab);
	public static final Item thruster_medium = new ItemBase("thruster_medium").setCreativeTab(MainRegistry.partsTab);
	public static final Item thruster_large = new ItemBase("thruster_large").setCreativeTab(MainRegistry.partsTab);
	
	public static final Item cap_aluminium = new ItemBase("cap_aluminium").setCreativeTab(MainRegistry.partsTab);
	public static final Item hull_small_steel = new ItemBase("hull_small_steel").setCreativeTab(MainRegistry.partsTab);
	public static final Item hull_small_aluminium = new ItemBase("hull_small_aluminium").setCreativeTab(MainRegistry.partsTab);
	public static final Item hull_big_steel = new ItemBase("hull_big_steel").setCreativeTab(MainRegistry.partsTab);
	public static final Item hull_big_aluminium = new ItemBase("hull_big_aluminium").setCreativeTab(MainRegistry.partsTab);
	public static final Item hull_big_titanium = new ItemBase("hull_big_titanium").setCreativeTab(MainRegistry.partsTab);
	public static final Item fins_flat = new ItemBase("fins_flat").setCreativeTab(MainRegistry.partsTab);
	public static final Item fins_small_steel = new ItemBase("fins_small_steel").setCreativeTab(MainRegistry.partsTab);
	public static final Item fins_big_steel = new ItemBase("fins_big_steel").setCreativeTab(MainRegistry.partsTab);
	public static final Item fins_tri_steel = new ItemBase("fins_tri_steel").setCreativeTab(MainRegistry.partsTab);
	public static final Item fins_quad_titanium = new ItemBase("fins_quad_titanium").setCreativeTab(MainRegistry.partsTab);
	public static final Item sphere_steel = new ItemBase("sphere_steel").setCreativeTab(MainRegistry.partsTab);
	public static final Item pedestal_steel = new ItemBase("pedestal_steel").setCreativeTab(MainRegistry.partsTab);
	public static final Item dysfunctional_reactor = new ItemBase("dysfunctional_reactor").setCreativeTab(MainRegistry.partsTab);
	public static final Item rotor_steel = new ItemBase("rotor_steel").setCreativeTab(MainRegistry.partsTab);
	public static final Item generator_steel = new ItemBase("generator_steel").setCreativeTab(MainRegistry.partsTab);
	
	public static final Item sat_head_mapper = new ItemBase("sat_head_mapper").setCreativeTab(MainRegistry.partsTab);
	public static final Item sat_head_scanner = new ItemBase("sat_head_scanner").setCreativeTab(MainRegistry.partsTab);
	public static final Item sat_head_radar = new ItemBase("sat_head_radar").setCreativeTab(MainRegistry.partsTab);
	public static final Item sat_head_laser = new ItemBase("sat_head_laser").setCreativeTab(MainRegistry.partsTab);
	public static final Item sat_head_resonator = new ItemBase("sat_head_resonator").setCreativeTab(MainRegistry.partsTab);

	public static final Item seg_10 = new ItemBase("seg_10").setCreativeTab(MainRegistry.partsTab);
	public static final Item seg_15 = new ItemBase("seg_15").setCreativeTab(MainRegistry.partsTab);
	public static final Item seg_20 = new ItemBase("seg_20").setCreativeTab(MainRegistry.partsTab);
	
	public static final Item fuel_tank_small = new ItemBase("fuel_tank_small").setCreativeTab(MainRegistry.partsTab);
	public static final Item fuel_tank_medium = new ItemBase("fuel_tank_medium").setCreativeTab(MainRegistry.partsTab);
	public static final Item fuel_tank_large = new ItemBase("fuel_tank_large").setCreativeTab(MainRegistry.partsTab);
	
	public static final Item mp_thruster_10_kerosene = new ItemMissile("mp_thruster_10_kerosene").makeThruster(FuelType.KEROSENE, 1F, 1.5F, PartSize.SIZE_10).setHealth(10F);
	//public static final Item mp_thruster_10_kerosene_tec = new ItemMissile("mp_thruster_10_kerosene_tec").makeThruster(FuelType.KEROSENE, 1F, 1.5F, PartSize.SIZE_10).setHealth(15F).setRarity(Rarity.COMMON);
	public static final Item mp_thruster_10_solid = new ItemMissile("mp_thruster_10_solid").makeThruster(FuelType.SOLID, 1F, 1.5F, PartSize.SIZE_10).setHealth(15F);
	public static final Item mp_thruster_10_xenon = new ItemMissile("mp_thruster_10_xenon").makeThruster(FuelType.XENON, 1F, 1.5F, PartSize.SIZE_10).setHealth(5F);
	public static final Item mp_thruster_15_kerosene = new ItemMissile("mp_thruster_15_kerosene").makeThruster(FuelType.KEROSENE, 1F, 7.5F, PartSize.SIZE_15).setHealth(15F);
	//public static final Item mp_thruster_15_kerosene_tec = new ItemMissile("mp_thruster_15_kerosene_tec").makeThruster(FuelType.KEROSENE, 1F, 7.5F, PartSize.SIZE_15).setHealth(20F).setRarity(Rarity.COMMON);
	public static final Item mp_thruster_15_kerosene_dual = new ItemMissile("mp_thruster_15_kerosene_dual").makeThruster(FuelType.KEROSENE, 1F, 2.5F, PartSize.SIZE_15).setHealth(15F);
	public static final Item mp_thruster_15_kerosene_triple = new ItemMissile("mp_thruster_15_kerosene_triple").makeThruster(FuelType.KEROSENE, 1F, 5F, PartSize.SIZE_15).setHealth(15F);
	public static final Item mp_thruster_15_solid = new ItemMissile("mp_thruster_15_solid").makeThruster(FuelType.SOLID, 1F, 5F, PartSize.SIZE_15).setHealth(20F);
	public static final Item mp_thruster_15_solid_hexdecuple = new ItemMissile("mp_thruster_15_solid_hexdecuple").makeThruster(FuelType.SOLID, 1F, 5F, PartSize.SIZE_15).setHealth(25F).setRarity(Rarity.UNCOMMON);
	public static final Item mp_thruster_15_hydrogen = new ItemMissile("mp_thruster_15_hydrogen").makeThruster(FuelType.HYDROGEN, 1F, 7.5F, PartSize.SIZE_15).setHealth(20F);
	public static final Item mp_thruster_15_hydrogen_dual = new ItemMissile("mp_thruster_15_hydrogen_dual").makeThruster(FuelType.HYDROGEN, 1F, 2.5F, PartSize.SIZE_15).setHealth(15F);
	public static final Item mp_thruster_15_balefire_short = new ItemMissile("mp_thruster_15_balefire_short").makeThruster(FuelType.BALEFIRE, 1F, 5F, PartSize.SIZE_15).setHealth(25F);
	public static final Item mp_thruster_15_balefire = new ItemMissile("mp_thruster_15_balefire").makeThruster(FuelType.BALEFIRE, 1F, 5F, PartSize.SIZE_15).setHealth(25F);
	public static final Item mp_thruster_15_balefire_large = new ItemMissile("mp_thruster_15_balefire_large").makeThruster(FuelType.BALEFIRE, 1F, 7.5F, PartSize.SIZE_15).setHealth(35F);
	public static final Item mp_thruster_15_balefire_large_rad = new ItemMissile("mp_thruster_15_balefire_large_rad").makeThruster(FuelType.BALEFIRE, 1F, 7.5F, PartSize.SIZE_15).setAuthor("The Master").setHealth(35F).setRarity(Rarity.UNCOMMON);
	public static final Item mp_thruster_20_kerosene = new ItemMissile("mp_thruster_20_kerosene").makeThruster(FuelType.KEROSENE, 1F, 100F, PartSize.SIZE_20).setHealth(30F);
	public static final Item mp_thruster_20_kerosene_dual = new ItemMissile("mp_thruster_20_kerosene_dual").makeThruster(FuelType.KEROSENE, 1F, 100F, PartSize.SIZE_20).setHealth(30F);
	public static final Item mp_thruster_20_kerosene_triple = new ItemMissile("mp_thruster_20_kerosene_triple").makeThruster(FuelType.KEROSENE, 1F, 100F, PartSize.SIZE_20).setHealth(30F);
	public static final Item mp_thruster_20_solid = new ItemMissile("mp_thruster_20_solid").makeThruster(FuelType.SOLID, 1F, 100F, PartSize.SIZE_20).setHealth(35F).setWittyText("It's basically just a big hole at the end of the fuel tank.");
	public static final Item mp_thruster_20_solid_multi = new ItemMissile("mp_thruster_20_solid_multi").makeThruster(FuelType.SOLID, 1F, 100F, PartSize.SIZE_20).setHealth(35F);
	public static final Item mp_thruster_20_solid_multier = new ItemMissile("mp_thruster_20_solid_multier").makeThruster(FuelType.SOLID, 1F, 100F, PartSize.SIZE_20).setHealth(35F).setWittyText("Did I miscount? Hope not.");

	public static final Item mp_stability_10_flat = new ItemMissile("mp_stability_10_flat").makeStability(0.5F, PartSize.SIZE_10).setHealth(10F);
	public static final Item mp_stability_10_cruise = new ItemMissile("mp_stability_10_cruise").makeStability(0.25F, PartSize.SIZE_10).setHealth(5F);
	public static final Item mp_stability_10_space = new ItemMissile("mp_stability_10_space").makeStability(0.35F, PartSize.SIZE_10).setHealth(5F).setRarity(Rarity.COMMON).setWittyText("Standing there alone, the ship is waiting / All systems are go, are you sure?");
	public static final Item mp_stability_15_flat = new ItemMissile("mp_stability_15_flat").makeStability(0.5F, PartSize.SIZE_15).setHealth(10F);
	public static final Item mp_stability_15_thin = new ItemMissile("mp_stability_15_thin").makeStability(0.35F, PartSize.SIZE_15).setHealth(5F);
	public static final Item mp_stability_15_soyuz = new ItemMissile("mp_stability_15_soyuz").makeStability(0.25F, PartSize.SIZE_15).setHealth(15F).setRarity(Rarity.COMMON).setWittyText("Союз!");
	//public static final Item mp_stability_20_flat = new ItemMissile("mp_s_20").makeStability(0.5F, PartSize.SIZE_20);

	public static final Item mp_fuselage_10_kerosene = new ItemMissile("mp_fuselage_10_kerosene").makeFuselage(FuelType.KEROSENE, 2500F, PartSize.SIZE_10, PartSize.SIZE_10).setAuthor("Hoboy").setHealth(20F);
	public static final Item mp_fuselage_10_kerosene_camo = ((ItemMissile) mp_fuselage_10_kerosene).copy("mp_fuselage_10_kerosene_camo").setRarity(Rarity.COMMON).setTitle("Camo");
	public static final Item mp_fuselage_10_kerosene_desert = ((ItemMissile) mp_fuselage_10_kerosene).copy("mp_fuselage_10_kerosene_desert").setRarity(Rarity.COMMON).setTitle("Desert Camo");
	public static final Item mp_fuselage_10_kerosene_sky = ((ItemMissile) mp_fuselage_10_kerosene).copy("mp_fuselage_10_kerosene_sky").setRarity(Rarity.COMMON).setTitle("Sky Camo");
	public static final Item mp_fuselage_10_kerosene_flames = ((ItemMissile) mp_fuselage_10_kerosene).copy("mp_fuselage_10_kerosene_flames").setRarity(Rarity.UNCOMMON).setTitle("Sick Flames");
	public static final Item mp_fuselage_10_kerosene_insulation = ((ItemMissile) mp_fuselage_10_kerosene).copy("mp_fuselage_10_kerosene_insulation").setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(25F);
	public static final Item mp_fuselage_10_kerosene_sleek = ((ItemMissile) mp_fuselage_10_kerosene).copy("mp_fuselage_10_kerosene_sleek").setRarity(Rarity.RARE).setTitle("IF-R&D").setHealth(35F);
	public static final Item mp_fuselage_10_kerosene_metal = ((ItemMissile) mp_fuselage_10_kerosene).copy("mp_fuselage_10_kerosene_metal").setRarity(Rarity.UNCOMMON).setTitle("Bolted Metal").setHealth(30F).setAuthor("Hoboy");
	public static final Item mp_fuselage_10_kerosene_taint = ((ItemMissile) mp_fuselage_10_kerosene).copy("mp_fuselage_10_kerosene_taint").setRarity(Rarity.UNCOMMON).setAuthor("Sam").setTitle("Tainted");

	public static final Item mp_fuselage_10_solid = new ItemMissile("mp_fuselage_10_solid").makeFuselage(FuelType.SOLID, 2500F, PartSize.SIZE_10, PartSize.SIZE_10).setHealth(25F);
	public static final Item mp_fuselage_10_solid_flames = ((ItemMissile) mp_fuselage_10_solid).copy("mp_fuselage_10_solid_flames").setRarity(Rarity.UNCOMMON).setTitle("Sick Flames");
	public static final Item mp_fuselage_10_solid_insulation = ((ItemMissile) mp_fuselage_10_solid).copy("mp_fuselage_10_solid_insulation").setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(30F);
	public static final Item mp_fuselage_10_solid_sleek = ((ItemMissile) mp_fuselage_10_solid).copy("mp_fuselage_10_solid_sleek").setRarity(Rarity.RARE).setTitle("IF-R&D").setHealth(35F);
	public static final Item mp_fuselage_10_solid_soviet_glory = ((ItemMissile) mp_fuselage_10_solid).copy("mp_fuselage_10_solid_soviet_glory").setRarity(Rarity.EPIC).setAuthor("Hoboy").setHealth(35F).setTitle("Soviet Glory");
	public static final Item mp_fuselage_10_solid_cathedral = ((ItemMissile) mp_fuselage_10_solid).copy("mp_fuselage_10_solid_cathedral").setRarity(Rarity.RARE).setAuthor("Satan").setTitle("Unholy Cathedral").setWittyText("Quakeesque!");
	public static final Item mp_fuselage_10_solid_moonlit = ((ItemMissile) mp_fuselage_10_solid).copy("mp_fuselage_10_solid_moonlit").setRarity(Rarity.UNCOMMON).setAuthor("The Master & Hoboy").setTitle("Moonlit");
	public static final Item mp_fuselage_10_solid_battery = ((ItemMissile) mp_fuselage_10_solid).copy("mp_fuselage_10_solid_battery").setRarity(Rarity.UNCOMMON).setAuthor("wolfmonster222").setHealth(30F).setTitle("Ecstatic").setWittyText("I got caught eating batteries again :(");
	public static final Item mp_fuselage_10_solid_duracell = ((ItemMissile) mp_fuselage_10_solid).copy("mp_fuselage_10_solid_duracell").setRarity(Rarity.RARE).setAuthor("Hoboy").setTitle("Duracell").setHealth(30F).setWittyText("The crunchiest battery on the market!");

	public static final Item mp_fuselage_10_xenon = new ItemMissile("mp_fuselage_10_xenon").makeFuselage(FuelType.XENON, 5000F, PartSize.SIZE_10, PartSize.SIZE_10).setHealth(20F);
	public static final Item mp_fuselage_10_xenon_bhole = ((ItemMissile) mp_fuselage_10_xenon).copy("mp_fuselage_10_xenon_bhole").setRarity(Rarity.RARE).setAuthor("Sten89").setTitle("Morceus-1457");

	public static final Item mp_fuselage_10_long_kerosene = new ItemMissile("mp_fuselage_10_long_kerosene").makeFuselage(FuelType.KEROSENE, 5000F, PartSize.SIZE_10, PartSize.SIZE_10).setAuthor("Hoboy").setHealth(30F);
	public static final Item mp_fuselage_10_long_kerosene_camo = ((ItemMissile) mp_fuselage_10_long_kerosene).copy("mp_fuselage_10_long_kerosene_camo").setRarity(Rarity.COMMON).setTitle("Camo");
	public static final Item mp_fuselage_10_long_kerosene_desert = ((ItemMissile) mp_fuselage_10_long_kerosene).copy("mp_fuselage_10_long_kerosene_desert").setRarity(Rarity.COMMON).setTitle("Desert Camo");
	public static final Item mp_fuselage_10_long_kerosene_sky = ((ItemMissile) mp_fuselage_10_long_kerosene).copy("mp_fuselage_10_long_kerosene_sky").setRarity(Rarity.COMMON).setTitle("Sky Camo");
	public static final Item mp_fuselage_10_long_kerosene_flames = ((ItemMissile) mp_fuselage_10_long_kerosene).copy("mp_fuselage_10_long_kerosene_flames").setRarity(Rarity.UNCOMMON).setTitle("Sick Flames");
	public static final Item mp_fuselage_10_long_kerosene_insulation = ((ItemMissile) mp_fuselage_10_long_kerosene).copy("mp_fuselage_10_long_kerosene_insulation").setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(35F);
	public static final Item mp_fuselage_10_long_kerosene_sleek = ((ItemMissile) mp_fuselage_10_long_kerosene).copy("mp_fuselage_10_long_kerosene_sleek").setRarity(Rarity.RARE).setTitle("IF-R&D").setHealth(40F);
	public static final Item mp_fuselage_10_long_kerosene_metal = ((ItemMissile) mp_fuselage_10_long_kerosene).copy("mp_fuselage_10_long_kerosene_metal").setRarity(Rarity.UNCOMMON).setAuthor("Hoboy").setHealth(35F);
	public static final Item mp_fuselage_10_long_kerosene_dash = ((ItemMissile) mp_fuselage_10_long_kerosene).copy("mp_fuselage_10_long_kerosene_dash").setRarity(Rarity.EPIC).setAuthor("Sam").setTitle("Dash").setWittyText("I wash my hands of it.").setCreativeTab(null);
	public static final Item mp_fuselage_10_long_kerosene_taint = ((ItemMissile) mp_fuselage_10_long_kerosene).copy("mp_fuselage_10_long_kerosene_taint").setRarity(Rarity.UNCOMMON).setAuthor("Sam").setTitle("Tainted");
	public static final Item mp_fuselage_10_long_kerosene_vap = ((ItemMissile) mp_fuselage_10_long_kerosene).copy("mp_fuselage_10_long_kerosene_vap").setRarity(Rarity.EPIC).setAuthor("VT-6/24").setTitle("Minty Contrail").setWittyText("Upper rivet!");

	public static final Item mp_fuselage_10_long_solid = new ItemMissile("mp_fuselage_10_long_solid").makeFuselage(FuelType.SOLID, 5000F, PartSize.SIZE_10, PartSize.SIZE_10).setHealth(35F);
	public static final Item mp_fuselage_10_long_solid_flames = ((ItemMissile) mp_fuselage_10_long_solid).copy("mp_fuselage_10_long_solid_flames").setRarity(Rarity.UNCOMMON).setTitle("Sick Flames");
	public static final Item mp_fuselage_10_long_solid_insulation = ((ItemMissile) mp_fuselage_10_long_solid).copy("mp_fuselage_10_long_solid_insulation").setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(40F);
	public static final Item mp_fuselage_10_long_solid_sleek = ((ItemMissile) mp_fuselage_10_long_solid).copy("mp_fuselage_10_long_solid_sleek").setRarity(Rarity.RARE).setTitle("IF-R&D").setHealth(45F);
	public static final Item mp_fuselage_10_long_solid_soviet_glory = ((ItemMissile) mp_fuselage_10_long_solid).copy("mp_fuselage_10_long_solid_soviet_glory").setRarity(Rarity.EPIC).setAuthor("Hoboy").setHealth(45F).setTitle("Soviet Glory").setWittyText("Fully Automated Luxury Gay Space Communism!");
	public static final Item mp_fuselage_10_long_solid_bullet = ((ItemMissile) mp_fuselage_10_long_solid).copy("mp_fuselage_10_long_solid_bullet").setRarity(Rarity.COMMON).setAuthor("Sam").setTitle("Bullet Bill");
	public static final Item mp_fuselage_10_long_solid_silvermoonlight = ((ItemMissile) mp_fuselage_10_long_solid).copy("mp_fuselage_10_long_solid_silvermoonlight").setRarity(Rarity.UNCOMMON).setAuthor("The Master").setTitle("Silver Moonlight");

	public static final Item mp_fuselage_10_15_kerosene = new ItemMissile("mp_fuselage_10_15_kerosene").makeFuselage(FuelType.KEROSENE, 10000F, PartSize.SIZE_10, PartSize.SIZE_15).setHealth(40F);
	public static final Item mp_fuselage_10_15_solid = new ItemMissile("mp_fuselage_10_15_solid").makeFuselage(FuelType.SOLID, 10000F, PartSize.SIZE_10, PartSize.SIZE_15).setHealth(40F);
	public static final Item mp_fuselage_10_15_hydrogen = new ItemMissile("mp_fuselage_10_15_hydrogen").makeFuselage(FuelType.HYDROGEN, 10000F, PartSize.SIZE_10, PartSize.SIZE_15).setHealth(40F);
	public static final Item mp_fuselage_10_15_balefire = new ItemMissile("mp_fuselage_10_15_balefire").makeFuselage(FuelType.BALEFIRE, 10000F, PartSize.SIZE_10, PartSize.SIZE_15).setHealth(40F);

	public static final Item mp_fuselage_15_kerosene = new ItemMissile("mp_fuselage_15_kerosene").makeFuselage(FuelType.KEROSENE, 15000F, PartSize.SIZE_15, PartSize.SIZE_15).setAuthor("Hoboy").setHealth(50F);
	public static final Item mp_fuselage_15_kerosene_camo = ((ItemMissile) mp_fuselage_15_kerosene).copy("mp_fuselage_15_kerosene_camo").setRarity(Rarity.COMMON).setTitle("Camo");
	public static final Item mp_fuselage_15_kerosene_desert = ((ItemMissile) mp_fuselage_15_kerosene).copy("mp_fuselage_15_kerosene_desert").setRarity(Rarity.COMMON).setTitle("Desert Camo");
	public static final Item mp_fuselage_15_kerosene_sky = ((ItemMissile) mp_fuselage_15_kerosene).copy("mp_fuselage_15_kerosene_sky").setRarity(Rarity.COMMON).setTitle("Sky Camo");
	public static final Item mp_fuselage_15_kerosene_insulation = ((ItemMissile) mp_fuselage_15_kerosene).copy("mp_fuselage_15_kerosene_insulation").setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(55F).setWittyText("Rest in spaghetti Columbia :(");
	public static final Item mp_fuselage_15_kerosene_metal = ((ItemMissile) mp_fuselage_15_kerosene).copy("mp_fuselage_15_kerosene_metal").setRarity(Rarity.UNCOMMON).setAuthor("Hoboy").setTitle("Bolted Metal").setHealth(60F).setWittyText("Metal frame with metal plating reinforced with bolted metal sheets and metal.");
	public static final Item mp_fuselage_15_kerosene_decorated = ((ItemMissile) mp_fuselage_15_kerosene).copy("mp_fuselage_15_kerosene_decorated").setRarity(Rarity.UNCOMMON).setAuthor("Hoboy").setTitle("Decorated").setHealth(60F);
	public static final Item mp_fuselage_15_kerosene_steampunk = ((ItemMissile) mp_fuselage_15_kerosene).copy("mp_fuselage_15_kerosene_steampunk").setRarity(Rarity.RARE).setAuthor("Hoboy").setTitle("Steampunk").setHealth(60F);
	public static final Item mp_fuselage_15_kerosene_polite = ((ItemMissile) mp_fuselage_15_kerosene).copy("mp_fuselage_15_kerosene_polite").setRarity(Rarity.LEGENDARY).setAuthor("Hoboy").setTitle("Polite").setHealth(60F);
	public static final Item mp_fuselage_15_kerosene_blackjack = ((ItemMissile) mp_fuselage_15_kerosene).copy("mp_fuselage_15_kerosene_blackjack").setRarity(Rarity.LEGENDARY).setTitle("Queen Whiskey").setHealth(100F);
	public static final Item mp_fuselage_15_kerosene_lambda = ((ItemMissile) mp_fuselage_15_kerosene).copy("mp_fuselage_15_kerosene_lambda").setRarity(Rarity.RARE).setAuthor("VT-6/24").setTitle("Lambda Complex").setHealth(75F).setWittyText("MAGNIFICENT MICROWAVE CASSEROLE");
	public static final Item mp_fuselage_15_kerosene_minuteman = ((ItemMissile) mp_fuselage_15_kerosene).copy("mp_fuselage_15_kerosene_minuteman").setRarity(Rarity.UNCOMMON).setAuthor("Spexta").setTitle("MX 1702");
	public static final Item mp_fuselage_15_kerosene_pip = ((ItemMissile) mp_fuselage_15_kerosene).copy("mp_fuselage_15_kerosene_pip").setRarity(Rarity.EPIC).setAuthor("The Doctor").setTitle("LittlePip").setWittyText("31!").setCreativeTab(null);
	public static final Item mp_fuselage_15_kerosene_taint = ((ItemMissile) mp_fuselage_15_kerosene).copy("mp_fuselage_15_kerosene_taint").setRarity(Rarity.UNCOMMON).setAuthor("Sam").setTitle("Tainted").setWittyText("DUN-DUN!");
	public static final Item mp_fuselage_15_kerosene_yuck = ((ItemMissile) mp_fuselage_15_kerosene).copy("mp_fuselage_15_kerosene_yuck").setRarity(Rarity.EPIC).setAuthor("Hoboy").setTitle("Flesh").setWittyText("Note: Never clean DNA vials with your own spit.").setHealth(60F);

	public static final Item mp_fuselage_15_solid = new ItemMissile("mp_fuselage_15_solid").makeFuselage(FuelType.SOLID, 15000F, PartSize.SIZE_15, PartSize.SIZE_15).setHealth(60F).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item mp_fuselage_15_solid_insulation = ((ItemMissile) mp_fuselage_15_solid).copy("mp_fuselage_15_solid_insulation").setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(65F);
	public static final Item mp_fuselage_15_solid_desh = ((ItemMissile) mp_fuselage_15_solid).copy("mp_fuselage_15_solid_desh").setRarity(Rarity.RARE).setAuthor("Hoboy").setTitle("Desh Plating").setHealth(80F);
	public static final Item mp_fuselage_15_solid_soviet_glory = ((ItemMissile) mp_fuselage_15_solid).copy("mp_fuselage_15_solid_soviet_glory").setRarity(Rarity.RARE).setAuthor("Hoboy").setTitle("Soviet Glory").setHealth(70F);
	public static final Item mp_fuselage_15_solid_soviet_stank = ((ItemMissile) mp_fuselage_15_solid).copy("mp_fuselage_15_solid_soviet_stank").setRarity(Rarity.EPIC).setAuthor("Hoboy").setTitle("Soviet Stank").setHealth(15F).setWittyText("Aged like a fine wine! Well, almost.");
	public static final Item mp_fuselage_15_solid_faust = ((ItemMissile) mp_fuselage_15_solid).copy("mp_fuselage_15_solid_faust").setRarity(Rarity.LEGENDARY).setAuthor("Dr.Nostalgia").setTitle("Mighty Lauren").setHealth(250F).setWittyText("Welcome to Subway, may I take your order?");
	public static final Item mp_fuselage_15_solid_silvermoonlight = ((ItemMissile) mp_fuselage_15_solid).copy("mp_fuselage_15_solid_silvermoonlight").setRarity(Rarity.UNCOMMON).setAuthor("The Master").setTitle("Silver Moonlight");
	public static final Item mp_fuselage_15_solid_snowy = ((ItemMissile) mp_fuselage_15_solid).copy("mp_fuselage_15_solid_snowy").setRarity(Rarity.UNCOMMON).setAuthor("Dr.Nostalgia").setTitle("Chilly Day");
	public static final Item mp_fuselage_15_solid_panorama = ((ItemMissile) mp_fuselage_15_solid).copy("mp_fuselage_15_solid_panorama").setRarity(Rarity.RARE).setAuthor("Hoboy").setTitle("Panorama");
	public static final Item mp_fuselage_15_solid_roses = ((ItemMissile) mp_fuselage_15_solid).copy("mp_fuselage_15_solid_roses").setRarity(Rarity.UNCOMMON).setAuthor("Hoboy").setTitle("Bed of roses");

	public static final Item mp_fuselage_15_hydrogen = new ItemMissile("mp_fuselage_15_hydrogen").makeFuselage(FuelType.HYDROGEN, 15000F, PartSize.SIZE_15, PartSize.SIZE_15).setHealth(50F).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item mp_fuselage_15_hydrogen_cathedral = ((ItemMissile) mp_fuselage_15_hydrogen).copy("mp_fuselage_15_hydrogen_cathedral").setRarity(Rarity.UNCOMMON).setAuthor("Satan").setTitle("Unholy Cathedral");

	public static final Item mp_fuselage_15_balefire = new ItemMissile("mp_fuselage_15_balefire").makeFuselage(FuelType.BALEFIRE, 15000F, PartSize.SIZE_15, PartSize.SIZE_15).setHealth(75F).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);

	public static final Item mp_fuselage_15_20_kerosene = new ItemMissile("mp_fuselage_15_20_kerosene").makeFuselage(FuelType.KEROSENE, 20000, PartSize.SIZE_15, PartSize.SIZE_20).setAuthor("Hoboy").setHealth(70F).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item mp_fuselage_15_20_kerosene_magnusson = ((ItemMissile) mp_fuselage_15_20_kerosene).copy("mp_fuselage_15_20_kerosene_magnusson").setRarity(Rarity.RARE).setAuthor("VT-6/24").setTitle("White Forest Rocket").setWittyText("And get your cranio-conjugal parasite away from my nose cone!");
	public static final Item mp_fuselage_15_20_solid = new ItemMissile("mp_fuselage_15_20_solid").makeFuselage(FuelType.SOLID, 20000, PartSize.SIZE_15, PartSize.SIZE_20).setHealth(70F).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);

	//public static final Item mp_fuselage_20_kerosene = new ItemMissile("mp_f_20").makeFuselage(FuelType.KEROSENE, 1000F, PartSize.SIZE_20, PartSize.SIZE_20).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);

	public static final Item mp_warhead_10_he = new ItemMissile("mp_warhead_10_he").makeWarhead(WarheadType.HE, 15F, 1.5F, PartSize.SIZE_10).setHealth(5F).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item mp_warhead_10_incendiary = new ItemMissile("mp_warhead_10_incendiary").makeWarhead(WarheadType.INC, 15F, 1.5F, PartSize.SIZE_10).setHealth(5F).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item mp_warhead_10_buster = new ItemMissile("mp_warhead_10_buster").makeWarhead(WarheadType.BUSTER, 5F, 1.5F, PartSize.SIZE_10).setHealth(5F).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item mp_warhead_10_nuclear = new ItemMissile("mp_warhead_10_nuclear").makeWarhead(WarheadType.NUCLEAR, 35F, 1.5F, PartSize.SIZE_10).setTitle("Tater Tot").setHealth(10F).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item mp_warhead_10_nuclear_large = new ItemMissile("mp_warhead_10_nuclear_large").makeWarhead(WarheadType.NUCLEAR, 75F, 2.5F, PartSize.SIZE_10).setTitle("Chernobyl Boris").setHealth(15F).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item mp_warhead_10_taint = new ItemMissile("mp_warhead_10_taint").makeWarhead(WarheadType.TAINT, 15F, 1.5F, PartSize.SIZE_10).setHealth(20F).setRarity(Rarity.UNCOMMON).setWittyText("Eat my taint! Bureaucracy is dead and we killed it!").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item mp_warhead_10_cloud = new ItemMissile("mp_warhead_10_cloud").makeWarhead(WarheadType.CLOUD, 15F, 1.5F, PartSize.SIZE_10).setHealth(20F).setRarity(Rarity.RARE).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item mp_warhead_15_he = new ItemMissile("mp_warhead_15_he").makeWarhead(WarheadType.HE, 50F, 2.5F, PartSize.SIZE_15).setHealth(10F).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item mp_warhead_15_incendiary = new ItemMissile("mp_warhead_15_incendiary").makeWarhead(WarheadType.INC, 35F, 2.5F, PartSize.SIZE_15).setHealth(10F).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item mp_warhead_15_nuclear = new ItemMissile("mp_warhead_15_nuclear").makeWarhead(WarheadType.NUCLEAR, 125F, 5F, PartSize.SIZE_15).setTitle("Auntie Bertha").setHealth(15F).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item mp_warhead_15_nuclear_shark = ((ItemMissile) mp_warhead_15_nuclear).copy("mp_warhead_15_nuclear_shark").setRarity(Rarity.UNCOMMON).setTitle("Discount Bullet Bill").setWittyText("Nose art on a cannon bullet? Who does that?");
	public static final Item mp_warhead_15_boxcar = new ItemMissile("mp_warhead_15_boxcar").makeWarhead(WarheadType.TX, 250F, 7.5F, PartSize.SIZE_15).setWittyText("?!?!").setHealth(35F).setRarity(Rarity.LEGENDARY).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item mp_warhead_15_n2 = new ItemMissile("mp_warhead_15_n2").makeWarhead(WarheadType.N2, 100F, 5F, PartSize.SIZE_15).setWittyText("[screams geometrically]").setHealth(20F).setRarity(Rarity.RARE).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item mp_warhead_15_balefire = new ItemMissile("mp_warhead_15_balefire").makeWarhead(WarheadType.BALEFIRE, 100F, 7.5F, PartSize.SIZE_15).setRarity(Rarity.LEGENDARY).setAuthor("VT-6/24").setHealth(15F).setWittyText("Hightower, never forgetti.").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	//public static final Item mp_warhead_20_he = new ItemMissile("mp_w_20").makeWarhead(WarheadType.HE, 15F, 1F, PartSize.SIZE_20).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);

	public static final Item mp_chip_1 = new ItemMissile("mp_c_1").makeChip(0.1F).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item mp_chip_2 = new ItemMissile("mp_c_2").makeChip(0.05F).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item mp_chip_3 = new ItemMissile("mp_c_3").makeChip(0.01F).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item mp_chip_4 = new ItemMissile("mp_c_4").makeChip(0.005F).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item mp_chip_5 = new ItemMissile("mp_c_5").makeChip(0.0F).setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);

	public static final Item missile_skin_camo = new ItemCustomLore("missile_skin_camo").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_skin_desert = new ItemCustomLore("missile_skin_desert").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_skin_flames = new ItemCustomLore("missile_skin_flames").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_skin_manly_pink = new ItemCustomLore("missile_skin_manly_pink").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_skin_orange_insulation = new ItemCustomLore("missile_skin_orange_insulation").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_skin_sleek = new ItemCustomLore("missile_skin_sleek").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_skin_soviet_glory = new ItemCustomLore("missile_skin_soviet_glory").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_skin_soviet_stank = new ItemCustomLore("missile_skin_soviet_stank").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	public static final Item missile_skin_metal = new ItemCustomLore("missile_skin_metal").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab);
	
	public static final Item missile_custom = new ItemCustomMissile("missile_custom").setMaxStackSize(1).setCreativeTab(null);
	
	//Door items
	public static final Item door_metal = new ItemModDoor("door_metal").setCreativeTab(MainRegistry.blockTab);
	public static final Item door_office = new ItemModDoor("door_office").setCreativeTab(MainRegistry.blockTab);
	public static final Item door_bunker = new ItemModDoor("door_bunker").setCreativeTab(MainRegistry.blockTab);
	
	//Music
	public static final Item record_lc = new ItemModRecord("lc", HBMSoundHandler.lambdaCore, "record_lc").setCreativeTab(CreativeTabs.MISC);
	public static final Item record_ss = new ItemModRecord("ss", HBMSoundHandler.sectorSweep, "record_ss").setCreativeTab(CreativeTabs.MISC);
	public static final Item record_vc = new ItemModRecord("vc", HBMSoundHandler.vortalCombat, "record_vc").setCreativeTab(CreativeTabs.MISC);
	
	//Weird items
	public static final Item flame_pony = new ItemCustomLore("flame_pony").setCreativeTab(MainRegistry.partsTab);
	public static final Item flame_conspiracy = new ItemCustomLore("flame_conspiracy").setCreativeTab(MainRegistry.partsTab);
	public static final Item flame_politics = new ItemCustomLore("flame_politics").setCreativeTab(MainRegistry.partsTab);
	public static final Item flame_opinion = new ItemCustomLore("flame_opinion").setCreativeTab(MainRegistry.partsTab);
	public static final Item polaroid = new ItemPolaroid("polaroid").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	public static final Item glitch = new ItemGlitch("glitch").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab);
	public static final Item burnt_bark = new ItemCustomLore("burnt_bark").setCreativeTab(null);
	public static final Item letter = new ItemStarterKit("letter").setCreativeTab(MainRegistry.consumableTab);
	public static final Item book_secret = new ItemCustomLore("book_secret").setCreativeTab(MainRegistry.polaroidID == 11 ? MainRegistry.consumableTab : null);
	public static final Item book_of_ = new ItemCustomLore("book_of_").setCreativeTab(null);
	public static final Item crystal_horn = new ItemCustomLore("crystal_horn").setCreativeTab(MainRegistry.partsTab);
	public static final Item crystal_charred = new ItemCustomLore("crystal_charred").setCreativeTab(MainRegistry.partsTab);
	public static final Item watch = new ItemCustomLore("watch").setCreativeTab(null).setMaxStackSize(1);
	public static final Item apple_euphemium = new ItemAppleEuphemium(20, 100, false, "apple_euphemium").setMaxStackSize(1).setCreativeTab(null);
	
	public static final Item wand = new ItemWand("wand_k").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setFull3D();
	public static final Item wand_s = new ItemWandS("wand_s").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setFull3D();
	public static final Item wand_d = new ItemWandD("wand_d").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setFull3D();
	public static final Item analyzer = new ItemAnalyzer("analyzer").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setFull3D();
	public static final Item defuser = new ItemBase("defuser").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab);
	
	//Chopper
	public static final Item chopper_head = new ItemBase("chopper_head").setCreativeTab(MainRegistry.partsTab);
	public static final Item chopper_gun = new ItemBase("chopper_gun").setCreativeTab(MainRegistry.partsTab);
	public static final Item chopper_torso = new ItemBase("chopper_torso").setCreativeTab(MainRegistry.partsTab);
	public static final Item chopper_tail = new ItemBase("chopper_tail").setCreativeTab(MainRegistry.partsTab);
	public static final Item chopper_wing = new ItemBase("chopper_wing").setCreativeTab(MainRegistry.partsTab);
	public static final Item chopper_blades = new ItemBase("chopper_blades").setCreativeTab(MainRegistry.partsTab);
	public static final Item combine_scrap = new ItemBase("combine_scrap").setCreativeTab(MainRegistry.partsTab);
	
	public static final Item shimmer_head = new ItemBase("shimmer_head").setCreativeTab(MainRegistry.partsTab);
	public static final Item shimmer_axe_head = new ItemBase("shimmer_axe_head").setCreativeTab(MainRegistry.partsTab);
	public static final Item shimmer_handle = new ItemBase("shimmer_handle").setCreativeTab(MainRegistry.partsTab);
	
	//Stuff
	public static final Item telepad = new ItemBase("telepad").setCreativeTab(MainRegistry.partsTab);
	public static final Item entanglement_kit = new ItemCustomLore("entanglement_kit").setCreativeTab(MainRegistry.partsTab);
	
	//Dummy texture items
	public static final Item bob_metalworks = new ItemBase("bob_metalworks").setCreativeTab(null);
	public static final Item bob_assembly = new ItemBase("bob_assembly").setCreativeTab(null);
	public static final Item bob_chemistry = new ItemBase("bob_chemistry").setCreativeTab(null);
	public static final Item bob_oil = new ItemBase("bob_oil").setCreativeTab(null);
	public static final Item bob_nuclear = new ItemBase("bob_nuclear").setCreativeTab(null);
	
	public static final Item smoke1 = new ItemBase("smoke1").setCreativeTab(null);
	public static final Item smoke2 = new ItemBase("smoke2").setCreativeTab(null);
	public static final Item smoke3 = new ItemBase("smoke3").setCreativeTab(null);
	public static final Item smoke4 = new ItemBase("smoke4").setCreativeTab(null);
	public static final Item smoke5 = new ItemBase("smoke5").setCreativeTab(null);
	public static final Item smoke6 = new ItemBase("smoke6").setCreativeTab(null);
	public static final Item smoke7 = new ItemBase("smoke7").setCreativeTab(null);
	public static final Item smoke8 = new ItemBase("smoke8").setCreativeTab(null);
	
	public static final Item b_smoke1 = new ItemBase("b_smoke1").setCreativeTab(null);
	public static final Item b_smoke2 = new ItemBase("b_smoke2").setCreativeTab(null);
	public static final Item b_smoke3 = new ItemBase("b_smoke3").setCreativeTab(null);
	public static final Item b_smoke4 = new ItemBase("b_smoke4").setCreativeTab(null);
	public static final Item b_smoke5 = new ItemBase("b_smoke5").setCreativeTab(null);
	public static final Item b_smoke6 = new ItemBase("b_smoke6").setCreativeTab(null);
	public static final Item b_smoke7 = new ItemBase("b_smoke7").setCreativeTab(null);
	public static final Item b_smoke8 = new ItemBase("b_smoke8").setCreativeTab(null);
	
	public static final Item d_smoke1 = new ItemBase("d_smoke1").setCreativeTab(null);
	public static final Item d_smoke2 = new ItemBase("d_smoke2").setCreativeTab(null);
	public static final Item d_smoke3 = new ItemBase("d_smoke3").setCreativeTab(null);
	public static final Item d_smoke4 = new ItemBase("d_smoke4").setCreativeTab(null);
	public static final Item d_smoke5 = new ItemBase("d_smoke5").setCreativeTab(null);
	public static final Item d_smoke6 = new ItemBase("d_smoke6").setCreativeTab(null);
	public static final Item d_smoke7 = new ItemBase("d_smoke7").setCreativeTab(null);
	public static final Item d_smoke8 = new ItemBase("d_smoke8").setCreativeTab(null);
	
	public static final Item cloud1 = new ItemBase("cloud1").setCreativeTab(null);
	public static final Item cloud2 = new ItemBase("cloud2").setCreativeTab(null);
	public static final Item cloud3 = new ItemBase("cloud3").setCreativeTab(null);
	public static final Item cloud4 = new ItemBase("cloud4").setCreativeTab(null);
	public static final Item cloud5 = new ItemBase("cloud5").setCreativeTab(null);
	public static final Item cloud6 = new ItemBase("cloud6").setCreativeTab(null);
	public static final Item cloud7 = new ItemBase("cloud7").setCreativeTab(null);
	public static final Item cloud8 = new ItemBase("cloud8").setCreativeTab(null);
	
	public static final Item gasflame1 = new ItemBase("gasflame1").setCreativeTab(null);
	public static final Item gasflame2 = new ItemBase("gasflame2").setCreativeTab(null);
	public static final Item gasflame3 = new ItemBase("gasflame3").setCreativeTab(null);
	public static final Item gasflame4 = new ItemBase("gasflame4").setCreativeTab(null);
	public static final Item gasflame5 = new ItemBase("gasflame5").setCreativeTab(null);
	public static final Item gasflame6 = new ItemBase("gasflame6").setCreativeTab(null);
	public static final Item gasflame7 = new ItemBase("gasflame7").setCreativeTab(null);
	public static final Item gasflame8 = new ItemBase("gasflame8").setCreativeTab(null);
	
	public static final Item flame_1 = new ItemBase("flame_1").setCreativeTab(null);
	public static final Item flame_2 = new ItemBase("flame_2").setCreativeTab(null);
	public static final Item flame_3 = new ItemBase("flame_3").setCreativeTab(null);
	public static final Item flame_4 = new ItemBase("flame_4").setCreativeTab(null);
	public static final Item flame_5 = new ItemBase("flame_5").setCreativeTab(null);
	public static final Item flame_6 = new ItemBase("flame_6").setCreativeTab(null);
	public static final Item flame_7 = new ItemBase("flame_7").setCreativeTab(null);
	public static final Item flame_8 = new ItemBase("flame_8").setCreativeTab(null);
	public static final Item flame_9 = new ItemBase("flame_9").setCreativeTab(null);
	public static final Item flame_10 = new ItemBase("flame_10").setCreativeTab(null);
	
	public static final Item orange1 = new ItemBase("orange1").setCreativeTab(null);
	public static final Item orange2 = new ItemBase("orange2").setCreativeTab(null);
	public static final Item orange3 = new ItemBase("orange3").setCreativeTab(null);
	public static final Item orange4 = new ItemBase("orange4").setCreativeTab(null);
	public static final Item orange5 = new ItemBase("orange5").setCreativeTab(null);
	public static final Item orange6 = new ItemBase("orange6").setCreativeTab(null);
	public static final Item orange7 = new ItemBase("orange7").setCreativeTab(null);
	public static final Item orange8 = new ItemBase("orange8").setCreativeTab(null);
	
	public static final Item pc1 = new ItemBase("pc1").setCreativeTab(null);
	public static final Item pc2 = new ItemBase("pc2").setCreativeTab(null);
	public static final Item pc3 = new ItemBase("pc3").setCreativeTab(null);
	public static final Item pc4 = new ItemBase("pc4").setCreativeTab(null);
	public static final Item pc5 = new ItemBase("pc5").setCreativeTab(null);
	public static final Item pc6 = new ItemBase("pc6").setCreativeTab(null);
	public static final Item pc7 = new ItemBase("pc7").setCreativeTab(null);
	public static final Item pc8 = new ItemBase("pc8").setCreativeTab(null);
	
	public static final Item chlorine1 = new ItemBase("chlorine1").setCreativeTab(null);
	public static final Item chlorine2 = new ItemBase("chlorine2").setCreativeTab(null);
	public static final Item chlorine3 = new ItemBase("chlorine3").setCreativeTab(null);
	public static final Item chlorine4 = new ItemBase("chlorine4").setCreativeTab(null);
	public static final Item chlorine5 = new ItemBase("chlorine5").setCreativeTab(null);
	public static final Item chlorine6 = new ItemBase("chlorine6").setCreativeTab(null);
	public static final Item chlorine7 = new ItemBase("chlorine7").setCreativeTab(null);
	public static final Item chlorine8 = new ItemBase("chlorine8").setCreativeTab(null);
	
	public static final Item ln2_1 = new ItemBase("ln2_1").setCreativeTab(null);
	public static final Item ln2_2 = new ItemBase("ln2_2").setCreativeTab(null);
	public static final Item ln2_3 = new ItemBase("ln2_3").setCreativeTab(null);
	public static final Item ln2_4 = new ItemBase("ln2_4").setCreativeTab(null);
	public static final Item ln2_5 = new ItemBase("ln2_5").setCreativeTab(null);
	public static final Item ln2_6 = new ItemBase("ln2_6").setCreativeTab(null);
	public static final Item ln2_7 = new ItemBase("ln2_7").setCreativeTab(null);
	public static final Item ln2_8 = new ItemBase("ln2_8").setCreativeTab(null);
	public static final Item ln2_9 = new ItemBase("ln2_9").setCreativeTab(null);
	public static final Item ln2_10 = new ItemBase("ln2_10").setCreativeTab(null);
	
	public static final Item gas1 = new ItemBase("gas1").setCreativeTab(null);
	public static final Item gas2 = new ItemBase("gas2").setCreativeTab(null);
	public static final Item gas3 = new ItemBase("gas3").setCreativeTab(null);
	public static final Item gas4 = new ItemBase("gas4").setCreativeTab(null);
	public static final Item gas5 = new ItemBase("gas5").setCreativeTab(null);
	public static final Item gas6 = new ItemBase("gas6").setCreativeTab(null);
	public static final Item gas7 = new ItemBase("gas7").setCreativeTab(null);
	public static final Item gas8 = new ItemBase("gas8").setCreativeTab(null);
	
	public static final Item spill1 = new ItemBase("spill1").setCreativeTab(null);
	public static final Item spill2 = new ItemBase("spill2").setCreativeTab(null);
	public static final Item spill3 = new ItemBase("spill3").setCreativeTab(null);
	public static final Item spill4 = new ItemBase("spill4").setCreativeTab(null);
	public static final Item spill5 = new ItemBase("spill5").setCreativeTab(null);
	public static final Item spill6 = new ItemBase("spill6").setCreativeTab(null);
	public static final Item spill7 = new ItemBase("spill7").setCreativeTab(null);
	public static final Item spill8 = new ItemBase("spill8").setCreativeTab(null);
	
	public static final Item nothing = new ItemBase("nothing").setCreativeTab(null);
	
	public static final Item discharge = new ItemBase("discharge").setCreativeTab(null);
	
	public static final Item mysteryshovel = new ItemMS("mysteryshovel").setFull3D().setMaxStackSize(1);
	public static final Item memory = new ItemBattery(Long.MAX_VALUE / 100L, 100000000000000L, 100000000000000L, "memory").setMaxStackSize(1).setCreativeTab(null);
	
	public static void preInit(){
		for(Item item : ALL_ITEMS){
			ForgeRegistries.ITEMS.register(item);
		}
		
		for(Block block : ModBlocks.ALL_BLOCKS){
			if(block == ModBlocks.block_scrap){
				ForgeRegistries.ITEMS.register(new ItemBlockScrap(block).setRegistryName(block.getRegistryName()));
			} else if(block instanceof BlockModDoor){
			} else {
				ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
			}
		}
	}
	
	public static void init(){
	}
	
	public static void postInit(){
	}

}
