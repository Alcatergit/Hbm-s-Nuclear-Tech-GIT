package com.leafia.contents.machines.manfacturing.wish;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockMachineBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class WishBlock extends BlockMachineBase {
	public WishBlock(Material materialIn,String s) {
		super(materialIn,ModBlocks.guiID_wish,s);
	}
	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta) {
		return new WishTE();
	}
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}
