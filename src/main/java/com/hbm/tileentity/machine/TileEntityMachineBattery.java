package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.packet.AuxLongPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.blocks.machine.MachineBattery;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.item.Item;
import api.hbm.energy.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class TileEntityMachineBattery extends TileEntityMachineBase implements ITickable, IConsumer, ISource {

	public long[] log = new long[20];
	public long power = 0;
	public long powerDelta = 0;
	public long maxPower = 1000000;

	//0: input only
	//1: buffer
	//2: output only
	//3: nothing
	public short redLow = 0;
	public short redHigh = 2;
	
	public boolean conducts = false;
	
	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] {1, 3};
	private static final int[] slots_side = new int[] {2};
	public int age = 0;
	public List<IConsumer> list = new ArrayList<IConsumer>();
	
	private String customName;
	
	public TileEntityMachineBattery() {
		super(4);
	}
	
	public TileEntityMachineBattery(long power) {
		this();
		maxPower = power;
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
		if(world.getTileEntity(pos) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <=64;
		}
	}
	
	public long getPowerRemainingScaled(long i) {
		return (power * i) / maxPower;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("conducts", conducts);
		compound.setLong("power", power);
		compound.setLong("powerDelta", powerDelta);
		compound.setShort("redLow", redLow);
		compound.setShort("redHigh", redHigh);
		detectPower = power + 1;
		
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		power = compound.getLong("power");
		powerDelta = compound.getLong("powerDelta");
		this.conducts = compound.getBoolean("conducts");
		this.redLow = compound.getShort("redLow");
		this.redHigh = compound.getShort("redHigh");
		super.readFromNBT(compound);
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
		}else{
			Item itemFillX = itemStackFill.getItem();
			if(itemFillX == ModItems.dynosphere_desh_charged || itemFillX == ModItems.dynosphere_schrabidium_charged || itemFillX == ModItems.dynosphere_euphemium_charged || itemFillX == ModItems.dynosphere_dineutronium_charged){
				if(inventory.getStackInSlot(3) == null || inventory.getStackInSlot(3).isEmpty()){
					inventory.setStackInSlot(3, itemStackFill);
					inventory.setStackInSlot(2, ItemStack.EMPTY);
				}
			}
		}
	}
	
	@Override
	public void update() {
		if(world.getBlockState(pos).getBlock() instanceof MachineBattery && !world.isRemote) {
			
			this.maxPower = ((MachineBattery)world.getBlockState(pos).getBlock()).getMaxPower();
		
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
			long avg = (power + prevPower) / 2;
			this.powerDelta = avg - this.log[0];

			for(int i = 1; i < this.log.length; i++) {
				this.log[i - 1] = this.log[i];
			}
			
			this.log[this.log.length-1] = avg;

			detectAndSendChanges();
		}
		
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) { 

		this.power = nbt.getLong("power");
		this.powerDelta = nbt.getLong("powerDelta");
		this.maxPower = nbt.getLong("maxPower");
		this.redLow = nbt.getShort("redLow");
		this.redHigh = nbt.getShort("redHigh");
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

	@Override
	public long getMaxPower() {
		if(!world.isRemote && getRelevantMode() >= 2)
			return 0;
		return maxPower;
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(pos.up(), getTact());
		ffgeua(pos.down(), getTact());
		ffgeua(pos.west(), getTact());
		ffgeua(pos.east(), getTact());
		ffgeua(pos.north(), getTact());
		ffgeua(pos.south(), getTact());
		
	}

	@Override
	public void ffgeua(BlockPos pos, boolean newTact) {
		Library.ffgeua(new BlockPos.MutableBlockPos(pos), newTact, this, world);
	}

	@Override
	public boolean getTact() {
		if(age >= 0 && age < 10)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public long getSPower() {
		return power;
	}

	@Override
	public void setSPower(long i) {
		this.power = i;
	}

	@Override
	public List<IConsumer> getList() {
		return list;
	}

	@Override
	public void clearList() {
		list.clear();
	}
	
	private boolean detectConducts;
	private long detectPower;
	
	public void detectAndSendChanges() {
		boolean mark = false;
		
		if(detectConducts != conducts){
			mark = true;
			detectConducts = conducts;
		}
		if(detectPower != power){
			mark = true;
			detectPower = power;
		}
		PacketDispatcher.wrapper.sendToAllAround(new AuxLongPacket(pos.getX(), pos.getY(), pos.getZ(), powerDelta, 0), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
		
		if(mark)
			markDirty();
	}
}
