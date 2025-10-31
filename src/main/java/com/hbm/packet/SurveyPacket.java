package com.hbm.packet;

import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBase;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SurveyPacket extends RecordablePacket {
	int rbmkHeight;

	public SurveyPacket(){
	}

	public SurveyPacket(int rbmkHeight){
		this.rbmkHeight = rbmkHeight;
	}

	@Override
	public void fromBits(LeafiaBuf buf){

		rbmkHeight = buf.readInt();
	}

	@Override
	public void toBits(LeafiaBuf buf){
		buf.writeInt(rbmkHeight);
	}

	public static class Handler implements IMessageHandler<SurveyPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(SurveyPacket m, MessageContext ctx){

			Minecraft.getMinecraft().addScheduledTask(() -> {
				try {
					TileEntityRBMKBase.rbmkHeight = m.rbmkHeight;
				} catch(Exception x) {
				}
			});

			return null;
		}
	}
}
