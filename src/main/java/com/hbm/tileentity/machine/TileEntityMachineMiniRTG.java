package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.lib.Library;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityMachineMiniRTG extends TileEntity implements ITickable, ISource {

	public List<IConsumer> list = new ArrayList<>();
	public long power;
	public long maxPower = 100;
	boolean tact = false;
	
	@Override
	public void update() {
		if(!world.isRemote) {

			power += 25;

			if(power > maxPower)
				power = maxPower;

			tact = false;
			ffgeuaInit();
			tact = true;
			ffgeuaInit();
		}
	}
	
	@Override
	public void ffgeuaInit() {
		ffgeua(pos.up(), getTact());
		ffgeua(pos.down(), getTact());
		ffgeua(pos.west(), getTact());
		ffgeua(pos.east(), getTact());
		ffgeua(pos.north(), getTact());
		ffgeua(pos.south(), getTact());
	}

	@Override
	public void ffgeua(BlockPos pos, boolean newTact) {

		Library.ffgeua(new BlockPos.MutableBlockPos(pos), newTact, this, world);
	}

	@Override
	public boolean getTact() {
		return tact;
	}

	@Override
	public long getSPower() {
		return power;
	}

	@Override
	public void setSPower(long i) {
		power = i;
	}

	@Override
	public List<IConsumer> getList() {
		return list;
	}

	@Override
	public void clearList() {
		list.clear();
	}

}
