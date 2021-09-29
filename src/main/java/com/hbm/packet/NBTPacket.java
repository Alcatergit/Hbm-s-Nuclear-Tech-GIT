package com.hbm.packet;

import java.io.IOException;

import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.TileEntityTickingBase;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NBTPacket implements IMessage {

	PacketBuffer buffer;
	int x;
	int y;
	int z;

	public NBTPacket() {
	}

	public NBTPacket(NBTTagCompound nbt, BlockPos pos) {

		this.buffer = new PacketBuffer(Unpooled.buffer());
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();

		buffer.writeCompoundTag(nbt);

	}

	@Override
	public void fromBytes(ByteBuf buf) {

		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();

		if(buffer == null) {
			buffer = new PacketBuffer(Unpooled.buffer());
		}
		buffer.writeBytes(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {

		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);

		if(buffer == null) {
			buffer = new PacketBuffer(Unpooled.buffer());
		}
		buf.writeBytes(buffer);
	}

	public static class Handler implements IMessageHandler<NBTPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(NBTPacket m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				if(Minecraft.getMinecraft().world == null)
					return;

				TileEntity te = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(m.x, m.y, m.z));

				try {

					NBTTagCompound nbt = m.buffer.readCompoundTag();

					if(nbt != null) {
						 if(te instanceof INBTPacketReceiver)
								((INBTPacketReceiver) te).networkUnpack(nbt);
					}

				} catch(IOException e) {
					e.printStackTrace();
				}
			});

			return null;
		}
	}

}