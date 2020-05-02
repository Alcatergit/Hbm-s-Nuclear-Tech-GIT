package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.conductor.TileEntityFFDuctBaseMk2;
import com.hbm.tileentity.conductor.TileEntityFFFluidDuctMk2;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFluidPipeMk2 extends BlockContainer {

	private static final float p = 1F / 16F;
	private static final AxisAlignedBB DUCT_BB = new AxisAlignedBB(11 * p / 2, 11 * p / 2, 11 * p / 2, 1 - 11 * p / 2, 1 - 11 * p / 2, 1 - 11 * p / 2);
	
	
	public BlockFluidPipeMk2(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityFFFluidDuctMk2();
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		TileEntity te = worldIn.getTileEntity(pos);
		
		if(te instanceof TileEntityFFDuctBaseMk2){
			((TileEntityFFDuctBaseMk2)te).onNeighborChange();
		}
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		//getActualState appears to be called when the neighbor changes on client, so I can use this to update instead of a buggy packet.
		TileEntity te = worldIn.getTileEntity(pos);
		if(te instanceof TileEntityFFDuctBaseMk2)
			((TileEntityFFDuctBaseMk2)te).onNeighborChange();
		return state;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity te = worldIn.getTileEntity(pos);
		
		if(te instanceof TileEntityFFDuctBaseMk2){
			TileEntityFFDuctBaseMk2.breakBlock(worldIn, pos);
		}
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (world.getTileEntity(pos) instanceof TileEntityFFDuctBaseMk2) {
			TileEntityFFDuctBaseMk2 cable = (TileEntityFFDuctBaseMk2) world.getTileEntity(pos);

			if (cable != null) {
				float p = 1F / 16F;
				float minX = 11 * p / 2 - (cable.connections[5] != null ? (11 * p / 2) : 0);
				float minY = 11 * p / 2 - (cable.connections[1] != null ? (11 * p / 2) : 0);
				float minZ = 11 * p / 2 - (cable.connections[2] != null ? (11 * p / 2) : 0);
				float maxX = 1 - 11 * p / 2 + (cable.connections[3] != null ? (11 * p / 2) : 0);
				float maxY = 1 - 11 * p / 2 + (cable.connections[0] != null ? (11 * p / 2) : 0);
				float maxZ = 1 - 11 * p / 2 + (cable.connections[4] != null ? (11 * p / 2) : 0);

				return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
			}
		}
		return DUCT_BB;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
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
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
}
