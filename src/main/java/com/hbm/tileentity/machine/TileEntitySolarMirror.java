package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityTickingBase;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntitySolarMirror extends TileEntityTickingBase {

	public int tX;
	public int tY;
	public int tZ;
	public boolean isOn;
	
	@Override
	public String getInventoryName() {
		return null;
	}
	
	@Override
	public void update() {
		if(!world.isRemote) {

			if(world.getTotalWorldTime() % 20 == 0)
				sendUpdate();

			if(tY < pos.getY()){
				isOn = false;
				return;
			}

			int sun = world.getLightFor(EnumSkyBlock.SKY, pos) - world.getSkylightSubtracted() - 11;

			if(sun <= 0 || !world.canSeeSky(pos.up())){
				isOn = false;
				return;
			}
			
			isOn = true;

			TileEntity te = world.getTileEntity(new BlockPos(tX, tY - 1, tZ));

			if(te instanceof TileEntitySolarBoiler) {
				TileEntitySolarBoiler boiler = (TileEntitySolarBoiler)te;
				boiler.heat += sun;
			}
		}
	}
	
	public void sendUpdate(){
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("posX", tX);
		data.setInteger("posY", tY);
		data.setInteger("posZ", tZ);
		data.setBoolean("isOn", isOn);
		this.networkPack(data, 200);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		tX = nbt.getInteger("posX");
		tY = nbt.getInteger("posY");
		tZ = nbt.getInteger("posZ");
		isOn = nbt.getBoolean("isOn");
	}
	
	public void setTarget(int x, int y, int z) {
		tX = x;
		tY = y;
		tZ = z;
		this.markDirty();
		this.sendUpdate();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		tX = compound.getInteger("targetX");
		tY = compound.getInteger("targetY");
		tZ = compound.getInteger("targetZ");
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("targetX", tX);
		compound.setInteger("targetY", tY);
		compound.setInteger("targetZ", tZ);
		return super.writeToNBT(compound);
	}
	
	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = new AxisAlignedBB(
					pos.getX() - 0.25,
					pos.getY(),
					pos.getZ() - 0.25,
					pos.getX() + 1.25,
					pos.getY() + 1.5,
					pos.getZ() + 1.25
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
