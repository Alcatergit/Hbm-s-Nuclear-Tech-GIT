package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.potion.HbmPotion;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemBook extends Item {

	public ItemBook(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(this == ModItems.book_of_)
		{
			tooltip.add("Edition 4, gold lined pages");
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if(world.isRemote)
			return super.onItemRightClick(world, player, hand);
		
		if(!player.isSneaking()) {
			List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(player.posX - 10, player.posY - 2, player.posZ - 10, player.posX + 10, player.posY + 2, player.posZ + 10));
			
			for(Object o : list) {
				
				if(o instanceof EntityLivingBase) {
					EntityLivingBase entity = (EntityLivingBase)o;
					
					entity.addPotionEffect(new PotionEffect(HbmPotion.telekinesis, 20, 0));
				}
			}
		} else {
			if(player.inventory.hasItemStack(new ItemStack(ModItems.ingot_u238m2, 1, 1)) &&
					player.inventory.hasItemStack(new ItemStack(ModItems.ingot_u238m2, 1, 2)) &&
					player.inventory.hasItemStack(new ItemStack(ModItems.ingot_u238m2, 1, 3))) {
				
				player.inventory.clearMatchingItems(ModItems.ingot_u238m2, 1, 0, null);
				player.inventory.clearMatchingItems(ModItems.ingot_u238m2, 2, 0, null);
				player.inventory.clearMatchingItems(ModItems.ingot_u238m2, 3, 0, null);
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_u238m2));
				player.inventoryContainer.detectAndSendChanges();
				
			}
		}
		
		return super.onItemRightClick(world, player, hand);
	}
}
