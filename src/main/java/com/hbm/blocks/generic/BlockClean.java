package com.hbm.blocks.generic;

import java.util.List;

import com.hbm.blocks.BlockBase;
import com.hbm.blocks.ModBlocks;
import com.hbm.util.I18nUtil;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockClean extends BlockBase {
	
	public BlockClean(Material m, String s){
		super(m, s);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("§3["+I18nUtil.resolveKey("trait.cleanroom")+"§3]");
		tooltip.add(" §b"+I18nUtil.resolveKey("trait.cleanroom.desc"));
		super.addInformation(stack, player, tooltip, advanced);
	}

	public static void getUsed(Block b, BlockPos pos, World world){
		if(b == ModBlocks.tile_lab && world.rand.nextInt(2000) == 0){
			world.setBlockState(pos, ModBlocks.tile_lab_cracked.getDefaultState());
		} else if(b == ModBlocks.tile_lab_cracked && world.rand.nextInt(10000) == 0){
			world.setBlockState(pos, ModBlocks.tile_lab_broken.getDefaultState());
		}
	}
}
