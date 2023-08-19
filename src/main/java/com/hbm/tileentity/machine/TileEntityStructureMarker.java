package com.hbm.tileentity.machine;

import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEStructurePacket;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class TileEntityStructureMarker extends TileEntity implements ITickable {

	// 0: Factory
	// 1: Nuclear Reactor
	// 2: Reactor with Coat
	// 3: Watz Power Plant
	// 4: Singularity-Anti-Fusion-Experiment
	public int type = 0;

	@Override
	public void update() {
		if(this.type > 4)
			type = 0;

		if(!world.isRemote)
			PacketDispatcher.wrapper.sendToAllAround(new TEStructurePacket(pos.getX(), pos.getY(), pos.getZ(), type), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 80));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		type = compound.getInteger("type");
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("type", type);
		return super.writeToNBT(compound);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

}
