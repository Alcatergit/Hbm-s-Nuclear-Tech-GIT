package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.calc.UnionOfTileEntitiesAndBooleans;
import com.hbm.interfaces.IConductor;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEPylonDestructorPacket;
import com.hbm.packet.TEPylonSenderPacket;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityPylonRedWire extends TileEntity implements ITickable, IConductor {

	public List<UnionOfTileEntitiesAndBooleans> uoteab = new ArrayList<UnionOfTileEntitiesAndBooleans>();
	public List<TileEntityPylonRedWire> connected = new ArrayList<TileEntityPylonRedWire>();
	public boolean scheduleConnectionCheck = false;
	public BlockPos[] scheduleBuffer;

	@Override
	public void update() {
		for (int i = connected.size() - 1; i >= 0; i--) {
			if (connected.size() >= i + 1 && connected.get(i) == null)
				connected.remove(i);
		}

		for (int i = connected.size() - 1; i >= 0; i--) {
			if (connected.size() >= i + 1 && connected.get(i) != null && this.world.getBlockState(connected.get(i).pos).getBlock() != ModBlocks.red_pylon)
				connected.remove(i);
		}

		if (scheduleConnectionCheck && this.scheduleBuffer != null) {
			scheduleConnectionCheck = false;
			this.connected = TileEntityPylonRedWire.convertArrayToList(this.scheduleBuffer, world);
		}
		

		if (!world.isRemote)
			if (!connected.isEmpty()) {
				PacketDispatcher.wrapper.sendToAllAround(new TEPylonDestructorPacket(pos.getX(), pos.getY(), pos.getZ()), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 200));

				for (TileEntityPylonRedWire wire : connected)
					PacketDispatcher.wrapper.sendToAllAround(new TEPylonSenderPacket(pos.getX(), pos.getY(), pos.getZ(), wire.pos.getX(), wire.pos.getY(), wire.pos.getZ()), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 200));
			}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		int[] conX = nbt.getIntArray("conX");
		int[] conY = nbt.getIntArray("conY");
		int[] conZ = nbt.getIntArray("conZ");

		BlockPos[] con = new BlockPos[conX.length];
		
		for(int i = 0; i < conX.length; i++) {
			con[i] = new BlockPos(conX[i], conY[i], conZ[i]);
		}
			
		scheduleConnectionCheck = true;
		scheduleBuffer = con;
	}
	
	public void addTileEntityBasedOnCoords(BlockPos pos) {

		TileEntity te = world.getTileEntity(pos);
		if(te != null && te instanceof TileEntityPylonRedWire)
			this.connected.add((TileEntityPylonRedWire)te);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		BlockPos[] con = TileEntityPylonRedWire.convertListToArray(connected);

		int[] conX = new int[con.length];
		int[] conY = new int[con.length];
		int[] conZ = new int[con.length];
		
		for(int i = 0; i < conX.length; i++) {
			conX[i] = con[i].getX();
			conY[i] = con[i].getY();
			conZ[i] = con[i].getZ();
		}

		nbt.setIntArray("conX", conX);
		nbt.setIntArray("conY", conY);
		nbt.setIntArray("conZ", conZ);
		return nbt;
	}

	public static List<TileEntityPylonRedWire> convertArrayToList(BlockPos[] array, World worldObj) {
		
		List<TileEntityPylonRedWire> list = new ArrayList<TileEntityPylonRedWire>();
		
		for(int i = 0; i < array.length; i++) {
			TileEntity te = worldObj.getTileEntity(array[i]);
			if(te != null && te instanceof TileEntityPylonRedWire)
				list.add((TileEntityPylonRedWire)te);
		}
		
		return list;
	}
	
	public static BlockPos[] convertListToArray(List<TileEntityPylonRedWire> list) {
		
		BlockPos[] array = new BlockPos[list.size()];
		
		for(int i = 0; i < list.size(); i++) {
			TileEntity te = list.get(i);
			array[i] = te.getPos();
		}
		
		return array;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
