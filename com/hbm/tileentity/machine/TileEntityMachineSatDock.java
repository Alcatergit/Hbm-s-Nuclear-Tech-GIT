package com.hbm.tileentity.machine;

import java.util.List;
import java.util.Random;

import com.hbm.entity.missile.EntityMinerRocket;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemSatChip;
import com.hbm.saveddata.satellites.Satellite;
import com.hbm.saveddata.satellites.SatelliteMiner;
import com.hbm.saveddata.satellites.SatelliteSavedData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachineSatDock extends TileEntity implements ITickable {

	public ItemStackHandler inventory;
	
	//private static final int[] access = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };
	
	private String customName;
	
	public TileEntityMachineSatDock() {
		inventory = new ItemStackHandler(16){
			@Override
			protected void onContentsChanged(int slot) {
				super.onContentsChanged(slot);
				markDirty();
			}
		};
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.satDock";
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
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	SatelliteSavedData data = null;
	
	@Override
	public void update() {
		if(!world.isRemote) {
			
			if(data == null)
				data = (SatelliteSavedData)world.getPerWorldStorage().getOrLoadData(SatelliteSavedData.class, "satellites");
			
		    if(data == null) {
		        world.getPerWorldStorage().setData("satellites", new SatelliteSavedData(world));
		        data = (SatelliteSavedData)world.getPerWorldStorage().getOrLoadData(SatelliteSavedData.class, "satellites");
		    }
		    data.markDirty();

		    if(data != null && !inventory.getStackInSlot(15).isEmpty()) {
			    int freq = ItemSatChip.getFreq(inventory.getStackInSlot(15));
			    
			    Satellite sat = data.getSatFromFreq(freq);
			    
			    int delay = 10 * 60 * 1000;
			    
			    if(sat != null && sat instanceof SatelliteMiner) {
			    	
			    	SatelliteMiner miner = (SatelliteMiner)sat;
			    	
			    	if(miner.lastOp + delay < System.currentTimeMillis()) {
			    		
			        	EntityMinerRocket rocket = new EntityMinerRocket(world);
			        	rocket.posX = pos.getX() + 0.5;
			        	rocket.posY = 300;
			        	rocket.posZ = pos.getZ() + 0.5;
			        	world.spawnEntity(rocket);
			        	miner.lastOp = System.currentTimeMillis();
			        	data.markDirty();
			    	}
			    }
		    }
		    
		    List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos.getX() - 0.25 + 0.5, pos.getY() + 0.75, pos.getZ() - 0.25 + 0.5, pos.getX() + 0.25 + 0.5, pos.getY() + 2, pos.getZ() + 0.25 + 0.5));
		    
		    for(Entity e : list) {
		    	
		    	if(e instanceof EntityMinerRocket) {
		    		
		    		EntityMinerRocket rocket = (EntityMinerRocket)e;
		    		
		    		if(rocket.getDataManager().get(EntityMinerRocket.TIMER) == 1 && rocket.timer == 50) {
		    			unloadCargo();
		    		}
		    	}
		    }

		    ejectInto(pos.getX() + 2, pos.getY(), pos.getZ());
		    ejectInto(pos.getX() - 2, pos.getY(), pos.getZ());
		    ejectInto(pos.getX(), pos.getY(), pos.getZ() + 2);
		    ejectInto(pos.getX(), pos.getY(), pos.getZ() - 2);
		}
	}
	
	private static Random rand = new Random();
	
	private void unloadCargo() {
		
		int items = rand.nextInt(6) + 10;
		
		int randy = 0;
		rand = new Random();
		
		for(int i = 0; i < items; i++) {
			
			randy = (int) (rand.nextFloat() * 100);
			
			if(randy < 10)
				addToInv(new ItemStack(ModItems.powder_aluminium, 3));
			else if(randy < 20)
				addToInv(new ItemStack(ModItems.powder_iron, 3));
			else if(randy < 30)
				addToInv(new ItemStack(ModItems.powder_titanium, 2));
			else if(randy < 45)
				addToInv(new ItemStack(ModItems.powder_coal, 4));
			else if(randy < 50)
				addToInv(new ItemStack(ModItems.powder_uranium, 2));
			else if(randy < 53)
				addToInv(new ItemStack(ModItems.powder_plutonium, 1));
			else if(randy < 58)
				addToInv(new ItemStack(ModItems.powder_thorium, 2));
			else if(randy < 60)
				addToInv(new ItemStack(ModItems.powder_desh_mix, 3));
			else if(randy < 65)
				addToInv(new ItemStack(ModItems.powder_diamond, 2));
			else if(randy < 75)
				addToInv(new ItemStack(Items.REDSTONE, 5));
			else if(randy < 77)
				addToInv(new ItemStack(ModItems.powder_nitan_mix, 2));
			else if(randy < 80)
				addToInv(new ItemStack(ModItems.powder_power, 2));
			else if(randy < 85)
				addToInv(new ItemStack(ModItems.powder_copper, 5));
			else if(randy < 90)
				addToInv(new ItemStack(ModItems.powder_lead, 3));
			else if(randy < 95)
				addToInv(new ItemStack(ModItems.fluorite, 4));
			else if(randy < 99)
				addToInv(new ItemStack(ModItems.powder_lapis, 4));
			else
				addToInv(new ItemStack(ModItems.powder_combine_steel, 1));
		}
	}
	
	private void addToInv(ItemStack stack) {
		
		for(int i = 0; i < 15; i++) {
			
			if(!inventory.getStackInSlot(i).isEmpty() && inventory.getStackInSlot(i).getItem() == stack.getItem() && inventory.getStackInSlot(i).getItemDamage() == stack.getItemDamage() && 
					inventory.getStackInSlot(i).getCount() < inventory.getStackInSlot(i).getMaxStackSize()) {
				
				inventory.getStackInSlot(i).grow(1);
				
				return;
			}
		}
		
		for(int i = 0; i < 15; i++) {
			
			if(inventory.getStackInSlot(i).isEmpty()) {
				inventory.setStackInSlot(i, new ItemStack(stack.getItem(), 1, stack.getItemDamage()));
				return;
			}
		}
	}
	
	private void ejectInto(int x, int y, int z) {
		BlockPos eject = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(eject);
		
		if(te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)){
			IItemHandler test = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			if(test instanceof IItemHandlerModifiable){
				IItemHandlerModifiable chest = (IItemHandlerModifiable)test;
				for(int i = 0; i < 15; i++) {
					
					if(!inventory.getStackInSlot(i).isEmpty()) {
						
						for(int j = 0; j < chest.getSlots(); j++) {
							
							ItemStack sta = inventory.getStackInSlot(i).copy();
							sta.setCount(1);
							
							if(chest.getStackInSlot(j) != null && chest.getStackInSlot(j).getItem() == inventory.getStackInSlot(i).getItem() && 
									chest.getStackInSlot(j).getItemDamage() == inventory.getStackInSlot(i).getItemDamage() && 
									chest.getStackInSlot(j).getCount() < chest.getStackInSlot(j).getMaxStackSize()) {
								
								inventory.getStackInSlot(i).shrink(1);
								
								if(inventory.getStackInSlot(i).isEmpty())
									inventory.setStackInSlot(i, ItemStack.EMPTY);
								
								chest.setStackInSlot(j, new ItemStack(chest.getStackInSlot(j).getItem(), chest.getStackInSlot(j).getCount() + 1, chest.getStackInSlot(j).getItemDamage()));
								return;
							}
						}
					}
				}
				
				for(int i = 0; i < 15; i++) {
					
					if(!inventory.getStackInSlot(i).isEmpty()) {
						
						for(int j = 0; j < chest.getSlots(); j++) {
							
							ItemStack sta = inventory.getStackInSlot(i).copy();
							sta.setCount(1);
							
							if((chest.getStackInSlot(j) == null || chest.getStackInSlot(j).isEmpty()) && chest.isItemValid(j, sta)) {
								
								inventory.getStackInSlot(i).shrink(1);
								
								if(inventory.getStackInSlot(i).isEmpty())
									inventory.setStackInSlot(i, ItemStack.EMPTY);
								
								chest.setStackInSlot(j, new ItemStack(sta.getItem(), 1, sta.getItemDamage()));
								return;
							}
						}
					}
				}
			}
		}
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
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : super.getCapability(capability, facing);
	}
}
