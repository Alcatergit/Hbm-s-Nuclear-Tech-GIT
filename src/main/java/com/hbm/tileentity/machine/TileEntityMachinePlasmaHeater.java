package com.hbm.tileentity.machine;

import java.util.Arrays;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineITER;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.Library;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
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
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMachinePlasmaHeater extends TileEntityMachineBase implements ITickable, IFluidHandler, ITankPacketAcceptor, IEnergyUser {

	public long power;
	public static final long maxPower = 10000000000L;

	public FluidTank[] tanks;
	public Fluid[] types = new Fluid[]{ModForgeFluids.deuterium, ModForgeFluids.tritium};
	public FluidTank plasma;
	public Fluid plasmaType = ModForgeFluids.plasma_dt;
	
	public TileEntityMachinePlasmaHeater() {
		super(1);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(16000);
		tanks[1] = new FluidTank(16000);
		plasma = new FluidTank(64000);
	}

	@Override
	public String getName() {
		return "container.plasmaHeater";
	}

	@Override
	public void update() {
		updateType();
		if(!world.isRemote) {

			if(this.world.getTotalWorldTime() % 20 == 0)
				this.updateConnections();

			/// START Managing all the internal stuff ///
			power = Library.chargeTEFromItems(inventory, 0, power, maxPower);

			int maxConv = 50;
			int powerReq = 100000;

			int convert = Math.min(tanks[0].getFluidAmount(), tanks[1].getFluidAmount());
			convert = Math.min(convert, (plasma.getCapacity() - plasma.getFluidAmount()) * 2);
			convert = Math.min(convert, maxConv);
			convert = (int) Math.min(convert, power / powerReq);
			convert = Math.max(0, convert);

			if(convert > 0 && plasmaType != null) {

				tanks[0].drain(convert, true);
				tanks[1].drain(convert, true);

				plasma.fill(new FluidStack(plasmaType, convert*2), true);
				power -= convert * powerReq;
				this.markDirty();
			}
			/// END Managing all the internal stuff ///

			/// START Loading plasma into the ITER ///

			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
			int dist = 11;

			if(world.getBlockState(new BlockPos(pos.getX() + dir.offsetX * dist, pos.getY() + 2, pos.getZ() + dir.offsetZ * dist)).getBlock() == ModBlocks.iter) {
				int[] pos1 = ((MachineITER)ModBlocks.iter).findCore(world, pos.getX() + dir.offsetX * dist, pos.getY() + 2, pos.getZ() + dir.offsetZ * dist);
				Fluid type = plasma.getFluid() == null ? null : plasma.getFluid().getFluid();
				
				if(pos1 != null) {
					TileEntity te = world.getTileEntity(new BlockPos(pos1[0], pos1[1], pos1[2]));

					if(te instanceof TileEntityITER) {
						TileEntityITER iter = (TileEntityITER)te;

						if(iter.plasma.getFluidAmount() == 0 && type != null) {
							iter.plasmaType = type;
						}

						if(iter.isOn) {

							if(type != null && iter.plasmaType == type) {

								int toLoad = Math.min(iter.plasma.getCapacity() - iter.plasma.getFluidAmount(), this.plasma.getFluidAmount());
								toLoad = Math.min(toLoad, 40);
								this.plasma.drain(toLoad, true);
								iter.plasma.fill(new FluidStack(type, toLoad), true);
								this.markDirty();
								iter.markDirty();
							}
						}
					}
				}
			}

			/// END Loading plasma into the ITER ///

			/// START Notif packets ///
			PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos, new FluidTank[]{tanks[0], tanks[1], plasma}), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 20)); 
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			this.networkPack(data, 50);
			/// END Notif packets ///
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
	}

	private void updateConnections()  {

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection side = dir.getRotation(ForgeDirection.UP);
		
		for(int i = 1; i < 4; i++) {
			for(int j = -1; j < 2; j++) {
				this.trySubscribe(world, pos.add(side.offsetX * j + dir.offsetX * 2, i, side.offsetZ * j + dir.offsetZ * 2), j < 0 ? ForgeDirection.DOWN : ForgeDirection.UP);
			}
		}
	}
	
	private void updateType() {

		//if(plasma.getFluidAmount() > 0)
		//	return;
		this.types[0] = tanks[0].getFluid() == null ? null : tanks[0].getFluid().getFluid();
		this.types[1] = tanks[1].getFluid() == null ? null : tanks[1].getFluid().getFluid();

		List<Fluid> types = Arrays.asList(this.types[0], this.types[1]);

		if(types.contains(ModForgeFluids.deuterium) && types.contains(ModForgeFluids.tritium)) {
			plasmaType = ModForgeFluids.plasma_dt;
			return;
		}
		if(types.contains(ModForgeFluids.deuterium) && types.contains(ModForgeFluids.hydrogen)) {
			plasmaType = ModForgeFluids.plasma_hd;
			return;
		}
		if(types.contains(ModForgeFluids.hydrogen) && types.contains(ModForgeFluids.tritium)) {
			plasmaType = ModForgeFluids.plasma_ht;
			return;
		}
		if(types.contains(ModForgeFluids.xenon) && types.contains(ModForgeFluids.mercury)) {
			plasmaType = ModForgeFluids.plasma_xm;
			return;
		}
		if(types.contains(ModForgeFluids.puf6) && types.contains(ModForgeFluids.tritium)) {
			plasmaType = ModForgeFluids.plasma_put;
			return;
		}
		if(types.contains(ModForgeFluids.balefire) && types.contains(ModForgeFluids.amat)) {
			plasmaType = ModForgeFluids.plasma_bf;
			return;
		}

		plasmaType = null;
	}
	
	public long getPowerScaled(int i) {
		return (power * i) / maxPower;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("power", power);
		compound.setTag("fuel_1", tanks[0].writeToNBT(new NBTTagCompound()));
		compound.setTag("fuel_2", tanks[1].writeToNBT(new NBTTagCompound()));
		compound.setTag("plasma", plasma.writeToNBT(new NBTTagCompound()));
		if(plasmaType != null)
			compound.setString("plasma_type", plasmaType.getName());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		power = compound.getLong("power");
		tanks[0].readFromNBT(compound.getCompoundTag("fuel_1"));
		tanks[1].readFromNBT(compound.getCompoundTag("fuel_2"));
		plasma.readFromNBT(compound.getCompoundTag("plasma"));
		plasmaType = FluidRegistry.getFluid(compound.getString("plasma_type"));
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
	
	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0], plasma.getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(resource != null){
			if(tanks[0].getFluid() == null || tanks[0].getFluid().getFluid() == resource.getFluid()){
				if(tanks[1].getFluid() != null && tanks[1].getFluid().getFluid() == resource.getFluid()){
					return tanks[1].fill(resource, doFill);
				}
				
				return tanks[0].fill(resource, doFill);
			}
			if(tanks[1].getFluid() == null || tanks[1].getFluid().getFluid() == resource.getFluid()){
				return tanks[1].fill(resource, doFill);
			}
			
		}
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public void recievePacket(NBTTagCompound[] tags) {
		if(tags.length == 3){
			tanks[0].readFromNBT(tags[0]);
			tanks[1].readFromNBT(tags[1]);
			plasma.readFromNBT(tags[2]);
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

}