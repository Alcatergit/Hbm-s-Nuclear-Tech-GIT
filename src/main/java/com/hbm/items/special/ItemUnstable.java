package com.hbm.items.special;

import java.util.List;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.ModDamageSource;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemUnstable extends Item {

	public ItemUnstable(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setHasSubtypes(true);

		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		switch(stack.getItemDamage()) {
		case 1:
			return "ELEMENTS";
		case 2:
			return "ARSENIC";
		case 3:
			return "VAULT";
		default:
			return ("" + I18n.format(this.getTranslationKey() + ".name")).trim();
		}
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return !oldStack.isItemEqual(newStack);
	}
}
