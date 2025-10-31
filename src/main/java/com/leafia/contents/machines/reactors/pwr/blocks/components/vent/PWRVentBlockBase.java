package com.leafia.contents.machines.reactors.pwr.blocks.components.vent;

import com.hbm.blocks.BlockBase;
import com.leafia.contents.machines.reactors.pwr.blocks.components.PWRComponentBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class PWRVentBlockBase extends BlockBase implements PWRComponentBlock, ITileEntityProvider {
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public PWRVentBlockBase(Material m,String s) {
		super(m,s);
	}
	@Override
	public boolean tileEntityShouldCreate(World world,BlockPos pos) {
		return false;
	}
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{FACING});
	}
	@Override
	public int getMetaFromState(IBlockState state) {
		return (state.getValue(FACING)).getIndex();
	}
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.byIndex(meta);
		return this.getDefaultState().withProperty(FACING, enumfacing);
	}
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}
	public boolean isNormalCube(IBlockState state) {
		return false;
	}
	public boolean isNormalCube(IBlockState state,IBlockAccess world,BlockPos pos) {
		return false;
	}
	@Override
	public void onBlockPlacedBy(World worldIn,BlockPos pos,IBlockState state,EntityLivingBase placer,ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING,EnumFacing.getDirectionFromEntityLiving(pos,placer)));
		correctDirection(worldIn,pos);
	}
	protected final void rotateTarget(World world,BlockPos pos,EnumFacing newDirection) {
		if (!world.isValid(pos)) return;
		IBlockState state = world.getBlockState(pos);
		if (!(state.getBlock() instanceof PWRVentBlockBase)) return;
		if (state.getValue(FACING).equals(newDirection)) return;
		state = state.withProperty(FACING,newDirection);
		world.setBlockState(pos,state);
	}
	public abstract boolean correctDirection(World world,BlockPos pos);
}
