package com.hbm.blocks.network;

import api.hbm.block.IConveyorBelt;
import api.hbm.block.IEnterableBlock;
import com.hbm.tileentity.network.TileEntityConveyorChute;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockConveyorChute extends BlockConveyor {
    public BlockConveyorChute(Material materialIn, String s) {
        super(materialIn, s);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        super.onEntityCollidedWithBlock(world, pos, state, entity);

        BlockPos belowPos = pos.down();
        IBlockState belowState = world.getBlockState(belowPos);
        Block belowBlock = belowState.getBlock();

        if (belowBlock instanceof IConveyorBelt || belowBlock instanceof IEnterableBlock) {
            entity.motionX *= 5.0;
            entity.motionY *= 5.0;
            entity.motionZ *= 5.0;
        } else if (entity.posY > pos.getY() + 0.25) {
            entity.motionX *= 3.0;
            entity.motionY *= 3.0;
            entity.motionZ *= 3.0;
        }
    }

    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityConveyorChute();
    }

    @Override
    public Vec3d getTravelLocation(World world, int x, int y, int z, Vec3d itemPos, double speed) {
        BlockPos pos = new BlockPos(x, y, z);
        BlockPos belowPos = pos.down();
        IBlockState belowState = world.getBlockState(belowPos);
        Block belowBlock = belowState.getBlock();

        if (belowBlock instanceof IConveyorBelt || belowBlock instanceof IEnterableBlock) {
            speed *= 5.0;
        } else if (itemPos.y > pos.getY() + 0.25) {
            speed *= 3.0;
        }

        return super.getTravelLocation(world, x, y, z, itemPos, speed);
    }

    @Override
    public EnumFacing getTravelDirection(World world, BlockPos pos, Vec3d itemPos) {
        BlockPos belowPos = pos.down();
        IBlockState belowState = world.getBlockState(belowPos);
        Block belowBlock = belowState.getBlock();

        if (belowBlock instanceof IConveyorBelt || belowBlock instanceof IEnterableBlock || itemPos.y > pos.getY() + 0.25) {
            return EnumFacing.UP;
        }

        return world.getBlockState(pos).getValue(FACING);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return true;
    }
}
