package com.hbm.packet;

import com.hbm.tileentity.bomb.TileEntityRailgun;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RailgunRotationPacket implements IMessage {

	int x;
	int y;
	int z;
	float pitch;
	float yaw;

	public RailgunRotationPacket() { }

	public RailgunRotationPacket(int x, int y, int z, float pitch, float yaw)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		pitch = buf.readFloat();
		yaw = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeFloat(pitch);
		buf.writeFloat(yaw);
	}

	public static class Handler implements IMessageHandler<RailgunRotationPacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(RailgunRotationPacket m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				try {
					TileEntity te = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(m.x, m.y, m.z));

					if (te != null && te instanceof TileEntityRailgun) {
							
						TileEntityRailgun gun = (TileEntityRailgun) te;
						
						gun.pitch = m.pitch;
						gun.yaw = m.yaw;
					}
					
				} catch (Exception x) { }
			});
			
			return null;
		}
	}
}
