package com.hbm.blocks.network;

import api.hbm.block.IConveyorBelt;
import api.hbm.block.IEnterableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockConveyorLift extends BlockConveyor {
    public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 2); //Bottom 0, Middle 1, Output 2

    public BlockConveyorLift(Material materialIn, String s) {
        super(materialIn, s);
    }

    @Override
    public EnumFacing getTravelDirection(World world, BlockPos pos, Vec3d itemPos) {

        boolean bottom = !(world.getBlockState(pos.down()).getBlock() instanceof IConveyorBelt);
        boolean top = !(world.getBlockState(pos.up()).getBlock() instanceof IConveyorBelt) && !bottom && !(world.getBlockState(pos.up()).getBlock() instanceof IEnterableBlock);

        if(!top) {
            return EnumFacing.DOWN;
        }

        return world.getBlockState(pos).getValue(FACING);
    }

    @Override
    public Vec3d getClosestSnappingPosition(World world, BlockPos pos, Vec3d itemPos) {

        boolean bottom = !(world.getBlockState(pos.down()).getBlock() instanceof IConveyorBelt);
        boolean top = !(world.getBlockState(pos.up()).getBlock() instanceof IConveyorBelt) && !bottom && !(world.getBlockState(pos.up()).getBlock() instanceof IEnterableBlock);

        if(!top) {
            return new Vec3d(pos.getX() + 0.5, itemPos.y, pos.getZ() + 0.5);
        } else {
            return super.getClosestSnappingPosition(world, pos, itemPos);
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(TYPE, getUpdatedType(worldIn, pos)));
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos){
        world.setBlockState(pos, state.withProperty(TYPE, getUpdatedType(world, pos)));
    }

    private int getUpdatedType(World world, BlockPos pos){
        boolean hasLiftBelow = world.getBlockState(pos.down()).getBlock() instanceof BlockConveyorLift;
        boolean hasLiftAbove = world.getBlockState(pos.up()).getBlock() instanceof BlockConveyorLift;
        if(hasLiftBelow && !hasLiftAbove){
            return 2;
        }
        if(hasLiftBelow && hasLiftAbove){
            return 1;
        }
        return 0;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING, TYPE});
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing)state.getValue(FACING)).getIndex() - 2 + (state.getValue(TYPE)<<2);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront((meta+2) % 4);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(TYPE, meta>>2);
    }
}
