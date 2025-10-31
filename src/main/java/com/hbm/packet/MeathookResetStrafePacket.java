package com.hbm.packet;

import com.hbm.items.ModItems.Armory;
import com.hbm.items.weapon.ItemGunShotty;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MeathookResetStrafePacket extends RecordablePacket {

	public MeathookResetStrafePacket() {
	}
	
	@Override
	public void fromBits(LeafiaBuf buf) {
	}

	@Override
	public void toBits(LeafiaBuf buf) {
	}
	
	public static class Handler implements IMessageHandler<MeathookResetStrafePacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(MeathookResetStrafePacket message, MessageContext ctx) {
			EntityPlayer p = Minecraft.getMinecraft().player;
			if(p.getHeldItemMainhand().getItem() == Armory.gun_supershotgun){
				ItemGunShotty.motionStrafe = 0;
			}
			return null;
		}
		
	}

}
