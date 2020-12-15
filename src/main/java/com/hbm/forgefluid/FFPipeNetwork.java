package com.hbm.forgefluid;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IFluidPipe;
import com.hbm.main.MainRegistry;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class FFPipeNetwork implements IFluidHandler {

	
	protected Fluid type;
	protected List<ICapabilityProvider> fillables = new ArrayList<ICapabilityProvider>();
	protected List<IFluidPipe> pipes = new ArrayList<IFluidPipe>();
	
	protected FluidTank internalNetworkTank = new FluidTank(4000);

	private int tickTimer = 0;

	
	/**
	 * Constructor.
	 */
	public FFPipeNetwork() {
		this(null);
	}

	/**
	 * Constructs the network with a fluid type, hbm pipes only work with a single fluid pipe.
	 * @param fluid
	 */
	public FFPipeNetwork(Fluid fluid) {
		//new Exception().printStackTrace();
		this.type = fluid;
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			MainRegistry.allPipeNetworks.add(this);
	}
	
	/**
	 * Gets the number of pipes and consumers in the network.
	 * @return - the number of pipes in the network plus the number of consumers
	 */
	public int getSize() {
		return pipes.size() + fillables.size();
	}
	
	/**
	 * Gets a list of things the network is currently trying to fill
	 * @return - list of consumers in the network
	 */
	public List<ICapabilityProvider> getConsumers(){
		return this.fillables;
	}
	
	/**
	 * Gets a list of pipes in the network.
	 * @return - list of pipes in the network
	 */
	public List<IFluidPipe> getPipes(){
		return this.pipes;
	}
	
	public World getNetworkWorld(){
		for(IFluidPipe pipe : this.pipes){
			if(pipe != null && ((TileEntity)pipe).getWorld() != null){
				return ((TileEntity)pipe).getWorld();
			}
		}
		return null;
	}
	
	/**
	 * Called whenever the world ticks to fill any connected fluid handlers
	 */
	public void updateTick(){
		//System.out.println(this.getType().getName() + " " + this.fillables.size());
		if(tickTimer < 20){
			tickTimer ++;
		} else {
			tickTimer = 0;
		}
		if(tickTimer == 9 || tickTimer == 19){
			if(pipes.isEmpty())
				this.destroySoft();
		//	cleanPipes();
			//cleanConsumers();
			fillFluidInit();
		}
		
	}
	
	public void fillFluidInit(){
		//if(getType() == ModForgeFluids.oil)
		//	System.out.println(this.fillables.size());
		//Pretty much the same thing as the transfer fluid in Library.java
		if(internalNetworkTank.getFluid() == null || internalNetworkTank.getFluidAmount() <= 0)
			return;
		
		List<IFluidHandler> consumers = new ArrayList<IFluidHandler>();
		for(ICapabilityProvider handle : this.fillables){
			if(handle != null && handle.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null) && handle.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null).fill(new FluidStack(this.type, 1), false) > 0 && !consumers.contains(handle));
				consumers.add(handle.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null));
		}
		int size = consumers.size();
		if(size <= 0)
			return;
		int part = this.internalNetworkTank.getFluidAmount() / size;
		int lastPart = part + this.internalNetworkTank.getFluidAmount() - part * size;
		int i = 1;
		
		for(IFluidHandler consume : consumers){
			i++;
			if(internalNetworkTank.getFluid() != null && consume != null){
				internalNetworkTank.drain(consume.fill(new FluidStack(internalNetworkTank.getFluid().getFluid(), i<consumers.size()?part:lastPart), true), true);
			}
		}
	}

	/*public void cleanPipes(){
		for(IFluidPipe pipe : pipes){
			if(pipe == null)
				pipes.remove(pipe);
		}
	}
	
	public void cleanConsumers(){
		for(IFluidHandler consumer : fillables){
			if(consumer == null)
				fillables.remove(consumer);
		}
	}*/
	/**
	 * Merges two pipe networks together. Usually called when you connect two or more pipe networks with another pipe
	 * @param net - the network that you want to merge into
	 * @param merge - the network that gets merged, then deleted
	 * @return The newly merged network
	 */
	public static FFPipeNetwork mergeNetworks(FFPipeNetwork net, FFPipeNetwork merge) {
		if (net != null && merge != null && net != merge) {
			if(net.getType() == null || merge.getType() == null || net.getType() != merge.getType())
				return net;
			for (IFluidPipe pipe : merge.pipes) {
				net.addPipe(pipe);
				pipe.setNetwork(net);
			}
			merge.pipes.clear();
			for (ICapabilityProvider fill : merge.fillables) {
				net.fillables.add(fill);

			}
			merge.Destroy();
			return net;
		} else if(net != null) {
			return net;
		} else {
			return null;
		}
	}

	/**
	 * Builds a network around a pipe, usually called when you want to link a lot of pipes together
	 * @param pipe - the pipe tile entity you want the build the network around
	 * @return The newly built network
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static FFPipeNetwork buildNewNetwork(TileEntity pipe) {
		FFPipeNetwork net = null;
		if (pipe instanceof IFluidPipe) {
//			if(!pipe.getWorldObj().isRemote)
			//	return null;
			IFluidPipe fPipe = (IFluidPipe) pipe;
			fPipe.getNetwork().Destroy();
			//System.out.println("true net: " + fPipe.getNetworkTrue());
			net = new FFPipeNetwork(fPipe.getType());
			
			List[] netVars = iteratePipes(null, null, null, pipe, net.getType());
		//	net.pipes = netVars[0];
			net.pipes.clear();
			net.pipes.addAll(netVars[0]);
		//	System.out.println(netVars[0].size());
			//System.out.println(netVars[1].size());
			//System.out.println(netVars[2].size());
			for(IFluidPipe setPipe : net.pipes){
				setPipe.setNetwork(net);
			}
		//	net.fillables = netVars[1];
			net.fillables.clear();
			net.fillables.addAll(netVars[1]);
			net.setType(fPipe.getType());
			
			List<FFPipeNetwork> mergeList = netVars[2];
			for(FFPipeNetwork network : mergeList){
				mergeNetworks(net, network);
			}
			
		}
		return net;
	}

	/**
	 * Recursive function that goes through all the pipes and fluid handlers connected to each other and returns a list of both. Called when building a new network.
	 * @param pipes - the list of pipes to add new pipes to
	 * @param consumers - the list of consumers to add new consumers to
	 * @param networks - this list of networks the currently iterating pipe network is connected to
	 * @param te - the TileEntity you want to start the iteration from
	 * @param type - the type of fluid the network hsa
	 * @return A list array containing the pipes connected to the network and the fluid handlers connected to the network.
	 */
	@SuppressWarnings("rawtypes")
	public static List[] iteratePipes(List<IFluidPipe> pipes, List<ICapabilityProvider> consumers, List<FFPipeNetwork> networks, TileEntity te, Fluid type) {
		
		if(pipes == null)
			pipes = new ArrayList<IFluidPipe>();
		if(consumers == null)
			consumers = new ArrayList<ICapabilityProvider>();
		if(networks == null)
			networks = new ArrayList<FFPipeNetwork>();
		if (te == null)
			return new List[]{pipes, consumers, networks};
		TileEntity next = null;
		if (te.getWorld().getTileEntity(te.getPos()) != null) {
			if(!pipes.contains((IFluidPipe)te) && ((IFluidPipe)te).getIsValidForForming()){
				pipes.add((IFluidPipe) te);
				//System.out.println("TE Coords: " + te.xCoord + " " + te.yCoord + " " + te.zCoord);
			}
			for (int i = 0; i < 6; i++) {
				next = getTileEntityAround(te, i);
				if (next instanceof IFluidHandler && next instanceof IFluidPipe && ((IFluidPipe)next).getIsValidForForming() && ((IFluidPipe)next).getType() == type && !pipes.contains((IFluidPipe)next)) {

					iteratePipes(pipes, consumers, networks, next, type);
					//So java really does pass by location and not by value. I feel dumb now.
					
					//System.out.println("pipes length 1: " + pipes.size());
					//pipes.clear();
					//pipes.addAll(nextPipe[0]);
					//System.out.println("pipes length 2: " + pipes.size());
					//consumers.addAll(nextPipe[1]);
					//networks.addAll(nextPipe[2]);
				} else if (next != null && next.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null) && !(next instanceof IFluidPipe)) {
					if(!consumers.contains((ICapabilityProvider)next)){
						consumers.add((ICapabilityProvider) next);
						
					}
					
				}
			}
			if(((IFluidPipe)te).getNetworkTrue() != null && ((IFluidPipe)te).getIsValidForForming() && ((IFluidPipe)te).getNetworkTrue().getType() == type && !networks.contains(((IFluidPipe)te).getNetwork())){
				networks.add(((IFluidPipe)te).getNetwork());
			}
		}
		
		return new List[]{pipes, consumers, networks};
	}
	/**
	 * Should be self explanatory. Normally used in a for loop to get all the surrounding tile entities. No idea why I put it in this class.
	 * @param te - the tile entity that you want to find other tile entities directly touching
	 * @param dir - the direction around the tile entity expressed as an integer
	 * @return The tile entity around the given one. Null if doesn't exist.
	 */
	public static TileEntity getTileEntityAround(TileEntity te, int dir) {
		if (te == null)
			return null;
		switch (dir) {
		case 0:
			return te.getWorld().getTileEntity(te.getPos().east());
		case 1:
			return te.getWorld().getTileEntity(te.getPos().west());
		case 2:
			return te.getWorld().getTileEntity(te.getPos().south());
		case 3:
			return te.getWorld().getTileEntity(te.getPos().north());
		case 4:
			return te.getWorld().getTileEntity(te.getPos().up());
		case 5:
			return te.getWorld().getTileEntity(te.getPos().down());
		default:
			return null;
		}
	}

	/**
	 * Destroys the network and removes it from the registry.
	 */
	public void Destroy() {
		this.fillables.clear();
		for(IFluidPipe pipe : pipes){
			pipe.setNetwork(null);
		}
		this.pipes.clear();
		MainRegistry.allPipeNetworks.remove(this);
	}
	
	public void destroySoft() {
		this.fillables.clear();
		for(IFluidPipe pipe : pipes){
			pipe.setNetwork(null);
		}
		this.pipes.clear();
	}

	/**
	 * Sets the network fluid type, because that's how HBM pipes work. Also sets every pipe in the network to be this type.
	 * @param fluid - the fluid to set the network's fluid to
	 */
	public void setType(Fluid fluid) {
		//System.out.println("here");
		for(IFluidPipe pipe : this.pipes){
			pipe.setTypeTrue(fluid);
		}
		this.type = fluid;
	}

	/**
	 * Gets the network's fluid type.
	 * @return - the network's fluid type
	 */
	public Fluid getType() {
		return this.type;
	}

	/**
	 * Adds a pipe to the network. Used when doing stuff to all the network.
	 * @param pipe - the pipe to be added
	 * @return Whether it succeeded in adding the pipe.
	 */
	public boolean addPipe(IFluidPipe pipe) {
		if (pipe.getType() != null && pipe.getType() == this.getType()) {
			pipes.add(pipe);
			return pipes.add(pipe);
		}
		return false;
	}

	/**
	 * Remove a pipe from the network.
	 * @param pipe - the pipe to be removed
	 * @return if it successfully removed the pipe
	 */
	public boolean removePipe(IFluidPipe pipe) {
		if (pipes.contains(pipe)) {
			return pipes.remove(pipe);
		}
		return false;
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties() {
		return this.internalNetworkTank.getTankProperties();
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(resource != null && resource.getFluid() == this.type){
			return internalNetworkTank.fill(resource, doFill);
			
		}else{
			return 0;
		}
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(resource != null && resource.getFluid() == this.type)
			return internalNetworkTank.drain(resource.amount, doDrain);
		else
			return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return internalNetworkTank.drain(maxDrain, doDrain);
	}

}
