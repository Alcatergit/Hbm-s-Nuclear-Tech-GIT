package com.hbm.items.gear;

import com.hbm.items.ModItems;

import com.hbm.util.ContaminationUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class ModShield extends ItemShield {

    public static String materialOd;

    public ModShield(String matOd, ToolMaterial material, String s){
        this(matOd, material.getMaxUses(), s);
    }
	public ModShield(String matOd, int dmg, String s){
		super();
        materialOd = matOd;
		this.setTranslationKey(s);
		this.setRegistryName(s);
        this.setMaxDamage(dmg);
        this.setCreativeTab(CreativeTabs.COMBAT);
		ModItems.ALL_ITEMS.add(this);
	}

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        int[] ids = OreDictionary.getOreIDs(repair);
        for(int id : ids){
            if(OreDictionary.getOreName(id).equals(materialOd)) return true;
        }
        return super.getIsRepairable(toRepair, repair);
    }

    public void handleImpact(Item item, Entity cause, float amount){
        if(cause == null) return;
        if(item == ModItems.cobalt_shield){
            if(cause instanceof EntityLivingBase attacker){
                attacker.addPotionEffect(new PotionEffect(MobEffects.HUNGER, (int) amount+20, 2));
            }
        } if(item == ModItems.starmetal_shield){
            if(cause instanceof EntityLivingBase attacker){
                attacker.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, (int) amount+20, 1));
            }
        } else if(item == ModItems.cmb_shield){
            if(cause instanceof EntityLivingBase attacker){
                attacker.addPotionEffect(new PotionEffect(MobEffects.POISON, (int) amount+20, 1));
            }
        } else if(item == ModItems.schrabidium_shield){
            if(cause instanceof EntityLivingBase attacker){
                ContaminationUtil.contaminate(attacker, ContaminationUtil.HazardType.RADIATION, ContaminationUtil.ContaminationType.CREATIVE, amount);
            }
            cause.setFire(2);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        Item item = stack.getItem();
        if(item == ModItems.cobalt_shield){
            tooltip.add("§eReflects Damage as Hunger Duration");
        } if(item == ModItems.starmetal_shield){
             tooltip.add("§eReflects Damage as Slowness Duration");
        } else if(item == ModItems.cmb_shield){
            tooltip.add("§eReflects Damage as Poison Duration");
        } else if(item == ModItems.schrabidium_shield){
            tooltip.add("§eReflects Damage as Radiation");
            tooltip.add("§eSets Attacker on Fire");
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public @NotNull String getItemStackDisplayName(ItemStack stack) {
        return I18n.translateToLocal(this.getTranslationKey(stack) + ".name").trim();
    }

    @Override
    public boolean isShield(ItemStack stack, @Nullable EntityLivingBase entity) {
        return !stack.isEmpty() && stack.getItem() instanceof ModShield;
    }
}
