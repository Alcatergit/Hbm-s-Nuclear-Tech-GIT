package com.hbm.packet;

import com.hbm.items.tool.ItemSatInterface;
import com.hbm.saveddata.satellites.Satellite;
import com.hbm.saveddata.satellites.SatelliteSavedData;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SatLaserPacket implements IMessage {

	//0: Add
	//1: Subtract
	//2: Set
	int x;
	int z;
	int freq;

	public SatLaserPacket()
	{
		
	}

	public SatLaserPacket(int x, int z, int freq)
	{
		this.x = x;
		this.z = z;
		this.freq = freq;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		z = buf.readInt();
		freq = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(z);
		buf.writeInt(freq);
	}

	public static class Handler implements IMessageHandler<SatLaserPacket, IMessage> {
		
		@Override
		public IMessage onMessage(SatLaserPacket m, MessageContext ctx) {
			ctx.getServerHandler().player.getServer().addScheduledTask(() -> {
				EntityPlayer p = ctx.getServerHandler().player;
				if(!ctx.getServerHandler().player.world.isBlockLoaded(new BlockPos(m.x, 0, m.z)))
					return;
				if(p.getHeldItemMainhand().getItem() instanceof ItemSatInterface) {
					
					int freq = ItemSatInterface.getFreq(p.getHeldItemMainhand());
					
					if(freq == m.freq) {
					    Satellite sat = SatelliteSavedData.getData(p.world).getSatFromFreq(m.freq);
					    
					    if(sat != null)
					    	sat.onClick(p.world, m.x, m.z);
					}
				}
			});
			
			return null;
		}
	}
}
