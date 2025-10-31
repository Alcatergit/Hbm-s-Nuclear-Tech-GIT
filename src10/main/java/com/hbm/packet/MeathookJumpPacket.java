package com.hbm.packet;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGunShotty;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MeathookJumpPacket implements IMessage {

	public MeathookJumpPacket() {
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}
	
	public static class Handler implements IMessageHandler<MeathookJumpPacket, IMessage> {

		@Override
		public IMessage onMessage(MeathookJumpPacket message, MessageContext ctx) {
			EntityPlayer p = ctx.getServerHandler().player;
			if(p.getHeldItemMainhand().getItem() == ModItems.gun_supershotgun && ItemGunShotty.hasHookedEntity(p.world, p.getHeldItemMainhand())){
				ItemGunShotty.setHookedEntity(p, p.getHeldItemMainhand(), null);
			}
			return null;
		}
		
	}

}
