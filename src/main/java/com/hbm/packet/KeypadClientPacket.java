package com.hbm.packet;

import com.hbm.interfaces.IKeypadHandler;
import com.hbm.util.KeypadClient;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class KeypadClientPacket implements IMessage {

	public int x, y, z;
	byte[] data;
	
	public KeypadClientPacket() {
	}
	
	public KeypadClientPacket(BlockPos pos, byte[] data) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.data = data;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		data = new byte[21];
		buf.readBytes(data);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeBytes(data);
	}

	public static class Handler implements IMessageHandler<KeypadClientPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(KeypadClientPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(m.x, m.y, m.z));
			if(te instanceof IKeypadHandler){
				KeypadClient pad = ((IKeypadHandler) te).getKeypad().client();
				for(int i = 0; i < 12; i ++){
					pad.buttons[i].cooldown = m.data[i];
				}
				pad.isSettingCode = m.data[12] == 1 ? true : false;
				for(int i = 0; i < 6; i ++){
					pad.code[i] = m.data[13 + i];
				}
				pad.successColorTicks = m.data[19];
				pad.failColorTicks = m.data[20];
			}
			return null;
		}
		
	}
	
}
