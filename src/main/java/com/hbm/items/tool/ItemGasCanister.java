package com.hbm.items.tool;

import java.util.List;

import com.hbm.forgefluid.HbmFluidHandlerGasCanister;
import com.hbm.forgefluid.HbmFluidHandlerItemStack;
import com.hbm.forgefluid.SpecialContainerFillLists.EnumGasCanister;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGasCanister extends Item implements IHasCustomModel {

	public static final ModelResourceLocation fluidCanisterModel = new ModelResourceLocation(RefStrings.MODID + ":gas_empty", "inventory");
	public int cap;
	
	
	public ItemGasCanister(String s, int cap){
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		this.setMaxStackSize(1);
		this.setMaxDamage(cap);
		this.cap = cap;
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	
	@Override
	public int getItemStackLimit(ItemStack stack){
		return isFullOrEmpty(stack) ? 64 : 1;
	}
	
	public static boolean isFullOrEmpty(ItemStack stack){
		if(stack.hasTagCompound() && stack.getItem() == ModItems.gas_canister){
			FluidStack f = FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(HbmFluidHandlerGasCanister.FLUID_NBT_KEY));
			if(f == null)
				return true;
			return f.amount == 4000 || f.amount == 0;
			
		} else if(stack.getItem() == ModItems.gas_canister){
			return true;
		}
		return false;
	}
	
	public static boolean isEmptyCanister(ItemStack out) {
		if(out.getItem() == ModItems.gas_canister && FluidUtil.getFluidContained(out) == null)
			return true;
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		FluidStack f = FluidUtil.getFluidContained(stack);
		if(f == null || f.getFluid() == null) {
			return I18n.format("item.gas_empty.name");
		} else {
			EnumGasCanister canister = EnumGasCanister.getEnumFromFluid(f.getFluid());
			if(canister == null)
				return I18n.format("item.gas_null.name");
			return I18n.format(canister.getTranslateKey());
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		FluidStack f = FluidUtil.getFluidContained(stack);
		tooltip.add((f == null ? "0" : f.amount) + "/" + cap + " mb");
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(tab == this.getCreativeTab() || tab == CreativeTabs.SEARCH){
			for(Fluid f : EnumGasCanister.getFluids()){
				ItemStack stack = new ItemStack(this, 1, 0);
				stack.setTagCompound(new NBTTagCompound());
				if(f != null)
					stack.getTagCompound().setTag(HbmFluidHandlerGasCanister.FLUID_NBT_KEY, new FluidStack(f, cap).writeToNBT(new NBTTagCompound()));
				items.add(stack);
			}
		}
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		return new HbmFluidHandlerGasCanister(stack, cap);
	}

	@Override
	public ModelResourceLocation getResourceLocation() {
		return fluidCanisterModel;
	}
	
	public static boolean isFullCanister(ItemStack stack, Fluid fluid){
		if(stack != null){
			if(stack.getItem() instanceof ItemGasCanister && FluidUtil.getFluidContained(stack) != null && FluidUtil.getFluidContained(stack).getFluid() == fluid && FluidUtil.getFluidContained(stack).amount == ((ItemGasCanister)stack.getItem()).cap)
				return true;
		}
		return false;
	}
	
	public static ItemStack getFullCanister(Fluid f){
		ItemStack stack = new ItemStack(ModItems.gas_canister, 1, 0);
		stack.setTagCompound(new NBTTagCompound());
		if(f != null && EnumGasCanister.contains(f))
			stack.getTagCompound().setTag(HbmFluidHandlerGasCanister.FLUID_NBT_KEY, new FluidStack(f, 4000).writeToNBT(new NBTTagCompound()));
		return stack;
	}
}