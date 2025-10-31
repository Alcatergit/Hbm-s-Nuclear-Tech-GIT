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

	protected static final AxisAlignedBB AABB_WALL_1 = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_2 = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
    protected static final AxisAlignedBB AABB_WALL_3 = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_4 = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_5 = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
    
	public FoundryBasin(String s) {
		super(s);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFoundryBasin();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_1);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_2);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_3);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_4);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_5);
    }

	@Override public boolean canAcceptPartialFlow(World world, BlockPos p, ForgeDirection side, MaterialStack stack) { return false; }
	@Override public MaterialStack flow(World world, BlockPos p, ForgeDirection side, MaterialStack stack) { return stack; }

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return side != EnumFacing.UP;
	}

	@Override
	public double getPH(){ //particle height
		return 0.875D;
	}
}
