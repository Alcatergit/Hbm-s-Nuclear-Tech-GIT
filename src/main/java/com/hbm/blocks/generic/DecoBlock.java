package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.deco.TileEntityDecoBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DecoBlock extends BlockContainer {

	public static PropertyDirection FACING = BlockHorizontal.FACING;
	
	public static final float f = 0.0625F;
	public static final AxisAlignedBB WALL_WEST_BOX = new AxisAlignedBB(14*f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	public static final AxisAlignedBB WALL_EAST_BOX = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 2*f, 1.0F, 1.0F);
	public static final AxisAlignedBB WALL_NORTH_BOX = new AxisAlignedBB(0.0F, 0.0F, 14*f, 1.0F, 1.0F, 1.0F);
	public static final AxisAlignedBB WALL_SOUTH_BOX = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2*f);
	public static final AxisAlignedBB STEEL_ROOF_BOX = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1*f, 1.0F);
	public static final AxisAlignedBB STEEL_BEAM_BOX = new AxisAlignedBB(7*f, 0.0F, 7*f, 9*f, 1.0F, 9*f);
	public static final AxisAlignedBB SCAFFOLD_EASTWEST_BOX = new AxisAlignedBB(2*f, 0.0F, 0.0F, 14*f, 1.0F, 1.0F);
	public static final AxisAlignedBB SCAFFOLD_NORTHSOUTH_BOX = new AxisAlignedBB(0.0F, 0.0F, 2*f, 1.0F, 1.0F, 14*f);
	
	Random rand = new Random();
	
	public DecoBlock(Material materialIn, String s) {
		super(materialIn);
		this.setRegistryName(s);
		this.setTranslationKey(s);
		this.setCreativeTab(MainRegistry.blockTab);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public Block setSoundType(SoundType sound) {
		return super.setSoundType(sound);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if(this == ModBlocks.steel_scaffold || this == ModBlocks.steel_beam)
			return null;
		return new TileEntityDecoBlock();
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		if(this == ModBlocks.steel_scaffold || this == ModBlocks.steel_beam)
			return false;
		return true;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		if(this == ModBlocks.steel_beam || this == ModBlocks.steel_scaffold)
			return EnumBlockRenderType.MODEL;
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
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing()));
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return super.getItemDropped(state, rand, fortune);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		EnumFacing te = state.getValue(FACING);
		if(this == ModBlocks.steel_wall)
        {
			switch(te)
			{
			case WEST:
            	return WALL_WEST_BOX;
			case NORTH:
            	return WALL_NORTH_BOX;
			case EAST:
            	return WALL_EAST_BOX;
			case SOUTH:
            	return WALL_SOUTH_BOX;
            default:
            	return FULL_BLOCK_AABB;
			}
        } else if(this == ModBlocks.steel_roof){
        	return STEEL_ROOF_BOX;
        } else if(this == ModBlocks.steel_beam){
        	return STEEL_BEAM_BOX;
        } else if(this == ModBlocks.steel_scaffold)
        {
			switch(te)
			{
			case WEST:
				return SCAFFOLD_EASTWEST_BOX;
			case NORTH:
	            return SCAFFOLD_NORTHSOUTH_BOX;
			case EAST:
				return SCAFFOLD_EASTWEST_BOX;
			case SOUTH:
				return SCAFFOLD_NORTHSOUTH_BOX;
            default:
            	return FULL_BLOCK_AABB;
			}
        }
		return FULL_BLOCK_AABB;
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
