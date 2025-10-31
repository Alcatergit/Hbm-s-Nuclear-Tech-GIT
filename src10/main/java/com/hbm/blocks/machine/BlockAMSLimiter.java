package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandler;
import com.hbm.interfaces.IMultiBlock;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityAMSLimiter;
import com.hbm.tileentity.machine.TileEntityDummy;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAMSLimiter extends BlockContainer implements IMultiBlock {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public BlockAMSLimiter(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityAMSLimiter();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityAMSLimiter entity = (TileEntityAMSLimiter) world.getTileEntity(pos);
			if(entity != null)
			{
				player.openGui(MainRegistry.instance, ModBlocks.guiID_ams_limiter, world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		EnumFacing e = placer.getHorizontalFacing().getOpposite().rotateY();

		world.setBlockState(pos, state.withProperty(FACING, e));
		if (e == EnumFacing.EAST) {
			if(MultiblockHandler.checkSpace(world, pos, MultiblockHandler.AMSLimiterDimensionEast)) {
				MultiblockHandler.fillUp(world, pos, MultiblockHandler.AMSLimiterDimensionEast, ModBlocks.dummy_block_ams_limiter);
				
				//
				DummyBlockAMSLimiter.safeBreak = true;
				world.setBlockState(pos.add(2, 0, 0), ModBlocks.dummy_port_ams_limiter.getDefaultState());
				TileEntity te = world.getTileEntity(pos.add(2, 0, 0));
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.target = pos;
				}
				world.setBlockState(pos.add(-2, 0, 0), ModBlocks.dummy_port_ams_limiter.getDefaultState());
				TileEntity te2 = world.getTileEntity(pos.add(-2, 0, 0));
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.target = pos;
				}
				DummyBlockAMSLimiter.safeBreak = false;
				//
				
			} else
				world.destroyBlock(pos, true);
		}
		if (e == EnumFacing.SOUTH) {
			if(MultiblockHandler.checkSpace(world, pos, MultiblockHandler.AMSLimiterDimensionSouth)) {
				MultiblockHandler.fillUp(world, pos, MultiblockHandler.AMSLimiterDimensionSouth, ModBlocks.dummy_block_ams_limiter);
				
				//
				DummyBlockAMSLimiter.safeBreak = true;
				world.setBlockState(pos.add(0, 0, 2), ModBlocks.dummy_port_ams_limiter.getDefaultState());
				TileEntity te = world.getTileEntity(pos.add(0, 0, 2));
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.target = pos;
				}
				world.setBlockState(pos.add(0, 0, -2), ModBlocks.dummy_port_ams_limiter.getDefaultState());
				TileEntity te2 = world.getTileEntity(pos.add(0, 0, -2));
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.target = pos;
				}
				DummyBlockAMSLimiter.safeBreak = false;
				//
				
			} else
				world.destroyBlock(pos, true);
		}
		if (e == EnumFacing.WEST) {
			if(MultiblockHandler.checkSpace(world, pos, MultiblockHandler.AMSLimiterDimensionWest)) {
				MultiblockHandler.fillUp(world, pos, MultiblockHandler.AMSLimiterDimensionWest, ModBlocks.dummy_block_ams_limiter);
				
				//
				DummyBlockAMSLimiter.safeBreak = true;
				world.setBlockState(pos.add(2, 0, 0), ModBlocks.dummy_port_ams_limiter.getDefaultState());
				TileEntity te = world.getTileEntity(pos.add(2, 0, 0));
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.target = pos;
				}
				world.setBlockState(pos.add(-2, 0, 0), ModBlocks.dummy_port_ams_limiter.getDefaultState());
				TileEntity te2 = world.getTileEntity(pos.add(-2, 0, 0));
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.target = pos;
				}
				DummyBlockAMSLimiter.safeBreak = false;
				//
				
			} else
				world.destroyBlock(pos, true);
		}
		if (e == EnumFacing.NORTH) {
			if(MultiblockHandler.checkSpace(world, pos, MultiblockHandler.AMSLimiterDimensionNorth)) {
				MultiblockHandler.fillUp(world, pos, MultiblockHandler.AMSLimiterDimensionNorth, ModBlocks.dummy_block_ams_limiter);
				
				//
				DummyBlockAMSLimiter.safeBreak = true;
				world.setBlockState(pos.add(0, 0, 2), ModBlocks.dummy_port_ams_limiter.getDefaultState());
				TileEntity te = world.getTileEntity(pos.add(0, 0, 2));
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.target = pos;
				}
				world.setBlockState(pos.add(0, 0, -2), ModBlocks.dummy_port_ams_limiter.getDefaultState());
				TileEntity te2 = world.getTileEntity(pos.add(0, 0, -2));
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.target = pos;
				}
				DummyBlockAMSLimiter.safeBreak = false;
				//
				
			} else
				world.destroyBlock(pos, true);
		}
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
	public boolean isFullCube(IBlockState state) {
		return false;
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
