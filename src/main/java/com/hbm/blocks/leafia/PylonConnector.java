package com.hbm.blocks.leafia;

import com.hbm.blocks.network.energy.PylonBase;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.Library;
import com.hbm.tileentity.leafia.TileEntityPylonConnector;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class PylonConnector extends PylonBase {
	public static final PropertyDirection FACING = BlockDirectional.FACING;
	public PylonConnector(Material materialIn, String s) {
		super(materialIn, s);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityPylonConnector();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {

		float pixel = 0.0625F;
		float min = pixel * 5F;
		float max = pixel * 11F;

		ForgeDirection dir = ForgeDirection.getOrientation(state.getValue(FACING).ordinal()).getOpposite();

		float minX = dir == Library.NEG_X ? 0F : min;
		float maxX = dir == Library.POS_X ? 1F : max;
		float minY = dir == Library.NEG_Y ? 0F : min;
		float maxY = dir == Library.POS_Y ? 1F : max;
		float minZ = dir == Library.NEG_Z ? 0F : min;
		float maxZ = dir == Library.POS_Z ? 1F : max;

		return new AxisAlignedBB(minX,minY,minZ,maxX,maxY,maxZ);
	}
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, facing);
	}
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this,FACING);
	}
	@Override
	public int getMetaFromState(IBlockState state) {
		int facing = state.getValue(FACING).ordinal();
		return facing;
	}
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing facing = EnumFacing.VALUES[meta];
		return this.getDefaultState().withProperty(FACING,facing);
	}
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
}
