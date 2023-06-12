package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.MachineRecipes;
import com.hbm.inventory.MachineRecipes.GasCentOutput;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
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

public class TileEntityMachineGasCent extends TileEntityMachineBase implements ITickable, IEnergyUser, ITankPacketAcceptor, IFluidHandler {

	
	public long power;
	public int progress;
	public boolean isProgressing;
	public static final int maxPower = 100000;
	public static final int processingSpeed = 200;
	public boolean needsUpdate = false;
	
	public FluidTank tank;
	
	//private static final int[] slots_top = new int[] {3};
	//private static final int[] slots_bottom = new int[] {5, 6, 7, 8};
	//private static final int[] slots_side = new int[] {0, 3};
	
	private String customName;
	
	public TileEntityMachineGasCent() {
		super(9);
		tank = new FluidTank(8000);
	}
	
	public String getName() {
		return "container.gasCentrifuge";
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		power = nbt.getLong("powerTime");
		progress = nbt.getShort("CookTime");
		tank.readFromNBT(nbt);
		if(nbt.hasKey("inventory"))
			inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
		
		super.readFromNBT(nbt);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setLong("powerTime", power);
		nbt.setShort("cookTime", (short) progress);
		tank.writeToNBT(nbt);
		nbt.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(nbt);
	}
	
	public int getCentrifugeProgressScaled(int i) {
		return (progress * i) / processingSpeed;
	}
	
	public long getPowerRemainingScaled(int i) {
		return (power * i) / maxPower;
	}
	
	private boolean canProcess() {
		
		if(power > 0 && this.tank.getFluidAmount() >= MachineRecipes.getFluidConsumedGasCent(tank.getFluid() == null ? null : tank.getFluid().getFluid())) {
			
			List<GasCentOutput> list = MachineRecipes.getGasCentOutput(tank.getFluid() == null ? null : tank.getFluid().getFluid());
			
			if(list == null)
				return false;
			
			if(list.size() < 1 || list.size() > 4)
				return false;
			
			for(int i = 0; i < list.size(); i++) {
				
				int slot = i + 5;
				
				if(inventory.getStackInSlot(slot).isEmpty())
					continue;
				
				if(inventory.getStackInSlot(slot).getItem() == list.get(i).output.getItem() &&
						inventory.getStackInSlot(slot).getItemDamage() == list.get(i).output.getItemDamage() &&
						inventory.getStackInSlot(slot).getCount() + list.get(i).output.getCount() <= inventory.getStackInSlot(slot).getMaxStackSize())
					continue;
				
				return false;
			}
			
			return true;
		}
		
		return false;
	}
	
	private void process() {

		List<GasCentOutput> out = MachineRecipes.getGasCentOutput(tank.getFluid() == null ? null : tank.getFluid().getFluid());
		this.progress = 0;
		tank.drain(MachineRecipes.getFluidConsumedGasCent(tank.getFluid() == null ? null : tank.getFluid().getFluid()), true);
		
		List<GasCentOutput> random = new ArrayList<GasCentOutput>();
		
		for(int i = 0; i < out.size(); i++) {
			for(int j = 0; j < out.get(i).weight; j++) {
				random.add(out.get(i));
			}
		}
		
		Collections.shuffle(random);
		
		GasCentOutput result = random.get(world.rand.nextInt(random.size()));
		
		int slot = result.slot + 4;
		
		if(inventory.getStackInSlot(slot).isEmpty()) {
			inventory.setStackInSlot(slot, result.output.copy());
		} else {
			inventory.getStackInSlot(slot).grow(result.output.getCount());
		}
	}
	
	@Override
	public void update() {
		
		if(!world.isRemote) {
			
			if (needsUpdate) {
				needsUpdate = false;
			}
			this.updateConnectionsExcept(world, pos, Library.POS_Y);
			PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos.getX(), pos.getY(), pos.getZ(), new FluidTank[] {tank}), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
			
			power = Library.chargeTEFromItems(inventory, 0, power, maxPower);
			
			//First number doesn't matter, there's only one tank.
			if(this.inputValidForTank(-1, 3))
				FFUtils.fillFromFluidContainer(inventory, tank, 3, 4);
			
			
			
			if(canProcess()) {
				
				isProgressing = true;
				
				this.progress++;
				
				this.power -= 200;
				
				if(this.power < 0)
					power = 0;
				
				if(progress >= processingSpeed) {
					process();
				}
				
			} else {
				isProgressing = false;
				this.progress = 0;
			}

			PacketDispatcher.wrapper.sendToAll(new LoopedSoundPacket(pos.getX(), pos.getY(), pos.getZ()));
			detectAndSendChanges();
		}

		
	}
	
	private long detectPower;
	private int detectProgress;
	private boolean detectIsProgressing;
	private FluidTank detectTank;
	
	private void detectAndSendChanges(){
		boolean mark = false;
		if(detectPower != power){
			detectPower = power;
			mark = true;
		}
		if(detectProgress != progress){
			detectProgress = progress;
			mark = true;
		}
		if(detectIsProgressing != isProgressing){
			detectIsProgressing = isProgressing;
			mark = true;
		}
		if(!FFUtils.areTanksEqual(tank, detectTank)){
			detectTank = FFUtils.copyTank(tank);
			needsUpdate = true;
			mark = true;
		}
		PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos.getX(), pos.getY(), pos.getZ(), power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
		PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(pos.getX(), pos.getY(), pos.getZ(), progress, 0), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
		PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(pos.getX(), pos.getY(), pos.getZ(), isProgressing ? 1 : 0, 1), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 100));
		if(mark)
			markDirty();
	}

	protected boolean inputValidForTank(int tank, int slot){
		if(!inventory.getStackInSlot(slot).isEmpty()){
			if(isValidFluid(FluidUtil.getFluidContained(inventory.getStackInSlot(slot)))){
				return true;
			}
		}
		return false;
	}
	
	private boolean isValidFluid(FluidStack stack) {
		if(stack == null)
			return false;
		return MachineRecipes.getFluidConsumedGasCent(stack.getFluid()) != 0;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int amount) {
		return slot > 3;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(EnumFacing e){
		return new int[]{0, 3, 4, 5, 6, 7, 8};
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(pos, pos.add(1, 4, 1));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
	public void setPower(long i) {
		power = i;
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
	public void recievePacket(NBTTagCompound[] tags) {
		if(tags.length != 1){
			return;
		} else {
			tank.readFromNBT(tags[0]);
		}
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{tank.getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (isValidFluid(resource)) {
			return tank.fill(resource, doFill);
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
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		} else {
			return super.getCapability(capability, facing);
		}
	}
}
