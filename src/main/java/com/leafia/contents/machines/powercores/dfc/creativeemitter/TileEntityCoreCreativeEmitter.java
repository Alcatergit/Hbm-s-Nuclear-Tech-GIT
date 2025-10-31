package com.leafia.contents.machines.powercores.dfc.creativeemitter;

import com.hbm.tileentity.machine.TileEntityCoreEmitter;
import com.leafia.dev.container_utility.LeafiaPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;

public class TileEntityCoreCreativeEmitter extends TileEntityCoreEmitter {
	public long[] joulesT = new long[]{
			100_000,
			20_000_000,
			100_000_000,
			1_000_000_000
	};
	int selecting = 0;
	@Override
	public String getPacketIdentifier() {
		return "DFC_CBOOSTER";
	}
	@Override
	public void update() {
		if (!world.isRemote) {

			if (isOn) {
				//i.e. 50,000,000 HE = 10,000 SPK
				//1 SPK = 5,000HE
				long out = joulesT[selecting];
				if (out > 0)
					raycast(out);
				watts = (int)(out/1000);
			} else watts = 0;

			this.markDirty();

			LeafiaPacket packet = LeafiaPacket._start(this)
					.__write(0, isOn)
					.__write(1, watts)
					.__write(2, prev);
			packet.__sendToAffectedClients();
			sendChanges(null);
			LeafiaPacket._start(this).__write(31,targetPosition).__sendToAffectedClients(); // why does this have to be done??
		} else if (isOn) {
			lastRaycast = raycast(0);
		}
	}

	public void sendChanges(@Nullable EntityPlayer plr) {
		LeafiaPacket packet = LeafiaPacket._start(this)
				.__write(3, selecting)
				.__write(4, joulesT[0])
				.__write(5, joulesT[1])
				.__write(6, joulesT[2])
				.__write(7, joulesT[3]);
		if (plr == null)
			packet.__sendToAffectedClients();
		else
			packet.__sendToClient(plr);
	}

	@Override
	public LeafiaPacket syncClients(LeafiaPacket packet) {
		packet.__write(3, selecting)
				.__write(4, joulesT[0])
				.__write(5, joulesT[1])
				.__write(6, joulesT[2])
				.__write(7, joulesT[3]);
		return super.syncClients(packet);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("isOn",isOn);
		compound.setLong("0",joulesT[0]);
		compound.setLong("1",joulesT[1]);
		compound.setLong("2",joulesT[2]);
		compound.setLong("3",joulesT[3]);
		compound.setInteger("selecting",selecting);
		return super.writeToNBT(compound);
	}
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		isOn = compound.getBoolean("isOn");
		joulesT[0] = compound.getLong("0");
		joulesT[1] = compound.getLong("1");
		joulesT[2] = compound.getLong("2");
		joulesT[3] = compound.getLong("3");
		selecting = compound.getInteger("selecting");
		super.readFromNBT(compound);
	}

	boolean changed = false;
	@Override
	public void onReceivePacketLocal(byte key,Object value) {
		if (key == 0)
			isActive = (boolean)value;
		if (key == 3)
			selecting = (int)value;
		else if (key >= 4 && key <= 7) {
			joulesT[key - 4] = (long) value;
			changed = true;
		}
		super.onReceivePacketLocal(key,value);
	}

	@Override
	public void onReceivePacketServer(byte key,Object value,EntityPlayer plr) {
		if (key == 0)
			selecting = (int)value;
		else if (key < 5)
			joulesT[key-1] = (long)value;
		sendChanges(null);
		super.onReceivePacketServer(key,value,plr);
	}

	@Override
	public void onPlayerValidate(EntityPlayer plr) {
		super.onPlayerValidate(plr);
		sendChanges(plr);
	}
}
