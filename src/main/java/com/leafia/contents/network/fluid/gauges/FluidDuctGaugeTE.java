package com.leafia.contents.network.fluid.gauges;

import com.hbm.forgefluid.FFPipeNetworkMk2;
import com.leafia.contents.network.fluid.FluidDuctEquipmentTE;
import com.leafia.dev.LeafiaDebug;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.llib.group.LeafiaSet;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class FluidDuctGaugeTE extends FluidDuctEquipmentTE implements ITickable, IFluidGauge {
	LeafiaSet<FFPipeNetworkMk2> unoptimizedshit = new LeafiaSet<>();
	int[] fills = new int[20];
	int needle = 0;
	@Override
	public void update() {
		if (!world.isRemote) {
			for (FFPipeNetworkMk2 network : unoptimizedshit) {
				if (network != this.network) {
					network.listeners.remove(this);
					unoptimizedshit.remove(network);
				}
			}
			if (network != null && !this.isInvalid()) {
				unoptimizedshit.add(network);
				network.listeners.add(this);
			}
			needle = Math.floorMod(needle+1,20);
			fills[needle] = 0;

			int sum = 0;
			for (int fill : fills)
				sum += fill;

			LeafiaPacket._start(this).__write(0,sum).__sendToAffectedClients();
		}
	}

	public int local_fillPerSec = 0;
	@Override
	public void onReceivePacketLocal(byte key,Object value) {
		super.onReceivePacketLocal(key,value);
		if (key == 0)
			local_fillPerSec = (int)value;
	}

	@Override
	public void invalidate() {
		network.listeners.remove(this);
		super.invalidate();
	}

	@Override
	public void onFill(int amt) {
		LeafiaDebug.debugLog(world,amt+"mB");
		fills[needle] = amt;
	}
}
