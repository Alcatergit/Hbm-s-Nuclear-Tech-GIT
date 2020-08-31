package com.hbm.tileentity.machine;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.machine.MachineChemplant;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.FluidContainerRegistry;
import com.hbm.handler.MultiblockHandler;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemChemistryTemplate;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxParticlePacket;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEChemplantPacket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityMachineChemplant extends TileEntity implements IConsumer, ITankPacketAcceptor, ITickable {

	public ItemStackHandler inventory = new ItemStackHandler(21) {
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
	public boolean needsUpdate = false;
	public boolean needsTankTypeUpdate = false;
	int age = 0;
	int consumption = 100;
	int speed = 100;
	public FluidTank[] tanks;
	public Fluid[] tankTypes;
	
	public ItemStack previousTemplate = ItemStack.EMPTY;
	//Drillgon200: Yeah I don't even know what I was doing originally
	public ItemStack previousTemplate2 = ItemStack.EMPTY;
	
	Random rand = new Random();
	
	private String customName;
	
	public TileEntityMachineChemplant() {
		tanks = new FluidTank[4];
		tanks[0] = new FluidTank(16000);
		tanks[1] = new FluidTank(16000);
		tanks[2] = new FluidTank(16000);
		tanks[3] = new FluidTank(16000);
		tankTypes = new Fluid[]{null, null, null, null};
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(world.getTileEntity(pos) != this)
		{
			return false;
		}else{
			return player.getDistanceSqToCenter(pos) <=128;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		String[] types;
		
		this.power = nbt.getLong("powerTime");
		detectPower = power + 1;
		isProgressing = nbt.getBoolean("progressing");
		detectIsProgressing = !isProgressing;
		
		tanks[0].readFromNBT(nbt.getCompoundTag("input1"));
		tanks[1].readFromNBT(nbt.getCompoundTag("input2"));
		tanks[2].readFromNBT(nbt.getCompoundTag("output1"));
		tanks[3].readFromNBT(nbt.getCompoundTag("output2"));
		
		types = new String[]{nbt.getString("tankType0"), nbt.getString("tankType1"), nbt.getString("tankType2"), nbt.getString("tankType3")};
		
		if(types[0] != null && !types[0].equals("empty")){
			tankTypes[0] = FluidRegistry.getFluid(types[0]);
		} else {
			tankTypes[0] = null;
		}
		if(types[1] != null && !types[1].equals("empty")){
			tankTypes[1] = FluidRegistry.getFluid(types[1]);
		} else {
			tankTypes[1] = null;
		}
		if(types[2] != null && !types[2].equals("empty")){
			tankTypes[2] = FluidRegistry.getFluid(types[2]);
		} else {
			tankTypes[2] = null;
		}
		if(types[3] != null && !types[3].equals("empty")){
			tankTypes[3] = FluidRegistry.getFluid(types[3]);
		} else {
			tankTypes[3] = null;
		}
		if(nbt.hasKey("inventory"))
			inventory.deserializeNBT((NBTTagCompound) nbt.getTag("inventory"));
	}
	
	
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("powerTime", power);
		String[] types = new String[]{tankTypes[0] != null ? tankTypes[0].getName() : "empty", tankTypes[1] != null ? tankTypes[1].getName() : "empty", tankTypes[2] != null ? tankTypes[2].getName() : "empty", tankTypes[3] != null ? tankTypes[3].getName() : "empty"};

		nbt.setBoolean("progressing", isProgressing);
		
		NBTTagCompound input1 = new NBTTagCompound();
		NBTTagCompound input2 = new NBTTagCompound();
		NBTTagCompound output1 = new NBTTagCompound();
		NBTTagCompound output2 = new NBTTagCompound();
		
		tanks[0].writeToNBT(input1);
		tanks[1].writeToNBT(input2);
		tanks[2].writeToNBT(output1);
		tanks[3].writeToNBT(output2);
		
		nbt.setTag("input1", input1);
		nbt.setTag("input2", input2);
		nbt.setTag("output1", output1);
		nbt.setTag("output2", output2);
		
		nbt.setString("tankType0", types[0] != null ? types[0] : "empty");
		nbt.setString("tankType1", types[1] != null ? types[1] : "empty");
		nbt.setString("tankType2", types[2] != null ? types[2] : "empty");
		nbt.setString("tankType3", types[3] != null ? types[3] : "empty");
		
		NBTTagCompound inv = inventory.serializeNBT();
		nbt.setTag("inventory", inv);
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
		
		needsTankTypeUpdate = previousTemplate2 != inventory.getStackInSlot(4);
		previousTemplate2 = inventory.getStackInSlot(4);
		this.consumption = 100;
		this.speed = 100;
		
		
		for(int i = 1; i < 4; i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			
			if(stack != ItemStack.EMPTY) {
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
		if(this.needsTankTypeUpdate)
			setContainers();
		
		
		if(!world.isRemote)
		{
			if(needsUpdate){
				needsUpdate = false;
			}
			int meta = world.getBlockState(pos).getValue(MachineChemplant.FACING);
			isProgressing = false;
			
			age++;
			if(age >= 20)
			{
				age = 0;
			}
			
			if(age == 9 || age == 19) {
				fillFluidInit(tanks[2]);
				fillFluidInit(tanks[3]);
			}
			
			
			power = Library.chargeTEFromItems(inventory, 0, power, maxPower);
			if(inputValidForTank(0, 17))
				FFUtils.fillFromFluidContainer(inventory, tanks[0], 17, 19);
			if(inputValidForTank(1, 18))
				FFUtils.fillFromFluidContainer(inventory, tanks[1], 18, 20);
			FFUtils.fillFluidContainer(inventory, tanks[2], 9, 11);
			FFUtils.fillFluidContainer(inventory, tanks[3], 10, 12);
			

			FluidStack[] inputs = MachineRecipes.getFluidInputFromTempate(inventory.getStackInSlot(4));
			FluidStack[] outputs = MachineRecipes.getFluidOutputFromTempate(inventory.getStackInSlot(4));
			
			if((MachineRecipes.getChemInputFromTempate(inventory.getStackInSlot(4)) != null || !Library.isArrayEmpty(inputs)) && 
					(MachineRecipes.getChemOutputFromTempate(inventory.getStackInSlot(4)) != null || !Library.isArrayEmpty(outputs))) {
				this.maxProgress = (ItemChemistryTemplate.getProcessTime(inventory.getStackInSlot(4)) * speed) / 100;
				if(power >= consumption && removeItems(MachineRecipes.getChemInputFromTempate(inventory.getStackInSlot(4)), cloneItemStackProper(inventory)) && hasFluidsStored(inputs)) {
					if(hasSpaceForItems(MachineRecipes.getChemOutputFromTempate(inventory.getStackInSlot(4))) && hasSpaceForFluids(outputs)) {
						progress++;
						isProgressing = true;
						
						if(progress >= maxProgress) {
							progress = 0;
							addItems(MachineRecipes.getChemOutputFromTempate(inventory.getStackInSlot(4)));
							addFluids(outputs);

							removeItems(MachineRecipes.getChemInputFromTempate(inventory.getStackInSlot(4)), inventory);
							removeFluids(inputs);
						}
						
						power -= consumption;
					}
				} else
					progress = 0;
			} else
				progress = 0;

			TileEntity te1 = null;
			TileEntity te2 = null;
			
			if(meta == 2) {
				te1 = world.getTileEntity(pos.add(-2, 0, 0));
				te2 = world.getTileEntity(pos.add(3, 0, -1));
			}
			if(meta == 3) {
				te1 = world.getTileEntity(pos.add(2, 0, 0));
				te2 = world.getTileEntity(pos.add(-3, 0, 1));
			}
			if(meta == 4) {
				te1 = world.getTileEntity(pos.add(0, 0, 2));
				te2 = world.getTileEntity(pos.add(-1, 0, -3));
			}
			if(meta == 5) {
				te1 = world.getTileEntity(pos.add(0, 0, -2));
				te2 = world.getTileEntity(pos.add(1, 0, 3));
			}
		
			
			tryExchangeTemplates(te1, te2);
			
			
			if(te1 != null && te1.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, MultiblockHandler.intToEnumFacing(meta).rotateY())){
				IItemHandler cap = te1.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, MultiblockHandler.intToEnumFacing(meta).rotateY());
				for(int i = 5; i < 9; i ++){
					tryFillContainerCap(cap, i);
				}
			}
			
			if(te2 != null && te2.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, MultiblockHandler.intToEnumFacing(meta).rotateY())){
				IItemHandler cap = te2.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, MultiblockHandler.intToEnumFacing(meta).rotateY());
				for(int i = 0; i < cap.getSlots(); i ++){
					tryFillAssemblerCap(cap, i);
				}
			}
			
			
			
			if(isProgressing) {
				if(meta == 2) {
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(pos.getX() + 0.375, pos.getY() + 3, pos.getZ() - 0.625, 1),
							new TargetPoint(world.provider.getDimension(), pos.getX() + 0.375, pos.getY() + 3, pos.getZ() - 0.625, 50));
				}
				if(meta == 3) {
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(pos.getX() + 0.625, pos.getY() + 3, pos.getZ() + 1.625, 1),
							new TargetPoint(world.provider.getDimension(), pos.getX() + 0.625, pos.getY() + 3, pos.getZ() + 1.625, 50));
				}
				if(meta == 4) {
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(pos.getX() - 0.625, pos.getY() + 3, pos.getZ() + 0.625, 1),
							new TargetPoint(world.provider.getDimension(), pos.getX() - 0.625, pos.getY() + 3, pos.getZ() + 0.625, 50));
				}
				if(meta == 5) {
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(pos.getX() + 1.625, pos.getY() + 3, pos.getZ() + 0.375, 1),
							new TargetPoint(world.provider.getDimension(), pos.getX() + 1.625, pos.getY() + 3, pos.getZ() + 0.375, 50));
				}
			}
			
			detectAndSendChanges();
		}
		
	}
	
	

	public boolean tryExchangeTemplates(TileEntity te1, TileEntity te2) {
		//validateTe sees if it's a valid inventory tile entity
		boolean te1Valid = validateTe(te1);
		boolean te2Valid = validateTe(te2);
		
		if(te1Valid && te2Valid){
			IItemHandler iTe1 = te1.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			IItemHandler iTe2e = te2.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			if(!(iTe2e instanceof IItemHandlerModifiable))
				return false;
			IItemHandlerModifiable iTe2 = (IItemHandlerModifiable)iTe2e;
			boolean openSlot = false;
			boolean existingTemplate = false;
			boolean filledContainer = false;
			//Check if there's an existing template and an open slot
			for(int i = 0; i < iTe1.getSlots(); i++){
				if(iTe1.getStackInSlot(i) == null || iTe1.getStackInSlot(i) == ItemStack.EMPTY){
					openSlot = true;
					
				}
				
			}
			if(this.inventory.getStackInSlot(4) != ItemStack.EMPTY && inventory.getStackInSlot(4).getItem() instanceof ItemChemistryTemplate){
				existingTemplate = true;
			}
			//Check if there's a template in input
			for(int i = 0; i < iTe2.getSlots(); i++){
				if((iTe2.getStackInSlot(i) != null && iTe2.getStackInSlot(i) != ItemStack.EMPTY) && iTe2.getStackInSlot(i).getItem() instanceof ItemChemistryTemplate){
					if(openSlot && existingTemplate){
						filledContainer = tryFillContainerCap(iTe1, 4);
						
					}
					if(filledContainer || existingTemplate == false){
						ItemStack copy = iTe2.getStackInSlot(i).copy();
						iTe2.setStackInSlot(i, ItemStack.EMPTY);
						this.inventory.setStackInSlot(4, copy);
					}
				}
				
			}
			
		
		}
		return false;
		
	}

	private boolean validateTe(TileEntity te) {
		return te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
	}

	private void setContainers() {
		if(inventory.getStackInSlot(4) == ItemStack.EMPTY || (inventory.getStackInSlot(4) != ItemStack.EMPTY && !(inventory.getStackInSlot(4).getItem() instanceof ItemChemistryTemplate))) {
		} else {
			needsTankTypeUpdate = true;
			if(previousTemplate != ItemStack.EMPTY && ItemStack.areItemStacksEqual(previousTemplate, inventory.getStackInSlot(4))){
				
				needsTankTypeUpdate = false;
				
			} else {
				
			}
			previousTemplate = inventory.getStackInSlot(4).copy();
			FluidStack[] inputs = MachineRecipes.getFluidInputFromTempate(inventory.getStackInSlot(4));
			FluidStack[] outputs = MachineRecipes.getFluidOutputFromTempate(inventory.getStackInSlot(4));

			tankTypes[0] = inputs[0] == null ? null : inputs[0].getFluid();
			tankTypes[1] = inputs[1] == null ? null : inputs[1].getFluid();
			tankTypes[2] = outputs[0] == null ? null : outputs[0].getFluid();
			tankTypes[3] = outputs[1] == null ? null : outputs[1].getFluid();
			
			if((inputs[0] != null && tanks[0].getFluid() == null) || tanks[0].getFluid() != null && tanks[0].getFluid().getFluid() != tankTypes[0]){
				tanks[0].setFluid(null);
				if(needsTankTypeUpdate){
					needsTankTypeUpdate = false;
				}
			}
			
			if((inputs[1] != null && tanks[1].getFluid() == null) || tanks[1].getFluid() != null && tanks[1].getFluid().getFluid() != tankTypes[1]){
				tanks[1].setFluid(null);
				if(needsTankTypeUpdate){
					needsTankTypeUpdate = false;
				}
			}
			if((outputs[0] != null && tanks[2].getFluid() == null) || tanks[2].getFluid() != null && tanks[2].getFluid().getFluid() != tankTypes[2]){
				tanks[2].setFluid(null);
			}
			if((outputs[1] != null && tanks[3].getFluid() == null) || tanks[3].getFluid() != null && tanks[3].getFluid().getFluid() != tankTypes[3]){
				tanks[3].setFluid(null);
				if(needsTankTypeUpdate){
					needsTankTypeUpdate = false;
				}
			}
		}
	}
	
	protected boolean inputValidForTank(int tank, int slot){
		
		if(inventory.getStackInSlot(slot) != ItemStack.EMPTY && tankTypes[tank] != null){
			if(FluidUtil.getFluidHandler(inventory.getStackInSlot(slot)) != null && FluidUtil.getFluidContained(inventory.getStackInSlot(slot)) != null){
				return FluidUtil.getFluidContained(inventory.getStackInSlot(slot)).getFluid() == tankTypes[tank];
			}
			FluidStack test = FluidContainerRegistry.getFluidFromItem(inventory.getStackInSlot(slot).getItem());
			if(test != null && test.getFluid() == tankTypes[tank]){
				return true;
			}
			//Drillgon200: I really hope fluid container registry comes back.
		}
		
		return false;
	}
	
	public boolean hasFluidsStored(FluidStack[] fluids) {
		if(Library.isArrayEmpty(fluids))
			return true;
		
		if((fluids[0] == null || fluids[0] != null && fluids[0].amount <= tanks[0].getFluidAmount()) && 
				(fluids[1] == null || fluids[1] != null && fluids[1].amount <= tanks[1].getFluidAmount()))
			return true;
		
		return false;
	}
	
	public boolean hasSpaceForFluids(FluidStack[] fluids) {
		if(Library.isArrayEmpty(fluids))
			return true;
		if(((fluids[0] == null || fluids[0] != null && tanks[2].fill(fluids[0], false) == fluids[0].amount) && 
				(fluids[1] == null || fluids[1] != null && tanks[3].fill(fluids[1], false) == fluids[1].amount)))
			return true;
		
		return false;
	}
	
	public void removeFluids(FluidStack[] fluids) {
		if(Library.isArrayEmpty(fluids))
			return;
		if(fluids[0] != null){
			tanks[0].drain(fluids[0].amount, true);
		}
		if(fluids[1] != null){
			tanks[1].drain(fluids[1].amount, true);
		}
	}
	
	public boolean hasSpaceForItems(ItemStack[] stacks) {
		if(stacks == null)
			return true;
		if(stacks != null && Library.isArrayEmpty(stacks))
			return true;

		ItemStack sta0 = Library.carefulCopy(inventory.getStackInSlot(5));
		if(sta0 != null)
			sta0.setCount(1);
		ItemStack sta1 = Library.carefulCopy(stacks[0]);
		if(sta1 != null)
			sta1.setCount(1);
		ItemStack sta2 = Library.carefulCopy(inventory.getStackInSlot(6));
		if(sta2 != null)
			sta2.setCount(1);
		ItemStack sta3 = Library.carefulCopy(stacks[1]);
		if(sta3 != null)
			sta3.setCount(1);
		ItemStack sta4 = Library.carefulCopy(inventory.getStackInSlot(7));
		if(sta4 != null)
			sta4.setCount(1);
		ItemStack sta5 = Library.carefulCopy(stacks[2]);
		if(sta5 != null)
			sta5.setCount(1);
		ItemStack sta6 = Library.carefulCopy(inventory.getStackInSlot(8));
		if(sta6 != null)
			sta6.setCount(1);
		ItemStack sta7 = Library.carefulCopy(stacks[3]);
		if(sta7 != null)
			sta7.setCount(1);
		
		if((inventory.getStackInSlot(5) == ItemStack.EMPTY || stacks[0] == null || (stacks[0] != null && isItemAcceptable(sta0, sta1) && inventory.getStackInSlot(5).getCount() + stacks[0].getCount() <= inventory.getStackInSlot(5).getMaxStackSize())) && 
				(inventory.getStackInSlot(6) == ItemStack.EMPTY || stacks[1] == null || (stacks[1] != null && isItemAcceptable(sta2, sta3) && inventory.getStackInSlot(6).getCount() + stacks[1].getCount() <= inventory.getStackInSlot(6).getMaxStackSize())) && 
				(inventory.getStackInSlot(7) == ItemStack.EMPTY || stacks[2] == null || (stacks[2] != null && isItemAcceptable(sta4, sta5) && inventory.getStackInSlot(7).getCount() + stacks[2].getCount() <= inventory.getStackInSlot(7).getMaxStackSize())) && 
				(inventory.getStackInSlot(8) == ItemStack.EMPTY || stacks[3] == null || (stacks[3] != null && isItemAcceptable(sta6, sta7) && inventory.getStackInSlot(8).getCount() + stacks[3].getCount() <= inventory.getStackInSlot(8).getMaxStackSize())))
			return true;
			
		return false;
	}
	
	public void addItems(ItemStack[] stacks) {
		if(inventory.getStackInSlot(5) == ItemStack.EMPTY && stacks[0] != null)
			inventory.setStackInSlot(5, stacks[0].copy());
		else if (inventory.getStackInSlot(5) != ItemStack.EMPTY && stacks[0] != null)
			inventory.getStackInSlot(5).setCount(inventory.getStackInSlot(5).getCount() + stacks[0].getCount());

		if(inventory.getStackInSlot(6) == ItemStack.EMPTY && stacks[1] != null)
			inventory.setStackInSlot(6, stacks[1].copy());
		else if (inventory.getStackInSlot(6) != ItemStack.EMPTY && stacks[1] != null)
			inventory.getStackInSlot(6).setCount(inventory.getStackInSlot(6).getCount() + stacks[1].getCount());

		if(inventory.getStackInSlot(7) == ItemStack.EMPTY && stacks[2] != null)
			inventory.setStackInSlot(7, stacks[2].copy());
		else if (inventory.getStackInSlot(7) != ItemStack.EMPTY && stacks[2] != null)
			inventory.getStackInSlot(7).setCount(inventory.getStackInSlot(7).getCount() + stacks[2].getCount());

		if(inventory.getStackInSlot(8) == ItemStack.EMPTY && stacks[3] != null)
			inventory.setStackInSlot(8, stacks[3].copy());
		else if (inventory.getStackInSlot(8) != ItemStack.EMPTY && stacks[3] != null)
			inventory.getStackInSlot(8).setCount(inventory.getStackInSlot(8).getCount() + stacks[3].getCount());
	}
	
	public void addFluids(FluidStack[] stacks) {
		if(stacks[0] != null){
			tanks[2].fill(stacks[0], true);
		}
		if(stacks[1] != null){
			tanks[3].fill(stacks[1], true);
		}
	}
	
	//I can't believe that worked.
	public IItemHandlerModifiable cloneItemStackProper(IItemHandlerModifiable array) {
		IItemHandlerModifiable stack = new ItemStackHandler(array.getSlots());
		
		for(int i = 0; i < array.getSlots(); i++)
			if(array.getStackInSlot(i) != null)
				stack.setStackInSlot(i, array.getStackInSlot(i).copy());
			else
				stack.setStackInSlot(i, ItemStack.EMPTY);;
		
		return stack;
	}
	
	/**Unloads output into chests*/
	//Drillgon200: I don't think we need this anymore
	/*public boolean tryFillContainer(IInventory inventory, int slot) {
		
		int size = inventory.getSizeInventory();

		for(int i = 0; i < size; i++) {
			if(inventory.getStackInSlot(i) != null) {
				
				if(slots[slot] == null)
					return false;
				
				ItemStack sta1 = inventory.getStackInSlot(i).copy();
				ItemStack sta2 = slots[slot].copy();
				if(sta1 != null && sta2 != null) {
					sta1.stackSize = 1;
					sta2.stackSize = 1;
				
					if(ItemStack.areItemStacksEqual(sta1, sta2) && ItemStack.areItemStackTagsEqual(sta1, sta2) && inventory.getStackInSlot(i).stackSize < inventory.getStackInSlot(i).getMaxStackSize()) {
						slots[slot].stackSize--;
						
						if(slots[slot].stackSize <= 0)
							slots[slot] = null;
						
						ItemStack sta3 = inventory.getStackInSlot(i).copy();
						sta3.stackSize++;
						inventory.setInventorySlotContents(i, sta3);
					
						return true;
					}
				}
			}
		}
		for(int i = 0; i < size; i++) {
			
			if(slots[slot] == null)
				return false;
			
			ItemStack sta2 = slots[slot].copy();
			if(inventory.getStackInSlot(i) == null && sta2 != null) {
				sta2.stackSize = 1;
				slots[slot].stackSize--;
				
				if(slots[slot].stackSize <= 0)
					slots[slot] = null;
				
				inventory.setInventorySlotContents(i, sta2);
					
				return true;
			}
		}
		
		return false;
	}*/
	
	//Unloads output into chests. Capability version.
		public boolean tryFillContainerCap(IItemHandler inv, int slot) {
			
			int size = inv.getSlots();

			for(int i = 0; i < size; i++) {
				if(inv.getStackInSlot(i) != null && inv.getStackInSlot(i) != ItemStack.EMPTY) {
					
					if(inventory.getStackInSlot(slot).getItem() == Items.AIR)
						return false;
					
					ItemStack sta1 = inv.getStackInSlot(i).copy();
					ItemStack sta2 = inventory.getStackInSlot(slot).copy();
					if(sta1 != null && sta2 != null) {
						sta1.setCount(1);
						sta2.setCount(1);
					
						if(isItemAcceptable(sta1, sta2) && inv.getStackInSlot(i).getCount() < inv.getStackInSlot(i).getMaxStackSize()) {
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
	
	/**Loads assembler's input queue from chests*/
	/*public boolean tryFillAssembler(IInventory inventory, int slot) {

		FluidStack[] inputs = MachineRecipes.getFluidInputFromTempate(slots[4]);
		FluidStack[] outputs = MachineRecipes.getFluidOutputFromTempate(slots[4]);
		
		if(!((MachineRecipes.getChemInputFromTempate(slots[4]) != null || !Library.isArrayEmpty(inputs)) && 
				(MachineRecipes.getChemOutputFromTempate(slots[4]) != null || !Library.isArrayEmpty(outputs))))
			return false;
		else {
			List<ItemStack> list = MachineRecipes.getChemInputFromTempate(slots[4]);
			if(list == null || list.isEmpty())
				return false;
			
			for(int i = 0; i < list.size(); i++)
				list.get(i).stackSize = 1;


			if(inventory.getStackInSlot(slot) == null)
				return false;
			
			ItemStack stack = inventory.getStackInSlot(slot).copy();
			stack.stackSize = 1;
			
			boolean flag = false;
			
			for(int i = 0; i < list.size(); i++)
				if(ItemStack.areItemStacksEqual(stack, list.get(i)) && ItemStack.areItemStackTagsEqual(stack, list.get(i)))
					flag = true;
			
			if(!flag)
				return false;
			
		}
		
		for(int i = 13; i < 17; i++) {
			
			if(slots[i] != null) {
			
				ItemStack sta1 = inventory.getStackInSlot(slot).copy();
				ItemStack sta2 = slots[i].copy();
				if(sta1 != null && sta2 != null) {
					sta1.stackSize = 1;
					sta2.stackSize = 1;
			
					if(ItemStack.areItemStacksEqual(sta1, sta2) && ItemStack.areItemStackTagsEqual(sta1, sta2) && slots[i].stackSize < slots[i].getMaxStackSize()) {
						ItemStack sta3 = inventory.getStackInSlot(slot).copy();
						sta3.stackSize--;
						if(sta3.stackSize <= 0)
							sta3 = null;
						inventory.setInventorySlotContents(slot, sta3);
				
						slots[i].stackSize++;
						return true;
					}
				}
			}
		}
		
		for(int i = 13; i < 17; i++) {

			ItemStack sta2 = inventory.getStackInSlot(slot).copy();
			if(slots[i] == null && sta2 != null) {
				sta2.stackSize = 1;
				slots[i] = sta2.copy();
				
				ItemStack sta3 = inventory.getStackInSlot(slot).copy();
				sta3.stackSize--;
				if(sta3.stackSize <= 0)
					sta3 = null;
				inventory.setInventorySlotContents(slot, sta3);
				
				return true;
			}
		}
		
		return false;
	}*/
	
	public boolean tryFillAssemblerCap(IItemHandler inv, int slot) {
		
		FluidStack[] inputs = MachineRecipes.getFluidInputFromTempate(inventory.getStackInSlot(4));
		FluidStack[] outputs = MachineRecipes.getFluidOutputFromTempate(inventory.getStackInSlot(4));
		
		if(!((MachineRecipes.getChemInputFromTempate(inventory.getStackInSlot(4)) != null || !Library.isArrayEmpty(inputs)) && 
				(MachineRecipes.getChemOutputFromTempate(inventory.getStackInSlot(4)) != null || !Library.isArrayEmpty(outputs))))
			return false;
		else {
			List<ItemStack> list = MachineRecipes.getChemInputFromTempate(inventory.getStackInSlot(4));
			if(list == null)
				return false;
			for(int i = 0; i < list.size(); i++)
				list.get(i).setCount(1);
			

			if(inv.getStackInSlot(slot) == null)
				return false;
			
			ItemStack stack = inv.getStackInSlot(slot).copy();
			stack.setCount(1);
			
			boolean flag = false;
			
			for(int i = 0; i < list.size(); i++)
				if(isItemAcceptable(stack, list.get(i)))
					flag = true;
			
			if(!flag)
				return false;
			
		}
		
		for(int i = 13; i < 17; i++) {
			
			if(inventory.getStackInSlot(i).getItem() != Items.AIR) {
			
				ItemStack sta1 = inv.getStackInSlot(slot).copy();
				ItemStack sta2 = inventory.getStackInSlot(i).copy();
				if(sta1 != null && sta2 != null) {
					sta1.setCount(1);;
					sta2.setCount(1);;
			
					if(isItemAcceptable(sta1, sta2) && inventory.getStackInSlot(i).getCount() < inventory.getStackInSlot(i).getMaxStackSize()) {
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
		
		for(int i = 13; i < 17; i++) {

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
				return true;
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
				
					if(sta != null && isItemAcceptable(sta, st) && array.getStackInSlot(i).getCount() > 0) {
						array.getStackInSlot(i).shrink(1);;
						
						if(array.getStackInSlot(i).isEmpty())
							array.setStackInSlot(i, ItemStack.EMPTY);;
						
						return true;
					}
				}
			}
			
			return false;
		}
	
	public boolean isItemAcceptable(ItemStack stack1, ItemStack stack2) {
		
		if(stack1 != null && stack2 != null && stack1.getItem() != Items.AIR && stack1.getItem() != Items.AIR) {
			if(Library.areItemStacksCompatible(stack1, stack2))
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

	public void fillFluidInit(FluidTank tank) {
		int meta = world.getBlockState(pos).getValue(MachineChemplant.FACING);
		MutableBlockPos fill = new BlockPos.MutableBlockPos();
		boolean update = false || needsUpdate;
		if(meta == 5) {
			update = FFUtils.fillFluid(this, tank, world, fill.setPos(pos.getX()-2, pos.getY(), pos.getZ()), 2000) || update;
			update = FFUtils.fillFluid(this, tank, world, fill.setPos(pos.getX()-2, pos.getY(), pos.getZ()+1), 2000) || update;
			update = FFUtils.fillFluid(this, tank, world, fill.setPos(pos.getX()+3, pos.getY(), pos.getZ()), 2000) || update;
			update = FFUtils.fillFluid(this, tank, world, fill.setPos(pos.getX()+3, pos.getY(), pos.getZ()+1), 2000) || update;
		}
		
		if(meta == 3) {
			update = FFUtils.fillFluid(this, tank, world, fill.setPos(pos.getX(), pos.getY(), pos.getZ()-2), 2000) || update;
			update = FFUtils.fillFluid(this, tank, world, fill.setPos(pos.getX()-1, pos.getY(), pos.getZ()-2), 2000) || update;
			update = FFUtils.fillFluid(this, tank, world, fill.setPos(pos.getX(), pos.getY(), pos.getZ()+3), 2000) || update;
			update = FFUtils.fillFluid(this, tank, world, fill.setPos(pos.getX()-1, pos.getY(), pos.getZ()+3), 2000) || update;
		}
		
		if(meta == 2) {
			update = FFUtils.fillFluid(this, tank, world, fill.setPos(pos.getX(), pos.getY(), pos.getZ()+2), 2000) || update;
			update = FFUtils.fillFluid(this, tank, world, fill.setPos(pos.getX()+1, pos.getY(), pos.getZ()+2), 2000) || update;
			update = FFUtils.fillFluid(this, tank, world, fill.setPos(pos.getX(), pos.getY(), pos.getZ()-3), 2000) || update;
			update = FFUtils.fillFluid(this, tank, world, fill.setPos(pos.getX()+1, pos.getY(), pos.getZ()-3), 2000) || update;
		}
		
		if(meta == 4) {
			update = FFUtils.fillFluid(this, tank, world, fill.setPos(pos.getX()+2, pos.getY(), pos.getZ()), 2000) || update;
			update = FFUtils.fillFluid(this, tank, world, fill.setPos(pos.getX()+2, pos.getY(), pos.getZ()-1), 2000) || update;
			update = FFUtils.fillFluid(this, tank, world, fill.setPos(pos.getX()-3, pos.getY(), pos.getZ()), 2000) || update;
			update = FFUtils.fillFluid(this, tank, world, fill.setPos(pos.getX()-3, pos.getY(), pos.getZ()-1), 2000) || update;
		}
		needsUpdate = update;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) :
			capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(new ChemplantFluidHandler(tanks, tankTypes)) :
				super.getCapability(capability, facing);
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);

		return new SPacketUpdateTileEntity(pos, 0, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {

		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public void recievePacket(NBTTagCompound[] tags) {
		if(tags.length != 4){
			return;
		} else {
			tanks[0].readFromNBT(tags[0]);
			tanks[1].readFromNBT(tags[1]);
			tanks[2].readFromNBT(tags[2]);
			tanks[3].readFromNBT(tags[3]);
		}
		
	}
	
	private class ChemplantFluidHandler implements IFluidHandler{

		private FluidTank[] tanks;
		private Fluid[] tankTypes;
		
		public ChemplantFluidHandler(FluidTank[] tanks, Fluid[] tankTypes){
			this.tanks = tanks;
			this.tankTypes = tankTypes;
		}
		
		@Override
		public IFluidTankProperties[] getTankProperties() {
			return new IFluidTankProperties[]{tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0], tanks[2].getTankProperties()[0], tanks[3].getTankProperties()[0]};
		}

		@Override
		public int fill(FluidStack resource, boolean doFill) {
			if(resource == null)
				return 0;
			if(tankTypes[0] != null && resource.getFluid() == tankTypes[0]) {
				return tanks[0].fill(resource, doFill);
			}
			if(tankTypes[1] != null && resource.getFluid() == tankTypes[1]){
				return tanks[1].fill(resource, doFill);
			}
			return 0;
		}

		@Override
		public FluidStack drain(FluidStack resource, boolean doDrain) {
			if(resource == null)
				return null;
			if (resource.isFluidEqual(tanks[2].getFluid())) {
				return tanks[2].drain(resource.amount, doDrain);
			}
			if (resource.isFluidEqual(tanks[3].getFluid())) {
				return tanks[3].drain(resource.amount, doDrain);
			}
			return null;
		}

		@Override
		public FluidStack drain(int maxDrain, boolean doDrain) {
			if (tanks[2].getFluid() != null) {
				return tanks[2].drain(maxDrain, doDrain);
			} else if(tanks[3].getFluid() != null){
				return tanks[3].drain(maxDrain, doDrain);
			}
			return null;
		}
		
	}

	public ItemStack getStackInSlot(int i) {
		return inventory.getStackInSlot(i);
	}

	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.chemplant";
	}
	
	private long detectPower;
	private boolean detectIsProgressing;
	private FluidTank[] detectTanks = new FluidTank[]{null, null, null, null};
	
	private void detectAndSendChanges() {
		
		PacketDispatcher.wrapper.sendToAll(new LoopedSoundPacket(pos.getX(), pos.getY(), pos.getZ()));
		
		
		boolean mark = false;
		
		if(detectIsProgressing != isProgressing){
			mark = true;
			detectIsProgressing = isProgressing;
		}
		PacketDispatcher.wrapper.sendToAllAround(new TEChemplantPacket(pos.getX(), pos.getY(), pos.getZ(), isProgressing), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 100));
		if(detectPower != power){
			mark = true;
			detectPower = power;
		}
		PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos.getX(), pos.getY(), pos.getZ(), power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 100));
		if(!FFUtils.areTanksEqual(detectTanks[0], tanks[0])){
			detectTanks[0] = FFUtils.copyTank(tanks[0]);
			mark = true;
			needsUpdate = true;
		}
		if(!FFUtils.areTanksEqual(detectTanks[1], tanks[1])){
			detectTanks[1] = FFUtils.copyTank(tanks[1]);
			mark = true;
			needsUpdate = true;
		}
		if(!FFUtils.areTanksEqual(detectTanks[2], tanks[2])){
			detectTanks[2] = FFUtils.copyTank(tanks[2]);
			mark = true;
			needsUpdate = true;
		}
		if(!FFUtils.areTanksEqual(detectTanks[3], tanks[3])){
			detectTanks[3] = FFUtils.copyTank(tanks[3]);
			mark = true;
			needsUpdate = true;
		}
		PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos.getX(), pos.getY(), pos.getZ(), new FluidTank[] {tanks[0], tanks[1], tanks[2], tanks[3]}), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 100));
		
		if(mark)
			markDirty();
	}
}
