package com.hbm.tileentity.machine;

import java.util.Arrays;

import com.hbm.items.tool.ItemCassette;
import com.hbm.items.tool.ItemCassette.SoundType;
import com.hbm.items.tool.ItemCassette.TrackType;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TESirenPacket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachineSiren extends TileEntity implements ITickable {

	public ItemStackHandler inventory;
	
	///private static final int[] slots_top = new int[] { 0 };
	///private static final int[] slots_bottom = new int[] { 0 };
	//private static final int[] slots_side = new int[] { 0 };
	
	public boolean lock = false;
	
	private String customName;
	
	public TileEntityMachineSiren() {
		inventory = new ItemStackHandler(1){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.siren";
	}

	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(world.getTileEntity(pos) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <=64;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void update() {
		if(!world.isRemote) {
			int id = Arrays.asList(TrackType.values()).indexOf(getCurrentType());
			
			if(getCurrentType().name().equals(TrackType.NULL.name())) {
				PacketDispatcher.wrapper.sendToDimension(new TESirenPacket(pos.getX(), pos.getY(), pos.getZ(), id, false), world.provider.getDimension());
				return;
			}
			
			boolean active = world.isBlockIndirectlyGettingPowered(pos) > 0;
			
			if(getCurrentType().getType().name().equals(SoundType.LOOP.name())) {
				
				PacketDispatcher.wrapper.sendToDimension(new TESirenPacket(pos.getX(), pos.getY(), pos.getZ(), id, active), world.provider.getDimension());
			} else {
				
				if(!lock && active) {
					lock = true;
					PacketDispatcher.wrapper.sendToDimension(new TESirenPacket(pos.getX(), pos.getY(), pos.getZ(), id, false), world.provider.getDimension());
					PacketDispatcher.wrapper.sendToDimension(new TESirenPacket(pos.getX(), pos.getY(), pos.getZ(), id, true), world.provider.getDimension());
				}
				
				if(lock && !active) {
					lock = false;
				}
			}
		}
	}
	
	public TrackType getCurrentType() {
		if(inventory.getStackInSlot(0).getItem() instanceof ItemCassette) {
			return TrackType.getEnum(inventory.getStackInSlot(0).getItemDamage());
		}
		
		return TrackType.NULL;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : super.getCapability(capability, facing);
	}
}
