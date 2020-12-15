package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IReactor;
import com.hbm.interfaces.ISource;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
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
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityFusionMultiblock extends TileEntity implements ITickable, IReactor, ISource, IFluidHandler, ITankPacketAcceptor {

	public long power;
	public final static long maxPower = 100000000;
	public ItemStackHandler inventory;
	public int age = 0;
	public List<IConsumer> list = new ArrayList<IConsumer>();
	public FluidTank tanks[];
	public Fluid[] tankTypes;
	public boolean needsUpdate;
	
	private String customName;
	
	public TileEntityFusionMultiblock() {
		needsUpdate = false;
		inventory = new ItemStackHandler(12){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
		tanks = new FluidTank[3];
		tankTypes = new Fluid[3];
		tanks[0] = new FluidTank(128000);
		tankTypes[0] = FluidRegistry.WATER;
		tanks[1] = new FluidTank(64000);
		tankTypes[1] = ModForgeFluids.deuterium;
		tanks[2] = new FluidTank(64000);
		tankTypes[2] = ModForgeFluids.tritium;
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.fusionMultiblock";
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
			return true;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		power = compound.getLong("power");
		if(compound.hasKey("tanks"))
			FFUtils.deserializeTankArray(compound.getTagList("tanks", 10), tanks);
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		tankTypes[0] = FluidRegistry.WATER;
		tankTypes[1] = ModForgeFluids.deuterium;
		tankTypes[2] = ModForgeFluids.tritium;
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("power", power);
		compound.setTag("tanks", FFUtils.serializeTankArray(tanks));
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void update() {
		age++;
		if(age >= 20)
		{
			age = 0;
		}
		
		
		
		if(!world.isRemote)
		{
			//Drillgon200: Move to server side only to avoid glitchy power
			if(age == 9 || age == 19)
				ffgeuaInit();
			
			long prevPower = power;
			int prevTank0Amount = tanks[0].getFluidAmount();
			int prevTank1Amount = tanks[1].getFluidAmount();
			int prevTank2Amount = tanks[2].getFluidAmount();
			
			if(needsUpdate){
				needsUpdate = false;
			}
			if(this.inputValidForTank(0, 0))
				if(FFUtils.fillFromFluidContainer(inventory, tanks[0], 0, 9))
					needsUpdate = true;
			if(this.inputValidForTank(1, 2))
				if(FFUtils.fillFromFluidContainer(inventory, tanks[1], 2, 10))
					needsUpdate = true;
			if(this.inputValidForTank(2, 3))
				if(FFUtils.fillFromFluidContainer(inventory, tanks[2], 3, 11))
					needsUpdate = true;
			
			
			if(inventory.getStackInSlot(2).getItem() == ModItems.tritium_deuterium_cake)
			{
				inventory.getStackInSlot(2).shrink(1);
				if(inventory.getStackInSlot(2).isEmpty())
				{
					inventory.setStackInSlot(2, ItemStack.EMPTY);
				}

				tanks[1].fill(new FluidStack(tankTypes[1],10000), true);
				tanks[2].fill(new FluidStack(tankTypes[2], 10000), true);
				needsUpdate = true;
			}
			
			if(inventory.getStackInSlot(3).getItem() == ModItems.tritium_deuterium_cake)
			{
				inventory.getStackInSlot(3).shrink(1);
				if(inventory.getStackInSlot(3).isEmpty())
				{
					inventory.setStackInSlot(3, ItemStack.EMPTY);
				}

				tanks[1].fill(new FluidStack(tankTypes[1], 10000), true);
				tanks[2].fill(new FluidStack(tankTypes[2], 10000), true);
				needsUpdate = true;
			}
			
			if(!isRunning() &&
					(inventory.getStackInSlot(4).getItem() == ModItems.fusion_core || inventory.getStackInSlot(4).getItem() == ModItems.energy_core) && inventory.getStackInSlot(4).getItemDamage() == 0 &&
					(inventory.getStackInSlot(5).getItem() == ModItems.fusion_core || inventory.getStackInSlot(5).getItem() == ModItems.energy_core) && inventory.getStackInSlot(5).getItemDamage() == 0 &&
					(inventory.getStackInSlot(6).getItem() == ModItems.fusion_core || inventory.getStackInSlot(6).getItem() == ModItems.energy_core) && inventory.getStackInSlot(6).getItemDamage() == 0 &&
					(inventory.getStackInSlot(7).getItem() == ModItems.fusion_core || inventory.getStackInSlot(7).getItem() == ModItems.energy_core) && inventory.getStackInSlot(7).getItemDamage() == 0 &&
					hasFuse() &&
					tanks[1].getFluidAmount() > 0 && tanks[2].getFluidAmount() > 0)
			{
				inventory.setStackInSlot(4, ItemStack.EMPTY);
				inventory.setStackInSlot(5, ItemStack.EMPTY);
				inventory.setStackInSlot(6, ItemStack.EMPTY);
				inventory.setStackInSlot(7, ItemStack.EMPTY);
				fillPlasma();
			} else {
				if(isStructureValid(world) && isRunning())
				{
					tanks[1].drain(1, true);
					tanks[2].drain(1, true);
					
					if(tanks[0].getFluidAmount() >= 20)
					{
						tanks[0].drain(20, true);
						power += 100000;
						
						if(isCoatingValid(world))
						{
							power += 100000;
						}
						
						if(power > maxPower)
						{
							power = maxPower;
						}
					}
					
					fillPlasma();
				} else {
					emptyPlasma();
				}
			}
			
			if(!isRunning())
			{
				emptyPlasma();
			}
			
			if(tanks[1].getFluidAmount() <= 0 || tanks[2].getFluidAmount() <= 0)
			{
				emptyPlasma();
			}
			
			power = Library.chargeItemsFromTE(inventory, 1, power, maxPower);

			PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos, new FluidTank[] {tanks[0], tanks[1], tanks[2]}), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 20));
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos, power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 20));
			
			if(prevTank0Amount != tanks[0].getFluidAmount() || prevTank1Amount != tanks[1].getFluidAmount() || prevTank2Amount != tanks[2].getFluidAmount() || prevPower != power)
				markDirty();
		}
	}

	@Override
	public boolean isStructureValid(World world) {
		
		//...and I wrote all of this by hand! Ha!
		//Drillgon200: Wait, seriously?
		
		MutableBlockPos mPos = new BlockPos.MutableBlockPos();
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		if(world.getBlockState(mPos.setPos(x + 5, y - 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y - 2, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y - 2, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y - 2, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y - 2, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y - 2, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y - 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y - 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y - 2, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y - 2, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y - 2, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y - 2, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y - 2, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y - 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y - 2, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y - 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y - 2, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y - 2, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y - 2, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y - 2, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y - 2, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y - 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y - 2, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y - 2, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y - 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y - 2, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y - 2, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y - 2, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y - 2, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y - 2, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y - 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y - 2, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y - 2, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y - 2, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y - 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y - 2, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y - 2, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y - 2, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y - 2, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y - 2, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y - 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y - 2, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y - 2, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y - 2, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y - 2, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y - 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y - 2, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y - 2, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y - 2, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y - 2, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y - 2, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y - 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y - 2, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y - 2, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y - 2, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y - 2, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y - 2, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y - 2, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y - 2, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y - 2, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y - 2, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y - 2, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y - 2, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y - 2, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y - 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y - 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y - 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y - 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y - 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y - 2, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y - 2, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y - 2, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y - 2, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y - 2, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y - 2, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y - 2, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y - 2, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y - 2, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y - 2, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y - 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y - 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y - 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y - 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y - 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 8, y - 2, z - 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 8, y - 2, z + 0)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 8, y - 2, z + 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 7, y - 2, z - 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 7, y - 2, z + 0)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 7, y - 2, z + 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 8, y - 2, z - 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 8, y - 2, z + 0)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 8, y - 2, z + 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 7, y - 2, z - 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 7, y - 2, z + 0)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 7, y - 2, z + 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 1, y - 2, z + 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 0, y - 2, z + 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 1, y - 2, z + 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 1, y - 2, z + 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 0, y - 2, z + 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 1, y - 2, z + 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 1, y - 2, z - 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 0, y - 2, z - 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 1, y - 2, z - 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 1, y - 2, z - 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 0, y - 2, z - 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 1, y - 2, z - 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 1, y - 2, z + 1)).getBlock() == ModBlocks.fusion_motor &&
				world.getBlockState(mPos.setPos(x + 1, y - 2, z + 0)).getBlock() == ModBlocks.fusion_motor &&
				world.getBlockState(mPos.setPos(x + 1, y - 2, z - 1)).getBlock() == ModBlocks.fusion_motor &&
				world.getBlockState(mPos.setPos(x + 0, y - 2, z + 1)).getBlock() == ModBlocks.fusion_motor &&
				world.getBlockState(mPos.setPos(x + 0, y - 2, z + 0)).getBlock() == ModBlocks.fusion_center &&
				world.getBlockState(mPos.setPos(x + 0, y - 2, z - 1)).getBlock() == ModBlocks.fusion_motor &&
				world.getBlockState(mPos.setPos(x - 1, y - 2, z + 1)).getBlock() == ModBlocks.fusion_motor &&
				world.getBlockState(mPos.setPos(x - 1, y - 2, z + 0)).getBlock() == ModBlocks.fusion_motor &&
				world.getBlockState(mPos.setPos(x - 1, y - 2, z - 1)).getBlock() == ModBlocks.fusion_motor &&
				
				world.getBlockState(mPos.setPos(x + 5, y + 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y + 2, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y + 2, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y + 2, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y + 2, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y + 2, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y + 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y + 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y + 2, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y + 2, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y + 2, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y + 2, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y + 2, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y + 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y + 2, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y + 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y + 2, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y + 2, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y + 2, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y + 2, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y + 2, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y + 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y + 2, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y + 2, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y + 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y + 2, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y + 2, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y + 2, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y + 2, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y + 2, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y + 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y + 2, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y + 2, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y + 2, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y + 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y + 2, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y + 2, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y + 2, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y + 2, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y + 2, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y + 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y + 2, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y + 2, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y + 2, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y + 2, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y + 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y + 2, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y + 2, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y + 2, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y + 2, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y + 2, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y + 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y + 2, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y + 2, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y + 2, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y + 2, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y + 2, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y + 2, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y + 2, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y + 2, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y + 2, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y + 2, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y + 2, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y + 2, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y + 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y + 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y + 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y + 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y + 2, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y + 2, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y + 2, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y + 2, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y + 2, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y + 2, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y + 2, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y + 2, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y + 2, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y + 2, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y + 2, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y + 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y + 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y + 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y + 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y + 2, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 8, y + 2, z - 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 8, y + 2, z + 0)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 8, y + 2, z + 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 7, y + 2, z - 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 7, y + 2, z + 0)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 7, y + 2, z + 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 8, y + 2, z - 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 8, y + 2, z + 0)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 8, y + 2, z + 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 7, y + 2, z - 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 7, y + 2, z + 0)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 7, y + 2, z + 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 1, y + 2, z + 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 0, y + 2, z + 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 1, y + 2, z + 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 1, y + 2, z + 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 0, y + 2, z + 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 1, y + 2, z + 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 1, y + 2, z - 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 0, y + 2, z - 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 1, y + 2, z - 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 1, y + 2, z - 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 0, y + 2, z - 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 1, y + 2, z - 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 1, y + 2, z + 1)).getBlock() == ModBlocks.fusion_motor &&
				world.getBlockState(mPos.setPos(x + 1, y + 2, z + 0)).getBlock() == ModBlocks.fusion_motor &&
				world.getBlockState(mPos.setPos(x + 1, y + 2, z - 1)).getBlock() == ModBlocks.fusion_motor &&
				world.getBlockState(mPos.setPos(x + 0, y + 2, z + 1)).getBlock() == ModBlocks.fusion_motor &&
				world.getBlockState(mPos.setPos(x + 0, y + 2, z + 0)).getBlock() == ModBlocks.fusion_center &&
				world.getBlockState(mPos.setPos(x + 0, y + 2, z - 1)).getBlock() == ModBlocks.fusion_motor &&
				world.getBlockState(mPos.setPos(x - 1, y + 2, z + 1)).getBlock() == ModBlocks.fusion_motor &&
				world.getBlockState(mPos.setPos(x - 1, y + 2, z + 0)).getBlock() == ModBlocks.fusion_motor &&
				world.getBlockState(mPos.setPos(x - 1, y + 2, z - 1)).getBlock() == ModBlocks.fusion_motor &&
				
				world.getBlockState(mPos.setPos(x + 6, y - 1, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 6, y - 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 6, y - 1, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 6, y - 1, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 6, y - 1, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 6, y - 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 6, y - 1, z + 3)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x - 6, y - 1, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 6, y - 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 6, y - 1, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 6, y - 1, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 6, y - 1, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 6, y - 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 6, y - 1, z + 3)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x - 3, y - 1, z + 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y - 1, z + 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y - 1, z + 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y - 1, z + 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y - 1, z + 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y - 1, z + 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y - 1, z + 6)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x - 3, y - 1, z - 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y - 1, z - 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y - 1, z - 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y - 1, z - 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y - 1, z - 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y - 1, z - 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y - 1, z - 6)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x + 5, y - 1, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y - 1, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y - 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y - 1, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y - 1, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y - 1, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y - 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y - 1, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y - 1, z + 4)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x - 5, y - 1, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y - 1, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y - 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y - 1, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y - 1, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y - 1, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y - 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y - 1, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y - 1, z + 4)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x - 4, y - 1, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y - 1, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y - 1, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y - 1, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y - 1, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y - 1, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y - 1, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y - 1, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y - 1, z + 5)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x - 4, y - 1, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y - 1, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y - 1, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y - 1, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y - 1, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y - 1, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y - 1, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y - 1, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y - 1, z - 5)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x + 4, y - 1, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y - 1, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y - 1, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y - 1, z - 4)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x + 3, y - 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y - 1, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y - 1, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y - 1, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y - 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x - 3, y - 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y - 1, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y - 1, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y - 1, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y - 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x - 2, y - 1, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y - 1, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y - 1, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y - 1, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y - 1, z + 3)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x - 2, y - 1, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y - 1, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y - 1, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y - 1, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y - 1, z - 3)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x + 2, y - 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y - 1, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y - 1, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y - 1, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y - 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y - 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y - 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y - 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y - 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y - 1, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y - 1, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y - 1, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y - 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y - 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y - 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y - 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				
				world.getBlockState(mPos.setPos(x + 6, y + 1, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 6, y + 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 6, y + 1, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 6, y + 1, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 6, y + 1, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 6, y + 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 6, y + 1, z + 3)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x - 6, y + 1, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 6, y + 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 6, y + 1, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 6, y + 1, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 6, y + 1, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 6, y + 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 6, y + 1, z + 3)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x - 3, y + 1, z + 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y + 1, z + 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y + 1, z + 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y + 1, z + 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y + 1, z + 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y + 1, z + 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y + 1, z + 6)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x - 3, y + 1, z - 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y + 1, z - 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y + 1, z - 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y + 1, z - 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y + 1, z - 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y + 1, z - 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y + 1, z - 6)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x + 5, y + 1, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y + 1, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y + 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y + 1, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y + 1, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y + 1, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y + 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y + 1, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y + 1, z + 4)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x - 5, y + 1, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y + 1, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y + 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y + 1, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y + 1, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y + 1, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y + 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y + 1, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y + 1, z + 4)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x - 4, y + 1, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y + 1, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y + 1, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y + 1, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y + 1, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y + 1, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y + 1, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y + 1, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y + 1, z + 5)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x - 4, y + 1, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y + 1, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y + 1, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y + 1, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y + 1, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y + 1, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y + 1, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y + 1, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y + 1, z - 5)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x + 4, y + 1, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y + 1, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y + 1, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y + 1, z - 4)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x + 3, y + 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y + 1, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y + 1, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y + 1, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y + 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x - 3, y + 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y + 1, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y + 1, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y + 1, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 3, y + 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x - 2, y + 1, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y + 1, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y + 1, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y + 1, z + 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y + 1, z + 3)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x - 2, y + 1, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y + 1, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y + 1, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y + 1, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y + 1, z - 3)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x + 2, y + 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y + 1, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y + 1, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y + 1, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y + 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y + 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y + 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y + 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y + 1, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y + 1, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y + 1, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y + 1, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y + 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y + 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y + 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y + 1, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				
				world.getBlockState(mPos.setPos(x + 8, y + 1, z - 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 8, y + 1, z + 0)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 8, y + 1, z + 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 7, y + 1, z - 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 7, y + 1, z + 0)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 7, y + 1, z + 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 8, y + 1, z - 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 8, y + 1, z + 0)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 8, y + 1, z + 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 7, y + 1, z - 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 7, y + 1, z + 0)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 7, y + 1, z + 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 1, y + 1, z + 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 0, y + 1, z + 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 1, y + 1, z + 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 1, y + 1, z + 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 0, y + 1, z + 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 1, y + 1, z + 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 1, y + 1, z - 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 0, y + 1, z - 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 1, y + 1, z - 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 1, y + 1, z - 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 0, y + 1, z - 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 1, y + 1, z - 7)).getBlock() == ModBlocks.fusion_heater &&
				
				world.getBlockState(mPos.setPos(x + 8, y - 1, z - 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 8, y - 1, z + 0)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 8, y - 1, z + 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 7, y - 1, z - 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 7, y - 1, z + 0)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 7, y - 1, z + 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 8, y - 1, z - 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 8, y - 1, z + 0)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 8, y - 1, z + 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 7, y - 1, z - 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 7, y - 1, z + 0)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 7, y - 1, z + 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 1, y - 1, z + 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 0, y - 1, z + 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 1, y - 1, z + 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 1, y - 1, z + 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 0, y - 1, z + 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 1, y - 1, z + 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 1, y - 1, z - 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 0, y - 1, z - 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 1, y - 1, z - 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 1, y - 1, z - 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 0, y - 1, z - 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 1, y - 1, z - 7)).getBlock() == ModBlocks.fusion_heater &&

				world.getBlockState(mPos.setPos(x, y + 1, z)).getBlock() == ModBlocks.fusion_center &&
				world.getBlockState(mPos.setPos(x, y - 1, z)).getBlock() == ModBlocks.fusion_center &&
				world.getBlockState(mPos.setPos(x + 6, y, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 6, y, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 6, y, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 6, y, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 6, y, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 6, y, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 6, y, z + 3)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x - 6, y, z - 3)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 6, y, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 6, y, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 6, y, z + 0)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 6, y, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 6, y, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 6, y, z + 3)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x - 3, y, z + 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y, z + 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y, z + 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y, z + 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y, z + 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y, z + 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y, z + 6)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x - 3, y, z - 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y, z - 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y, z - 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 0, y, z - 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y, z - 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y, z - 6)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 3, y, z - 6)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x + 5, y, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y, z - 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y, z - 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 5, y, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 4, y, z + 5)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 5, y, z + 4)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 4, y, z + 5)).getBlock() == ModBlocks.fusion_conductor &&

				world.getBlockState(mPos.setPos(x + 2, y, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y, z)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 2, y, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x, y, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y, z + 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y, z + 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y, z)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y, z - 1)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 2, y, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x - 1, y, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x, y, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				world.getBlockState(mPos.setPos(x + 1, y, z - 2)).getBlock() == ModBlocks.fusion_conductor &&
				
				world.getBlockState(mPos.setPos(x + 8, y, z - 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 8, y, z + 0)).getBlock() == ModBlocks.fusion_hatch &&
				world.getBlockState(mPos.setPos(x + 8, y, z + 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 7, y, z - 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 7, y, z + 0)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 7, y, z + 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 8, y, z - 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 8, y, z + 0)).getBlock() == ModBlocks.fusion_hatch &&
				world.getBlockState(mPos.setPos(x - 8, y, z + 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 7, y, z - 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 7, y, z + 0)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 7, y, z + 1)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 1, y, z + 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 0, y, z + 8)).getBlock() == ModBlocks.fusion_hatch &&
				world.getBlockState(mPos.setPos(x + 1, y, z + 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 1, y, z + 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 0, y, z + 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 1, y, z + 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 1, y, z - 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 0, y, z - 8)).getBlock() == ModBlocks.fusion_hatch &&
				world.getBlockState(mPos.setPos(x + 1, y, z - 8)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x - 1, y, z - 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 0, y, z - 7)).getBlock() == ModBlocks.fusion_heater &&
				world.getBlockState(mPos.setPos(x + 1, y, z - 7)).getBlock() == ModBlocks.fusion_heater &&

				world.getBlockState(mPos.setPos(x, y, z)).getBlock() == ModBlocks.fusion_core)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public boolean isCoatingValid(World world) {
		
		MutableBlockPos mPos = new BlockPos.MutableBlockPos();
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		if(world.getBlockState(mPos.setPos(x + 4, y - 1, z - 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 4, y - 1, z - 2)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 4, y - 1, z - 1)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 4, y - 1, z)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 4, y - 1, z + 1)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 4, y - 1, z + 2)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 4, y - 1, z + 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 4, y - 1, z - 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 4, y - 1, z - 2)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 4, y - 1, z - 1)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 4, y - 1, z)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 4, y - 1, z + 1)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 4, y - 1, z + 2)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 4, y - 1, z + 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 3, y - 1, z + 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 2, y - 1, z + 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 1, y - 1, z + 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x, y - 1, z + 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 1, y - 1, z + 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 2, y - 1, z + 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 3, y - 1, z + 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 3, y - 1, z - 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 2, y - 1, z - 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 1, y - 1, z - 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x, y - 1, z - 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 1, y - 1, z - 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 2, y - 1, z - 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 3, y - 1, z - 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 3, y - 1, z + 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 3, y - 1, z - 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 3, y - 1, z + 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 3, y - 1, z - 3)).getBlock() == ModBlocks.block_tungsten &&
				
				world.getBlockState(mPos.setPos(x + 4, y + 1, z - 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 4, y + 1, z - 2)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 4, y + 1, z - 1)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 4, y + 1, z)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 4, y + 1, z + 1)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 4, y + 1, z + 2)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 4, y + 1, z + 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 4, y + 1, z - 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 4, y + 1, z - 2)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 4, y + 1, z - 1)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 4, y + 1, z)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 4, y + 1, z + 1)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 4, y + 1, z + 2)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 4, y + 1, z + 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 3, y + 1, z + 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 2, y + 1, z + 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 1, y + 1, z + 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x, y + 1, z + 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 1, y + 1, z + 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 2, y + 1, z + 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 3, y + 1, z + 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 3, y + 1, z - 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 2, y + 1, z - 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 1, y + 1, z - 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x, y + 1, z - 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 1, y + 1, z - 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 2, y + 1, z - 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 3, y + 1, z - 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 3, y + 1, z + 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 3, y + 1, z - 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 3, y + 1, z + 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 3, y + 1, z - 3)).getBlock() == ModBlocks.block_tungsten &&
				
				world.getBlockState(mPos.setPos(x + 3, y, z - 2)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 3, y, z - 1)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 3, y, z)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 3, y, z + 1)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 3, y, z + 2)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 3, y, z - 2)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 3, y, z - 1)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 3, y, z)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 3, y, z + 1)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 3, y, z + 2)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 2, y, z + 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 1, y, z + 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x, y, z + 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 1, y, z + 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 2, y, z + 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 2, y, z - 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 1, y, z - 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x, y, z - 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 1, y, z - 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 2, y, z - 3)).getBlock() == ModBlocks.block_tungsten &&
				
				world.getBlockState(mPos.setPos(x + 5, y, z - 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 5, y, z - 2)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 5, y, z - 1)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 5, y, z + 0)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 5, y, z + 1)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 5, y, z + 2)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 5, y, z + 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 5, y, z - 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 5, y, z - 2)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 5, y, z - 1)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 5, y, z + 0)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 5, y, z + 1)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 5, y, z + 2)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 5, y, z + 3)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 3, y, z + 5)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 2, y, z + 5)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 1, y, z + 5)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 0, y, z + 5)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 1, y, z + 5)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 2, y, z + 5)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 3, y, z + 5)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 3, y, z - 5)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 2, y, z - 5)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 1, y, z - 5)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 0, y, z - 5)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 1, y, z - 5)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 2, y, z - 5)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 3, y, z - 5)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 4, y, z + 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x + 4, y, z - 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 4, y, z + 4)).getBlock() == ModBlocks.block_tungsten &&
				world.getBlockState(mPos.setPos(x - 4, y, z - 4)).getBlock() == ModBlocks.block_tungsten)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public boolean hasFuse() {
		return inventory.getStackInSlot(8).getItem() == ModItems.fuse || inventory.getStackInSlot(8).getItem() == ModItems.screwdriver;
	}

	@Override
	public int getWaterScaled(int i) {
		return 0;
	}

	@Override
	public int getCoolantScaled(int i) {
		return 0;
	}

	@Override
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}

	@Override
	public int getHeatScaled(int i) {
		return 0;
	}
	
	public boolean isRunning() {
		MutableBlockPos mPos = new BlockPos.MutableBlockPos();
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		if(hasFuse() && (
				world.getBlockState(mPos.setPos(x + 4, y, z - 3)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x + 4, y, z - 2)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x + 4, y, z - 1)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x + 4, y, z + 0)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x + 4, y, z + 1)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x + 4, y, z + 2)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x + 4, y, z + 3)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x - 4, y, z - 3)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x - 4, y, z - 2)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x - 4, y, z - 1)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x - 4, y, z + 0)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x - 4, y, z + 1)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x - 4, y, z + 2)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x - 4, y, z + 3)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x - 3, y, z + 4)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x - 2, y, z + 4)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x - 1, y, z + 4)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x + 0, y, z + 4)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x + 1, y, z + 4)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x + 2, y, z + 4)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x + 3, y, z + 4)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x - 3, y, z - 4)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x - 2, y, z - 4)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x - 1, y, z - 4)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x + 0, y, z - 4)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x + 1, y, z - 4)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x + 2, y, z - 4)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x + 3, y, z - 4)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x + 3, y, z + 3)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x + 3, y, z - 3)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x - 3, y, z + 3)).getBlock() == ModBlocks.plasma ||
				world.getBlockState(mPos.setPos(x - 3, y, z - 3)).getBlock() == ModBlocks.plasma))
		{
			return true;
		}
		return false;
	}
	
	public void fillPlasma() {
		MutableBlockPos mPos = new BlockPos.MutableBlockPos();
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		setPlasma(mPos.setPos(x + 4, y, z - 3));
		setPlasma(mPos.setPos(x + 4, y, z - 2));
		setPlasma(mPos.setPos(x + 4, y, z - 1));
		setPlasma(mPos.setPos(x + 4, y, z + 0));
		setPlasma(mPos.setPos(x + 4, y, z + 1));
		setPlasma(mPos.setPos(x + 4, y, z + 2));
		setPlasma(mPos.setPos(x + 4, y, z + 3));
		setPlasma(mPos.setPos(x - 4, y, z - 3));
		setPlasma(mPos.setPos(x - 4, y, z - 2));
		setPlasma(mPos.setPos(x - 4, y, z - 1));
		setPlasma(mPos.setPos(x - 4, y, z + 0));
		setPlasma(mPos.setPos(x - 4, y, z + 1));
		setPlasma(mPos.setPos(x - 4, y, z + 2));
		setPlasma(mPos.setPos(x - 4, y, z + 3));
		setPlasma(mPos.setPos(x - 3, y, z + 4));
		setPlasma(mPos.setPos(x - 2, y, z + 4));
		setPlasma(mPos.setPos(x - 1, y, z + 4));
		setPlasma(mPos.setPos(x + 0, y, z + 4));
		setPlasma(mPos.setPos(x + 1, y, z + 4));
		setPlasma(mPos.setPos(x + 2, y, z + 4));
		setPlasma(mPos.setPos(x + 3, y, z + 4));
		setPlasma(mPos.setPos(x - 3, y, z - 4));
		setPlasma(mPos.setPos(x - 2, y, z - 4));
		setPlasma(mPos.setPos(x - 1, y, z - 4));
		setPlasma(mPos.setPos(x + 0, y, z - 4));
		setPlasma(mPos.setPos(x + 1, y, z - 4));
		setPlasma(mPos.setPos(x + 2, y, z - 4));
		setPlasma(mPos.setPos(x + 3, y, z - 4));
		setPlasma(mPos.setPos(x + 3, y, z + 3));
		setPlasma(mPos.setPos(x + 3, y, z - 3));
		setPlasma(mPos.setPos(x - 3, y, z + 3));
		setPlasma(mPos.setPos(x - 3, y, z - 3));
	}
	
	public void emptyPlasma() {
		MutableBlockPos mPos = new BlockPos.MutableBlockPos();
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		removePlasma(mPos.setPos(x + 4, y, z - 3));
		removePlasma(mPos.setPos(x + 4, y, z - 2));
		removePlasma(mPos.setPos(x + 4, y, z - 1));
		removePlasma(mPos.setPos(x + 4, y, z + 0));
		removePlasma(mPos.setPos(x + 4, y, z + 1));
		removePlasma(mPos.setPos(x + 4, y, z + 2));
		removePlasma(mPos.setPos(x + 4, y, z + 3));
		removePlasma(mPos.setPos(x - 4, y, z - 3));
		removePlasma(mPos.setPos(x - 4, y, z - 2));
		removePlasma(mPos.setPos(x - 4, y, z - 1));
		removePlasma(mPos.setPos(x - 4, y, z + 0));
		removePlasma(mPos.setPos(x - 4, y, z + 1));
		removePlasma(mPos.setPos(x - 4, y, z + 2));
		removePlasma(mPos.setPos(x - 4, y, z + 3));
		removePlasma(mPos.setPos(x - 3, y, z + 4));
		removePlasma(mPos.setPos(x - 2, y, z + 4));
		removePlasma(mPos.setPos(x - 1, y, z + 4));
		removePlasma(mPos.setPos(x + 0, y, z + 4));
		removePlasma(mPos.setPos(x + 1, y, z + 4));
		removePlasma(mPos.setPos(x + 2, y, z + 4));
		removePlasma(mPos.setPos(x + 3, y, z + 4));
		removePlasma(mPos.setPos(x - 3, y, z - 4));
		removePlasma(mPos.setPos(x - 2, y, z - 4));
		removePlasma(mPos.setPos(x - 1, y, z - 4));
		removePlasma(mPos.setPos(x + 0, y, z - 4));
		removePlasma(mPos.setPos(x + 1, y, z - 4));
		removePlasma(mPos.setPos(x + 2, y, z - 4));
		removePlasma(mPos.setPos(x + 3, y, z - 4));
		removePlasma(mPos.setPos(x + 3, y, z + 3));
		removePlasma(mPos.setPos(x + 3, y, z - 3));
		removePlasma(mPos.setPos(x - 3, y, z + 3));
		removePlasma(mPos.setPos(x - 3, y, z - 3));
	}
	
	public void setPlasma(BlockPos pos) {
		if(world.getBlockState(pos).getBlock() != ModBlocks.plasma)
			world.setBlockState(pos, ModBlocks.plasma.getDefaultState());
	}
	
	public void removePlasma(BlockPos pos) {
		if(world.getBlockState(pos).getBlock() == ModBlocks.plasma)
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
	}

	protected boolean inputValidForTank(int tank, int slot){
		if(!inventory.getStackInSlot(slot).isEmpty() && tanks[tank] != null){
			if(isValidFluidForTank(tank, FluidUtil.getFluidContained(inventory.getStackInSlot(slot)))){
				return true;
			}
		}
		return false;
	}
	
	private boolean isValidFluidForTank(int tank, FluidStack stack) {
		if(stack == null || tanks[tank] == null)
			return false;
		return stack.getFluid() == tankTypes[tank];
	}

	@Override
	public void ffgeua(BlockPos pos, boolean newTact) {
		
		Library.ffgeua(new BlockPos.MutableBlockPos(pos), newTact, this, world);
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(pos.up(3), getTact());
		ffgeua(pos.down(3), getTact());
	}
	
	@Override
	public boolean getTact() {
		if(age >= 0 && age < 10)
		{
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
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0], tanks[2].getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(resource == null) {
			return 0;
		} else if(resource.getFluid() == tankTypes[0]) {
			needsUpdate = true;
			return tanks[0].fill(resource, doFill);			
		} else if(resource.getFluid() == tankTypes[1]) {
			needsUpdate = true;
			return tanks[1].fill(resource, doFill);
		} else if(resource.getFluid() == tankTypes[2]) {
			needsUpdate = true;
			return tanks[2].fill(resource, doFill);
		} else {
			return 0;
		}
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public void recievePacket(NBTTagCompound[] tags) {
		if(tags.length != 3){
			return;
		} else {
			tanks[0].readFromNBT(tags[0]);
			tanks[1].readFromNBT(tags[1]);
			tanks[2].readFromNBT(tags[2]);
		}
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this) : super.getCapability(capability, facing);
	}

}
