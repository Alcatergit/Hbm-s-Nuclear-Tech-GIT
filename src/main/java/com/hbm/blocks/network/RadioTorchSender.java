package com.hbm.blocks.network;

import com.hbm.main.MainRegistry;
import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.network.TileEntityRadioTorchSender;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RadioTorchSender extends BlockContainer {

	public RadioTorchSender(String s) {
		super(Material.IRON);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityRadioTorchSender();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking())	{
			TileEntityRadioTorchSender entity = (TileEntityRadioTorchSender) world.getTileEntity(pos);
			if(entity != null){
				player.openGui(MainRegistry.instance, ModBlocks.guiID_radio_torch_sender, world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state){
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean getWeakChanges(IBlockAccess world, BlockPos pos){
		return true;
	}
}
