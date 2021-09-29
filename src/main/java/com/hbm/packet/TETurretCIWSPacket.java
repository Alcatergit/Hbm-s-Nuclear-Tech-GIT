package com.hbm.packet;

import com.hbm.tileentity.turret.TileEntityTurretCIWS;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TETurretCIWSPacket implements IMessage {

	int x, y, z;
	double rotY, rotP;
	
	public TETurretCIWSPacket() {
	}
	
	public TETurretCIWSPacket(int x, int y, int z, double rotY, double rotP) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.rotP = rotP;
		this.rotY = rotY;
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		rotP = buf.readDouble();
		rotY = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeDouble(rotP);
		buf.writeDouble(rotY);
		
	}
	
	public static class Handler implements IMessageHandler<TETurretCIWSPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(TETurretCIWSPacket message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				BlockPos pos = new BlockPos(message.x, message.y, message.z);
				TileEntity te = Minecraft.getMinecraft().world.getTileEntity(pos);
				if(te instanceof TileEntityTurretCIWS){
					((TileEntityTurretCIWS)te).rotationYaw = message.rotY;
					((TileEntityTurretCIWS)te).rotationPitch = message.rotP;
				}
			});
			return null;
		}
		
	}

}
