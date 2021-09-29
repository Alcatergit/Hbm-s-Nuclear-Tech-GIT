package com.hbm.items.special;

import java.util.List;

import com.hbm.config.WeaponConfig;
import com.hbm.entity.effect.EntityRagingVortex;
import com.hbm.lib.ModDamageSource;
import com.hbm.util.I18nUtil;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemDigamma extends ItemRadioactive {

	int digamma;
	
	public ItemDigamma(float radiation, int diagamma, String s) {
		super(radiation, s);
		this.digamma = diagamma;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entity, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entity, itemSlot, isSelected);
		if(entity instanceof EntityPlayer) {

    		EntityPlayer player = (EntityPlayer) entity;

    		if(player.ticksExisted % digamma == 0 && !player.capabilities.isCreativeMode) {

    			player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("digamma", -0.5D, 2));

    			if(player.getHealth() > player.getMaxHealth())
    				player.setHealth(player.getMaxHealth());

    			if(player.getMaxHealth() <= 0) {
    				player.attackEntityFrom(ModDamageSource.radiation, 100F);
    				player.onDeath(ModDamageSource.radiation);
    			}
    		}
    	}
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn) {
		list.add(TextFormatting.GOLD + I18nUtil.resolveKey("trait.hlParticle", "1.67*10³⁴a"));
		list.add(TextFormatting.RED + I18nUtil.resolveKey("trait.hlPlayer", (digamma / 20F) + "s"));

		list.add("");
		super.addInformation(stack, world, list, flagIn);

		float d = ((int)((1000F / 60) * 10)) / 10F;

		list.add(TextFormatting.RED + "[" + I18nUtil.resolveKey("trait.digamma") + "]");
		list.add(TextFormatting.DARK_RED + "" + d + "DRX/s");

		list.add(TextFormatting.RED + "[" + I18nUtil.resolveKey("trait.drop") + "]");
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		if (entityItem != null) {

			if (entityItem.onGround && WeaponConfig.dropSing) {

	        	EntityRagingVortex bl = new EntityRagingVortex(entityItem.world, 10F);
	        	bl.posX = entityItem.posX ;
	        	bl.posY = entityItem.posY ;
	        	bl.posZ = entityItem.posZ ;
	        	entityItem.world.spawnEntity(bl);

				return true;
			}
		}

		return false;
	}

}
