package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import com.hbm.saveddata.TomSaveData;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import java.util.Random;

/*
 *   ___________
 *  /           \
 * |\___________/|
 * | ,           |
 * | |         ` |
 * | |           |
 * | '         . |
 *  \___________/
 *
 *      PU-238
 *
 */
public class BlockDirt extends Block {

    public BlockDirt(Material mat) {
        super(mat);
    }

    public BlockDirt(Material mat, boolean tick, String s) {
        super(mat);
        this.setTranslationKey(s);
        this.setRegistryName(s);
        this.setTickRandomly(tick);
        this.setSoundType(SoundType.GROUND);
        ModBlocks.ALL_BLOCKS.add(this);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(Blocks.DIRT);
    }

	/*@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				for(int k = -1; k < 2; k++) {
					Block b = world.getBlock(x + i, y + j, z + k);
					if(b instanceof BlockGrass) {
						world.setBlock(x, y, z, Blocks.dirt);
					}
				}
			}
		}
	}*/

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {

        if (!world.isRemote) {
            TomSaveData data = TomSaveData.forWorld(world);

            int light = Math.max(world.getLightFor(EnumSkyBlock.BLOCK, pos.up()), (int) (world.getLight(pos.up()) * (1 - data.dust)));
            if (light >= 9 && data.fire == 0) {
                world.setBlockState(pos, Blocks.GRASS.getDefaultState());
                if (world.getBlockState(pos.down()) == Blocks.DIRT) {
                    world.setBlockState(pos.down(), ModBlocks.impact_dirt.getDefaultState());
                }
            }
        }
    }
}