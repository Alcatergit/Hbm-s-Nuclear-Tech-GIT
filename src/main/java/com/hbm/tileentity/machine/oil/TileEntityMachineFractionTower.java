package com.hbm.tileentity.machine.oil;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.RefineryRecipes;
import com.hbm.lib.ForgeDirection;
import com.hbm.util.Tuple.Quartet;
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

public class TileEntityMachineFractionTower extends TileEntity  implements INBTPacketReceiver, ITickable, IFluidHandler {
	
	public FluidTank[] tanks;
	public Fluid[] types;
	
	public TileEntityMachineFractionTower() {
		super();
		
		tanks = new FluidTank[3];
		types = new Fluid[3];
		types[0] = ModForgeFluids.heavyoil;
		types[1] = ModForgeFluids.bitumen;
		types[2] = ModForgeFluids.smear;
		tanks[0] = new FluidTank(ModForgeFluids.heavyoil, 0, 4000);
		tanks[1] = new FluidTank(ModForgeFluids.bitumen, 0, 4000);
		tanks[2] = new FluidTank(ModForgeFluids.smear, 0, 4000);
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
			
			TileEntity stack = world.getTileEntity(pos.up(3));
			
			
			if(stack instanceof TileEntityMachineFractionTower) {
				TileEntityMachineFractionTower frac = (TileEntityMachineFractionTower) stack;
				
				//make types equal
				for(int i = 0; i < 3; i++) {
					frac.setTankType(i, types[i]);
				}
				
				//calculate transfer
				int oil = Math.min(tanks[0].getFluidAmount(), frac.tanks[0].getCapacity() - frac.tanks[0].getFluidAmount());
				int left = Math.min(frac.tanks[1].getFluidAmount(), tanks[1].getCapacity() - tanks[1].getFluidAmount());
				int right = Math.min(frac.tanks[2].getFluidAmount(), tanks[2].getCapacity() - tanks[2].getFluidAmount());
				
				//move oil up, pull fractions down
				tanks[0].drain(oil, true);
				tanks[1].fill(new FluidStack(types[1], left), true);
				tanks[2].fill(new FluidStack(types[2], right), true);
				frac.tanks[0].fill(new FluidStack(frac.types[0], oil), true);
				frac.tanks[1].drain(left, true);
				frac.tanks[2].drain(right, true);
			}
			
			setupTanks();
			
			if(world.getTotalWorldTime() % 20 == 0)
				fractionate();
			
			if(world.getTotalWorldTime() % 10 == 0) {
				fillFluidInit(tanks[1]);
				fillFluidInit(tanks[2]);
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
		
		Quartet<Fluid, Fluid, Integer, Integer> quart = RefineryRecipes.getFractions(types[0]);
		
		if(quart != null) {
			setTankType(1, quart.getW());
			setTankType(2, quart.getX());
		}
	}
	
	private void fractionate() {
		
		Quartet<Fluid, Fluid, Integer, Integer> quart = RefineryRecipes.getFractions(types[0]);
		
		if(quart != null) {
			
			int left = quart.getY();
			int right = quart.getZ();
			
			if(tanks[0].getFluidAmount() >= 100 && hasSpace(left, right)) {
				tanks[0].drain(100, true);
				tanks[1].fill(new FluidStack(types[1], left), true);
				tanks[2].fill(new FluidStack(types[2], right), true);
			}
		}
	}
	
	private boolean hasSpace(int left, int right) {
		return tanks[1].getFluidAmount() + left <= tanks[1].getCapacity() && tanks[2].getFluidAmount() + right <= tanks[2].getCapacity();
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
		for(int i = 2; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			fillFluid(pos.getX() + dir.offsetX * 2, pos.getY(), pos.getZ() + dir.offsetZ * 2, tank);
		}
	}

	public void fillFluid(int x, int y, int z, FluidTank tank) {
		FFUtils.fillFluid(this, tank, world, new BlockPos(x, y, z), tank.getCapacity());
	}

	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = new AxisAlignedBB(
					pos.getX() - 1,
					pos.getY(),
					pos.getZ() - 1,
					pos.getX() + 2,
					pos.getY() + 3,
					pos.getZ() + 2
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
		return new IFluidTankProperties[]{tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0], tanks[2].getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill){
		if(resource != null && resource.getFluid() == types[0])
			return tanks[0].fill(resource, doFill);
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain){
		FluidStack drain = null;
		if(resource.getFluid() == types[1])
			drain = tanks[1].drain(resource, doDrain);
		if(resource.getFluid() == types[2])
			drain = tanks[2].drain(resource, doDrain);
		return drain;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain){
		FluidStack drain = tanks[1].drain(maxDrain, doDrain);
		if(drain == null)
			drain = tanks[2].drain(maxDrain, doDrain);
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