package com.hbm.packet;

import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemSatChip;
import com.hbm.saveddata.SatelliteSaveData;
import com.hbm.saveddata.SatelliteSaveData.SatelliteSaveStructure;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SatelliteRequestPacket implements IMessage {

	public SatelliteRequestPacket() {
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}
	
	public static class Handler implements IMessageHandler<SatelliteRequestPacket, SatelliteResponsePacket> {

		@Override
		public SatelliteResponsePacket onMessage(SatelliteRequestPacket message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;
			if(player.getHeldItemMainhand().getItem() == ModItems.sat_interface){
				SatelliteSaveStructure sat = SatelliteSaveData.getData(player.world).getSatFromFreq(ItemSatChip.getFreq(player.getHeldItemMainhand()));
				if(sat != null){
					return new SatelliteResponsePacket(sat);
				}
			}
			return null;
		}
		
	}

}
