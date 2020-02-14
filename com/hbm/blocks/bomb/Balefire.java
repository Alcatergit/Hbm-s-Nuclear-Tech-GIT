package com.hbm.blocks.bomb;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.potion.HbmPotion;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Balefire extends BlockFire {

	public Balefire(String s) {
		super();
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(null);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (worldIn.getGameRules().getBoolean("doFireTick")) {
			if (!worldIn.isAreaLoaded(pos, 2))
				return; // Forge: prevent loading unloaded chunks when spreading
						// fire
			if (!this.canPlaceBlockAt(worldIn, pos)) {
				worldIn.setBlockToAir(pos);
			}

			Block block = worldIn.getBlockState(pos.down()).getBlock();
			boolean flag = block.isFireSource(worldIn, pos.down(), EnumFacing.UP);

			int i = ((Integer) state.getValue(AGE)).intValue();

			/* if (!flag && worldIn.isRaining() && this.canDie(worldIn, pos) && rand.nextFloat() < 0.2F + (float)i * 0.03F)
			{
			    worldIn.setBlockToAir(pos);
			}
			else*/
			{
				/* if (i < 15)
				{
				    state = state.withProperty(AGE, Integer.valueOf(i + rand.nextInt(3) / 2));
				    worldIn.setBlockState(pos, state, 4);
				}*/

				worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn) + rand.nextInt(10));

				if (!flag) {
					if (!this.canNeighborCatchFire(worldIn, pos)) {
						if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP) || i > 3) {
							worldIn.setBlockToAir(pos);
						}

						return;
					}

					/* if (!this.canCatchFire(worldIn, pos.down(), EnumFacing.UP) && i == 15 && rand.nextInt(4) == 0)
					{
					    worldIn.setBlockToAir(pos);
					    return;
					}*/
				}

				boolean flag1 = worldIn.isBlockinHighHumidity(pos);
				int j = 0;

				if (flag1) {
					j = -50;
				}

				this.tryCatchFire(worldIn, pos.east(), 300 + j, rand, i, EnumFacing.WEST);
				this.tryCatchFire(worldIn, pos.west(), 300 + j, rand, i, EnumFacing.EAST);
				this.tryCatchFire(worldIn, pos.down(), 250 + j, rand, i, EnumFacing.UP);
				this.tryCatchFire(worldIn, pos.up(), 250 + j, rand, i, EnumFacing.DOWN);
				this.tryCatchFire(worldIn, pos.north(), 300 + j, rand, i, EnumFacing.SOUTH);
				this.tryCatchFire(worldIn, pos.south(), 300 + j, rand, i, EnumFacing.NORTH);

				for (int k = -1; k <= 1; ++k) {
					for (int l = -1; l <= 1; ++l) {
						for (int i1 = -1; i1 <= 4; ++i1) {
							if (k != 0 || i1 != 0 || l != 0) {
								int j1 = 100;

								if (i1 > 1) {
									j1 += (i1 - 1) * 100;
								}

								BlockPos blockpos = pos.add(k, i1, l);
								int k1 = this.getNeighborEncouragement(worldIn, blockpos);

								if (k1 > 0) {
									int l1 = (k1 + 40 + worldIn.getDifficulty().getDifficultyId() * 7) / (i + 30);

									/* if (flag1)
									{
									    l1 /= 2;
									}*/

									if (l1 > 0 && rand.nextInt(j1) <= l1/* && (!worldIn.isRaining() || !this.canDie(worldIn, blockpos))*/) {
										int i2 = i + rand.nextInt(5) / 4;

										if (i2 > 15) {
											i2 = 15;
										}

										worldIn.setBlockState(blockpos, state.withProperty(AGE, Integer.valueOf(i2)), 3);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private boolean canNeighborCatchFire(World worldIn, BlockPos pos) {
		for (EnumFacing enumfacing : EnumFacing.values()) {
			if (this.canCatchFire(worldIn, pos.offset(enumfacing), enumfacing.getOpposite())) {
				return true;
			}
		}

		return false;
	}

	private int getNeighborEncouragement(World worldIn, BlockPos pos) {
		if (!worldIn.isAirBlock(pos)) {
			return 0;
		} else {
			int i = 0;

			for (EnumFacing enumfacing : EnumFacing.values()) {
				i = Math.max(worldIn.getBlockState(pos.offset(enumfacing)).getBlock().getFireSpreadSpeed(worldIn, pos.offset(enumfacing), enumfacing.getOpposite()), i);
			}

			return i;
		}
	}

	private void tryCatchFire(World p_149841_1_, BlockPos pos, int p_149841_5_, Random p_149841_6_, int p_149841_7_, EnumFacing face) {
		int j1 = p_149841_1_.getBlockState(pos).getBlock().getFlammability(p_149841_1_, pos, face);

		if (p_149841_6_.nextInt(p_149841_5_) < j1) {
			boolean flag = p_149841_1_.getBlockState(pos).getBlock() == Blocks.TNT;

			p_149841_1_.setBlockState(pos, this.getDefaultState().withProperty(AGE, 15), 3);

			if (flag) {
				Blocks.TNT.onBlockDestroyedByPlayer(p_149841_1_, pos, p_149841_1_.getBlockState(pos));
			}
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		entityIn.setFire(10);

		if (entityIn instanceof EntityLivingBase)
			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(HbmPotion.radiation, 5 * 20, 9));
	}

}
