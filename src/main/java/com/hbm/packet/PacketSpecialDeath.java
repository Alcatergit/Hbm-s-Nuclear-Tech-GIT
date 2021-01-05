package com.hbm.packet;

import java.lang.reflect.Method;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.ModDamageSource;
import com.hbm.main.ModEventHandlerClient;
import com.hbm.particle.DisintegrationParticleHandler;
import com.hbm.render.amlfrom1710.Vec3;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSpecialDeath implements IMessage {

	public static Method rGetHurtSound;
	
	int entId;
	int effectId;
	float[] auxData;
	
	public PacketSpecialDeath() {
	}
	
	public PacketSpecialDeath(Entity ent, int effectId, float... auxData) {
		this.effectId = effectId;
		this.entId = ent.getEntityId();
		this.auxData = auxData;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entId = buf.readInt();
		effectId = buf.readInt();
		int len = buf.readByte();
		auxData = new float[len];
		for(int i = 0; i < len; i++){
			auxData[i] = buf.readFloat();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entId);
		buf.writeInt(effectId);
		buf.writeByte(auxData.length);
		for(float f : auxData){
			buf.writeFloat(f);
		}
	}
	
	public static class Handler implements IMessageHandler<PacketSpecialDeath, IMessage> {

		@SuppressWarnings("deprecation")
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PacketSpecialDeath m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				Entity ent = Minecraft.getMinecraft().world.getEntityByID(m.entId);
				if(ent instanceof EntityLivingBase){
					switch(m.effectId){
					case 0:
						ModEventHandlerClient.specialDeathEffectEntities.add((EntityLivingBase) ent);
						DisintegrationParticleHandler.spawnGluonDisintegrateParticles(ent);
						break;
					case 1:
						((EntityLivingBase) ent).hurtTime = 2;
						try {
							if(rGetHurtSound == null)
								rGetHurtSound = ReflectionHelper.findMethod(EntityLivingBase.class, "getHurtSound", "func_184601_bQ", DamageSource.class);
							SoundEvent s = (SoundEvent) rGetHurtSound.invoke(ent, ModDamageSource.radiation);
							Minecraft.getMinecraft().world.playSound(ent.posX, ent.posY, ent.posZ, s, SoundCategory.MASTER, 1, 1, false);
						} catch(Exception e) {
							e.printStackTrace();
						}
						break;
					case 2:
						ModEventHandlerClient.specialDeathEffectEntities.add((EntityLivingBase) ent);
						DisintegrationParticleHandler.spawnLightningDisintegrateParticles(ent, new Vec3(m.auxData[0], m.auxData[1], m.auxData[2]));
						break;
					}
				}
			});
			return null;
		}
		
	}
	
}
