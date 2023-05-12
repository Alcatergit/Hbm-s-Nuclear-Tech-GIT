package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityDummyPort;

import net.minecraft.block.Block;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DummyBlockMachine extends DummyOldBase {

	public static boolean safeBreak = false;
	
	private int id;
	private Block drop;
	float oX = 0;
	float oY = 0;
	float oZ = 0;
	float dX = 1;
	float dY = 1;
	float dZ = 1;
	public AxisAlignedBB dummy_BB = FULL_BLOCK_AABB;
	
	public DummyBlockMachine(Material materialIn, String s, boolean port) {
		super(materialIn, s, port);
	}
	
	public DummyBlockMachine(Material materialIn, String s, boolean port, int id, Block drop) {
		super(materialIn, s, port);
		this.id = id;
		this.drop = drop;
	}
	
	public DummyBlockMachine setBounds(float oX, float oY, float oZ, float dX, float dY, float dZ) {

		oX *= 0.0625F;
		oY *= 0.0625F;
		oZ *= 0.0625F;
		dX *= 0.0625F;
		dY *= 0.0625F;
		dZ *= 0.0625F;
		this.dummy_BB = new AxisAlignedBB(oX, oY, oZ, dX, dY, dZ);
		return this;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return port ? new TileEntityDummyPort() : new TileEntityDummy();
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
    		TileEntity te = world.getTileEntity(pos);
    		if(te != null && te instanceof TileEntityDummy) {
    		
    			if(!world.isRemote)
    				world.destroyBlock(((TileEntityDummy)te).target, true);
    		}
    	world.removeTileEntity(pos);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
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
		return new ItemStack(Item.getItemFromBlock(drop));
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
    			int a = ((TileEntityDummy)te).target.getX();
    			int b = ((TileEntityDummy)te).target.getY();
    			int c = ((TileEntityDummy)te).target.getZ();
    			
    			if(te != null)
    			{
    				player.openGui(MainRegistry.instance, id, world, a, b, c);
    			}
    		}
			return true;
		} else {
			return false;
		}
	}
	
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return this.dummy_BB;
	}

}
