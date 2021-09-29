package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemDemonCore extends ItemHazard {

	public ItemDemonCore(String s){
		super(s);
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem){
		if(entityItem != null && !entityItem.world.isRemote && entityItem.onGround) {
			entityItem.setItem(new ItemStack(ModItems.demon_core_closed));
			entityItem.world.spawnEntity(new EntityItem(entityItem.world, entityItem.posX, entityItem.posY, entityItem.posZ, new ItemStack(ModItems.screwdriver)));
			return true;
		}
		return false;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn){
		super.addInformation(stack, world, list, flagIn);
		list.add(TextFormatting.RED + "[" + I18nUtil.resolveKey("trait.drop") + "]");
	}

}
