package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import api.hbm.block.IDrillInteraction;
import api.hbm.block.IMiningDrill;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

public class BlockCluster extends BlockOre implements IDrillInteraction {

	public BlockCluster(String s) {
		super();
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setHarvestLevel("pickaxe", 1);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune){
		return Items.AIR;
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack){
		if(player instanceof FakePlayer || player == null) {
			return;
		}

		if(!world.isRemote && world.getGameRules().getBoolean("doTileDrops") && !world.restoringBlockSnapshots) {
			
			Item drop = getDrop();
			
			if(drop == null)
				return;
			
			float f = 0.7F;
			double mX = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
			double mY = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
			double mZ = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
			
			EntityItem entityitem = new EntityItem(world, (double) pos.getX() + mX, (double) pos.getY() + mY, (double) pos.getZ() + mZ, new ItemStack(drop));
			entityitem.setPickupDelay(10);
			world.spawnEntity(entityitem);
		}
	}
	
	private Item getDrop() {

		if(this == ModBlocks.cluster_iron)
			return ModItems.crystal_iron;
		if(this == ModBlocks.cluster_titanium)
			return ModItems.crystal_titanium;
		if(this == ModBlocks.cluster_aluminium)
			return ModItems.crystal_aluminium;
		if(this == ModBlocks.cluster_copper)
			return ModItems.crystal_copper;
		if(this == ModBlocks.basalt_gem)
			return ModItems.gem_volcanic;
		
		return null;
	}

	@Override
	public boolean canBreak(World world, int x, int y, int z, IBlockState state, IMiningDrill drill) {
		return drill.getDrillRating() >= 30;
	}

	@Override
	public ItemStack extractResource(World world, int x, int y, int z, IBlockState state, IMiningDrill drill) {
		return drill.getDrillRating() >= 30 ? new ItemStack(this.getDrop()) : null;
	}

	@Override
	public float getRelativeHardness(World world, int x, int y, int z, IBlockState state, IMiningDrill drill) {
		return state.getBlockHardness(world, new BlockPos(x, y, z));
	}
}