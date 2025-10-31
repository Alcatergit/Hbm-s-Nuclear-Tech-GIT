package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.items.ModItems.Materials.Crystals;
import com.hbm.items.ModItems.Materials.Nuggies;
import com.hbm.items.ModItems.Materials.Powders;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockDepthOre extends BlockDepth {
	
	public BlockDepthOre(String s){
		super(s);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune){
		if(this == ModBlocks.cluster_depth_iron) {
			return Crystals.crystal_iron;
		}
		if(this == ModBlocks.cluster_depth_titanium) {
			return Crystals.crystal_titanium;
		}
		if(this == ModBlocks.cluster_depth_tungsten) {
			return Crystals.crystal_tungsten;
		}
		if(this == ModBlocks.ore_depth_cinnebar) {
			return ModItems.cinnebar;
		}
		if(this == ModBlocks.ore_depth_zirconium) {
			return Nuggies.nugget_zirconium;
		}
		if(this == ModBlocks.ore_depth_nether_neodymium) {
			return ModItems.fragment_neodymium;
		}
		if(this == ModBlocks.ore_depth_nether_nitan) {
			return Powders.powder_nitan_mix;
		}
		
		return super.getItemDropped(state, rand, fortune);
	}
	
	@Override
	public int quantityDropped(Random rand) {
		
		if(this == ModBlocks.ore_depth_cinnebar) {
			return 2 + rand.nextInt(3);
		}
		if(this == ModBlocks.ore_depth_zirconium) {
			return 2 + rand.nextInt(2);
		}
		if(this == ModBlocks.ore_depth_nether_neodymium) {
			return 2 + rand.nextInt(2);
		}
		if(this == ModBlocks.ore_depth_nether_nitan) {
			return 1;
		}
		
		return super.quantityDropped(rand);
	}
}