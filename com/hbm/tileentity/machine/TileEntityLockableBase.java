package com.hbm.tileentity.machine;

import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemKey;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;

public class TileEntityLockableBase extends TileEntity {
	protected int lock;
	private boolean isLocked = false;
	protected double lockMod = 0.1D;

	public boolean isLocked() {
		return isLocked;
	}
	
	public void lock() {
		
		if(lock == 0) {
			MainRegistry.logger.error("A block has been set to locked state before setting pins, this should not happen and may cause errors! " + this.toString());
		}
		if(isLocked == false)
			markDirty();
		isLocked = true;
	}
	
	public void setPins(int pins) {
		if(lock != pins)
			markDirty();
		lock = pins;
	}
	
	public int getPins() {
		return lock;
	}
	
	public void setMod(double mod) {
		if(lockMod != mod)
			markDirty();
		lockMod = mod;
	}
	
	public double getMod() {
		return lockMod;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		lock = compound.getInteger("lock");
		isLocked = compound.getBoolean("isLocked");
		lockMod = compound.getDouble("lockMod");
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("lock", lock);
		compound.setBoolean("isLocked", isLocked);
		compound.setDouble("lockMod", lockMod);
		return super.writeToNBT(compound);
	}
	
	public boolean canAccess(EntityPlayer player) {
		
		if(!isLocked) {
			return true;
		} else {
			ItemStack stack = player.getHeldItemMainhand();
			
			if(stack.getItem() instanceof ItemKey && ItemKey.getPins(stack) == this.lock) {
	        	world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.lockOpen, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return true;
			}
			
			if(stack.getItem() == ModItems.key_red) {
	        	world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.lockOpen, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return true;
			}
			
			return tryPick(player);
		}
	}
	
	private boolean tryPick(EntityPlayer player) {

		boolean canPick = false;
		ItemStack stack = player.getHeldItemMainhand();
		double chanceOfSuccess = this.lockMod * 100;
		
		if(stack != null && stack.getItem() == ModItems.pin && Library.hasInventoryItem(player.inventory, ModItems.screwdriver)) {
			
			stack.shrink(1);
			canPick = true;
		}
		
		if(stack != null && stack.getItem() == ModItems.screwdriver && Library.hasInventoryItem(player.inventory, ModItems.pin)) {
			
			Library.consumeInventoryItem(player.inventory, ModItems.pin);
			player.inventoryContainer.detectAndSendChanges();
			canPick = true;
		}
		
		if(canPick) {
			
			if(Library.checkArmorPiece(player, ModItems.jackt, 2) || Library.checkArmorPiece(player, ModItems.jackt2, 2))
				chanceOfSuccess *= 100D;
			
			double rand = player.world.rand.nextDouble() * 100;
			
			if(chanceOfSuccess > rand) {
        		world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.pinUnlock, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return true;
			}

    		world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.pinBreak, SoundCategory.BLOCKS, 1.0F, 0.8F + player.world.rand.nextFloat() * 0.2F);
		}
		
		return false;
	}
}
