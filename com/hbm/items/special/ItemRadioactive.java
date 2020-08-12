package com.hbm.items.special;

import java.util.List;

import com.hbm.inventory.BreederRecipes;
import com.hbm.lib.Library;
import com.hbm.util.ContaminationUtil;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemRadioactive extends ItemCustomLore {

	float radiation;
	boolean fire;
	boolean blinding;
	
	public ItemRadioactive(float radiation, String s) {
		super(s);
		this.radiation = radiation;
	}

	public ItemRadioactive(float radiation, boolean fire, String s) {
		super(s);
		this.radiation = radiation;
		this.fire = fire;
	}

	public ItemRadioactive(float radiation, boolean fire, boolean blinding, String s) {
		super(s);
		this.radiation = radiation;
		this.fire = fire;
		this.blinding = blinding;
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		doRadiationDamage(entityIn, stack.getCount(), stack);
	}

	public void doRadiationDamage(Entity entity, float mod, ItemStack stack) {

		if (entity instanceof EntityLivingBase) {
			
			if(this.radiation > 0)
				ContaminationUtil.applyRadData(entity, this.radiation * mod / 20F);
			
			if(this.fire)
				entity.setFire(5);
			
			if(this.blinding)
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100, 0));
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn) {
		if(radiation > 0) {
			list.add(TextFormatting.GREEN + "[Radioactive]");
			list.add(TextFormatting.YELLOW + (radiation + "RAD/s"));
		}

		if(fire)
			list.add(TextFormatting.GOLD + "[Pyrophyric / Hot]");

		if(blinding)
			list.add(TextFormatting.DARK_AQUA + "[Blinding]");
		
		int[] breeder = BreederRecipes.getFuelValue(stack);

		if(breeder != null) {
			list.add(BreederRecipes.getHEATString("[Provides " + breeder[0] + " HEAT]", breeder[0]));
			list.add(TextFormatting.YELLOW + "Worth " + breeder[1] + " operations in breeding reactor");
			list.add(TextFormatting.YELLOW + "Worth " + (breeder[0] * breeder[1] * 5) + " operations in nuclear furnace");
		}
	}

}
