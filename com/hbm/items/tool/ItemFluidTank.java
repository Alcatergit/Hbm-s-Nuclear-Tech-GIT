package com.hbm.items.tool;

import java.util.List;
import java.util.Map.Entry;

import com.hbm.forgefluid.HbmFluidHandlerItemStack;
import com.hbm.interfaces.IHasCustomModel;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFluidTank extends Item implements IHasCustomModel {

	public static final ModelResourceLocation fluidTankModel = new ModelResourceLocation(
			RefStrings.MODID + ":fluid_tank_full", "inventory");
	
	public static final ModelResourceLocation fluidBarrelModel = new ModelResourceLocation(
			RefStrings.MODID + ":fluid_barrel_full", "inventory");

	private int cap;
	private Fluid fluidType;

	public ItemFluidTank(String s, int cap, Fluid f) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		this.setHasSubtypes(true);
		this.setMaxDamage(cap);
		this.setMaxStackSize(1);
		this.cap = cap;
		this.fluidType = f;

		ModItems.ALL_ITEMS.add(this);
	}

	public ItemFluidTank(String s, int cap) {
		this(s, cap, null);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab == this.getCreativeTab() || tab == CreativeTabs.SEARCH) {
			ItemStack empty = new ItemStack(this, 1, 0);
			empty.setTagCompound(new NBTTagCompound());
			items.add(empty);
			for (Entry<String, Fluid> entry : FluidRegistry.getRegisteredFluids().entrySet()) {
				ItemStack stack = new ItemStack(this, 1, 0);
				stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setTag(HbmFluidHandlerItemStack.FLUID_NBT_KEY,
						new FluidStack(entry.getValue(), cap).writeToNBT(new NBTTagCompound()));
				items.add(stack);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		String s = ("" + I18n.format(this.getUnlocalizedName() + ".name")).trim();
		String s1 = null;// ("" +
							// StatCollector.translateToLocal(FluidType.getEnum(stack.getItemDamage()).getUnlocalizedName()))
		// .trim();
		if (FluidUtil.getFluidContained(stack) != null) {
			s1 = ("" + I18n.format(FluidUtil.getFluidContained(stack).getLocalizedName()).trim());
		} else {
			if(this == ModItems.fluid_tank_full)
				return "Empty Universal Fluid Tank";
			else if(this == ModItems.fluid_barrel_full)
				return "Empty Fluid Barrel";
		}

		if (s1 != null) {
			s = s + " " + s1;
		}

		return s;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new HbmFluidHandlerItemStack(stack, cap);
	}

	@Override
	public ModelResourceLocation getResourceLocation() {
		if(this == ModItems.fluid_tank_full)
			return fluidTankModel;
		else if(this == ModItems.fluid_barrel_full)
			return fluidBarrelModel;
		return null;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		
		FluidStack f = FluidUtil.getFluidContained(stack);
		list.add((f == null ? "0" : f.amount) + "/" + cap + " mb");

	}
	
	public static ItemStack getFullBarrel(Fluid f){
		ItemStack stack = new ItemStack(ModItems.fluid_barrel_full, 1, 0);
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setTag(HbmFluidHandlerItemStack.FLUID_NBT_KEY,new FluidStack(f, 64000).writeToNBT(new NBTTagCompound()));
		return stack;
	}


}
