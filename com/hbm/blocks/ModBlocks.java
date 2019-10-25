package com.hbm.blocks;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.blocks.generic.BlockOre;
import com.hbm.blocks.generic.WasteEarth;
import com.hbm.blocks.machine.MachinePress;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModBlocks {

	public static List<Block> ALL_BLOCKS = new ArrayList<Block>();
	
	//public static final Block fatduck = new BlockBase(Material.IRON, "fatduck");
	public static final Block block_niter = new BlockBase(Material.IRON, "block_niter").setHardness(5.0F).setResistance(10.0F);
	public static final Block block_sulfur = new BlockBase(Material.IRON, "block_sulfur").setHardness(5.0F).setResistance(10.0F);
	public static final Block gravel_obsidian = new BlockFallingBase(Material.IRON, "gravel_obsidian", SoundType.GROUND).setHardness(5.0F).setResistance(600F);
	public static final Block taint = new BlockTaint(Material.IRON, "taint").setCreativeTab(null).setHardness(15.0F).setResistance(10.0F);
	
	public static final Block waste_earth = new WasteEarth(Material.GROUND, 0.25F, 7.5F, "waste_earth").setSoundType(SoundType.GROUND).setHardness(0.5F).setResistance(1.0F);
	public static final Block waste_mycelium = new WasteEarth(Material.GROUND, 1.0F, 25.0F, "waste_mycelium").setSoundType(SoundType.GROUND).setLightLevel(1.0F).setHardness(0.5F).setResistance(1.0F);
	public static final Block frozen_grass = new WasteEarth(Material.GROUND, "frozen_grass").setSoundType(SoundType.GLASS).setHardness(0.5F).setResistance(2.5F);
	
	public static final Block sellafield_slaked = new BlockBase(Material.ROCK, "sellafield_slaked").setSoundType(SoundType.STONE).setHardness(5.0F);
	public static final Block sellafield_0 = new BlockOre(Material.ROCK, 0.5F, 10.0F, "sellafield_0").setSoundType(SoundType.STONE).setHardness(5.0F);
	public static final Block sellafield_1 = new BlockOre(Material.ROCK, 1.0F, 15.0F, "sellafield_1").setSoundType(SoundType.STONE).setHardness(5.0F);
	public static final Block sellafield_2 = new BlockOre(Material.ROCK, 2.5F, 25.0F, "sellafield_2").setSoundType(SoundType.STONE).setHardness(5.0F);
	public static final Block sellafield_3 = new BlockOre(Material.ROCK, 4.0F, 40.0F, "sellafield_3").setSoundType(SoundType.STONE).setHardness(5.0F);
	public static final Block sellafield_4 = new BlockOre(Material.ROCK, 5.0F, 50.0F, "sellafield_4").setSoundType(SoundType.STONE).setHardness(5.0F);
	public static final Block sellafield_core = new BlockOre(Material.ROCK, 10.0F, 150.0F, "sellafield_core").setSoundType(SoundType.STONE).setHardness(10.0F);
	
	public static final Block machine_press = new MachinePress(Material.IRON, "machine_press").setHardness(5.0F).setResistance(10.0F);
	public static final int guiID_machine_press = 53;
	
	public static void preInit(){
		for(Block block : ALL_BLOCKS){
			ForgeRegistries.BLOCKS.register(block);
		}
	}
	
	public static void init(){
		
	}
	
	public static void postInit(){
		
	}
}
