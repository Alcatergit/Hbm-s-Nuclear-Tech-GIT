package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.WeaponConfig;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.RefStrings;
import com.hbm.lib.ForgeDirection;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.TileEntityTickingBase;
import com.hbm.capability.HbmLivingProps;

import api.hbm.energy.IEnergyUser;
import api.hbm.entity.IRadarDetectable;
import api.hbm.entity.IRadarDetectable.RadarTargetType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class TileEntityMachineRadar extends TileEntityTickingBase implements ITickable, IEnergyUser {

	public List<Entity> entList = new ArrayList();
	public List<int[]> nearbyMissiles = new ArrayList<int[]>();
	public int pingTimer = 0;
	public int lastPower;
	final static int maxTimer = 40;

	public boolean scanMissiles = true;
	public boolean scanPlayers = false;
	public boolean smartMode = true;
	public boolean redMode = true;

	public boolean jammed = false;

	public float prevRotation;
	public float rotation;

	public long power = 0;
	public static final int maxPower = 100000;
	
	
	@Override
	public String getInventoryName() {
		return "";
	}
	
	@Override
	public void update() {
		if(pos.getY() < WeaponConfig.radarAltitude)
			return;
		
		
		
		if(!world.isRemote) {

			this.updateConnectionsExcept(world, pos, ForgeDirection.UP);
			
			nearbyMissiles.clear();


			if(power > 0) {
				allocateMissiles();

				power -= 500;

				if(power < 0)
					power = 0;
			}
			
			if(lastPower != getRedPower())
				world.notifyNeighborsOfStateChange(pos, getBlockType(), true);

			sendMissileData();
			lastPower = getRedPower();
			
			if(world.getBlockState(pos.down()).getBlock() != ModBlocks.muffler) {

				pingTimer++;

				if(power > 0 && pingTimer >= maxTimer) {
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.sonarPing, SoundCategory.BLOCKS, 1.0F, 1.0F);
					pingTimer = 0;
				}
			}
			
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos, power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
		} else {

			prevRotation = rotation;
			
			if(power > 0) {
				rotation += 5F;
			}
			
			if(rotation >= 360) {
				rotation -= 360F;
				prevRotation -= 360F;
			}
		}
	}


	public void handleButtonPacket(int value, int meta) {
		
		switch(meta) {
		case 0: this.scanMissiles = !this.scanMissiles; break;
		case 1: this.scanPlayers = !this.scanPlayers; break;
		case 2: this.smartMode = !this.smartMode; break;
		case 3: this.redMode = !this.redMode; break;
		}
	}

	public boolean isEntityApproaching(Entity e){
		boolean xAxisApproaching = (pos.getX() < e.posX  && e.motionX < 0) || (pos.getX() > e.posX  && e.motionX > 0);
		boolean zAxisApproaching = (pos.getZ() < e.posZ && e.motionZ < 0) || (pos.getZ() > e.posZ && e.motionZ > 0);
		return xAxisApproaching && zAxisApproaching;
	}
	
	private void allocateMissiles() {
		
		nearbyMissiles.clear();
		entList.clear();
		jammed = false;
		
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos.getX() + 0.5 - WeaponConfig.radarRange, 0D, pos.getZ() + 0.5 - WeaponConfig.radarRange, pos.getX() + 0.5 + WeaponConfig.radarRange, 10000, pos.getZ() + 0.5 + WeaponConfig.radarRange));

		for(Entity e : list) {
			
			if(e.posY < pos.getY() + WeaponConfig.radarBuffer)
				continue;
			
			if(e instanceof EntityLivingBase && HbmLivingProps.getDigamma((EntityLivingBase) e) > 0.001) {
				this.jammed = true;
				nearbyMissiles.clear();
				entList.clear();
				return;
			}

			if(e instanceof EntityPlayer && this.scanPlayers) {
				nearbyMissiles.add(new int[] { (int)e.posX, (int)e.posZ, RadarTargetType.PLAYER.ordinal(), (int)e.posY });
				entList.add(e);
			}
			
			if(e instanceof IRadarDetectable && this.scanMissiles) {
				nearbyMissiles.add(new int[] { (int)e.posX, (int)e.posZ, ((IRadarDetectable)e).getTargetType().ordinal(), (int)e.posY });
				
				if(this.smartMode){
					if(e.motionY <= 0 && isEntityApproaching(e)){
						entList.add(e);
					}
				}
				else{
					entList.add(e);
				}
			}
		}
	}
	
	public int getRedPower() { //redstone output
		
		if(!entList.isEmpty()) {
			
			/// PROXIMITY ///
			if(redMode) {
				
				double maxRange = WeaponConfig.radarRange * Math.sqrt(2D);
				
				int power = 0;
				
				for(int i = 0; i < entList.size(); i++) {
					
					Entity e = entList.get(i);
					double dist = Math.sqrt(Math.pow(e.posX - pos.getX(), 2) + Math.pow(e.posZ - pos.getZ(), 2));
					int p = 15 - (int)Math.floor(dist / maxRange * 15);
					
					if(p > power)
						power = p;
				}
				
				return power;
				
			/// TIER ///
			} else {
				
				int power = 0;
				
				for(int i = 0; i < nearbyMissiles.size(); i++) {
					
					if(nearbyMissiles.get(i)[3] + 1 > power) {
						power = nearbyMissiles.get(i)[3] + 1;
					}
				}
				
				return power;
			}
		}
		
		return 0;
	}
	
	private void sendMissileData() {
		
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", power);
		data.setBoolean("scanMissiles", scanMissiles);
		data.setBoolean("scanPlayers", scanPlayers);
		data.setBoolean("smartMode", smartMode);
		data.setBoolean("redMode", redMode);
		data.setBoolean("jammed", jammed);
		data.setInteger("count", this.nearbyMissiles.size());

		for(int i = 0; i < this.nearbyMissiles.size(); i++) {
			data.setInteger("x" + i, this.nearbyMissiles.get(i)[0]);
			data.setInteger("z" + i, this.nearbyMissiles.get(i)[1]);
			data.setInteger("type" + i, this.nearbyMissiles.get(i)[2]);
			data.setInteger("y" + i, this.nearbyMissiles.get(i)[3]);
		}

		this.networkPack(data, 15);
	}

	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.nearbyMissiles.clear();
		this.power = data.getLong("power");
		this.scanMissiles = data.getBoolean("scanMissiles");
		this.scanPlayers = data.getBoolean("scanPlayers");
		this.smartMode = data.getBoolean("smartMode");
		this.redMode = data.getBoolean("redMode");
		this.jammed = data.getBoolean("jammed");

		int count = data.getInteger("count");

		for(int i = 0; i < count; i++) {

			int x = data.getInteger("x" + i);
			int z = data.getInteger("z" + i);
			int type = data.getInteger("type" + i);
			int y = data.getInteger("y" + i);

			this.nearbyMissiles.add(new int[] {x, z, type, y});
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		power = compound.getLong("power");
		scanMissiles =compound.getBoolean("scanMissiles");
		scanPlayers = compound.getBoolean("scanPlayers");
		smartMode = compound.getBoolean("smartMode");
		redMode = compound.getBoolean("redMode");
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("power", power);
		compound.setBoolean("scanMissiles", scanMissiles);
		compound.setBoolean("scanPlayers", scanPlayers);
		compound.setBoolean("smartMode", smartMode);
		compound.setBoolean("redMode", redMode);
		return super.writeToNBT(compound);
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
