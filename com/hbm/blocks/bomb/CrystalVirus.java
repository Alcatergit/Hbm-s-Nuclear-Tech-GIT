package com.hbm.blocks.bomb;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class CrystalVirus extends Block {

	public CrystalVirus(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setTickRandomly(true);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public void updateTick(World world, BlockPos pos1, IBlockState state, Random rand) {
		if(GeneralConfig.enableVirus) {
			int x = pos1.getX();
			int y = pos1.getY();
			int z = pos1.getZ();
			MutableBlockPos pos = new BlockPos.MutableBlockPos();
			if(world.getBlockState(pos.setPos(x + 1, y, z)).getBlock() != ModBlocks.crystal_virus && world.getBlockState(pos.setPos(x + 1, y, z)).getBlock() != Blocks.AIR && world.getBlockState(pos.setPos(x + 1, y, z)).getBlock() != ModBlocks.crystal_hardened && world.getBlockState(pos.setPos(x + 1, y, z)).getBlock() != ModBlocks.crystal_pulsar) {
				world.setBlockState(pos.setPos(x + 1, y, z), ModBlocks.crystal_virus.getDefaultState());
			}

			if(world.getBlockState(pos.setPos(x, y + 1, z)).getBlock() != ModBlocks.crystal_virus && world.getBlockState(pos.setPos(x, y + 1, z)).getBlock() != Blocks.AIR && world.getBlockState(pos.setPos(x, y + 1, z)).getBlock() != ModBlocks.crystal_hardened && world.getBlockState(pos.setPos(x, y + 1, z)).getBlock() != ModBlocks.crystal_pulsar) {
				world.setBlockState(pos.setPos(x, y + 1, z), ModBlocks.crystal_virus.getDefaultState());
			}

			if(world.getBlockState(pos.setPos(x, y, z + 1)).getBlock() != ModBlocks.crystal_virus && world.getBlockState(pos.setPos(x, y, z + 1)).getBlock() != Blocks.AIR && world.getBlockState(pos.setPos(x, y, z + 1)).getBlock() != ModBlocks.crystal_hardened && world.getBlockState(pos.setPos(x, y, z + 1)).getBlock() != ModBlocks.crystal_pulsar) {
				world.setBlockState(pos.setPos(x, y, z + 1), ModBlocks.crystal_virus.getDefaultState());
			}

			if(world.getBlockState(pos.setPos(x - 1, y, z)).getBlock() != ModBlocks.crystal_virus && world.getBlockState(pos.setPos(x - 1, y, z)).getBlock() != Blocks.AIR && world.getBlockState(pos.setPos(x - 1, y, z)).getBlock() != ModBlocks.crystal_hardened && world.getBlockState(pos.setPos(x - 1, y, z)).getBlock() != ModBlocks.crystal_pulsar) {
				world.setBlockState(pos.setPos(x - 1, y, z), ModBlocks.crystal_virus.getDefaultState());
			}

			if(world.getBlockState(pos.setPos(x, y - 1, z)).getBlock() != ModBlocks.crystal_virus && world.getBlockState(pos.setPos(x, y - 1, z)).getBlock() != Blocks.AIR && world.getBlockState(pos.setPos(x, y - 1, z)).getBlock() != ModBlocks.crystal_hardened && world.getBlockState(pos.setPos(x, y - 1, z)).getBlock() != ModBlocks.crystal_pulsar) {
				world.setBlockState(pos.setPos(x, y - 1, z), ModBlocks.crystal_virus.getDefaultState());
			}

			if(world.getBlockState(pos.setPos(x, y, z - 1)).getBlock() != ModBlocks.crystal_virus && world.getBlockState(pos.setPos(x, y, z - 1)).getBlock() != Blocks.AIR && world.getBlockState(pos.setPos(x, y, z - 1)).getBlock() != ModBlocks.crystal_hardened && world.getBlockState(pos.setPos(x, y, z - 1)).getBlock() != ModBlocks.crystal_pulsar) {
				world.setBlockState(pos.setPos(x, y, z - 1), ModBlocks.crystal_virus.getDefaultState());
			}
			world.setBlockState(pos.setPos(x, y, z), ModBlocks.crystal_hardened.getDefaultState());
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos1, Block blockIn, BlockPos fromPos) {
		int x = pos1.getX();
		int y = pos1.getY();
		int z = pos1.getZ();
		MutableBlockPos pos = new BlockPos.MutableBlockPos();
		if((world.getBlockState(pos.setPos(x + 1, y, z)).getBlock() == Blocks.AIR || world.getBlockState(pos.setPos(x + 1, y, z)).getBlock() == ModBlocks.crystal_virus || world.getBlockState(pos.setPos(x + 1, y, z)).getBlock() == ModBlocks.crystal_hardened || world.getBlockState(pos.setPos(x + 1, y, z)).getBlock() == ModBlocks.crystal_pulsar) && (world.getBlockState(pos.setPos(x - 1, y, z)).getBlock() == Blocks.AIR || world.getBlockState(pos.setPos(x - 1, y, z)).getBlock() == ModBlocks.crystal_virus || world.getBlockState(pos.setPos(x - 1, y, z)).getBlock() == ModBlocks.crystal_hardened || world.getBlockState(pos.setPos(x - 1, y, z)).getBlock() == ModBlocks.crystal_pulsar)
				&& (world.getBlockState(pos.setPos(x, y + 1, z)).getBlock() == Blocks.AIR || world.getBlockState(pos.setPos(x, y + 1, z)).getBlock() == ModBlocks.crystal_virus || world.getBlockState(pos.setPos(x, y + 1, z)).getBlock() == ModBlocks.crystal_hardened || world.getBlockState(pos.setPos(x, y + 1, z)).getBlock() == ModBlocks.crystal_pulsar) && (world.getBlockState(pos.setPos(x, y - 1, z)).getBlock() == Blocks.AIR || world.getBlockState(pos.setPos(x, y - 1, z)).getBlock() == ModBlocks.crystal_virus || world.getBlockState(pos.setPos(x, y - 1, z)).getBlock() == ModBlocks.crystal_hardened || world.getBlockState(pos.setPos(x, y - 1, z)).getBlock() == ModBlocks.crystal_pulsar)
				&& (world.getBlockState(pos.setPos(x, y, z + 1)).getBlock() == Blocks.AIR || world.getBlockState(pos.setPos(x, y, z + 1)).getBlock() == ModBlocks.crystal_virus || world.getBlockState(pos.setPos(x, y, z + 1)).getBlock() == ModBlocks.crystal_hardened || world.getBlockState(pos.setPos(x, y, z + 1)).getBlock() == ModBlocks.crystal_pulsar) && (world.getBlockState(pos.setPos(x, y, z - 1)).getBlock() == Blocks.AIR || world.getBlockState(pos.setPos(x, y, z - 1)).getBlock() == ModBlocks.crystal_virus || world.getBlockState(pos.setPos(x, y, z - 1)).getBlock() == ModBlocks.crystal_hardened || world.getBlockState(pos.setPos(x, y, z - 1)).getBlock() == ModBlocks.crystal_pulsar) && !world.isRemote) {
			world.setBlockState(pos.setPos(x, y, z), ModBlocks.crystal_hardened.getDefaultState());
		}
	}

}
