package com.hbm.packet;

import com.hbm.tileentity.network.energy.TileEntityPylonBase;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TEPylonDestructorPacket extends RecordablePacket {

	int x;
	int y;
	int z;

	public TEPylonDestructorPacket()
	{
		
	}

	public TEPylonDestructorPacket(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBits(LeafiaBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}

	@Override
	public void toBits(LeafiaBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

	public static class Handler implements IMessageHandler<TEPylonDestructorPacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEPylonDestructorPacket m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				TileEntity te = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(m.x, m.y, m.z));

				if (te != null && te instanceof TileEntityPylonBase) {
						
					TileEntityPylonBase pyl = (TileEntityPylonBase) te;
					pyl.disconnectAll();
				}
			});
			
			return null;
		}
	}
}
