package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandler;
import com.hbm.interfaces.IMultiBlock;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityAMSEmitter;
import com.hbm.tileentity.machine.TileEntityDummy;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAMSEmitter extends BlockContainer implements IMultiBlock {

	public BlockAMSEmitter(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityAMSEmitter();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityAMSEmitter entity = (TileEntityAMSEmitter) world.getTileEntity(pos);
			if(entity != null)
			{
				player.openGui(MainRegistry.instance, ModBlocks.guiID_ams_emitter, world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if(MultiblockHandler.checkSpace(world, pos, MultiblockHandler.AMSEmitterDimension)) {
			MultiblockHandler.fillUp(world, pos, MultiblockHandler.AMSEmitterDimension, ModBlocks.dummy_block_ams_emitter);

			DummyBlockAMSEmitter.safeBreak = true;
			world.setBlockState(pos.add(0, 5, 0), ModBlocks.dummy_port_ams_emitter.getDefaultState());
			TileEntity te = world.getTileEntity(pos.add(0, 5, 0));
			if(te instanceof TileEntityDummy) {
				TileEntityDummy dummy = (TileEntityDummy)te;
				dummy.target = pos;
			}
			world.setBlockState(pos.add(0, 5, -1), ModBlocks.dummy_port_ams_emitter.getDefaultState());
			TileEntity te2 = world.getTileEntity(pos.add(0, 5, -1));
			if(te instanceof TileEntityDummy) {
				TileEntityDummy dummy = (TileEntityDummy)te2;
				dummy.target = pos;
			}
			world.setBlockState(pos.add(1, 5, 0), ModBlocks.dummy_port_ams_emitter.getDefaultState());
			TileEntity te3 = world.getTileEntity(pos.add(1, 5, 0));
			if(te3 instanceof TileEntityDummy) {
				TileEntityDummy dummy = (TileEntityDummy)te3;
				dummy.target = pos;
			}
			world.setBlockState(pos.add(-1, 5, 0), ModBlocks.dummy_port_ams_emitter.getDefaultState());
			TileEntity te4 = world.getTileEntity(pos.add(-1, 5, 0));
			if(te4 instanceof TileEntityDummy) {
				TileEntityDummy dummy = (TileEntityDummy)te4;
				dummy.target = pos;
			}
			world.setBlockState(pos.add(0, 5, 1), ModBlocks.dummy_port_ams_emitter.getDefaultState());
			TileEntity te5 = world.getTileEntity(pos.add(0, 5, 1));
			if(te5 instanceof TileEntityDummy) {
				TileEntityDummy dummy = (TileEntityDummy)te5;
				dummy.target = pos;
			}
			DummyBlockAMSEmitter.safeBreak = false;
			
		} else
			world.destroyBlock(pos, true);
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

}
