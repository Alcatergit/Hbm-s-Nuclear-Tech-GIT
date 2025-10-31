package com.hbm.items.special;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemMissile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemLootCrate extends Item {

	public static List<ItemMissile> list10 = new ArrayList<ItemMissile>();
	public static List<ItemMissile> list15 = new ArrayList<ItemMissile>();
	public static List<ItemMissile> listMisc = new ArrayList<ItemMissile>();
	private static Random rand = new Random();
	
	public ItemLootCrate(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		playerIn.inventoryContainer.detectAndSendChanges();

		if(stack.getItem() == ModItems.loot_10)
			playerIn.inventory.addItemStackToInventory(new ItemStack(choose(list10)));
		if(stack.getItem() == ModItems.loot_15)
			playerIn.inventory.addItemStackToInventory(new ItemStack(choose(list15)));
		if(stack.getItem() == ModItems.loot_misc)
			playerIn.inventory.addItemStackToInventory(new ItemStack(choose(listMisc)));
		
		stack.shrink(1);
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
	
	private ItemMissile choose(List<ItemMissile> parts) {
		
		boolean flag = true;
		ItemMissile item = null;
		
		while(flag) {
			item = parts.get(rand.nextInt(parts.size()));
			
			switch(item.rarity) {
			case COMMON:
				flag = false;
				break;
			case UNCOMMON:
				if(rand.nextInt(5) == 0) flag = false;
				break;
			case RARE:
				if(rand.nextInt(10) == 0) flag = false;
				break;
			case EPIC:
				if(rand.nextInt(25) == 0) flag = false;
				break;
			case LEGENDARY:
				if(rand.nextInt(50) == 0) flag = false;
				break;
			case SEWS_CLOTHES_AND_SUCKS_HORSE_COCK:
				if(rand.nextInt(100) == 0) flag = false;
				break;
			
			}
		}
		
		return item;
	}
	
	
}
