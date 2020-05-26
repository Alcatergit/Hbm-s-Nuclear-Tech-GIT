package com.hbm.tileentity.machine;

import com.hbm.interfaces.IClientRequestUpdator;
import com.hbm.interfaces.IConsumer;
import com.hbm.inventory.MachineRecipes;
import com.hbm.items.machine.ItemBattery;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.ClientRequestUpdatePacket;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachineCentrifuge extends TileEntity implements ITickable, IConsumer, IClientRequestUpdator {

	public ItemStackHandler inventory;
	
	public int dualCookTime;
	public long power;
	public boolean isProgressing;
	public static final int maxPower = 100000;
	public static final int processingSpeed = 200;
	private boolean requestUpdate = true;
	private boolean firstUpdate = true;
	
	//private static final int[] slots_top = new int[] {0};
	//private static final int[] slots_bottom = new int[] {2, 3, 4, 5};
	//private static final int[] slots_side = new int[] {0, 1};
	
	private String customName;
	
	public TileEntityMachineCentrifuge() {
		inventory = new ItemStackHandler(6){
			@Override
			public boolean isItemValid(int slot, ItemStack stack) {
				if(slot == 2 || slot == 3 || slot == 4 || slot == 5)
				{
					return false;
				}
				
				if(slot == 1) {
					return stack.getItem() instanceof ItemBattery;
				}
				
				return !(stack.getItem() instanceof ItemBattery);
			}
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.centrifuge";
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
	
	public int getCentrifugeProgressScaled(int i) {
		return (dualCookTime * i) / processingSpeed;
	}
	
	public long getPowerRemainingScaled(int i) {
		return (power * i) / maxPower;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("powerTime", power);
		compound.setShort("cookTime", (short) dualCookTime);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		power = compound.getLong("powerTime");
		dualCookTime = compound.getShort("CookTime");
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	public boolean canProcess() {
		
		if(inventory.getStackInSlot(0).isEmpty())
		{
			return false;
		}
		ItemStack[] itemStack = MachineRecipes.getCentrifugeProcessingResult(inventory.getStackInSlot(0));
		if(itemStack == null)
		{
			return false;
		}
		
		if(inventory.getStackInSlot(2).isEmpty() && inventory.getStackInSlot(3).isEmpty() && inventory.getStackInSlot(4).isEmpty() && inventory.getStackInSlot(5).isEmpty())
		{
			return true;
		}
		
		if((inventory.getStackInSlot(2).isEmpty() || (itemStack[0] != null && inventory.getStackInSlot(2).isItemEqual(itemStack[0]) && inventory.getStackInSlot(2).getCount() + itemStack[0].getCount() <= itemStack[0].getMaxStackSize())) && 
				(inventory.getStackInSlot(3).isEmpty() || (itemStack[1] != null && inventory.getStackInSlot(3).isItemEqual(itemStack[1]) && inventory.getStackInSlot(3).getCount() + itemStack[1].getCount() <= itemStack[1].getMaxStackSize())) && 
				(inventory.getStackInSlot(4).isEmpty() || (itemStack[2] != null && inventory.getStackInSlot(4).isItemEqual(itemStack[2]) && inventory.getStackInSlot(4).getCount() + itemStack[2].getCount() <= itemStack[2].getMaxStackSize())) && 
				(inventory.getStackInSlot(5).isEmpty() || (itemStack[3] != null && inventory.getStackInSlot(5).isItemEqual(itemStack[3]) && inventory.getStackInSlot(5).getCount() + itemStack[3].getCount() <= itemStack[3].getMaxStackSize())))
		{
			return true;
		}
		
		return false;
	}
	
	private void processItem() {
		if(canProcess()) {
			ItemStack[] itemStack = MachineRecipes.getCentrifugeProcessingResult(inventory.getStackInSlot(0));
			
			if(inventory.getStackInSlot(2).isEmpty() && itemStack[0] != null)
			{
				inventory.setStackInSlot(2, itemStack[0].copy());
			}else if(itemStack[0] != null && inventory.getStackInSlot(2).isItemEqual(itemStack[0]))
			{
				inventory.getStackInSlot(2).grow(itemStack[0].getCount());
			}
			
			if(inventory.getStackInSlot(3).isEmpty() && itemStack[1] != null)
			{
				inventory.setStackInSlot(3, itemStack[1].copy());
			}else if(itemStack[1] != null && inventory.getStackInSlot(3).isItemEqual(itemStack[1]))
			{
				inventory.getStackInSlot(3).grow(itemStack[1].getCount());
			}
			
			if(inventory.getStackInSlot(4).isEmpty() && itemStack[2] != null)
			{
				inventory.setStackInSlot(4, itemStack[2].copy());
			}else if(itemStack[2] != null && inventory.getStackInSlot(4).isItemEqual(itemStack[2]))
			{
				inventory.getStackInSlot(4).grow(itemStack[2].getCount());
			}
			
			if(inventory.getStackInSlot(5).isEmpty() && itemStack[3] != null)
			{
				inventory.setStackInSlot(5, itemStack[3].copy());
			}else if(itemStack[3] != null && inventory.getStackInSlot(5).isItemEqual(itemStack[3]))
			{
				inventory.getStackInSlot(5).grow(itemStack[3].getCount());
			}
			
			//Drillgon200: What was the setFull3D about? And why is this in a for loop?
			for(int i = 0; i < 1; i++)
			{
				if(inventory.getStackInSlot(i).isEmpty())
				{
					//inventory.setStackInSlot(i, new ItemStack(inventory.getStackInSlot(i).getItem().setFull3D()));
					inventory.setStackInSlot(i, ItemStack.EMPTY);
				}else{
					inventory.getStackInSlot(i).shrink(1);
				}
			}
		}
	}
	
	public boolean hasPower() {
		return power > 0;
	}
	
	public boolean isProcessing() {
		return this.dualCookTime > 0;
	}
	
	@Override
	public void update() {
		
		if(firstUpdate){
			if(world.isRemote){
				PacketDispatcher.wrapper.sendToServer(new ClientRequestUpdatePacket(pos.getX(), pos.getY(), pos.getZ()));
			}
			firstUpdate = false;
		}
		
		if(!world.isRemote) {
			
			power = Library.chargeTEFromItems(inventory, 1, power, maxPower);
			
			if(hasPower() && isProcessing())
			{
				this.power -= 200;
				
				if(this.power < 0)
				{
					this.power = 0;
				}
			}
			
			if(hasPower() && canProcess())
			{
				isProgressing = true;
			} else {
				isProgressing = false;
			}
			
			
			PacketDispatcher.wrapper.sendToAllAround(new LoopedSoundPacket(pos.getX(), pos.getY(), pos.getZ()), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 200));
			detectAndSendChanges();
			
		}
		
		if(hasPower() && canProcess())
		{
			dualCookTime++;
			
			if(this.dualCookTime >= TileEntityMachineCentrifuge.processingSpeed)
			{
				this.dualCookTime = 0;
				this.processItem();
			}
		} else {
			dualCookTime = 0;
		}
	}
	
	private long detectPower;
	private int detectCookTime;
	private boolean detectIsProgressing;
	
	private void detectAndSendChanges(){
		boolean mark = false;
		if(detectPower != power || requestUpdate){
			mark = true;
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos.getX(), pos.getY(), pos.getZ(), power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 200));
			detectPower = power;
		}
		if(detectCookTime != dualCookTime || requestUpdate){
			mark = true;
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(pos.getX(), pos.getY(), pos.getZ(), dualCookTime, 0), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 200));
			detectCookTime = dualCookTime;
		}
		if(detectIsProgressing != isProgressing || requestUpdate){
			mark = true;
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(pos.getX(), pos.getY(), pos.getZ(), isProgressing ? 1 : 0, 1), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 200));
			detectIsProgressing = isProgressing;
		}
		
		requestUpdate = false;
		if(mark)
			markDirty();
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(pos, pos.add(1, 4, 1));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
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
	public void requestClientUpdate() {
		this.requestUpdate = true;
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
