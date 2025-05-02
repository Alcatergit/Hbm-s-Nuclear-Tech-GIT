package com.hbm.blocks.network;

import com.hbm.main.MainRegistry;
import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.network.TileEntityRadioTorchReceiver;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RadioTorchReceiver extends BlockContainer {

	public static final PropertyDirection FACING = BlockDirectional.FACING;

	public RadioTorchReceiver(String s) {
		super(Material.IRON);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityRadioTorchReceiver();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking())	{
			TileEntityRadioTorchReceiver entity = (TileEntityRadioTorchReceiver) world.getTileEntity(pos);
			if(entity != null){
				player.openGui(MainRegistry.instance, ModBlocks.guiID_radio_torch_receiver, world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)), 2);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state){
		return EnumBlockRenderType.MODEL;
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
        return this.getDefaultState().withProperty(FACING, enumfacing);
	}
	

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
	   return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}

	
	
	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

	@Override
	public boolean getWeakChanges(IBlockAccess world, BlockPos pos){
		return false;
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		TileEntityRadioTorchReceiver entity = (TileEntityRadioTorchReceiver) blockAccess.getTileEntity(pos);
        return entity.lastState;
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		if(side == (EnumFacing)blockState.getValue(FACING).getOpposite())
			return getWeakPower(blockState, blockAccess, pos, side);
		return 0;
	}

}
