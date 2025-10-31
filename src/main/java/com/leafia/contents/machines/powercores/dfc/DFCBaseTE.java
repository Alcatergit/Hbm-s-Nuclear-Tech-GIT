package com.leafia.contents.machines.powercores.dfc;

import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.machine.TileEntityCore;
import com.leafia.dev.LeafiaDebug;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.container_utility.LeafiaPacketReceiver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class DFCBaseTE extends TileEntityMachineBase implements LeafiaPacketReceiver {
	protected BlockPos targetPosition = new BlockPos(0,0,0);
	public BlockPos getTargetPosition() { return targetPosition; }
	public DFCBaseTE(int scount) {
		super(scount);
	}

	@Override
	public void setWorld(World worldIn) {
		super.setWorld(worldIn);
		LeafiaDebug.debugLog(worldIn,"TileEntity regenerated");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("x1",targetPosition.getX());
		compound.setInteger("y1",targetPosition.getY());
		compound.setInteger("z1",targetPosition.getZ());
		return super.writeToNBT(compound);
	}
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		targetPosition = new BlockPos(compound.getInteger("x1"),compound.getInteger("y1"),compound.getInteger("z1"));
		super.readFromNBT(compound);
		if (world != null && world.isRemote)
			LeafiaPacket._start(this).__write(31,true).__sendToServer();
	}
	public Vec3d getDirection() {
		return new Vec3d(targetPosition.subtract(pos)).normalize();
	}
	@Override
	public void onReceivePacketLocal(byte key,Object value) {
		if (key == 31) targetPosition = (BlockPos)value;
	}
	@Override
	public void onReceivePacketServer(byte key,Object value,EntityPlayer plr) {
		if (key == 31) {
			LeafiaPacket packet = LeafiaPacket._start(this);
			syncClients(packet);
			packet.__sendToClient(plr);
		}
	}
	public LeafiaPacket syncClients(LeafiaPacket packet) {
		LeafiaDebug.debugLog(world,"syncClients");
		packet.__write(31,targetPosition);
		return packet;
	}
	@Override
	public void onPlayerValidate(EntityPlayer plr) {
		LeafiaPacket._start(this).__write(31,targetPosition).__sendToClient(plr);
	}
	public void setTargetPosition(BlockPos pos) {
		targetPosition = pos;
		if (!world.isRemote)
			LeafiaPacket._start(this).__write(31,pos).__sendToAffectedClients();
	}
	public EnumFacing getFront() {
		Vec3i relative = targetPosition.subtract(pos);
		int max = Math.max(Math.abs(relative.getX()),Math.max(Math.abs(relative.getY()),Math.abs(relative.getZ())));
		if (max == relative.getX()) return EnumFacing.EAST;
		else if (max == -relative.getX()) return EnumFacing.WEST;
		else if (max == relative.getZ()) return EnumFacing.SOUTH;
		else if (max == -relative.getZ()) return EnumFacing.NORTH;
		else if (max == relative.getY()) return EnumFacing.UP;
		else return EnumFacing.DOWN;
	}
	public TileEntityCore lastGetCore = null;
	@Nullable
	protected TileEntityCore getCore(int range) {
		lastGetCore = Library.leafiaRayTraceBlocksCustom(world,new Vec3d(pos).add(0.5,0.5,0.5),new Vec3d(pos).add(0.5,0.5,0.5).add(getDirection().scale(range)),(process,config,current) -> {
			if (current.posSnapped.equals(pos)) return process.CONTINUE();
			if (!current.block.canCollideCheck(current.state,true))
				return process.CONTINUE();
			RayTraceResult result = current.state.collisionRayTrace(world,current.posSnapped,current.posIntended.subtract(config.unitDir.scale(2)),current.posIntended.add(config.unitDir.scale(2)));
			if (result == null)
				return process.CONTINUE();
			TileEntity te = world.getTileEntity(current.posSnapped);
			if(te instanceof TileEntityCore)
				return process.RETURN((TileEntityCore)te);
			return process.BREAK();
		});
		return lastGetCore;
	}
}
