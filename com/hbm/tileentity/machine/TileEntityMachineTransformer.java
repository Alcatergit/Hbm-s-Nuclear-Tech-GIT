package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.lib.Library;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityMachineTransformer extends TileEntity implements ITickable, ISource, IConsumer {

	public long power;
	public long maxPower = 10000;
	public int delay = 1;
	public List<IConsumer> list = new ArrayList<IConsumer>();
	boolean tact;
	int age;
	
	public TileEntityMachineTransformer(long b, int d){
		maxPower = b;
		delay = d;
	}

	@Override
	public void update() {
		if(!world.isRemote) {
			
			age++;
			
			if(age == delay) {
				
				maxPower /= (20D / delay);
				long saved = 0;
				
				if(power > maxPower) {
					saved = power - maxPower;
					power = maxPower;
				}
				
				tact = true;
				ffgeuaInit();
				tact = false;
				ffgeuaInit();
				
				age = 0;
				
				maxPower *= (20D / delay);
				
				power += saved;
			}
		}
		
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("powerTime", power);
		compound.setLong("maxPower", maxPower);
		compound.setInteger("delay", delay);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.power = compound.getLong("powerTime");
		this.maxPower = compound.getLong("maxPower");
		this.delay = compound.getInteger("delay");
		super.readFromNBT(compound);
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
		return maxPower;
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
	public long getSPower() {
		return power;
	}

	@Override
	public void setSPower(long i) {
		this.power = i;
	}

	@Override
	public List<IConsumer> getList() {
		return list;
	}

	@Override
	public void clearList() {
		list.clear();
	}

	@Override
	public boolean getTact() {
		return tact;
	}
}
