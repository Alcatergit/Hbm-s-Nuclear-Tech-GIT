package com.hbm.tileentity.machine;

import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemKeyPin;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.interfaces.ILaserable;
import com.hbm.items.weapon.ItemCrucible;
import com.hbm.packet.AuxParticlePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.inventory.DFCRecipes;
import com.hbm.tileentity.INBTPacketReceiver;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import vazkii.quark.api.IDropoffManager;

@Optional.InterfaceList({@Optional.Interface(iface = "vazkii.quark.api.IDropoffManager", modid = "quark")})
public class TileEntityCrateTungsten extends TileEntityLockableBase implements IDropoffManager, ITickable, ILaserable, INBTPacketReceiver {

	public ItemStackHandler inventory;

	public int heatTimer = 0;
	public int age = 0;
	public long joules = 0;

	private final boolean[] slotConverted = new boolean[27];
	private boolean internalModification = false;
	
	public TileEntityCrateTungsten() {
		inventory = new ItemStackHandler(27){
			@Override
			protected void onContentsChanged(int slot){
				if(!internalModification) {
					slotConverted[slot] = false;
				}
				markDirty();
			}
		};
	}

	public boolean acceptsDropoff(EntityPlayer player) {
		return true;
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
		if (world.getTileEntity(pos) != this) {
			return false;
		} else {
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64;
		}
	}

	public boolean canAccess(EntityPlayer player) {
		
		if(!this.isLocked() || player == null) {
			return true;
		} else {
			ItemStack stack = player.getHeldItemMainhand();
			
			if(stack.getItem() instanceof ItemKeyPin && ItemKeyPin.getPins(stack) == this.lock) {
	        	world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.lockOpen, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return true;
			}
			
			if(stack.getItem() == ModItems.key_red) {
	        	world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.lockOpen, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return true;
			}
			
			return this.tryPick(player);
		}
	}

	@Override
	public void update() {
		
		if(!world.isRemote) {
			if(heatTimer > 0)
				heatTimer--;
	
			if(heatTimer > 0) {
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(pos.getX(), pos.getY(), pos.getZ(), 4), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 50));
			}
			age++;
			if(age > 20){
				networkPack();
				age = 0;
			}
		}
	}

	public void networkPack() {
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("timer", this.heatTimer);
		data.setLong("spk", this.joules);
		INBTPacketReceiver.networkPack(this, data, 150);
	}

	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.heatTimer = data.getInteger("timer");
		this.joules = data.getLong("spk");
	}

	@Override
	public void addEnergy(long energy, EnumFacing dir) {
		heatTimer = 5;
		
		for(int i = 0; i < inventory.getSlots(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			
			if(stack.isEmpty())
				continue;
			
			if(stack.getItem() == ModItems.crucible && ItemCrucible.getCharges(stack) < 3 && energy > 10000000)
				ItemCrucible.charge(stack);
			
			if(slotConverted[i])
				continue;
			
			ItemStack result = FurnaceRecipes.instance().getSmeltingResult(stack);

			long requiredEnergy = DFCRecipes.getRequiredFlux(stack);
			if(requiredEnergy > -1 && energy > requiredEnergy){
				result = DFCRecipes.getOutput(stack);
			}
			
			if(result != null && !result.isEmpty()){
				int size = stack.getCount();
			
				if(result.getCount() * size <= result.getMaxStackSize()) {
					ItemStack newStack = result.copy();
					newStack.setCount(result.getCount() * size);
					internalModification = true;
					inventory.setStackInSlot(i, newStack);
					internalModification = false;
					slotConverted[i] = true;
				}
			}
		}
		joules = energy;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		if(compound.hasKey("heatTimer"))
			this.heatTimer = compound.getInteger("heatTimer");
		if(compound.hasKey("slotConverted")) {
			byte[] arr = compound.getByteArray("slotConverted");
			for(int i = 0; i < arr.length && i < slotConverted.length; i++)
				slotConverted[i] = arr[i] != 0;
		}
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		compound.setInteger("heatTimer", this.heatTimer);
		byte[] arr = new byte[slotConverted.length];
		for(int i = 0; i < slotConverted.length; i++)
			arr[i] = (byte) (slotConverted[i] ? 1 : 0);
		compound.setByteArray("slotConverted", arr);
		return super.writeToNBT(compound);
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
