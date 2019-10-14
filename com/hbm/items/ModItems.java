package com.hbm.items;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.gear.AxeSchrabidium;
import com.hbm.items.gear.HoeSchrabidium;
import com.hbm.items.gear.ModAxe;
import com.hbm.items.gear.PickaxeSchrabidium;
import com.hbm.items.gear.RedstoneSword;
import com.hbm.items.gear.SpadeSchrabidium;
import com.hbm.items.gear.SwordSchrabidium;
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
	
	public static final Item redstone_sword = new RedstoneSword(ToolMaterial.STONE, "redstone_sword");
	// Alloy tools
	public static final Item alloy_axe = new ModAxe(MainRegistry.enumToolMaterialAlloy, "alloy_axe");
	//Schrab tools
	public static final Item schrabidium_pickaxe = new PickaxeSchrabidium(MainRegistry.enumToolMaterialSchrabidium, "schrabidium_pickaxe");
	public static final Item schrabidium_axe = new AxeSchrabidium(MainRegistry.enumToolMaterialSchrabidium, "schrabidium_axe");
	public static final Item schrabidium_hoe = new HoeSchrabidium(MainRegistry.enumToolMaterialSchrabidium, "schrabidium_hoe");
	public static final Item schrabidium_shovel = new SpadeSchrabidium(MainRegistry.enumToolMaterialSchrabidium, "schrabidium_shovel");
	public static final Item schrabidium_sword = new SwordSchrabidium(MainRegistry.enumToolMaterialSchrabidium, "schrabidium_sword");
	
	
	
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
