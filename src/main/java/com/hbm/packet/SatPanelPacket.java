package com.hbm.packet;

import com.hbm.items.tool.ItemSatInterface;
import com.hbm.saveddata.satellites.Satellite;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SatPanelPacket extends RecordablePacket {
	
	NBTTagCompound buffer;
	int type;

	public SatPanelPacket() {

	}

	public SatPanelPacket(Satellite sat) {
		type = sat.getID();

		NBTTagCompound nbt = new NBTTagCompound();
		sat.writeToNBT(nbt);
		
		buffer=(nbt);
	}

	@Override
	public void fromBits(LeafiaBuf buf) {
		
		type = buf.readInt();

		buffer=buf.readNBT();
	}

	@Override
	public void toBits(LeafiaBuf buf) {
		
		buf.writeInt(type);

		buf.writeNBT(buffer);
	}

	public static class Handler implements IMessageHandler<SatPanelPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(SatPanelPacket m, MessageContext ctx) {
			
			Minecraft.getMinecraft().addScheduledTask(() -> {
				 {
					NBTTagCompound nbt = m.buffer;
					ItemSatInterface.currentSat = Satellite.create(m.type);
					
					if(nbt != null)
						ItemSatInterface.currentSat.readFromNBT(nbt);
					
				}
			});
			
			return null;
		}
	}
}