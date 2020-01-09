package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.machine.MachineBattery;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.items.special.ItemBattery;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachineBattery extends TileEntity implements ITickable, IConsumer, ISource {

	public ItemStackHandler inventory;
	
	public long power = 0;
	public long maxPower = 1000000;
	
	public boolean conducts = false;
	
	//private static final int[] slots_top = new int[] {0};
	//private static final int[] slots_bottom = new int[] {0, 1};
	//private static final int[] slots_side = new int[] {1};
	public int age = 0;
	public List<IConsumer> list = new ArrayList<IConsumer>();
	
	private String customName;
	
	public TileEntityMachineBattery() {
		inventory = new ItemStackHandler(2){
			@Override
			protected void onContentsChanged(int slot) {
				super.onContentsChanged(slot);
				markDirty();
			}
			@Override
			public boolean isItemValid(int slot, ItemStack stack) {
				return stack.getItem() instanceof ItemBattery;
			}
			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
				if(this.isItemValid(slot, stack))
					return super.insertItem(slot, stack, simulate);
				else
					return ItemStack.EMPTY;
			}
		};
		
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
		compound.setTag("inventory", inventory.serializeNBT());
		compound.setBoolean("conducts", conducts);
		detectConducts = !conducts;
		compound.setLong("power", power);
		detectPower = power + 1;
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		power = compound.getLong("power");
		conducts = compound.getBoolean("conducts");
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}

	@Override
	public void update() {
		if(world.getBlockState(pos).getBlock() instanceof MachineBattery && !world.isRemote) {
			this.maxPower = ((MachineBattery)world.getBlockState(pos).getBlock()).getMaxPower();
			
			conducts = world.isBlockIndirectlyGettingPowered(pos) > 0;
		
			if(this.conducts)
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
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> cap, EnumFacing facing) {
		return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : super.getCapability(cap, facing);
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
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos.getX(), pos.getY(), pos.getZ(), power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 100));
			detectPower = power;
		}
		
		if(mark)
			markDirty();
	}
}
