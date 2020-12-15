package com.hbm.world.generator.room;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.TileEntitySafe;
import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.CellularDungeonRoom;
import com.hbm.world.generator.DungeonToolbox;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TestDungeonRoom8 extends CellularDungeonRoom {

	public TestDungeonRoom8(CellularDungeon parent) {
		super(parent);
	}

	public void generateMain(World world, int x, int y, int z) {
		
		super.generateMain(world, x, y, z);
		DungeonToolbox.generateBox(world, x + parent.width / 2 - 3, y + 1, z + parent.width / 2 - 3, 1, parent.height - 2, 1, ModBlocks.meteor_pillar);
		DungeonToolbox.generateBox(world, x + parent.width / 2 + 3, y + 1, z + parent.width / 2 - 3, 1, parent.height - 2, 1, ModBlocks.meteor_pillar);
		DungeonToolbox.generateBox(world, x + parent.width / 2 + 3, y + 1, z + parent.width / 2 + 3, 1, parent.height - 2, 1, ModBlocks.meteor_pillar);
		DungeonToolbox.generateBox(world, x + parent.width / 2 - 3, y + 1, z + parent.width / 2 + 3, 1, parent.height - 2, 1, ModBlocks.meteor_pillar);
		world.setBlockState(new BlockPos(x + parent.width / 2 - 3, y + 3, z + parent.width / 2 - 3), ModBlocks.meteor_brick_chiseled.getDefaultState(), 2);
		world.setBlockState(new BlockPos(x + parent.width / 2 + 3, y + 3, z + parent.width / 2 - 3), ModBlocks.meteor_brick_chiseled.getDefaultState(), 2);
		world.setBlockState(new BlockPos(x + parent.width / 2 + 3, y + 3, z + parent.width / 2 + 3), ModBlocks.meteor_brick_chiseled.getDefaultState(), 2);
		world.setBlockState(new BlockPos(x + parent.width / 2 - 3, y + 3, z + parent.width / 2 + 3), ModBlocks.meteor_brick_chiseled.getDefaultState(), 2);

		DungeonToolbox.generateBox(world, x + 4, y + 1, z + 4, parent.width - 8, 1, parent.width - 8, ModBlocks.meteor_polished);
		
		int i = world.rand.nextInt(8);
		
		switch(i) {
		case 0: world.setBlockState(new BlockPos(x + parent.width / 2, y + 2, z + parent.width / 2), ModBlocks.meteor_brick_chiseled.getDefaultState(), 3); break;
		case 1: world.setBlockState(new BlockPos(x + parent.width / 2, y + 2, z + parent.width / 2), ModBlocks.ntm_dirt.getDefaultState(), 3); break;
		case 2: world.setBlockState(new BlockPos(x + parent.width / 2, y + 2, z + parent.width / 2), ModBlocks.block_starmetal.getDefaultState(), 3); break;
		case 3: world.setBlockState(new BlockPos(x + parent.width / 2, y + 2, z + parent.width / 2), ModBlocks.statue_elb_f.getDefaultState(), 3); break;
		case 4: world.setBlockState(new BlockPos(x + parent.width / 2, y + 2, z + parent.width / 2), ModBlocks.crate_red.getDefaultState(), 3); break;
		case 5: world.setBlockState(new BlockPos(x + parent.width / 2, y + 2, z + parent.width / 2), ModBlocks.balefire.getDefaultState(), 3); break;
		case 6: world.setBlockState(new BlockPos(x + parent.width / 2, y + 2, z + parent.width / 2), ModBlocks.block_meteor.getDefaultState(), 3); break;
		case 7:
			world.setBlockState(new BlockPos(x + parent.width / 2, y + 2, z + parent.width / 2), ModBlocks.safe.getDefaultState(), 3);
			if(world.getTileEntity(new BlockPos(x + parent.width / 2, y + 2, z + parent.width / 2)) instanceof TileEntitySafe) {

				if(world.rand.nextInt(10) == 0)
					((TileEntitySafe)world.getTileEntity(new BlockPos(x + parent.width / 2, y + 2, z + parent.width / 2))).inventory.setStackInSlot(7, new ItemStack(ModItems.book_of_));
				else
					((TileEntitySafe)world.getTileEntity(new BlockPos(x + parent.width / 2, y + 2, z + parent.width / 2))).inventory.setStackInSlot(7, new ItemStack(Items.BOOK));
			}
			break;
		}
		
		/*world.setBlockState(new BlockPos(x + parent.width / 2, y, z + parent.width / 2, Blocks.mob_spawner, 0, 2);
        TileEntityMobSpawner tileentitymobspawner2 = (TileEntityMobSpawner)world.getTileEntity(x + parent.width / 2, y, z + parent.width / 2);

        if (tileentitymobspawner2 != null)
        {
            tileentitymobspawner2.func_145881_a().setEntityName("entity_cyber_crab");
        }*/
	}
}