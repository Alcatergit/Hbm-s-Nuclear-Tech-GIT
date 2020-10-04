package com.hbm.blocks.test;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.TileEntityKeypadBase;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class KeypadTest extends BlockContainer {

	public KeypadTest(Material m, String s) {
		super(m);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityKeypadBase();
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(BlockHorizontal.FACING, placer.getHorizontalFacing().getOpposite()));
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
		TileEntityKeypadBase te = (TileEntityKeypadBase) worldIn.getTileEntity(pos);
		AxisAlignedBB key = te.keypad.client().rayTrace(pos);
		if(key != null) {
			return key;
		}
		return super.getSelectedBoundingBox(state, worldIn, pos);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityKeypadBase te = (TileEntityKeypadBase) worldIn.getTileEntity(pos);
		if(worldIn.isRemote) {
			if(te.keypad.client().isPlayerMouseingOver(pos)) {
				return te.keypad.client().playerClick(pos);
			}
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BlockHorizontal.FACING);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(BlockHorizontal.FACING).getIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		if(meta < 2 || meta > 5)
			return this.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.getFront(2));
		return this.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.getFront(meta));
	}
}
