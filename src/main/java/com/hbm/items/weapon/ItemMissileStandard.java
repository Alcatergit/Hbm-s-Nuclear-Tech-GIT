package com.hbm.items.weapon;

import java.util.HashMap;
import java.util.List;

import com.hbm.config.BombConfig;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

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
			list.add("§7[HE Missile I]§r");
			list.add(" §eRadius: 15m§r");
		}
		if(this == ModItems.missile_strong) {
			list.add("§7[HE Missile II]§r");
			list.add(" §eRadius: 25m§r");
		}
		if(this == ModItems.missile_burst) {
			list.add("§7[HE Missile III]§r");
			list.add(" §eRadius: 50m§r");
		}

		//IC
		if(this == ModItems.missile_incendiary) {
			list.add("§6[IC Missile I]§r");
			list.add(" §eRadius: 10m§r");
		}
		if(this == ModItems.missile_incendiary_strong) {
			list.add("§6[IC Missile II]§r");
			list.add(" §eRadius: 20m§r");
		}
		if(this == ModItems.missile_inferno) {
			list.add("§6[IC Missile III]§r");
			list.add(" §eRadius: 40m§r");
		}
		
		//Cluster
		if(this == ModItems.missile_cluster) {
			list.add("§1[CL Missile I]§r");
			list.add(" §9Radius: 5m§r");
			list.add(" §9Count: 25§r");
		}
		if(this == ModItems.missile_cluster_strong) {
			list.add("§1[CL Missile II]§r");
			list.add(" §9Radius: 7m§r");
			list.add(" §9Count: 50§r");
		}
		if(this == ModItems.missile_rain) {
			list.add("§1[CL Missile III]§r");
			list.add(" §9Radius: 10m§r");
			list.add(" §9Count: 100§r");
		}

		//Bunker
		if(this == ModItems.missile_buster) {
			list.add("§8[BU Missile I]§r");
			list.add(" §7Radius: 15m§r");
			list.add(" §7Depth: 15m§r");
		}
		if(this == ModItems.missile_buster_strong) {
			list.add("§8[BU Missile II]§r");
			list.add(" §7Radius: 20m§r");
			list.add(" §7Depth: 20m§r");
		}
		if(this == ModItems.missile_drill) {
			list.add("§8[BU Missile III]§r");
			list.add(" §7Radius: 30m§r");
			list.add(" §7Depth: 30m§r");
		}

		if(this == ModItems.missile_n2)	{
			list.add("§c[Extreme Missile]§r");
			list.add(" §eMax Radius: "+(int)(BombConfig.n2Radius/12) * 5+"m§r");
		}
		if(this == ModItems.missile_nuclear){
			list.add("§2[Nuclear Missile]§r");
			list.add(" §eRadius: "+BombConfig.missileRadius+"m§r");
			if(!BombConfig.disableNuclear){
				list.add("§2[Fallout]§r");
				list.add(" §aRadius: "+(int)BombConfig.missileRadius*(1+BombConfig.falloutRange/100)+"m§r");
			}
		}
		if(this == ModItems.missile_nuclear_cluster){
			list.add("§6[Thermonuclear Missile]§r");
			list.add(" §eRadius: "+BombConfig.missileRadius*2+"m§r");
			if(!BombConfig.disableNuclear){
				list.add("§2[Fallout]§r");
				list.add(" §aRadius: "+(int)BombConfig.missileRadius*2*(1+BombConfig.falloutRange/100)+"m§r");
			}
		}
		if(this == ModItems.missile_volcano){
			list.add("§4[Tectonic Missile]§r");
		}
		if(this == ModItems.missile_emp_strong){
			list.add("§3[EMP Missile]§r");
			list.add(" §bRadius: 100m§r");
		}
		if(this == ModItems.missile_emp){
			list.add("§3[EMP Missile]§r");
			list.add(" §bRadius: 50m§r");
		}
		if(this == ModItems.missile_micro){
			list.add("§2[Nuclear Micro Missile]§r");
			list.add(" §eRadius: "+BombConfig.fatmanRadius+"m§r");
			if(!BombConfig.disableNuclear){
				list.add("§2[Fallout]§r");
				list.add(" §aRadius: "+(int)BombConfig.fatmanRadius*(1+BombConfig.falloutRange/100)+"m§r");
			}
		}
		if(this == ModItems.missile_endo){
			list.add("§3[Thermal Missile]§r");
			list.add(" §bRadius: 30m§r");
		}
		if(this == ModItems.missile_exo){
			list.add("§4[Thermal Missile]§r");
			list.add(" §6Radius: 30m§r");
		}
		if(this == ModItems.missile_doomsday){
			list.add("§5[Cluster Missile Missile Missile]§r");
		}
		if(this == ModItems.missile_taint){
			list.add("§d[Evil Slime Missile]§r");
		}
		if(this == ModItems.missile_bhole){
			list.add("§0[Singularity Missile]§r");
		}
		if(this == ModItems.missile_schrabidium){
			list.add("§b[Schrabidium Missile]§r");
			list.add(" §eRadius: "+BombConfig.aSchrabRadius+"m§r");
		}
		if(this == ModItems.missile_anti_ballistic){
			list.add("§2[AB Missile]§r");
			list.add(" §aOnly targets other missiles§r");
		}
	}
}