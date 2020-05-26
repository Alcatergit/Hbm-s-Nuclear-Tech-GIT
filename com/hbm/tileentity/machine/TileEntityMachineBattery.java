package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.machine.MachineBattery;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.items.machine.ItemBattery;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityMachineBattery extends TileEntityMachineBase implements ITickable, IConsumer, ISource {

	public long power = 0;
	public long maxPower = 1000000;
	
	//0: input only
	//1: buffer
	//2: output only
	//3: nothing
	public short redLow = 0;
	public short redHigh = 2;
	
	public boolean conducts = false;
	
	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] {0, 1};
	private static final int[] slots_side = new int[] {1};
	public int age = 0;
	public List<IConsumer> list = new ArrayList<IConsumer>();
	
	private String customName;
	
	public TileEntityMachineBattery() {
		super(2);
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
		compound.setShort("redLow", redLow);
		compound.setShort("redHigh", redHigh);
		detectPower = power + 1;
		
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		power = compound.getLong("power");
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
		switch(i)
		{
		case 0:
			if(stack.getItem() instanceof ItemBattery)
				return true;
			break;
		case 1:
			if(stack.getItem() instanceof ItemBattery)
				return true;
			break;
		}
		
		return true;
	}
	
	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		
		if(itemStack.getItem() instanceof ItemBattery) {
			if(i == 0 && ItemBattery.getCharge(itemStack) == 0) {
				return true;
			}
			if(i == 1 && ItemBattery.getCharge(itemStack) == ItemBattery.getMaxChargeStatic(itemStack)) {
				return true;
			}
		}
			
		return false;
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
			
			power = Library.chargeTEFromItems(inventory, 0, power, maxPower);
			power = Library.chargeItemsFromTE(inventory, 1, power, maxPower);
			
			detectAndSendChanges();
		}
		
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) { 

		this.power = nbt.getLong("power");
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
	
	private void detectAndSendChanges() {
		boolean mark = false;
		
		if(detectConducts != conducts){
			mark = true;
			detectConducts = conducts;
		}
		if(detectPower != power){
			mark = true;
			detectPower = power;
		}
		
		if(mark)
			markDirty();
	}
}
