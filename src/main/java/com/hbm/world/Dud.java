package com.hbm.world;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;


public class Dud extends WorldGenerator {
	protected Block[] GetValidSpawnBlocks()
	{
		return new Block[]
		{
			Blocks.GRASS,
			Blocks.DIRT,
			Blocks.STONE,
			Blocks.SAND,
			Blocks.SANDSTONE,
		};
	}

	public boolean LocationIsValidSpawn(World world, BlockPos pos)
 {

		IBlockState checkBlockState = world.getBlockState(pos.down());
		Block checkBlock = checkBlockState.getBlock();
		Block blockAbove = world.getBlockState(pos).getBlock();
		Block blockBelow = world.getBlockState(pos.down(2)).getBlock();

		for (Block i : GetValidSpawnBlocks())
		{
			if (blockAbove != Blocks.AIR)
			{
				return false;
			}
			if (checkBlock == i)
			{
				return true;
			}
			else if (checkBlock == Blocks.SNOW_LAYER && blockBelow == i)
			{
				return true;
			}
			else if (checkBlockState.getMaterial() == Material.PLANTS && blockBelow == i)
			{
				return true;
			}
		}
		return false;
	}

    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        return generate(world, rand, pos, false);
    }

    public boolean generate(World world, Random rand, BlockPos pos, boolean force) {
        return generate_r0(world, rand, pos, force);
    }

	public boolean generate_r0(World world, Random rand, BlockPos pos, boolean force)
	{
		if(!force && !LocationIsValidSpawn(world, pos)) {
			return false;
		}
		float chance = rand.nextFloat();
        if(chance < 0.4) world.setBlockState(pos, ModBlocks.crashed_balefire.getStateFromMeta(1), 3);
        else if(chance < 0.7) world.setBlockState(pos, ModBlocks.crashed_balefire.getStateFromMeta(2), 3);
        else if(chance < 0.9) world.setBlockState(pos, ModBlocks.crashed_balefire.getDefaultState(), 3);
        else world.setBlockState(pos, ModBlocks.crashed_balefire.getStateFromMeta(3), 3);
        if(GeneralConfig.enableDebugMode)
			System.out.print("[Debug] Successfully spawned dud at " + pos.getX() + " " + pos.getY() +" " + pos.getZ() + "\n");
		return true;

	}
}
