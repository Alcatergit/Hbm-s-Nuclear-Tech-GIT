package com.hbm.forgefluid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.hbm.interfaces.IFluidPipeMk2;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class FFPipeNetworkMk2 implements IFluidHandler {

	protected Fluid type;
	protected Map<BlockPos, TileEntity> fillables = new HashMap<BlockPos, TileEntity>();
	protected Map<BlockPos, IFluidPipeMk2> pipes = new HashMap<BlockPos, IFluidPipeMk2>();

	public FFPipeNetworkMk2(IFluidPipeMk2 te) {
		this.type = te.getType();
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(resource == null || resource.getFluid() != type)
			return 0;
		List<IFluidHandler> handlers = new ArrayList<IFluidHandler>();
		List<Integer> capacities = new ArrayList<Integer>();
		int totalCapacity = 0;
		
		Iterator<TileEntity> itr = fillables.values().iterator();
		while(itr.hasNext()){
			TileEntity te = itr.next();
			if(te.isInvalid()){
				itr.remove();
				continue;
			}
			if(FFUtils.safeCheckCapa(te, CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)){
				IFluidHandler h = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
				if(h != null){
					int cap = h.fill(new FluidStack(resource.getFluid(), Integer.MAX_VALUE), false);
					if(cap > 0){
						handlers.add(h);
						capacities.add(cap);
						totalCapacity += cap;
					}
				}
			}
		}
		
		if(handlers.isEmpty())
			return 0;
		
		int totalFilled = 0;
		int remaining = resource.amount;
		int remainingCapacity = totalCapacity;
		
		for(int i = 0; i < handlers.size() && remaining > 0; i++){
			IFluidHandler consumer = handlers.get(i);
			int share;
			if(i == handlers.size() - 1){
				share = Math.min(remaining, capacities.get(i));
			} else {
				share = weightedShare(remaining, capacities.get(i), remainingCapacity, Math.min(remaining, capacities.get(i)));
			}
			if(share <= 0) {
				remainingCapacity -= capacities.get(i);
				continue;
			}
			int vol = consumer.fill(new FluidStack(resource.getFluid(), share), doFill);
			totalFilled += vol;
			remaining -= vol;
			remainingCapacity -= capacities.get(i);
		}
		
		return totalFilled;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(resource == null || resource.getFluid() != type)
			return null;
		List<IFluidHandler> providers = new ArrayList<IFluidHandler>();
		List<Integer> availabilities = new ArrayList<Integer>();
		int totalAvail = 0;
		
		Iterator<TileEntity> itr = fillables.values().iterator();
		while(itr.hasNext()){
			TileEntity te = itr.next();
			if(te.isInvalid()){
				itr.remove();
				continue;
			}
			if(FFUtils.safeCheckCapa(te, CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)){
				IFluidHandler h = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
				if(h != null){
					FluidStack test = h.drain(new FluidStack(type, Integer.MAX_VALUE), false);
					if(test != null && test.amount > 0){
						providers.add(h);
						availabilities.add(test.amount);
						totalAvail += test.amount;
					}
				}
			}
		}
		
		if(providers.isEmpty())
			return null;
		
		int totalDrained = 0;
		int remaining = resource.amount;
		int remainingAvail = totalAvail;
		
		for(int i = 0; i < providers.size() && remaining > 0; i++){
			IFluidHandler provider = providers.get(i);
			int share;
			if(i == providers.size() - 1){
				share = Math.min(remaining, availabilities.get(i));
			} else {
				share = weightedShare(remaining, availabilities.get(i), remainingAvail, Math.min(remaining, availabilities.get(i)));
			}
			if(share <= 0) {
				remainingAvail -= availabilities.get(i);
				continue;
			}
			FluidStack drained = provider.drain(new FluidStack(type, share), doDrain);
			if(drained != null){
				totalDrained += drained.amount;
				remaining -= drained.amount;
			}
			remainingAvail -= availabilities.get(i);
		}
		
		return totalDrained > 0 ? new FluidStack(type, totalDrained) : null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return null;
	}

	private static int weightedShare(int total, int part, int whole, int cap) {
		if(total <= 0 || part <= 0 || whole <= 0 || cap <= 0) return 0;
		if(part >= whole) return Math.min(total, cap);
		int share = (int)(((long)total * part) / whole);
		if(share <= 0) return 0;
		return Math.min(share, cap);
	}

	public int size() {
		return fillables.size() + pipes.size();
	}
	
	public Fluid getType() {
		return type;
	}

	public Map<BlockPos, IFluidPipeMk2> getPipePositions() {
		return pipes;
	}

	public Map<BlockPos, TileEntity> getFillableTiles() {
		return fillables;
	}

	public void destroy() {
		pipes.values().forEach(pipe -> pipe.setNetwork(null));
		pipes.clear();
		fillables.clear();
	}

	public void checkForRemoval(TileEntity te) {
		if(te == null)
			return;
		if(te instanceof IFluidPipeMk2) {
			pipes.remove(te.getPos());
		} else{
			try{
				if(FFUtils.safeCheckCapa(te, CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)) {
					fillables.remove(te.getPos());
				}
			} catch(Throwable ignored){
			}
		}
	}

	public boolean tryAdd(TileEntity te) {
		if(te == null)
			return false;
		if(te instanceof IFluidPipeMk2) {
			if(!pipes.containsKey(te.getPos()) && ((IFluidPipeMk2) te).getType() == this.type) {
				pipes.put(te.getPos(), (IFluidPipeMk2) te);
				return true;
			}
		} else if(FFUtils.safeCheckCapa(te, CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)) {
			if(!fillables.containsKey(te.getPos())) {
				fillables.put(te.getPos(), te);
				return true;
			}
		}
		return false;
	}
	
	public static FFPipeNetworkMk2 mergeNetworks(FFPipeNetworkMk2 net1, FFPipeNetworkMk2 net2) {
		if((net1 == null || net2 == null) || net1 == net2)
			return net1;

		/*net2.pipes.values().forEach(pipe -> {
			pipe.setNetwork(net1);
			pipe.setType(net1.type);
		});*/
		for(IFluidPipeMk2 pipe : net2.pipes.values()){
			pipe.setNetwork(net1);
		}

		net1.fillables.putAll(net2.fillables);
		net1.pipes.putAll(net2.pipes);

		net2.fillables.clear();
		net2.pipes.clear();

		return net1;
	}

	public static FFPipeNetworkMk2 buildNetwork(TileEntity te) {
		FFPipeNetworkMk2 net = null;
		if(te instanceof IFluidPipeMk2 pipe) {
            if(pipe.getNetwork() != null)
				return pipe.getNetwork();
			Fluid type = pipe.getType();

			Map<BlockPos, IFluidPipeMk2> pipes = new HashMap<BlockPos, IFluidPipeMk2>();
			Map<BlockPos, TileEntity> consumers = new HashMap<BlockPos, TileEntity>();
			List<FFPipeNetworkMk2> toMerge = new ArrayList<FFPipeNetworkMk2>();
			iteratePipes(pipes, consumers, toMerge, te, type);

			if(!toMerge.isEmpty())
				net = toMerge.remove(0);
			else
				net = new FFPipeNetworkMk2(pipe);
			
			while(!toMerge.isEmpty())
				mergeNetworks(net, toMerge.remove(0));
			
			for(IFluidPipeMk2 p : pipes.values())
				p.setNetwork(net);
				
			net.pipes.putAll(pipes);
			net.fillables.putAll(consumers);
			
			
		}
		return net;
	}

	public static void iteratePipes(Map<BlockPos, IFluidPipeMk2> pipes, Map<BlockPos, TileEntity> consumers, List<FFPipeNetworkMk2> networks, TileEntity te, Fluid type) {
		if(te == null)
			return;

		if(te instanceof IFluidPipeMk2 pipe) {
            if(pipe.getType() == type && pipe.isValidForBuilding()) {
				if(pipe.getNetwork() == null) {
					if(!pipes.containsKey(te.getPos())) {
						pipes.put(te.getPos(), pipe);
						for(EnumFacing e : EnumFacing.VALUES){
							BlockPos pos = te.getPos().offset(e);
							if(te.getWorld().isBlockLoaded(pos))
								iteratePipes(pipes, consumers, networks, te.getWorld().getTileEntity(pos), type);
						}
						
					}
				} else if(pipe.getNetwork().type == type && !networks.contains(pipe.getNetwork())) {
					networks.add(pipe.getNetwork());
				}
			}
		} else if(FFUtils.safeCheckCapa(te, CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)) {
			if(!consumers.containsKey(te.getPos()))
				consumers.put(te.getPos(), te);
		}
	}
}
