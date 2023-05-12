package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityDummyPort;
import com.hbm.tileentity.machine.TileEntityMachineChemplant;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DummyBlockChemplant extends DummyOldBase {

	public static boolean safeBreak = false;

	public DummyBlockChemplant(Material materialIn, String s, boolean port) {
		super(materialIn, s, port);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return this == ModBlocks.dummy_port_chemplant ? new TileEntityDummyPort() : new TileEntityDummy();
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if(!safeBreak) {
    		TileEntity te = world.getTileEntity(pos);
    		if(te != null && te instanceof TileEntityDummy) {
    			BlockPos abc = ((TileEntityDummy)te).target;
    			//world.getBlock(a, b, c).breakBlock(world, a, b, c, block, i);
    			if(!world.isRemote && abc != null)
    				world.destroyBlock(abc, true);
    		}
    	}
    	world.removeTileEntity(pos);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
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
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.AIR;
	}
    
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    	return new ItemStack(Item.getItemFromBlock(ModBlocks.machine_chemplant));
    }
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
    		TileEntity te = world.getTileEntity(pos);
    		if(te != null && te instanceof TileEntityDummy) {
    			BlockPos abc = ((TileEntityDummy)te).target;
    			if(abc == null)
    				return false;
    			TileEntityMachineChemplant entity = (TileEntityMachineChemplant) world.getTileEntity(abc);
    			if(entity != null)
    			{
    				player.openGui(MainRegistry.instance, ModBlocks.guiID_machine_chemplant, world, abc.getX(), abc.getY(), abc.getZ());
    			}
    		}
			return true;
		} else {
			return false;
		}
	}

}
