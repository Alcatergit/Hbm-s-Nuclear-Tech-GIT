package com.hbm.tileentity.machine.oil;

import com.hbm.blocks.BlockDummyable;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.CrackRecipes;
import com.hbm.lib.ForgeDirection;
import com.hbm.tileentity.INBTPacketReceiver;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMachineCatalyticCracker extends TileEntity implements INBTPacketReceiver, ITickable, IFluidHandler {
	
	public FluidTank[] tanks;
	public Fluid[] types;
	
	public TileEntityMachineCatalyticCracker() {
		super();
		tanks = new FluidTank[5];
		types = new Fluid[5];

		tanks[0] = new FluidTank(ModForgeFluids.bitumen, 0, 4000);
		types[0] = ModForgeFluids.bitumen;

		tanks[1] = new FluidTank(ModForgeFluids.steam, 0, 8000);
		types[1] = ModForgeFluids.steam;

		tanks[2] = new FluidTank(ModForgeFluids.oil, 0, 4000);
		types[2] = ModForgeFluids.oil;

		tanks[3] = new FluidTank(ModForgeFluids.aromatics, 0, 4000);
		types[3] = ModForgeFluids.aromatics;

		tanks[4] = new FluidTank(ModForgeFluids.spentsteam, 0, 4000);
		types[4] = ModForgeFluids.spentsteam;
	}
	
	public void setTankType(int idx, Fluid type){
		if(types[idx] != type){
			types[idx] = type;
			if(type != null){
				tanks[idx].setFluid(new FluidStack(type, 0));
			}else {
				tanks[idx].setFluid(null);
			}
		}
	}
	
	@Override
	public void update() {

		if(!world.isRemote) {
			setupTanks();
			
			if(world.getTotalWorldTime() % 20 == 0)
				crack();
			
			if(world.getTotalWorldTime() % 10 == 0) {
				fillFluidInit(tanks[2]);
				fillFluidInit(tanks[3]);
				fillFluidInit(tanks[4]);
				networkPack();
			}
		}
	}

	public void networkPack(){
		NBTTagCompound data = new NBTTagCompound();
		for(int i=0; i<tanks.length; i++){
			if(types[i] != null){
				tanks[i].setFluid(new FluidStack(types[i], tanks[i].getFluidAmount()));
			} else {
				tanks[i].setFluid(null);
			}
		}
		data.setTag("tanks", FFUtils.serializeTankArray(tanks));
		INBTPacketReceiver.networkPack(this, data, 25);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		FFUtils.deserializeTankArray(nbt.getTagList("tanks", 10), tanks);
		for(int i=0; i<tanks.length; i++){
			if(tanks[i].getFluid() != null){
				types[i] = tanks[i].getFluid().getFluid();
			} else {
				types[i] = null;
			}
		}
	}
	
	private void setupTanks() {
		
		FluidStack[] fluids = CrackRecipes.getOutputsFromFluid(types[0]);
		
		if(fluids != null) {
			setTankType(1, ModForgeFluids.steam);
			setTankType(2, fluids[0].getFluid());
			if(fluids.length == 2){
				setTankType(3, fluids[1].getFluid());
				setTankType(4, ModForgeFluids.spentsteam);
			} else {
				setTankType(3, ModForgeFluids.spentsteam);
				setTankType(4, null);
			}
		} else {
			setTankType(0, null);
			setTankType(1, null);
			setTankType(2, null);
			setTankType(3, null);
			setTankType(4, null);
		}
	}
	
	private void crack() {
		
		FluidStack[] outputFluids = CrackRecipes.getOutputsFromFluid(types[0]);
		
		if(outputFluids != null) {
			
			while(tanks[0].getFluidAmount() >= 100 && tanks[1].getFluidAmount() >= 200 && hasSpace(outputFluids)) {
				tanks[0].drain(100, true);
				tanks[1].drain(200, true);
				if(outputFluids.length == 2){
					tanks[2].fill(outputFluids[0].copy(), true);
					tanks[3].fill(outputFluids[1].copy(), true);
					tanks[4].fill(new FluidStack(ModForgeFluids.spentsteam, 2), true); //LPS has the density of WATER not STEAM (1%!)
				} else {
					tanks[2].fill(outputFluids[0].copy(), true);
					tanks[3].fill(new FluidStack(ModForgeFluids.spentsteam, 2), true); //LPS has the density of WATER not STEAM (1%!)
				}
			}
		}
	}
	
	private boolean hasSpace(FluidStack[] outputFluids) {
		if(outputFluids.length == 2){
			return tanks[2].getFluidAmount() + outputFluids[0].amount <= tanks[2].getCapacity() && tanks[3].getFluidAmount() + outputFluids[1].amount <= tanks[3].getCapacity() && tanks[4].getFluidAmount() + 2 <= tanks[4].getCapacity();
		}else{
			return tanks[2].getFluidAmount() + outputFluids[0].amount <= tanks[2].getCapacity() && tanks[3].getFluidAmount() + 2 <= tanks[3].getCapacity();
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		FFUtils.deserializeTankArray(nbt.getTagList("tanks", 10), tanks);
		for(int i=0; i<tanks.length; i++){
			if(tanks[i].getFluid() != null){
				types[i] = tanks[i].getFluid().getFluid();
			} else {
				types[i] = null;
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		for(int i=0; i<tanks.length; i++){
			if(types[i] != null){
				tanks[i].setFluid(new FluidStack(types[i], tanks[i].getFluidAmount()));
			} else {
				tanks[i].setFluid(null);
			}
		}
		nbt.setTag("tanks", FFUtils.serializeTankArray(tanks));
		return nbt;
	}

	public void fillFluidInit(FluidTank tank) {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		fillFluid(pos.getX() + dir.offsetX * 4 + rot.offsetX * 1, pos.getY(), pos.getZ() + dir.offsetZ * 4 + rot.offsetZ * 1, tank);
		fillFluid(pos.getX() + dir.offsetX * 4 - rot.offsetX * 2, pos.getY(), pos.getZ() + dir.offsetZ * 4 - rot.offsetZ * 2, tank);
		fillFluid(pos.getX() - dir.offsetX * 4 + rot.offsetX * 1, pos.getY(), pos.getZ() - dir.offsetZ * 4 + rot.offsetZ * 1, tank);
		fillFluid(pos.getX() - dir.offsetX * 4 - rot.offsetX * 2, pos.getY(), pos.getZ() - dir.offsetZ * 4 - rot.offsetZ * 2, tank);

		fillFluid(pos.getX() + dir.offsetX * 2 + rot.offsetX * 3, pos.getY(), pos.getZ() + dir.offsetZ * 2 + rot.offsetZ * 3, tank);
		fillFluid(pos.getX() + dir.offsetX * 2 - rot.offsetX * 4, pos.getY(), pos.getZ() + dir.offsetZ * 2 - rot.offsetZ * 4, tank);
		fillFluid(pos.getX() - dir.offsetX * 2 + rot.offsetX * 3, pos.getY(), pos.getZ() - dir.offsetZ * 2 + rot.offsetZ * 3, tank);
		fillFluid(pos.getX() - dir.offsetX * 2 - rot.offsetX * 4, pos.getY(), pos.getZ() - dir.offsetZ * 2 - rot.offsetZ * 4, tank);
	}

	public void fillFluid(int x, int y, int z, FluidTank tank) {
		FFUtils.fillFluid(this, tank, world, new BlockPos(x, y, z), tank.getCapacity());
	}

	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = new AxisAlignedBB(
				pos.getX() - 3,
				pos.getY(),
				pos.getZ() - 3,
				pos.getX() + 4,
				pos.getY() + 16,
				pos.getZ() + 4
				);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public IFluidTankProperties[] getTankProperties(){
		return new IFluidTankProperties[]{tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0], tanks[2].getTankProperties()[0], tanks[3].getTankProperties()[0], tanks[4].getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill){
		if(resource != null){
			if(resource.getFluid() == types[0])
				return tanks[0].fill(resource, doFill);
			if(resource.getFluid() == types[1])
				return tanks[1].fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain){
		FluidStack drain = null;
		if(resource.getFluid() == types[2])
			drain = tanks[2].drain(resource, doDrain);
		if(resource.getFluid() == types[3])
			drain = tanks[3].drain(resource, doDrain);
		if(resource.getFluid() == types[4])
			drain = tanks[4].drain(resource, doDrain);
		return drain;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain){
		FluidStack drain = tanks[2].drain(maxDrain, doDrain);
		if(drain == null)
			drain = tanks[3].drain(maxDrain, doDrain);
		if(drain == null)
			drain = tanks[4].drain(maxDrain, doDrain);
		return drain;
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
