package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.block.BlockBush;

import java.util.Random;

public class BlockDeadPlant extends BlockBush
{
    public BlockDeadPlant(Material materialIn, String s) {
        super(materialIn);
        this.setUnlocalizedName(s);
        this.setRegistryName(s);

        ModBlocks.ALL_BLOCKS.add(this);

    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return this.canBlockStay(world, pos, world.getBlockState(pos));
    }

//    protected void checkAndDropBlock(World world, BlockPos pos) {
//        if (!this.canBlockStay(world, pos)) {
//            this.dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
//            world.setBlockState(pos, Blocks.AIR.getDefaultState());
//        }
//
//    }

    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        Block block = world.getBlockState(pos.down()).getBlock();
        return block == Blocks.GRASS || block == Blocks.DIRT || block == ModBlocks.waste_earth || block == ModBlocks.waste_dirt || block == ModBlocks.dirt_dead || block == ModBlocks.dirt_oily;
    }


    @Override
    public Block setSoundType(SoundType sound){
        return super.setSoundType(sound);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.4000000059604645D, 0.8999999761581421D);
    }



    public boolean isOpaqueCube() {
        return false;
    }

    public int getRenderType() {
        return 1;
    }

    public int damageDropped(int meta) {
        return meta;
    }

    public Item getItemDropped(int meta, Random rand, int fortune) {
        return Items.AIR;
    }


}