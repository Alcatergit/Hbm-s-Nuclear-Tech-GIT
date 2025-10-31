package com.hbm.packet;

import com.hbm.main.MainRegistry;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AuxParticlePacket extends RecordablePacket {

	double x;
	double y;
	double z;
	int type;

	public AuxParticlePacket()
	{
		
	}

	public AuxParticlePacket(double x, double y, double z, int type)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
	}

	@Override
	public void fromBits(LeafiaBuf buf) {
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		type = buf.readInt();
	}

	@Override
	public void toBits(LeafiaBuf buf) {
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeInt(type);
	}

	public static class Handler implements IMessageHandler<AuxParticlePacket, IMessage> {
		
		@Override
		public IMessage onMessage(AuxParticlePacket m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				try {
					
					MainRegistry.proxy.particleControl(m.x, m.y, m.z, m.type);
					
				} catch(Exception x) { }
			});
			
			
			return null;
		}
	}
}
