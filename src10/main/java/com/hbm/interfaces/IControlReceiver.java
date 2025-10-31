package com.hbm.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IControlReceiver {

	boolean hasPermission(EntityPlayer player);
	
	void receiveControl(NBTTagCompound data);
}
