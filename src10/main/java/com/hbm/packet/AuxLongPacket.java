package com.hbm.packet;

import com.hbm.tileentity.machine.TileEntityCoreEmitter;
import com.hbm.tileentity.machine.TileEntityCoreReceiver;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AuxLongPacket implements IMessage {

	int x;
	int y;
	int z;
	long value;
	int id;

	public AuxLongPacket() {

	}

	public AuxLongPacket(int x, int y, int z, long value, int id) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.value = value;
		this.id = id;
	}

	public AuxLongPacket(BlockPos pos, long value, int id) {
		this(pos.getX(), pos.getY(), pos.getZ(), value, id);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		value = buf.readLong();
		id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeLong(value);
		buf.writeInt(id);
	}

	public static class Handler implements IMessageHandler<AuxLongPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(AuxLongPacket m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				try {
					TileEntity te = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(m.x, m.y, m.z));
					if(te instanceof TileEntityCoreEmitter){
						if(m.id == 0)
							((TileEntityCoreEmitter) te).prev = m.value;
					} else if(te instanceof TileEntityCoreReceiver){
						if(m.id == 0)
							((TileEntityCoreReceiver)te).joules = m.value;
					}
				} catch(Exception x) {
				}
			});

			return null;
		}
	}
}