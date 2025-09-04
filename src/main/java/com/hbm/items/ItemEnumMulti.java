package com.hbm.items;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.hbm.interfaces.IHasCustomMetaModels;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.util.EnumUtil;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemEnumMulti extends Item implements IHasCustomMetaModels {
	
	//hell yes, now we're thinking with enums!
	protected Class<? extends Enum> theEnum;

	public ItemEnumMulti(Class<? extends Enum> theEnum, String s) {
		this.setHasSubtypes(true);
		this.theEnum = theEnum;

		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setMaxDamage(0);
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (tab == this.getCreativeTab() || tab == CreativeTabs.SEARCH){
			for (Enum e: theEnum.getEnumConstants()) {
				list.add(new ItemStack(this, 1, e.ordinal()));
			}
		}
	}

	@Override
	public Set<Integer> getMetaValues(){
		Set<Integer> meta = new HashSet<Integer>();
		for (Enum e: theEnum.getEnumConstants()) {
			meta.add(e.ordinal());
		}
		return meta;
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		Enum type = theEnum.getEnumConstants()[itemStack.getItemDamage()];
		if(type instanceof ItemEnums.IEnumFurnaceFuel fuel) return fuel.getBurnTime();
		return 0;
	}

	@Override
	public ModelResourceLocation getResourceLocation(int meta) {
		Enum type = theEnum.getEnumConstants()[meta];
		return new ModelResourceLocation(RefStrings.MODID + ":"+this.getTranslationKey().substring(5)+"_" + type.toString().toLowerCase(), "inventory");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		return (I18n.format(stack.getTranslationKey() + "_" + EnumUtil.grabEnumSafely(theEnum,stack.getItemDamage()).toString().toLowerCase() + ".name")).trim();
	}
}
