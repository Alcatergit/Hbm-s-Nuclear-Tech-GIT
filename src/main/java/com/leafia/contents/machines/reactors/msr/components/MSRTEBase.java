package com.leafia.contents.machines.reactors.msr.components;

import com.hbm.forgefluid.FFUtils;
import com.hbm.util.I18nUtil;
import com.leafia.contents.machines.reactors.msr.components.element.MSRElementBlock;
import com.leafia.contents.machines.reactors.msr.components.element.MSRElementTE;
import com.leafia.contents.machines.reactors.msr.components.plug.MSRPlugTE;
import com.leafia.dev.LeafiaDebug;
import com.leafia.dev.LeafiaDebug.Tracker;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.container_utility.LeafiaPacketReceiver;
import com.llib.group.LeafiaMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class MSRTEBase extends TileEntity implements ITickable, LeafiaPacketReceiver {
	public FluidTank tank = new FluidTank(1000);
	public static double baseTemperature = 500;
	public static NBTTagCompound nbtProtocol(NBTTagCompound tag) {
		if (tag == null) tag = new NBTTagCompound();
		if (!tag.hasKey("itemMixture"))
			tag.setTag("itemMixture",new NBTTagList());
		if (!tag.hasKey("heat"))
			tag.setDouble("heat",0);
		return tag;
	}
	public static Map<String,Double> readMixture(NBTTagCompound tag) {
		Map<String,Double> mixture = new LeafiaMap<>();
		NBTTagList list = tag.getTagList("itemMixture",10);
		for (NBTBase nbtBase : list) {
			if (nbtBase instanceof NBTTagCompound compound)
				mixture.put(compound.getString("type"),compound.getDouble("amount"));
		}
		return mixture;
	}
	public static NBTTagList writeMixture(Map<String,Double> mixture) {
		NBTTagList list = new NBTTagList();
		for (Entry<String,Double> entry : mixture.entrySet()) {
			NBTTagCompound compound = new NBTTagCompound();
			compound.setString("type",entry.getKey());
			compound.setDouble("amount",entry.getValue());
			list.appendTag(compound);
		}
		return list;
	}
	static public FluidStack transferStats(FluidStack stack0,FluidStack stack1,double div) {
		if (stack0 == null) return stack1;
		if (stack1 == null) return stack1; //stack = new FluidStack(stack0.getFluid(),0);
		NBTTagCompound compound = nbtProtocol(stack0.tag);
		NBTTagCompound target = nbtProtocol(stack1.tag);
		Map<String,Double> mixture0 = readMixture(compound);
		Map<String,Double> mixture1 = readMixture(target);
		for (String fluid : mixture0.keySet()) {
			double amount0 = mixture0.get(fluid);
			double amount1 = 0;
			if (mixture1.containsKey(fluid))
				amount1 = mixture1.get(fluid);
			double transfer = amount0-amount1;
			if (transfer > 0) {
				transfer /= div * 2;
				amount0 -= transfer;
				amount1 += transfer;
				mixture0.put(fluid,amount0);
				mixture1.put(fluid,amount1);
			}
		}
		compound.setTag("itemMixture",writeMixture(mixture0));
		target.setTag("itemMixture",writeMixture(mixture1));
		double heatTransfer = compound.getDouble("heat")-target.getDouble("heat");
		//if (heatTransfer > 0) {
			heatTransfer /= div * 2;
			compound.setDouble("heat",compound.getDouble("heat")-heatTransfer);
			target.setDouble("heat",target.getDouble("heat")+heatTransfer);
		//}
		stack0.tag = compound;
		stack1.tag = target;
		return stack1;
	}
	public void sendFluids() {
		Tracker._startProfile(this,"sendFluids");
		int demand = 0;
		int average = 0;
		Map<MSRTEBase,Integer> list = new LeafiaMap<>();
		List<MSRTEBase> transferStatTargets = new ArrayList<>();
		for (EnumFacing facing : EnumFacing.values()) {
			BlockPos target = pos.add(facing.getDirectionVec());
			if (world.getTileEntity(target) instanceof MSRTEBase te) {
				if (te instanceof MSRPlugTE plug) {
					if (!plug.molten)
						continue;
				}
				if (te.tank.getFluidAmount() < tank.getFluidAmount()) {
					int a = te.tank.getCapacity()-te.tank.getFluidAmount();
					int b = (tank.getFluidAmount()-te.tank.getFluidAmount())/2;
					int addDemand = Math.min(a,b);
					Tracker._tracePosition(this,te.pos,"+"+addDemand+"mB",a,b);
					demand += addDemand;
					list.put(te,addDemand);
				}
				transferStatTargets.add(te);
			}
		}
		double canSend = Math.min(demand,tank.getFluidAmount());
		double multiplier = canSend/demand;
		for (MSRTEBase target : transferStatTargets)
			transferStats(tank.getFluid(),target.tank.getFluid(),transferStatTargets.size());
		if (!list.isEmpty()) {
			for (Entry<MSRTEBase,Integer> entry : list.entrySet()) {
				int amt = (int)(entry.getValue()*multiplier/list.size());
				if (amt > 0) {
					MSRTEBase te = entry.getKey();
					//Tracker._tracePosition(this,te.pos,"+"+demand+"mB");

					assert tank.getFluid() != null;
					tank.drain(te.tank.fill(new FluidStack(te.tank.getFluid() == null ? tank.getFluid() : te.tank.getFluid(),amt),true),true);
				}
			}
		}
		Tracker._endProfile(this);
	}
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("tank"))
			tank.readFromNBT(compound.getCompoundTag("tank"));
	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("tank",tank.writeToNBT(new NBTTagCompound()));
		return super.writeToNBT(compound);
	}
	public static void appendPrintHook(List<String> list,World world,int x,int y,int z) {
		TileEntity entity = world.getTileEntity(new BlockPos(x,y,z));
		if (entity instanceof MSRTEBase msr) {
			list.add(TextFormatting.GOLD+I18nUtil.resolveKey("tile.msr.status"));
			if (entity instanceof MSRElementTE element)
				list.add("  "+I18nUtil.resolveKey("tile.msr_element.restriction",(int)(element.restriction*100)));
			list.add("  "+I18nUtil.resolveKey("tile.msr.fill",msr.tank.getFluidAmount()+"/"+msr.tank.getCapacity()+"mB"));
			if (msr.tank.getFluid() != null)
				FFUtils.addFluidInfo(msr.tank.getFluid(),list,"  ");
		}
	}

	@Override
	public void update() {
		if (!world.isRemote) {
			sendFluids();
			LeafiaDebug.debugPos(world,pos,0.05f,0xFFFF00,tank.getFluidAmount()+"mB");
			generateTankPacket().__sendToAffectedClients();
		}
	}
	protected LeafiaPacket generateTankPacket() {
		LeafiaPacket packet = LeafiaPacket._start(this);
		packet.__write(31,tank.writeToNBT(new NBTTagCompound()));
		return packet;
	}
	@Override
	public void onReceivePacketLocal(byte key,Object value) {
		if (key == 31) {
			tank.readFromNBT((NBTTagCompound)value);
		}
	}
	@Override
	public void onReceivePacketServer(byte key,Object value,EntityPlayer plr) { }
	@Override
	public void onPlayerValidate(EntityPlayer plr) { }
}
