package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.ForgeDirection;
import com.hbm.saveddata.RadiationSavedData;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class TileEntityMachineAmgen extends TileEntityLoadedBase implements ITickable, IEnergyGenerator {

	public long power;
	public long maxPower = 500;
    public long production = -1;
	
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

    public int getHeat(World world, IBlockState state, BlockPos pos){
        if(state == null) return 0;
        Block b = state.getBlock();
        if(b == ModBlocks.geysir_water) {
            return 75;
        } else if(b == ModBlocks.geysir_chlorine) {
            return 100;
        } else if(b == ModBlocks.geysir_vapor) {
            return 50;
        } else if(b == ModBlocks.geysir_nether) {
            return 500;
        } else {
            int temp = BlockFluidBase.getTemperature(world, pos);
            if(temp == Integer.MAX_VALUE) return 0;
            temp -= 373;
            if(temp < 0) return 0;
            return temp>>3;
        }
    }

    public void updateHeat(){
        int prod = 0;
        BlockPos.MutableBlockPos posN = new BlockPos.MutableBlockPos();
        for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            posN.setPos(pos.getX() + dir.offsetX, pos.getY() + dir.offsetY, pos.getZ() + dir.offsetZ);
            if(!world.isBlockLoaded(posN)) continue;
            prod += getHeat(world, world.getBlockState(posN), posN);
        }
        this.production = prod;
    }

    int counter = 0;
	@Override
	public void update() {
		if(!world.isRemote) {
			long prevPower = power;

			if(this.getBlockType() == ModBlocks.machine_amgen) {
				power += (long) RadiationSavedData.getData(world).getRadNumFromCoord(pos);
				RadiationSavedData.decrementRad(world, pos, 5F);
				
			} else {
				if(production == -1 || counter % 80 == 0) updateHeat();
                power += production;
                counter++;
			}
			
			if(power > maxPower)
				power = maxPower;

            if(power > 0) this.sendPower(world, pos);
			if(prevPower != power)
				markDirty();
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
		return this.maxPower;
	}
}
