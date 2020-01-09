package com.hbm.packet;

import com.hbm.interfaces.IClientRequestUpdator;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientRequestUpdatePacket implements IMessage {

	int x, y, z;
	
	public ClientRequestUpdatePacket() {
	}
	
	public ClientRequestUpdatePacket(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}
	
	public static class Handler implements IMessageHandler<ClientRequestUpdatePacket, IMessage> {

		@Override
		public IMessage onMessage(ClientRequestUpdatePacket message, MessageContext ctx) {
			ctx.getServerHandler().player.getServer().addScheduledTask(() -> {
				BlockPos pos = new BlockPos(message.x, message.y, message.z);
				World world = ctx.getServerHandler().player.world;
				if(world.isBlockLoaded(pos)){
					TileEntity te = world.getTileEntity(pos);
					if(te instanceof IClientRequestUpdator)
						((IClientRequestUpdator)te).requestClientUpdate();
				}
			});
			return null;
		}
		
	}
}
