package com.hbm.items.ohno;

import com.hbm.inventory.leafia.MissileCustomNukeContainer;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;

public class ItemMissileCustomNuke extends Item {
	public static ItemStack lastStackEw = null;
	public static int lastSlotEw = -1;
	public ItemMissileCustomNuke(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setMaxStackSize(1);
		this.setCreativeTab(MainRegistry.missileTab);
		ModItems.ALL_ITEMS.add(this);
	}
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		MissileCustomNukeContainer newContainer = new MissileCustomNukeContainer(player.inventory,stack);
		player.openContainer = newContainer;
		if(world.isRemote) {
			lastStackEw = stack;
			lastSlotEw = (hand == EnumHand.MAIN_HAND) ? player.inventory.currentItem : -1;
			player.openGui(MainRegistry.instance, ModItems.guiID_item_customnuke, world, 0, 0, 0);
		}
		return super.onItemRightClick(world, player, hand);
	}
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add("§d["+I18nUtil.resolveKey("desc.custommissile")+"]§r");
	}
}