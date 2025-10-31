package com.leafia.contents.machines.powercores.dfc.exchanger;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.HeatRecipes;
import com.hbm.items.machine.ItemForgeFluidIdentifier;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.machine.TileEntityCore;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.Tuple.Quartet;
import com.hbm.util.Tuple.Triplet;
import com.leafia.contents.machines.powercores.dfc.DFCBaseTE;
import com.leafia.dev.LeafiaDebug;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.llib.group.LeafiaSet;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DFCExchangerTE extends DFCBaseTE implements ITickable, IGUIProvider, IFluidHandler {
	public FluidTank input = new FluidTank(2560_000);
	public FluidTank output = new FluidTank(2560_000);
	public Fluid inputFluid = ModForgeFluids.COOLANT;
	public Fluid outputFluid = ModForgeFluids.HOTCOOLANT;
	int inAmt = 1;
	int outAmt = 1;
	Quartet<Integer,Fluid,Integer,Integer> getBoiledFluid(Fluid f,int compression) {
		int comp = 0;
		Fluid output = f;
		int in = 0;
		int out = 0;
		for (int i = 0; i < compression; i++) {
			Fluid of = HeatRecipes.getBoilFluid(output);
			if (of != null) {
				in += HeatRecipes.getInputAmountHot(output);
				out += HeatRecipes.getOutputAmountHot(output);
				output = of;
				comp++;
			} else break;
		}
		return new Quartet<>(comp,output,in,out);
	}
	public int compression = 1;
	public int amountToHeat = 1;
	public int tickDelay = 1;
	public void setInput(Fluid f,int compression) {
		Quartet<Integer,Fluid,Integer,Integer> quartlet = getBoiledFluid(f,compression);
		if (quartlet.getA() > 0) {
			if (inputFluid != f)
				input.drain(input.getCapacity(),true);
			if (outputFluid != f)
				output.drain(output.getCapacity(),true);
			this.compression = quartlet.getA();
			inputFluid = f;
			outputFluid = quartlet.getB();
			inAmt = quartlet.getC();
			outAmt = quartlet.getD();
		}
	}
	@Override
	public void slotContentsChanged(int slot,ItemStack newStack) {
		super.slotContentsChanged(slot,newStack);
		if (newStack.getItem() instanceof ItemForgeFluidIdentifier)
			setInput(ItemForgeFluidIdentifier.getType(newStack),compression);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("tankI",input.writeToNBT(new NBTTagCompound()));
		compound.setTag("tankO",output.writeToNBT(new NBTTagCompound()));
		compound.setString("fluid",inputFluid.getName());
		compound.setInteger("amount",amountToHeat);
		compound.setInteger("delay",tickDelay);
		compound.setByte("compression",(byte)compression);
		return super.writeToNBT(compound);
	}
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("amount"))
			amountToHeat = compound.getInteger("amount");
		if (compound.hasKey("delay"))
			tickDelay = compound.getInteger("delay");
		if (compound.hasKey("compression"))
			compression = compound.getByte("compression");
		if (compound.hasKey("fluid"))
			setInput(FluidRegistry.getFluid(compound.getString("fluid")),compression);
		if (compound.hasKey("tankI"))
			input = input.readFromNBT(compound.getCompoundTag("tankI"));
		if (compound.hasKey("tankO"))
			output = output.readFromNBT(compound.getCompoundTag("tankO"));
	}

	public DFCExchangerTE() {
		super(1);
	}

	public static final int range = 50;
	int timer = 0;
	@Override
	public void update() {
		TileEntityCore core = getCore(range);
		if (!world.isRemote) {
			LeafiaPacket._start(this).__write(31,targetPosition).__sendToAffectedClients();
			timer++;
			if (timer >= tickDelay) {
				timer = 0;
				int heatAmt = outputFluid.getTemperature()-inputFluid.getTemperature();
				if (core != null && heatAmt > 0) {
					double mbPerKelvin = 1000;
					double difference = core.temperature-(outputFluid.getTemperature()-273);
					int maxDrain = (int)(difference/heatAmt*mbPerKelvin);
					int drain = Math.min(maxDrain,amountToHeat)/inAmt*inAmt;
					int fill = drain/inAmt*outAmt;
					if (drain > 0 && maxDrain > 0) {
						// i got lazy here
						FluidStack stack = input.drain(drain,false);
						int amt0 = 0;
						int amt1 = output.fill(new FluidStack(outputFluid,fill),false);
						if (stack != null) amt0 = stack.amount;
						if (amt0 == drain && amt1 == fill) {
							input.drain(drain,true);
							output.fill(new FluidStack(outputFluid,fill),true);
							core.temperature = Math.max(core.temperature-drain*heatAmt/mbPerKelvin/20,0);
						}
					}
				}
			}
			for (EnumFacing face : EnumFacing.values())
				fillFluid(pos.offset(face),output);
			LeafiaPacket._start(this)
					.__write(0,input.writeToNBT(new NBTTagCompound()))
					.__write(1,output.writeToNBT(new NBTTagCompound()))
					.__write(2,inputFluid.getName())
					.__write(3,outputFluid.getName())
					.__write(4,compression)
					.__write(5,amountToHeat)
					.__write(6,tickDelay)
					.__sendToListeners();
		}
	}

	@Override
	public void onReceivePacketServer(byte key,Object value,EntityPlayer plr) {
		super.onReceivePacketServer(key,value,plr);
		switch(key) {
			case 0: amountToHeat = Math.max((int)value,1); break;
			case 1: tickDelay = Math.max((int)value,1); break;
			case 2: setInput(inputFluid,(int)value); break;
		}
	}

	@Override
	public void onReceivePacketLocal(byte key,Object value) {
		super.onReceivePacketLocal(key,value);
		switch(key) {
			case 0: input.readFromNBT((NBTTagCompound)value); break;
			case 1: output.readFromNBT((NBTTagCompound)value); break;
			case 2: inputFluid = FluidRegistry.getFluid((String)value); break;
			case 3: outputFluid = FluidRegistry.getFluid((String)value); break;
			case 4: compression = (int)value; break;
			case 5: amountToHeat = (int)value; break;
			case 6: tickDelay = (int)value; break;
		}
	}

	public LeafiaSet<EntityPlayer> listeners = new LeafiaSet<>();
	@Override
	public List<EntityPlayer> getListeners() {
		return listeners;
	}
	public void fillFluid(BlockPos pos1,FluidTank tank) {
		FFUtils.fillFluid(this, tank, world, pos1, 1000_000);
	}

	@Override
	public String getName() {
		return "tile.dfc_exchanger.name";
	}

	@Override
	public String getPacketIdentifier() {
		return "DFC_EXCHANGER";
	}

	@Override
	public Container provideContainer(int ID,EntityPlayer player,World world,int x,int y,int z) {
		return new DFCExchangerContainer(player,this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID,EntityPlayer player,World world,int x,int y,int z) {
		return new DFCExchangerGUI(player,this);
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{input.getTankProperties()[0],output.getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource,boolean doFill) {
		if (inputFluid.equals(resource.getFluid()))
			return input.fill(resource,doFill);
		return 0;
	}

	@Override
	public @Nullable FluidStack drain(FluidStack resource,boolean doDrain) {
		return output.drain(resource,doDrain);
	}

	@Override
	public @Nullable FluidStack drain(int maxDrain,boolean doDrain) {
		return output.drain(maxDrain,doDrain);
	}

	@Override
	public <T> T getCapability(Capability<T> capability,EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		} else {
			return super.getCapability(capability, facing);
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return true;
		} else {
			return super.hasCapability(capability, facing);
		}
	}
}
