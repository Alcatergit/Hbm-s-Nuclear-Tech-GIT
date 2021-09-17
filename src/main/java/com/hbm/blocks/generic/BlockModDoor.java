package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockModDoor extends Block {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool OPEN = PropertyBool.create("open");
	public static final PropertyEnum<BlockDoor.EnumHingePosition> HINGE = PropertyEnum.<BlockDoor.EnumHingePosition> create("hinge", BlockDoor.EnumHingePosition.class);
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	public static final PropertyEnum<BlockDoor.EnumDoorHalf> HALF = PropertyEnum.<BlockDoor.EnumDoorHalf> create("half", BlockDoor.EnumDoorHalf.class);
	protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1875D);
	protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.8125D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.8125D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.1875D, 1.0D, 1.0D);

	public BlockModDoor(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(OPEN, Boolean.FALSE).withProperty(HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(POWERED, Boolean.FALSE).withProperty(HALF, BlockDoor.EnumDoorHalf.LOWER));
		ModBlocks.ALL_BLOCKS.add(this);
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		state = state.getActualState(source, pos);
		EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
		boolean flag = !(Boolean) state.getValue(OPEN);
		boolean flag1 = state.getValue(HINGE) == BlockDoor.EnumHingePosition.RIGHT;

		switch(enumfacing) {
		case EAST:
		default:
			return flag ? EAST_AABB : (flag1 ? NORTH_AABB : SOUTH_AABB);
		case SOUTH:
			return flag ? SOUTH_AABB : (flag1 ? EAST_AABB : WEST_AABB);
		case WEST:
			return flag ? WEST_AABB : (flag1 ? SOUTH_AABB : NORTH_AABB);
		case NORTH:
			return flag ? NORTH_AABB : (flag1 ? WEST_AABB : EAST_AABB);
		}
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks
	 * for render
	 */
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	/**
	 * Determines if an entity can path through this block
	 */
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return isOpen(combineMetadata(worldIn, pos));
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	/**
	 * Get the MapColor for this Block and the given BlockState
	 */
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return MapColor.IRON;
	}

	/**
	 * Called when the block is right clicked by a player.
	 */
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		BlockPos blockpos = state.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER ? pos : pos.down();
		IBlockState iblockstate = pos.equals(blockpos) ? state : worldIn.getBlockState(blockpos);

		if(iblockstate.getBlock() != this) {
			return false;
		} else {
			state = iblockstate.cycleProperty(OPEN);
			worldIn.setBlockState(blockpos, state, 10);
			worldIn.markBlockRangeForRenderUpdate(blockpos, pos);
			worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.openDoor, SoundCategory.BLOCKS, 1.0F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
			return true;
		}
	}

	public void toggleDoor(World worldIn, BlockPos pos, boolean open) {
		IBlockState iblockstate = worldIn.getBlockState(pos);

		if(iblockstate.getBlock() == this) {
			BlockPos blockpos = iblockstate.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER ? pos : pos.down();
			IBlockState iblockstate1 = pos == blockpos ? iblockstate : worldIn.getBlockState(blockpos);

			if(iblockstate1.getBlock() == this && (Boolean) iblockstate1.getValue(OPEN) != open) {
				worldIn.setBlockState(blockpos, iblockstate1.withProperty(OPEN, open), 10);
				worldIn.markBlockRangeForRenderUpdate(blockpos, pos);
				worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.openDoor, SoundCategory.BLOCKS, 1.0F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
			}
		}
	}

	/**
	 * Called when a neighboring block was changed and marks that this state
	 * should perform any checks during a neighbor change. Cases may include
	 * when redstone power is updated, cactus blocks popping off due to a
	 * neighboring solid block, etc.
	 */
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER) {
			BlockPos blockpos = pos.down();
			IBlockState iblockstate = worldIn.getBlockState(blockpos);

			if(iblockstate.getBlock() != this) {
				worldIn.setBlockToAir(pos);
			} else if(blockIn != this) {
				iblockstate.neighborChanged(worldIn, blockpos, blockIn, fromPos);
			}
		} else {
			boolean flag1 = false;
			BlockPos blockpos1 = pos.up();
			IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);

			if(iblockstate1.getBlock() != this) {
				worldIn.setBlockToAir(pos);
				flag1 = true;
			}

			if(!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP)) {
				worldIn.setBlockToAir(pos);
				flag1 = true;

				if(iblockstate1.getBlock() == this) {
					worldIn.setBlockToAir(blockpos1);
				}
			}

			if(flag1) {
				if(!worldIn.isRemote) {
					this.dropBlockAsItem(worldIn, pos, state, 0);
				}
			} else {
				boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(blockpos1);

				if(blockIn != this && (flag || blockIn.getDefaultState().canProvidePower()) && flag != (Boolean) iblockstate1.getValue(POWERED)) {
					worldIn.setBlockState(blockpos1, iblockstate1.withProperty(POWERED, flag), 2);

					if(flag != (Boolean) state.getValue(OPEN)) {
						worldIn.setBlockState(pos, state.withProperty(OPEN, flag), 2);
						worldIn.markBlockRangeForRenderUpdate(pos, pos);
						worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.openDoor, SoundCategory.BLOCKS, 1.0F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
					}
				}
			}
		}
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? Items.AIR : this.getItem();
	}

	/**
	 * Checks if this block can be placed exactly at the given position.
	 */
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		if(pos.getY() >= worldIn.getHeight() - 1) {
			return false;
		} else {
			IBlockState state = worldIn.getBlockState(pos.down());
			return (state.isTopSolid() || state.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID) && super.canPlaceBlockAt(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos.up());
		}
	}

	public EnumPushReaction getMobilityFlag(IBlockState state) {
		return EnumPushReaction.DESTROY;
	}

	public static int combineMetadata(IBlockAccess worldIn, BlockPos pos) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		int i = iblockstate.getBlock().getMetaFromState(iblockstate);
		boolean flag = isTop(i);
		IBlockState iblockstate1 = worldIn.getBlockState(pos.down());
		int j = iblockstate1.getBlock().getMetaFromState(iblockstate1);
		int k = flag ? j : i;
		IBlockState iblockstate2 = worldIn.getBlockState(pos.up());
		int l = iblockstate2.getBlock().getMetaFromState(iblockstate2);
		int i1 = flag ? i : l;
		boolean flag1 = (i1 & 1) != 0;
		boolean flag2 = (i1 & 2) != 0;
		return removeHalfBit(k) | (flag ? 8 : 0) | (flag1 ? 16 : 0) | (flag2 ? 32 : 0);
	}

	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(this.getItem());
	}

	private Item getItem() {
		if(this == ModBlocks.door_metal)
			return ModItems.door_metal;
		else if(this == ModBlocks.door_office)
			return ModItems.door_office;
		else
			return ModItems.door_bunker;
	}

	/**
	 * Called before the Block is set to air in the world. Called regardless of
	 * if the player's tool can actually collect this block
	 */
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		BlockPos blockpos = pos.down();
		BlockPos blockpos1 = pos.up();

		if(player.capabilities.isCreativeMode && state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER && worldIn.getBlockState(blockpos).getBlock() == this) {
			worldIn.setBlockToAir(blockpos);
		}

		if(state.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER && worldIn.getBlockState(blockpos1).getBlock() == this) {
			if(player.capabilities.isCreativeMode) {
				worldIn.setBlockToAir(pos);
			}

			worldIn.setBlockToAir(blockpos1);
		}
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	/**
	 * Get the actual Block state of this Block at the given position. This
	 * applies properties not visible in the metadata, such as fence
	 * connections.
	 */
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		if(state.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER) {
			IBlockState iblockstate = worldIn.getBlockState(pos.up());

			if(iblockstate.getBlock() == this) {
				state = state.withProperty(HINGE, iblockstate.getValue(HINGE)).withProperty(POWERED, iblockstate.getValue(POWERED));
			}
		} else {
			IBlockState iblockstate1 = worldIn.getBlockState(pos.down());

			if(iblockstate1.getBlock() == this) {
				state = state.withProperty(FACING, iblockstate1.getValue(FACING)).withProperty(OPEN, iblockstate1.getValue(OPEN));
			}
		}

		return state;
	}

	/**
	 * Returns the blockstate with the given rotation from the passed
	 * blockstate. If inapplicable, returns the passed blockstate.
	 */
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.getValue(HALF) != BlockDoor.EnumDoorHalf.LOWER ? state : state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If
	 * inapplicable, returns the passed blockstate.
	 */
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return mirrorIn == Mirror.NONE ? state : state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING))).cycleProperty(HINGE);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta) {
		return (meta & 8) > 0 ? this.getDefaultState().withProperty(HALF, BlockDoor.EnumDoorHalf.UPPER).withProperty(HINGE, (meta & 1) > 0 ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT).withProperty(POWERED, (meta & 2) > 0) : this.getDefaultState().withProperty(HALF, BlockDoor.EnumDoorHalf.LOWER).withProperty(FACING, EnumFacing.getHorizontal(meta & 3).rotateYCCW()).withProperty(OPEN, (meta & 4) > 0);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
		int i = 0;

		if(state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER) {
			i = i | 8;

			if(state.getValue(HINGE) == BlockDoor.EnumHingePosition.RIGHT) {
				i |= 1;
			}

			if((Boolean) state.getValue(POWERED)) {
				i |= 2;
			}
		} else {
			i = i | ((EnumFacing) state.getValue(FACING)).rotateY().getHorizontalIndex();

			if((Boolean) state.getValue(OPEN)) {
				i |= 4;
			}
		}

		return i;
	}

	protected static int removeHalfBit(int meta) {
		return meta & 7;
	}

	public static boolean isOpen(IBlockAccess worldIn, BlockPos pos) {
		return isOpen(combineMetadata(worldIn, pos));
	}

	public static EnumFacing getFacing(IBlockAccess worldIn, BlockPos pos) {
		return getFacing(combineMetadata(worldIn, pos));
	}

	public static EnumFacing getFacing(int combinedMeta) {
		return EnumFacing.getHorizontal(combinedMeta & 3).rotateYCCW();
	}

	protected static boolean isOpen(int combinedMeta) {
		return (combinedMeta & 4) != 0;
	}

	protected static boolean isTop(int meta) {
		return (meta & 8) != 0;
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { HALF, FACING, OPEN, HINGE, POWERED });
	}

	/**
	 * Get the geometry of the queried face at the given position and state.
	 * This is used to decide whether things like buttons are allowed to be
	 * placed on the face, or how glass panes connect to the face, among other
	 * things.
	 * <p>
	 * Common values are {@code SOLID}, which is the default, and
	 * {@code UNDEFINED}, which represents something that does not fit the other
	 * descriptions and will generally cause other things not to connect to the
	 * face.
	 * 
	 * @return an approximation of the form of the given face
	 */
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
}
