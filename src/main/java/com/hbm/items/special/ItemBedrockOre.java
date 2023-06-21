package com.hbm.items.special;

import java.util.Map;
import java.util.List;

import com.hbm.inventory.BedrockOreRegistry;
import com.hbm.main.MainRegistry;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBedrockOre extends Item {

	public ItemBedrockOre(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setHasSubtypes(true);
		this.setCreativeTab(MainRegistry.controlTab);
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items){
		if(tab == CreativeTabs.SEARCH || tab == this.getCreativeTab()){
			for(Integer oreMeta : BedrockOreRegistry.oreIndexes.keySet()) {
				items.add(new ItemStack(this, 1, oreMeta));
			}
		}
	}

	public static ItemStack getOut(int oreMeta, int amount){
		ItemStack out = BedrockOreRegistry.getResource(BedrockOreRegistry.oreIndexes.get(oreMeta)).copy();
		out.setCount(amount);
		return out;
	}

	public static String getOreTag(ItemStack stack){
		return BedrockOreRegistry.oreIndexes.get(stack.getMetadata());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		return I18n.format(this.getUnlocalizedName() + ".name", BedrockOreRegistry.getOreName(getOreTag(stack)));
	}

	public static int getColor(ItemStack stack){
		return BedrockOreRegistry.getOreColor(getOreTag(stack));
	}

	public static int getOutType(int oreMeta){
		String oreResult = BedrockOreRegistry.oreResults.get(BedrockOreRegistry.oreIndexes.get(oreMeta));
		if(oreResult.startsWith("gem")) return 0;
		if(oreResult.startsWith("dust")) return 1;
		if(oreResult.startsWith("ingot")) return 2;
		return -1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn) {
		if(stack.getItem() == ModItems.ore_bedrock){
			String oreName = BedrockOreRegistry.oreIndexes.get(stack.getMetadata());
			int tier = BedrockOreRegistry.getOreTier(oreName);
			list.add("§6Tier: "+tier);
			FluidStack req = BedrockOreRegistry.getFluidRequirement(tier);
			list.add("§eRequired: " + req.amount + "mB " + req.getFluid().getLocalizedName(req));
		}
		super.addInformation(stack, world, list, flagIn);
	}
}