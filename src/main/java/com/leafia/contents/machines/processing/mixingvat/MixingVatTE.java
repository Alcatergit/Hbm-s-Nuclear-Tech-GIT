package com.leafia.contents.machines.processing.mixingvat;

import api.hbm.energy.IEnergyUser;
import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.leafia.contents.machines.processing.mixingvat.container.MixingVatContainer;
import com.leafia.contents.machines.processing.mixingvat.container.MixingVatNclrGUI;
import com.leafia.contents.machines.reactors.msr.components.MSRTEBase;
import com.leafia.contents.machines.reactors.msr.components.element.MSRElementTE.MSRFuel;
import com.leafia.dev.blockitems.LeafiaQuickModel;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.container_utility.LeafiaPacketReceiver;
import com.leafia.dev.math.FiaMatrix;
import com.llib.group.LeafiaMap;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
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

public class MixingVatTE extends TileEntityMachineBase implements LeafiaQuickModel, LeafiaPacketReceiver, ITickable, IEnergyUser, IFluidHandler, IGUIProvider {
	public long power;
	public static final int maxPower = 100_000;
	static int maxProgress = 20*3;
	int progress = 0;
	public FluidTank tankNc0 = new FluidTank(4000);
	public FluidTank tankNc1 = new FluidTank(4000);

	public MixingVatTE() {
		super(3+10+8);
	}
	public void fillFluid(BlockPos pos1,FluidTank tank) {
		FFUtils.fillFluid(this, tank, world, pos1, 500);
	}
	public long getProgressScaled(long i) {
		return (progress * i) / maxProgress;
	}

	@Override
	public String getName() {
		return "tile.mixingvat.name";
	}

	@Override
	public String _resourcePath() {
		return "mixingvat";
	}

	@Override
	public String _assetPath() {
		return "xenoulexi/mixingvat";
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
	@SideOnly(Side.CLIENT)
	public TileEntitySpecialRenderer<TileEntity> _renderer() {
		return new MixingVatRender();
	}

	@Override
	public Block _block() {
		return ModBlocks.mixingvat;
	}

	@Override
	public double _sizeReference() {
		return 5;
	}

	@Override
	public double _itemYoffset() {
		return -0.07;
	}

	@Override
	public <T> T getCapability(Capability<T> capability,EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		} else {
			return super.getCapability(capability, facing);
		}
	}
	EnumFacing getFacingForward() {
		BlockDummyable dummyable = (BlockDummyable)getBlockType();
		FiaMatrix mat = dummyable.getRotationMat(world,pos);
		if (mat == null) return null;
		return EnumFacing.getFacingFromVector((float)mat.frontVector.x,(float)mat.frontVector.y,(float)mat.frontVector.z);
	}
	EnumFacing getFacing() {
		BlockDummyable dummyable = (BlockDummyable)getBlockType();
		FiaMatrix mat = dummyable.getRotationMat(world,pos);
		if (mat == null) return null;
		mat = mat.rotateY(180);
		return EnumFacing.getFacingFromVector((float)mat.frontVector.x,(float)mat.frontVector.y,(float)mat.frontVector.z);
	}
	@Override
	public boolean hasCapability(Capability<?> capability,EnumFacing facing) {
		if(capability == CapabilityEnergy.ENERGY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return facing == null || facing.equals(getFacing());
		return super.hasCapability(capability, facing);
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{tankNc0.getTankProperties()[0], tankNc1.getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource,boolean doFill) {
		if (!nuclearMode) {

		} else {
			if (resource.getFluid().equals(ModForgeFluids.FLUORIDE))
				return tankNc0.fill(resource,doFill);
		}
		return 0;
	}
	@Override
	public @Nullable FluidStack drain(FluidStack resource,boolean doDrain) {
		return null;
	}
	@Override
	public @Nullable FluidStack drain(int maxDrain,boolean doDrain) {
		return null;
	}
	public @Nullable FluidStack drainOut(FluidStack resource,boolean doDrain) {
		if (!nuclearMode) {

		} else {
			return tankNc1.drain(resource,doDrain);
		}
		return null;
	}
	public @Nullable FluidStack drainOut(int maxDrain,boolean doDrain) {
		if (!nuclearMode) {

		} else {
			return tankNc1.drain(maxDrain,doDrain);
		}
		return null;
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
		for (int i = 15; i < 21; i++) {
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
			EnumFacing facing = getFacingForward(); {
				updateStandardConnections(world,pos);
				updateStandardConnections(world,pos.offset(facing.rotateY()));
				updateStandardConnections(world,pos.offset(facing,2));
				updateStandardConnections(world,pos.offset(facing,2).offset(facing.rotateY()));
			}
			power = Library.chargeTEFromItems(inventory,0,power,maxPower);
			FluidStack stack = tankNc0.getFluid();
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
				if (tankNc1.fill(mixed,false) >= 1000) {
					power -= 1000/20;
					progress++;
					if (progress >= maxProgress) {
						progress = 0;
						tankNc1.fill(mixed,true);
						tankNc0.drain(1000,true);
						for (int i = 15; i < 21; i++) {
							if (getFuelType(inventory.getStackInSlot(i)) != null)
								inventory.getStackInSlot(i).shrink(1);
						}
					}
				} else
					progress = 0;
			} else
				progress = 0;
			if (!nuclearMode) {

			} else {
				fillFluid(pos.offset(facing,2).offset(facing.rotateY(),-1),tankNc1);
				fillFluid(pos.offset(facing,2).offset(facing.rotateY(),2),tankNc1);
			}
			LeafiaPacket._start(this)
					.__write(0,power)
					.__write(1,nuclearMode)
					.__write(2,progress)
					.__write(6,tankNc0.writeToNBT(new NBTTagCompound()))
					.__write(7,tankNc1.writeToNBT(new NBTTagCompound()))
					.__sendToAffectedClients();
		}
	}

	public boolean nuclearMode = true;

	@Override
	public Container provideContainer(int ID,EntityPlayer player,World world,int x,int y,int z) {
		return new MixingVatContainer(player.inventory,this);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiScreen provideGUI(int ID,EntityPlayer player,World world,int x,int y,int z) {
		return new MixingVatNclrGUI(player.inventory,this);
	}

	@Override
	public String getPacketIdentifier() {
		return "MixingVat";
	}

	@Override
	public void onReceivePacketLocal(byte key,Object value) {
		switch(key) {
			case 0:
				power = (long)value;
				break;
			case 1:
				nuclearMode = (boolean)value;
				break;
			case 2:
				progress = (int)value;
				break;
			case 6:
				tankNc0.readFromNBT((NBTTagCompound)value);
				break;
			case 7:
				tankNc1.readFromNBT((NBTTagCompound)value);
				break;
		}
	}

	@Override
	public void onReceivePacketServer(byte key,Object value,EntityPlayer plr) {
		if (key == 0)
			tankNc1.drain(5000,true);
	}

	@Override
	public void onPlayerValidate(EntityPlayer plr) {

	}
}
