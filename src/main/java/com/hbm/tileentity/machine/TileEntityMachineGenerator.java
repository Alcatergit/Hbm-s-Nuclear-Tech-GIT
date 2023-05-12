package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.machine.MachineGenerator;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFuelRod;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachineGenerator extends TileEntityLoadedBase implements ITickable, IEnergyGenerator, IFluidHandler, ITankPacketAcceptor {

	public ItemStackHandler inventory;
	
	public int heat;
	public final int heatMax = 100000;
	public long power;
	public final long maxPower = 100000;
	public boolean isLoaded = false;
	public FluidTank[] tanks;
	public Fluid[] tankTypes;
	public boolean needsUpdate;
	
	//private static final int[] slots_top = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
	//private static final int[] slots_bottom = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
	//private static final int[] slots_side = new int[] {9, 10, 11};
	
	private String customName;
	
	public TileEntityMachineGenerator() {
		inventory = new ItemStackHandler(14){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
			@Override
			public boolean isItemValid(int i, ItemStack itemStack) {
				if(i >= 0 && i <= 8)
					if(itemStack.getItem() instanceof ItemFuelRod)
						return true;
				if(i == 9)
					if(FFUtils.containsFluid(itemStack, FluidRegistry.WATER))
						return true;
				if(i == 10)
					if(FFUtils.containsFluid(itemStack, ModForgeFluids.coolant))
						return true;
				if(i == 11)
					if(itemStack.getItem() instanceof IBatteryItem)
						return true;
				return false;
			}
			
			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
				if(this.isItemValid(slot, stack))
					return super.insertItem(slot, stack, simulate);
				return ItemStack.EMPTY;
			}
		};
		
		tanks = new FluidTank[2];
		tankTypes = new Fluid[2];
		tanks[0] = new FluidTank(32000);
		tankTypes[0] = FluidRegistry.WATER;
		tanks[1] = new FluidTank(16000);
		tankTypes[1] = ModForgeFluids.coolant;
		needsUpdate = false;
	}
	
	@Override
	public void update() {
		if(!world.isRemote)
		{
			
			if(needsUpdate) {
				PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos.getX(), pos.getY(), pos.getZ(), new FluidTank[] {tanks[0], tanks[1]}), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 100));
				needsUpdate = false;
			}
			this.sendPower(world, pos);
			
			if(this.inputValidForTank(0, 9))
				FFUtils.fillFromFluidContainer(inventory, tanks[0], 9, 12);
			if(this.inputValidForTank(1, 10))
				FFUtils.fillFromFluidContainer(inventory, tanks[1], 10, 13);
			
			//Batteries
			power = Library.chargeItemsFromTE(inventory, 11, power, maxPower);
			
			for(int i = 0; i < 9; i++)
			{
				if(inventory.getStackInSlot(i) != ItemStack.EMPTY && inventory.getStackInSlot(i).getItem() == ModItems.rod_uranium_fuel)
				{
					int j = ItemFuelRod.getLifeTime(inventory.getStackInSlot(i));
					ItemFuelRod.setLifetime(inventory.getStackInSlot(i), j + 1);
					attemptHeat(1);
					attemptPower(100);
				
					if(ItemFuelRod.getLifeTime(inventory.getStackInSlot(i)) == ((ItemFuelRod)inventory.getStackInSlot(i).getItem()).getMaxLifeTime())
					{
						inventory.setStackInSlot(i, new ItemStack(ModItems.rod_uranium_fuel_depleted));
					}
				}
				if(inventory.getStackInSlot(i) != ItemStack.EMPTY && inventory.getStackInSlot(i).getItem() == ModItems.rod_dual_uranium_fuel)
				{
					int j = ItemFuelRod.getLifeTime(inventory.getStackInSlot(i));
					ItemFuelRod.setLifetime(inventory.getStackInSlot(i), j + 1);
					attemptHeat(1);
					attemptPower(100);

					if(ItemFuelRod.getLifeTime(inventory.getStackInSlot(i)) == ((ItemFuelRod)inventory.getStackInSlot(i).getItem()).getMaxLifeTime())
					{
						inventory.setStackInSlot(i, new ItemStack(ModItems.rod_dual_uranium_fuel_depleted));
					}
				}
				if(inventory.getStackInSlot(i) != ItemStack.EMPTY && inventory.getStackInSlot(i).getItem() == ModItems.rod_quad_uranium_fuel)
				{
					int j = ItemFuelRod.getLifeTime(inventory.getStackInSlot(i));
					ItemFuelRod.setLifetime(inventory.getStackInSlot(i), j + 1);
					attemptHeat(1);
					attemptPower(100);

					if(ItemFuelRod.getLifeTime(inventory.getStackInSlot(i)) == ((ItemFuelRod)inventory.getStackInSlot(i).getItem()).getMaxLifeTime())
					{
						inventory.setStackInSlot(i, new ItemStack(ModItems.rod_quad_uranium_fuel_depleted));
					}
				}
				if(inventory.getStackInSlot(i) != ItemStack.EMPTY && inventory.getStackInSlot(i).getItem() == ModItems.rod_plutonium_fuel)
				{
					int j = ItemFuelRod.getLifeTime(inventory.getStackInSlot(i));
					ItemFuelRod.setLifetime(inventory.getStackInSlot(i), j + 1);
					attemptHeat(2);
					attemptPower(150);

					if(ItemFuelRod.getLifeTime(inventory.getStackInSlot(i)) == ((ItemFuelRod)inventory.getStackInSlot(i).getItem()).getMaxLifeTime())
					{
						inventory.setStackInSlot(i, new ItemStack(ModItems.rod_plutonium_fuel_depleted));
					}
				}
				if(inventory.getStackInSlot(i) != ItemStack.EMPTY && inventory.getStackInSlot(i).getItem() == ModItems.rod_dual_plutonium_fuel)
				{
					int j = ItemFuelRod.getLifeTime(inventory.getStackInSlot(i));
					ItemFuelRod.setLifetime(inventory.getStackInSlot(i), j + 1);
					attemptHeat(2);
					attemptPower(150);

					if(ItemFuelRod.getLifeTime(inventory.getStackInSlot(i)) == ((ItemFuelRod)inventory.getStackInSlot(i).getItem()).getMaxLifeTime())
					{
						inventory.setStackInSlot(i, new ItemStack(ModItems.rod_dual_plutonium_fuel_depleted));
					}
				}
				if(inventory.getStackInSlot(i) != ItemStack.EMPTY && inventory.getStackInSlot(i).getItem() == ModItems.rod_quad_plutonium_fuel)
				{
					int j = ItemFuelRod.getLifeTime(inventory.getStackInSlot(i));
					ItemFuelRod.setLifetime(inventory.getStackInSlot(i), j + 1);
					attemptHeat(2);
					attemptPower(150);

					if(ItemFuelRod.getLifeTime(inventory.getStackInSlot(i)) == ((ItemFuelRod)inventory.getStackInSlot(i).getItem()).getMaxLifeTime())
					{
						inventory.setStackInSlot(i, new ItemStack(ModItems.rod_quad_plutonium_fuel_depleted));
					}
				}
				if(inventory.getStackInSlot(i) != ItemStack.EMPTY && inventory.getStackInSlot(i).getItem() == ModItems.rod_mox_fuel)
				{
					int j = ItemFuelRod.getLifeTime(inventory.getStackInSlot(i));
					ItemFuelRod.setLifetime(inventory.getStackInSlot(i), j + 1);
					attemptHeat(1);
					attemptPower(50);

					if(ItemFuelRod.getLifeTime(inventory.getStackInSlot(i)) == ((ItemFuelRod)inventory.getStackInSlot(i).getItem()).getMaxLifeTime())
					{
						inventory.setStackInSlot(i, new ItemStack(ModItems.rod_mox_fuel_depleted));
					}
				}
				if(inventory.getStackInSlot(i) != ItemStack.EMPTY && inventory.getStackInSlot(i).getItem() == ModItems.rod_dual_mox_fuel)
				{
					int j = ItemFuelRod.getLifeTime(inventory.getStackInSlot(i));
					ItemFuelRod.setLifetime(inventory.getStackInSlot(i), j + 1);
					attemptHeat(1);
					attemptPower(50);

					if(ItemFuelRod.getLifeTime(inventory.getStackInSlot(i)) == ((ItemFuelRod)inventory.getStackInSlot(i).getItem()).getMaxLifeTime())
					{
						inventory.setStackInSlot(i, new ItemStack(ModItems.rod_dual_mox_fuel_depleted));
					}
				}
				if(inventory.getStackInSlot(i) != ItemStack.EMPTY && inventory.getStackInSlot(i).getItem() == ModItems.rod_quad_mox_fuel)
				{
					int j = ItemFuelRod.getLifeTime(inventory.getStackInSlot(i));
					ItemFuelRod.setLifetime(inventory.getStackInSlot(i), j + 1);
					attemptHeat(1);
					attemptPower(50);

					if(ItemFuelRod.getLifeTime(inventory.getStackInSlot(i)) == ((ItemFuelRod)inventory.getStackInSlot(i).getItem()).getMaxLifeTime())
					{
						inventory.setStackInSlot(i, new ItemStack(ModItems.rod_quad_mox_fuel_depleted));
					}
				}
				if(inventory.getStackInSlot(i) != ItemStack.EMPTY && inventory.getStackInSlot(i).getItem() == ModItems.rod_schrabidium_fuel)
				{
					int j = ItemFuelRod.getLifeTime(inventory.getStackInSlot(i));
					ItemFuelRod.setLifetime(inventory.getStackInSlot(i), j + 1);
					attemptHeat(10);
					attemptPower(25000);

					if(ItemFuelRod.getLifeTime(inventory.getStackInSlot(i)) == ((ItemFuelRod)inventory.getStackInSlot(i).getItem()).getMaxLifeTime())
					{
						inventory.setStackInSlot(i, new ItemStack(ModItems.rod_schrabidium_fuel_depleted));
					}
				}
				if(inventory.getStackInSlot(i) != ItemStack.EMPTY && inventory.getStackInSlot(i).getItem() == ModItems.rod_dual_schrabidium_fuel)
				{
					int j = ItemFuelRod.getLifeTime(inventory.getStackInSlot(i));
					ItemFuelRod.setLifetime(inventory.getStackInSlot(i), j + 1);
					attemptHeat(10);
					attemptPower(25000);

					if(ItemFuelRod.getLifeTime(inventory.getStackInSlot(i)) == ((ItemFuelRod)inventory.getStackInSlot(i).getItem()).getMaxLifeTime())
					{
						inventory.setStackInSlot(i, new ItemStack(ModItems.rod_dual_schrabidium_fuel_depleted));
					}
				}
				if(inventory.getStackInSlot(i) != ItemStack.EMPTY && inventory.getStackInSlot(i).getItem() == ModItems.rod_quad_schrabidium_fuel)
				{
					int j = ItemFuelRod.getLifeTime(inventory.getStackInSlot(i));
					ItemFuelRod.setLifetime(inventory.getStackInSlot(i), j + 1);
					attemptHeat(10);
					attemptPower(25000);

					if(ItemFuelRod.getLifeTime(inventory.getStackInSlot(i)) == ((ItemFuelRod)inventory.getStackInSlot(i).getItem()).getMaxLifeTime())
					{
						inventory.setStackInSlot(i, new ItemStack(ModItems.rod_quad_schrabidium_fuel_depleted));
					}
				}
			}
			
			if(this.power > maxPower)
			{
				this.power = maxPower;
			}
			
			if(this.heat > heatMax)
			{
				this.explode();
			}
			
			if(((inventory.getStackInSlot(0) != ItemStack.EMPTY && inventory.getStackInSlot(0).getItem() instanceof ItemFuelRod) || inventory.getStackInSlot(0) == ItemStack.EMPTY) && 
					((inventory.getStackInSlot(1) != ItemStack.EMPTY && !(inventory.getStackInSlot(1).getItem() instanceof ItemFuelRod)) || inventory.getStackInSlot(1) == ItemStack.EMPTY) && 
					((inventory.getStackInSlot(2) != ItemStack.EMPTY && !(inventory.getStackInSlot(2).getItem() instanceof ItemFuelRod)) || inventory.getStackInSlot(2) == ItemStack.EMPTY) && 
					((inventory.getStackInSlot(3) != ItemStack.EMPTY && !(inventory.getStackInSlot(3).getItem() instanceof ItemFuelRod)) || inventory.getStackInSlot(3) == ItemStack.EMPTY) && 
					((inventory.getStackInSlot(4) != ItemStack.EMPTY && !(inventory.getStackInSlot(4).getItem() instanceof ItemFuelRod)) || inventory.getStackInSlot(4) == ItemStack.EMPTY) && 
					((inventory.getStackInSlot(5) != ItemStack.EMPTY && !(inventory.getStackInSlot(5).getItem() instanceof ItemFuelRod)) || inventory.getStackInSlot(5) == ItemStack.EMPTY) && 
					((inventory.getStackInSlot(6) != ItemStack.EMPTY && !(inventory.getStackInSlot(6).getItem() instanceof ItemFuelRod)) || inventory.getStackInSlot(6) == ItemStack.EMPTY) && 
					((inventory.getStackInSlot(7) != ItemStack.EMPTY && !(inventory.getStackInSlot(7).getItem() instanceof ItemFuelRod)) || inventory.getStackInSlot(7) == ItemStack.EMPTY) && 
					((inventory.getStackInSlot(8) != ItemStack.EMPTY && !(inventory.getStackInSlot(8).getItem() instanceof ItemFuelRod)) || inventory.getStackInSlot(8) == ItemStack.EMPTY))
			{
				if(this.heat - 10 >= 0 && tanks[1].getFluidAmount() - 2 >= 0)
				{
					this.heat -= 10;
					tanks[1].drain(2, true);
				}
				
				if(this.heat < 10 && heat != 0 && this.tanks[1].getFluidAmount() != 0)
				{
					this.heat--;
					tanks[1].drain(1, true);
				}
				
				if(this.heat != 0 && this.tanks[1].getFluidAmount() == 0)
				{
					this.heat--;
				}
				
				if(this.world.getBlockState(pos) instanceof MachineGenerator)
					this.isLoaded = false;
				
			} else {

				if(this.world.getBlockState(pos) instanceof MachineGenerator)
					this.isLoaded = true;
			}
			
			
			detectAndSendChanges();
		}
		
	}
	
	

	protected boolean inputValidForTank(int tank, int slot){
		if(inventory.getStackInSlot(slot) != ItemStack.EMPTY && tanks[tank] != null){
			return FFUtils.checkRestrictions(inventory.getStackInSlot(slot), f -> f.getFluid() == tankTypes[tank]);
		}
		return false;
	}
	
	public void attemptPower(int i) {
		
		int j = (int) Math.ceil(i / 100);
		
		if(this.tanks[0].getFluidAmount() - j >= 0)
		{
			this.power += i;
			if(j > tanks[0].getCapacity() / 25)
				j = tanks[0].getCapacity() / 25;
			tanks[0].drain(j, true);
		}
	}
	
	public void attemptHeat(int i) {
		Random rand = new Random();
		
		int j = rand.nextInt(i + 1);
		
		if(this.tanks[1].getFluidAmount() - j >= 0)
		{
			tanks[1].drain(j, true);
		} else {
			this.heat += i;
		}
	}
	
	public void explode() {
		for(int i = 0; i < inventory.getSlots(); i++)
		{
			inventory.setStackInSlot(i, ItemStack.EMPTY);
		}
		
    	world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 18.0F, true);
    	ExplosionNukeGeneric.waste(world, pos.getX(), pos.getY(), pos.getZ(), 35);
    	world.setBlockState(pos, Blocks.FLOWING_LAVA.getDefaultState());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		tankTypes[0] = FluidRegistry.WATER;
		tankTypes[1] = ModForgeFluids.coolant;
		power = compound.getLong("power");
		detectPower = power + 1;
		heat = compound.getInteger("heat");
		detectHeat = heat + 1;
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		if(compound.hasKey("tanks"))
			FFUtils.deserializeTankArray(compound.getTagList("tanks", 10), this.tanks);
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("power", power);
		compound.setInteger("heat", heat);
		NBTTagCompound tag = inventory.serializeNBT();
		compound.setTag("inventory", tag);
		NBTTagList tankTag = FFUtils.serializeTankArray(this.tanks);
		compound.setTag("tanks", tankTag);
		return super.writeToNBT(compound);
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.generator";
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
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getHeatScaled(int i) {
		return (heat * i) / heatMax;
	}
	
	public boolean hasPower() {
		return power > 0;
	}
	
	public boolean hasHeat() {
		return heat > 0;
	}

	@Override
	public void recievePacket(NBTTagCompound[] tags) {
		if(tags.length != 2){
			return;
		} else {
			tanks[0].readFromNBT(tags[0]);
			tanks[1].readFromNBT(tags[1]);
		}
		
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(resource == null){
			return 0;
		} else if (resource.getFluid() == FluidRegistry.WATER){
			return tanks[0].fill(resource, doFill);
		} else if (resource.getFluid() == ModForgeFluids.coolant){
			return tanks[1].fill(resource, doFill);
		} else {
		return 0;
		}
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
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0]};
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) :
			capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this) : 
				super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
	}
	
	private long detectPower;
	private int detectHeat;
	private FluidTank[] detectTanks = new FluidTank[]{null, null};
	
	private void detectAndSendChanges() {
		
		boolean mark = false;
		if(detectPower != power){
			mark = true;
			detectPower = power;
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos.getX(), pos.getY(), pos.getZ(), power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 100));
		}
		if(detectHeat != heat){
			mark = true;
			detectHeat = heat;
		}
		if(!FFUtils.areTanksEqual(tanks[0], detectTanks[0])){
			mark = true;
			detectTanks[0] = FFUtils.copyTank(tanks[0]);
			needsUpdate = true;
		}
		if(!FFUtils.areTanksEqual(tanks[1], detectTanks[1])){
			mark = true;
			detectTanks[1] = FFUtils.copyTank(tanks[1]);
			needsUpdate = true;
		}
		if(mark)
			markDirty();
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
