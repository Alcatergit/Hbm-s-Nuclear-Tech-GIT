package com.hbm.items.machine;

import com.hbm.items.ModItems;
import com.hbm.inventory.ChemplantRecipes;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemChemistryIcon extends Item {

	public ItemChemistryIcon(String s){
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(null);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		String s = ("" + I18n.format(ModItems.chemistry_template.getUnlocalizedName() + ".name")).trim();
        String s1 = ("" + I18n.format("chem." + ChemplantRecipes.getName(stack))).trim();

        if (s1 != null)
        {
            s = s + " " + s1;
        }

        return s;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if(tab == this.getCreativeTab()){
		for (int i: ChemplantRecipes.recipeNames.keySet()){
				list.add(new ItemStack(this, 1, i));
        	}
		}
	}
}
