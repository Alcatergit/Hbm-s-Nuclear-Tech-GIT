package com.leafia.contents.network.fluid.valves;

import com.hbm.lib.HBMSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FluidBoxValveBlockRS extends FluidBoxValveBase {
	public FluidBoxValveBlockRS(Material materialIn,String s) { super(materialIn,s); }
	@Override
	public void neighborChanged(IBlockState state,World world,BlockPos pos,Block blockIn,BlockPos fromPos) {
		boolean on = world.getRedstonePowerFromNeighbors(pos) > 0;
		setState(world,pos,on);
		if(on)
			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundEvents.reactorStart, SoundCategory.BLOCKS, 1.0F, 0.3F);
		super.neighborChanged(state,world,pos,blockIn,fromPos);
	}
}