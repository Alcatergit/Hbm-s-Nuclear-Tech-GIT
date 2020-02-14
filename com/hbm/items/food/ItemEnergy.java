package com.hbm.items.food;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemEnergy extends Item {

	public ItemEnergy(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.consumableTab);
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entity) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.capabilities.isCreativeMode) {
				stack.shrink(1);
			}
			if(this == ModItems.bottle_cherry)
        	{
        		player.heal(6F);
                player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 30 * 20, 0));
                player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 30 * 20, 2));
                Library.applyRadDirect(player, 5.0F);
        	}
			if (this == ModItems.bottle_nuka) {
				player.heal(4F);
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 30 * 20, 1));
				player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 30 * 20, 1));
				Library.applyRadDirect(player, 5.0F);
			}
			if ( this == ModItems.bottle_cherry ||  this == ModItems.bottle_nuka) {
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.cap_nuka));
				if (stack.isEmpty()) {
					return new ItemStack(ModItems.bottle_empty);
				}

				player.inventory.addItemStackToInventory(new ItemStack(ModItems.bottle_empty));
			}
		}
		return stack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
		if(!player.inventory.hasItemStack(new ItemStack(ModItems.bottle_opener)))
			return ActionResult.<ItemStack>newResult(EnumActionResult.PASS, player.getHeldItem(hand));
		player.setActiveHand(hand);
		return ActionResult.<ItemStack>newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		if (this == ModItems.bottle_nuka) {
			list.add("Contains about 210 kcal and 1500 mSv.");
		}
		if(this == ModItems.bottle_cherry)
    	{
            list.add("Now with severe radiation poisoning in every seventh bottle!");
    	}
	}
}
