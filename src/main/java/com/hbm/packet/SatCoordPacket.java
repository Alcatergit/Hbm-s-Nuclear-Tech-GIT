package com.hbm.packet;

import com.hbm.items.tool.ItemSatInterface;
import com.hbm.saveddata.satellites.Satellite;
import com.hbm.saveddata.satellites.SatelliteSavedData;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SatCoordPacket implements IMessage {

	int x;
	int y;
	int z;
	int freq;

	public SatCoordPacket()
	{
		
	}

	public SatCoordPacket(int x, int y, int z, int freq)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.freq = freq;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		freq = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(freq);
	}

	public static class Handler implements IMessageHandler<SatCoordPacket, IMessage> {
		
		@Override
		public IMessage onMessage(SatCoordPacket m, MessageContext ctx) {
			ctx.getServerHandler().player.getServer().addScheduledTask(() -> {
				EntityPlayer p = ctx.getServerHandler().player;
				
				if(p.getHeldItemMainhand().getItem() instanceof ItemSatInterface) {
					
					int freq = ItemSatInterface.getFreq(p.getHeldItemMainhand());
					
					if(freq == m.freq) {
					    Satellite sat = SatelliteSavedData.getData(p.world).getSatFromFreq(m.freq);
					    
					    if(sat != null)
					    	sat.onCoordAction(p.world, p, m.x, m.y, m.z);
					}
				}
			});
			
			return null;
		}
	}
}