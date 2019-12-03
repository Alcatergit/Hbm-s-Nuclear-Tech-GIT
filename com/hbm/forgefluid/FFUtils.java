package com.hbm.forgefluid;

import com.hbm.tileentity.machine.TileEntityDummy;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.IItemHandlerModifiable;

//Drillgon200: Let's hope this works without bugs in 1.12.2...
//Drillgon200: Still mad they removed the fluid container registry.
public class FFUtils {

	/**
	 * Replacement method for the old method of transferring fluids out of a machine
	 * 
	 * @param tileEntity - the tile entity it is filling from
	 * @param tank       - the fluid tank to fill from
	 * @param world      - the world the filling is taking place in
	 * @param i          - x coord of place to fill
	 * @param j          - y coord of place to fill
	 * @param k          - z coord of place to fill
	 * @param maxDrain   - the maximum amount that can be drained from the tank at a
	 *                   time
	 * @return Whether something was actually filled or not, or whether it needs an
	 *         update
	 */

	public static boolean fillFluid(TileEntity tileEntity, FluidTank tank, World world, BlockPos toFill, int maxDrain) {
		if (tank.getFluidAmount() <= 0 || tank.getFluid() == null) {
			return false;
		}
		TileEntity te = world.getTileEntity(toFill);

		if (te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
			if (te instanceof TileEntityDummy) {
				TileEntityDummy ted = (TileEntityDummy) te;
				if (world.getTileEntity(ted.target) == tileEntity) {
					return false;
				}
			}
			IFluidHandler tef = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
			if (tef.fill(new FluidStack(tank.getFluid(), Math.min(maxDrain, tank.getFluidAmount())), false) > 0) {
				tank.drain(tef.fill(new FluidStack(tank.getFluid(), Math.min(maxDrain, tank.getFluidAmount())), true),
						true);
				return true;
			}
		}
		return false;
	}

	/**
	 * Fills a fluid handling item from a tank
	 * 
	 * @param slots - the slot inventory
	 * @param tank  - the tank to fill from
	 * @param slot1 - the slot with an empty container
	 * @param slot2 - the output slot.
	 * @return true if something was actually filled
	 */
	public static boolean fillFromFluidContainer(IItemHandlerModifiable slots, FluidTank tank, int slot1, int slot2) {

		if (slots == null || tank == null || slots.getSlots() < slot1 || slots.getSlots() < slot2
				|| slots.getStackInSlot(slot1) == null || slots.getStackInSlot(slot1).isEmpty()) {
			return false;
		}
		if (FluidUtil.getFluidContained(slots.getStackInSlot(slot1)) == null) {
			moveItems(slots, slot1, slot2);
			return false;
		}
		if (slots.getStackInSlot(slot1).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
			boolean returnValue = false;
			IFluidHandlerItem ifhi = slots.getStackInSlot(slot1)
					.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			if (ifhi != null && (tank.getFluid() == null || FluidUtil.getFluidContained(slots.getStackInSlot(slot1))
					.getFluid() == tank.getFluid().getFluid())) {
				tank.fill(ifhi.drain(Math.min(6000, tank.getCapacity() - tank.getFluidAmount()), true), true);
				returnValue = true;
			}
			if (FluidUtil.getFluidContained(slots.getStackInSlot(slot1)) == null) {
				moveItems(slots, slot1, slot2);
			}
			return returnValue;
		}
		return false;
	}

	/**
	 * Fills a tank from a fluid handler item.
	 * 
	 * @param slots - the slot inventory
	 * @param tank  - the tank to be filled
	 * @param slot1 - the slot with the full container
	 * @param slot2 - the output slot
	 */
	public static boolean fillFluidContainer(IItemHandlerModifiable slots, FluidTank tank, int slot1, int slot2) {
		if (slots == null || tank == null || tank.getFluid() == null || slots.getSlots() < slot1
				|| slots.getSlots() < slot2 || slots.getStackInSlot(slot1) == null
				|| slots.getStackInSlot(slot1).isEmpty()) {
			return false;
		}
		if (slots.getStackInSlot(slot1).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
			boolean returnValue = false;
			IFluidHandlerItem ifhi = FluidUtil.getFluidHandler(slots.getStackInSlot(slot1));
			FluidStack stack = FluidUtil.getFluidContained(slots.getStackInSlot(slot1));
			if (stack != null && ifhi.fill(tank.getFluid(), false) <= 0) {
				moveItems(slots, slot1, slot2);
				return false;
			}
			if (stack.getFluid() == tank.getFluid().getFluid()) {
				tank.drain(ifhi.fill(new FluidStack(tank.getFluid(), Math.min(6000, tank.getFluidAmount())), true),
						true);
				returnValue = true;
			}
			stack = FluidUtil.getFluidContained(slots.getStackInSlot(slot1));
			if ((stack != null && tank.getFluid() == null) || stack.getFluid() != tank.getFluid().getFluid()
					|| ifhi.fill(tank.getFluid(), false) <= 0) {
				moveItems(slots, slot1, slot2);
			}
			return returnValue;
		}
		return false;
	}

	private static boolean moveItems(IItemHandlerModifiable slots, int in, int out) {
		if (slots.getStackInSlot(in) != null && !slots.getStackInSlot(in).isEmpty()) {

			if (slots.getStackInSlot(out) == null || slots.getStackInSlot(out).isEmpty()) {

				slots.setStackInSlot(out, slots.getStackInSlot(in));
				slots.setStackInSlot(in, ItemStack.EMPTY);
				return true;
			} else {
				int amountToTransfer = Math.min(
						slots.getStackInSlot(out).getMaxStackSize() - slots.getStackInSlot(out).getCount(),
						slots.getStackInSlot(in).getCount());
				slots.getStackInSlot(in).shrink(amountToTransfer);
				if (slots.getStackInSlot(in).getCount() <= 0)
					slots.setStackInSlot(in, ItemStack.EMPTY);

				slots.getStackInSlot(out).grow(amountToTransfer);
				return true;
			}
		}
		return false;
	}

	public static FluidTank changeTankSize(FluidTank fluidTank, int i) {
		FluidTank newTank = new FluidTank(i);
		if (fluidTank.getFluid() == null) {
			return newTank;
		} else {
			newTank.fill(fluidTank.getFluid(), true);
			return newTank;
		}
	}
}
