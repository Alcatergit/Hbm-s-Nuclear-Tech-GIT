package com.hbm.blocks;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.machine.MachinePress;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModBlocks {

	public static List<Block> ALL_BLOCKS = new ArrayList<Block>();
	
	public static final Block fatduck = new BlockBase(Material.IRON, "fatduck");
	public static final Block machine_press = new MachinePress(Material.ANVIL, "machine_press");
	
	public static void preInit(){
		for(Block block : ALL_BLOCKS){
			System.out.println("here");
			ForgeRegistries.BLOCKS.register(block);
		}
	}
	
	public static void init(){
		
	}
	
	public static void postInit(){
		
	}
}
