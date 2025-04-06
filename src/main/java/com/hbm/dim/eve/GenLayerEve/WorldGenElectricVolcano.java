package com.hbm.dim.eve.GenLayerEve;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenElectricVolcano extends WorldGenerator {

	public Block volBlock;
	public Block mainMat;

	public int height;
	public int width;

	public Block surfaceBlock;
	public Block stoneBlock;

	public WorldGenElectricVolcano(int height, int width, Block surfaceBlock, Block stoneBlock) {
		super();
		this.height = height;
		this.width = width;
		this.surfaceBlock = surfaceBlock;
		this.stoneBlock = stoneBlock;

		this.volBlock = ModBlocks.basalt;
		this.mainMat = Blocks.OBSIDIAN; //ModBlocks.geysir_electric;
	}

	public boolean generate(World p_76484_1_, Random p_76484_2_, BlockPos pos) {

		if(p_76484_2_.nextInt(10) != 0) {
			return false;
		}
		while (p_76484_1_.isAirBlock(pos) && pos.getY() > 2) {
			pos.down();
		}

		if(p_76484_1_.getBlockState(pos).getBlock() != surfaceBlock) {
			return false;
		} else {
			pos.up(p_76484_2_.nextInt(1));
			int l = p_76484_2_.nextInt(4) + height;
			int i1 = l / 4 + p_76484_2_.nextInt(width);

			if(i1 > 1 && p_76484_2_.nextInt(5) == 0) {
				p_76484_2_.nextInt(30);
			}

			int j1;
			int k1;
			int l1;

			for(j1 = 0; j1 < l; ++j1) {
				float f = (1.0F - (float) j1 / (float) l) * (float) i1;
				k1 = MathHelper.ceil(f);

				for(l1 = -k1; l1 <= k1; ++l1) {
					float f1 = (float) MathHelper.abs(l1) - 0.25F;

					for(int i2 = -k1; i2 <= k1; ++i2) {
						float f2 = (float) MathHelper.abs(i2) - 0.75F;

						if((l1 == 0 && i2 == 0 || f1 * f1 + f2 * f2 <= f * f) && (l1 != -k1 && l1 != k1 && i2 != -k1 && i2 != k1 || p_76484_2_.nextFloat() <= 0.75F)) {
							Block block = p_76484_1_.getBlockState(pos.add(l1, j1, i2)).getBlock();

							if(block.getMaterial(block.getDefaultState()) == Material.AIR || block == surfaceBlock || block == stoneBlock) {
								p_76484_1_.setBlockState(pos.add(l1, j1, i2), ModBlocks.basalt.getDefaultState());
							}

							if(j1 != 0 && k1 > 1) {
								block = p_76484_1_.getBlockState(pos.add(l1, -j1, i2)).getBlock();

								if(block.getMaterial(block.getDefaultState()) == Material.AIR || block == surfaceBlock || block == stoneBlock) {
									this.setBlockAndNotifyAdequately(p_76484_1_, pos.add(l1, -j1, i2), ModBlocks.ore_depth_nether_neodymium.getDefaultState());
								}
							}
						}
						if((l1 == 0 && j1 == 0) || f1 * f1 + f2 * f2 > f * f) {
							Block block = p_76484_1_.getBlockState(pos.add(l1, j1, i2)).getBlock();

							if(block.getMaterial(block.getDefaultState()) == Material.AIR || block == surfaceBlock || block == stoneBlock) {
								this.setBlockAndNotifyAdequately(p_76484_1_, pos.add(0, j1, 0), Blocks.AIR.getDefaultState());
							}
						}
						if((l1 == 0 && j1 == 0) || f1 * f1 + f2 * f2 > f * f) {
							Block block = p_76484_1_.getBlockState(pos.add(l1, j1, i2)).getBlock();

							if(block.getMaterial(block.getDefaultState()) == Material.AIR || block == surfaceBlock || block == stoneBlock) {
								this.setBlockAndNotifyAdequately(p_76484_1_, pos.add(0, 5, 0), Blocks.OBSIDIAN.getDefaultState()); // ModBlocks.geysir_electric.getDefaultState());
							}
						}
					}
				}
			}

			return true;
		}
	}
}