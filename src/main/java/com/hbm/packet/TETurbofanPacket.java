package com.hbm.packet;

import com.hbm.tileentity.machine.TileEntityMachineTurbofan;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TETurbofanPacket implements IMessage {

	int x;
	int y;
	int z;
	boolean isRunning;

	public TETurbofanPacket()
	{
		
	}

	public TETurbofanPacket(int x, int y, int z, boolean isRunning)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.isRunning = isRunning;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		isRunning = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeBoolean(isRunning);
	}

	public static class Handler implements IMessageHandler<TETurbofanPacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(TETurbofanPacket m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				TileEntity te = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(m.x, m.y, m.z));

				if (te != null && te instanceof TileEntityMachineTurbofan) {
						
					TileEntityMachineTurbofan gen = (TileEntityMachineTurbofan) te;
					gen.isRunning = m.isRunning;
				}
			});
			
			return null;
		}
	}
}
