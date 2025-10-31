package com.hbm.interfaces;

import net.minecraft.nbt.NBTTagCompound;

public interface ITankPacketAcceptor {
	public void recievePacket(NBTTagCompound[] tags);
}
