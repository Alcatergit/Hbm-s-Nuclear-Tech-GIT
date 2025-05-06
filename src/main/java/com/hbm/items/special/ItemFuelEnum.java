package com.hbm.items.special;

import java.util.*;

import com.hbm.inventory.ChemplantRecipes;
import com.hbm.items.ItemEnums.EnumCokeType;
import com.hbm.items.ItemEnums.EnumTarType;
import com.hbm.items.ModItems;
import com.hbm.interfaces.IHasCustomMetaModels;
import com.hbm.lib.RefStrings;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.NonNullList;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class ItemFuelEnum extends ItemFuel implements IHasCustomMetaModels {

	public ItemFuelEnum(String s, int burnTime) {
		super(s, burnTime);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (tab == this.getCreativeTab() || tab == CreativeTabs.SEARCH){
			if (this == ModItems.coke) {
				for (EnumCokeType type : EnumCokeType.values()) {
					list.add(new ItemStack(this, 1, type.ordinal()));
				}
			}
			if (this == ModItems.oil_tar) {
				for (EnumTarType type : EnumTarType.values()) {
					list.add(new ItemStack(this, 1, type.ordinal()));
				}
			}
		}
	}

	@Override
	public Set<Integer> getMetaValues(){
		Set<Integer> meta = new HashSet<Integer>();
		if (this == ModItems.coke) {
			for(EnumCokeType type : EnumCokeType.values())
				meta.add(type.ordinal());
		}
		if (this == ModItems.oil_tar) {
			for(EnumTarType type : EnumTarType.values())
				meta.add(type.ordinal());
		}
		return meta;
	}

	@Override
	public ModelResourceLocation getResourceLocation(int meta) {
		if(this == ModItems.coke) {
			EnumCokeType cokeType = EnumCokeType.values()[meta];
			return new ModelResourceLocation(RefStrings.MODID + ":coke_" + cokeType.toString().toLowerCase(), "inventory");
		}
		if(this == ModItems.oil_tar){
			EnumTarType tarType = EnumTarType.values()[meta];
			return new ModelResourceLocation(RefStrings.MODID + ":oil_tar_" + tarType.toString().toLowerCase(), "inventory");
		}
		return new ModelResourceLocation(RefStrings.MODID + ":tsar_core", "inventory");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		if(stack.getItem() == ModItems.coke) return (I18n.format(stack.getTranslationKey() + "_" + EnumCokeType.values()[stack.getItemDamage()].toString().toLowerCase() + ".name")).trim();
		if(stack.getItem() == ModItems.oil_tar) return (I18n.format(stack.getTranslationKey() + "_" + EnumTarType.values()[stack.getItemDamage()].toString().toLowerCase() + ".name")).trim();
		return "ERROR";
	}
}
