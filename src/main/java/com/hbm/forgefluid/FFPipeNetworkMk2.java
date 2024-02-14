package com.hbm.forgefluid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

	protected static Random rand = new Random();
	
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
		
		Iterator<TileEntity> itr = fillables.values().iterator();
		while(itr.hasNext()){
			TileEntity te = itr.next();
			if(te.isInvalid()){
				itr.remove();
				continue;
			}
			if(te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)){
				IFluidHandler h = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
				if(h != null && h.fill(new FluidStack(resource.getFluid(), 1), false) > 0){
					handlers.add(h);
				}
			}
		}
		
		if(handlers.isEmpty())
			return 0;
		
		int part = resource.amount/handlers.size();
		int totalDrained = 0;
		int remaining = resource.amount;
		//Drillgon200: Extra hacky compensation
		int intRoundingCompensation = resource.amount-part*handlers.size();
		rand.setSeed(((TileEntity)this.fillables.values().iterator().next()).getWorld().getTotalWorldTime());
		int randomFillIndex = rand.nextInt(handlers.size());
		for(int i = 0; i < handlers.size(); i++){
			IFluidHandler consumer = handlers.get(i);
			int vol = consumer.fill(new FluidStack(resource.getFluid(), randomFillIndex == i ? part + intRoundingCompensation : part), doFill);
			totalDrained += vol;
			remaining -= vol;
			if(remaining <= 0)
				return totalDrained;
		}
		
		return totalDrained;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		//I'm not sure how I'm supposed to implement a drain for a fluid pipe network as it no longer has an internal tank.
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return null;
	}

	public int size() {
		return fillables.size() + pipes.size();
	}
	
	public Fluid getType() {
		return type;
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
				if(te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
					fillables.remove(te.getPos());
				}
			} catch(Throwable t){
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
		} else if(te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
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
		if(te instanceof IFluidPipeMk2) {
			IFluidPipeMk2 pipe = (IFluidPipeMk2) te;
			if(pipe.getNetwork() != null)
				return pipe.getNetwork();
			Fluid type = pipe.getType();

			Map<BlockPos, IFluidPipeMk2> pipes = new HashMap<BlockPos, IFluidPipeMk2>();
			Map<BlockPos, TileEntity> consumers = new HashMap<BlockPos, TileEntity>();
			List<FFPipeNetworkMk2> toMerge = new ArrayList<FFPipeNetworkMk2>();
			iteratePipes(pipes, consumers, toMerge, te, type);

			if(toMerge.size() > 0)
				net = toMerge.remove(0);
			else
				net = new FFPipeNetworkMk2(pipe);
			
			while(toMerge.size() > 0)
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

		if(te instanceof IFluidPipeMk2) {
			IFluidPipeMk2 pipe = (IFluidPipeMk2) te;
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
		} else if(te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
			if(!consumers.containsKey(te.getPos()))
				consumers.put(te.getPos(), te);
		}
	}

}
