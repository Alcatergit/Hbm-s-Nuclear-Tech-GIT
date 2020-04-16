package com.hbm.tileentity.machine;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.machine.MachineAssembler;
import com.hbm.handler.MultiblockHandler;
import com.hbm.interfaces.IConsumer;
import com.hbm.inventory.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemAssemblyTemplate;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEAssemblerPacket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityMachineAssembler extends TileEntity implements ITickable, IConsumer {

	public ItemStackHandler inventory = new ItemStackHandler(18) {
		protected void onContentsChanged(int slot) {
			super.onContentsChanged(slot);
			markDirty();
		};
	};

	public long power;
	public static final long maxPower = 100000;
	public int progress;
	public int maxProgress = 100;
	public boolean isProgressing;
	int age = 0;
	int consumption = 100;
	int speed = 100;
	
	Random rand = new Random();
	
	private String customName;
	
	public TileEntityMachineAssembler() {
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
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <=128;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("powerTime");
		this.isProgressing = nbt.getBoolean("progressing");
		detectPower = power + 1;
		detectIsProgressing = !isProgressing;
		if(nbt.hasKey("inventory"))
			inventory.deserializeNBT((NBTTagCompound) nbt.getTag("inventory"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("progressing", this.isProgressing);
		nbt.setLong("powerTime", power);
		nbt.setTag("inventory", inventory.serializeNBT());
		return nbt;
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getProgressScaled(int i) {
		return (progress * i) / maxProgress;
	}
	
	@Override
	public void update() {

		this.consumption = 100;
		this.speed = 100;
		
		for(int i = 1; i < 4; i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			
			if(stack != null) {
				if(stack.getItem() == ModItems.upgrade_speed_1) {
					this.speed -= 25;
					this.consumption += 300;
				}
				if(stack.getItem() == ModItems.upgrade_speed_2) {
					this.speed -= 50;
					this.consumption += 600;
				}
				if(stack.getItem() == ModItems.upgrade_speed_3) {
					this.speed -= 75;
					this.consumption += 900;
				}
				if(stack.getItem() == ModItems.upgrade_power_1) {
					this.consumption -= 30;
					this.speed += 5;
				}
				if(stack.getItem() == ModItems.upgrade_power_2) {
					this.consumption -= 60;
					this.speed += 10;
				}
				if(stack.getItem() == ModItems.upgrade_power_3) {
					this.consumption -= 90;
					this.speed += 15;
				}
			}
		}
		
		if(speed < 25)
			speed = 25;
		if(consumption < 10)
			consumption = 10;
		
		if(!world.isRemote) {
			isProgressing = false;
			power = Library.chargeTEFromItems(inventory, 0, power, maxPower);
			if(MachineRecipes.getOutputFromTempate(inventory.getStackInSlot(4)) != ItemStack.EMPTY && MachineRecipes.getRecipeFromTempate(inventory.getStackInSlot(4)) != null) {
				this.maxProgress = (ItemAssemblyTemplate.getProcessTime(inventory.getStackInSlot(4)) * speed) / 100;
				if(power >= consumption && removeItems(MachineRecipes.getRecipeFromTempate(inventory.getStackInSlot(4)), cloneItemStackProper(inventory))) {
					
					if(inventory.getStackInSlot(5) == ItemStack.EMPTY || (inventory.getStackInSlot(5).getItem() != Items.AIR && inventory.getStackInSlot(5).getItem() == MachineRecipes.getOutputFromTempate(inventory.getStackInSlot(4)).copy().getItem()) && inventory.getStackInSlot(5).getCount() + MachineRecipes.getOutputFromTempate(inventory.getStackInSlot(4)).copy().getCount() <= inventory.getStackInSlot(5).getMaxStackSize()) {
						progress++;
						isProgressing = true;
						
						if(progress >= maxProgress) {
							progress = 0;
							if(inventory.getStackInSlot(5).getItem() == Items.AIR) {
								inventory.setStackInSlot(5, MachineRecipes.getOutputFromTempate(inventory.getStackInSlot(4)).copy());
							} else {
								inventory.getStackInSlot(5).grow(MachineRecipes.getOutputFromTempate(inventory.getStackInSlot(4)).copy().getCount());
							}
							
							removeItems(MachineRecipes.getRecipeFromTempate(inventory.getStackInSlot(4)), inventory);
						}
						
						power -= consumption;
					}
				} else
					progress = 0;
			} else
				progress = 0;
			
			int meta = world.getBlockState(pos).getValue(MachineAssembler.FACING);
			TileEntity te = null;
			if(meta == 2) {
				te = world.getTileEntity(pos.add(-2, 0, 0));
			}
			if(meta == 3) {
				te = world.getTileEntity(pos.add(2, 0, 0));
			}
			if(meta == 4) {
				te = world.getTileEntity(pos.add(0, 0, 2));
			}
			if(meta == 5) {
				te = world.getTileEntity(pos.add(0, 0, -2));
			}
			
			if(te != null && te instanceof ICapabilityProvider){
				ICapabilityProvider capte = (ICapabilityProvider)te;
				if(capte.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, MultiblockHandler.intToEnumFacing(meta).rotateY())){
					IItemHandler cap = capte.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, MultiblockHandler.intToEnumFacing(meta).rotateY());
					tryFillContainerCap(cap, 5);
				}
			}
			
			te = null;
			if(meta == 2) {
				te = world.getTileEntity(pos.add(3, 0, -1));
			}
			if(meta == 3) {
				te = world.getTileEntity(pos.add(-3, 0, 1));
			}
			if(meta == 4) {
				te = world.getTileEntity(pos.add(-1, 0, -3));
			}
			if(meta == 5) {
				te = world.getTileEntity(pos.add(1, 0, 3));
			}
			
			if(te != null && te instanceof ICapabilityProvider){
				ICapabilityProvider capte = (ICapabilityProvider)te;
				if(capte.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, MultiblockHandler.intToEnumFacing(meta).rotateY())){
					IItemHandler cap = capte.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, MultiblockHandler.intToEnumFacing(meta).rotateY());
					for(int i = 0; i < cap.getSlots(); i++){
						tryFillAssemblerCap(cap, i);
					}
				}
			}
			
			detectAndSendChanges();
			
		}
		
	}
	


	//I can't believe that worked.
	public ItemStackHandler cloneItemStackProper(IItemHandlerModifiable array) {
		ItemStackHandler stack = new ItemStackHandler(array.getSlots());
		
		for(int i = 0; i < array.getSlots(); i++)
			if(array.getStackInSlot(i).getItem() != Items.AIR)
				stack.setStackInSlot(i, array.getStackInSlot(i).copy());
			else
				stack.setStackInSlot(i, ItemStack.EMPTY);;
		
		return stack;
	}
	
	//Unloads output into chests
	public boolean tryFillContainer(IInventory inv, int slot) {
		
		int size = inv.getSizeInventory();

		for(int i = 0; i < size; i++) {
			if(inv.getStackInSlot(i) != null) {
				
				if(inventory.getStackInSlot(slot).getItem() == Items.AIR)
					return false;
				
				ItemStack sta1 = inv.getStackInSlot(i).copy();
				ItemStack sta2 = inventory.getStackInSlot(slot).copy();
				if(sta1 != null && sta2 != null) {
					sta1.setCount(1);
					sta2.setCount(1);
				
					if(ItemStack.areItemStacksEqual(sta1, sta2) && ItemStack.areItemStackTagsEqual(sta1, sta2) && inventory.getStackInSlot(i).getCount() < inventory.getStackInSlot(i).getMaxStackSize()) {
						inventory.getStackInSlot(slot).shrink(1);
						
						if(inventory.getStackInSlot(slot).isEmpty())
							inventory.setStackInSlot(slot, ItemStack.EMPTY);
						
						ItemStack sta3 = inventory.getStackInSlot(i).copy();
						sta3.grow(1);
						inv.setInventorySlotContents(i, sta3);
					
						return true;
					}
				}
			}
		}
		for(int i = 0; i < size; i++) {
			
			if(inventory.getStackInSlot(slot).getItem() == Items.AIR)
				return false;
			
			ItemStack sta2 = inventory.getStackInSlot(slot).copy();
			if(inv.getStackInSlot(i) == null && sta2 != null) {
				sta2.setCount(1);
				inventory.getStackInSlot(slot).shrink(1);;
				
				if(inventory.getStackInSlot(slot).isEmpty())
					inventory.setStackInSlot(slot, ItemStack.EMPTY);
				
				inv.setInventorySlotContents(i, sta2);
					
				return true;
			}
		}
		
		return false;
	}
	//Unloads output into chests. Capability version.
	public boolean tryFillContainerCap(IItemHandler inv, int slot) {
		
		int size = inv.getSlots();

		for(int i = 0; i < size; i++) {
			if(inv.getStackInSlot(i) != null) {
				
				if(inventory.getStackInSlot(slot).getItem() == Items.AIR)
					return false;
				
				ItemStack sta1 = inv.getStackInSlot(i).copy();
				ItemStack sta2 = inventory.getStackInSlot(slot).copy();
				if(sta1 != null && sta2 != null) {
					sta1.setCount(1);
					sta2.setCount(1);
				
					if(ItemStack.areItemStacksEqual(sta1, sta2) && ItemStack.areItemStackTagsEqual(sta1, sta2) && inv.getStackInSlot(i).getCount() < inv.getStackInSlot(i).getMaxStackSize()) {
						inventory.getStackInSlot(slot).shrink(1);
						
						if(inventory.getStackInSlot(slot).isEmpty())
							inventory.setStackInSlot(slot, ItemStack.EMPTY);
						
						ItemStack sta3 = inv.getStackInSlot(i).copy();
						sta3.setCount(1);
						inv.insertItem(i, sta3, false);
					
						return true;
					}
				}
			}
		}
		for(int i = 0; i < size; i++) {
			
			if(inventory.getStackInSlot(slot).getItem() == Items.AIR)
				return false;
			
			ItemStack sta2 = inventory.getStackInSlot(slot).copy();
			if(inv.getStackInSlot(i).getItem() == Items.AIR && sta2 != null) {
				sta2.setCount(1);
				inventory.getStackInSlot(slot).shrink(1);;
				
				if(inventory.getStackInSlot(slot).isEmpty())
					inventory.setStackInSlot(slot, ItemStack.EMPTY);
				
				inv.insertItem(i, sta2, false);
					
				return true;
			}
		}
		
		return false;
	}
	
	//Loads assembler's input queue from chests
	public boolean tryFillAssembler(IInventory inv, int slot) {
		
		if(MachineRecipes.getOutputFromTempate(inventory.getStackInSlot(4)) == ItemStack.EMPTY || MachineRecipes.getRecipeFromTempate(inventory.getStackInSlot(4)) == null)
			return false;
		else {
			List<ItemStack> list = MachineRecipes.getRecipeFromTempate(inventory.getStackInSlot(4));
			
			for(int i = 0; i < list.size(); i++)
				list.get(i).setCount(1);


			if(inv.getStackInSlot(slot) == null)
				return false;
			
			ItemStack stack = inv.getStackInSlot(slot).copy();
			stack.setCount(1);
			
			boolean flag = false;
			
			for(int i = 0; i < list.size(); i++)
				if(ItemStack.areItemStacksEqual(stack, list.get(i)) && ItemStack.areItemStackTagsEqual(stack, list.get(i)))
					flag = true;
			
			if(!flag)
				return false;
			
		}
		
		for(int i = 6; i < 18; i++) {
			
			if(inventory.getStackInSlot(i).getItem() != Items.AIR) {
			
				ItemStack sta1 = inv.getStackInSlot(slot).copy();
				ItemStack sta2 = inventory.getStackInSlot(i).copy();
				if(sta1 != null && sta2 != null) {
					sta1.setCount(1);;
					sta2.setCount(1);;
			
					if(ItemStack.areItemStacksEqual(sta1, sta2) && ItemStack.areItemStackTagsEqual(sta1, sta2) && inventory.getStackInSlot(i).getCount() < inventory.getStackInSlot(i).getMaxStackSize()) {
						ItemStack sta3 = inv.getStackInSlot(slot).copy();
						sta3.shrink(1);;
						if(sta3.getCount() <= 0)
							sta3 = ItemStack.EMPTY;
						inv.setInventorySlotContents(slot, sta3);
				
						inventory.getStackInSlot(i).grow(1);;
						return true;
					}
				}
			}
		}
		
		for(int i = 6; i < 18; i++) {

			ItemStack sta2 = inv.getStackInSlot(slot).copy();
			if(inventory.getStackInSlot(i).getItem() == Items.AIR && (sta2 != null && sta2.getItem() != Items.AIR)) {
				sta2.setCount(1);;
				inventory.setStackInSlot(i, sta2.copy());
				
				ItemStack sta3 = inv.getStackInSlot(slot).copy();
				sta3.shrink(1);;
				if(sta3.isEmpty())
					sta3 = ItemStack.EMPTY;
				inv.setInventorySlotContents(slot, sta3);
				
				return true;
			}
		}
		
		return false;
	}
	
	public boolean tryFillAssemblerCap(IItemHandler inv, int slot) {
		
		if(MachineRecipes.getOutputFromTempate(inventory.getStackInSlot(4)) == ItemStack.EMPTY || MachineRecipes.getRecipeFromTempate(inventory.getStackInSlot(4)) == null)
			return false;
		else {
			List<ItemStack> list = MachineRecipes.getRecipeFromTempate(inventory.getStackInSlot(4));
			
			for(int i = 0; i < list.size(); i++)
				list.get(i).setCount(1);
			

			if(inv.getStackInSlot(slot) == null)
				return false;
			
			ItemStack stack = inv.getStackInSlot(slot).copy();
			stack.setCount(1);
			
			boolean flag = false;
			
			for(int i = 0; i < list.size(); i++)
				if(ItemStack.areItemStacksEqual(stack, list.get(i)) && ItemStack.areItemStackTagsEqual(stack, list.get(i)))
					flag = true;
			
			if(!flag)
				return false;
			
		}
		
		for(int i = 6; i < 18; i++) {
			
			if(inventory.getStackInSlot(i).getItem() != Items.AIR) {
			
				ItemStack sta1 = inv.getStackInSlot(slot).copy();
				ItemStack sta2 = inventory.getStackInSlot(i).copy();
				if(sta1 != null && sta2 != null) {
					sta1.setCount(1);;
					sta2.setCount(1);;
			
					if(ItemStack.areItemStacksEqual(sta1, sta2) && ItemStack.areItemStackTagsEqual(sta1, sta2) && inventory.getStackInSlot(i).getCount() < inventory.getStackInSlot(i).getMaxStackSize()) {
						ItemStack sta3 = inv.getStackInSlot(slot).copy();
						sta3.shrink(1);
						if(sta3.getCount() <= 0)
							sta3 = ItemStack.EMPTY;
						inv.extractItem(slot, 1, false);
				
						inventory.getStackInSlot(i).grow(1);;
						return true;
					}
				}
			}
		}
		
		for(int i = 6; i < 18; i++) {

			ItemStack sta2 = inv.getStackInSlot(slot).copy();
			if(inventory.getStackInSlot(i).getItem() == Items.AIR && (sta2 != null && sta2.getItem() != Items.AIR)) {
				sta2.setCount(1);;
				inventory.setStackInSlot(i, sta2.copy());
				
				ItemStack sta3 = inv.getStackInSlot(slot).copy();
				sta3.shrink(1);;
				if(sta3.isEmpty())
					sta3 = ItemStack.EMPTY;
				inv.extractItem(slot, 1, false);
				
				return true;
			}
		}
		
		return false;
	}
	
	//boolean true: remove items, boolean false: simulation mode
	public boolean removeItems(List<ItemStack> stack, IItemHandlerModifiable array) {
		
		if(stack == null)
			return false;
		for(int i = 0; i < stack.size(); i++) {
			for(int j = 0; j < stack.get(i).getCount(); j++) {
				ItemStack sta = stack.get(i).copy();
				sta.setCount(1);
			
				if(!canRemoveItemFromArray(sta, array))
					return false;
			}
		}
		
		return true;
		
	}
	
	public boolean canRemoveItemFromArray(ItemStack stack, IItemHandlerModifiable array) {

		ItemStack st = stack.copy();
		if(st == null)
			return true;
		
		for(int i = 6; i < 18; i++) {
			
			if(array.getStackInSlot(i).getItem() != Items.AIR) {
				ItemStack sta = array.getStackInSlot(i).copy();
				sta.setCount(1);
			
				if(sta != null && isItemAcceptible(sta, st) && array.getStackInSlot(i).getCount() > 0) {
					array.getStackInSlot(i).shrink(1);;
					
					if(array.getStackInSlot(i).isEmpty())
						array.setStackInSlot(i, ItemStack.EMPTY);;
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isItemAcceptible(ItemStack stack1, ItemStack stack2) {
		
		if(stack1 != null && stack2 != null) {
			if(areStacksEqual(stack1, stack2))
				return true;
		
			int[] ids1 = OreDictionary.getOreIDs(stack1);
			int[] ids2 = OreDictionary.getOreIDs(stack2);
			
			if(ids1 != null && ids2 != null && ids1.length > 0 && ids2.length > 0) {
				for(int i = 0; i < ids1.length; i++)
					for(int j = 0; j < ids2.length; j++)
						if(ids1[i] == ids2[j])
							return true;
			}
		}
		
		return false;
	}
	
	//Drillgon200: Method so I can check stuff like containing a fluid without checking if the compound tags are exactly equal, that way
	//it's more compatible with capabilities.
	private boolean areStacksEqual(ItemStack sta1, ItemStack sta2){
		return Library.areItemStacksCompatible(sta2, sta1);
		//return ItemStack.areItemStacksEqual(sta1, sta2);
	}

	@Override
	public void setPower(long i) {
		power = i;
		
	}

	@Override
	public long getPower() {
		return power;
		
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.assembler";
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : super.getCapability(capability, facing);
	}
	
	private long detectPower;
	private boolean detectIsProgressing;
	
	private void detectAndSendChanges() {
		
		PacketDispatcher.wrapper.sendToAllTracking(new LoopedSoundPacket(pos), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 30));
		PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos, power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
		PacketDispatcher.wrapper.sendToAllTracking(new TEAssemblerPacket(pos, isProgressing), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 100));
		
		boolean mark = false;
		if(detectPower != power){
			mark = true;
			detectPower = power;
		}
		if(detectIsProgressing != isProgressing){
			mark = true;
			detectIsProgressing = isProgressing;
		}
		if(mark)
			markDirty();
	}
}
