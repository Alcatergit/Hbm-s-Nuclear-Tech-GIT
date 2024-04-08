package com.hbm.blocks.network;

import api.hbm.block.IConveyorBelt;
import api.hbm.block.IEnterableBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlockConveyorLift extends BlockConveyorChute {

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
    public int getUpdatedType(World world, BlockPos pos){
        boolean hasChuteBelow = world.getBlockState(pos.down()).getBlock() instanceof BlockConveyorChute;
        boolean hasInputBelt = false;
        Block inputBlock = world.getBlockState(pos.offset(world.getBlockState(pos).getValue(FACING).getOpposite(), 1)).getBlock();
        if (inputBlock instanceof IConveyorBelt || inputBlock instanceof IEnterableBlock) {
            hasInputBelt = true;
        }
        if(hasChuteBelow){
            return hasInputBelt ? 2 : 1;
        }
        return 0;
    }
}