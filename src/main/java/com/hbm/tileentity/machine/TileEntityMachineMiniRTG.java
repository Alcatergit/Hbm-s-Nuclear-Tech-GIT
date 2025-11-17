package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyGenerator;
import net.minecraft.block.Block;
import net.minecraft.util.ITickable;

public class TileEntityMachineMiniRTG extends TileEntityLoadedBase implements ITickable, IEnergyGenerator {

	public long power;
    public long powerProduction = -1;
    public long powerMax = -1;

    @Override
	public void update() {
		if(!world.isRemote) {
            if(powerMax < 0)
                setPowerProduction(this.getBlockType());
			this.sendPower(world, pos);
            power += powerProduction;
			if(power > powerMax)
				power = powerMax;
		}
	}

    public void setPowerProduction(Block b){
        if(b == ModBlocks.machine_powerrtg){
            powerProduction = 2500;
            powerMax = 50000;
        } else if(b == ModBlocks.machine_rtg){
            powerProduction = 500;
            powerMax = 10000;
        } else {
            powerProduction = 100;
            powerMax = 5000;
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
		if(powerMax < 0)
            setPowerProduction(this.getBlockType());

		return powerMax;
	}
}
