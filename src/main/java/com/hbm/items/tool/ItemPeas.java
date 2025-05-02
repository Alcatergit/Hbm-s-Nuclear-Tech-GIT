package com.hbm.items.tool;

import java.util.List;

import com.hbm.entity.mob.EntityQuackos;
import com.hbm.items.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemPeas extends Item {

	public ItemPeas(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (!player.capabilities.isCreativeMode) {
			player.getHeldItem(hand).shrink(1);
		}
		
		List<EntityQuackos> quacc = world.getEntitiesWithinAABB(EntityQuackos.class, player.getEntityBoundingBox().grow(50, 50, 50));
		
		for(EntityQuackos ducc : quacc) {
			ducc.despawn();
		}
		
		return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("He accepts your offering.");
	}
}
