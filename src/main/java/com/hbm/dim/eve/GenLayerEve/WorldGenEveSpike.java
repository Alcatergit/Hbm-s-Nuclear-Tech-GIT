package com.hbm.dim.eve.GenLayerEve;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenEveSpike extends WorldGenerator {

	public boolean generate(World p_76484_1_, Random p_76484_2_, BlockPos pos) {
		while(p_76484_1_.isAirBlock(pos) && pos.getY() > 2) {
			pos.down();
		}

		if(p_76484_1_.getBlockState(pos) != ModBlocks.eve_silt.getDefaultState()) {
			return false;
		} else {
			pos.up(p_76484_2_.nextInt(4));
			int l = p_76484_2_.nextInt(4) + 10;
			int i1 = l / 4 + p_76484_2_.nextInt(2);

			if(i1 > 1 && p_76484_2_.nextInt(2) == 0) {
				pos.up(10 + p_76484_2_.nextInt(30));
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
						float f2 = (float) MathHelper.abs(i2) - 0.25F;

						if((l1 == 0 && i2 == 0 || f1 * f1 + f2 * f2 <= f * f) && (l1 != -k1 && l1 != k1 && i2 != -k1 && i2 != k1 || p_76484_2_.nextFloat() <= 0.75F)) {
							Block block = p_76484_1_.getBlockState(pos.add(l1, j1, i2)).getBlock();

							if(block.getMaterial(block.getDefaultState()) == Material.AIR || block == ModBlocks.eve_silt || block == ModBlocks.eve_rock) {
								this.setBlockAndNotifyAdequately(p_76484_1_, pos.add(l1, j1, i2), ModBlocks.eve_rock.getDefaultState());
							}

							if(j1 != 0 && k1 > 1) {
								block = p_76484_1_.getBlockState(pos.add(l1, -j1, i2)).getBlock();

								if (block.getMaterial(block.getDefaultState()) == Material.AIR || block == ModBlocks.eve_silt || block == ModBlocks.eve_rock) {
									this.setBlockAndNotifyAdequately(p_76484_1_, pos.add(l1, -j1, i2), ModBlocks.eve_rock.getDefaultState());
								}
							}
						}
					}
				}
			}

			j1 = i1 - 1;

			if(j1 < 0) {
				j1 = 0;
			} else if(j1 > 1) {
				j1 = 1;
			}

			for(int j2 = -j1; j2 <= j1; ++j2) {
				k1 = -j1;

				while(k1 <= j1) {
					l1 = pos.getY() - 1;
					int k2 = 50;

					if(Math.abs(j2) == 1 && Math.abs(k1) == 1) {
						k2 = p_76484_2_.nextInt(5);
					}

					while(true) {
						if (l1 > 50) {
							Block block1 = p_76484_1_.getBlockState(pos.add(j2, l1 - pos.getY(), k1)).getBlock();

							if(block1.getMaterial(block1.getDefaultState()) == Material.AIR || block1 == ModBlocks.eve_silt || block1 == ModBlocks.eve_silt || block1 == ModBlocks.eve_silt || block1 == ModBlocks.eve_rock) {
								this.setBlockAndNotifyAdequately(p_76484_1_, pos.add(j2, l1 - pos.getY(), k1), ModBlocks.eve_rock.getDefaultState());
								--l1;
								--k2;

								if(k2 <= 0) {
									l1 -= p_76484_2_.nextInt(5) + 1;
									k2 = p_76484_2_.nextInt(5);
								}

								continue;
							}
						}

						++k1;
						break;
					}
				}
			}

			return true;
		}
	}
}