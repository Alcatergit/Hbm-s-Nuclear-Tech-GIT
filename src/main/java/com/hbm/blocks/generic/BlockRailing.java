package com.hbm.blocks.generic;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.Library;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRailing extends Block {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public int type;
	
	public BlockRailing(Material materialIn, int type, String s){
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.type = type;
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public boolean isFullCube(IBlockState state){
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
		switch(type){
		case 0:
		case 1:
			return Library.rotateAABB(new AxisAlignedBB(0, 0, 0, 1, 1, 0.125), state.getValue(FACING));
		case 2:
		default:
			return super.getBoundingBox(state, source, pos);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState){
		switch(type){
		case 0:
		case 1:
			addCollisionBoxToList(pos, entityBox, collidingBoxes, state.getCollisionBoundingBox(worldIn, pos));
			return;
		case 2:
			addCollisionBoxToList(pos, entityBox, collidingBoxes, Library.rotateAABB(new AxisAlignedBB(0, 0, 0, 1, 1, 0.125), state.getValue(FACING)));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, Library.rotateAABB(new AxisAlignedBB(0, 0, 0, 1, 1, 0.125), state.getValue(FACING).rotateY()));
			return;
		default:
			super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
		}
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{FACING});
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.byIndex(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
	}
	
	
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
	   return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}
	
	
}
