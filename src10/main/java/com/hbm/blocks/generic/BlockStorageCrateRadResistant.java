package com.hbm.blocks.generic;

import java.util.List;

import com.hbm.util.I18nUtil;
import com.hbm.handler.RadiationSystemNT;
import com.hbm.interfaces.IRadResistantBlock;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockStorageCrateRadResistant extends BlockStorageCrate implements IRadResistantBlock {

	public BlockStorageCrateRadResistant(Material materialIn, String s) {
		super(materialIn, s);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		RadiationSystemNT.markChunkForRebuild(worldIn, pos);
		super.onBlockAdded(worldIn, pos, state);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		RadiationSystemNT.markChunkForRebuild(worldIn, pos);
		super.breakBlock(worldIn, pos, state);
	}
}
