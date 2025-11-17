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

public class FWatz {

	public static String[][] fwatz = new String[][] {
		{
			"SSS             SSS",
			"SSS             SSS",
			"SSS             SSS",
			"        XXX        ",
			"        XXX        ",
			"                   ",
			"                   ",
			"       MMMMM       ",
			"   XX  MMMMM  XX   ",
			"   XX  MMMMM  XX   ",
			"   XX  MMMMM  XX   ",
			"       MMMMM       ",
			"                   ",
			"                   ",
			"        XXX        ",
			"        XXX        ",
			"SSS             SSS",
			"SSS             SSS",
			"SSS             SSS"
		},
		{
			"SSS             SSS",
			"STS             STS",
			"SSS             SSS",
			"        XHX        ",
			"        XXX        ",
			"       MMMMM       ",
			"      MMMMMMM      ",
			"     MMMMMMMMM     ",
			"   XXMMMMMMMMMXX   ",
			"   HXMMMMCMMMMXH   ",
			"   XXMMMMMMMMMXX   ",
			"     MMMMMMMMM     ",
			"      MMMMMMM      ",
			"       MMMMM       ",
			"        XXX        ",
			"        XHX        ",
			"SSS             SSS",
			"STS             STS",
			"SSS             SSS"
		},
		{
			"SSS             SSS",
			"STS             STS",
			"SSS             SSS",
			"        XXX        ",
			"       MMMMM       ",
			"      MMMMMMM      ",
			"     MMMMMMMMM     ",
			"    MMMM   MMMM    ",
			"   XMMM     MMMX   ",
			"   XMMM  C  MMMX   ",
			"   XMMM     MMMX   ",
			"    MMMM   MMMM    ",
			"     MMMMMMMMM     ",
			"      MMMMMMM      ",
			"       MMMMM       ",
			"        XXX        ",
			"SSS             SSS",
			"STS             STS",
			"SSS             SSS"
		},
		{
			"SSSX           XSSS",
			"STSX           XSTS",
			"SSSX    XXX    XSSS",
			"XXXX    XXX    XXXX",
			"      MMMMMMM      ",
			"     MMMMMMMMM     ",
			"    MMMM   MMMM    ",
			"    MMM     MMM    ",
			"  XXMM       MMXX  ",
			"  XXMM   C   MMXX  ",
			"  XXMM       MMXX  ",
			"    MMM     MMM    ",
			"    MMMM   MMMM    ",
			"     MMMMMMMMM     ",
			"      MMMMMMM      ",
			"XXXX    XXX    XXXX",
			"SSSX    XXX    XSSS",
			"STSX           XSTS",
			"SSSX           XSSS"
		},
		{
			"XXXXXXXXXXXXXXXXXXX",
			"XXXXXXXXXXXXXXXXXXX",
			"XXXXXXXXXXXXXXXXXXX",
			"XXXXXXXMMMMMXXXXXXX",
			"XXXXXMMMMMMMMMXXXXX",
			"XXXXMMMM   MMMMXXXX",
			"XXXXMMM     MMMXXXX",
			"XXXMMM       MMMXXX",
			"XXXMM         MMXXX",
			"XXXMM    #    MMXXX",
			"XXXMM         MMXXX",
			"XXXMMM       MMMXXX",
			"XXXXMMM     MMMXXXX",
			"XXXXMMMM   MMMMXXXX",
			"XXXXXMMMMMMMMMXXXXX",
			"XXXXXXXMMMMMXXXXXXX",
			"XXXXXXXXXXXXXXXXXXX",
			"XXXXXXXXXXXXXXXXXXX",
			"XXXXXXXXXXXXXXXXXXX"
		},
		{
			"                   ",
			"                   ",
			"                   ",
			"       MTHTM       ",
			"     MMMTMTMMM     ",
			"    MMM     MMM    ",
			"    MM       MM    ",
			"   MM         MM   ",
			"   TT         TT   ",
			"   HM         MH   ",
			"   TT         TT   ",
			"   MM         MM   ",
			"    MM       MM    ",
			"    MMM     MMM    ",
			"     MMMTMTMMM     ",
			"       MTHTM       ",
			"                   ",
			"                   ",
			"                   "
		},
		{
			"                   ",
			"                   ",
			"                   ",
			"       MTTTM       ",
			"     MMMTTTMMM     ",
			"    MMM     MMM    ",
			"    MM       MM    ",
			"   MM         MM   ",
			"   TT         TT   ",
			"   TT         TT   ",
			"   TT         TT   ",
			"   MM         MM   ",
			"    MM       MM    ",
			"    MMM     MMM    ",
			"     MMMTTTMMM     ",
			"       MTTTM       ",
			"                   ",
			"                   ",
			"                   "
		},
		{
			"                   ",
			"                   ",
			"                   ",
			"       MTTTM       ",
			"     MMMTTTMMM     ",
			"    MMM     MMM    ",
			"    MM       MM    ",
			"   MM         MM   ",
			"   TT         TT   ",
			"   TT         TT   ",
			"   TT         TT   ",
			"   MM         MM   ",
			"    MM       MM    ",
			"    MMM     MMM    ",
			"     MMMTTTMMM     ",
			"       MTTTM       ",
			"                   ",
			"                   ",
			"                   "
		},
		{
			"                   ",
			"                   ",
			"                   ",
			"       MTTTM       ",
			"     MMMTTTMMM     ",
			"    MMMM   MMMM    ",
			"    MMM     MMM    ",
			"   MMM       MMM   ",
			"   TT         TT   ",
			"   TT         TT   ",
			"   TT         TT   ",
			"   MMM       MMM   ",
			"    MMM     MMM    ",
			"    MMMM   MMMM    ",
			"     MMMTTTMMM     ",
			"       MTTTM       ",
			"                   ",
			"                   ",
			"                   "
		},
		{
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"      MMTTTMM      ",
			"     MMMTTTMMM     ",
			"    MMMM   MMMM    ",
			"    MMM     MMM    ",
			"    TT       TT    ",
			"    TT       TT    ",
			"    TT       TT    ",
			"    MMM     MMM    ",
			"    MMMM   MMMM    ",
			"     MMMTTTMMM     ",
			"      MMTTTMM      ",
			"                   ",
			"                   ",
			"                   ",
			"                   "
		},
		{
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"       MTTTM       ",
			"      MMTTTMM      ",
			"     MMMTTTMMM     ",
			"    MMMM   MMMM    ",
			"    TTT     TTT    ",
			"    TTT     TTT    ",
			"    TTT     TTT    ",
			"    MMMM   MMMM    ",
			"     MMMTTTMMM     ",
			"      MMTTTMM      ",
			"       MTTTM       ",
			"                   ",
			"                   ",
			"                   ",
			"                   "
		},
		{
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"       MTTTM       ",
			"      MMTTTMM      ",
			"     MMMTTTMMM     ",
			"     TTTTTTTTT     ",
			"     TTTTTTTTT     ",
			"     TTTTTTTTT     ",
			"     MMMTTTMMM     ",
			"      MMTTTMM      ",
			"       MTTTM       ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   "
		},
		{
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"       MTTTM       ",
			"       TTTTT       ",
			"       TTTTT       ",
			"       TTTTT       ",
			"       MTTTM       ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   "
		}
	};

	public void generateHull(World world, Random rand, BlockPos pos) {
		MutableBlockPos mPos = new BlockPos.MutableBlockPos();
		int x = pos.getX() - 9;
		int y = pos.getY();
		int z = pos.getZ() - 9;
		
		for(int i = 0; i < 19; i++) {
			for(int j = 0; j < 13; j++) {
				for(int k = 0; k < 19; k++) {
					String c = fwatz[j][i].substring(k, k + 1);
					Block b = Blocks.AIR;

					if(c.equals("X"))
						b = ModBlocks.fwatz_scaffold;
					if(c.equals("S"))
						b = ModBlocks.fwatz_cooler;
					if(c.equals("T"))
						b = ModBlocks.fwatz_tank;
					if(c.equals("M"))
						b = ModBlocks.fwatz_conductor;
					if(c.equals("C"))
						b = ModBlocks.fwatz_computer;
					if(c.equals("#"))
						b = ModBlocks.fwatz_core;
					
					world.setBlockState(mPos.setPos(x + i, y + j, z + k), b.getDefaultState());
				}
			}
		}		
		
		world.setBlockState(mPos.setPos(x + 3, y + 5, z + 9), ModBlocks.fwatz_hatch.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.WEST), 3);
		world.setBlockState(mPos.setPos(x + 15, y + 5, z + 9), ModBlocks.fwatz_hatch.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.EAST), 3);
		world.setBlockState(mPos.setPos(x + 9, y + 5, z + 15), ModBlocks.fwatz_hatch.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.SOUTH), 3);		
		world.setBlockState(mPos.setPos(x + 9, y + 5, z + 3), ModBlocks.fwatz_hatch.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.NORTH), 3);
        world.setBlockState(mPos.setPos(x + 3, y + 1, z + 9), ModBlocks.fwatz_hatch.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.WEST), 3);
        world.setBlockState(mPos.setPos(x + 15, y + 1, z + 9), ModBlocks.fwatz_hatch.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.EAST), 3);
        world.setBlockState(mPos.setPos(x + 9, y + 1, z + 15), ModBlocks.fwatz_hatch.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.SOUTH), 3);
        world.setBlockState(mPos.setPos(x + 9, y + 1, z + 3), ModBlocks.fwatz_hatch.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.NORTH), 3);
	}

	public static boolean checkHull(World world, BlockPos pos) {
		MutableBlockPos mPos = new BlockPos.MutableBlockPos();
		int x = pos.getX() - 9;
		int y = pos.getY() - 4;
		int z = pos.getZ() - 9;
		
		boolean flag = true;
		
		for(int i = 0; i < 19; i++) {
			for(int j = 0; j < 13; j++) {
				for(int k = 0; k < 19; k++) {
					String c = fwatz[j][i].substring(k, k + 1);
					Block b = Blocks.AIR;
					boolean flag2 = false;

					if(c.equals("X")) {
						b = ModBlocks.fwatz_scaffold;
						flag2 = true;
					}
					if(c.equals("H")) {
						b = ModBlocks.fwatz_hatch;
						flag2 = true;
					}
					if(c.equals("S")) {
						b = ModBlocks.fwatz_cooler;
						flag2 = true;
					}
					if(c.equals("T")) {
						b = ModBlocks.fwatz_tank;
						flag2 = true;
					}
					if(c.equals("M")) {
						b = ModBlocks.fwatz_conductor;
						flag2 = true;
					}
					if(c.equals("C")) {
						b = ModBlocks.fwatz_computer;
						flag2 = true;
					}
					if(c.equals("#")) {
						b = ModBlocks.fwatz_core;
						flag2 = true;
					}
					
					if(flag2)
						if(world.getBlockState(mPos.setPos(x + i, y + j, z + k)).getBlock() != b){
							return false;
						}
				}
			}
		}

		return flag;
	}
}