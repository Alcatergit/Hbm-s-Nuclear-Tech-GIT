package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFactory;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCoreTitanium extends TileEntity implements ITickable, IFactory, IConsumer {

	public int progress = 0;
	public long power = 0;
	public int soundCycle = 0;
	public final static int processTime = 200;
	public final static int maxPower = (int)((ItemBattery)ModItems.factory_core_titanium).getMaxCharge();
	public ItemStackHandler inventory;
	public ICapabilityProvider dropProvider;
	
	private String customName;
	
	public TileEntityCoreTitanium() {
		inventory = new ItemStackHandler(23){
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
		return this.hasCustomInventoryName() ? this.customName : "container.factoryTitanium";
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
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setShort("cookTime", (short) progress);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.progress = compound.getShort("cookTime");
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	@Override
	public void update() {
		if(inventory.getStackInSlot(22).getItem() == ModItems.factory_core_titanium)
		{
			this.power = (int) ItemBattery.getCharge(inventory.getStackInSlot(22));
		} else {
			this.power = 0;
		}
		
		if(inventory.getStackInSlot(9).isEmpty())
		{
			for(int i = 0; i < 9; i++)
			{
				if(isProcessable(inventory.getStackInSlot(i)))
				{
					inventory.setStackInSlot(9, inventory.getStackInSlot(i).copy());
					inventory.setStackInSlot(i, ItemStack.EMPTY);
					break;
				}
			}
		} else {
			for(int i = 0; i < 9; i++)
			{
				if(!inventory.getStackInSlot(i).isEmpty())
				{
					if(inventory.getStackInSlot(i).getItem() == inventory.getStackInSlot(9).getItem() && inventory.getStackInSlot(i).getItemDamage() == inventory.getStackInSlot(9).getItemDamage())
					{
						if(inventory.getStackInSlot(9).getCount() + inventory.getStackInSlot(i).getCount() <= inventory.getStackInSlot(i).getMaxStackSize())
						{
							inventory.getStackInSlot(9).grow(inventory.getStackInSlot(i).getCount());
							inventory.setStackInSlot(i, ItemStack.EMPTY);
						} else {
							int j = 64 - inventory.getStackInSlot(9).getCount();
							inventory.getStackInSlot(9).grow(j);
							inventory.getStackInSlot(i).shrink(j);
						}
						break;
					}
				}
			}
		}
		
		if(inventory.getStackInSlot(10).isEmpty())
		{
			for(int i = 0; i < 9; i++)
			{
				if(isProcessable(inventory.getStackInSlot(i)))
				{
					inventory.setStackInSlot(10, inventory.getStackInSlot(i).copy());
					inventory.setStackInSlot(i, ItemStack.EMPTY);
					break;
				}
			}
		} else {
			for(int i = 0; i < 9; i++)
			{
				if(!inventory.getStackInSlot(i).isEmpty())
				{
					if(inventory.getStackInSlot(i).getItem() == inventory.getStackInSlot(10).getItem() && inventory.getStackInSlot(i).getItemDamage() == inventory.getStackInSlot(10).getItemDamage())
					{
						if(inventory.getStackInSlot(10).getCount() + inventory.getStackInSlot(i).getCount() <= inventory.getStackInSlot(i).getMaxStackSize())
						{
							inventory.getStackInSlot(10).grow(inventory.getStackInSlot(i).getCount());
							inventory.setStackInSlot(i, ItemStack.EMPTY);
						} else {
							int j = 64 - inventory.getStackInSlot(10).getCount();
							inventory.getStackInSlot(10).grow(j);
							inventory.getStackInSlot(i).shrink(j);
						}
						break;
					}
				}
			}
		}
		
		if(this.power > 0 && (isProcessable(inventory.getStackInSlot(9)) || isProcessable(inventory.getStackInSlot(10))) && isStructureValid(world))
		{
			this.progress += 1;
			((ItemBattery)inventory.getStackInSlot(22).getItem()).dischargeBattery(inventory.getStackInSlot(22), 1);
			if(soundCycle == 0)
	        	this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_MINECART_RIDING, SoundCategory.BLOCKS, 1.0F, 0.75F);
			soundCycle++;
			
			if(soundCycle >= 50)
				soundCycle = 0;
		} else {
			this.progress = 0;
		}
		
		if(!inventory.getStackInSlot(9).isEmpty() && !inventory.getStackInSlot(11).isEmpty() && (FurnaceRecipes.instance().getSmeltingResult(inventory.getStackInSlot(9)).getItem() != inventory.getStackInSlot(11).getItem() || FurnaceRecipes.instance().getSmeltingResult(inventory.getStackInSlot(9)).getItemDamage() != inventory.getStackInSlot(11).getItemDamage()))
		{
			this.progress = 0;
		}
		
		if(!inventory.getStackInSlot(10).isEmpty() && !inventory.getStackInSlot(12).isEmpty() && (FurnaceRecipes.instance().getSmeltingResult(inventory.getStackInSlot(10)).getItem() != inventory.getStackInSlot(12).getItem() || FurnaceRecipes.instance().getSmeltingResult(inventory.getStackInSlot(10)).getItemDamage() != inventory.getStackInSlot(12).getItemDamage()))
		{
			this.progress = 0;
		}
		
		if(this.progress >= TileEntityCoreTitanium.processTime)
		{
			if(isProcessable(inventory.getStackInSlot(9)))
			{
				ItemStack itemStack = FurnaceRecipes.instance().getSmeltingResult(inventory.getStackInSlot(9));
				if(inventory.getStackInSlot(11).isEmpty())
				{
					inventory.setStackInSlot(11, itemStack.copy());
				}else if(inventory.getStackInSlot(11).isItemEqual(itemStack)) {
					inventory.getStackInSlot(11).grow(itemStack.getCount());
				}
				if(inventory.getStackInSlot(9).isEmpty())
				{
					inventory.setStackInSlot(9, new ItemStack(inventory.getStackInSlot(9).getItem().setFull3D()));
				}else{
					inventory.getStackInSlot(9).shrink(1);
				}
				if(inventory.getStackInSlot(9).isEmpty())
				{
					inventory.setStackInSlot(9, ItemStack.EMPTY);
				}
			}
			if(isProcessable(inventory.getStackInSlot(10)))
			{
				ItemStack itemStack = FurnaceRecipes.instance().getSmeltingResult(inventory.getStackInSlot(10));
				if(inventory.getStackInSlot(12).isEmpty())
				{
					inventory.setStackInSlot(12, itemStack.copy());
				}else if(inventory.getStackInSlot(12).isItemEqual(itemStack)) {
					inventory.getStackInSlot(12).grow(itemStack.getCount());
				}
				if(inventory.getStackInSlot(10).isEmpty())
				{
					inventory.setStackInSlot(10, new ItemStack(inventory.getStackInSlot(10).getItem()));
				}else{
					inventory.getStackInSlot(10).shrink(1);
				}
				if(inventory.getStackInSlot(10).isEmpty())
				{
					inventory.setStackInSlot(10, ItemStack.EMPTY);
				}
			}
			
			this.progress = 0;
		}
			
		if(!inventory.getStackInSlot(11).isEmpty())
		{
			for(int i = 0; i < 9; i++)
			{
				int j = i + 13;
				if(!inventory.getStackInSlot(j).isEmpty())
				{
					if(inventory.getStackInSlot(j).getItem() == inventory.getStackInSlot(11).getItem() && inventory.getStackInSlot(j).getItemDamage() == inventory.getStackInSlot(11).getItemDamage())
					{
						if(inventory.getStackInSlot(j).getCount() < inventory.getStackInSlot(j).getMaxStackSize())
						{
							if(inventory.getStackInSlot(j).getCount() + inventory.getStackInSlot(11).getCount() <= inventory.getStackInSlot(11).getMaxStackSize())
							{
								inventory.getStackInSlot(j).grow(inventory.getStackInSlot(11).getCount());
								inventory.setStackInSlot(11, ItemStack.EMPTY);
								break;
							} else {
								int k = inventory.getStackInSlot(j).getMaxStackSize() - inventory.getStackInSlot(j).getCount();
								if(k < 0)
								{
									inventory.getStackInSlot(j).grow(k);
									inventory.getStackInSlot(11).shrink(k);
									break;
								}
							}
						}
					}
				} else {
					inventory.setStackInSlot(j, inventory.getStackInSlot(11).copy());
					inventory.setStackInSlot(11, ItemStack.EMPTY);
					break;
				}
			}
		}
		
		if(!inventory.getStackInSlot(12).isEmpty())
		{
			for(int i = 0; i < 9; i++)
			{
				int j = i + 13;
				if(!inventory.getStackInSlot(j).isEmpty())
				{
					if(inventory.getStackInSlot(j).getItem() == inventory.getStackInSlot(12).getItem() && inventory.getStackInSlot(j).getItemDamage() == inventory.getStackInSlot(12).getItemDamage())
					{
						if(inventory.getStackInSlot(j).getCount() < inventory.getStackInSlot(j).getMaxStackSize())
						{
							if(inventory.getStackInSlot(j).getCount() + inventory.getStackInSlot(12).getCount() <= inventory.getStackInSlot(12).getMaxStackSize())
							{
								inventory.getStackInSlot(j).grow(inventory.getStackInSlot(12).getCount());
								inventory.setStackInSlot(12, ItemStack.EMPTY);
								break;
							} else {
								int k = inventory.getStackInSlot(j).getMaxStackSize() - inventory.getStackInSlot(j).getCount();
								if(k < 0)
								{
									inventory.getStackInSlot(j).grow(k);
									inventory.getStackInSlot(12).shrink(k);
									break;
								}
							}
						}
					}
				} else {
					inventory.setStackInSlot(j, inventory.getStackInSlot(12).copy());
					inventory.setStackInSlot(12, ItemStack.EMPTY);
					break;
				}
			}
		}
	}

	@Override
	public boolean isStructureValid(World world) {
		MutableBlockPos mPos = new BlockPos.MutableBlockPos();
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		if(world.getBlockState(mPos.setPos(x, y, z)).getBlock() == ModBlocks.factory_titanium_core &&
				world.getBlockState(mPos.setPos(x - 1, y - 1, z - 1)).getBlock() == ModBlocks.factory_titanium_hull &&
				world.getBlockState(mPos.setPos(x, y - 1, z - 1)).getBlock() == ModBlocks.factory_titanium_hull &&
				world.getBlockState(mPos.setPos(x + 1, y - 1, z - 1)).getBlock() == ModBlocks.factory_titanium_hull &&
				world.getBlockState(mPos.setPos(x - 1, y - 1, z)).getBlock() == ModBlocks.factory_titanium_hull &&
				(world.getBlockState(mPos.setPos(x, y - 1, z)).getBlock() == ModBlocks.factory_titanium_conductor || world.getBlockState(mPos.setPos(x, y - 1, z)).getBlock() == ModBlocks.factory_titanium_hull) &&
				world.getBlockState(mPos.setPos(x + 1, y - 1, z)).getBlock() == ModBlocks.factory_titanium_hull &&
				world.getBlockState(mPos.setPos(x - 1, y - 1, z + 1)).getBlock() == ModBlocks.factory_titanium_hull &&
				world.getBlockState(mPos.setPos(x, y - 1, z + 1)).getBlock() == ModBlocks.factory_titanium_hull &&
				world.getBlockState(mPos.setPos(x + 1, y - 1, z + 1)).getBlock() == ModBlocks.factory_titanium_hull &&
				world.getBlockState(mPos.setPos(x - 1, y, z - 1)).getBlock() == ModBlocks.factory_titanium_hull &&
				world.getBlockState(mPos.setPos(x, y, z - 1)).getBlock() == ModBlocks.factory_titanium_furnace &&
				world.getBlockState(mPos.setPos(x + 1, y, z - 1)).getBlock() == ModBlocks.factory_titanium_hull &&
				world.getBlockState(mPos.setPos(x - 1, y, z)).getBlock() == ModBlocks.factory_titanium_furnace &&
				world.getBlockState(mPos.setPos(x + 1, y, z)).getBlock() == ModBlocks.factory_titanium_furnace &&
				world.getBlockState(mPos.setPos(x - 1, y, z + 1)).getBlock() == ModBlocks.factory_titanium_hull &&
				world.getBlockState(mPos.setPos(x, y, z + 1)).getBlock() == ModBlocks.factory_titanium_furnace &&
				world.getBlockState(mPos.setPos(x + 1, y, z + 1)).getBlock() == ModBlocks.factory_titanium_hull &&
				world.getBlockState(mPos.setPos(x - 1, y + 1, z - 1)).getBlock() == ModBlocks.factory_titanium_hull &&
				world.getBlockState(mPos.setPos(x, y + 1, z - 1)).getBlock() == ModBlocks.factory_titanium_hull &&
				world.getBlockState(mPos.setPos(x + 1, y + 1, z - 1)).getBlock() == ModBlocks.factory_titanium_hull &&
				world.getBlockState(mPos.setPos(x - 1, y + 1, z)).getBlock() == ModBlocks.factory_titanium_hull &&
				(world.getBlockState(mPos.setPos(x, y + 1, z)).getBlock() == ModBlocks.factory_titanium_conductor || world.getBlockState(mPos.setPos(x, y + 1, z)).getBlock() == ModBlocks.factory_titanium_hull) &&
				world.getBlockState(mPos.setPos(x + 1, y + 1, z)).getBlock() == ModBlocks.factory_titanium_hull &&
				world.getBlockState(mPos.setPos(x - 1, y + 1, z + 1)).getBlock() == ModBlocks.factory_titanium_hull &&
				world.getBlockState(mPos.setPos(x, y + 1, z + 1)).getBlock() == ModBlocks.factory_titanium_hull &&
				world.getBlockState(mPos.setPos(x + 1, y + 1, z + 1)).getBlock() == ModBlocks.factory_titanium_hull)
		{
			return true;
		}
		return false;
	}

	@Override
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}

	@Override
	public int getProgressScaled(int i) {
		return (progress * i) / processTime;
	}

	@Override
	public boolean isProcessable(ItemStack item) {
		if(item != null && !item.isEmpty())
		{
			return !FurnaceRecipes.instance().getSmeltingResult(item).isEmpty();
		} else {
			return false;
		}
	}

	@Override
	public void setPower(long i) {
		if(inventory.getStackInSlot(22).getItem() == ModItems.factory_core_titanium)
		{
			((ItemBattery)inventory.getStackInSlot(22).getItem()).setCharge(inventory.getStackInSlot(22), (int)i);
		}
	}
	@Override
	public long getPower() {
		return power;
	}
	@Override
	public long getMaxPower() {
		return maxPower;
	}

}
