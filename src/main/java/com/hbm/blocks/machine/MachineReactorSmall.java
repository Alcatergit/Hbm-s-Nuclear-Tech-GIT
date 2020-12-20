package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandler;
import com.hbm.interfaces.IMultiBlock;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityMachineReactorSmall;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MachineReactorSmall extends BlockContainer implements IMultiBlock {

	public MachineReactorSmall(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.machineTab);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMachineReactorSmall();
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.machine_reactor_small);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity tileentity = world.getTileEntity(pos);

        if (tileentity instanceof TileEntityMachineReactorSmall)
        {
            InventoryHelper.dropInventoryItems(world, pos, (TileEntityMachineReactorSmall)tileentity);
            
            world.updateComparatorOutputLevel(pos, this);
        }
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if(MultiblockHandler.checkSpace(world, pos, MultiblockHandler.reactorSmallDimension)) {

			DummyBlockMachine.safeBreak = true;
			world.setBlockState(pos.up(1), ModBlocks.dummy_block_reactor_small.getDefaultState());
			TileEntity te = world.getTileEntity(pos.up(1));
			if(te instanceof TileEntityDummy) {
				TileEntityDummy dummy = (TileEntityDummy)te;
				dummy.target = pos;
			}
			world.setBlockState(pos.up(2), ModBlocks.dummy_port_reactor_small.getDefaultState());
			TileEntity te2 = world.getTileEntity(pos.up(2));
			if(te2 instanceof TileEntityDummy) {
				TileEntityDummy dummy = (TileEntityDummy)te2;
				dummy.target = pos;
			}
			DummyBlockMachine.safeBreak = false;
			//
			
		} else
			world.destroyBlock(pos, true);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityMachineReactorSmall entity = (TileEntityMachineReactorSmall) world.getTileEntity(pos);
			if(entity != null)
			{
				player.openGui(MainRegistry.instance, ModBlocks.guiID_reactor_small, world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		} else {
			return false;
		}
	}
}
