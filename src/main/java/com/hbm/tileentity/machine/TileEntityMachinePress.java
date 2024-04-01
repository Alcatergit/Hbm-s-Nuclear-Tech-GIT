package com.hbm.tileentity.machine;

import com.hbm.inventory.PressRecipes;
import com.hbm.items.machine.ItemStamp;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEPressPacket;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachinePress extends TileEntityMachineBase implements ITickable, ICapabilityProvider {

	public int progress = 0;
	public int power = 0;
	public int burnTime = 0;
	public final static int maxProgress = 200;
	public final static int maxPower = 700;
	public int maxBurn = 160;
	public int item;
	public int meta;
	public boolean isRetracting = false;
	public boolean test = true;

	public TileEntityMachinePress(){
		super(4);
	}
	
	public int getPowerScaled(int i) {
		return (power * i) / maxPower;
	}

	public int getBurnScaled(int i) {
		if(maxBurn == 0)
			return 0;
		return (burnTime * i) / maxBurn;
	}

	public int getProgressScaled(int i) {
		return (progress * i) / maxProgress;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		progress = nbt.getInteger("progress");
		detectProgress = progress + 1;
		power = nbt.getInteger("power");
		detectPower = power + 1;
		burnTime = nbt.getInteger("burnTime");
		detectBurnTime = burnTime + 1;
		maxBurn = nbt.getInteger("maxBurn");
		detectMaxBurn = maxBurn + 1;
		isRetracting = nbt.getBoolean("ret");
		detectIsRetracting = !isRetracting;
		if(nbt.hasKey("inventory"))
			((ItemStackHandler) inventory).deserializeNBT((NBTTagCompound) nbt.getTag("inventory"));
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

		return nbt;
	}

	@Override
	public void update() {
		/*	if(test){
				Vec3d bottomLeft = new Vec3d(pos.getX(), pos.getY() + 5, pos.getZ());
				Portal portal = new Mirror(world, bottomLeft, bottomLeft.addVector(1, 0, 0), bottomLeft.addVector(0, 1, 0), bottomLeft.addVector(1, 1, 0), null);
				System.out.println(portal);
				test = false;
			}*/
		if(!world.isRemote) {
			if(burnTime > 0) {
				this.burnTime--;
				this.power++;
				if(power > maxPower)
					power = maxPower;
			} else {
				if(power > 0)
					power--;
			}
			if(!(world.isBlockIndirectlyGettingPowered(pos) > 0)) {
				if(inventory.getStackInSlot(0) != ItemStack.EMPTY && this.burnTime == 0 && TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(0)) > 0) {
					this.maxBurn = this.burnTime = TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(0)) / 8;
					ItemStack copy = inventory.getStackInSlot(0).copy();
					inventory.getStackInSlot(0).shrink(1);

					if(inventory.getStackInSlot(0).getCount() <= 0) {

						if(copy.getItem().getContainerItem() != null)
							inventory.setStackInSlot(0, new ItemStack(copy.getItem().getContainerItem()));
						else
							inventory.setStackInSlot(0, ItemStack.EMPTY);
					}
				}

				if(power >= maxPower / 3) {

					int speed = power * 25 / maxPower;

					if(inventory.getStackInSlot(1) != ItemStack.EMPTY && inventory.getStackInSlot(2) != ItemStack.EMPTY) {
						ItemStack stack = PressRecipes.getPressResult(inventory.getStackInSlot(2).copy(), inventory.getStackInSlot(1).copy());
						if(stack != null && (inventory.getStackInSlot(3) == ItemStack.EMPTY || (inventory.getStackInSlot(3).getItem() == stack.getItem() && inventory.getStackInSlot(3).getCount() + stack.getCount() <= inventory.getStackInSlot(3).getMaxStackSize()))) {
							if(progress >= maxProgress) {

								isRetracting = true;

								if(inventory.getStackInSlot(3) == ItemStack.EMPTY)
									inventory.setStackInSlot(3, stack.copy());
								else
									inventory.getStackInSlot(3).grow(stack.getCount());
								;

								inventory.getStackInSlot(2).shrink(1);
								;
								if(inventory.getStackInSlot(2).getCount() <= 0)
									inventory.setStackInSlot(2, ItemStack.EMPTY);

								if(inventory.getStackInSlot(1).getMaxDamage() > 0){
									inventory.getStackInSlot(1).setItemDamage(inventory.getStackInSlot(1).getItemDamage() + 1);
									if(inventory.getStackInSlot(1).getItemDamage() >= inventory.getStackInSlot(1).getMaxDamage())
										inventory.setStackInSlot(1, ItemStack.EMPTY);
								}
								// this.world.playSound(pos.getX(), pos.getY(),
								// pos.getZ(), HBMSoundHandler.pressOperate,
								// SoundCategory.BLOCKS, 1.5F, 1.0F, false);
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

					if(isRetracting) {
						progress -= speed;
					}
				} else {
					isRetracting = true;
				}

				if(progress <= 0) {
					isRetracting = false;
					progress = 0;
				}
			}
			detectAndSendChanges();
		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
		// return new AxisAlignedBB(pos, pos.add(1, 3, 1));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return super.getCapability(capability, facing);
	}

	@Override
	public String getName(){
		return "container.press";
	}
	
	public boolean isUsableByPlayer(EntityPlayer player) {
		if(player.world.getTileEntity(this.pos) != this) {
			return false;
		} else {
			return player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64;
		}
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(EnumFacing e){
		int i = e.ordinal();
		return i == 0 ? new int[] { 3 } : new int[]{ 0, 1, 2 };
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack itemStack, int amount){
		return this.isItemValidForSlot(slot, itemStack);
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int amount){
		return slot == 3;
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack){
		if(stack.getItem() instanceof ItemStamp && i == 1)
			return true;
		
		if(TileEntityFurnace.getItemBurnTime(stack) > 0 && i == 0)
			return true;
		
		if(!(stack.getItem() instanceof ItemStamp) && i == 2)
			return true;
		return false;
	}

	private int detectProgress;
	private int detectPower;
	private int detectBurnTime;
	private int detectMaxBurn;
	private boolean detectIsRetracting;

	private void detectAndSendChanges() {

		boolean mark = false;
		if(detectProgress != progress) {
			mark = true;
			detectProgress = progress;
		}
		if(detectPower != power) {
			mark = true;
			detectPower = power;
		}
		if(detectBurnTime != burnTime) {
			mark = true;
			detectBurnTime = burnTime;
		}
		if(detectMaxBurn != maxBurn) {
			mark = true;
			detectMaxBurn = maxBurn;
		}
		if(detectIsRetracting != isRetracting) {
			mark = true;
			detectIsRetracting = isRetracting;
		}
		if(mark)
			markDirty();
		PacketDispatcher.wrapper.sendToAllAround(new TEPressPacket(this.pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(2), progress), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 100));
	}

}
