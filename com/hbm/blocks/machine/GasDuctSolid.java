package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.conductor.TileEntityFFDuctBase;
import com.hbm.tileentity.conductor.TileEntityFFGasDuctSolid;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class GasDuctSolid extends BlockContainer {

	public GasDuctSolid(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityFFGasDuctSolid();
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if(worldIn.getTileEntity(pos) != null && worldIn.getTileEntity(pos) instanceof TileEntityFFDuctBase) {
			((TileEntityFFDuctBase)worldIn.getTileEntity(pos)).breakBlock();
		}
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
		if(world.getTileEntity(pos) instanceof TileEntityFFDuctBase) {
			((TileEntityFFDuctBase)world.getTileEntity(pos)).onNeighborBlockChange();
		}
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

}
