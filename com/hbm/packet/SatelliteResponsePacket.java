package com.hbm.packet;

import com.hbm.inventory.gui.GUIScreenSatInterface;
import com.hbm.saveddata.SatelliteSaveData.SatelliteSaveStructure;
import com.hbm.saveddata.SatelliteSaveData.SatelliteType;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SatelliteResponsePacket implements IMessage {

	public SatelliteSaveStructure sat;
	
	public SatelliteResponsePacket() {
	}
	
	public SatelliteResponsePacket(SatelliteSaveStructure sat){
		this.sat = sat;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		int id = buf.readInt();
		int type = buf.readInt();
		long op = buf.readLong();
		sat = new SatelliteSaveStructure(id, SatelliteType.values()[type], op);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(sat.satelliteID);
		buf.writeInt(sat.type.ordinal());
		buf.writeLong(sat.lastOp);
	}
	
	public static class Handler implements IMessageHandler<SatelliteResponsePacket, IMessage> {

		@Override
		public IMessage onMessage(SatelliteResponsePacket m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				if(Minecraft.getMinecraft().currentScreen instanceof GUIScreenSatInterface){
					((GUIScreenSatInterface)Minecraft.getMinecraft().currentScreen).connectedSat = m.sat;
				}
			});
			return null;
		}
		
	}

}
