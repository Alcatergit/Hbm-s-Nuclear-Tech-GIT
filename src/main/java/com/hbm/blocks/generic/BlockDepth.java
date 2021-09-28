package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;

import api.hbm.item.IDepthRockTool;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDepth extends Block {

	public BlockDepth(String s){
		super(Material.ROCK);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setBlockUnbreakable();
		this.setResistance(10.0F);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	@SuppressWarnings("deprecation")
	public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World worldIn, BlockPos pos){
		if(player.getHeldItemMainhand().getItem() instanceof IDepthRockTool) {
			if(!player.getHeldItemMainhand().isEmpty() && ((IDepthRockTool)player.getHeldItemMainhand().getItem()).canBreakRock(worldIn, player, player.getHeldItemMainhand(), state, pos))
				return (float)(1D / 100D);
		}
		return super.getPlayerRelativeBlockHardness(state, player, worldIn, pos);
	}
}