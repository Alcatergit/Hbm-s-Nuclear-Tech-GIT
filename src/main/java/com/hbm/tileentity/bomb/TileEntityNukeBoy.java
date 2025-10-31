package com.hbm.tileentity.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.NukeBoy;
import com.hbm.items.ModItems;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.container_utility.LeafiaPacketReceiver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityNukeBoy extends TileEntity implements LeafiaPacketReceiver {

	public ItemStackHandler inventory;
	public boolean failed = false;
	private String customName;

	public TileEntityNukeBoy() {
		inventory = new ItemStackHandler(5) {
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
				if (world.isRemote) return;
				if (super.getStackInSlot(1).getItem() != ModItems.boy_bullet)
					failed = false;
				tryDetonate(false);
			}
		};
	}

	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.nukeBoy";
	}

	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomName(String name) {
		this.customName = name;
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
		if(world.getTileEntity(pos) != this) {
			return false;
		} else {
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		if(compound.hasKey("failed"))
			failed = compound.getBoolean("failed");
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		compound.setBoolean("failed",failed);
		return super.writeToNBT(compound);
	}
	boolean detonated = false;
	public void tryDetonate(boolean triggered) {
		if (world.isRemote) return;
		boolean willExplode = false;
		boolean shielded = (inventory.getStackInSlot(0).getItem() == ModItems.boy_shielding);
		if ((inventory.getStackInSlot(1).getItem() == ModItems.boy_target_invalid) && shielded)
			willExplode = true;
		else if (triggered) {
			if (inventory.getStackInSlot(4).getItem() == ModItems.boy_igniter) {
				world.playSound(null,pos,SoundEvents.BLOCK_TRIPWIRE_ATTACH,SoundCategory.BLOCKS,0.5f,1.5f);
				if (inventory.getStackInSlot(3).getItem() == ModItems.boy_propellant) {
					world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 0.5f, 1);
					inventory.setStackInSlot(3,ItemStack.EMPTY);
					inventory.setStackInSlot(4,new ItemStack(ModItems.scrap));
					if (inventory.getStackInSlot(2).getItem() == ModItems.boy_bullet) {
						if (inventory.getStackInSlot(1).getItem() == ModItems.boy_target) {
							if (shielded)
								willExplode = true;
							else {
								inventory.setStackInSlot(2,ItemStack.EMPTY);
								inventory.setStackInSlot(1,new ItemStack(ModItems.boy_target_invalid));
							}
						} else if (inventory.getStackInSlot(1).isEmpty()) {
							inventory.setStackInSlot(1,inventory.getStackInSlot(2));
							inventory.setStackInSlot(2,ItemStack.EMPTY);
							failed = true;
							LeafiaPacket._start(this).__write((byte)0,true).__sendToClients(16);
							markDirty();
						}
					}
				}
			}
		}
		if (!detonated && willExplode) {
			detonated = true;
			ModBlocks.nuke_boy.onPlayerDestroy(world, pos, world.getBlockState(pos));
			this.clearSlots();
			world.setBlockToAir(pos);
			((NukeBoy) ModBlocks.nuke_boy).igniteTestBomb(world, pos.getX(), pos.getY(), pos.getZ());
		}
	}

	public void clearSlots() {
		for(int i = 0; i < inventory.getSlots(); i++) {
			inventory.setStackInSlot(i, ItemStack.EMPTY);
		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public String getPacketIdentifier() {
		return "lilboi";
	}
	@Override
	public void onReceivePacketLocal(byte key,Object value) {
		failed = (boolean)value;
	}
	@Override
	public void onReceivePacketServer(byte key,Object value,EntityPlayer plr) {}

	@Override
	public void onPlayerValidate(EntityPlayer plr) {

	}
}
