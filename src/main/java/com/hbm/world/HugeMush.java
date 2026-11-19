package com.hbm.world;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockMushHuge;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class HugeMush extends WorldGenerator {

	private final IBlockState cap;
	private final IBlockState stem;

	public HugeMush(boolean red) {
		super(false);
		net.minecraft.block.Block b = red ? ModBlocks.mush_block_red : ModBlocks.mush_block;
		this.cap = b.getDefaultState();
		this.stem = b.getDefaultState().withProperty(BlockMushHuge.VARIANT, BlockMushHuge.EnumType.STEM);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos1) {
		MutableBlockPos pos = new BlockPos.MutableBlockPos();
		int x0 = pos1.getX();
		int y0 = pos1.getY();
		int z0 = pos1.getZ();
		fillSquare(world, pos, x0, y0, z0, 1, false);
		fillSquare(world, pos, x0, y0 + 3, z0, 1, false);
		fillSquare(world, pos, x0, y0 + 5, z0, 2, false);
		fillSquare(world, pos, x0, y0 + 6, z0, 4, true);
		fillSquare(world, pos, x0, y0 + 7, z0, 4, false);
		fillSquare(world, pos, x0, y0 + 8, z0, 4, true);
		fillSquare(world, pos, x0, y0 + 9, z0, 3, false);
		fillSquare(world, pos, x0, y0 + 10, z0, 1, false);
		for(int i = 0; i < 8; i++) {
			pos.setPos(x0, y0 + i, z0);
			world.setBlockState(pos, this.stem);
		}
		return true;
	}

	private void fillSquare(World world, MutableBlockPos pos, int x0, int y, int z0, int r, boolean triangle) {
		for(int i = -r; i <= r; i++) {
			for(int j = -r; j <= r; j++) {
				int ai = Math.abs(i);
				int aj = Math.abs(j);
				if(triangle) {
					if((ai == r && aj == r) || (ai == r && aj == r - 1) || (ai == r - 1 && aj == r)) continue;
				} else {
					if(ai == r && aj == r) continue;
				}
				pos.setPos(x0 + i, y, z0 + j);
				world.setBlockState(pos, this.cap);
			}
		}
	}
}