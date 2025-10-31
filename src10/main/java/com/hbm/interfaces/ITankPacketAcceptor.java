package com.hbm.interfaces;

import net.minecraft.nbt.NBTTagCompound;

public interface ITankPacketAcceptor {
	void recievePacket(NBTTagCompound[] tags);
}
