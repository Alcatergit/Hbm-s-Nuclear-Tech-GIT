package com.hbm.items.tool;

import java.util.List;

import com.hbm.forgefluid.HbmFluidHandlerCanister;
import com.hbm.forgefluid.SpecialContainerFillLists.EnumCanister;
import com.hbm.interfaces.IHasCustomModel;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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

public class ItemFluidCanister extends Item implements IHasCustomModel {

	public static final ModelResourceLocation fluidCanisterModel = new ModelResourceLocation(RefStrings.MODID + ":canister_empty", "inventory");
	private int cap;
	
	
	public ItemFluidCanister(String s, int cap){
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		this.setMaxStackSize(1);
		this.setMaxDamage(cap);
		this.cap = cap;
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if(FluidUtil.getFluidContained(stack) == null)
			return "Empty Canister";
		return super.getItemStackDisplayName(stack);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (this == ModItems.canister_generic) {
			tooltip.add("All hail the spout!");
		}
		FluidStack f = FluidUtil.getFluidContained(stack);
		tooltip.add((f == null ? "0" : f.amount) + "/" + cap + " mb");
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(tab == this.getCreativeTab() || tab == CreativeTabs.SEARCH){
			for(Fluid f : EnumCanister.getFluids()){
				ItemStack stack = new ItemStack(this, 1, 0);
				stack.setTagCompound(new NBTTagCompound());
				if(f != null)
					stack.getTagCompound().setTag(HbmFluidHandlerCanister.FLUID_NBT_KEY, new FluidStack(f, cap).writeToNBT(new NBTTagCompound()));
				items.add(stack);
			}
		}
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new HbmFluidHandlerCanister(stack, cap);
	}


	@Override
	public ModelResourceLocation getResourceLocation() {
		return fluidCanisterModel;
	}
}
