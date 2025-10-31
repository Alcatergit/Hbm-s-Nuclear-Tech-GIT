package com.leafia.contents.machines.reactors.msr;

import com.hbm.blocks.machine.BlockMachineBase;
import com.hbm.main.MainRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MSRMixerTest extends BlockMachineBase {
	public MSRMixerTest(Material materialIn,String s) {
		super(materialIn,0,s);
	}
	@Override
	protected boolean rotatable() {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta) {
		return new MSRMixerTE();
	}

	@Override
	public boolean onBlockActivated(World world,BlockPos pos,IBlockState state,EntityPlayer player,EnumHand hand,EnumFacing facing,float hitX,float hitY,float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			player.openGui(MainRegistry.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
			return true;
		} else {
			return true;
		}
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}
