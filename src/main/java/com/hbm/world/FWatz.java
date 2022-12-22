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
			"        XXX        ",
			"        XXX        ",
			"       MMMMM       ",
			"      MMMMMMM      ",
			"     MMMMMMMMM     ",
			"   XXMMMMMMMMMXX   ",
			"   XXMMMMCMMMMXX   ",
			"   XXMMMMMMMMMXX   ",
			"     MMMMMMMMM     ",
			"      MMMMMMM      ",
			"       MMMMM       ",
			"        XXX        ",
			"        XXX        ",
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
			"    MMMMPPPMMMM    ",
			"   XMMMPPPPPMMMX   ",
			"   XMMMPPCPPMMMX   ",
			"   XMMMPPPPPMMMX   ",
			"    MMMMPPPMMMM    ",
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
			"    MMMMPPPMMMM    ",
			"    MMMPPPPPMMM    ",
			"  XXMMPPPPPPPMMXX  ",
			"  XXMMPPPCPPPMMXX  ",
			"  XXMMPPPPPPPMMXX  ",
			"    MMMPPPPPMMM    ",
			"    MMMMPPPMMMM    ",
			"     MMMMMMMMM     ",
			"      MMMMMMM      ",
			"XXXX    XXX    XXXX",
			"SSSX    XXX    XSSS",
			"STSX           XSTS",
			"SSSX           XSSS"
		},
		{
			"SSSXXXXXXXXXXXXXSSS",
			"STSXXXXXXXXXXXXXSTS",
			"SSSXXXXXXXXXXXXXSSS",
			"XXXXXXXMMMMMXXXXXXX",
			"XXXXXMMMMMMMMMXXXXX",
			"XXXXMMMMPPPMMMMXXXX",
			"XXXXMMMPPPPPMMMXXXX",
			"XXXMMMPPPPPPPMMMXXX",
			"XXXMMPPPPPPPPPMMXXX",
			"XXXMMPPPPCPPPPMMXXX",
			"XXXMMPPPPPPPPPMMXXX",
			"XXXMMMPPPPPPPMMMXXX",
			"XXXXMMMPPPPPMMMXXXX",
			"XXXXMMMMPPPMMMMXXXX",
			"XXXXXMMMMMMMMMXXXXX",
			"XXXXXXXMMMMMXXXXXXX",
			"SSSXXXXXXXXXXXXXSSS",
			"STSXXXXXXXXXXXXXSTS",
			"SSSXXXXXXXXXXXXXSSS"
		},
		{
			"SSS             SSS",
			"STS             STS",
			"SSS             SSS",
			"       MMHMM       ",
			"     MMMMMMMMM     ",
			"    MMMPPPPPMMM    ",
			"    MMPPPPPPPMM    ",
			"   MMPPPPPPPPPMM   ",
			"   MMPPPPPPPPPMM   ",
			"   HMPPPPCPPPPMH   ",
			"   MMPPPPPPPPPMM   ",
			"   MMPPPPPPPPPMM   ",
			"    MMPPPPPPPMM    ",
			"    MMMPPPPPMMM    ",
			"     MMMMMMMMM     ",
			"       MMHMM       ",
			"SSS             SSS",
			"STS             STS",
			"SSS             SSS"
		},
		{
			"SSS             SSS",
			"STS             STS",
			"SSS             SSS",
			"       MTTTM       ",
			"     MMMTTTMMM     ",
			"    MMMPPPPPMMM    ",
			"    MMPPPPPPPMM    ",
			"   MMPPPPPPPPPMM   ",
			"   TTPPPPPPPPPTT   ",
			"   TTPPPP#PPPPTT   ",
			"   TTPPPPPPPPPTT   ",
			"   MMPPPPPPPPPMM   ",
			"    MMPPPPPPPMM    ",
			"    MMMPPPPPMMM    ",
			"     MMMTTTMMM     ",
			"       MTTTM       ",
			"SSS             SSS",
			"STS             STS",
			"SSS             SSS"
		},
		{
			"SSS             SSS",
			"STS             STS",
			"SSS             SSS",
			"       MMMMM       ",
			"     MMMMMMMMM     ",
			"    MMMPPPPPMMM    ",
			"    MMPPPPPPPMM    ",
			"   MMPPPPPPPPPMM   ",
			"   MMPPPPPPPPPMM   ",
			"   MMPPPPCPPPPMM   ",
			"   MMPPPPPPPPPMM   ",
			"   MMPPPPPPPPPMM   ",
			"    MMPPPPPPPMM    ",
			"    MMMPPPPPMMM    ",
			"     MMMMMMMMM     ",
			"       MMMMM       ",
			"SSS             SSS",
			"STS             STS",
			"SSS             SSS"
		},
		{
			"                   ",
			"                   ",
			"                   ",
			"       MMMMM       ",
			"     MMMMMMMMM     ",
			"    MMMMPPPMMMM    ",
			"    MMMPPPPPMMM    ",
			"   MMMPPPPPPPMMM   ",
			"   MMPPPPPPPPPMM   ",
			"   MMPPPPCPPPPMM   ",
			"   MMPPPPPPPPPMM   ",
			"   MMMPPPPPPPMMM   ",
			"    MMMPPPPPMMM    ",
			"    MMMMPPPMMMM    ",
			"     MMMMMMMMM     ",
			"       MMMMM       ",
			"                   ",
			"                   ",
			"                   "
		},
		{
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"      MMMMMMM      ",
			"     MMMMMMMMM     ",
			"    MMMMPPPMMMM    ",
			"    MMMPPPPPMMM    ",
			"    MMPPPPPPPMM    ",
			"    MMPPPCPPPMM    ",
			"    MMPPPPPPPMM    ",
			"    MMMPPPPPMMM    ",
			"    MMMMPPPMMMM    ",
			"     MMMMMMMMM     ",
			"      MMMMMMM      ",
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
			"       MMMMM       ",
			"      MMMMMMM      ",
			"     MMMMMMMMM     ",
			"    MMMMPPPMMMM    ",
			"    MMMPPPPPMMM    ",
			"    MMMPPCPPMMM    ",
			"    MMMPPPPPMMM    ",
			"    MMMMPPPMMMM    ",
			"     MMMMMMMMM     ",
			"      MMMMMMM      ",
			"       MMMMM       ",
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
			"       MMMMM       ",
			"      MMMMMMM      ",
			"     MMMMMMMMM     ",
			"     MMMMMMMMM     ",
			"     MMMMCMMMM     ",
			"     MMMMMMMMM     ",
			"     MMMMMMMMM     ",
			"      MMMMMMM      ",
			"       MMMMM       ",
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
			"       MMMMM       ",
			"       MMMMM       ",
			"       MMMMM       ",
			"       MMMMM       ",
			"       MMMMM       ",
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

	}

	public static boolean checkHull(World world, BlockPos pos) {
		MutableBlockPos mPos = new BlockPos.MutableBlockPos();
		int x = pos.getX() - 9;
		int y = pos.getY() - 6;
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
	
	public static void fillPlasma(World world, BlockPos pos) {
		MutableBlockPos mPos = new BlockPos.MutableBlockPos();
		int x = pos.getX() - 9;
		int y = pos.getY() - 6;
		int z = pos.getZ() - 9;
		
		for(int i = 0; i < 19; i++) {
			for(int j = 0; j < 13; j++) {
				for(int k = 0; k < 19; k++) {
					String c = fwatz[j][i].substring(k, k + 1);

					if(c.equals("P"))
						world.setBlockState(mPos.setPos(x + i, y + j, z + k), ModBlocks.fwatz_plasma.getDefaultState());
				}
			}
		}
	}
	
	public static void emptyPlasma(World world, BlockPos pos) {
		MutableBlockPos mPos = new BlockPos.MutableBlockPos();
		int x = pos.getX() - 9;
		int y = pos.getY() - 6;
		int z = pos.getZ() - 9;
		
		for(int i = 0; i < 19; i++) {
			for(int j = 0; j < 13; j++) {
				for(int k = 0; k < 19; k++) {
					String c = fwatz[j][i].substring(k, k + 1);

					if(c.equals("P"))
						if(world.getBlockState(mPos.setPos(x + i, y + j, z + k)).getBlock() == ModBlocks.fwatz_plasma)
							world.setBlockState(mPos.setPos(x + i, y + j, z + k), Blocks.AIR.getDefaultState());
				}
			}
		}
	}
	
	public static boolean getPlasma(World world, BlockPos pos) {
		MutableBlockPos mPos = new BlockPos.MutableBlockPos();
		int x = pos.getX() - 9;
		int y = pos.getY() - 6;
		int z = pos.getZ() - 9;
		
		boolean flag = false;
		
		for(int i = 0; i < 19; i++) {
			for(int j = 0; j < 13; j++) {
				for(int k = 0; k < 19; k++) {
					String c = fwatz[j][i].substring(k, k + 1);
					
					if(c.equals("P") && world.getBlockState(mPos.setPos(x + i, y + j, z + k)).getBlock() == ModBlocks.fwatz_plasma)
						return true;
				}
			}
		}

		return flag;
	}

}