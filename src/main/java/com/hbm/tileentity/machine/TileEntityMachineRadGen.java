package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachineRadGen extends TileEntity implements ITickable, ISource {

	public ItemStackHandler inventory;

	public long power;
	public int fuel;
	public int strength;
	public int mode;
	public int soundCycle = 0;
	public float rotation;
	public static final long maxPower = 100000;
	public static final int maxFuel = 10000;
	public static final int maxStrength = 1000;
	public int age = 0;
	public List<IConsumer> list = new ArrayList<IConsumer>();

	///private static final int[] slots_top = new int[] { 0 };
	///private static final int[] slots_bottom = new int[] { 0, 0 };
	///private static final int[] slots_side = new int[] { 0 };

	private String customName;
	
	public TileEntityMachineRadGen() {
		inventory = new ItemStackHandler(3){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
	}

	private static int[] accessibleSlots = new int[]{0};

	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.radGen";
	}

	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}


	public void setCustomName(String name) {
		this.customName = name;
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (world.getTileEntity(pos) != this) {
			return false;
		} else {
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.power = compound.getLong("power");
		this.fuel = compound.getInteger("fuel");
		this.strength = compound.getInteger("strength");
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("power", power);
		compound.setInteger("fuel", fuel);
		compound.setInteger("strength", strength);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}

	public int[] getAccessibleSlotsFromSide(EnumFacing e) {
		return accessibleSlots;
	}

	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return i == 0 && this.getRads(stack) > 0;
	}
	
	@Override
	public void update() {
		if (!world.isRemote) {
			
			age++;
			if(age >= 20)
			{
				age = 0;
			}
			
			if(age == 9 || age == 19)
				ffgeuaInit();
		}
		
		if(!world.isRemote) {
			
			int r = getRads(inventory.getStackInSlot(0));
			if(r > 0) {
				if(inventory.getStackInSlot(0).getItem().hasContainerItem(inventory.getStackInSlot(0))) {
					if(inventory.getStackInSlot(1).isEmpty()) {
						if(fuel + r <= maxFuel) {
							
							inventory.setStackInSlot(1, new ItemStack(inventory.getStackInSlot(0).getItem().getContainerItem()));
							
							inventory.getStackInSlot(0).shrink(1);
							if(inventory.getStackInSlot(0).isEmpty())
								inventory.setStackInSlot(0, ItemStack.EMPTY);
							fuel += r;
						}
					} else if(inventory.getStackInSlot(0).getItem().getContainerItem() == inventory.getStackInSlot(1).getItem() && inventory.getStackInSlot(1).getCount() < inventory.getStackInSlot(1).getMaxStackSize()) {
						if(fuel + r <= maxFuel) {
							
							inventory.getStackInSlot(1).grow(1);
							
							inventory.getStackInSlot(0).shrink(1);
							if(inventory.getStackInSlot(0).isEmpty())
								inventory.setStackInSlot(0, ItemStack.EMPTY);
							fuel += r;
						}
					}
				} else {
					if(fuel + r <= maxFuel) {
						inventory.getStackInSlot(0).shrink(1);
						if(inventory.getStackInSlot(0).isEmpty())
							inventory.setStackInSlot(0, ItemStack.EMPTY);
						fuel += r;
					}
				}
			}
			
			if(fuel > 0) {
				fuel--;
				if(strength < maxStrength)
					strength += Math.ceil(fuel / 1000);
			} else {
				if(strength > 0)
					strength -= (strength * 0.1);
			}

			if(strength > maxStrength)
				strength = maxStrength;

			if(strength < 0)
				strength = 0;
			
			power += strength;
			
			if(power > maxPower)
				power = maxPower;
			
			mode = 0;
			if(strength > 0)
				mode = 1;
			if(strength > 800)
				mode = 2;
			
			//PacketDispatcher.wrapper.sendToAll(new TEIGeneratorPacket(xCoord, yCoord, zCoord, rotation, torque));
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos.getX(), pos.getY(), pos.getZ(), power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 20));
		}
	}
	
	private int getRads(ItemStack stack) {
		if(stack == null)
			return 0;
		
		Item item = stack.getItem();

		if(item == ModItems.nugget_uranium) return 5;
		if(item == ModItems.ingot_uranium) return 50;
		if(item == Item.getItemFromBlock(ModBlocks.block_uranium)) return 500;
		if(item == ModItems.rod_uranium) return 30;
		if(item == ModItems.rod_dual_uranium) return 60;
		if(item == ModItems.rod_quad_uranium) return 90;

		if(item == ModItems.nugget_u235) return 50;
		if(item == ModItems.ingot_u235) return 500;
		if(item == ModItems.rod_u235) return 300;
		if(item == ModItems.rod_dual_u235) return 600;
		if(item == ModItems.rod_quad_u235) return 900;
		
		if(item == ModItems.nugget_u238) return 10;
		if(item == ModItems.ingot_u238) return 100;
		if(item == ModItems.rod_u238) return 60;
		if(item == ModItems.rod_dual_u238) return 120;
		if(item == ModItems.rod_quad_u238) return 240;

		if(item == ModItems.nugget_pu238) return 40;
		if(item == ModItems.ingot_pu238) return 400;
		if(item == ModItems.rod_pu238) return 240;
		if(item == ModItems.rod_dual_pu238) return 480;
		if(item == ModItems.rod_quad_pu238) return 960;
		
		if(item == ModItems.nugget_pu239) return 70;
		if(item == ModItems.ingot_pu239) return 700;
		if(item == ModItems.rod_pu239) return 420;
		if(item == ModItems.rod_dual_pu239) return 840;
		if(item == ModItems.rod_quad_pu239) return 1680;
		
		if(item == ModItems.nugget_pu240) return 20;
		if(item == ModItems.ingot_pu240) return 200;
		if(item == ModItems.rod_pu240) return 120;
		if(item == ModItems.rod_dual_pu240) return 240;
		if(item == ModItems.rod_quad_pu240) return 480;
		
		if(item == ModItems.nugget_neptunium) return 60;
		if(item == ModItems.ingot_neptunium) return 600;
		if(item == ModItems.rod_neptunium) return 360;
		if(item == ModItems.rod_dual_neptunium) return 720;
		if(item == ModItems.rod_quad_neptunium) return 1440;

		if(item == ModItems.nugget_schrabidium) return 100;
		if(item == ModItems.ingot_schrabidium) return 1000;
		if(item == Item.getItemFromBlock(ModBlocks.block_schrabidium)) return 10000;
		if(item == ModItems.rod_schrabidium) return 600;
		if(item == ModItems.rod_dual_schrabidium) return 1200;
		if(item == ModItems.rod_quad_schrabidium) return 2400;
		
		if(item == ModItems.nugget_solinium) return 120;
		if(item == ModItems.ingot_solinium) return 1200;
		if(item == ModItems.rod_schrabidium) return 720;
		if(item == ModItems.rod_dual_schrabidium) return 1440;
		if(item == ModItems.rod_quad_schrabidium) return 2880;
		
		if(item == ModItems.nuclear_waste) return 100;
		if(item == ModItems.waste_uranium) return 150;
		if(item == ModItems.waste_plutonium) return 150;
		if(item == ModItems.waste_mox) return 150;
		if(item == ModItems.waste_schrabidium) return 150;
		if(item == Item.getItemFromBlock(ModBlocks.block_waste)) return 1000;
		if(item == Item.getItemFromBlock(ModBlocks.yellow_barrel)) return 900;
		if(item == ModItems.trinitite) return 80;
		if(item == Item.getItemFromBlock(ModBlocks.block_trinitite)) return 800;

		if(item == Item.getItemFromBlock(ModBlocks.sellafield_0)) return 1000;
		if(item == Item.getItemFromBlock(ModBlocks.sellafield_1)) return 2000;
		if(item == Item.getItemFromBlock(ModBlocks.sellafield_2)) return 3000;
		if(item == Item.getItemFromBlock(ModBlocks.sellafield_3)) return 4000;
		if(item == Item.getItemFromBlock(ModBlocks.sellafield_4)) return 5000;
		if(item == Item.getItemFromBlock(ModBlocks.sellafield_core)) return 10000;

		if(item == ModItems.rod_uranium_fuel_depleted) return 400;
		if(item == ModItems.rod_dual_uranium_fuel_depleted) return 800;
		if(item == ModItems.rod_quad_uranium_fuel_depleted) return 1600;

		if(item == ModItems.rod_mox_fuel_depleted) return 550;
		if(item == ModItems.rod_dual_mox_fuel_depleted) return 1100;
		if(item == ModItems.rod_quad_mox_fuel_depleted) return 2200;

		if(item == ModItems.rod_plutonium_fuel_depleted) return 600;
		if(item == ModItems.rod_dual_plutonium_fuel_depleted) return 1200;
		if(item == ModItems.rod_quad_plutonium_fuel_depleted) return 2400;

		if(item == ModItems.rod_schrabidium_fuel_depleted) return 800;
		if(item == ModItems.rod_dual_schrabidium_fuel_depleted) return 1600;
		if(item == ModItems.rod_quad_schrabidium_fuel_depleted) return 3200;
		
		if(item == ModItems.rod_quad_euphemium) return 5000;
		
		if(item == ModItems.rod_waste) return 600;
		if(item == ModItems.rod_dual_waste) return 1200;
		if(item == ModItems.rod_quad_waste) return 4800;

		if(item == Item.getItemFromBlock(ModBlocks.block_yellowcake)) return 1000;
		if(item == Item.getItemFromBlock(ModBlocks.mush)) return 10;
		if(item == Item.getItemFromBlock(ModBlocks.waste_earth)) return 25;
		if(item == Item.getItemFromBlock(ModBlocks.waste_mycelium)) return 150;
		
		return 0;
	}
	
	public int getFuelScaled(int i) {
		return (fuel * i) / maxFuel;
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getStrengthScaled(int i) {
		return (strength * i) / maxStrength;
	}
	
	@Override
	public void ffgeua(BlockPos pos, boolean newTact) {
		
		Library.ffgeua(new BlockPos.MutableBlockPos(pos), newTact, this, world);
	}

	@Override
	public void ffgeuaInit() {
		int i = getBlockMetadata();
		
		switch(i) {
		case 2: 
			ffgeua(pos.add(5, 0, 0), getTact()); break;
		case 3: 
			ffgeua(pos.add(-5, 0, 0), getTact()); break;
		case 4: 
			ffgeua(pos.add(0, 0, -5), getTact()); break;
		case 5: 
			ffgeua(pos.add(0, 0, 5), getTact()); break;
		}
	}

	@Override
	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
	}
	
	@Override
	public long getSPower() {
		return power;
	}

	@Override
	public void setSPower(long i) {
		this.power = i;
	}

	@Override
	public List<IConsumer> getList() {
		return list;
	}

	@Override
	public void clearList() {
		this.list.clear();
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
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
		} else {
			return super.getCapability(capability, facing);
		}
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

}
