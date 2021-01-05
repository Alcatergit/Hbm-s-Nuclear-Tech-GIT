package com.hbm.packet;

import com.hbm.capability.HbmCapability;
import com.hbm.capability.HbmCapability.IHBMData;
import com.hbm.handler.HbmKeybinds.EnumKeybind;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class KeybindPacket implements IMessage {

	int key;
	boolean pressed;

	public KeybindPacket() { }

	public KeybindPacket(EnumKeybind key, boolean pressed) {
		this.key = key.ordinal();
		this.pressed = pressed;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		key = buf.readInt();
		pressed = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(key);
		buf.writeBoolean(pressed);
	}

	public static class Handler implements IMessageHandler<KeybindPacket, IMessage> {

		@Override
		public IMessage onMessage(KeybindPacket m, MessageContext ctx) {
			ctx.getServerHandler().player.getServer().addScheduledTask(() -> {
				EntityPlayer p = ctx.getServerHandler().player;
				IHBMData props = HbmCapability.getData(p);

				props.setKeyPressed(EnumKeybind.values()[m.key], m.pressed);
			});
			return null;
		}
	}
}