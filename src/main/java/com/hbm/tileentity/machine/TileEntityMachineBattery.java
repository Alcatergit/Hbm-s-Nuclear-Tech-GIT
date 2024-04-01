package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hbm.items.ModItems;
import com.hbm.blocks.machine.MachineBattery;
import com.hbm.lib.Library;
import com.hbm.lib.ForgeDirection;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyConductor;
import api.hbm.energy.IEnergyConnector;
import api.hbm.energy.IEnergyUser;
import api.hbm.energy.IPowerNet;
import api.hbm.energy.PowerNet;
import api.hbm.energy.IBatteryItem;
import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.Optional;

import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityMachineBattery extends TileEntityMachineBase implements ITickable, IEnergyUser, SimpleComponent {

	public long[] log = new long[20];
	public long power = 0;
	public long powerDelta = 0;

	//0: input only
	//1: buffer
	//2: output only
	//3: nothing
	public static final int mode_input = 0;
	public static final int mode_buffer = 1;
	public static final int mode_output = 2;
	public static final int mode_none = 3;
	public short redLow = 0;
	public short redHigh = 2;
	public ConnectionPriority priority = ConnectionPriority.NORMAL;

	public byte lastRedstone = 0;
	
	private String customName;
	
	public TileEntityMachineBattery() {
		super(4);
	}

	public static ForgeDirection[] getSendDirections(){
		return ForgeDirection.VALID_DIRECTIONS;
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.battery";
	}

	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
	}
	
	@Override
	public String getName() {
		return "container.battery";
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(world.getTileEntity(pos) != this){
			return false;
		}else{
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <=64;
		}
	}
	
	public long getPowerRemainingScaled(long i) {
		return (power * i) / this.getMaxPower();
	}

	public byte getComparatorPower() {
		if(power == 0) return 0;
		double frac = (double) this.power / (double) this.getMaxPower() * 15D;
		return (byte) (MathHelper.clamp((int) frac + 1, 0, 15)); //to combat eventual rounding errors with the FEnSU's stupid maxPower
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("power", this.power);
		compound.setShort("redLow", this.redLow);
		compound.setShort("redHigh", this.redHigh);
		compound.setByte("priority", (byte)this.priority.ordinal());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.power = compound.getLong("power");
		this.redLow = compound.getShort("redLow");
		this.redHigh = compound.getShort("redHigh");
		this.priority = ConnectionPriority.values()[compound.getByte("priority")];
		super.readFromNBT(compound);
	}


	public void writeNBT(NBTTagCompound nbt) {
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", this.power);
		data.setShort("redLow", this.redLow);
		data.setShort("redHigh", this.redHigh);
		data.setByte("priority", (byte)this.priority.ordinal());
		nbt.setTag("NBT_PERSISTENT_KEY", data);
	}


	public void readNBT(NBTTagCompound nbt) {
		NBTTagCompound data = nbt.getCompoundTag("NBT_PERSISTENT_KEY");
		this.power = data.getLong("power");
		this.redLow = data.getShort("redLow");
		this.redHigh = data.getShort("redHigh");
		this.priority = ConnectionPriority.values()[data.getByte("priority")];
	}

	@Override
	public int[] getAccessibleSlotsFromSide(EnumFacing p_94128_1_) {
        return new int[]{ 0, 1, 2, 3};
    }
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		if(i == 0)
			if(stack.getItem() instanceof IBatteryItem){
				IBatteryItem batteryItem = ((IBatteryItem)stack.getItem());
				if(batteryItem.getCharge(stack) > 0 && batteryItem.getDischargeRate() > 0){
					return true;
				}
			}
		if(i == 2)
			if(stack.getItem() instanceof IBatteryItem){
				IBatteryItem batteryItem = ((IBatteryItem)stack.getItem());
				if(batteryItem.getCharge(stack) < batteryItem.getMaxCharge() && batteryItem.getChargeRate() > 0){
					return true;
				}
			}
		return false;
	}
	
	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return (i == 1 || i == 3);
	}

	public void tryMoveItems() {
		ItemStack itemStackDrain = inventory.getStackInSlot(0);
		if(itemStackDrain.getItem() instanceof IBatteryItem) {
			IBatteryItem itemDrain = ((IBatteryItem)itemStackDrain.getItem());
			if(itemDrain.getCharge(itemStackDrain) == 0) {
				if(inventory.getStackInSlot(1) == null || inventory.getStackInSlot(1).isEmpty()){
					inventory.setStackInSlot(1, itemStackDrain);
					inventory.setStackInSlot(0, ItemStack.EMPTY);
				}
			}
		}
		ItemStack itemStackFill = inventory.getStackInSlot(2);
		if(itemStackFill.getItem() instanceof IBatteryItem) {
			IBatteryItem itemFill = ((IBatteryItem)itemStackFill.getItem());
			if(itemFill.getCharge(itemStackFill) == itemFill.getMaxCharge()) {
				if(inventory.getStackInSlot(3) == null || inventory.getStackInSlot(3).isEmpty()){
					inventory.setStackInSlot(3, itemStackFill);
					inventory.setStackInSlot(2, ItemStack.EMPTY);
				}
			}
		}
	}
	
	@Override
	public void update() {
		if(!world.isRemote) {
			if(this.power > getMaxPower()) this.power = getMaxPower();
			long prevPower = this.power;

			if(inventory.getSlots() < 3){
				inventory = this.getNewInventory(4, 64);
			}

			power = Library.chargeTEFromItems(inventory, 0, power, getMaxPower());
			
			//////////////////////////////////////////////////////////////////////
			this.transmitPowerFairly();
			//////////////////////////////////////////////////////////////////////
			
			power = Library.chargeItemsFromTE(inventory, 2, power, getMaxPower());
			
			byte comp = this.getComparatorPower();
			if(comp != this.lastRedstone)
				this.markDirty();
			this.lastRedstone = comp;

			tryMoveItems();
			long avg = (power >> 1) + (prevPower >> 1); //had issue with getting avg of extreme long values
			
			this.powerDelta = avg - this.log[0];
			for(int i = 1; i < this.log.length; i++) {
				this.log[i - 1] = this.log[i];
			}
			this.log[this.log.length-1] = avg;

			this.networkPack(packNBT(), 20);
		}
	}

	public NBTTagCompound packNBT(){
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setLong("power", power);
		nbt.setLong("powerDelta", powerDelta);
		nbt.setShort("redLow", redLow);
		nbt.setShort("redHigh", redHigh);
		nbt.setByte("priority", (byte) this.priority.ordinal());
		return nbt;
	}

	protected void transmitPowerFairly() {
		
		short mode = (short) this.getRelevantMode();
		
		//HasSets to we don't have any duplicates
		Set<IPowerNet> nets = new HashSet();
		Set<IEnergyConnector> consumers = new HashSet();
		
		//iterate over all sides
		for(ForgeDirection dir : getSendDirections()) {
			
			TileEntity te = world.getTileEntity(pos.add(dir.offsetX, dir.offsetY, dir.offsetZ));
			
			//if it's a cable, buffer both the network and all subscribers of the net
			if(te instanceof IEnergyConductor) {
				IEnergyConductor con = (IEnergyConductor) te;
				if(con.canConnect(dir.getOpposite()) && con.getPowerNet() != null) {
					nets.add(con.getPowerNet());
					con.getPowerNet().unsubscribe(this);
					consumers.addAll(con.getPowerNet().getSubscribers());
				}
				
			//if it's just a consumer, buffer it as a subscriber
			} else if(te instanceof IEnergyConnector) {
				IEnergyConnector con = (IEnergyConnector) te;
				if(con.canConnect(dir.getOpposite())) {
					consumers.add((IEnergyConnector) te);
				}
			}
		}

		//send power to buffered consumers, independent of nets
		if(this.power > 0 && (mode == mode_buffer || mode == mode_output)) {
			List<IEnergyConnector> con = new ArrayList();
			con.addAll(consumers);
			
			if(PowerNet.trackingInstances == null) {
				PowerNet.trackingInstances = new ArrayList();
			}
			PowerNet.trackingInstances.clear();
			
			nets.forEach(x -> {
				if(x instanceof PowerNet)
					PowerNet.trackingInstances.add((PowerNet) x);
			});
			
			long toSend = Math.min(this.power, this.getMaxTransfer());
			long powerRemaining = this.power - toSend;
			this.power = PowerNet.fairTransferWithPrio(this.getPriority(), con, toSend) + powerRemaining;
		}
		
		//resubscribe to buffered nets, if necessary
		if(mode == mode_buffer || mode == mode_input) {
			nets.forEach(x -> x.subscribe(this));
		}
	}

	public long getMaxTransfer() {
		return this.getMaxPower() / 20;
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) { 
		this.power = nbt.getLong("power");
		this.powerDelta = nbt.getLong("powerDelta");
		this.redLow = nbt.getShort("redLow");
		this.redHigh = nbt.getShort("redHigh");
		this.priority = ConnectionPriority.values()[nbt.getByte("priority")];
	}
	
	public short getRelevantMode() {
		
		if(world.isBlockIndirectlyGettingPowered(pos) > 0) {
			return this.redHigh;
		} else {
			return this.redLow;
		}
	}

	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	private long bufferedMax = 0;

	@Override
	public long getMaxPower() {
		
		if(bufferedMax == 0) {
			bufferedMax = ((MachineBattery)world.getBlockState(pos).getBlock()).getMaxPower();
		}
		
		return bufferedMax;
	}

	@Override
	public long transferPower(long powerInput) {

		int mode = this.getRelevantMode();
		
		if(mode == mode_output || mode == mode_none) {
			return powerInput;
		}
		this.markDirty();
		long ownMaxPower = this.getMaxPower();
		if(powerInput > ownMaxPower-this.power) {
			
			long overshoot = powerInput-(ownMaxPower-this.power);
			this.power = ownMaxPower;
			return overshoot;
		}
		this.power += powerInput;
		return 0;
	}

	@Override
	public long getTransferWeight() {

		int mode = this.getRelevantMode();
		
		if(mode == mode_output || mode == mode_none) {
			return 0;
		}
		
		return Math.max(getMaxPower() - getPower(), 0);
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return true;
	}

	@Override
	public ConnectionPriority getPriority() {
		return this.priority;
	}

	@Override
	public boolean isStorage() { //used for batteries
		return true;
	}


	// opencomputers interface

	@Override
	public String getComponentName() {
		return "battery";
	}

	@Callback(doc = "getPower(); returns the current power level - long")
	public Object[] getPower(Context context, Arguments args) {
		return new Object[] {power};
	}

	@Callback(doc = "getMaxPower(); returns the maximum power level - long")
	public Object[] getMaxPower(Context context, Arguments args) {
		return new Object[] {getMaxPower()};
	}

	@Callback(doc = "getChargePercent(); returns the charge in percent - double")
	public Object[] getChargePercent(Context context, Arguments args) {
		return new Object[] {100D * getPower()/(double)getMaxPower()};
	}

	@Callback(doc = "getPowerDelta(); returns the in/out power flow - long")
	public Object[] getPowerDelta(Context context, Arguments args) {
		return new Object[] {powerDelta};
	}

	@Callback(doc = "getPriority(); returns the priority (1:low, 2:normal, 3:high) - int")
	public Object[] getPriority(Context context, Arguments args) {
		return new Object[] {1+getPriority().ordinal()};
	}

	@Callback(doc = "setPriority(int prio); sets the priority (1:low, 2:normal, 3:high)")
	public Object[] setPriority(Context context, Arguments args) {
		int prio = args.checkInteger(0);
		if(prio == 1) priority = ConnectionPriority.LOW;
		if(prio == 2) priority = ConnectionPriority.NORMAL;
		if(prio == 3) priority = ConnectionPriority.HIGH;
		return new Object[] {null};
	}
}
