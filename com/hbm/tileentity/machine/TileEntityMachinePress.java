package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.explosion.ExplosionParticleB;
import com.hbm.inventory.MachineRecipes;
import com.hbm.items.special.ItemBlades;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEPressPacket;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachinePress extends TileEntity implements ITickable, ICapabilityProvider {
	
	public int progress = 0;
	public int power = 0;
	public int burnTime = 0;
	public final static int maxProgress = 200;
	public final static int maxPower = 700;
	public int maxBurn = 160;
	public int item;
	public int meta;
	public boolean isRetracting = false;
	public boolean test = false;
	
	
	public ItemStackHandler inventory = new ItemStackHandler(4) {
		protected void onContentsChanged(int slot) {
			super.onContentsChanged(slot);
			markDirty();
		};
	};
	
	private String customName;
	
	public int getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	public int getBurnScaled(int i) {
		return (burnTime * i) / maxBurn;
	}
	
	public int getProgressScaled(int i) {
		return (progress * i) / maxProgress;
	}
	
	public int getSizeInventory(){
		return inventory.getSlots();
	}
	
	public ItemStack getStackInSlot(int i){
		return inventory.getStackInSlot(i);
	}

	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		progress = nbt.getInteger("progress");
		power = nbt.getInteger("power");
		burnTime = nbt.getInteger("burnTime");
		maxBurn = nbt.getInteger("maxBurn");
		isRetracting = nbt.getBoolean("ret");
		((ItemStackHandler)inventory).deserializeNBT((NBTTagCompound) nbt.getTag("inventory"));
		 if (nbt.hasKey("CustomName", 8))
	        {
	            this.customName = nbt.getString("CustomName");
	        }
	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("progress", progress);
		nbt.setInteger("power", power);
		nbt.setInteger("burnTime", burnTime);
		nbt.setInteger("maxBurn", maxBurn);
		nbt.setBoolean("ret", isRetracting);
		nbt.setTag("inventory", ((ItemStackHandler) inventory).serializeNBT());
		
		if (this.hasCustomName())
        {
            nbt.setString("CustomName", this.customName);
        }
		return nbt;
	}
	
	public boolean hasCustomName() {
		
		return this.customName != null && this.customName.length() > 0;
	}

	@Override
	public void update() {	
		if(!world.isRemote)
		{
			if(burnTime > 0) {
				this.burnTime--;
				this.power++;
				if(power > maxPower)
					power = maxPower;
			} else {
				if(power > 0)
					power--;
			}
			
			if(inventory.getStackInSlot(0) != ItemStack.EMPTY && this.burnTime == 0 && TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(0)) > 0) {
				this.maxBurn = this.burnTime = TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(0)) / 8;
				inventory.getStackInSlot(0).shrink(1);;
				if(inventory.getStackInSlot(0).getCount() <= 0) {
					
					if(inventory.getStackInSlot(0).getItem().getContainerItem() != null)
						inventory.setStackInSlot(0, new ItemStack(inventory.getStackInSlot(0).getItem().getContainerItem()));
					else
						inventory.setStackInSlot(0, ItemStack.EMPTY);
				}
			}
			
			if(power >= maxPower / 3) {

				int speed = power * 25 / maxPower;
				
				if(inventory.getStackInSlot(1) != ItemStack.EMPTY && inventory.getStackInSlot(2) != ItemStack.EMPTY) {
					ItemStack stack = MachineRecipes.getPressResult(inventory.getStackInSlot(2).copy(), inventory.getStackInSlot(1).copy());
					if(stack != null &&
							(inventory.getStackInSlot(3) == ItemStack.EMPTY ||
							(inventory.getStackInSlot(3).getItem() == stack.getItem() &&
							inventory.getStackInSlot(3).getCount() + stack.getCount() <= inventory.getStackInSlot(3).getMaxStackSize()))) {
						
						if(progress >= maxProgress) {
							
							isRetracting = true;
							
							if(inventory.getStackInSlot(3) == ItemStack.EMPTY)
								inventory.setStackInSlot(3, stack.copy());
							else
								inventory.getStackInSlot(3).grow(stack.getCount());;
							
							inventory.getStackInSlot(2).shrink(1);;
							if(inventory.getStackInSlot(2).getCount() <= 0)
								inventory.setStackInSlot(2, ItemStack.EMPTY);
							
							inventory.getStackInSlot(1).setItemDamage(inventory.getStackInSlot(1).getItemDamage() + 1);
							if(inventory.getStackInSlot(1).getItemDamage() >= inventory.getStackInSlot(1).getMaxDamage())
								inventory.setStackInSlot(1, ItemStack.EMPTY);
					    //    this.world.playSound(pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.pressOperate, SoundCategory.BLOCKS, 1.5F, 1.0F, false);
					        this.world.playSound(null, pos, HBMSoundHandler.pressOperate, SoundCategory.BLOCKS, 1.5F, 1.0F);
						}
						
						if(!isRetracting)
							progress += speed;
						
					} else {
						isRetracting = true;
					}
				} else {
					isRetracting = true;
				}

				if(isRetracting)
					progress -= speed;
			} else {
				isRetracting = true;
			}
			
			if(progress <= 0) {
				isRetracting = false;
				progress = 0;
			}
			
			PacketDispatcher.wrapper.sendToAll(new TEPressPacket(this.pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(2), progress));
		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T) inventory;
		}
		return super.getCapability(capability, facing);
	}

	public String getName() {
		return this.hasCustomName() ? this.customName : "container.press";
	}

	public boolean isUsableByPlayer(EntityPlayer player) {
		if(player.world.getTileEntity(this.pos) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <=64;
		}
	}
	
}
