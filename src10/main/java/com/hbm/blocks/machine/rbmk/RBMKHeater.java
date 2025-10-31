package com.hbm.blocks.machine.rbmk;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKHeater;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class RBMKHeater extends RBMKBase {

	public RBMKHeater(String s, String c){
		super(s, c);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= offset)
			return new TileEntityRBMKHeater();
		
		if(hasExtra(meta))
			return new TileEntityProxyCombo(false, false, true);

		return null;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		return openInv(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, ModBlocks.guiID_rbmk_heater, hand);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state){
		return EnumBlockRenderType.MODEL;
	}
}
