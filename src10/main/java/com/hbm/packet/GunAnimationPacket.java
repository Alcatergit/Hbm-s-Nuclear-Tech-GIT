package com.hbm.packet;

import com.hbm.items.weapon.ItemGunBase;
import com.hbm.render.anim.HbmAnimations.AnimType;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GunAnimationPacket implements IMessage {

	int type;
	EnumHand hand;

	public GunAnimationPacket() { }

	public GunAnimationPacket(int type, EnumHand hand) {
		this.type = type;
		this.hand = hand;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		type = buf.readInt();
		hand = buf.readInt() > 0 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(type);
		buf.writeInt(hand == EnumHand.MAIN_HAND ? 0 : 1);
	}

	public static class Handler implements IMessageHandler<GunAnimationPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(GunAnimationPacket m, MessageContext ctx) {

			try {
				EntityPlayer player = Minecraft.getMinecraft().player;
				ItemStack stack = player.getHeldItem(m.hand);
				int slot = player.inventory.currentItem;
				if(m.hand == EnumHand.OFF_HAND)
					slot = 9;

				if(stack == null)
					return null;

				if(!(stack.getItem() instanceof ItemGunBase))
					return null;

				if(m.type < 0 || m.type >= AnimType.values().length)
					return null;
				
				
				AnimType type = AnimType.values()[m.type];
				((ItemGunBase) stack.getItem()).startAnim(player, stack, slot, type);

			} catch(Exception x) { }

			return null;
		}
	}
}