package com.hbm.tileentity.machine;

import java.util.Arrays;
import java.util.List;

import com.hbm.inventory.control_panel.ControlEvent;
import com.hbm.inventory.control_panel.ControlEventSystem;
import com.hbm.inventory.control_panel.IControllable;
import com.hbm.items.machine.ItemCassette;
import com.hbm.items.machine.ItemCassette.SoundType;
import com.hbm.items.machine.ItemCassette.TrackType;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TESirenPacket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachineSiren extends TileEntity implements ITickable, IControllable {

	public ItemStackHandler inventory;
	
	///private static final int[] slots_top = new int[] { 0 };
	///private static final int[] slots_bottom = new int[] { 0 };
	//private static final int[] slots_side = new int[] { 0 };
	
	public boolean lock = false;
	public boolean ctrlActive = false;
	
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
			
			boolean active = ctrlActive || world.isBlockIndirectlyGettingPowered(pos) > 0;
			
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

	@Override
	public void onChunkUnload() {
		if(!world.isRemote) {
			int id = Arrays.asList(TrackType.values()).indexOf(getCurrentType());
			PacketDispatcher.wrapper.sendToDimension(new TESirenPacket(pos.getX(), pos.getY(), pos.getZ(), id, false), world.provider.getDimension());		
		}
	}

	@Override
	public void invalidate() {
		if(!world.isRemote) {
			int id = Arrays.asList(TrackType.values()).indexOf(getCurrentType());
			PacketDispatcher.wrapper.sendToDimension(new TESirenPacket(pos.getX(), pos.getY(), pos.getZ(), id, false), world.provider.getDimension());		
		}
		ControlEventSystem.get(world).removeControllable(this);
		super.invalidate();
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

	@Override
	public void receiveEvent(BlockPos from, ControlEvent e){
		if(e.name.equals("siren_set_state")){
			ctrlActive = e.vars.get("isOn").getBoolean();
		}
	}

	@Override
	public List<String> getInEvents(){
		return Arrays.asList("siren_set_state");
	}

	@Override
	public BlockPos getControlPos(){
		return getPos();
	}

	@Override
	public World getControlWorld(){
		return getWorld();
	}
	
	@Override
	public void validate(){
		super.validate();
		ControlEventSystem.get(world).addControllable(this);
	}
}
