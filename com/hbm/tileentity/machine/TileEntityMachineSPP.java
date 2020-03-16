package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.lib.Library;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityMachineSPP extends TileEntity implements ITickable, ISource {

	public long power;
	public static final long maxPower = 100000;
	public int age = 0;
	public int gen = 0;
	public List<IConsumer> list = new ArrayList<IConsumer>();
	
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
		age++;
		if(age >= 20)
			age -= 20;
		if(age == 9 || age == 19)
			ffgeuaInit();
		
		if(!world.isRemote) {
			long prevPower = power;
			//if(age == 1)
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
		
		return (world.getBlockState(new BlockPos(pos.getX() + 1, y, pos.getZ())).getBlock() != Blocks.AIR &&
				world.getBlockState(new BlockPos(pos.getX() + 1, y, pos.getZ() + 1)).getBlock() != Blocks.AIR &&
				world.getBlockState(new BlockPos(pos.getX() + 1, y, pos.getZ() - 1)).getBlock() != Blocks.AIR &&
				world.getBlockState(new BlockPos(pos.getX() - 1, y, pos.getZ() + 1)).getBlock() != Blocks.AIR &&
				world.getBlockState(new BlockPos(pos.getX() - 1, y, pos.getZ())).getBlock() != Blocks.AIR &&
				world.getBlockState(new BlockPos(pos.getX() - 1, y, pos.getZ() - 1)).getBlock() != Blocks.AIR &&
				world.getBlockState(new BlockPos(pos.getX(), y, pos.getZ() + 1)).getBlock() != Blocks.AIR &&
				world.getBlockState(new BlockPos(pos.getX(), y, pos.getZ() - 1)).getBlock() != Blocks.AIR &&
				world.getBlockState(new BlockPos(pos.getX(), y, pos.getZ())).getBlock() == Blocks.AIR);
	}

	@Override
	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
	}

	@Override
	public void clearList() {
		this.list.clear();
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(pos.east(), getTact());
		ffgeua(pos.west(), getTact());
		ffgeua(pos.south(), getTact());
		ffgeua(pos.north(), getTact());
		ffgeua(pos.down(), getTact());
	}

	@Override
	public void ffgeua(BlockPos pos, boolean newTact) {
		Library.ffgeua(new BlockPos.MutableBlockPos(pos), newTact, this, world);
	}

	@Override
	public long getSPower() {
		return this.power;
	}

	@Override
	public void setSPower(long i) {
		this.power = i;
	}

	@Override
	public List<IConsumer> getList() {
		return this.list;
	}

}
