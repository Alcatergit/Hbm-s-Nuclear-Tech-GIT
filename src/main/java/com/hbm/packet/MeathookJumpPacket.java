package com.hbm.packet;

import com.hbm.items.ModItems.Armory;
import com.hbm.items.weapon.ItemGunShotty;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MeathookJumpPacket extends RecordablePacket {

	public MeathookJumpPacket() {
	}
	
	@Override
	public void fromBits(LeafiaBuf buf) {
	}

	@Override
	public void toBits(LeafiaBuf buf) {
	}
	
	public static class Handler implements IMessageHandler<MeathookJumpPacket, IMessage> {

		@Override
		public IMessage onMessage(MeathookJumpPacket message, MessageContext ctx) {
			EntityPlayer p = ctx.getServerHandler().player;
			if(p.getHeldItemMainhand().getItem() == Armory.gun_supershotgun && ItemGunShotty.hasHookedEntity(p.world, p.getHeldItemMainhand())){
				ItemGunShotty.setHookedEntity(p, p.getHeldItemMainhand(), null);
			}
			return null;
		}
		
	}

}
