package com.hbm.items.special;

import java.util.List;

import com.hbm.entity.siege.SiegeTier;
import com.hbm.items.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSiegeCoin extends Item {
	
	public ItemSiegeCoin(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.UNCOMMON;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items){
		if(tab == CreativeTabs.SEARCH || tab == this.getCreativeTab())
			for(int i = 0; i < SiegeTier.getLength(); i++) {
				items.add(new ItemStack(this, 1, i));
			}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn){
		tooltip.add(TextFormatting.YELLOW + "Tier " + (stack.getItemDamage() + 1));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}