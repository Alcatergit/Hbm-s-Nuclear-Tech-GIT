package com.hbm.world.generator;

import com.hbm.blocks.ModBlocks;

public class TestDungeon extends CellularDungeon {

	public TestDungeon(int width, int height, int dimX, int dimZ, int tries, int branches) {
		super(width, height, dimX, dimZ, tries, branches);

		this.floor.add(ModBlocks.meteor_polished.getDefaultState());
		this.wall.add(ModBlocks.meteor_brick.getDefaultState());
		this.wall.add(ModBlocks.meteor_brick.getDefaultState());
		this.wall.add(ModBlocks.meteor_brick_mossy.getDefaultState());
		this.wall.add(ModBlocks.meteor_brick_cracked.getDefaultState());
		this.ceiling.add(ModBlocks.block_meteor_broken.getDefaultState());
	}

}
