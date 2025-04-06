package com.hbm.blocks.machine;

import java.util.List;

import com.hbm.lib.ForgeDirection;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.tileentity.machine.TileEntityFoundryBasin;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class FoundryBasin extends FoundryCastingBase {
    
	public FoundryBasin(String s) {
		super(s);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFoundryBasin();
	}

    @Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		AxisAlignedBB[] bbs = new AxisAlignedBB[] {
				new AxisAlignedBB(pos, pos.add(1D, 0.125D, 1D)),
				new AxisAlignedBB(pos, pos.add(1D, 1D, 0.125D)),
				new AxisAlignedBB(pos, pos.add(0.125D, 1D, 1D)),
				new AxisAlignedBB(pos.add(0.875, 0, 0), pos.add(1D, 1D, 1D)),
				new AxisAlignedBB(pos.add(0, 0, 0.875), pos.add(1D, 1D, 1D)),
		};

		for(AxisAlignedBB bb : bbs) {
			if(entityBox.intersects(bb)) {
				collidingBoxes.add(bb);
			}
		}
    }

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.999F, 1.0F);
	}

	@Override public boolean canAcceptPartialFlow(World world, BlockPos p, ForgeDirection side, MaterialStack stack) { return false; }
	@Override public MaterialStack flow(World world, BlockPos p, ForgeDirection side, MaterialStack stack) { return stack; }

	@Override
	public boolean isSideSolid(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return side != EnumFacing.UP;
	}

	@Override
	public double getPH(){ //particle height
		return 0.875D;
	}
}
