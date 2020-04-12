package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hbm.entity.missile.EntityMissileAntiBallistic;
import com.hbm.entity.missile.EntityMissileBaseAdvanced;
import com.hbm.entity.missile.EntityMissileCustom;
import com.hbm.interfaces.IConsumer;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TERadarPacket;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMachineRadar extends TileEntity implements ITickable, IConsumer {

	public static List<Entity> allMissiles = new ArrayList<Entity>();
	public List<int[]> nearbyMissiles = new ArrayList<int[]>();
	int pingTimer = 0;
	final static int maxTimer = 40;

	public long power = 0;
	public static final int maxPower = 100000;
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		power = compound.getLong("power");
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("power", power);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void update() {
		if(pos.getY() < MainRegistry.radarAltitude)
			return;
		
		if(!world.isRemote)
			nearbyMissiles.clear();
		
		if(power > 0) {

			if(!world.isRemote) {
				allocateMissiles();
				sendMissileData();
			}
			
			power -= 500;
			if(power < 0)
				power = 0;
		}
		
		world.notifyNeighborsOfStateChange(pos, getBlockType(), true);
		
		if(!world.isRemote)
			PacketDispatcher.wrapper.sendToAllTracking(new AuxElectricityPacket(pos, power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
		
		pingTimer++;
		
		if(power > 0 && pingTimer >= maxTimer) {
			this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.sonarPing, SoundCategory.BLOCKS, 5.0F, 1.0F);
			pingTimer = 0;
		}
	}
	
	private void allocateMissiles() {
		
		nearbyMissiles.clear();
		
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos.getX() + 0.5 - MainRegistry.radarRange, 0, pos.getZ() + 0.5 - MainRegistry.radarRange, pos.getX() + 0.5 + MainRegistry.radarRange, 5000, pos.getZ() + 0.5 + MainRegistry.radarRange));

		for(Entity e : list) {
			/*if(e instanceof EntityMissileBaseAdvanced) {
				EntityMissileBaseAdvanced mis = (EntityMissileBaseAdvanced)e;
				nearbyMissiles.add(new int[] { (int)mis.posX, (int)mis.posZ, mis.getMissileType() });
			}*/
			
			/*if(e instanceof EntityRocketHoming && e.posY >= yCoord + MainRegistry.radarBuffer) {
				EntityRocketHoming rocket = (EntityRocketHoming)e;
				
				if(rocket.getIsCritical())
					nearbyMissiles.add(new int[] { (int)e.posX, (int)e.posZ, 7, (int)e.posY });
				else
					nearbyMissiles.add(new int[] { (int)e.posX, (int)e.posZ, 6, (int)e.posY });
				
				continue;
			}*/

			if(e instanceof EntityPlayer && e.posY >= pos.getY() + MainRegistry.radarBuffer) {
				nearbyMissiles.add(new int[] { (int)e.posX, (int)e.posZ, 5, (int)e.posY });
			}
			
			if(e instanceof EntityMissileAntiBallistic && e.posY >= pos.getY() + MainRegistry.radarBuffer) {
				nearbyMissiles.add(new int[] { (int)e.posX, (int)e.posZ, 4, (int)e.posY });
			}
		}
		
		for(Iterator<Entity> itr = allMissiles.iterator(); itr.hasNext();){
			Entity e = itr.next();
			if(e.isDead){
				itr.remove();
				continue;
			}
			if(e != null && e.posY >= pos.getY() + MainRegistry.radarBuffer){
				if(e instanceof EntityMissileBaseAdvanced) {
					if(e.posX < pos.getX() + MainRegistry.radarRange && e.posX > pos.getX() - MainRegistry.radarRange &&
							e.posZ < pos.getZ() + MainRegistry.radarRange && e.posZ > pos.getZ() - MainRegistry.radarRange) {
						EntityMissileBaseAdvanced mis = (EntityMissileBaseAdvanced)e;
						nearbyMissiles.add(new int[] { (int)mis.posX, (int)mis.posZ, mis.getMissileType().ordinal(), (int)mis.posY });
					}
				} else if(e instanceof EntityMissileCustom){
					if(e.posX < pos.getX() + MainRegistry.radarRange && e.posX > pos.getX() - MainRegistry.radarRange &&
							e.posZ < pos.getZ() + MainRegistry.radarRange && e.posZ > pos.getZ() - MainRegistry.radarRange)
						nearbyMissiles.add(new int[] {(int)e.posX, (int)e.posZ, 6, (int)e.posY});
				}
			}
		}
		
		for(Entity e : allMissiles) {
			if(e != null && !e.isDead && e.posY >= pos.getY() + MainRegistry.radarBuffer)
				if(e instanceof EntityMissileBaseAdvanced) {
					if(e.posX < pos.getX() + MainRegistry.radarRange && e.posX > pos.getX() - MainRegistry.radarRange &&
							e.posZ < pos.getZ() + MainRegistry.radarRange && e.posZ > pos.getZ() - MainRegistry.radarRange) {
						EntityMissileBaseAdvanced mis = (EntityMissileBaseAdvanced)e;
						nearbyMissiles.add(new int[] { (int)mis.posX, (int)mis.posZ, mis.getMissileType().ordinal(), (int)mis.posY });
					}
				}
		}
	}
	
	public int getRedPower() {
		
		if(!nearbyMissiles.isEmpty()) {
			
			double maxRange = MainRegistry.radarRange * Math.sqrt(2D);
			
			int power = 0;
			
			for(int i = 0; i < nearbyMissiles.size(); i++) {
				
				int[] j = nearbyMissiles.get(i);
				double dist = Math.sqrt(Math.pow(j[0] - pos.getX(), 2) + Math.pow(j[1] - pos.getZ(), 2));
				int p = 15 - (int)Math.floor(dist / maxRange * 15);
				
				if(p > power)
					power = p;
			}
			
			return power;
		}
		
		return 0;
	}
	
	private void sendMissileData() {
		
		//PacketDispatcher.wrapper.sendToAllAround(new TERadarDestructorPacket(pos), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
		
		PacketDispatcher.wrapper.sendToAllAround(new TERadarPacket(pos, nearbyMissiles), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}

	@Override
	public void setPower(long i) {
		if(power != i)
			markDirty();
		power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
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
