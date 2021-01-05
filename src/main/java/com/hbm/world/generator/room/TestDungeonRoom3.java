package com.hbm.world.generator.room;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.CellularDungeonRoom;
import com.hbm.world.generator.DungeonToolbox;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TestDungeonRoom3 extends CellularDungeonRoom {

	public TestDungeonRoom3(CellularDungeon parent) {
		super(parent);
	}

	public void generateMain(World world, int x, int y, int z) {
		
		super.generateMain(world, x, y, z);
		DungeonToolbox.generateBox(world, x + parent.width / 2 - 2, y + 1, z + parent.width / 2 - 2, 5, 4, 5, ModBlocks.deco_lead.getDefaultState());
		DungeonToolbox.generateBox(world, x + parent.width / 2 - 1, y + 1, z + parent.width / 2 - 1, 3, 3, 3, ModBlocks.toxic_block.getDefaultState());
		DungeonToolbox.generateBox(world, x + parent.width / 2 - 1, y + 4, z + parent.width / 2 - 1, 3, 1, 3, Blocks.AIR.getDefaultState());
		
		world.setBlockState(new BlockPos(x + parent.width / 2, y + 1, z + parent.width / 2), Blocks.MOB_SPAWNER.getDefaultState(), 2);
        TileEntityMobSpawner tileentitymobspawner2 = (TileEntityMobSpawner)world.getTileEntity(new BlockPos(x + parent.width / 2, y + 1, z + parent.width / 2));

        if (tileentitymobspawner2 != null)
        {
            tileentitymobspawner2.getSpawnerBaseLogic().setEntityId(new ResourceLocation(RefStrings.MODID, "entity_cyber_crab"));
        }
	}
}