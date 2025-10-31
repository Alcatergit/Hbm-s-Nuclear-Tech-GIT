package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.lib.ModDamageSource;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityBroadcaster extends TileEntity implements ITickable {

	@Override
	public void update() {
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos.getX() + 0.5 - 25, pos.getY() + 0.5 - 25, pos.getZ() + 0.5 - 25, pos.getX() + 0.5 + 25, pos.getY() + 0.5 + 25, pos.getZ() + 0.5 + 25));
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i) instanceof EntityLivingBase) {
				EntityLivingBase e = (EntityLivingBase)list.get(i);
				double d = Math.sqrt(Math.pow(e.posX - (pos.getX() + 0.5), 2) + Math.pow(e.posY - (pos.getY() + 0.5), 2) + Math.pow(e.posZ - (pos.getZ() + 0.5), 2));
				
				if(d <= 25) {
					double t = (25 - d) / 25 * 10;
					e.attackEntityFrom(ModDamageSource.broadcast, (float) t);
					if(!(e instanceof EntityPlayer && (((EntityPlayer) e).capabilities.isCreativeMode || ((EntityPlayer) e).isSpectator())))
						if(e.getActivePotionEffect(MobEffects.NAUSEA) == null || e.getActivePotionEffect(MobEffects.NAUSEA).getDuration() < 100)
							e.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 300, 0));
				}
			}
		}

		if(!world.isRemote) {
			PacketDispatcher.wrapper.sendToAllAround(new LoopedSoundPacket(pos.getX(), pos.getY(), pos.getZ()), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 500));
		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
