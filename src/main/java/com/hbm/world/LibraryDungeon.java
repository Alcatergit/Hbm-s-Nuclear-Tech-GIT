package com.hbm.world;

import java.util.Random;

import com.hbm.config.GeneralConfig;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockHalfWoodSlab;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockWoodSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.DungeonHooks;

public class LibraryDungeon extends WorldGenerator
{

	public boolean LocationIsValidSpawn(World world, BlockPos pos)
	{
		IBlockState blockAboveState = world.getBlockState(pos.up(8));
		IBlockState blockBelow = world.getBlockState(pos.down());
		
		if(blockAboveState.getMaterial().isSolid() && blockBelow.getMaterial().isSolid() && pos.getY() - 1 > 4)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		return generate(world, rand, pos, false);
	}
	
	public boolean generate(World world, Random rand, BlockPos pos, boolean force)
	{
		int i = rand.nextInt(1);

		if(i == 0)
		{
		    generate_r0(world, rand, pos.getX(), pos.getY(), pos.getZ(), force);
		}

       return true;

	}

	public boolean generate_r0(World world, Random rand, int x, int y, int z, boolean force)
	{
		if(!force && (!LocationIsValidSpawn(world, new BlockPos(x, y, z)) || !LocationIsValidSpawn(world, new BlockPos(x + 8, y, z)) || !LocationIsValidSpawn(world, new BlockPos(x + 8, y, z + 10)) || !LocationIsValidSpawn(world, new BlockPos(x, y, z + 10))))
		{
			return false;
		}
		MutableBlockPos pos = new BlockPos.MutableBlockPos();

		world.setBlockState(pos.setPos(x + 0, y + 0, z + 0), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 0), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 0, y + 0, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 0, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 0, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 0, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 0, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 0, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 0, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 0, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 0, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 0, z + 10), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 10), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 0, y + 1, z + 0), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 0), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 0, y + 1, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 1), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 1, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 2), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 2), Blocks.AIR.getDefaultState(), 3);

		world.setBlockState(pos.setPos(x + 6, y + 1, z + 2), Blocks.MOB_SPAWNER.getDefaultState(), 2);
        TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getTileEntity(pos.setPos(x + 6, y + 1, z + 2));

        if (tileentitymobspawner != null)
        {
            tileentitymobspawner.getSpawnerBaseLogic().setEntityId(this.pickMobSpawner(rand));
        }
        else
        {
            System.err.println("Failed to fetch mob spawner entity at (" + (x + 6) + ", " + (y + 1) + ", " + (z + 2) + ")");
        }
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 1, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 3), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 3), Blocks.OAK_FENCE.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 1, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 4), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 1, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 5), Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.EAST), 3);
        TileEntityChest tileentitychest = (TileEntityChest)world.getTileEntity(pos.setPos(x + 1, y + 1, z + 5));

        if (tileentitychest != null)
        {
        	tileentitychest.setLootTable(LootTableList.CHESTS_SIMPLE_DUNGEON, rand.nextLong());
           // WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(DUNGEON_CHEST, rand), tileentitychest, ChestGenHooks.getCount(DUNGEON_CHEST, rand));
        }
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 1, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 6), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 6), Blocks.OAK_FENCE.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 1, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 7), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 1, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 8), Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.EAST), 3);
        TileEntityChest tileentitychest1 = (TileEntityChest)world.getTileEntity(pos.setPos(x + 1, y + 1, z + 8));

        if (tileentitychest1 != null)
        {
        	tileentitychest1.setLootTable(LootTableList.CHESTS_SIMPLE_DUNGEON, rand.nextLong());
            //WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(DUNGEON_CHEST, rand), tileentitychest1, ChestGenHooks.getCount(DUNGEON_CHEST, rand));
        }
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 8), Blocks.BEDROCK.getDefaultState(), 3);

		world.setBlockState(pos.setPos(x + 6, y + 1, z + 8), Blocks.MOB_SPAWNER.getDefaultState(), 2);
        TileEntityMobSpawner tileentitymobspawner1 = (TileEntityMobSpawner)world.getTileEntity(pos.setPos(x + 6, y + 1, z + 8));

        if (tileentitymobspawner1 != null)
        {
            tileentitymobspawner1.getSpawnerBaseLogic().setEntityId(this.pickMobSpawner(rand));
        }
        else
        {
            System.err.println("Failed to fetch mob spawner entity at (" + (x + 6) + ", " + (y + 1) + ", " + (z + 8) + ")");
        }
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 1, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 9), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 1, z + 10), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 10), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 0, y + 2, z + 0), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 2, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 0), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 0, y + 2, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 1), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 2, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 2, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 2), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 2, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 2, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 3), Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.EAST), 3);
        TileEntityChest tileentitychest2 = (TileEntityChest)world.getTileEntity(pos.setPos(x + 1, y + 2, z + 3));

        if (tileentitychest2 != null)
        {
        	tileentitychest2.setLootTable(LootTableList.CHESTS_SIMPLE_DUNGEON, rand.nextLong());
           // WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(DUNGEON_CHEST, rand), tileentitychest2, ChestGenHooks.getCount(DUNGEON_CHEST, rand));
        }
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 3), Blocks.OAK_FENCE.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 2, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 2, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 4), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 2, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 2, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 5), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 2, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 2, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 6), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 6), Blocks.OAK_FENCE.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 2, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 2, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 7), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 2, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 2, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 8), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 2, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 2, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 9), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 2, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 2, z + 10), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 2, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 10), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 0, y + 3, z + 0), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 3, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 3, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 3, z + 0), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 0, y + 3, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 3, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 3, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 3, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 3, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 3, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 3, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 3, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 3), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 3), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 3), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 4, y + 3, z + 3), Blocks.DOUBLE_WOODEN_SLAB.getDefaultState().withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 3, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 3, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 3, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 4), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 4), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 4), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 4, y + 3, z + 4), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 3, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 3, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 3, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 5), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 5), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 5), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 4, y + 3, z + 5), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 3, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 3, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 3, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 6), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 6), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 6), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 4, y + 3, z + 6), Blocks.DOUBLE_WOODEN_SLAB.getDefaultState().withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 3, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 3, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 3, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 7), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 7), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 7), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 4, y + 3, z + 7), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 3, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 3, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 3, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 8), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 8), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 8), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 4, y + 3, z + 8), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 3, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 3, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 3, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 9), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 9), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 9), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 4, y + 3, z + 9), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 3, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 3, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 3, z + 10), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 3, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 3, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 3, z + 10), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 0, y + 4, z + 0), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 4, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 4, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 4, z + 0), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 0, y + 4, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 4, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 4, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 4, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 4, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 4, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 4, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 4, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 4, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 4, z + 3), Blocks.OAK_FENCE.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 4, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 4, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 4, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 4), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 4, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 4, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 4, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 4, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 5), Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.EAST), 3);
        TileEntityChest tileentitychest3 = (TileEntityChest)world.getTileEntity(pos.setPos(x + 1, y + 4, z + 5));

        if (tileentitychest3 != null)
        {
        	tileentitychest3.setLootTable(LootTableList.CHESTS_SIMPLE_DUNGEON, rand.nextLong());
           // WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(DUNGEON_CHEST, rand), tileentitychest3, ChestGenHooks.getCount(DUNGEON_CHEST, rand));
        }
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 5), Blocks.BEDROCK.getDefaultState(), 3);

		world.setBlockState(pos.setPos(x + 3, y + 4, z + 5), Blocks.MOB_SPAWNER.getDefaultState(), 2);
        TileEntityMobSpawner tileentitymobspawner2 = (TileEntityMobSpawner)world.getTileEntity(pos.setPos(x + 3, y + 4, z + 5));

        if (tileentitymobspawner2 != null)
        {
            tileentitymobspawner2.getSpawnerBaseLogic().setEntityId(this.pickMobSpawner(rand));
        }
        else
        {
            System.err.println("Failed to fetch mob spawner entity at (" + (x + 3) + ", " + (y + 4) + ", " + (z + 5) + ")");
        }
		world.setBlockState(pos.setPos(x + 4, y + 4, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 4, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 4, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 4, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 6), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 4, z + 6), Blocks.OAK_FENCE.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 4, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 4, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 4, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 7), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 4, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 4, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 4, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 4, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 8), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 4, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 4, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 4, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 4, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 9), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 9), Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.NORTH), 3);
        TileEntityChest tileentitychest4 = (TileEntityChest)world.getTileEntity(pos.setPos(x + 2, y + 4, z + 9));

        if (tileentitychest4 != null)
        {
        	tileentitychest4.setLootTable(LootTableList.CHESTS_SIMPLE_DUNGEON, rand.nextLong());
           // WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(DUNGEON_CHEST, rand), tileentitychest4, ChestGenHooks.getCount(DUNGEON_CHEST, rand));
        }
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 9), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 4, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 4, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 4, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 4, z + 10), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 4, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 4, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 4, z + 10), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 0, y + 5, z + 0), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 5, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 5, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 5, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 5, z + 0), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 0, y + 5, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5, z + 1), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 5, y + 5, z + 1), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 6, y + 5, z + 1), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 7, y + 5, z + 1), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 8, y + 5, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 5, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5, z + 2), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 5, y + 5, z + 2), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 6, y + 5, z + 2), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 7, y + 5, z + 2), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 8, y + 5, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 5, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5, z + 3), Blocks.DOUBLE_WOODEN_SLAB.getDefaultState().withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 5, y + 5, z + 3), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 6, y + 5, z + 3), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 7, y + 5, z + 3), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 8, y + 5, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 5, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 4), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5, z + 4), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 5, y + 5, z + 4), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 6, y + 5, z + 4), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 7, y + 5, z + 4), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 8, y + 5, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 5, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 5), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5, z + 5), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 5, y + 5, z + 5), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 6, y + 5, z + 5), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 7, y + 5, z + 5), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 8, y + 5, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 5, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 6), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5, z + 6), Blocks.DOUBLE_WOODEN_SLAB.getDefaultState().withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 5, y + 5, z + 6), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 6, y + 5, z + 6), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 7, y + 5, z + 6), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 8, y + 5, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 5, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 7), Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.EAST), 3);
        TileEntityChest tileentitychest5 = (TileEntityChest)world.getTileEntity(pos.setPos(x + 1, y + 5, z + 7));

        if (tileentitychest5 != null)
        {
        	tileentitychest5.setLootTable(LootTableList.CHESTS_SIMPLE_DUNGEON, rand.nextLong());
            //WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(DUNGEON_CHEST, rand), tileentitychest5, ChestGenHooks.getCount(DUNGEON_CHEST, rand));
        }
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 5, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 5, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 5, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 5, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 5, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 8), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 5, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 5, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 5, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 5, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 5, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 9), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 9), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 9), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 5, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 5, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 5, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 5, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 5, z + 10), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 5, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 5, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 5, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 5, z + 10), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 0, y + 6, z + 0), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 6, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 6, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 6, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 6, z + 0), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 0, y + 6, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 6, z + 1), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 6, z + 1), Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.SOUTH), 3);
        TileEntityChest tileentitychest6 = (TileEntityChest)world.getTileEntity(pos.setPos(x + 6, y + 6, z + 1));

        if (tileentitychest6 != null)
        {
        	tileentitychest6.setLootTable(LootTableList.CHESTS_SIMPLE_DUNGEON, rand.nextLong());
            //WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(DUNGEON_CHEST, rand), tileentitychest6, ChestGenHooks.getCount(DUNGEON_CHEST, rand));
        }
		world.setBlockState(pos.setPos(x + 7, y + 6, z + 1), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 6, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 6, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 6, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 6, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 6, z + 2), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 6, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 6, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6, z + 3), Blocks.OAK_FENCE.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 6, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 6, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 6, z + 3), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 6, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 6, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 6, z + 4), Blocks.BEDROCK.getDefaultState(), 3);

		world.setBlockState(pos.setPos(x + 5, y + 6, z + 4), Blocks.MOB_SPAWNER.getDefaultState(), 2);
        TileEntityMobSpawner tileentitymobspawner3 = (TileEntityMobSpawner)world.getTileEntity(pos.setPos(x + 5, y + 6, z + 4));

        if (tileentitymobspawner3 != null)
        {
            tileentitymobspawner3.getSpawnerBaseLogic().setEntityId(this.pickMobSpawner(rand));
        }
        else
        {
            System.err.println("Failed to fetch mob spawner entity at (" + (x + 5) + ", " + (y + 6) + ", " + (z + 4) + ")");
        }
		world.setBlockState(pos.setPos(x + 6, y + 6, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 6, z + 4), Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.WEST), 3);
        TileEntityChest tileentitychest7 = (TileEntityChest)world.getTileEntity(pos.setPos(x + 7, y + 6, z + 4));

        if (tileentitychest7 != null)
        {
        	tileentitychest7.setLootTable(LootTableList.CHESTS_SIMPLE_DUNGEON, rand.nextLong());
           // WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(DUNGEON_CHEST, rand), tileentitychest7, ChestGenHooks.getCount(DUNGEON_CHEST, rand));
        }
		world.setBlockState(pos.setPos(x + 8, y + 6, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 6, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 6, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 6, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 6, z + 5), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 6, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 6, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6, z + 6), Blocks.OAK_FENCE.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 6, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 6, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 6, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 6, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 6, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 6, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 6, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 6, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 6, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 6, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 6, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 6, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 6, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 6, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 6, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 6, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 6, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 6, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 6, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 6, z + 10), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 6, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 6, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 6, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 6, z + 10), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 0, y + 7, z + 0), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 7, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 7, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 7, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 7, z + 0), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 0, y + 7, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 7, z + 1), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 7, z + 1), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 7, y + 7, z + 1), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 7, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 7, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 7, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 7, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 7, z + 2), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 7, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 7, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7, z + 3), Blocks.OAK_FENCE.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 7, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 7, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 7, z + 3), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 7, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 7, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 7, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 7, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 7, z + 4), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockHalfWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP).withProperty(BlockHalfWoodSlab.VARIANT, BlockPlanks.EnumType.OAK), 3);
		world.setBlockState(pos.setPos(x + 8, y + 7, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 7, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 7, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 7, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 7, z + 5), getShelf(rand).getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 7, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 7, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7, z + 6), Blocks.OAK_FENCE.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 7, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 7, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 7, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 7, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 7, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 7, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 7, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 7, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 7, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 7, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 7, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 7, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 7, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 7, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 7, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 7, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 7, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 7, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 7, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 7, z + 10), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 7, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 7, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 7, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 7, z + 10), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 0, y + 8, z + 0), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 8, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 8, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 8, z + 0), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 8, z + 0), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 0, y + 8, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 8, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 8, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 8, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 8, z + 1), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 8, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 8, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 8, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 8, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 8, z + 2), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 8, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 8, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 8, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 8, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 8, z + 3), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 8, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 8, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 8, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 8, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 8, z + 4), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 8, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 8, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 8, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 8, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 8, z + 5), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 8, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 8, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 8, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 8, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 8, z + 6), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 8, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 8, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 8, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 8, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 8, z + 7), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 8, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 8, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 8, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 8, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 8, z + 8), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 8, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 8, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 8, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 8, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 8, z + 9), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 0, y + 8, z + 10), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 5, y + 8, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 6, y + 8, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 7, y + 8, z + 10), getBrick(rand), 3);
		world.setBlockState(pos.setPos(x + 8, y + 8, z + 10), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 3);

		generate_r02_last(world, rand, x, y, z, pos);
		return true;

	}
	public boolean generate_r02_last(World world, Random rand, int x, int y, int z, MutableBlockPos pos)
	{

		/*world.setBlock(x + 4, y + 3, z + 2, Blocks.torch, 4, 3);
		world.setBlock(x + 5, y + 3, z + 3, Blocks.torch, 1, 3);
		world.setBlock(x + 5, y + 3, z + 6, Blocks.torch, 1, 3);
		world.setBlock(x + 3, y + 5, z + 3, Blocks.torch, 2, 3);
		world.setBlock(x + 3, y + 5, z + 6, Blocks.torch, 2, 3);
		world.setBlock(x + 4, y + 5, z + 7, Blocks.torch, 3, 3);*/
		world.setBlockState(pos.setPos(x + 4, y + 3, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5, z + 7), Blocks.AIR.getDefaultState(), 3);
		if(GeneralConfig.enableDebugMode)
			System.out.print("[Debug] Successfully spawned library at " + x + " " + y +" " + z + "\n");
		return true;

	}
	public IBlockState getBrick(Random rand) {
		return Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.byMetadata(rand.nextInt(3)));
		
	}
	public Block getShelf(Random rand) {
		int i = rand.nextInt(2);
		if(i == 0)
		{
			return Blocks.PLANKS;
		}
		return Blocks.BOOKSHELF;
	}
    private ResourceLocation pickMobSpawner(Random p_76543_1_)
    {
        return DungeonHooks.getRandomDungeonMob(p_76543_1_);
    }

}