package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemLock;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityCrateIron;
import com.hbm.tileentity.machine.TileEntityCrateSteel;
import com.hbm.tileentity.machine.TileEntityCrateTungsten;
import com.hbm.tileentity.machine.TileEntityCrateDesh;
import com.hbm.tileentity.machine.TileEntitySafe;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
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
import net.minecraft.world.World;

public class BlockStorageCrate extends BlockContainer {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public BlockStorageCrate(Material materialIn, String s){
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setSoundType(SoundType.METAL);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta){
		if(this == ModBlocks.crate_iron)
			return new TileEntityCrateIron();
		if(this == ModBlocks.crate_steel)
			return new TileEntityCrateSteel();
		if(this == ModBlocks.crate_tungsten)
			return new TileEntityCrateTungsten();
		if(this == ModBlocks.crate_desh)
			return new TileEntityCrateDesh();
		if(this == ModBlocks.safe)
			return new TileEntitySafe();
		return null;
	}

	@Override
	public Block setSoundType(SoundType sound){
		return super.setSoundType(sound);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
		InventoryHelper.dropInventoryItems(worldIn, pos, worldIn.getTileEntity(pos));
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(world.isRemote) {
			return true;
		} else if(player.getHeldItemMainhand() != null && (player.getHeldItemMainhand().getItem() instanceof ItemLock || player.getHeldItemMainhand().getItem() == ModItems.key_kit)) {
			return false;

		} else if(!player.isSneaking()) {
			TileEntity entity = world.getTileEntity(pos);
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			if(entity instanceof TileEntityCrateIron && ((TileEntityCrateIron)entity).canAccess(player)) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_crate_iron, world, x, y, z);
			}
			if(entity instanceof TileEntityCrateSteel && ((TileEntityCrateSteel)entity).canAccess(player)) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_crate_steel, world, x, y, z);
			}
			if(entity instanceof TileEntityCrateTungsten && ((TileEntityCrateTungsten)entity).canAccess(player)) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_crate_tungsten, world, x, y, z);
			}
			if(entity instanceof TileEntityCrateDesh && ((TileEntityCrateDesh)entity).canAccess(player)) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_crate_desh, world, x, y, z);
			}
			if(entity instanceof TileEntitySafe && ((TileEntitySafe)entity).canAccess(player)) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_safe, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
		if(this != ModBlocks.safe)
			super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		else
			worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand){
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	protected BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public int getMetaFromState(IBlockState state){
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta){
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if(enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot){
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn){
		return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state){
		return EnumBlockRenderType.MODEL;
	}

}
