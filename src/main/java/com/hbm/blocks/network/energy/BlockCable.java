package com.hbm.blocks.network.energy;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.lib.Library;
import com.hbm.tileentity.network.energy.TileEntityCableBaseNT;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCable extends BlockContainer {

	public BlockCable(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.blockTab);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCableBaseNT();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (world.getTileEntity(pos) instanceof TileEntityCableBaseNT) {
			TileEntityCableBaseNT cable = (TileEntityCableBaseNT) world.getTileEntity(pos);

			boolean posX = Library.canConnect(world, cable.getPos().add(1, 0, 0), Library.POS_X);
			boolean negX = Library.canConnect(world, cable.getPos().add(-1, 0, 0), Library.NEG_X);
			boolean posY = Library.canConnect(world, cable.getPos().add(0, 1, 0), Library.POS_Y);
			boolean negY = Library.canConnect(world, cable.getPos().add(0, -1, 0), Library.NEG_Y);
			boolean posZ = Library.canConnect(world, cable.getPos().add(0, 0, 1), Library.POS_Z);
			boolean negZ = Library.canConnect(world, cable.getPos().add(0, 0, -1), Library.NEG_Z);
			

			if (cable != null) {
				float p = 1F / 16F;
				float minX = 11 * p / 2 - (negX ? (11 * p / 2) : 0);
				float minY = 11 * p / 2 - (negY ? (11 * p / 2) : 0);
				float minZ = 11 * p / 2 - (negZ ? (11 * p / 2) : 0);
				float maxX = 1 - 11 * p / 2 + (posX ? (11 * p / 2) : 0);
				float maxY = 1 - 11 * p / 2 + (posY ? (11 * p / 2) : 0);
				float maxZ = 1 - 11 * p / 2 + (posZ ? (11 * p / 2) : 0);

				return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
			}
		}
		return FULL_BLOCK_AABB;
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

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face){
		return BlockFaceShape.CENTER;
	}
}
