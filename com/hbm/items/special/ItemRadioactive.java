package com.hbm.items.special;

import com.hbm.items.ModItems;
import com.hbm.lib.Library;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemRadioactive extends ItemCustomLore {

	public ItemRadioactive(String s) {
		super(s);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		doRadiationDamage(entityIn, stack.getCount());
	}

	public void doRadiationDamage(Entity entity, float mod) {

		if (entity instanceof EntityLivingBase) {
			EntityLivingBase living = (EntityLivingBase) entity;
			// Schrabidic
			if (this == ModItems.ingot_schrabidium) {

				if (living instanceof EntityPlayer && !Library.checkForHazmat((EntityPlayer) living))
					living.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 60 * 20, 0));
				Library.applyRadData(living, 7.5F/20F * mod);

			}
		}
	}

}
