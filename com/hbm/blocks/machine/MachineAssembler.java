package com.hbm.blocks.machine;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IMultiBlock;
import com.hbm.main.MainRegistry;

public class MachineAssembler extends BlockContainer implements IMultiBlock {

	private final Random field_149933_a = new Random();
	private static boolean keepInventory;
	
	public MachineAssembler(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.machineTab);
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.machine_assembler);
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
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack) {
		int i = MathHelper.floor(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (i == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.assemblerDimensionEast)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.assemblerDimensionEast, ModBlocks.dummy_block_assembler);

				//
				DummyBlockAssembler.safeBreak = true;
				world.setBlock(x - 1, y, z, ModBlocks.dummy_port_assembler);
				TileEntity te = world.getTileEntity(x - 1, y, z);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 1, y, z + 1, ModBlocks.dummy_port_assembler);
				TileEntity te2 = world.getTileEntity(x - 1, y, z + 1);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x + 2, y, z, ModBlocks.dummy_port_assembler);
				TileEntity te3 = world.getTileEntity(x + 2, y, z);
				if(te3 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te3;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x + 2, y, z + 1, ModBlocks.dummy_port_assembler);
				TileEntity te4 = world.getTileEntity(x + 2, y, z + 1);
				if(te4 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te4;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockAssembler.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
			world.destroyBlock(pos, true);
		}
		if (i == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.assemblerDimensionSouth)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.assemblerDimensionSouth, ModBlocks.dummy_block_assembler);

				//
				DummyBlockAssembler.safeBreak = true;
				world.setBlock(x, y, z - 1, ModBlocks.dummy_port_assembler);
				TileEntity te = world.getTileEntity(x, y, z - 1);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 1, y, z - 1, ModBlocks.dummy_port_assembler);
				TileEntity te2 = world.getTileEntity(x - 1, y, z - 1);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x, y, z + 2, ModBlocks.dummy_port_assembler);
				TileEntity te3 = world.getTileEntity(x, y, z + 2);
				if(te3 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te3;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 1, y, z + 2, ModBlocks.dummy_port_assembler);
				TileEntity te4 = world.getTileEntity(x - 1, y, z + 2);
				if(te4 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te4;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockAssembler.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
		if (i == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.assemblerDimensionWest)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.assemblerDimensionWest, ModBlocks.dummy_block_assembler);

				//
				DummyBlockAssembler.safeBreak = true;
				world.setBlock(x + 1, y, z, ModBlocks.dummy_port_assembler);
				TileEntity te = world.getTileEntity(x + 1, y, z);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x + 1, y, z - 1, ModBlocks.dummy_port_assembler);
				TileEntity te2 = world.getTileEntity(x + 1, y, z - 1);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 2, y, z, ModBlocks.dummy_port_assembler);
				TileEntity te3 = world.getTileEntity(x - 2, y, z);
				if(te3 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te3;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 2, y, z - 1, ModBlocks.dummy_port_assembler);
				TileEntity te4 = world.getTileEntity(x - 2, y, z - 1);
				if(te4 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te4;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockAssembler.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
		if (i == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.assemblerDimensionNorth)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.assemblerDimensionNorth, ModBlocks.dummy_block_assembler);

				//
				DummyBlockAssembler.safeBreak = true;
				world.setBlock(x, y, z + 1, ModBlocks.dummy_port_assembler);
				TileEntity te = world.getTileEntity(x, y, z + 1);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x + 1, y, z + 1, ModBlocks.dummy_port_assembler);
				TileEntity te2 = world.getTileEntity(x + 1, y, z + 1);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x, y, z - 2, ModBlocks.dummy_port_assembler);
				TileEntity te3 = world.getTileEntity(x, y, z - 2);
				if(te3 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te3;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x + 1, y, z - 2, ModBlocks.dummy_port_assembler);
				TileEntity te4 = world.getTileEntity(x + 1, y, z - 2);
				if(te4 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te4;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockAssembler.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
	}
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		// TODO Auto-generated method stub
		super.breakBlock(worldIn, pos, state);
	}
}
