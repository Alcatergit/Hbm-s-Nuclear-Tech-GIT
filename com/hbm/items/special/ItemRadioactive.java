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
			//Strong
			if(this == ModItems.man_core ||
					this == ModItems.ingot_u235) {
				Library.applyRadData(living, 5F/20F * mod);
			}
			//Strong Nuggets
			if(this == ModItems.nugget_u235 ||
					this == ModItems.nugget_pu239) {
				Library.applyRadData(living, 3.5F/20F * mod);
			}
			//Medium
			if(this == ModItems.ingot_pu238 ||
					this == ModItems.trinitite ||
					this == ModItems.pellet_rtg) {
				Library.applyRadData(living, 2.5F/20F * mod);
			}
			//Meduim Nuggets
			if(this == ModItems.nugget_pu238 ||
					this == ModItems.nugget_neptunium) {
				Library.applyRadData(living, 1F/20F * mod);
			}
			//Weak
			if(this == ModItems.ingot_uranium){
				Library.applyRadData(living, 0.5F/20F * mod);
			}
			//Strong
			if(this == ModItems.nuclear_waste) {
				Library.applyRadData(living, 5F/20F * mod);
			}
			
			// Schrabidic
			if (this == ModItems.ingot_schrabidium) {

				if (living instanceof EntityPlayer && !Library.checkForHazmat((EntityPlayer) living))
					living.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 60 * 20, 0));
				Library.applyRadData(living, 7.5F/20F * mod);

			}
		}
	}

}
