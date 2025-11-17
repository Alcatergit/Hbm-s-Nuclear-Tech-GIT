package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandler;
import com.hbm.interfaces.IMultiBlock;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityDummyFluidPort;
import com.hbm.tileentity.machine.TileEntityMachineFluidTank;

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
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MachineFluidTank extends BlockContainer implements IMultiBlock {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public MachineFluidTank(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMachineFluidTank();
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.machine_fluidtank);
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
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.byIndex(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
    		TileEntityMachineFluidTank entity = (TileEntityMachineFluidTank) world.getTileEntity(pos);
    		if(entity != null)
    		{
    			player.openGui(MainRegistry.instance, ModBlocks.guiID_machine_fluidtank, world, pos.getX(), pos.getY(), pos.getZ());
    		}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if(tileentity instanceof TileEntityMachineFluidTank) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityMachineFluidTank) tileentity);
			worldIn.updateComparatorOutputLevel(pos, this);
		}
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if(!(placer instanceof EntityPlayer))
			return;

		EntityPlayer player = (EntityPlayer) placer;
		EnumHand hand = player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;

		world.setBlockToAir(pos);

		int i = MathHelper.floor(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		EnumFacing facing;
		int[] dimension;

		if(i == 0) {
			facing = EnumFacing.EAST;
			dimension = MultiblockHandler.fluidTankDimensionEW;
		} else if(i == 1) {
			facing = EnumFacing.SOUTH;
			dimension = MultiblockHandler.fluidTankDimensionNS;
		} else if(i == 2) {
			facing = EnumFacing.WEST;
			dimension = MultiblockHandler.fluidTankDimensionEW;
		} else {
			facing = EnumFacing.NORTH;
			dimension = MultiblockHandler.fluidTankDimensionNS;
		}

		EnumFacing playerFacing = placer.getHorizontalFacing();
		BlockPos corePos = pos.offset(playerFacing, 1);

		if(!MultiblockHandler.checkSpace(world, corePos, dimension)) {
			if(!player.capabilities.isCreativeMode) {
				ItemStack held = player.getHeldItem(hand);
				Item item = Item.getItemFromBlock(this);

				if(held.isEmpty()) {
					player.setHeldItem(hand, new ItemStack(this));
				} else {
					if(held.getItem() != item || held.getCount() == held.getMaxStackSize()) {
						player.inventory.addItemStackToInventory(new ItemStack(this));
					} else {
						held.grow(1);
					}
				}
			}
			return;
		}

		if(!world.isRemote) {
			world.setBlockState(corePos, this.getDefaultState().withProperty(FACING, facing), 3);
			MultiblockHandler.fillUp(world, corePos, dimension, ModBlocks.dummy_block_fluidtank);

			DummyBlockFluidTank.safeBreak = true;
			world.setBlockState(corePos.add(1, 0, 1), ModBlocks.dummy_port_fluidtank.getDefaultState());
			TileEntity te = world.getTileEntity(corePos.add(1, 0, 1));
			if(te instanceof TileEntityDummyFluidPort) {
				((TileEntityDummyFluidPort)te).target = corePos;
			}
			world.setBlockState(corePos.add(1, 0, -1), ModBlocks.dummy_port_fluidtank.getDefaultState());
			TileEntity te1 = world.getTileEntity(corePos.add(1, 0, -1));
			if(te1 instanceof TileEntityDummyFluidPort) {
				((TileEntityDummyFluidPort)te1).target = corePos;
			}
			world.setBlockState(corePos.add(-1, 0, 1), ModBlocks.dummy_port_fluidtank.getDefaultState());
			TileEntity te2 = world.getTileEntity(corePos.add(-1, 0, 1));
			if(te2 instanceof TileEntityDummyFluidPort) {
				((TileEntityDummyFluidPort)te2).target = corePos;
			}
			world.setBlockState(corePos.add(-1, 0, -1), ModBlocks.dummy_port_fluidtank.getDefaultState());
			TileEntity te3 = world.getTileEntity(corePos.add(-1, 0, -1));
			if(te3 instanceof TileEntityDummyFluidPort) {
				((TileEntityDummyFluidPort)te3).target = corePos;
			}
			DummyBlockFluidTank.safeBreak = false;
		}
	}
}
