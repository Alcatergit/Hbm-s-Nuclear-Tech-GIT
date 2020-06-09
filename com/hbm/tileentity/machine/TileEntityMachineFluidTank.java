package com.hbm.tileentity.machine;

import com.hbm.forgefluid.FFUtils;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.items.ModItems;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachineFluidTank extends TileEntity implements ITickable, IFluidHandler, ITankPacketAcceptor {

	public ItemStackHandler inventory;

	// public static final int maxFill = 64 * 3;
	public FluidTank tank;

	//private static final int[] slots_top = new int[] { 0 };
	//private static final int[] slots_bottom = new int[] { 0 };
	//private static final int[] slots_side = new int[] { 0 };
	public int age = 0;
	public boolean needsUpdate = false;
	
	private String customName;
	
	public TileEntityMachineFluidTank() {
		inventory = new ItemStackHandler(7){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
		tank = new FluidTank(256000);
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.fluidtank";
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
		tank.readFromNBT(compound);
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		tank.writeToNBT(compound);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void update() {
		if (!world.isRemote) {
			age++;
			if (age >= 20) {
				age = 0;
			}

			if ((age == 9 || age == 19))
				if (dna()) {
					fillFluidInit();
				}
			if (needsUpdate) {
				needsUpdate = false;
			}
			PacketDispatcher.wrapper.sendToAllTracking(new FluidTankPacket(pos.getX(), pos.getY(), pos.getZ(), new FluidTank[] {tank}), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));

			FFUtils.fillFromFluidContainer(inventory, tank, 2, 3);
			FFUtils.fillFluidContainer(inventory, tank, 4, 5);

			detectAndSendChanges();
		}
	}

	private void fillFluidInit() {
		if (tank.getFluid() != null) {
			FFUtils.fillFluid(this, tank, world, pos.add(2, 0, -1), 6000);
			FFUtils.fillFluid(this, tank, world, pos.add(2, 0, 1), 6000);
			FFUtils.fillFluid(this, tank, world, pos.add(-2, 0, -1), 6000);
			FFUtils.fillFluid(this, tank, world, pos.add(-2, 0, 1), 6000);
			FFUtils.fillFluid(this, tank, world, pos.add(-1, 0, 2), 6000);
			FFUtils.fillFluid(this, tank, world, pos.add(1, 0, 2), 6000);
			FFUtils.fillFluid(this, tank, world, pos.add(-1, 0, -2), 6000);
			FFUtils.fillFluid(this, tank, world, pos.add(1, 0, -2), 6000);
		}
	}

	public boolean dna() {
		if (inventory.getStackInSlot(6).getItem() == ModItems.fuse || inventory.getStackInSlot(6).getItem() == ModItems.screwdriver)
			return true;
		return false;
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
	
	private FluidTank detectTank;
	
	private void detectAndSendChanges() {
		boolean mark = false;
		if(!FFUtils.areTanksEqual(tank, detectTank)){
			needsUpdate = true;
			mark = true;
			detectTank = FFUtils.copyTank(tank);
		}
		if(mark)
			markDirty();
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{tank.getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (this.canFill(resource.getFluid())) {		
			return tank.fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (resource == null || !resource.isFluidEqual(tank.getFluid())) {
			return null;
		}
		if (this.canDrain(resource.getFluid())) {
			return tank.drain(resource.amount, doDrain);
		}
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if (this.canDrain(null)) {
			return tank.drain(maxDrain, doDrain);
		}
		return null;
	}
	
	public boolean canFill(Fluid fluid) {
		if (!this.world.isRemote) {
			return !this.dna();
		}
		return false;
	}

	public boolean canDrain(Fluid fluid) {
		if (!this.world.isRemote) {
			return tank.getFluid() != null;
		}
		return false;
	}

	@Override
	public void recievePacket(NBTTagCompound[] tags) {
		if(tags.length != 1) {
			return;
		} else {
			tank.readFromNBT(tags[0]);
		}
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
		} else if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		} else {
			return super.getCapability(capability, facing);
		}
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			return true;
		} else if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return true;
		} else {
			return super.hasCapability(capability, facing);
		}
	}

}
