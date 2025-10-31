package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachineCoal;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MachineCoal extends BlockContainer {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	private final boolean isActive;
	private static boolean keepInventory;

	public MachineCoal(Material m, String s, boolean blockState) {
		super(m);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.machineTab);
		isActive = blockState;

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMachineCoal();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.machine_coal_off);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
		this.setDefaultDirection(world, pos, state);
	}

	private void setDefaultDirection(World world, BlockPos pos, IBlockState state) {
		if (!world.isRemote) {
			IBlockState block1 = world.getBlockState(pos.north());
			IBlockState block2 = world.getBlockState(pos.south());
			IBlockState block3 = world.getBlockState(pos.west());
			IBlockState block4 = world.getBlockState(pos.east());

			EnumFacing direction = state.getValue(FACING);

			if (direction == EnumFacing.NORTH && block1.isFullBlock() && !block2.isFullBlock()) {
				direction = EnumFacing.SOUTH;
			}
			if (direction == EnumFacing.SOUTH && block2.isFullBlock() && !block1.isFullBlock()) {
				direction = EnumFacing.NORTH;
			}
			if (direction == EnumFacing.WEST && block3.isFullBlock() && !block4.isFullBlock()) {
				direction = EnumFacing.EAST;
			}
			if (direction == EnumFacing.EAST && block4.isFullBlock() && !block3.isFullBlock()) {
				direction = EnumFacing.WEST;
			}

			world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, direction), 2);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

		if (stack.hasDisplayName() && world.getTileEntity(pos) instanceof TileEntityMachineCoal) {
			((TileEntityMachineCoal) world.getTileEntity(pos)).setCustomName(stack.getDisplayName());
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		} else if (!player.isSneaking()) {
			TileEntityMachineCoal entity = (TileEntityMachineCoal) world.getTileEntity(pos);
			if (entity != null) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_machine_coal, world, pos.getX(), pos.getY(),
						pos.getZ());
			}
			return true;
		} else {
			return false;
		}
	}

	public static void updateBlockState(boolean isProcessing, World world, BlockPos pos) {
		IBlockState i = world.getBlockState(pos);
		TileEntity entity = world.getTileEntity(pos);
		keepInventory = true;
		if (isProcessing && world.getBlockState(pos).getBlock() == ModBlocks.machine_coal_off) {
			world.setBlockState(pos,
					ModBlocks.machine_coal_on.getDefaultState().withProperty(FACING, i.getValue(FACING)));
		} else if (!isProcessing && world.getBlockState(pos).getBlock() == ModBlocks.machine_coal_on) {
			world.setBlockState(pos,
					ModBlocks.machine_coal_off.getDefaultState().withProperty(FACING, i.getValue(FACING)));
		}

		keepInventory = false;

		if (entity != null) {
			entity.validate();
			world.setTileEntity(pos, entity);
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (!keepInventory) {
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityMachineCoal) {
				InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityMachineCoal) tileentity);
				worldIn.updateComparatorOutputLevel(pos, this);
			}
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand) {
		if (isActive) {

			EnumFacing enumfacing = (EnumFacing) stateIn.getValue(FACING);
			double d0 = (double) pos.getX() + 0.5D;
			double d1 = (double) pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
			double d2 = (double) pos.getZ() + 0.5D;
			double d4 = rand.nextDouble() * 0.6D - 0.3D;

			if (rand.nextDouble() < 0.1D) {
				world.playSound((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			}

			switch (enumfacing) {
			case WEST:
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
				world.spawnParticle(EnumParticleTypes.FLAME, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
				break;
			case EAST:
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
				world.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
				break;
			case NORTH:
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
				world.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
				break;
			case SOUTH:
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
				world.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
			default:
				break;
			}
		}
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(ModBlocks.machine_coal_off));
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
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
