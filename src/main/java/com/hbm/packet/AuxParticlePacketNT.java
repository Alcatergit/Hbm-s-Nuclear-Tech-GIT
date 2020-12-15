package com.hbm.packet;

import java.io.IOException;

import com.hbm.main.MainRegistry;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AuxParticlePacketNT implements IMessage {
	
	PacketBuffer buffer;

	public AuxParticlePacketNT() { }

	public AuxParticlePacketNT(NBTTagCompound nbt, double x, double y, double z) {
		
		this.buffer = new PacketBuffer(Unpooled.buffer());

		nbt.setDouble("posX", x);
		nbt.setDouble("posY", y);
		nbt.setDouble("posZ", z);
		
		buffer.writeCompoundTag(nbt);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		if (buffer == null) {
			buffer = new PacketBuffer(Unpooled.buffer());
		}
		buffer.writeBytes(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		if (buffer == null) {
			buffer = new PacketBuffer(Unpooled.buffer());
		}
		buf.writeBytes(buffer);
	}

	public static class Handler implements IMessageHandler<AuxParticlePacketNT, IMessage> {
		
		@Override
		public IMessage onMessage(AuxParticlePacketNT m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				if(Minecraft.getMinecraft().world == null)
					return;
				
				try {
					
					NBTTagCompound nbt = m.buffer.readCompoundTag();
					
					if(nbt != null)
						MainRegistry.proxy.effectNT(nbt);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
			return null;
		}
	}

}
