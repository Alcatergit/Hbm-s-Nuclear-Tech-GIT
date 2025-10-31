package com.hbm.items.machine;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.hbm.items.ModItems;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.CrucibleRecipes;
import com.hbm.inventory.CrucibleRecipes.CrucibleRecipe;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.NonNullList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCrucibleTemplate extends Item {
	
	public static final ModelResourceLocation cruciModel = new ModelResourceLocation(RefStrings.MODID + ":crucible_template", "inventory");
	
	public ItemCrucibleTemplate(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		String s = ("" + I18nUtil.resolveKey(this.getTranslationKey() + ".name")).trim();
        String s1 = ("" + I18nUtil.resolveKey(CrucibleRecipes.getName(stack))).trim();

        if (s1 != null) {
            s = s + " " + s1;
        }

        return s;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if(tab == this.getCreativeTab() || tab == CreativeTabs.SEARCH){
			for(int i: CrucibleRecipes.recipes.keySet()) {
	            list.add(new ItemStack(this, 1, i));
	        }
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		
		CrucibleRecipe recipe = CrucibleRecipes.recipes.get(stack.getItemDamage());
		
		if(recipe == null) {
			return;
		}

		list.add("§l" + I18nUtil.resolveKey("info.template_out_p"));
		for(MaterialStack out : recipe.output) {
			list.add(" §a"+I18nUtil.resolveKey(out.material.getTranslationKey()) + ": " + Mats.formatAmount(out.amount, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)));
		}

		list.add("§l" + I18nUtil.resolveKey("info.template_in_p"));
		
		for(MaterialStack in : recipe.input) {
			list.add(" §c"+I18nUtil.resolveKey(in.material.getTranslationKey()) + ": " + Mats.formatAmount(in.amount, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)));
		}
	}
}
