package com.hbm.blocks.generic;

import com.hbm.blocks.BlockEnumMeta;
import com.hbm.blocks.BlockEnums;
import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.hbm.inventory.OreDictManager.DictFrame;
import static com.hbm.items.ItemEnums.EnumChunkType;

public class BlockResourceStone extends BlockEnumMeta {
    public BlockResourceStone() {
        super(Material.ROCK, SoundType.STONE, "stone_resource", BlockEnums.EnumStoneType.class, true, true);
    }

    @Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune) {
        int meta = state.getValue(META);

        if (meta == BlockEnums.EnumStoneType.ASBESTOS.ordinal()) {
            world.setBlockState(pos, ModBlocks.gas_asbestos.getDefaultState(), 3);
        }

        super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess access, BlockPos pos, IBlockState state, int fortune) {
        int meta = state.getValue(META);
        Random rand = ((World) access).rand;

        if (meta == BlockEnums.EnumStoneType.MALACHITE.ordinal()) {
            List<ItemStack> ret = new ArrayList<>();
            ret.add(DictFrame.fromOne(ModItems.chunk_ore, EnumChunkType.MALACHITE, 3 + fortune + rand.nextInt(fortune + 2)));
            return ret;
        }

        return super.getDrops(access, pos, state, fortune);
    }

}
