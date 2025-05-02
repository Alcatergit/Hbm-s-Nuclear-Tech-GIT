package com.hbm.tileentity.conductor;

import java.util.ArrayList;
import java.util.List;

import com.hbm.forgefluid.FFPipeNetworkMk2;
import com.hbm.forgefluid.FFUtils;
import com.hbm.interfaces.IFluidPipeMk2;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PipeUpdatePacket;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class TileEntityFFDuctBaseMk2 extends TileEntity implements IFluidPipeMk2, IFluidHandler {

	public EnumFacing[] connections = new EnumFacing[6];
	protected Fluid type;
	protected FFPipeNetworkMk2 network = null;
	public TileEntity[] tileentityCache = new TileEntity[6];
	public boolean isBeingDestroyed = false;

	public TileEntityFFDuctBaseMk2() {
	}

	public void setType(Fluid f) {
		if(f != type) {
			type = f;
			world.notifyNeighborsOfStateChange(pos, getBlockType(), true);
			world.neighborChanged(pos, getBlockType(), pos);
			IBlockState state = world.getBlockState(pos);
			world.markAndNotifyBlock(pos, world.getChunk(pos), state, state, 2);
			rebuildNetworks(world, pos);
			if(world instanceof WorldServer) {
				PlayerChunkMapEntry entry = ((WorldServer) world).getPlayerChunkMap().getEntry(MathHelper.floor(pos.getX()) >> 4, MathHelper.floor(pos.getZ()) >> 4);

				if(entry != null) {
					for(EntityPlayerMP player : entry.getWatchingPlayers()) {
						player.connection.sendPacket(new SPacketUpdateTileEntity(pos, 0, writeToNBT(new NBTTagCompound())));
					}
				}
			}
			if(!world.isRemote)
				PacketDispatcher.wrapper.sendToAllTracking(new PipeUpdatePacket(pos, 1), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
		}
	}

	public Fluid getType() {
		return type;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		if(type != null)
			compound.setString("fluidType", type.getName());
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("fluidType"))
			this.type = FluidRegistry.getFluid(compound.getString("fluidType"));
		super.readFromNBT(compound);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket(){
		return new SPacketUpdateTileEntity(this.getPos(), 0, this.writeToNBT(new NBTTagCompound()));
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
		public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
			this.readFromNBT(pkt.getNbtCompound());
		}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		Fluid f = this.type;
		this.readFromNBT(tag);
		if(f == type)
			return;
		for(EnumFacing e : EnumFacing.VALUES) {
			TileEntity te = world.getTileEntity(pos.offset(e));
			if(te instanceof TileEntityFFDuctBaseMk2)
				((TileEntityFFDuctBaseMk2) te).onNeighborChange();
		}
		this.onNeighborChange();
	}

	// Probably called before neighbor changed
	@Override
	public void onLoad() {
		if(!world.isRemote){
			world.getMinecraftServer().addScheduledTask(() -> {
				joinOrMakeNetwork();
				onNeighborChange();
			});
		} else {
			joinOrMakeNetwork();
			onNeighborChange();
		}
	}
		

	public void onNeighborChange() {
		rebuildCache();
		updateConnections();
		if(!world.isRemote)
			PacketDispatcher.wrapper.sendToAllTracking(new PipeUpdatePacket(pos), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
	}

	@Override
	public void onChunkUnload() {
		if(network == null)
			return;
		for(TileEntity te : tileentityCache) {
			if(te != null) {
				if(te instanceof IFluidPipeMk2)
					continue;
				if(!world.isBlockLoaded(te.getPos())) {
					network.checkForRemoval(te);
					continue;
				}
				boolean flag = true;
				for(EnumFacing e : EnumFacing.VALUES) {
					BlockPos pos = te.getPos().offset(e);
					if(world.isBlockLoaded(pos)) {
						TileEntity ent = world.getTileEntity(pos);
						if(ent instanceof IFluidPipeMk2 && ((IFluidPipeMk2) ent).getNetwork() == network) {
							flag = false;
							break;
						}
					}
				}
				if(flag)
					network.checkForRemoval(te);
			}
		}
		network.checkForRemoval(this);

		// Not sure if I need to do this, but I'll be safe
		for(int i = 0; i < tileentityCache.length; i++)
			tileentityCache[i] = null;

		this.network = null;
	}

	@Override
	public void invalidate() {
		super.invalidate();
	}

	// Drillgon200: Has to be static because breakBlock doesn't get called on
	// client, and the tile entity is gone before a packet can reach it.
	public static void breakBlock(World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if(te instanceof TileEntityFFDuctBaseMk2) {
			((TileEntityFFDuctBaseMk2) te).isBeingDestroyed = true;
		}
		rebuildNetworks(world, pos);
	}

	public static void rebuildNetworks(World world, BlockPos pos) {
		TileEntity center = world.getTileEntity(pos);
		for(EnumFacing e : EnumFacing.VALUES) {
			TileEntity te = world.getTileEntity(pos.offset(e));
			if(te instanceof IFluidPipeMk2) {
				IFluidPipeMk2 pipe = (IFluidPipeMk2) te;
				if(pipe.getNetwork() != null)
					pipe.getNetwork().destroy();
			}
		}
		if(center instanceof IFluidPipeMk2 && ((IFluidPipeMk2) center).getNetwork() != null)
			((IFluidPipeMk2) center).getNetwork().destroy();

		for(EnumFacing e : EnumFacing.VALUES)
			FFPipeNetworkMk2.buildNetwork(world.getTileEntity(pos.offset(e)));
		FFPipeNetworkMk2.buildNetwork(center);
	}

	@Override
	public void joinOrMakeNetwork() {
		List<FFPipeNetworkMk2> otherNetworks = new ArrayList<FFPipeNetworkMk2>();
		for(EnumFacing e : EnumFacing.VALUES) {
			BlockPos offset = pos.offset(e);
			TileEntity te = world.getTileEntity(offset);
			if(te instanceof IFluidPipeMk2) {
				IFluidPipeMk2 pipe = (IFluidPipeMk2) te;
				if(pipe.getNetwork() != null && pipe.getNetwork().getType() == this.getType() && !otherNetworks.contains(pipe.getNetwork())) {
					otherNetworks.add(pipe.getNetwork());
				}
			}
		}
		if(otherNetworks.isEmpty()) {
			network = new FFPipeNetworkMk2(this);
			network.tryAdd(this);
			return;
		} else {
			FFPipeNetworkMk2 net = otherNetworks.remove(0);
			while(otherNetworks.size() > 0)
				net = FFPipeNetworkMk2.mergeNetworks(net, otherNetworks.remove(0));
			network = net;
			net.tryAdd(this);
		}
	}

	protected boolean rebuildCache() {
		boolean changed = false;
		for(EnumFacing e : EnumFacing.VALUES) {
			TileEntity te = world.getTileEntity(pos.offset(e));
			if(tileentityCache[e.getIndex()] == null) {
				if(te != null) {
					if(network != null)
						network.tryAdd(te);
					tileentityCache[e.getIndex()] = te;
					changed = true;
				}
			} else {
				if(te == null) {
					if(network != null)
						network.checkForRemoval(tileentityCache[e.getIndex()]);
					tileentityCache[e.getIndex()] = null;
					changed = true;
				} else if(te != tileentityCache[e.getIndex()]) {
					if(network != null) {
						network.checkForRemoval(tileentityCache[e.getIndex()]);
						network.tryAdd(te);
					}
					tileentityCache[e.getIndex()] = te;
					changed = true;
				}
			}
		}
		if(world.isRemote){
			//System.out.println(this + " " + this.getPos() + " " + changed);
			//new Exception().printStackTrace();
		}
		return changed;
	}

	public void updateConnections() {
		if(FFUtils.checkFluidConnectablesMk2(this.world, pos.up(), getType(), EnumFacing.UP.getOpposite()))
			connections[0] = EnumFacing.UP;
		else
			connections[0] = null;

		if(FFUtils.checkFluidConnectablesMk2(this.world, pos.down(), getType(), EnumFacing.DOWN.getOpposite()))
			connections[1] = EnumFacing.DOWN;
		else
			connections[1] = null;

		if(FFUtils.checkFluidConnectablesMk2(this.world, pos.north(), getType(), EnumFacing.NORTH.getOpposite()))
			connections[2] = EnumFacing.NORTH;
		else
			connections[2] = null;

		if(FFUtils.checkFluidConnectablesMk2(this.world, pos.east(), getType(), EnumFacing.EAST.getOpposite()))
			connections[3] = EnumFacing.EAST;
		else
			connections[3] = null;

		if(FFUtils.checkFluidConnectablesMk2(this.world, pos.south(), getType(), EnumFacing.SOUTH.getOpposite()))
			connections[4] = EnumFacing.SOUTH;
		else
			connections[4] = null;

		if(FFUtils.checkFluidConnectablesMk2(this.world, pos.west(), getType(), EnumFacing.WEST.getOpposite()))
			connections[5] = EnumFacing.WEST;
		else
			connections[5] = null;
	}

	@Override
	public FFPipeNetworkMk2 getNetwork() {
		return network;
	}

	@Override
	public void setNetwork(FFPipeNetworkMk2 net) {
		network = net;
	}

	@Override
	public boolean isValidForBuilding() {
		return !isBeingDestroyed;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return network != null ? network.getTankProperties() : new IFluidTankProperties[] {};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		return network != null ? network.fill(resource, doFill) : 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return network != null ? network.drain(resource, doDrain) : null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return network != null ? network.drain(maxDrain, doDrain) : null;
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
