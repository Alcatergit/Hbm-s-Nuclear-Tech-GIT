package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hbm.items.ModItems;
import com.hbm.packet.AuxLongPacket;
import com.hbm.packet.PacketDispatcher;
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

public class TileEntityMachineBattery extends TileEntityMachineBase implements ITickable, IEnergyUser {

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

	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] {1, 3};
	private static final int[] slots_side = new int[] {2};
	
	private String customName;
	
	public TileEntityMachineBattery() {
		super(4);
	}
	
	public TileEntityMachineBattery(long power) {
		this();
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
		compound.setLong("powerDelta", this.powerDelta);
		compound.setShort("redLow", this.redLow);
		compound.setShort("redHigh", this.redHigh);
		compound.setByte("priority", (byte)this.priority.ordinal());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.power = compound.getLong("power");
		this.powerDelta = compound.getLong("powerDelta");
		this.redLow = compound.getShort("redLow");
		this.redHigh = compound.getShort("redHigh");
		this.priority = ConnectionPriority.values()[compound.getByte("priority")];
		super.readFromNBT(compound);
	}


	public void writeNBT(NBTTagCompound nbt) {
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", this.power);
		data.setLong("powerDelta", this.powerDelta);
		data.setShort("redLow", this.redLow);
		data.setShort("redHigh", this.redHigh);
		data.setByte("priority", (byte)this.priority.ordinal());
		nbt.setTag("NBT_PERSISTENT_KEY", data);
	}


	public void readNBT(NBTTagCompound nbt) {
		NBTTagCompound data = nbt.getCompoundTag("NBT_PERSISTENT_KEY");
		this.power = data.getLong("power");
		this.powerDelta = data.getLong("powerDelta");
		this.redLow = data.getShort("redLow");
		this.redHigh = data.getShort("redHigh");
		this.priority = ConnectionPriority.values()[data.getByte("priority")];
	}

	@Override
	public int[] getAccessibleSlotsFromSide(EnumFacing p_94128_1_)
    {
        return p_94128_1_ == EnumFacing.DOWN ? slots_bottom : (p_94128_1_ == EnumFacing.UP ? slots_top : slots_side);
    }
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		if(i == 0)
			return (stack.getItem() instanceof IBatteryItem);
		if(i == 2)
			return (stack.getItem() instanceof IBatteryItem);
		return true;
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

			long prevPower = this.power;
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

			this.networkPack(packNBT(avg), 20);
		}
	}

	public NBTTagCompound packNBT(long power){
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
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			
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
			this.power = PowerNet.fairTransfer(con, this.power);
		}
		
		//resubscribe to buffered nets, if necessary
		if(mode == mode_buffer || mode == mode_input) {
			nets.forEach(x -> x.subscribe(this));
		}
	}
	
	protected void transmitPower() {
		
		short mode = (short) this.getRelevantMode();
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			
			TileEntity te = world.getTileEntity(pos.add(dir.offsetX, dir.offsetY, dir.offsetZ));
			
			// first we make sure we're not subscribed to the network that we'll be supplying
			if(te instanceof IEnergyConductor) {
				IEnergyConductor con = (IEnergyConductor) te;
				
				if(con.getPowerNet() != null && con.getPowerNet().isSubscribed(this))
					con.getPowerNet().unsubscribe(this);
			}
			
			//then we add energy
			if(mode == mode_buffer || mode == mode_output) {
				if(te instanceof IEnergyConnector) {
					IEnergyConnector con = (IEnergyConnector) te;
					long oldPower = this.power;
					long transfer = this.power - con.transferPower(this.power);
					this.power = oldPower - transfer;
				}
			}
			
			//then we subscribe if possible
			if(te instanceof IEnergyConductor) {
				IEnergyConductor con = (IEnergyConductor) te;
				
				if(con.getPowerNet() != null) {
					if(mode == mode_output || mode == mode_none) {
						if(con.getPowerNet().isSubscribed(this)) {
							con.getPowerNet().unsubscribe(this);
						}
					} else if(!con.getPowerNet().isSubscribed(this)) {
						con.getPowerNet().subscribe(this);
					}
				}
			}
		}
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
}
