package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachineBoiler;
import com.hbm.tileentity.machine.TileEntityMachineBoilerElectric;
import com.hbm.tileentity.machine.TileEntityMachineBoilerRTG;

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

public class MachineBoiler extends BlockContainer {

	private static boolean keepInventory;
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	private final boolean isActive;

	public MachineBoiler(Material materialIn, boolean active, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.machineTab);
		this.isActive = active;

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if (this == ModBlocks.machine_boiler_off || this == ModBlocks.machine_boiler_on)
			return new TileEntityMachineBoiler();
		if (this == ModBlocks.machine_boiler_electric_off || this == ModBlocks.machine_boiler_electric_on)
			return new TileEntityMachineBoilerElectric();
		if (this == ModBlocks.machine_boiler_rtg_off || this == ModBlocks.machine_boiler_rtg_on)
			return new TileEntityMachineBoilerRTG();
		return null;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if (this == ModBlocks.machine_boiler_off || this == ModBlocks.machine_boiler_on)
			return Item.getItemFromBlock(ModBlocks.machine_boiler_off);
		if (this == ModBlocks.machine_boiler_electric_off || this == ModBlocks.machine_boiler_electric_on)
			return Item.getItemFromBlock(ModBlocks.machine_boiler_electric_off);
		if (this == ModBlocks.machine_boiler_rtg_off || this == ModBlocks.machine_boiler_rtg_on)
			return Item.getItemFromBlock(ModBlocks.machine_boiler_rtg_off);
		return super.getItemDropped(state, rand, fortune);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.setDefaultFacing(worldIn, pos, state);
		super.onBlockAdded(worldIn, pos, state);
	}

	private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			IBlockState iblockstate = worldIn.getBlockState(pos.north());
			IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
			IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
			IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

			if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
				enumfacing = EnumFacing.SOUTH;
			} else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
				enumfacing = EnumFacing.NORTH;
			} else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
				enumfacing = EnumFacing.EAST;
			} else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
				enumfacing = EnumFacing.WEST;
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityMachineBoiler) {
				((TileEntityMachineBoiler) tileentity).setCustomName(stack.getDisplayName());
			}
			if (tileentity instanceof TileEntityMachineBoilerElectric) {
				((TileEntityMachineBoilerElectric) tileentity).setCustomName(stack.getDisplayName());
			}
			if (tileentity instanceof TileEntityMachineBoilerRTG) {
				((TileEntityMachineBoilerRTG) tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		} else if (!player.isSneaking()) {
			TileEntity te = world.getTileEntity(pos);

			if (te instanceof TileEntityMachineBoiler) {

				TileEntityMachineBoiler entity = (TileEntityMachineBoiler) te;

				if (entity != null) {

					player.openGui(MainRegistry.instance, ModBlocks.guiID_machine_boiler, world, pos.getX(), pos.getY(), pos.getZ());
				}
			}

			if(te instanceof TileEntityMachineBoilerElectric) {
				
				TileEntityMachineBoilerElectric entity = (TileEntityMachineBoilerElectric) te;
				if(entity != null)
				{
					player.openGui(MainRegistry.instance, ModBlocks.guiID_machine_boiler_electric, world, pos.getX(), pos.getY(), pos.getZ());
				}
			}
			if(te instanceof TileEntityMachineBoilerRTG) {
				
				TileEntityMachineBoilerRTG entity = (TileEntityMachineBoilerRTG) te;
				if(entity != null)
				{
					player.openGui(MainRegistry.instance, ModBlocks.guiID_machine_boiler_rtg, world, pos.getX(), pos.getY(), pos.getZ());
				}
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
		if (i.getBlock() == ModBlocks.machine_boiler_off || i.getBlock() == ModBlocks.machine_boiler_on){
			if (isProcessing && i.getBlock() != ModBlocks.machine_boiler_on) {
				world.setBlockState(pos, ModBlocks.machine_boiler_on.getDefaultState().withProperty(FACING, i.getValue(FACING)));
			} else if (!isProcessing && i.getBlock() != ModBlocks.machine_boiler_off) {
				world.setBlockState(pos, ModBlocks.machine_boiler_off.getDefaultState().withProperty(FACING, i.getValue(FACING)));
			}
		}
		if (i.getBlock() == ModBlocks.machine_boiler_electric_off || i.getBlock() == ModBlocks.machine_boiler_electric_on){
			if (isProcessing && i.getBlock() != ModBlocks.machine_boiler_electric_on) {
				world.setBlockState(pos, ModBlocks.machine_boiler_electric_on.getDefaultState().withProperty(FACING, i.getValue(FACING)));
			} else if (i.getBlock() != ModBlocks.machine_boiler_electric_off) {
				world.setBlockState(pos, ModBlocks.machine_boiler_electric_off.getDefaultState().withProperty(FACING, i.getValue(FACING)));
			}
		}
		if (i.getBlock() == ModBlocks.machine_boiler_rtg_off || i.getBlock() == ModBlocks.machine_boiler_rtg_on){
			if (isProcessing && i.getBlock() != ModBlocks.machine_boiler_rtg_on) {
				world.setBlockState(pos, ModBlocks.machine_boiler_rtg_on.getDefaultState().withProperty(FACING, i.getValue(FACING)));
			} else if (i.getBlock() != ModBlocks.machine_boiler_rtg_off) {
				world.setBlockState(pos, ModBlocks.machine_boiler_rtg_off.getDefaultState().withProperty(FACING, i.getValue(FACING)));
			}
		}
		keepInventory = false;
		if (entity != null) {
			entity.validate();
			world.setTileEntity(pos, entity);
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (!keepInventory) {
			if (tileentity instanceof TileEntityMachineBoiler) {
				InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityMachineBoiler) tileentity);
				worldIn.updateComparatorOutputLevel(pos, this);
			}
			if (tileentity instanceof TileEntityMachineBoilerElectric) {
				InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityMachineBoilerElectric) tileentity);
				worldIn.updateComparatorOutputLevel(pos, this);
			}
			if (tileentity instanceof TileEntityMachineBoilerRTG) {
				InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityMachineBoilerRTG) tileentity);
				worldIn.updateComparatorOutputLevel(pos, this);
			}
			
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (this.isActive) {
			if (this == ModBlocks.machine_boiler_on) {
				EnumFacing enumfacing = (EnumFacing) stateIn.getValue(FACING);
				double d0 = (double) pos.getX() + 0.5D;
				double d1 = (double) pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
				double d2 = (double) pos.getZ() + 0.5D;
				double d4 = rand.nextDouble() * 0.6D - 0.3D;

				if (rand.nextDouble() < 0.1D) {
					worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
				}

				switch (enumfacing) {
				case WEST:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
					worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
					break;
				case EAST:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
					worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
					break;
				case NORTH:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
					worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
					break;
				case SOUTH:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
					worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
					break;
				default:
					break;
				}
			} else if(this == ModBlocks.machine_boiler_electric_on){
				EnumFacing enumfacing = (EnumFacing) stateIn.getValue(FACING);
				double d0 = (double) pos.getX() + 0.5D;
				double d1 = (double) pos.getY() + 0.25 + rand.nextDouble() * 6.0D / 16.0D;
				double d2 = (double) pos.getZ() + 0.5D;
				double d4 = rand.nextDouble() * 0.6D - 0.3D;

				switch (enumfacing) {
				case WEST:
					worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
					break;
				case EAST:
					worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
					break;
				case NORTH:
					worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
					break;
				case SOUTH:
					worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		if (this == ModBlocks.machine_boiler_on || this == ModBlocks.machine_boiler_off)
			return new ItemStack(ModBlocks.machine_boiler_off);
		if (this == ModBlocks.machine_boiler_electric_on || this == ModBlocks.machine_boiler_electric_off)
			return new ItemStack(ModBlocks.machine_boiler_electric_off);
		if (this == ModBlocks.machine_boiler_rtg_on || this == ModBlocks.machine_boiler_rtg_off)
			return new ItemStack(ModBlocks.machine_boiler_rtg_off);
		return super.getPickBlock(state, target, world, pos, player);
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
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
	}
}
