package com.hbm.forgefluid;

import api.hbm.fluid.IFillableItem;
import com.hbm.inventory.fluid.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class HbmFluidHandlerItemStack implements IFillableItem, ICapabilityProvider {

	public static final String FLUID_NBT_KEY = "HbmFluidKey";

	private ItemStack container;
	private int cap;

	public HbmFluidHandlerItemStack(ItemStack stack, int cap) {
		container = stack;
		this.cap = cap;
	}

	private FluidStack getFluid() {
		if (!container.hasTagCompound()) {
			container.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound tag = container.getTagCompound();
		if (!tag.hasKey(FLUID_NBT_KEY)) {
			return null;
		}
		return new FluidStack(tag.getInteger("Fill"), Fluids.fromID(tag.getInteger("FluidID")));
	}

	private void setFluid(FluidStack fluid) {
		if (!container.hasTagCompound()) {
			container.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound tag = container.getTagCompound();
		if (fluid == null) {
			container.setItemDamage(0);
			tag.removeTag(FLUID_NBT_KEY);
			return;
		}
		container.setItemDamage(cap - fluid.fill);
		tag.setInteger("FluidID", fluid.type.getID());
		tag.setInteger("Fill", fluid.fill);
	}

	@Override
	public boolean acceptsFluid(FluidType type, ItemStack stack) {
		FluidStack contained = getFluid();
		return contained == null || contained.type == type;
	}

	@Override
	public int tryFill(FluidType type, int amount, ItemStack stack) {
		if (stack.getCount() > 1) return amount;
		FluidStack contained = getFluid();
		int filled;
		if (contained == null) {
			filled = Math.min(cap, amount);
			setFluid(new FluidStack(type, filled));
			return amount - filled;
		}

		if (contained.type != type) return amount;

		filled = Math.min(cap - contained.fill, amount);
		setFluid(new FluidStack(type, filled + contained.fill));
		return amount - filled;
	}

	@Override
	public boolean providesFluid(FluidType type, ItemStack stack) {
		FluidStack contained = getFluid();
		return contained != null && contained.type == type;
	}

	@Override
	public int tryEmpty(FluidType type, int amount, ItemStack stack) {
		if (stack.getCount() > 1) return 0;
		FluidStack contained = getFluid();
		if (contained == null || contained.type != type) return 0;
		int drained = Math.min(contained.fill, amount);
		setFluid(drained >= contained.fill ? null : new FluidStack(type, contained.fill - drained));
		return drained;
	}

	@Override
	public FluidType getFirstFluidType(ItemStack stack) {
		FluidStack contained = getFluid();
		return contained != null ? contained.type : null;
	}

	@Override
	public int getFill(ItemStack stack) {
		FluidStack contained = getFluid();
		return contained != null ? contained.fill : 0;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (container.getCount() > 1) return false;
		return capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (container.getCount() > 1) return null;
		return capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY ? (T) this : null;
	}

	public ItemStack getContainer() {
		return container;
	}
}
