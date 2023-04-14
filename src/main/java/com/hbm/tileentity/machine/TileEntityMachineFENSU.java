package com.hbm.tileentity.machine;

import com.hbm.lib.Library;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMachineFENSU extends TileEntityMachineBattery {

	public EnumDyeColor color = EnumDyeColor.LIGHT_BLUE;

	public float prevRotation = 0F;
	public float rotation = 0F;

	@Override
	public void update() {
		
		this.maxPower = Long.MAX_VALUE;

		if(!world.isRemote) {

			short mode = (short) this.getRelevantMode();

			if(mode == 1 || mode == 2)
			{
				age++;
				if(age >= 20)
				{
					age = 0;
				}

				if(age == 9 || age == 19)
					ffgeuaInit();
			}
			long prevPower = this.power;
			power = Library.chargeTEFromItems(inventory, 0, power, maxPower);
			power = Library.chargeItemsFromTE(inventory, 2, power, maxPower);

			tryMoveItems();
			long avg = (this.power + prevPower) / 2;
			this.powerDelta = avg - this.log[0];

			for(int i = 1; i < this.log.length; i++) {
				this.log[i - 1] = this.log[i];
			}
			
			this.log[this.log.length-1] = avg;

			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setLong("power", power);
			nbt.setLong("maxPower", maxPower);
			nbt.setShort("redLow", redLow);
			nbt.setShort("redHigh", redHigh);
			nbt.setByte("color", (byte) this.color.getMetadata());
			this.networkPack(nbt, 250);

			this.detectAndSendChanges();
		} else {
			this.prevRotation = this.rotation;
			this.rotation += this.getSpeed();

			if(rotation >= 360) {
				rotation -= 360;
				prevRotation -= 360;
			}
		}
	}

	@Override
	public long getPowerRemainingScaled(long i) {

		double powerScaled = (double)power / (double)maxPower;

		return (long)(i * powerScaled);
	}

	public float getSpeed() {
		return (float) Math.pow(Math.log(power * 0.75 + 1) * 0.05F, 5);
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

	@Override
	public void networkUnpack(NBTTagCompound nbt) { 
		this.color = EnumDyeColor.byMetadata(nbt.getByte("color"));
		super.networkUnpack(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.color = EnumDyeColor.byMetadata(compound.getByte("color"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setByte("color", (byte) this.color.getMetadata());
		return super.writeToNBT(compound);
	}
}