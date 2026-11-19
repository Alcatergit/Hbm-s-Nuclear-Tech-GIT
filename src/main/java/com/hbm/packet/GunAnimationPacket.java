package com.hbm.packet;

import java.nio.charset.StandardCharsets;

import com.hbm.items.weapon.ItemGunBase;
import com.hbm.render.anim.HbmAnimations.AnimType;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GunAnimationPacket implements IMessage {

	int type;
	EnumHand hand;
	String soundName;
	float volume = 1.0F;
	float pitch = 1.0F;
	boolean stopSound;

	public GunAnimationPacket() { }

	public GunAnimationPacket(int type, EnumHand hand) {
		this.type = type;
		this.hand = hand;
	}

	public GunAnimationPacket(SoundEvent sound, float volume, float pitch, EnumHand hand) {
		this.type = -1;
		this.hand = hand;
		this.soundName = sound.getSoundName().toString();
		this.volume = volume;
		this.pitch = pitch;
	}

	public GunAnimationPacket(SoundEvent sound, EnumHand hand) {
		this.type = -1;
		this.hand = hand;
		this.soundName = sound.getSoundName().toString();
		this.stopSound = true;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		type = buf.readInt();
		hand = buf.readInt() > 0 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
		if(buf.readBoolean()) {
			int len = buf.readInt();
			byte[] bytes = new byte[len];
			buf.readBytes(bytes);
			soundName = new String(bytes, StandardCharsets.UTF_8);
			volume = buf.readFloat();
			pitch = buf.readFloat();
			stopSound = buf.readBoolean();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(type);
		buf.writeInt(hand == EnumHand.MAIN_HAND ? 0 : 1);
		if(soundName != null) {
			buf.writeBoolean(true);
			byte[] bytes = soundName.getBytes(StandardCharsets.UTF_8);
			buf.writeInt(bytes.length);
			buf.writeBytes(bytes);
			buf.writeFloat(volume);
			buf.writeFloat(pitch);
			buf.writeBoolean(stopSound);
		} else {
			buf.writeBoolean(false);
		}
	}

	public static class Handler implements IMessageHandler<GunAnimationPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(GunAnimationPacket m, MessageContext ctx) {

			try {
				EntityPlayer player = Minecraft.getMinecraft().player;

				if(m.stopSound && m.soundName != null) {
					Minecraft.getMinecraft().getSoundHandler().stop(m.soundName, SoundCategory.PLAYERS);
					return null;
				}

				ItemStack stack = player.getHeldItem(m.hand);
				int slot = player.inventory.currentItem;
				if(m.hand == EnumHand.OFF_HAND)
					slot = 9;

				if(stack == null)
					return null;

				if(!(stack.getItem() instanceof ItemGunBase))
					return null;

				if(m.soundName != null) {
					SoundEvent sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(m.soundName));
					if(sound != null)
						ItemGunBase.playGunSound(player, sound, m.volume, m.pitch, m.hand);
				}

				if(m.type >= 0 && m.type < AnimType.values().length) {
					AnimType type = AnimType.values()[m.type];
					((ItemGunBase) stack.getItem()).startAnim(player, stack, slot, type);
				}

			} catch(Exception x) { }

			return null;
		}
	}
}
