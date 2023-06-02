package com.hbm.tileentity.conductor;

import java.util.ArrayList;
import java.util.List;

import com.hbm.forgefluid.FFPipeNetwork;
import com.hbm.forgefluid.FFUtils;
import com.hbm.interfaces.IFluidPipe;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEFluidTypePacketTest;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityFFDuctBase extends TileEntity implements IFluidPipe, IFluidHandler, ITickable {

	public EnumFacing[] connections = new EnumFacing[6];
	public Fluid type = null;
	public FFPipeNetwork network = null;
	public ICapabilityProvider[] fluidHandlerCache = new ICapabilityProvider[6];
	
	public boolean isValidForForming = true;
	public boolean firstUpdate = true;
	public boolean needsBuildNetwork = false;
	public boolean thisIsATest = false;
	
	public int weirdTest = 0;

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("fluidType"))
			this.type = FluidRegistry.getFluid(compound.getString("fluidType"));
		needsBuildNetwork = true;
		super.readFromNBT(compound);
	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		if(this.type != null)
			compound.setString("fluidType", type.getName());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void onChunkUnload() {
		if(this.getNetworkTrue() != null){
			this.getNetworkTrue().getPipes().remove(this);
		}
	}
	
	@Override
	public void update() {
		if(!world.isRemote)
			detectAndSendChanges();
			
		this.updateConnections();
		//System.out.println(connections[2]);
		if(needsBuildNetwork){
			//this.getNetwork();
			//this.checkOtherNetworks();
			//this.network.addPipe(this);
			//this.checkOtherNetworks();
			
			
			if(this.network == null) {
				FFPipeNetwork.buildNewNetwork(this);
				//System.out.println("here");
			}
			this.checkFluidHandlers();
			needsBuildNetwork = false;
		}
		
		if(thisIsATest){
			//System.out.println("dfasfdadf");
			this.typeChanged(this.type);
			this.checkFluidHandlers();
			thisIsATest = false;
		}
	}
	
	public void updateConnections() {
		if(FFUtils.checkFluidConnectables(this.world, pos.up(), getNetworkTrue(), EnumFacing.UP.getOpposite())) connections[0] = EnumFacing.UP;
		else connections[0] = null;

		if(FFUtils.checkFluidConnectables(this.world, pos.down(), getNetworkTrue(), EnumFacing.DOWN.getOpposite())) connections[1] = EnumFacing.DOWN;
		else connections[1] = null;

		if(FFUtils.checkFluidConnectables(this.world, pos.north(), getNetworkTrue(), EnumFacing.NORTH.getOpposite())) connections[2] = EnumFacing.NORTH;
		else connections[2] = null;

		if(FFUtils.checkFluidConnectables(this.world, pos.east(), getNetworkTrue(), EnumFacing.EAST.getOpposite())) connections[3] = EnumFacing.EAST;
		else connections[3] = null;

		if(FFUtils.checkFluidConnectables(this.world, pos.south(), getNetworkTrue(), EnumFacing.SOUTH.getOpposite())) connections[4] = EnumFacing.SOUTH;
		else connections[4] = null;

		if(FFUtils.checkFluidConnectables(this.world, pos.west(), getNetworkTrue(), EnumFacing.WEST.getOpposite())) connections[5] = EnumFacing.WEST;
		else connections[5] = null;
	}
	
	public void checkOtherNetworks() {
		List<FFPipeNetwork> list = new ArrayList<FFPipeNetwork>();
		list.add(this.getNetworkTrue());
		TileEntity te;
		FFPipeNetwork largeNet = null;
		for (int i = 0; i < 6; i++) {
			te = FFPipeNetwork.getTileEntityAround(this, i);
			if (te instanceof IFluidPipe && ((IFluidPipe) te).getNetworkTrue() != null && ((IFluidPipe) te).getNetworkTrue().getType() == this.getType()) {
				if (!list.contains(((IFluidPipe) te).getNetworkTrue())) {
					list.add(((IFluidPipe) te).getNetworkTrue());
					if (largeNet == null
							|| ((IFluidPipe) te).getNetworkTrue().getSize() > largeNet
									.getSize())
						largeNet = ((IFluidPipe) te).getNetworkTrue();
				}
			}
		}
		if (largeNet != null) {
			for (FFPipeNetwork network : list) {
				FFPipeNetwork.mergeNetworks(largeNet, network);
			}
			this.network = largeNet;
		} else {
			this.getNetwork().Destroy();
			this.network = this.createNewNetwork();
		}
	}
	
	public FFPipeNetwork createNewNetwork() {
		return new FFPipeNetwork(this.type);
	}
	
	public void typeChanged(Fluid type){
		
		this.getNetwork().setType(type);
		FFPipeNetwork.buildNewNetwork(this);
		//for(int i = 0; i < 6; i++){
	//		TileEntity ent = FFPipeNetwork.getTileEntityAround(this, i);
		//	if(ent != null && ent instanceof IFluidPipe){
		//		FFPipeNetwork.buildNewNetwork(ent);
			//}
		//}
		if(!world.isRemote)
			PacketDispatcher.wrapper.sendToAll(new TEFluidTypePacketTest(pos.getX(), pos.getY(), pos.getZ(), type));
	}
	
	public void onNeighborBlockChange() {
		this.checkFluidHandlers();
	}
	
	public void checkFluidHandlers() {
		if(this.network == null) {
			return;
		}
		for(int i = 0; i < 6;i++) {
			TileEntity te = FFPipeNetwork.getTileEntityAround(this, i);
			if(te != null && !(te instanceof IFluidPipe) && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
				if(fluidHandlerCache[i] != null){
					this.network.getConsumers().remove(fluidHandlerCache[i]);
				}
				if(!this.network.getConsumers().contains(te)) {
					this.network.getConsumers().add((ICapabilityProvider) te);
				}
				
				fluidHandlerCache[i] = (ICapabilityProvider)te;
					
			}
		}
	}
	
	private void detectAndSendChanges() {
		PacketDispatcher.wrapper.sendToAllAround(new TEFluidTypePacketTest(pos.getX(), pos.getY(), pos.getZ(), type), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 50));
		markDirty();
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return this.getNetwork().getTankProperties();
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		return this.getNetwork().fill(resource, doFill);
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return this.getNetwork().drain(resource, doDrain);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return this.getNetwork().drain(maxDrain, doDrain);
	}

	@Override
	public FFPipeNetwork getNetwork() {
		if (this.network != null) {
			return this.network;
		} else {
			this.network = new FFPipeNetwork();
			this.network.setType(this.getType());
			this.network.addPipe(this);
			return this.network;
		}
	}

	@Override
	public FFPipeNetwork getNetworkTrue() {
		return this.network;
	}
	@Override
	public void setNetwork(FFPipeNetwork net) {
		this.network = net;
	}

	@Override
	public Fluid getType() {
		return this.type;
	}
	
	@Override
	public void setType(Fluid fluid) {
		this.type = fluid;
		this.typeChanged(fluid);
	}
	
	@Override
	public void setTypeTrue(Fluid fluid){
		this.type = fluid;
	}

	@Override
	public boolean getIsValidForForming() {
		return this.isValidForForming;
	}

	@Override
	public void breakBlock() {
	//	if(!this.world.isRemote)
		//	PacketDispatcher.wrapper.sendToAll(new TEFFPipeDestructorPacket(this.xCoord, this.yCoord, this.zCoord));
		this.getNetwork().Destroy();
		this.isValidForForming = false;
		for(int i = 0; i < 6; i++){
			TileEntity ent = FFPipeNetwork.getTileEntityAround(this, i);
			if(ent != null && ent instanceof IFluidPipe){
				FFPipeNetwork.buildNewNetwork(ent);
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this) : super.getCapability(capability, facing);
	}
}
