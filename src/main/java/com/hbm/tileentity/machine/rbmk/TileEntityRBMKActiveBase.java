package com.hbm.tileentity.machine.rbmk;

import net.minecraft.entity.player.EntityPlayer;

public abstract class TileEntityRBMKActiveBase extends TileEntityRBMKBase {
	
	public abstract String getName();

	public boolean isUseableByPlayer(EntityPlayer player) {
		if(world.getTileEntity(pos) != this) {
			return false;
		} else {
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 128;
		}
	}
}
