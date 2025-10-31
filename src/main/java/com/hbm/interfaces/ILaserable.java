package com.hbm.interfaces;

import com.leafia.contents.network.spk_cable.network.ISPKConductor;
import com.leafia.contents.network.spk_cable.network.ISPKMember;
import com.leafia.contents.network.spk_cable.network.SPKNet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ILaserable extends ISPKMember {
	
	public void addEnergy(long energy, EnumFacing dir);
	default boolean isInputPreferable(EnumFacing dir) { return true; }

	default void updateSPKConnections(World world,BlockPos pos) {
		for (EnumFacing facing : EnumFacing.values()) {
			TileEntity te = world.getTileEntity(pos.offset(facing));
			if (te instanceof ISPKConductor) {
				SPKNet nw = ((ISPKConductor) te).getNetwork();
				if (nw != null) {
					if (!nw.containsMember(this)) nw.addMember(this);
				}
			}
		}
	}
}