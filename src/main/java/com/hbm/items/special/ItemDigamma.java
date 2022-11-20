package com.hbm.items.special;

import java.util.List;

import com.hbm.config.WeaponConfig;
import com.hbm.entity.effect.EntityQuasar;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.I18nUtil;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemDigamma extends ItemHazard {

	int digamma;
	
	public ItemDigamma(float radiation, int diagamma, String s) {
		super(radiation, s);
		//obacht! the particle's digamma value is "ticks until half life" while the superclass' interpretation is "simply add flat value"
		this.digamma = diagamma;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entity, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entity, itemSlot, isSelected);
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			ContaminationUtil.applyDigammaData(player, 1F / ((float) digamma));
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn) {
		list.add(TextFormatting.GOLD + I18nUtil.resolveKey("trait.hlParticle", "1.67*10³⁴ a"));
		list.add(TextFormatting.RED + I18nUtil.resolveKey("trait.hlPlayer", (digamma / 20F) + "s"));

		list.add("");
		super.addInformation(stack, world, list, flagIn);

		float d = ((int) ((1000F / digamma) * 200F)) / 10F;

		list.add(TextFormatting.RED + "[" + I18nUtil.resolveKey("trait.digamma") + "]");
		list.add(TextFormatting.DARK_RED + "" + d + "mDRX/s");

		list.add(TextFormatting.RED + "[" + I18nUtil.resolveKey("trait.drop") + "]");
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		if(entityItem != null) {

			if(entityItem.onGround && !entityItem.world.isRemote) {

				if(WeaponConfig.dropSing) {
					EntityQuasar bl = new EntityQuasar(entityItem.world, 5F);
					bl.posX = entityItem.posX;
					bl.posY = entityItem.posY;
					bl.posZ = entityItem.posZ;
					entityItem.world.spawnEntity(bl);
				}

				entityItem.setDead();

				return true;
			}
		}

		return false;
	}

}
