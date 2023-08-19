package com.hbm.tileentity.machine.oil;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.RefineryRecipes;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.util.Tuple.Pair;

import api.hbm.energy.IEnergyUser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMachineRefinery extends TileEntityMachineBase implements ITickable, IEnergyUser, IFluidHandler, ITankPacketAcceptor {

	public long power = 0;
	public int itemOutputTimer = 0;
	public static final int totalItemTime = 50;
	public static final long maxPower = 1000;
	public int age = 0;
	public boolean needsUpdate = false;
	public FluidTank[] tanks;
	public Fluid[] tankTypes;

	//private static final int[] slots_top = new int[] { 1 };
	//private static final int[] slots_bottom = new int[] { 0, 2, 4, 6, 8, 10, 11};
	//private static final int[] slots_side = new int[] { 0, 3, 5, 7, 9 };
	
	private String customName;
	
	public TileEntityMachineRefinery() {
		super(12);
		tanks = new FluidTank[5];
		tankTypes = new Fluid[] {ModForgeFluids.hotoil, ModForgeFluids.heavyoil, ModForgeFluids.naphtha, ModForgeFluids.lightoil, ModForgeFluids.petroleum};
		tanks[0] = new FluidTank(64000);
		tanks[1] = new FluidTank(24000);
		tanks[2] = new FluidTank(24000);
		tanks[3] = new FluidTank(24000);
		tanks[4] = new FluidTank(24000);
	}
	
	public String getName() {
		return "container.machineRefinery";
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("f")) {
            this.tankTypes[0] = FluidRegistry.getFluid(nbt.getString("f"));
        }
		power = nbt.getLong("power");
		itemOutputTimer = nbt.getInteger("itemOutputTimer");
		if(nbt.hasKey("tanks"))
			FFUtils.deserializeTankArray(nbt.getTagList("tanks", 10), tanks);
		super.readFromNBT(nbt);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if(tankTypes[0] != null){
			nbt.setString("f", tankTypes[0].getName());
		} else {
			if(tanks[0].getFluid() != null){
				nbt.setString("f", tanks[0].getFluid().getFluid().getName());
			}
		}
		nbt.setLong("power", power);
		nbt.setInteger("itemOutputTimer", itemOutputTimer);
		nbt.setTag("tanks", FFUtils.serializeTankArray(tanks));
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void update() {
		if (!world.isRemote) {
			if(needsUpdate){
				needsUpdate = false;
			}
			this.updateConnections();
			PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos.getX(), pos.getY(), pos.getZ(), new FluidTank[] {tanks[0], tanks[1], tanks[2], tanks[3], tanks[4]}), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 20));
			power = Library.chargeTEFromItems(inventory, 0, power, maxPower);

			age++;
			if(age >= 20)
			{
				age = 0;
			}
			
			if(age == 1 || age == 11) {
				fillFluidInit(tanks[1]);
			}
			if(age == 2 || age == 12){
				fillFluidInit(tanks[2]);
			}
			if(age == 3 || age == 13){
				fillFluidInit(tanks[3]);
			}
			if(age == 4 || age == 14){
				fillFluidInit(tanks[4]);
			}
			if(this.inputValidForTank(0, 1)) //checking if the containers fluid has a recipe
				FFUtils.fillFromFluidContainer(inventory, tanks[0], 1, 2);
			
			refine();
			
			FFUtils.fillFluidContainer(inventory, tanks[1], 3, 4);
			FFUtils.fillFluidContainer(inventory, tanks[2], 5, 6);
			FFUtils.fillFluidContainer(inventory, tanks[3], 7, 8);
			FFUtils.fillFluidContainer(inventory, tanks[4], 9, 10);

			detectAndSendChanges();
		}
	}

	private void updateConnections() {
		this.trySubscribe(world, pos.add(2, 0, 1), Library.POS_X);
		this.trySubscribe(world, pos.add(2, 0, -1), Library.POS_X);
		this.trySubscribe(world, pos.add(-2, 0, 1), Library.NEG_X);
		this.trySubscribe(world, pos.add(-2, 0, -1), Library.NEG_X);
		this.trySubscribe(world, pos.add(1, 0, 2), Library.POS_Z);
		this.trySubscribe(world, pos.add(-1, 0, 2), Library.POS_Z);
		this.trySubscribe(world, pos.add(1, 0, -2), Library.NEG_Z);
		this.trySubscribe(world, pos.add(-1, 0, -2), Library.NEG_Z);
	}

	private void setupTanks(FluidStack[] fluids){
		if(fluids != null){
			setTankType(1, fluids[0].getFluid());
			setTankType(2, fluids[1].getFluid());
			setTankType(3, fluids[2].getFluid());
			setTankType(4, fluids[3].getFluid());
		}
	}

	public void setTankType(int idx, Fluid type){
		if(tankTypes[idx] != type){
			tankTypes[idx] = type;
			if(type != null){
				tanks[idx].setFluid(new FluidStack(type, 0));
			}else {
				tanks[idx].setFluid(null);
			}
		}
	}

	private void refine(){
		Pair<FluidStack[], ItemStack> recipe = RefineryRecipes.getRecipe(tankTypes[0]);
		FluidStack[] outputFluids = recipe.getKey();
		ItemStack outputItem = recipe.getValue();
		setupTanks(outputFluids);
		
		if(power >= 5 && tanks[0].getFluidAmount() >= 100 &&
				tanks[1].getFluidAmount() + outputFluids[0].amount <= tanks[1].getCapacity() && 
				tanks[2].getFluidAmount() + outputFluids[1].amount <= tanks[2].getCapacity() && 
				tanks[3].getFluidAmount() + outputFluids[2].amount <= tanks[3].getCapacity() && 
				tanks[4].getFluidAmount() + outputFluids[3].amount <= tanks[4].getCapacity()) {

			tanks[0].drain(100, true);
			tanks[1].fill(outputFluids[0].copy(), true);
			tanks[2].fill(outputFluids[1].copy(), true);
			tanks[3].fill(outputFluids[2].copy(), true);
			tanks[4].fill(outputFluids[3].copy(), true);
			itemOutputTimer += 1;
			power -= 5;
			needsUpdate = true;
		}

		if(itemOutputTimer >= totalItemTime) {
			if(inventory.getStackInSlot(11).isEmpty()) {
				inventory.setStackInSlot(11, outputItem.copy());
				itemOutputTimer = 0;
			} else if(!inventory.getStackInSlot(11).isEmpty() && inventory.getStackInSlot(11).getItem() == outputItem.getItem() && inventory.getStackInSlot(11).getCount() < inventory.getStackInSlot(11).getMaxStackSize()) {
				inventory.getStackInSlot(11).grow(1);
				itemOutputTimer = 0;
			}
		}
	}
	
	private long detectPower;
	private FluidTank[] detectTanks = new FluidTank[]{null, null, null, null, null};
	
	private void detectAndSendChanges() {
		boolean mark = false;
		if(detectPower != power){
			mark = true;
			detectPower = power;
		}
		if(!FFUtils.areTanksEqual(tanks[0], detectTanks[0])){
			mark = true;
			needsUpdate = true;
			detectTanks[0] = FFUtils.copyTank(tanks[0]);
		}
		if(!FFUtils.areTanksEqual(tanks[1], detectTanks[1])){
			mark = true;
			needsUpdate = true;
			detectTanks[1] = FFUtils.copyTank(tanks[1]);
		}
		if(!FFUtils.areTanksEqual(tanks[2], detectTanks[2])){
			mark = true;
			needsUpdate = true;
			detectTanks[2] = FFUtils.copyTank(tanks[2]);
		}
		if(!FFUtils.areTanksEqual(tanks[3], detectTanks[3])){
			mark = true;
			needsUpdate = true;
			detectTanks[3] = FFUtils.copyTank(tanks[3]);
		}
		if(!FFUtils.areTanksEqual(tanks[4], detectTanks[4])){
			mark = true;
			needsUpdate = true;
			detectTanks[4] = FFUtils.copyTank(tanks[4]);
		}
		PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos.getX(), pos.getY(), pos.getZ(), power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 20));
		if(mark)
			markDirty();
	}

	protected boolean inputValidForTank(int tank, int slot){
		
		if(!inventory.getStackInSlot(slot).isEmpty()){
			FluidStack containerFluid = FluidUtil.getFluidContained(inventory.getStackInSlot(slot));
			if(containerFluid != null){
				if(RefineryRecipes.getRecipe(containerFluid.getFluid()) != null){
					setTankType(tank, containerFluid.getFluid());
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(EnumFacing e){
		return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int amount) {
		return i==2 || i==4 || i==6 || i==8 || i==10 || i==11;
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
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
	
	public void fillFluidInit(FluidTank tank) {
		FFUtils.fillFluid(this, tank, world, pos.add(1, 0, -2), 2000);
		FFUtils.fillFluid(this, tank, world, pos.add(1, 0, 2), 2000);
		FFUtils.fillFluid(this, tank, world, pos.add(-1, 0, -2), 2000);
		FFUtils.fillFluid(this, tank, world, pos.add(-1, 0, 2), 2000);
		
		FFUtils.fillFluid(this, tank, world, pos.add(-2, 0, 1), 2000);
		FFUtils.fillFluid(this, tank, world, pos.add(2, 0, 1), 2000);
		FFUtils.fillFluid(this, tank, world, pos.add(-2, 0, -1), 2000);
		FFUtils.fillFluid(this, tank, world, pos.add(2, 0, -1), 2000);
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
		return new IFluidTankProperties[]{tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0], tanks[2].getTankProperties()[0], tanks[3].getTankProperties()[0], tanks[4].getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(resource == null)
			return 0;
		if(tankTypes[0] != null && resource.getFluid() == tankTypes[0]) {
			return tanks[0].fill(resource, doFill);
		}
		if(tanks[0].getFluidAmount() == 0 && RefineryRecipes.getRecipe(resource.getFluid()) != null){
			tankTypes[0] = resource.getFluid();
			this.markDirty();
			return tanks[0].fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(resource == null)
			return null;
		if (resource.isFluidEqual(tanks[1].getFluid())) {
			return tanks[1].drain(resource.amount, doDrain);
		}
		if (resource.isFluidEqual(tanks[2].getFluid())) {
			return tanks[2].drain(resource.amount, doDrain);
		}
		if (resource.isFluidEqual(tanks[3].getFluid())) {
			return tanks[3].drain(resource.amount, doDrain);
		}
		if (resource.isFluidEqual(tanks[4].getFluid())) {
			return tanks[4].drain(resource.amount, doDrain);
		}
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if (tanks[1].getFluid() != null) {
			return tanks[1].drain(maxDrain, doDrain);
		} else if(tanks[2].getFluid() != null){
			return tanks[2].drain(maxDrain, doDrain);
		} else if(tanks[3].getFluid() != null){
			return tanks[3].drain(maxDrain, doDrain);
		} else if(tanks[4].getFluid() != null){
			return tanks[4].drain(maxDrain, doDrain);
		}
		return null;
	}

	@Override
	public void recievePacket(NBTTagCompound[] tags) {
		if(tags.length != 5){
			return;
		} else {
			tanks[0].readFromNBT(tags[0]);
			tanks[1].readFromNBT(tags[1]);
			tanks[2].readFromNBT(tags[2]);
			tanks[3].readFromNBT(tags[3]);
			tanks[4].readFromNBT(tags[4]);
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		} else {
			return super.getCapability(capability, facing);
		}
	}
}
