package com.hbm.blocks;

import java.util.List;

import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BlockBase extends Block {
	
	public BlockBase(Material m, String s){
		super(m);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setHarvestLevel("pickaxe", 0);
		this.setCreativeTab(MainRegistry.controlTab);
		ModBlocks.ALL_BLOCKS.add(this);
	}

	public BlockBase(Material m, SoundType sound, String s){
		super(m);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setSoundType(sound);
		this.setHarvestLevel("pickaxe", 0);
		this.setCreativeTab(MainRegistry.controlTab);
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag advanced) {
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.meteor_battery)){
			list.add(I18nUtil.resolveKey("desc.teslacoils"));
		}
	}

	public Block setSoundType(SoundType sound){
		return super.setSoundType(sound);
	}
}
