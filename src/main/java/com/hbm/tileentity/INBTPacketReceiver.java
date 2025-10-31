package com.hbm.tileentity;

import com.hbm.interfaces.Spaghetti;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public interface INBTPacketReceiver {
	@Deprecated
	@Spaghetti("For f*ck's sake we are tired of NBTPacket, fucking don't use it for new work or i'll cut your face down")
	public void networkUnpack(NBTTagCompound nbt);

	@Deprecated
	@Spaghetti("For f*ck's sake we are tired of NBTPacket, fucking don't use it for new work or i'll cut your face down")
	public static void networkPack(TileEntity that, NBTTagCompound data, int range) {
		BlockPos pos = that.getPos();
		PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(data, pos), new TargetPoint(that.getWorld().provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), range));
	}
}
