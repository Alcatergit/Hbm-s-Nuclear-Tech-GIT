package com.hbm.world;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class Watz {

	public static String[][] watz = new String[][] {
		{
			"  SSS  ",
			" SSSSS ",
			"SSSSSSS",
			"SSSISSS",
			"SSSSSSS",
			" SSSSS ",
			"  SSS  "
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
			"  SSS  ",
			" SSSSS ",
			"SSSSSSS",
			"SSSISSS",
			"SSSSSSS",
			" SSSSS ",
			"  SSS  "
		}
	};

	public boolean generateReactor(World world, Random rand, BlockPos pos) {
		MutableBlockPos mPos = new BlockPos.MutableBlockPos();
		int x = pos.getX() - 3;
		int y = pos.getY();
		int z = pos.getZ() - 3;

		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 13; j++) {
				for(int k = 0; k < 7; k++) {
					String c = watz[j][i].substring(k, k + 1);
					Block b = Blocks.AIR;

					if(c.equals("W"))
						b = ModBlocks.watz_element;
					else if(c.equals("R"))
						b = ModBlocks.watz_control;
					else if(c.equals("S"))
						b = ModBlocks.watz_end;
					else if(c.equals("K"))
						b = ModBlocks.watz_cooler;
					else if(c.equals("I"))
						b = ModBlocks.watz_conductor;
					else if(c.equals("C"))
						b = ModBlocks.reinforced_brick;
					else if(c.equals("#"))
						b = ModBlocks.watz_core;
					
					world.setBlockState(mPos.setPos(x + i, y + j, z + k), b.getDefaultState());
				}
			}
		}
		world.setBlockState(mPos.setPos(x + 3, y + 6, z + 0), ModBlocks.watz_hatch.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.values()[2]), 3);
		world.setBlockState(mPos.setPos(x + 0, y + 6, z + 3), ModBlocks.watz_hatch.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.values()[4]), 3);
		world.setBlockState(mPos.setPos(x + 3, y + 6, z + 6), ModBlocks.watz_hatch.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.values()[3]), 3);
		world.setBlockState(mPos.setPos(x + 6, y + 6, z + 3), ModBlocks.watz_hatch.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.values()[5]), 3);
		return true;
	}

	public static boolean checkHull(World world, BlockPos pos) {
		MutableBlockPos mPos = new BlockPos.MutableBlockPos();
		int x = pos.getX() - 3;
		int y = pos.getY() - 6;
		int z = pos.getZ() - 3;
		
		boolean flag = true;
		
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 13; j++) {
				for(int k = 0; k < 7; k++) {
					String c = watz[j][i].substring(k, k + 1);
					Block b = Blocks.AIR;
					boolean flag2 = false;

					if(c.equals("W")){
						b = ModBlocks.watz_element;
						flag2 = true;
					}
					else if(c.equals("R")){
						b = ModBlocks.watz_control;
						flag2 = true;
					}
					else if(c.equals("S")){
						b = ModBlocks.watz_end;
						flag2 = true;
					}
					else if(c.equals("K")){
						b = ModBlocks.watz_cooler;
						flag2 = true;
					}
					else if(c.equals("I")){
						b = ModBlocks.watz_conductor;
						flag2 = true;
					}
					else if(c.equals("C")){
						b = ModBlocks.reinforced_brick;
						flag2 = true;
					}
					else if(c.equals("A")){
						b = ModBlocks.watz_hatch;
						flag2 = true;
					}
					else if(c.equals("#")){
						b = ModBlocks.watz_core;
						flag2 = true;
					}

					
					if(flag2){
						if(world.getBlockState(mPos.setPos(x + i, y + j, z + k)).getBlock() != b){
							return false;
						}
					}
				}
			}
		}

		return flag;
	}
}
