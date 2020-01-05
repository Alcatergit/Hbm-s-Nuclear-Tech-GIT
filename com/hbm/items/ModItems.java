package com.hbm.items;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.items.ItemBlockScrap;
import com.hbm.items.bomb.ItemBoy;
import com.hbm.items.bomb.ItemFleija;
import com.hbm.items.bomb.ItemGadget;
import com.hbm.items.bomb.ItemMan;
import com.hbm.items.bomb.ItemManMike;
import com.hbm.items.bomb.ItemMike;
import com.hbm.items.bomb.ItemSolinium;
import com.hbm.items.bomb.ItemTsar;
import com.hbm.items.food.ItemEnergy;
import com.hbm.items.gear.ArmorEuphemium;
import com.hbm.items.gear.ArmorHazmat;
import com.hbm.items.gear.ArmorModel;
import com.hbm.items.gear.ArmorSchrabidium;
import com.hbm.items.gear.ArmorT45;
import com.hbm.items.gear.AxeSchrabidium;
import com.hbm.items.gear.HoeSchrabidium;
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
import com.hbm.items.special.ItemBattery;
import com.hbm.items.special.ItemBlades;
import com.hbm.items.special.ItemCell;
import com.hbm.items.special.ItemCustomLore;
import com.hbm.items.special.ItemFuel;
import com.hbm.items.special.ItemFuelRod;
import com.hbm.items.special.ItemPotatos;
import com.hbm.items.special.ItemRadioactive;
import com.hbm.items.special.ItemSyringe;
import com.hbm.items.special.WatzFuel;
import com.hbm.items.special.weapon.GunB92;
import com.hbm.items.tool.ItemAssemblyTemplate;
import com.hbm.items.tool.ItemChemistryIcon;
import com.hbm.items.tool.ItemChemistryTemplate;
import com.hbm.items.tool.ItemDetonator;
import com.hbm.items.tool.ItemFluidTank;
import com.hbm.items.tool.ItemLaserDetonator;
import com.hbm.items.tool.ItemFluidCanister;
import com.hbm.items.tool.ItemLeadBox;
import com.hbm.items.tool.ItemMultiDetonator;
import com.hbm.items.weapon.ItemAmmo;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModItems {
	
	public static final List<Item> ALL_ITEMS = new ArrayList<Item>();
	
	//Syringe
	public static final Item syringe_empty = new ItemBase("syringe_empty").setFull3D().setCreativeTab(MainRegistry.consumableTab);
	public static final Item syringe_awesome = new ItemSyringe("syringe_awesome").setCreativeTab(MainRegistry.consumableTab);
	public static final Item syringe_metal_empty = new ItemBase("syringe_metal_empty").setFull3D().setCreativeTab(MainRegistry.consumableTab);
	public static final Item syringe_metal_stimpak = new ItemSyringe("syringe_metal_stimpak").setFull3D().setCreativeTab(MainRegistry.consumableTab);
	
	//Energy items
	public static final Item factory_core_titanium = new ItemBattery(70400, 10, 0, "factory_core_titanium").setMaxStackSize(1);
	public static final Item factory_core_advanced = new ItemBattery(41600, 10, 0, "factory_core_advanced").setMaxStackSize(1);
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
	public static final Item battery_advanced = new ItemBattery(200, 5, 5, "battery_advanced").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_lithium = new ItemBattery(2500, 10, 10, "battery_lithium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_schrabidium = new ItemBattery(10000, 50, 50, "battery_schrabidium").setMaxStackSize(1);
	public static final Item battery_spark = new ItemBattery(1000000, 20000, 20000, "battery_spark").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_creative = new ItemBase("battery_creative").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);

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
	public static final Item battery_su_l = new ItemBattery(35, 0, 1, "battery_su_1").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_steam = new ItemBattery(600, 3, 60, "battery_steam").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	public static final Item battery_steam_large = new ItemBattery(1000, 5, 100, "battery_steam_large").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	
	//Fluid handling items
	public static final Item canister_generic = new ItemFluidCanister("canister_fuel", 4000).setCreativeTab(MainRegistry.controlTab);
	public static final Item canister_napalm = new ItemCustomLore("canister_napalm").setCreativeTab(MainRegistry.controlTab);
	
	public static final Item fluid_tank_full = new ItemFluidTank("fluid_tank_full", 4000).setCreativeTab(MainRegistry.controlTab);
	public static final Item fluid_barrel_full = new ItemFluidTank("fluid_barrel_full", 64000).setCreativeTab(MainRegistry.controlTab);
	public static final Item fluid_barrel_infinite = new ItemBase("fluid_barrel_infinite").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);
	//Weapons
	public static final Item detonator = new ItemDetonator("detonator").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab);
	public static final Item detonator_multi = new ItemMultiDetonator("detonator_multi").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab);
	public static final Item detonator_laser = new ItemLaserDetonator("detonator_laser").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab);
	
	public static final Item gun_fatman_ammo = new ItemBase("gun_fatman_ammo");
	
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
	
	public static final Item steel_helmet = new ModArmor(MainRegistry.enumArmorMaterialSteel, -1, EntityEquipmentSlot.HEAD, "steel_helmet").setMaxStackSize(1);
	public static final Item steel_plate = new ModArmor(MainRegistry.enumArmorMaterialSteel, -1, EntityEquipmentSlot.CHEST, "steel_plate").setMaxStackSize(1);
	public static final Item steel_legs = new ModArmor(MainRegistry.enumArmorMaterialSteel, -1, EntityEquipmentSlot.LEGS, "steel_legs").setMaxStackSize(1);
	public static final Item steel_boots = new ModArmor(MainRegistry.enumArmorMaterialSteel, -1, EntityEquipmentSlot.FEET, "steel_boots").setMaxStackSize(1);
	
	public static final Item t45_helmet = new ArmorT45(MainRegistry.enumArmorMaterialT45, -1, EntityEquipmentSlot.HEAD, "t45_helmet").setCreativeTab(CreativeTabs.COMBAT);
	public static final Item t45_plate = new ArmorT45(MainRegistry.enumArmorMaterialT45, -1, EntityEquipmentSlot.CHEST, "t45_plate").setCreativeTab(CreativeTabs.COMBAT);
	public static final Item t45_legs = new ArmorT45(MainRegistry.enumArmorMaterialT45, -1, EntityEquipmentSlot.LEGS, "t45_legs").setCreativeTab(CreativeTabs.COMBAT);
	public static final Item t45_boots = new ArmorT45(MainRegistry.enumArmorMaterialT45, -1, EntityEquipmentSlot.FEET, "t45_boots").setCreativeTab(CreativeTabs.COMBAT);
	
	public static final Item goggles = new ArmorModel(ArmorMaterial.IRON, -1, EntityEquipmentSlot.HEAD, "goggles").setMaxStackSize(1);
	public static final Item gas_mask = new ArmorModel(ArmorMaterial.IRON, -1, EntityEquipmentSlot.HEAD, "gas_mask").setMaxStackSize(1);
	public static final Item gas_mask_m65 = new ArmorModel(ArmorMaterial.IRON, -1, EntityEquipmentSlot.HEAD, "gas_mask_m65").setMaxStackSize(1);
	
	//Guns
	public static final Item gun_b92 = new GunB92("gun_b92").setCreativeTab(MainRegistry.weaponTab);
	
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
	public static final Item nugget_solinium = new ItemRadioactive("nugget_solinium").setCreativeTab(MainRegistry.partsTab);
	
	
	
	//Radioactive Materials
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
	public static final Item biomass = new ItemBase("biomass").setCreativeTab(MainRegistry.partsTab);
	public static final Item biomass_compressed = new ItemFuel("biomass_compressed", 800).setCreativeTab(MainRegistry.partsTab);
	public static final Item cordite = new ItemBase("cordite").setCreativeTab(MainRegistry.partsTab);
	public static final Item neutron_reflector = new ItemBase("neutron_reflector").setCreativeTab(MainRegistry.partsTab);
	
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
	public static final Item powder_cloud = new ItemBase("powder_cloud").setCreativeTab(MainRegistry.partsTab);
	
	//Misc
	public static final Item catalyst_clay = new ItemBase("catalyst_clay").setCreativeTab(MainRegistry.partsTab);
	
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
	public static final Item cell_empty = new ItemCell("cell_empty").setCreativeTab(MainRegistry.controlTab);
	public static final Item cell_tritium = new ItemRadioactive("cell_tritium").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty);
	public static final Item cell_sas3 = new ItemRadioactive("cell_sas3").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty);
	
	//Consume
	public static final Item bottle_opener = new WeaponSpecial(MainRegistry.enumToolMaterialBottleOpener, "bottle_opener").setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1);
	public static final Item bottle_empty = new ItemBase("bottle_empty").setCreativeTab(MainRegistry.consumableTab);
	public static final Item bottle_nuka = new ItemEnergy("bottle_nuka").setContainerItem(bottle_empty).setCreativeTab(MainRegistry.consumableTab);
	public static final Item bottle_cherry = new ItemEnergy("bottle_cherry").setContainerItem(ModItems.bottle_empty).setCreativeTab(MainRegistry.consumableTab);
	public static final Item cap_nuka = new ItemBase("cap_nuka").setCreativeTab(MainRegistry.consumableTab);
	
	public static final Item redstone_sword = new RedstoneSword(ToolMaterial.STONE, "redstone_sword").setCreativeTab(CreativeTabs.COMBAT);
	//Tools
	public static final Item alloy_pickaxe = new ModPickaxe(MainRegistry.enumToolMaterialAlloy, "alloy_pickaxe").setCreativeTab(CreativeTabs.TOOLS);
	public static final Item alloy_axe = new ModAxe(MainRegistry.enumToolMaterialAlloy, "alloy_axe").setCreativeTab(CreativeTabs.TOOLS);
	public static final Item alloy_hoe = new ModHoe(MainRegistry.enumToolMaterialAlloy, "alloy_hoe").setCreativeTab(CreativeTabs.TOOLS);
	public static final Item alloy_shovel = new ModSpade(MainRegistry.enumToolMaterialAlloy, "alloy_shovel").setCreativeTab(CreativeTabs.TOOLS);
	public static final Item alloy_sword = new ModSword(MainRegistry.enumToolMaterialAlloy, "alloy_sword").setCreativeTab(CreativeTabs.COMBAT);
	
	public static final Item schrabidium_pickaxe = new PickaxeSchrabidium(MainRegistry.enumToolMaterialSchrabidium, "schrabidium_pickaxe").setCreativeTab(CreativeTabs.TOOLS);
	public static final Item schrabidium_axe = new AxeSchrabidium(MainRegistry.enumToolMaterialSchrabidium, "schrabidium_axe").setCreativeTab(CreativeTabs.TOOLS);
	public static final Item schrabidium_hoe = new HoeSchrabidium(MainRegistry.enumToolMaterialSchrabidium, "schrabidium_hoe").setCreativeTab(CreativeTabs.TOOLS);
	public static final Item schrabidium_shovel = new SpadeSchrabidium(MainRegistry.enumToolMaterialSchrabidium, "schrabidium_shovel").setCreativeTab(CreativeTabs.TOOLS);
	public static final Item schrabidium_sword = new SwordSchrabidium(MainRegistry.enumToolMaterialSchrabidium, "schrabidium_sword").setCreativeTab(CreativeTabs.COMBAT);
	
	//Templates
	public static final Item assembly_template = new ItemAssemblyTemplate("assembly_template").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab);
	public static final Item chemistry_template = new ItemChemistryTemplate("chemistry_template").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab);
	public static final Item chemistry_icon = new ItemChemistryIcon("chemistry_icon").setMaxStackSize(1).setCreativeTab(null);
	
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
	
	//Wires
	public static final Item wire_advanced_alloy = new ItemBase("wire_advanced_alloy").setCreativeTab(MainRegistry.partsTab);
	public static final Item wire_red_copper = new ItemBase("wire_red_copper").setCreativeTab(MainRegistry.partsTab);
	public static final Item wire_tungsten = new ItemCustomLore("wire_tungsten").setCreativeTab(MainRegistry.partsTab);
	public static final Item wire_aluminium = new ItemBase("wire_aluminium").setCreativeTab(MainRegistry.partsTab);
	public static final Item wire_copper = new ItemBase("wire_copper").setCreativeTab(MainRegistry.partsTab);
	public static final Item wire_gold = new ItemBase("wire_gold").setCreativeTab(MainRegistry.partsTab);
	public static final Item wire_schrabidium = new ItemRadioactive("wire_schrabidium").setCreativeTab(MainRegistry.partsTab);
	public static final Item wire_magnetized_tungsten = new ItemBase("wire_magnetized_tungsten").setCreativeTab(MainRegistry.partsTab);
	
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
	public static final Item ammo_357_desh = new ItemAmmo("ammo_357_desh").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_revolver_cursed_ammo = new ItemCustomLore("gun_revolver_cursed_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_44 = new ItemAmmo("ammo_44").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_9mm = new ItemAmmo("ammo_9mm").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_22lr = new ItemAmmo("ammo_22lr").setCreativeTab(MainRegistry.weaponTab);
	public static final Item gun_mp_ammo = new ItemCustomLore("gun_mp_ammo").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_5mm = new ItemAmmo("ammo_5mm").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_50bmg = new ItemAmmo("ammo_50bmg").setCreativeTab(MainRegistry.weaponTab);
	public static final Item ammo_50ae = new ItemAmmo("ammo_50ae").setCreativeTab(MainRegistry.weaponTab);
	
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
	
	//Nuke parts
	public static final Item man_core = new ItemManMike("man_core");
	public static final Item man_explosive8 = new ItemManMike("man_explosive8").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item man_igniter = new ItemMan("man_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	
	public static final Item gadget_core = new ItemGadget("gadget_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	
	public static final Item boy_bullet = new ItemBoy("boy_bullet").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item boy_target = new ItemBoy("boy_target").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	
	public static final Item mike_core = new ItemMike("mike_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	
	public static final Item tsar_core = new ItemTsar("tsar_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	
	public static final Item fleija_igniter = new ItemFleija("fleija_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item fleija_propellant = new ItemFleija("fleija_propellant").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item fleija_core = new ItemFleija("fleija_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	
	public static final Item solinium_core = new ItemSolinium("solinium_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	
	//Dummy texture items
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
	
	public static void preInit(){
		for(Item item : ALL_ITEMS){
			ForgeRegistries.ITEMS.register(item);
			
		}
		
		for(Block block : ModBlocks.ALL_BLOCKS){
			if(block == ModBlocks.block_scrap){
				ForgeRegistries.ITEMS.register(new ItemBlockScrap(block).setRegistryName(block.getRegistryName()));
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
