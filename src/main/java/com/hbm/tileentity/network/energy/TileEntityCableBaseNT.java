package com.hbm.tileentity.network.energy;

import api.hbm.energy.IEnergyConductor;
import api.hbm.energy.PowerNet;
import com.hbm.lib.ForgeDirection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityCableBaseNT extends TileEntity implements ITickable, IEnergyConductor {
	
	protected PowerNet network;

	@Override
	public void update() {
		
		if(!world.isRemote && canUpdate()) {
			
			//we got here either because the net doesn't exist or because it's not valid, so that's safe to assume
			this.setNetwork(null);
			
			this.connect();
			
			if(this.getNetwork() == null) {
				PowerNet net = new PowerNet();
				net.assignConductor(this);
				this.setNetwork(net);
			}
		}
	}
	
	protected void connect() {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			
			TileEntity te = world.getTileEntity(pos.add(dir.offsetX, dir.offsetY, dir.offsetZ));
			
			if(te instanceof IEnergyConductor) {
				
				IEnergyConductor conductor = (IEnergyConductor) te;
				
				if(!conductor.canConnect(dir.getOpposite()))
					continue;
				
				if(this.getNetwork() == null && conductor.getNetwork() != null) {
					conductor.getNetwork().assignConductor(this);
				}
				
				if(this.getNetwork() != null && conductor.getNetwork() != null && this.getNetwork() != conductor.getNetwork()) {
					conductor.getNetwork().joinFrom(this.getNetwork());
				}
			}
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();
		
		if(!world.isRemote) {
			if(this.network != null) {
				this.network.reevaluate();
				this.network = null;
			}
		}
	}

	/**
	 * Only update until a power net is formed, in >99% of the cases it should be the first tick. Everything else is handled by neighbors and the net itself.
	 */
	public boolean canUpdate() {
		return (this.network == null || !this.network.isValid()) && !this.isInvalid();
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN;
	}

	@Override
	public long getPower() {
		return 0;
	}

	@Override
	public long getMaxPower() {
		return 0;
	}

	@Override
	public TileEntityCableBaseNT setNetwork(PowerNet network) {
		this.network = network;
		return this;
	}

	@Override
	public long transferPower(long power) {
		
		if(this.network == null)
			return power;
		
		return this.network.transferPower(power);
	}

	@Override
	public PowerNet getNetwork() {
		return this.network;
	}
}
