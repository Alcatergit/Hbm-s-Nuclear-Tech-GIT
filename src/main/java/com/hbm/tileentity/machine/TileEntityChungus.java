package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.inventory.MachineRecipes;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.Library;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.INBTPacketReceiver;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityChungus extends TileEntity implements ITickable, IFluidHandler, ISource, INBTPacketReceiver {

	public long power;
	public static final long maxPower = 100000000000L;
	private int turnTimer;
	public float rotor;
	public float lastRotor;
	
	public List<IConsumer> list1 = new ArrayList<>();
	
	public FluidTank[] tanks;
	public Fluid[] types;
	
	public TileEntityChungus() {
		
		tanks = new FluidTank[2];
		types = new Fluid[2];
		tanks[0] = new FluidTank(1000000000);
		tanks[1] = new FluidTank(1000000000);
		types[0] = ModForgeFluids.steam;
		types[1] = ModForgeFluids.spentsteam;
	}

	@Override
	public void update() {
		
		if(!world.isRemote) {
			
			Object[] outs = MachineRecipes.getTurbineOutput(types[0]);
			
			types[1] = (Fluid)outs[0];
			
			int processMax = (int) Math.ceil(tanks[0].getFluidAmount() / (Integer)outs[2]);				//the maximum amount of cycles total
			int processSteam = tanks[0].getFluidAmount() / (Integer)outs[2];								//the maximum amount of cycles depending on steam
			int processWater = (tanks[1].getCapacity() - tanks[1].getFluidAmount()) / (Integer)outs[1];		//the maximum amount of cycles depending on water
			
			int cycles = Math.min(processMax, Math.min(processSteam, processWater));
			
			tanks[0].drain((Integer)outs[2] * cycles, true);
			tanks[1].fill(new FluidStack(types[1], (Integer)outs[1] * cycles), true);
			
			power += (Integer)outs[3] * cycles;
			
			if(power > maxPower)
				power = maxPower;
			
			turnTimer--;
			
			if(cycles > 0)
				turnTimer = 25;
			
			this.fillFluidInit(tanks[1]);
			this.ffgeuaInit();
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setString("type", types[0].getName());
			data.setInteger("operational", turnTimer);
			this.networkPack(data, 150);
			
		} else {
			
			this.lastRotor = this.rotor;
			
			if(turnTimer > 0) {
				
				this.rotor += 25F;
				
				if(this.rotor >= 360) {
					this.rotor -= 360;
					this.lastRotor -= 360;
				}
				
				Random rand = world.rand;
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
				ForgeDirection side = dir.getRotation(ForgeDirection.UP);
				
				for(int i = 0; i < 10; i++) {
					world.spawnParticle(EnumParticleTypes.CLOUD,
							pos.getX() + 0.5 + dir.offsetX * (rand.nextDouble() + 1.25) + rand.nextGaussian() * side.offsetX * 0.65,
							pos.getY() + 2.5 + rand.nextGaussian() * 0.65,
							pos.getZ() + 0.5 + dir.offsetZ * (rand.nextDouble() + 1.25) + rand.nextGaussian() * side.offsetZ * 0.65,
							-dir.offsetX * 0.2, 0, -dir.offsetZ * 0.2);
				}
			}
		}
	}
	
	public void networkPack(NBTTagCompound nbt, int range) {
		PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(nbt, pos), new TargetPoint(this.world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), range));
	}

	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.power = data.getLong("power");
		this.turnTimer = data.getInteger("operational");
		this.types[0] = FluidRegistry.getFluid(data.getString("type"));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tanks[0].readFromNBT(nbt.getCompoundTag("tank0"));
		tanks[1].readFromNBT(nbt.getCompoundTag("tank1"));
		types[0] = FluidRegistry.getFluid(nbt.getString("types0"));
		types[1] = FluidRegistry.getFluid(nbt.getString("types1"));
		power = nbt.getLong("power");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setTag("tank0", tanks[0].writeToNBT(new NBTTagCompound()));
		nbt.setTag("tank1", tanks[1].writeToNBT(new NBTTagCompound()));
		nbt.setString("types0", types[0].getName());
		nbt.setString("types1", types[1].getName());
		nbt.setLong("power", power);
		return nbt;
	}

	@Override
	public void ffgeua(BlockPos pos, boolean newTact) {
		Library.ffgeua(new BlockPos.MutableBlockPos(pos), newTact, this, world);
	}

	@Override
	public void ffgeuaInit() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ffgeua(new BlockPos(pos.getX() - dir.offsetX * 11, pos.getY(), pos.getZ() - dir.offsetZ * 11), getTact());
	}

	public void fillFluidInit(FluidTank tank) {
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		dir = dir.getRotation(ForgeDirection.UP);

		fillFluid(pos.getX() + dir.offsetX * 3, pos.getY(), pos.getZ() + dir.offsetZ * 3, tank);
		fillFluid(pos.getX() + dir.offsetX * -3, pos.getY(), pos.getZ() + dir.offsetZ * -3, tank);
	}

	public void fillFluid(int x, int y, int z, FluidTank tank) {
		FFUtils.fillFluid(this, tank, world, new BlockPos(x, y, z), tank.getCapacity());
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
		return list1;
	}

	@Override
	public void clearList() {
		this.list1.clear();
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public boolean getTact(){
		return world.getTotalWorldTime() % 2 == 0;
	}

	@Override
	public IFluidTankProperties[] getTankProperties(){
		return new IFluidTankProperties[]{tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill){
		if(resource != null && resource.getFluid() == types[0]){
			return tanks[0].fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain){
		if(resource != null && resource.getFluid() == types[1]){
			return tanks[1].drain(resource, doDrain);
		}
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain){
		return tanks[1].drain(maxDrain, doDrain);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing){
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing){
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		}
		return super.getCapability(capability, facing);
	}
}