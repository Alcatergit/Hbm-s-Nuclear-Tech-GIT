package com.hbm.blocks.network;

import api.hbm.block.IConveyorItem;
import api.hbm.block.IConveyorPackage;
import api.hbm.block.IEnterableBlock;
import com.hbm.blocks.ModBlocks;
import com.hbm.lib.InventoryHelper;
import com.hbm.tileentity.network.TileEntityCraneBase;
import com.hbm.tileentity.network.TileEntityCraneUnboxer;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CraneUnboxer extends BlockCraneBase implements IEnterableBlock {
    public CraneUnboxer(Material materialIn, String s) {
        super(materialIn);
        this.setUnlocalizedName(s);
        this.setRegistryName(s);
        ModBlocks.ALL_BLOCKS.add(this);
    }

    @Override
    public TileEntityCraneBase createNewTileEntity(World world, int meta) {
        return new TileEntityCraneUnboxer();
    }

    @Override
    public boolean canItemEnter(World world, int x, int y, int z, EnumFacing dir, IConveyorItem entity) {
        return false;
    }

    @Override
    public void onItemEnter(World world, int x, int y, int z, EnumFacing dir, IConveyorItem entity) { }

    @Override
    public boolean canPackageEnter(World world, int x, int y, int z, EnumFacing dir, IConveyorPackage entity) {
        BlockPos pos = new BlockPos(x, y, z);
        IBlockState state = world.getBlockState(pos);
        EnumFacing orientation = state.getValue(BlockHorizontal.FACING);
        return dir == orientation;
    }

    @Override
    public void onPackageEnter(World world, int x, int y, int z, EnumFacing dir, IConveyorPackage entity) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        boolean worked = false;
        if (te instanceof TileEntityCraneUnboxer) {

            for (ItemStack stack : entity.getItemStacks()) {
                if(stack == null || stack.isEmpty()) continue;
                worked = ((TileEntityCraneUnboxer)te).tryFillTeDirect(stack);

                if (stack.getCount() > 0 || !worked) {
                    EntityItem drop = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, stack.copy());
                    world.spawnEntity(drop);
                }
            }
        }
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState blockState) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos) {
        int redstoneSignal = blockState.getComparatorInputOverride(world, pos);
        return redstoneSignal;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tileentity = world.getTileEntity(pos);

        if(tileentity instanceof TileEntityCraneUnboxer) {
            InventoryHelper.dropInventoryItems(world, pos, (TileEntityCraneUnboxer) tileentity);
        }
        super.breakBlock(world, pos, state);
    }
}
