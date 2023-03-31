package com.hbm.tileentity.machine;

import com.hbm.lib.ForgeDirection;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.INBTPacketReceiver;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityTowerSmall extends TileEntityCondenser {
	
	public TileEntityTowerSmall() {
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(100000);
		tanks[1] = new FluidTank(100000);
	}
	
	@Override
	public void update() {
		super.update();
		
		if(world.isRemote) {
			
			if(this.waterTimer > 0 && this.world.getTotalWorldTime() % 4 == 0) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "tower");
				data.setFloat("lift", 1.5F);
				data.setFloat("base", 0.5F);
				data.setFloat("max", 3F);
				data.setInteger("life", 250 + world.rand.nextInt(250));
	
				data.setDouble("posX", pos.getX() + 0.5);
				data.setDouble("posZ", pos.getZ() + 0.5);
				data.setDouble("posY", pos.getY() + 18);
				
				MainRegistry.proxy.effectNT(data);
			}
		}
	}

	@Override
	public void fillFluidInit(FluidTank tank) {
		
		for(int i = 2; i <= 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			fillFluid(pos.getX() + dir.offsetX * 3, pos.getY(), pos.getZ() + dir.offsetZ * 3, tank);
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = new AxisAlignedBB(
					pos.getX() - 2,
					pos.getY(),
					pos.getZ() - 2,
					pos.getX() + 3,
					pos.getY() + 20,
					pos.getZ() + 3
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