package com.hbm.items.special;

import java.util.LinkedHashSet;
import java.util.Set;

import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.interfaces.IHasCustomMetaModels;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.NonNullList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAutogen extends Item implements IHasCustomMetaModels {

	public Set<Integer> metaValues = new LinkedHashSet();

	MaterialShapes shape;
	
	public ItemAutogen(String s, MaterialShapes shape) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setHasSubtypes(true);
		this.shape = shape;
		ModItems.ALL_ITEMS.add(this);

		for(NTMMaterial mat : Mats.orderedList) {
			if(isMaterialValid(mat)) {
				metaValues.add(mat.id);
			}
		}
	}

	public boolean isMaterialValid(NTMMaterial mat){
		return mat.shapes.contains(this.shape);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list){
		if(tab == CreativeTabs.SEARCH || tab == this.getCreativeTab()){
			for(Integer i : metaValues) {
				list.add(new ItemStack(this, 1, i));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		
		NTMMaterial mat = Mats.matById.get(stack.getItemDamage());
		
		if(mat == null) {
			return "UNDEFINED";
		}
		
		return I18n.format(this.getTranslationKey() + ".name", I18n.format(mat.getTranslationKey()));
	}

	@Override
	public Set<Integer> getMetaValues(){
		return metaValues;
	}

	@Override
	public ModelResourceLocation getResourceLocation(int meta) {
		NTMMaterial mat = Mats.matById.get(meta);
		return new ModelResourceLocation(RefStrings.MODID + ":"+this.getTranslationKey().substring(5)+"_"+mat.getTranslationKey().substring(7), "inventory");
	}
}
