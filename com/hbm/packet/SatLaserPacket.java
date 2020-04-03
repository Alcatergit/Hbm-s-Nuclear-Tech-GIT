package com.hbm.packet;

import com.hbm.entity.logic.EntityDeathBlast;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemSatChip;
import com.hbm.saveddata.SatelliteSaveData;
import com.hbm.saveddata.SatelliteSaveData.SatelliteSaveStructure;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
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
			
			
			EntityPlayer p = ctx.getServerHandler().player;

			p.getServer().addScheduledTask(() -> {
				if(p.getHeldItemMainhand().getItem() != ModItems.sat_interface || ItemSatChip.getFreq(p.getHeldItemMainhand()) != m.freq){
					return;
				}
			    SatelliteSaveData data = SatelliteSaveData.getData(p.world);
			    
			    SatelliteSaveStructure sat = data.getSatFromFreq(m.freq);
			    
			    if(sat != null) {
			    	if(sat.lastOp + 10000 < System.currentTimeMillis()) {
			    		sat.lastOp = System.currentTimeMillis();
			    		
			    		int y = p.world.getHeight(m.x, m.z);
			    		
			    		//ExplosionLarge.explodeFire(p.worldObj, m.x, y, m.z, 50, true, true, true);
			    		EntityDeathBlast blast = new EntityDeathBlast(p.world);
			    		blast.posX = m.x;
			    		blast.posY = y;
			    		blast.posZ = m.z;
			    		
			    		p.world.spawnEntity(blast);
			    	}
			    }
			});
			
			return null;
		}
	}
}