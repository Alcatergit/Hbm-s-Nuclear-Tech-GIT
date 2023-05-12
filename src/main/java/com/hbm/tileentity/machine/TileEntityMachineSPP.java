package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityMachineSPP extends TileEntityLoadedBase implements ITickable, IEnergyGenerator {

	public long power;
	public static final long maxPower = 100000;
	public int gen = 0;
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		power = compound.getLong("power");
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("power", power);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void update() {
		if(!world.isRemote) {
			long prevPower = power;
			this.sendPower(world, pos);

			if(world.getTotalWorldTime() % 20 == 0)
				gen = checkStructure() * 15;

			if(gen > 0)
				power += gen;
			if(power > maxPower)
				power = maxPower;
			if(prevPower != power)
				markDirty();
		}
		
	}
	
	public int checkStructure() {

		int h = 0;
		
		for(int i = pos.getY() + 1; i < 254; i++)
			if(world.getBlockState(new BlockPos(pos.getX(), i, pos.getZ())).getBlock() == ModBlocks.machine_spp_top) {
				h = i;
				break;
			}
		
		for(int i = pos.getY() + 1; i < h; i++)
			if(!checkSegment(i))
				return 0;
		
		
		return h - pos.getY() - 1;
	}
	
	public boolean checkSegment(int y) {
		
		//   BBB
		//   BAB
		//   BBB
		
		return (!world.isAirBlock(new BlockPos(pos.getX() + 1, y, pos.getZ())) &&
				!world.isAirBlock(new BlockPos(pos.getX() + 1, y, pos.getZ() + 1)) &&
				!world.isAirBlock(new BlockPos(pos.getX() + 1, y, pos.getZ() - 1)) &&
				!world.isAirBlock(new BlockPos(pos.getX() - 1, y, pos.getZ() + 1)) &&
				!world.isAirBlock(new BlockPos(pos.getX() - 1, y, pos.getZ())) &&
				!world.isAirBlock(new BlockPos(pos.getX() - 1, y, pos.getZ() - 1)) &&
				!world.isAirBlock(new BlockPos(pos.getX(), y, pos.getZ() + 1)) &&
				!world.isAirBlock(new BlockPos(pos.getX(), y, pos.getZ() - 1)) &&
				world.isAirBlock(new BlockPos(pos.getX(), y, pos.getZ())));
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
		return maxPower;
	}
}
