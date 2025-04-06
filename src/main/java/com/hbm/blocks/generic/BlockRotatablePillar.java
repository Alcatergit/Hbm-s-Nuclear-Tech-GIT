package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import com.hbm.util.I18nUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class BlockRotatablePillar extends BlockRotatedPillar {

	public BlockRotatablePillar(Material materialIn, String s, SoundType sound) {
		this(materialIn, s);
		this.setSoundType(sound);
	}

	public BlockRotatablePillar(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.block_schrabidium_cluster)){
			tooltip.add(I18nUtil.resolveKey("tile.block_schrabidium_cluster.desc"));
		}
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.block_euphemium_cluster)){
			tooltip.add(I18nUtil.resolveKey("tile.block_euphemium_cluster.desc"));
		}
		float hardness = this.getExplosionResistance(null);
		if(hardness > 50){
			tooltip.add("§6" + I18nUtil.resolveKey("trait.blastres", hardness));
		}
	}
}
