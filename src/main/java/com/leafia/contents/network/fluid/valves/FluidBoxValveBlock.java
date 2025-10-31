package com.leafia.contents.network.fluid.valves;

import com.hbm.lib.HBMSoundEvents;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FluidBoxValveBlock extends FluidBoxValveBase {
	public FluidBoxValveBlock(Material materialIn,String s) { super(materialIn,s); }
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote) return true;
		if (!player.isSneaking()) {
			boolean isOn = state.getValue(STATE);
			setState(world,pos,!isOn);
			if(!isOn) world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundEvents.reactorStart, SoundCategory.BLOCKS, 1.0F, 1.0F);
			else world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundEvents.reactorStart, SoundCategory.BLOCKS, 1.0F, 0.85F);
			return true;
		}
		return false;
	}
}