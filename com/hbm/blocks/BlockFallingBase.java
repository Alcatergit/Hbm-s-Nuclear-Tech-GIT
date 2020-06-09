package com.hbm.blocks;

import java.util.List;

import com.hbm.main.MainRegistry;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockFallingBase extends BlockFalling {
	
	public BlockFallingBase(Material m, String s, SoundType type){
		super(m);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		this.setSoundType(type);
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		if(this == ModBlocks.gravel_diamond){
			tooltip.add("There is some kind of joke here,");
			tooltip.add("but I can't quite tell what it is.");
		}
	}
	
}
