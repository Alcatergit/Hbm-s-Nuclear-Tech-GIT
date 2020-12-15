package com.hbm.packet;

import com.hbm.main.MainRegistry;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerInformPacket implements IMessage {

	String dmesg = "";

	public PlayerInformPacket()
	{

	}

	public PlayerInformPacket(String dmesg)
	{
		this.dmesg = dmesg;
	}

	@Override
	public void fromBytes(ByteBuf buf) {

		dmesg = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {

		ByteBufUtils.writeUTF8String(buf, dmesg);
	}

	public static class Handler implements IMessageHandler<PlayerInformPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PlayerInformPacket m, MessageContext ctx) {
			try {

				MainRegistry.proxy.displayTooltip(m.dmesg);

			} catch (Exception x) { }
			return null;
		}
	}
}
