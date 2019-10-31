package com.hbm.items;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.bomb.ItemBoy;
import com.hbm.items.bomb.ItemFleija;
import com.hbm.items.bomb.ItemGadget;
import com.hbm.items.bomb.ItemManMike;
import com.hbm.items.bomb.ItemMike;
import com.hbm.items.bomb.ItemSolinium;
import com.hbm.items.bomb.ItemTsar;
import com.hbm.items.food.ItemEnergy;
import com.hbm.items.gear.ArmorT45;
import com.hbm.items.gear.AxeSchrabidium;
import com.hbm.items.gear.HoeSchrabidium;
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
import com.hbm.items.special.ItemFuelRod;
import com.hbm.items.special.ItemRadioactive;
import com.hbm.items.special.ItemSyringe;
import com.hbm.items.special.WatzFuel;
import com.hbm.items.tool.ItemAssemblyTemplate;
import com.hbm.items.tool.ItemLeadBox;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModItems {
	
	public static final List<Item> ALL_ITEMS = new ArrayList<Item>();
	
	//Syringe
	public static final Item syringe_empty = new ItemBase("syringe_empty").setFull3D().setCreativeTab(MainRegistry.consumableTab);
	public static final Item syringe_awesome = new ItemSyringe("syringe_awesome").setCreativeTab(MainRegistry.consumableTab);
	public static final Item syringe_metal_empty = new ItemBase("syringe_metal_empty").setFull3D().setCreativeTab(MainRegistry.consumableTab);
	public static final Item syringe_metal_stimpak = new ItemSyringe("syringe_metal_stimpak").setFull3D().setCreativeTab(MainRegistry.consumableTab);
	
	//Energy items
	public static final Item battery_schrabidium = new ItemBattery(10000, 50, 50, "battery_schrabidium").setMaxStackSize(1);
	public static final Item factory_core_titanium = new ItemBattery(70400, 10, 0, "factory_core_titanium").setMaxStackSize(1);
	public static final Item factory_core_advanced = new ItemBattery(41600, 10, 0, "factory_core_advanced").setMaxStackSize(1);
	public static final Item dynosphere_desh = new ItemBattery(10000L, 100, 0, "dynosphere_desh").setMaxStackSize(1);
	public static final Item dynosphere_schrabidium = new ItemBattery(1000000L, 1000, 0, "dynosphere_schrabidium").setMaxStackSize(1);
	public static final Item dynosphere_euphemium = new ItemBattery(100000000L, 10000, 0, "dynosphere_euphemium").setMaxStackSize(1);
	public static final Item dynosphere_dineutronium = new ItemBattery(10000000000L, 100000, 0, "dynosphere_dineutronium").setMaxStackSize(1);
	public static final Item fusion_core = new ItemBattery(200000, 0, 25, "fusion_core").setMaxStackSize(1);
	public static final Item energy_core = new ItemBattery(100000, 0, 10, "energy_core").setMaxStackSize(1);
	public static final Item fusion_core_infinite = new ItemBase("fusion_core_infinite").setMaxStackSize(1);
	
	//Weapons
	public static final Item gun_fatman_ammo = new ItemBase("gun_fatman_ammo");
	
	//Armor
	public static final Item t45_helmet = new ArmorT45(MainRegistry.enumArmorMaterialT45, -1, EntityEquipmentSlot.HEAD, "t45_helmet").setCreativeTab(CreativeTabs.COMBAT);
	public static final Item t45_plate = new ArmorT45(MainRegistry.enumArmorMaterialT45, -1, EntityEquipmentSlot.CHEST, "t45_plate").setCreativeTab(CreativeTabs.COMBAT);
	public static final Item t45_legs = new ArmorT45(MainRegistry.enumArmorMaterialT45, -1, EntityEquipmentSlot.LEGS, "t45_legs").setCreativeTab(CreativeTabs.COMBAT);
	public static final Item t45_boots = new ArmorT45(MainRegistry.enumArmorMaterialT45, -1, EntityEquipmentSlot.FEET, "t45_boots").setCreativeTab(CreativeTabs.COMBAT);
	
	
	
	//Materials
	public static final Item ingot_schrabidium = new ItemRadioactive("ingot_schrabidium").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_advanced_alloy = new ItemBase("ingot_advanced_alloy").setCreativeTab(MainRegistry.partsTab);
	public static final Item sulfur = new ItemBase("sulfur").setCreativeTab(MainRegistry.partsTab);
	public static final Item niter = new ItemBase("niter").setCreativeTab(MainRegistry.partsTab);
	public static final Item lignite = new ItemBase("lignite").setCreativeTab(MainRegistry.partsTab);
	
	public static final Item nugget_schrabidium = new ItemRadioactive("nugget_schrabidium").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_solinium = new ItemRadioactive("nugget_solinium").setCreativeTab(MainRegistry.partsTab);
	
	public static final Item wire_schrabidium = new ItemRadioactive("wire_schrabidium").setCreativeTab(MainRegistry.partsTab);
	
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
	public static final Item nugget_neptunium = new ItemRadioactive("nugget_neptunium");
	public static final Item nugget_uranium_fuel = new ItemRadioactive("nugget_uranium_fuel").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_plutonium_fuel = new ItemRadioactive("nugget_plutonium_fuel").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_mox_fuel = new ItemRadioactive("nugget_mox_fuel").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_schrabidium_fuel = new ItemRadioactive("nugget_schrabidium_fuel").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_thorium_fuel = new ItemRadioactive("nugget_thorium_fuel").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_hes = new ItemRadioactive("nugget_hes").setCreativeTab(MainRegistry.partsTab);
	public static final Item nugget_les = new ItemRadioactive("nugget_les").setCreativeTab(MainRegistry.partsTab);
	
	public static final Item pellet_schrabidium = new WatzFuel(50000, 140000, 0.975F, 200, 1.05F, 1.05F, "pellet_schrabidium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_hes = new WatzFuel(108000, 65000, 1F, 85, 1, 1.025F, "pellet_hes").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_mes = new WatzFuel(216000, 23000, 1.025F, 50, 1, 1F, "pellet_mes").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_les = new WatzFuel(432000, 7000, 1.05F, 15, 1, 0.975F, "pellet_les").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_beryllium = new WatzFuel(864000, 50, 1.05F, 0, 0.95F, 1.025F, "pellet_beryllium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_neptunium = new WatzFuel(216000, 3000, 1.1F, 25, 1.1F, 1.005F, "pellet_neptunium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_lead = new WatzFuel(1728000, 0, 0.95F, 0, 0.95F, 0.95F, "pellet_lead").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_advanced = new WatzFuel(216000, 1000, 1.1F, 0, 0.995F, 0.99F, "pellet_advanced").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	
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
	
	public static final Item man_core = new ItemManMike("man_core");
	
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
	public static final Item scrap = new ItemBase("scrap").setCreativeTab(MainRegistry.partsTab);
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
	public static final Item biomass_compressed = new ItemBase("biomass_compressed").setCreativeTab(MainRegistry.partsTab);
	public static final Item briquette_lignite = new ItemBase("briquette_lignite").setCreativeTab(MainRegistry.partsTab);
	public static final Item coke = new ItemBase("coke").setCreativeTab(MainRegistry.partsTab);
	
	//Powders
	public static final Item powder_lignite = new ItemBase("powder_lignite").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_coal = new ItemBase("powder_coal").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_yellowcake = new ItemRadioactive("powder_yellowcake").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_neptunium = new ItemRadioactive("powder_neptunium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_uranium = new ItemRadioactive("powder_uranium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_plutonium = new ItemRadioactive("powder_plutonium").setCreativeTab(MainRegistry.partsTab);
	public static final Item powder_schrabidium = new ItemRadioactive("powder_schrabidium").setCreativeTab(MainRegistry.partsTab);
	
	//Cells
	public static final Item cell_empty = new ItemCell("cell_empty").setCreativeTab(MainRegistry.controlTab);
	public static final Item cell_tritium = new ItemRadioactive("cell_tritium").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty);
	public static final Item cell_sas3 = new ItemRadioactive("cell_sas3").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty);
	
	//Consume
	public static final Item bottle_opener = new WeaponSpecial(MainRegistry.enumToolMaterialBottleOpener, "bottle_opener").setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1);
	public static final Item bottle_empty = new ItemBase("bottle_empty").setCreativeTab(MainRegistry.consumableTab);
	public static final Item bottle_nuka = new ItemEnergy("bottle_nuka").setContainerItem(bottle_empty).setCreativeTab(MainRegistry.consumableTab);
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
	
	//Nuke parts
	public static final Item gadget_core = new ItemGadget("gadget_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	
	public static final Item boy_bullet = new ItemBoy("boy_bullet").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	public static final Item boy_target = new ItemBoy("boy_target").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	
	public static final Item mike_core = new ItemMike("mike_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	
	public static final Item tsar_core = new ItemTsar("tsar_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	
	public static final Item fleija_propellant = new ItemFleija("fleija_propellant").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab);
	
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
	
	public static void preInit(){
		for(Item item : ALL_ITEMS){
			ForgeRegistries.ITEMS.register(item);
			
		}
		
		for(Block block : ModBlocks.ALL_BLOCKS){
			ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		}
	}
	
	public static void init(){
		
	}
	
	public static void postInit(){
		
	}

}
