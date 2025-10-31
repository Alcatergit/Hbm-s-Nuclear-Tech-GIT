package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class BlockStruct extends BlockContainer {
	private final Class<? extends TileEntity> structTEClass;

	public BlockStruct(Material materialIn, String s, Class<? extends TileEntity> tileEntityClass) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.structTEClass = tileEntityClass;
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
        try {
            return structTEClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

}
