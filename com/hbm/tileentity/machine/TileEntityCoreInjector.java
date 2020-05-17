package com.hbm.tileentity.machine;

import com.hbm.forgefluid.FFUtils;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCoreInjector extends TileEntityMachineBase implements ITickable, IFluidHandler, ITankPacketAcceptor {

	public FluidTank[] tanks;
	public static final int range = 15;
	public int beam;
	
	public TileEntityCoreInjector() {
		super(0);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(128000);
		tanks[1] = new FluidTank(128000);
	}

	@Override
	public void update() {
		if(!world.isRemote) {

			beam = 0;
			
			EnumFacing dir = EnumFacing.getFront(this.getBlockMetadata());
			for(int i = 1; i <= range; i++) {

				int x = pos.getX() + dir.getFrontOffsetX() * i;
				int y = pos.getY() + dir.getFrontOffsetY() * i;
				int z = pos.getZ() + dir.getFrontOffsetZ() * i;
				BlockPos pos1 = new BlockPos(x, y, z);
				TileEntity te = world.getTileEntity(pos1);
				
				if(te instanceof TileEntityCore) {
					
					TileEntityCore core = (TileEntityCore)te;
					
					for(int t = 0; t < 2; t++) {
						//Drillgon200: I'm hoping this code will do exactly the same thing as the commented out code below it
						if(tanks[t].drain(core.tanks[t].fill(tanks[t].getFluid(), true), true) != null)
							core.markDirty();
						
						/*if(core.tanks[t].getTankType() == tanks[t].getTankType()) {
							
							int f = Math.min(tanks[t].getFill(), core.tanks[t].getMaxFill() - core.tanks[t].getFill());

							tanks[t].setFill(tanks[t].getFill() - f);
							core.tanks[t].setFill(core.tanks[t].getFill() + f);
							core.markDirty();
							
						} else if(core.tanks[t].getFluidAmount() == 0) {
							
							int f = Math.min(tanks[t].getFill(), core.tanks[t].getMaxFill() - core.tanks[t].getFill());

							tanks[t].setFill(tanks[t].getFill() - f);
							core.tanks[t].setFill(core.tanks[t].getFill() + f);
							core.markDirty();
						}*/
					}
					beam = i;
					break;
				}
				
				if(world.getBlockState(pos1).getBlock() != Blocks.AIR)
					break;
			}
			
			this.markDirty();

			PacketDispatcher.wrapper.sendToAllTracking(new FluidTankPacket(pos, tanks), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 250));
			PacketDispatcher.wrapper.sendToAllTracking(new AuxGaugePacket(pos, beam, 0), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 250));
		}
	}

	@Override
	public String getName() {
		return "container.dfcInjector";
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(resource == null)
			return 0;
		if(tanks[0].getFluid() == null || tanks[0].getFluid().getFluid() == resource.getFluid()){
			return tanks[0].fill(resource, doFill);
		}
		if(tanks[1].getFluid() == null || tanks[1].getFluid().getFluid() == resource.getFluid()){
			return tanks[1].fill(resource, doFill);
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
		if(tags.length == 2){
			tanks[0].readFromNBT(tags[0]);
			tanks[1].readFromNBT(tags[1]);
		}
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
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("tanks"))
			FFUtils.deserializeTankArray(compound.getTagList("tanks", 10), tanks);
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("tanks", FFUtils.serializeTankArray(tanks));
		return super.writeToNBT(compound);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		}
		return super.getCapability(capability, facing);
	}

}
