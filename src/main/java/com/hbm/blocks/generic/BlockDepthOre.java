package com.hbm.blocks.generic;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

import static com.hbm.blocks.OreEnumUtil.OreEnum;

public class BlockDepthOre extends BlockDepth {

    protected final OreEnum oreEnum;

    public BlockDepthOre(String s, OreEnum oreEnum) {
        super(s);
        this.oreEnum = oreEnum;
    }


    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

        Random rand = world instanceof World ? ((World) world).rand : RANDOM;

        int count = (oreEnum == null) ? quantityDropped(state, fortune, rand) : oreEnum.quantityFunction.apply(state, fortune, rand);

        for (int i = 0; i < count; i++) {
            ItemStack droppedItem;

            if (oreEnum == null) {
                droppedItem = new ItemStack(this.getItemDropped(state, rand, fortune), 1, this.damageDropped(state));
            } else {
                droppedItem = oreEnum.dropFunction.apply(state, rand);
            }

            if (droppedItem.getItem() != Items.AIR) {
                drops.add(droppedItem);
            }
        }
    }

}