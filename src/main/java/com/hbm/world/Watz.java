package com.hbm.world;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class Watz extends WorldGenerator {

	public static String[][] array = new String[][] {
		{
			"SSSSSSS",
			"SSSSSSS",
			"SSSSSSS",
			"SSSISSS",
			"SSSSSSS",
			"SSSSSSS",
			"SSSSSSS"
		},
		{
			"  CCC  ",
			" CWRWC ",
			"CWRKRWC",
			"CRKIKRC",
			"CWRKRWC",
			" CWRWC ",
			"  CCC  "
		},
		{
			"  CCC  ",
			" CWRWC ",
			"CWRKRWC",
			"CRKIKRC",
			"CWRKRWC",
			" CWRWC ",
			"  CCC  "
		},
		{
			"  CCC  ",
			" CWRWC ",
			"CWRKRWC",
			"CRKIKRC",
			"CWRKRWC",
			" CWRWC ",
			"  CCC  "
		},
		{
			"  CCC  ",
			" CWRWC ",
			"CWRKRWC",
			"CRKIKRC",
			"CWRKRWC",
			" CWRWC ",
			"  CCC  "
		},
		{
			"  CCC  ",
			" CWRWC ",
			"CWRKRWC",
			"CRKIKRC",
			"CWRKRWC",
			" CWRWC ",
			"  CCC  "
		},
		{
			"  CAC  ",
			" CWRWC ",
			"CWRKRWC",
			"ARK#KRA",
			"CWRKRWC",
			" CWRWC ",
			"  CAC  "
		},
		{
			"  CCC  ",
			" CWRWC ",
			"CWRKRWC",
			"CRKIKRC",
			"CWRKRWC",
			" CWRWC ",
			"  CCC  "
		},
		{
			"  CCC  ",
			" CWRWC ",
			"CWRKRWC",
			"CRKIKRC",
			"CWRKRWC",
			" CWRWC ",
			"  CCC  "
		},
		{
			"  CCC  ",
			" CWRWC ",
			"CWRKRWC",
			"CRKIKRC",
			"CWRKRWC",
			" CWRWC ",
			"  CCC  "
		},
		{
			"  CCC  ",
			" CWRWC ",
			"CWRKRWC",
			"CRKIKRC",
			"CWRKRWC",
			" CWRWC ",
			"  CCC  "
		},
		{
			"  CCC  ",
			" CWRWC ",
			"CWRKRWC",
			"CRKIKRC",
			"CWRKRWC",
			" CWRWC ",
			"  CCC  "
		},
		{
			"SSSSSSS",
			"SSSSSSS",
			"SSSSSSS",
			"SSSISSS",
			"SSSSSSS",
			"SSSSSSS",
			"SSSSSSS"
		}
	};
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		int i = rand.nextInt(1);

		if(i == 0)
		{
		    generate_r0(worldIn, rand, new BlockPos.MutableBlockPos(position));
		}

       return true;
	}

	public boolean generate_r0(World world, Random rand, MutableBlockPos pos)
	{
		int x = pos.getX() - 3;
		int y = pos.getY();
		int z = pos.getZ() - 3;
		
		world.setBlockState(pos.setPos(x + 0, y + 0, z + 0), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 0), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 0), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 0), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 0), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 0), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 0), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 0, z + 1), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 1), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 1), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 1), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 1), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 1), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 1), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 0, z + 2), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 2), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 2), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 2), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 2), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 2), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 2), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 0, z + 3), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 3), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 3), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 3), ModBlocks.watz_conductor.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 3), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 3), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 3), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 0, z + 4), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 4), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 4), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 4), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 4), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 4), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 4), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 0, z + 5), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 5), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 5), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 5), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 5), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 5), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 5), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 0, z + 6), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 6), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 6), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 6), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 6), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 6), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 6), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 1, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 1, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 1), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 1, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 2), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 1, z + 3), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 3), ModBlocks.watz_conductor.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 3), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 1, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 4), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 1, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 5), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 1, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 2, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 2, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 1), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 2, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 2), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 2, z + 3), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 3), ModBlocks.watz_conductor.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 3), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 2, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 4), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 2, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 5), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 2, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 3, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 3, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 3, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 1), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 3, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 3, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 2), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 3, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 3, z + 3), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 3), ModBlocks.watz_conductor.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 3, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 3), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 3, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 4), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 3, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 3, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 5), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 3, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 3, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 3, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 4, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 4, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 4, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 1), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 4, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 4, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 2), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 4, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 4, z + 3), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 3), ModBlocks.watz_conductor.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 4, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 3), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 4, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 4), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 4, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 4, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 5), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 4, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 4, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 4, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 5, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 5, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 5, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 5, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 1), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 5, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 5, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 5, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 2), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 5, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 5, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 5, z + 3), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 3), ModBlocks.watz_conductor.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 5, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 5, z + 3), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 5, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 4), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 5, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 5, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 5, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 5), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 5, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 5, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 5, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 5, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 5, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 6, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 0), ModBlocks.watz_hatch.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.values()[2]), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 6, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 6, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 6, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 1), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 6, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 6, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 6, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 2), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 6, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 6, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 6, z + 3), ModBlocks.watz_hatch.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.values()[4]), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 3), ModBlocks.watz_core.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 6, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 6, z + 3), ModBlocks.watz_hatch.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.values()[5]), 3);
		world.setBlockState(pos.setPos(x + 0, y + 6, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 4), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 6, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 6, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 6, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 5), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 6, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 6, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 6, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 6), ModBlocks.watz_hatch.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.values()[3]), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 6, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 6, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 7, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 7, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 7, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 7, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 1), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 7, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 7, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 7, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 2), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 7, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 7, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 7, z + 3), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 3), ModBlocks.watz_conductor.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 7, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 7, z + 3), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 7, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 4), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 7, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 7, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 7, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 5), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 7, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 7, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 7, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 7, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 7, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 8, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 8, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 8, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 8, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 1), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 8, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 8, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 8, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 2), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 8, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 8, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 8, z + 3), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 3), ModBlocks.watz_conductor.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 8, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 8, z + 3), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 8, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 4), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 8, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 8, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 8, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 5), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 8, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 8, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 8, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 8, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 8, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 9, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 9, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 9, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 9, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 9, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 9, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 9, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 9, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 9, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 9, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 9, z + 1), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 9, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 9, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 9, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 9, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 9, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 9, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 9, z + 2), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 9, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 9, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 9, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 9, z + 3), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 9, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 9, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 9, z + 3), ModBlocks.watz_conductor.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 9, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 9, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 9, z + 3), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 9, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 9, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 9, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 9, z + 4), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 9, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 9, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 9, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 9, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 9, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 9, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 9, z + 5), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 9, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 9, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 9, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 9, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 9, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 9, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 9, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 9, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 9, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 9, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 10, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 10, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 10, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 10, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 10, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 10, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 10, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 10, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 10, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 10, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 10, z + 1), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 10, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 10, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 10, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 10, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 10, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 10, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 10, z + 2), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 10, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 10, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 10, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 10, z + 3), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 10, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 10, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 10, z + 3), ModBlocks.watz_conductor.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 10, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 10, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 10, z + 3), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 10, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 10, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 10, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 10, z + 4), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 10, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 10, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 10, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 10, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 10, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 10, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 10, z + 5), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 10, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 10, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 10, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 10, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 10, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 10, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 10, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 10, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 10, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 10, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 11, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 11, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 11, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 11, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 11, z + 0), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 11, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 11, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 11, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 11, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 11, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 11, z + 1), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 11, z + 1), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 11, z + 1), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 11, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 11, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 11, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 11, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 11, z + 2), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 11, z + 2), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 11, z + 2), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 11, z + 2), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 11, z + 3), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 11, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 11, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 11, z + 3), ModBlocks.watz_conductor.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 11, z + 3), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 11, z + 3), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 11, z + 3), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 11, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 11, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 11, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 11, z + 4), ModBlocks.watz_cooler.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 11, z + 4), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 11, z + 4), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 11, z + 4), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 11, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 11, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 11, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 11, z + 5), ModBlocks.watz_control.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 11, z + 5), ModBlocks.watz_element.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 11, z + 5), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 11, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 11, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 11, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 11, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 11, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 11, z + 6), ModBlocks.reinforced_brick.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 11, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 11, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 12, z + 0), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 12, z + 0), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 12, z + 0), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 12, z + 0), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 12, z + 0), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 12, z + 0), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 12, z + 0), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 12, z + 1), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 12, z + 1), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 12, z + 1), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 12, z + 1), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 12, z + 1), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 12, z + 1), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 12, z + 1), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 12, z + 2), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 12, z + 2), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 12, z + 2), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 12, z + 2), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 12, z + 2), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 12, z + 2), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 12, z + 2), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 12, z + 3), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 12, z + 3), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 12, z + 3), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 12, z + 3), ModBlocks.watz_conductor.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 12, z + 3), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 12, z + 3), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 12, z + 3), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 12, z + 4), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 12, z + 4), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 12, z + 4), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 12, z + 4), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 12, z + 4), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 12, z + 4), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 12, z + 4), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 12, z + 5), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 12, z + 5), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 12, z + 5), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 12, z + 5), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 12, z + 5), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 12, z + 5), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 12, z + 5), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 12, z + 6), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 12, z + 6), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 12, z + 6), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 12, z + 6), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 12, z + 6), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 12, z + 6), ModBlocks.watz_end.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 12, z + 6), ModBlocks.watz_end.getDefaultState(), 3);
		return true;

	}
}
