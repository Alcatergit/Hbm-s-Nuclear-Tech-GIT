package com.hbm.tileentity.machine;

import com.hbm.blocks.machine.PowerDetector;
import com.hbm.interfaces.IConsumer;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityMachineDetector extends TileEntity implements ITickable, IConsumer {

	private long power;

	public TileEntityMachineDetector(){
		super();
		this.power = 0;
	}
	
	@Override
	public void update() {
		if(!world.isRemote) {

			int meta = this.getBlockMetadata();
			int state = 0;

			if(this.power > 0) {
				state = 1;
				this.power -= 1;
			}

			if(meta != state) {
				PowerDetector.updateBlockState(state==1 ? true: false, world, pos);
				this.markDirty();
			}
		}
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return 30;
	}

}
