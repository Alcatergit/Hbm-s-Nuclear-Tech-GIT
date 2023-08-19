package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.StorageDrumRecipes;
import com.hbm.interfaces.IItemHazard;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.items.ModItems;

import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.saveddata.RadiationSavedData;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class TileEntityStorageDrum extends TileEntityMachineBase implements ITickable, IFluidHandler, ITankPacketAcceptor {

	public FluidTank[] tanks;
	private static final int[] slots_arr = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 };
	public int age = 0;

	private static final float decayRate = 0.9965402628F; //10s Halflife

	public TileEntityStorageDrum() {
		super(24, 1);
		tanks = new FluidTank[2];
		//wastefluid
		tanks[0] = new FluidTank(16000);
		//wastegas
		tanks[1] = new FluidTank(16000);
	}

	@Override
	public String getName() {
		return "container.storageDrum";
	}

	@Override
	public void update() {
		
		if(!world.isRemote) {
			
			float rad = 0;
			
			int liquid = 0;
			int gas = 0;
			
			for(int i = 0; i < 24; i++) {
				
				if(!inventory.getStackInSlot(i).isEmpty()) {
					
					Item item = inventory.getStackInSlot(i).getItem();
					
					if(item instanceof IItemHazard && world.getTotalWorldTime() % 20 == 0) {
						rad += ((IItemHazard)item).getModule().radiation;
					}

					int[] wasteData = StorageDrumRecipes.getWaste(inventory.getStackInSlot(i));
					if(wasteData != null){
						if(world.rand.nextInt(wasteData[0]) == 0){
							ItemStack outputStack = StorageDrumRecipes.getOutput(inventory.getStackInSlot(i));
							if(outputStack != null){
								liquid += wasteData[1];
								gas += wasteData[2];
								inventory.setStackInSlot(i, outputStack.copy());
							}
						}
					} else {
						ContaminationUtil.neutronActivateItem(inventory.getStackInSlot(i), 0.0F, decayRate);
					}
				}
			}

			for(int i = 0; i < 2; i++) {
				
				int overflow = Math.max(this.tanks[i].getFluidAmount() + (i == 0 ? liquid : gas) - this.tanks[i].getCapacity(), 0);
				
				if(overflow > 0) {
					RadiationSavedData.incrementRad(world, pos, overflow * 0.5F, Float.MAX_VALUE);
				}
			}
			
			this.tanks[0].fill(new FluidStack(ModForgeFluids.wastefluid, liquid), true);
			this.tanks[1].fill(new FluidStack(ModForgeFluids.wastegas, gas), true);
			
			age++;
			
			if(age >= 20)
				age -= 20;
			
			if(age == 9 || age == 19) {
				fillFluidInit(tanks[0]);
			}
			if(age == 8 || age == 18) {
				fillFluidInit(tanks[1]);
			}

			PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos, tanks), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
			
			if(rad > 0) {
				ContaminationUtil.radiate(world, pos.getZ(), pos.getY(), pos.getX(), 32, rad);
			}
		}
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return StorageDrumRecipes.getOutput(itemStack) != null || ContaminationUtil.isContaminated(itemStack);
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return !ContaminationUtil.isContaminated(itemStack) && StorageDrumRecipes.getOutput(itemStack) == null;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(EnumFacing side) {
		return slots_arr;
	}

	public void fillFluidInit(FluidTank type) {
		fillFluid(this.pos.getX() - 1, this.pos.getY(), this.pos.getZ(), type);
		fillFluid(this.pos.getX() + 1, this.pos.getY(), this.pos.getZ(), type);
		fillFluid(this.pos.getX(), this.pos.getY() - 1, this.pos.getZ(), type);
		fillFluid(this.pos.getX(), this.pos.getY() + 1, this.pos.getZ(), type);
		fillFluid(this.pos.getX(), this.pos.getY(), this.pos.getZ() - 1, type);
		fillFluid(this.pos.getX(), this.pos.getY(), this.pos.getZ() + 1, type);
	}

	public void fillFluid(int x, int y, int z, FluidTank tank) {
		FFUtils.fillFluid(this, tank, world, new BlockPos(x, y, z), tank.getCapacity());
	}

	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tanks[0].readFromNBT(nbt.getCompoundTag("liquid"));
		this.tanks[1].readFromNBT(nbt.getCompoundTag("gas"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setTag("liquid", this.tanks[0].writeToNBT(new NBTTagCompound()));
		nbt.setTag("gas", this.tanks[1].writeToNBT(new NBTTagCompound()));
		return nbt;
	}

	@Override
	public IFluidTankProperties[] getTankProperties(){
		return new IFluidTankProperties[]{tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill){
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain){
		FluidStack stack = tanks[0].drain(resource, doDrain);
		if(stack == null)
			stack = tanks[1].drain(resource, doDrain);
		return stack;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain){
		FluidStack stack = tanks[0].drain(maxDrain, doDrain);
		if(stack == null)
			stack = tanks[1].drain(maxDrain, doDrain);
		return stack;
	}

	@Override
	public void recievePacket(NBTTagCompound[] tags){
		if(tags.length == 2){
			tanks[0].readFromNBT(tags[0]);
			tanks[1].readFromNBT(tags[1]);
		}
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing){
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing){
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
}