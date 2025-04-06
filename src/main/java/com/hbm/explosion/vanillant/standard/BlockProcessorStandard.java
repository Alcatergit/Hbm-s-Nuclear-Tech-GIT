package com.hbm.explosion.vanillant.standard;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.interfaces.IBlockMutator;
import com.hbm.explosion.vanillant.interfaces.IBlockProcessor;
import com.hbm.explosion.vanillant.interfaces.IDropChanceMutator;
import com.hbm.explosion.vanillant.interfaces.IFortuneMutator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockProcessorStandard implements IBlockProcessor {

	protected IDropChanceMutator chance;
	protected IFortuneMutator fortune;
	protected IBlockMutator convert;

	public BlockProcessorStandard() { }

	public BlockProcessorStandard withChance(IDropChanceMutator chance) {
		this.chance = chance;
		return this;
	}

	public BlockProcessorStandard withFortune(IFortuneMutator fortune) {
		this.fortune = fortune;
		return this;
	}

	public BlockProcessorStandard withBlockEffect(IBlockMutator convert) {
		this.convert = convert;
		return this;
	}

	@Override
	public void process(ExplosionVNT explosion, World world, double x, double y, double z, HashSet<BlockPos> affectedBlocks) {
		Iterator<BlockPos> iterator = affectedBlocks.iterator();
		float dropChance = 1.0F / explosion.size;

		List<BlockPos> blocksToRemove = new ArrayList<>();

		while (iterator.hasNext()) {
			BlockPos pos = iterator.next();
			IBlockState state = world.getBlockState(pos);
			Block block = state.getBlock();

			if (!world.isAirBlock(pos)) {
				if (block.canDropFromExplosion(explosion.compat)) {

					if (chance != null) {
						dropChance = chance.mutateDropChance(explosion, block, pos.getX(), pos.getY(), pos.getZ(), dropChance);
					}

					int dropFortune = (fortune == null) ? 0 : fortune.mutateFortune(explosion, block, pos.getX(), pos.getY(), pos.getZ());

					block.dropBlockAsItemWithChance(world, pos, state, dropChance, dropFortune);
				}

				block.onBlockExploded(world, pos, explosion.compat);

				if (convert != null) {
					convert.mutatePre(explosion, state, pos);
				}
			} else {
				blocksToRemove.add(pos);
			}
		}

		affectedBlocks.removeAll(blocksToRemove);

		if (convert != null) {
			for (BlockPos pos : affectedBlocks) {
				if (world.isAirBlock(pos)) {
					convert.mutatePost(explosion, pos);
				}
			}
		}
	}

	public BlockProcessorStandard setNoDrop() {
		this.chance = new DropChanceMutatorStandard(0F);
		return this;
	}

	public BlockProcessorStandard setAllDrop() {
		this.chance = new DropChanceMutatorStandard(1F);
		return this;
	}

	public BlockProcessorStandard setFortune(int fortune) {
		this.fortune = (explosion, block, x, y, z) -> fortune;
		return this;
	}
}
