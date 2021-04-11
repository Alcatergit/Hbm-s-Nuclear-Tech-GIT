package com.hbm.packet;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.weapon.ItemSwordCutter;
import com.hbm.lib.Library;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketMobSlicer implements IMessage {

	public Vec3d pos;
	public Vec3d norm;
	
	public PacketMobSlicer() {
	}
	
	public PacketMobSlicer(Vec3d position, Vec3d normal) {
		pos = position;
		norm = normal;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		pos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
		norm = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(pos.x);
		buf.writeDouble(pos.y);
		buf.writeDouble(pos.z);
		buf.writeDouble(norm.x);
		buf.writeDouble(norm.y);
		buf.writeDouble(norm.z);
	}
	
	public static class Handler implements IMessageHandler<PacketMobSlicer, IMessage> {

		@Override
		public IMessage onMessage(PacketMobSlicer m, MessageContext ctx) {
			EntityPlayerMP p = ctx.getServerHandler().player;
			if(!(p.getHeldItemMainhand().getItem() instanceof ItemSwordCutter))
				return null;
			p.getServer().addScheduledTask(()->{
				List<EntityLivingBase> attack = new ArrayList<>();
				Vec3d eye = p.getPositionEyes(1);
				Vec3d v1 = p.getLookVec();
				Vec3d v2 = m.pos.subtract(eye);
				for(float i = 0; i <= 1; i += 0.1F){
					Vec3d dir = new Vec3d(v1.x+(v2.x-v1.x)*i, v1.y+(v2.y-v1.y)*i, v1.z+(v2.z-v1.z)*i).normalize();
					RayTraceResult r = Library.rayTraceIncludeEntities(p.world, eye, eye.add(dir.scale(3)), p);
					if(r != null && r.typeOfHit == Type.ENTITY && r.entityHit instanceof EntityLivingBase && !attack.contains(r.entityHit)){
						attack.add((EntityLivingBase) r.entityHit);
					}
				}
				for(EntityLivingBase victim : attack){
					Vec3d pos = m.pos.subtract(victim.posX, victim.posY, victim.posZ);
					float[] plane = new float[]{(float)m.norm.x, (float)m.norm.y, (float)m.norm.z, -(float)m.norm.dotProduct(pos)};
					victim.setDead();
					PacketDispatcher.wrapper.sendToAllTracking(new PacketSpecialDeath(victim, 3, plane), victim);
					if(victim instanceof EntityPlayerMP){
						PacketDispatcher.wrapper.sendTo(new PacketSpecialDeath(victim, 3, plane), (EntityPlayerMP) victim);
					}
				}
			});
			return null;
		}
		
	}
}
