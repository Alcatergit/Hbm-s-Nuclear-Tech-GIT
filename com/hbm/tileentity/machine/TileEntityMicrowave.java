package com.hbm.tileentity.machine;

import com.hbm.interfaces.IConsumer;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMicrowave extends TileEntityMachineBase implements ITickable, IConsumer {

	public long power;
	public static final long maxPower = 50000;
	public static final int consumption = 50;
	public static final int maxTime = 300;
	public int time;
	public int speed;
	public static final int maxSpeed = 5;
	
	public TileEntityMicrowave() {
		super(3);
	}
	
	@Override
	public String getName() {
		return "container.microwave";
	}
	
	@Override
	public void update() {
		if(!world.isRemote) {

			this.power = Library.chargeTEFromItems(inventory, 2, power, maxPower);

			if(canProcess()) {

				if(speed == maxSpeed) {
					world.destroyBlock(pos, false);
					world.newExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 7.5F, true, true);
					return;
				}

				power -= consumption;
				time += speed;

				if(time >= maxTime) {
					process();
					time = 0;
				}
			}

			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("time", time);
			data.setInteger("speed", speed);
			networkPack(data, 50);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound data) {
		power = data.getLong("power");
		time = data.getInteger("time");
		speed = data.getInteger("speed");
	}
	
	@Override
	public void handleButtonPacket(int value, int meta) {
		if(value == 0)
			speed++;

		if(value == 1)
			speed--;

		if(speed < 0)
			speed = 0;

		if(speed > maxSpeed)
			speed = maxSpeed;
	}
	
	private void process() {

		ItemStack stack = FurnaceRecipes.instance().getSmeltingResult(inventory.getStackInSlot(0)).copy();

		if(inventory.getStackInSlot(1).isEmpty()) {
			inventory.setStackInSlot(1, stack);
		} else {
			inventory.getStackInSlot(1).setCount(inventory.getStackInSlot(1).getCount() + stack.getCount());
		}

		inventory.getStackInSlot(0).shrink(1);

		this.markDirty();
	}

	private boolean canProcess() {

		if(speed  == 0)
			return false;

		if(power < consumption)
			return false;

		if(!FurnaceRecipes.instance().getSmeltingResult(inventory.getStackInSlot(0)).isEmpty()) {

			ItemStack stack = FurnaceRecipes.instance().getSmeltingResult(inventory.getStackInSlot(0));

			if(inventory.getStackInSlot(1).isEmpty())
				return true;

			if(!stack.isItemEqual(inventory.getStackInSlot(1)))
				return false;

			return stack.getCount() + inventory.getStackInSlot(1).getCount() <= stack.getMaxStackSize();
		}

		return false;
	}
	
	public long getPowerScaled(int i) {
		return (power * i) / maxPower;
	}

	public int getProgressScaled(int i) {
		return (time * i) / maxTime;
	}

	public int getSpeedScaled(int i) {
		return (speed * i) / maxSpeed;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
	
	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

}
