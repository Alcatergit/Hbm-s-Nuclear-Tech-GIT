package com.hbm.tileentity.deco;

import com.hbm.main.MainRegistry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityObjTester extends TileEntity implements ITickable {

	public int fireAge = -1;

	@Override
	public void update() {
		if(world.isRemote) {
			if(fireAge >= 0) {
				fireAge++;
			}
			MainRegistry.proxy.spawnParticle(pos.getX(), pos.getY(), pos.getZ(), "bfg_fire", new float[]{fireAge});
		}
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
}
