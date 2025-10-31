package com.hbm.blocks.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.potion.HbmPotion;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class Balefire extends BlockFire {

	public Balefire(String s) {
		super();
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(null);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override 
	protected boolean canDie(World worldIn, BlockPos pos){
		Block b = worldIn.getBlockState(pos.down()).getBlock();

		return (b != ModBlocks.baleonitite_slaked && 
			b != ModBlocks.baleonitite_1 && 
			b != ModBlocks.baleonitite_2 && 
			b != ModBlocks.baleonitite_3 && 
			b != ModBlocks.baleonitite_4 && 
			b != ModBlocks.baleonitite_core);
	}

	@Override 
	public int getFlammability(Block b){
		if(b != ModBlocks.baleonitite_slaked && 
			b != ModBlocks.baleonitite_1 && 
			b != ModBlocks.baleonitite_2 && 
			b != ModBlocks.baleonitite_3 && 
			b != ModBlocks.baleonitite_4 && 
			b != ModBlocks.baleonitite_core){
			return 20000;
		}
		return super.getEncouragement(b);
	}

	@Override 
	public int getEncouragement(Block b){
		if(b != ModBlocks.baleonitite_slaked && 
			b != ModBlocks.baleonitite_1 && 
			b != ModBlocks.baleonitite_2 && 
			b != ModBlocks.baleonitite_3 && 
			b != ModBlocks.baleonitite_4 && 
			b != ModBlocks.baleonitite_core){
			return 20000;
		}
		return super.getEncouragement(b);
	}

	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		entityIn.setFire(10);

		if (entityIn instanceof EntityLivingBase)
			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(HbmPotion.radiation, 5 * 20, 9));
	}

	private void catchBalefire(World worldIn, BlockPos pos, int chance, Random random, int age, EnumFacing face)
	{
		int i = worldIn.getBlockState(pos).getBlock().getFlammability(worldIn, pos, face);

		if (random.nextInt(chance) < i)
		{
			IBlockState iblockstate = worldIn.getBlockState(pos);

			if (random.nextInt(age + 10) < 8 && !worldIn.isRainingAt(pos))
			{
				int j = age + random.nextInt(5) / 4;

				if (j > 15)
				{
					j = 15;
				}

				worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, Integer.valueOf(j)), 3);
			}
			else
			{
				//worldIn.setBlockToAir(pos);
				worldIn.setBlockState(pos, ModBlocks.ash_balefire.getDefaultState(), 3);
			}

			if (iblockstate.getBlock() == Blocks.TNT)
			{
				Blocks.TNT.onPlayerDestroy(worldIn, pos, iblockstate.withProperty(BlockTNT.EXPLODE, Boolean.valueOf(true)));
			}
		}
	}
	private boolean canNeighborCatchFire(World worldIn, BlockPos pos)
	{
		for (EnumFacing enumfacing : EnumFacing.values())
		{
			if (this.canCatchFire(worldIn, pos.offset(enumfacing), enumfacing.getOpposite()))
			{
				return true;
			}
		}

		return false;
	}
	private int getNeighborEncouragement(World worldIn, BlockPos pos)
	{
		if (!worldIn.isAirBlock(pos))
		{
			return 0;
		}
		else
		{
			int i = 0;

			for (EnumFacing enumfacing : EnumFacing.values())
			{
				i = Math.max(worldIn.getBlockState(pos.offset(enumfacing)).getBlock().getFireSpreadSpeed(worldIn, pos.offset(enumfacing), enumfacing.getOpposite()), i);
			}

			return i;
		}
	}
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (worldIn.getGameRules().getBoolean("doFireTick"))
		{
			if (!worldIn.isAreaLoaded(pos, 2)) return; // Forge: prevent loading unloaded chunks when spreading fire
			if (!this.canPlaceBlockAt(worldIn, pos))
			{
				worldIn.setBlockToAir(pos);
			}

			Block block = worldIn.getBlockState(pos.down()).getBlock();
			boolean flag = !canDie(worldIn,pos); //block.isFireSource(worldIn, pos.down(), EnumFacing.UP);

			int i = ((Integer)state.getValue(AGE)).intValue();

			//if (!flag && worldIn.isRaining() && this.canDie(worldIn, pos) && rand.nextFloat() < 0.2F + (float)i * 0.03F)
			//{
				//worldIn.setBlockToAir(pos);
			//}
			//else
			//{
				if (i < 15)
				{
					state = state.withProperty(AGE, Integer.valueOf(i + rand.nextInt(3) / 2));
					worldIn.setBlockState(pos, state, 4);
				}

				worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn) + rand.nextInt(35));

				if (!flag)
				{
					if (!this.canNeighborCatchFire(worldIn, pos))
					{
						if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP) || i > 3)
						{
							worldIn.setBlockToAir(pos);
						}

						return;
					}

					if (!this.canCatchFire(worldIn, pos.down(), EnumFacing.UP) && i == 15 && rand.nextInt(4) == 0)
					{
						worldIn.setBlockToAir(pos);
						return;
					}
				}

				boolean flag1 = worldIn.isBlockinHighHumidity(pos);
				int j = 0;

				if (flag1)
				{
					j = -50;
				}

				this.catchBalefire(worldIn, pos.east(), 100 + j, rand, i, EnumFacing.WEST);
				this.catchBalefire(worldIn, pos.west(), 100 + j, rand, i, EnumFacing.EAST);
				this.catchBalefire(worldIn, pos.down(), 80 + j, rand, i, EnumFacing.UP);
				this.catchBalefire(worldIn, pos.up(), 80 + j, rand, i, EnumFacing.DOWN);
				this.catchBalefire(worldIn, pos.north(), 100 + j, rand, i, EnumFacing.SOUTH);
				this.catchBalefire(worldIn, pos.south(), 100 + j, rand, i, EnumFacing.NORTH);

				for (int k = -1; k <= 1; ++k)
				{
					for (int l = -1; l <= 1; ++l)
					{
						for (int i1 = -1; i1 <= 4; ++i1)
						{
							if (k != 0 || i1 != 0 || l != 0)
							{
								int j1 = 100;

								if (i1 > 1)
								{
									j1 += (i1 - 1) * 100;
								}

								BlockPos blockpos = pos.add(k, i1, l);
								int k1 = this.getNeighborEncouragement(worldIn, blockpos);

								if (k1 > 0)
								{
									int l1 = (k1 + 40 + worldIn.getDifficulty().getId() * 7) / (i + 30);

									if (flag1)
									{
										l1 /= 2;
									}

									if (l1 > 0 && rand.nextInt(j1) <= l1 && (!worldIn.isRaining() || !this.canDie(worldIn, blockpos)))
									{
										int i2 = i + rand.nextInt(5) / 4;

										if (i2 > 15)
										{
											i2 = 15;
										}

										worldIn.setBlockState(blockpos, state.withProperty(AGE, Integer.valueOf(i2)), 3);
									}
								}
							}
						}
					}
				}
			//}
		}
	}
}
