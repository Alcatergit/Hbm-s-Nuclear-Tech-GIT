package com.hbm.blocks.network.energy;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.network.energy.TileEntityConverterHeRf;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockConverterHeRf extends BlockContainer {

	public BlockConverterHeRf(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.machineTab);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityConverterHeRf();
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}
