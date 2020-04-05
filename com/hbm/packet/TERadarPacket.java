package com.hbm.packet;

import com.hbm.tileentity.machine.TileEntityMachineRadar;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TERadarPacket implements IMessage {

	int x;
	int y;
	int z;
	int conX;
	int conY;
	int conZ;
	int alt;

	public TERadarPacket() {

	}

	public TERadarPacket(BlockPos pos, int conX, int conY, int conZ, int alt) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.conX = conX;
		this.conY = conY;
		this.conZ = conZ;
		this.alt = alt;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		conX = buf.readInt();
		conY = buf.readInt();
		conZ = buf.readInt();
		alt = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(conX);
		buf.writeInt(conY);
		buf.writeInt(conZ);
		buf.writeInt(alt);
	}

	public static class Handler implements IMessageHandler<TERadarPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(TERadarPacket m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				TileEntity te = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(m.x, m.y, m.z));

				try {
					if (te != null && te instanceof TileEntityMachineRadar) {

						TileEntityMachineRadar radar = (TileEntityMachineRadar) te;
						radar.nearbyMissiles.add(new int[]{m.conX, m.conY, m.conZ, m.alt});
					}
				} catch (Exception x) {
				}
			});
			return null;
		}
	}
}
