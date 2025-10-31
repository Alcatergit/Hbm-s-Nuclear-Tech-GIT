package com.leafia.contents.machines.elevators;

import com.hbm.blocks.BlockDummyable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EvBuffer extends BlockDummyable {
	public EvBuffer(Material materialIn,String s) {
		super(materialIn,s);
	}

	@Override
	public int[] getDimensions() {
		return new int[]{2,0,0,0,0,0};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World world,int i) {
		if (i >= 12) return new EvBufferTE();
		return null;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state,IBlockAccess source,BlockPos pos) {
		return new AxisAlignedBB(0.2,0,0.2,0.8,1,0.8);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
}
