package com.hbm.tileentity.machine;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.ITankPacketAcceptor;
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
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachineUF6Tank extends TileEntity implements ITickable, ITankPacketAcceptor {

	public ItemStackHandler inventory;
	
	//public static final int maxFill = 64 * 3;
	public FluidTank tank;
	public Fluid tankType;

	//private static final int[] slots_top = new int[] {0};
	//private static final int[] slots_bottom = new int[] {1, 3};
	//private static final int[] slots_side = new int[] {2};
	
	private String customName;
	
	public TileEntityMachineUF6Tank() {
		inventory = new ItemStackHandler(4){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
		tank = new FluidTank(64000);
		tankType = ModForgeFluids.uf6;
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.uf6_tank";
	}

	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(world.getTileEntity(pos) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <=64;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		tank.readFromNBT(compound);
		tankType = ModForgeFluids.uf6;
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound);
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
		if(!world.isRemote)
		{
			if(inputValidForTank(-1, 0))
				FFUtils.fillFromFluidContainer(inventory, tank, 0, 1);
			FFUtils.fillFluidContainer(inventory, tank, 2, 3);
			
			PacketDispatcher.wrapper.sendToAll(new FluidTankPacket(pos.getX(), pos.getY(), pos.getZ(), new FluidTank[]{tank}));
		}
	}
	
	protected boolean inputValidForTank(int irrelevant, int slot){
		if(!inventory.getStackInSlot(slot).isEmpty() && tank != null){
			if(isValidFluidForTank(FluidUtil.getFluidContained(inventory.getStackInSlot(slot)))){
				return true;
			}
		}
		return false;
	}
	
	private boolean isValidFluidForTank(FluidStack stack) {
		if(stack == null || tank == null)
			return false;
		return stack.getFluid() == tankType;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(pos, pos.add(1, 2, 1));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public void recievePacket(NBTTagCompound[] tags) {
		if(tags.length != 1){
			return;
		} else {
			tank.readFromNBT(tags[0]);
		}
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

}
