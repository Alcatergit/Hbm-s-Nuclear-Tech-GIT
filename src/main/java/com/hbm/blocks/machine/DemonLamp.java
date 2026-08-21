package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.machine.TileEntityDemonLamp;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DemonLamp extends BlockContainer {

	// ==================== Added: Direction attribute ====================
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public DemonLamp(SoundType st, String s) {
		super(Material.IRON);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		super.setSoundType(st);

		// ==================== Add: Set default block state ====================
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public Block setSoundType(SoundType sound){
		return super.setSoundType(sound);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityDemonLamp();
	}

	// ==================== New: Get block status ====================
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	// ==================== Added: Get the block state corresponding to the metadata ====================
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing facing = EnumFacing.byHorizontalIndex(meta);
		return this.getDefaultState().withProperty(FACING, facing);
	}

	// ==================== Added: Get the metadata corresponding to the block state ====================
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex();
	}

	// ==================== New: Set direction based on player's perspective when placing ====================
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		// Regardless of whether the player is facing up or down, obtain the horizontal orientation
		EnumFacing playerFacing = placer.getHorizontalFacing();
		return this.getDefaultState().withProperty(FACING, playerFacing);
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
}
