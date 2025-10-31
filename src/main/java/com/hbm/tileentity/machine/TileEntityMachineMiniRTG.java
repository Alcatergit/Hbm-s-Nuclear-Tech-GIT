package com.hbm.tileentity.machine;

import api.hbm.energy.IEnergyGenerator;
import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.TileEntityLoadedBase;
import net.minecraft.util.ITickable;

public class TileEntityMachineMiniRTG extends TileEntityLoadedBase implements ITickable, IEnergyGenerator {

	public long power;
	
	@Override
	public void update() {
		if(!world.isRemote) {
			this.sendPower(world, pos);
			if(this.getBlockType() == ModBlocks.machine_powerrtg)
				power += 2500;
			else
				power += 70;

			if(power > getMaxPower())
				power = getMaxPower();
		}
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getMaxPower() {
		if(this.getBlockType() == ModBlocks.machine_powerrtg)
			return 50000;

		return 10000;
	}
}
