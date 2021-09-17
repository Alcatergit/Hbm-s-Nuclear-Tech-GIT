package com.hbm.interfaces;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ICustomSelectionBox {

	public boolean renderBox(World world, EntityPlayer player, IBlockState state, BlockPos pos, double x, double y, double z, float partialTicks);
}
