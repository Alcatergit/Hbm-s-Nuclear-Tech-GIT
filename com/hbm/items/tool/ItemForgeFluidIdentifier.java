package com.hbm.items.tool;

import java.util.List;
import java.util.Map.Entry;

import com.hbm.interfaces.IHasCustomModel;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.conductor.TileEntityFFFluidDuct;
import com.hbm.tileentity.conductor.TileEntityFFFluidDuctMk2;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ItemForgeFluidIdentifier extends Item implements IHasCustomModel {

	public static final ModelResourceLocation identifierModel = new ModelResourceLocation(RefStrings.MODID + ":forge_fluid_identifier", "inventory");

	public ItemForgeFluidIdentifier(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.partsTab);

		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		return itemStack.copy();
	}

	@Override
	public boolean hasContainerItem() {
		return true;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab == this.getCreativeTab() || tab == CreativeTabs.SEARCH) {
			for (Entry<String, Fluid> set : FluidRegistry.getRegisteredFluids().entrySet()) {
				ItemStack stack = new ItemStack(this, 1, 0);
				NBTTagCompound tag = new NBTTagCompound();
				tag.setString("fluidtype", set.getKey());
				stack.setTagCompound(tag);
				items.add(stack);
			}
		}
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		if (!(stack.getItem() instanceof ItemForgeFluidIdentifier))
			return;
		Fluid f = null;
		if (stack.hasTagCompound()) {
			f = FluidRegistry.getFluid(stack.getTagCompound().getString("fluidtype"));
		}
		list.add("[CREATED USING TEMPLATE FOLDER]");
		list.add("");
		list.add("Universal fluid identifier for:");
		if (f != null)
			list.add("   " + f.getLocalizedName(new FluidStack(f, 1000)));
		else
			list.add("   " + "ERROR - bad data");
	}

	public static Fluid getType(ItemStack stack) {
		if (stack != null && stack.getItem() instanceof ItemForgeFluidIdentifier && stack.hasTagCompound())
			return FluidRegistry.getFluid(stack.getTagCompound().getString("fluidtype"));
		else
			return null;
	}
	
	public static ItemStack getStackFromFluid(Fluid f){
		ItemStack stack = new ItemStack(ModItems.forge_fluid_identifier, 1, 0);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("fluidtype", f.getName());
		stack.setTagCompound(tag);
		return stack;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te != null && te instanceof TileEntityFFFluidDuct) {
			TileEntityFFFluidDuct duct = (TileEntityFFFluidDuct) te;
			duct.setType(getType(player.getHeldItem(hand)));
		}
		if (te != null && te instanceof TileEntityFFFluidDuctMk2) {
			TileEntityFFFluidDuctMk2 duct = (TileEntityFFFluidDuctMk2) te;
			duct.setType(getType(player.getHeldItem(hand)));
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public ModelResourceLocation getResourceLocation() {
		return identifierModel;
	}
}
