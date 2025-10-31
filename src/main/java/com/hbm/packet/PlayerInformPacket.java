package com.hbm.packet;

import com.hbm.main.MainRegistry;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerInformPacket extends RecordablePacket {

	String dmesg = "";

	public PlayerInformPacket()
	{

	}

	public PlayerInformPacket(String dmesg)
	{
		this.dmesg = dmesg;
	}

	@Override
	public void fromBits(LeafiaBuf buf) {

		dmesg = buf.readUTF8String();
	}

	@Override
	public void toBits(LeafiaBuf buf) {

		buf.writeUTF8String(dmesg);
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
