package com.hbm.tileentity.machine.oil;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.explosion.ExplosionThermo;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachineGasFlare extends TileEntityLoadedBase implements ITickable, IEnergyGenerator, IFluidHandler, ITankPacketAcceptor {

	public ItemStackHandler inventory;
	
	public long power;
	public static final long maxPower = 100000;
	public Fluid tankType;
	public FluidTank tank;
	public boolean needsUpdate;
	
	//private static final int[] slots_top = new int[] {1};
	//private static final int[] slots_bottom = new int[] {2, 0};
	//private static final int[] slots_side = new int[] {0};
	
	private String customName;
	
	public TileEntityMachineGasFlare() {
		inventory = new ItemStackHandler(3){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
		tankType = ModForgeFluids.gas;
		tank = new FluidTank(64000);
		needsUpdate = false;
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.gasFlare";
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
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <=128;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.power = compound.getLong("powerTime");
		tank.readFromNBT(compound);
		tankType = ModForgeFluids.gas;
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("powerTime", power);
		tank.writeToNBT(compound);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	@Override
	public void update() {
		
		if(!world.isRemote) {

			this.sendPower(world, pos.add(2, 0, 0), Library.POS_X);
			this.sendPower(world, pos.add(-2, 0, 0), Library.NEG_X);
			this.sendPower(world, pos.add(0, 0, 2), Library.POS_Z);
			this.sendPower(world, pos.add(0, 0, -2), Library.NEG_Z);

			long prevPower = power;
			int prevAmount = tank.getFluidAmount();
			if(needsUpdate) {
				needsUpdate = false;
			}

			if(this.inputValidForTank(-1, 1))
				if(FFUtils.fillFromFluidContainer(inventory, tank, 1, 2))
					needsUpdate = true;
			
			if(tank.getFluidAmount() >= 10) {
				tank.drain(10, true);
				needsUpdate = true;
				power += 50;
				
				if(power > maxPower)
					power = maxPower;

	    		world.spawnEntity(new EntityGasFlameFX(world, pos.getX() + 0.5F, pos.getY() + 11F, pos.getZ() + 0.5F, 0.0, 0.0, 0.0));
				ExplosionThermo.setEntitiesOnFire(world, pos.getX(), pos.getY() + 11, pos.getZ(), 5);
	    		
	    		if(this.world.getTotalWorldTime() % 5 == 0)
					this.world.playSound(null, pos.getX(), pos.getY() + 11, pos.getZ(), HBMSoundHandler.flamethrowerShoot, SoundCategory.BLOCKS, 1.5F, 1F);
			}
			
			power = Library.chargeItemsFromTE(inventory, 0, power, maxPower);

			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos, power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
			PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos, new FluidTank[] {tank}), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
			if(prevPower != power || prevAmount != tank.getFluidAmount()){
				markDirty();
			}
		}
	}
	
	protected boolean inputValidForTank(int tank, int slot){
		if(!inventory.getStackInSlot(slot).isEmpty()){
			if(isValidFluid(FluidUtil.getFluidContained(inventory.getStackInSlot(slot)))){
				return true;	
			}
		}
		return false;
	}
	
	private boolean isValidFluid(FluidStack stack) {
		if(stack == null)
			return false;
		return stack.getFluid() == ModForgeFluids.gas;
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
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{tank.getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(this.isValidFluid(resource)) {
			return tank.fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return null;
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
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			return true;
		} else if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return true;
		} else {
			return super.hasCapability(capability, facing);
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
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}
}
