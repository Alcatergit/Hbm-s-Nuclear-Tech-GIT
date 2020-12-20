package com.hbm.packet;

import com.hbm.tileentity.machine.TileEntityStructureMarker;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TEStructurePacket implements IMessage {

	int x;
	int y;
	int z;
	int type;

	public TEStructurePacket()
	{
		
	}

	public TEStructurePacket(int x, int y, int z, int type)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(type);
	}

	public static class Handler implements IMessageHandler<TEStructurePacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(TEStructurePacket m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				TileEntity te = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(m.x, m.y, m.z));

				if (te != null && te instanceof TileEntityStructureMarker) {
						
					TileEntityStructureMarker marker = (TileEntityStructureMarker) te;
					marker.type = m.type;
				}
			});
			
			return null;
		}
	}
}
