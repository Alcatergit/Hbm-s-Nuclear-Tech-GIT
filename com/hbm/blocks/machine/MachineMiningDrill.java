package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandler;
import com.hbm.interfaces.IMultiBlock;
import com.hbm.lib.InventoryHelper;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityMachineMiningDrill;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MachineMiningDrill extends BlockContainer implements IMultiBlock {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public MachineMiningDrill(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMachineMiningDrill();
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.machine_drill);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(ModBlocks.machine_drill);
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
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		int i = MathHelper.floor(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		EnumFacing e = placer.getHorizontalFacing().getOpposite();
		world.setBlockState(pos, state.withProperty(FACING, e));

		if (i == 0) {
			if(MultiblockHandler.checkSpace(world, pos, MultiblockHandler.drillDimension)) {
				MultiblockHandler.fillUp(world, pos, MultiblockHandler.drillDimension, ModBlocks.dummy_block_drill);
				
				//
				DummyBlockDrill.safeBreak = true;
				world.setBlockState(pos.add(1, 0, 0), ModBlocks.dummy_port_drill.getDefaultState());
				TileEntity te = world.getTileEntity(pos.add(1, 0, 0));
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.target = pos;
				}
				world.setBlockState(pos.add(-1, 0, 0), ModBlocks.dummy_port_drill.getDefaultState());
				TileEntity te2 = world.getTileEntity(pos.add(-1, 0, 0));
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.target = pos;
				}
				DummyBlockDrill.safeBreak = false;
				//
				
			} else
				world.destroyBlock(pos, true);
		}
		if (i == 1) {
			if(MultiblockHandler.checkSpace(world, pos, MultiblockHandler.drillDimension)) {
				MultiblockHandler.fillUp(world, pos, MultiblockHandler.drillDimension, ModBlocks.dummy_block_drill);
				
				//
				DummyBlockDrill.safeBreak = true;
				world.setBlockState(pos.add(0, 0, 1), ModBlocks.dummy_port_drill.getDefaultState());
				TileEntity te = world.getTileEntity(pos.add(0, 0, 1));
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.target = pos;
				}
				world.setBlockState(pos.add(0, 0, -1), ModBlocks.dummy_port_drill.getDefaultState());
				TileEntity te2 = world.getTileEntity(pos.add(0, 0, -1));
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.target = pos;
				}
				DummyBlockDrill.safeBreak = false;
				//
				
			} else
				world.destroyBlock(pos, true);
		}
		if (i == 2) {
			if(MultiblockHandler.checkSpace(world, pos, MultiblockHandler.drillDimension)) {
				MultiblockHandler.fillUp(world, pos, MultiblockHandler.drillDimension, ModBlocks.dummy_block_drill);
				
				//
				DummyBlockDrill.safeBreak = true;
				world.setBlockState(pos.add(1, 0, 0), ModBlocks.dummy_port_drill.getDefaultState());
				TileEntity te = world.getTileEntity(pos.add(1, 0, 0));
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.target = pos;
				}
				world.setBlockState(pos.add(-1, 0, 0), ModBlocks.dummy_port_drill.getDefaultState());
				TileEntity te2 = world.getTileEntity(pos.add(-1, 0, 0));
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.target = pos;
				}
				DummyBlockDrill.safeBreak = false;
				//
				
			} else
				world.destroyBlock(pos, true);
		}
		if (i == 3) {
			if(MultiblockHandler.checkSpace(world, pos, MultiblockHandler.drillDimension)) {
				MultiblockHandler.fillUp(world, pos, MultiblockHandler.drillDimension, ModBlocks.dummy_block_drill);
				
				//
				DummyBlockDrill.safeBreak = true;
				world.setBlockState(pos.add(0, 0, 1), ModBlocks.dummy_port_drill.getDefaultState());
				TileEntity te = world.getTileEntity(pos.add(0, 0, 1));
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.target = pos;
				}
				world.setBlockState(pos.add(0, 0, -1), ModBlocks.dummy_port_drill.getDefaultState());
				TileEntity te2 = world.getTileEntity(pos.add(0, 0, -1));
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.target = pos;
				}
				DummyBlockDrill.safeBreak = false;
				//
				
			} else
				world.destroyBlock(pos, true);
		}
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		InventoryHelper.dropInventoryItems(worldIn, pos, worldIn.getTileEntity(pos));
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if(enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

}
