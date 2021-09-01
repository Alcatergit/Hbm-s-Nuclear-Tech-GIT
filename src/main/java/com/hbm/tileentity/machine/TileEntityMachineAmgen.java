package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.lib.Library;
import com.hbm.saveddata.RadiationSavedData;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityMachineAmgen extends TileEntity implements ITickable, ISource {

	public List<IConsumer> list = new ArrayList<IConsumer>();
	public long power;
	public long maxPower = 500;
	boolean tact = false;
	
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

			Block block = world.getBlockState(pos).getBlock();
			
			if(block == ModBlocks.machine_amgen) {
				RadiationSavedData data = RadiationSavedData.getData(world);
				float rad = data.getRadNumFromCoord(pos);
				
				power += rad;
				
				RadiationSavedData.decrementRad(world, pos, 5F);
				
			} else if(block == ModBlocks.machine_geo) {
				
				Block b = world.getBlockState(pos.down()).getBlock();
				if(b == ModBlocks.geysir_water) {
					power += 75;
				} else if(b == ModBlocks.geysir_chlorine) {
					power += 100;
				} else if(b == ModBlocks.geysir_vapor) {
					power += 50;
				} else if(b == ModBlocks.geysir_nether) {
					power += 500;
				} else if(b == Blocks.LAVA) {
					power += 100;
					
					if(world.rand.nextInt(1200) == 0) {
						world.setBlockState(pos.down(), Blocks.OBSIDIAN.getDefaultState());
					}
				} else if(b == Blocks.FLOWING_LAVA) {
					power += 25;
					
					if(world.rand.nextInt(600) == 0) {
						world.setBlockState(pos.down(), Blocks.COBBLESTONE.getDefaultState());
					}
				}
				
				b = world.getBlockState(pos.up()).getBlock();
				
				if(b == Blocks.LAVA) {
					power += 100;
					
					if(world.rand.nextInt(1200) == 0) {
						world.setBlockState(pos.up(), Blocks.OBSIDIAN.getDefaultState());
					}
				} else if(b == Blocks.FLOWING_LAVA) {
					power += 25;
					
					if(world.rand.nextInt(600) == 0) {
						world.setBlockState(pos.up(), Blocks.COBBLESTONE.getDefaultState());
					}
				}
			}
			
			if(power > maxPower)
				power = maxPower;

			tact = false;
			ffgeuaInit();
			tact = true;
			ffgeuaInit();
			if(prevPower != power)
				markDirty();
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
