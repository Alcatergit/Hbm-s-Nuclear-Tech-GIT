package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachineGenerator;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class MachineGenerator extends BlockContainer {

	
	
	public MachineGenerator(Material m, String s) {
		super(m);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.machineTab);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMachineGenerator();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		//return super.getItemDropped(state, rand, fortune);
		return Item.getItemFromBlock(ModBlocks.machine_reactor_small);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		} else if (!player.isSneaking()) {
			TileEntityMachineGenerator entity = (TileEntityMachineGenerator) world.getTileEntity(pos);
			if (entity != null) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_machine_generator, world, pos.getX(), pos.getY(),
						pos.getZ());
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityMachineGenerator) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityMachineGenerator) tileentity);
			worldIn.updateComparatorOutputLevel(pos, this);
		}
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion explosionIn) {
		if (!world.isRemote)
        {
			TileEntityMachineGenerator entity = (TileEntityMachineGenerator) world.getTileEntity(pos);
			if(entity != null && world.isBlockLoaded(pos))
			{
				world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 18.0F, true);
		    	ExplosionNukeGeneric.wasteNoSchrab(world, pos, 35);
		    	world.setBlockState(pos, Blocks.FLOWING_LAVA.getDefaultState());
			}
        }
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}
