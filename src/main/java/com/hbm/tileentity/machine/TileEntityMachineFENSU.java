package com.hbm.tileentity.machine;

import com.hbm.lib.Library;
import com.hbm.lib.ForgeDirection;

import api.hbm.energy.IEnergyConductor;
import api.hbm.energy.IEnergyConnector;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMachineFENSU extends TileEntityMachineBattery {

	public EnumDyeColor color = EnumDyeColor.LIGHT_BLUE;

	public static final long maxTransfer = 10_000_000_000_000_000L; //10E
	//									9,223,372,036,854,775,807 is long max
	
	public float prevRotation = 0F;
	public float rotation = 0F;

	@Override
	public void update() {
		if(!world.isRemote) {
			super.update();
		} else {
			this.prevRotation = this.rotation;
			this.rotation += this.getSpeed();

			if(rotation >= 360) {
				rotation -= 360;
				prevRotation -= 360;
			}
		}
	}

	@Override
	protected void transmitPower() {
		
		short mode = (short) this.getRelevantMode();
		
		ForgeDirection dir = ForgeDirection.DOWN;
			
		TileEntity te = world.getTileEntity(pos.add(dir.offsetX, dir.offsetY, dir.offsetZ));
			
		// first we make sure we're not subscribed to the network that we'll be supplying
		if(te instanceof IEnergyConductor) {
			IEnergyConductor con = (IEnergyConductor) te;

			if(con.getPowerNet() != null && con.getPowerNet().isSubscribed(this))
				con.getPowerNet().unsubscribe(this);
		}

		//then we add energy
		if(mode == mode_buffer || mode == mode_output) {
			if(te instanceof IEnergyConnector) {
				IEnergyConnector con = (IEnergyConnector) te;
				
				long max = maxTransfer;
				long toTransfer = Math.min(max, this.power);
				long remainder = this.power - toTransfer;
				this.power = toTransfer;
				
				long oldPower = this.power;
				long transfer = this.power - con.transferPower(this.power);
				this.power = oldPower - transfer;
				
				power += remainder;
			}
		}

		//then we subscribe if possible
		if(te instanceof IEnergyConductor) {
			IEnergyConductor con = (IEnergyConductor) te;
			
			if(con.getPowerNet() != null) {
				if(mode == mode_output || mode == mode_none) {
					if(con.getPowerNet().isSubscribed(this)) {
						con.getPowerNet().unsubscribe(this);
					}
				} else if(!con.getPowerNet().isSubscribed(this)) {
					con.getPowerNet().subscribe(this);
				}
			}
		}
	}

	@Override
	public NBTTagCompound packNBT(long avg){
		NBTTagCompound nbt = super.packNBT(avg);
		nbt.setByte("color", (byte) this.color.getMetadata());
		return nbt;
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) { 
		this.power = nbt.getLong("power");
		this.powerDelta = nbt.getLong("powerDelta");
		this.redLow = nbt.getShort("redLow");
		this.redHigh = nbt.getShort("redHigh");
		this.color = EnumDyeColor.byMetadata(nbt.getByte("color"));
		this.priority = ConnectionPriority.values()[nbt.getByte("priority")];
	}

	@Override
	public long getPowerRemainingScaled(long i) {
		
		double powerScaled = (double)power / (double)getMaxPower();
		
		return (long)(i * powerScaled);
	}

	@Override
	public long getTransferWeight() {
		return Math.min(super.getTransferWeight(), maxTransfer);
	}

	public float getSpeed() {
		return (float) Math.pow(Math.log(power * 0.75 + 1) * 0.05F, 5);
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.color = EnumDyeColor.byMetadata(compound.getByte("color"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setByte("color", (byte) this.color.getMetadata());
		return super.writeToNBT(compound);
	}

	@Override
	public long getMaxPower() {
		return Long.MAX_VALUE;
	}
}