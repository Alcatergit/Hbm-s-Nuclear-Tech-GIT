package com.hbm.items.weapon;

import com.hbm.animloader.AnimationWrapper.EndResult;
import com.hbm.animloader.AnimationWrapper.EndType;
import com.hbm.handler.GunConfiguration;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.anim.HbmAnimations.BlenderAnimation;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemGunJShotty extends ItemGunBase {

	public ItemGunJShotty(GunConfiguration config, String s) {
		super(config, s);
	}

	@Override
	public void startAnim(EntityPlayer player, ItemStack stack, int slot, AnimType type) {
		switch(type){
		case RELOAD:
			int bullets = getMag(stack);
			if(bullets == 1){
				HbmAnimations.hotbar[slot] = new BlenderAnimation(stack.getItem().getUnlocalizedName(), 1, System.currentTimeMillis(), ResourceManager.jshotgun_anim0, new EndResult(EndType.END));
			} else if(bullets == 0){
				HbmAnimations.hotbar[slot] = new BlenderAnimation(stack.getItem().getUnlocalizedName(), 1, System.currentTimeMillis(), ResourceManager.jshotgun_anim1, new EndResult(EndType.END));
			}
		default:
			super.startAnim(player, stack, slot, type);
		}
	}
	
}
