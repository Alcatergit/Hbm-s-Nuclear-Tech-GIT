package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IDummy;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityMachineCentrifuge;

import net.minecraft.block.BlockContainer;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DummyBlockCentrifuge extends BlockContainer implements IDummy {

	public static boolean safeBreak = false;
	
	public DummyBlockCentrifuge(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityDummy();
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if(!safeBreak) {
    		TileEntity te = world.getTileEntity(pos);
    		if(te != null && te instanceof TileEntityDummy) {
    			if(!world.isRemote)
    				world.destroyBlock(((TileEntityDummy)te).target, true);
    		}
    	}
    	world.removeTileEntity(pos);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.AIR;
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
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(ModBlocks.machine_centrifuge);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(worldIn.isRemote)
		{
			return true;
		} else if(!playerIn.isSneaking())
		{
    		TileEntity te = worldIn.getTileEntity(pos);
    		if(te != null && te instanceof TileEntityDummy) {
    			int a = ((TileEntityDummy)te).target.getX();
    			int b = ((TileEntityDummy)te).target.getY();
    			int c = ((TileEntityDummy)te).target.getZ();
    			
    			TileEntityMachineCentrifuge entity = (TileEntityMachineCentrifuge) worldIn.getTileEntity(((TileEntityDummy)te).target);
    			if(entity != null)
    			{
    				playerIn.openGui(MainRegistry.instance, ModBlocks.guiID_centrifuge, worldIn, a, b, c);
    			}
    		}
			return true;
		} else {
			return false;
		}
	}
}
