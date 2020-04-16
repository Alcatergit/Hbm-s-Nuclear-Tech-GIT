package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandler;
import com.hbm.interfaces.IMultiBlock;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityMachineCyclotron;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MachineCyclotron extends BlockContainer implements IMultiBlock {

	public MachineCyclotron(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMachineCyclotron();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.machine_cyclotron);
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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			TileEntityMachineCyclotron entity = (TileEntityMachineCyclotron) world.getTileEntity(pos);
			if(entity != null) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_machine_cyclotron, world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if(tileentity instanceof TileEntityMachineCyclotron) {
			InventoryHelper.dropInventoryItems(worldIn, pos, ((TileEntityMachineCyclotron) tileentity).dropProvider);
			worldIn.updateComparatorOutputLevel(pos, this);
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if(MultiblockHandler.checkSpace(world, pos, MultiblockHandler.cyclDimension)) {
			MultiblockHandler.fillUp(world, pos, MultiblockHandler.cyclDimension, ModBlocks.dummy_block_cyclotron);

			DummyBlockCyclotron.safeBreak = true;
			world.setBlockState(pos.add(0, 0, 1), ModBlocks.dummy_port_cyclotron.getDefaultState());
			TileEntity te = world.getTileEntity(pos.add(0, 0, 1));
			if(te instanceof TileEntityDummy) {
				TileEntityDummy dummy = (TileEntityDummy) te;
				dummy.target = pos;
			}
			world.setBlockState(pos.add(0, 0, -1), ModBlocks.dummy_port_cyclotron.getDefaultState());
			TileEntity te2 = world.getTileEntity(pos.add(0, 0, -1));
			if(te instanceof TileEntityDummy) {
				TileEntityDummy dummy = (TileEntityDummy) te2;
				dummy.target = pos;
			}
			world.setBlockState(pos.add(1, 0, 0), ModBlocks.dummy_port_cyclotron.getDefaultState());
			TileEntity te3 = world.getTileEntity(pos.add(1, 0, 0));
			if(te3 instanceof TileEntityDummy) {
				TileEntityDummy dummy = (TileEntityDummy) te3;
				dummy.target = pos;
			}
			world.setBlockState(pos.add(-1, 0, 0), ModBlocks.dummy_port_cyclotron.getDefaultState());
			TileEntity te4 = world.getTileEntity(pos.add(-1, 0, 0));
			if(te4 instanceof TileEntityDummy) {
				TileEntityDummy dummy = (TileEntityDummy) te4;
				dummy.target = pos;
			}
			DummyBlockCyclotron.safeBreak = false;

		} else {
			world.destroyBlock(pos, true);
		}
	}

}
