package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityTickingBase;

import api.hbm.energy.IEnergyUser;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHadronPower extends TileEntityTickingBase implements IEnergyUser {

	public long power;
	public static final long maxPower = 1000000000;

	@Override
	public void update() {
		if(!world.isRemote) {
			this.updateStandardConnections(world, pos);
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			this.networkPack(data, 15);
		}
	}

	@Override
	public String getInventoryName(){
		return "Hadron Power Thing";
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
	}
	
	@Override
	public void setPower(long i) {
		power = i;
		this.markDirty();
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
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		compound.setLong("power", power);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound){
		power = compound.getLong("power");
		super.readFromNBT(compound);
	}

}
