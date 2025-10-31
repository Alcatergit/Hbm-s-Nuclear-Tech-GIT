package com.leafia.contents.machines.elevators.floors;

import com.hbm.blocks.ModBlocks.Elevators;
import com.hbm.main.MainRegistry;
import com.leafia.dev.LeafiaDebug;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.container_utility.LeafiaPacketReceiver;
import com.llib.technical.FiaLatch;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class EvFloorTE extends TileEntity implements ITickable,LeafiaPacketReceiver {
	static final byte idGui = 31;
	static final byte idFloor = 0;
	static final byte idOpen = 1;

	public int floor = 1;
	public FiaLatch<Float> open = new FiaLatch<>(0f);
	public void openGui(EntityPlayer player) {
		LeafiaPacket._start(this).__write(idFloor,floor).__write(idGui,0).__sendToClient(player);
	}
	@Override
	public String getPacketIdentifier() {
		return "EV_FLOOR";
	}
	@Override
	public void onReceivePacketLocal(byte key,Object value) {
		switch(key) {
			case idFloor: floor = (int)value; break;
			case idGui: Minecraft.getMinecraft().player.openGui(MainRegistry.instance,Elevators.guiIdFloor,world,pos.getX(),pos.getY(),pos.getZ()); break;
			case idOpen: open.set((float)value).update(); break;
		}
	}
	@Override
	public void onReceivePacketServer(byte key,Object value,EntityPlayer plr) {
		if (key == 0) {
			floor = (int)value;
			LeafiaPacket._start(this).__write(idFloor,floor).__sendToAffectedClients();
		}
	}
	@Override
	public void onPlayerValidate(EntityPlayer plr) {
		LeafiaPacket._start(this).__write(idOpen,open.cur).__sendToClient(plr);
	}
	@Override
	public double affectionRange() {
		return 256;
	}

	@Override
	public void update() {
		if (open.needsUpdate()) {
			LeafiaDebug.debugLog(world,"fuck you "+open.cur);
			LeafiaPacket._start(this).__write(idOpen,open.update()).__sendToAffectedClients();
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setByte("floor",(byte)floor);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.floor = compound.getByte("floor");
	}
}
