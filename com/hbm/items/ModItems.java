package com.hbm.items;

import ModItems;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
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
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModItems {
	
	public static final List<Item> ALL_ITEMS = new ArrayList<Item>();
	
	//Syringe
	public static final Item syringe_empty = new ItemBase("syringe_empty").setFull3D();
	public static final Item syringe_awesome = new ItemSyringe("syringe_awesome");
	public static final Item syringe_metal_empty = new ItemBase("syringe_metal_empty").setFull3D();
	public static final Item syringe_metal_stimpak = new ItemSyringe("syringe_metal_stimpak").setFull3D();
	
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
	public static final Item t45_helmet = new ArmorT45(MainRegistry.enumArmorMaterialT45, -1, EntityEquipmentSlot.HEAD, "t45_helmet");
	public static final Item t45_plate = new ArmorT45(MainRegistry.enumArmorMaterialT45, -1, EntityEquipmentSlot.CHEST, "t45_plate");
	public static final Item t45_legs = new ArmorT45(MainRegistry.enumArmorMaterialT45, -1, EntityEquipmentSlot.LEGS, "t45_legs");
	public static final Item t45_boots = new ArmorT45(MainRegistry.enumArmorMaterialT45, -1, EntityEquipmentSlot.FEET, "t45_boots");
	
	
	
	//Materials
	public static final Item ingot_schrabidium = new ItemRadioactive("ingot_schrabidium");
	public static final Item ingot_advanced_alloy = new ItemBase("ingot_advanced_alloy");
	public static final Item sulfur = new ItemBase("sulfur");
	public static final Item niter = new ItemBase("niter");
	public static final Item lignite = new ItemBase("lignite").setCreativeTab(MainRegistry.partsTab);
	
	//Radioactive Materials
	public static final Item trinitite = new ItemRadioactive("trinitite");
	public static final Item nugget_u235 = new ItemRadioactive("nugget_u235");
	public static final Item nugget_pu238 = new ItemRadioactive("nugget_pu238");
	public static final Item nugget_pu239 = new ItemRadioactive("nugget_pu239");
	public static final Item nugget_neptunium = new ItemRadioactive("nugget_neptunium");
	public static final Item nugget_u233 = new ItemRadioactive("nugget_u233");
	
	public static final Item pellet_schrabidium = new WatzFuel(50000, 140000, 0.975F, 200, 1.05F, 1.05F, "pellet_schrabidium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_hes = new WatzFuel(108000, 65000, 1F, 85, 1, 1.025F, "pellet_hes").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_mes = new WatzFuel(216000, 23000, 1.025F, 50, 1, 1F, "pellet_mes").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_les = new WatzFuel(432000, 7000, 1.05F, 15, 1, 0.975F, "pellet_les").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_beryllium = new WatzFuel(864000, 50, 1.05F, 0, 0.95F, 1.025F, "pellet_beryllium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_neptunium = new WatzFuel(216000, 3000, 1.1F, 25, 1.1F, 1.005F, "pellet_neptunium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_lead = new WatzFuel(1728000, 0, 0.95F, 0, 0.95F, 0.95F, "pellet_lead").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	public static final Item pellet_advanced = new WatzFuel(216000, 1000, 1.1F, 0, 0.995F, 0.99F, "pellet_advanced").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	
	public static final Item ingot_uranium = new ItemRadioactive("ingot_uranium").setCreativeTab(MainRegistry.partsTab);
	public static final Item ingot_u235 = new ItemRadioactive("ingot_u235");
	public static final Item ingot_pu238 = new ItemRadioactive("ingot_pu238");
	
	public static final Item man_core = new ItemRadioactive("man_core");
	
	public static final Item nuclear_waste = new ItemRadioactive("nuclear_waste");
	public static final Item nuclear_waste_tiny = new ItemRadioactive("nuclear_waste_tiny").setUnlocalizedName("nuclear_waste_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_tiny");
	public static final Item waste_uranium = new ItemRadioactive("waste_uranium").setUnlocalizedName("waste_uranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_uranium");
	public static final Item waste_thorium = new ItemRadioactive("waste_thorium").setUnlocalizedName("waste_thorium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_thorium");
	public static final Item waste_plutonium = new ItemRadioactive("waste_plutonium").setUnlocalizedName("waste_plutonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_plutonium");
	public static final Item waste_mox = new ItemRadioactive("waste_mox").setUnlocalizedName("waste_mox").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_mox");
	public static final Item waste_schrabidium = new ItemRadioactive("waste_schrabidium").setUnlocalizedName("waste_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_schrabidium");
	public static final Item waste_uranium_hot = new ItemRadioactive("waste_uranium_hot").setUnlocalizedName("waste_uranium_hot").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_uranium_hot");
	public static final Item waste_thorium_hot = new ItemRadioactive("waste_thorium_hot").setUnlocalizedName("waste_thorium_hot").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_thorium_hot");
	public static final Item waste_plutonium_hot = new ItemRadioactive("waste_plutonium_hot").setUnlocalizedName("waste_plutonium_hot").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_plutonium_hot");
	public static final Item waste_mox_hot = new ItemRadioactive("waste_mox_hot").setUnlocalizedName("waste_mox_hot").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_mox_hot");
	public static final Item waste_schrabidium_hot = new ItemRadioactive("waste_schrabidium_hot").setUnlocalizedName("waste_schrabidium_hot").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_schrabidium_hot");
	public static final Item scrap = new ItemBase("scrap").setUnlocalizedName("scrap").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":scrap");
	public static final Item containment_box = new ItemLeadBox("containment_box").setCreativeTab(null);
	
	public static final Item pellet_rtg = new ItemRadioactive("pellet_rtg").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
	
	//Fuel rods
	public static final Item rod_empty = new ItemBase("rod_empty").setCreativeTab(MainRegistry.controlTab);
	public static final Item rod_uranium_fuel = new ItemFuelRod(100000, 15, "rod_uranium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty);
	public static final Item rod_thorium_fuel_depleted = new ItemRadioactive().setUnlocalizedName("rod_thorium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_thorium_fuel_depleted");
	public static final Item rod_dual_thorium_fuel_depleted = new ItemRadioactive().setUnlocalizedName("rod_dual_thorium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_thorium_fuel_depleted");
	public static final Item rod_quad_thorium_fuel_depleted = new ItemRadioactive().setUnlocalizedName("rod_quad_thorium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_thorium_fuel_depleted");
	public static final Item rod_uranium_fuel_depleted = new ItemRadioactive().setUnlocalizedName("rod_uranium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_uranium_fuel_depleted");
	public static final Item rod_dual_uranium_fuel_depleted = new ItemRadioactive().setUnlocalizedName("rod_dual_uranium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_uranium_fuel_depleted");
	public static final Item rod_quad_uranium_fuel_depleted = new ItemRadioactive().setUnlocalizedName("rod_quad_uranium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_uranium_fuel_depleted");
	public static final Item rod_plutonium_fuel_depleted = new ItemRadioactive().setUnlocalizedName("rod_plutonium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_plutonium_fuel_depleted");
	public static final Item rod_dual_plutonium_fuel_depleted = new ItemRadioactive().setUnlocalizedName("rod_dual_plutonium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_plutonium_fuel_depleted");
	public static final Item rod_quad_plutonium_fuel_depleted = new ItemRadioactive().setUnlocalizedName("rod_quad_plutonium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_plutonium_fuel_depleted");
	public static final Item rod_mox_fuel_depleted = new ItemRadioactive().setUnlocalizedName("rod_mox_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_mox_fuel_depleted");
	public static final Item rod_dual_mox_fuel_depleted = new ItemRadioactive().setUnlocalizedName("rod_dual_mox_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_mox_fuel_depleted");
	public static final Item rod_quad_mox_fuel_depleted = new ItemRadioactive().setUnlocalizedName("rod_quad_mox_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_mox_fuel_depleted");
	public static final Item rod_schrabidium_fuel_depleted = new ItemRadioactive().setUnlocalizedName("rod_schrabidium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_schrabidium_fuel_depleted");
	public static final Item rod_dual_schrabidium_fuel_depleted = new ItemRadioactive().setUnlocalizedName("rod_dual_schrabidium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_schrabidium_fuel_depleted");
	public static final Item rod_quad_schrabidium_fuel_depleted = new ItemRadioactive().setUnlocalizedName("rod_quad_schrabidium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_schrabidium_fuel_depleted");
	public static final Item rod_waste = new ItemRadioactive().setUnlocalizedName("rod_waste").setMaxStackSize(1).setCreativeTab(null).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_waste");
	public static final Item rod_dual_waste = new ItemRadioactive().setUnlocalizedName("rod_dual_waste").setMaxStackSize(1).setCreativeTab(null).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_waste");
	public static final Item rod_quad_waste = new ItemRadioactive().setUnlocalizedName("rod_quad_waste").setMaxStackSize(1).setCreativeTab(null).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_waste");
	
	//Generic Items
	public static final Item pellet_coal = new ItemBase("pellet_coal");
	public static final Item biomass = new ItemBase("biomass");
	public static final Item biomass_compressed = new ItemBase("biomass_compressed");
	public static final Item briquette_lignite = new ItemBase("briquette_lignite");
	public static final Item coke = new ItemBase("coke");
	
	//Powders
	public static final Item powder_lignite = new ItemBase("powder_lignite");
	public static final Item powder_coal = new ItemBase("powder_coal");
	
	//Consume
	public static final Item bottle_opener = new WeaponSpecial(MainRegistry.enumToolMaterialBottleOpener, "bottle_opener").setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1);
	public static final Item bottle_empty = new ItemBase("bottle_empty").setCreativeTab(MainRegistry.consumableTab);
	public static final Item bottle_nuka = new ItemEnergy("bottle_nuka").setContainerItem(bottle_empty).setCreativeTab(MainRegistry.consumableTab);
	public static final Item cap_nuka = new ItemBase("cap_nuka").setCreativeTab(MainRegistry.consumableTab);
	
	public static final Item redstone_sword = new RedstoneSword(ToolMaterial.STONE, "redstone_sword");
	//Tools
	public static final Item alloy_pickaxe = new ModPickaxe(MainRegistry.enumToolMaterialAlloy, "alloy_pickaxe");
	public static final Item alloy_axe = new ModAxe(MainRegistry.enumToolMaterialAlloy, "alloy_axe");
	public static final Item alloy_hoe = new ModHoe(MainRegistry.enumToolMaterialAlloy, "alloy_hoe");
	public static final Item alloy_shovel = new ModSpade(MainRegistry.enumToolMaterialAlloy, "alloy_shovel");
	public static final Item alloy_sword = new ModSword(MainRegistry.enumToolMaterialAlloy, "alloy_sword");
	
	public static final Item schrabidium_pickaxe = new PickaxeSchrabidium(MainRegistry.enumToolMaterialSchrabidium, "schrabidium_pickaxe");
	public static final Item schrabidium_axe = new AxeSchrabidium(MainRegistry.enumToolMaterialSchrabidium, "schrabidium_axe");
	public static final Item schrabidium_hoe = new HoeSchrabidium(MainRegistry.enumToolMaterialSchrabidium, "schrabidium_hoe");
	public static final Item schrabidium_shovel = new SpadeSchrabidium(MainRegistry.enumToolMaterialSchrabidium, "schrabidium_shovel");
	public static final Item schrabidium_sword = new SwordSchrabidium(MainRegistry.enumToolMaterialSchrabidium, "schrabidium_sword");
	
	//Templates
	public static final Item assembly_template = new ItemAssemblyTemplate("assembly_template").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab);
	
	//Plates
	public static final Item plate_iron = new ItemBase("plate_iron");
	public static final Item plate_steel = new ItemBase("plate_steel");
	public static final Item plate_gold = new ItemBase("plate_gold");
	public static final Item plate_lead = new ItemBase("plate_lead");
	public static final Item plate_copper = new ItemBase("plate_copper");
	public static final Item plate_advanced_alloy = new ItemBase("plate_advanced_alloy");
	public static final Item plate_combine_steel = new ItemBase("plate_combine_steel");
	public static final Item plate_mixed = new ItemBase("plate_mixed");
	public static final Item plate_paa = new ItemBase("plate_paa");
	public static final Item plate_dalekanium = new ItemBase("plate_dalekanium");
	public static final Item plate_euphemium = new ItemBase("plate_euphemium");
	public static final Item plate_polymer = new ItemBase("plate_polymer");
	public static final Item plate_kevlar = new ItemBase("plate_kevlar");
	public static final Item plate_dineutronium = new ItemBase("plate_dineutronium");
	public static final Item plate_desh = new ItemBase("plate_desh");
	public static final Item plate_saturnite = new ItemBase("plate_saturnite");
	public static final Item plate_titanium = new ItemBase("plate_titanium");
	public static final Item plate_aluminium = new ItemBase("plate_aluminium");
	public static final Item plate_schrabidium = new ItemBase("plate_schrabidium");
	
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
	
	//Dummy texture items
	public static final Item smoke1 = new ItemBase("smoke1");
	public static final Item smoke2 = new ItemBase("smoke2");
	public static final Item smoke3 = new ItemBase("smoke3");
	public static final Item smoke4 = new ItemBase("smoke4");
	public static final Item smoke5 = new ItemBase("smoke5");
	public static final Item smoke6 = new ItemBase("smoke6");
	public static final Item smoke7 = new ItemBase("smoke7");
	public static final Item smoke8 = new ItemBase("smoke8");
	
	public static final Item b_smoke1 = new ItemBase("b_smoke1");
	public static final Item b_smoke2 = new ItemBase("b_smoke2");
	public static final Item b_smoke3 = new ItemBase("b_smoke3");
	public static final Item b_smoke4 = new ItemBase("b_smoke4");
	public static final Item b_smoke5 = new ItemBase("b_smoke5");
	public static final Item b_smoke6 = new ItemBase("b_smoke6");
	public static final Item b_smoke7 = new ItemBase("b_smoke7");
	public static final Item b_smoke8 = new ItemBase("b_smoke8");
	
	public static final Item d_smoke1 = new ItemBase("d_smoke1");
	public static final Item d_smoke2 = new ItemBase("d_smoke2");
	public static final Item d_smoke3 = new ItemBase("d_smoke3");
	public static final Item d_smoke4 = new ItemBase("d_smoke4");
	public static final Item d_smoke5 = new ItemBase("d_smoke5");
	public static final Item d_smoke6 = new ItemBase("d_smoke6");
	public static final Item d_smoke7 = new ItemBase("d_smoke7");
	public static final Item d_smoke8 = new ItemBase("d_smoke8");
	
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
