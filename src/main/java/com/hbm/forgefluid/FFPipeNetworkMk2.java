package com.hbm.forgefluid;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hbm.interfaces.IFluidPipeMk2;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityBarrel;
import com.hbm.tileentity.machine.TileEntityDummyFluidPort;
import com.hbm.tileentity.machine.TileEntityMachineFluidTank;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class FFPipeNetworkMk2 implements IFluidHandler {

	protected Fluid type;
	protected Map<BlockPos, TileEntity> fillables = new LinkedHashMap<BlockPos, TileEntity>();
	protected Map<BlockPos, TileEntity> providers = new LinkedHashMap<BlockPos, TileEntity>();
	protected Map<BlockPos, IFluidPipeMk2> pipes = new LinkedHashMap<BlockPos, IFluidPipeMk2>();
	protected long lastUpdateWorldTime = -1;
	protected int recvCursor = 0;
	protected int provCursor = 0;
	public FFPipeNetworkMk2(IFluidPipeMk2 te) {
		this.type = te.getType();
	}

	public FFPipeNetworkMk2(Fluid type) {
		this.type = type;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(resource == null || resource.getFluid() != type)
			return 0;

		List<IFluidHandler> handlers = new ArrayList<>();
		List<Long> demands = new ArrayList<>();
		long totalDemand = 0;

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
					int canFill = h.fill(new FluidStack(resource.getFluid(), Integer.MAX_VALUE), false);
					if(canFill > 0){
						handlers.add(h);
						demands.add((long)canFill);
						totalDemand += canFill;
					}
				}
			}
		}

		if(handlers.isEmpty())
			return 0;

		long toTransfer = Math.min(resource.amount, totalDemand);
		long transferred = 0;
		long remainingDemand = totalDemand;
		int recvCount = handlers.size();

		int recvStart = recvCount > 0 ? recvCursor % recvCount : 0;
		for(int step = 0; step < recvCount && transferred < toTransfer; step++) {
			int i = (recvStart + step) % recvCount;
			IFluidHandler handler = handlers.get(i);
			long demand = demands.get(i);
			long remainingBudget = toTransfer - transferred;
			long maxForReceiver = Math.min(demand, remainingBudget);
			if(maxForReceiver <= 0) {
				remainingDemand -= demand;
				continue;
			}
			long toSend = step == recvCount - 1 ? maxForReceiver : weightedShare(remainingBudget, demand, remainingDemand, maxForReceiver);
			if(toSend <= 0) {
				toSend = 1;
			}
			int filled = handler.fill(new FluidStack(resource.getFluid(), (int)toSend), doFill);
			transferred += filled;
			remainingDemand -= demand;
		}
		if(recvCount > 0 && doFill) recvCursor = (recvStart + 1) % recvCount;

		if(transferred < toTransfer && doFill) {
			for(int i = 0; i < recvCount && transferred < toTransfer; i++) {
				IFluidHandler handler = handlers.get(i);
				long remaining = toTransfer - transferred;
				int filled = handler.fill(new FluidStack(resource.getFluid(), (int)Math.min(remaining, Integer.MAX_VALUE)), true);
				if(filled > 0) {
					transferred += filled;
				}
			}
		}

		return (int)transferred;
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
		providers.clear();
	}

	public void checkForRemoval(TileEntity te) {
		if(te == null)
			return;
		if(te instanceof IFluidPipeMk2) {
			pipes.remove(te.getPos());
		} else{
			try{
				if(FFUtils.safeCheckCapa(te, CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)) {
					if(te instanceof TileEntityProxyCombo proxy) {
						TileEntity resolved = proxy.getTE();
						if(resolved != null) te = resolved;
					}
					if(te instanceof TileEntityDummyFluidPort dummy && dummy.target != null) {
						TileEntity resolved = te.getWorld().getTileEntity(dummy.target);
						if(resolved != null) te = resolved;
					}
					fillables.remove(te.getPos());
					providers.remove(te.getPos());
				}
			} catch(Throwable ignored){
			}
		}
	}

	public void removePipe(BlockPos pos) {
		pipes.remove(pos);
	}

	public void splitIfDisconnected(World world) {
		if(pipes.isEmpty()) {
			destroy();
			return;
		}

		BlockPos startPos = pipes.keySet().iterator().next();

		Set<BlockPos> connected = new HashSet<>();
		Deque<BlockPos> queue = new ArrayDeque<>();
		queue.add(startPos);
		connected.add(startPos);

		while(!queue.isEmpty()) {
			BlockPos current = queue.poll();
			for(EnumFacing e : EnumFacing.VALUES) {
				BlockPos neighbor = current.offset(e);
				if(pipes.containsKey(neighbor) && !connected.contains(neighbor)) {
					connected.add(neighbor);
					queue.add(neighbor);
				}
			}
		}

		if(connected.size() == pipes.size()) {
			cleanupOrphanedContainers(world);
			return;
		}

		FFPipeNetworkMk2 newNet = new FFPipeNetworkMk2(type);

		Iterator<Map.Entry<BlockPos, IFluidPipeMk2>> it = pipes.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<BlockPos, IFluidPipeMk2> entry = it.next();
			if(!connected.contains(entry.getKey())) {
				IFluidPipeMk2 p = entry.getValue();
				p.setNetwork(newNet);
				newNet.pipes.put(entry.getKey(), p);
				it.remove();
			}
		}

		moveContainersToNetwork(newNet, providers);
		moveContainersToNetwork(newNet, fillables);
		cleanupOrphanedContainers(world);
		newNet.cleanupOrphanedContainers(world);
	}

	private void moveContainersToNetwork(FFPipeNetworkMk2 newNet, Map<BlockPos, TileEntity> containerMap) {
		Iterator<Map.Entry<BlockPos, TileEntity>> it = containerMap.entrySet().iterator();
		Set<BlockPos> pipesInThisNet = new HashSet<>(pipes.keySet());
		while(it.hasNext()) {
			Map.Entry<BlockPos, TileEntity> entry = it.next();
			TileEntity te = entry.getValue();
			if(te == null || te.isInvalid()) {
				it.remove();
				continue;
			}
			boolean adjacentToThisNet = false;
			for(EnumFacing e : EnumFacing.VALUES) {
				BlockPos adjPos = te.getPos().offset(e);
				if(pipesInThisNet.contains(adjPos)) {
					adjacentToThisNet = true;
					break;
				}
			}
			if(!adjacentToThisNet) {
				if(containerMap == providers) {
					newNet.providers.put(entry.getKey(), te);
				} else {
					newNet.fillables.put(entry.getKey(), te);
				}
				if(te instanceof TileEntityBarrel barrel) {
					barrel.network = newNet;
				} else if(te instanceof TileEntityMachineFluidTank tank) {
					tank.network = newNet;
				}
				it.remove();
			}
		}
	}

	private Set<BlockPos> buildConnectedContainerSet(World world) {
		Set<BlockPos> pipesInThisNet = new HashSet<>(pipes.keySet());
		Set<BlockPos> connectedContainers = new HashSet<>();

		for(BlockPos pipePos : pipesInThisNet) {
			for(EnumFacing e : EnumFacing.VALUES) {
				BlockPos neighborPos = pipePos.offset(e);
				if(pipesInThisNet.contains(neighborPos)) continue;

				TileEntity neighbor = world.getTileEntity(neighborPos);
				if(neighbor == null) continue;

				if(neighbor instanceof TileEntityProxyCombo proxy) {
					TileEntity resolved = proxy.getTE();
					if(resolved != null) connectedContainers.add(resolved.getPos());
				} else if(neighbor instanceof TileEntityDummyFluidPort dummy && dummy.target != null) {
					connectedContainers.add(dummy.target);
				} else if(FFUtils.safeCheckCapa(neighbor, CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)) {
					connectedContainers.add(neighbor.getPos());
				}
			}
		}

		return connectedContainers;
	}

	private void cleanupOrphanedContainers(World world) {
		if(world == null) return;

		Set<BlockPos> connectedContainers = buildConnectedContainerSet(world);

		Iterator<Map.Entry<BlockPos, TileEntity>> it = providers.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<BlockPos, TileEntity> entry = it.next();
			if(!connectedContainers.contains(entry.getKey())) {
				TileEntity te = entry.getValue();
				if(te instanceof TileEntityBarrel barrel) {
					barrel.network = null;
				} else if(te instanceof TileEntityMachineFluidTank tank) {
					tank.network = null;
				}
				it.remove();
				fillables.remove(entry.getKey());
			}
		}

		it = fillables.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<BlockPos, TileEntity> entry = it.next();
			if(!connectedContainers.contains(entry.getKey())) {
				TileEntity te = entry.getValue();
				if(te instanceof TileEntityBarrel barrel) {
					barrel.network = null;
				} else if(te instanceof TileEntityMachineFluidTank tank) {
					tank.network = null;
				}
				it.remove();
			}
		}
	}

	public void tryAdd(TileEntity te) {
		if(te == null)
			return;
		if(te instanceof IFluidPipeMk2) {
			if(!pipes.containsKey(te.getPos()) && ((IFluidPipeMk2) te).getType() == this.type) {
				pipes.put(te.getPos(), (IFluidPipeMk2) te);
				return;
			}
		} else if(FFUtils.safeCheckCapa(te, CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)) {
			if(te instanceof TileEntityProxyCombo proxy) {
				TileEntity resolved = proxy.getTE();
				if(resolved != null) te = resolved;
			}
			if(te instanceof TileEntityDummyFluidPort dummy) {
				BlockPos originalPos = te.getPos();
				if(dummy.target == null)
					return;
				TileEntity resolvedTe = te.getWorld().getTileEntity(dummy.target);
				if(resolvedTe == null)
					return;
				te = resolvedTe;
				fillables.remove(originalPos);
				providers.remove(originalPos);
			}
			if(!fillables.containsKey(te.getPos())) {
				fillables.put(te.getPos(), te);
			}
			if(!providers.containsKey(te.getPos())) {
				providers.put(te.getPos(), te);
			}
		}
	}
	
	public static FFPipeNetworkMk2 mergeNetworks(FFPipeNetworkMk2 net1, FFPipeNetworkMk2 net2) {
		if((net1 == null || net2 == null) || net1 == net2)
			return net1;

		for(IFluidPipeMk2 pipe : net2.pipes.values()){
			pipe.setNetwork(net1);
		}

		net1.fillables.putAll(net2.fillables);
		net1.providers.putAll(net2.providers);
		net1.pipes.putAll(net2.pipes);

		net2.fillables.clear();
		net2.providers.clear();
		net2.pipes.clear();

		return net1;
	}

	public Map<BlockPos, IFluidPipeMk2> getPipePositions() {
		return pipes;
	}

	public Map<BlockPos, TileEntity> getFillableTiles() {
		return fillables;
	}

	public Map<BlockPos, TileEntity> getProviderTiles() {
		return providers;
	}

	public void addProvider(TileEntity te) {
		if(te != null && !te.isInvalid()) {
			providers.put(te.getPos(), te);
		}
	}

	public void removeProvider(TileEntity te) {
		if(te != null) {
			providers.remove(te.getPos());
		}
	}

	public void addReceiver(TileEntity te) {
		if(te != null && !te.isInvalid()) {
			fillables.put(te.getPos(), te);
		}
	}

	public void removeReceiver(TileEntity te) {
		if(te != null) {
			fillables.remove(te.getPos());
		}
	}

	public static long weightedShare(long total, long part, long whole, long cap) {
		if(total <= 0 || part <= 0 || whole <= 0 || cap <= 0) return 0;
		if(part >= whole) return Math.min(total, cap);
		long share = (total * part) / whole;
		if(share <= 0) return 0;
		return Math.min(share, cap);
	}

	public void update(World world) {
		if(world == null) return;
		long worldTime = world.getTotalWorldTime();
		if(lastUpdateWorldTime == worldTime) return;
		lastUpdateWorldTime = worldTime;
		doUpdate();
	}

	public void forceUpdate(World world) {
		if(world == null) return;
		long saved = lastUpdateWorldTime;
		lastUpdateWorldTime = -1;
		doUpdate();
		lastUpdateWorldTime = Math.max(saved, world.getTotalWorldTime());
	}

	private void doUpdate() {
		if(providers.isEmpty() && fillables.isEmpty()) return;

		Fluid fluidType = this.type;

		long totalAvailable = 0;
		List<IFluidHandler> provHandlers = new ArrayList<>();
		List<Long> provSupplies = new ArrayList<>();

		Iterator<Map.Entry<BlockPos, TileEntity>> provItr = providers.entrySet().iterator();
		while(provItr.hasNext()) {
			Map.Entry<BlockPos, TileEntity> entry = provItr.next();
			TileEntity te = entry.getValue();
			if(te.isInvalid()) {
				provItr.remove();
				continue;
			}
			IFluidHandler handler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
			if(handler != null) {
				FluidStack stack = handler.drain(Integer.MAX_VALUE, false);
				if(stack != null && stack.amount > 0 && (fluidType == null || stack.getFluid() == fluidType)) {
					provHandlers.add(handler);
					provSupplies.add((long)stack.amount);
					totalAvailable += stack.amount;
					if(fluidType == null) fluidType = stack.getFluid();
				}
			}
		}

		long totalDemand = 0;
		List<IFluidHandler> recvHandlers = new ArrayList<>();
		List<Long> recvDemands = new ArrayList<>();

		Iterator<Map.Entry<BlockPos, TileEntity>> recvItr = fillables.entrySet().iterator();
		while(recvItr.hasNext()) {
			Map.Entry<BlockPos, TileEntity> entry = recvItr.next();
			TileEntity te = entry.getValue();
			if(te.isInvalid()) {
				recvItr.remove();
				continue;
			}
			IFluidHandler handler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
			if(handler != null) {
				int canFill = handler.fill(new FluidStack(fluidType, Integer.MAX_VALUE), false);
				if(canFill > 0) {
					recvHandlers.add(handler);
					recvDemands.add((long)canFill);
					totalDemand += canFill;
				}
			}
		}

		if(totalDemand <= 0) {
			return;
		}

		long toTransfer = Math.min(totalAvailable, totalDemand);
		long transferred = 0;
		long remainingDemand = totalDemand;
		int recvCount = recvHandlers.size();

		int recvStart = recvCount > 0 ? recvCursor % recvCount : 0;
		for(int step = 0; step < recvCount && transferred < toTransfer; step++) {
			int i = (recvStart + step) % recvCount;
			IFluidHandler handler = recvHandlers.get(i);
			long demand = recvDemands.get(i);
			long remainingBudget = toTransfer - transferred;
			long maxForReceiver = Math.min(demand, remainingBudget);
			if(maxForReceiver <= 0) {
				remainingDemand -= demand;
				continue;
			}
			long toSend = step == recvCount - 1 ? maxForReceiver : weightedShare(remainingBudget, demand, remainingDemand, maxForReceiver);
			if(toSend <= 0) {
				toSend = 1;
			}
			int filled = handler.fill(new FluidStack(fluidType, (int)toSend), true);
			transferred += filled;
			remainingDemand -= demand;
		}
		if(recvCount > 0) recvCursor = (recvStart + 1) % recvCount;

		if(transferred < toTransfer) {
			for(int i = 0; i < recvCount && transferred < toTransfer; i++) {
				IFluidHandler handler = recvHandlers.get(i);
				long remaining = toTransfer - transferred;
				int filled = handler.fill(new FluidStack(fluidType, (int)Math.min(remaining, Integer.MAX_VALUE)), true);
				if(filled > 0) {
					transferred += filled;
				}
			}
		}

		provHandlers.clear();
		provSupplies.clear();
		totalAvailable = 0;
		Iterator<Map.Entry<BlockPos, TileEntity>> provRebuildItr = providers.entrySet().iterator();
		while(provRebuildItr.hasNext()) {
			Map.Entry<BlockPos, TileEntity> entry = provRebuildItr.next();
			TileEntity te = entry.getValue();
			if(te.isInvalid()) continue;
			IFluidHandler handler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
			if(handler != null) {
				FluidStack stack = handler.drain(Integer.MAX_VALUE, false);
				if(stack != null && stack.amount > 0) {
					provHandlers.add(handler);
					provSupplies.add((long)stack.amount);
					totalAvailable += stack.amount;
				}
			}
		}
		int provCount = provHandlers.size();

		long debited = 0;
		long remainingSupply = totalAvailable;

		int provStart = provCount > 0 ? provCursor % provCount : 0;
		for(int step = 0; step < provCount && debited < transferred; step++) {
			int i = (provStart + step) % provCount;
			IFluidHandler handler = provHandlers.get(i);
			long supply = provSupplies.get(i);
			long remainingToDebit = transferred - debited;
			long maxForProvider = Math.min(supply, remainingToDebit);
			if(maxForProvider <= 0) {
				remainingSupply -= supply;
				continue;
			}
			long toDrain = step == provCount - 1 ? maxForProvider : weightedShare(remainingToDebit, supply, remainingSupply, maxForProvider);
			if(toDrain <= 0) {
				toDrain = 1;
			}
			FluidStack drained = handler.drain(new FluidStack(fluidType, (int)toDrain), true);
			if(drained != null) {
				debited += drained.amount;
			}
			remainingSupply -= supply;
		}
		if(provCount > 0) provCursor = (provStart + 1) % provCount;

		if(debited < transferred) {
			for(int i = 0; i < provCount && debited < transferred; i++) {
				IFluidHandler handler = provHandlers.get(i);
				long remaining = transferred - debited;
				FluidStack drained = handler.drain(new FluidStack(fluidType, (int)Math.min(remaining, Integer.MAX_VALUE)), true);
				if(drained != null) {
					debited += drained.amount;
				}
			}
		}
	}
}
