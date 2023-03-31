package com.hbm.tileentity.machine;

import com.hbm.lib.ForgeDirection;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.INBTPacketReceiver;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityTowerLarge extends TileEntityCondenser {
	
	public TileEntityTowerLarge() {
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(1000000);
		tanks[1] = new FluidTank(1000000);
	}
	
	@Override
	public void update() {
		super.update();
		
		if(world.isRemote) {
			
			if(this.waterTimer > 0 && this.world.getTotalWorldTime() % 4 == 0) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "tower");
				data.setFloat("lift", 1F);
				data.setFloat("base", 1F);
				data.setFloat("max", 5F);
				data.setInteger("life", 750 + world.rand.nextInt(750));
	
				data.setDouble("posX", pos.getX() + 0.5 + world.rand.nextDouble() * 2 - 1);
				data.setDouble("posZ", pos.getZ() + 0.5 + world.rand.nextDouble() * 2 - 1);
				data.setDouble("posY", pos.getY() + 1);
				
				MainRegistry.proxy.effectNT(data);
			}
		}
	}

	@Override
	public void fillFluidInit(FluidTank tank) {
		
		for(int i = 2; i <= 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
			fillFluid(pos.getX() + dir.offsetX * 5, pos.getY(), pos.getZ() + dir.offsetZ * 5, tank);
			fillFluid(pos.getX() + dir.offsetX * 5 + rot.offsetX * 3, pos.getY(), pos.getZ() + dir.offsetZ * 5 + rot.offsetZ * 3, tank);
			fillFluid(pos.getX() + dir.offsetX * 5 + rot.offsetX * -3, pos.getY(), pos.getZ() + dir.offsetZ * 5 + rot.offsetZ * -3, tank);
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = new AxisAlignedBB(
					pos.getX() - 4,
					pos.getY(),
					pos.getZ() - 4,
					pos.getX() + 5,
					pos.getY() + 13,
					pos.getZ() + 5
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}