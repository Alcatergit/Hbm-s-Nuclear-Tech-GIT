package com.hbm.tileentity.machine;

import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.items.machine.ItemRTGPellet;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.RTGUtil;

import api.hbm.energy.IEnergyGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class TileEntityMachineRTG extends TileEntityMachineBase implements ITickable, IEnergyGenerator {
	
	public int heat;
	public final int heatMax = 6000;
	public long power;
	public final long maxPower = 1000000;
    public static final long powerPerTU = 25L;
	
	public TileEntityMachineRTG() {
		super(15);
	}
	
	@Override
	public void update() {
		if(!world.isRemote)
		{
			this.sendPower(world, pos);
			int[] slots = new int[inventory.getSlots()];
			for(int i = 0; i < inventory.getSlots();i++){
				slots[i] = i;
			}
			heat = RTGUtil.updateRTGs(inventory, slots);
			
			if(heat > heatMax)
				heat = heatMax;
			
			power += heat * powerPerTU;
			if(power > maxPower)
				power = maxPower;
			
			
			detectAndSendChanges();
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		heat = compound.getInteger("heat");
		detectHeat = heat + 1;
		power = compound.getLong("power");
		detectPower = power + 1;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("heat", this.heat);
		compound.setLong("power", this.power);
		
		return super.writeToNBT(compound);
	}

    @Override
    public String getName(){
        return "container.rtg";
    }

	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getHeatScaled(int i) {
		return (heat * i) / heatMax;
	}
	
	public boolean hasPower() {
		return power > 0;
	}
	
	public boolean hasHeat() {
		return heat > 0;
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
		if(world.getTileEntity(pos) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <=64;
		}
	}
	
	private int detectHeat;
	private long detectPower;
	
	private void detectAndSendChanges() {
		
		boolean mark = false;
		if(detectHeat != heat){
			mark = true;
			detectHeat = heat;
		}
		if(detectPower != power){
			mark = true;
			detectPower = power;
		}
		PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos.getX(), pos.getY(), pos.getZ(), power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
		if(mark)
			markDirty();
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

    @Override
    public int[] getAccessibleSlotsFromSide(EnumFacing e) {
        return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStack, int amount) {
        return !(itemStack.getItem() instanceof ItemRTGPellet);
    }
}
