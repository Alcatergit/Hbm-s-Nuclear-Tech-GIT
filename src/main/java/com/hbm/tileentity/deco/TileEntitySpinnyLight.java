package com.hbm.tileentity.deco;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntitySpinnyLight extends TileEntity {
	
	public EnumDyeColor color = EnumDyeColor.WHITE;
	public long timeAdded = 0;
	
	@Override
	public void onLoad() {
		timeAdded = world.getWorldTime();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		color = EnumDyeColor.values()[compound.getByte("color")];
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setByte("color", (byte) color.ordinal());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 0, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public double getMaxRenderDistanceSquared() {
		return 65535.0D;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(this.pos, this.pos.add(1, 1, 1)).grow(10);
	}
}
