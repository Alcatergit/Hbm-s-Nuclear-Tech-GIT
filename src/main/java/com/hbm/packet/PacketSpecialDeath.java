package com.hbm.packet;

import java.lang.reflect.Method;
import java.util.Random;

import com.hbm.lib.ModDamageSource;
import com.hbm.main.ModEventHandlerClient;
import com.hbm.particle.DisintegrationParticleHandler;
import com.hbm.particle.ParticleBlood;
import com.hbm.particle.ParticleSlicedMob;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.util.ModelRendererUtil;
import com.hbm.render.util.Triangle;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
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
					case 3:
						//ModEventHandlerClient.specialDeathEffectEntities.add((EntityLivingBase) ent);
						float[] plane = m.auxData;
						ParticleSlicedMob[] particles = ModelRendererUtil.generateCutParticles(ent, plane, capTris -> {
							int bloodCount = 5;
							if(capTris.isEmpty()){
								return;
							}
							for(int i = 0; i < bloodCount; i ++){
								Triangle randTriangle = capTris.get(ent.world.rand.nextInt(capTris.size()));
								//Hopefully this works for getting a random position in a triangle
								float rand1 = ent.world.rand.nextFloat();
								float rand2 = ent.world.rand.nextFloat();
								if(rand2 < rand1){
									float tmp = rand2;
									rand2 = rand1;
									rand1 = tmp;
								}
								Vec3d pos = randTriangle.p1.pos.scale(rand1);
								pos = pos.add(randTriangle.p2.pos.scale(rand2-rand1));
								pos = pos.add(randTriangle.p3.pos.scale(1-rand2));
								pos = pos.addVector(ent.posX, ent.posY, ent.posZ);
								
								Random rand = ent.world.rand;
								ParticleBlood blood = new ParticleBlood(ent.world, pos.x, pos.y, pos.z, 1, 0.4F+rand.nextFloat()*0.4F, 18+rand.nextInt(10), 0.05F);
								Vec3d direction = Minecraft.getMinecraft().player.getLook(1).crossProduct(new Vec3d(plane[0], plane[1], plane[2])).normalize().scale(-0.6F);
								Vec3d randMotion = new Vec3d(rand.nextDouble()*2-1, rand.nextDouble()*2-1, rand.nextDouble()*2-1).scale(0.2F);
								direction = direction.add(randMotion);
								blood.motion((float)direction.x, (float)direction.y, (float)direction.z);
								blood.color(0.5F, 0.1F, 0.1F, 1F);
								blood.onUpdate();
								Minecraft.getMinecraft().effectRenderer.addEffect(blood);
							}
						});
						for(ParticleSlicedMob p : particles)
							Minecraft.getMinecraft().effectRenderer.addEffect(p);
					}
				}
			});
			return null;
		}
		
	}
	
}
