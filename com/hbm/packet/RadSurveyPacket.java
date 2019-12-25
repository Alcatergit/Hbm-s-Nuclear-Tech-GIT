package com.hbm.packet;

import com.hbm.capability.RadiationCapability;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RadSurveyPacket implements IMessage {
	float rad;

	public RadSurveyPacket()
	{
		
	}

	public RadSurveyPacket(float rad)
	{
		this.rad = rad;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		rad = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		buf.writeFloat(rad);
	}

	public static class Handler implements IMessageHandler<RadSurveyPacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(RadSurveyPacket m, MessageContext ctx) {
			try {
				
				EntityPlayer player = Minecraft.getMinecraft().player;
				if(player.hasCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null))
					player.getCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null).setRads(m.rad);
			} catch (Exception x) { }
			return null;
		}
	}
}
