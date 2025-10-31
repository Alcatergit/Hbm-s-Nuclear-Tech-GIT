package com.leafia.contents.machines.reactors.pwr.blocks.components.terminal;

import com.leafia.contents.machines.reactors.pwr.PWRData;
import com.leafia.contents.machines.reactors.pwr.blocks.components.PWRComponentEntity;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.container_utility.LeafiaPacketReceiver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

public class PWRTerminalTE extends TileEntity implements PWRComponentEntity, IFluidHandler, LeafiaPacketReceiver {
	//static {
	//	MainRegistry.registerTileEntities.put(TileEntityPWRTerminal.class,"pwr_terminal"); // didnt work. I hate this game
	//}
	BlockPos corePos = null;
	@Override
	public void setCoreLink(@Nullable BlockPos pos) {
		corePos = pos;
	}

	@Nullable
	@Override
	public PWRData getLinkedCore() {
		return PWRComponentEntity.getCoreFromPos(world,corePos);
	}

	@Override
	public void assignCore(@Nullable PWRData data) {}
	@Override
	public PWRData getCore() { return null; }
	@Nullable
	PWRData gatherData() {
		if (this.corePos != null) {
			TileEntity entity = world.getTileEntity(corePos);
			if (entity != null) {
				if (entity instanceof PWRComponentEntity) {
					return ((PWRComponentEntity) entity).getCore();
				}
			}
		}
		return null;
	}
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if (compound.hasKey("corePosX"))
			corePos = new BlockPos(
					compound.getInteger("corePosX"),
					compound.getInteger("corePosY"),
					compound.getInteger("corePosZ")
			);
		super.readFromNBT(compound);
	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		if (corePos != null) {
			compound.setInteger("corePosX",corePos.getX());
			compound.setInteger("corePosY",corePos.getY());
			compound.setInteger("corePosZ",corePos.getZ());
		}
		return super.writeToNBT(compound);
	}
	@Override
	public void onDiagnosis() {
		LeafiaPacket._start(this).__write(0,corePos).__sendToAffectedClients();
	}

	// redirects
	@Override
	public IFluidTankProperties[] getTankProperties() {
		PWRData gathered = gatherData();
		return (gathered != null) ? gathered.getTankProperties() : new IFluidTankProperties[0];
	}
	@Override
	public int fill(FluidStack resource,boolean doFill) {
		PWRData gathered = gatherData();
		return (gathered != null) ? gathered.fill(resource,doFill) : 0;
	}
	@Nullable
	@Override
	public FluidStack drain(FluidStack resource,boolean doDrain) {
		PWRData gathered = gatherData();
		return (gathered != null) ? gathered.drain(resource,doDrain) : null;
	}
	@Nullable
	@Override
	public FluidStack drain(int maxDrain,boolean doDrain) {
		PWRData gathered = gatherData();
		return (gathered != null) ? gathered.drain(maxDrain,doDrain) : null;
	}


	@Override
	public void validate() {
		super.validate();
		if (world.isRemote)
			LeafiaPacket._validate(this);
	}

	@Override
	public boolean hasCapability(Capability<?> capability,@Nullable EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability,facing);
	}
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability,@Nullable EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		}
		return super.getCapability(capability,facing);
	}

	@Override
	public String getPacketIdentifier() {
		return "PWRTerminal";
	}
	@Override
	public void onReceivePacketLocal(byte key,Object value) {
		if (key == 0) {
			//if (value.equals(false))
			//	corePos = null;
			//else
				corePos = (BlockPos)value; // Now supports null values!
		}
	}
	@Override
	public void onReceivePacketServer(byte key,Object value,EntityPlayer plr) {

	}
	@Override
	public void onPlayerValidate(EntityPlayer plr) {
		LeafiaPacket._start(this).__write(0,/*(corePos == null) ? false : */corePos).__sendToClient(plr);
	}
}
