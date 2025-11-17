package com.hbm.tileentity;

import api.hbm.block.ICrucibleAcceptor;
import api.hbm.energy.IEnergyUser;
import api.hbm.tile.IHeatSource;

import com.hbm.lib.ForgeDirection;
import com.hbm.inventory.material.Mats.MaterialStack;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityProxyCombo extends TileEntityProxyBase implements IEnergyUser, IHeatSource, ICrucibleAcceptor {

	TileEntity tile;
	boolean inventory;
	boolean power;
	boolean fluid;
    public boolean molten;
	boolean heat;

	public TileEntityProxyCombo() {
	}

	public TileEntityProxyCombo(boolean inventory, boolean power, boolean fluid) {
		this.inventory = inventory;
		this.power = power;
		this.fluid = fluid;
		this.heat = false;
        this.molten = false;
	}

	public TileEntityProxyCombo(boolean inventory, boolean power, boolean fluid, boolean heat) {
		this.inventory = inventory;
		this.power = power;
		this.fluid = fluid;
		this.heat = heat;
        this.molten = false;
	}

    public TileEntityProxyCombo(boolean inventory, boolean power, boolean fluid, boolean heat, boolean molten) {
        this.inventory = inventory;
        this.power = power;
        this.fluid = fluid;
        this.heat = heat;
        this.molten = molten;
    }

	// fewer messy recursive operations
	public TileEntity getTile() {

		if(tile == null) {
			tile = this.getTE();
		}

		return tile;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(tile == null) {
			tile = this.getTE();
			if(tile == null){
				return super.getCapability(capability, facing);
			}
		}
		if(inventory && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			return tile.getCapability(capability, facing);
		}
		if(power && capability == CapabilityEnergy.ENERGY){
			return tile.getCapability(capability, facing);
		}
		if(fluid && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return tile.getCapability(capability, facing);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(tile == null) {
			tile = this.getTE();
			if(tile == null){
				return super.hasCapability(capability, facing);
			}
		}
		if(inventory && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			return tile.hasCapability(capability, facing);
		}
		if(power && capability == CapabilityEnergy.ENERGY){
			return tile.hasCapability(capability, facing);
		}
		if(fluid && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return tile.hasCapability(capability, facing);
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public void setPower(long i) {

		if(!power)
			return;

		if(getTile() instanceof IEnergyUser user) {
			user.setPower(i);
		}
	}

	@Override
	public long getPower() {

		if(!power)
			return 0;

		if(getTile() instanceof IEnergyUser user) {
			return user.getPower();
		}

		return 0;
	}

	@Override
	public long getMaxPower() {

		if(!power)
			return 0;

		if(getTile() instanceof IEnergyUser user) {
			return user.getMaxPower();
		}

		return 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		inventory = compound.getBoolean("inv");
		fluid = compound.getBoolean("flu");
		power = compound.getBoolean("pow");
		heat = compound.getBoolean("hea");
        molten = compound.getBoolean("mol");
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("inv", inventory);
		compound.setBoolean("flu", fluid);
		compound.setBoolean("pow", power);
		compound.setBoolean("hea", heat);
        compound.setBoolean("mol", molten);
        return super.writeToNBT(compound);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

	@Override
	public int getHeatStored() {
		if (!this.heat) {
			return 0;
		}

		if (getTile() instanceof IHeatSource source) {
			return source.getHeatStored();
		}
		return 0;
	}

	@Override
	public void useUpHeat(int heat) {
		if (!this.heat) {
			return;
		}

		if (getTile() instanceof IHeatSource source) {
            source.useUpHeat(heat);
		}
	}

    @Override
    public boolean canAcceptPartialPour(World world, BlockPos pos, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
        if(this.molten && getTile() instanceof ICrucibleAcceptor acc){
            return acc.canAcceptPartialPour(world, pos, dX, dY, dZ, side, stack);
        }
        return false;
    }

    @Override
    public MaterialStack pour(World world, BlockPos pos, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
        if(this.molten && getTile() instanceof ICrucibleAcceptor acc){
            return acc.pour(world, pos, dX, dY, dZ, side, stack);
        }
        return null;
    }

    @Override
    public boolean canAcceptPartialFlow(World world, BlockPos pos, ForgeDirection side, MaterialStack stack) {
        if(this.molten && getTile() instanceof ICrucibleAcceptor acc){
            return acc.canAcceptPartialFlow(world, pos, side, stack);
        }
        return false;
    }

    @Override
    public MaterialStack flow(World world, BlockPos pos, ForgeDirection side, MaterialStack stack) {
        if(this.molten && getTile() instanceof ICrucibleAcceptor acc){
            return acc.flow(world, pos, side, stack);
        }
        return null;
    }
}
