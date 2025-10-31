package com.hbm.packet;

import com.hbm.capability.HbmLivingCapability.IEntityHbmProps;
import com.hbm.capability.HbmLivingProps;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ExtPropPacket extends RecordablePacket {

	//PacketBuffer buffer; Not again.
	NBTTagCompound die;

	public ExtPropPacket(){
	}

	public ExtPropPacket(NBTTagCompound nbt){
		die = nbt;
	}

	@Override
	public void fromBits(LeafiaBuf buf){
		die = buf.readNBT();
	}

	@Override
	public void toBits(LeafiaBuf buf){
		buf.writeNBT(die);
	}

	public static class Handler implements IMessageHandler<ExtPropPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(ExtPropPacket m, MessageContext ctx){
			Minecraft.getMinecraft().addScheduledTask(() -> {
				if(Minecraft.getMinecraft().world == null)
					return;

					NBTTagCompound nbt = m.die;
					IEntityHbmProps props = HbmLivingProps.getData(Minecraft.getMinecraft().player);
					props.loadNBTData(nbt);
			});
			
			return null;
		}
	}
}