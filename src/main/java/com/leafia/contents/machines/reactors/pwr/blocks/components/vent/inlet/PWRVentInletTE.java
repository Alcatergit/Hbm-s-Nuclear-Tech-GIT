package com.leafia.contents.machines.reactors.pwr.blocks.components.vent.inlet;

import com.hbm.forgefluid.ModForgeFluids;
import com.leafia.contents.machines.reactors.pwr.blocks.components.element.PWRElementBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.element.PWRElementTE;
import com.leafia.contents.machines.reactors.pwr.blocks.components.vent.PWRVentBlockBase;
import com.leafia.contents.machines.reactors.pwr.blocks.components.vent.element.PWRVentElementBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.vent.element.PWRVentElementTE;
import com.leafia.contents.machines.reactors.pwr.blocks.components.vent.outlet.PWRVentOutletBlock;
import com.leafia.dev.LeafiaDebug;
import com.leafia.dev.LeafiaDebug.Tracker;
import com.leafia.dev.LeafiaUtil;
import com.llib.group.LeafiaMap;
import com.llib.group.LeafiaSet;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;
import java.util.Map.Entry;

public class PWRVentInletTE extends TileEntity implements IFluidHandler, ITickable {
	public static LeafiaMap<PWRVentInletTE,LeafiaSet<BlockPos>> listeners = new LeafiaMap<>();
	LeafiaSet<BlockPos> listenPositions() {
		if (this.isInvalid()) return new LeafiaSet<>(); // dummy set
		if (!listeners.containsKey(this)) {
			listeners.put(this,new LeafiaSet<>());
		}
		return listeners.get(this);
	}
	static double consumptionScale = 1;
	static class DemandStream {
		final PWRElementTE element;
		double multiplier = 0;
		int accumTicks = 0;
		int accumDemand = 0;
		int lastTicks = 0;
		int ready = 0;
		double accumCool = 0; // my brain exploded so i became lazy now
		double cool = 0; // my brain exploded so i became lazy now
		int spentTicks = 0;
		public DemandStream(PWRElementTE element) {
			this.element = element;
		}
		/*public int spend(int demand) {
			waiting += demand;
			waitedTicks++;
			int spent = Math.min(ready,(int)Math.ceil(ready/Math.max(ticksTook,1d)));
			ready -= spent;
			return spent;
		}*/
	}
	LeafiaMap<PWRElementTE,DemandStream> streams = new LeafiaMap<>();
	int lastLength = 0;
	boolean valid = false;
	public void rebuildMap() {
		Tracker._startProfile(this,"rebuildMap");
		LeafiaSet<DemandStream> addedStreams = new LeafiaSet<>();
		IBlockState state = world.getBlockState(pos);
		EnumFacing face = state.getValue(PWRVentBlockBase.FACING);
		int length = 0;
		BlockPos pos1 = pos.offset(face);
		valid = false;
		while (world.isValid(pos1)) {
			if (world.getBlockState(pos1).getBlock() instanceof PWRVentOutletBlock) {
				if (world.getBlockState(pos1).getValue(PWRVentBlockBase.FACING).equals(face)) {
					valid = true;
				}
			}
			if (!(world.getBlockState(pos1).getBlock() instanceof PWRVentElementBlock))
				break;
			length++;
			pos1 = pos1.offset(face);
		}
		length = valid ? length : 0;
		boolean updateListen = lastLength != length;
		if (updateListen)
			listenPositions().clear();
		lastLength = length;
		EnumFacing dirA = EnumFacing.EAST;
		EnumFacing dirB = EnumFacing.SOUTH;
		if (face.getAxis().equals(Axis.X))
			dirA = EnumFacing.UP;
		else if (face.getAxis().equals(Axis.Z))
			dirB = EnumFacing.UP;
		for (int row = 0; row < length; row++) {
			for (int a = -7; a <= 7; a++) {
				for (int b = -7; b <= 7; b++) {
					BlockPos pos2 = pos.offset(face,row+1).offset(dirA,a).offset(dirB,b);
					double distanceMultiplier = Math.pow(MathHelper.clampedLerp(1,0,Math.sqrt(a*a+b*b)/7),0.5);
					Tracker._tracePosition(this,pos2,distanceMultiplier);
					if (!world.isValid(pos2)) continue;
					if (updateListen)
						listenPositions().add(pos2);
					if (world.getBlockState(pos2).getBlock() instanceof PWRElementBlock) {
						PWRElementBlock machine = (PWRElementBlock)world.getBlockState(pos2).getBlock();
						TileEntity entity = world.getTileEntity(machine.getTopElement(world,pos2));
						if (entity instanceof PWRElementTE) {
							PWRElementTE element = (PWRElementTE)entity;
							DemandStream stream = streams.get(element);
							LeafiaDebug.debugLog(world,"ELEMENT FOUND");
							if (!streams.containsKey(element)) {
								stream = new DemandStream(element);
								streams.put(element,stream);
							}
							if (!addedStreams.contains(stream)) {
								stream.multiplier = 1;
								addedStreams.add(stream);
							}
							stream.multiplier += distanceMultiplier;
						}
					}
				}
			}
		}
		for (Entry<PWRElementTE,DemandStream> entry : streams.entrySet()) {
			if (!addedStreams.contains(entry.getValue()) || entry.getKey().isInvalid())
				streams.remove(entry.getKey());
		}
		Tracker._endProfile(this);
	}
	protected void removeInvalidStreams() {
		for (PWRElementTE element : streams.keySet()) {
			if (element.isInvalid())
				streams.remove(element);
		}
	}
	Fluid fluid = ModForgeFluids.CRYOGEL;
	IFluidTankProperties inlet = new IFluidTankProperties() {
		@Nullable @Override
		public FluidStack getContents() { return null; }
		@Override public int getCapacity() { return getDemandAmount(); }
		@Override public boolean canFill() { return true; }
		@Override public boolean canDrain() { return false; }
		@Override public boolean canFillFluidType(FluidStack fluidStack) { return fluidStack.getFluid().equals(fluid); }
		@Override public boolean canDrainFluidType(FluidStack fluidStack) { return false; }
	};
	public int getDemandAmount() {
		removeInvalidStreams();
		int amt = 0;
		for (DemandStream stream : streams.values()) {
			if (stream.spentTicks >= stream.lastTicks)
				amt += stream.accumDemand;
		}
		return amt;
	}
	IFluidTankProperties[] inletArray = new IFluidTankProperties[]{inlet};
	@Override public IFluidTankProperties[] getTankProperties() { return inletArray; }
	@Override
	public int fill(FluidStack resource,boolean doFill) {
		if (!inlet.canFillFluidType(resource)) return 0;
		int demand = getDemandAmount();
		int filled = Math.min(demand,resource.amount);
		int spent = 0;
		LeafiaDebug.debugLog(world,"demand: "+demand+"mB");
		if (doFill) {
			float scale = filled/(float)demand;
			for (DemandStream stream : streams.values()) {
				if (stream.spentTicks >= stream.lastTicks) {
					stream.spentTicks = 0;
					stream.lastTicks = stream.accumTicks;
					int curSpent = (int)Math.floor(stream.accumDemand*scale);
					stream.ready += curSpent;
					stream.cool += stream.accumCool*scale;
					spent += curSpent;
					stream.accumDemand = 0;
					stream.accumCool = 0;
				}
				stream.accumTicks = 0;
			}
			//filled = spent;
		}
		//if (doFill)
		//	demand -= filled;
		//idfk what is this so fuck it
		IFluidTank virtualTank = new FluidTank(fluid,filled,filled);
		FluidEvent.fireEvent(new FluidEvent.FluidFillingEvent(new FluidStack(resource,filled),world,getPos(),virtualTank,filled));
		return filled;
	}
	@Nullable @Override public FluidStack drain(FluidStack resource,boolean doDrain) { return null; }
	@Nullable @Override public FluidStack drain(int maxDrain,boolean doDrain) { return null; }
	boolean checked = false;
	@Override
	public void update() {
		IBlockState state = world.getBlockState(pos);
		EnumFacing face = state.getValue(PWRVentBlockBase.FACING);

		TileEntity teBack = world.getTileEntity(pos.offset(face.getOpposite()));
		if (teBack instanceof PWRVentDuctTE) {
			PWRVentDuctTE dummy = (PWRVentDuctTE)teBack;
			if (dummy.listenersShouldUpdate) {
				fluid = dummy.getType();
			}
		}
		if (!world.isRemote) {
			if (!checked) { /// initial generation
				checked = true;
				rebuildMap();
			}
			Tracker._startProfile(this,"update");
			// for vent scan
			EnumFacing dirA = EnumFacing.EAST;
			EnumFacing dirB = EnumFacing.SOUTH;
			if (face.getAxis().equals(Axis.X))
				dirA = EnumFacing.UP;
			else if (face.getAxis().equals(Axis.Z))
				dirB = EnumFacing.UP;

			// for cooling rate calculation
			double baseTemp = 293.15;
			double exponent = 0.1;
			double tempScale = baseTemp/300; // this script expects 20*C as normal temperature, while minecraft water is 26.85, so do some scaling here
			double desired = fluid.getTemperature()
					*tempScale
					-273.15;
			double capacity = Math.pow(273.15+desired,exponent)-Math.pow(baseTemp,exponent);
			double baseMultiplier = 1+Math.abs(capacity)*5;
			double ventRatio = 10;

			// main process
			removeInvalidStreams();
			for (DemandStream stream : streams.values()) {
				double heat = stream.element.getHeat();
				if (stream.accumTicks < 50) {
					stream.accumTicks++;
					double difference = heat-desired;

					BlockPos ventPos = pos.offset(face,lastLength+2+world.rand.nextInt(6))
							.offset(dirA,world.rand.nextInt(5)-2)
							.offset(dirB,world.rand.nextInt(5)-2);

					double ventFluid = 20;
					double transferMultiplier = 0.25;
					if (world.isValid(ventPos)) {
						IBlockState ventState = world.getBlockState(ventPos);
						Block ventBlock = ventState.getBlock();
						if (ventBlock instanceof BlockFluidBase) {
							BlockFluidBase blockFluid = (BlockFluidBase)ventBlock;
							ventFluid = blockFluid.getFluid().getTemperature()*tempScale-273.15;
							if (!blockFluid.getFluid().isGaseous()) transferMultiplier = 1;
						} else {
							if (LeafiaUtil.isSolidVisibleCube(ventState))
								transferMultiplier = 0; // fuck you
						}
					}
					double baseCooled = ventRatio*baseMultiplier*Math.signum(difference);
					double ventDifference = (20+baseCooled)-ventFluid;
					double baseRate = (baseCooled+Math.pow(Math.abs(ventDifference)/20,exponent)*20*Math.signum(ventDifference)*ventRatio)/ventRatio;
					double rate = baseRate*transferMultiplier;

					double scaledDifference = Math.pow(Math.abs(difference)+1,0.25)-1;

					double scaled = scaledDifference*rate; //(Math.pow(Math.abs(difference)+1,0.5)-1)*Math.signum(difference)*Math.abs(baseRate)/100;
					double fn = scaled*stream.multiplier/stream.element.getHeight();

					stream.accumDemand += (int)(scaledDifference*consumptionScale);
					stream.accumCool += fn;
					Tracker._tracePosition(this,stream.element.getPos(),"desired: "+desired,"capacity: "+capacity,"baseCooled: "+baseCooled,"ventDifference: "+ventDifference,"baseRate: "+baseRate,"rate: "+rate,"cooled: "+scaled);
				}
				int consumption = (int)Math.min(Math.ceil(stream.ready/Math.max(stream.lastTicks,1d)),stream.ready);
				stream.ready -= consumption;
				stream.spentTicks++;
				// my brain got exploded so I became lazy now VV
				double cooling = Math.min(stream.cool/Math.max(stream.lastTicks,1d),stream.cool);
				stream.cool -= cooling;
				stream.element.setHeat(stream.element.getHeat()-cooling);
			}
			Tracker._endProfile(this);
			//if (state.getBlock() instanceof MachinePWRVentInlet) {
			//	MachinePWRVentInlet vent = (MachinePWRVentInlet)state.getBlock();
			//}
		} else {
			BlockPos pos1 = pos.offset(face);
			while (world.isValid(pos1)) {
				if (world.getBlockState(pos1).getBlock() instanceof PWRVentElementBlock) {
					TileEntity te = world.getTileEntity(pos1);
					if (te != null) {
						PWRVentElementTE element = (PWRVentElementTE)te;
						element.fluid = this.fluid;
					}
					pos1 = pos1.offset(face);
				} else
					break;
			}
		}
	}
	@Override
	public <T> T getCapability(Capability<T> capability,EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		return super.getCapability(capability, facing);
	}
	@Override
	public boolean hasCapability(Capability<?> capability,@Nullable EnumFacing facing) {
		IBlockState state = world.getBlockState(pos);
		EnumFacing face = state.getValue(PWRVentBlockBase.FACING);
		return super.hasCapability(capability,facing) || ((facing == null || face.getOpposite().equals(facing)) && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
	}
}