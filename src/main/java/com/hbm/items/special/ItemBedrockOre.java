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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.nbt.NBTTagCompound;
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
		return I18n.format(this.getUnlocalizedName() + ".name", getOreTag(stack).substring(3).replaceAll("([A-Z])", " $1").trim());
	}

	public static int getColor(ItemStack stack){
		Integer i = BedrockOreRegistry.oreColors.get(getOreTag(stack));
		if(i != 0) return i;
		return 0;
	}

	public static int getOutType(int oreMeta){
		String oreResult = BedrockOreRegistry.oreResults.get(BedrockOreRegistry.oreIndexes.get(oreMeta));
		if(oreResult.startsWith("gem")) return 0;
		if(oreResult.startsWith("dust")) return 1;
		if(oreResult.startsWith("ingot")) return 2;
		return -1;
	}
}