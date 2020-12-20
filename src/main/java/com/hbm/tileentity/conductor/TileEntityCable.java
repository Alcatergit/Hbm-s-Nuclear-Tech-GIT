package com.hbm.tileentity.conductor;

import java.util.ArrayList;
import java.util.List;

import com.hbm.calc.UnionOfTileEntitiesAndBooleans;
import com.hbm.interfaces.IConductor;
import com.hbm.lib.Library;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCable extends TileEntity implements ITickable, IConductor {

	public EnumFacing[] connections = new EnumFacing[6];
	
	public List<UnionOfTileEntitiesAndBooleans> uoteab = new ArrayList<UnionOfTileEntitiesAndBooleans>();
	
	@Override
	public void update() {
		this.updateConnections();
	}
	
	public void updateConnections() {
		if(Library.checkCableConnectables(this.world, pos.up())) connections[0] = EnumFacing.UP;
		else connections[0] = null;
		
		if(Library.checkCableConnectables(this.world, pos.down())) connections[1] = EnumFacing.DOWN;
		else connections[1] = null;
		
		if(Library.checkCableConnectables(this.world, pos.north())) connections[2] = EnumFacing.NORTH;
		else connections[2] = null;
		
		if(Library.checkCableConnectables(this.world, pos.east())) connections[3] = EnumFacing.EAST;
		else connections[3] = null;
		
		if(Library.checkCableConnectables(this.world, pos.south())) connections[4] = EnumFacing.SOUTH;
		else connections[4] = null;
		
		if(Library.checkCableConnectables(this.world, pos.west())) connections[5] = EnumFacing.WEST;
		else connections[5] = null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

}
