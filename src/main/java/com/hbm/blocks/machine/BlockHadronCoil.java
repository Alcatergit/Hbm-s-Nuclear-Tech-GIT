package com.hbm.blocks.machine;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.util.I18nUtil;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockHadronCoil extends Block {

	public int factor;
	
	public BlockHadronCoil(Material materialIn, int factor, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.factor = factor;
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public Block setSoundType(SoundType sound) {
		return super.setSoundType(sound);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(I18nUtil.resolveKey("info.coil") + ": " + factor);
	}

}
