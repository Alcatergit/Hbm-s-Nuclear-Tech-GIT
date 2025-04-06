package com.hbm.hazard.helper;

import com.hbm.config.GeneralConfig;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class HazardHelper {
    static public boolean isHoldingReacher(final EntityLivingBase target) {

        if (target instanceof EntityPlayer && !GeneralConfig.enable528) {
            return Library.checkForHeld((EntityPlayer) target, ModItems.reacher);
        } else return false;

    }

    static public void applyPotionEffect(final EntityLivingBase target, final Potion potion, final int duration, final int amplifier) {
        target.addPotionEffect(new PotionEffect(potion, duration, amplifier));
    }
}
