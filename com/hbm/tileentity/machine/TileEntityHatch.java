package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockSeal;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityHatch extends TileEntity implements ITickable {

	private BlockPos controller;
	
	@Override
	public void update() {
		if(controller == null)
			return;
		Block b = world.getBlockState(controller).getBlock();
		
		if(b != ModBlocks.seal_controller && !world.isRemote) {
			this.world.setBlockState(pos, Blocks.AIR.getDefaultState());
		} else {
			if(BlockSeal.getFrameSize(world, controller) == 0 && !world.isRemote)
				this.world.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		controller = BlockPos.fromLong(compound.getLong("controller"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		if(controller != null)
			compound.setLong("controller", controller.toLong());
		return super.writeToNBT(compound);
	}
	
	public void setControllerPos(BlockPos pos) {
		this.controller = pos;
	}
}
