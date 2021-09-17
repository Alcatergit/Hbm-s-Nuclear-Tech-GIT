package com.hbm.tileentity.bomb;

import com.hbm.interfaces.IConsumer;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEMissilePacket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityLaunchPad extends TileEntity implements ITickable, IConsumer {

	public ItemStackHandler inventory;

	public long power;
	public final long maxPower = 100000;

	// private static final int[] slots_top = new int[] {0};
	// private static final int[] slots_bottom = new int[] { 0, 1, 2};
	// private static final int[] slots_side = new int[] {0};
	public int state = 0;

	private String customName;

	public TileEntityLaunchPad() {
		inventory = new ItemStackHandler(3);
	}

	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.launchPad";
	}

	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomName(String name) {
		this.customName = name;
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
		if (world.getTileEntity(pos) != this) {
			return false;
		} else {
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		power = compound.getLong("power");
		detectPower = power + 1;
		if (compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));

		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("power", power);

		compound.setTag("inventory", inventory.serializeNBT());

		return super.writeToNBT(compound);
	}

	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}

	@Override
	public void update() {
		
		if (!world.isRemote) {
			power = Library.chargeTEFromItems(inventory, 2, power, maxPower);
			detectAndSendChanges();
		}

	}

	private ItemStack detectStack = ItemStack.EMPTY;
	private long detectPower;
	
	private void detectAndSendChanges() {
		boolean mark = false;
		if(!(detectStack.isEmpty() && inventory.getStackInSlot(0).isEmpty()) && !detectStack.isItemEqualIgnoreDurability(inventory.getStackInSlot(0))){
			mark = true;
			detectStack = inventory.getStackInSlot(0).copy();
		}
		if(detectPower != power){
			mark = true;
			detectPower = power;
		}
		PacketDispatcher.wrapper.sendToAllTracking(new TEMissilePacket(pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(0)), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 1000));
		PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos.getX(), pos.getY(), pos.getZ(), power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
		if(mark)
			markDirty();
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
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

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : super.getCapability(capability, facing);
	}
}
