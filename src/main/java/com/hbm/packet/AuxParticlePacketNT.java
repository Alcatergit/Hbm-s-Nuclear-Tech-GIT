package com.hbm.packet;

import com.hbm.main.MainRegistry;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AuxParticlePacketNT extends RecordablePacket {
	
	NBTTagCompound nbt;

	public AuxParticlePacketNT() { }

	public AuxParticlePacketNT(NBTTagCompound nbt, double x, double y, double z) {
		
		//this.buffer = new PacketBuffer(Unpooled.buffer()); // fuck you why

		nbt.setDouble("posX", x);
		nbt.setDouble("posY", y);
		nbt.setDouble("posZ", z);
		
		this.nbt = nbt;
	}

	@Override
	public void fromBits(LeafiaBuf buf) {
		nbt = buf.readNBT();
	}

	@Override
	public void toBits(LeafiaBuf buf) {
		buf.writeNBT(nbt);
	}

	public static class Handler implements IMessageHandler<AuxParticlePacketNT, IMessage> {
		
		@Override
		public IMessage onMessage(AuxParticlePacketNT m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				if(Minecraft.getMinecraft().world == null)
					return;

					
					NBTTagCompound nbt = m.nbt;
					
					if(nbt != null)
						MainRegistry.proxy.effectNT(nbt);
					

			});
			
			return null;
		}
	}

}
