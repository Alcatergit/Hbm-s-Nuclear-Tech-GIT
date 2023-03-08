package com.hbm.tileentity.network;

import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.util.ITickable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class TileEntityRadioTorchBase extends TileEntity implements ITickable, INBTPacketReceiver, IControlReceiver {

	/** channel we're broadcasting on/listening to */
	public String channel = "";
	/** previous redstone state for input/output, needed for state change detection */
	public int lastState = 0;
	/** last update tick, needed for receivers listening for changes */
	public long lastUpdate;
	/** switches state change mode to tick-based polling */
	public boolean polling = false;
	/** switches redstone passthrough to custom signal mapping */
	public boolean customMap = false;
	/** custom mapping */
	public String[] mapping = new String[16];

	@Override
	public void update() {

		if(!world.isRemote) {
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("isPolling", polling);
			data.setBoolean("hasMapping", customMap);
			if(channel != null) 
				data.setString("channel", channel);
			for(int i = 0; i < 16; i++) {
				if(mapping[i] != null) {
					data.setString("mapping" + i, mapping[i]);
				}
			}
			this.networkPack(data, 50);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.polling = nbt.getBoolean("isPolling");
		this.customMap = nbt.getBoolean("hasMapping");
		this.lastState = nbt.getInteger("lastPower");
		this.lastUpdate = nbt.getLong("lastTime");
		this.channel = nbt.getString("channel");
		for(int i = 0; i < 16; i++) {
			this.mapping[i] = nbt.getString("mapping" + i);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("isPolling", polling);
		nbt.setBoolean("hasMapping", customMap);
		nbt.setInteger("lastPower", lastState);
		nbt.setLong("lastTime", lastUpdate);
		if(channel != null) 
			nbt.setString("channel", channel);
		for(int i = 0; i < 16; i++) {
			if(mapping[i] != null) {
				nbt.setString("mapping" + i, mapping[i]);
			}
		}
		return super.writeToNBT(nbt);
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.polling = nbt.getBoolean("isPolling");
		this.customMap = nbt.getBoolean("hasMapping");
		this.channel = nbt.getString("channel");
		for(int i = 0; i < 16; i++)
			this.mapping[i] = nbt.getString("mapping" + i);
	}

	public void networkPack(NBTTagCompound nbt, int range) {
		if(!world.isRemote)
			PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(nbt, pos), new TargetPoint(this.world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), range));
	}
	

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(pos.getX() - player.posX, pos.getY() - player.posY, pos.getZ() - player.posZ).lengthVector() < 16;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("isPolling")) 
			this.polling = data.getBoolean("isPolling");
		if(data.hasKey("hasMapping")) 
			this.customMap = data.getBoolean("hasMapping");
		if(data.hasKey("channel")) 
			this.channel = data.getString("channel");
		for(int i = 0; i < 16; i++) {
			if(data.hasKey("mapping" + i)) {
				this.mapping[i] = data.getString("mapping" + i);
			}
		}
		
		this.markDirty();
	}
}
