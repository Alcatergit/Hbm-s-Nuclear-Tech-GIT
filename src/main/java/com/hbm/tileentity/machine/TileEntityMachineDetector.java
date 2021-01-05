package com.hbm.tileentity.machine;

import com.hbm.blocks.machine.PowerDetector;
import com.hbm.interfaces.IConsumer;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityMachineDetector extends TileEntity implements ITickable, IConsumer {

	long power;
	
	@Override
	public void update() {
		if(!world.isRemote) {

			int meta = this.getBlockMetadata();
			int state = 0;

			if(power > 0) {
				state = 1;
				power--;
			}

			if(meta != state) {
				world.setBlockState(pos, world.getBlockState(pos).withProperty(PowerDetector.IS_ON, state > 0 ? true : false));
				this.markDirty();
			}
		}
	}

	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return 20;
	}

}
