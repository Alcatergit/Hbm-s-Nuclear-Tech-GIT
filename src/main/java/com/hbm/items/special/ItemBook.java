package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemBook extends Item {

	public ItemBook(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("Edition 4, gold lined pages");
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if(!world.isRemote)
			player.openGui(MainRegistry.instance, ModItems.guiID_item_book, world, 0, 0, 0);

		return super.onItemRightClick(world, player, hand);
	}
}
