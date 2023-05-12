package com.hbm.tileentity.machine;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemForgeFluidIdentifier;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.FluidTypePacketTest;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

public class TileEntityMachineTurbine extends TileEntityLoadedBase implements ITickable, IEnergyGenerator, ITankPacketAcceptor, IFluidHandler {

	public ItemStackHandler inventory;

	public long power;
	public static final long maxPower = 1000000;
	public FluidTank[] tanks;
	public Fluid[] tankTypes;
	//Drillgon200: Not even used but I'm too lazy to remove them
	public boolean needsTankTypeUpdate;

	// private static final int[] slots_top = new int[] {4};
	// private static final int[] slots_bottom = new int[] {6};
	// private static final int[] slots_side = new int[] {4};

	private String customName;

	public TileEntityMachineTurbine() {
		inventory = new ItemStackHandler(7) {
			@Override
			protected void onContentsChanged(int slot) {
				super.onContentsChanged(slot);
				markDirty();
			}

			@Override
			public boolean isItemValid(int slot, ItemStack stack) {
				if(slot == 0)
					return stack != null && stack.getItem() == ModItems.forge_fluid_identifier;
				if(slot == 4)
					if(stack != null && stack.getItem() instanceof IBatteryItem)
						return true;

				return slot != 4 && stack != null;
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
		tanks[0] = new FluidTank(64000);
		tankTypes[0] = ModForgeFluids.steam;
		tanks[1] = new FluidTank(128000);
		tankTypes[1] = ModForgeFluids.spentsteam;
		needsTankTypeUpdate = false;
	}

	@Override
	public void update() {
		if(!world.isRemote) {
			
			if(inventory.getStackInSlot(0).getItem() == ModItems.forge_fluid_identifier && inventory.getStackInSlot(1).isEmpty()){
				Fluid f = ItemForgeFluidIdentifier.getType(inventory.getStackInSlot(0));
				if(isValidFluidForTank(0, new FluidStack(f, 1000))){
					if(tankTypes[0] != f){
						tankTypes[0] = f;
						tanks[0].setFluid(null);
						tanks[1].setFluid(null);
					}
					inventory.setStackInSlot(1, inventory.getStackInSlot(0));
					inventory.setStackInSlot(0, ItemStack.EMPTY);
				}
			}

			PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos.getX(), pos.getY(), pos.getZ(), new FluidTank[] { tanks[0], tanks[1] }), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
			PacketDispatcher.wrapper.sendToAllAround(new FluidTypePacketTest(pos.getX(), pos.getY(), pos.getZ(), tankTypes), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));

			power = Library.chargeItemsFromTE(inventory, 4, power, maxPower);

			fillFluidInit(tanks[1]);
			this.sendPower(world, pos);

			if(inputValidForTank(0, 2))
				if(FFUtils.fillFromFluidContainer(inventory, tanks[0], 2, 3)) {
					if(tanks[0].getFluid() != null) {
						tankTypes[0] = tanks[0].getFluid().getFluid();
					}
				}

			Object[] outs = MachineRecipes.getTurbineOutput(tanks[0].getFluid() == null ? null : tanks[0].getFluid().getFluid());

			if(outs == null) {

			} else {
				tankTypes[1] = ((Fluid) outs[0]);

				int processMax = 1200;																//the maximum amount of cycles based on the 1.2k cycle cap (subject to change)
				int processSteam = tanks[0].getFluidAmount() / (Integer)outs[2];							//the maximum amount of cycles depending on steam
				int processWater = (tanks[1].getCapacity() - tanks[1].getFluidAmount()) / (Integer)outs[1];	//the maximum amount of cycles depending on water

				int cycles = Math.min(processMax, Math.min(processSteam, processWater));

				tanks[0].drain((Integer)outs[2] * cycles, true);
				tanks[1].fill(new FluidStack(tankTypes[1], (Integer)outs[1] * cycles), true);

				power += (Integer)outs[3] * cycles;

				if(power > maxPower)
					power = maxPower;
			}

			FFUtils.fillFluidContainer(inventory, tanks[1], 5, 6);

			detectAndSendChanges();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("tankType0"))
			tankTypes[0] = FluidRegistry.getFluid(nbt.getString("tankType0"));
		else
			tankTypes[0] = null;
		if(nbt.hasKey("tankType1"))
			tankTypes[1] = FluidRegistry.getFluid(nbt.getString("tankType1"));
		else
			tankTypes[1] = null;

		power = nbt.getLong("power");
		detectPower = power + 1;
		if(nbt.hasKey("tanks"))
			FFUtils.deserializeTankArray(nbt.getTagList("tanks", 10), tanks);
		if(nbt.hasKey("inventory"))
			inventory.deserializeNBT(nbt.getCompoundTag("inventory"));

		super.readFromNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setTag("tanks", FFUtils.serializeTankArray(tanks));
		nbt.setTag("inventory", inventory.serializeNBT());
		if(tankTypes[0] != null)
			nbt.setString("tankType0", tankTypes[0].getName());
		if(tankTypes[1] != null)
			nbt.setString("tankType1", tankTypes[1].getName());
		nbt.setLong("power", power);
		return super.writeToNBT(nbt);
	}

	protected boolean inputValidForTank(int tank, int slot) {
		if(inventory.getStackInSlot(slot) != ItemStack.EMPTY && tanks[tank] != null) {
			return FFUtils.checkRestrictions(inventory.getStackInSlot(slot), f -> f.getFluid() == tankTypes[tank]);
		}
		return false;
	}

	private boolean isValidFluidForTank(int tank, FluidStack stack) {
		if(stack == null || tanks[tank] == null)
			return false;
		return stack.getFluid() == ModForgeFluids.steam || stack.getFluid() == ModForgeFluids.hotsteam || stack.getFluid() == ModForgeFluids.superhotsteam || stack.getFluid() == ModForgeFluids.ultrahotsteam;
	}

	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.machineTurbine";
	}

	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomName(String name) {
		this.customName = name;
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
		if(world.getTileEntity(pos) != this) {
			return false;
		} else {
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64;
		}
	}

	public long getPowerScaled(int i) {
		return (power * i) / maxPower;
	}

	@Override
	public void recievePacket(NBTTagCompound[] tags) {
		if(tags.length != 2) {
			return;
		} else {
			tanks[0].readFromNBT(tags[0]);
			tanks[1].readFromNBT(tags[1]);
		}

	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[] { tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0] };
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(resource != null && resource.getFluid() == tankTypes[0] && resource.amount > 0) {
			return tanks[0].fill(resource, doFill);
		} else {
			return 0;
		}
	}

	public void fillFluidInit(FluidTank tank) {

		FFUtils.fillFluid(this, tank, world, pos.east(), 64000);
		FFUtils.fillFluid(this, tank, world, pos.west(), 64000);
		FFUtils.fillFluid(this, tank, world, pos.up(), 64000);
		FFUtils.fillFluid(this, tank, world, pos.down(), 64000);
		FFUtils.fillFluid(this, tank, world, pos.south(), 64000);
		FFUtils.fillFluid(this, tank, world, pos.north(), 64000);
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(resource != null && resource.getFluid() == tankTypes[1] && resource.amount > 0) {
			return tanks[1].drain(resource.amount, doDrain);
		} else {
			return null;
		}
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if(maxDrain > 0) {
			return tanks[1].drain(maxDrain, doDrain);
		} else {
			return null;
		}
	}

	@Override
	public boolean hasCapability(Capability<?> cap, EnumFacing facing) {
		return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(cap, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this) : super.getCapability(capability, facing);
	}

	private long detectPower;
	private FluidTank[] detectTanks = new FluidTank[] { null, null };
	private Fluid[] detectFluids = new Fluid[] { null, null };

	private void detectAndSendChanges() {

		boolean mark = false;
		
		PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos.getX(), pos.getY(), pos.getZ(), power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
		
		if(detectPower != power) {
			mark = true;
			detectPower = power;
		}
		if(!FFUtils.areTanksEqual(detectTanks[0], tanks[0])) {
			mark = true;
			detectTanks[0] = FFUtils.copyTank(tanks[0]);
		}
		if(!FFUtils.areTanksEqual(detectTanks[1], tanks[1])) {
			mark = true;
			detectTanks[1] = FFUtils.copyTank(tanks[1]);
		}
		if(detectFluids[0] != tankTypes[0]) {
			mark = true;
			detectFluids[0] = tankTypes[0];
		}
		if(detectFluids[1] != tankTypes[1]) {
			mark = true;
			detectFluids[1] = tankTypes[1];
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
