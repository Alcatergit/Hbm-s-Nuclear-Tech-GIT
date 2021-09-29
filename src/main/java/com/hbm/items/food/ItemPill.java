package com.hbm.items.food;

import java.util.List;
import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemPill extends ItemFood {

	Random rand = new Random();
	
	public ItemPill(int hunger, String s) {
		super(hunger, false);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if (!worldIn.isRemote)
        {
        	if(this == ModItems.pill_iodine) {
        		player.removePotionEffect(MobEffects.BLINDNESS);
        		player.removePotionEffect(MobEffects.NAUSEA);
        		player.removePotionEffect(MobEffects.MINING_FATIGUE);
        		player.removePotionEffect(MobEffects.HUNGER);
        		player.removePotionEffect(MobEffects.SLOWNESS);
        		player.removePotionEffect(MobEffects.POISON);
        		player.removePotionEffect(MobEffects.WEAKNESS);
        		player.removePotionEffect(MobEffects.WITHER);
        		player.removePotionEffect(HbmPotion.radiation);
        	}

        	if(this == ModItems.plan_c) {
        		for(int i = 0; i < 10; i++)
        			player.attackEntityFrom(rand.nextBoolean() ? ModDamageSource.euthanizedSelf : ModDamageSource.euthanizedSelf2, 1000);
        	}

        	if(this == ModItems.radx) {
        		player.addPotionEffect(new PotionEffect(HbmPotion.radx, 3 * 60 * 20, 0));
        	}
        }
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(this == ModItems.pill_iodine) {
			tooltip.add("Removes negative effects");
		}
		if(this == ModItems.plan_c) {
			tooltip.add("Deadly");
		}
		if(this == ModItems.radx) {
			tooltip.add("Increases radiation resistance by 0.4 for 3 minutes");
		}
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 10;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.setActiveHand(handIn);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
