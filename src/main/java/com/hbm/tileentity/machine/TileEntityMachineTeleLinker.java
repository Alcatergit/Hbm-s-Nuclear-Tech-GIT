package com.hbm.tileentity.machine;

import com.hbm.items.machine.ItemTurretBiometry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachineTeleLinker extends TileEntity implements ITickable {

	public ItemStackHandler inventory;
	public ICapabilityProvider dropProvider;
	
	//public static final int maxFill = 64 * 3;

	//private static final int[] slots_top = new int[] {0};
	//private static final int[] slots_bottom = new int[] {1};
	//private static final int[] slots_side = new int[] {2};
	
	private String customName;
	
	public TileEntityMachineTeleLinker() {
		inventory = new ItemStackHandler(3){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
		dropProvider = new ICapabilityProvider(){

			@Override
			public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
				return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
			}

			@Override
			public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
				return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : null;
			}
			
		};
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.teleLinker";
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
		if(!world.isRemote)
		{
			if(inventory.getStackInSlot(0).getItem() instanceof ItemTurretBiometry && inventory.getStackInSlot(1).getItem() instanceof ItemTurretBiometry) {
				
				String[] names = ItemTurretBiometry.getNames(inventory.getStackInSlot(0));
				
				if(names != null)
					for(int i = 0; i < names.length; i++)
						ItemTurretBiometry.addName(inventory.getStackInSlot(1), names[i]);
			}
			
			if(inventory.getStackInSlot(2).getItem() instanceof ItemTurretBiometry) {
				ItemTurretBiometry.clearNames(inventory.getStackInSlot(2));
			}
		}
	}
	
	
}
