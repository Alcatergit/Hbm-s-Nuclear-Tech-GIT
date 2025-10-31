package com.leafia.contents.machines.reactors.msr;

import api.hbm.energy.IEnergyUser;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.ModItems.Materials.Nuggies;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.leafia.contents.machines.reactors.msr.components.MSRTEBase;
import com.leafia.contents.machines.reactors.msr.components.element.MSRElementTE.MSRFuel;
import com.leafia.contents.machines.reactors.msr.container.MSRMixerContainer;
import com.leafia.contents.machines.reactors.msr.container.MSRMixerGUI;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.container_utility.LeafiaPacketReceiver;
import com.llib.group.LeafiaMap;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Map.Entry;

public class MSRMixerTE extends TileEntityMachineBase implements ITickable, LeafiaPacketReceiver, IGUIProvider, IEnergyUser, IFluidHandler {
	public FluidTank tank0 = new FluidTank(4000);
	public FluidTank tank1 = new FluidTank(4000);
	public long power;
	public static final int maxPower = 100_000;
	static int maxProgress = 20*3;
	int progress = 0;

	public MSRMixerTE() {
		super(9);
	}
	public long getProgressScaled(long i) {
		return (progress * i) / maxProgress;
	}

	@Override
	public Container provideContainer(int ID,EntityPlayer player,World world,int x,int y,int z) {
		return new MSRMixerContainer(player.inventory,this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID,EntityPlayer player,World world,int x,int y,int z) {
		return new MSRMixerGUI(player.inventory,this);
	}

	@Override
	public String getName() {
		return "tile.msrmixer.name";
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}
	@Override
	public long getPower() {
		return power;
	}
	@Override
	public long getMaxPower() {
		return maxPower;
	}
	public long getPowerRemainingScaled(long i) {
		return (power * i) / maxPower;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{tank0.getTankProperties()[0], tank1.getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource,boolean doFill) {
		if (resource.getFluid().equals(ModForgeFluids.FLUORIDE))
			return tank0.fill(resource,doFill);
		return 0;
	}

	@Override
	public <T> T getCapability(Capability<T> capability,EnumFacing facing){
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing){
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public @Nullable FluidStack drain(FluidStack resource,boolean doDrain) {
		return tank1.drain(resource,doDrain);
	}

	@Override
	public @Nullable FluidStack drain(int maxDrain,boolean doDrain) {
		return tank1.drain(maxDrain,doDrain);
	}

	MSRFuel getFuelType(ItemStack stack) {
		Item item = stack.getItem();
		/*
		if (item == Nuggies.nugget_uranium_fuel)
			return MSRFuel.meu;
		 */
		for (MSRFuel fuel : MSRFuel.values()) {
			for (Item i : fuel.items)
				if (i.equals(item))
					return fuel;
		}
		if (!stack.isEmpty()) {
			for (int oreID : OreDictionary.getOreIDs(stack)) {
				String id = OreDictionary.getOreName(oreID);
				for (MSRFuel fuel : MSRFuel.values()) {
					for (String dict : fuel.dicts)
						if (dict.equals(id))
							return fuel;
				}
			}
		}
		return null;
	}

	Map<String,Double> createMixture() {
		Map<String,Double> mixture = new LeafiaMap<>();
		for (int i = 3; i < 9; i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			MSRFuel fuel = getFuelType(stack);
			if (fuel != null)
				mixture.put(fuel.name(),mixture.getOrDefault(fuel.name(),0d)+1);
		}
		return mixture;
	}

	@Override
	public void update() {
		if (!world.isRemote) {
			power = Library.chargeTEFromItems(inventory,0,power,maxPower);
			FluidStack stack = tank0.getFluid();
			Map<String,Double> itemMixture = createMixture();
			if (stack != null && !itemMixture.isEmpty() && stack.amount >= 1000 && power > 1000/20) {
				FluidStack mixed = stack.copy();
				mixed.amount = 1000;
				Map<String,Double> mixture = MSRTEBase.readMixture(MSRTEBase.nbtProtocol(mixed.tag));
				for (Entry<String,Double> entry : itemMixture.entrySet())
					mixture.put(entry.getKey(),mixture.getOrDefault(entry.getKey(),0d)+entry.getValue());
				NBTTagCompound tag = new NBTTagCompound();
				tag.setTag("itemMixture",MSRTEBase.writeMixture(mixture));
				mixed.tag = tag;
				if (tank1.fill(mixed,false) >= 1000) {
					power -= 1000/20;
					progress++;
					if (progress >= maxProgress) {
						progress = 0;
						tank1.fill(mixed,true);
						tank0.drain(1000,true);
						for (int i = 3; i < 9; i++) {
							if (getFuelType(inventory.getStackInSlot(i)) != null)
								inventory.getStackInSlot(i).shrink(1);
						}
					}
				} else
					progress = 0;
			} else
				progress = 0;
			fillFluid(pos.add(getDirection().getDirectionVec()),tank1);
			LeafiaPacket._start(this)
					.__write(0,power)
					.__write(1,tank0.writeToNBT(new NBTTagCompound()))
					.__write(2,tank1.writeToNBT(new NBTTagCompound()))
					.__write(3,progress)
					.__sendToAffectedClients();
		}
	}
	EnumFacing getDirection() {
		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() instanceof MSRMixerTest)
			return state.getValue(BlockHorizontal.FACING);
		return EnumFacing.NORTH;
	}
	public void fillFluid(BlockPos pos1,FluidTank tank) {
		FFUtils.fillFluid(this, tank, world, pos1, 500);
	}

	@Override
	public String getPacketIdentifier() {
		return "MSRMixer";
	}

	@Override
	public void onReceivePacketLocal(byte key,Object value) {
		switch(key) {
			case 0:
				power = (long)value;
				break;
			case 1:
				tank0.readFromNBT((NBTTagCompound)value);
				break;
			case 2:
				tank1.readFromNBT((NBTTagCompound)value);
				break;
			case 3:
				progress = (int)value;
				break;
		}
	}

	@Override
	public void onReceivePacketServer(byte key,Object value,EntityPlayer plr) {
		if (key == 0)
			tank1.drain(5000,true);
	}

	@Override
	public void onPlayerValidate(EntityPlayer plr) {

	}
}
