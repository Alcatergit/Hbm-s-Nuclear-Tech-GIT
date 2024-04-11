package com.hbm.blocks.network;

import api.hbm.block.IConveyorBelt;
import api.hbm.block.IEnterableBlock;
import com.hbm.entity.item.EntityMovingItem;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.Entity;
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
    public Vec3d getTravelLocation(World world, int x, int y, int z, Vec3d itemPos, double speed) {
        BlockPos pos = new BlockPos(x, y, z);
        EnumFacing dir = this.getTravelDirection(world, pos, itemPos);
        Vec3d snap = this.getClosestSnappingPosition(world, pos, itemPos);
        Vec3d dest = new Vec3d(
                snap.x - dir.getFrontOffsetX() * speed,
                snap.y - dir.getFrontOffsetY() * speed,
                snap.z - dir.getFrontOffsetZ() * speed);
        Vec3d motion = new Vec3d(
                dest.x - itemPos.x,
                dest.y - itemPos.y,
                dest.z - itemPos.z);
        double len = motion.lengthVector();
        Vec3d ret = new Vec3d(
                itemPos.x + motion.x / len * speed,
                itemPos.y + motion.y / len * speed,
                itemPos.z + motion.z / len * speed);
        return ret;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        if(!world.isRemote) {

            if(entity instanceof EntityItem && entity.ticksExisted > 10 && !entity.isDead) {

                EntityMovingItem item = new EntityMovingItem(world);
                item.setItemStack(((EntityItem)entity).getItem());
                Vec3d entityPos = new Vec3d(entity.posX, entity.posY, entity.posZ);
                Vec3d snap = this.getClosestSnappingPosition(world, pos, entityPos);
                item.setPositionAndRotation(snap.x, snap.y, snap.z, 0, 0);
                world.spawnEntity(item);
                
                entity.setDead();
            }
        }
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
    public int getUpdatedType(World world, BlockPos pos, EnumFacing side){
        boolean hasChuteBelow = world.getBlockState(pos.down()).getBlock() instanceof BlockConveyorChute;
        boolean hasInputBelt = false;
        Block inputBlock = world.getBlockState(pos.offset(side.getOpposite(), 1)).getBlock();
        if (inputBlock instanceof IConveyorBelt || inputBlock instanceof IEnterableBlock) {
            hasInputBelt = true;
        }
        if(hasChuteBelow){
            return hasInputBelt ? 2 : 1;
        }
        return 0;
    }
}