package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.interfaces.Untested;
import com.hbm.inventory.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemForgeFluidIdentifier;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.Library;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.FluidTypePacketTest;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMachineLargeTurbine extends TileEntityMachineBase implements ITickable, IEnergyGenerator, IFluidHandler, ITankPacketAcceptor {

	public long power;
	public static final long maxPower = 100000000;
	public int age = 0;
	public FluidTank[] tanks;
	public Fluid[] types = new Fluid[2];

	private boolean shouldTurn;
	public float rotor;
	public float lastRotor;
	
	public TileEntityMachineLargeTurbine() {
		super(7);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(512000);
		tanks[1] = new FluidTank(10240000);
		types[0] = ModForgeFluids.steam;
		types[1] = ModForgeFluids.spentsteam;
	}

	@Untested
	@Override
	public void update() {
		if(!world.isRemote) {

			age++;
			if(age >= 2)
			{
				age = 0;
			}

			fillFluidInit(tanks[1]);
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
			this.sendPower(world, pos.add(dir.offsetX * -4, 0, dir.offsetZ * -4), dir.getOpposite());
			
			if(inventory.getStackInSlot(0).getItem() == ModItems.forge_fluid_identifier && inventory.getStackInSlot(1).isEmpty()){
				Fluid f = ItemForgeFluidIdentifier.getType(inventory.getStackInSlot(0));
				if(isValidFluidForTank(0, new FluidStack(f, 1000))){
					types[0] = f;
					if(tanks[0].getFluid() != null && tanks[0].getFluid().getFluid() != types[0])
						tanks[0].setFluid(null);
					inventory.setStackInSlot(1, inventory.getStackInSlot(0));
					inventory.setStackInSlot(0, ItemStack.EMPTY);
				}
			}
			
			if(inputValidForTank(0, 2))
				FFUtils.fillFromFluidContainer(inventory, tanks[0], 2, 3);
			power = Library.chargeItemsFromTE(inventory, 4, power, maxPower);
			
			boolean operational = false;

			Object[] outs = MachineRecipes.getTurbineOutput(types[0]);

			if(outs == null) {
				types[1] = null;
				tanks[1].setFluid(null);
			} else {
				types[1] = (Fluid) outs[0];
				if(tanks[1].getFluid() != null && tanks[1].getFluid().getFluid() != types[1])
					tanks[1].setFluid(null);

				int processMax = (int) Math.ceil(Math.ceil(tanks[0].getFluidAmount() / 10F) / (Integer)outs[2]);		//the maximum amount of cycles based on the 10% cap
				int processSteam = tanks[0].getFluidAmount() / (Integer)outs[2];							//the maximum amount of cycles depending on steam
				int processWater = (tanks[1].getCapacity() - tanks[1].getFluidAmount()) / (Integer)outs[1];	//the maximum amount of cycles depending on water

				int cycles = Math.min(processMax, Math.min(processSteam, processWater));

				tanks[0].drain((Integer)outs[2] * cycles, true);
				tanks[1].fill(new FluidStack(types[1], (Integer)outs[1] * cycles), true);

				power += (Integer)outs[3] * cycles;

				if(power > maxPower)
					power = maxPower;
				if(cycles > 0)
					operational = true;
			}

			FFUtils.fillFluidContainer(inventory, tanks[1], 5, 6);

			PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos, new FluidTank[]{tanks[0], tanks[1]}), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 20));
			PacketDispatcher.wrapper.sendToAllAround(new FluidTypePacketTest(pos.getX(), pos.getY(), pos.getZ(), new Fluid[]{types[0], types[1]}), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 20));
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setBoolean("operational", operational);
			this.networkPack(data, 50);
		} else {

			this.lastRotor = this.rotor;

			if(shouldTurn) {

				this.rotor += 15F;

				if(this.rotor >= 360) {
					this.rotor -= 360;
					this.lastRotor -= 360;
				}
			}
		}
	}
	
	protected boolean inputValidForTank(int tank, int slot) {
		if(inventory.getStackInSlot(slot) != ItemStack.EMPTY && tanks[tank] != null) {
			FluidStack f = FluidUtil.getFluidContained(inventory.getStackInSlot(slot));
			if(f != null && f.getFluid() == types[tank])
				return true;
		}
		return false;
	}
	
	private boolean isValidFluidForTank(int tank, FluidStack stack) {
		if(stack == null || tanks[tank] == null)
			return false;
		return stack.getFluid() == ModForgeFluids.steam || stack.getFluid() == ModForgeFluids.hotsteam || stack.getFluid() == ModForgeFluids.superhotsteam || stack.getFluid() == ModForgeFluids.ultrahotsteam;
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.power = data.getLong("power");
		this.shouldTurn = data.getBoolean("operational");
	}

	public long getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if(compound.hasKey("tankType0"))
			types[0] = FluidRegistry.getFluid(compound.getString("tankType0"));
		else
			types[0] = null;
		if(compound.hasKey("tankType1"))
			types[1] = FluidRegistry.getFluid(compound.getString("tankType1"));
		else
			types[1] = null;
		
		FFUtils.deserializeTankArray(compound.getTagList("tanks", 10), tanks);
		power = compound.getLong("power");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("tanks", FFUtils.serializeTankArray(tanks));
		compound.setLong("power", power);
		if(types[0] != null)
			compound.setString("tankType0", types[0].getName());
		if(types[1] != null)
			compound.setString("tankType1", types[1].getName());
		return super.writeToNBT(compound);
	}

	@Override
	public String getName() {
		return "container.machineLargeTurbine";
	}

	public void fillFluidInit(FluidTank type) {

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		dir = dir.getRotation(ForgeDirection.UP);

		fillFluid(pos.getX() + dir.offsetX * 2, pos.getY(), pos.getZ() + dir.offsetZ * 2, type);
		fillFluid(pos.getX() + dir.offsetX * -2, pos.getY(), pos.getZ() + dir.offsetZ * -2, type);
	}

	public void fillFluid(int x, int y, int z, FluidTank type) {
		FFUtils.fillFluid(this, type, world, new BlockPos(x, y, z), 10239000);
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
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(resource != null && resource.getFluid() == types[0]){
			return tanks[0].fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(resource != null && resource.getFluid() == types[1]){
			return tanks[1].drain(resource, doDrain);
		}
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return tanks[1].drain(maxDrain, doDrain);
	}

	@Override
	public void recievePacket(NBTTagCompound[] tags) {
		if(tags.length == 2){
			tanks[0].readFromNBT(tags[0]);
			tanks[1].readFromNBT(tags[1]);
		}
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return true;
		}
		return super.hasCapability(capability, facing);
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
