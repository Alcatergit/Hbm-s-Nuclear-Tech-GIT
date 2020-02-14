package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemStarterKit extends Item {

	public ItemStarterKit(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.maxStackSize = 1;
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		
		if(this == ModItems.grenade_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_generic, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_strong, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_frag, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_fire, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_shrapnel, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_cluster, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_flare, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_electric, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_poison, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_gas, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_cloud, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_pink_cloud, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_smart, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_mirv, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_breach, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_burst, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_pulse, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_plasma, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_tau, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_schrabidium, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_lemon, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_gascan, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_mk2, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_aschrab, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_nuke, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_nuclear, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_zomg, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_black_hole, 16));
		}
		
		world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.itemUnpack, SoundCategory.PLAYERS, 1.0F, 1.0F);
		stack.shrink(1);;
		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(this == ModItems.grenade_kit){
			tooltip.add("Please empty inventory before opening!");
		}
	}
}
