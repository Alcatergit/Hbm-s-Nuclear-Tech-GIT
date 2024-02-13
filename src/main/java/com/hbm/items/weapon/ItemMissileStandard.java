package com.hbm.items.weapon;

import java.util.HashMap;
import java.util.List;

import com.hbm.config.BombConfig;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemMissileStandard extends Item {
	
	public ItemMissileStandard(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setMaxStackSize(1);
		this.setCreativeTab(MainRegistry.missileTab);
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		//HE
		if(this == ModItems.missile_generic) {
			list.add("§7["+I18nUtil.resolveKey("desc.hemissile", "I")+"]§r");
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", 15));
		}
		if(this == ModItems.missile_strong) {
			list.add("§7["+I18nUtil.resolveKey("desc.hemissile", "II")+"]§r");
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", 25));
		}
		if(this == ModItems.missile_burst) {
			list.add("§7["+I18nUtil.resolveKey("desc.hemissile", "III")+"]§r");
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", 50));
		}

		//IC
		if(this == ModItems.missile_incendiary) {
			list.add("§6["+I18nUtil.resolveKey("desc.icmissile", "I")+"]§r");
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", 10));
		}
		if(this == ModItems.missile_incendiary_strong) {
			list.add("§6["+I18nUtil.resolveKey("desc.icmissile", "II")+"]§r");
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", 20));
		}
		if(this == ModItems.missile_inferno) {
			list.add("§6["+I18nUtil.resolveKey("desc.icmissile", "III")+"]§r");
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", 40));
		}
		
		//Cluster
		if(this == ModItems.missile_cluster) {
			list.add("§1["+I18nUtil.resolveKey("desc.clmissile", "I")+"]§r");
			list.add(TextFormatting.BLUE + " "+ I18nUtil.resolveKey("desc.radius", 5));
			list.add(" "+TextFormatting.BLUE+I18nUtil.resolveKey("desc.count", 25));
		}
		if(this == ModItems.missile_cluster_strong) {
			list.add("§1["+I18nUtil.resolveKey("desc.clmissile", "II")+"]§r");
			list.add(TextFormatting.BLUE + " "+ I18nUtil.resolveKey("desc.radius", 7));
			list.add(" "+TextFormatting.BLUE+I18nUtil.resolveKey("desc.count", 50));
		}
		if(this == ModItems.missile_rain) {
			list.add("§1["+I18nUtil.resolveKey("desc.clmissile", "III")+"]§r");
			list.add(TextFormatting.BLUE + " "+ I18nUtil.resolveKey("desc.radius", 10));
			list.add(" "+TextFormatting.BLUE+I18nUtil.resolveKey("desc.count", 100));
		}

		//Bunker
		if(this == ModItems.missile_buster) {
			list.add("§8["+I18nUtil.resolveKey("desc.bumissile", "I")+"]§r");
			list.add(TextFormatting.GRAY + " "+ I18nUtil.resolveKey("desc.radius", 15));
			list.add(" "+TextFormatting.GRAY+I18nUtil.resolveKey("desc.depth", 15));
		}
		if(this == ModItems.missile_buster_strong) {
			list.add("§8["+I18nUtil.resolveKey("desc.bumissile", "II")+"]§r");
			list.add(TextFormatting.GRAY + " "+ I18nUtil.resolveKey("desc.radius", 20));
			list.add(" "+TextFormatting.GRAY+I18nUtil.resolveKey("desc.depth", 20));
		}
		if(this == ModItems.missile_drill) {
			list.add("§8["+I18nUtil.resolveKey("desc.bumissile", "III")+"]§r");
			list.add(TextFormatting.GRAY + " "+ I18nUtil.resolveKey("desc.radius", 30));
			list.add(" "+TextFormatting.GRAY+I18nUtil.resolveKey("desc.depth", 30));
		}

		if(this == ModItems.missile_n2)	{
			list.add("§c["+I18nUtil.resolveKey("desc.n2missile")+"]§r");
			list.add(" "+TextFormatting.YELLOW+I18nUtil.resolveKey("desc.maxradius", (int)(BombConfig.n2Radius/12) * 5));
		}
		if(this == ModItems.missile_nuclear){
			list.add("§2["+I18nUtil.resolveKey("desc.numissile")+"]§r");
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", BombConfig.missileRadius));
			if(!BombConfig.disableNuclear){
				list.add(TextFormatting.DARK_GREEN + "["+I18nUtil.resolveKey("trait.fallout")+"]");
				list.add(" " + TextFormatting.GREEN +I18nUtil.resolveKey("desc.radius",(int)BombConfig.missileRadius*(1+BombConfig.falloutRange/100)));
			}
		}
		if(this == ModItems.missile_nuclear_cluster){
			list.add("§6["+I18nUtil.resolveKey("desc.tumissile")+"]§r");
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", BombConfig.missileRadius*2));
			if(!BombConfig.disableNuclear){
				list.add(TextFormatting.DARK_GREEN + "["+I18nUtil.resolveKey("trait.fallout")+"]");
				list.add(" " + TextFormatting.GREEN +I18nUtil.resolveKey("desc.radius",(int)BombConfig.missileRadius*2*(1+BombConfig.falloutRange/100)));
			}
		}
		if(this == ModItems.missile_volcano){
			list.add("§4["+I18nUtil.resolveKey("desc.tecmissile")+"]§r");
		}
		if(this == ModItems.missile_emp_strong){
			list.add("§3["+I18nUtil.resolveKey("desc.empmissile")+"]§r");
			list.add(TextFormatting.AQUA + " "+ I18nUtil.resolveKey("desc.radius", 100));
		}
		if(this == ModItems.missile_emp){
			list.add("§3["+I18nUtil.resolveKey("desc.empmissile")+"]§r");
			list.add(TextFormatting.AQUA + " "+ I18nUtil.resolveKey("desc.radius", 50));
		}
		if(this == ModItems.missile_micro){
			list.add("§2["+I18nUtil.resolveKey("desc.numicromissile")+"]§r");
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", BombConfig.fatmanRadius));
			if(!BombConfig.disableNuclear){
				list.add(TextFormatting.DARK_GREEN + "["+I18nUtil.resolveKey("trait.fallout")+"]");
				list.add(" " + TextFormatting.GREEN + I18nUtil.resolveKey("desc.radius", (int)BombConfig.fatmanRadius*(1+BombConfig.falloutRange/100)));
			}
		}
		if(this == ModItems.missile_endo){
			list.add("§3["+I18nUtil.resolveKey("desc.thermalmissile")+"]§r");
			list.add(TextFormatting.AQUA + " "+ I18nUtil.resolveKey("desc.radius", 30));
		}
		if(this == ModItems.missile_exo){
			list.add("§4["+I18nUtil.resolveKey("desc.thermalmissile")+"]§r");
			list.add(TextFormatting.GOLD + " "+ I18nUtil.resolveKey("desc.radius", 30));
		}
		if(this == ModItems.missile_doomsday){
			list.add("§5["+I18nUtil.resolveKey("desc.cmmmmissile")+"]§r");
		}
		if(this == ModItems.missile_taint){
			list.add("§d["+I18nUtil.resolveKey("desc.esmissile")+"]§r");
		}
		if(this == ModItems.missile_bhole){
			list.add("§0["+I18nUtil.resolveKey("desc.sinmissile")+"]§r");
		}
		if(this == ModItems.missile_schrabidium){
			list.add("§b["+I18nUtil.resolveKey("desc.schrabmissile")+"]§r");
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", BombConfig.aSchrabRadius));
		}
		if(this == ModItems.missile_anti_ballistic){
			list.add("§2["+I18nUtil.resolveKey("desc.abmissile")+"]§r");
			list.add(TextFormatting.GREEN+" "+I18nUtil.resolveKey("desc.abmissile1"));
		}
	}
}