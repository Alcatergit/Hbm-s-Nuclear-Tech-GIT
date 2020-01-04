package com.hbm.tileentity.machine;

import com.hbm.interfaces.IMultiBlock;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityDummy extends TileEntity implements ITickable {

	public BlockPos target;
	boolean needsMark = true;
	
	@Override
	public void update() {
		if(!this.world.isRemote) {
			if(needsMark){
				markDirty();
				needsMark = false;
			}
    		if(!(this.world.getBlockState(target).getBlock() instanceof IMultiBlock)) {
    			world.destroyBlock(pos, false);
    		}
    	}
	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if(target != null){
			compound.setInteger("tx", target.getX());
			compound.setInteger("ty", target.getY());
			compound.setInteger("tz", target.getZ());
		}
		return compound;
	}
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		int x = compound.getInteger("tx");
		int y = compound.getInteger("ty");
		int z = compound.getInteger("tz");
		this.target = new BlockPos(x, y, z);
	}
	
	
	
}
