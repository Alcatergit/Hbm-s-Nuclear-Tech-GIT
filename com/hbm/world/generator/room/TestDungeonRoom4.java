package com.hbm.world.generator.room;

import java.util.ArrayList;

import com.hbm.blocks.ModBlocks;
import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.CellularDungeonRoom;
import com.hbm.world.generator.DungeonToolbox;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class TestDungeonRoom4 extends CellularDungeonRoom {
	
	public TestDungeonRoom4(CellularDungeon parent, CellularDungeonRoom daisyChain, EnumFacing dir) {
		super(parent);
		this.daisyChain = daisyChain;
		this.daisyDirection = dir;
	}

	public void generateMain(World world, int x, int y, int z) {
		
		super.generateMain(world, x, y, z);
		DungeonToolbox.generateBox(world, x, y + parent.height - 2, z, parent.width, 1, parent.width, new ArrayList<Block>() {
			private static final long serialVersionUID = 4515783293220593960L;

		{ add(Blocks.AIR); add(Blocks.WEB); }});
		
		DungeonToolbox.generateBox(world, x + 1, y, z + 1, parent.width - 2, 1, parent.width - 2, new ArrayList<Block>() {
			private static final long serialVersionUID = 955043091630002469L;

		{ add(ModBlocks.meteor_polished); add(ModBlocks.meteor_polished); add(ModBlocks.meteor_polished); add(ModBlocks.meteor_polished); add(ModBlocks.meteor_polished); add(ModBlocks.meteor_spawner); }});
	}
	
	public void generateWall(World world, int x, int y, int z, EnumFacing wall, boolean door) {
		
		if(wall != EnumFacing.NORTH)
			super.generateWall(world, x, y, z, wall, door);
	}
}