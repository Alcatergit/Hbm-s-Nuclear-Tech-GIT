package com.hbm.items;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
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
import com.hbm.items.special.ItemBlades;
import com.hbm.items.special.ItemRadioactive;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModItems {
	
	public static final List<Item> ALL_ITEMS = new ArrayList<Item>();
	
	//Materials
	public static final Item ingot_schrabidium = new ItemRadioactive("ingot_schrabidium");
	public static final Item ingot_advanced_alloy = new ItemBase("ingot_advanced_alloy");
	public static final Item sulfur = new ItemBase("sulfur");
	public static final Item niter = new ItemBase("niter");
	
	//Radioactive
	public static final Item nugget_u235 = new ItemRadioactive("nugget_u235");
	public static final Item nugget_pu238 = new ItemRadioactive("nugget_pu238");
	public static final Item nugget_pu239 = new ItemRadioactive("nugget_pu239");
	public static final Item nugget_neptunium = new ItemRadioactive("nugget_neptunium");
	
	public static final Item ingot_u235 = new ItemRadioactive("ingot_u235");
	public static final Item ingot_pu238 = new ItemRadioactive("ingot_pu238");
	
	public static final Item man_core = new ItemRadioactive("man_core");
	
	//Generic Items
	public static final Item pellet_coal = new ItemBase("pellet_coal");
	public static final Item biomass = new ItemBase("biomass");
	public static final Item biomass_compressed = new ItemBase("biomass_compressed");
	public static final Item briquette_lignite = new ItemBase("briquette_lignite");
	public static final Item coke = new ItemBase("coke");
	
	//Powders
	public static final Item powder_lignite = new ItemBase("powder_lignite");
	public static final Item powder_coal = new ItemBase("powder_coal");
	
	
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
