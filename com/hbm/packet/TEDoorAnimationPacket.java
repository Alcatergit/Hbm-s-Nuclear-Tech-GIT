package com.hbm.packet;

import com.hbm.interfaces.IAnimatedDoor;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TEDoorAnimationPacket implements IMessage {

	public int x, y, z;
	public byte state;
	
	public TEDoorAnimationPacket() {
	}
	
	public TEDoorAnimationPacket(BlockPos pos, byte state) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.state = state;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		state = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeByte(state);
	}
	
	public static class Handler implements IMessageHandler<TEDoorAnimationPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(TEDoorAnimationPacket m, MessageContext ctx) {
			
			Minecraft.getMinecraft().addScheduledTask(() -> {
				BlockPos pos = new BlockPos(m.x, m.y, m.z);
				TileEntity te = Minecraft.getMinecraft().world.getTileEntity(pos);
				if(te instanceof IAnimatedDoor){
					((IAnimatedDoor) te).handleNewState(m.state);
				}
			});
			return null;
		}
		
	}

}
