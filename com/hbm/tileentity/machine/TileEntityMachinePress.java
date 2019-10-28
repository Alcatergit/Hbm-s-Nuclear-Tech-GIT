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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMachinePress extends TileEntity implements ISidedInventory, ITickable {
	
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
	
	private NonNullList<ItemStack> slots = NonNullList.<ItemStack>withSize(4, ItemStack.EMPTY);
	
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
		return slots.size();
	}
	
	public ItemStack getStackInSlot(int i){
		return slots.get(i);
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "container.press";
	}

	@Override
	public boolean hasCustomName() {
		return this.customName != null && this.customName.length() > 0;
	}

	@Override
    public boolean isEmpty()
    {
        for (ItemStack itemstack : this.slots)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
    }

	@Override
    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.slots, index, count);
    }

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.slots, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
        this.slots.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }
		
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		if(world.getTileEntity(pos) != this)
		{
			return false;
		}else{
			return player.getDistanceSqToCenter(pos) <= 64;
		}
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		
		if(stack.getItem() instanceof ItemBlades && i == 1)
			return true;
		
		if(TileEntityFurnace.getItemBurnTime(stack) > 0 && i == 0)
			return true;
		
		return i == 2;
	}

	/**
	 * 0 is progress, 1 is power, 2 is burn time, 3 is item, 4 is meta
	 */
	@Override
	public int getField(int id) {
		switch(id){
		case 0:
			return this.progress;
		case 1:
			return this.power;
		case 2:
			return this.burnTime;
		case 3:
			return this.item;
		case 4:
			return this.meta;
		default:
			return 0;
	}
	}

	/**
	 * 0 is progress, 1 is power, 2 is burn time, 3 is item, 4 is meta
	 */
	@Override
	public void setField(int id, int value) {	
		switch(id){
			case 0:
				this.progress = value;
				break;
			case 1:
				this.power = value;
				break;
			case 2:
				this.burnTime = value;
				break;
			case 3:
				this.item = value;
				break;
			case 4:
				this.meta = value;
				break;
			default:
				break;
		}
	}

	@Override
	public int getFieldCount() {
		return 5;
	}

	@Override
	public void clear() {
		this.slots.clear();
	}
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return null;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return index == 2;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return index == 3;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		progress = nbt.getInteger("progress");
		power = nbt.getInteger("power");
		burnTime = nbt.getInteger("burnTime");
		maxBurn = nbt.getInteger("maxBurn");
		isRetracting = nbt.getBoolean("ret");
		ItemStackHelper.loadAllItems(nbt, slots);
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
		
		ItemStackHelper.saveAllItems(nbt, slots);
		
		if (this.hasCustomName())
        {
            nbt.setString("CustomName", this.customName);
        }
		return nbt;
	}

	@Override
	public void update() {	
		if(!world.isRemote)
		{
			world.spawnEntity(EntityNukeExplosionMK4.statFac(world, 30, pos.getX(), pos.getY(), pos.getZ()));
			if(burnTime > 0) {
				this.burnTime--;
				this.power++;
				if(power > maxPower)
					power = maxPower;
			} else {
				if(power > 0)
					power--;
			}
			
			if(slots.get(0) != ItemStack.EMPTY && this.burnTime == 0 && TileEntityFurnace.getItemBurnTime(slots.get(0)) > 0) {
				this.maxBurn = this.burnTime = TileEntityFurnace.getItemBurnTime(slots.get(0)) / 8;
				slots.get(0).shrink(1);;
				if(slots.get(0).getCount() <= 0) {
					
					if(slots.get(0).getItem().getContainerItem() != null)
						slots.set(0, new ItemStack(slots.get(0).getItem().getContainerItem()));
					else
						slots.set(0, ItemStack.EMPTY);
				}
			}
			
			if(power >= maxPower / 3) {

				int speed = power * 25 / maxPower;
				
				if(slots.get(1) != ItemStack.EMPTY && slots.get(2) != ItemStack.EMPTY) {
					ItemStack stack = MachineRecipes.getPressResult(slots.get(2).copy(), slots.get(1).copy());
					if(stack != null &&
							(slots.get(3) == ItemStack.EMPTY ||
							(slots.get(3).getItem() == stack.getItem() &&
							slots.get(3).getCount() + stack.getCount() <= slots.get(3).getMaxStackSize()))) {
						
						if(progress >= maxProgress) {
							
							isRetracting = true;
							
							if(slots.get(3) == ItemStack.EMPTY)
								slots.set(3, stack.copy());
							else
								slots.get(3).grow(stack.getCount());;
							
							slots.get(2).shrink(1);;
							if(slots.get(2).getCount() <= 0)
								slots.set(2, ItemStack.EMPTY);
							
							slots.get(1).setItemDamage(slots.get(1).getItemDamage() + 1);
							if(slots.get(1).getItemDamage() >= slots.get(1).getMaxDamage())
								slots.set(1, ItemStack.EMPTY);
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
			
			PacketDispatcher.wrapper.sendToAll(new TEPressPacket(this.pos.getX(), pos.getY(), pos.getZ(), slots.get(2), progress));
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
	
}
