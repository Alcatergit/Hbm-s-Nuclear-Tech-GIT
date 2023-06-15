package com.hbm.packet;

import com.hbm.interfaces.IDoor;
import com.hbm.tileentity.machine.TileEntityBlastDoor;
import com.hbm.tileentity.machine.TileEntityVaultDoor;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TEVaultPacket implements IMessage {

	int x;
	int y;
	int z;
	boolean isOpening;
	int state;
	long sysTime;
	int type;

	public TEVaultPacket() {

	}

	public TEVaultPacket(int x, int y, int z, int state, long sysTime, int type) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.state = state;
		this.sysTime = sysTime;
		this.type = type;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		state = buf.readInt();
		sysTime = buf.readLong();
		type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(state);
		buf.writeLong(sysTime);
		buf.writeInt(type);
	}

	public static class Handler implements IMessageHandler<TEVaultPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(TEVaultPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(m.x, m.y, m.z));

			try {
				if (te != null && te instanceof TileEntityVaultDoor) {

					TileEntityVaultDoor vault = (TileEntityVaultDoor) te;
					vault.state = IDoor.DoorState.values()[m.state];
					if(m.sysTime == 1)
						vault.sysTime = System.currentTimeMillis();
					vault.type = m.type;
				}
				
				if (te != null && te instanceof TileEntityBlastDoor) {

					TileEntityBlastDoor vault = (TileEntityBlastDoor) te;
					vault.state = IDoor.DoorState.values()[m.state];
					if(m.sysTime == 1)
						vault.sysTime = System.currentTimeMillis();
				}
			} catch (Exception x) {
			}
			return null;
		}
	}
}
