package com.hbm.blocks.bomb;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.explosion.ExplosionChaos;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class CheaterVirus extends Block {

	static boolean protect = true;

	public CheaterVirus(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setTickRandomly(true);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		if(CheaterVirus.protect)
			worldIn.setBlockState(pos, state, 2);
	}

	@Override
	public void updateTick(World world, BlockPos pos1, IBlockState state, Random rand) {
		if(GeneralConfig.enableVirus) {
			int x = pos1.getX();
			int y = pos1.getY();
			int z = pos1.getZ();
			MutableBlockPos pos = new BlockPos.MutableBlockPos();
			if(world.getBlockState(pos.setPos(x + 1, y, z)).getBlock() != ModBlocks.cheater_virus && world.getBlockState(pos.setPos(x + 1, y, z)).getBlock() != Blocks.AIR && world.getBlockState(pos.setPos(x + 1, y, z)).getBlock() != ModBlocks.cheater_virus) {
				world.setBlockState(pos.setPos(x + 1, y, z), ModBlocks.cheater_virus.getDefaultState());
				;
			}

			if(world.getBlockState(pos.setPos(x, y + 1, z)).getBlock() != ModBlocks.cheater_virus && world.getBlockState(pos.setPos(x, y + 1, z)).getBlock() != Blocks.AIR && world.getBlockState(pos.setPos(x, y + 1, z)).getBlock() != ModBlocks.cheater_virus) {
				world.setBlockState(pos.setPos(x, y + 1, z), ModBlocks.cheater_virus.getDefaultState());
				;
			}

			if(world.getBlockState(pos.setPos(x, y, z + 1)).getBlock() != ModBlocks.cheater_virus && world.getBlockState(pos.setPos(x, y, z + 1)).getBlock() != Blocks.AIR && world.getBlockState(pos.setPos(x, y, z + 1)).getBlock() != ModBlocks.cheater_virus) {
				world.setBlockState(pos.setPos(x, y, z + 1), ModBlocks.cheater_virus.getDefaultState());
				;
			}

			if(world.getBlockState(pos.setPos(x - 1, y, z)).getBlock() != ModBlocks.cheater_virus && world.getBlockState(pos.setPos(x - 1, y, z)).getBlock() != Blocks.AIR && world.getBlockState(pos.setPos(x - 1, y, z)).getBlock() != ModBlocks.cheater_virus) {
				world.setBlockState(pos.setPos(x - 1, y, z), ModBlocks.cheater_virus.getDefaultState());
				;
			}

			if(world.getBlockState(pos.setPos(x, y - 1, z)).getBlock() != ModBlocks.cheater_virus && world.getBlockState(pos.setPos(x, y - 1, z)).getBlock() != Blocks.AIR && world.getBlockState(pos.setPos(x, y - 1, z)).getBlock() != ModBlocks.cheater_virus) {
				world.setBlockState(pos.setPos(x, y - 1, z), ModBlocks.cheater_virus.getDefaultState());
				;
			}

			if(world.getBlockState(pos.setPos(x, y, z - 1)).getBlock() != ModBlocks.cheater_virus && world.getBlockState(pos.setPos(x, y, z - 1)).getBlock() != Blocks.AIR && world.getBlockState(pos.setPos(x, y, z - 1)).getBlock() != ModBlocks.cheater_virus) {
				world.setBlockState(pos.setPos(x, y, z - 1), ModBlocks.cheater_virus.getDefaultState());
				;
			}

			protect = false;
			world.setBlockState(pos.setPos(x, y, z), Blocks.AIR.getDefaultState());
			protect = true;
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos1, Block blockIn, BlockPos fromPos) {
		int x = pos1.getX();
		int y = pos1.getY();
		int z = pos1.getZ();
		MutableBlockPos pos = new BlockPos.MutableBlockPos();
		if((world.getBlockState(pos.setPos(x + 1, y, z)).getBlock() == Blocks.AIR || world.getBlockState(pos.setPos(x + 1, y, z)).getBlock() == ModBlocks.cheater_virus || world.getBlockState(pos.setPos(x + 1, y, z)).getBlock() == ModBlocks.cheater_virus_seed) && (world.getBlockState(pos.setPos(x - 1, y, z)).getBlock() == Blocks.AIR || world.getBlockState(pos.setPos(x - 1, y, z)).getBlock() == ModBlocks.cheater_virus || world.getBlockState(pos.setPos(x - 1, y, z)).getBlock() == ModBlocks.cheater_virus_seed) && (world.getBlockState(pos.setPos(x, y + 1, z)).getBlock() == Blocks.AIR || world.getBlockState(pos.setPos(x, y + 1, z)).getBlock() == ModBlocks.cheater_virus || world.getBlockState(pos.setPos(x, y + 1, z)).getBlock() == ModBlocks.cheater_virus_seed)
				&& (world.getBlockState(pos.setPos(x, y - 1, z)).getBlock() == Blocks.AIR || world.getBlockState(pos.setPos(x, y - 1, z)).getBlock() == ModBlocks.cheater_virus || world.getBlockState(pos.setPos(x, y - 1, z)).getBlock() == ModBlocks.cheater_virus_seed) && (world.getBlockState(pos.setPos(x, y, z + 1)).getBlock() == Blocks.AIR || world.getBlockState(pos.setPos(x, y, z + 1)).getBlock() == ModBlocks.cheater_virus || world.getBlockState(pos.setPos(x, y, z + 1)).getBlock() == ModBlocks.cheater_virus_seed) && (world.getBlockState(pos.setPos(x, y, z - 1)).getBlock() == Blocks.AIR || world.getBlockState(pos.setPos(x, y, z - 1)).getBlock() == ModBlocks.cheater_virus || world.getBlockState(pos.setPos(x, y, z - 1)).getBlock() == ModBlocks.cheater_virus_seed) && !world.isRemote) {
			protect = false;
			world.setBlockState(pos.setPos(x, y, z), Blocks.AIR.getDefaultState());
			ExplosionChaos.spreadVirus(world, x, y, z, 5);
			protect = true;
		}
	}

	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		if(entityIn instanceof EntityLivingBase) {
			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.WITHER, 60 * 60 * 60, 9));
		}
	}

}
